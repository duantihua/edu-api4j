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
package org.openurp.edu.grade.course.model;

import org.openurp.base.model.Department;
import org.openurp.code.edu.model.GradeType;

public class GradeStateStat {

  private Department department;

  private Integer unpublished;

  private Integer submited;

  private Integer published;

  private GradeType gradeType;

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Integer getUnpublished() {
    return unpublished;
  }

  public void setUnpublished(Integer unpublished) {
    this.unpublished = unpublished;
  }

  public Integer getSubmited() {
    return submited;
  }

  public void setSubmited(Integer submited) {
    this.submited = submited;
  }

  public Integer getPublished() {
    return published;
  }

  public void setPublished(Integer published) {
    this.published = published;
  }

  public GradeType getGradeType() {
    return gradeType;
  }

  public void setGradeType(GradeType gradeType) {
    this.gradeType = gradeType;
  }

}
