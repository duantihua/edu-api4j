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

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.industry;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * 课程性质
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.edu.model.CourseNature")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@industry
public class CourseNature extends Code<Integer> {

  private static final long serialVersionUID = 8355099231396221397L;

  private boolean practical;

  public CourseNature() {
  }

  public CourseNature(Integer id) {
    super(id);
  }

  public boolean isPractical() {
    return practical;
  }

  public void setPractical(boolean practical) {
    this.practical = practical;
  }
}
