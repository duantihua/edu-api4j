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
package org.openurp.edu.web.action;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.Securities;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Teacher;

import java.util.List;

public abstract class TeacherProjectSupport extends MultiProjectSupportAction {

  @Override
  protected List<Project> getProjects() {
    OqlBuilder<Project> builder = OqlBuilder.<Project>from(Teacher.class.getName(), "teacher")
      .where("teacher.staff.code = :me", Securities.getUsername())
        .join("teacher.projects","project")
      .where("(project.endOn >:now or project.endOn is null)", new java.util.Date());
    builder.select("project");
    builder.orderBy("project.code,project.minor desc");

    return entityDao.search(builder);
  }

  public Teacher getLoginTeacher(Project project) {
    OqlBuilder<Teacher> teacherQuery = OqlBuilder.from(Teacher.class, "t");
    teacherQuery.where("t.staff.code = :code", Securities.getUsername());
    List<Teacher> teachers = entityDao.search(teacherQuery);

    if (teachers.isEmpty()) {
      return null;
    } else {
      return teachers.get(0);
    }
  }
}
