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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.QueryBuilder;
import org.beangle.commons.lang.BitStrings;
import org.beangle.orm.hibernate.udt.WeekDay;
import org.openurp.base.edu.model.CourseUnit;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.service.OccupyProcessor;
import org.openurp.base.service.StdOccupyProvider;
import org.openurp.base.service.wrapper.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractStdOccupyProvider implements StdOccupyProvider {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  protected EntityDao entityDao;

  protected Semester semester;

  public void setEntityService(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected Map executeOccupyQuery(QueryBuilder query, TimeZone zone, OccupyProcessor processor) {
    Long st = System.currentTimeMillis();
    Map params = query.getParams();
    if (null == params) {
      params = new HashMap();
    }
    Map occupis = new HashMap();
    for (Iterator iter = zone.getWeeks().iterator(); iter.hasNext();) {
      WeekDay week = (WeekDay) iter.next();
      Map weekOccupy = new HashMap();
      for (Iterator iter2 = zone.getUnits().iterator(); iter2.hasNext();) {
        CourseUnit unit = (CourseUnit) iter2.next();
        for (String weekState : zone.getWeekStates()) {
          params.put("weekId", new Integer(week.getId()));
          params.put("startTime", unit.getBeginAt());
          params.put("endTime", unit.getEndAt());
          params.put("weekState", BitStrings.binValueOf(weekState));
          query.params(params);
          List datas = entityDao.search(query);
          if (!datas.isEmpty()) {
            processor.process(weekOccupy, unit, datas);
          }
        }
      }
      if (!weekOccupy.isEmpty()) {
        occupis.put(week, weekOccupy);
      }
    }
    logger.info("occupy query consume time {} millis", System.currentTimeMillis() - st);
    return occupis;
  }
}
