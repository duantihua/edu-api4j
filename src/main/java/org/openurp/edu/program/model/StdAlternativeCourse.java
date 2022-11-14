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

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Student;

/**
 * 学生可代替课程实体类.<br>
 * <p>
 * 指定了学生专业培养计划中的某些课程可以由某些课程替代.<br>
 */
@Entity(name = "org.openurp.edu.program.model.StdAlternativeCourse")
@Table(name = "std_alt_courses")
public class StdAlternativeCourse extends AbstractCourseSubstitution {

  private static final long serialVersionUID = -3619832967631930149L;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 被替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "std_alt_courses_olds", joinColumns = @JoinColumn(name = "std_alternative_course_id"))
  private Set<Course> olds = CollectUtils.newHashSet();
  /** 已替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "std_alt_courses_news", joinColumns = @JoinColumn(name = "std_alternative_course_id"))
  private Set<Course> news = CollectUtils.newHashSet();

  /** 备注 */
  @Size(max = 300)
  private String remark;

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Set<Course> getOlds() {
    return olds;
  }

  public void setOlds(Set<Course> olds) {
    this.olds = olds;
  }

  public Set<Course> getNews() {
    return news;
  }

  public void setNews(Set<Course> news) {
    this.news = news;
  }

}
