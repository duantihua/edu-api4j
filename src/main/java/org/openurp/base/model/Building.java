/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.asset.model.BuildingType;

/**
 * 学校教学楼
 *
 * @depend - - - Campus
 * @depend - - - Department
 */
@Entity(name = "org.openurp.base.model.Building")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Building extends AbstractBaseInfo {

  private static final long serialVersionUID = -5856318772633333467L;

  @ManyToOne(fetch = FetchType.LAZY)
  private BuildingType buildingType;

  /** 所在校区 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  /** 学校 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  public BuildingType getBuildingType() {
    return buildingType;
  }

  public void setBuildingType(BuildingType buildingType) {
    this.buildingType = buildingType;
  }

}
