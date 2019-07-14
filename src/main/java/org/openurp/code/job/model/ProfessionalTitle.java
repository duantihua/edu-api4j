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
package org.openurp.code.job.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.nation;

/**
 * 教师职称
 *
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.job.model.ProfessionalTitle")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@nation
public class ProfessionalTitle extends Code<Integer> {

  private static final long serialVersionUID = 6784350593284360178L;

  /** 职称等级 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ProfessionalGrade grade;

  public ProfessionalGrade getGrade() {
    return grade;
  }

  public void setGrade(ProfessionalGrade grade) {
    this.grade = grade;
  }

}
