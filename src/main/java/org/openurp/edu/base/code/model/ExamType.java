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
 * 考试类型
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.edu.base.code.model.ExamType")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class ExamType extends Code<Integer> {

  private static final long serialVersionUID = 9173090452635378341L;

  /** 期末考试 */
  public static final Integer FINAL = new Integer(1);

  /** 补考 */
  public static final Integer MAKEUP = new Integer(3);

  /** 缓考 */
  public static final Integer DELAY = new Integer(4);

  /** 补缓考 */
  public static final Integer MAKEUP_DELAY = new Integer(2);

  /** 期中考 */
  public static final Integer MIDTERM = new Integer(6);

  public ExamType() {
    super();
  }

  public ExamType(Integer id) {
    this.id = id;
  }
}
