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
package org.openurp.base.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.beangle.commons.lang.Objects;

/**
 * 基础信息基类<br>
 * 提供部门、校区、教学楼、教室等四个信息的公共部分,<br>
 * 一共7个属性,其中id为非业务主键，code为业务编码<br>
 *
 *
 */
@MappedSuperclass
public abstract class AbstractBaseInfo extends NumberIdTimeObject<Integer> implements BaseInfo {

  private static final long serialVersionUID = -6628857663461758644L;

  /** 基础信息编码 */
  @Column(unique = true)
  @NotNull
  @Size(max = 100)
  protected String code;

  /** 基础信息名称 */
  @NotNull
  @Size(max = 255)
  protected String name;

  /** 基础信息英文名 */
  @Size(max = 500)
  protected String enName;

  /** 基础信息简称 */
  @Size(max = 255)
  protected String shortName;

  /** 备注 */
  @Size(max = 500)
  protected String remark;

  /** 生效时间 */
  @NotNull
  protected java.sql.Date beginOn;

  /** 失效时间 */
  protected java.sql.Date endOn;

  public AbstractBaseInfo() {
  }

  public AbstractBaseInfo(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  /**
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(BaseInfo myClass) {
    return getCode().compareTo(myClass.getCode());
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return Objects.toStringBuilder(this).add("id", this.id).add("code", this.code).add("name", this.name)
        .toString();
  }

}
