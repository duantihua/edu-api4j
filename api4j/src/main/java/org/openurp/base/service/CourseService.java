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
package org.openurp.base.service;

import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;

/**
 * 课程信息服务类.<br>
 * 现提供对课程基本信息增删改查功能.
 *
 *
 */
public interface CourseService {

  /**
   * 通过课程代码查询课程
   *
   * @param code
   * @return
   */
  public Course getCourse(Project project,String code);
}
