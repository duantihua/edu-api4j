/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.edu.program.subst.model;

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
import org.openurp.edu.base.model.Course;
import org.openurp.edu.base.model.Student;

/**
 * 学生可代替课程实体类.<br>
 * <p>
 * 指定了学生专业培养计划中的某些课程可以由某些课程替代.<br>
 *
 *
 */
@Entity(name = "org.openurp.edu.program.subst.model.StdCourseSubstitution")
@Table(name = "std_course_subs")
public class StdCourseSubstitution extends AbstractCourseSubstitution {

  private static final long serialVersionUID = -3619832967631930149L;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 被替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "std_course_subs_olds")
  private Set<Course> origins = CollectUtils.newHashSet();

  /** 已替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "std_course_subs_news")
  private Set<Course> substitutes = CollectUtils.newHashSet();

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

  public Set<Course> getOrigins() {
    return origins;
  }

  public void setOrigins(Set<Course> origins) {
    this.origins = origins;
  }

  public Set<Course> getSubstitutes() {
    return substitutes;
  }

  public void setSubstitutes(Set<Course> substitutes) {
    this.substitutes = substitutes;
  }
}
