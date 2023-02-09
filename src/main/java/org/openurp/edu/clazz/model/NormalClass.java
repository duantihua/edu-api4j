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
package org.openurp.edu.clazz.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.base.edu.model.Project;
import org.openurp.base.std.model.Student;

/**
 * 常规教学班实现
 */
@Entity(name = "org.openurp.edu.clazz.model.NormalClass")
public class NormalClass extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = -1257647007810545779L;

  /** 名称 */
  @NotNull
  @Size(max = 100)
  private String name;

  /** 所在项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 培养层次 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;

  /** 学生名单 */
  @ManyToMany
  private Set<Student> students = CollectUtils.newHashSet();
  /**
   * 生效时间
   */
  @NotNull
  protected java.sql.Date beginOn;

  /**
   * 失效时间
   */
  protected java.sql.Date endOn;

  /**
   * 年级
   */
  @Size(max = 10)
  protected String grade;

  /**
   * 班级代码
   */
  @Column(unique = true)
  @NotNull
  @Size(max = 32)
  protected String code;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Student> getStudents() {
    return students;
  }

  public void setStudents(Set<Student> students) {
    this.students = students;
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

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public EducationLevel getLevel() {
    return level;
  }

  public void setLevel(EducationLevel level) {
    this.level = level;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
