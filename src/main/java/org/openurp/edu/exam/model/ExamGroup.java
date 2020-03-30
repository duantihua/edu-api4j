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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.time.HourMinute;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.base.model.Project;

@Entity(name = "org.openurp.edu.exam.model.ExamGroup")
public class ExamGroup extends LongIdObject {

  private static final long serialVersionUID = -9155171983268795637L;

  private String name;

  /** 考试类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType = new ExamType();

  /** 项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 考试开始日期 */
  private java.sql.Date beginOn;

  /** 考试结束日期 */
  private java.sql.Date endOn;

  /**允许随堂考试*/
  private boolean allowInClass;

  /** 最小学生上课冲突人数 */
  private int minCourseConflictCount;

  /** 最大学生上课冲突比率 */
  private float maxCourseConflictRatio;

  /** 排考结果是否发布 */
  public PublishState state;

  /** 组内场次 */
  @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = { CascadeType.ALL })
  private List<DateTurn> turns = CollectUtils.newArrayList();

  /** 任务 */
  @OneToMany(mappedBy = "group")
  private List<ExamTask> tasks = CollectUtils.newArrayList();

  @ManyToMany
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  @NotNull
  private java.util.Date updatedAt;

  /** 教室分配设置 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private RoomAllocSetting allocSetting;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
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

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public PublishState getState() {
    return state;
  }

  public void setState(PublishState state) {
    this.state = state;
  }

  public List<DateTurn> getTurns() {
    return turns;
  }

  public void setTurns(List<DateTurn> turns) {
    this.turns = turns;
  }

  public List<ExamTask> getTasks() {
    return tasks;
  }

  public void setTasks(List<ExamTask> tasks) {
    this.tasks = tasks;
  }

  public Set<Classroom> getRooms() {
    return rooms;
  }

  public void setRooms(Set<Classroom> rooms) {
    this.rooms = rooms;
  }

  public int getMinCourseConflictCount() {
    return minCourseConflictCount;
  }

  public void setMinCourseConflictCount(int minCourseConflictCount) {
    this.minCourseConflictCount = minCourseConflictCount;
  }

  public float getMaxCourseConflictRatio() {
    return maxCourseConflictRatio;
  }

  public void setMaxCourseConflictRatio(float maxCourseConflictRatio) {
    this.maxCourseConflictRatio = maxCourseConflictRatio;
  }

  public List<ExamTurn> getExamTurns() {
    Set<ExamTurn> eturns = CollectUtils.newHashSet();
    for (DateTurn t : getTurns()) {
      ExamTurn et = new ExamTurn();
      et.setId(new Long(t.getBeginAt().value * 10000 + t.getEndAt().value));
      if (!eturns.contains(et)) {
        et.setBeginAt(t.getBeginAt());
        et.setEndAt(t.getEndAt());
        eturns.add(et);
      }
    }
    List<ExamTurn> turnList = CollectUtils.newArrayList(eturns);
    Collections.sort(turnList);
    return turnList;
  }

  public boolean turnExists(java.sql.Date date, HourMinute beginAt, HourMinute endAt) {
    for (DateTurn t : getTurns()) {
      if (t.getExamOn().equals(date) && t.getBeginAt().equals(beginAt) && t.getEndAt().equals(endAt))
        return true;
    }
    return false;
  }

  public DateTurn getDateTurn(java.sql.Date date, HourMinute beginAt, HourMinute endAt) {
    for (DateTurn t : getTurns()) {
      if (t.getExamOn().equals(date) && t.getBeginAt().equals(beginAt) && t.getEndAt().equals(endAt))
        return t;
    }
    return null;
  }

  public List<java.sql.Date> getDates() {
    Set<java.sql.Date> dates = CollectUtils.newHashSet();
    for (DateTurn t : getTurns()) {
      dates.add(t.getExamOn());
    }
    List<java.sql.Date> dateList = CollectUtils.newArrayList(dates);
    Collections.sort(dateList);
    return dateList;
  }

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public RoomAllocSetting getAllocSetting() {
    return allocSetting;
  }

  public void setAllocSetting(RoomAllocSetting allocSetting) {
    this.allocSetting = allocSetting;
  }

  public boolean isAllowInClass() {
    return allowInClass;
  }

  public void setAllowInClass(boolean allowInClass) {
    this.allowInClass = allowInClass;
  }

}
