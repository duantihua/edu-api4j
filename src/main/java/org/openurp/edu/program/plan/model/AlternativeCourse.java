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

import java.util.Set;

import org.beangle.commons.entity.Entity;
import org.openurp.edu.base.model.Course;

/**
 * 课程替代关系.
 *
 *
 */
public interface AlternativeCourse extends Entity<Long> {

  public Set<Course> getOlds();

  public void setOlds(Set<Course> course);

  public Set<Course> getNews();

  public void setNews(Set<Course> course);

}
