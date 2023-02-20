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
package org.openurp.edu.grade.app.service.impl;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.grade.app.model.GradeModifyApply;
import org.openurp.edu.grade.app.service.GradeModifyApplyService;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.ExamGrade;

public class GradeModifyApplyServiceImpl extends BaseServiceImpl implements GradeModifyApplyService {

  public CourseGrade getCourseGrade(GradeModifyApply apply) {
    OqlBuilder<CourseGrade> builder = OqlBuilder.from(CourseGrade.class, "grade");
    builder.where("grade.semester  = :semester", apply.getSemester());
    builder.where("grade.project  = :project", apply.getProject());
    builder.where("grade.std  = :std", apply.getStd());
    builder.where("grade.course  = :course", apply.getCourse());
    List<CourseGrade> grades = entityDao.search(builder);
    return grades.isEmpty() ? null : grades.get(0);
  }

  public ExamGrade getExamGrade(GradeModifyApply apply) {
    OqlBuilder<ExamGrade> builder = OqlBuilder.from(ExamGrade.class, "grade");
    builder.where("grade.courseGrade.semester  = :semester", apply.getSemester());
    builder.where("grade.courseGrade.project  = :project", apply.getProject());
    builder.where("grade.courseGrade.std  = :std", apply.getStd());
    builder.where("grade.courseGrade.course  = :course", apply.getCourse());
    builder.where("grade.gradeType = :gradeType", apply.getGradeType());
    List<ExamGrade> grades = entityDao.search(builder);
    return grades.isEmpty() ? null : grades.get(0);
  }
}
