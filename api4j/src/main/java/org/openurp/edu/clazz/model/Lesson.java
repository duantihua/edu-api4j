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
package org.openurp.edu.clazz.model;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.orm.hibernate.udt.HourMinute;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.base.space.model.Classroom;
import org.openurp.base.hr.model.Teacher;
import org.openurp.code.edu.model.TeachingMethod;
import org.openurp.code.edu.model.TeachingNature;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Entity(name = "org.openurp.edu.clazz.model.Lesson")
public class Lesson extends LongIdObject {
  /**
   * 教学进度表
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected TeachingPlan plan;

  private int idx;

  private java.sql.Date openOn;

  private String units;

  /** 开始时间 */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute beginAt;

  /** 结束时间 */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute endAt;

  @Size(max = 300)
  private String contents;

  @ManyToOne(fetch = FetchType.LAZY)
  private Subclazz subclazz;

  @ManyToOne(fetch = FetchType.LAZY)
  private TeachingMethod teachingMethod;

  @ManyToOne(fetch = FetchType.LAZY)
  private TeachingNature teachingNature;

  /** 授课教师列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private Set<Teacher> teachers = CollectUtils.newHashSet();

  /** 教室列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  /** 排课备注 */
  @Size(max = 500)
  private String places;

  public TeachingPlan getPlan() {
    return plan;
  }

  public void setPlan(TeachingPlan plan) {
    this.plan = plan;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public Subclazz getSubclazz() {
    return subclazz;
  }

  public void setSubclazz(Subclazz subclazz) {
    this.subclazz = subclazz;
  }

  public TeachingMethod getTeachingMethod() {
    return teachingMethod;
  }

  public void setTeachingMethod(TeachingMethod teachingMethod) {
    this.teachingMethod = teachingMethod;
  }

  public Date getOpenOn() {
    return openOn;
  }

  public void setOpenOn(Date openOn) {
    this.openOn = openOn;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public TeachingNature getTeachingNature() {
    return teachingNature;
  }

  public void setTeachingNature(TeachingNature teachingNature) {
    this.teachingNature = teachingNature;
  }

  public int getIdx() {
    return idx;
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

  public Set<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(Set<Teacher> teachers) {
    this.teachers = teachers;
  }

  public Set<Classroom> getRooms() {
    return rooms;
  }

  public void setRooms(Set<Classroom> rooms) {
    this.rooms = rooms;
  }

  public String getPlaces() {
    return places;
  }

  public void setPlaces(String places) {
    this.places = places;
  }

  public HourMinute getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(HourMinute beginAt) {
    this.beginAt = beginAt;
  }

  public HourMinute getEndAt() {
    return endAt;
  }

  public void setEndAt(HourMinute endAt) {
    this.endAt = endAt;
  }
}
