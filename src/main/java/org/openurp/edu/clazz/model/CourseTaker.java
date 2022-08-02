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
package org.openurp.edu.clazz.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.beangle.commons.lang.Objects;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.edu.code.CourseType;
import org.openurp.code.edu.model.CourseTakeType;
import org.openurp.code.edu.model.ElectionMode;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.edu.model.Student;

/**
 * 学生修读课程信息
 * 同一个task的构成在一个教学班中
 */
@Entity(name = "org.openurp.edu.clazz.model.CourseTaker")
public class CourseTaker extends NumberIdTimeObject<Long> implements Cloneable, Comparable<CourseTaker> {

  private static final long serialVersionUID = -4305006607087691491L;

  /** 教学任务 */
  @NaturalId(mutable = true)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;
  /** 学生 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType courseType;

  /** 修读类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseTakeType takeType;

  /** 选课方式 **/
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ElectionMode electionMode;

  /** 是否属于替代选课 */
  private boolean alternative = false;

  /** 是否免听 */
  private boolean freeListening = false;

  /** 备注 */
  private String remark;

  /** 课程分组 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Subclazz subclazz;

  public CourseTaker() {
    Date now = new Date();
    this.updatedAt = now;
  }

  public CourseTaker(Long takerId) {
    this();
    setId(takerId);
  }

  public CourseTaker(Clazz task, Student student, CourseTakeType courseTakeType) {
    this();
    this.clazz = task;
    this.semester = task.getSemester();
    this.course = task.getCourse();
    this.std = student;
    this.takeType = courseTakeType;
  }

  /**
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    try {
      CourseTaker taker = (CourseTaker) super.clone();
      taker.setId(null);
      taker.setClazz(null);
      return taker;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }

  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  /**
   * @return Returns the takeType.
   */
  public CourseTakeType getTakeType() {
    return takeType;
  }

  /**
   * @param takeType
   *          The takeType to set.
   */
  public void setTakeType(CourseTakeType courseTakeType) {
    this.takeType = courseTakeType;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz task) {
    this.clazz = task;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public boolean equals(Object object) {
    if (!(object instanceof CourseTaker)) { return false; }
    // FIXME 去掉教学任务的判断，实际上像一个新的教学任务添加学生时会报错
    CourseTaker rhs = (CourseTaker) object;
    return Objects.equalsBuilder().add(getClazz(), rhs.getClazz()).add(getStd(), rhs.getStd()).isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return (null != getStd() && null != getStd().getId()) ? getStd().getId().hashCode() : 29;
  }

  /**
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(CourseTaker other) {
    return Objects.compareBuilder().add(this.std.getUser().getCode(), other.getStd().getUser().getCode())
        .toComparison();
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public ElectionMode getElectionMode() {
    return electionMode;
  }

  public void setElectionMode(ElectionMode electionMode) {
    this.electionMode = electionMode;
  }

  public boolean isFreeListening() {
    return freeListening;
  }

  public void setFreeListening(boolean freeListening) {
    this.freeListening = freeListening;
  }

  public Subclazz getSubclazz() {
    return subclazz;
  }

  public void setSubclazz(Subclazz subclazz) {
    this.subclazz = subclazz;
  }

  public boolean isAlternative() {
    return alternative;
  }

  public void setAlternative(boolean alternative) {
    this.alternative = alternative;
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }
}
