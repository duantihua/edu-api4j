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

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.orm.hibernate.udt.HourMinute;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.ExamType;
import org.openurp.base.edu.model.Classroom;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 考场
 */
@Entity(name = "org.openurp.edu.exam.model.ExamRoom")
public class ExamRoom extends LongIdObject {

  private static final long serialVersionUID = -4159480893203938576L;
  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Semester semester;

  /** 开课院系 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Department teachDepart;

  /** 考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  /** 考试日期 */
  private java.sql.Date examOn;

  /** 开始时间 */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute beginAt;

  /** 结束时间 */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute endAt;

  /** 教室 */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Classroom room;

  /** 考生数量 */
  private int stdCount;

  /** 考试活动 */
  @ManyToMany(mappedBy = "rooms")
  private Set<ExamActivity> activities = CollectUtils.newHashSet();

  /** 监考信息 */
  @OneToMany(mappedBy = "examRoom", orphanRemoval = true, targetEntity = Invigilation.class, cascade = CascadeType.ALL)
  private Set<Invigilation> invigilations = CollectUtils.newHashSet();

  /** 应考学生记录 */
  @OneToMany(mappedBy = "examRoom", targetEntity = ExamTaker.class)
  private Set<ExamTaker> examTakers = CollectUtils.newHashSet();

  public ExamRoom() {
    super();
  }

  public ExamRoom(ExamActivity activity, Classroom room) {
    super();
    this.semester = activity.getSemester();
    this.teachDepart = activity.getClazz().getTeachDepart();
    this.examOn = activity.getExamOn();
    this.beginAt = activity.getBeginAt();
    this.endAt = activity.getEndAt();
    this.room = room;
    this.activities.add(activity);
    activity.getRooms().add(this);
  }

  public Map<Course, List<ExamTaker>> getCourseStds() {
    Map<Course, List<ExamTaker>> courseStds = CollectUtils.newHashMap();
    for (ExamTaker es : getExamTakers()) {
      Course c = es.getClazz().getCourse();
      List<ExamTaker> stdList = courseStds.get(c);
      if (null == stdList) {
        stdList = CollectUtils.newArrayList();
        courseStds.put(c, stdList);
      }
      stdList.add(es);
    }
    return courseStds;
  }

  public Department getTeachDepart() {
    return teachDepart;
  }

  public void setTeachDepart(Department teachDepart) {
    this.teachDepart = teachDepart;
  }

  /**
   * 不计较年份,比较是否是相近的考试活动.<br>
   *
   * @param other
   * @param teacher
   *          是否考虑教师
   * @param room
   *          是否考虑教室
   * @return
   */
  public boolean isSameExcept(ExamRoom other, boolean room) {
    if (room) {
      boolean sameRoom = Objects.equals(getRoom(), other.getRoom());
      if (!sameRoom) return false;
    }
    return (getExamOn().equals(other.getExamOn()))
        && (getBeginAt().equals(other.getBeginAt()) && getEndAt().equals(other.getEndAt()));
  }

  public int getStdCount() {
    return stdCount;
  }

  public void setStdCount(int stdCount) {
    this.stdCount = stdCount;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
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

  public Classroom getRoom() {
    return room;
  }

  public void setRoom(Classroom room) {
    this.room = room;
  }

  public Set<ExamActivity> getActivities() {
    return activities;
  }

  public void setActivities(Set<ExamActivity> activities) {
    this.activities = activities;
  }

  public Set<Invigilation> getInvigilations() {
    return invigilations;
  }

  public void setInvigilations(Set<Invigilation> invigilations) {
    this.invigilations = invigilations;
  }

  public Set<ExamTaker> getExamTakers() {
    return examTakers;
  }

  public void setExamTakers(Set<ExamTaker> examTakers) {
    this.examTakers = examTakers;
  }

  public void addExamTaker(ExamTaker taker) {
    taker.setExamRoom(this);
    getExamTakers().add(taker);
  }

  /**
   * 把所有的信息克隆一遍<br>
   * 不包括examTakers
   */
  public Object clone() {
    ExamRoom activity = new ExamRoom();
    activity.setExamOn(getExamOn());
    activity.setBeginAt(getBeginAt());
    activity.setEndAt(getEndAt());
    activity.setSemester(getSemester());
    return activity;
  }

  public Set<Clazz> getClazzes() {
    Set<Clazz> clazzes = CollectUtils.newHashSet();
    for (ExamActivity ea : activities) {
      clazzes.add(ea.getClazz());
    }
    return clazzes;
  }

}
