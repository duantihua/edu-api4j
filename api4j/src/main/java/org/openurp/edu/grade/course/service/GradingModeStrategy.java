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

import org.openurp.code.edu.model.GradeType;
import org.openurp.edu.grade.course.model.CourseGradeState;

/**
 * 课程成绩记录方式配置策略
 */
public interface GradingModeStrategy {

  /**
   * 针对空白的记录方式进行设置默认值
   *
   * @param gradeState
   * @param gradeTypes
   */
  public void configGradingMode(CourseGradeState gradeState, List<GradeType> gradeTypes);
}
