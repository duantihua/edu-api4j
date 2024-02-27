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
package org.openurp.edu.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.orm.hibernate.udt.WeekTime;
import org.openurp.base.model.Department;
import org.openurp.base.space.model.Room;
import org.openurp.base.space.model.Classroom;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;
import org.openurp.base.hr.model.Teacher;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.ClazzActivity;
import org.openurp.service.security.DataRealm;

/**
 * 教学资源管理服务类<br>
 * 教学资源涉及到的范畴为: 1)班级,2)教师,3)教室,4)学生<br>
 * 教学资源服务主要用来:<br>
 * 1)查询各种教学资源在指定时间<code>WeekTime<code>内是否被使用.<br>
 * 此时的WeekTime的各种参数都要设置齐全.提供这种服务的方法有<br>
 * 1)以getFreeRoom开头的方法，可以获得制定条件的空闲教室<br>
 * 2)以getFreeTeacher开头的方法，可以获得制定条件的空闲教师<br>
 * 3)以isXXXXOcuupy为命名方式的方法，通过返回的布尔值，提供在指定时间内的是否被占用<br>
 * true为占用，false为空闲<br>
 * 2)查询各种教学资源在指定时间上的教学活动（排课和排考以及任何其他的借用、占用）<br>
 * 此时的code>WeekTime<code>的参数除了教学占用周(weekState)必备外，其他参数可选.<br>
 * 提供的方法以getXXXXActivities(...)方式命名<br>
 *
 */
public interface TeachResourceService {

  /**
   * 学生在该时间是否被占用
   *
   * @param time
   * @param std
   * @return
   */
  public boolean isStdOccupied(WeekTime time, Long stdId);

  /**
   * 学生在该时间是否被占用
   *
   * @param time
   * @param std
   * @return
   */
  public boolean isStdsOccupied(WeekTime time, Collection stdIds);

  /**
   * 学生在该时间是否被占用,除指定的任务外
   *
   * @param time
   * @param std
   * @return
   */
  public boolean isStdsOccupied(WeekTime time, Collection stdIds, Clazz expect);

  /**
   * 查询指定id的教室在该时间点上是否被占用
   *
   * @param roomId
   * @param time
   * @return
   */
  public boolean isRoomOccupied(WeekTime time, Serializable roomId);

  /**
   * 查询指定的Session上设置的教室的时间是否被占用
   *
   * @param time
   * @param roomId
   * @return
   */
  public boolean isSessionRoomOccupied(ClazzActivity activity);

  /**
   * 查询指定id的教师在给定的时间上是否被占用
   *
   * @param time
   * @param teacherId
   * @return
   */
  public boolean isTeacherOccupied(WeekTime time, Long teacherId);

  /**
   * 查询指定id的行政班级在给定的时间上是否被占用
   *
   * @param time
   * @param adminClassId
   * @return
   */
  public boolean isSquadOccupied(WeekTime time, Long adminClassId);

  /**
   * 查询一组行政班级在给定的时间上是否被占用
   *
   * @param time
   * @param adminClasses
   *          班级对象集合
   * @return
   */
  public boolean isSquadesOccupied(WeekTime time, Collection adminClasses);

  /**
   * 在已有的教室中查找空闲的教室
   *
   * @return
   */
  public Collection getFreeRoomsIn(Collection roomIds, WeekTime[] times, Room room);

  /**
   * 查询指定的教学活动类型中特定部门的符合条件的空闲教室
   */
  public OqlBuilder<Classroom> getFreeRoomsOf(OqlBuilder<Classroom> query, Map<String, Object> params,
      List<Department> departs, WeekTime unit, List<Classroom> rooms);

  public Collection getFreeRoomsOf(Project project, Long[] departIds, WeekTime[] times, Room room,
      PageLimit pageLimit, Order order);

  /**
   * <p>
   * 查询所有给定时间段内的排课教学活动.<br>
   * 时间段中的weekId/startUnit/units可以为null<br>
   * 前置条件：std.id不为空.<br>
   * time中的weekStateNum必须设置.<br>
   *
   * @param room
   * @param time
   * @return
   */
  public List getStdActivities(Long stdId, WeekTime time, Class activityClass, Semester semester);

  public List getRoomOccupyInfos(Integer roomId, Long weekStateNum, Integer year);

  public List getTeacherOccupyInfos(Long teacherId, Long weekStateNum, Integer year);

  public List getSquadOccupyInfos(Long adminClassId, Long weekStateNum, Integer year);

  /**
   * 返回指定id的教室
   *
   * @param roomIds
   *          allocate scope
   * @return
   */
  public List getClassrooms(Collection roomIds);

  /**
   * 返回指定id数组的教师列表
   *
   * @param teacherIds
   * @return
   */
  public List getTeachers(Collection teacherIds);

  /**
   * 返回指定id串的教室，按照教学楼和教室代码排序.
   *
   * @param roomIdSeq
   * @return
   */
  public List getClassrooms(String roomIdSeq);

  public List getClassrooms(Integer[] roomIds);

  /**
   * 根据教学班人数上限统计课程教室利用率
   *
   * @param semester
   * @param examType
   * @param limit
   * @param ratio
   * @return
   */
  public Map<ClazzActivity, Object[]> getRoomUtilizationOfCourse(List<Department> departments,
                                                                 Semester semester, Float ratio);

  /**
   * 根据教学班学生人数统计课程教室利用率
   *
   * @param semester
   * @param examType
   * @param limit
   * @param ratio
   * @return
   */
  public Map<ClazzActivity, Object[]> getElectCountRoomUtilizationOfCourse(List<Department> departments,
                                                                           Semester semester, Float ratio);

  /**
   * 根据占用信息来详细查询班级
   *
   * @param semester
   *          学期
   * @param startWeek
   *          起始周
   * @param endWeek
   *          结束周
   * @param startWeekDay
   *          起始星期
   * @param endWeekDay
   *          结束星期
   * @param startUnit
   *          起始小节
   * @param endUnit
   *          结束小节
   * @param busy
   *          在以上提供的上课信息上是否有课
   * @param dataRealm
   *          数据级权限，如果为null，则不做权限控制
   * @return
   */
  public Collection<Squad> querySquadByOccupyInfo(Semester semester, Integer startWeek,
      Integer endWeek, Integer startWeekDay, Integer endWeekDay, Integer startUnit, Integer endUnit,
      boolean busy, DataRealm dataRealm);

  public List<ClazzActivity> getSquadActivities(Squad squad, WeekTime time, Semester semester);

  public List<ClazzActivity> getTeacherActivities(Teacher teacher, WeekTime time, Semester semester);

  public List<ClazzActivity> getRoomActivities(Classroom room, WeekTime time, Semester semester);

  public List<ClazzActivity> getRoomActivities(Classroom room, WeekTime time, Semester semester,
                                               List<Department> departments, Project project);

  public List<ClazzActivity> getStdActivities(Student student, WeekTime time, Semester semester);

  /**
   * 返回指定部门管理的在times上空闲的教师
   *
   * @param departIds
   * @param times
   * @return
   */
  public Collection getFreeTeachersIn(Collection teacherIds, WeekTime[] times, Teacher teacher);

  public Collection<Teacher> getFreeTeachersOf(Project project, WeekTime[] times, Teacher teacher,
      Teacher replaceTeacher, PageLimit pageLimit, String order);

  public int getTeacherPeriod(Clazz clazz, Teacher teacher);

}
