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

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.beangle.commons.collection.CollectUtils;

/**
 * 原始计划
 */
@Entity(name = "org.openurp.edu.program.model.MajorPlan")
public class MajorPlan extends AbstractCoursePlan {

  private static final long serialVersionUID = 269841215644053574L;

  /** 课程组 */
  @OneToMany(orphanRemoval = true, targetEntity = MajorCourseGroup.class, cascade = { CascadeType.ALL })
  @OrderBy("indexno")
  @JoinColumn(name = "plan_id", nullable = false)
  private List<CourseGroup> groups = CollectUtils.newArrayList();

  public List<CourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<CourseGroup> groups) {
    this.groups = groups;
  }

  public Object clone() throws CloneNotSupportedException {
    return (MajorPlan) super.clone();
  }

  public Program getProgram() {
    return program;
  }

  public void setProgram(Program program) {
    this.program = program;
  }

  public Date getBeginOn() {
    return null != program ? program.getBeginOn() : null;
  }

  public Date getEndOn() {
    return null != program ? program.getEndOn() : null;
  }
}
