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
package org.openurp.code.edu.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 课程类别
 *
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.edu.model.CourseType")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CourseType extends Code<Integer> {

  private static final long serialVersionUID = 8232522018765348618L;
  /**
   * 上级类别
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType parent;

  /**
   * 是否理论课:true:理论课 false:实践课
   */
  private boolean practical;

  /**是否专业课*/
  private boolean major;

  /**是否选修课*/
  private boolean optional;

  public boolean isPractical() {
    return practical;
  }

  public void setPractical(boolean practical) {
    this.practical = practical;
  }

  public CourseType() {
    super();
  }

  public CourseType(Integer id) {
    super(id);
  }

  public boolean isMajor() {
    return major;
  }

  public void setMajor(boolean major) {
    this.major = major;
  }

  public boolean isOptional() {
    return optional;
  }

  public void setOptional(boolean optional) {
    this.optional = optional;
  }

  public CourseType getParent() {
    return parent;
  }

  public void setParent(CourseType parent) {
    this.parent = parent;
  }
}
