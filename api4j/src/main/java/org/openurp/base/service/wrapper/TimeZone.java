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
package org.openurp.base.service.wrapper;

import java.util.List;

import org.beangle.orm.hibernate.udt.WeekDay;
import org.openurp.base.edu.model.CourseUnit;

public class TimeZone {

  private String[] weekStates;

  private List<CourseUnit> units;

  private List<WeekDay> weeks;

  public String[] getWeekStates() {
    return weekStates;
  }

  public void setWeekStates(String[] weekStates) {
    this.weekStates = weekStates;
  }

  public List<CourseUnit> getUnits() {
    return units;
  }

  public void setUnits(List<CourseUnit> units) {
    this.units = units;
  }

  public List<WeekDay> getWeeks() {
    return weeks;
  }

  public void setWeeks(List<WeekDay> weeks) {
    this.weeks = weeks;
  }

}
