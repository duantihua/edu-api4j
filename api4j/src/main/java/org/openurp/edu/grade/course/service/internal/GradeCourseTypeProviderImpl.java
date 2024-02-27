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
package org.openurp.edu.grade.course.service.internal;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.service.GradeCourseTypeProvider;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.CoursePlan;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.model.SharePlan;
import org.openurp.edu.program.plan.service.CoursePlanProvider;

public class GradeCourseTypeProviderImpl extends BaseServiceImpl implements GradeCourseTypeProvider {

  CoursePlanProvider coursePlanProvider;

  public CourseType getCourseType(Student std, Course course, CourseType defaultCourseType) {
    CoursePlan plan = coursePlanProvider.getCoursePlan(std);
    CourseType planCourseType = null;
    // 如果没有计划
    if (null != plan) {
      for (CourseGroup cg : plan.getGroups()) {
        if (cg == null) {
          continue;
        }
        for (PlanCourse pc : cg.getPlanCourses()) {
          if (pc.getCourse().equals(course)) {
            planCourseType = cg.getCourseType();
            break;
          }
        }
      }
    }
    // 计划里面没有该课程,就查找供公共课程
    if (null == planCourseType) {
      Integer grade = Integer.valueOf(std.getGrade().getCode().substring(0, 4));
      @SuppressWarnings("rawtypes")
      OqlBuilder builder = OqlBuilder.from(SharePlan.class, "sp").join("sp.groups", "spg")
          .join("spg.planCourses", "spgp").where("spgp.course=:course", course)
          .where("sp.project=:project", std.getProject())
          .where("year(sp.beginOn)<=:grade and (sp.endOn is null or year(sp.endOn)>=:grade)", grade)
          .select("spg.courseType");
      @SuppressWarnings("unchecked")
      List<CourseType> types = entityDao.search(builder);
      if (!types.isEmpty()) {
        if (null != defaultCourseType && types.contains(defaultCourseType)) {
          planCourseType = defaultCourseType;
        } else {
          planCourseType = types.get(0);
        }
      }
    }
    if (null == planCourseType) planCourseType = defaultCourseType;
    return planCourseType;
  }

  public void setCoursePlanProvider(CoursePlanProvider coursePlanProvider) {
    this.coursePlanProvider = coursePlanProvider;
  }

}
