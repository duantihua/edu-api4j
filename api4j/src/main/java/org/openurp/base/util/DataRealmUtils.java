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
package org.openurp.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.model.Department;
import org.openurp.code.std.model.StdType;
import org.openurp.service.security.DataRealm;

public class DataRealmUtils {

  /**
   * @deprecated 参数只支持OqlBuilder，应该要支持OqlBuilder
   * @param query
   * @param attrs
   * @param dataRealm
   */
  public static void addDataRealm(OqlBuilder<?> query, String[] attrs, DataRealm dataRealm) {
    addDataRealms(query, attrs, Collections.singletonList(dataRealm));
  }

  /**
   * 给查询语句添加 学生类别、院系的数据级权限限制条件
   *
   * <pre>
   * FIXME
   * 这个方法已经过时，应该去掉
   * </pre>
   *
   * @param query
   * @param attrs
   * @param dataRealms
   */
  public static void addDataRealms(OqlBuilder query, String[] attrs, List dataRealms) {
    if (dataRealms == null || dataRealms.isEmpty()) // (CollectUtils.isEmpty(dataRealms))
      return;
    List conditions = new ArrayList();
    List datas = new ArrayList();
    for (int i = 0; i < dataRealms.size(); i++) {
      DataRealm dataRealm = (DataRealm) dataRealms.get(i);
      StringBuffer buffer = new StringBuffer("");
      if (attrs.length > 0) {
        if (Strings.isNotEmpty(dataRealm.getStudentTypeIdSeq()) && Strings.isNotEmpty(attrs[0])) {
          buffer.append(" exists (from " + StdType.class.getName() + " mytype where mytype.id =" + attrs[0]);
          buffer.append(" and mytype.id in(:mytypeIds" + randomInt() + "))");
          datas.add(Strings.transformToLong(Strings.split(dataRealm.getStudentTypeIdSeq())));
        }
      }
      if (attrs.length > 1) {
        if (Strings.isNotEmpty(dataRealm.getDepartmentIdSeq()) && Strings.isNotEmpty(attrs[1])) {
          if (buffer.length() > 0) {
            buffer.append(" and ");
          }
          buffer.append(
              " exists (from " + Department.class.getName() + " mydepart where mydepart.id =" + attrs[1]);
          buffer.append(" and mydepart.id in(:myDepartIds" + randomInt() + "))");
          datas.add(Strings.transformToLong(Strings.split(dataRealm.getDepartmentIdSeq())));
        }
      }
      if (buffer.length() > 0) {
        conditions.add(new Condition((buffer.toString())));
      }
    }
    StringBuffer buffer = new StringBuffer("(");
    for (int i = 0; i < conditions.size(); i++) {
      Condition condition = (Condition) conditions.get(i);
      if (i != 0) {
        buffer.append(" or ");
      }
      buffer.append(condition.getContent());
    }
    buffer.append(")");
    Condition con = new Condition(buffer.toString());
    con.params(datas);
    query.where(con);
  }

  private static String randomInt() {
    String d = String.valueOf(Math.random());
    d = Strings.replace(d, ".", "");
    d = d.substring(0, 8);
    return d;
  }
}
