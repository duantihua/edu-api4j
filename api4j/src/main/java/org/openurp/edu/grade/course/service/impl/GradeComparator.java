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

import java.util.Map;

import org.openurp.base.edu.model.Course;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.program.model.AlternativeCourse;

/**
 * 成绩比较器
 *
 * @see BestGradeFilter
 */
public class GradeComparator {

  /**
   * Return true if first better then second.
   * 先比较绩点，后比较分数，最后按照是否通过比较
   *
   * @param first
   *          NotNull
   * @param second
   */
  public static final boolean betterThan(CourseGrade first, CourseGrade second) {
    if (null == second) return true;
    float gp1 = (null == first.getGp()) ? 0 : first.getGp();
    float gp2 = (null == second.getGp()) ? 0 : second.getGp();
    int gpResult = Float.compare(gp1, gp2);
    if (0 != gpResult) return gpResult > 0;
    float score1 = (null == first.getScore()) ? 0 : first.getScore();
    float score2 = (null == second.getScore()) ? 0 : second.getScore();
    int scoreResult = Float.compare(score1, score2);
    if (0 != scoreResult) return scoreResult > 0;
    return first.isPassed();
  }

  /**
   * 是否替代成功 <br>
   * 先比较绩点，后比较分数，最后按照是否通过比较
   *
   * @param alternative
   * @param grades
   */
  public static boolean isSubstitute(AlternativeCourse alternative, Map<Course, CourseGrade> grades) {
    boolean existOrigGrade = false;
    float gpa1 = 0;
    float ga1 = 0;
    float credit1 = 0;
    int passed1 = 0;
    for (Course course : alternative.getOlds()) {
      CourseGrade grade = grades.get(course);
      if (null != grade) {
        if (grade.isPassed()) passed1 += 1;
        if (null != grade.getGp()) gpa1 += grade.getCourse().getDefaultCredits() * grade.getGp();
        if (null != grade.getScore()) ga1 = grade.getCourse().getDefaultCredits() * grade.getScore();
        existOrigGrade = true;
      }
      credit1 += course.getDefaultCredits();
    }

    boolean fullGrade2 = true;
    float gpa2 = 0;
    float ga2 = 0;
    float credit2 = 0;
    int passed2 = 0;
    for (Course course : alternative.getNews()) {
      CourseGrade grade = grades.get(course);
      if (null != grade) {
        if (grade.isPassed()) passed2 += 1;
        if (null != grade.getGp()) gpa2 += grade.getCourse().getDefaultCredits() * grade.getGp();
        if (null != grade.getScore()) ga2 = grade.getCourse().getDefaultCredits() * grade.getScore();
      } else {
        fullGrade2 = false;
      }
      credit2 += course.getDefaultCredits();
    }

    boolean success = false;
    if (!existOrigGrade && fullGrade2) {
      success = true;
    } else {
      if ((fullGrade2) && (credit1 > 0 && credit2 > 0)) {
        // 优先比较绩点，其次比较分数，最后比较是否通过.
        int gpaCompare = 0;
        if (gpa1 > 0 || gpa2 > 0) {
          gpaCompare = Float.compare(gpa1 / credit1, gpa2 / credit2);
        }
        if (0 == gpaCompare && (ga1 > 0 || ga2 > 0)) {
          gpaCompare = Float.compare(ga1 / credit1, ga2 / credit2);
        }
        if (0 == gpaCompare) gpaCompare = passed1 - passed2;
        success = gpaCompare <= 0;
      }
    }
    if (!success && existOrigGrade && fullGrade2) {
      for (Course course : alternative.getNews()) {
        grades.remove(course);
      }
    }
    return success;
  }

}
