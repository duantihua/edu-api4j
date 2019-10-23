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
package org.openurp.edu.lesson.model;

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
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.base.model.Teacher;

/**
 * 教学活动
 *
 * @since 2005-11-22
 */
@Entity(name = "org.openurp.edu.lesson.model.CourseActivity")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseActivity extends LongIdObject implements Comparable<CourseActivity>, Activity {
  private static final long serialVersionUID = 2498530728105897805L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Lesson lesson;

  /** 上课时间 */
  @Embedded
  protected WeekTime time;

  /** 授课教师列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Teacher> teachers = CollectUtils.newHashSet();

  /** 教室列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  /** 排课备注 */
  @Size(max = 500)
  private String remark;

  public CourseActivity() {
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

  public CourseActivity(Teacher teacher, Classroom room, WeekTime time) {
    if (teacher != null) {
      getTeachers().add(teacher);
    }
    getRooms().add(room);
    setTime(new WeekTime(time));
  }

  public Object clone() {
    CourseActivity activity = new CourseActivity();
    activity.getRooms().addAll(getRooms());
    activity.setTime(new WeekTime(getTime()));
    activity.setLesson(lesson);
    activity.getTeachers().addAll(getTeachers());
    return activity;
  }

  /**
   * 判断该教学活动的时间段能否与目标教学活动在[相邻时间段]上合并
   *
   * @param other
   * @return
   */
  public boolean canMergerWith(CourseActivity activity) {
    if (!getTeachers().equals(activity.getTeachers())) return false;
    if (!getRooms().equals(activity.getRooms())) return false;
    if ((getRemark() != null && activity.getRemark() != null && !getRemark().equals(activity.getRemark()))
        || (getRemark() == null && activity.getRemark() != null)
        || (activity.getRemark() == null && getRemark() != null)) return false;
    return WeekTimes.canMergerWith(getTime(), activity.getTime());
  }

  /**
   * 将两排课活动合并，前提是两活动可以合并
   *
   * @see #canMergerWith(CourseActivity)
   * @param other
   */
  public void mergeWith(CourseActivity other) {
    WeekTimes.mergeWith(this.getTime(), other.getTime());
  }

  /**
   * 合并在年份和教学周占用上,可以合并的教学活动<br>
   * 合并标准是年份,教学周,教室,教师,星期
   */
  public static List<CourseActivity> mergeActivites(List<CourseActivity> tobeMerged) {
    List<CourseActivity> mergedActivityList = CollectUtils.newArrayList();
    if (CollectUtils.isEmpty(tobeMerged)) return mergedActivityList;
    Collections.sort(tobeMerged);
    Iterator<CourseActivity> activityIter = tobeMerged.iterator();
    CourseActivity toMerged = activityIter.next();
    mergedActivityList.add(toMerged);
    while (activityIter.hasNext()) {
      CourseActivity activity = activityIter.next();
      if (toMerged.canMergerWith(activity)) toMerged.mergeWith(activity);
      else {
        toMerged = activity;
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
  public int compareTo(CourseActivity activity) {
    int rs = 0;
    // compare teacher
    if (rs == 0) rs = getTeachers().size() - activity.getTeachers().size();
    // compare room
    if (rs == 0) rs = getRooms().size() - activity.getRooms().size();
    // compare weeks
    if (rs == 0) rs = getTime().getWeekstate().compareTo(activity.getTime().getWeekstate());
    if (rs == 0) rs = getTime().getStartOn().compareTo(activity.getTime().getStartOn());
    if (rs == 0) rs = getTime().getBeginAt().value - activity.getTime().getBeginAt().value;
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
   * @return Returns the lesson.
   */
  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
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
    return lesson.getNo();
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
