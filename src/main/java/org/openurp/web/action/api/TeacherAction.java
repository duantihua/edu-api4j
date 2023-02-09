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
package org.openurp.web.action.api;

import java.util.Date;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.edu.model.Teacher;
import org.openurp.edu.web.action.BaseAction;

/**
 * 教师web service api<br>
 * entry: api/teacher<br>
 */
public class TeacherAction extends BaseAction {

  /**
   * Entry : api/teacher!get.action<br>
   * Accept params: <br>
   * <ul>
   * <li>term 工号或姓名</li>
   * <li>pageNo 页数</li>
   * <li>pageSize 页长</li>
   * <li>teacher.属性</li>
   * </ul>
   * Return: json<br>
   */
  public String json() {
    String codeOrName = get("term");
    OqlBuilder<Teacher> query = OqlBuilder.from(Teacher.class, "teacher");
//    query.where("teacher.project.id=:projectId", getIntId("project"));
    populateConditions(query);

    // Integer teacherDepartId = getInt("teacher.department.id");
    // if (teacherDepartId != null) {
    // query.where("teacher.department.id = :departmentId", teacherDepartId);
    // }
    if (Strings.isNotEmpty(codeOrName)) {
      query.where("(teacher.name like :name or teacher.staff.code like :code)", '%' + codeOrName + '%',
          '%' + codeOrName + '%');
    }
    Date now = new Date();
    query.where(":now1 >= teacher.beginOn and (teacher.endOn is null or :now2 <= teacher.endOn)", now, now)
        .orderBy("teacher.name");
    PageLimit pageLimit = getPageLimit();
    query.limit(pageLimit);
    put("teachers", entityDao.search(query));
    put("pageLimit", pageLimit);
    return forward();
  }

}
