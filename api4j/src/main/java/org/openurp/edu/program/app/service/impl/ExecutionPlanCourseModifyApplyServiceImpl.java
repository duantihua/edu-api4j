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
package org.openurp.edu.program.app.service.impl;

import java.util.List;

import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.program.app.dao.ExecutionPlanCourseModifyApplyDao;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModify;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailAfter;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailBefore;
import org.openurp.edu.program.app.service.ExecutionPlanCourseModifyApplyService;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;

/**
 *
 */
public class ExecutionPlanCourseModifyApplyServiceImpl extends BaseServiceImpl implements
    ExecutionPlanCourseModifyApplyService {

  private ExecutionPlanCourseModifyApplyDao executePlanCourseModifyApplyDao;

  public void saveModifyApply(ExecutionPlanCourseModify apply, ExecutionPlanCourseModifyDetailBefore before,
      ExecutionPlanCourseModifyDetailAfter after) {
    executePlanCourseModifyApplyDao.saveModifyApply(apply, before, after);
  }

  public void setExecutionPlanCourseModifyApplyDao(ExecutionPlanCourseModifyApplyDao executePlanCourseModifyApplyDao) {
    this.executePlanCourseModifyApplyDao = executePlanCourseModifyApplyDao;
  }

  public List<ExecutionPlanCourseModify> myApplies(Long planId, Long userId) {

    OqlBuilder<ExecutionPlanCourseModify> query = OqlBuilder.from(ExecutionPlanCourseModify.class, "apply");
    query.where("apply.executePlan.id = :planId", planId).where("apply.proposer.id = :userId", userId)
        .orderBy("apply.flag").orderBy(Order.desc("apply.applyDate"));
    return entityDao.search(query);
  }

  public List<ExecutionPlanCourseModify> myReadyAddApplies(Long planId, Long userId) {
    OqlBuilder<ExecutionPlanCourseModify> query = OqlBuilder.from(ExecutionPlanCourseModify.class, "apply");
    query.where("apply.executePlan.id = :planId", planId).where("apply.proposer.id = :userId", userId)
        .where("apply.flag = :flag", ExecutionPlanCourseModify.INITREQUEST)
        .where("apply.requisitionType = :requisitionType", ExecutionPlanCourseModify.ADD)
        .orderBy(Order.desc("apply.applyDate"));
    return entityDao.search(query);
  }

  public List<ExecutionPlanCourseModify> appliesOfPlan(Long planId) {
    OqlBuilder<ExecutionPlanCourseModify> query = OqlBuilder.from(ExecutionPlanCourseModify.class, "apply");
    query.where("apply.executePlan.id = :planId", planId).orderBy("apply.flag")
        .orderBy(Order.desc("apply.applyDate")).limit(null);
    return entityDao.search(query);
  }

  public List<ExecutionPlanCourse> myReadyModifyApply(Long planId, Long userId) {
    OqlBuilder query = OqlBuilder.from(ExecutionPlanCourseModify.class.getName() + " apply, "
        + ExecutionPlan.class.getName() + " plan");
    query
        .select("select pcourse")
        .where("apply.executePlan.id = plan.id")
        .join("plan.groups", "cgroup")
        .join("cgroup.planCourses", "pcourse")
        .where("apply.executePlan.id = :planId", planId)
        .where("apply.proposer.id = :userId", userId)
        .where("apply.flag = :flag", ExecutionPlanCourseModify.INITREQUEST)
        .where(
            "exists(select state.id from " + ExecutionPlanCourseModifyDetailBefore.class.getName()
                + " state where state.apply.id = apply.id and state.course=pcourse.course)");
    return entityDao.search(query);
  }

}
