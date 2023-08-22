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

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.base.model.User;
import org.openurp.edu.program.app.dao.ExecutionPlanCourseGroupModifyAuditDao;
import org.openurp.edu.program.app.model.ExecutionCourseGroupModify;
import org.openurp.edu.program.app.service.ExecutionPlanCourseGroupModifyAuditService;
import org.openurp.edu.program.plan.dao.PlanCourseGroupCommonDao;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.plan.service.ExecutionPlanAuditException;
import org.openurp.edu.program.plan.service.ExecutionPlanCourseGroupService;

public class ExecutionPlanCourseGroupModifyAuditServiceImpl extends BaseServiceImpl
    implements ExecutionPlanCourseGroupModifyAuditService {

  private ExecutionPlanCourseGroupModifyAuditDao executePlanCourseGroupModifyAuditDao;

  protected ExecutionPlanCourseGroupService executePlanCourseGroupService;

  protected PlanCourseGroupCommonDao planCourseGroupCommonDao;

  public void approved(ExecutionCourseGroupModify apply, User assessor) throws ExecutionPlanAuditException {
    executePlanCourseGroupModifyAuditDao.approved(apply, assessor);

    /*
     * 补丁代码
     * 解决添加课程组时设置indexno的，太丑陋了，需要重构executePlanCourseGroupModifyAuditDao
     * 可能变成service
     */
    if (ExecutionCourseGroupModify.ADD.equals(apply.getRequisitionType())) {
      ExecutionCourseGroup parent = null;
      ExecutionPlan plan = entityDao.get(ExecutionPlan.class, apply.getExecutionPlan().getId());
      if (apply.getNewPlanCourseGroup() != null && apply.getNewPlanCourseGroup().getParent() != null) {
        parent = entityDao.get(ExecutionCourseGroup.class, apply.getNewPlanCourseGroup().getParent().getId());
      }
      Integer typeId = apply.getNewPlanCourseGroup().getCourseType().getId();
      ExecutionCourseGroup group = (ExecutionCourseGroup) planCourseGroupCommonDao
          .getCourseGroupByCourseType(new ExecutionCourseGroup(), plan.getId(), typeId);
      if (ExecutionCourseGroupModify.ADD.equals(apply.getRequisitionType())) {
        int indexno = 0;
        if (parent != null) {
          indexno = parent.getChildren().size() + 1;
        } else {
          indexno = plan.getTopCourseGroups().size() + 1;
        }
        executePlanCourseGroupService.move(group, parent, indexno);
      }
    }
  }

  public void rejected(ExecutionCourseGroupModify apply, User assessor) {
    executePlanCourseGroupModifyAuditDao.rejected(apply, assessor);
  }

  public void setExecutionPlanCourseGroupModifyAuditDao(
      ExecutionPlanCourseGroupModifyAuditDao executePlanCourseGroupModifyAuditDao) {
    this.executePlanCourseGroupModifyAuditDao = executePlanCourseGroupModifyAuditDao;
  }

  public void setExecutionPlanCourseGroupService(ExecutionPlanCourseGroupService executePlanCourseGroupService) {
    this.executePlanCourseGroupService = executePlanCourseGroupService;
  }

  public void setPlanCourseGroupCommonDao(PlanCourseGroupCommonDao planCourseGroupCommonDao) {
    this.planCourseGroupCommonDao = planCourseGroupCommonDao;
  }

}
