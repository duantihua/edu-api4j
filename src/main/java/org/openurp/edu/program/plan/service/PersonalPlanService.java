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

import org.openurp.base.std.model.Student;
import org.openurp.edu.program.model.ExecutionPlan;

public interface PersonalPlanService {

  /**
   * 尝试获取学生的专业计划，以提供给个人和专业计划对比使用<br>
   *
   * @param std 肯定是已经有了个人计划的学生
   * @return
   */
  ExecutionPlan getExecutionPlanForDiff(Student std) ;

}
