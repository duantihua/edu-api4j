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
package org.openurp.edu.clazz.app.model;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.beangle.orm.hibernate.udt.WeekTimes;

public class CollisionInfo {

  private Object resource;

  private List<WeekTime> times = CollectUtils.newArrayList();

  private String reason;

  public CollisionInfo() {
    super();
  }

  public CollisionInfo(Object resource, WeekTime time) {
    this.resource = resource;
    WeekTime newTime = new WeekTime(time);
    this.times.add(newTime);
  }

  public CollisionInfo(Object resource, WeekTime time, String reason) {
    super();
    this.resource = resource;
    WeekTime newTime = new WeekTime(time);
    this.times.add(newTime);
    this.reason = reason;
  }

  public void add(WeekTime time) {
    WeekTime newTime = new WeekTime(time);
    if (!times.contains(newTime)) {
      this.times.add(newTime);
    }
  }

  public Object getResource() {
    return resource;
  }

  public void setResource(Object resource) {
    this.resource = resource;
  }

  public List<WeekTime> getTimes() {
    return times;
  }

  public void setTimes(List<WeekTime> times) {
    this.times = times;
  }

  public void mergeTimes() {
    setTimes(WeekTimes.mergeTimes(getTimes()));
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

}
