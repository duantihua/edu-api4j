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
package org.openurp.edu.grade.course.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.beangle.commons.entity.Entity;
import org.openurp.edu.base.code.model.GradeType;
import org.openurp.edu.base.code.model.ScoreMarkStyle;

/**
 * 成绩状态表<br>
 *
 * <pre>
 * 记录了对应教学任务成绩
 * 1)记录方式,
 * 2)各种成绩成分的百分比,
 * 3)各种成绩的确认状态,
 * 4)各种成绩的发布状态
 * </pre>
 *
 *
 */
@MappedSuperclass
public interface GradeState extends Entity<Long> {
  /**
   * 成绩类型
   *
   * @return
   */
  public GradeType getGradeType();

  /**
   * 记录方式
   *
   * @return
   */
  public ScoreMarkStyle getMarkStyle();

  /**
   * 设置记录方式
   *
   * @param markStyle
   */
  public void setMarkStyle(ScoreMarkStyle scoreMarkStyle);

  /**
   * 返回保留小数位
   *
   * @return
   */
  public int getPrecision();

  /**
   * 设置小数位
   *
   * @param percision
   */
  public void setPrecision(int percision);

  /**
   * 录入时间
   *
   * @return
   */
  public Date getUpdatedAt();

  /**
   * 设置录入时间
   *
   * @param updatedAt
   */
  public void setUpdatedAt(Date updatedAt);

  /**
   * 是否提交确认
   *
   * @return
   */
  public boolean isConfirmed();

  /**
   * 是否发布
   *
   * @return
   */
  public boolean isPublished();

  /**
   * 返回状态
   *
   * @return
   */
  public int getStatus();

  /**
   * 设置状态
   *
   * @param status
   */
  public void setStatus(int status);

  /**
   * 返回操作者
   *
   * @return
   */
  public String getOperator();

  /**
   * 设置操作者
   *
   * @param operator
   */
  public void setOperator(String operator);
}
