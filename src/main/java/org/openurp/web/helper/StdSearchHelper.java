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
package org.openurp.web.helper;

import java.util.Date;

import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.QueryHelper;
import org.openurp.base.std.model.Student;

/**
 * 查询学生辅助类
 */
public class StdSearchHelper extends SearchHelper {

  public OqlBuilder<? extends Entity<Long>> buildStdQuery() {
    return buildStdQuery(null);
  }

  /**
   * 查询学生的界面参数一般规定为:std<br>
   * 其他查询班级项,采用squad开头<br>
   * specialityKind表示是否为双专业<br>
   * std_state一专业学籍有效性和二专业是否就读的条件
   *
   * @param request
   * @param extraStdTypeAttr
   * @return
   */
  public OqlBuilder<? extends Entity<Long>> buildStdQuery(String extraStdTypeAttr) {
    OqlBuilder<? extends Entity<Long>> query = null;
    query = OqlBuilder.from(Student.class, "std");
    query.where("std.project=:project", projectContext.getProject());
    QueryHelper.populateConditions(query);
    Boolean stdActive = Params.getBoolean("stdActive");
    if (null != stdActive) {
      if (Boolean.TRUE.equals(stdActive)) {
        query.where("std.state.inschool=true and std.beginOn <= :now and std.endOn >= :now and std.registed = true", new Date());
      } else {
        query.where("std.state.inschool=false or std.beginOn > :now or std.endOn < :now or std.registed=false", new Date());
      }
      // query.where(new Condition("std.active = :active", stdActive));
    }

    projectContext.applyRestriction(query);
    query.limit(QueryHelper.getPageLimit());
    query.orderBy(Order.parse(Params.get("orderBy")));

    // 特殊情况会用到
    String stdIds = Params.get("stdIds");
    if (Strings.isNotEmpty(stdIds)) {
      Long[] ids = Strings.splitToLong(stdIds);
      query.where(new Condition("std.id in (:stdIds)", ids));
    }
    return query;
  }

  public static void addMajorConditon(OqlBuilder entityQuery, String stdAttr) {
    Integer levelId = Params.getInt("std.level.id");
    Integer departId = Params.getInt("department.id");
    Integer specialityId = Params.getInt("major.id");
    Integer aspectId = Params.getInt("direction.id");
    if (!(new Long(4)).equals(levelId)) {
      entityQuery.join("left", stdAttr + ".major", "major");
      entityQuery.join("left", stdAttr + ".direction", "direction");
      if (null != aspectId) {
        entityQuery.where("direction.id=" + aspectId);
      }
      if (null != specialityId) {
        entityQuery.where("major.id=" + specialityId);
      } else {
        if (null != departId) entityQuery.where(stdAttr + ".department.id=" + departId);
      }
    } else {
      entityQuery.join("left", stdAttr + ".secondAspect", "secondAspect");
      entityQuery.join("left", stdAttr + ".secondMajor", "secondMajor");
      entityQuery.where("secondMajor is not null");
      if (null != aspectId) {
        entityQuery.where("secondAspect.id=" + aspectId);
      } else {
        if (null != specialityId) {
          entityQuery.where("secondMajor.id=" + specialityId);
        } else {
          if (null != departId) entityQuery.where(stdAttr + ".secondMajor.department.id=" + departId);
        }
      }
    }
  }

}
