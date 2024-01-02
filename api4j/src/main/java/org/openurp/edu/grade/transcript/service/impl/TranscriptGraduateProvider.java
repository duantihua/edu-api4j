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
package org.openurp.edu.grade.transcript.service.impl;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Graduate;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.transcript.service.TranscriptDataProvider;

import java.util.List;
import java.util.Map;

/**
 * 学位信息提供者
 *
 * @since 2012-06-07
 */
public class TranscriptGraduateProvider extends BaseServiceImpl implements TranscriptDataProvider {

  public Object getDatas(List<Student> stds, Map<String, String> options) {
    OqlBuilder<Graduate> query = OqlBuilder.from(Graduate.class, "g");
    query.where("g.std in (:std)", stds);
    List<Graduate> stdGraduates = entityDao.search(query);
    Map<Student, Graduate> datas = CollectUtils.newHashMap();
    for (Graduate g : stdGraduates) {
      datas.put(g.getStd(), g);
    }
    return datas;
  }

  public String getDataName() {
    return "graduates";
  }

}
