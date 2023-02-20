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

import java.util.List;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;

public interface StdGradeService {

  public Student getStdByCode(String stdCode, Project project, List<Department> departments,
      EntityDao entityDao);

  public OqlBuilder<GradeType> buildGradeTypeQuery();

  public Object[] getStatus(String crn, String stdId, String semesterId, EntityDao entityDao);

  public boolean checkStdGradeExists(Student std, Semester semester, Course course, Project project);
}
