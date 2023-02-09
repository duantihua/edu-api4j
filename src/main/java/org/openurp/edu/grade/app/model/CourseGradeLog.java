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
package org.openurp.edu.grade.app.model;

import java.util.Date;

import org.beangle.commons.entity.Entity;
import org.openurp.code.edu.model.ExamStatus;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;

/**
 * 成绩新增/修改记录
 */
public interface CourseGradeLog extends Entity<Long> {

  /**
   * 学生
   *
   * @return
   */
  public Student getStd();

  /**
   * 课程
   *
   * @return
   */
  public Course getCourse();

  /**
   * 学期
   *
   * @return
   */
  public Semester getSemester();

  /**
   * 成绩类型
   *
   * @return
   */
  public GradeType getGradeType();

  /**
   * 修改前分数
   *
   * @return
   */
  public String getOldScore();

  /**
   * 修改后分数
   *
   * @return
   */
  public String getNewScore();

  /**
   * 修改前考试情况
   *
   * @return
   */
  public ExamStatus getOldExamStatus();

  /**
   * 修改后考试情况
   *
   * @return
   */
  public ExamStatus getNewExamStatus();

  /**
   * 被修改成绩的id
   *
   * @return
   */
  public Long getGradeId();

  /**
   * 修改时间
   *
   * @return
   */
  public Date getUpdatedAt();

  /**
   * 修改人
   *
   * @return
   */
  public String getOperator();

  /**
   * 修改访问途径(ip,agent)
   *
   * @return
   */
  public String getUpdatedFrom();

  /**
   * 是否删除
   *
   * @return
   */
  public boolean isRemove();

  /**
   * 备注
   *
   * @return
   */
  public String getRemark();

}
