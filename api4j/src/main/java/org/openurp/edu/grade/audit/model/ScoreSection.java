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
package org.openurp.edu.grade.audit.model;

import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 分数范围
 */
@Entity(name = "org.openurp.edu.grade.audit.model.ScoreSection")
public class ScoreSection extends LongIdObject {

  private static final long serialVersionUID = 428221810871042136L;

  /** 起始分值 */
  private float fromScore;

  /** 结束分值 */
  private float toScore;

  public float getFromScore() {
    return fromScore;
  }

  public void setFromScore(float fromScore) {
    this.fromScore = fromScore;
  }

  public float getToScore() {
    return toScore;
  }

  public void setToScore(float toScore) {
    this.toScore = toScore;
  }

}
