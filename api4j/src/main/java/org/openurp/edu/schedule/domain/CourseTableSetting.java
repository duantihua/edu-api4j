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
package org.openurp.edu.schedule.domain;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.orm.hibernate.udt.WeekDay;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.edu.model.Semester;

/**
 * 课程表打印设置
 *
 *
 */
public class CourseTableSetting extends LongIdObject {

  private static final long serialVersionUID = 1L;

  public static final String VERTICAL = "vertical";

  public static final String HORIZONTAL = "horizontal";

  public static final String ALLINONE = "single";

  /**
   * 缺省每页的课表个数
   */
  private int tablePerPage = 1;

  /**
   * 课表中的像素数字大小
   */
  private int fontSize = 12;

  /**
   * 课表类型
   */
  private String style = HORIZONTAL;

  /**
   * 课表类型
   */
  private String kind;

  /**
   * 课程表发生的日历
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /**
   * 一周内要打印的星期
   */
  private List<WeekDay> weekdays;

  /**
   * 是否显示教学日历的中的时间设置
   */
  private boolean displaySemesterTime;

  /**
   * 课程表的数据是否局限于给定的日历,否则查找与日历时间重叠的其他日历的课程
   */
  private boolean forSemester = true;

  /**
   * 打印时是否不打印教学任务列表
   */
  private boolean ignoreTask = false;

  private WeekTime[] times;

  private String orderBy;

  public CourseTableSetting() {
    super();
  }

  public CourseTableSetting(Semester semester) {
    setSemester(semester);
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public boolean getDisplaySemesterTime() {
    return displaySemesterTime;
  }

  public void setDisplaySemesterTime(boolean displaySemesterTime) {
    this.displaySemesterTime = displaySemesterTime;
  }

  public List<WeekDay> getWeekdays() {
    return weekdays;
  }

  public void setWeekdays(List<WeekDay> weekdays) {
    this.weekdays = weekdays;
  }

  public boolean getForSemester() {
    return forSemester;
  }

  public void setForSemester(boolean forSemester) {
    this.forSemester = forSemester;
  }

  public boolean getIgnoreTask() {
    return ignoreTask;
  }

  public void setIgnoreTask(boolean ignoreTask) {
    this.ignoreTask = ignoreTask;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public WeekTime[] getTimes() {
    return times;
  }

  public void setTimes(WeekTime[] times) {
    this.times = times;
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public int getTablePerPage() {
    return tablePerPage;
  }

  public void setTablePerPage(int tablePerPage) {
    this.tablePerPage = tablePerPage;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

}
