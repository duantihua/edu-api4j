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

import java.util.List;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdObject;
import org.beangle.commons.lang.time.HourMinute;
import org.beangle.commons.lang.tuple.Pair;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.base.model.Campus;

/**
 * 每个小节的时间设置
 * </p>
 *
 * @since 2006-7-13
 */
@Entity(name = "org.openurp.base.edu.model.TimeSetting")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class TimeSetting extends NumberIdObject<Integer> {

  private static final long serialVersionUID = 3359358617662801996L;

  /** 名称 */
  @NotNull
  @Size(max = 100)
  private String name;

  /** 默认小节列表 */
  @OrderBy("indexno")
  @Cascade({ CascadeType.ALL })
  @OneToMany(targetEntity = CourseUnit.class, mappedBy = "setting")
  private List<CourseUnit> units = CollectUtils.newArrayList();

  /** 每个小节多少分钟 */
  private short minutesPerUnit;

  /** 项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 校区 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /** 生效时间 */
  @NotNull
  protected java.sql.Date beginOn;

  /** 失效时间 */
  protected java.sql.Date endOn;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<Integer, CourseUnit> getUnitMap() {
    Map<Integer, CourseUnit> rs = CollectUtils.newHashMap();
    for (CourseUnit unit : units) {
      rs.put(unit.getIndexno(), unit);
    }
    return rs;
  }

  public Pair<Integer, Integer> getUnitLevel(HourMinute first, HourMinute second) {
    int startUnit = 100;
    int endUnit = 0;
    CourseUnit testUnit = new CourseUnit(first, second);
    for (CourseUnit unit : units) {
      if (CourseUnit.isCrossWith(unit, testUnit)) {
        if (unit.getIndexno() < startUnit) startUnit = unit.getIndexno();
        if (unit.getIndexno() > endUnit) endUnit = unit.getIndexno();
      }
    }
    return Pair.of(startUnit, endUnit);
  }

  public List<CourseUnit> getUnits() {
    return units;
  }

  public void setUnits(List<CourseUnit> units) {
    this.units = units;
  }

  public short getMinutesPerUnit() {
    return minutesPerUnit;
  }

  public void setMinutesPerUnit(short minutesPerUnit) {
    this.minutesPerUnit = minutesPerUnit;
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
