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
package org.openurp.edu.clazz.app.model.constraint;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;

/**
 * 学生个人学分上限
 */
@Entity(name = "org.openurp.edu.clazz.app.model.constraint.StdCreditConstraint")
public class StdCreditConstraint extends AbstractCreditConstraint {

  private static final long serialVersionUID = -6627564288570998553L;

  /** 学年学期 */
  @NotNull
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 学生对象 */
  @NotNull
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 平均绩点 */
  private Float GPA;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Float getGPA() {
    return GPA;
  }

  public void setGPA(Float GPA) {
    this.GPA = GPA;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }
}
