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
package org.openurp.edu.exam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.base.model.Project;

@Entity(name = "org.openurp.edu.exam.model.RoomAllocSetting")
public class RoomAllocSetting extends LongIdObject {

  private static final long serialVersionUID = -6232074703201469078L;

  @NotNull
  @Size(max = 50)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  public float minOccupyRatio = 0;

  public int minCapacity = 0;

  private RoomAllocPolicy allocPolicy;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public float getMinOccupyRatio() {
    return minOccupyRatio;
  }

  public void setMinOccupyRatio(float minOccupyRatio) {
    this.minOccupyRatio = minOccupyRatio;
  }

  public int getMinCapacity() {
    return minCapacity;
  }

  public void setMinCapacity(int minCapacity) {
    this.minCapacity = minCapacity;
  }

  public RoomAllocPolicy getAllocPolicy() {
    return allocPolicy;
  }

  public void setAllocPolicy(RoomAllocPolicy allocPolicy) {
    this.allocPolicy = allocPolicy;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
