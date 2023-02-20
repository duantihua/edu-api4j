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
package org.openurp.edu.grade.plan.service.observers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.plan.adapters.GroupResultAdapter;
import org.openurp.edu.grade.plan.model.CourseAuditResult;
import org.openurp.edu.grade.plan.model.GroupAuditResult;
import org.openurp.edu.grade.plan.model.PlanAuditResult;
import org.openurp.edu.grade.plan.service.PlanAuditContext;

/**
 * 计划审核保存
 */
public class PlanAuditPersistObserver extends BaseServiceImpl implements PlanAuditObserver {

  public void notifyStart() {
  }

  public void finish() {
  }

  public boolean notifyBegin(PlanAuditContext context, int index) {
    return true;
  }

  private PlanAuditResult getResult(Student std) {
    OqlBuilder<PlanAuditResult> query = OqlBuilder.from(PlanAuditResult.class, "planResult");
    query.where("planResult.std = :std", std);
    List<PlanAuditResult> results = entityDao.search(query);
    if (results.size() > 0) { return results.get(0); }
    return null;
  }

  public void notifyEnd(PlanAuditContext context, int index) {
    PlanAuditResult newResult = context.getResult();
    PlanAuditResult existedResult = getResult(context.getStd());
    if (null != existedResult) {
      existedResult.setRemark(newResult.getRemark());

      existedResult.setUpdatedAt(new Date());
      existedResult.setAuditStat(newResult.getAuditStat());
      StringBuilder updates = new StringBuilder();
      mergeGroupResult(existedResult, new GroupResultAdapter(existedResult),
          new GroupResultAdapter(newResult), updates);

      existedResult.setPassed(newResult.isPassed());

      // delete last ';'
      if (updates.length() > 0) updates.deleteCharAt(updates.length() - 1);
      existedResult.setUpdates(updates.toString());
    } else {
      existedResult = newResult;
    }
    entityDao.saveOrUpdate(existedResult);
    context.setResult(existedResult);
  }

  private void mergeGroupResult(PlanAuditResult existedResult, GroupAuditResult target,
      GroupAuditResult source, StringBuilder updates) {
    // 统计完成学分的变化
    float delta = source.getAuditStat().getPassedCredits() - target.getAuditStat().getPassedCredits();
    if (Float.compare(delta, 0) != 0) {
      updates.append(source.getName());
      if (delta > 0) updates.append('+').append(delta);
      else updates.append(delta);
      updates.append(';');
    }
    target.setAuditStat(source.getAuditStat());
    target.setPassed(source.isPassed());
    target.setIndexno(source.getIndexno());
    // 收集课程组[groupName->groupResult]
    Map<String, GroupAuditResult> targetGroupResults = CollectUtils.newHashMap();
    Map<String, GroupAuditResult> sourceGroupResults = CollectUtils.newHashMap();
    for (GroupAuditResult result : target.getChildren())
      targetGroupResults.put(result.getName(), result);
    for (GroupAuditResult result : source.getChildren())
      sourceGroupResults.put(result.getName(), result);

    // 收集课程结果[course->courseResult]
    Map<Course, CourseAuditResult> targetCourseResults = CollectUtils.newHashMap();
    Map<Course, CourseAuditResult> sourceCourseResults = CollectUtils.newHashMap();
    for (CourseAuditResult courseResult : target.getCourseResults())
      targetCourseResults.put(courseResult.getCourse(), courseResult);
    for (CourseAuditResult courseResult : source.getCourseResults())
      sourceCourseResults.put(courseResult.getCourse(), courseResult);

    // 删除没有的课程组
    Set<String> removed = CollectUtils.subtract(targetGroupResults.keySet(), sourceGroupResults.keySet());
    for (String groupName : removed) {
      GroupAuditResult gg = targetGroupResults.get(groupName);
      gg.detach();
      target.removeChild(gg);
    }

    // 添加课程组
    Set<String> added = CollectUtils.subtract(sourceGroupResults.keySet(), targetGroupResults.keySet());
    for (String groupName : added) {
      GroupAuditResult groupResult = (GroupAuditResult) sourceGroupResults.get(groupName);
      target.addChild(groupResult);
      groupResult.attachTo(existedResult);
    }

    // 合并课程组
    Set<String> common = CollectUtils.intersection(sourceGroupResults.keySet(), targetGroupResults.keySet());
    for (String groupName : common) {
      mergeGroupResult(existedResult, targetGroupResults.get(groupName), sourceGroupResults.get(groupName),
          updates);
    }
    // ------- 合并课程结果
    // 删除没有的课程
    Set<Course> removedCourses = CollectUtils.subtract(targetCourseResults.keySet(),
        sourceCourseResults.keySet());
    for (Course course : removedCourses) {
      CourseAuditResult courseResult = (CourseAuditResult) targetCourseResults.get(course);
      target.getCourseResults().remove(courseResult);
    }
    // 添加新的课程结果
    Set<Course> addedCourses = CollectUtils.subtract(sourceCourseResults.keySet(),
        targetCourseResults.keySet());
    for (Course course : addedCourses) {
      CourseAuditResult courseResult = (CourseAuditResult) sourceCourseResults.get(course);
      courseResult.getGroupResult().getCourseResults().remove(courseResult);
      courseResult.setGroupResult(target);
      target.getCourseResults().add(courseResult);
    }
    // 合并共同的课程
    Set<Course> commonCourses = CollectUtils.intersection(sourceCourseResults.keySet(),
        targetCourseResults.keySet());
    for (Course course : commonCourses) {
      CourseAuditResult targetCourseResult = targetCourseResults.get(course);
      CourseAuditResult sourceCourseResult = sourceCourseResults.get(course);
      targetCourseResult.setPassed(sourceCourseResult.isPassed());
      targetCourseResult.setScores(sourceCourseResult.getScores());
      targetCourseResult.setCompulsory(sourceCourseResult.isCompulsory());
      targetCourseResult.setRemark(sourceCourseResult.getRemark());
    }
  }
}
