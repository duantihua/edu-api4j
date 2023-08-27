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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.service.impl.GradeComparator;
import org.openurp.edu.grade.plan.model.CourseAuditResult;
import org.openurp.edu.grade.plan.model.GroupAuditResult;
import org.openurp.edu.grade.plan.service.PlanAuditContext;
import org.openurp.edu.grade.plan.service.PlanAuditListener;
import org.openurp.edu.grade.plan.service.StdGrade;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.plan.service.AlternativeCourseService;
import org.openurp.edu.program.model.AlternativeCourse;

/**
 * 替代课程不支持重复利用成绩
 */
public class PlanAuditAlternativeCourseListener implements PlanAuditListener {

  private AlternativeCourseService alternativeCourseService;

  private static final String alternatives_str = "alternatives";

  private String getGroupKey(CourseGroup courseGroup) {
    return courseGroup.getName() + "c";
  }

  public boolean startPlanAudit(PlanAuditContext context) {
    context.getParams().put(alternatives_str,
        alternativeCourseService.getAlternativeCourses(context.getResult().getStd()));
    return true;
  }

  public void endPlanAudit(PlanAuditContext context) {
  }

  public boolean startCourseAudit(PlanAuditContext context, GroupAuditResult groupResult,
      PlanCourse planCourse) {
    Set<?> substituted = (Set<?>) context.getParams().get(getGroupKey(planCourse.getGroup()));
    return !(substituted.contains(planCourse.getCourse()));
  }

  public boolean startGroupAudit(PlanAuditContext context, CourseGroup courseGroup,
      GroupAuditResult groupResult) {
    @SuppressWarnings("unchecked")
    Set<Course> substituted = (Set<Course>) context.getParams().get(getGroupKey(courseGroup));
    if (null == substituted) {
      substituted = CollectUtils.newHashSet();
      context.getParams().put(getGroupKey(courseGroup), substituted);
    }
    @SuppressWarnings("unchecked")
    List<AlternativeCourse> alternatives = (List<AlternativeCourse>) context.getParams()
        .get(alternatives_str);
    StdGrade stdGrade = context.getStdGrade();
    Map<Course, PlanCourse> courseMap = CollectUtils.newHashMap();
    for (PlanCourse planCourse : courseGroup.getPlanCourses()) {
      courseMap.put(planCourse.getCourse(), planCourse);
    }

    // 审核学期
    // 没有指定具体审核哪个学期，那么就审核全部学期
    String[] auditTerms = context.getAuditTerms();
    boolean needCheckTerm = (null != auditTerms && auditTerms.length >= 0);

    for (AlternativeCourse sc : alternatives) {
      if (courseMap.keySet().containsAll(sc.getOlds()) && isSubstitutes(stdGrade, sc)) {
        // 保留替代成绩
        // FIXME multi Grade need order by
        List<CourseGrade> substituteGrades = CollectUtils.newArrayList();
        for (Course c : sc.getNews()) {
          substituteGrades.addAll(stdGrade.getGrades(c));
          // 替代课程不支持重复利用成绩
          stdGrade.addNoGradeCourse(c);
        }
        // 增加原课程审核结果
        for (Course ori : sc.getOlds()) {
          PlanCourse planCourse = courseMap.get(ori);
          if (needCheckTerm) {
            boolean inTerm = false;
            for (int j = 0; j < auditTerms.length; j++) {
              inTerm = planCourse.getTerms().contains(Integer.valueOf(auditTerms[j]));
              if (inTerm) break;
            }
            if (!inTerm) continue;
          }

          courseMap.remove(ori);
          CourseAuditResult planCourseResult = new CourseAuditResult(planCourse);
          planCourseResult.checkPassed(stdGrade.getGrades(ori), substituteGrades);
          groupResult.addCourseResult(planCourseResult);
          groupResult.checkPassed(false);
          substituted.add(ori);
        }
      }
    }
    return true;
  }

  /**
   * 把替换课程List转换成Map Map（原始课程ID，替换课程）<br>
   * 课程必须在满足两边组都有成绩的情况下，才能按照平均绩点进行比较
   *
   * @param substituteCourseList
   *          ：替换课程LIst
   * @return
   */
  protected boolean isSubstitutes(StdGrade stdGrade, AlternativeCourse alternative) {
    Set<Course> allCourses = CollectUtils.newHashSet(alternative.getOlds());
    allCourses.addAll(alternative.getNews());

    Map<Course, CourseGrade> subGrades = CollectUtils.newHashMap();
    for (Course course : allCourses) {
      List<CourseGrade> grades = stdGrade.getGrades(course);
      if (CollectUtils.isNotEmpty(grades)) subGrades.put(course, grades.get(0));
    }
    if (GradeComparator.isSubstitute(alternative, subGrades)) {
      for (Course course : allCourses)
        stdGrade.useGrades(course);
      return true;
    }
    return false;

  }

  public AlternativeCourseService getAlternativeCourseService() {
    return alternativeCourseService;
  }

  public void setAlternativeCourseService(AlternativeCourseService alternativeCourseService) {
    this.alternativeCourseService = alternativeCourseService;
  }

}
