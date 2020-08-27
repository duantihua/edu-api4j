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
package org.openurp.edu.grade.course.model;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.GradingMode;
import org.openurp.edu.grade.Grade;

/**
 * 成绩状态抽象基类
 */
@MappedSuperclass
public abstract class AbstractGradeState extends LongIdObject implements GradeState {

  private static final long serialVersionUID = 1L;

  /** 成绩记录方式 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected GradingMode gradingMode;

  /** 成绩录入状态 */
  protected int status = Grade.Status.New;

  /** * 小数点后保留几位 */
  protected int scorePrecision = 0;

  /** 上次成绩录入时间 */
  protected Date updatedAt;

  /** 操作者 */
  @Size(max = 50)
  protected String operator;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getScorePrecision() {
    return scorePrecision;
  }

  public void setScorePrecision(int scorePrecision) {
    this.scorePrecision = scorePrecision;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * 确认的和发布的全部算作确认过的
   */
  public boolean isConfirmed() {
    return status >= Grade.Status.Confirmed;
  }

  public GradingMode getGradingMode() {
    return gradingMode;
  }

  public void setGradingMode(GradingMode gradingMode) {
    this.gradingMode = gradingMode;
  }

  public boolean isPublished() {
    return status == Grade.Status.Published;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

}
