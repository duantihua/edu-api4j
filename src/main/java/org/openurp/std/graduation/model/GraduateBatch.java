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
package org.openurp.std.graduation.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.openurp.base.edu.model.ProjectBasedObject;
import org.openurp.base.std.model.GraduateSeason;

/**
 * 毕业审核批次<br>
 * 毕业审核批次中有很多学生名单
 */
@Entity(name = "org.openurp.std.graduation.model.GraduateBatch")
public class GraduateBatch extends ProjectBasedObject<Long> {

  private static final long serialVersionUID = -6510377955716412956L;
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GraduateSeason season;

  /** 名称 */
  @NotNull
  @Size(max = 150)
  private String name;

  @NotNull
  private Date graduateOn;

  /** 是否授学位 */
  private boolean degreeOffered;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getGraduateOn() {
    return graduateOn;
  }

  public void setGraduateOn(Date graduateOn) {
    this.graduateOn = graduateOn;
  }

  public boolean isDegreeOffered() {
    return degreeOffered;
  }

  public void setDegreeOffered(boolean degreeOffered) {
    this.degreeOffered = degreeOffered;
  }

  public GraduateSeason getSeason() {
    return season;
  }

  public void setSeason(GraduateSeason season) {
    this.season = season;
  }
}
