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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.openurp.edu.base.code.model.GradeType;

/**
 * 考试成绩状态
 */
@Entity(name = "org.openurp.edu.grade.course.model.GaGradeState")
public class GaGradeState extends AbstractGradeState {

  private static final long serialVersionUID = 5943717907400211688L;

  /** 成绩类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GradeType gradeType;

  /** 课程成绩状态 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseGradeState gradeState;

  /** 备注 */
  private String remark;

  public GaGradeState() {
    super();
  }

  public CourseGradeState getGradeState() {
    return gradeState;
  }

  public void setGradeState(CourseGradeState gradeState) {
    this.gradeState = gradeState;
  }

  public GradeType getGradeType() {
    return gradeType;
  }

  public void setGradeType(GradeType gradeType) {
    this.gradeType = gradeType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
