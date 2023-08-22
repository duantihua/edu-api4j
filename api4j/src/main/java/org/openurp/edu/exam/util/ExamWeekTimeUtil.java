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
package org.openurp.edu.exam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.beangle.orm.hibernate.udt.WeekDay;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.exam.model.ExamActivity;
import org.openurp.edu.exam.model.ExamRoom;

/**
 * 考务时间辅助类
 */
public class ExamWeekTimeUtil {

  /**
   * 根据考试安排得到考试时间单元
   */
  public static WeekTime getWeekTimeFromActivity(ExamActivity activity) {
    WeekTime unit = WeekTime.of(activity.getExamOn());
    unit.setBeginAt(activity.getBeginAt());
    unit.setEndAt(activity.getEndAt());
    return unit;
  }

  /**
   * 根据考试安排得到考试时间单元
   */
  public static WeekTime getWeekTimeFromActivity(ExamRoom examRoom) {
    WeekTime unit = WeekTime.of(examRoom.getExamOn());
    unit.setBeginAt(examRoom.getBeginAt());
    unit.setEndAt(examRoom.getEndAt());
    return unit;
  }

  /**
   * 根据日期得到星期
   *
   * @param date
   * @return
   */
  public static Integer getWeekDayByDate(Date date) {
    return WeekDay.of(date).getIndex();
  }

  /**
   * 取得当前日期是多少周
   *
   * <pre>
   * FIXME 不规范的时间API
   * 翻译一个日期在一年当中处于第几周
   * 缺陷:
   * 1.2012年有54周，这里最多只会返回53周
   * 2.没有设定一周的第一天是星期几，client code获得的返回结果可能并不符合预期
   * </pre>
   *
   * @param date
   * @return
   */
  public static int getWeekOfYear(Date date) {
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(date);
    if (11 == gc.get(Calendar.MONTH) && 1 == gc.get(Calendar.WEEK_OF_YEAR)) {
      return gc.getActualMaximum(Calendar.WEEK_OF_YEAR) + 1;
    } else {
      return gc.get(Calendar.WEEK_OF_YEAR);
    }
  }

  /**
   * FIXME 不规范的时间API
   *
   * <pre>
   * 获得某个日期相对与学期是第几周
   * 缺陷:
   * 无，可以使用RelativeDateUtil
   * </pre>
   *
   * @param semester
   * @param nowDate
   * @return
   */
  public static int getTeachWeekOfYear(Semester semester, Date nowDate) {
    Calendar start = Calendar.getInstance();
    start.setFirstDayOfWeek(semester.getCalendar().getFirstWeekday().getIndex());
    start.setTime(semester.getBeginOn());
    // start.set(Calendar.DAY_OF_WEEK, semester.getFirstWeekday());
    int weeks = 0;
    while (!start.getTime().after(nowDate)) {
      start.add(Calendar.WEEK_OF_YEAR, 1);
      weeks++;
    }
    return weeks;
  }

  /**
   * FIXME 不规范的时间API
   *
   * <pre>
   * 获得nowDate相对于fromDate是第几周
   * 缺陷:
   * 1. 没有规定一周的第一天是星期几，client code可能获得不想的结果
   * 2. 假设一年有53周，如果遇到2012年的话会有问题
   * </pre>
   *
   * @param fromDate
   *          学期开始日期
   * @param nowDate
   * @return
   */
  @Deprecated
  public static int getTeachWeekOfYear(Date fromDate, Date nowDate) {
    int week = 0;
    int fromYear = fromDate.getYear() + 1900;
    int nowYear = nowDate.getYear() + 1900;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date firstDay = sdf.parse(nowYear + "-01-01");

      int fromWeeks = getWeekOfYear(fromDate);
      int nowWeeks = getWeekOfYear(nowDate);
      int firstDayOfWeek = getWeekDayByDate(firstDay);

      if (nowYear > fromYear) {
        week = 53 - (fromWeeks - 1) + nowWeeks;
        if (firstDayOfWeek != 7) {
          week--;
        }
      } else {
        week = nowWeeks - (fromWeeks - 1);
      }
    } catch (ParseException e) {
      e.printStackTrace();
      throw new RuntimeException("date parse error");
    }
    return week;
  }

}
