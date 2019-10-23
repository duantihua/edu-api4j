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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.Model;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Target;
import org.openurp.code.edu.model.Language;
import org.openurp.edu.base.code.model.CourseAbilityRate;

/**
 * 公共共享课程组(默认实现)
 */
@Entity(name = "org.openurp.edu.program.plan.model.ShareCourseGroup")
public class ShareCourseGroup extends AbstractCourseGroup {

  private static final long serialVersionUID = -6481752967441999886L;

  /** 公共共享计划 */
  @Target(SharePlan.class)
  @ManyToOne(fetch = FetchType.LAZY)
  private CoursePlan plan;

  /** 上级组 */
  @Target(ShareCourseGroup.class)
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseGroup parent;

  /** 下级组列表 */
  @OneToMany(targetEntity = ShareCourseGroup.class, mappedBy = "parent")
  @OrderBy("indexno")
  @Cascade(CascadeType.ALL)
  private List<CourseGroup> children = CollectUtils.newArrayList();

  /** 课程列表 */
  @OneToMany(mappedBy = "group", targetEntity = SharePlanCourse.class)
  @Cascade(CascadeType.ALL)
  protected List<PlanCourse> planCourses = CollectUtils.newArrayList();

  /** 对应外语语种 */
  @ManyToOne(fetch = FetchType.LAZY)
  protected Language language;

  /** 对应课程能力等级 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseAbilityRate abilityRate;

  public Object clone() throws CloneNotSupportedException {
    ShareCourseGroup courseGroup = (ShareCourseGroup) super.clone();
    courseGroup.setId(null);
    // 克隆子组
    List<CourseGroup> groups = courseGroup.getChildren();
    List<CourseGroup> groupClones = CollectUtils.newArrayList();
    for (CourseGroup cg : groups) {
      CourseGroup groupClone = (CourseGroup) cg.clone();
      groupClones.add(groupClone);
      groupClone.setParent(courseGroup);
    }
    courseGroup.setChildren(groupClones);
    courseGroup.setPlanCourses(new ArrayList<PlanCourse>());
    // 克隆课程
    for (PlanCourse planCourse : getPlanCourses()) {
      courseGroup.addPlanCourse((PlanCourse) planCourse.clone());
    }
    // 克隆关系
    // courseGroup.setRelation((GroupRelation) getRelation().clone());
    return courseGroup;
  }

  public Object cloneToMajorCourseGroup() {
    MajorCourseGroup courseGroup = Model.newInstance(MajorCourseGroup.class);
    courseGroup.setCourseNum(getCourseNum());
    courseGroup.setCourseType(getCourseType());
    courseGroup.setCredits(getCredits());
    courseGroup.setIndexno(getIndexno());
    // courseGroup.setLimitCourseNum(getLimitCourseNum());
    // courseGroup.setLimitCredits(getLimitCredits());
    courseGroup.setTermCredits(getTermCredits());
    courseGroup.setRemark(getRemark());
    courseGroup.setChildren(new ArrayList<CourseGroup>());
    courseGroup.setPlanCourses(new ArrayList<PlanCourse>());
    // 克隆关系
    // courseGroup.setRelation(getRelation());
    courseGroup.setId(null);
    return courseGroup;
  }

  public void setPlanCourses(List<PlanCourse> planCourses) {
    this.planCourses = planCourses;
  }

  public List<CourseGroup> getChildren() {
    return children;
  }

  public void setChildren(List<CourseGroup> children) {
    this.children = children;
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

  /**
   * 添加计划课程
   */
  public void addPlanCourses(List<PlanCourse> givenPlanCourses) {
    for (PlanCourse element : givenPlanCourses) {
      boolean finded = false;
      for (PlanCourse element2 : planCourses) {
        if (element.getCourse().getId().equals(element2.getCourse().getId())) {
          finded = true;
          break;
        }
      }
      if (!finded) {
        element.setGroup(this);
        planCourses.add(element);
      }
    }
  }

  public void updateCoursePlan(CoursePlan plan) {
    setPlan(plan);
    if (getChildren() != null) {
      for (CourseGroup group : getChildren()) {
        group.updateCoursePlan(plan);
      }
    }
  }

  public List<PlanCourse> getPlanCourses() {
    return this.planCourses;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public CourseAbilityRate getAbilityRate() {
    return abilityRate;
  }

  public void setAbilityRate(CourseAbilityRate abilityRate) {
    this.abilityRate = abilityRate;
  }

}
