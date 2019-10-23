/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.edu.program.subst.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.Education;
import org.openurp.edu.base.code.model.StdType;
import org.openurp.edu.base.model.Course;
import org.openurp.edu.base.model.Direction;
import org.openurp.edu.base.model.Major;
import org.openurp.edu.base.model.Project;

/**
 * 专业替代课程
 *
 *
 */
@Entity(name = "org.openurp.edu.program.subst.model.MajorCourseSubstitution")
@Table(name = "major_course_subs")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class MajorCourseSubstitution extends AbstractCourseSubstitution {

  private static final long serialVersionUID = 5820298588618272410L;
  /** 年级 */
  @NotNull
  @Size(max = 100)
  private String fromGrade;

  /** 年级 */
  @NotNull
  @Size(max = 100)
  private String toGrade;
  /** 培养层次 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Education education;

  /** 项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 院系 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 学生类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private StdType stdType;

  /** 专业 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /** 方向 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Direction direction;

  /** 备注 */
  @Size(max = 300)
  private String remark;

  /** 被替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "major_course_subs_olds")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Course> origins = CollectUtils.newHashSet();

  /** 已替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "major_course_subs_news")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Course> substitutes = CollectUtils.newHashSet();

  public String getFromGrade() {
    return fromGrade;
  }

  public void setFromGrade(String fromGrade) {
    this.fromGrade = fromGrade;
  }

  public String getToGrade() {
    return toGrade;
  }

  public void setToGrade(String toGrade) {
    this.toGrade = toGrade;
  }

  public Major getMajor() {
    return major;
  }

  public void setMajor(Major major) {
    this.major = major;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Education getEducation() {
    return education;
  }

  public void setEducation(Education education) {
    this.education = education;
  }

  public StdType getStdType() {
    return stdType;
  }

  public void setStdType(StdType stdType) {
    this.stdType = stdType;
  }

  public Set<Course> getOrigins() {
    return origins;
  }

  public void setOrigins(Set<Course> origins) {
    this.origins = origins;
  }

  public Set<Course> getSubstitutes() {
    return substitutes;
  }

  public void setSubstitutes(Set<Course> substitutes) {
    this.substitutes = substitutes;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
