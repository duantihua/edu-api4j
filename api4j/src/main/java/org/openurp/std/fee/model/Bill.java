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
package org.openurp.std.fee.model;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.base.model.Department;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;
import org.openurp.base.std.code.FeeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 收费明细信息
 *
 * @author chaostone
 */
@Entity(name = "org.openurp.std.fee.model.Bill")
public class Bill extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = 5868193073466043875L;

  /**
   * 学生
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /**
   * 收费部门
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department depart;

  /**
   * 发票号
   */
  @Size(max = 32)
  private String invoiceCode;

  /**
   * 交费类型
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private FeeType feeType;

  /**
   * 应缴费用
   */
  private int amount;

  /**
   * 实收金额
   */
  private int payed;

  /**
   * 学年度学期
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /**
   * 实缴日期
   */
  @NotNull
  private Date payAt;

  /**
   * 创建日期
   */
  @NotNull
  private Date createdAt;

  /**
   * 上缴录入人
   */
  @Size(max = 50)
  private String updatedBy;

  /**
   * 备注
   */
  @Size(max = 500)
  private String remark;

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Department getDepart() {
    return depart;
  }

  public void setDepart(Department depart) {
    this.depart = depart;
  }

  public String getInvoiceCode() {
    return invoiceCode;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
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

  public FeeType getFeeType() {
    return feeType;
  }

  public void setFeeType(FeeType feeType) {
    this.feeType = feeType;
  }

  public Date getPayAt() {
    return payAt;
  }

  public void setPayAt(Date payAt) {
    this.payAt = payAt;
  }

  public String getStdCode() {
    return (null == std) ? "" : std.getCode();
  }

  public void setStdCode(String c) {

  }

  public int getPayed() {
    return payed;
  }

  public void setPayed(int payed) {
    this.payed = payed;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
