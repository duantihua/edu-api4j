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
import javax.persistence.Column;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 教职工类别
 *
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.edu.base.code.model.TeacherType")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class TeacherType extends Code<Integer> {

  private static final long serialVersionUID = -1761600684758782044L;

  /** 是否外聘 */
  @Column(name = "external_")
  private boolean external;

  /** 是否兼职 */
  private boolean parttime;

  public TeacherType() {
    super();
  }

  public TeacherType(Integer id) {
    super(id);
  }

  public boolean isExternal() {
    return external;
  }

  public void setExternal(boolean external) {
    this.external = external;
  }

  public boolean isParttime() {
    return parttime;
  }

  public void setParttime(boolean parttime) {
    this.parttime = parttime;
  }

}
