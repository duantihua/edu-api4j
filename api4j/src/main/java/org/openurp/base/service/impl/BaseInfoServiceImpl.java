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

import java.lang.reflect.Method;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.BaseInfo;
import org.openurp.base.model.School;
import org.openurp.base.service.BaseInfoService;

public class BaseInfoServiceImpl extends BaseServiceImpl implements BaseInfoService {

  /**
   * FIXME 这个类的调用因该仅限于基础信息
   */
  public List getBaseInfos(Class clazz, School school) {
    if (BaseInfo.class.isAssignableFrom(clazz) || likeBaseInfo(clazz)) {
      OqlBuilder builder = OqlBuilder.from(clazz, "baseInfo")
          .where("baseInfo.beginOn <= :now and (baseInfo.endOn is null or baseInfo.endOn >= :now)",
              new java.util.Date())
          .where("baseInfo.school=:school",school)
          .orderBy("baseInfo.code");
      builder.cacheable(true);
      return entityDao.search(builder);
    } else {
      throw new RuntimeException(clazz.getName() + " is not a baseInfo ");
    }
  }

  private boolean likeBaseInfo(Class clazz) {
    Method[] methods = clazz.getDeclaredMethods();
    boolean hasBeginOn = false;
    boolean hasEndOn = false;
    boolean hasCode = false;
    for (Method method : methods) {
      if ("getBeginOn".equals(method.getName())) {
        hasBeginOn = true;
      } else if ("getEndOn".equals(method.getName())) {
        hasEndOn = true;
      } else if ("getCode".equals(method.getName())) {
        hasCode = true;
      }
    }
    return hasBeginOn && hasCode && hasEndOn;
  }

  public BaseInfo getBaseInfo(Class clazz, Integer id) {
    OqlBuilder query = OqlBuilder.from(clazz, "info");
    query.where("info.id=:infoId", id);
    List rs = entityDao.search(query);
    if (!rs.isEmpty()) return (BaseInfo) rs.get(0);
    else return null;
  }

}
