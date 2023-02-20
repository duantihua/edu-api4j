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
package org.openurp.edu.grade.course.service.impl;

import java.util.List;

import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.StdGpa;

/**
 * 平均绩点计算策略
 */
public interface GpaPolicy {

  /**
   * 计算平均绩点
   *
   * @param grades
   * @return
   */
  public Float calcGpa(List<CourseGrade> grades);

  /**
   * 计算平均分
   *
   * @param grades
   * @return
   */
  public Float calcGa(List<CourseGrade> grades);

  /**
   * 保留小数位
   *
   * @param gpa
   * @param precision
   * @return
   */
  public Float round(Float gpa);

  /**
   * 计算平均绩点
   *
   * @param grades
   * @return
   */
  public StdGpa calc(Student std, List<CourseGrade> grades, boolean calcDetail);

  /**
   * 平均绩点和平均分的小数点后精确位数,默认为2
   *
   * @return
   */
  public int getPrecision();
}
