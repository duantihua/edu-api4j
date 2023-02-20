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
package org.openurp.edu.grade.course.service.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.functor.Predicate;
import org.openurp.edu.grade.course.model.CourseGrade;

/**
 * 只留下通过课程的成绩
 */
public class PassedGradeFilter implements GradeFilter {

  public List<CourseGrade> filter(List<CourseGrade> grades) {
    List<CourseGrade> gradeList = CollectUtils.select(grades, new Predicate<CourseGrade>() {
      public Boolean apply(CourseGrade grade) {
        return grade.isPassed();
      }
    });
    return gradeList;
  }
}
