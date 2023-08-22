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
package org.openurp.std.fee.config;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.openurp.base.model.Department;
import org.openurp.base.std.code.FeeType;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.base.edu.model.Major;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "org.openurp.std.fee.config.TuitionConfig")
public class TuitionConfig extends IntegerIdObject {

  @NotNull
  /** 起始年级 */
  private String fromGrade;

  @NotNull
  /** 截止年级 */
  private String toGrade;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  /** 学历层次 */
  private EducationLevel level;

  /**
   * 系
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /**
   * 所属的专业
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /**
   * 收费类型
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private FeeType feeType;

  /**
   * 对应的值
   */
  private int amount;

  /**
   * remark
   */
  private String remark;

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

  public EducationLevel getLevel() {
    return level;
  }

  public void setLevel(EducationLevel level) {
    this.level = level;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Major getMajor() {
    return major;
  }

  public void setMajor(Major major) {
    this.major = major;
  }

  public FeeType getFeeType() {
    return feeType;
  }

  public void setFeeType(FeeType feeType) {
    this.feeType = feeType;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
