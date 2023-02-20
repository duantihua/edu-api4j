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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.base.edu.code.CourseType;
import org.openurp.base.edu.code.EducationType;
import org.openurp.base.std.code.StdType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Direction;
import org.openurp.base.edu.model.Major;
import org.openurp.base.edu.model.Project;
import org.openurp.base.model.AuditStatus;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.base.std.model.Grade;
import org.openurp.code.edu.model.Degree;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.edu.model.StudyType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * 专业培养方案
 */
@Cacheable
@Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "org.openurp.edu.program.model.Program")
public class Program extends NumberIdTimeObject<Long> implements Cloneable {

  private static final long serialVersionUID = 4260627210556648248L;

  /**
   * 名称
   */
  @NotNull
  @Size(max = 200)
  private String name;
  /**
   * 年级
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Grade grade;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /**
   * 部门
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /**
   * 培养层次
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;
  /**
   * 培养类型
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationType eduType;
  /**
   * 学生类别
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private StdType stdType;

  /**
   * 专业
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /**
   * 专业方向
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Direction direction;

  /**
   * 学制
   */
  @NotNull
  private Float duration;

  /**
   * 学习形式
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private StudyType studyType;

  /**
   * 毕业授予学位
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Degree degree;

  /**
   * 开始日期
   */
  @NotNull
  private Date beginOn;

  /**
   * 结束日期 结束日期包括在有效期内
   */
  private Date endOn;

  /**
   * 备注
   */
  @Size(max = 800)
  private String remark;

  /**
   * 学位GPA
   */
  private Float degreeGpa;

  /**
   * 审核状态
   */
  @NotNull
  @Type(type = "org.beangle.orm.hibernate.udt.IDEnumType")
  private AuditStatus status = AuditStatus.UNSUBMITTED;

  /**
   * 多出学分可以冲抵的课程类别
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType offsetType;

  @OneToMany(targetEntity = TermCampus.class, cascade = {CascadeType.ALL})
  @JoinColumn(name = "program_id", nullable = true)
  private List<TermCampus> termCampuses = CollectUtils.newArrayList();

  /**学位课程*/
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private Set<Course> degreeCourses=CollectUtils.newHashSet();
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

  public Grade getGrade() {
    return grade;
  }

  public void setGrade(Grade grade) {
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

  public AuditStatus getStatus() {
    return status;
  }

  public void setStatus(AuditStatus status) {
    this.status = status;
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

  public List<TermCampus> getTermCampuses() {
    return termCampuses;
  }

  public void setTermCampuses(List<TermCampus> termCampuses) {
    this.termCampuses = termCampuses;
  }

  public Set<Course> getDegreeCourses() {
    return degreeCourses;
  }

  public void setDegreeCourses(Set<Course> degreeCourses) {
    this.degreeCourses = degreeCourses;
  }

  public EducationType getEduType() {
    return eduType;
  }

  public void setEduType(EducationType eduType) {
    this.eduType = eduType;
  }
}
