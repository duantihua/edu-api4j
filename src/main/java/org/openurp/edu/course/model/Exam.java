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
package org.openurp.edu.course.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.lang.time.HourMinute;
import org.hibernate.annotations.Type;
import org.openurp.code.edu.model.ClassroomType;
import org.openurp.code.edu.model.ExamForm;
import org.openurp.code.edu.model.ExamMode;

/**
 * 考试安排
 */
@Embeddable
public class Exam implements Serializable, Cloneable {

  private static final long serialVersionUID = 8657880756173823301L;

  private java.sql.Date examOn;

  @NotNull
  @Column(name = "exam_begin_at")
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute beginAt = HourMinute.Zero;

  @NotNull
  @Column(name = "exam_end_at")
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute endAt = HourMinute.Zero;

  /** 考核方式 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamMode examMode;

  /** 考试形式 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamForm examForm;

  /** 考试教室类型 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType examRoomType;

  /** 时长(以分钟为单位) */
  private short examDuration;

  /** 是否有补考 */
  private boolean hasMakeup;

  public java.sql.Date getExamOn() {
    return examOn;
  }

  public void setExamOn(java.sql.Date examOn) {
    this.examOn = examOn;
  }

  public Exam clone() {
    try {
      Exam one = (Exam) super.clone();
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

  public ExamMode getExamMode() {
    return examMode;
  }

  public void setExamMode(ExamMode examMode) {
    this.examMode = examMode;
  }

  public ExamForm getExamForm() {
    return examForm;
  }

  public void setExamForm(ExamForm examForm) {
    this.examForm = examForm;
  }

  public ClassroomType getExamRoomType() {
    return examRoomType;
  }

  public void setExamRoomType(ClassroomType examRoomType) {
    this.examRoomType = examRoomType;
  }

  public short getExamDuration() {
    return examDuration;
  }

  public void setExamDuration(short examDuration) {
    this.examDuration = examDuration;
  }

  public boolean isHasMakeup() {
    return hasMakeup;
  }

  public void setHasMakeup(boolean hasMakeup) {
    this.hasMakeup = hasMakeup;
  }

}
