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
package org.openurp.edu.exam.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.ExamDeferReason;
import org.openurp.base.std.model.Student;
import org.openurp.code.edu.model.ExamType;
import org.openurp.edu.clazz.model.Clazz;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 补缓考申请信息
 */
@Entity(name = "org.openurp.edu.exam.model.ExamDeferApply")
public class ExamDeferApply extends LongIdObject {

  private static final long serialVersionUID = 1L;

  /**
   * 考生记录
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamDeferReason reason;
  /**
   * 是否通过
   */
  private Boolean passed;

  /**
   * 申请原因
   */
  private String remark;

  /**
   * 电话
   */
  private String mobile;

  private Date examBeginAt;
  /**
   * 申请时间
   */
  @NotNull
  private Date updatedAt;

  public Boolean getPassed() {
    return passed;
  }

  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public ExamDeferReason getReason() {
    return reason;
  }

  public void setReason(ExamDeferReason reason) {
    this.reason = reason;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getExamBeginAt() {
    return examBeginAt;
  }

  public void setExamBeginAt(Date examBeginAt) {
    this.examBeginAt = examBeginAt;
  }
}
