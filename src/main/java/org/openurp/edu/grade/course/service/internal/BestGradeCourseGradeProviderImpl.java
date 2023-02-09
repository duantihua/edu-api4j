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
package org.openurp.edu.grade.course.service.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.Grade;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.service.CourseGradeProvider;
import org.openurp.edu.grade.course.service.impl.BestGradeFilter;

public class BestGradeCourseGradeProviderImpl extends BaseServiceImpl implements CourseGradeProvider {

  private BestGradeFilter bestGradeFilter;

  public List<CourseGrade> getPublished(Student std, Semester... semesters) {
    OqlBuilder<CourseGrade> query = OqlBuilder.from(CourseGrade.class, "grade");
    query.where("grade.std = :std", std);
    query.where("grade.status =:status", Grade.Status.Published);
    if (null != semesters && semesters.length > 0) {
      query.where("grade.semester in(:semesters)", semesters);
    }
    query.orderBy("grade.semester.beginOn");
    return bestGradeFilter.filter(entityDao.search(query));
  }

  public List<CourseGrade> getAll(Student std, Semester... semesters) {
    OqlBuilder<CourseGrade> query = OqlBuilder.from(CourseGrade.class, "grade");
    query.where("grade.std = :std", std);
    if (null != semesters && semesters.length > 0) {
      query.where("grade.semester in(:semesters)", semesters);
    }
    query.orderBy("grade.semester.beginOn");
    return bestGradeFilter.filter(entityDao.search(query));
  }

  public Map<Student, List<CourseGrade>> getPublished(Collection<Student> stds, Semester... semesters) {
    OqlBuilder<CourseGrade> query = OqlBuilder.from(CourseGrade.class, "grade");
    query.where("grade.std in (:stds)", stds);
    query.where("grade.status =:status", Grade.Status.Published);
    if (null != semesters && semesters.length > 0) {
      query.where("grade.semester in(:semesters)", semesters);
    }
    List<CourseGrade> allGrades = entityDao.search(query);
    Map<Student, List<CourseGrade>> gradeMap = CollectUtils.newHashMap();
    for (Student std : stds) {
      gradeMap.put(std, new ArrayList<CourseGrade>());
    }
    for (CourseGrade g : allGrades)
      gradeMap.get(g.getStd()).add(g);
    for (Student std : stds) {
      gradeMap.put(std, bestGradeFilter.filter(gradeMap.get(std)));
    }
    return gradeMap;
  }

  public Map<Student, List<CourseGrade>> getAll(Collection<Student> stds, Semester... semesters) {
    OqlBuilder<CourseGrade> query = OqlBuilder.from(CourseGrade.class, "grade");
    query.where("grade.std in (:stds)", stds);
    if (null != semesters && semesters.length > 0) {
      query.where("grade.semester in(:semesters)", semesters);
    }
    List<CourseGrade> allGrades = entityDao.search(query);
    Map<Student, List<CourseGrade>> gradeMap = CollectUtils.newHashMap();
    for (Student std : stds) {
      gradeMap.put(std, new ArrayList<CourseGrade>());
    }
    for (CourseGrade g : allGrades)
      gradeMap.get(g.getStd()).add(g);
    for (Student std : stds) {
      gradeMap.put(std, bestGradeFilter.filter(gradeMap.get(std)));
    }
    return gradeMap;
  }

  @SuppressWarnings("unchecked")
  public Map<Long, Boolean> getPassedStatus(Student std) {
    OqlBuilder<?> query = OqlBuilder.from(CourseGrade.class, "cg");
    query.where("cg.std = :std", std);
    query.select("cg.course.id,cg.passed");
    List<Object[]> rs = (List<Object[]>) entityDao.search(query);
    Map<Long, Boolean> courseMap = CollectUtils.newHashMap();
    for (Object obj[] : rs) {
      Long courseId = (Long) obj[0];
      if (null != obj[1]) {
        if (courseMap.containsKey(courseId) && Boolean.TRUE.equals(courseMap.get(courseId))) {
          continue;
        } else {
          courseMap.put(courseId, (Boolean) obj[1]);
        }
      } else {
        courseMap.put(courseId, Boolean.FALSE);
      }
    }
    return courseMap;
  }

  public void setBestGradeFilter(BestGradeFilter bestGradeFilter) {
    this.bestGradeFilter = bestGradeFilter;
  }

}
