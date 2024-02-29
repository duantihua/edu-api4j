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
package org.openurp.base.service;

import java.util.List;

import org.openurp.base.resource.model.Building;
import org.openurp.base.resource.model.Room;

public interface ClassroomService {

  /**
   * 根据指定的教室编码，查询教室的详细信息 如果没有该教室，返回null;
   *
   * @param id
   * @return
   */
  public Room getClassroom(Integer id);

  /**
   * 获取指定校区的教学楼（给DWR使用）
   *
   * @param campusId
   * @return
   */
  public List<Building> getBuildings(Integer campusId);

  /**
   * 保存新的教室信息，如果教室已经存在则抛出异常.
   *
   * @param classroom
   */
  public void saveOrUpdate(Room classroom);

  /**
   * 删除教室信息，慎用. 如果教室id不存在，返回.
   *
   * @param id
   */
  public void removeClassroom(Integer id);
}
