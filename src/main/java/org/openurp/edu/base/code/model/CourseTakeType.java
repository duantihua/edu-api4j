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
 * 修课类别
 * （重修、增修、免修不免试、主修，选修）
 *
 * @since 2005-12-2
 */
@Entity(name = "org.openurp.edu.base.code.model.CourseTakeType")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CourseTakeType extends Code<Integer> {

  private static final long serialVersionUID = -8534135665927042117L;

  /**
   * 正常修读
   */
  public static final int NORMAL = 1;

  /**
   * 重修
   */
  public static final int RESTUDY = 3;
  /**
   * 免修
   */
  public static final int Exemption = 5;

  public CourseTakeType() {
  }

  public CourseTakeType(String code) {
    super(Integer.valueOf(code));
    setCode(code);
  }

  public CourseTakeType(int id) {
    super(id);
  }

}
