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

import java.util.Collection;

import javax.persistence.MappedSuperclass;

import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.edu.base.model.Course;

/**
 * 抽象替代课程实现
 *
 *
 */
@MappedSuperclass
public abstract class AbstractCourseSubstitution extends NumberIdTimeObject<Long>
    implements CourseSubstitution {

  private static final long serialVersionUID = 1364767411434767491L;

  public void addOrigin(Course course) {
    getOrigins().add(course);
  }

  public void addOrigins(Collection<Course> courses) {
    getOrigins().addAll(courses);
  }

  public void addSubstitute(Course course) {
    getSubstitutes().add(course);
  }

  public void addSubstitutes(Collection<Course> courses) {
    getSubstitutes().addAll(courses);
  }

}
