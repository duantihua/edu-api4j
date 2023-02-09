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
package org.openurp.edu.program.plan.service;

import java.util.Collection;
import java.util.List;

import org.openurp.base.edu.code.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.program.model.CoursePlan;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.model.PlanCourse;

/**
 * 培养计划服务接口
 */
public interface ExecutionPlanService {

  List<ExecutionPlanCourse> getPlanCourses(ExecutionPlan plan);

  /**
   * 根据行政班的grade,stdType,department,major,direction,level属性来精确匹配找到一个专业培养计划
   *
   * @param clazz
   * @return
   */
  ExecutionPlan getExecutionPlanByAdminClass(Squad clazz);

  /**
   * 保存新的培养计划
   *
   * @param plan
   * @return
   */
  void saveOrUpdateExecutionPlan(ExecutionPlan plan);

  /**
   * 删除培养计划
   */
  void removeExecutionPlan(ExecutionPlan plan);

  /**
   * 查找没有在某个培养计划使用的课程类别
   *
   * @param plan
   * @return
   */
  List<CourseType> getUnusedCourseTypes(ExecutionPlan plan);

  /**
   * 统计培养计划的总学分
   *
   * @param planId
   * @return
   */
  float statPlanCredits(Long planId);

  /**
   * 统计总学分
   */
  float statPlanCredits(ExecutionPlan plan);

  /**
   * 检测某个专业培养计划是否有这个课程
   *
   * @param cgroup
   * @param course
   * @return
   */
  boolean hasCourse(ExecutionCourseGroup cgroup, Course course);

  boolean hasCourse(ExecutionCourseGroup cgroup, Course course, PlanCourse planCourse);

}
