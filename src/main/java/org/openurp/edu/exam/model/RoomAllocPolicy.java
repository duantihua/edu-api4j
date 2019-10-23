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
package org.openurp.edu.exam.model;

public enum RoomAllocPolicy {

  SameTask("只有一个任务", true, true, true), SameCourse("只有一个课程", false, true, true), SameDepart("只有一个开课院系", false,
      false, true);

  private RoomAllocPolicy(String name, boolean sameTask, boolean sameCourse, boolean sameDepart) {
    this.name = name;
    this.sameTask = sameTask;
    this.sameCourse = sameCourse;
    this.sameDepart = sameDepart;
  }

  private final String name;

  /** 同一个考场内相同任务 */
  private boolean sameTask = true;

  /** 同一个考场内相同课程 */
  private boolean sameCourse = true;

  /** 同一个考场内是否是同一个开课院系 */
  private boolean sameDepart = true;

  public boolean isSameTask() {
    return sameTask;
  }

  public void setSameTask(boolean sameTask) {
    this.sameTask = sameTask;
  }

  public boolean isSameCourse() {
    return sameCourse;
  }

  public void setSameCourse(boolean sameCourse) {
    this.sameCourse = sameCourse;
  }

  public boolean isSameDepart() {
    return sameDepart;
  }

  public void setSameDepart(boolean sameDepart) {
    this.sameDepart = sameDepart;
  }

  public String getName() {
    return name;
  }

}
