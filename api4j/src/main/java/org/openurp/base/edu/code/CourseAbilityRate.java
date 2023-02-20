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
package org.openurp.base.edu.code;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 课程能力等级
 *
 * @since 2011-09-19
 */
@Entity(name = "org.openurp.base.edu.code.CourseAbilityRate")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CourseAbilityRate extends Code<Integer> {

  private static final long serialVersionUID = 478622629471793724L;

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public boolean sameLevel(int rate) {
    return this.rate == rate;
  }

  public boolean sameLevel(CourseAbilityRate o) {
    if (null != o) return rate == o.getRate();
    else return false;
  }

  private int rate;
}
