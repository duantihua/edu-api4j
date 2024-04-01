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
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.tuple.Pair;
import org.openurp.base.edu.model.Course;
import org.openurp.code.edu.model.CourseType;
import org.openurp.edu.clazz.model.CourseTaker;
import org.openurp.edu.grade.Grade;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.plan.model.AuditCourseResult;
import org.openurp.edu.grade.plan.model.AuditGroupResult;
import org.openurp.edu.grade.plan.model.AuditPlanResult;
import org.openurp.edu.grade.plan.service.AuditPlanContext;
import org.openurp.edu.grade.plan.service.AuditPlanListener;
import org.openurp.edu.program.model.CourseGroup;
import org.openurp.edu.program.model.PlanCourse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 在读课程审核监听器
 */
public class PlanAuditCourseTakerListener implements AuditPlanListener {

  private EntityDao entityDao;
  private static final String TakeCourse2Types = "takeCourse2Types";
  private static final String Group2CoursesKey = "group2CoursesKey";
  /**
   * 默认是否通过
   */
  private boolean defaultPassed = false;

  @SuppressWarnings({"unchecked"})
  public boolean startPlanAudit(AuditPlanContext context) {
    @SuppressWarnings("rawtypes")
    OqlBuilder builder = OqlBuilder.from(CourseTaker.class, "ct").where("ct.std=:std", context.getStd());
    builder.where("not exists(from " + CourseGrade.class.getName()
        + " cg where cg.semester=ct.clazz.semester and cg.course=ct.clazz.course "
        + "and cg.std=ct.std and cg.status=:status)", Grade.Status.Published);
    builder.where("ct.clazz.semester.endOn >= :now", new Date());
    builder.select("ct.clazz.course,ct.clazz.courseType");
    Map<Course, CourseType> course2Types = CollectUtils.newHashMap();
    for (Object c : entityDao.search(builder)) {
      course2Types.put((Course) ((Object[]) c)[0], (CourseType) ((Object[]) c)[1]);
    }
    context.getParams().put(TakeCourse2Types, course2Types);
    context.getParams().put(Group2CoursesKey, new ArrayList<Pair<AuditGroupResult, Course>>());
    return true;
  }

  public boolean startGroupAudit(AuditPlanContext context, CourseGroup courseGroup,
                                 AuditGroupResult groupResult) {
    return true;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public boolean startCourseAudit(AuditPlanContext context, AuditGroupResult groupResult,
                                  PlanCourse planCourse) {
    if (((Map<Course, CourseType>) context.getParams().get(TakeCourse2Types))
        .containsKey(planCourse.getCourse())) {
      ((ArrayList<Pair<AuditGroupResult, Course>>) (context.getParams().get(Group2CoursesKey)))
          .add(new Pair(groupResult, planCourse.getCourse()));
    }
    return true;
  }

  @SuppressWarnings({"unchecked"})
  public void endPlanAudit(AuditPlanContext context) {
    Map<Course, CourseType> course2Types = (Map<Course, CourseType>) context.getParams()
        .remove(TakeCourse2Types);
    ArrayList<Pair<AuditGroupResult, Course>> results = (ArrayList<Pair<AuditGroupResult, Course>>) context
        .getParams().remove(Group2CoursesKey);

    Set<AuditGroupResult> used = CollectUtils.newHashSet();
    // 先按照代码进行匹配
    for (Pair<AuditGroupResult, Course> tuple : results) {
      add2Group(tuple.getRight(), tuple.getLeft());
      course2Types.remove(tuple.getRight());
      used.add(tuple.getLeft());
    }

    AuditGroupResult lastTarget = getTargetGroupResult(context);
    /* 考虑剩余课程，匹配到相应类别(一定没有课程),匹配失败到目标类别(一般是通识选修或者任意选修) */
    for (Map.Entry<Course, CourseType> entry : course2Types.entrySet()) {
      CourseGroup g = context.getCoursePlan().getGroup(entry.getValue());
      AuditGroupResult gr = null;
      if (null == g || (g.getCourseType().isOptional() && !g.isAutoAddup())) {
        gr = context.getResult().getGroupResult(entry.getValue());
      }
      if (null == gr) gr = lastTarget;
      if (null != gr) {
        add2Group(entry.getKey(), gr);
        used.add(gr);
      }
    }
    for (AuditGroupResult aur : used) {
      aur.checkPassed(true);
    }

  }

  /**
   * 将课程添加/更新到组内
   *
   * @param groupResult
   * @param course
   */
  private void add2Group(Course course, AuditGroupResult groupResult) {
    AuditCourseResult existedResult = null;
    for (AuditCourseResult cr : groupResult.getCourseResults()) {
      if (cr.getCourse().equals(course)) {
        existedResult = cr;
        break;
      }
    }

    // groupResult.getPlanResult().setPartial(true);
    if (existedResult == null) {
      existedResult = new AuditCourseResult();
      existedResult.setCourse(course);
      existedResult.setPassed(defaultPassed);
      groupResult.addCourseResult(existedResult);
    } else {
      
      //if (defaultPassed) existedResult.setPassed(defaultPassed);
      //existedResult.getGroupResult().updateCourseResult(existedResult);
    }

    existedResult.addRemark("在读");
    if (null == existedResult.getScores()) {
      existedResult.setScores("--");
    }
  }

  /**
   * 获取转换目标课程组审核结果
   *
   * @param context
   * @return
   */
  private AuditGroupResult getTargetGroupResult(AuditPlanContext context) {
    CourseType electiveType = context.getSetting().getConvertTarget();
    AuditPlanResult result = context.getResult();
    AuditGroupResult groupResult = result.getGroupResult(electiveType);
    // 如果计划中没有任意选修课，那么就在审核结果中添加一个任意选修课
    if (null == groupResult) {
      AuditGroupResult groupRs = new AuditGroupResult();
      groupRs.setIndexno("99.99");
      groupRs.setCourseType(electiveType);
      groupRs.setName(electiveType.getName());
      groupRs.setSubCount((short) 0);
      groupResult = groupRs;
      result.addGroupResult(groupResult);
    }
    return groupResult;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public boolean isDefaultPassed() {
    return defaultPassed;
  }

  public void setDefaultPassed(boolean defaultPassed) {
    this.defaultPassed = defaultPassed;
  }

}
