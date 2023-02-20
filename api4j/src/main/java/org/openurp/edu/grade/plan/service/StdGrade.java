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
package org.openurp.edu.grade.plan.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.course.model.CourseGrade;

public interface StdGrade {

  /**
   * 查询课程对应的成绩，不会被标记为usedCourses
   *
   * @param course
   * @return
   */
  List<CourseGrade> getGrades(Course course);

  /**
   * 使用课程课程拿成绩之后，会被标记为usedCourses
   *
   * @param course
   * @return
   */
  List<CourseGrade> useGrades(Course course);

  /**
   * 拿到还未使用过的课程
   *
   * @return
   */
  Collection<Course> getRestCourses();

  /**
   * @return
   */
  List<CourseGrade> getGrades();

  /**
   * 获得一个课程的成绩，并且会标记该课程已被使用过
   *
   * @param course
   */
  void addNoGradeCourse(Course course);

  /**
   * 返回每个课程是否通过
   *
   * @return
   */
  Map<Long, Boolean> getCoursePassedMap();

}
