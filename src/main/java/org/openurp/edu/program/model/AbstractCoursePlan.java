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
package org.openurp.edu.program.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.code.model.CourseType;
import org.openurp.base.edu.model.AuditState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象课程方案
 * </p>
 *
 * @since 2009
 */
@MappedSuperclass
public abstract class AbstractCoursePlan extends LongIdObject implements CoursePlan {

  private static final long serialVersionUID = 1606351182470625309L;
  /**
   * 培养方案
   */
  @NotNull
  @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  protected Program program;
  /**
   * 审核状态
   */
  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private AuditState auditState = AuditState.UNSUBMITTED;

  /**
   * 要求学分
   */
  @NotNull
  private float credits;

  /**
   * 起始学期
   */
  private int startTerm;

  /**
   * 结束学期
   */
  private int endTerm;


  /** 开始日期 */
  @NotNull
  private java.sql.Date beginOn;

  /** 结束日期 结束日期包括在有效期内 */
  @NotNull
  private java.sql.Date endOn;

  private java.util.Date updatedAt;

  public int getStartTerm() {
    return startTerm;
  }

  public void setStartTerm(int startTerm) {
    this.startTerm = startTerm;
  }

  public int getEndTerm() {
    return endTerm;
  }

  public void setEndTerm(int endTerm) {
    this.endTerm = endTerm;
  }

  public int getTermsCount() {
    return endTerm - startTerm + 1;
  }

  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public void addGroup(CourseGroup group) {
    if (null == getGroups()) {
      setGroups(new ArrayList<CourseGroup>());
    }
    getGroups().add(group);
    group.updateCoursePlan(this);
  }

  public List<CourseGroup> getTopCourseGroups() {
    if (getGroups() == null) {
      return new ArrayList<CourseGroup>();
    }
    List<CourseGroup> res = new ArrayList<CourseGroup>();
    for (CourseGroup group : getGroups()) {
      if (group != null && group.getParent() == null) res.add(group);
    }
    return res;
  }

  public CourseGroup getGroup(CourseType type) {
    if (null == getGroups()) return null;
    for (CourseGroup group : getGroups()) {
      if (group.getCourseType().equals(type)) return group;
    }
    return null;
  }

  public boolean isNumericTerm() {
    return true;
  }

  public Program getProgram() {
    return program;
  }

  public void setProgram(Program program) {
    this.program = program;
  }

  public AuditState getAuditState() {
    return auditState;
  }

  public void setAuditState(AuditState auditState) {
    this.auditState = auditState;
  }

  @Override
  public Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(Date beginOn) {
    this.beginOn = beginOn;
  }

  @Override
  public Date getEndOn() {
    return endOn;
  }

  public void setEndOn(Date endOn) {
    this.endOn = endOn;
  }

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
