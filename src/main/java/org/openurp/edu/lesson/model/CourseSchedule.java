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
package org.openurp.edu.lesson.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.Component;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.time.WeekState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.openurp.code.edu.model.ClassroomType;
import org.openurp.edu.base.model.Classroom;

/**
 * 课程安排具体结果
 */
@Embeddable
public class CourseSchedule implements Serializable, Cloneable, Component {

  private static final long serialVersionUID = 3067092503219100019L;

  /** 排课课时 */
  @NotNull
  private int period;
  /**
   * 周状态
   */
  @NotNull
  @Type(type = "org.beangle.commons.lang.time.hibernate.WeekStateType")
  private WeekState weekstate = WeekState.Zero;

  /** 具体排课结果 */
  @OneToMany(mappedBy = "lesson", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<CourseActivity> activities = new HashSet<CourseActivity>();

  /** 教室类型 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType roomType;

  public CourseSchedule() {

  }

  /**
   * 克隆课程安排，不克隆activities
   *
   * @return 克隆结果
   */
  public CourseSchedule clone() {
    try {
      CourseSchedule info = (CourseSchedule) super.clone();
      info.setActivities(new HashSet<CourseActivity>());
      return info;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 查询课程所安排的教室
   *
   * @return
   */
  public Set<Classroom> getCourseScheduledRooms() {
    if (null != getActivities() && !getActivities().isEmpty()) {
      Set<Classroom> rooms = new HashSet<Classroom>();
      for (CourseActivity activity : getActivities()) {
        rooms.addAll(activity.getRooms());
      }
      return rooms;
    }
    return Collections.emptySet();
  }

  /**
   * @return
   */
  public int getLastWeek() {
    return (null != weekstate) ? weekstate.getLast() : 0;
  }

  public Set<CourseActivity> getActivities() {
    return activities;
  }

  public void setActivities(Set<CourseActivity> activities) {
    this.activities = activities;
  }

  public WeekState getWeekstate() {
    return weekstate;
  }

  public void setWeekstate(WeekState weekstate) {
    this.weekstate = weekstate;
  }

  public int getWeeks() {
    if (null == weekstate) return 0;
    else return weekstate.getWeeks();
  }

  public static float calcWeekHour(float period, int weeks) {
    float wh = period / (float) weeks;
    return reserve(wh, 2);
  }

  private static float reserve(float num, int precision) {
    int mutilply = (int) Math.pow(10, precision + 1);
    num *= mutilply;
    if (num % 10 >= 5) num += 10;
    num -= num % 10;
    return num / mutilply;
  }

  /**
   * 周课时取整方法
   * <ul>
   * <li>0～0.2 取整</li>
   * <li>0.3～0.6 取0.5</li>
   * <li>0.7～0.9 取+1</li>
   * </ul>
   */
  public float getWeekHour() {
    int w = getWeeks();
    if (0 != w) {
      return calcWeekHour(period, w);
    } else {
      return 0;
    }
  }

  public int getFirstWeek() {
    return (null != weekstate) ? weekstate.getFirst() : 0;
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public ClassroomType getRoomType() {
    return roomType;
  }

  public void setRoomType(ClassroomType roomType) {
    this.roomType = roomType;
  }

  public Status getStatus() {
    if (period == 0) return Status.DONT_ARRANGE;
    else {
      return (getActivities().isEmpty()) ? Status.NEED_ARRANGE : Status.ARRANGED;
    }
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("weekstate", this.weekstate).add("period", this.period)
        .toString();
  }

  public enum Status {
    NEED_ARRANGE("待安排"), ARRANGED("已安排"), DONT_ARRANGE("不安排");
    private String description;

    private Status(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }

  }
}
