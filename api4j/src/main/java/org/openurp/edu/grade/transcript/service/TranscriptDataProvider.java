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
package org.openurp.edu.grade.transcript.service;

import java.util.List;
import java.util.Map;

import org.openurp.base.std.model.Student;

/**
 * 成绩单信息提供者
 *
 * @since 2012-05-21
 */
public interface TranscriptDataProvider {

  /**
   * 批量提供学生数据
   *
   * @param stds
   * @param options
   * @return
   */
  Object getDatas(List<Student> stds, Map<String, String> options);

  /**
   * 提供数据名
   *
   * @return
   */
  String getDataName();
}
