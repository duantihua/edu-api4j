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

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Teacher;

/**
 * 教师的教材要求偏好<br>
 * 该偏好是指任课教师可以将自己上课较为稳定的要求设定为偏好，填写<br>
 * 任务要求时，如果按照偏好批量设置较为省时省力。<br>
 *
 *
 */
@Deprecated
public class RequirePrefer extends LongIdObject {

  private static final long serialVersionUID = -7335843714667994840L;

  private Teacher teacher;

  private Course course;
  // TODO 暂且不知道如何处理
  // private TaskRequirement require = new TaskRequirement();

  public RequirePrefer() {
  }

  public RequirePrefer(Teacher teacher, Course course) {
    this.teacher = teacher;
    this.course = course;
  }

  /**
   * @return Returns the course.
   */
  public Course getCourse() {
    return course;
  }

  /**
   * @param course
   *          The course to set.
   */
  public void setCourse(Course course) {
    this.course = course;
  }

  /**
   * TODO
   *
   * @return Returns the require.
   */
  /*
   * public TaskRequirement getRequire() {
   * return require;
   * }
   */

  /**
   * TODO
   *
   * @param require
   *          The require to set.
   */
  /*
   * public void setRequire(TaskRequirement require) {
   * this.require = require;
   * }
   */

  /**
   * @return Returns the teacher.
   */
  public Teacher getTeacher() {
    return teacher;
  }

  /**
   * @param teacher
   *          The teacher to set.
   */
  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

}
