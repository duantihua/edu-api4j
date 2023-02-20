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
 * 成绩记录方式
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.edu.model.GradingMode")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@industry
public class GradingMode extends Code<Integer> {

  private static final long serialVersionUID = 5368829635861739907L;

  /** 百分制 */
  public static final Integer Percent = new Integer(1);

  /** 英文等级制 */
  public static final Integer RankEn = new Integer(2);

  /** 中文等级制 */
  public static final Integer RankCn = new Integer(3);

  /** 标准分 */
  public static final Integer Num710 = new Integer(4);

  /** 两级制 */
  public static final Integer Two = new Integer(5);

  public GradingMode() {
    super();
  }

  public GradingMode(Integer id) {
    super(id);
  }

  /**
   * 是否为数字类型
   * 是否以数字方式显示成绩，默认为false表示以等级方式显示成绩
   */
  private boolean numerical = false;

  public boolean isNumerical() {
    return numerical;
  }

  public void setNumerical(boolean numerical) {
    this.numerical = numerical;
  }

}
