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
package org.openurp.std.register.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.code.std.model.UncheckinReason;
import org.openurp.code.std.model.UnregisteredReason;

/**
 * 学生注册信息
 */
@Entity(name = "org.openurp.std.register.model.Register")
public class Register extends LongIdObject {

  private static final long serialVersionUID = -7131131119889421960L;

  /** 注册学年学期 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 注册学生 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 注册时间 */
  private Date registerAt;

  /** 是否注册 */
  private boolean registed;

  /** 是否报到 */
  private boolean checkin;

  /** 是否缴费 */
  private boolean tuitionPaid;

  /** 未注册原因 */
  @ManyToOne(fetch = FetchType.LAZY)
  private UnregisteredReason unregisteredReason;

  /** 未报到原因 */
  @ManyToOne(fetch = FetchType.LAZY)
  private UncheckinReason uncheckinReason;

  /** 操作人 */
  @Size(max = 50)
  private String operateBy;

  /** 操作ip */
  @Size(max = 50)
  private String operateIp;

  /** 说明 */
  @Size(max = 50)
  private String remark;

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Register() {
    super();
  }

  public Register(Semester semester, Student std, Timestamp registerAt) {
    super();
    this.semester = semester;
    this.std = std;
    this.registerAt = registerAt;
  }

  public Register(Semester semester, Student std) {
    this(semester, std, new Timestamp(System.currentTimeMillis()));
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Date getRegisterAt() {
    return registerAt;
  }

  public void setRegisterAt(Date registerAt) {
    this.registerAt = registerAt;
  }

  public boolean isRegisted() {
    return registed;
  }

  public void setRegisted(boolean registed) {
    this.registed = registed;
  }

  public boolean isCheckin() {
    return checkin;
  }

  public void setCheckin(boolean checkin) {
    this.checkin = checkin;
  }

  public boolean isTuitionPaid() {
    return tuitionPaid;
  }

  public void setTuitionPaid(boolean tuitionPaid) {
    this.tuitionPaid = tuitionPaid;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public UnregisteredReason getUnregisteredReason() {
    return unregisteredReason;
  }

  public void setUnregisteredReason(UnregisteredReason unregisteredReason) {
    this.unregisteredReason = unregisteredReason;
  }

  public UncheckinReason getUncheckinReason() {
    return uncheckinReason;
  }

  public void setUncheckinReason(UncheckinReason uncheckinReason) {
    this.uncheckinReason = uncheckinReason;
  }

  public String getOperateBy() {
    return operateBy;
  }

  public void setOperateBy(String operateBy) {
    this.operateBy = operateBy;
  }

  public String getOperateIp() {
    return operateIp;
  }

  public void setOperateIp(String operateIp) {
    this.operateIp = operateIp;
  }

}
