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
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.edu.program.model.Program;
import org.openurp.edu.web.action.AdminBaseAction;

import com.google.gson.Gson;

/**
 * 培养方案web service api<br>
 * entry: api/program<br>
 */
public class ProgramAction extends AdminBaseAction {

  /**
   * Entry : api/program!json.action<br>
   * Accept params: <br>
   * <ul>
   * <li>term 代码或名称</li>
   * <li>pageNo 页数</li>
   * <li>pageSize 页长</li>
   * <li>program.属性</li>
   * </ul>
   * Return: json<br>
   */
  public String json() {
    OqlBuilder<Program> query = OqlBuilder.from(Program.class, "program");
    populateConditions(query, "program.grade");
    query.where("program.major.project.id = :projectId", getIntId("project"));
    query.where(":now >= program.beginOn and (program.endOn is null or :now <= program.endOn)", new Date())
        .orderBy("program.name");

    query.limit(getPageLimit());
    String names = get("term");
    if (Strings.isNotEmpty(names)) {
      names = names.replace('，', ',').replaceAll(",+", ",");
      String[] conds = Strings.split(names);
      if (null != conds && conds.length != 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("(\n");
        for (int i = 0; i < conds.length; i++) {
          String like = "'%" + conds[i] + "%'";
          if (Strings.isEmpty(like)) {
            continue;
          }
          if (i != 0) {
            sb.append("\n or ");
          }
          sb.append("program.name like ").append(like);
        }
        sb.append("\n)");
        query.where(sb.toString());
      }
    }

    String grade = get("grade");
    if (Strings.isNotBlank(grade) && !grade.equals("null")) {
      query.where("program.grade in (:grades)", Strings.split(grade));
    }
    String levelStr = get("levels");
    if (Strings.isNotBlank(levelStr) && !levelStr.equals("null")) {
      Integer[] levels = Strings.splitToInt(levelStr);
      query.where("program.level.id in (:levels)", levels);
    }
    String departStr = get("departs");
    if (Strings.isNotBlank(departStr) && !departStr.equals("null")) {
      Integer[] departs = Strings.splitToInt(departStr);
      query.where("program.department.id in (:departs)", departs);
    }
    String majorStr = get("majors");
    if (Strings.isNotBlank(majorStr) && !majorStr.equals("null")) {
      Integer[] majors = Strings.splitToInt(majorStr);
      query.where("program.major.id in (:majors)", majors);
    }
    String directionStr = get("directions");
    if (Strings.isNotBlank(directionStr) && !directionStr.equals("null")) {
      Integer[] directions = Strings.splitToInt(directionStr);
      query.where("program.direction.id in (:directions)", directions);
    }
    String stdTypeStr = get("stdTypes");
    if (Strings.isNotBlank(stdTypeStr) && !stdTypeStr.equals("null")) {
      Integer[] stdTypes = Strings.splitToInt(stdTypeStr);
      query.where("program.stdType.id in (:stdTypes)", stdTypes);
    }

    List<Map<String, Object>> result = CollectUtils.newArrayList();

    List<Program> programs = entityDao.search(query);
    for (Program program : programs) {
      Map<String, Object> entity = CollectUtils.newHashMap();
      entity.put("id", program.getId());
      entity.put("name", program.getName());
      result.add(entity);
    }
    put("programsJSON", new Gson().toJson(result));
    return forward();
  }

}
