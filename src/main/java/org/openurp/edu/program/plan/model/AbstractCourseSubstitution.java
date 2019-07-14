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
package org.openurp.edu.program.plan.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.MappedSuperclass;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.edu.base.model.Course;

/**
 * 抽象替代课程实现
 */
@MappedSuperclass
public abstract class AbstractCourseSubstitution extends NumberIdTimeObject<Long>
    implements AlternativeCourse {

  private static final long serialVersionUID = 1364767411434767491L;

  public void addOld(Course course) {
    getOlds().add(course);
  }

  public void addOlds(Collection<Course> courses) {
    getOlds().addAll(courses);
  }

  public void addNew(Course course) {
    getNews().add(course);
  }

  public void addNews(Collection<Course> courses) {
    getNews().addAll(courses);
  }

  public void exchange() {
    Set<Course> olds = CollectUtils.newHashSet(getNews());
    getNews().clear();
    addNews(getOlds());
    getOlds().clear();
    addOlds(olds);
  }

}
