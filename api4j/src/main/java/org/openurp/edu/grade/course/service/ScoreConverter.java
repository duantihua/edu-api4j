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
package org.openurp.edu.grade.course.service;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.script.ExpressionEvaluator;
import org.openurp.edu.grade.config.GradeRateConfig;
import org.openurp.edu.grade.config.GradeRateItem;

public class ScoreConverter {

  private GradeRateConfig config;

  private ExpressionEvaluator expressionEvaluator;
  /** 默认成绩 */
  private Map<String, Float> defaultScoreMap;

  public ScoreConverter(GradeRateConfig config, ExpressionEvaluator expressionEvaluator) {
    super();
    this.config = config;
    this.expressionEvaluator = expressionEvaluator;
    defaultScoreMap = CollectUtils.newHashMap();
    if (null != config) {
      for (Iterator<GradeRateItem> iterator = config.getItems().iterator(); iterator.hasNext();) {
        GradeRateItem item = iterator.next();
        defaultScoreMap.put(item.getGrade(), item.getDefaultScore());
      }
    }
  }

  /**
   * 将字符串按照成绩记录方式转换成数字.<br>
   * 空成绩将转换成""
   *
   * @param score
   * @param gradingMode
   * @return
   */
  public String convert(Float score) {
    if (null == score) { return ""; }
    if (null == config) {
      return NumberFormat.getInstance().format(score.floatValue());
    } else {
      return config.convert(score);
    }
  }

  public boolean isPassed(Float score) {
    if (null == config || null == score) {
      return false;
    } else {
      return Float.compare(score, config.getPassScore()) >= 0;
    }
  }

  /**
   * 将字符串按照成绩记录方式转换成数字.<br>
   * 空成绩将转换成null
   *
   * @param score
   * @param gradingMode
   * @return
   */
  public Float convert(String score) {
    if (Strings.isBlank(score)) return null;
    if (null == config || config.getItems().size() == 0) {
      if (Numbers.isDigits(score)) return new Float(Numbers.toFloat(score));
      else return null;
    } else {
      Float newScore = (Float) defaultScoreMap.get(score);
      if (null != newScore) { return newScore; }
      if (Numbers.isDigits(score)) { return new Float(Numbers.toFloat(score)); }
      return null;
    }
  }

  /**
   * 计算分数对应的绩点
   *
   * @param score
   * @return
   */
  public Float calcGp(Float score) {
    if (null == score || score.floatValue() <= 0) return new Float(0);
    else {
      for (Iterator<GradeRateItem> iter = config.getItems().iterator(); iter.hasNext();) {
        GradeRateItem gradeRateItem = iter.next();
        if (gradeRateItem.inScope(score)) {
          if (Strings.isNotEmpty(gradeRateItem.getGpExp())) {
            Map<String, Object> data = CollectUtils.newHashMap();
            data.put("score", score);
            return expressionEvaluator.eval(gradeRateItem.getGpExp(), data, Float.class);
          } else {
            return null;
          }
        }
      }
    }
    // 默认绩点为00;
    return new Float(0);
  }

}
