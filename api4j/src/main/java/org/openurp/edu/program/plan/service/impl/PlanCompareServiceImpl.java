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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.base.edu.code.CourseType;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.CoursePlan;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.plan.service.PlanCompareService;
import org.openurp.edu.program.plan.util.CourseTypeWrapper;
import org.openurp.edu.program.plan.util.PlanCourseWrapper;

public class PlanCompareServiceImpl extends BaseServiceImpl implements PlanCompareService {

  public Map<CourseType, List<? extends PlanCourse>[]> diff(CoursePlan leftExecutionPlan,
      CoursePlan rightExecutionPlan) {

    Map<CourseType, List<? extends PlanCourse>[]> result = new HashMap<CourseType, List<? extends PlanCourse>[]>();

    List<? extends CourseGroup> leftCourseGroups = leftExecutionPlan.getGroups();
    Collection<CourseTypeWrapper> leftCourseTypes = new HashSet<CourseTypeWrapper>();
    for (CourseGroup courseGroup : leftCourseGroups) {
      leftCourseTypes.add(new CourseTypeWrapper(courseGroup.getCourseType()));
    }

    List<? extends CourseGroup> rightCourseGroups = rightExecutionPlan.getGroups();
    Collection<CourseTypeWrapper> rightCourseTypes = new HashSet<CourseTypeWrapper>();
    for (CourseGroup courseGroup : rightCourseGroups) {
      rightCourseTypes.add(new CourseTypeWrapper(courseGroup.getCourseType()));
    }

    // 获得只存在于leftPlan的课程组
    Collection<CourseType> onlyInLeftCourseTypes = unWrapCourseTypes(
        CollectionUtils.subtract(leftCourseTypes, rightCourseTypes));
    if (onlyInLeftCourseTypes.size() > 0) {
      for (CourseType courseType : onlyInLeftCourseTypes) {
        result.put(courseType, null);
        List<? extends PlanCourse>[] planCourses = new ArrayList[2];
        planCourses[0] = new ArrayList(leftExecutionPlan.getGroup(courseType).getPlanCourses());
        Collections.sort(planCourses[0], PlanCourseWrapper.COMPARATOR);
        planCourses[1] = new ArrayList();
        result.put(courseType, planCourses);
      }
    }

    // 获得只存在于rightPlan的模块
    Collection<CourseType> onlyInRightCourseTypes = unWrapCourseTypes(
        CollectionUtils.subtract(rightCourseTypes, leftCourseTypes));
    if (onlyInRightCourseTypes.size() > 0) {
      for (CourseType courseType : onlyInRightCourseTypes) {
        result.put(courseType, null);
        List<ExecutionPlanCourse>[] planCourses = new ArrayList[2];
        planCourses[0] = new ArrayList();
        planCourses[1] = new ArrayList(rightExecutionPlan.getGroup(courseType).getPlanCourses());
        Collections.sort(planCourses[1], PlanCourseWrapper.COMPARATOR);
        result.put(courseType, planCourses);
      }
    }

    // 获得两者共有的模块
    Collection<CourseType> shareCourseTypes = unWrapCourseTypes(
        CollectionUtils.intersection(leftCourseTypes, rightCourseTypes));
    if (shareCourseTypes.size() > 0) {
      for (CourseType courseType : shareCourseTypes) {
        Collection<PlanCourseWrapper> wrappedLeftPlanCourses = wrapPlanCourses(
            leftExecutionPlan.getGroup(courseType).getPlanCourses());
        Collection<PlanCourseWrapper> wrappedRightPlanCourses = wrapPlanCourses(
            rightExecutionPlan.getGroup(courseType).getPlanCourses());

        Collection<PlanCourse> onlyInLeftPlanCourses = unWrapPlanCourses(
            CollectionUtils.subtract(wrappedLeftPlanCourses, wrappedRightPlanCourses));
        Collection<PlanCourse> onlyInRightPlanCourses = unWrapPlanCourses(
            CollectionUtils.subtract(wrappedRightPlanCourses, wrappedLeftPlanCourses));

        if (onlyInLeftPlanCourses.size() != 0 || onlyInRightPlanCourses.size() != 0) {
          result.put(courseType, null);
          List<ExecutionPlanCourse>[] planCourses = new ArrayList[2];
          planCourses[0] = new ArrayList(onlyInLeftPlanCourses);
          planCourses[1] = new ArrayList(onlyInRightPlanCourses);
          Collections.sort(planCourses[0], PlanCourseWrapper.COMPARATOR);
          Collections.sort(planCourses[1], PlanCourseWrapper.COMPARATOR);
          result.put(courseType, planCourses);
        }
      }
    }

    return result;
  }

  private Collection<PlanCourseWrapper> wrapPlanCourses(Collection<? extends PlanCourse> planCourses) {
    return CollectUtils.collect(planCourses, PlanCourseWrapper.WRAPPER);
  }

  private Collection<PlanCourse> unWrapPlanCourses(Collection<PlanCourseWrapper> planCourseWrappers) {
    return CollectUtils.collect(planCourseWrappers, PlanCourseWrapper.UNWRAPPER);
  }

  private Collection<CourseTypeWrapper> wrapCourseTypes(Collection<CourseType> courseTypes) {
    return CollectUtils.collect(courseTypes, CourseTypeWrapper.WRAPPER);
  }

  private Collection<CourseType> unWrapCourseTypes(Collection<CourseTypeWrapper> courseTypeWrappers) {
    return CollectUtils.collect(courseTypeWrappers, CourseTypeWrapper.UNWRAPPER);
  }

}
