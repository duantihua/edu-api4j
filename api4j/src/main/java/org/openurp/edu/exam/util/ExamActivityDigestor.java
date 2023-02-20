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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.udt.WeekDay;
import org.beangle.commons.text.i18n.TextResource;
import org.openurp.base.edu.model.WeekTimeBuilder;
import org.openurp.edu.exam.model.ExamActivity;
import org.openurp.edu.exam.model.ExamRoom;

public class ExamActivityDigestor {

  public static final String singleTeacher = ":teacher1";

  public static final String multiTeacher = ":teacher+";

  public static final String moreThan1Teacher = ":teacher2";

  public static final String day = ":day";

  public static final String date = ":date";

  public static final String units = ":units";

  public static final String weeks = ":weeks";

  public static final String time = ":time";

  public static final String room = ":room";

  public static final String building = ":building";

  public static final String district = ":district";

  public static final String defaultFormat = ":date :time 第:weeks周 :day";

  public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String delimeter = ",";

  private ExamActivityDigestor() {
    super();
  }

  public static ExamActivityDigestor getInstance() {
    return new ExamActivityDigestor();
  }

  public String digest(ExamActivity activity, TextResource resource) {
    return digest(activity, resource, defaultFormat);
  }

  /**
   * 输出教学活动安排
   *
   * @param task
   * @param
   * @param locale
   * @param format
   *          teacher[2|1|+] day units time weeks room building district
   * @return
   */
  public String digest(ExamActivity activity, TextResource resource, String format) {
    if (null == activity) return "";
    if (Strings.isEmpty(format)) {
      format = defaultFormat;
    }
    // int year = SemesterUtil.getStartYear(semester);
    // 本年度以周六结尾
    // boolean endAtSat = SemesterUtil.yearEndAtSat(year);

    // List<ExamActivity> mergedActivities = new ArrayList<ExamActivity>();
    boolean hasRoom = Strings.contains(format, room);
    boolean hasTeacher = Strings.contains(format, "teacher");

    // for (Iterator<ExamActivity> iter = activities.iterator();
    // iter.hasNext();) {
    // // 克隆一个课程安排
    // ExamActivity activity = iter.next();
    // boolean merged = false;
    // for (Iterator<ExamActivity> iterator = mergedActivities.iterator();
    // iterator.hasNext();) {
    // ExamActivity added = iterator.next();
    // if (added.isSameActivityExcept(activity, hasTeacher, hasRoom)) {
    // merged = true;
    // }
    // }
    // if (!merged) {
    // mergedActivities.add((ExamActivity) activity.clone());
    // }
    // }

    StringBuffer arrangeInfoBuf = new StringBuffer();
    // 合并后的教学活动
    for (Iterator<ExamRoom> iter = activity.getRooms().iterator(); iter.hasNext();) {
      ExamRoom examRoom = iter.next();
      arrangeInfoBuf.append(format);
      int replaceStart = 0;
      replaceStart = arrangeInfoBuf.indexOf(day);
      if (-1 != replaceStart) {
        WeekDay weekday = WeekDay.of(activity.getExamOn());
        arrangeInfoBuf.replace(replaceStart, replaceStart + day.length(), weekday.getName());
      }
      replaceStart = arrangeInfoBuf.indexOf(units);
      if (-1 != replaceStart) {
        // 排考没有小节
        // arrangeInfoBuf.replace(replaceStart, replaceStart +
        // units.length(), activity
        // .getRoomOccupation().getTime().getBeginAt()
        // + "-" + activity.getRoomOccupation().getTime().getEndAt());
      }
      replaceStart = arrangeInfoBuf.indexOf(time);
      if (-1 != replaceStart) {
        // 如果教学活动中有具体时间
        arrangeInfoBuf.replace(replaceStart, replaceStart + time.length(),
            activity.getBeginAt().toString() + "-" + activity.getEndAt().toString());
      }
      replaceStart = arrangeInfoBuf.indexOf(date);
      if (-1 != replaceStart) {
        arrangeInfoBuf.replace(replaceStart, replaceStart + date.length(), df.format(activity.getExamOn()));
      }
      replaceStart = arrangeInfoBuf.indexOf(weeks);
      if (-1 != replaceStart) {
        arrangeInfoBuf.replace(replaceStart, replaceStart + weeks.length(),
            WeekTimeBuilder.weekIndexOf(examRoom.getSemester(), activity.getExamOn()) + "");
      }
      replaceStart = arrangeInfoBuf.indexOf(room);
      if (-1 != replaceStart) {
        arrangeInfoBuf.replace(replaceStart, replaceStart + room.length(),
            (null != examRoom.getRoom()) ? examRoom.getRoom().getName() : "");

        replaceStart = arrangeInfoBuf.indexOf(building);
        if (-1 != replaceStart) {
          if (null != examRoom.getRoom() && null != examRoom.getRoom().getBuilding()) {
            arrangeInfoBuf.replace(replaceStart, replaceStart + building.length(),
                examRoom.getRoom().getBuilding().getName());
          } else {
            arrangeInfoBuf.replace(replaceStart, replaceStart + building.length(), "");
          }

        }
        replaceStart = arrangeInfoBuf.indexOf(district);
        if (-1 != replaceStart) {
          if (null != examRoom.getRoom() && null != examRoom.getRoom().getBuilding()
              && null != examRoom.getRoom().getBuilding().getCampus()) {
            arrangeInfoBuf.replace(replaceStart, replaceStart + district.length(),
                examRoom.getRoom().getBuilding().getCampus().getName());
          } else {
            arrangeInfoBuf.replace(replaceStart, replaceStart + district.length(), "");
          }
        }
      }
      arrangeInfoBuf.append(delimeter);
    }
    if (arrangeInfoBuf.lastIndexOf(delimeter) != -1)
      arrangeInfoBuf.delete(arrangeInfoBuf.lastIndexOf(delimeter), arrangeInfoBuf.length());
    return arrangeInfoBuf.toString();
  }

  public ExamActivityDigestor setDelimeter(String delimeter) {
    this.delimeter = delimeter;
    return this;
  }

}
