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

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.industry;

/**
 * 成绩类型
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.edu.model.GradeType")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@industry
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

  /** 期中成绩 */
  public static final Integer MIDDLE_ID = new Integer(1);

  /** 期末成绩 */
  public static final Integer END_ID = new Integer(2);

  /** 平时成绩 */
  public static final Integer USUAL_ID = new Integer(3);

  /** 补考卷面 */
  public static final Integer MAKEUP_ID = new Integer(4);

  /** 缓考成绩 */
  public static final Integer DELAY_ID = new Integer(6);

  /** 总评成绩 */
  public static final Integer GA_ID = new Integer(7);

  /** 缓考总评 */
  public static final Integer DELAY_GA_ID = new Integer(8);

  /** 补考总评 */
  public static final Integer MAKEUP_GA_ID = new Integer(9);

  /** 最终成绩 */
  public static final Integer FINAL_ID = new Integer(0);

}
