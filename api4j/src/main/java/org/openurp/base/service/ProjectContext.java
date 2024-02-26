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

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.core.userdetail.Profile;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.model.Department;
import org.openurp.base.model.User;
import org.openurp.code.std.model.StdType;
import org.openurp.code.edu.model.EducationLevel;

import java.util.List;

/**
 * 教学项目相关的上下文
 */
public interface ProjectContext {

  /**
   * 获得用户当前在哪个教学项目下
   *
   * @return
   */
  Project getProject();

  /**
   * 返回当前用户是否是root用户，id为1的用户
   *
   * @return
   */
  boolean isRoot();

  /**
   * 获得当前登录的用户，如果没有登录，返回Null
   *
   * @return
   */
  User getUser();

  /**
   * <pre>
   * 获得当前教学项目下，用户处于哪个学期
   * 返回的结果可能是：
   * 1.如果用户从来没有切换过学期切换控件，那么返回当前教学项目的当前时间所处的学期
   * 2.如果用户切换过学期控件，那么返回上一次所切换到的学期
   * 3.如果用户正在切换学期控件，那么返回用户切换的学期
   *
   * 受到页面上传入的project.id, semester.id参数，session中项目的影响。
   * 之所以会传入project.id这个参数是因为，学期切换控件支持切换非本项目的学期
   * </pre>
   *
   * @return
   */
  Semester getSemester();

  /**
   * 设置当前在哪个学期下
   *
   * @param semesterId
   */
  void rememberSemester(Integer semesterId);

  /**
   * 清除系统记住的上次所使用的学期
   */
  void forgetSemester();

  /**
   * 获得用户在当前项目下所拥有的 EducationLevel 数据级权限
   *
   * @return
   */
  public List<EducationLevel> getEducationLevels();

  /**
   * 获得用户在当前项目下所拥有的 StdType 数据级权限
   *
   * @return
   */
  public List<StdType> getStdTypes();

  /**
   * 获得用户在当前项目下所拥有的 Department 数据级权限
   *
   * @return
   */
  public List<Department> getDeparts();

  /**
   * 获得用户在当前项目下所拥有的 Department 中是院系的 数据级权限
   *
   * @return
   */
  public List<Department> getColleges();

  /**
   * 获得用户在当前项目下所拥有的 Department 中是开课的 数据级权限
   *
   * @return
   */
  public List<Department> getTeachDeparts();

  /**
   * 获得id字符串
   *
   * @see #getEducationLevels()
   * @return
   */
  public String getEducationIdSeq();

  /**
   * 获得id字符串
   *
   * @see #getStdTypes()
   * @return
   */
  public String getStdTypeIdSeq();

  /**
   * 获得id字符串
   *
   * @see #getDeparts()
   * @return
   */
  public String getDepartIdSeq();

  /**
   * 获得id字符串
   *
   * @see #getColleges()
   * @return
   */
  public String getCollegeIdSeq();

  /**
   * 获得id字符串
   *
   * @see #getTeachDeparts()
   * @return
   */
  public String getTeachDepartIdSeq();

  /**
   * 获得用户所拥有的所有教学项目
   *
   * @return
   */
  public List<Project> getProjects();

  public void applyRestriction(OqlBuilder<?> builder);

  Profile getCurrentProfile();

  List<Profile> getProfiles();
}
