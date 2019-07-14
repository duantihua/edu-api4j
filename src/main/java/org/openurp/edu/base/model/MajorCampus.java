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
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Campus;
import org.openurp.base.time.Terms;
import org.openurp.code.edu.model.EducationLevel;

/**
 * 专业与校区对应
 *
 * @author xinzhou
 */
@Entity(name = "org.openurp.edu.base.model.MajorCampus")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class MajorCampus extends IntegerIdObject implements Cloneable {

  private static final long serialVersionUID = -1991362950215673917L;

  /** 年级,形式为yyyy-p */
  @NotNull
  @Size(max = 10)
  private String grade;

  /** 专业 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /** 对应学期 */
  @NotNull
  @Type(type = "org.openurp.base.time.hibernate.TermsType")
  private Terms terms;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public Terms getTerms() {
    return terms;
  }

  public void setTerms(Terms terms) {
    this.terms = terms;
  }

  public Major getMajor() {
    return major;
  }

  public void setMajor(Major major) {
    this.major = major;
  }

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  /** 校区信息 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  public EducationLevel getLevel() {
    return level;
  }

  public void setLevel(EducationLevel level) {
    this.level = level;
  }

}
