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
package org.openurp.edu.service.impl;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.edu.model.*;
import org.openurp.edu.service.TeachResourceService;
import org.openurp.base.model.Department;
import org.openurp.base.model.Room;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.Restriction;
import org.openurp.edu.clazz.model.RestrictionMeta;
import org.openurp.edu.clazz.model.ClazzSession;
import org.openurp.edu.clazz.service.CourseLimitUtils;
import org.openurp.service.security.DataRealm;

import java.io.Serializable;
import java.util.*;

public class TeachResourceServiceImpl extends BaseServiceImpl implements TeachResourceService {

  public boolean isStdOccupied(WeekTime time, Long stdId) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isStdsOccupied(WeekTime time, Collection stdIds) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isStdsOccupied(WeekTime time, Collection stdIds, Clazz expect) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isRoomOccupied(WeekTime time, Serializable roomId) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isSessionRoomOccupied(ClazzSession activity) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isTeacherOccupied(WeekTime time, Long teacherId) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isSquadOccupied(WeekTime time, Long adminClassId) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isSquadesOccupied(WeekTime time, Collection adminClasses) {
    // TODO Auto-generated method stub
    return false;
  }

  public Collection getFreeRoomsIn(Collection roomIds, WeekTime[] times, Room room) {
    return null;
  }

  public OqlBuilder<Classroom> getFreeRoomsOf(OqlBuilder<Classroom> query, Map<String, Object> params,
                                              List<Department> departs, WeekTime unit, List<Classroom> rooms) {
    query.where(
        "not exists(select 1 from org.openurp.edu.room.model.Occupancy occupancy where occupancy.room = classroom.room "
            + "and (occupancy.time.startOn = :startOn) "
            + "and bitand(occupancy.time.weekstate , :weekState)>0 "
            + "and (:startTime <= occupancy.time.endAt and :endTime > occupancy.time.beginAt)" + ")");
    query.where("classroom.beginOn <= :now and (classroom.endOn is null or classroom.endOn >= :now)");
    query.join("classroom.departments", "depart");
    query.where("depart in (:departs)");
    query.select("distinct classroom");
    if (!rooms.isEmpty()) {
      query.where("classroom not in (:rooms)");
    }
    params.put("startOn", unit.getStartOn());
    params.put("weekState", unit.getWeekstate());
    params.put("startTime", unit.getBeginAt());
    params.put("endTime", unit.getEndAt());
    params.put("now", new Date());
    params.put("departs", departs);
    if (!rooms.isEmpty()) {
      params.put("rooms", rooms);
    }
    query.params(params);
    return null;
  }

  /**
   * 根据时间得到空余教室
   */
  public Collection getFreeRoomsOf(Project project, Long[] departIds, WeekTime[] times, Room room,
                                   PageLimit limit, Order order) {
    OqlBuilder<Classroom> query = OqlBuilder.from(Classroom.class, "room");
    query.where("room.building.department.id in (:departs)", departIds);
    query.where(
        "not exists (select 1 from org.openurp.edu.room.model.Occupancy occ where occ.time in (:times) and room.room = occ.room)",
        times);
    query.limit(limit);
    query.orderBy(order);
    return entityDao.search(query);
  }

  public Collection getFreeTeachersIn(Collection teacherIds, WeekTime[] times, Teacher teacher) {
    return null;
  }

  public Collection<Teacher> getFreeTeachersOf(Project project, WeekTime[] times, Teacher teacher,
                                               Teacher replaceTeacher, PageLimit pageLimit, String order) {
    OqlBuilder<Teacher> builder = OqlBuilder.from(Teacher.class, "teacher");
    if (null != replaceTeacher) {
      builder.where("teacher!= :replaceTeacher", replaceTeacher);
    }
    if (null != teacher) {
      if (Strings.isNotEmpty(teacher.getCode())) {
        builder.where(Condition.like("teacher.staff.code", teacher.getCode()));
      }
      if (Strings.isNotEmpty(teacher.getName())) {
        builder.where(Condition.like("teacher.name", teacher.getName()));
      }
      if (null != teacher.getDepartment()) {
        builder.where("teacher.department = :deparment", teacher.getDepartment());
      }
    }
    StringBuilder hql = new StringBuilder(
        "not exists (from org.openurp.edu.clazz.model.Clazz clazz join   clazz.schedule.sessions activity "
            + "join activity.teachers actTeacher where actTeacher=teacher ");
    String occupy = "";
    for (int i = 0; i < times.length; i++) {
      occupy = "(activity.time.startOn=:startOn" + i + " and bitand(activity.time.weekstate,"
          + new Long(times[i].getWeekstate().value) + ")>0  and " + times[i].getBeginAt().value
          + " <= activity.time.endAt and " + times[i].getEndAt().value + " > activity.time.beginAt)";
      if (i > 0) {
        hql.append(" or ");
      } else if (i == 0) {
        hql.append(" and (");
      }
      hql.append(occupy);
      builder.param("startOn" + i, times[i].getStartOn());
    }
    hql.append("))");
    if (times.length > 0) {
      builder.where(hql.toString());
    }
    builder.limit(pageLimit).orderBy(order);
    return entityDao.search(builder);
  }

  public List<ClazzSession> getSquadActivities(Squad squad, WeekTime time, Semester semester) {
    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity");
    builder.where("activity.clazz.semester =:semester", semester);
    builder.where("activity.clazz.project.id=:projectId", squad.getProject().getId());
    setTimeQuery(time, builder);
    Condition con = CourseLimitUtils.build(RestrictionMeta.Squad, "lgi", squad.getId().toString());
    List<?> params = con.getParams();
    builder.where("exists(from " + Restriction.class.getName()
        + " lg  join lg.items as lgi where lg.clazz=activity.clazz and " + con.getContent()
        + ")", params.get(0), params.get(1), params.get(2));

    return entityDao.search(builder);
  }

  protected void setTimeQuery(WeekTime time, OqlBuilder<ClazzSession> builder) {
    if (time != null) {
      if (null != time.getStartOn()) {
        builder.where("activity.time.startOn =:startOn", time.getStartOn());
      }
      if (null != time.getEndAt()) {
        builder.where("activity.time.endAt =:endUnit", time.getEndAt());
      }
      if (null != time.getBeginAt()) {
        builder.where("activity.time.beginAt =:startUnit", time.getBeginAt());
      }
      if (null != time.getWeekstate() && 0 < time.getWeekstate().value) {
        builder.where("bitand(activity.time.weekstate," + time.getWeekstate().value + ")>0");
      }
    }
  }

  public List<ClazzSession> getTeacherActivities(Teacher teacher, WeekTime time, Semester semester) {
    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity");
    builder.join("activity.teachers", "teacher");
    builder.where("activity.clazz.semester =:semester", semester);
    setTimeQuery(time, builder);
    builder.where("teacher = :teacher", teacher);
    return entityDao.search(builder);
  }

  public List<ClazzSession> getRoomActivities(Classroom room, WeekTime time, Semester semester) {
    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity");
    builder.join("activity.rooms", "room");
    builder.where("activity.clazz.semester =:semester", semester);
    setTimeQuery(time, builder);
    builder.where("room = :room", room);
    return entityDao.search(builder);
  }

  public List<ClazzSession> getRoomActivities(Classroom room, WeekTime time, Semester semester,
                                              List<Department> departments, Project project) {
    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity");
    builder.join("activity.rooms", "room");
    builder.where("activity.clazz.semester =:semester", semester);
    if (CollectUtils.isNotEmpty(departments)) {
      builder.where("activity.clazz.teachDepart in (:departments)", departments);
    }
    if (null != project) {
      builder.where("activity.clazz.project = :project", project);
    }
    setTimeQuery(time, builder);
    builder.where("room = :room", room);
    return entityDao.search(builder);
  }

  public List<ClazzSession> getStdActivities(Student student, WeekTime time, Semester semester) {

    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity");
    builder.join("activity.clazz.enrollment.courseTakers", "taker");
    builder.where("activity.clazz.semester =:semester", semester);
    setTimeQuery(time, builder);
    builder.where("taker.std = :student", student);
    return entityDao.search(builder);
  }

  public List getRoomActivities(Serializable roomId, WeekTime time, Class activityClass, Semester semester) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getStdActivities(Long stdId, WeekTime time, Class activityClass, Semester semester) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getRoomOccupyInfos(Integer roomId, Long weekStateNum, Integer year) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getTeacherOccupyInfos(Long teacherId, Long weekStateNum, Integer year) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getSquadOccupyInfos(Long adminClassId, Long weekStateNum, Integer year) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getClassrooms(Collection roomIds) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getTeachers(Collection teacherIds) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getClassrooms(String roomIdSeq) {
    // TODO Auto-generated method stub
    return null;
  }

  public List getClassrooms(Integer[] roomIds) {
    // TODO Auto-generated method stub
    return null;
  }

  public Map<ClazzSession, Object[]> getElectCountRoomUtilizationOfCourse(List<Department> departments,
                                                                          Semester semester, Float ratio) {
    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity")
        .where("activity.clazz.semester=:semester", semester)
        .where("activity.clazz.teachDepart in (:depart)", departments);
    List<ClazzSession> activitys = entityDao.search(builder);
    Map<ClazzSession, Object[]> utilizations = CollectUtils.newHashMap();
    for (ClazzSession courseActivity : activitys) {
      Set<Classroom> rooms = courseActivity.getRooms();
      int capacity = 0;
      for (Classroom room : rooms) {
        capacity += room.getCourseCapacity();
      }
      Object[] objs = new Object[2];
      objs[1] = capacity;
      if (capacity != 0) {
        Float ratioNow = (float) courseActivity.getClazz().getEnrollment().getActual() / (float) capacity;
        if (ratioNow <= ratio) {
          objs[0] = ratioNow;
          utilizations.put(courseActivity, objs);
        }
      } else {
        objs[0] = 0f;
        utilizations.put(courseActivity, objs);
      }
    }
    return utilizations;
  }

  public Map<ClazzSession, Object[]> getRoomUtilizationOfCourse(List<Department> departments, Semester semester,
                                                                Float ratio) {
    OqlBuilder<ClazzSession> builder = OqlBuilder.from(ClazzSession.class, "activity")
        .where("activity.clazz.semester=:semester", semester)
        .where("activity.clazz.teachDepart in (:depart)", departments);
    List<ClazzSession> activitys = entityDao.search(builder);
    Map<ClazzSession, Object[]> utilizations = CollectUtils.newHashMap();
    for (ClazzSession courseActivity : activitys) {
      Set<Classroom> rooms = courseActivity.getRooms();
      int capacity = 0;
      for (Classroom room : rooms) {
        capacity += room.getCourseCapacity();
      }
      Object[] objs = new Object[2];
      objs[1] = capacity;
      if (capacity != 0) {
        Float ratioNow = (float) courseActivity.getClazz().getEnrollment().getCapacity() / (float) capacity;
        if (ratioNow <= ratio) {
          objs[0] = ratioNow;
          utilizations.put(courseActivity, objs);
        }
      } else {
        objs[0] = 0f;
        utilizations.put(courseActivity, objs);
      }
    }
    return utilizations;
  }

  public Collection<Squad> querySquadByOccupyInfo(Semester semester, Integer startWeek, Integer endWeek,
                                                  Integer startWeekDay, Integer endWeekDay, Integer startUnit, Integer endUnit, boolean busy,
                                                  DataRealm dataRealm) {
    return null;
  }

  public int getTeacherPeriod(Clazz clazz, Teacher teacher) {
    Set<ClazzSession> courseActivities = clazz.getSchedule().getSessions();
    int period = 0;
    for (ClazzSession courseActivity : courseActivities) {
      if (courseActivity.getTeachers().contains(teacher)) {
        WeekTime time = courseActivity.getTime();
        period += Math.ceil(time.getEndAt().interval(time.getBeginAt()) / 45.0)
            * time.getWeekstate().getWeeks();
      }
    }
    return period;
  }
}
