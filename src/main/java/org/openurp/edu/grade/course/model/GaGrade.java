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
package org.openurp.edu.grade.course.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.edu.base.code.model.GradeType;
import org.openurp.edu.base.code.model.ScoreMarkStyle;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.grade.Grade;

/**
 * 考试成绩
 */
@Entity(name = "org.openurp.edu.grade.course.model.GaGrade")
public class GaGrade extends NumberIdObject<Long> implements Grade {

  private static final long serialVersionUID = 3737090012804400743L;

  /** 成绩类型 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GradeType gradeType;

  /** 成绩记录方式 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ScoreMarkStyle markStyle;

  /** 得分 */
  private Float score;

  /** 得分字面值 */
  private String scoreText;

  /** 加减修正分 */
  private Float delta;

  /** 对应的课程成绩 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseGrade courseGrade;

  /** 成绩状态 */
  protected int status;

  /** 是否通过 */
  private boolean passed;

  /** 操作者 */
  @Size(max = 50)
  private String operator;

  /** 绩点 */
  private Float gp;

  private Date updatedAt;

  @Size(max = 100)
  private String remark;

  public Float getGp() {
    return gp;
  }

  public void setGp(Float gp) {
    this.gp = gp;
  }

  public GaGrade() {
  }

  public GaGrade(GradeType gradeType, Float score) {
    this.gradeType = gradeType;
    this.score = score;
  }

  @Override
  public Student getStd() {
    return (null == courseGrade) ? null : courseGrade.getStd();
  }

  public GaGrade init(GradeType gradeType, Float score) {
    this.gradeType = gradeType;
    this.score = score;
    return this;
  }

  public CourseGrade getCourseGrade() {
    return courseGrade;
  }

  public void setCourseGrade(CourseGrade courseGrade) {
    this.courseGrade = courseGrade;
  }

  /** 成绩类型 */
  public GradeType getGradeType() {
    return gradeType;
  }

  public void setGradeType(GradeType gradeType) {
    this.gradeType = gradeType;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public String getScoreText() {
    return scoreText;
  }

  public void setScoreText(String scoreText) {
    this.scoreText = scoreText;
  }

  public boolean isPublished() {
    return getStatus() == Grade.Status.PUBLISHED;
  }

  public boolean isConfirmed() {
    return status >= Grade.Status.CONFIRMED;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  // 大的成绩放前面
  public int compareTo(Grade grade) {
    if (null == getScore()) return 1;
    else if (null == grade.getScore()) return -1;
    return grade.getScore().compareTo(getScore());
  }

  public boolean isPassed() {
    return passed;
  }

  public void setPassed(boolean isPass) {
    this.passed = isPass;
  }

  public ScoreMarkStyle getMarkStyle() {
    return markStyle;
  }

  public void setMarkStyle(ScoreMarkStyle markStyle) {
    this.markStyle = markStyle;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public boolean isBeyondSubmit() {
    return status >= Grade.Status.CONFIRMED;
  }

  public Float getDelta() {
    return delta;
  }

  public void setDelta(Float delta) {
    this.delta = delta;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
