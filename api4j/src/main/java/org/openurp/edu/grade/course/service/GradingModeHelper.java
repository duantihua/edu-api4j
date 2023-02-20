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

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Numbers;
import org.openurp.code.edu.model.GradingMode;
import org.openurp.edu.grade.config.GradeRateConfig;
import org.openurp.edu.grade.config.GradeRateItem;

public class GradingModeHelper {

  // grade,style
  private Map<String, GradingMode> gradeStyles = CollectUtils.newHashMap();

  // code,style
  private Map<String, GradingMode> styles = CollectUtils.newHashMap();

  private GradingMode defaultNumberStyle;

  private EntityDao entityDao;

  private GradingModeHelper() {
  }

  public void init(Integer defaultNumberStyleId) {
    List<GradeRateConfig> configs = entityDao.getAll(GradeRateConfig.class);
    for (GradeRateConfig config : configs) {
      if (!config.getGradingMode().isNumerical()) {
        List<GradeRateItem> items = config.getItems();
        for (GradeRateItem item : items) {
          if (null != item.getGrade()) {
            gradeStyles.put(item.getGrade(), config.getGradingMode());
          }
        }
      }
    }
    List<GradingMode> mss = entityDao.getAll(GradingMode.class);
    for (GradingMode style : mss) {
      styles.put(style.getCode(), style);
    }
    if (null != defaultNumberStyleId) {
      defaultNumberStyle = (GradingMode) entityDao.get(GradingMode.class, defaultNumberStyleId);
    }
  }

  public GradingMode styleForCode(String code) {
    GradingMode style = styles.get(code);
    if (null == style) {
      return defaultNumberStyle;
    } else {
      return style;
    }
  }

  public GradingMode styleForScore(String score) {
    GradingMode style = gradeStyles.get(score);
    if (null == style) {
      if (Numbers.isDigits(score)) {
        style = defaultNumberStyle;
      }
    }
    return style;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }
}
