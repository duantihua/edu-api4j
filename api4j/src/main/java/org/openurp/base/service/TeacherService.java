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
package org.openurp.base.service;

import java.util.Collection;
import java.util.List;

import org.openurp.base.hr.model.Teacher;

public interface TeacherService {

  /**
   * 根据工号查询教师
   */
  public Teacher getTeacher(String teacherCode);

  /**
   * 根据院系查询教师
   */
  public List<Object[]> getTeacherNamesByDepart(Integer departmentId);

  /**
   * 返回指定id的教师信息，如果不存在这样的教师信息，则返回空.
   *
   * @param id
   * @return
   */
  public Teacher getTeacherById(Long id);

  /**
   * 返回指定职工号的教师信息，如果不存在这样的教师信息，则返回空.
   *
   * @param teacherNO
   * @return
   */
  public Teacher getTeacherByNO(String teacherNO);

  /**
   * 根据教师所在部门查询
   *
   * @param departIds
   * @return
   */
  public List<Teacher> getTeachersByDepartment(String departIds);

  /**
   * 根据教师所在部门查询
   *
   * @param departIds
   * @return
   */
  public List<Teacher> getTeachersByDepartment(Long[] departIds);

  /**
   * 返回指定id数组的教师列表
   *
   * @param teacherIds
   * @return
   */
  public List<Teacher> getTeachersById(Long teacherIds[]);

  /**
   * 返回指定id数组的教师列表
   *
   * @param teacherIds
   * @return
   */
  public List<Teacher> getTeachersById(Collection teacherIds);

  /**
   * 返回指定职工号数组的教师列表
   *
   * @param teacherIds
   * @return
   */
  public List<Teacher> getTeachersByNO(String teacherNOs[]);

}
