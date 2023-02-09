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
package org.openurp.edu.grade.course.service.stat;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.StdGpa;
import org.openurp.edu.grade.course.service.impl.GradeFilter;

/**
 * 学生成绩单打印模型
 */
public class StdGrade {

  protected Student std;

  protected List<CourseGrade> grades;

  protected List<GradeFilter> gradeFilters;

  protected StdGpa stdGpa;

  protected Comparator<CourseGrade> cmp;

  public StdGrade() {
    super();
  }

  /**
   * 将grades转换成[course.id.toString,courseGrade]样式的map<br>
   * 主要用于快速根据课程找到成绩.对于重修课程(课程出现重复)对应的成绩是不可预知的. FIXME
   *
   * @return
   */
  public Map<String, CourseGrade> toGradeMap() {
    Map<String, CourseGrade> gradeMap = CollectUtils.newHashMap();
    if (null == grades || grades.isEmpty()) return gradeMap;
    else {
      for (Iterator<CourseGrade> iter = grades.iterator(); iter.hasNext();) {
        CourseGrade courseGrade = iter.next();
        gradeMap.put(courseGrade.getCourse().getId().toString(), courseGrade);
      }
      return gradeMap;
    }
  }

  /**
   * @param std
   * @param grades
   *          <CourseGrade>
   * @param cmp
   */
  public StdGrade(Student std, List<CourseGrade> courseGrades, Comparator cmp,
      List<GradeFilter> gradeFilters) {
    this.std = std;
    this.gradeFilters = gradeFilters;
    this.grades = courseGrades;
    if (null != gradeFilters) {
      for (GradeFilter filter : gradeFilters) {
        grades = filter.filter(grades);
      }
    }
    if (null != cmp) {
      Collections.sort(grades, cmp);
    }
    this.cmp = cmp;
  }

  public void filterGrade(GradeFilter gradeFilter) {
    if (null != gradeFilter) {
      grades = gradeFilter.filter(grades);
    }
  }

  /**
   * 计算学生已经获得的学分(成绩合格)
   *
   * @return
   */
  public Float getCredits() {
    if (null == grades || grades.isEmpty()) { return new Float(0); }
    float credits = 0;
    for (Iterator<CourseGrade> iter = grades.iterator(); iter.hasNext();) {
      CourseGrade courseGrade = (CourseGrade) iter.next();
      if (courseGrade.isPassed()) {
        credits += courseGrade.getCourse().getDefaultCredits();
      }
    }
    return new Float(credits);
  }

  public void addGrade(CourseGrade grade) {
    this.grades.add(grade);
  }

  public List<CourseGrade> getGrades() {
    return grades;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public StdGpa getStdGpa() {
    return stdGpa;
  }

  public void setStdGpa(StdGpa stdGpa) {
    this.stdGpa = stdGpa;
  }

  public void setGrades(List<CourseGrade> grades) {
    this.grades = grades;
  }

  public List<GradeFilter> getGradeFilters() {
    return gradeFilters;
  }

  public void setGradeFilters(List<GradeFilter> gradeFilters) {
    this.gradeFilters = gradeFilters;
  }

  public Comparator<CourseGrade> getCmp() {
    return cmp;
  }

  public void setCmp(Comparator<CourseGrade> cmp) {
    this.cmp = cmp;
  }

}
