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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.time.HourMinute;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Semester;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.base.model.Teacher;
import org.openurp.edu.lesson.model.Lesson;

/**
 * 排考活动
 */
@Entity(name = "org.openurp.edu.exam.model.ExamActivity")
public class ExamActivity extends LongIdObject {

  private static final long serialVersionUID = -6748665397101838909L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @NaturalId
  protected Lesson lesson;

  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Semester semester;

  /** 考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @NaturalId
  private ExamType examType;

  /** 考试日期 */
  private java.sql.Date examOn;

  /** 开始时间 */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute beginAt;

  /** 结束时间 */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute endAt;

  /** 备注 */
  @Size(max = 255)
  private String remark;

  /** 考生数量 */
  private int stdCount;

  /** 考场列表 */
  @ManyToMany
  private Set<ExamRoom> rooms = CollectUtils.newHashSet();

  /** 发布状态 */
  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private PublishState state = PublishState.None;

  /** 应考学生记录 */
  @OneToMany(mappedBy = "activity", targetEntity = ExamStudent.class)
  private List<ExamStudent> examStudents = CollectUtils.newArrayList();

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Lesson getLesson() {
    return lesson;
  }

  /**
   * 把所有的信息克隆一遍<br>
   * 不包括examTakers
   */
  public Object clone() {
    ExamActivity activity = new ExamActivity();
    activity.setExamOn(getExamOn());
    activity.setBeginAt(getBeginAt());
    activity.setEndAt(getEndAt());
    activity.setExamType(getExamType());
    activity.setLesson(getLesson());
    activity.setSemester(getSemester());
    activity.setRemark(getRemark());
    activity.setState(getState());
    for (ExamRoom examRoom : activity.getRooms()) {
      activity.getRooms().add((ExamRoom) examRoom.clone());
    }
    return activity;
  }

  public ExamActivity() {
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public void setExamStudents(List<ExamStudent> examStudents) {
    this.examStudents = examStudents;
  }

  /**
   * 得到类型和学期一致的考试的名单
   */
  public List<ExamStudent> getExamStudents() {
    return examStudents;
  }

  public List<Classroom> getClassrooms() {
    if (null == rooms || rooms.isEmpty()) return Collections.emptyList();
    else {
      List<Classroom> classrooms = CollectUtils.newArrayList();
      for (ExamRoom er : getRooms())
        classrooms.add(er.getRoom());
      return classrooms;
    }
  }

  /**
   * 返回主考老师
   */
  public List<Teacher> getExaminers() {
    if (null == rooms || rooms.isEmpty()) return Collections.emptyList();
    else {
      Set<Teacher> teachers = CollectUtils.newHashSet();
      for (ExamRoom er : getRooms())
        if (null != er.getExaminer()) teachers.add(er.getExaminer());
      return CollectUtils.newArrayList(teachers);
    }
  }

  public PublishState getState() {
    return state;
  }

  public void setState(PublishState state) {
    this.state = state;
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

  public Set<ExamRoom> getRooms() {
    return rooms;
  }

  public void setRooms(Set<ExamRoom> rooms) {
    this.rooms = rooms;
  }

  public int getStdCount() {
    return stdCount;
  }

  public void setStdCount(int stdCount) {
    this.stdCount = stdCount;
  }

}
