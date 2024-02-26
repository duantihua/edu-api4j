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

import java.util.List;
import java.util.Map;

import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.model.StdPlan;

/**
 * 个人培养计划与专业培养计划对比服务类
 * 从eams-3shufe移植
 */
public interface PersonalPlanCompareService {

  /**
   * 获得两个培养计划之间的差异
   *
   * @param executePlan
   * @param stdExecutionPlan
   * @return List<PlanCourse>[0] 专业培养计划中的课程 List<PlanCourse>[1]培养计划中的课程
   */
  Map<String, List<? extends PlanCourse>[]> diffPersonalAndExecutionPlan(ExecutionPlan executePlan,
      StdPlan stdExecutionPlan);

  /**
   * 将一个培养计划中的课程，复制到另一个培养计划对应的类别中<br>
   *
   * @param fromPlan
   * @param toPlan
   * @param courseTypePlanCourseIds
   * @throws PersonalPlanSyncException
   */
  void copyPlanCourses(ExecutionPlan fromPlan, StdPlan toPlan, List<Number[]> courseTypePlanCourseIds)
      throws PersonalPlanSyncException;

  /**
   * 将一个培养计划中的类别，完全复制到另一个培养计划中<br>
   * 注意，另一个培养计划中必须没有那种类别<br>
   *
   * @param fromPlan
   * @param toPlan
   * @param courseTypeIds
   * @throws PersonalPlanSyncException
   */
  void copyCourseGroups(ExecutionPlan fromPlan, StdPlan toPlan, List<Integer> courseTypeIds)
      throws PersonalPlanSyncException;

  /**
   * TODO delete不安全
   * 将一个培养计划中的课程删除
   *
   * @param plan
   * @param courseTypePlanCourseIds
   * @throws PersonalPlanSyncException
   */
  void deletePlanCourses(StdPlan plan, List<Number[]> courseTypePlanCourseIds)
      throws PersonalPlanSyncException;

  /**
   * TODO delete不安全
   * 将一个培养计划中的类别删除
   *
   * @param plan
   * @param courseTypeIds
   * @throws PersonalPlanSyncException
   */
  void deleteCourseGroups(StdPlan plan, List<Integer> courseTypeIds) throws PersonalPlanSyncException;

}
