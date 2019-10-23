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
package org.openurp.edu.base.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.edu.base.code.model.CourseHourType;

/**
 * 课程分类课时信息
 *
 *
 */
@Entity(name = "org.openurp.edu.base.model.CourseHour")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseHour extends LongIdObject {

  private static final long serialVersionUID = 4265945906585570325L;

  /** 课时类型 */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private CourseHourType hourType;

  /** 学时/总课时 */
  @NotNull
  private int period;

  /** 周课时 */
  private Integer weekHour;

  /** 周数 */
  private Integer weeks;

  /** 课程 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public CourseHourType getHourType() {
    return hourType;
  }

  public void setHourType(CourseHourType type) {
    this.hourType = type;
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public Integer getWeekHour() {
    return weekHour;
  }

  public void setWeekHour(Integer weekHour) {
    this.weekHour = weekHour;
  }

  public Integer getWeeks() {
    return weeks;
  }

  public void setWeeks(Integer weeks) {
    this.weeks = weeks;
  }

}
