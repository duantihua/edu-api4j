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
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.StdType;
import org.openurp.edu.program.model.Program;

/**
 * 专业计划
 */
@Entity(name = "org.openurp.edu.program.plan.model.ExecutePlan")
@Cacheable
@Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExecutePlan extends AbstractCoursePlan {

  private static final long serialVersionUID = 7084539759992691314L;

  /** 培养方案 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Program program;

  /** 部门 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 学生类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private StdType stdType;

  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /** 课程组 */
  @OneToMany(orphanRemoval = true, targetEntity = ExecuteCourseGroup.class, cascade = { CascadeType.ALL })
  @JoinColumn(name = "plan_id", nullable = false)
  @OrderBy("indexno")
  @Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<CourseGroup> groups = CollectUtils.newArrayList();

  /** 审核备注 */
  @OneToMany(mappedBy = "executePlan", orphanRemoval = true, cascade = { CascadeType.ALL })
  private List<ExecutePlanComment> comments = CollectUtils.newArrayList();

  public ExecutePlan() {
    super();
  }

  public List<CourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<CourseGroup> groups) {
    this.groups = groups;
  }

  public Object clone() throws CloneNotSupportedException {
    ExecutePlan copy = (ExecutePlan) super.clone();
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


  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public StdType getStdType() {
    return stdType;
  }

  public void setStdType(StdType stdType) {
    this.stdType = stdType;
  }

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

  public List<ExecutePlanComment> getComments() {
    return comments;
  }

  public void setComments(List<ExecutePlanComment> comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "ExecutePlanBean [program=" + program + ", startTerm=" + getStartTerm() + ", endTerm=" + getEndTerm()
        + "]";
  }

}
