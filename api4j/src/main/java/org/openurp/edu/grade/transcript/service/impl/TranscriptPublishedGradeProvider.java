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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.service.CourseGradeProvider;
import org.openurp.edu.grade.course.service.impl.GradeFilter;
import org.openurp.edu.grade.course.service.impl.GradeFilterRegistry;
import org.openurp.edu.grade.transcript.service.TranscriptDataProvider;

/**
 * 提供成绩单发布的成绩及其过滤逻辑
 *
 * @since 2012-05-21
 */
public class TranscriptPublishedGradeProvider implements TranscriptDataProvider {

  private GradeFilterRegistry gradeFilterRegistry;

  private CourseGradeProvider courseGradeProvider;

  public String getDataName() {
    return "grades";
  }

  @SuppressWarnings("unchecked")
  public Object getDatas(List<Student> stds, Map<String, String> options) {
    Map<Student, Object> datas = CollectUtils.newHashMap();
    final List<GradeFilter> matched = getFilters(options);
    Map<Student, List<CourseGrade>> gradeMap = courseGradeProvider.getPublished(stds);
    for (Student std : stds) {
      List<CourseGrade> grades = gradeMap.get(std);
      for (GradeFilter filter : matched) {
        grades = filter.filter(grades);
      }
      datas.put(std, grades);
    }
    return datas;
  }

  /**
   * 获取相应的过滤器
   *
   * @param name
   * @return
   */
  protected List<GradeFilter> getFilters(Map<String, String> options) {
    if (null == options || options.isEmpty()) return Collections.emptyList();
    return gradeFilterRegistry.getFilters(options.get("grade.filters"));
  }

  public void setGradeFilterRegistry(GradeFilterRegistry gradeFilterRegistry) {
    this.gradeFilterRegistry = gradeFilterRegistry;
  }

  public void setCourseGradeProvider(CourseGradeProvider courseGradeProvider) {
    this.courseGradeProvider = courseGradeProvider;
  }
}
