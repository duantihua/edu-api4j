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
package org.openurp.web.dwr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.entity.metadata.Model;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.base.edu.model.Calendar;

public class SemesterDaoDwrHibernate extends HibernateEntityDao implements SemesterDaoDwr {

  public List getTermsOrderByDistance(Integer calendarId, String year) {
    Calendar calendar = (Calendar) Model.newInstance(Calendar.class);
    calendar.setId(calendarId);
    Map params = new HashMap();
    params.put("calendar", calendar);
    params.put("schoolYear", year);
    List rs = search("@getTermsOrderByDistance", params, true);
    return rs;
  }

  public List getYearsOrderByDistance(Integer calendarId) {
    Calendar calendar = (Calendar) Model.newInstance(Calendar.class);
    calendar.setId(calendarId);
    Map params = new HashMap();
    params.put("calendar", calendar);
    List rawYears = search("@getYearsOrderByDistance", params, true);
    List newYears = new ArrayList();
    Set distinctYears = new HashSet();
    for (Iterator iter = rawYears.iterator(); iter.hasNext();) {
      String schoolYear = (String) iter.next();
      if (!distinctYears.contains(schoolYear)) {
        distinctYears.add(schoolYear);
        newYears.add(schoolYear);
      }
    }
    return newYears;
  }

}
