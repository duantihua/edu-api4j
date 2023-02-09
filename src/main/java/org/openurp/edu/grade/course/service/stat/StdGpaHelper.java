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
package org.openurp.edu.grade.course.service.stat;

import java.util.Collections;
import java.util.List;

import org.openurp.edu.grade.course.model.StdGpa;
import org.openurp.edu.grade.course.service.GpaService;

public final class StdGpaHelper {

  public static void statGpa(MultiStdGrade multiStdGrade, GpaService gpaService) {
    List<StdGrade> stdGradeList = multiStdGrade.getStdGrades();
    if (null == stdGradeList) {
      stdGradeList = Collections.emptyList();
    }
    for (StdGrade stdGrade : stdGradeList) {
      StdGpa stdGpa = new StdGpa(stdGrade.getStd());
      stdGpa.setGpa(gpaService.getGpa(stdGrade.getStd(), stdGrade.getGrades()));
      stdGrade.setStdGpa(stdGpa);
    }
  }

}
