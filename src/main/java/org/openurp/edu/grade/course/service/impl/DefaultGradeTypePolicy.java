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

import org.openurp.code.edu.model.GradeType;
import org.openurp.edu.clazz.model.CourseTaker;
import org.openurp.edu.exam.model.ExamTaker;

import org.openurp.edu.grade.course.service.GradeTypePolicy;

public class DefaultGradeTypePolicy implements GradeTypePolicy {

  /**
   * 补缓成绩要符合考试类型
   */
  public boolean isGradeFor(CourseTaker taker, GradeType gradeType, ExamTaker examtaker) {
    if (taker.isFreeListening() && gradeType.getId().equals(GradeType.USUAL_ID)) { return false; }
    if (null != gradeType.getExamType() && gradeType.isMakeupOrDeplay()) {
      return null != examtaker && examtaker.getExamType().getId().equals(gradeType.getExamType().getId());
    } else {
      return true;
    }
  }

}
