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
package org.openurp.edu.base.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Building;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Room;
import org.openurp.code.edu.model.ClassroomType;

@Entity(name = "org.openurp.edu.base.model.Classroom")
@Cacheable
@Cache(region = "eams.core", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Classroom extends ProjectBasedObject<Integer> {

  private static final long serialVersionUID = -296464887575077607L;

  /**
   * 房间 可以为空，表示虚拟房间
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Room room;

  private String code;
  /**
   * 名
   */
  private String name;
  /**
   * 英文名
   */
  private String enName;

  /**
   * 简称
   */
  private String shortName;
  /**
   * 教室类型
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType roomType;

  /** 所在校区 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;
  /**
   * 上课容量
   */
  private int courseCapacity;
  /**
   * 考试容量
   */
  private int examCapacity;

  private java.sql.Date beginOn;

  private java.sql.Date endOn;

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public ClassroomType getRoomType() {
    return roomType;
  }

  public void setRoomType(ClassroomType classroomType) {
    this.roomType = classroomType;
  }

  public int getCourseCapacity() {
    return courseCapacity;
  }

  public void setCourseCapacity(int courseCapacity) {
    this.courseCapacity = courseCapacity;
  }

  public int getExamCapacity() {
    return examCapacity;
  }

  public Building getBuilding() {
    return (null == room) ? null : room.getBuilding();
  }

  public void setExamCapacity(int examCapacity) {
    this.examCapacity = examCapacity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  public Campus getCampus() {
    return campus;
  }

  public String getExamDescription(){
    return campus.getName()+" "+ getName() +" " +getRoomType().getName() +"("+ getExamCapacity()+")";
  }

}
