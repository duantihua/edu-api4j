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
package org.openurp.edu.room.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.space.model.Room;
import org.openurp.edu.room.service.RoomResourceService;

public class RoomResourceServiceImpl extends BaseServiceImpl implements RoomResourceService {

  public List<Room> getFreeRooms(Collection<Room> rooms, WeekTime unit) {
    OqlBuilder<Room> query = OqlBuilder.from(Room.class, "classroom");
    query.where(
        "not exists(select 1 from org.openurp.edu.room.model.Occupancy occupancy where occupancy.room = classroom.room "
            + "and (occupancy.time.startOn = :startOn) " + "and (occupancy.time.weekstate = :weekState) "
            + "and (:startTime <= occupancy.time.endAt and :endTime > occupancy.time.beginAt)" + ")");
    if (!CollectUtils.isEmpty(rooms)) {
      query.where("classroom in (:rooms)");
    }
    query.where("classroom.beginOn <= :now and (classroom.endOn is null or classroom.endOn >= :now)");
    query.orderBy("classroom.code");

    Map<String, Object> params = CollectUtils.newHashMap();
    params.put("startOn", unit.getStartOn());
    params.put("weekState", unit.getWeekstate());
    params.put("startTime", unit.getBeginAt().value);
    params.put("endTime", unit.getEndAt().value);
    if (!CollectUtils.isEmpty(rooms)) {
      params.put("rooms", rooms);
    }
    params.put("now", new Date());
    query.params(params);

    return entityDao.search(query);
  }
}
