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
package org.openurp.edu.grade.course.service;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Operation;
import org.openurp.code.edu.model.GradeType;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.CourseGradeState;

/**
 * 成绩发布监听器堆栈
 */
public class CourseGradePublishStack {

  protected List<CourseGradePublishListener> listeners = CollectUtils.newArrayList();

  public List<Operation> onPublish(CourseGrade grade, GradeType[] gradeTypes) {
    List<Operation> results = CollectUtils.newArrayList();
    for (CourseGradePublishListener listener : listeners) {
      results.addAll(listener.onPublish(grade, gradeTypes));
    }
    return results;
  }

  public List<Operation> onPublish(Collection<CourseGrade> grades, CourseGradeState gradeState,
      GradeType[] gradeTypes) {
    List<Operation> results = CollectUtils.newArrayList();
    for (CourseGradePublishListener listener : listeners) {
      results.addAll(listener.onPublish(grades, gradeState, gradeTypes));
    }
    return results;
  }

  public void setListeners(List<CourseGradePublishListener> publishListeners) {
    this.listeners = publishListeners;
  }

}
