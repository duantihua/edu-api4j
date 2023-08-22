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
package org.openurp.edu.clazz.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.udt.WeekDay;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.beangle.orm.hibernate.udt.WeekTimes;
import org.beangle.commons.lang.tuple.Pair;
import org.beangle.commons.text.i18n.TextResource;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.edu.model.TimeSetting;
import org.openurp.base.edu.model.WeekTimeBuilder;

/**
 * 输出一组时间的字符串表示
 */
public class WeekTimeDigestor {

  public static final String day = ":day";

  public static final String units = ":units";

  public static final String weeks = ":weeks";

  public static final String time = ":time";

  public static final String defaultFormat = ":day :units [:weeks]";

  private String delimeter = ",";

  private WeekTimeDigestor() {
    super();
  }

  public static WeekTimeDigestor getInstance() {
    return new WeekTimeDigestor();
  }

  /**
   * 根据默认格式 {@link #defaultFormat}格式，获得教学任务里部分排课活动的文字信息
   *
   * @param textResource
   * @param clazz
   * @param times
   * @return
   */
  public String digest(TextResource textResource, Semester semester, TimeSetting timeSetting,
      Collection<WeekTime> times) {
    return digest(textResource, semester, timeSetting, times, defaultFormat);
  }

  /**
   * 根据格式，获得教学任务里部分排课活动的文字信息
   *
   * @param textResource
   * @param clazz
   * @return
   */
  public String digest(TextResource textResource, Semester semester, TimeSetting timeSetting,
      Collection<WeekTime> times, String format) {
    if (CollectUtils.isEmpty(times)) return "";
    if (Strings.isEmpty(format)) format = defaultFormat;

    List<WeekTime> mergedTimes = CollectUtils.newArrayList();
    List<WeekTime> timeList = CollectUtils.newArrayList(times);
    Collections.sort(timeList);
    for (WeekTime weektime : timeList) {
      boolean merged = false;
      for (WeekTime added : mergedTimes) {
        if (WeekTimes.canMergerWith(added, weektime)) {
          WeekTimes.mergeWith(added, weektime);
          merged = true;
        }
      }
      if (!merged) {
        mergedTimes.add(weektime);
      }
    }

    StringBuffer arrangeBuf = new StringBuffer();
    // 合并后的教学活动
    for (WeekTime activity : mergedTimes) {
      arrangeBuf.append(format);
      int replaceStart = arrangeBuf.indexOf(day);
      if (-1 != replaceStart) {
        WeekDay weekday = activity.getWeekday();
        if (null != textResource && textResource.getLocale().getLanguage().equals("en")) {
          arrangeBuf.replace(replaceStart, replaceStart + day.length(), weekday.getEnName() + ".");
        } else {
          arrangeBuf.replace(replaceStart, replaceStart + day.length(), weekday.getName());
        }
      }
      replaceStart = arrangeBuf.indexOf(units);
      if (-1 != replaceStart) {
        Pair<Integer, Integer> rs = timeSetting.getUnitLevel(activity.getBeginAt(), activity.getEndAt());
        arrangeBuf.replace(replaceStart, replaceStart + units.length(), rs.getLeft() + "-" + rs.getRight());
      }
      replaceStart = arrangeBuf.indexOf(time);
      if (-1 != replaceStart) {
        // 如果教学活动中有具体时间
        arrangeBuf.replace(replaceStart, replaceStart + time.length(), activity.getBeginAt().toString() + "-"
            + activity.getEndAt().toString());
      }
      replaceStart = arrangeBuf.indexOf(weeks);
      if (-1 != replaceStart) {
        // 以本年度的最后一周(而不是从教学日历周数计算而来)作为结束周进行缩略.
        // 是因为很多日历指定的周数,仅限于教学使用了.
        arrangeBuf.replace(replaceStart, replaceStart + weeks.length(), WeekTimeBuilder.digestWeekTime(activity, semester)
            + " ");
      }
      arrangeBuf.append(" ").append(delimeter);
    }
    if (arrangeBuf.lastIndexOf(delimeter) != -1) arrangeBuf.delete(arrangeBuf.lastIndexOf(delimeter),
        arrangeBuf.length());
    return arrangeBuf.toString();
  }

  /**
   * 设置分割符，默认为逗号
   *
   * @param delimeter
   * @return
   */
  public WeekTimeDigestor setDelimeter(String delimeter) {
    this.delimeter = delimeter;
    return this;
  }

}
