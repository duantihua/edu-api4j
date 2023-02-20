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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletResponse;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Strings;
import org.openurp.base.edu.model.Major;
import org.openurp.base.edu.model.MajorJournal;
import org.openurp.edu.web.action.BaseAction;
import org.openurp.web.action.internal.ClazzGsonBuilderHelper;
import org.openurp.web.action.internal.ClazzGsonBuilderWorker;

import com.google.gson.Gson;

/**
 * 专业web service api<br>
 * entry: api/major<br>
 */
public class MajorAction extends BaseAction {

  /**
   * Entry : api/major!get.action<br>
   * Accept params: <br>
   * <ul>
   * <li>levelIds</li>
   * <li>departmentIds</li>
   * <li>pageNo 页数</li>
   * <li>pageSize 页长</li>
   * <li>major.属性</li>
   * </ul>
   * Return: json<br>
   *
   * @throws IOException
   */
  public String json() throws IOException {
    Integer[] levelIds = Strings.splitToInt(get("levelIds"));
    Integer[] departmentIds = Strings.splitToInt(get("departmentIds"));
    String warnings = "";

    if (Arrays.isEmpty(levelIds)) {
      warnings += "请先选择培养层次\n";
    }
    if (Arrays.isEmpty(departmentIds)) {
      warnings += "请先选择上课院系";
    }

    List<Major> majors = new ArrayList<Major>();
    if (Strings.isBlank(warnings)) {
      OqlBuilder<Major> query = OqlBuilder.from(Major.class, "major");
      query.where("major.project.id = :projectId", getIntId("project"))
          .where("exists(from major.journals md where md.depart.id in (:departIds))", departmentIds)
          .orderBy("major.code, major.name");
      majors = entityDao.search(query);
    }

    Gson gson = new Gson();

    String json = gson
        .toJson(ClazzGsonBuilderHelper.genGroupResult(majors, warnings, new ClazzGsonBuilderWorker() {
          public void dirtywork(Object object, Map<String, Object> groups) {
            Major major = (Major) object;
            for (MajorJournal md : major.getJournals()) {
              String groupName = md.getDepart().getName().toString();
              if (groups.get(groupName) == null) {
                groups.put(groupName, new ArrayList<Map<String, Object>>());
              }
              List<Map<String, Object>> entities = (List<Map<String, Object>>) groups.get(groupName);
              Map<String, Object> entity = new TreeMap<String, Object>();
              entity.put("id", major.getId());
              entity.put("name", major.getName());
              entity.put("code", major.getCode());
              if (!entities.contains(entity)) {
                entities.add(entity);
              }
            }
          }
        }));
    HttpServletResponse response = getResponse();
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write(json);
    response.getWriter().close();
    return null;
  }

}
