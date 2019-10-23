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
package org.openurp.code.edu.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.industry;

/**
 * 留学生HSK等级
 *
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.edu.model.HskLevel")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@industry
public class HskLevel extends Code<Integer> {

  private static final long serialVersionUID = 6867316414352824211L;

  /** 等级 */
  private Integer grade;

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer degree) {
    this.grade = degree;
  }

  public boolean lowerThan(Integer degree) {
    return this.grade.intValue() < degree.intValue();
  }

  public boolean highThan(Integer degree) {
    return this.grade.intValue() > degree.intValue();
  }
}
