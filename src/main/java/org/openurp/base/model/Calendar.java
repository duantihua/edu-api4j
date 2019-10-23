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
package org.openurp.base.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.lang.time.WeekDay;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

/**
 * 日历方案
 *
 *
 */
@Entity(name = "org.openurp.base.model.Calendar")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calendar extends NumberIdTimeObject<Integer> {

  private static final long serialVersionUID = 3782522480625235916L;

  /** 名称 */
  @NotNull
  @Size(max = 50)
  @Column(unique = true)
  private String name;

  /** 学校 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  /** 生效时间 */
  @NotNull
  protected java.sql.Date beginOn;

  /** 失效时间 */
  protected java.sql.Date endOn;
  /**
   * 星期中第一天,默认星期天
   */
  @NotNull
  @Type(type = "org.beangle.commons.lang.time.hibernate.WeekDayType")
  private WeekDay firstWeekday;
  /** 包含学期 */
  @OneToMany(mappedBy = "calendar", orphanRemoval = true)
  @OrderBy()
  @Cascade({ CascadeType.ALL })
  private List<Semester> semesters;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Semester> getSemesters() {
    return semesters;
  }

  public void setSemesters(List<Semester> semesters) {
    this.semesters = semesters;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public WeekDay getFirstWeekday() {
    return firstWeekday;
  }

  public void setFirstWeekday(WeekDay firstWeekday) {
    this.firstWeekday = firstWeekday;
  }

  public Semester getNearest() {
    long now = System.currentTimeMillis();
    long near = 0;
    long temp = Long.MAX_VALUE;
    java.sql.Date date = new java.sql.Date(now);
    Semester nearnest = null;
    for (Semester semester : semesters) {
      if (null != semester.getBeginOn() && date.toString().equals(semester.getBeginOn().toString())
          || null != semester.getEndOn()
              && date.toString().equals(semester.getEndOn().toString())) { return semester; }
      long a = Math.abs(semester.getBeginOn().getTime() - now);
      long b = Math.abs(semester.getEndOn().getTime() - now);
      near = a > b ? b : a;
      if (near < temp) {
        nearnest = semester;
        temp = near;
      }
    }
    /*
     * long now = System.currentTimeMillis(); long lasted = Long.MAX_VALUE;
     * Semester nearnest = null; for (int i = 0; i < semesters.size(); i++)
     * { Semester semester = (Semester) semesters.get(i); long distince =
     * Math.abs((semester.getBeginOn().getTime() - now +
     * semester.getEndOn().getTime() - now); if (distince < lasted) {
     * nearnest = semester; lasted = distince; } }
     */
    return nearnest;
  }
}
