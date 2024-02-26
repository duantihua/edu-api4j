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
package org.openurp.edu.grade.app.model;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.std.model.StudentScope;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "org.openurp.edu.grade.app.model.AuditSetting")
public class AuditSetting extends LongIdObject {

  private static final long serialVersionUID = 6149971489639130484L;

  public AuditSetting() {
    super();
  }

  public AuditSetting(Long id) {
    super(id);
  }

  public static AuditSetting empty(CourseType ctype) {
    CourseType electiveType = ctype;
    if (null == electiveType) {
      electiveType = new CourseType(-1);
      electiveType.setName("计划外");
    }
    return new AuditSetting(CollectUtils.newHashSet(), CollectUtils.newHashSet(), electiveType);
  }

  public AuditSetting(Set<CourseType> disauditCourseTypes, Set<CourseType> convertableCourseTypes,
                      CourseType convertTargetCourseType) {
    super();
    this.excludes = disauditCourseTypes;
    this.convertables = convertableCourseTypes;
    if (null == convertTargetCourseType) {
      CourseType electiveType = new CourseType(-1);
      electiveType.setName("计划外");
      this.convertTarget = electiveType;
    } else {
      this.convertTarget = convertTargetCourseType;
    }
  }

  /**
   * 不审核的课程类别
   */
  @ManyToMany
  private Set<CourseType> excludes = new HashSet<CourseType>();

  /**
   * 冲抵课程类别
   * (可学分转移的课程类别)
   */
  @ManyToMany
  @JoinTable(name = "", joinColumns = @JoinColumn(name = "audit_setting_id"), inverseJoinColumns = @JoinColumn(name = ""))
  private Set<CourseType> convertables = new HashSet<CourseType>();

  /**
   * 学分转换的目标课程类别
   * 指定哪个课程类别是：(任意选修课类别)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType convertTarget;

  /**
   * 名称
   */
  @Size(max = 500)
  @NotNull
  private String name;

  /**
   * 学生范围
   */
  @Embedded
  @AssociationOverrides({
      @AssociationOverride(name = "levels", joinTable = @JoinTable(name = "audit_settings_levels", joinColumns = @JoinColumn(name = "audit_setting_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "level_id", referencedColumnName = "ID"))),
      @AssociationOverride(name = "stdTypes", joinTable = @JoinTable(name = "audit_settings_std_types", joinColumns = @JoinColumn(name = "audit_setting_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "STD_TYPE_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "departments", joinTable = @JoinTable(name = "audit_settings_departments", joinColumns = @JoinColumn(name = "audit_setting_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "majors", joinTable = @JoinTable(name = "audit_settings_majors", joinColumns = @JoinColumn(name = "audit_setting_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "MAJOR_ID", referencedColumnName = "ID"))),
      @AssociationOverride(name = "directions", joinTable = @JoinTable(name = "audit_settings_directions", joinColumns = @JoinColumn(name = "audit_setting_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "DIRECTION_ID", referencedColumnName = "ID")))})
  private StudentScope studentScope = new StudentScope();

  /**
   * 生效日期
   */
  @NotNull
  private Date beginOn;

  /**
   * 失效日期
   */
  private Date endOn;

  public boolean isConvertable(CourseType courseType) {
    return convertables.contains(courseType);
  }

  public boolean isDisaudit(CourseType courseType) {
    if (CollectUtils.isNotEmpty(excludes)) {
      return excludes.contains(courseType);
    }
    return false;
  }

  public boolean isConvertableCourseType(CourseType courseType) {
    if (CollectUtils.isNotEmpty(convertables)) {
      return convertables.contains(courseType);
    }
    return false;
  }

  public Set<CourseType> getExcludes() {
    return excludes;
  }

  public void setExcludes(Set<CourseType> excludes) {
    this.excludes = excludes;
  }

  public Set<CourseType> getConvertables() {
    return convertables;
  }

  public void setConvertables(Set<CourseType> convertables) {
    this.convertables = convertables;
  }

  public CourseType getConvertTarget() {
    return convertTarget;
  }

  public void setConvertTarget(CourseType convertTarget) {
    this.convertTarget = convertTarget;
  }

  public AuditSetting toSetting() {
    return new AuditSetting(this.excludes, this.convertables, this.convertTarget);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StudentScope getStudentScope() {
    return studentScope;
  }

  public void setStudentScope(StudentScope studentScope) {
    this.studentScope = studentScope;
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
