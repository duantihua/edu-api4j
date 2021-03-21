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
package org.openurp.base.edu.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Department;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.base.model.School;
import org.openurp.base.model.User;
import org.openurp.code.hr.model.WorkStatus;
import org.openurp.code.job.model.ProfessionalGrade;
import org.openurp.code.job.model.ProfessionalTitle;
import org.openurp.base.edu.code.model.TeacherType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * 教师信息默认实现
 */
@Entity(name = "org.openurp.base.edu.model.Teacher")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = 1L;

  /**
   * 学校
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;

  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "openurp.base")
  private Set<Project> projects = new HashSet<Project>();

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected User user;

  /**任教院系*/
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /**
   * 职称
   */
  @ManyToOne(fetch = FetchType.LAZY)
  protected ProfessionalTitle title;

  /**
   * 教职工类别
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected TeacherType teacherType;

  /**
   * 教师在职状态
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private WorkStatus status;

  /**
   * 任职开始日期
   */
  @NotNull
  private java.sql.Date beginOn;

  /**
   * 任职结束日期
   */
  private java.sql.Date endOn;

  /**
   * 备注
   */
  @Size(max = 500)
  protected String remark;

  public String getCode() {
    return (null == user) ? null : user.getCode();
  }

  public String getName() {
    return (null == user) ? null : user.getName();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Department getDepartment() {
    return (null == user) ? null : user.getDepartment();
  }

  public TeacherType getTeacherType() {
    return teacherType;
  }

  public void setTeacherType(TeacherType teacherType) {
    this.teacherType = teacherType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Teacher() {
  }

  public Teacher(Long id) {
    this.setId(id);
  }

  public ProfessionalTitle getTitle() {
    return title;
  }

  public void setTitle(ProfessionalTitle title) {
    this.title = title;
  }

  public ProfessionalGrade getTitleGrade() {
    return null == title ? null : title.getGrade();
  }

  public WorkStatus getStatus() {
    return status;
  }

  public void setStatus(WorkStatus status) {
    this.status = status;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  public Set<Project> getProjects() {
    return projects;
  }

  public void setProjects(Set<Project> projects) {
    this.projects = projects;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
