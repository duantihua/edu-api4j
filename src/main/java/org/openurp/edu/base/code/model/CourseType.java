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
package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 课程类别
 *
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.edu.base.code.model.CourseType")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CourseType extends Code<Integer> {

  private static final long serialVersionUID = 8232522018765348618L;

  /**
   * 是否理论课:true:理论课 false:实践课
   */
  private boolean practical;

  public boolean isPractical() {
    return practical;
  }

  public void setPractical(boolean practical) {
    this.practical = practical;
  }

  public CourseType() {
    super();
  }

  public CourseType(Integer id) {
    super(id);
  }

}
