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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.hr.model.Teacher;
import org.openurp.base.service.TeacherService;

public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

  public Teacher getTeacher(String code) {
    if (Strings.isBlank(code)) { return null; }
    Iterator<Teacher> it = entityDao.get(Teacher.class, "code", code).iterator();
    return it.hasNext() ? it.next() : null;
  }

  public List<Object[]> getTeacherNamesByDepart(Integer departmentId) {
    if (null == departmentId) return CollectUtils.newArrayList(0);
    OqlBuilder<Object[]> builder = OqlBuilder.from(Teacher.class.getName() + " teacher");
    builder.where("teacher.department.id=:departmentId", departmentId);
    builder.select("teacher.id,teacher.name");
    return entityDao.search(builder);
  }

  public Teacher getTeacherById(Long id) {
    return entityDao.get(Teacher.class, id);
  }

  public Teacher getTeacherByNO(String teacherNO) {
    if (Strings.isEmpty(teacherNO)) return null;
    else {
      List<Teacher> teachers = entityDao.get(Teacher.class, "code", teacherNO);
      if (teachers.isEmpty()) return null;
      else return teachers.get(0);
    }
  }

  public List<Teacher> getTeachersByDepartment(String departIds) {
    if (Strings.isEmpty(departIds)) return Collections.emptyList();
    else return getTeachersByDepartment(Strings.transformToLong(Strings.split(departIds)));
  }

  public List<Teacher> getTeachersByDepartment(Long[] departIds) {
    if (null == departIds || departIds.length < 1) return Collections.emptyList();
    else return entityDao.get(Teacher.class, "department.id", departIds);
  }

  public List<Teacher> getTeachersById(Long[] teacherIds) {
    if (null == teacherIds || teacherIds.length < 1) return Collections.emptyList();
    else return entityDao.get(Teacher.class, "id", teacherIds);
  }

  public List<Teacher> getTeachersById(Collection teacherIds) {
    if (!teacherIds.isEmpty()) return entityDao.get(Teacher.class, "id", teacherIds);
    else return Collections.emptyList();
  }

  public List<Teacher> getTeachersByNO(String[] teacherNOs) {
    if (null == teacherNOs || teacherNOs.length < 1) return Collections.emptyList();
    else {
      return entityDao.get(Teacher.class, "code", teacherNOs);
    }
  }

}
