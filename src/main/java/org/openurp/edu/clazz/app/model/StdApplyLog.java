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

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.edu.clazz.app.model.enums.StdApplyType;

/**
 * 学生选课申请日志
 * TODO v1.1 这个类有问题，目前这个类只代表了放弃修读的记录, 对应的表是 xk_fqxd_rz_t v1.2 对应的表是XK_SQJL_T
 */
@Entity(name = "org.openurp.edu.clazz.app.model.StdApplyLog")
public class StdApplyLog extends LongIdObject {

  private static final long serialVersionUID = 6692048840481469549L;

  /** 申请的学期id */
  @NotNull
  private Integer semesterId;

  /** 申请的学生id */
  @NotNull
  private Long stdId;

  /** 申请的学生学号 */
  @NotNull
  private String stdCode;

  /** 申请的学生姓名 */
  @NotNull
  private String stdName;

  /** 申请的课程学分 */
  @NotNull
  private Float courseCredit;

  /** 申请的课程代码 */
  @NotNull
  private String courseCode;

  /** 申请的课程名称 */
  @NotNull
  private String courseName;

  /** 申请的ip */
  @NotNull
  private String ip;

  /** 申请的日期 */
  @NotNull
  private Date applyOn;

  /** 申请的类型 */
  @NotNull
  private StdApplyType applyType;

  /** 申请的处理结果 */
  @NotNull
  private int resultType;

  /** 备注 */
  private String remark;

  /** 相关的教学任务 */
  @NotNull
  private Long taskId;

  public Integer getSemesterId() {
    return semesterId;
  }

  public void setSemesterId(Integer semesterId) {
    this.semesterId = semesterId;
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

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getApplyOn() {
    return applyOn;
  }

  public void setApplyOn(Date applyOn) {
    this.applyOn = applyOn;
  }

  public StdApplyType getApplyType() {
    return applyType;
  }

  public void setApplyType(StdApplyType applyType) {
    this.applyType = applyType;
  }

  public Long getStdId() {
    return stdId;
  }

  public void setStdId(Long stdId) {
    this.stdId = stdId;
  }

  public Float getCourseCredit() {
    return courseCredit;
  }

  public void setCourseCredit(Float courseCredit) {
    this.courseCredit = courseCredit;
  }

  public int getResultType() {
    return resultType;
  }

  public void setResultType(int resultType) {
    this.resultType = resultType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

}
