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
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.code.edu.model.ExamType;
import org.openurp.code.edu.model.GradeType;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.CourseTaker;
import org.openurp.edu.exam.model.ExamTaker;
import org.openurp.edu.grade.course.service.MakeupStdStrategy;

/**
 * 按照排考情况，统计补缓名单
 */
public class MakeupByExamStrategy extends BaseServiceImpl implements MakeupStdStrategy {

  public List<CourseTaker> getCourseTakers(Clazz clazz) {
    OqlBuilder<CourseTaker> query = OqlBuilder.from(CourseTaker.class, "taker");
    query.where("taker.clazz = :clazz", clazz).where(
        " exists (from " + ExamTaker.class.getName() + " et "
            + " where et.std = taker.std and et.clazz = taker.clazz and et.examType.id in(:examTypeIds))",
        CollectUtils.newArrayList(ExamType.MAKEUP, ExamType.DELAY));
    return entityDao.search(query);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Map<Clazz, Number> getCourseTakerCounts(List<Clazz> clazzes) {
    if (clazzes.isEmpty()) return CollectUtils.newHashMap();
    ;
    Map<Long, Clazz> clazzMap = CollectUtils.newHashMap();
    for (Clazz clazz : clazzes)
      clazzMap.put(clazz.getId(), clazz);

    OqlBuilder query = OqlBuilder.from(CourseTaker.class, "taker");
    query.where("taker.clazz in (:clazzes)", clazzes)
        .where(
            " exists (from " + ExamTaker.class.getName() + " et "
                + " where et.std = taker.std and et.clazz = taker.clazz and et.examType.id in(:examTypeIds))",
            CollectUtils.newArrayList(ExamType.MAKEUP, ExamType.DELAY))
        .select("taker.clazz.id,count(*)").groupBy("taker.clazz.id");
    List rs = entityDao.search(query);
    Map<Clazz, Number> counts = CollectUtils.newHashMap();
    for (Object obj : rs) {
      Object[] count = (Object[]) obj;
      counts.put(clazzMap.get(count[0]), (Number) count[1]);
    }
    return counts;
  }

  public String getClazzCondition(Integer gradeTypeId) {
    if (gradeTypeId.equals(GradeType.GA_ID)) return "";
    else return "and exists(from " + ExamTaker.class.getName()
        + " et where et.clazz=clazz and et.examType.id in(" + ExamType.MAKEUP + "," + ExamType.DELAY + "))";
  }
}
