/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.beangle.commons.entity.pojo.NumberIdObject;

@MappedSuperclass
public abstract class NumberIdTimeObject<T extends Number> extends NumberIdObject<T> {
  private static final long serialVersionUID = -5395713578471562117L;

  /** 最后修改时间 */
  protected Date updatedAt;

  public NumberIdTimeObject() {
    super();
  }

  public NumberIdTimeObject(T id) {
    super(id);
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
