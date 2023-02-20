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

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Teacher;
import org.openurp.edu.clazz.model.Clazz;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "org.openurp.edu.exam.model.InvigilationClazzQuota")
public class InvigilationClazzQuota extends LongIdObject {

  private static final long serialVersionUID = -3547476184143257602L;

  /**
   * 教学任务
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /**
   * 教师
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Teacher teacher;

  /**
   * 系数
   */
  @NotNull
  private float ratio;

  /**
   * 课时
   */
  @NotNull
  private float creditHours;

  /**
   * 次数
   */
  @NotNull
  private float amount;

  public InvigilationClazzQuota() {
    super();
  }

  public InvigilationClazzQuota(Clazz clazz, Teacher teacher) {
    super();
    this.clazz = clazz;
    this.teacher = teacher;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public float getRatio() {
    return ratio;
  }

  public void setRatio(float ratio) {
    this.ratio = ratio;
  }

  public float getCreditHours() {
    return creditHours;
  }

  public void setCreditHours(float creditHours) {
    this.creditHours = creditHours;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }
}
