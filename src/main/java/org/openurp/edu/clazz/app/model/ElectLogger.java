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
package org.openurp.edu.clazz.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.code.edu.model.CourseTakeType;
import org.openurp.code.edu.model.ElectionMode;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.app.model.enums.ElectRuleType;
import org.openurp.edu.clazz.model.CourseTaker;

/**
 * 选课日志
 */
@Entity(name = "org.openurp.edu.clazz.app.model.ElectLogger")
public class ElectLogger extends NumberIdTimeObject<Long> {
  private static final long serialVersionUID = 1L;

  /** 教学项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 课程序号 */
  @NotNull
  private String crn;

  /** 课程类型 */
  @NotNull
  private String courseType;

  /** 课程代码 */
  @NotNull
  private String courseCode;

  /** 课程名称 */
  @NotNull
  private String courseName;

  /** 学分 */
  private float credits;

  /** 轮次 */
  private Integer turn;

  /** 学号 */
  @NotNull
  private String stdCode;

  /** 姓名 */
  @NotNull
  private String stdName;

  /** 操作者工号 */
  @NotNull
  private String operatorCode;

  /** 操作者姓名 */
  @NotNull
  private String operatorName;

  /** IP */
  @NotNull
  private String ipAddress;

  /** 类型 */
  @NotNull
  private ElectRuleType type;

  /** 修读类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseTakeType courseTakeType;

  /** 选课方式 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ElectionMode electionMode;

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public String getClazzNo() {
    return crn;
  }

  public void setCrn(String crn) {
    this.crn = crn;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public Integer getTurn() {
    return turn;
  }

  public void setTurn(Integer turn) {
    this.turn = turn;
  }

  public String getStdCode() {
    return stdCode;
  }

  public void setStdCode(String stdCode) {
    this.stdCode = stdCode;
  }

  public String getStdName() {
    return stdName;
  }

  public void setStdName(String stdName) {
    this.stdName = stdName;
  }

  public String getOperatorCode() {
    return operatorCode;
  }

  public void setOperatorCode(String operatorCode) {
    this.operatorCode = operatorCode;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public ElectRuleType getType() {
    return type;
  }

  public void setType(ElectRuleType type) {
    this.type = type;
  }

  public CourseTakeType getCourseTakeType() {
    return courseTakeType;
  }

  public void setCourseTakeType(CourseTakeType courseTakeType) {
    this.courseTakeType = courseTakeType;
  }

  public void setLoggerData(CourseTaker courseTaker) {
    Course course = courseTaker.getClazz().getCourse();
    setProject(courseTaker.getClazz().getProject());
    setCourseCode(course.getCode());
    setCourseName(course.getName());
    setCourseTakeType(courseTaker.getTakeType());
    setCourseType(courseTaker.getClazz().getCourseType().getName());
    setCredits(course.getDefaultCredits());
    setCrn(courseTaker.getClazz().getCrn());
    setStdCode(courseTaker.getStd().getCode());
    setStdName(courseTaker.getStd().getName());
    setSemester(courseTaker.getClazz().getSemester());
  }

  public void setElectionMode(ElectionMode electionMode) {
    this.electionMode = electionMode;
  }

  public ElectionMode getElectionMode() {
    return this.electionMode;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
