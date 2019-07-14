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
import org.openurp.base.model.Department;
import org.openurp.base.model.User;
import org.openurp.edu.base.model.Teacher;

/**
 * 监考任务
 */
@Entity(name = "org.openurp.edu.exam.model.Invigilation")
public class Invigilation extends LongIdObject {

  private static final long serialVersionUID = -7734720828900446321L;

  /** 排考活动 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamRoom examRoom;

  /** 监考院系 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Department department;

  /** 监考老师 */
  @ManyToOne(fetch = FetchType.LAZY)
  protected User invigilator;

  /** 自定义监考 */
  @Size(max = 50)
  protected String invigilatorName;

  /** 是否是第一监考 */
  private boolean chief;

  /** 是否发布 */
  private boolean published;

  /** 更新时间 */
  private java.util.Date updatedAt;

  public Invigilation() {
  }

  public Invigilation(ExamRoom examRoom, User user, Department department) {
    this.invigilator = user;
    this.examRoom = examRoom;
    this.department = department;
    this.updatedAt = new java.util.Date(System.currentTimeMillis());
  }

  public Invigilation(ExamRoom examRoom, String teacherName, Department department) {
    this.invigilatorName = teacherName;
    this.examRoom = examRoom;
    this.department = department;
    this.updatedAt = new java.util.Date(System.currentTimeMillis());
  }

  public ExamRoom getExamRoom() {
    return examRoom;
  }

  public void setExamRoom(ExamRoom examRoom) {
    this.examRoom = examRoom;
  }

  public boolean isSameMonitor(Invigilation other) {
    return getDepartment().equals(other.getDepartment())
        && this.getInvigilator().equals(other.getInvigilator());
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public User getInvigilator() {
    return invigilator;
  }

  public void setInvigilator(User invigilator) {
    this.invigilator = invigilator;
  }

  public String getInvigilatorName() {
    return invigilatorName;
  }

  public void setInvigilatorName(String invigilatorName) {
    this.invigilatorName = invigilatorName;
  }

  public boolean isChief() {
    return chief;
  }

  public void setChief(boolean chief) {
    this.chief = chief;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
