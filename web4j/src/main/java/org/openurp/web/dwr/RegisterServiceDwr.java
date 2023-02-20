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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.base.service.StudentService;
import org.openurp.std.register.model.Register;

public class RegisterServiceDwr {
  private EntityDao entityDao;
  private StudentService studentService;

  public Map<String, Object> getRegister1(String stdId, Integer projectId, Integer semesterId) {
    Student std = entityDao.get(Student.class, Long.parseLong(stdId));
    Map<String, Object> registerMap = new HashMap<String, Object>();
    OqlBuilder<Register> query = OqlBuilder.from(Register.class, "register");
    query.where("register.std.code=:code", std.getCode());
    query.where("register.semester.id=:semesterId", semesterId);
    query.where("register.std.project.id=:projectId", projectId);
    Collection<Register> collection = entityDao.search(query);
    registerMap.put("stdId", std.getId());
    registerMap.put("stdName", std.getName());
    registerMap.put("gender", (null != std.getGender()) ? std.getGender().getName() : "");
    registerMap.put("major", (null != std.getMajor()) ? std.getMajor().getName() : "");
    registerMap.put("grade", std.getGrade());
    // 查询是否在籍在校
    registerMap.put("active", studentService.isActive(std));
    registerMap.put("inSchool", studentService.isInschool(std));

    if (CollectUtils.isEmpty(collection)) {
      registerMap.put("UnregistReason", "unregister");
    } else {
      Register register = ((List<Register>) collection).get(0);
      registerMap.put("UnregistReason", register.getUnregisteredReason());
    }
    return registerMap;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

}
