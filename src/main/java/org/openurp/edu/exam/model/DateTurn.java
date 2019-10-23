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
package org.openurp.edu.exam.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.time.HourMinute;
import org.hibernate.annotations.Type;

@Entity(name = "org.openurp.edu.exam.model.DateTurn")
public class DateTurn extends LongIdObject {

  private static final long serialVersionUID = 8897863916974084603L;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExamGroup group;

  private java.sql.Date examOn;

  /**
   * 开始时间 格式采用数字.800,表示8:00
   */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute beginAt;

  /**
   * 结束时间 格式采用数字.1400,表示14:00
   */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute endAt;

  private Integer stdLimit;

  public DateTurn() {
    super();
  }

  public DateTurn(Date examOn, ExamTurn turn) {
    this(examOn, turn.getBeginAt(), turn.getEndAt());
  }

  public DateTurn(Date examOn, HourMinute beginAt, HourMinute endAt) {
    super();
    this.examOn = examOn;
    this.beginAt = beginAt;
    this.endAt = endAt;
  }

  public ExamGroup getGroup() {
    return group;
  }

  public void setGroup(ExamGroup session) {
    this.group = session;
  }

  public java.sql.Date getExamOn() {
    return examOn;
  }

  public void setExamOn(java.sql.Date examOn) {
    this.examOn = examOn;
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

  public Integer getStdLimit() {
    return stdLimit;
  }

  public void setStdLimit(Integer stdLimit) {
    this.stdLimit = stdLimit;
  }

}
