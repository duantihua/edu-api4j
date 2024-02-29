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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.beangle.orm.hibernate.udt.WeekTimes;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.resource.model.Classroom;
import org.openurp.base.hr.model.Teacher;
import org.openurp.code.edu.model.TeachingMethod;
import org.openurp.code.edu.model.TeachingNature;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * 教学活动
 *
 * @since 2005-11-22
 */
@Entity(name = "org.openurp.edu.clazz.model.ClazzActivity")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class ClazzActivity extends LongIdObject implements Comparable<ClazzActivity> {
  private static final long serialVersionUID = 2498530728105897805L;

  /**
   * 教学任务
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Clazz clazz;

  /**
   * 上课时间
   */
  @Embedded
  protected WeekTime time;

  /**
   * 授课教师列表
   */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  @JoinTable(name="clazz_activities_teachers",joinColumns={@JoinColumn(name="activity_id")},inverseJoinColumns={@JoinColumn(name="teacher_id")})
  private Set<Teacher> teachers = CollectUtils.newHashSet();

  /**
   * 教室列表
   */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  @JoinTable(name="clazz_activities_rooms",joinColumns={@JoinColumn(name="activity_id")},inverseJoinColumns={@JoinColumn(name="classroom_id")})
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  /**
   * 排课备注
   */
  @Size(max = 500)
  private String places;

  /**
   * 授课性质
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  protected TeachingNature teachingNature = new TeachingNature(1);

  /**
   * 授课方式
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  protected TeachingMethod teachingMethod = new TeachingMethod(1);

  @ManyToOne(fetch = FetchType.LAZY)
  private Subclazz subclazz;

  private short beginUnit;
  private short endUnit;

  public ClazzActivity() {
    super();
  }

  /**
   * 第一次活动时间
   *
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
   * @return
   */
  public java.sql.Date getLastActivityTime() {
    if (null != time) {
      return time.getLastDay();
    } else {
      return null;
    }
  }

  public ClazzActivity(Teacher teacher, Classroom room, WeekTime time) {
    if (teacher != null) {
      getTeachers().add(teacher);
    }
    getRooms().add(room);
    setTime(new WeekTime(time));
  }

  public Object clone() {
    ClazzActivity session = new ClazzActivity();
    session.getRooms().addAll(getRooms());
    session.setTime(new WeekTime(getTime()));
    session.setClazz(clazz);
    session.getTeachers().addAll(getTeachers());
    return session;
  }

  /**
   * 判断该教学活动的时间段能否与目标教学活动在[相邻时间段]上合并
   *
   * @return
   */
  public boolean canMergerWith(ClazzActivity session) {
    if (!Objects.equals(getTeachingNature(), session.getTeachingNature())) {
      return false;
    }
    if (!Objects.equals(getTeachingMethod(), session.getTeachingMethod())) {
      return false;
    }

    if (!getTeachers().equals(session.getTeachers())) {
      //时间地点一致就合并
      if (getTime().equals(session.getTime()) && getRooms().equals(session.getRooms())
          && Objects.equals(getPlaces(), session.getPlaces())) {
        return true;
      } else {
        return false;
      }
    }
    if (!getRooms().equals(session.getRooms())) {
      //时间和人员一致就合并
      if (getTime().equals(session.getTime()) && getTeachers().equals(session.getTeachers())) {
        return true;
      } else {
        return false;
      }
    }
    if ((getPlaces() != null && session.getPlaces() != null && !getPlaces().equals(session.getPlaces()))
        || (getPlaces() == null && session.getPlaces() != null)
        || (session.getPlaces() == null && getPlaces() != null))
      return false;
    if (!Objects.equals(getSubclazz(), session.getSubclazz())) {
      return false;
    }
    return WeekTimes.canMergerWith(getTime(), session.getTime());
  }

  /**
   * 将两排课活动合并，前提是两活动可以合并
   *
   * @param other
   * @see #canMergerWith(ClazzActivity)
   */
  public void mergeWith(ClazzActivity other) {
    getTeachers().addAll(other.getTeachers());
    getRooms().addAll(other.getRooms());
    WeekTimes.mergeWith(this.getTime(), other.getTime());
  }

  /**
   * 合并在年份和教学周占用上,可以合并的教学活动<br>
   * 合并标准是年份,教学周,教室,教师,星期
   */
  public static List<ClazzActivity> mergeActivites(List<ClazzActivity> tobeMerged) {
    List<ClazzActivity> mergedActivityList = CollectUtils.newArrayList();
    if (CollectUtils.isEmpty(tobeMerged)) return mergedActivityList;
    Collections.sort(tobeMerged);
    Iterator<ClazzActivity> activityIter = tobeMerged.iterator();
    ClazzActivity toMerged = activityIter.next();
    mergedActivityList.add(toMerged);
    while (activityIter.hasNext()) {
      ClazzActivity session = activityIter.next();
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
  public int compareTo(ClazzActivity session) {
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

  public String getPlaces() {
    return places;
  }

  public void setPlaces(String places) {
    this.places = places;
  }

  public TeachingNature getTeachingNature() {
    return teachingNature;
  }

  public void setTeachingNature(TeachingNature teachingNature) {
    this.teachingNature = teachingNature;
  }

  public TeachingMethod getTeachingMethod() {
    return teachingMethod;
  }

  public void setTeachingMethod(TeachingMethod teachingMethod) {
    this.teachingMethod = teachingMethod;
  }

  public Subclazz getSubclazz() {
    return subclazz;
  }

  public void setSubclazz(Subclazz subclazz) {
    this.subclazz = subclazz;
  }

  public short getBeginUnit() {
    return beginUnit;
  }

  public void setBeginUnit(short beginUnit) {
    this.beginUnit = beginUnit;
  }

  public short getEndUnit() {
    return endUnit;
  }

  public void setEndUnit(short endUnit) {
    this.endUnit = endUnit;
  }
}
