/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
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
package org.openurp.edu.grade.app.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.code.edu.model.ExamStatus;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;

/**
 * 成绩修改申请
 */
@Entity(name = "org.openurp.edu.grade.app.model.GradeModifyApply")
public class GradeModifyApply extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = -4325413107423926231L;
  /**
   * 学生
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /**
   * 教学日历
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 教学项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /*** 课程 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;
  /** 成绩类型 */

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GradeType gradeType;

  /** 考试情况 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamStatus examStatus;

  /** 原考试情况 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamStatus examStatusBefore;

  /** 原得分 */
  private Float origScore;

  /** 原得分字面值 */
  private String origScoreText;

  /** 得分 */
  private Float score;

  /** 得分字面值 */
  private String scoreText;

  /** 审核状态 */
  @NotNull
  @Enumerated(value = EnumType.STRING)
  private GradeModifyStatus status = GradeModifyStatus.NOT_AUDIT;

  /** 申请理由 */
  private String applyReason;

  /** 审核理由 */
  private String auditReason;

  /** 申请人 */
  @Size(max = 50)
  private String applyer;

  /** 审核人 */
  @Size(max = 50)
  private String auditer;

  /** 最终审核人 */
  @Size(max = 50)
  private String finalAuditer;

  public GradeType getGradeType() {
    return gradeType;
  }

  public void setGradeType(GradeType gradeType) {
    this.gradeType = gradeType;
  }

  public ExamStatus getExamStatus() {
    return examStatus;
  }

  public void setExamStatus(ExamStatus examStatus) {
    this.examStatus = examStatus;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public GradeModifyStatus getStatus() {
    return status;
  }

  public void setStatus(GradeModifyStatus status) {
    this.status = status;
  }

  public String getApplyer() {
    return applyer;
  }

  public void setApplyer(String applyer) {
    this.applyer = applyer;
  }

  public String getAuditer() {
    return auditer;
  }

  public void setAuditer(String auditer) {
    this.auditer = auditer;
  }

  public String getFinalAuditer() {
    return finalAuditer;
  }

  public void setFinalAuditer(String finalAuditer) {
    this.finalAuditer = finalAuditer;
  }

  public String getScoreText() {
    return scoreText;
  }

  public void setScoreText(String scoreText) {
    this.scoreText = scoreText;
  }

  public ExamStatus getExamStatusBefore() {
    return examStatusBefore;
  }

  public void setExamStatusBefore(ExamStatus examStatusBefore) {
    this.examStatusBefore = examStatusBefore;
  }

  public String getOrigScoreText() {
    return origScoreText;
  }

  public void setOrigScoreText(String origScoreText) {
    this.origScoreText = origScoreText;
  }

  public Float getOrigScore() {
    return origScore;
  }

  public void setOrigScore(Float origScore) {
    this.origScore = origScore;
  }

  public boolean hasChange() {
    if (this.score == null || this.origScore == null) { return this.score != this.origScore
        || !this.examStatus.equals(this.examStatusBefore); }
    return !this.score.equals(this.origScore) || !this.examStatus.equals(this.examStatusBefore);
  }

  public String getApplyReason() {
    return applyReason;
  }

  public void setApplyReason(String applyReason) {
    this.applyReason = applyReason;
  }

  public String getAuditReason() {
    return auditReason;
  }

  public void setAuditReason(String auditReason) {
    this.auditReason = auditReason;
  }

  public enum GradeModifyStatus {
    NOT_AUDIT("未审核"), DEPART_AUDIT_PASSED("院系审核通过"), DEPART_AUDIT_UNPASSED("院系审核未通过"), ADMIN_AUDIT_PASSED(
        "院长审核通过"), ADMIN_AUDIT_UNPASSED("院长审核未通过"), FINAL_AUDIT_PASSED(
            "最终审核通过"), FINAL_AUDIT_UNPASSED("最终审核未通过"), GRADE_DELETED("成绩已被删除");

    private String fullName;

    private GradeModifyStatus() {
    }

    private GradeModifyStatus(String fullName) {
      this.fullName = fullName;
    }

    public String getFullName() {
      return fullName;
    }
  }
}
