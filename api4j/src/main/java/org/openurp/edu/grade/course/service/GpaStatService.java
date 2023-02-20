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

import java.util.Collection;
import java.util.List;

import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.StdGpa;
import org.openurp.edu.grade.course.service.impl.MultiStdGpa;

/**
 * 平均绩点统计服务
 */
public interface GpaStatService {

  /**
   * 如果semesters不包含元素或者为null则统计所有学期 否则统计学生的在校semesters所包含的学期的平均绩点
   *
   * <pre>
   *      平均绩点为： gpa=(∑(绩点*学分))/∑(学分)
   *      平均分为： ga=(∑(得分*学分))/∑(学分)
   * </pre>
   *
   * @param std
   * @return
   */
  public StdGpa stat(Student std, Semester... semesters);

  /**
   * 根据指定数据进行统计绩点
   *
   * @param std
   * @param grades
   * @return
   */
  public StdGpa stat(Student std, List<CourseGrade> grades);

  /**
   * 统计多个学生的平均绩点和其他信息 如果semesters不包含元素或者为null则统计这些所有学期
   * 否则统计这些学生的semesters所包含的学期的平均绩点
   *
   * @param stds
   * @return
   */
  public MultiStdGpa stat(Collection<Student> stds, Semester... semesters);

  public void refresh(StdGpa stdGpa, List<CourseGrade> grades);

  public void refresh(StdGpa stdGpa);
}
