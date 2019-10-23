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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Target;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.ExamMode;

/**
 * 原始计划的计划课程
 */
@Entity(name = "org.openurp.edu.program.plan.model.OriginalPlanCourse")
public class OriginalPlanCourse extends AbstractPlanCourse implements ExecutePlanCourse {

  private static final long serialVersionUID = -2091355773150181171L;

  /** 课程组 */
  @Target(OriginalCourseGroup.class)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseGroup group;

  /** 开课部门 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 考核方式 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamMode examMode;

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

}
