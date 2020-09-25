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
package org.openurp.stu.model;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * 处分名称
 *
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.stu.model.StdPunishType")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class StdPunishType extends Code<Integer> {

  private static final long serialVersionUID = 2306711938609410744L;

  /**
   * 处分等级值
   * 级别越小越严重
   */
  private Integer grade;

  public boolean isSeriousThan(StdPunishType other) {
    return getGrade().intValue() < other.getGrade().intValue();
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

}
