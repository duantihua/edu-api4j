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
import org.openurp.base.std.code.StdType;

/**
 * 专业计划
 */
@Entity(name = "org.openurp.edu.program.model.ExecutionPlan")
@Cacheable
@Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExecutionPlan extends AbstractCoursePlan {

  private static final long serialVersionUID = 7084539759992691314L;

  /** 部门 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;


  /** 课程组 */
  @OneToMany(orphanRemoval = true, targetEntity = ExecutionCourseGroup.class, cascade = { CascadeType.ALL })
  @JoinColumn(name = "plan_id", nullable = false)
  @OrderBy("indexno")
  @Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<CourseGroup> groups = CollectUtils.newArrayList();

  public ExecutionPlan() {
    super();
  }

  public List<CourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<CourseGroup> groups) {
    this.groups = groups;
  }

  public Object clone() throws CloneNotSupportedException {
    ExecutionPlan copy = (ExecutionPlan) super.clone();
    copy.setGroups(new ArrayList<CourseGroup>());
    copy.setId(null);
    return copy;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Date getBeginOn() {
    return null != program ? program.getBeginOn() : null;
  }

  public Date getEndOn() {
    return null != program ? program.getEndOn() : null;
  }
  @Override
  public String toString() {
    return "ExecutionPlan [program=" + program + ", startTerm=" + getStartTerm() + ", endTerm=" + getEndTerm()
        + "]";
  }

}
