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
package org.openurp.edu.room.domain;

import java.util.Collection;

import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.resource.model.Room;
import org.openurp.code.edu.model.ActivityType;

public final class OccupyUnit {

  private final Collection<Room> rooms;

  private final WeekTime[] units;

  /** 用途 */
  protected final ActivityType usage;

  /** 使用者 */
  protected final Long userid;

  /** 说明 */
  protected String comment;

  public OccupyUnit(Collection<Room> rooms, WeekTime[] units, ActivityType usage, Long userid) {
    super();
    this.rooms = rooms;
    this.units = units;
    this.usage = usage;
    this.userid = userid;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Collection<Room> getRooms() {
    return rooms;
  }

  public WeekTime[] getUnits() {
    return units;
  }

  public ActivityType getUsage() {
    return usage;
  }

  public Long getUserid() {
    return userid;
  }

}
