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
package org.openurp.edu.program.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.code.edu.model.Degree;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.edu.model.StudyType;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.base.code.model.StdType;
import org.openurp.edu.base.model.AuditState;
import org.openurp.edu.base.model.Direction;
import org.openurp.edu.base.model.Major;
import org.openurp.edu.base.model.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * 专业培养方案
 */
@Cacheable
@Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "org.openurp.edu.program.model.Program")
public class Program extends NumberIdTimeObject<Long> implements Cloneable {

  private static final long serialVersionUID = 4260627210556648248L;

  /** 名称 */
  @NotNull
  @Size(max = 200)
  private String name;
  /** 年级 */
  @NotNull
  private String grade;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /** 部门 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 培养层次 */
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;

  /** 学生类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private StdType stdType;

  /** 专业 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /** 专业方向 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Direction direction;

  /** 学制 */
  @NotNull
  private Float duration;

  /** 学习形式 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private StudyType studyType;

  /** 毕业授予学位 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Degree degree;

  /** 开始日期 */
  @NotNull
  private Date beginOn;

  /** 结束日期 结束日期包括在有效期内 */
  private Date endOn;

  /** 备注 */
  @Size(max = 800)
  private String remark;

  /** 学位GPA */
  private Float degreeGpa;

  /** 审核状态 */
  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private AuditState state = AuditState.UNSUBMITTED;

  /** 多出学分可以冲抵的课程类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType offsetType;

  public Program() {
    super();
  }

  public Program(Long id) {
    super(id);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public EducationLevel getLevel() {
    return level;
  }

  public void setLevel(EducationLevel level) {
    this.level = level;
  }

  public StdType getStdType() {
    return stdType;
  }

  public void setStdType(StdType stdType) {
    this.stdType = stdType;
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

  public Float getDuration() {
    return duration;
  }

  public void setDuration(Float duration) {
    this.duration = duration;
  }

  public StudyType getStudyType() {
    return studyType;
  }

  public void setStudyType(StudyType studyType) {
    this.studyType = studyType;
  }

  public Degree getDegree() {
    return degree;
  }

  public void setDegree(Degree degree) {
    this.degree = degree;
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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public AuditState getState() {
    return state;
  }

  public void setState(AuditState auditState) {
    this.state = auditState;
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  /**
   * @return the degreeGpa
   */
  public Float getDegreeGpa() {
    return degreeGpa;
  }

  /**
   * @param degreeGpa the degreeGpa to set
   */
  public void setDegreeGpa(Float degreeGpa) {
    this.degreeGpa = degreeGpa;
  }

  public CourseType getOffsetType() {
    return offsetType;
  }

  public void setOffsetType(CourseType offsetType) {
    this.offsetType = offsetType;
  }


}
