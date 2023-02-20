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
package org.openurp.edu.grade.config;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Range;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

/**
 * 成绩分级配置项
 */
@Entity(name = "org.openurp.edu.grade.config.GradeRateItem")
public class GradeRateItem extends LongIdObject {

  private static final long serialVersionUID = 0L;

  /** 成绩配置 */
  @ManyToOne(fetch = FetchType.LAZY)
  private GradeRateConfig config;

  /** 显示名称 */
  private String grade;

  /** 最低分 */
  private Float minScore;

  /** 最高分 */
  private Float maxScore;

  /**
   * 绩点表达式
   */
  @Size(max = 255)
  private String gpExp;

  /** 默认分数 */
  private Float defaultScore;

  public boolean contains(Float f) {
    return Range.between(minScore, maxScore).contains(f.floatValue());
  }

  public boolean inScope(Float score) {
    return maxScore.compareTo(score) > -1 && minScore.compareTo(score) < 1;
  }

  public GradeRateConfig getConfig() {
    return config;
  }

  public void setConfig(GradeRateConfig config) {
    this.config = config;
  }

  public Float getMinScore() {
    return minScore;
  }

  public void setMinScore(Float minScore) {
    this.minScore = minScore;
  }

  public Float getMaxScore() {
    return maxScore;
  }

  public void setMaxScore(Float maxScore) {
    this.maxScore = maxScore;
  }

  public Float getDefaultScore() {
    return defaultScore;
  }

  public void setDefaultScore(Float defaultScore) {
    this.defaultScore = defaultScore;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getGpExp() {
    return gpExp;
  }

  public void setGpExp(String gpExp) {
    this.gpExp = gpExp;
  }

}
