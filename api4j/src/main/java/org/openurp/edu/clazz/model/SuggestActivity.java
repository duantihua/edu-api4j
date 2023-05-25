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

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.beangle.orm.hibernate.udt.WeekTimes;
import org.openurp.base.edu.model.CourseUnit;
import org.openurp.base.edu.model.Teacher;
import org.openurp.base.edu.model.TimeSetting;

/**
 * 建议教学活动
 *
 *
 */
@Entity(name = "org.openurp.edu.clazz.model.SuggestActivity")
public class SuggestActivity extends LongIdObject implements Comparable<SuggestActivity> {
  private static final long serialVersionUID = 2498530728105897805L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(targetEntity = ArrangeSuggest.class)
  protected ArrangeSuggest arrangeSuggest;

  /** 上课时间 */
  @Embedded
  protected WeekTime time;

  /** 授课教师列表 */
  @ManyToMany
  private Set<Teacher> teachers = CollectUtils.newHashSet();

  public SuggestActivity() {
    super();
  }

  public SuggestActivity(Long id) {
    super(id);
  }

  public SuggestActivity(Teacher teacher, WeekTime time) {
    getTeachers().add(teacher);
    setTime(new WeekTime(time));
  }

  public ArrangeSuggest getArrangeSuggest() {
    return arrangeSuggest;
  }

  public void setArrangeSuggest(ArrangeSuggest clazz) {
    this.arrangeSuggest = clazz;
  }

  public Set<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(Set<Teacher> teachers) {
    this.teachers = teachers;
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
    SuggestActivity activity = new SuggestActivity();
    activity.setTime(new WeekTime(getTime()));
    activity.setArrangeSuggest(arrangeSuggest);
    activity.getTeachers().addAll(getTeachers());
    return activity;
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("time", getTime().toString()).add("teachers", getTeachers())
        .add("id", this.id).toString();
  }

  /**
   * 合并排课建议活动
   *
   * @param activities
   * @return
   */
  public static List<SuggestActivity> mergeActivities(List<SuggestActivity> activities) {
    List<SuggestActivity> mergedActivities = CollectUtils.newArrayList();
    if (CollectUtils.isEmpty(activities)) { return mergedActivities; }
    // 进行排序，将相邻的排课建议活动合并在一起
    Collections.sort(activities);

    Iterator<SuggestActivity> activityIter = activities.iterator();

    // 下一个要被合并的Activity
    SuggestActivity nextToBeMergedActivity = activityIter.next();
    mergedActivities.add(nextToBeMergedActivity);

    while (activityIter.hasNext()) {
      SuggestActivity activity = activityIter.next();
      if (nextToBeMergedActivity.canMergerWith(activity)) {
        // 如果时间上可以合并，而且教师也是一样的
        nextToBeMergedActivity.mergeWith(activity);
      } else if (nextToBeMergedActivity.getTime().equals(activity.getTime())) {
        // 如果时间上一样，只不过教师不一样，那么还是合并，然后将教师合并
        nextToBeMergedActivity.mergeWith(activity);
        nextToBeMergedActivity.getTeachers().addAll(activity.getTeachers());
      } else {
        nextToBeMergedActivity = activity;
        mergedActivities.add(nextToBeMergedActivity);
      }
    }
    return mergedActivities;
  }

  public boolean canMergerWith(SuggestActivity activity) {
    if (!getTeachers().equals(activity.getTeachers())) { return false; }
    return WeekTimes.canMergerWith(getTime(), activity.getTime());
  }

  public void mergeWith(SuggestActivity other) {
    WeekTimes.mergeWith(this.getTime(), other.getTime());
  }

  /**
   * 排序规则，在 {@link #mergeActivities(List)} 中有用到
   *
   * @param other
   */
  public int compareTo(SuggestActivity other) {
    int rs = 0;
    // compare weeks
    if (rs == 0) rs = getTime().getWeekstate().compareTo(other.getTime().getWeekstate());
    if (rs == 0) rs = getTime().getStartOn().compareTo(other.getTime().getStartOn());
    if (rs == 0) rs = getTime().getBeginAt().value - other.getTime().getBeginAt().value;
    // compare teacher
    if (rs == 0) rs = getTeachers().size() - other.getTeachers().size();
    return rs;
  }

  public Set<SuggestActivity> flatten(Map<Integer, CourseUnit> unitMap) {
    Set<SuggestActivity> flattenedActivities = CollectUtils.newHashSet();
    for (Teacher teacher : teachers) {
      WeekTime flattenedTime = new WeekTime(time);
      flattenedActivities.add(new SuggestActivity(teacher, flattenedTime));
    }
    return flattenedActivities;
  }

  public static Set<SuggestActivity> flatten(Collection<SuggestActivity> activities,
      TimeSetting timeSetting) {
    Set<SuggestActivity> flattenedActivities = CollectUtils.newHashSet();
    for (SuggestActivity activity : activities) {
      flattenedActivities.addAll(activity.flatten(timeSetting.getUnitMap()));
    }
    return flattenedActivities;
  }

  public ClazzActivity toSession() {
    ClazzActivity activity = new ClazzActivity();
    activity.setClazz(this.arrangeSuggest.getClazz());
    activity.getTeachers().addAll(this.teachers);
    activity.setTime((WeekTime) this.time.clone());
    return activity;
  }

}
