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
package org.openurp.edu.clazz.app.model.constraint;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 奖励学分标准<br>
 * [floorAvgGrade,ceilAvgGrade)－>awardCredits
 */
@Entity(name = "org.openurp.edu.clazz.app.model.constraint.CreditAwardCriteria")
public class CreditAwardCriteria extends LongIdObject {

  private static final long serialVersionUID = 8574528313999902227L;

  /** 平均绩点下限(包含) */
  @NotNull
  private float floorAvgGrade;

  /** 平均绩点上限(不包含) */
  @NotNull
  private float ceilAvgGrade;

  /** 奖励学分 */
  @NotNull
  private float awardCredits;

  /**
   * @return Returns the awardCredits.
   */
  public float getAwardCredits() {
    return awardCredits;
  }

  /**
   * @param awardCredits
   *          The awardCredits to set.
   */
  public void setAwardCredits(float awardCredits) {
    this.awardCredits = awardCredits;
  }

  /**
   * @return Returns the ceilAvgGrade.
   */
  public float getCeilAvgGrade() {
    return ceilAvgGrade;
  }

  /**
   * @param ceilAvgGrade
   *          The ceilAvgGrade to set.
   */
  public void setCeilAvgGrade(float ceilAvgGrade) {
    this.ceilAvgGrade = ceilAvgGrade;
  }

  /**
   * @return Returns the floorAvgGrade.
   */
  public float getFloorAvgGrade() {
    return floorAvgGrade;
  }

  /**
   * @param floorAvgGrade
   *          The floorAvgGrade to set.
   */
  public void setFloorAvgGrade(float floorAvgGrade) {
    this.floorAvgGrade = floorAvgGrade;
  }
}
