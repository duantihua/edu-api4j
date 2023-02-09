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

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.web.action.BaseAction;

/**
 * 课程web service api<br>
 * entry: api/course<br>
 */
public class CourseAction extends BaseAction {

  /**
   * Entry : api/course!get.action<br>
   * Accept params: <br>
   * <ul>
   * <li>term 代码或名称</li>
   * <li>pageNo 页数</li>
   * <li>pageSize 页长</li>
   * <li>course.属性</li>
   * </ul>
   * Return: json<br>
   */
  public String json() {
    String codeOrName = get("term");
    OqlBuilder<Course> query = OqlBuilder.from(Course.class, "course");
    populateConditions(query);
    if (Strings.isNotEmpty(codeOrName)) {
      query.where("(course.name like :name or course.code like :code)", '%' + codeOrName + '%',
          '%' + codeOrName + '%');
    }
    PageLimit pageLimit = getPageLimit();
    query.limit(pageLimit);
    query.orderBy("course.name");
    put("courses", entityDao.search(query));
    put("pageLimit", pageLimit);
    return forward();
  }

}
