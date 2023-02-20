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
package org.openurp.base.util;

import java.util.Collection;

import org.beangle.orm.hibernate.udt.HourMinute;
import org.beangle.orm.hibernate.udt.WeekState;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.edu.model.WeekTimeBuilder;

public class WeekTimeBuilderTest {

  public static void main(String[] args) {
    java.sql.Date beginOn = java.sql.Date.valueOf("2016-12-25");
    java.sql.Date endOn = java.sql.Date.valueOf("2017-01-04");
    Collection<WeekTime> times = WeekTimeBuilder.build(beginOn, endOn);
    for (WeekTime wt : times) {
      System.out.println(wt);
    }

    WeekTime wrong = new WeekTime();
    wrong.setBeginAt(new HourMinute((short) 1200));
    wrong.setEndAt(new HourMinute((short) 1245));
    wrong.setStartOn(java.sql.Date.valueOf("2017-01-01"));
    wrong.setWeekstate(WeekState.of(new int[]{52,53,54}));
    assert WeekTimeBuilder.needNormalize(wrong);

    WeekTime next = WeekTimeBuilder.normalize(wrong);
    assert null != next;
    assert next.getStartOn().equals(java.sql.Date.valueOf("2018-01-07"));

    System.out.println(wrong);
    System.out.println(next);
  }
}
