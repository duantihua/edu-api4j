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
package org.openurp.edu.program.model;

import java.util.Comparator;

/**
 * 计划课程比较器
 * 1. 必修在前，2 学期小的在前，3 代码小的在前
 */
public class PlanCourseComparator implements Comparator<PlanCourse> {
  @Override
  public int compare(PlanCourse o1, PlanCourse o2) {
    if (o1.isCompulsory() ^ o2.isCompulsory()) {
      if (o1.isCompulsory()) return -1;
      else return 1;
    } else {
      var termCmp = o1.getTerms().compareTo(o2.getTerms());
      if (termCmp == 0) {
        return o1.getCourse().getCode().compareTo(o2.getCourse().getCode());
      } else {
        return termCmp;
      }
    }
  }
}
