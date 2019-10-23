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
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 成绩类型
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.edu.base.code.model.GradeType")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class GradeType extends Code<Integer> {

  private static final long serialVersionUID = -505415273804354748L;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  public boolean isGa() {
    return id >= 7 && id <= 9;
  }

  public boolean isMakeupOrDeplay() {
    return (id >= 8 && id <= 9) || (id == 4) || (id == 6);
  }

  public GradeType() {
    super();
  }

  public GradeType(Integer id) {
    super(id);
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

}
