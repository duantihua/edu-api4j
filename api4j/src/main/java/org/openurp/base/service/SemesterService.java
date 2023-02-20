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

import java.sql.Date;
import java.util.List;

import org.openurp.base.edu.model.Calendar;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;

public interface SemesterService {

  /**
   * 依据非业务主键查询对应的教学日历. 若id为空或不存在返回null.
   *
   * @param id
   * @return
   */
  public Semester getSemester(Integer id);

  /**
   * 查找在指定的日历时间范围内，时间有重叠的其他学期<br>
   * 前置条件：semester中的id/开始日期和截止日期不能为null;<br>
   * 包含自身.
   *
   * @param semester
   * @return
   */
  public List<Semester> getSemestersOfOverlapped(Semester semester);

  /**
   * 根据学期、学年度、学生类型返回一个Semester对象<br>
   * 没有直接对应的，凡返回上级对象的日历
   *
   * @param calendarId
   * @param year
   * @param term
   * @return
   */
  public Semester getSemester(Calendar calendar, String year, String term);

  /**
   * 根据学期、学年度、学生类型返回一个Semester对象<br>
   * 没有直接对应的，凡返回上级对象的日历
   *
   * @param project
   * @param schoolYear
   * @param term
   * @return
   */
  public Semester getSemester(Project project, String schoolYear, String term);

  /**
   * 查询对应培养类型的当前日历设置
   *
   * @param calendarId
   * @return
   */
  public Semester getSemester(Calendar calendar, Date date);

  /**
   * 查询对应培养类型的当前日历设置
   *
   * @param calendarId
   * @return
   */
  public Semester getCurSemester(Calendar calendar);

  /**
   * 查询对应培养类型的当前日历设置
   *
   * @param calendarId
   * @return
   */
  public Semester getCurSemester(Integer calendarId);

  /**
   * 查询对应培养类型的距离当前时间最近的日历设置
   *
   * @param calendarId
   * @return
   */
  public Semester getNearestSemester(Calendar calendar);

  /**
   * 查询对应培养类型的上个学期日历设置
   *
   * @param calendarId
   * @return
   */
  @Deprecated
  public Semester getPreviousSemester(Calendar calendar);

  /**
   * 查询个学期日历
   *
   * @param calendarId
   * @return
   */
  public Semester getNextSemester(Semester semester);

  /**
   * 通过培养类型找到当前学年学期
   *
   * @param project
   */
  public Semester getCurSemester(Project project);

  /**
   * 查询对应培养类型的距离当前时间最近的日历设置
   *
   * @param project
   * @return
   */
  public Semester getNearestSemester(Project project);

  /**
   * 计算first到second教学日历之间的学期数.<br>
   * first在second之前则返回正整数，否则返回1或负整数.<br>
   * [first,second]包含两段的学期数.<br>
   * 如果给出两个教学日历中的培养类型不一致，则返回null<br>
   * 相同教学日历,则返回1<br>
   *
   * @param first
   * @param second
   * @param omitSmallTerm
   *          计算学期间隔中,是否忽略小学期
   * @return
   */
  public int getTermsBetween(Semester first, Semester second, boolean omitSmallTerm);

  /**
   * 检查同培养类型和学年度中的学期设置是否存在日期冲突现象.
   *
   * @param semester
   */
  public boolean checkDateCollision(Semester semester);

  /**
   * 删除教学日历. 将该教学日历删除，并将该日历的前后日历传接起来
   */
  public void removeSemester(Semester semester);

  /**
   * 更新已有的教学日历，更新空对象将直接返回.
   */
  public void saveSemester(Semester semester);

  /**
   * 根据培养类型找到日历方案
   *
   * @param project
   * @return
   */
  public Calendar getCalendar(Project project);

  /**
   * 根据培养类型找到日历方案
   *
   * @param projects
   * @return
   */
  public List<Calendar> getCalendars(List<Project> projects);

  /**
   * 根据开始学年学期和结束学年学期查询出之间的所有学年学期<br>
   * 注意：为有效时间范围内的
   *
   * @param semester
   * @return
   */
  public List<Semester> getSemesters(Integer semesterStartId, Integer semesterEndId);

  /**
   * 给出指定日期相交的学期
   *
   * @param calendar
   * @param begOn
   * @param endOn
   * @return
   */
  public Semester getSemester(Calendar calendar, Date begOn, Date endOn);

  /**
   * 获取当前学期的上一个学期
   *
   * @param semester
   * @return
   */
  public Semester getPrevSemester(Semester semester);
}
