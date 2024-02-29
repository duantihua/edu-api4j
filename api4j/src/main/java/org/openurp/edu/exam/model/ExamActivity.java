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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.orm.hibernate.udt.HourMinute;
import org.hibernate.annotations.Type;
import org.openurp.code.edu.model.ClassroomType;
import org.openurp.code.edu.model.ExamForm;
import org.openurp.code.edu.model.ExamType;
import org.openurp.base.resource.model.Classroom;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.model.Clazz;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 排考活动
 */
@Entity(name = "org.openurp.edu.exam.model.ExamActivity")
public class ExamActivity extends LongIdObject {

  private static final long serialVersionUID = -6748665397101838909L;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExamTask task;

  /**
   * 教学任务
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Clazz clazz;

  /**
   * 学年学期
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Semester semester;

  /**
   * 考试类型
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  /** 考试周 */
  private Short examWeek;

  /** 是否集中安排 */
  private Boolean centralized;

  /**试卷编号*/
  private String examPaperNo;

  /**
   * 考试日期
   */
  private java.sql.Date examOn;

  /**
   * 开始时间
   */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute beginAt;

  /**
   * 结束时间
   */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute endAt;
  /**
   * 考试形式
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamForm examForm;
  /** 考试教室类型 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType roomType;

  /** 时长(以分钟为单位) */
  private short examDuration;
  /**
   * 备注
   */
  @Size(max = 255)
  private String remark;

  /**
   * 考生数量
   */
  private int stdCount;

  /**
   * 考场列表
   */
  @ManyToMany
  private Set<ExamRoom> rooms = CollectUtils.newHashSet();

  /**
   * 发布状态
   */
  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private PublishState publishState = PublishState.None;

  /**
   * 应考学生记录
   */
  @OneToMany(mappedBy = "activity", targetEntity = ExamTaker.class)
  private List<ExamTaker> examTakers = CollectUtils.newArrayList();

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public ExamTask getTask() {
    return task;
  }

  public void setTask(ExamTask task) {
    this.task = task;
  }

  public Short getExamWeek() {
    return examWeek;
  }

  public void setExamWeek(Short examWeek) {
    this.examWeek = examWeek;
  }

  public Boolean getCentralized() {
    return centralized;
  }

  public void setCentralized(Boolean centralized) {
    this.centralized = centralized;
  }

  public String getExamPaperNo() {
    return examPaperNo;
  }

  public void setExamPaperNo(String examPaperNo) {
    this.examPaperNo = examPaperNo;
  }

  /**
   * 把所有的信息克隆一遍<br>
   * 不包括examTakers
   */
//  public Object clone() {
//    ExamActivity activity = new ExamActivity();
//    activity.setExamOn(getExamOn());
//    activity.setBeginAt(getBeginAt());
//    activity.setEndAt(getEndAt());
//    activity.setExamType(getExamType());
//    activity.setClazz(getClazz());
//    activity.setSemester(getSemester());
//    activity.setRemark(getRemark());
//    activity.setState(getState());
//    for (ExamRoom examRoom : activity.getRooms()) {
//      activity.getRooms().add((ExamRoom) examRoom.clone());
//    }
//    return activity;
//  }

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

  public void setExamTakers(List<ExamTaker> examTakers) {
    this.examTakers = examTakers;
  }

  /**
   * 得到类型和学期一致的考试的名单
   */
  public List<ExamTaker> getExamTakers() {
    return examTakers;
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

  public PublishState getPublishState() {
    return publishState;
  }

  public void setPublishState(PublishState publishState) {
    this.publishState = publishState;
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

  public ClassroomType getRoomType() {
    return roomType;
  }

  public void setRoomType(ClassroomType roomType) {
    this.roomType = roomType;
  }

  public short getExamDuration() {
    return examDuration;
  }

  public void setExamDuration(short examDuration) {
    this.examDuration = examDuration;
  }

  public ExamForm getExamForm() {
    return examForm;
  }

  public void setExamForm(ExamForm examForm) {
    this.examForm = examForm;
  }
}
