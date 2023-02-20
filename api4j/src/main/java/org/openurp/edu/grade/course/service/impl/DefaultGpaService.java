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

import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.service.CourseGradeProvider;
import org.openurp.edu.grade.course.service.GpaService;

/**
 *
 */
public class DefaultGpaService implements GpaService {

  private GpaPolicy gpaPolicy = new DefaultGpaPolicy();

  private CourseGradeProvider courseGradeProvider;

  public Float getGpa(Student std) {
    return gpaPolicy.calcGpa(courseGradeProvider.getPublished(std));
  }

  public Float getGpa(Student std, List<CourseGrade> grades) {
    return gpaPolicy.calcGpa(grades);
  }

  public Float getGpa(Student std, Semester semester) {
    return gpaPolicy.calcGpa(courseGradeProvider.getPublished(std, semester));
  }

  public GpaPolicy getGpaPolicy() {
    return gpaPolicy;
  }

  public void setGpaPolicy(GpaPolicy gpaPolicy) {
    this.gpaPolicy = gpaPolicy;
  }

  public void setCourseGradeProvider(CourseGradeProvider provider) {
    this.courseGradeProvider = provider;
  }

}
