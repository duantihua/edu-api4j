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
package org.openurp.edu.student.alter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.ems.meta.PropertyMeta;

/**
 * 学籍异动变动项
 */
@Entity(name = "org.openurp.edu.student.alter.model.StdAlterationItem")
public class StdAlterationItem extends LongIdObject {
  private static final long serialVersionUID = -9189977160001292658L;

  /** 学籍异动 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private StdAlteration alteration;

  /** 属性元信息 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private PropertyMeta meta;

  /** 变更前 */
  private String oldvalue;

  /** 变更后 */
  private String newvalue;

  /** 变更前 */
  private String oldtext;

  /** 变更后 */
  private String newtext;

  public StdAlterationItem() {
    super();
  }

  public StdAlterationItem(Long id) {
    super(id);
  }

  public StdAlterationItem(PropertyMeta property, String oldvalue, String newvalue, String oldtext, String newtext) {
    super();
    this.meta = property;
    this.oldvalue = oldvalue;
    this.newvalue = newvalue;
    this.oldtext = oldtext;
    this.newtext = newtext;
  }

  public StdAlteration getAlteration() {
    return alteration;
  }

  public void setAlteration(StdAlteration alteration) {
    this.alteration = alteration;
  }

  public String getOldvalue() {
    return oldvalue;
  }

  public void setOldvalue(String oldvalue) {
    this.oldvalue = oldvalue;
  }

  public String getNewvalue() {
    return newvalue;
  }

  public void setNewvalue(String newvalue) {
    this.newvalue = newvalue;
  }

  public PropertyMeta getMeta() {
    return meta;
  }

  public void setMeta(PropertyMeta meta) {
    this.meta = meta;
  }

  public String getOldtext() {
    return oldtext;
  }

  public void setOldtext(String oldtext) {
    this.oldtext = oldtext;
  }

  public String getNewtext() {
    return newtext;
  }

  public void setNewtext(String newtext) {
    this.newtext = newtext;
  }

}
