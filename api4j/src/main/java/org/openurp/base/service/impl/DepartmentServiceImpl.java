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
package org.openurp.base.service.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.model.Department;
import org.openurp.base.service.DepartmentService;

public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService {

  public List<Department> getDepartments() {
    return entityDao.search(OqlBuilder.from(Department.class));
  }

  public Department getDepartment(Integer id) {
    if (null == id) return null;
    return entityDao.get(Department.class, id);

  }

  public List<Department> getColleges() {
    return entityDao.search(OqlBuilder.from(Department.class, "depart")
        .where(
            "depart.teaching =true and depart.beginOn <= :now and (depart.endOn is null or depart.endOn >= :now)",
            new java.util.Date())
        .orderBy("depart.code"));
  }

  public List<Department> getAdministatives() {
    return entityDao.search(OqlBuilder.from(Department.class, "depart")
        .where(
            "depart.teaching =false depart.beginOn <= :now and (depart.endOn is null or depart.endOn >= :now)",
            new java.util.Date())
        .orderBy("depart.code"));
  }

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.DepartmentService#getDepartments(java.lang.String[])
   */
  public List<Department> getDepartments(Integer[] ids) {
    if (null == ids || ids.length < 1) return Collections.EMPTY_LIST;
    else return entityDao.get(Department.class, ids);
  }

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.DepartmentService#getAdministatives(java.lang.String)
   */
  public List<Department> getAdministatives(String idSeq) {
    return getAdministatives(Strings.transformToInt(Strings.split(idSeq)));
  }

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.DepartmentService#getAdministatives(java.lang.String[])
   */
  public List<Department> getAdministatives(Integer[] ids) {
    if (null == ids || ids.length < 1) return null;
    return entityDao.search(OqlBuilder.from(Department.class, "depart").where("depart.teaching =false")
        .where("depart.id in (:ids)", ids));
  }

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.DepartmentService#getColleges(java.lang.String)
   */
  public List<Department> getColleges(String idSeq) {
    return getColleges(Strings.transformToInt(Strings.split(idSeq)));
  }

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.DepartmentService#getColleges(java.lang.String[])
   */
  public List<Department> getColleges(Integer[] ids) {
    if (null == ids || ids.length < 1) return null;
    return entityDao.search(OqlBuilder.from(Department.class, "depart").where("depart.teaching =true")
        .where("depart.id in (:ids)", ids));
  }

  public List<Department> getTeachDeparts(String idSeq) {
    if (Strings.isEmpty(idSeq)) return Collections.EMPTY_LIST;
    else {
      return entityDao.search(OqlBuilder.from(Department.class, "depart").where("depart.teaching=true")
          .where("depart.id in (:ids)", Strings.transformToInt(Strings.split(idSeq))));
    }
  }

  public Collection<Department> getRelatedDeparts(String stdTypeIds) {
    return entityDao.search(OqlBuilder.from(Department.class, "depart").join("depart.stdTypes", "stdType")
        .where("stdType.id in (:stdTypeIds)", Strings.transformToInt(Strings.split(stdTypeIds)))
        .select("select distinct depart "));
  }

  public void saveOrUpdate(Department department) {
    department.setUpdatedAt(new Date(System.currentTimeMillis()));
    this.entityDao.saveOrUpdate(department);
  }

  public void removeDepartment(Integer id) {
    if (null == id) return;
    entityDao.remove(Department.class, "id", id);
  }

  public List<Department> getDepartments(String idSeq) {
    return this.getDepartments(Strings.transformToInt(Strings.split(idSeq)));
  }

  public List<Department> getTeachDeparts() {
    return entityDao.search(OqlBuilder.from(Department.class, "department").where("department.teaching=true")
        .where("department.beginOn <= :now and (department.endOn is null or department.endOn >= :now)",
            new java.util.Date())
        .orderBy("department.code"));
  }
}
