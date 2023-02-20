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
package org.openurp.edu.program.plan.util;

import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.CoursePlan;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.model.MajorCourseGroup;
import org.openurp.edu.program.model.MajorPlan;
import org.openurp.edu.program.model.MajorPlanCourse;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.model.ShareCourseGroup;
import org.openurp.edu.program.model.StdCourseGroup;
import org.openurp.edu.program.model.StdPlan;
import org.openurp.edu.program.model.StdPlanCourse;

/**
 * 用于获得eams-teach-program包中的各种类别的Hibernate映射的接口类
 */
public class ProgramHibernateClassGetter {

  public static Class<? extends CourseGroup> hibernateClass(CourseGroup planGroup) {
    if (MajorCourseGroup.class
        .isAssignableFrom(planGroup.getClass())) { return MajorCourseGroup.class; }
    if (StdCourseGroup.class.isAssignableFrom(planGroup.getClass())) { return StdCourseGroup.class; }
    if (ExecutionCourseGroup.class.isAssignableFrom(planGroup.getClass())) { return ExecutionCourseGroup.class; }
    return null;
  }

  public static Class<? extends PlanCourse> hibernateClass(PlanCourse planCourse) {
    if (MajorPlanCourse.class.isAssignableFrom(planCourse.getClass())) { return MajorPlanCourse.class; }
    if (StdPlanCourse.class.isAssignableFrom(planCourse.getClass())) { return StdPlanCourse.class; }
    if (ExecutionPlanCourse.class.isAssignableFrom(planCourse.getClass())) { return ExecutionPlanCourse.class; }
    return null;
  }

  public static Class<? extends CoursePlan> hibernateClass(CoursePlan plan) {
    if (MajorPlan.class.isAssignableFrom(plan.getClass())) { return MajorPlan.class; }
    if (StdPlan.class.isAssignableFrom(plan.getClass())) { return StdPlan.class; }
    if (ExecutionPlan.class.isAssignableFrom(plan.getClass())) { return ExecutionPlan.class; }
    return null;
  }

}
