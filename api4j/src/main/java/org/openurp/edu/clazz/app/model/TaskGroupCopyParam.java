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
package org.openurp.edu.clazz.app.model;

import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Semester;

/**
 * 排课组复制时所用到的参数
 */
public class TaskGroupCopyParam {

  /**
   * 复制到哪个学期
   */
  private Semester toSemester;

  /**
   * 是否复制老师
   */
  private Boolean copyTeacher;

  /**
   * 课程组内的任务所使用的课程都会被替换为这个课程<br>
   * 该值可以为null，如果为null，则任务中的课程保持原样
   */
  private Course replaceCourse;

  public TaskGroupCopyParam() {
    super();
  }

  public TaskGroupCopyParam(Semester toSemester, boolean copyTeacher, Course replaceCourse) {
    super();
    this.toSemester = toSemester;
    this.copyTeacher = copyTeacher;
    this.replaceCourse = replaceCourse;
  }

  public Course getReplaceCourse() {
    return replaceCourse;
  }

  public void setReplaceCourse(Course replaceCourse) {
    this.replaceCourse = replaceCourse;
  }

  public Boolean getCopyTeacher() {
    return copyTeacher;
  }

  public void setCopyTeacher(Boolean copyTeacher) {
    this.copyTeacher = copyTeacher;
  }

  public Semester getToSemester() {
    return toSemester;
  }

  public void setToSemester(Semester toSemester) {
    this.toSemester = toSemester;
  }
}
