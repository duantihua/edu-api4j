/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.grade.course.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.Model;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.base.model.User;
import org.openurp.edu.base.code.model.GradeType;
import org.openurp.edu.base.code.model.ScoreMarkStyle;
import org.openurp.edu.grade.Grade;
import org.openurp.edu.grade.course.domain.GradeTypeConstants;
import org.openurp.edu.lesson.model.Lesson;

/**
 * 成绩状态表
 * 记录了对应教学任务成绩<br>
 * 1)记录方式,<br>
 * 2)各种成绩成分的百分比,<br>
 * 3)各种成绩的确认状态,<br>
 * 4)各种成绩的发布状态<br>
 * 每种成绩在状态的站位参见GradeType.mark
 */
@Entity(name = "org.openurp.edu.grade.course.model.CourseGradeState")
public class CourseGradeState extends AbstractGradeState {

  private static final long serialVersionUID = 3297067284042522108L;

  /** 教学任务 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  /** 可录入各成绩类型的状态设置 */
  @OneToMany(mappedBy = "gradeState", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  private Set<ExamGradeState> examStates = CollectUtils.newHashSet();
  /** 可录入各成绩类型的状态设置 */
  @OneToMany(mappedBy = "gradeState", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  private Set<GaGradeState> gaStates = CollectUtils.newHashSet();
  /** 个人百分比审核状态 */
  @Enumerated(value = EnumType.STRING)
  private AuditStatus auditStatus;

  /** 审核理由 */
  private String auditReason;

  /** 其他录入人 */
  @ManyToOne(fetch = FetchType.LAZY)
  private User extraInputer;

  public CourseGradeState() {
  }

  public CourseGradeState(Lesson lesson) {
    this.lesson = lesson;
    this.setMarkStyle(new ScoreMarkStyle(ScoreMarkStyle.Percent));
  }

  public void updateStatus(GradeType gradeType, int status) {
    GradeState state = getState(gradeType);
    if (null == state) {
      if (gradeType.isGa()) {
        GaGradeState gas = (GaGradeState) Model.newInstance(GaGradeState.class);
        gas.setGradeState(this);
        gas.setGradeType(gradeType);
        gas.setStatus(status);
        gas.setMarkStyle(markStyle);
        gaStates.add(gas);
        state = gas;
      } else {
        ExamGradeState egs = (ExamGradeState) Model.newInstance(ExamGradeState.class);
        egs.setGradeState(this);
        egs.setGradeType(gradeType);
        egs.setStatus(status);
        egs.setMarkStyle(markStyle);
        examStates.add(egs);
        state = egs;
      }
    } else {
      state.setStatus(status);
    }
  }

  public GradeState getState(GradeType gradeType) {
    if (gradeType.isGa()) {
      for (GaGradeState state : gaStates) {
        if (state.getGradeType().getId().equals(gradeType.getId())) return state;
      }
    } else {
      for (ExamGradeState state : examStates) {
        if (state.getGradeType().getId().equals(gradeType.getId())) return state;
      }
    }
    return null;
  }

  public GradeState getOrCreateState(GradeType gradeType) {
    if (gradeType.isGa()) {
      GaGradeState result = null;
      for (GaGradeState state : gaStates) {
        if (state.getGradeType().getId().equals(gradeType.getId())) {
          result = state;
          break;
        }
      }
      if (null == result) {
        result = new GaGradeState();
        result.setStatus(Grade.Status.NEW);
        result.setUpdatedAt(new Date());
        result.setGradeType(gradeType);
        result.setGradeState(this);
        this.gaStates.add(result);
      }
      return result;
    } else {
      ExamGradeState result = null;
      for (ExamGradeState state : examStates) {
        if (state.getGradeType().getId().equals(gradeType.getId())) {
          result = state;
          break;
        }
      }
      if (null == result) {
        result = new ExamGradeState();
        result.setStatus(Grade.Status.NEW);
        result.setUpdatedAt(new Date());
        result.setGradeType(gradeType);
        result.setGradeState(this);
        this.examStates.add(result);
      }
      return result;
    }
  }

  /**
   * 是否是指定状态
   *
   * @param gradeType
   * @param status
   * @return
   */
  public boolean isStatus(GradeType gradeType, int status) {
    GradeState gradeTypeState = getState(gradeType);
    if (null == gradeTypeState) { return false; }
    return gradeTypeState.getStatus() == status;
  }

  public Short getPercent(GradeType gradeType) {
    for (Iterator<ExamGradeState> iter = examStates.iterator(); iter.hasNext();) {
      ExamGradeState gradeTypeState = (ExamGradeState) iter.next();
      if (null != gradeType && gradeTypeState.getGradeType().getId().equals(gradeType.getId())) { return gradeTypeState
          .getPercent(); }
    }
    return null;
  }

  public int getStatus(GradeType gradeType) {
    if (gradeType.isGa()) {
      for (GaGradeState egs : gaStates) {
        if (egs.getGradeType().getId().equals(gradeType.getId())) { return egs.getStatus(); }
      }
    } else {
      for (ExamGradeState egs : examStates) {
        if (egs.getGradeType().getId().equals(gradeType.getId())) { return egs.getStatus(); }
      }
    }
    return 0;
  }

  public GradeType getGradeType() {
    return new GradeType(GradeTypeConstants.FINAL_ID);
  }

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public Set<ExamGradeState> getExamStates() {
    return examStates;
  }

  public void setExamStates(Set<ExamGradeState> states) {
    this.examStates = states;
  }

  public AuditStatus getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(AuditStatus auditStatus) {
    this.auditStatus = auditStatus;
  }

  public String getAuditReason() {
    return auditReason;
  }

  public void setAuditReason(String auditReason) {
    this.auditReason = auditReason;
  }

  public User getExtraInputer() {
    return extraInputer;
  }

  public void setExtraInputer(User extraInputer) {
    this.extraInputer = extraInputer;
  }

  public Set<GaGradeState> getGaStates() {
    return gaStates;
  }

  public void setGaStates(Set<GaGradeState> gaStates) {
    this.gaStates = gaStates;
  }

  public enum AuditStatus {
    NEED_AUDIT("待审核"), DEPART_AUDIT_PASSED("审核通过"), DEPART_AUDIT_UNPASSED("审核未通过"), NEED_FINAL_AUDIT("院长审核"), FINAL_AUDIT_PASSED(
        "最终审核通过"), FINAL_AUDIT_UNPASSED("最终审核未通过");

    private String fullName;

    private AuditStatus() {
    }

    private AuditStatus(String fullName) {
      this.fullName = fullName;
    }

    public String getFullName() {
      return fullName;
    }
  }
}
