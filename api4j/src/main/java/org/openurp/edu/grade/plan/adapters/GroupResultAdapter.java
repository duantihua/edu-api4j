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
package org.openurp.edu.grade.plan.adapters;

import java.util.Collections;
import java.util.List;

import org.openurp.code.edu.model.CourseType;
import org.openurp.edu.grade.plan.model.AuditStat;
import org.openurp.edu.grade.plan.model.CourseAuditResult;
import org.openurp.edu.grade.plan.model.GroupAuditResult;
import org.openurp.edu.grade.plan.model.PlanAuditResult;

public class GroupResultAdapter extends GroupAuditResult {

  private static final long serialVersionUID = 1929862666682635612L;

  private PlanAuditResult result;

  private List<GroupAuditResult> topGroups;

  /**
   * 用于伪装组结果
   *
   * @param result
   */
  public GroupResultAdapter(PlanAuditResult result) {
    super();
    this.result = result;
    this.topGroups = result.getTopGroupResults();
  }

  public void removeChild(GroupAuditResult gr) {
  }

  public void addChild(GroupAuditResult gr) {
  }

  public AuditStat getAuditStat() {
    return result.getAuditStat();
  }

  public void attachTo(PlanAuditResult planResult) {
  }

  public void detach() {
  }

  // 课程仅仅在组中,计划中没有
  public void addCourseResult(CourseAuditResult courseResult) {
  }

  // 课程仅仅在组中,计划中没有
  public void updateCourseResult(CourseAuditResult rs) {
  }

  public List<GroupAuditResult> getChildren() {
    return this.topGroups;
  }

  public void checkPassed() {
    GroupAuditResult.checkPassed(this, false);
  }

  public void checkPassed(boolean isRecursive) {
    GroupAuditResult.checkPassed(this, isRecursive);
  }

  public String getName() {
    return "计划";
  }

  public List<CourseAuditResult> getCourseResults() {
    return Collections.emptyList();
  }

  public CourseType getCourseType() {
    return null;
  }

  public GroupAuditResult getParent() {
    return null;
  }

  public PlanAuditResult getPlanResult() {
    return result;
  }

  public boolean isPassed() {
    return result.isPassed();
  }

  public void setPassed(boolean passed) {
    result.setPassed(passed);
  }

  public void setAuditStat(AuditStat auditStat) {
    result.setAuditStat(auditStat);
  }

  @Override
  public short getSubCount() {
    return (short) topGroups.size();
  }

  @Override
  public void setSubCount(short num) {
  }

  public void setChildren(List<GroupAuditResult> children) {
  }

  public void setCourseResults(List<CourseAuditResult> planCourseAuditResults) {
  }

  public void setCourseType(CourseType courseType) {
  }

  public void setParent(GroupAuditResult parent) {
  }

  public void setPlanResult(PlanAuditResult planResult) {
  }

  public void setName(String name) {
    // TODO Auto-generated method stub
  }
}
