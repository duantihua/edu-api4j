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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.code.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.plan.model.CourseAuditResult;
import org.openurp.edu.grade.plan.model.GroupAuditResult;
import org.openurp.edu.grade.plan.model.PlanAuditResult;
import org.openurp.edu.grade.plan.service.PlanAuditContext;
import org.openurp.edu.grade.plan.service.PlanAuditListener;
import org.openurp.edu.grade.plan.service.StdGrade;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.PlanCourse;

/**
 * 任意选修课监听<br>
 * 将其他模块多出的课程和学分，转换到任意选修课
 */
public class PlanAuditCommonElectiveListener implements PlanAuditListener {

  public void endPlanAudit(PlanAuditContext context) {
    PlanAuditResult result = context.getResult();
    StdGrade stdGrade = context.getStdGrade();
    CourseType electiveType = context.getSetting().getConvertTarget();
    GroupAuditResult groupResult = result.getGroupResult(electiveType);
    // 如果计划中没有任意选修课，那么就在审核结果中添加一个任意选修课
    if (null == groupResult) {
      GroupAuditResult groupRs = new GroupAuditResult();
      groupRs.setCourseType(electiveType);
      groupRs.setName(electiveType.getName());
      groupRs.setSubCount((short) 0);
      groupRs.setIndexno("99.99");
      groupResult = groupRs;
      result.addGroupResult(groupResult);
    }
    Collection<Course> restCourses = stdGrade.getRestCourses();
    for (Course course : restCourses) {
      CourseAuditResult courseResult = new CourseAuditResult();
      courseResult.setCourse(course);
      List<CourseGrade> grades = stdGrade.useGrades(course);
      if (!grades.isEmpty() && !grades.get(0).getCourseType().getId().equals(electiveType.getId())) {
        courseResult.setRemark("计划外");
      }
      courseResult.checkPassed(grades);
      groupResult.addCourseResult(courseResult);
    }
    // 设置该组的课程审核结果
    processConvertCredits(groupResult, result, context);
    groupResult.checkPassed(true);
  }

  /**
   * 处理转换学分<br>
   * 转换学分是学生完成培养计划各组后的剩余学分。<br>
   * 计算公式如下: converted = ∑(groupresult.passed?completed - required : 0)
   *
   * @param result
   */
  protected void processConvertCredits(GroupAuditResult target, PlanAuditResult result,
      PlanAuditContext context) {
    // 从公选当前节点查找祖先
    Set<GroupAuditResult> parents = CollectUtils.newHashSet();
    // 同一级别的兄弟节点
    Set<GroupAuditResult> sibling = CollectUtils.newHashSet();

    GroupAuditResult start = target.getParent();
    while (null != start && !parents.contains(start)) {
      parents.add(start);
      start = start.getParent();
    }
    GroupAuditResult parent = target.getParent();
    if (null != parent) {
      sibling.addAll(parent.getChildren());
      sibling.remove(target);
    }

    float otherConverted = 0f;
    float siblingConverted = 0f;
    for (GroupAuditResult gr : result.getGroupResults()) {
      if (!context.getSetting().isConvertable(gr.getCourseType())) continue;
      // 自己和父节点过滤掉
      if (gr.equals(target) || parents.contains(gr)) continue;

      if (sibling.contains(gr)) {
        siblingConverted += gr.isPassed()
            ? gr.getAuditStat().getPassedCredits() - gr.getAuditStat().getRequiredCredits()
            : 0f;
      } else if (null == gr.getParent()) {
        otherConverted += gr.isPassed()
            ? gr.getAuditStat().getPassedCredits() - gr.getAuditStat().getRequiredCredits()
            : 0f;
      }
    }

    // 将转换学分累计到公选课及其父组上
    target.getAuditStat().setConvertedCredits(otherConverted + siblingConverted);
    for (GroupAuditResult r : parents)
      r.getAuditStat().setConvertedCredits(otherConverted);
  }

  public boolean startPlanAudit(PlanAuditContext context) {
    return true;
  }

  public boolean startCourseAudit(PlanAuditContext context, GroupAuditResult groupResult,
      PlanCourse planCourse) {
    return true;
  }

  public boolean startGroupAudit(PlanAuditContext context, CourseGroup courseGroup,
      GroupAuditResult groupResult) {
    return true;
  }

}
