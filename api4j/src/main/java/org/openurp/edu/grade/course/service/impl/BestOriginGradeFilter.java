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
 * 最好成绩过滤器(保留原始的学期和课程名称)
 */
public class BestOriginGradeFilter implements GradeFilter {

  private AlternativeCourseService alternativeCourseService;

  public BestOriginGradeFilter() {
  }

  /**
   * FIXME 缺少替代课程逻辑
   *
   * @param grades
   * @return
   */
  private Map<Course, CourseGrade> buildGradeMap(List<CourseGrade> grades) {
    Map<Course, CourseGrade> gradesMap = CollectUtils.newHashMap();
    CourseGrade old = null;
    for (CourseGrade grade : grades) {
      old = gradesMap.get(grade.getCourse());
      if (GradeComparator.betterThan(grade, old)) {
        if (null != old) {
          CourseGrade cloned = grade;
          if (grade.getSemester().after(old.getSemester())) {
            cloned = clone(grade);
            cloned.setSemester(old.getSemester());
          }
          gradesMap.put(grade.getCourse(), cloned);
        } else {
          gradesMap.put(grade.getCourse(), grade);
        }
      }
    }
    return gradesMap;
  }

  private CourseGrade clone(CourseGrade grade) {
    CourseGrade cloned = new CourseGrade();
    cloned.setStd(grade.getStd());
    cloned.setCourse(grade.getCourse());
    cloned.setSemester(grade.getSemester());
    cloned.setClazz(grade.getClazz());
    cloned.setCrn(grade.getCrn());
    cloned.setCourseType(grade.getCourseType());
    cloned.setCourseTakeType(grade.getCourseTakeType());
    cloned.setFreeListening(grade.isFreeListening());
    cloned.setExamMode(grade.getExamMode());
    cloned.setGradingMode(grade.getGradingMode());
    cloned.setProject(grade.getProject());
    cloned.setGp(grade.getGp());
    cloned.setPassed(grade.isPassed());
    cloned.setScore(grade.getScore());
    cloned.setScoreText(grade.getScoreText());
    cloned.setStatus(grade.getStatus());
    cloned.getExamGrades().addAll(grade.getExamGrades());
    return cloned;
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
      // 只能接受一对一替代
      CourseGrade origin = gradesMap.get(subCourse.getOlds().iterator().next());
      CourseGrade sub = gradesMap.get(subCourse.getNews().iterator().next());
      if (null == origin || null == sub) continue;
      if (GradeComparator.betterThan(sub, origin)) {
        gradesMap.remove(sub.getCourse().getId());
        CourseGrade subClone = clone(sub);
        subClone.setSemester(origin.getSemester());
        subClone.setCourse(origin.getCourse());
        gradesMap.put(origin.getCourse(), subClone);
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
