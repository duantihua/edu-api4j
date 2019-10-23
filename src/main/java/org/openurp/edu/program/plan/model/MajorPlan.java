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
package org.openurp.edu.program.plan.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.edu.base.model.Program;

/**
 * 专业计划
 */
@Entity(name = "org.openurp.edu.program.plan.model.MajorPlan")
@Cacheable
@Cache(region = "eams.teach", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MajorPlan extends AbstractCoursePlan {

  private static final long serialVersionUID = 7084539759992691314L;

  /** 培养方案 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Program program;

  /** 课程组 */
  @OneToMany(orphanRemoval = true, targetEntity = MajorCourseGroup.class, cascade = { CascadeType.ALL })
  @JoinColumn(name = "plan_id", nullable = false)
  @OrderBy("indexno")
  @Cache(region = "eams.teach", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<CourseGroup> groups = CollectUtils.newArrayList();

  /** 审核备注 */
  @OneToMany(mappedBy = "majorPlan", orphanRemoval = true, cascade = { CascadeType.ALL })
  private List<MajorPlanComment> comments = CollectUtils.newArrayList();

  public MajorPlan() {
    super();
  }

  public List<CourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<CourseGroup> groups) {
    this.groups = groups;
  }

  public Object clone() throws CloneNotSupportedException {
    MajorPlan copy = (MajorPlan) super.clone();
    copy.setGroups(new ArrayList<CourseGroup>());
    copy.setId(null);
    return copy;
  }

  public Program getProgram() {
    return program;
  }

  public void setProgram(Program program) {
    this.program = program;
  }

  /** 开始日期 */
  @NotNull
  private Date beginOn;

  /** 结束日期 结束日期包括在有效期内 */
  private Date endOn;

  public Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(Date beginOn) {
    this.beginOn = beginOn;
  }

  public Date getEndOn() {
    return endOn;
  }

  public void setEndOn(Date endOn) {
    this.endOn = endOn;
  }

  public List<MajorPlanComment> getComments() {
    return comments;
  }

  public void setComments(List<MajorPlanComment> comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "MajorPlanBean [program=" + program + ", startTerm=" + getStartTerm() + ", endTerm=" + getEndTerm()
        + "]";
  }

}
