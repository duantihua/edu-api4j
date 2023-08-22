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
package org.openurp.edu.program.app.service;

import java.util.List;

import org.openurp.edu.program.app.model.ExecutionPlanCourseModify;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailAfter;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailBefore;
import org.openurp.edu.program.model.ExecutionPlanCourse;

/**
 */
public interface ExecutionPlanCourseModifyApplyService {

  /**
   * 保存一个培养计划变更申请
   *
   * @param apply
   * @param before 可以为null
   * @param after 可以为null
   */
  void saveModifyApply(ExecutionPlanCourseModify apply, ExecutionPlanCourseModifyDetailBefore before,
      ExecutionPlanCourseModifyDetailAfter after);

  /**
   * 获得某个用户的培养计划课程变更申请
   *
   * @param planId
   * @param userId
   * @return
   */
  List<ExecutionPlanCourseModify> myApplies(Long planId, Long userId);

  /**
   * 获得某个用户的待审核的计划课程变更申请(不包含添加课程的申请) 中的 计划课程
   *
   * @param planId
   * @param userId
   * @return
   */
  List<ExecutionPlanCourse> myReadyModifyApply(Long planId, Long userId);

  /**
   * 获得某个用户的待审核的计划课程变更申请(仅包含添加课程的申请) 中的 计划课程
   *
   * @param planId
   * @param userId
   * @return
   */
  List<ExecutionPlanCourseModify> myReadyAddApplies(Long planId, Long userId);

  /**
   * 获得某个培养计划的课程变更申请
   *
   * @param planId
   * @return
   */
  List<ExecutionPlanCourseModify> appliesOfPlan(Long planId);
}
