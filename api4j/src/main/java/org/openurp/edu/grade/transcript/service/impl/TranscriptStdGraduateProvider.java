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

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.transcript.service.TranscriptDataProvider;
import org.openurp.base.std.model.Graduate;

/**
 * 学位信息提供者
 *
 * @since 2012-06-07
 */
public class TranscriptStdGraduateProvider extends BaseServiceImpl implements TranscriptDataProvider {

  public Object getDatas(List<Student> stds, Map<String, String> options) {
    OqlBuilder<Graduate> query = OqlBuilder.from(Graduate.class, "Graduate");
    query.where("Graduate.std in (:std)", stds);
    List<Graduate> stdGraduates = entityDao.search(query);
    Map<Student, List<Graduate>> datas = CollectUtils.newHashMap();
    for (Graduate stdGraduate : stdGraduates) {
      if (!datas.containsKey(stdGraduate.getStd())) {
        datas.put(stdGraduate.getStd(), CollectUtils.newArrayList());
      }
      datas.get(stdGraduate.getStd()).add(stdGraduate);
    }
    return datas;
  }

  public String getDataName() {
    return "stdGraduates";
  }

}
