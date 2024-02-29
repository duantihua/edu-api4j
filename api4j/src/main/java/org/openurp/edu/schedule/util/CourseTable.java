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
package org.openurp.edu.schedule.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.openurp.base.resource.model.Classroom;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;
import org.openurp.base.hr.model.Teacher;
import org.openurp.base.edu.model.TimeSetting;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.ClazzActivity;

/**
 * 课程表
 */
public class CourseTable {

  // 常用课表类型
  public static final String Squad = "squad";

  public static final String STD = "std";

  public static final String TEACHER = "teacher";

  public static final String ROOM = "room";

  public static final Map<String, Object> resourceClass = CollectUtils.newHashMap();
  static {
    resourceClass.put(Squad, Squad.class);
    resourceClass.put(STD, Student.class);
    resourceClass.put(TEACHER, Teacher.class);
    resourceClass.put(ROOM, Classroom.class);
  }

  String kind;

  Entity<?> resource;

  List<Clazz> clazzes;

  TimeSetting timeSetting;

  List<ClazzActivity> activities = CollectUtils.newArrayList();

  Float credits = null;

  public CourseTable(Entity<?> resource, String kind) {
    this.kind = kind;
    this.resource = resource;
  }

  /**
   * 该课程表中的对应学分总和
   *
   * @return
   */
  public Float getCredits() {
    if (null == credits) {
      if (null == clazzes) {
        return null;
      } else {
        float credits = 0;
        for (Clazz clazz : clazzes) {
          credits += clazz.getCourse().getDefaultCredits();
        }
        return new Float(credits);
      }
    } else {
      return credits;
    }
  }

  public void extractTaskFromActivity() {
    if (null == activities) { return; }
    Set<Clazz> clazzSet = CollectUtils.newHashSet();
    for (ClazzActivity activity : activities) {
      Clazz clazz = activity.getClazz();
      if (!clazzSet.contains(clazz)) {
        clazzSet.add(clazz);
      }
    }
    clazzes = CollectUtils.newArrayList(clazzSet);
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public Entity<?> getResource() {
    return resource;
  }

  public void setResource(Entity<Long> resource) {
    this.resource = resource;
  }

  public List<Clazz> getClazzes() {
    return clazzes;
  }

  public void setClazzes(List<Clazz> clazzes) {
    this.clazzes = clazzes;
  }

  public List<ClazzActivity> getActivities() {
    return activities;
  }

  public void setActivities(List<ClazzActivity> activities) {
    this.activities = activities;
  }

  public void setCredits(Float credits) {
    this.credits = credits;
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<T> getResourceClass(String kind) {
    Class<T> clazz = (Class<T>) resourceClass.get(kind);
    if (null != clazz) {
      return clazz;
    } else {
      throw new RuntimeException("not supported Resource stdType:" + kind);
    }
  }

  public TimeSetting getTimeSetting() {
    return timeSetting;
  }

  public void setTimeSetting(TimeSetting timeSetting) {
    this.timeSetting = timeSetting;
  }

}
