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
package org.openurp.service.security;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.commons.entity.Component;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

/**
 * FIXME 有问题的权限数据container<br>
 * 包含了学生类别、院系的数据级权限信息
 *
 * <pre>
 * 缺陷:
 * 里面只有院系、学生类别、分页信息。已经不能满足现在的按照项目、院系、学生类别的数据级权限的要求
 * </pre>
 *
 *
 */
@Deprecated
public class DataRealm implements Component, Serializable {

  private static final long serialVersionUID = -75303778825269630L;

  /** 学生类别id串 */
  private String studentTypeIdSeq;

  /** 部门id串 */
  private String departmentIdSeq;

  public Long getId() {
    return null;
  }

  public Boolean getIsValid() {
    return null;
  }

  public Set getItems() {
    return null;
  }

  public Serializable getLongId() {
    return null;
  }

  public boolean isSaved() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isValidEntity() {
    // TODO Auto-generated method stub
    return false;
  }

  public String key() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isPO() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isVO() {
    // TODO Auto-generated method stub
    return false;
  }

  public DataRealm() {
    super();
  }

  public Object clone() {
    return new DataRealm(getStudentTypeIdSeq(), getDepartmentIdSeq());
  }

  public DataRealm(String studentTypeIdSeq, String departmentIdSeq) {
    super();
    this.studentTypeIdSeq = studentTypeIdSeq;
    this.departmentIdSeq = departmentIdSeq;
  }

  /**
   * @return Returns the departmentIdSeq.
   */
  public String getDepartmentIdSeq() {
    return departmentIdSeq;
  }

  /**
   * @param departmentIdSeq
   *          The departmentIdSeq to set.
   */
  public void setDepartmentIdSeq(String departmentIdSeq) {
    this.departmentIdSeq = departmentIdSeq;
  }

  /**
   * @return Returns the studentTypeIdSeq.
   */
  public String getStudentTypeIdSeq() {
    return studentTypeIdSeq;
  }

  /**
   * @param studentTypeIdSeq
   *          The studentTypeIdSeq to set.
   */
  public void setStudentTypeIdSeq(String studentTypeIdSeq) {
    this.studentTypeIdSeq = studentTypeIdSeq;
  }

  public static DataRealm mergeAll(List realms) {
    DataRealm realm = new DataRealm();
    if (null == realms || realms.isEmpty()) { return realm; }
    for (Iterator iter = realms.iterator(); iter.hasNext();) {
      DataRealm thisRealm = (DataRealm) iter.next();
      realm.merge(thisRealm);
    }
    return realm;
  }

  public DataRealm merge(DataRealm other) {
    if (null == other) return this;
    else {
      setDepartmentIdSeq(evictComma(Strings.mergeSeq(getDepartmentIdSeq(), other.getDepartmentIdSeq())));
      setStudentTypeIdSeq(evictComma(Strings.mergeSeq(getStudentTypeIdSeq(), other.getStudentTypeIdSeq())));
      return this;
    }
  }

  private static String evictComma(String str) {
    if (Strings.isEmpty(str)) return str;
    else {
      if (str.startsWith(",") && str.endsWith(",")) return str.substring(1, str.length() - 1);
      else if (str.startsWith(",")) {
        return str.substring(1);
      } else if (str.endsWith(",")) {
        return str.substring(0, str.length() - 1);
      } else {
        return str;
      }
    }
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return Objects.toStringBuilder(this.getClass()).add("studentTypeIdSeq", this.studentTypeIdSeq)
        .add("departmentIdSeq", this.departmentIdSeq).toString();
  }
}
