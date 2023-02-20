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
package org.openurp.edu.student.info.service.impl;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.std.info.model.StudentInfoBean;
import org.openurp.edu.student.info.service.StudentInfoService;

public class StudentInfoServiceImpl extends BaseServiceImpl implements StudentInfoService {

  public <T extends StudentInfoBean> T getStudentInfo(Class<T> stdClass, Student std) {
    OqlBuilder<T> builder = OqlBuilder.from(stdClass, "stdInfo").where("stdInfo.std=:std", std);
    List<T> rs = entityDao.search(builder);
    if (!rs.isEmpty()) {
      return rs.get(0);
    } else return null;
  }
}
