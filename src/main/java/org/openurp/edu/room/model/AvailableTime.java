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
package org.openurp.edu.room.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.time.WeekTime;
import org.openurp.base.edu.model.Classroom;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;

import javax.persistence.Entity;

/**
 * 可用
 */
@Entity(name = "org.openurp.edu.room.model.AvailableTime")
public class AvailableTime extends LongIdObject {

  private Project project;

  /**
   * 教室
   */
  private Classroom room;

  /**
   * 时间
   */
  private WeekTime time = new WeekTime();// 时间安排

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Classroom getRoom() {
    return room;
  }

  public void setRoom(Classroom room) {
    this.room = room;
  }

  public WeekTime getTime() {
    return time;
  }

  public void setTime(WeekTime time) {
    this.time = time;
  }
}
