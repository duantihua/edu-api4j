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
package org.openurp.base.edu.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.std.model.Grade;
import org.openurp.code.edu.model.EducationLevel;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "org.openurp.base.edu.model.SchoolLength")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchoolLength extends LongIdObject {

  /**
   * 专业
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /**
   * 培养层次
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Grade fromGrade;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Grade toGrade;

  private float normal;

  private float minimum;

  private float maximum;

  public Major getMajor() {
    return major;
  }

  public void setMajor(Major major) {
    this.major = major;
  }

  public EducationLevel getLevel() {
    return level;
  }

  public void setLevel(EducationLevel level) {
    this.level = level;
  }

  public Grade getFromGrade() {
    return fromGrade;
  }

  public void setFromGrade(Grade fromGrade) {
    this.fromGrade = fromGrade;
  }

  public Grade getToGrade() {
    return toGrade;
  }

  public void setToGrade(Grade toGrade) {
    this.toGrade = toGrade;
  }

  public float getNormal() {
    return normal;
  }

  public void setNormal(float normal) {
    this.normal = normal;
  }

  public float getMinimum() {
    return minimum;
  }

  public void setMinimum(float minimum) {
    this.minimum = minimum;
  }

  public float getMaximum() {
    return maximum;
  }

  public void setMaximum(float maximum) {
    this.maximum = maximum;
  }
}
