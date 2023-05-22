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
package org.openurp.edu.grade.plan.service.internal;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.code.CourseType;
import org.openurp.base.std.model.Student;
import org.openurp.base.std.model.StudentScope;
import org.openurp.edu.grade.app.model.AuditSetting;
import org.openurp.edu.grade.plan.service.AuditSettingService;

import java.util.List;

public class AuditSettingServiceImpl extends BaseServiceImpl implements AuditSettingService {

  public AuditSetting getSetting(StudentScope studentScope, CourseType lastType) {
    OqlBuilder<AuditSetting> query = OqlBuilder.from(AuditSetting.class, "rule");
    query.where(
        "((current_date() between rule.beginOn and rule.endOn) or (current_date() >= rule.beginOn and rule.endOn is null))");
    query.where("rule.studentScope.project = :project", studentScope.getProject());
    query.where("exists (from rule.studentScope.levels es where es in (:levels))", studentScope.getLevels());
    query.where("exists (from rule.studentScope.stdTypes stdType where stdType in (:stdTypes))",
        studentScope.getStdTypes());
    List<AuditSetting> standards = entityDao.search(query);
    if (standards.size() == 1) {
      return standards.get(0);
    } else {
      return AuditSetting.empty(lastType);
    }
  }

  @Override
  public AuditSetting getSetting(Student student) {
    StudentScope studentScope = new StudentScope();
    studentScope.setProject(student.getProject());
    studentScope.getLevels().add(student.getLevel());
    studentScope.getStdTypes().add(student.getStdType());
    return getSetting(studentScope, null);
  }

}
