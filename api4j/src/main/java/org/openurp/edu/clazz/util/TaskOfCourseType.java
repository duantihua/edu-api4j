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
package org.openurp.edu.clazz.util;

import org.openurp.code.edu.model.CourseType;
import org.openurp.base.std.model.Squad;

/**
 * 对应计划中没有课程的课程组"生成"的"任务".<br>
 * 由于计划中的类似"限定选修模块课"等课程组,仅仅规定了课程的开课学期和学分,周课时.<br>
 * 其中并没有任何课程可言,但是为了知道计划对应的班级每学期应该上哪些选修课以备开课,<br>
 * 故此采用该类以代表,但并不存储.<br>
 * 他和教学任务共同组成了班级的"开课情况表"<br>
 * 在此意义上,TeachTask 可以称之为TaskOfCourse<br>
 *
 *
 */
public class TaskOfCourseType {

  private CourseType courseType;

  private Squad squad;

  private Float credits;

  public TaskOfCourseType() {
    super();
  }

  public TaskOfCourseType(CourseType courseType, Squad adminClass, Float credit) {
    this.courseType = courseType;
    this.squad = adminClass;
    this.credits = credit;
  }

  public Squad getSquad() {
    return squad;
  }

  public void setSquad(Squad adminClass) {
    this.squad = adminClass;
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public Float getCredits() {
    return credits;
  }

  public void setCredits(Float credit) {
    this.credits = credit;
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return ((null == courseType) ? "null" : courseType.getName()) + " "
        + ((null == squad) ? "null" : squad.getName());
  }
}
