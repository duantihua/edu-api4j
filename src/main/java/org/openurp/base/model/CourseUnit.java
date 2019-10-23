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

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.beangle.commons.lang.time.HourMinute;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.base.code.model.DayPart;

/**
 * 默认作息时间 表示一天内上课的小节信息.
 * </p>
 *
 * @since 2005-10-16
 */
@Entity(name = "org.openurp.base.model.CourseUnit")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseUnit extends NumberIdObject<Integer> implements Cloneable, Comparable<CourseUnit> {

  private static final long serialVersionUID = -3452158480415380931L;
  /** 小节编号 */
  @NotNull
  private int indexno;

  /** 小节对应的名字 */
  @NotNull
  @Size(max = 20)
  private String name;

  /** 小节对应的英文名 */
  @NotNull
  @Size(max = 50)
  private String enName;

  /** 时段 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private DayPart part;

  public DayPart getPart() {
    return part;
  }

  public void setPart(DayPart part) {
    this.part = part;
  }

  /** 开始时间 */
  @NotNull
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute beginAt;

  /** 结束时间 */
  @NotNull
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute endAt;

  /** 时间设置 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private TimeSetting setting;

  public CourseUnit() {
    super();
  }

  public CourseUnit(HourMinute beginAt, HourMinute endAt) {
    this.beginAt = beginAt;
    this.endAt = endAt;
  }

  public CourseUnit(int index, HourMinute beginAt, HourMinute endAt) {
    this.indexno = index;
    this.beginAt = beginAt;
    this.endAt = endAt;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public HourMinute getEndAt() {
    return endAt;
  }

  public void setEndAt(HourMinute endTime) {
    this.endAt = endTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HourMinute getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(HourMinute beginAt) {
    this.beginAt = beginAt;
  }

  public TimeSetting getSetting() {
    return setting;
  }

  public void setSetting(TimeSetting setting) {
    this.setting = setting;
  }

  public int getIndexno() {
    return indexno;
  }

  public void setIndexno(int index) {
    this.indexno = index;
  }

  public int compareTo(CourseUnit other) {
    return getIndexno() - other.getIndexno();
  }

  public String getColor() {
    return part.getColor();
  }

  public boolean overlapWith(HourMinute beginAt,HourMinute endAt){
    return isCrossWith(this,new CourseUnit(beginAt,endAt));
  }

  /**
   * 与其他小节是否有交错。<br>
   * 采用的是>比较是否交叉。<br>
   * 这里不同于小节比较，因为这里的时间描述的点，而小节描述的是段。
   *
   * @param unit
   * @return
   */
  public static boolean isCrossWith(CourseUnit me, CourseUnit unit) {
    return (unit.getEndAt().gt(me.getBeginAt())) && (me.getEndAt().gt(unit.getBeginAt()));
  }
}
