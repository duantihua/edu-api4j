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
package org.openurp.std.graduation.app.model;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.ems.rule.model.RuleConfig;
import org.openurp.base.std.model.StudentScope;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

/**
 * 毕业审核规则<br>
 */
@Entity(name = "org.openurp.std.graduation.app.model.GraduateAuditStandard")
public class GraduateAuditStandard extends LongIdObject {

  public static final String BUSINESS_NAME = "Graduate Audit Standard";

  private static final long serialVersionUID = -815097740106060901L;

  /** 名称 */
  @Size(max = 500)
  @NotNull
  private String name;

  /** 学生范围 */
  @Embedded
  @AssociationOverrides({
      @AssociationOverride(name = "levels", joinTable = @JoinTable(name = "GA_STANDARDS_EDUCATIONS", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "EDUCATION_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "stdTypes", joinTable = @JoinTable(name = "GA_STANDARDS_STD_TYPES", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "STD_TYPE_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "departments", joinTable = @JoinTable(name = "GA_STANDARDS_DEPARTMENTS", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "majors", joinTable = @JoinTable(name = "GA_STANDARDS_MAJORS", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "MAJOR_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "directions", joinTable = @JoinTable(name = "GA_STANDARDS_DIRECTIONS", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "DIRECTION_ID", referencedColumnName = "ID"))) })
  private StudentScope studentScope = new StudentScope();

  /** 生效日期 */
  @NotNull
  private Date beginOn;

  /** 失效日期 */
  private Date endOn;

  /** 审核项目 */
  @ManyToMany
  @JoinTable(name = "GA_STANDARDS_RULES", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "RULE_CONFIG_ID", referencedColumnName = "ID"))
  private Set<RuleConfig> rules = CollectUtils.newHashSet();

  /** 学位审核项目 */
  @ManyToMany
  @JoinTable(name = "GA_STANDARDS_DEGREE_RULES", joinColumns = @JoinColumn(name = "STANDARD_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "RULE_CONFIG_ID", referencedColumnName = "ID"))
  private Set<RuleConfig> degreeRules = CollectUtils.newHashSet();

  /** 备注 */
  @Size(max = 600)
  private String remark;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public StudentScope getStudentScope() {
    return studentScope;
  }

  public void setStudentScope(StudentScope studentScope) {
    this.studentScope = studentScope;
  }

  public Set<RuleConfig> getRules() {
    return rules;
  }

  public void setRules(Set<RuleConfig> rules) {
    this.rules = rules;
  }

  public Set<RuleConfig> getDegreeRules() {
    return degreeRules;
  }

  public void setDegreeRules(Set<RuleConfig> degreeRules) {
    this.degreeRules = degreeRules;
  }

  public Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(Date beginOn) {
    this.beginOn = beginOn;
  }

  public Date getEndOn() {
    return endOn;
  }

  public void setEndOn(Date endOn) {
    this.endOn = endOn;
  }

}
