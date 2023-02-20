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
package org.openurp.web.view.component.semester;

import org.openurp.base.edu.model.Semester;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 教学日历组件
 * </p>
 * view层freemarker模板教学日历组件模型类。
 *
 *
 * @since 2010-5-14
 * @see Semester
 */
public class SemesterCalendar extends AbstractSemesterCalendarUI {
  public SemesterCalendar(ValueStack stack) {
    this(stack, true);
  }

  public SemesterCalendar(ValueStack stack, boolean generateId) {
    super(stack);
    if (!generateId) {
      this.id = "skipGenerateId";
    }
  }
}
