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

import org.openurp.code.edu.model.GradingMode;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.CourseGradeState;
import org.openurp.edu.grade.course.model.ExamGrade;
import org.openurp.edu.grade.course.model.GaGrade;

/**
 * 成绩计算器
 */
public interface CourseGradeCalculator {
  /**
   * 计算最终成绩,是否通过和绩点
   *
   * @param grade
   */
  public void calcFinal(CourseGrade grade, CourseGradeState state);

  /**
   * 计算最终成绩,是否通过和绩点
   *
   * @param grade
   */
  public void calcAll(CourseGrade grade, CourseGradeState state);

  /**
   * 计算总评成绩
   *
   * @param grade
   * @return 总评成绩,但不改动成绩
   */
  public GaGrade calcEndGa(CourseGrade grade, CourseGradeState state);

  /**
   * 计算补考或者缓考总评成绩
   *
   * @param grade
   * @return 总评成绩,但不改动成绩
   */
  public GaGrade calcMakeupDelayGa(CourseGrade grade, CourseGradeState state);

  /**
   * 更新最终分数
   *
   * @param grade
   * @param score
   * @param newStyle
   */
  public void updateScore(CourseGrade grade, Float score, GradingMode newStyle);

  /**
   * 更新考试成绩分数
   *
   * @param eg
   * @param score
   * @param newStyle
   */
  public void updateScore(ExamGrade eg, Float score, GradingMode newStyle);

  /**
   * 更新总评成绩分数
   *
   * @param gag
   * @param score
   * @param newStyle
   */
  public void updateScore(GaGrade gag, Float score, GradingMode newStyle);

  /**
   * 得到用以转换成绩用的服务
   *
   * @return
   */
  public GradeRateService getGradeRateService();
}
