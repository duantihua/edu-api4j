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
package org.openurp.edu.clazz.model;

import org.beangle.commons.entity.Component;
import org.beangle.commons.lang.Objects;
import org.beangle.orm.hibernate.udt.WeekState;
import org.hibernate.annotations.*;
import org.openurp.base.edu.model.Classroom;
import org.openurp.code.edu.model.ClassroomType;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 课程安排具体结果
 */
@Embeddable
public class Schedule implements Serializable, Cloneable, Component {

  private static final long serialVersionUID = 3067092503219100019L;

  /**
   * 排课课时
   */
  @NotNull
  private int creditHours;
  /**
   * 周状态
   */
  @NotNull
  @Type(type = "org.beangle.orm.hibernate.udt.WeekStateType")
  private WeekState weekstate = WeekState.Zero;

  /**
   * 具体排课结果
   */
  @OneToMany(mappedBy = "clazz", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private Set<ClazzActivity> activities = new HashSet<ClazzActivity>();

  /**
   * 教室类型
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType roomType;

  /**
   * 院系自助安排
   */
  private boolean departArranged = true;

  public Schedule() {

  }

  /**
   * 克隆课程安排，不克隆activities
   *
   * @return 克隆结果
   */
  public Schedule clone() {
    try {
      Schedule info = (Schedule) super.clone();
      info.setActivities(new HashSet<ClazzActivity>());
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
  public Set<Classroom> getRooms() {
    if (null != getActivities() && !getActivities().isEmpty()) {
      Set<Classroom> rooms = new HashSet<Classroom>();
      for (ClazzActivity session : getActivities()) {
        rooms.addAll(session.getRooms());
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

  public Set<ClazzActivity> getActivities() {
    return activities;
  }

  public void setActivities(Set<ClazzActivity> sessions) {
    this.activities = sessions;
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

  public static float calcWeekHours(float period, int weeks) {
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
  public float getWeekHours() {
    int w = getWeeks();
    if (0 != w) {
      return calcWeekHours(creditHours, w);
    } else {
      return 0;
    }
  }

  public int getFirstWeek() {
    return (null != weekstate) ? weekstate.getFirst() : 0;
  }

  public int getCreditHours() {
    return creditHours;
  }

  public void setCreditHours(int creditHours) {
    this.creditHours = creditHours;
  }

  public ClassroomType getRoomType() {
    return roomType;
  }

  public void setRoomType(ClassroomType roomType) {
    this.roomType = roomType;
  }

  public Status getStatus() {
    if (creditHours == 0) return Status.DONT_ARRANGE;
    else {
      return (getActivities().isEmpty()) ? Status.NEED_ARRANGE : Status.ARRANGED;
    }
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("weekstate", this.weekstate).add("period", this.creditHours)
        .toString();
  }

  public boolean isDepartArranged() {
    return departArranged;
  }

  public void setDepartArranged(boolean departArranged) {
    this.departArranged = departArranged;
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
