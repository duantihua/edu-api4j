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
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.plan.model.AuditCourseResult;
import org.openurp.edu.grade.plan.model.AuditGroupResult;
import org.openurp.edu.grade.plan.model.AuditPlanResult;
import org.openurp.edu.grade.plan.service.AuditPlanContext;
import org.openurp.edu.grade.plan.service.AuditPlanListener;
import org.openurp.edu.grade.plan.service.StdGrade;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.PlanCourse;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 任意选修课监听<br>
 * 将其他模块多出的课程和学分，转换到任意选修课
 */
public class PlanAuditCommonElectiveListener implements AuditPlanListener {

  public void endPlanAudit(AuditPlanContext context) {
    AuditPlanResult result = context.getResult();
    StdGrade stdGrade = context.getStdGrade();
    CourseType electiveType = context.getSetting().getConvertTarget();
    AuditGroupResult groupResult = result.getGroupResult(electiveType);
    // 如果计划中没有任意选修课，那么就在审核结果中添加一个任意选修课
    if (null == groupResult) {
      AuditGroupResult groupRs = new AuditGroupResult();
      groupRs.setCourseType(electiveType);
      groupRs.setName(electiveType.getName());
      groupRs.setSubCount((short) 0);
      groupRs.setIndexno("99.99");
      groupResult = groupRs;
      result.addGroupResult(groupResult);
    }
    Collection<Course> restCourses = stdGrade.getRestCourses();
    for (Course course : restCourses) {
      AuditCourseResult courseResult = groupResult.getCourseResult(course);
      if(null==courseResult){
        courseResult = new AuditCourseResult();
        groupResult.addCourseResult(courseResult);
      }
      courseResult.setCourse(course);
      List<CourseGrade> grades = stdGrade.useGrades(course);
      if (!grades.isEmpty() && !grades.get(0).getCourseType().getId().equals(electiveType.getId())) {
        courseResult.addRemark("原" + grades.get(0).getCourseType().getName());
      }
      courseResult.checkPassed(grades);
      groupResult.updateCourseResult(courseResult);
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
  protected void processConvertCredits(AuditGroupResult target, AuditPlanResult result,
                                       AuditPlanContext context) {
    // 从公选当前节点查找祖先
    Set<AuditGroupResult> parents = CollectUtils.newHashSet();
    // 同一级别的兄弟节点
    Set<AuditGroupResult> sibling = CollectUtils.newHashSet();

    AuditGroupResult start = target.getParent();
    while (null != start && !parents.contains(start)) {
      parents.add(start);
      start = start.getParent();
    }
    AuditGroupResult parent = target.getParent();
    if (null != parent) {
      sibling.addAll(parent.getChildren());
      sibling.remove(target);
    }

    float otherConverted = 0f;
    float siblingConverted = 0f;
    for (AuditGroupResult gr : result.getGroupResults()) {
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
    for (AuditGroupResult r : parents)
      r.getAuditStat().setConvertedCredits(otherConverted);
  }

  public boolean startPlanAudit(AuditPlanContext context) {
    return true;
  }

  public boolean startCourseAudit(AuditPlanContext context, AuditGroupResult groupResult,
                                  PlanCourse planCourse) {
    return true;
  }

  public boolean startGroupAudit(AuditPlanContext context, CourseGroup courseGroup,
                                 AuditGroupResult groupResult) {
    return true;
  }

}
