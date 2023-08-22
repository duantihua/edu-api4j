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
package org.openurp.edu.grade.plan.service;

import org.beangle.ems.rule.model.SimpleContext;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.app.model.AuditSetting;
import org.openurp.edu.grade.plan.model.PlanAuditResult;
import org.openurp.edu.program.model.CoursePlan;

public class PlanAuditContext extends SimpleContext {

  /**
   * 本次审核的学生
   */
  private final Student std;

  /**
   * 本次审核的标准
   */
  private final AuditSetting setting;

  /**
   * 学生计划
   */
  private final CoursePlan coursePlan;

  /**
   * 结果
   */
  private PlanAuditResult result;

  /**
   * 学生的所有成绩
   */
  private StdGrade stdGrade;

  /**
   * 是否部分审核
   */
  private boolean partial;

  /**
   * 审核学期，如果为null，那么就审核所有学期
   */
  private String[] auditTerms;

  @SuppressWarnings("unchecked")
  public <T> T getParam(String paramName, Class<T> clazz) {
    return (T) getParams().get(paramName);
  }

  public AuditSetting getSetting() {
    return setting;
  }

  public PlanAuditContext(Student std, CoursePlan coursePlan, AuditSetting setting) {
    this.coursePlan = coursePlan;
    this.std = std;
    this.setting = setting;
  }

  public StdGrade getStdGrade() {
    return stdGrade;
  }

  public void setStdGrade(StdGrade stdGrade) {
    this.stdGrade = stdGrade;
  }

  public PlanAuditResult getResult() {
    return result;
  }

  public void setResult(PlanAuditResult result) {
    this.result = result;
  }

  public CoursePlan getCoursePlan() {
    return coursePlan;
  }

  public boolean isPartial() {
    return partial;
  }

  public String[] getAuditTerms() {
    return auditTerms;
  }

  public void setAuditTerms(String[] auditTerms) {
    this.auditTerms = auditTerms;
    if (auditTerms == null || auditTerms.length == 0) {
      this.partial = false;
    } else {
      this.partial = true;
    }
  }

  public Student getStd() {
    return std;
  }

}
