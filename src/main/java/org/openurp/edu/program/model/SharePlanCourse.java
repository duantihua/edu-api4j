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
import org.beangle.commons.lang.time.WeekState;
import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Department;
import org.openurp.base.time.Terms;
import org.openurp.code.edu.model.ExamMode;
import org.openurp.base.edu.model.CalendarStage;
import org.openurp.base.edu.model.Course;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * 公共共享课程组课程
 */
@Entity(name = "org.openurp.edu.program.model.SharePlanCourse")
public class SharePlanCourse extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -6806780960520737961L;

  /**
   * 课程
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Course course;

  /**
   * 开课学期
   */
  @NotNull
  @Type(type = "org.openurp.base.time.hibernate.TermsType")
  protected Terms terms = Terms.Empty;

  /**
   * 共享课程组
   */
  @Target(ShareCourseGroup.class)
  @ManyToOne(fetch = FetchType.LAZY)
  protected ShareCourseGroup group;

  /**
   * 开课部门
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /**
   * 考核方式
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamMode examMode;

  @NotNull
  @Type(type = "org.beangle.commons.lang.time.hibernate.WeekStateType")
  private WeekState weekstate = WeekState.Zero;

  @ManyToOne(fetch = FetchType.LAZY)
  private CalendarStage stage;

  public ShareCourseGroup getGroup() {
    return group;
  }

  public void setGroup(ShareCourseGroup group) {
    this.group = group;
  }

  public WeekState getWeekstate() {
    return weekstate;
  }

  public void setWeekstate(WeekState weekstate) {
    this.weekstate = weekstate;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Terms getTerms() {
    return terms;
  }

  public void setTerms(Terms terms) {
    this.terms = terms;
  }

  public Object clone() throws CloneNotSupportedException {
    SharePlanCourse planCourse = (SharePlanCourse) super.clone();
    planCourse.setId(null);
    return planCourse;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public ExamMode getExamMode() {
    return examMode;
  }

  public void setExamMode(ExamMode examMode) {
    this.examMode = examMode;
  }

  public CalendarStage getStage() {
    return stage;
  }

  public void setStage(CalendarStage stage) {
    this.stage = stage;
  }
}
