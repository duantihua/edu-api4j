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
package org.openurp.edu.clazz.service;

/**
 * 课表形式
 */
public enum CourseTableStyle {

  /**
   * x-星期，y-小节
   */
  WEEK_TABLE,
  /**
   * x-小节，y-星期
   */
  UNIT_COLUMN,
  /**
   * 逐次安排的列表
   */
  LIST;

  public static final String STYLE_KEY = "schedule.courseTable.style";

  public static final CourseTableStyle getStyle(String name) {
    CourseTableStyle tableStyle = null;
    if (null != name) {
      try {
        tableStyle = CourseTableStyle.valueOf(name);
      } catch (IllegalArgumentException e) {
      }
    }
    if (null == tableStyle) {
      tableStyle = CourseTableStyle.WEEK_TABLE;
    }
    return tableStyle;
  }
}
