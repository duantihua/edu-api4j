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
package org.openurp.base.edu.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.bean.comparators.PropertyComparator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.time.HourMinute;
import org.beangle.commons.lang.time.WeekDay;
import org.beangle.commons.lang.time.WeekState;
import org.beangle.commons.lang.time.WeekTime;
import org.beangle.commons.lang.time.Weeks;
import org.beangle.commons.lang.tuple.Pair;
import org.openurp.base.time.NumberRangeDigestor;
import org.openurp.base.time.NumberSequence;

public class WeekTimeBuilder {

  private final LocalDate startOn;

  private final LocalDate firstWeekEndOn;

  private WeekTimeBuilder(LocalDate startOn, WeekDay firstDay) {
    super();
    this.startOn = startOn;
    LocalDate endOn = startOn;
    WeekDay weekendDay = firstDay.previous();
    while (endOn.getDayOfWeek().getValue() != weekendDay.getId()) {
      endOn = endOn.plusDays(1);
    }
    firstWeekEndOn = endOn;
  }

  public static WeekTimeBuilder on(Semester semester) {
    return new WeekTimeBuilder(semester.getBeginOn().toLocalDate(), semester.getCalendar().getFirstWeekday());
  }

  /**
   * 这个方法都是在ftl里使用的
   *
   * @param state
   * @return
   */
  public static String digestWeekTime(WeekTime time, Semester semester) {
    if (null == time) return "";
    LocalDate beginOn = semester.getBeginOn().toLocalDate();
    int firstWeekday = beginOn.getDayOfWeek().getValue();
    LocalDate timeBeginOn = time.getStartOn().toLocalDate();
    while (timeBeginOn.getDayOfWeek().getValue() != firstWeekday) {
      timeBeginOn = timeBeginOn.plusDays(-1);
    }
    int weeksDistance = Weeks.between(beginOn, timeBeginOn);
    long weekstate = time.getWeekstate().getValue();
    if (weeksDistance < 0) {
      weekstate >>= (0 - weeksDistance);
    } else {
      weekstate <<= weeksDistance;
    }

    Integer[] weekIndecies = new WeekState(weekstate).getWeekList().toArray(new Integer[0]);
    String digest = NumberRangeDigestor.digest(weekIndecies, null);
    return digest.replace("[", "").replace("]", "").replace("number.range.odd", "单")
        .replace("number.range.even", "双");
  }

  public static List<WeekTime> build(java.sql.Date beginOn, java.sql.Date endOn) {
    Map<java.sql.Date, WeekTime> timeMap = CollectUtils.newHashMap();
    java.util.Calendar c = Calendar.getInstance();
    java.util.Calendar e = Calendar.getInstance();
    e.setTime(endOn);
    c.setTime(beginOn);
    while (!c.after(e)) {
      WeekTime t = WeekTime.of(new java.sql.Date(c.getTime().getTime()));
      WeekTime existed = timeMap.get(t.getStartOn());
      if (null == existed) {
        timeMap.put(t.getStartOn(), t);
      } else {
        existed.setWeekstate(existed.getWeekstate().bitor(t.getWeekstate()));
      }
      c.add(Calendar.DAY_OF_YEAR, 1);
    }
    List<WeekTime> times = CollectUtils.newArrayList(timeMap.values());
    Collections.sort(times, new PropertyComparator("startOn"));
    return times;
  }

  public List<WeekTime> buildOnOldWeekStr(WeekDay weekday, String weekstr) {
    List<Integer> weekList = new ArrayList<Integer>();
    for (int i = 0; i < weekstr.length(); i++) {
      if (weekstr.charAt(i) == '1') weekList.add(i);
    }
    int[] weeks = new int[weekList.size()];
    for (int i = 0; i < weekList.size(); i++) {
      weeks[i] = weekList.get(i);
    }
    return build(weekday, weeks);
  }

  public static boolean needNormalize(WeekTime wt) {
    int startYear = wt.getStartYear();
    java.sql.Date lastDay = wt.getLastDay();
    return startYear != lastDay.getYear() + 1900;
  }

  public static WeekTime normalize(WeekTime wt) {
    int startYear = wt.getStartYear();
    java.sql.Date lastDay = wt.getLastDay();
    WeekTime nextWt = null;
    while (startYear != lastDay.getYear() + 1900) {
      if (null == nextWt) {
        nextWt = new WeekTime();
        nextWt.setBeginAt(wt.getBeginAt());
        nextWt.setEndAt(wt.getEndAt());
        nextWt.setStartOn(
            java.sql.Date.valueOf(WeekTime.getStartOn(lastDay.getYear() + 1900, wt.getWeekday())));
        nextWt.setWeekstate(WeekState.Zero);
      }
      wt.dropDay(lastDay);
      nextWt.addDay(lastDay);
      lastDay = wt.getLastDay();
    }
    return nextWt;
  }

  public static int getOffset(Semester semester, WeekDay weekday) {
    LocalDate startOn = semester.getBeginOn().toLocalDate();
    while (startOn.getDayOfWeek().getValue() != weekday.getId()) {
      startOn = startOn.plusDays(1);
    }
    LocalDate yearStartOn = WeekTime.getStartOn(startOn.getYear(), weekday);
    return Weeks.between(yearStartOn, startOn);
  }

  public static int getReverseOffset(Semester semester, WeekDay weekday) {
    LocalDate startOn = semester.getBeginOn().toLocalDate();
    while (startOn.getDayOfWeek().getValue() != weekday.getId()) {
      startOn = startOn.plusDays(1);
    }
    LocalDate yearStartOn = WeekTime.getStartOn(startOn.getYear() + 1, weekday);
    return Math.abs(Weeks.between(yearStartOn, startOn));
  }

  public List<WeekTime> build(WeekDay weekday, Collection<Integer> weeks) {
    int[] weekIndices = new int[weeks.size()];
    int i = 0;
    for (Integer w : weeks) {
      weekIndices[i] = w;
      i += 1;
    }
    return build(weekday, weekIndices);
  }

  public List<WeekTime> build(WeekDay weekday, int[] weeks) {
    Map<Integer, WeekTime> times = new HashMap<Integer, WeekTime>();
    LocalDate startDate = startOn;
    while (startDate.getDayOfWeek().getValue() != weekday.getId()) {
      startDate = startDate.plusDays(1);
    }
    int minWeek = 1;
    if (startDate.isAfter(firstWeekEndOn)) minWeek = 2;

    for (int week : weeks) {
      if (week < minWeek) continue;
      LocalDate oneday = startDate.plusWeeks(week - 1);
      int year = oneday.getYear();
      WeekTime weektime = times.get(year);
      LocalDate yearStartOn = WeekTime.getStartOn(year, weekday);
      if (null == weektime) {
        weektime = new WeekTime();
        times.put(year, weektime);
        weektime.setStartOn(java.sql.Date.valueOf(yearStartOn));
        weektime.setWeekstate(new WeekState(0));
      }
      weektime.setWeekstate(new WeekState(
          weektime.getWeekstate().value | WeekState.of(Weeks.between(yearStartOn, oneday) + 1).value));
    }
    return new ArrayList<WeekTime>(times.values());
  }

  public static List<java.sql.Date> getYearStartOns(Semester semester, WeekDay weekday) {
    int year = semester.getStartYear();
    List<java.sql.Date> dates = CollectUtils.newArrayList();
    dates.add(java.sql.Date.valueOf(WeekTime.getStartOn(year, weekday)));
    dates.add(java.sql.Date.valueOf(WeekTime.getStartOn(year, weekday)));
    return dates;
  }

  public static java.sql.Date getStartOn(Semester semester, WeekDay weekday) {
    LocalDate ld = semester.getBeginOn().toLocalDate();
    while (ld.getDayOfWeek().getValue() != weekday.getId()) {
      ld = ld.plusDays(1);
    }
    return java.sql.Date.valueOf(ld);
  }

  public static WeekTime of(int startWeek, int endWeek, NumberSequence.Pattern pattern) {
    int[] range = NumberSequence.build(startWeek, endWeek, pattern);
    WeekTime courseTime = new WeekTime();
    courseTime.setWeekstate(WeekState.of(range));
    return courseTime;
  }

  /**
   * 构造某个日期（beginAt, endAt必须是同一天，只是时间不同）的WeekTime
   *
   * @param beginAt
   * @param endAt
   * @return
   */
  public static WeekTime of(java.sql.Date startOn, HourMinute beginAt, HourMinute endAt) {
    WeekTime time = WeekTime.of(startOn);
    time.setBeginAt(beginAt);
    time.setEndAt(endAt);
    return time;
  }

  public static Pair<java.sql.Date, java.sql.Date> getDateRange(Semester semester, int weekIndex) {
    java.sql.Date beginOn = WeekTimeBuilder.on(semester)
        .build(semester.getCalendar().getFirstWeekday(), new int[] { weekIndex }).get(0).getFirstDay();
    LocalDate ld = beginOn.toLocalDate();
    ld = ld.plusDays(6);
    return Pair.of(beginOn, java.sql.Date.valueOf(ld));
  }

  public static java.sql.Date getDate(Semester semester, int teachWeek, WeekDay weekday) {
    return WeekTimeBuilder.on(semester).build(weekday, new int[] { teachWeek }).get(0).getFirstDay();
  }

  public static int weekIndexOf(Semester semester, java.sql.Date oneday) {
    LocalDate beginOn = semester.getBeginOn().toLocalDate();
    int firstWeekday = beginOn.getDayOfWeek().getValue();
    LocalDate timeBeginOn = oneday.toLocalDate();
    while (timeBeginOn.getDayOfWeek().getValue() != firstWeekday) {
      timeBeginOn = timeBeginOn.plusDays(-1);
    }
    return Weeks.between(beginOn, timeBeginOn);
  }

  public static int getStartYear(Semester semester) {
    if (null != semester.getBeginOn()) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(semester.getBeginOn());
      return gc.get(Calendar.YEAR);
    }
    return 0;
  }

  public static WeekDay[] getWeekDays(Semester semester) {
    boolean isSundayFirst = semester.getCalendar().getFirstWeekday().equals(WeekDay.Sun);
    return WeekDay.getWeekdayArray(isSundayFirst);
  }

}
