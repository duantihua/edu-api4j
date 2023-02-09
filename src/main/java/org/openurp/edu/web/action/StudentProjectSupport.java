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
import org.beangle.commons.entity.pojo.Code;
import org.beangle.commons.lang.Assert;
import org.beangle.security.Securities;
import org.openurp.base.model.BaseInfo;
import org.openurp.base.edu.model.Project;
import org.openurp.base.std.model.Student;

import java.util.List;

public abstract class StudentProjectSupport extends MultiProjectSupportAction {

  protected void addBasecode(String key, Class<? extends Code<Integer>> clazz) {
    Assert.notEmpty(key);
    put(key, codeService.getCodes(clazz));
  }

  protected void addBaseInfo(String key, Class<? extends BaseInfo> clazz) {
    put(key, baseInfoService.getBaseInfos(clazz, getProject().getSchool()));
  }

  protected List getBaseInfos(Class<? extends BaseInfo> clazz) {
    return baseInfoService.getBaseInfos(clazz, getProject().getSchool());
  }

  @Override
  protected List<Project> getProjects() {
    OqlBuilder<Project> builder = OqlBuilder.from(Student.class.getName(), "std");
    builder.where("std.code = :usercode", Securities.getUsername()).select("select distinct std.project");
    builder.where("(std.project.endOn >:now or std.project.endOn is null)", new java.util.Date());
    return entityDao.search(builder);
  }

  public Student getLoginStudent(Project project) {
    OqlBuilder<Student> stdQuery = OqlBuilder.from(Student.class, "std");
    if (null != project) stdQuery.where("std.project =:project ", project);
    stdQuery.where("std.code = :usercode", Securities.getUsername());
    List<Student> stds = entityDao.search(stdQuery);

    if (stds.isEmpty()) {
      return null;
    } else {
      return stds.get(0);
    }
  }
}
