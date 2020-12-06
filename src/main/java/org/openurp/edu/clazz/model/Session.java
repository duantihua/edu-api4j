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

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.time.WeekTime;
import org.beangle.commons.lang.time.WeekTimes;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.edu.model.Classroom;
import org.openurp.base.edu.model.Teacher;

/**
 * 教学活动
 *
 * @since 2005-11-22
 */
@Entity(name = "org.openurp.edu.clazz.model.Session")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class Session extends LongIdObject implements Comparable<Session> {
  private static final long serialVersionUID = 2498530728105897805L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Clazz clazz;

  /** 上课时间 */
  @Embedded
  protected WeekTime time;

  /** 授课教师列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private Set<Teacher> teachers = CollectUtils.newHashSet();

  /** 教室列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  /** 排课备注 */
  @Size(max = 500)
  private String remark;

  public Session() {
    super();
  }

  /**
   * 第一次活动时间
   *
   * @param calendar
   * @return
   */
  public java.sql.Date getFirstActivityTime() {
    if (null != time) {
      return time.getFirstDay();
    } else {
      return null;
    }
  }

  /**
   * 最后一次活动时间
   *
   * @param calendar
   * @return
   */
  public java.sql.Date getLastActivityTime() {
    if (null != time) {
      return time.getLastDay();
    } else {
      return null;
    }
  }

  public Session(Teacher teacher, Classroom room, WeekTime time) {
    if (teacher != null) {
      getTeachers().add(teacher);
    }
    getRooms().add(room);
    setTime(new WeekTime(time));
  }

  public Object clone() {
    Session session = new Session();
    session.getRooms().addAll(getRooms());
    session.setTime(new WeekTime(getTime()));
    session.setClazz(clazz);
    session.getTeachers().addAll(getTeachers());
    return session;
  }

  /**
   * 判断该教学活动的时间段能否与目标教学活动在[相邻时间段]上合并
   *
   * @param other
   * @return
   */
  public boolean canMergerWith(Session session) {
    if (!getTeachers().equals(session.getTeachers())) return false;
    if (!getRooms().equals(session.getRooms())) return false;
    if ((getRemark() != null && session.getRemark() != null && !getRemark().equals(session.getRemark()))
        || (getRemark() == null && session.getRemark() != null)
        || (session.getRemark() == null && getRemark() != null))
      return false;
    return WeekTimes.canMergerWith(getTime(), session.getTime());
  }

  /**
   * 将两排课活动合并，前提是两活动可以合并
   *
   * @see #canMergerWith(Session)
   * @param other
   */
  public void mergeWith(Session other) {
    WeekTimes.mergeWith(this.getTime(), other.getTime());
  }

  /**
   * 合并在年份和教学周占用上,可以合并的教学活动<br>
   * 合并标准是年份,教学周,教室,教师,星期
   */
  public static List<Session> mergeActivites(List<Session> tobeMerged) {
    List<Session> mergedActivityList = CollectUtils.newArrayList();
    if (CollectUtils.isEmpty(tobeMerged)) return mergedActivityList;
    Collections.sort(tobeMerged);
    Iterator<Session> activityIter = tobeMerged.iterator();
    Session toMerged = activityIter.next();
    mergedActivityList.add(toMerged);
    while (activityIter.hasNext()) {
      Session session = activityIter.next();
      if (toMerged.canMergerWith(session)) toMerged.mergeWith(session);
      else {
        toMerged = session;
        mergedActivityList.add(toMerged);
      }
    }
    return mergedActivityList;
  }

  /**
   * teacher room weekday startUnit weekstate null will be put first,if
   * another is not null
   *
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(Session session) {
    int rs = 0;
    // compare teacher
    if (rs == 0) rs = getTeachers().size() - session.getTeachers().size();
    // compare room
    if (rs == 0) rs = getRooms().size() - session.getRooms().size();
    // compare weeks
    if (rs == 0) rs = getTime().getWeekstate().compareTo(session.getTime().getWeekstate());
    if (rs == 0) rs = getTime().getStartOn().compareTo(session.getTime().getStartOn());
    if (rs == 0) rs = getTime().getBeginAt().value - session.getTime().getBeginAt().value;
    return rs;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return Objects.toStringBuilder(this).add("rooms", this.getRooms()).add("time", getTime())
        .add("teachers", getTeachers()).add("id", this.id).toString();
  }

  /**
   * @return Returns the clazz.
   */
  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public Set<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(Set<Teacher> teachers) {
    this.teachers = teachers;
  }

  public Set<Classroom> getRooms() {
    return rooms;
  }

  public void setRooms(Set<Classroom> rooms) {
    this.rooms = rooms;
  }

  public WeekTime getTime() {
    return time;
  }

  public void setTime(WeekTime time) {
    this.time = time;
  }

  public Date getBeginAt() {
    if (null != time) return time.getFirstDay();
    else return null;
  }

  // FIXME 一定要有单元测试
  public Date getEndAt() {
    return null;
  }

  // FIXME 一定要有单元测试
  public boolean contains(Date oneDay) {
    return false;
  }

  public String getContent() {
    return clazz.getCrn();
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
