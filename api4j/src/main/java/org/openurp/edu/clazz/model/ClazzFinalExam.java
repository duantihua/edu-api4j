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

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.orm.hibernate.udt.HourMinute;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.code.edu.model.ClassroomType;
import org.openurp.code.edu.model.ExamForm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 考试安排
 */
@Entity(name = "org.openurp.edu.clazz.model.FinalExam")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class ClazzFinalExam extends LongIdObject {

  private static final long serialVersionUID = 8657880756173823301L;
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  private java.sql.Date examOn;

  @NotNull
  @Column(name = "exam_begin_at")
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute beginAt = HourMinute.Zero;

  @NotNull
  @Column(name = "exam_end_at")
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute endAt = HourMinute.Zero;

  /**
   * 考试形式
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamForm examForm;

  /**
   * 考试教室类型
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType roomType;

  /**
   * 时长(以分钟为单位)
   */
  private short examDuration;

  public java.sql.Date getExamOn() {
    return examOn;
  }

  public void setExamOn(java.sql.Date examOn) {
    this.examOn = examOn;
  }

  public ClazzFinalExam clone() {
    try {
      ClazzFinalExam one = (ClazzFinalExam) super.clone();
      return one;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
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


  public ExamForm getExamForm() {
    return examForm;
  }

  public void setExamForm(ExamForm examForm) {
    this.examForm = examForm;
  }

  public short getExamDuration() {
    return examDuration;
  }

  public void setExamDuration(short examDuration) {
    this.examDuration = examDuration;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public ClassroomType getRoomType() {
    return roomType;
  }

  public void setRoomType(ClassroomType roomType) {
    this.roomType = roomType;
  }
}
