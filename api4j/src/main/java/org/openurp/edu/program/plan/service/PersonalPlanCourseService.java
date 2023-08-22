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

import org.openurp.edu.program.model.StdPlan;
import org.openurp.edu.program.model.StdPlanCourse;

public interface PersonalPlanCourseService {

  /**
   * 删除培养计划中的课程<br>
   * 如果是专业培养计划，级联删除对应专业的学生个人培养计划<br>
   * 对该课程所在课程组的更动,是否传播到其他培养计划，依照saveCourseGroup所示<br>
   *
   * @param planCourse
   * @param plan
   * @param needReCalculateCredit
   */
  public void removePlanCourse(StdPlanCourse planCourse, StdPlan plan);

  /**
   * 添加培养计划中的课程<br>
   * 如果是专业培养计划，级联添加对应专业的学生个人培养计划<br>
   *
   * @param planCourse
   * @param plan
   * @param needReCalculateCredit
   */
  public void addPlanCourse(StdPlanCourse planCourse, StdPlan plan);

  /**
   * 更新培养计划中的课程<br>
   *
   * @param planCourse
   * @param plan
   * @param needReCalculateCredit
   */
  public void updatePlanCourse(StdPlanCourse planCourse, StdPlan plan);

}
