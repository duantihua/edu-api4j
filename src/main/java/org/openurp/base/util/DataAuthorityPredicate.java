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

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.functor.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据权限判定谓词.<br>
 * 判定实体中的学生类别和部门信息是否在给定的数据范围内.<br>
 * 数据范围由<code>stdTypeAttrName</code>限定学生类别范围，""表示忽略学生范围限制.<br>
 * <code>departAttrName</code>限定部门(院系)范围,""表示忽略院系部门范围限制.<br>
 * 当实体中要检查的学生类别字段由<code>stdTypeAttrName</code>说明，<br>
 * 部门字段由<code>departAttrName</code>说明.当要检查的为空时，则认为不违反数据范围限制.<br>
 * The null data belong to anybody.
 *
 *
 */
public class DataAuthorityPredicate implements Predicate<Object> {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected String stdTypeDataRealm = "";

  protected String departDataRealm = "";

  protected String stdTypeAttrName = "studentType";

  protected String departAttrName = "department";

  public DataAuthorityPredicate(String stdTypeIdSeq, String departIdSeq) {
    this.stdTypeDataRealm = stdTypeIdSeq;
    this.departDataRealm = departIdSeq;
  }

  public DataAuthorityPredicate() {

  }

  public DataAuthorityPredicate(String stdTypeIdSeq, String departIdSeq, String studentTypeName,
      String departAttrName) {
    this.stdTypeDataRealm = stdTypeIdSeq;
    this.departDataRealm = departIdSeq;
    this.stdTypeAttrName = studentTypeName;
    this.departAttrName = departAttrName;
  }

  public Boolean apply(Object arg0) {
    try {
      // null belong to everybody
      if (null == arg0) return true;

      if (Strings.isNotEmpty(stdTypeDataRealm)) {
        Long stdTypeId = (Long) PropertyUtils.getProperty(arg0, stdTypeAttrName + ".id");
        if ((null != stdTypeId) && !Strings.contains(stdTypeDataRealm, stdTypeId.toString())) return false;
      }
      if (Strings.isNotEmpty(departDataRealm)) {
        Long departId = (Long) PropertyUtils.getProperty(arg0, departAttrName + ".id");
        if ((null != departId) && !Strings.contains(departDataRealm, departId.toString())) return false;
      }
      return true;
    } catch (Exception e) {
      logger.info("exception occurred in judge dataAuthorty of " + arg0.getClass().getName(), e);
      return false;
    }
  }

  public String getDepartAttrName() {
    return departAttrName;
  }

  public void setDepartAttrName(String departAttrName) {
    this.departAttrName = departAttrName;
  }

  public String getDepartDataRealm() {
    return departDataRealm;
  }

  public void setDepartDataRealm(String departDataRealm) {
    this.departDataRealm = departDataRealm;
  }

  public String getStdTypeAttrName() {
    return stdTypeAttrName;
  }

  public void setStdTypeAttrName(String stdTypeAttrName) {
    this.stdTypeAttrName = stdTypeAttrName;
  }

  public String getStdTypeDataRealm() {
    return stdTypeDataRealm;
  }

  public void setStdTypeDataRealm(String stdTypeDataRealm) {
    this.stdTypeDataRealm = stdTypeDataRealm;
  }

}
