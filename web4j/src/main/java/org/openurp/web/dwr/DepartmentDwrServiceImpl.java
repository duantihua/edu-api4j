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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.base.edu.model.Major;

public class DepartmentDwrServiceImpl extends BaseServiceImpl {
  /**
   * 通过部门找专业
   */
  public List getMajor(Integer departmentId, Integer levelId) {
    Department depart = (Department) entityDao.get(Department.class, departmentId);
    List rs = Collections.EMPTY_LIST;
    if (null != depart) {
      List departs = new ArrayList();
      departs.add(depart);
      // depart.getDescendants()得到所有下级部分及其子部门
      departs.addAll(depart.getChildren());
      OqlBuilder query = OqlBuilder.from(Major.class, "major");
      query.where("major.enabled=true");
      query.where("exists(from major.journals md where md.depart in (:departs))", departs);
      if (null != levelId) {
        query.where("exists(from major.journals md where md.level =:level)", new EducationLevel(levelId));
      }
      rs = (List) entityDao.search(query);
    }
    return rs;
  }
}
