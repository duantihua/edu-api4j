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
package org.openurp.edu.grade.plan.adapters;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.functor.Predicate;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.time.Terms;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.CoursePlan;
import org.openurp.edu.program.model.PlanCourse;

import java.util.ArrayList;
import java.util.List;

public class CourseGroupAdapter implements CourseGroup {

  private static final long serialVersionUID = 3870693368996408615L;

  private CoursePlan coursePlan;

  private List<CourseGroup> groups = new ArrayList<CourseGroup>();

  public CourseGroupAdapter(CoursePlan coursePlan) {
    super();
    this.coursePlan = coursePlan;
    groups = (List<CourseGroup>) CollectUtils.select(coursePlan.getGroups(), new Predicate<CourseGroup>() {
      public Boolean apply(CourseGroup group) {
        return (null == group.getParent());
      }
    });
  }

  /**
   * 返回顶层组
   */
  public List<CourseGroup> getChildren() {
    return groups;
  }

  /**
   * 返回要求学分<br>
   * 要求学分不准确，因为审核的时候可能是部分审核
   */
  @Deprecated
  public float getCredits() {
    return coursePlan.getCredits();
  }

  public String getName() {
    return "plan";
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public short getCourseCount() {
    return 0;
  }

  public CoursePlan getCoursePlan() {
    return coursePlan;
  }

  public CourseType getCourseType() {
    return null;
  }

  public void addChildGroup(CourseGroup arg0) {
  }

  public void addPlanCourse(PlanCourse arg0) {
  }

  public CourseGroup getParent() {
    return null;
  }

  public List<PlanCourse> getPlanCourses() {
    return new ArrayList<PlanCourse>();
  }

  public String getRemark() {
    return null;
  }

  public boolean isCompulsory() {
    return false;
  }

  public void setCourseCount(short arg0) {
  }

  public void setCoursePlan(CoursePlan arg0) {
  }

  public void setCourseType(CourseType arg0) {
  }

  public void setCredits(float arg0) {
  }

  public void setParent(CourseGroup arg0) {

  }

  public void setRemark(String arg0) {

  }

  public void updateCoursePlan(CoursePlan arg0) {

  }

  public Long getId() {
    return null;
  }

  public void setId(Long arg0) {
  }

  public Long getLongId() {
    return null;
  }

  public boolean isPersisted() {
    return false;
  }

  public boolean saved() {
    return false;
  }

  public boolean unsaved() {
    return false;
  }

  public boolean isTransient() {
    return false;
  }

  public String key() {
    return null;
  }

  public void removePlanCourse(PlanCourse course) {
  }

  public void statCreditAndHour(int termsCount) {

  }

  /**
   * 但是没有任何实现
   */
  public String getCreditPerTerms() {
    throw new UnsupportedOperationException("CoursePlanGroupAdapter.getCreditPerTerms没有实现");
  }

  /**
   * 但是没有任何实现
   */
  public void setCreditPerTerms(String arg0) {
    throw new UnsupportedOperationException("CoursePlanGroupAdapter.setCreditPerTerms没有实现");
  }

  public List<String> getCreditList() {
    // TODO Auto-generated method stub
    return null;
  }

  public float getCredits(List<Integer> terms) {
    // TODO Auto-generated method stub
    return 0;
  }

  public List<? extends PlanCourse> getGroupCourses() {
    // TODO Auto-generated method stub
    return null;
  }

  public CourseType getParentCourseType() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<? extends PlanCourse> getPlanCourses(List<Integer> termList) {
    // TODO Auto-generated method stub
    return null;
  }

  public List<? extends PlanCourse> getPlanCourses(String terms) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isSameGroup(Object object) {
    // TODO Auto-generated method stub
    return false;
  }

  public void setGroupCourses(List<? extends PlanCourse> groupCourses) {
    // TODO Auto-generated method stub

  }

  public CoursePlan getPlan() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setPlan(CoursePlan plan) {
    // TODO Auto-generated method stub

  }

  public void setChildren(List<CourseGroup> children) {
    // TODO Auto-generated method stub

  }

  public void setPlanCourses(List<PlanCourse> planCourses) {
    // TODO Auto-generated method stub

  }

  public void addPlanCourses(List<PlanCourse> planCourses) {
    // TODO Auto-generated method stub

  }

  public String getTermCredits() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setTermCredits(String termCredits) {
    // TODO Auto-generated method stub

  }

  public int compareTo(CourseGroup o) {
    // TODO Auto-generated method stub
    return 0;
  }

  public String getIndexno() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setIndexno(String indexno) {
    // TODO Auto-generated method stub

  }

  public int getIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public short getSubCount() {
    return -1;
  }

  @Override
  public void setSubCount(short subCount) {

  }

  @Override
  public boolean isAutoAddup() {
    return true;
  }

  @Override
  public Terms getTerms() {
    return Terms.Empty;
  }

  @Override
  public void setTerms(Terms terms) {

  }

  public int getCreditHours() {
    return 0;
  }

  public void setCreditHours(int creditHours) {
  }

  public String getHourRatios() {
    return null;
  }

  @Override
  public void setHourRatios(String hourRatios) {
  }
}
