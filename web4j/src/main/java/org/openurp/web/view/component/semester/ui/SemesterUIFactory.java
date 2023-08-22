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
package org.openurp.web.view.component.semester.ui;

import java.util.HashMap;
import java.util.Map;

public class SemesterUIFactory {
  private static final Map<String, SemesterCalendarUI> uiList = new HashMap<String, SemesterCalendarUI>();

  public static void register(String name, SemesterCalendarUI instance) {
    if (null != name) {
      name = name.toUpperCase();
    }
    uiList.put(name, instance);
  }

  public static SemesterCalendarUI get(String name) throws NullPointerException {
    if (null != name) {
      name = name.toUpperCase();
    }
    SemesterCalendarUI result = uiList.get(name);
    if (null == result) { throw new NullPointerException(
        "SemesterCalendar UI Type = '" + name + "' is undefined "); }
    return result;
  }
}
