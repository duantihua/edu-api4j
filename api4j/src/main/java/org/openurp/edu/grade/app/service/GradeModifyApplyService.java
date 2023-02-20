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
package org.openurp.edu.grade.app.service;

import org.openurp.edu.grade.app.model.GradeModifyApply;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.ExamGrade;

public interface GradeModifyApplyService {

  public CourseGrade getCourseGrade(GradeModifyApply apply);

  public ExamGrade getExamGrade(GradeModifyApply apply);

}
