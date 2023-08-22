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
package org.openurp.edu.grade.course.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;

/**
 * 学生成绩查询<br>
 */
public interface CourseGradeProvider {

  /**
   * 查询学生发布的成绩
   *
   * @param std
   * @param semesters
   * @return
   */
  List<CourseGrade> getPublished(Student std, Semester... semesters);

  /**
   * 查询学生所有成绩
   *
   * @param std
   * @param semesters
   * @return
   */
  List<CourseGrade> getAll(Student std, Semester... semesters);

  /**
   * 查询一批学生发布的成绩
   *
   * @param stds
   * @param semesters
   * @return
   */
  Map<Student, List<CourseGrade>> getPublished(Collection<Student> stds, Semester... semesters);

  /**
   * 查询一批学生所有成绩
   *
   * @param stds
   * @param semesters
   * @return
   */
  Map<Student, List<CourseGrade>> getAll(Collection<Student> stds, Semester... semesters);

  /**
   * 查看学生各个课程的通过状态
   *
   * @param std
   * @return
   */
  Map<Long, Boolean> getPassedStatus(Student std);

}
