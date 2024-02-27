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
package org.openurp.edu.grade.plan.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.plan.adapters.GroupResultAdapter;
import org.openurp.edu.program.model.CourseGroup;

/**
 * 课程组审核结果
 */
@Entity(name = "org.openurp.edu.grade.plan.model.GroupAuditResult")
public class GroupAuditResult extends LongIdObject {

  private static final long serialVersionUID = -6622283837918622674L;

  /** 组名 */
  @Size(max = 200)
  @NotNull
  private String name;

  @Size(max = 100)
  private String indexno;

  /** 学分审核结果 */
  private AuditStat auditStat = new AuditStat();

  /** 各课程审核结果 */
  @OneToMany(mappedBy = "groupResult", orphanRemoval = true, cascade = { CascadeType.ALL })
  private List<CourseAuditResult> courseResults = CollectUtils.newArrayList();

  /** 课程类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType courseType;

  /** 上级课程组 */
  @ManyToOne(fetch = FetchType.LAZY)
  private GroupAuditResult parent;

  /** 是否通过 */
  private boolean passed;

  /** 子组 */
  @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = { CascadeType.ALL })
  private List<GroupAuditResult> children = CollectUtils.newArrayList();

  /** 要求完成组数量 */
  private short subCount;

  private String remark;
  /** 计划审核结果 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private PlanAuditResult planResult;

  public void attachTo(PlanAuditResult planResult) {
    setPlanResult(planResult);
    planResult.getGroupResults().add(this);
    for (GroupAuditResult groupResult : children) {
      groupResult.attachTo(planResult);
    }
  }

  public void detach() {
    if (null != getPlanResult()) getPlanResult().getGroupResults().remove(this);
    setPlanResult(null);
    for (GroupAuditResult groupResult : children)
      groupResult.detach();
  }

  public String getName() {
    return (null != name) ? name : courseType.getName();
  }

  public void setName(String name) {
    this.name = name;
  }

  public GroupAuditResult() {
    super();
  }

  public GroupAuditResult(CourseGroup group) {
    this.name = group.getName();
    this.courseType = group.getCourseType();
    this.indexno = group.getIndexno();
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public AuditStat getAuditStat() {
    return auditStat;
  }

  public void setAuditStat(AuditStat auditStat) {
    this.auditStat = auditStat;
  }

  public List<CourseAuditResult> getCourseResults() {
    return courseResults;
  }

  public void setCourseResults(List<CourseAuditResult> planCourseAuditResults) {
    this.courseResults = planCourseAuditResults;
  }

  public List<GroupAuditResult> getChildren() {
    return children;
  }

  public void setChildren(List<GroupAuditResult> children) {
    this.children = children;
  }

  /**
   * 用于掩盖组的上级结果类型差异性<br>
   * 包裝了頂層組的計劃結果.
   *
   * @return
   */
  public GroupAuditResult getSuperResult() {
    return (null != planResult) ? new GroupResultAdapter(planResult) : null;
  }

  public GroupAuditResult getParent() {
    return parent;
  }

  public void setParent(GroupAuditResult parent) {
    this.parent = parent;
  }

  /**
   * 添加培养计划课程审核结果
   *
   * @param courseResult
   */
  public void addCourseResult(CourseAuditResult courseResult) {
    courseResult.setGroupResult(this);
    getCourseResults().add(courseResult);
    // 设置该组的通过门数及通过的学分
    if (courseResult.isPassed()) {
      addPassedCourse(this, courseResult.getCourse());
    }
  }

  public void updateCourseResult(CourseAuditResult rs) {
    if (rs.isPassed()) addPassedCourse(rs.getGroupResult(), rs.getCourse());
  }

  /**
   * 级联添加已经通过的课程，直到计划
   *
   * @param groupResult
   * @param auditStat
   * @param course
   */
  private void addPassedCourse(GroupAuditResult groupResult, Course course) {
    if (null == groupResult) { return; }
    var std = groupResult.planResult.getStd();
    AuditStat auditStat = groupResult.getAuditStat();
    if (!auditStat.getPassedCourses().contains(course)) {
      auditStat.getPassedCourses().add(course);
      auditStat.addCredits(course.getCredits(std.getLevel()));
      auditStat.addNum(1);
    }
    // 递归调用上级组，或者更新整个计划
    if (null != groupResult.getParent()) addPassedCourse(groupResult.getParent(), course);
    else {
      AuditStat planAuditStat = groupResult.getPlanResult().getAuditStat();
      if (!planAuditStat.getPassedCourses().contains(course)) {
        planAuditStat.getPassedCourses().add(course);
        planAuditStat.addCredits(course.getCredits(std.getLevel()));
        planAuditStat.addNum(1);
      }
    }
  }

  public void addChild(GroupAuditResult gr) {
    gr.setParent(this);
    this.children.add(gr);
  }

  public void removeChild(GroupAuditResult gr) {
    gr.setParent(null);
    this.children.remove(gr);
  }

  public static void checkPassed(GroupAuditResult groupResult, boolean isRecursive) {
    if (null == groupResult) { return; }
    boolean childrenPassed = true;
    int requiredSubCount = groupResult.getSubCount();
    if (requiredSubCount < 0) requiredSubCount = groupResult.getChildren().size();
    if (requiredSubCount > 0) {
      int groupPassedNum = 0;
      for (GroupAuditResult childResult : groupResult.getChildren()) {
        if (childResult.isPassed()) groupPassedNum += 1;
      }
      childrenPassed = (groupPassedNum >= requiredSubCount);
    }
    groupResult.setPassed(childrenPassed && groupResult.getAuditStat().isPassed());
    if (isRecursive) {
      if (null != groupResult.getParent()) checkPassed(groupResult.getParent(), true);
      else checkPassed(new GroupResultAdapter(groupResult.getPlanResult()), false);
    }
  }

  public void checkPassed(boolean isRecursive) {
    checkPassed(this, isRecursive);
  }

  public boolean isPassed() {
    return passed;
  }

  public void setPassed(boolean passed) {
    this.passed = passed;
  }

  public PlanAuditResult getPlanResult() {
    return planResult;
  }

  public void setPlanResult(PlanAuditResult planResult) {
    this.planResult = planResult;
  }

  public short getSubCount() {
    return subCount;
  }

  public void setSubCount(short subCount) {
    this.subCount = subCount;
  }

  public String getIndexno() {
    return indexno;
  }

  public void setIndexno(String indexno) {
    this.indexno = indexno;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
