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
import org.openurp.base.resource.model.Classroom;
import org.openurp.base.edu.model.CourseUnit;
import org.openurp.base.hr.model.Teacher;
import org.openurp.base.edu.model.TimeSetting;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 建议教学活动
 */
@Entity(name = "org.openurp.edu.clazz.model.ScheduleSuggestActivity")
public class ScheduleSuggestActivity extends LongIdObject implements Comparable<ScheduleSuggestActivity> {
  private static final long serialVersionUID = 2498530728105897805L;

  /**
   * 教学任务
   */
  @NotNull
  @ManyToOne(targetEntity = ScheduleSuggest.class)
  protected ScheduleSuggest suggest;

  /**
   * 上课时间
   */
  @Embedded
  protected WeekTime time;

  /**
   * 授课教师列表
   */
  @ManyToOne(targetEntity = ScheduleSuggest.class)
  private Teacher teacher;

  private Classroom room;

  public ScheduleSuggestActivity() {
    super();
  }

  public ScheduleSuggestActivity(Long id) {
    super(id);
  }

  public ScheduleSuggestActivity(Teacher teacher, WeekTime time) {
    this.teacher = teacher;
    setTime(new WeekTime(time));
  }

  public ScheduleSuggest getSuggest() {
    return suggest;
  }

  public void setSuggest(ScheduleSuggest clazz) {
    this.suggest = clazz;
  }

  public WeekTime getTime() {
    return time;
  }

  public void setTime(WeekTime time) {
    this.time = time;
  }

  public Date getBeginAt() {
    return null;
  }

  public Date getEndAt() {
    return null;
  }

  public boolean contains(Date oneDay) {
    return false;
  }

  public String getContent() {
    return null;
  }

  public Object clone() {
    ScheduleSuggestActivity activity = new ScheduleSuggestActivity();
    activity.setTime(new WeekTime(getTime()));
    activity.setSuggest(suggest);
    activity.teacher = getTeacher();
    return activity;
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("time", getTime().toString()).add("teacher", getTeacher())
        .add("id", this.id).toString();
  }

  /**
   * 合并排课建议活动
   *
   * @param activities
   * @return
   */
  public static List<ScheduleSuggestActivity> mergeActivities(List<ScheduleSuggestActivity> activities) {
    List<ScheduleSuggestActivity> mergedActivities = CollectUtils.newArrayList();
    if (CollectUtils.isEmpty(activities)) {
      return mergedActivities;
    }
    // 进行排序，将相邻的排课建议活动合并在一起
    Collections.sort(activities);

    Iterator<ScheduleSuggestActivity> activityIter = activities.iterator();

    // 下一个要被合并的Activity
    ScheduleSuggestActivity nextToBeMergedActivity = activityIter.next();
    mergedActivities.add(nextToBeMergedActivity);

    while (activityIter.hasNext()) {
      ScheduleSuggestActivity activity = activityIter.next();
      if (nextToBeMergedActivity.canMergerWith(activity)) {
        // 如果时间上可以合并，而且教师也是一样的
        nextToBeMergedActivity.mergeWith(activity);
      } else if (nextToBeMergedActivity.getTime().equals(activity.getTime())) {
        // 如果时间上一样，只不过教师不一样，那么还是合并，然后将教师合并
        nextToBeMergedActivity.mergeWith(activity);
        nextToBeMergedActivity.setTeacher(activity.getTeacher());
      } else {
        nextToBeMergedActivity = activity;
        mergedActivities.add(nextToBeMergedActivity);
      }
    }
    return mergedActivities;
  }

  public boolean canMergerWith(ScheduleSuggestActivity activity) {
    if (!Objects.equals(getTeacher(), activity.getTeacher())) {
      return false;
    }
    return WeekTimes.canMergerWith(getTime(), activity.getTime());
  }

  public void mergeWith(ScheduleSuggestActivity other) {
    WeekTimes.mergeWith(this.getTime(), other.getTime());
  }

  /**
   * 排序规则，在 {@link #mergeActivities(List)} 中有用到
   *
   * @param other
   */
  public int compareTo(ScheduleSuggestActivity other) {
    int rs = 0;
    // compare weeks
    if (rs == 0) rs = getTime().getWeekstate().compareTo(other.getTime().getWeekstate());
    if (rs == 0) rs = getTime().getStartOn().compareTo(other.getTime().getStartOn());
    if (rs == 0) rs = getTime().getBeginAt().value - other.getTime().getBeginAt().value;
    return rs;
  }

  public Set<ScheduleSuggestActivity> flatten(Map<Integer, CourseUnit> unitMap) {
    Set<ScheduleSuggestActivity> flattenedActivities = CollectUtils.newHashSet();
    if (null != teacher) {
      WeekTime flattenedTime = new WeekTime(time);
      flattenedActivities.add(new ScheduleSuggestActivity(teacher, flattenedTime));
    }
    return flattenedActivities;
  }

  public static Set<ScheduleSuggestActivity> flatten(Collection<ScheduleSuggestActivity> activities,
                                                     TimeSetting timeSetting) {
    Set<ScheduleSuggestActivity> flattenedActivities = CollectUtils.newHashSet();
    for (ScheduleSuggestActivity activity : activities) {
      flattenedActivities.addAll(activity.flatten(timeSetting.getUnitMap()));
    }
    return flattenedActivities;
  }

  public ClazzActivity toSession() {
    ClazzActivity activity = new ClazzActivity();
    activity.setClazz(this.suggest.getClazz());
    if (null != this.teacher) {
      activity.getTeachers().add(this.teacher);
    }

    activity.setTime((WeekTime) this.time.clone());
    return activity;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Classroom getRoom() {
    return room;
  }

  public void setRoom(Classroom room) {
    this.room = room;
  }
}
