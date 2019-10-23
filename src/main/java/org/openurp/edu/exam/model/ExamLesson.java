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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.lesson.model.ExamTaker;
import org.openurp.edu.lesson.model.Lesson;

/**
 * 任务考试信息指定
 */
@Entity(name = "org.openurp.edu.exam.model.ExamLesson")
public class ExamLesson extends LongIdObject {
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExamTask task;

  /** 考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;
  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  /** 考试周 */
  private Short examWeek;

  /** 是否院系自行安排 */
  private Boolean departArranged;

  /**试卷编号*/
  private String examPaperNo;

  /** 学生数量 */
  private int stdCount;

  public List<ExamTaker> getExamTakers() {
    return lesson.getTeachclass().getExamTakers(task.getExamType());
  }

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public ExamTask getTask() {
    return task;
  }

  public void setTask(ExamTask task) {
    this.task = task;
  }

  public int getStdCount() {
    return stdCount;
  }

  public void setStdCount(int stdCount) {
    this.stdCount = stdCount;
  }

  public Short getExamWeek() {
    return examWeek;
  }

  public void setExamWeek(Short examWeek) {
    this.examWeek = examWeek;
  }

  public Boolean getDepartArranged() {
    return departArranged;
  }

  public void setDepartArranged(Boolean departArranged) {
    this.departArranged = departArranged;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public String getExamPaperNo() {
    return examPaperNo;
  }

  public void setExamPaperNo(String examPaperNo) {
    this.examPaperNo = examPaperNo;
  }

}
