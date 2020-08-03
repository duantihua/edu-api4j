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
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.ExamStatus;
import org.openurp.code.edu.model.ExamType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.CourseTaker;

/**
 * 应考记录
 */
@Entity(name = "org.openurp.edu.exam.model.ExamTaker")
public class ExamTaker extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -1593583921052845498L;
  /** 排考任务 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamClazz examClazz;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /** 学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  /** 考场 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamRoom examRoom;

  /** 考试活动 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamActivity activity;

  /** 考试情況 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamStatus examStatus;

  /** 缓考申请原因/记录处分 */
  @Size(max = 255)
  private String remark;

  /** 考场座位号 */
  private Short seatNo = 0;

  public Short getSeatNo() {
    return seatNo;
  }

  public void setSeatNo(Short seatNo) {
    this.seatNo = seatNo;
  }

  public ExamTaker() {
    super();
  }

  public ExamTaker(CourseTaker taker, ExamType examType) {
    this.examStatus = new ExamStatus(ExamStatus.NORMAL);
    this.examType = examType;
    this.setStd(taker.getStd());
    this.clazz = taker.getClazz();
    this.semester = taker.getClazz().getSemester();
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public ExamClazz getExamClazz() {
    return examClazz;
  }

  public void setExamClazz(ExamClazz examClazz) {
    this.examClazz = examClazz;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
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
      ExamTaker taker = (ExamTaker) super.clone();
      taker.setId(null);
      taker.setClazz(null);
      return taker;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  public ExamStatus getExamStatus() {
    return examStatus;
  }

  public void setExamStatus(ExamStatus examStatus) {
    this.examStatus = examStatus;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
