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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.edu.base.model.Direction;

/**
 * 原始计划的课程组
 *
 *
 */
@Entity(name = "org.openurp.edu.program.plan.model.OriginalCourseGroup")
public class OriginalCourseGroup extends AbstractCourseGroup {

  private static final long serialVersionUID = 4144045243297075224L;

  /** 自定义组名 */
  @Size(max = 100)
  private String alias;

  /** 该组针对的专业方向 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Direction direction;

  /** 培养方案 */
  @ManyToOne(targetEntity = OriginalPlan.class)
  @JoinColumn(name = "PLAN_ID", updatable = false, insertable = false, nullable = false)
  private CoursePlan plan;

  /** 上级组 */
  @ManyToOne(targetEntity = OriginalCourseGroup.class)
  @JoinColumn(name = "PARENT_ID", nullable = true)
  private CourseGroup parent;

  /** 下级节点 */
  @OneToMany(targetEntity = OriginalCourseGroup.class, cascade = { CascadeType.ALL })
  @OrderBy("indexno")
  @JoinColumn(name = "PARENT_ID", nullable = true)
  private List<CourseGroup> children = CollectUtils.newArrayList();

  /** 组内课程 */
  @OneToMany(mappedBy = "group", orphanRemoval = true, targetEntity = OriginalPlanCourse.class, cascade = {
      CascadeType.ALL })
  private List<PlanCourse> planCourses = CollectUtils.newArrayList();

  @Override
  public String getName() {
    StringBuilder sb = new StringBuilder();
    if (null != courseType) sb.append(courseType.getName());
    if (null != alias) sb.append(" ").append(alias);
    return sb.toString();
  }

  public CoursePlan getPlan() {
    return plan;
  }

  public void setPlan(CoursePlan plan) {
    this.plan = plan;
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

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

}
