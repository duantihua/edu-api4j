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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.lang.time.WeekState;
import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.ExamMode;

/**
 * 公共共享课程组课程
 */
@Entity(name = "org.openurp.edu.program.plan.model.SharePlanCourse")
public class SharePlanCourse extends AbstractPlanCourse {

  private static final long serialVersionUID = -6806780960520737961L;

  /** 共享课程组 */
  @Target(ShareCourseGroup.class)
  @ManyToOne(fetch = FetchType.LAZY)
  protected CourseGroup group;

  /** 开课部门 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 开课部门 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamMode examMode;

  @Type(type = "org.beangle.commons.lang.time.hibernate.WeekStateType")
  private WeekState weekstate;

  public CourseGroup getGroup() {
    return group;
  }

  public void setGroup(CourseGroup group) {
    this.group = group;
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

  public WeekState getWeekstate() {
    return weekstate;
  }

  public void setWeekstate(WeekState weekstate) {
    this.weekstate = weekstate;
  }
}
