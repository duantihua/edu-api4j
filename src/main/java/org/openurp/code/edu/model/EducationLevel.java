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
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.industry;

/**
 * 培养层次
 * (专科高职、专升本、高级本、硕士、本硕博连读、博士)
 *
 * @since 0.1.0
 */
@Entity(name = "org.openurp.code.edu.model.EducationLevel")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@industry
public class EducationLevel extends Code<Integer> {

  private static final long serialVersionUID = -4823371272084474124L;

  @ManyToOne(fetch = FetchType.LAZY)
  private AcademicLevel fromLevel;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private AcademicLevel toLevel;

  public AcademicLevel getFromLevel() {
    return fromLevel;
  }

  public void setFromLevel(AcademicLevel fromLevel) {
    this.fromLevel = fromLevel;
  }

  public AcademicLevel getToLevel() {
    return toLevel;
  }

  public void setToLevel(AcademicLevel toLevel) {
    this.toLevel = toLevel;
  }

  public EducationLevel() {
    super();
  }

  public EducationLevel(Integer id) {
    super(id);
  }

}
