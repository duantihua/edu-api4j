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
package org.openurp.edu.program.app.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.CourseHour;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlanCourse;

/** 专业计划课程修改后信息 */
@Entity(name = "org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailAfter")
@Table(name = "EXECUTE_COURSE_MOD_AFTERS")
public class ExecutionPlanCourseModifyDetailAfter extends ExecutionPlanCourseModifyDetail {

  private static final long serialVersionUID = 7799663739549705026L;

  /** 课程申请 */
  @OneToOne(optional = false, mappedBy = "newPlanCourse")
  @JoinColumn(name = "MA_PLAN_C_MOD_A_APPLY")
  protected ExecutionPlanCourseModify apply;

  public ExecutionPlanCourseModifyDetailAfter() {
    super();
  }

  public ExecutionPlanCourseModifyDetailAfter(Course course, ExecutionCourseGroup courseGroup) {
    super();
    setCourse(course);
    setFakeCourseGroupByReal(courseGroup);
  }

  public ExecutionPlanCourseModifyDetailAfter(ExecutionPlanCourse planCourse) {
    setCourse(planCourse.getCourse());
    setFakeCourseGroupByReal((ExecutionCourseGroup) planCourse.getGroup());
    // getCourseHours().putAll(planCourse.getCourseHours());
    // setHskLevel(planCourse.getHskLevel());
    // getPreCourses().addAll(planCourse.getPreCourses());
    setRemark(planCourse.getRemark());
    setDepartment(planCourse.getDepartment());
    setTerms(planCourse.getTerms());
  }

  public ExecutionPlanCourseModify getApply() {
    return apply;
  }

  public void setApply(ExecutionPlanCourseModify apply) {
    this.apply = apply;
  }

}
