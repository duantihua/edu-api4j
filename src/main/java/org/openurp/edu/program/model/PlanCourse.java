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
package org.openurp.edu.program.model;

import org.beangle.commons.entity.Entity;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.time.Terms;
import org.openurp.base.edu.model.Course;

/**
 * 培养计划中的课程.<br>
 * 具体体现了课程、开课和审核要求三部分.
 */
public interface PlanCourse extends Entity<Long>, Cloneable {

  /**
   * 查询课程.
   *
   * @return
   */
  public Course getCourse();

  /**
   * 设置课程
   *
   * @param course
   */
  public void setCourse(Course course);

  /**
   * <pre>
   * 开课学期，可以是数字，也可以是类似 春、秋的字样。
   * 和 {@link Semester#getGroup()} 呼应，用于生成开课计划
   * 如果是多个值，必须用逗号分开，比如：
   * ,1,
   * ,1,2,
   * ,春,
   * ,春,秋,
   * </pre>
   */
  public Terms getTerms();

  public void setTerms(Terms terms);

  /**
   * 备注
   *
   * @return
   */
  public String getRemark();

  public void setRemark(String remark);

  /**
   * 课程组
   */
  public CourseGroup getGroup();

  public void setGroup(CourseGroup courseGroup);

  public Object clone() throws CloneNotSupportedException;

  /**
   * 课程是否必修.
   *
   * @return
   */
  public boolean isCompulsory();

  /**
   * 设置是否必修
   *
   * @param compulsory
   */
  public void setCompulsory(boolean compulsory);

}
