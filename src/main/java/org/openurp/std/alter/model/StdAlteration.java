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
package org.openurp.std.alter.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.code.std.model.StdAlterReason;
import org.openurp.code.std.model.StdAlterType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;

/**
 * 学籍异动
 */
@Entity(name = "org.openurp.std.alter.model.StdAlteration")
public class StdAlteration extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = 3286874472425126383L;

  /** 学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 编号 */
  private String code;

  /** 变动类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private StdAlterType alterType;

  /** 变动原因 */
  @ManyToOne(fetch = FetchType.LAZY)
  private StdAlterReason reason;

  /** 变动开始日期 */
  @NotNull
  private Date beginOn;

  /** 变动日期结束日期 */
  @NotNull
  private java.sql.Date endOn;

  /** 变动项 */
  @OneToMany(mappedBy = "alteration", orphanRemoval = true, fetch = FetchType.EAGER)
  @Cascade({ CascadeType.ALL })
  private Set<StdAlterationItem> items = CollectUtils.newHashSet();

  /** 操作备注（以逗号,分隔的国际化文件的key,记录该变动操作的备注,如培养计划操作和帐号禁用） */
  private String remark;

  /** 是否生效 */
  private boolean effective;

  public StdAlterReason getReason() {
    return reason;
  }

  public void setReason(StdAlterReason reason) {
    this.reason = reason;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Set<StdAlterationItem> getItems() {
    return items;
  }

  public void setItems(Set<StdAlterationItem> items) {
    this.items = items;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public StdAlterType getAlterType() {
    return alterType;
  }

  public void setAlterType(StdAlterType alterType) {
    this.alterType = alterType;
  }

  public Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(Date beginOn) {
    this.beginOn = beginOn;
  }

  public boolean isEffective() {
    return effective;
  }

  public void setEffective(boolean effective) {
    this.effective = effective;
  }

}
