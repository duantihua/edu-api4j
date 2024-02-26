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
package org.openurp.edu.grade.plan.service.listeners;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.service.SemesterService;
import org.openurp.base.util.TermCalculator;
import org.openurp.edu.grade.app.model.AuditSetting;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.plan.model.CourseAuditResult;
import org.openurp.edu.grade.plan.model.GroupAuditResult;
import org.openurp.edu.grade.plan.model.PlanAuditResult;
import org.openurp.edu.grade.plan.service.PlanAuditContext;
import org.openurp.edu.grade.plan.service.PlanAuditListener;
import org.openurp.edu.grade.plan.service.StdGrade;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.PlanCourse;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 按照课程类别匹配的审核监听器<br>
 * 精确按照课程代码匹配的审核逻辑场景中，不要添加该监听器.
 *
 * @since 2012.03.19
 */
public class PlanAuditCourseTypeMatchListener implements PlanAuditListener {

  protected void addGroupResult(Map<CourseType, GroupAuditResult> results, GroupAuditResult gr) {
    results.put(gr.getCourseType(), gr);
    for (GroupAuditResult child : gr.getChildren()) {
      addGroupResult(results, child);
    }
  }

  private SemesterService semesterService;

  public void endPlanAudit(PlanAuditContext context) {
    String[] auditTerms = context.getAuditTerms();

    Map<CourseType, GroupAuditResult> results = CollectUtils.newHashMap();
    StdGrade stdGrade = context.getStdGrade();
    Collection<Course> restCourses = stdGrade.getRestCourses();
    if (!restCourses.isEmpty()) {
      PlanAuditResult result = context.getResult();
      for (GroupAuditResult gr : result.getGroupResults()) {
        addGroupResult(results, gr);
      }
    }
    for (Course course : restCourses) {
      List<CourseGrade> grades = stdGrade.getGrades(course);
      CourseType courseType = null;
      // 没有成绩
      if (grades.isEmpty()) continue;
      else courseType = grades.get(0).getCourseType();

      GroupAuditResult groupResult = results.get(courseType);
      if (null == groupResult) continue;
      // 计划里的必修组，不能按照类别匹配
      CourseGroup g = context.getCoursePlan().getGroup(groupResult.getCourseType());
      if (null != g && !g.getCourseType().isOptional()) continue;
      stdGrade.useGrades(course);

      StringBuilder remark = new StringBuilder();
      // 登记交流成绩，外校课程名称
      // if (course.isExchange()) {
      // for (CourseGrade grade : grades) {
      // for (ExternCourse ec : grade.getExchanges())
      // remark.append(ec.getName()).append(' ');
      // }
      // }
      /*
       * 如果是部分审核，那么就要看这个课程获得的成绩是否在审核的学期内
       * 如果在就添加这个课程的审核结果，如果不在的话就不添加这个课程的审核结果
       */
      if (null != auditTerms && auditTerms.length > 0 && context.getCoursePlan() != null) {
        boolean inAuditTerms = false;
        for (CourseGrade grade : grades) {
          if (inAuditTerms) break;

          int term = -1;
          if (context.getCoursePlan().getEndOn() != null) {
            term = new TermCalculator(semesterService, grade.getSemester())
                .getTerm(context.getCoursePlan().getEndOn(), context.getCoursePlan().getEndOn(), true);
          } else {
            term = new TermCalculator(semesterService, grade.getSemester())
                .getTerm(context.getCoursePlan().getBeginOn(), java.sql.Date.valueOf("2099-09-09"), true);
          }

          for (int j = 0; j < auditTerms.length; j++) {
            // 开课学期在审核学期里
            if (String.valueOf(term).equals(auditTerms[j])) {
              inAuditTerms = true;
              break;
            }
          }
        }
        if (!inAuditTerms) continue;
      }

      /*
       * 判断是否为计划外课程，如果课程组不为空，那么剩余的课程都是计划外课程
       * 如果课程组为空，那么剩余的课程不算是计划外课程
       */
      CourseGroup courseGroup = context.getCoursePlan().getGroup(courseType);
      boolean outOfPlan = false;
      if (null == courseGroup || !CollectUtils.isEmpty(courseGroup.getPlanCourses())) {
        outOfPlan = true;
      }

      CourseAuditResult existResult = null;
      boolean existed = false;
      for (CourseAuditResult cr : groupResult.getCourseResults()) {
        if (cr.getCourse().equals(course)) {
          existResult = cr;
          existed = true;
          break;
        }
      }
      if (existResult == null) existResult = new CourseAuditResult();

      existResult.setCourse(course);
      existResult.checkPassed(grades);
      groupResult.updateCourseResult(existResult);

      if (null != existResult.getRemark()) remark.insert(0, existResult.getRemark());
      if (outOfPlan) remark.append(" 计划外");
      existResult.setRemark(remark.toString());
      if (!existed) groupResult.addCourseResult(existResult);
      groupResult.checkPassed(true);

    }
  }

  public boolean startCourseAudit(PlanAuditContext context, GroupAuditResult groupResult,
                                  PlanCourse planCourse) {
    return true;
  }

  public boolean startGroupAudit(PlanAuditContext context, CourseGroup courseGroup,
                                 GroupAuditResult groupResult) {
    AuditSetting standard = context.getSetting();
    if (null != standard) {
      return !standard.isDisaudit(courseGroup.getCourseType());
    }
    return true;
  }

  public boolean startPlanAudit(PlanAuditContext context) {
    return true;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

}
