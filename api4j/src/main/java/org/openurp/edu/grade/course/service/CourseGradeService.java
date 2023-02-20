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
package org.openurp.edu.grade.course.service;

import java.util.List;

import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Project;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.grade.course.model.CourseGradeState;

public interface CourseGradeService {
  /**
   * 按照成绩状态，重新计算成绩的<br>
   * 1、首先更改成绩的成绩记录方式<br>
   * 2、score以及是否通过和绩点等项<br>
   * 3、如果成绩状态中发布状态，则进行发布操作
   *
   * @param gradeState
   * @return
   */
  public void recalculate(CourseGradeState gradeState);

  /**
   * 删除考试成绩<br>
   * 同时将该成绩和总评成绩的教师确认位置为0
   *
   * @param task
   * @param gradeType
   */
  public void remove(Clazz clazz, GradeType gradeType);

  /**
   * 发布或取消发布成绩
   *
   * @param clazzIdSeq
   * @param gradeType
   *          如果为空,则发布影响总评和最终
   * @param isPublished
   */
  public void publish(String clazzIdSeq, GradeType[] gradeTypes, boolean isPublished);

  /**
   * 查询成绩状态
   *
   * @param clazz
   * @return
   */
  public CourseGradeState getState(Clazz clazz);

  public List<GradeType> getPublishableGradeTypes(Project project);
}
