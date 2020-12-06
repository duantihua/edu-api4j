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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.model.Direction;

/**
 * 个人计划的课程组
 */
@Entity(name = "org.openurp.edu.program.model.StdCourseGroup")
public class StdCourseGroup extends AbstractCourseGroup {

  private static final long serialVersionUID = -42451313562392026L;

  /** 个人计划 */
  @ManyToOne(targetEntity = StdPlan.class)
  @JoinColumn(name = "PLAN_ID", updatable = false, insertable = false, nullable = false)
  private StdPlan plan;

  /** 上级组 */
  @ManyToOne(targetEntity = StdCourseGroup.class)
  @JoinColumn(name = "PARENT_ID", nullable = true)
  private CourseGroup parent;

  /** 下级组列表 */
  @OneToMany(targetEntity = StdCourseGroup.class, cascade = { CascadeType.ALL })
  @OrderBy("indexno")
  @JoinColumn(name = "PARENT_ID", nullable = true)
  private List<CourseGroup> children = CollectUtils.newArrayList();

  /** 计划课程列表 */
  @OneToMany(mappedBy = "group", orphanRemoval = true, targetEntity = StdPlanCourse.class, cascade = {
      CascadeType.ALL })
  private List<PlanCourse> planCourses = CollectUtils.newArrayList();

  public CoursePlan getPlan() {
    return plan;
  }

  public void setPlan(CoursePlan plan) {
    this.plan = (StdPlan) plan;
  }

  public CourseGroup getParent() {
    return parent;
  }

  public void setParent(CourseGroup parent) {
    this.parent = parent;
  }

  public List<CourseGroup> getChildren() {
    return children;
  }

  public void setChildren(List<CourseGroup> children) {
    this.children = children;
  }

  public List<PlanCourse> getPlanCourses() {
    return planCourses;
  }

  public void setPlanCourses(List<PlanCourse> planCourses) {
    this.planCourses = planCourses;
  }

  public Object clone() throws CloneNotSupportedException {
    StdCourseGroup personalCourseGroup = (StdCourseGroup) super.clone();
    personalCourseGroup.setId(null);
    personalCourseGroup.setParent(null);
    personalCourseGroup.setChildren(new ArrayList<CourseGroup>());
    personalCourseGroup.setPlanCourses(new ArrayList<PlanCourse>());
    return personalCourseGroup;
  }

  public Direction getDirection() {
    return null;
  }

  public String getMicroName() {
    return null;
  }

}
