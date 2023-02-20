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
package org.openurp.web.dwr;

import java.util.HashMap;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.std.model.Squad;

public class SquadDwrServiceImpl extends BaseServiceImpl {

  /**
   * 根据参数得到可选班级<br>
   * 使用位置：页面dwr
   *
   * @param grade
   * @param stdTypeId
   * @param departmentId
   * @param majorId
   * @param directionId
   * @return
   */
  public List getSquades(String grade, Long stdTypeId, Integer departmentId, Long majorId,
      Long directionId) {
    OqlBuilder query = OqlBuilder.from(Squad.class, "adminClass");
    if (Strings.isEmpty(grade)) { return null; }
    query.where("adminClass.grade = (:grade)", grade);
    if (null == stdTypeId) { return null; }
    query.where("adminClass.stdType.id = (:stdTypeId)", stdTypeId);
    if (null == departmentId) { return null; }
    query.where("adminClass.department.id = (:departmentId)", departmentId);
    if (null == majorId || majorId.intValue() == 0) {
      query.where("adminClass.major.id is null");
    } else {
      query.where("adminClass.major.id = (:majorId)", majorId);
    }
    if (null == directionId || directionId.intValue() == 0) {
      query.where("adminClass.direction.id is null");
    } else {
      query.where("adminClass.direction.id = (:directionId)", directionId);
    }
    return (List) entityDao.search(query);
  }

  /**
   * 模糊查找行政班级名称
   *
   * @param grade
   * @param stdTypeId
   * @param departId
   * @param majorId
   * @param directionId
   * @return
   */
  public List getSquadNames(String grade, Long levelId, Long departId, Long majorId,
      Long directionId) {
    HashMap params = new HashMap();
    String hql = "select d.id, d.name from Squad as d " + "where d.enabled=true";
    if (!Strings.isEmpty(grade)) {
      params.put("grade", grade);
      hql += " and d.grade=:grade";
    }
    if (null != levelId) {
      params.put("levelId", levelId);
      hql += " and d.level.id=:levelId";
    }
    if (null != departId) {
      params.put("departId", departId);
      hql += " and d.department.id=:departId";
    }
    if (null != majorId && majorId.intValue() != 0) {
      params.put("majorId", majorId);
      hql += " and d.major.id=:majorId";
    }
    if (null != directionId && directionId.intValue() != 0) {
      params.put("directionId", directionId);
      hql += " and d.direction.id=:directionId";
    }
    hql += " order by d.name";
    OqlBuilder query = OqlBuilder.from(hql);
    query.params(params);

    return (List) entityDao.search(query);
  }
}
