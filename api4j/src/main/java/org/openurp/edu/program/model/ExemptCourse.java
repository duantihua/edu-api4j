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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.code.StdType;
import org.openurp.base.std.model.Grade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Cacheable
@Cache(region = "edu.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "org.openurp.edu.program.model.ExemptCourse")
public class ExemptCourse extends LongIdObject {
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Grade fromGrade;
  /**
   * 截至年级
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Grade toGrade;

  /** 免修课程 */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Course course;

  @ManyToMany
  @NotNull
  @Cache(region = "openurp.course", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<StdType> stdTypes = CollectUtils.newHashSet();

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

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Set<StdType> getStdTypes() {
    return stdTypes;
  }

  public void setStdTypes(Set<StdType> stdTypes) {
    this.stdTypes = stdTypes;
  }
}
