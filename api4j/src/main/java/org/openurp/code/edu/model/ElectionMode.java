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
package org.openurp.code.edu.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.industry;

/**
 * 选课方式
 *
 * @since 2009
 */
@Entity(name = "org.openurp.code.edu.model.ElectionMode")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@industry
public class ElectionMode extends Code<Integer> {

  private static final long serialVersionUID = -2935919066313751631L;

  /** 指定 */
  public static final int Assigned =1;

  /** 自选 */
  public static final int Self = 2;

  /** 代理选课 */
  public static final int Proxy = 3;

  public ElectionMode(int id) {
    super(id);
  }

  public ElectionMode() {
  }
}
