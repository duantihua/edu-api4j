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
package org.openurp.edu.schedule.helper;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.text.i18n.TextResource;
import org.openurp.base.edu.model.TimeSetting;
import org.openurp.edu.clazz.model.ClazzSession;
import org.openurp.edu.clazz.util.ScheduleDigestor;

/**
 * @author zhouqi 2018年9月27日
 */
public class DigestorHelper {

  private ScheduleDigestor digestor;

  private TextResource textResource;

  private TimeSetting timeSetting;

  public DigestorHelper(TextResource textResource, TimeSetting timeSetting) {
    this.textResource = textResource;
    this.timeSetting = timeSetting;
    digestor = ScheduleDigestor.getInstance();
  }

  public String digest(ClazzSession session, String format) {
    return digest(Collections.singleton(session), format);
  }

  public String digest(Collection<ClazzSession> sessions, String format) {
    return digestor.digest(textResource, timeSetting, sessions,
        StringUtils.isBlank(format) ? ScheduleDigestor.defaultFormat : format);
  }
}
