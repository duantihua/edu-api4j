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
package org.openurp.edu.program.plan.service.impl;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.edu.program.plan.dao.PlanCourseCommonDao;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.plan.service.ExecutionPlanCourseService;

public class ExecutionPlanCourseServiceImpl extends BaseServiceImpl implements ExecutionPlanCourseService {

  private PlanCourseCommonDao planCourseCommonDao;

  public void addPlanCourse(ExecutionPlanCourse planCourse, ExecutionPlan plan) {
    planCourseCommonDao.addPlanCourse(planCourse, plan);
  }

  public void removePlanCourse(ExecutionPlanCourse planCourse, ExecutionPlan plan) {
    planCourseCommonDao.removePlanCourse(planCourse, plan);
  }

  public void updatePlanCourse(ExecutionPlanCourse planCourse, ExecutionPlan plan) {
    entityDao.saveOrUpdate(planCourse);
    planCourseCommonDao.updatePlanCourse(planCourse, plan);
  }

  public void setPlanCourseCommonDao(PlanCourseCommonDao planCourseCommonDao) {
    this.planCourseCommonDao = planCourseCommonDao;
  }

}
