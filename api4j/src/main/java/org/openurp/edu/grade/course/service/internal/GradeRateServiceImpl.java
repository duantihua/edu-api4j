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
package org.openurp.edu.grade.course.service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.script.ExpressionEvaluator;
import org.openurp.code.edu.model.GradingMode;
import org.openurp.base.edu.model.Project;
import org.openurp.edu.grade.config.GradeRateConfig;
import org.openurp.edu.grade.config.GradeRateItem;
import org.openurp.edu.grade.course.service.GradeRateService;
import org.openurp.edu.grade.course.service.ScoreConverter;

public class GradeRateServiceImpl extends BaseServiceImpl implements GradeRateService {

  private ExpressionEvaluator expressionEvaluator;

  /**
   * 查询记录方式对应的配置
   */
  public ScoreConverter getConverter(Project project, GradingMode gradingMode) {
    if (null == project || null == gradingMode) { throw new IllegalArgumentException(
        "require project and grade and grading option "); }
    OqlBuilder<GradeRateConfig> builder = OqlBuilder.from(GradeRateConfig.class, "config")
        .where("config.project=:project and config.gradingMode=:gradingMode", project, gradingMode)
        .cacheable();
    GradeRateConfig config = entityDao.uniqueResult(builder);
    if (null == config) throw new RuntimeException("Cannot find ScoreConverter for " + gradingMode.getName());
    return new ScoreConverter(config, expressionEvaluator);
  }

  public Map<GradingMode, List<GradeRateItem>> getGradeItems(Project project) {
    OqlBuilder<GradeRateConfig> builder = OqlBuilder.from(GradeRateConfig.class, "config")
        .where("config.project=:project and config.gradingMode.numerical=false", project);
    List<GradeRateConfig> configs = entityDao.search(builder);
    Map<GradingMode, Map<String, GradeRateItem>> datas = new HashMap<GradingMode, Map<String, GradeRateItem>>();
    for (GradeRateConfig config : configs) {
      Map<String, GradeRateItem> items = datas.get(config.getGradingMode());
      if (null == items) {
        items = new HashMap<String, GradeRateItem>();
        datas.put(config.getGradingMode(), items);
      }
      for (GradeRateItem item : config.getItems()) {
        items.put(item.getGrade(), item);
      }
    }
    Map<GradingMode, List<GradeRateItem>> rs = new HashMap<GradingMode, List<GradeRateItem>>();
    for (Map.Entry<GradingMode, Map<String, GradeRateItem>> entry : datas.entrySet()) {
      rs.put(entry.getKey(), new ArrayList<GradeRateItem>(entry.getValue().values()));
    }
    return rs;
  }

  /**
   * 获得支持的记录方式
   *
   * @param project
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public List<GradingMode> getGradingModes(Project project) {
    OqlBuilder builder = OqlBuilder.from(GradeRateConfig.class, "config")
        .where("config.project=:project", project).select("config.gradingMode").cacheable();
    return entityDao.search(builder);
  }

  public void setExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
    this.expressionEvaluator = expressionEvaluator;
  }

}
