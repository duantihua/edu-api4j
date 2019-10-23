/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.beangle.commons.lang.time;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.openurp.base.model.Semester;
import org.openurp.base.time.NumberRangeDigestor;
import org.openurp.base.time.NumberSequence;

public final class WeekStates {

  /**
   * 输入1-8,10-12,14-18双,20-26单，之类的字符串，返回构造出来的WeekState
   *
   * @param str
   * @return
   */
  public static WeekState build(String str) {
    String newstr = Strings.replace(Strings.replace(Strings.replace(str, "，", ","), "－", "-"), "—", "-");
    String[] weekPairs = Strings.split(newstr, ",");

    List<Integer> numbers = CollectUtils.newArrayList();
    for (String weekPair : weekPairs) {
      if (Strings.contains(weekPair, "-")) {
        NumberSequence.Pattern pattern = NumberSequence.Pattern.CONTINUE;
        if (weekPair.indexOf('单') != -1) {
          pattern = NumberSequence.Pattern.ODD;
        } else if (weekPair.indexOf('双') != -1) {
          pattern = NumberSequence.Pattern.EVEN;
        }
        weekPair = weekPair.replaceAll("[^\\d-]", "");
        String startWeek = Strings.substringBefore(weekPair, "-");
        String endWeek = Strings.substringAfter(weekPair, "-");

        if (Numbers.isDigits(startWeek) && Numbers.isDigits(endWeek)) {
          Integer[] nums = NumberSequence.buildInteger(Numbers.toInt(startWeek), Numbers.toInt(endWeek),
              pattern);
          numbers.addAll(Arrays.asList(nums));
        }
      } else {
        if (Numbers.isDigits(weekPair)) {
          numbers.add(Numbers.toInt(weekPair));
        }
      }
    }
    int[] weekResult = new int[numbers.size()];
    for (int i = 0; i < numbers.size(); i++) {
      weekResult[i] = numbers.get(i);
    }
    return WeekState.of(weekResult);
  }

  /**
   * 是否在第53周与下一年重叠
   *
   * @param year
   * @return
   */
  private static boolean shareAt53(int year) {
    String lastDay = year + "-12-31";
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(java.sql.Date.valueOf(lastDay));
    return (gregorianCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY);
  }

  private static int getStartYear(Semester semester) {
    if (null != semester.getBeginOn()) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(semester.getBeginOn());
      return gc.get(Calendar.YEAR);
    }
    return 0;
  }

  public static String digest(WeekState state) {
    if (null == state) return "";
    Integer[] weekIndecies = state.getWeekList().toArray(new Integer[0]);
    String digest = NumberRangeDigestor.digest(weekIndecies, null);
    return digest.replace("[", "").replace("]", "").replace("number.range.odd", "单")
        .replace("number.range.even", "双");
  }

  /**
   * 这个方法都是在ftl里使用的
   *
   * @param state
   * @return
   */
  public static String digestWeekTime(WeekTime time, Semester semester) {
    if (null == time) return "";
    LocalDate beginOn =  semester.getBeginOn().toLocalDate();
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
}
