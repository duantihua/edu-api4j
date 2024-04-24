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

import org.openurp.base.edu.model.Project;
import org.openurp.code.edu.model.GradingMode;
import org.openurp.edu.grade.config.GradeRateItem;

import java.util.List;
import java.util.Map;

/**
 * 绩点规则服务类
 * <p>
 * 提供以下服务
 * <li>转换成绩 convert</li>
 * <li>计算绩点 calGp</li>
 * <li>判断是否通过</li>
 * <li>查询支持的记录方式</li>
 */
public interface GradeRateService {
  ScoreConverter getConverter(Project project, GradingMode gradingMode);

  Map<GradingMode, List<GradeRateItem>> getGradeItems(Project project);

  /**
   * 查询该项目对应的记录方式
   *
   * @param project
   * @return
   */
  List<GradingMode> getGradingModes(Project project);
}
