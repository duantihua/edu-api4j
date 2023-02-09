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

import java.util.Date;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.grade.config.GradeInputSwitch;
import org.openurp.edu.grade.app.service.GradeInputSwitchService;

public class GradeInputSwithServiceImpl extends BaseServiceImpl implements GradeInputSwitchService {

  public GradeInputSwitch getSwitch(Project project, Semester semester) {
    OqlBuilder<GradeInputSwitch> query = OqlBuilder.from(GradeInputSwitch.class, "switch");
    query.where("switch.project=:project", project);
    query.where("switch.semester=:semester", semester);
    return entityDao.uniqueResult(query);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public List<Semester> getOpenedSemesters(Project project) {
    OqlBuilder query = OqlBuilder.from(GradeInputSwitch.class, "switch");
    query.where("switch.project=:project", project);
    query.where("switch.endAt>=:now", new Date());
    query.orderBy("switch.semester.beginOn").select("switch.semester");
    return entityDao.search(query);
  }

}
