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
import org.openurp.code.edu.model.CourseType;
import org.openurp.edu.program.model.*;
import org.openurp.edu.program.plan.dao.PlanCommonDao;
import org.openurp.edu.program.plan.dao.PlanCourseCommonDao;
import org.openurp.edu.program.plan.dao.PlanCourseGroupCommonDao;
import org.openurp.edu.program.plan.service.PersonalPlanCompareService;
import org.openurp.edu.program.plan.service.PersonalPlanSyncException;
import org.openurp.edu.program.plan.service.PlanCompareService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 个人计划和专业计划的对比
 * 从eams-3shufe移植
 */
public class PersonalPlanCompareServiceImpl extends BaseServiceImpl implements PersonalPlanCompareService {

  private PlanCommonDao planCommonDao;

  private PlanCourseCommonDao planCourseCommonDao;

  private PlanCourseGroupCommonDao planCourseGroupCommonDao;

  private PlanCompareService planCompareService;

  public Map<String, List<? extends PlanCourse>[]> diffPersonalAndExecutivePlan(ExecutivePlan executePlan,
                                                                                StdPlan stdPlan) {
    return planCompareService.diff(executePlan, stdPlan);
  }

  public void copyCourseGroups(ExecutivePlan fromPlan, StdPlan toPlan, List<Integer> courseTypeIds)
      throws PersonalPlanSyncException {
    copyCourseGroups(fromPlan, toPlan, courseTypeIds, true);
  }

  public void copyPlanCourses(ExecutivePlan fromPlan, StdPlan toPlan, List<Number[]> courseTypePlanCourseIds)
      throws PersonalPlanSyncException {
    for (Number[] courseTypePlanCourseId : courseTypePlanCourseIds) {
      Integer courseTypeId = courseTypePlanCourseId[0].intValue();
      CourseType type = entityDao.get(CourseType.class, courseTypeId);

      if (fromPlan.getGroup(type) == null) {
        throw new PersonalPlanSyncException(
            "fromPlan doesn't have this kind of course stdType's course group: " + type.toString());
      }
      if (toPlan.getGroup(type) == null) {
        copyCourseGroups(fromPlan, toPlan, Collections.singletonList(courseTypeId), false);
      }
      ExecutivePlanCourse sourcePlanCourse = entityDao.get(ExecutivePlanCourse.class,
          (Long) courseTypePlanCourseId[1]);
      if (sourcePlanCourse == null) {
        throw new PersonalPlanSyncException("Cannot find PlanCourse");
      }
      planCourseGroupCommonDao.copyPlanCourse(sourcePlanCourse, toPlan.getGroup(type), StdPlanCourse.class);
      toPlan.setCredits(planCommonDao.statPlanCredits(toPlan));
      planCommonDao.saveOrUpdatePlan(toPlan);
      entityDao.refresh(toPlan);
    }

  }

  private void copyCourseGroups(ExecutivePlan fromPlan, StdPlan toPlan, List<Integer> courseTypeIds,
                                boolean copyPlanCourses) throws PersonalPlanSyncException {
    for (Integer typeId : courseTypeIds) {
      CourseType type = entityDao.get(CourseType.class, typeId);

      if (fromPlan.getGroup(type) == null) {
        throw new PersonalPlanSyncException("源计划不存在课程类别：" + type.toString());
      }
      if (toPlan.getGroup(type) != null) {
        deleteCourseGroups(toPlan, Collections.singletonList(typeId));
      }
      CourseGroup sourceGroup = fromPlan.getGroup(type);
      if (sourceGroup == null) {
        throw new PersonalPlanSyncException("源计划不存在课程类别：" + type.toString());
      }
      CourseGroup copy = null;
      // 如果有上级课程组，则必须先复制上级课程组
      if (sourceGroup.getParent() != null) {
        if (toPlan.getGroup(sourceGroup.getParent().getCourseType()) == null) {
          throw new PersonalPlanSyncException(
              "请先复制父课程组：" + sourceGroup.getParent().getCourseType().getName());
        }
        copy = planCourseGroupCommonDao.copyCourseGroup(sourceGroup,
            toPlan.getGroup(sourceGroup.getParent().getCourseType()), toPlan, StdCourseGroup.class, StdPlanCourse.class);
      } else {
        copy = planCourseGroupCommonDao.copyCourseGroup((ExecutiveCourseGroup) sourceGroup, null, toPlan, StdCourseGroup.class, StdPlanCourse.class);
      }
      if (copy == null) {
        throw new PersonalPlanSyncException("复制课程组失败：" + type.toString());
      }
      if (!copyPlanCourses) {
        copy.getPlanCourses().clear();
      }

      planCourseGroupCommonDao.updateGroupTreeCredits(planCourseGroupCommonDao.getTopGroup(copy));
      toPlan.setCredits(planCommonDao.statPlanCredits(toPlan));
      entityDao.saveOrUpdate(toPlan);
      entityDao.refresh(toPlan);
    }
  }

  public void deleteCourseGroups(StdPlan plan, List<Integer> courseTypeIds) throws PersonalPlanSyncException {
    for (Integer typeId : courseTypeIds) {
      CourseType type = entityDao.get(CourseType.class, typeId);
      CourseGroup group = plan.getGroup(type);
      if (group == null) {
        throw new PersonalPlanSyncException("源计划不存在课程类别：" + type.toString());
      }
      planCourseGroupCommonDao.removeCourseGroup((ExecutiveCourseGroup) group);
      plan.setCredits(planCommonDao.statPlanCredits(plan));
      entityDao.saveOrUpdate(plan);
      entityDao.refresh(plan);
    }

  }

  public void deletePlanCourses(StdPlan plan, List<Number[]> courseTypePlanCourseIds)
      throws PersonalPlanSyncException {
    for (Number[] courseTypePlanCourseId : courseTypePlanCourseIds) {
      CourseType type = entityDao.get(CourseType.class, courseTypePlanCourseId[0].intValue());
      CourseGroup group = plan.getGroup(type);
      if (group == null) {
        throw new PersonalPlanSyncException("源计划不存在课程类别：" + type.toString());
      }

      StdPlanCourse toBeRemoved = entityDao.get(StdPlanCourse.class, courseTypePlanCourseId[1].longValue());
      if (toBeRemoved == null) {
        throw new PersonalPlanSyncException("无法找到计划课程");
      }
      planCourseCommonDao.removePlanCourse(toBeRemoved, plan);
      entityDao.refresh(plan);
    }

  }

  public void setPlanCourseGroupCommonDao(PlanCourseGroupCommonDao planCourseGroupCommonDao) {
    this.planCourseGroupCommonDao = planCourseGroupCommonDao;
  }

  public void setPlanCourseCommonDao(PlanCourseCommonDao planCourseCommonDao) {
    this.planCourseCommonDao = planCourseCommonDao;
  }

  public void setPlanCompareService(PlanCompareService planCompareService) {
    this.planCompareService = planCompareService;
  }

  public void setPlanCommonDao(PlanCommonDao planCommonDao) {
    this.planCommonDao = planCommonDao;
  }

}
