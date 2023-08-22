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

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Campus;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.edu.model.TimeSetting;
import org.openurp.base.service.TimeSettingService;

import java.util.List;

public class TimeSettingServiceImpl extends BaseServiceImpl implements TimeSettingService {

  public void saveTimeSetting(TimeSetting setting) {
    entityDao.saveOrUpdate(setting);
  }

  public void removeTimeSetting(TimeSetting setting) {
    entityDao.remove(setting);
  }

  public TimeSetting getClosestTimeSetting(Project project, Semester semester, Campus campus) {

    if (null != campus) {
      OqlBuilder settingBuilder = OqlBuilder.hql("select  ts from " + TimeSetting.class.getName()
          + "  ts where ts.project.id=" + project.getId() + " and ts.campus.id=" + campus.getId())
          .cacheable();
      List<TimeSetting> settings = entityDao.search(settingBuilder);
      if (!settings.isEmpty()) return settings.get(0);
    }
    OqlBuilder settingBuilder = OqlBuilder.hql(" from " + TimeSetting.class.getName()
        + "  ts where ts.campus is null and ts.project.id=" + project.getId()).cacheable();
    List<TimeSetting> settings = entityDao.search(settingBuilder);
    if (!settings.isEmpty()) {
      return settings.get(0);
    } else {
      OqlBuilder searchAll = OqlBuilder.hql(" from " + TimeSetting.class.getName()
          + "  ts where ts.project.id=" + project.getId()).cacheable();
      settings = entityDao.search(searchAll);
      if (!settings.isEmpty()) {
        return settings.get(0);
      } else {
        return null;
      }
    }
  }
}
