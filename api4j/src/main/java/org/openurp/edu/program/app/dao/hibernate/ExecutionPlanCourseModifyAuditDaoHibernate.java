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
package org.openurp.edu.program.app.dao.hibernate;

import java.sql.Date;

import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.base.model.User;
import org.openurp.base.time.Terms;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.program.app.dao.ExecutionPlanCourseModifyAuditDao;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModify;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetail;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailAfter;
import org.openurp.edu.program.plan.dao.PlanCourseCommonDao;
import org.openurp.edu.program.plan.dao.PlanCourseGroupCommonDao;
import org.openurp.edu.program.model.ExecutionCourseGroup;
import org.openurp.edu.program.model.ExecutionPlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.plan.service.ExecutionPlanAuditException;

public class ExecutionPlanCourseModifyAuditDaoHibernate extends HibernateEntityDao
    implements ExecutionPlanCourseModifyAuditDao {

  private PlanCourseCommonDao planCourseCommonDao;

  private PlanCourseGroupCommonDao planCourseGroupCommonDao;

  public void approved(ExecutionPlanCourseModify apply, User assessor) throws ExecutionPlanAuditException {
    // 只能对待审核的申请进行审核
    if (!ExecutionPlanCourseModify.INITREQUEST
        .equals(apply.getFlag())) { throw new ExecutionPlanAuditException("只能对待审核的申请进行审核"); }

    ExecutionPlan plan = get(ExecutionPlan.class, apply.getExecutionPlan().getId());
    if (plan == null) { throw new ExecutionPlanAuditException("您要修改的专业培养计划已经不存在。"); }
    if (ExecutionPlanCourseModify.ADD.equals(apply.getRequisitionType())) {
      ExecutionPlanCourseModifyDetail after = apply.getNewPlanCourse();
      ExecutionPlanCourse planCourse = new ExecutionPlanCourse();
      planCourse.setCourse(after.getCourse());
      planCourse.setTerms(after.getTerms());
      if (planCourse.getTerms() == null) {
        planCourse.setTerms(new Terms(0));
      }
      planCourse.setRemark(after.getRemark());
      planCourse.setCompulsory(after.isCompulsory());
      ExecutionCourseGroup mg = get(ExecutionCourseGroup.class, after.getFakeCourseGroup().getId());
      if (mg == null) { throw new ExecutionPlanAuditException(
          "课程组不存在：" + after.getFakeCourseGroup().getCourseType().getName()); }
      planCourse.setGroup(mg);
      planCourse.setDepartment(after.getDepartment());
      planCourseCommonDao.addPlanCourse(planCourse, plan);
    } else if (ExecutionPlanCourseModify.DELETE.equals(apply.getRequisitionType())) {
      ExecutionPlanCourseModifyDetail before = apply.getOldPlanCourse();
      Course course = before.getCourse();
      ExecutionPlanCourse planCourse = planCourseCommonDao.getExecutionPlanCourseByCourse(plan, course);
      if (planCourse == null) { throw new ExecutionPlanAuditException("课程不存在：" + course); }
      planCourseCommonDao.removePlanCourse(planCourse, plan);
    } else if (ExecutionPlanCourseModify.MODIFY.equals(apply.getRequisitionType())) {
      ExecutionPlanCourseModifyDetail before = apply.getOldPlanCourse();
      ExecutionPlanCourse planCourse = planCourseCommonDao.getExecutionPlanCourseByCourse(plan, before.getCourse());
      if (planCourse == null) { throw new ExecutionPlanAuditException("课程不存在：" + before.getCourse()); }
      ExecutionCourseGroup oldGroup = (ExecutionCourseGroup) planCourse.getGroup();

      ExecutionPlanCourseModifyDetailAfter after = apply.getNewPlanCourse();
      planCourse.setCompulsory(after.isCompulsory());
      planCourse.setCourse(after.getCourse());
      planCourse.setTerms(after.getTerms());
      if (planCourse.getTerms() == null) {
        planCourse.setTerms(new Terms(0));
      }
      planCourse.setRemark(after.getRemark());
      ExecutionCourseGroup mg = get(ExecutionCourseGroup.class, after.getFakeCourseGroup().getId());
      if (mg == null) { throw new ExecutionPlanAuditException(
          "课程组不存在：" + after.getFakeCourseGroup().getCourseType().getName()); }

      planCourse.setGroup(mg);
      planCourse.setDepartment(after.getDepartment());
      planCourseCommonDao.updatePlanCourse(planCourse, plan);
      planCourseGroupCommonDao.saveOrUpdateCourseGroup(oldGroup);
    } else {
      throw new ExecutionPlanAuditException("错误的计划课程变更申请类型");
    }

    apply.setFlag(ExecutionPlanCourseModify.ACCEPT);
    apply.setAssessor(assessor);
    apply.setReplyDate(new Date(System.currentTimeMillis()));
    saveOrUpdate(apply);
  }

  public void rejected(ExecutionPlanCourseModify apply, User assessor) throws ExecutionPlanAuditException {
    // 只能对待审核的申请进行审核
    if (!ExecutionPlanCourseModify.INITREQUEST
        .equals(apply.getFlag())) { throw new ExecutionPlanAuditException("只能对待审核的申请进行审核"); }

    apply.setFlag(ExecutionPlanCourseModify.REFUSE);
    apply.setAssessor(assessor);
    apply.setReplyDate(new Date(System.currentTimeMillis()));
    saveOrUpdate(apply);
  }

  public void setPlanCourseCommonDao(PlanCourseCommonDao planCourseCommonDao) {
    this.planCourseCommonDao = planCourseCommonDao;
  }

  public void setPlanCourseGroupCommonDao(PlanCourseGroupCommonDao planCourseGroupCommonDao) {
    this.planCourseGroupCommonDao = planCourseGroupCommonDao;
  }

}
