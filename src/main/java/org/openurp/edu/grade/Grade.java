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
package org.openurp.edu.grade;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.TimeEntity;
import org.openurp.code.edu.model.GradingMode;
import org.openurp.base.std.model.Student;

/**
 * 成绩接口
 * </p>
 * 系统中所有成绩的抽象接口，记录成绩的原始分数、呈现方式、状态和是否通过。
 * 成绩中设立原始分数和分数字面值，分别表示数字型分数和文字型评价。分数用于和其他记录方式进行转换，
 * 分数字面值则为最后的转换结果。
 *
 * @depend - - - GradingMode
 * @since 2006
 */
public interface Grade extends Comparable<Grade>, Entity<Long>, TimeEntity {

  public Student getStd();

  /**
   * 返回分数
   *
   * @return null 如果没有分数
   */
  public Float getScore();

  /**
   * 设置分数
   *
   * @param score
   */
  public void setScore(Float score);

  /**
   * 得分字面值
   *
   * @return
   */
  public String getScoreText();

  /**
   * 设置得分字面值
   *
   * @param scoreText
   */
  public void setScoreText(String scoreText);

  /**
   * 是否合格
   *
   * @return
   */
  public boolean isPassed();

  /**
   * 设置是否通过
   *
   * @param isPass
   */
  public void setPassed(boolean isPass);

  /**
   * 是否发布
   *
   * @return
   */
  public boolean isPublished();

  /**
   * 是否确认
   *
   * @return
   */
  public boolean isConfirmed();

  /**
   * 返回成绩状态
   *
   * @return
   */
  public int getStatus();

  /**
   * @param status
   */
  public void setStatus(int status);

  /**
   * 记录方式
   *
   * @return
   */
  public GradingMode getGradingMode();

  /**
   * 设置记录方式
   *
   * @param gradingMode
   */
  public void setGradingMode(GradingMode gradingMode);

  public final class Status {

    /**
     * 新添加、刚录入、暂存
     */
    public static final int New = 0;

    /**
     * 确认 or 已提交
     */
    public static final int Confirmed = 1;

    /**
     * 已发布
     */
    public static final int Published = 2;

    /**
     * 不在使用，没用的成绩
     */
    public static final int Obsolete = 3;
  }

  public String getOperator();

  public void setOperator(String operator);

  public java.util.Date getCreatedAt();

  public void setCreatedAt(java.util.Date createdAt);
}
