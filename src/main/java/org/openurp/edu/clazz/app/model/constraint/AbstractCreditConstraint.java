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
package org.openurp.edu.clazz.app.model.constraint;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 选课学分限制
 *
 *
 */
@MappedSuperclass
public abstract class AbstractCreditConstraint extends LongIdObject {

  private static final long serialVersionUID = 6763813672438837820L;
  /** 学分上限 */
  @NotNull
  private Float maxCredit;

  public Float getMaxCredit() {
    return maxCredit;
  }

  public void setMaxCredit(Float maxCredit) {
    this.maxCredit = maxCredit;
  }
}
