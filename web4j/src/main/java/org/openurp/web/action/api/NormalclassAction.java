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

import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.clazz.model.NormalClass;
import org.openurp.edu.web.action.BaseAction;

/**
 * 课程web service api<br>
 * entry: api/normalclass<br>
 */
public class NormalclassAction extends BaseAction {

  /**
   * Entry : api/normalclass!json.action<br>
   * Accept params: <br>
   * <ul>
   * <li>term 代码或名称</li>
   * <li>pageNo 页数</li>
   * <li>pageSize 页长</li>
   * <li>normalclass.属性</li>
   * </ul>
   * Return: json<br>
   */
  public String json() {
    // String codeOrNames = get("term");
    OqlBuilder<NormalClass> query = OqlBuilder.from(NormalClass.class, "normalclass");
    query.where("normalclass.project.id = :projectId", getIntId("project"));
    query.orderBy("normalclass.code");
    List list = entityDao.search(query);
    // System.out.println("size="+list.size());
    put("normalclasses", list);
    return forward();

  }

}
