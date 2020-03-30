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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.time.HourMinute;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Building;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.edu.base.model.Semester;
import org.openurp.code.edu.model.ClassroomType;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.base.model.Course;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.lesson.model.ExamTaker;
import org.openurp.edu.lesson.model.Lesson;

/**
 * 排考任务
 */
@Entity(name = "org.openurp.edu.exam.model.ExamTask")
public class ExamTask extends LongIdObject {

  private static final long serialVersionUID = -5162834520002681798L;
  /** 代码 */
  @NotNull
  @Size(max = 300)
  private String code;
  /** 项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 开课院系 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department teachDepart;

  /** 考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  /** 排考批次 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamGroup group;

  @OneToMany(mappedBy = "task")
  @OrderBy("stdCount DESC")
  private List<ExamLesson> examLessons = CollectUtils.newArrayList();

  /** 考试日期 */
  private java.sql.Date examOn;

  /** 开始时间 */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute beginAt = HourMinute.Zero;

  /** 结束时间 */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute endAt = HourMinute.Zero;

  /** 时间已经分配 */
  private boolean timeAllotted;

  /** 考生人数 */
  private int stdCount;

  /** 特定教学楼 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Building building;

  @ManyToOne(fetch = FetchType.LAZY)
  private RoomGroup roomGroup;

  /** 特定教室 */
  @ManyToMany
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  @Size(max = 255)
  private String remark;

  /** 最大学生上课冲突 */
  private Float maxCourseConflictRatio;

  /** 考试教室类型 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ClassroomType roomType;

  /** 时长(以分钟为单位) */
  private short duration;

  /** 考试周 */
  private Short examWeek;

  /** 是否院系自行安排 */
  private boolean departArranged;

  public boolean isEmptyTime() {
    return ((null == beginAt && null == endAt) || (beginAt.value == 0 && endAt.value == 0));
  }

  public Department getTeachDepart() {
    return teachDepart;
  }

  public void setTeachDepart(Department teachDepart) {
    this.teachDepart = teachDepart;
  }

  /**
   * 返回各个校区的人员分布
   *
   * @return
   */
  public Map<Campus, Integer> getCampusStdCounts() {
    Map<Campus, Integer> counts = CollectUtils.newHashMap();
    for (ExamLesson el : examLessons) {
      Integer c = counts.get(el.getLesson().getCampus());
      if (null == c) {
        counts.put(el.getLesson().getCampus(), el.getStdCount());
      } else {
        counts.put(el.getLesson().getCampus(), el.getStdCount() + c.intValue());
      }
    }
    return counts;
  }

  public void calcStdCount() {
    int a = 0;
    for (ExamLesson el : examLessons) {
      a += el.getStdCount();
    }
    this.stdCount = a;
  }

  public void buildCode() {
    this.code = getCourseCodes();
  }

  public List<ExamTaker> getExamTakers() {
    List<ExamTaker> takers = CollectUtils.newArrayList();
    for (ExamLesson el : examLessons) {
      takers.addAll(el.getExamTakers());
    }
    return takers;
  }

  public List<Lesson> getLessons() {
    List<Lesson> lessons = CollectUtils.newArrayList();
    for (ExamLesson el : examLessons) {
      lessons.add(el.getLesson());
    }
    return lessons;
  }

  public List<ExamLesson> getExamLessons() {
    return examLessons;
  }

  public void setExamLessons(List<ExamLesson> examLessons) {
    this.examLessons = examLessons;
  }

  public int getStdCount() {
    return stdCount;
  }

  public void setStdCount(int stdCount) {
    this.stdCount = stdCount;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
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

  public Building getBuilding() {
    return building;
  }

  public void setBuilding(Building building) {
    this.building = building;
  }

  public Set<Classroom> getRooms() {
    return rooms;
  }

  public void setRooms(Set<Classroom> rooms) {
    this.rooms = rooms;
  }

  public RoomGroup getRoomGroup() {
    return roomGroup;
  }

  public void setRoomGroup(RoomGroup roomGroup) {
    this.roomGroup = roomGroup;
  }

  public ExamGroup getGroup() {
    return group;
  }

  public void setGroup(ExamGroup session) {
    this.group = session;
  }

  @Override
  public String toString() {
    return "id:" + id;
  }

  public Float getMaxCourseConflictRatio() {
    return maxCourseConflictRatio;
  }

  public void setMaxCourseConflictRatio(Float maxCourseConflictRatio) {
    this.maxCourseConflictRatio = maxCourseConflictRatio;
  }

  public boolean isTimeAllotted() {
    return timeAllotted;
  }

  public void setTimeAllotted(boolean timeAllotted) {
    this.timeAllotted = timeAllotted;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Set<Course> getCourses() {
    Set<Course> cs = CollectUtils.newHashSet();
    for (ExamLesson el : examLessons) {
      cs.add(el.getLesson().getCourse());
    }
    return cs;
  }

  public String getCourseCodes() {
    Set<String> codes = new HashSet<String>();
    Set<Course> cs = getCourses();
    for (Course c : cs) {
      codes.add(c.getCode());
    }
    if (codes.size() == 1) return codes.iterator().next();
    else return Strings.join(codes.toArray(), ',');
  }

  public String getCourseNames() {
    Set<String> names = new HashSet<String>();
    Set<Course> cs = getCourses();
    for (Course c : cs) {
      names.add(c.getName());
    }
    if (names.size() == 1) return names.iterator().next();
    else return Strings.join(names.toArray(), ',');
  }

  public Short getExamWeek() {
    return examWeek;
  }

  public void setExamWeek(Short examWeek) {
    this.examWeek = examWeek;
  }

  public ClassroomType getRoomType() {
    return roomType;
  }

  public void setRoomType(ClassroomType roomType) {
    this.roomType = roomType;
  }

  public void setDuration(short duration) {
    this.duration = duration;
  }

  public short getDuration() {
    return this.duration;
  }

  public boolean isDepartArranged() {
    return departArranged;
  }

  public void setDepartArranged(boolean departArranged) {
    this.departArranged = departArranged;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
