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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openurp.base.edu.model.Semester;

public class MenuSemesterCalendar implements SemesterCalendarUI {
  public Object adapteItems(Map<String, List<Semester>> semesterTree) {
    List<HierarchySemester> results = new ArrayList<HierarchySemester>();
    for (String schoolYear : semesterTree.keySet()) {
      HierarchySemester year = new HierarchySemester();
      year.setName(schoolYear);
      List<Semester> semesters = semesterTree.get(schoolYear);
      if (null != semesters) {
        List<HierarchySemester> hierarchySemesters = new ArrayList<HierarchySemester>(semesters.size());
        for (Semester semester : semesters) {
          HierarchySemester term = new HierarchySemester();
          term.setId(semester.getId());
          term.setParent(year);
          term.setName(semester.getName());
          hierarchySemesters.add(term);
        }
        year.setChildren(hierarchySemesters);
      }
      results.add(year);
    }
    if (!results.isEmpty()) { return results; }
    return null;
  }
}
