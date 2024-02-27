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
package org.openurp.edu.grade.course.service.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.openurp.base.hr.model.Teacher;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.grade.course.model.CourseGradeState;

public class GradeStatExtractor extends DefaultPropertyExtractor {

  public GradeStatExtractor(TextResource textResource) {
    super(textResource);
  }

  public Object getPropertyValue(Object target, String property) throws Exception {
    if ("teachers".equals(property)) {
      String teacherName = "";
      List<Teacher> teachers = CollectUtils.newArrayList();
      if (target instanceof Clazz) {
        Clazz clazz = (Clazz) target;
        teachers = clazz.getTeachers();
      } else {
        CourseGradeState gradeState = (CourseGradeState) target;
        teachers = gradeState.getClazz().getTeachers();
      }
      if (teachers.size() == 0) { return "未安排教师"; }
      for (int i = 0; i < teachers.size(); i++) {
        if (i > 0) {
          teacherName += ",";
        }
        teacherName += teachers.get(i).getName();
      }
      return teacherName;
    } else {
      return super.getPropertyValue(target, property);
    }
  }
}
