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
package org.openurp.code.person.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.nation;

/**
 * 性别
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.person.model.Gender")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@nation
public class Gender extends Code<Integer> {
  private static final long serialVersionUID = -6057146266398751050L;

  public Gender() {
  }

  public Gender(Integer id) {
    super(id);
  }

  public Gender(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public static final Integer Male = new Integer(1);
  public static final Integer Female = new Integer(2);
}
