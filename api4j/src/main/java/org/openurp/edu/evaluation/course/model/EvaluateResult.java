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
package org.openurp.edu.evaluation.course.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.std.model.Student;
import org.openurp.edu.clazz.model.Clazz;

@Entity(name = "org.openurp.edu.evaluation.course.model.EvaluateResult")
public class EvaluateResult extends LongIdObject {
  private static final long serialVersionUID = 7205429889311806486L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @NaturalId
  protected Clazz clazz;

  /** 学生 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student student;

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

}
