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
package org.openurp.base.service.impl;

import java.sql.Date;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.base.resource.model.Building;
import org.openurp.base.resource.model.Room;
import org.openurp.base.service.ClassroomService;

public class ClassroomServiceImpl extends BaseServiceImpl implements ClassroomService {

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.ClassroomService#getClassroom(java.lang.Long)
   */
  public Room getClassroom(Integer id) {
    return entityDao.get(Room.class, id);
  }

  public List<Building> getBuildings(Integer campusId) {
    return entityDao.get(Building.class, "campus.id", campusId);
  }

  /**
   * @see org.openurp.base.edu.service.service.system.baseinfo.ClassroomService#removeClassroom(java.lang.String)
   */
  public void removeClassroom(Integer id) {
    if (null == id) return;
    entityDao.remove(entityDao.get(Room.class, id));
  }

  public void saveOrUpdate(Room classroom) {
    classroom.setUpdatedAt(new Date(System.currentTimeMillis()));
    this.entityDao.saveOrUpdate(classroom);
  }

}
