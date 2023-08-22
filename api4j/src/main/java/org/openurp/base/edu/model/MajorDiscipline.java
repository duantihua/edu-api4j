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

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.TemporalOn;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.edu.model.DisciplineCategory;

@Entity(name = "org.openurp.base.edu.model.MajorDiscipline")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class MajorDiscipline extends LongIdObject implements TemporalOn {

  private static final long serialVersionUID = 3557919411080857215L;

  /** 专业 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /** 学科门类 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private DisciplineCategory category;

  /** 教育部代码 */
  @NotNull
  private String disciplineCode;

  @NotNull
  private java.sql.Date beginOn;

  private java.sql.Date endOn;

  public boolean isMatchIn(java.sql.Date d) {
    if (!this.beginOn.after(d)) {
      if (null == endOn) return true;
      else return !d.after(this.endOn);
    }else {
      return false;
    }
  }

  public Major getMajor() {
    return major;
  }

  public void setMajor(Major major) {
    this.major = major;
  }

  public DisciplineCategory getCategory() {
    return category;
  }

  public void setCategory(DisciplineCategory category) {
    this.category = category;
  }

  public String getDisciplineCode() {
    return disciplineCode;
  }

  public void setDisciplineCode(String disciplineCode) {
    this.disciplineCode = disciplineCode;
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
}
