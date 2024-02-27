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
package org.openurp.edu.room.service;

import java.util.Collection;
import java.util.List;

import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.space.model.Room;

/**
 * 管理所有的牵涉到教室的资源调用,如申请占用\取消占用等
 *
 *
 */
public interface RoomResourceService {

  /**
   * 查询指定范围内的空闲教室
   *
   * @param rooms
   *          (给定教室范围)
   * @param unit
   *          (时间)
   * @return
   */
  public List<Room> getFreeRooms(Collection<Room> rooms, WeekTime unit);

  // FIXME 考试和上课的时间占用放在这两个模块中
  // public void addExamRoomOccupy(WeekTime unit, Room room, Clazz
  // clazz);
  //
  // public void removeExamRoomOccupy(WeekTime unit, Room room, Clazz
  // clazz);
  //
  // /**
  // * 查询指定范围内的空闲教室
  // *
  // * @param departments
  // * 给定部门范围
  // * @param courseTimes
  // * 时间
  // * @param activity
  // * 排课活动
  // * @return
  // */
  // public abstract OqlBuilder<Room> getFreeRoomsOf(List<Department>
  // departments,
  // WeekTime[] courseTimes, Session activity);
  //
  // public OqlBuilder<Room> getFreeRoomsOfConditions(WeekTime[] units);
  //
  // public OqlBuilder<Room> getOccupancyRoomsOf(List<Department>
  // departments, WeekTime[] courseTimes,
  // Session activity);
}
