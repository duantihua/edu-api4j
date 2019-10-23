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

import java.sql.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 法定假日
 *
 * @since 2013-03-07
 */
@Entity(name = "org.openurp.base.model.Holiday")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Holiday extends NumberIdObject<Integer> {

  private static final long serialVersionUID = 7235712141815472069L;

  /** 名称 */
  @NotNull
  @Size(max = 20)
  private String name;

  /** 起始日期 */
  @NotNull
  private java.sql.Date beginOn;

  /** 结束日期 */
  @NotNull
  private java.sql.Date endOn;

  /** 学校 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  public String getName() {
    return name;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBeginOn(Date startOn) {
    this.beginOn = startOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

}
