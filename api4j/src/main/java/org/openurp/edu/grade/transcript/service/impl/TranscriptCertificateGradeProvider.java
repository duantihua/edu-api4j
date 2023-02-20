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
/**
 *
 */
package org.openurp.edu.grade.transcript.service.impl;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.edu.extern.model.CertificateGrade;
import org.openurp.edu.grade.transcript.service.TranscriptDataProvider;

/**
 * @author zhouqi 2018年9月7日
 */
public class TranscriptCertificateGradeProvider implements TranscriptDataProvider {

  private EntityDao entityDao;

  @Override
  public Object getDatas(List<Student> stds, Map<String, String> options) {
    OqlBuilder<CertificateGrade> builder = OqlBuilder.from(CertificateGrade.class, "eeg");
    builder.where("eeg.std in (:stds)", stds);
    builder.where("eeg.passed = true");
    builder.orderBy(Order.parse("eeg.score"));
    List<CertificateGrade> eegs = entityDao.search(builder);

    Map<Student, List<CertificateGrade>> mapData = CollectUtils.newHashMap();
    for (CertificateGrade eeg : eegs) {
      if (!mapData.containsKey(eeg.getStd())) {
        mapData.put(eeg.getStd(), CollectUtils.newArrayList());
      }
      mapData.get(eeg.getStd()).add(eeg);
    }
    return mapData;
  }

  @Override
  public String getDataName() {
    return "externExamGrades";
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }
}
