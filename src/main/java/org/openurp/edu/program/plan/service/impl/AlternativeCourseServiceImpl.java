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
package org.openurp.edu.program.plan.service.impl;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.edu.program.model.AlternativeCourse;
import org.openurp.edu.program.model.MajorAlternativeCourse;
import org.openurp.edu.program.model.StdAlternativeCourse;
import org.openurp.edu.program.plan.service.AlternativeCourseService;

import java.util.List;

public class AlternativeCourseServiceImpl extends BaseServiceImpl implements AlternativeCourseService {

  /**
   * 得到个人的替代课程 得到专业的替代课程
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public List<AlternativeCourse> getAlternativeCourses(Student student) {
    List substituteList = getStdAlternativeCourses(student);
    substituteList.addAll(getMajorAlternativeCourses(student));
    return substituteList;
  }

  /** 得到个人的替代课程* */
  public List<StdAlternativeCourse> getStdAlternativeCourses(Student student) {
    OqlBuilder<StdAlternativeCourse> query = OqlBuilder.from(StdAlternativeCourse.class, "alternative");
    query.where("alternative.std=:std", student);
    return entityDao.search(query);
  }

  /** 得到专业的替代课程* */
  public List<MajorAlternativeCourse> getMajorAlternativeCourses(Student student) {
    OqlBuilder<MajorAlternativeCourse> query = OqlBuilder.from(MajorAlternativeCourse.class,
        "alternative");
    query.where(":grade between alternative.fromGrade.code and alternative.toGrade.code", student.getGrade().getCode());
    query.where("alternative.project = :project", student.getProject());
    query.where("alternative.stdType is null or alternative.stdType = :stdType", student.getStdType());
    if (null == student.getMajor()) {
      query.where("alternative.major is null");
    } else {
      query.where("alternative.major is null or alternative.major = :major", student.getMajor());
    }
    if (null == student.getDirection()) {
      query.where("alternative.direction is null");
    } else {
      query.where("alternative.direction is null or alternative.direction = :direction",
          student.getDirection());
    }
    query.cacheable();
    return entityDao.search(query);
  }

}
