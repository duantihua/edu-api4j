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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.hibernate.annotations.Type;
import org.openurp.base.time.Terms;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.program.plan.util.PlanUtils;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 课程设置中的课程组
 */
@MappedSuperclass
public abstract class AbstractCourseGroup extends LongIdObject implements CourseGroup, Cloneable {

  private static final long serialVersionUID = 1347767253840431206L;

  /** 要求完成组数 */
  protected short subCount;

  /** 课程类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected CourseType courseType;

  /** 要求学分 */
  private float credits;

  /** 要求门数 */
  private short courseCount;

  /** 备注 */
  @Size(max = 2000)
  private String remark;

  /** 学期学分分布 */
  private String termCredits;

  /** index no */
  @Size(max = 30)
  @NotNull
  private String indexno;

  /**是否自动累计学分*/
  private boolean autoAddup;

  /**开课学期*/
  @NotNull
  @Type(type = "org.openurp.base.time.hibernate.TermsType")
  protected Terms terms = Terms.Empty;

  public String getName() {
    return (null == courseType) ? null : courseType.getName();
  }

  public int getIndex() {
    String index = Strings.substringAfterLast(indexno, ".");
    if (Strings.isEmpty(index)) index = indexno;
    int idx = Numbers.toInt(index);
    if (idx <= 0) idx = 1;
    return idx;
  }

  public short getSubCount() {
    return subCount;
  }

  public void setSubCount(short subCount) {
    this.subCount = subCount;
  }

  public void addChildGroup(CourseGroup group) {
    group.setParent(this);
    getChildren().add(group);
  }

  public void addPlanCourse(PlanCourse planCourse) {
    for (PlanCourse planCourse1 : getPlanCourses()) {
      if (planCourse.getCourse().equals(planCourse1.getCourse())) { return; }
    }
    planCourse.setGroup(this);
    getPlanCourses().add(planCourse);
  }

  public void removePlanCourse(PlanCourse course) {
    getPlanCourses().remove(course);
  }

  public Object clone() throws CloneNotSupportedException {
    AbstractCourseGroup courseGroup = (AbstractCourseGroup) super.clone();
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
    return courseGroup;
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public short getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(short courseCount) {
    this.courseCount = courseCount;
  }

  public String getTermCredits() {
    return termCredits;
  }

  public void setTermCredits(String termCredits) {
    this.termCredits = termCredits;
  }

  public String getIndexno() {
    return indexno;
  }

  public void setIndexno(String indexno) {
    this.indexno = indexno;
  }

  @Override
  public boolean isAutoAddup() {
    return autoAddup;
  }

  public void setAutoAddup(boolean autoAddup) {
    this.autoAddup = autoAddup;
  }

  public Terms getTerms() {
    return terms;
  }

  public void setTerms(Terms terms) {
    this.terms = terms;
  }

  /**
   * 添加计划课程
   */
  public void addPlanCourses(List<PlanCourse> planCourses) {
    for (PlanCourse element : planCourses) {
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

  public List<PlanCourse> getPlanCourses(String terms) {
    if (Strings.isEmpty(terms)) { return new ArrayList<PlanCourse>(getPlanCourses()); }
    Set<PlanCourse> result = CollectUtils.newHashSet();
    Integer[] termSeq = Strings.splitToInt(terms);
    for (int i = 0; i < termSeq.length; i++) {
      result.addAll(PlanUtils.getPlanCourses(this, termSeq[i]));
    }
    return new ArrayList<PlanCourse>(result);
  }

  public int compareTo(CourseGroup o) {
    return getIndexno().compareTo(o.getIndexno());
  }
}
