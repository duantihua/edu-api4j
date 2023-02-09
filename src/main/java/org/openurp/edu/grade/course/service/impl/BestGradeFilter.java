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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.program.model.AlternativeCourse;
import org.openurp.edu.program.plan.service.AlternativeCourseService;

/**
 * 最好成绩过滤器
 */
public class BestGradeFilter implements GradeFilter {

  private AlternativeCourseService alternativeCourseService;

  public BestGradeFilter() {
  }

  protected Map<Course, CourseGrade> buildGradeMap(List<CourseGrade> grades) {
    Map<Course, CourseGrade> gradesMap = CollectUtils.newHashMap();
    CourseGrade old = null;
    for (CourseGrade grade : grades) {
      old = gradesMap.get(grade.getCourse());
      if (GradeComparator.betterThan(grade, old)) gradesMap.put(grade.getCourse(), grade);
    }
    return gradesMap;
  }

  /**
   * @param grades
   *          <SubstitueCourse>
   * @return
   */
  public List<CourseGrade> filter(List<CourseGrade> grades) {
    Map<Course, CourseGrade> gradesMap = buildGradeMap(grades);
    List<AlternativeCourse> substituteCourses = getSubstituteCourses(grades);
    for (final AlternativeCourse subCourse : substituteCourses) {
      if (GradeComparator.isSubstitute(subCourse, gradesMap)) {
        for (Course c : subCourse.getOlds())
          gradesMap.remove(c);
      }
    }
    return CollectUtils.newArrayList(gradesMap.values());
  }

  private List<AlternativeCourse> getSubstituteCourses(List<CourseGrade> grades) {
    if (grades.isEmpty()) { return Collections.emptyList(); }
    CourseGrade grade = grades.get(0);
    return alternativeCourseService.getAlternativeCourses(grade.getStd());
  }

  public final void setAlternativeCourseService(AlternativeCourseService alternativeCourseService) {
    this.alternativeCourseService = alternativeCourseService;
  }

}
