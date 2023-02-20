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
package org.openurp.edu.grade.course.service.event;

import org.beangle.commons.event.BusinessEvent;
import org.openurp.edu.grade.course.model.CourseGradeState;

/** 成绩提交事件 */
public class CourseGradeSubmitEvent extends BusinessEvent {

  private static final long serialVersionUID = 7334716231316808006L;

  public CourseGradeSubmitEvent(CourseGradeState source) {
    super(source);
  }

}
