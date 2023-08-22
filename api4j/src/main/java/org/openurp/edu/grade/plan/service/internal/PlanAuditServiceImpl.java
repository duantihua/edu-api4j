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
package org.openurp.edu.grade.plan.service.internal;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.app.model.AuditSetting;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.service.CourseGradeProvider;
import org.openurp.edu.grade.plan.adapters.CourseGroupAdapter;
import org.openurp.edu.grade.plan.adapters.GroupResultAdapter;
import org.openurp.edu.grade.plan.model.AuditStat;
import org.openurp.edu.grade.plan.model.CourseAuditResult;
import org.openurp.edu.grade.plan.model.GroupAuditResult;
import org.openurp.edu.grade.plan.model.PlanAuditResult;
import org.openurp.edu.grade.plan.service.AuditSettingService;
import org.openurp.edu.grade.plan.service.PlanAuditContext;
import org.openurp.edu.grade.plan.service.PlanAuditListener;
import org.openurp.edu.grade.plan.service.PlanAuditService;
import org.openurp.edu.grade.plan.service.observers.ObserverUtils;
import org.openurp.edu.grade.plan.service.observers.PlanAuditObserverStack;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.CoursePlan;
import org.openurp.edu.program.model.PlanCourse;
import org.openurp.edu.program.plan.service.CoursePlanProvider;
import org.openurp.edu.program.utils.PlanUtils;
import org.openurp.service.OutputObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

public class PlanAuditServiceImpl extends BaseServiceImpl implements PlanAuditService {

  private Logger logger = LoggerFactory.getLogger(PlanAuditServiceImpl.class);

  protected CoursePlanProvider coursePlanProvider;

  protected AuditSettingService auditSettingService;

  protected CourseGradeProvider courseGradeProvider;

  protected GroupResultBuilder groupResultBuilder = new DefaultGroupResultBuilder();

  protected List<PlanAuditListener> listeners = new ArrayList<PlanAuditListener>();

  public PlanAuditResult audit(final Student student) {
    var plan = coursePlanProvider.getCoursePlan(student);
    var setting = auditSettingService.getSetting(student);
    if (setting.isTransient() && null != plan && null != plan.getProgram().getOffsetType()) {
      setting.setConvertTarget(plan.getProgram().getOffsetType());
    }
    PlanAuditContext context = new PlanAuditContext(student, plan, setting);
    return audit(student, context);
  }

  public PlanAuditResult audit(final Student student, PlanAuditContext context) {
    logger.debug("start audit {}", student.getCode());
    PlanAuditResult planAuditResult = new PlanAuditResult(student);
    planAuditResult.setPassed(false);
    planAuditResult.setRemark(null);
    planAuditResult.setUpdatedAt(new Date());
    planAuditResult.setAuditStat(new AuditStat());
    context.setResult(planAuditResult);

    // 获得学生对应的计划
    CoursePlan plan = context.getCoursePlan();
    if (null == plan) {
      return context.getResult();
    }

    context.setStdGrade(new StdGradeImpl(courseGradeProvider.getPublished(student)));

    // context里面已经设置好了result，不管是空的还是怎样
    for (PlanAuditListener listener : listeners) {
      if (!listener.startPlanAudit(context)) {
        return planAuditResult;
      }
    }

    // 构建了一个虚拟的顶层组，这个顶层组在CoursePlan的结构中是没有的，这个顶层组可以认为是CoursePlan
    CourseGroupAdapter courseGroupAdapter = new CourseGroupAdapter(context.getCoursePlan());
    GroupAuditResult groupResultAdapter = new GroupResultAdapter(planAuditResult);

    // 培养计划的学分要求和门数要求需要根据审核学期来发生变化
    float requiredCredits = context.getCoursePlan().getCredits();
    if (context.getAuditTerms() != null && context.getAuditTerms().length != 0) {
      requiredCredits = 0;
      for (int i = 0; i < context.getAuditTerms().length; i++) {
        for (CourseGroup group : context.getCoursePlan().getGroups()) {
          if (group.getParent() == null) {
            requiredCredits += PlanUtils.getGroupCredits(group, Integer.valueOf(context.getAuditTerms()[i]));
          }
        }
      }
    }
    planAuditResult.getAuditStat().setRequiredCredits(requiredCredits);

    // 课程组的门数要求应该根据审核学期的变化而变化，目前门数没有分学期的数据存储，所以凡是部分审核的，那么门数要求为0
    int requiredCount = 0;
    if (!context.isPartial()) {
      for (CourseGroup group : context.getCoursePlan().getGroups()) {
        if (group.getParent() == null) {
          requiredCount += group.getCourseCount();
        }
      }
    }
    planAuditResult.getAuditStat().setRequiredCount(requiredCount);

    auditGroup(context, courseGroupAdapter, groupResultAdapter);

    for (PlanAuditListener listener : listeners) {
      listener.endPlanAudit(context);
    }

    GroupAuditResult lastTarget = context.getResult().getGroupResult(context.getSetting().getConvertTarget());
    if (null != lastTarget) {
      if (lastTarget.getAuditStat().getPassedCredits() == 0
          && lastTarget.getAuditStat().getRequiredCredits() == 0 && lastTarget.getCourseResults().isEmpty()) {
        context.getResult().removeGroupResult(lastTarget);
      }
    }
    return planAuditResult;
  }

  /**
   * 获得课程组审核结果(递归)
   *
   * @param context
   * @param courseGroup
   * @param groupAuditResult
   */
  private void auditGroup(PlanAuditContext context, CourseGroup courseGroup,
                          GroupAuditResult groupAuditResult) {
    // 循环子组的结果,将结果放入该组
    List<CourseGroup> courseGroups = courseGroup.getChildren();
    PlanAuditResult planAuditResult = context.getResult();

    groupAudit:
    for (Iterator<CourseGroup> it = courseGroups.iterator(); it.hasNext(); ) {
      CourseGroup children = it.next();
      GroupAuditResult childResult = groupResultBuilder.buildResult(context, children);
      groupAuditResult.addChild(childResult);
      planAuditResult.addGroupResult(childResult);
      for (Iterator<PlanAuditListener> it1 = listeners.iterator(); it1.hasNext(); ) {
        PlanAuditListener listener = it1.next();
        if (!listener.startGroupAudit(context, children, childResult)) {
          planAuditResult.getAuditStat().reduceRequired(childResult.getAuditStat().getRequiredCredits(),
              childResult.getAuditStat().getRequiredCount());
          groupAuditResult.removeChild(childResult);
          planAuditResult.removeGroupResult(childResult);
          continue groupAudit;
        }
      }
      auditGroup(context, children, childResult);
    }
    // 该组的计划课程审核结果
    List<PlanCourse> myPlanCourses = courseGroup.getPlanCourses();

    courseAudit:
    for (Iterator<PlanCourse> iter = myPlanCourses.iterator(); iter.hasNext(); ) {
      PlanCourse planCourse = iter.next();
      for (PlanAuditListener listener : listeners) {
        if (!listener.startCourseAudit(context, groupAuditResult, planCourse)) {
          continue courseAudit;
        }
      }

      CourseAuditResult planCourseAuditResult = new CourseAuditResult(planCourse);
      List<CourseGrade> courseGrades = context.getStdGrade().useGrades(planCourse.getCourse());
      if (courseGrades.isEmpty()) {
        if (!planCourse.isCompulsory() && !courseGroup.isAutoAddup()) continue;
        courseGrades = Collections.emptyList();
      }
      planCourseAuditResult.checkPassed(courseGrades);
      groupAuditResult.addCourseResult(planCourseAuditResult);
    }
    groupAuditResult.checkPassed(false);
  }

  public PlanAuditResult getResult(Student std) {
    OqlBuilder<PlanAuditResult> query = OqlBuilder.from(PlanAuditResult.class, "planResult");
    query.where("planResult.std = :std", std);
    List<PlanAuditResult> results = entityDao.search(query);
    if (results.size() > 0) {
      return results.get(0);
    }
    return null;
  }

  /**
   * 批量审核培养计划完成情况
   */
  public void batchAudit(Collection<Student> stds, String[] auditTerms, PlanAuditObserverStack observerStack,
                         OutputObserver weboutput) {
    observerStack.notifyStart();

    int amount = stds.size();
    String msg = MessageFormat.format("{0} 位学生计划完成审核", amount);
    ObserverUtils.notifyStart(weboutput, msg, amount, null);

    int count = 0;
    for (Iterator<Student> iterator = stds.iterator(); iterator.hasNext(); count++) {
      Student student = iterator.next();

      // 获得学生对应的计划
      CoursePlan plan = coursePlanProvider.getCoursePlan(student);
      AuditSetting setting = auditSettingService.getSetting(student);
      if (setting.isTransient() && null != plan && null != plan.getProgram().getOffsetType()) {
        setting.setConvertTarget(plan.getProgram().getOffsetType());
      }
      PlanAuditContext context = new PlanAuditContext(student, plan, setting);
      context.getParams().put("_weboutput", weboutput);
      context.setAuditTerms(auditTerms);

      if (null == context.getCoursePlan()) {
        msg = MessageFormat.format("无法找到 {0} {1} 的培养计划，审核失败。", student.getName(),
            student.getCode());
        ObserverUtils.outputMessage(context, OutputObserver.error, msg, true);
      } else {
        String auditTermsStr = "";
        if (auditTerms != null && auditTerms.length > 0) {
          auditTermsStr = "(第" + Strings.join(auditTerms, ",") + "学期)";
        }
        msg = MessageFormat.format("开始审核 {0} {1} 的计划完成情况{2}", student.getName(), student.getCode(),
            auditTermsStr);
        ObserverUtils.outputMessage(context, OutputObserver.good, msg, true);
        if (observerStack.notifyBegin(context, count)) {
          audit(student, context);
          observerStack.notifyEnd(context, count);
        }
        msg = MessageFormat.format("审核完毕 {0} {1} 的计划完成情况", student.getName(), student.getCode());
        ObserverUtils.outputMessage(context, OutputObserver.good, msg, false);
      }

      if (iterator.hasNext()) {
        ObserverUtils.delimiter(weboutput);
      }
    }
    observerStack.finish();
  }

  public AuditSetting getSetting(Student student) {
    return auditSettingService.getSetting(student);
  }

  public void setCoursePlanProvider(CoursePlanProvider coursePlanProvider) {
    this.coursePlanProvider = coursePlanProvider;
  }

  public void setCourseGradeProvider(CourseGradeProvider courseGradeProvider) {
    this.courseGradeProvider = courseGradeProvider;
  }

  public List<PlanAuditListener> getListeners() {
    return listeners;
  }

  public void setListeners(List<PlanAuditListener> listeners) {
    this.listeners = listeners;
  }

  public void setGroupResultBuilder(GroupResultBuilder groupResultBuilder) {
    this.groupResultBuilder = groupResultBuilder;
  }

  public void setAuditSettingService(AuditSettingService auditSettingService) {
    this.auditSettingService = auditSettingService;
  }

}
