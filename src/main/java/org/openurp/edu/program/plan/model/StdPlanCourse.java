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
package org.openurp.edu.program.plan.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Target;

/**
 * 个人计划的课程
 */
@Entity(name = "org.openurp.edu.program.plan.model.StdPlanCourse")
public class StdPlanCourse extends AbstractPlanCourse {

  private static final long serialVersionUID = 6931101559891478812L;

  /** 课程组 */
  @Target(StdCourseGroup.class)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseGroup group;

  public CourseGroup getGroup() {
    return group;
  }

  public void setGroup(CourseGroup group) {
    this.group = group;
  }

}
