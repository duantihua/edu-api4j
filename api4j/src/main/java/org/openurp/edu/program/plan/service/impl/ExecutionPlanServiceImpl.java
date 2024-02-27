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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.plan.dao.PlanCommonDao;
import org.openurp.edu.program.plan.service.ExecutionPlanQueryBuilder;
import org.openurp.edu.program.plan.service.ExecutionPlanService;

import java.util.*;

/**
 * 培养计划服务接口
 */
public class ExecutionPlanServiceImpl extends BaseServiceImpl implements ExecutionPlanService {

  private PlanCommonDao planCommonDao;

  public Set<String> getUnusedCourseTypeNames(ExecutionPlan plan) {
    return planCommonDao.getUnusedCourseTypeNames(plan);
  }

  public void removeExecutionPlan(ExecutionPlan plan) {
    planCommonDao.removePlan(plan);
  }

  public void saveOrUpdateExecutionPlan(ExecutionPlan plan) {
    planCommonDao.saveOrUpdatePlan(plan);
  }

  public float statPlanCredits(Long planId) {
    return statPlanCredits(entityDao.get(ExecutionPlan.class, planId));
  }

  public float statPlanCredits(ExecutionPlan plan) {
    return planCommonDao.statPlanCredits(plan);
  }

  public boolean hasCourse(ExecutionCourseGroup cgroup, Course course) {
    return planCommonDao.hasCourse(cgroup, course);
  }

  public boolean hasCourse(ExecutionCourseGroup cgroup, Course course, PlanCourse planCourse) {
    return planCommonDao.hasCourse(cgroup, course, planCourse);
  }

  public void setPlanCommonDao(PlanCommonDao planCommonDao) {
    this.planCommonDao = planCommonDao;
  }

  public ExecutionPlan getExecutionPlanByAdminClass(Squad clazz) {
    List<ExecutionPlan> res = entityDao.search(ExecutionPlanQueryBuilder.build(clazz));
    return CollectUtils.isEmpty(res) ? null : res.get(0);
  }

  public List<ExecutionPlanCourse> getPlanCourses(ExecutionPlan plan) {
    if (CollectUtils.isEmpty(plan.getGroups())) {
      return Collections.EMPTY_LIST;
    }
    List<ExecutionPlanCourse> planCourses = new ArrayList<ExecutionPlanCourse>();
    for (Iterator iter = plan.getGroups().iterator(); iter.hasNext(); ) {
      ExecutionCourseGroup group = (ExecutionCourseGroup) iter.next();
      planCourses.addAll((List) group.getPlanCourses());
    }
    return planCourses;
  }

}
