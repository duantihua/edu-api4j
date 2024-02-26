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
package org.openurp.edu.clazz.service;

//$Id: TeachTaskService.java,v 1.21 2006/12/26 09:48:33 duanth Exp $
/*
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.beangle.commons.entity.Entity;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.hr.model.Teacher;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 教学任务服务类 教学任务默认以开课院系为权限区分
 */
public interface ClazzService {

  /**
   * 某个学期里面的开课院系
   *
   * @param projects
   * @param departments
   * @param semester
   * @return
   */
  public List<Department> teachDepartsOfSemester(List<Project> projects, List<Department> departments,
      Semester semester);

  /**
   * 某个学期里面的课程类别
   *
   * @param projects
   * @param departIdSeq
   * @param semester
   * @return
   */
  public List<CourseType> courseTypesOfSemester(List<Project> projects, List<Department> departments,
      Semester semester);

  /**
   * 某个学期所有任务中涉及到的课院系
   *
   * @param projects
   * @param departIdSeq
   * @param semester
   * @return
   */
  public List<Department> attendDepartsOfSemester(List<Project> projects, Semester semester);

  /**
   * 某个学期里面可以上课的院系（当前有效的培养计划中所涉及到的所有院系）
   *
   * @param projects
   * @param departments
   * @param semester
   * @return
   */
  public List<Department> canAttendDepartsOfSemester(List<Project> projects, List<Department> departments,
      Semester semester);

  public List<Project> getProjectsForTeacher(Teacher teacher);

  public List<Clazz> getClazzByCategory(Serializable id, ClazzFilterStrategy strategy,
      Collection<Semester> semesters);

  public List<Clazz> getClazzByCategory(Serializable id, ClazzFilterStrategy strategy, Semester semester);

  public List<Clazz> copy(List<Clazz> clazzes, TaskCopyParams params);

  /**
   * 根据课程限制组条件获取教学任务
   *
   * @param semester
   * @param entity
   * @return
   */
  public <T extends Entity<?>> List<Clazz> getClazzes(Semester semester, T entity);

  /**
   * 填充教师
   *
   * @param teacherIds 如果为null或空数组，那么教师就被清空了
   * @param clazz
   */
  void fillTeachers(Long[] teacherIds, Clazz clazz);

  void normalizeActivity(Clazz clazz);

  void adjustWeekstateBySchedule(Semester semester, List<Clazz> clazzes);
}
