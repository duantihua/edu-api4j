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
package org.openurp.edu.grade.plan.service.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.plan.service.StdGrade;

public class StdGradeImpl implements StdGrade {

  private Map<Course, List<CourseGrade>> gradeMap = new HashMap<Course, List<CourseGrade>>();

  private Set<Course> usedCourses = new HashSet<Course>();

  private Set<Course> noGradeCourses = new HashSet<Course>();

  /**
   * 把学生成绩封装成Map(Key=课程代码,value=<CourseGrade> List 所有该课程的成绩)
   *
   * @param courseGrades
   */
  public StdGradeImpl(List<CourseGrade> courseGrades) {
    for (CourseGrade courseGrade : courseGrades) {
      List<CourseGrade> gradelist = gradeMap.get(courseGrade.getCourse());
      if (null == gradelist) {
        gradelist = new ArrayList<CourseGrade>();
        gradeMap.put(courseGrade.getCourse(), gradelist);
      }
      gradelist.add(courseGrade);
    }
    for (Course course : gradeMap.keySet()) {
      List<CourseGrade> gradelist = gradeMap.get(course);
      Collections.sort(gradelist);
    }
  }

  public List<CourseGrade> getGrades(Course course) {
    if (noGradeCourses.contains(course)) return Collections.emptyList();
    List<CourseGrade> courseGrades = gradeMap.get(course);
    if (null == courseGrades) courseGrades = new ArrayList<CourseGrade>();
    return courseGrades;
  }

  public List<CourseGrade> useGrades(Course course) {
    if (noGradeCourses.contains(course)) { return Collections.emptyList(); }
    List<CourseGrade> courseGrades = gradeMap.get(course);
    if (null == courseGrades) {
      courseGrades = new ArrayList<CourseGrade>();
    }
    usedCourses.add(course);
    return courseGrades;
  }

  public Collection<Course> getRestCourses() {
    return CollectUtils.subtract(gradeMap.keySet(), usedCourses);
  }

  public List<CourseGrade> getGrades() {
    List<CourseGrade> grades = new ArrayList<CourseGrade>();
    for (Course course : gradeMap.keySet()) {
      grades.addAll(gradeMap.get(course));
    }
    return grades;
  }

  public void addNoGradeCourse(Course course) {
    noGradeCourses.add(course);
  }

  public Map<Long, Boolean> getCoursePassedMap() {
    Map<Long, Boolean> passedMap = CollectUtils.newHashMap();
    for (Course course : gradeMap.keySet()) {
      List<CourseGrade> grades = gradeMap.get(course);
      if (!grades.isEmpty()) {
        CourseGrade g = grades.get(0);
        passedMap.put(course.getId(), g.isPassed());
      }
    }
    return passedMap;
  }
}
