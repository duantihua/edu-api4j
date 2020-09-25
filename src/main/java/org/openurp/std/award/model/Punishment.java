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
package org.openurp.std.award.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.Department;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.std.code.model.StdPunishType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * 处分记录
 *
 *
 */
@Entity(name = "org.openurp.std.award.model.Punishment")
public class Punishment extends LongIdObject {

  private static final long serialVersionUID = 1648452714242979752L;
  /** 处分名称 */
  @NotNull
  private String name;

  /** 处分文号 */
  private String docSeq;

  /** 处分类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private StdPunishType type;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Student std;

  /** 教学日历 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 日期 */
  @NotNull
  protected Date presentOn;

  /** 撤销日期 */
  protected Date withdrawOn;

  /** 部门 */
  @ManyToOne(fetch = FetchType.LAZY)
  protected Department depart;

  /** 备注 */
  @Size(max = 500)
  protected String remark;

  /** 处分原因 */
  @Size(max = 500)
  private String reason;

  /** 是否有效 */
  @NotNull
  private boolean valid = true;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Department getDepart() {
    return depart;
  }

  public void setDepart(Department depart) {
    this.depart = depart;
  }

  public Date getPresentOn() {
    return presentOn;
  }

  public void setPresentOn(Date presentOn) {
    this.presentOn = presentOn;
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

  public StdPunishType getType() {
    return type;
  }

  public void setType(StdPunishType type) {
    this.type = type;
  }

  public Date getWithdrawOn() {
    return withdrawOn;
  }

  public void setWithdrawOn(Date withdrawOn) {
    this.withdrawOn = withdrawOn;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public String getDocSeq() {
    return docSeq;
  }

  public void setDocSeq(String docSeq) {
    this.docSeq = docSeq;
  }
}
