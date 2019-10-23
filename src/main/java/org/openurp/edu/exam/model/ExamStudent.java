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
package org.openurp.edu.exam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.edu.lesson.model.ExamTaker;

/**
 * 应考记录
 */
@Entity(name = "org.openurp.edu.exam.model.ExamStudent")
public class ExamStudent extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -1593583921052845498L;
  /** 教学任务 */
  @NotNull
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamTaker taker;

  /** 考场 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamRoom examRoom;

  /** 考试活动 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamActivity activity;

  /** 考场座位号 */
  private Short seatNo = 0;

  public Short getSeatNo() {
    return seatNo;
  }

  public void setSeatNo(Short seatNo) {
    this.seatNo = seatNo;
  }

  public ExamStudent() {
    super();
  }

  public ExamStudent(ExamTaker taker) {
    this.taker = taker;
  }

  public ExamTaker getTaker() {
    return taker;
  }

  public void setTaker(ExamTaker taker) {
    this.taker = taker;
  }

  public ExamRoom getExamRoom() {
    return examRoom;
  }

  public void setExamRoom(ExamRoom examRoom) {
    this.examRoom = examRoom;
  }

  public ExamActivity getActivity() {
    return activity;
  }

  public void setActivity(ExamActivity activity) {
    this.activity = activity;
  }

  /**
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    try {
      ExamStudent taker = (ExamStudent) super.clone();
      taker.setId(null);
      return taker;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

}
