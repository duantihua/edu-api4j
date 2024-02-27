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

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.model.Department;
import org.openurp.base.std.model.Student;
import org.openurp.edu.program.model.*;
import org.openurp.edu.program.plan.service.CoursePlanProvider;

import java.util.*;

public class CoursePlanProviderImpl extends BaseServiceImpl implements CoursePlanProvider {

  public ExecutionPlan getExecutionPlan(Student student) {
    OqlBuilder<ExecutionPlan> query = OqlBuilder.from(ExecutionPlan.class, "plan");
    if (student.getState().getMajor() == null) {
      return null;
    } else {
      query.where("plan.program.grade=:grade", student.getGrade());
      query.where("plan.program.level=:level", student.getLevel());
      query.where("plan.program.major=:major", student.getState().getMajor());
      var departs = new ArrayList<Department>();
      departs.add(student.getDepartment());
      if (null != student.getDepartment().getParent()) {
        departs.add(student.getDepartment().getParent());
      }
      query.where("plan.program.department in(:departments)", departs);
      if (student.getState().getDirection() == null) {
        query.where("plan.program.direction is null");
      } else {
        query.where("plan.program.direction is null or plan.program.direction=:direction", student.getState().getDirection());
      }
      List<ExecutionPlan> plans = entityDao.search(query);
      if (plans.isEmpty()) {
        return null;
      } else {
        List<ExecutionPlan> suitables = new ArrayList<>();
        for (ExecutionPlan plan : plans) {
          var program = plan.getProgram();
          if (program.getStdTypes().isEmpty() || program.getStdTypes().contains(student.getStdType())) {
            suitables.add(plan);
          }
        }
        return suitables.isEmpty() ? null : suitables.get(0);
      }
    }
  }

  public StdPlan getPersonalPlan(Student std) {
    OqlBuilder<StdPlan> query = OqlBuilder.from(StdPlan.class, "plan");
    query.where("plan.std = :std", std);
    return entityDao.uniqueResult(query);
  }

  public Map<Student, CoursePlan> getCoursePlans(Collection<Student> students) {
    Map<Student, CoursePlan> result = CollectUtils.newHashMap();
    for (Student student : students) {
      CoursePlan plan = getCoursePlan(student);
      if (null != plan) result.put(student, plan);
    }
    return result;
  }

  public CoursePlan getCoursePlan(Student student) {
    // 个人计划
    CoursePlan plan = getPersonalPlan(student);
    // 专业计划

    if (null == plan) {
      plan = getExecutionPlan(student);
    }
    return plan;
  }

  public List<PlanCourse> getPlanCourses(Student student) {
    CoursePlan plan = getCoursePlan(student);
    if (null == plan) {
      return CollectUtils.newArrayList();
    }

    Set<PlanCourse> planCourses = CollectUtils.newHashSet();
    addPlanCourse(plan.getGroups(), planCourses);
    return CollectUtils.newArrayList(planCourses);
  }

  /**
   * @param courseGroups
   * @param planCourses
   */
  private void addPlanCourse(List<CourseGroup> courseGroups, Set<PlanCourse> planCourses) {
    if (CollectionUtils.isEmpty(courseGroups)) {
      return;
    }
    for (CourseGroup courseGroup : courseGroups) {
      planCourses.addAll(courseGroup.getPlanCourses());
      addPlanCourse(courseGroup.getChildren(), planCourses);
    }
  }

  public List<Semester> getSemesterByPlanCourse(PlanCourse planCourse) {
    Date beginOn = planCourse.getGroup().getPlan().getBeginOn();
    Date endOn = planCourse.getGroup().getPlan().getEndOn();

    OqlBuilder<Semester> builder = OqlBuilder.from(Semester.class, "semester");
    builder.where("semester.beginOn <= :endOn", endOn);
    builder.where("semester.endOn >= :beginOn", beginOn);
    builder.orderBy(Order.parse("semester.beginOn"));
    List<Semester> semestersByInterval = entityDao.search(builder);

    List<Integer> terms = planCourse.getTerms().getTermList();
    List<Semester> semestersByResult = CollectUtils.newArrayList();
    for (Integer term : terms) {
      semestersByResult.add(semestersByInterval.get(term - 1));
    }
    return semestersByResult;
  }
}
