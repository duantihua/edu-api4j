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

import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.CourseTaker;

/**
 * 补缓成绩设置策略
 */
public interface MakeupStdStrategy {

  String getClazzCondition(Integer gradeTypeId);

  List<CourseTaker> getCourseTakers(Clazz clazz);

  /**
   * 每个任务的补缓人数
   *
   * @param clazzes
   * @return
   */
  Map<Clazz, Number> getCourseTakerCounts(List<Clazz> clazzes);

}
