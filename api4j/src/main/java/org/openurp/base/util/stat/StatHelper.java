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
package org.openurp.base.util.stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;

/**
 * 统计帮助类
 *
 *
 */
public class StatHelper {

  private EntityDao entityDao;

  public StatHelper(EntityDao entityDao) {
    super();
    this.entityDao = entityDao;
  }

  public StatHelper() {
    super();
  }

  private List setStatEntities(Map statMap, Class entityClass) {
    Collection entities = entityDao.get(entityClass, "id", statMap.keySet());
    for (Iterator iter = entities.iterator(); iter.hasNext();) {
      Entity entity = (Entity) iter.next();
      StatItem stat = (StatItem) statMap.get(entity.getId());
      stat.setWhat(entity);
    }
    return new ArrayList(statMap.values());
  }

  private Map buildStatMap(Collection stats) {
    Map statMap = new HashMap();
    for (Iterator iter = stats.iterator(); iter.hasNext();) {
      StatItem element = (StatItem) iter.next();
      statMap.put(element.getWhat(), element);
    }
    return statMap;
  }

  /**
   * 向只包含id的数组列表中填充实体对象 clazzes[0]对应这data[0]的实体类型
   *
   * @param datas
   * @param clazzes
   */
  public void replaceIdWith(Collection datas, Class[] clazzes) {
    for (Iterator iter = datas.iterator(); iter.hasNext();) {
      Object[] data = (Object[]) iter.next();
      for (int i = 0; i < clazzes.length; i++) {
        if (null == clazzes[i]) {
          continue;
        }
        if (null != data[i]) {
          long id = ((Number) data[i]).longValue();
          data[i] = entityDao.get(clazzes[i], new Long(id).intValue());
        }
      }
    }
  }

  /**
   * 向只包含id的数组列表中填充实体对象 clazzes[0]对应这data[0]的实体类型
   *
   * @param datas
   * @param clazzes
   */
  public static void replaceIdWith(Collection datas, Class[] clazzes, EntityDao entityDao) {
    for (Iterator iter = datas.iterator(); iter.hasNext();) {
      Object[] data = (Object[]) iter.next();
      for (int i = 0; i < clazzes.length; i++) {
        if (null == clazzes[i]) continue;
        if (null != data[i]) {
          long id = ((Number) data[i]).longValue();
          data[i] = entityDao.get(clazzes[i], new Long(id));
        }
      }
    }
  }

  public List setStatEntities(Collection stats, Class entityClass) {
    Map statMap = buildStatMap(stats);
    return setStatEntities(statMap, entityClass);
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
