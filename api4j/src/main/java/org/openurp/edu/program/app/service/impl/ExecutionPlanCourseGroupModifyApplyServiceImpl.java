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
import org.openurp.edu.program.app.dao.ExecutionPlanCourseGroupModifyApplyDao;
import org.openurp.edu.program.app.model.FakeCourseGroup;
import org.openurp.edu.program.app.model.ExecutionCourseGroupModify;
import org.openurp.edu.program.app.model.ExecutionCourseGroupModifyDetailAfter;
import org.openurp.edu.program.app.model.ExecutionCourseGroupModifyDetailBefore;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModify;
import org.openurp.edu.program.app.service.ExecutionPlanCourseGroupModifyApplyService;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlan;

public class ExecutionPlanCourseGroupModifyApplyServiceImpl extends BaseServiceImpl implements
    ExecutionPlanCourseGroupModifyApplyService {
  private ExecutionPlanCourseGroupModifyApplyDao executePlanCourseGroupModifyApplyDao;

  public List<ExecutionCourseGroupModify> appliesOfPlan(Long planId) {
    OqlBuilder<ExecutionCourseGroupModify> oql = OqlBuilder.from(ExecutionCourseGroupModify.class, "applyBean");
    oql.where("applyBean.executePlan.id =:planId", planId);
    return entityDao.search(oql);
  }

  public List<ExecutionCourseGroupModify> myApplies(Long planId, Long userId) {
    OqlBuilder<ExecutionCourseGroupModify> oql = OqlBuilder.from(ExecutionCourseGroupModify.class, "apply");
    oql.where("apply.executePlan.id =:planId", planId).where("apply.proposer.id =:userId", userId)
        .orderBy("apply.flag").orderBy(Order.desc("apply.applyDate"));
    return entityDao.search(oql);
  }

  public List<ExecutionCourseGroup> myReadyModifyApply(Long planId, Long userId) {
    OqlBuilder query = OqlBuilder.from(ExecutionCourseGroupModify.class.getName() + " apply, "
        + ExecutionPlan.class.getName() + " plan");
    query
        .select("select cgroup")
        .where("apply.executePlan.id = plan.id")
        .join("plan.groups", "cgroup")
        .where("apply.executePlan.id = :planId", planId)
        .where("apply.proposer.id = :userId", userId)
        .where("apply.flag = :flag", ExecutionPlanCourseModify.INITREQUEST)
        .where(
            "exists(select state.id from " + ExecutionCourseGroupModifyDetailBefore.class.getName() + " state "
                + "where state.apply.id = apply.id and state.courseType=cgroup.courseType)");
    return entityDao.search(query);
  }

  public List<ExecutionCourseGroupModify> myReadyAddApplies(Long planId, Long userId) {
    OqlBuilder<ExecutionCourseGroupModify> oql = OqlBuilder.from(ExecutionCourseGroupModify.class, "apply");
    oql.where("apply.executePlan.id =:planId", planId).where("apply.proposer.id =:userId", userId)
        .where("apply.flag = :flag", ExecutionPlanCourseModify.INITREQUEST)
        .where("apply.requisitionType = :requisitionType", ExecutionPlanCourseModify.ADD)
        .orderBy(Order.desc("apply.applyDate"));
    return entityDao.search(oql);
  }

  /**
   * 保存课程组修改申请的方法
   * [获取修改前课程组的信息]
   */
  public void saveModifyApply(ExecutionCourseGroupModify modifyBean, Long courseGroupId,
      ExecutionCourseGroupModifyDetailAfter after) {
    ExecutionCourseGroupModifyDetailBefore before = null;
    if (courseGroupId != null) {
      ExecutionCourseGroup courseGroup = entityDao.get(ExecutionCourseGroup.class, courseGroupId);
      before = new ExecutionCourseGroupModifyDetailBefore();
      before.setApply(modifyBean);
      before.setSubCount(courseGroup.getSubCount());
      before.setCourseType(courseGroup.getCourseType());
      before.setCourseCount(courseGroup.getCourseCount());
      before.setCredits(courseGroup.getCredits());
      before.setParent(new FakeCourseGroup((ExecutionCourseGroup) courseGroup.getParent()));
      before.setRemark(courseGroup.getRemark());
      before.setTermCredits(courseGroup.getTermCredits());
    }
    executePlanCourseGroupModifyApplyDao.saveModifyApply(modifyBean, before, after);
  }

  public void setExecutionPlanCourseGroupModifyApplyDao(
      ExecutionPlanCourseGroupModifyApplyDao executePlanCourseGroupModifyApplyDao) {
    this.executePlanCourseGroupModifyApplyDao = executePlanCourseGroupModifyApplyDao;
  }

}
