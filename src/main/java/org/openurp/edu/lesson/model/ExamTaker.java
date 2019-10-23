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
package org.openurp.edu.lesson.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.edu.base.code.model.ExamStatus;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.base.model.Student;

/**
 * 应考记录
 */
@Entity(name = "org.openurp.edu.lesson.model.ExamTaker")
public class ExamTaker extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -1593583921052845498L;
  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType = new ExamType();

  /** 考试情况 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamStatus examStatus = new ExamStatus();

  /** 缓考申请原因/记录处分 */
  @Size(max = 255)
  private String remark;

  public ExamTaker() {
    super();
  }

  public ExamTaker(CourseTaker taker,ExamType examType) {
    this.examStatus = new ExamStatus(ExamStatus.NORMAL);
    this.examType=examType;
    this.setStd(taker.getStd());
    this.lesson = taker.getLesson();
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

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  /**
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    try {
      ExamTaker taker = (ExamTaker) super.clone();
      taker.setId(null);
      taker.setLesson(null);
      return taker;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
