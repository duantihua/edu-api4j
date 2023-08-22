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

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.web.action.BaseAction;

/**
 * 行政班web service api<br>
 * entry: api/squad<br>
 */
public class SquadAction extends BaseAction {

  /**
   * Entry : api/squad!json.action<br>
   * Accept params: <br>
   * <ul>
   * <li>term 代码或名称</li>
   * <li>pageNo 页数</li>
   * <li>pageSize 页长</li>
   * <li>squad.属性</li>
   * </ul>
   * Return: json<br>
   */
  public String json() {
    String codeOrNames = get("term");
    OqlBuilder<Squad> query = OqlBuilder.from(Squad.class, "squad");
    populateConditions(query, "squad.grade");
    query.where("squad.major.project.id = :projectId", getIntId("project"));
    String grade = get("squad.grade");
    if (Strings.isNotEmpty(grade)) {
      query.where("squad.grade in (:grades)", Strings.split(grade));
    }
    if (Strings.isNotEmpty(codeOrNames)) {
      codeOrNames = codeOrNames.replace('，', ',').replaceAll(",+", ",");
      String[] conds = Strings.split(codeOrNames);
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
          sb.append("squad.name like ").append(like).append(" or squad.code like ").append(like);
        }
        sb.append("\n)");
        query.where(sb.toString());
      }
    }

    String grade_ = get("grade");
    if (Strings.isNotBlank(grade_) && !grade_.equals("null")) {
      query.where("squad.grade in (:grades)", Strings.split(grade_));
    }
    String levelStr = get("levels");
    if (Strings.isNotBlank(levelStr) && !levelStr.equals("null")) {
      Integer[] levels = Strings.splitToInt(levelStr);
      query.where("squad.level.id in (:levels)", levels);
    }
    String departStr = get("departs");
    if (Strings.isNotBlank(departStr) && !departStr.equals("null")) {
      Integer[] departs = Strings.splitToInt(departStr);
      query.where("squad.department.id in (:departs)", departs);
    }
    String majorStr = get("majors");
    if (Strings.isNotBlank(majorStr) && !majorStr.equals("null")) {
      Integer[] majors = Strings.splitToInt(majorStr);
      query.where("squad.major.id in (:majors)", majors);
    }
    String directionStr = get("directions");
    if (Strings.isNotBlank(directionStr) && !directionStr.equals("null")) {
      Integer[] directions = Strings.splitToInt(directionStr);
      query.where("squad.direction.id in (:directions)", directions);
    }
    String stdTypeStr = get("stdTypes");
    if (Strings.isNotBlank(stdTypeStr) && !stdTypeStr.equals("null")) {
      Integer[] stdTypes = Strings.splitToInt(stdTypeStr);
      query.where("squad.stdType.id in (:stdTypes)", stdTypes);
    }

    Date now = new Date();
    query.where(":now1 >= squad.beginOn and (squad.endOn is null or :now2 <= squad.endOn)",
        now, now).orderBy("squad.code");

    query.orderBy("squad.code");
    put("squades", entityDao.search(query));

    return forward();
  }

}
