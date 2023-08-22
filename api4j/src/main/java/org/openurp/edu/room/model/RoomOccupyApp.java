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

import org.beangle.commons.entity.pojo.IntegerIdObject;

import javax.persistence.Entity;

@Entity(name = "org.openurp.edu.room.model.RoomOccupyApp")
public class RoomOccupyApp extends IntegerIdObject {

  public static Integer COURSE = 1;
  public static Integer EXAM = 2;

  private static final long serialVersionUID = -7506294697665394661L;

  private String name;
  private String activityUrl;

  public String getName() {
    return name;
  }

  public RoomOccupyApp() {
    super();
  }

  public RoomOccupyApp(Integer id) {
    super();
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getActivityUrl() {
    return activityUrl;
  }

  public void setActivityUrl(String activityUrl) {
    this.activityUrl = activityUrl;
  }

}
