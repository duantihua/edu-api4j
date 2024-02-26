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
package org.openurp.edu.clazz.app.model.constraint;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;

/**
 * 学生个人选课门数上限<br>
 * 限制选课总门数，限制某个课程类别的课程总门数<br>
 */
@Entity(name = "org.openurp.edu.clazz.app.model.constraint.StdCourseCountConstraint")
@Table(name = "STD_COURSE_COUNT_CONS")
public class StdCourseCountConstraint extends LongIdObject implements Serializable {
  private static final long serialVersionUID = -8807239284763266997L;

  /** 学生 */
  @NotNull
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 学年学期 */
  @NotNull
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 当前学期选课的总门数上限，总门数中不包括重修的 */
  private Integer maxCourseCount;

  /**
   * 某个课程类别能够选课的门数上限
   * (不包括重修的)
   */
  @ElementCollection
  @MapKeyJoinColumn(name = "COURSE_TYPE_ID")
  @Column(name = "COURSE_COUNT")
  @CollectionTable(name = "CONS_COURS_TYPE_MAX_COUNT")
  private Map<CourseType, Integer> courseTypeMaxCourseCount = CollectUtils.newHashMap();

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Integer getMaxCourseCount() {
    return maxCourseCount;
  }

  public void setMaxCourseCount(Integer maxCourseCount) {
    this.maxCourseCount = maxCourseCount;
  }

  public Map<CourseType, Integer> getCourseTypeMaxCourseCount() {
    return courseTypeMaxCourseCount;
  }

  public void setCourseTypeMaxCourseCount(Map<CourseType, Integer> courseTypeMaxCourseCount) {
    this.courseTypeMaxCourseCount = courseTypeMaxCourseCount;
  }

  /*
   * private String remark;
   * public String getRemark() {
   * return remark;
   * }
   * public void setRemark(String remark) {
   * this.remark = remark;
   * }
   * @ElementCollection
   * @MapKeyJoinColumn(name = "COURSE_TYPE_ID")
   * @Column(name = "COURSE_COUNT")
   * @CollectionTable(name="CONS_ELECT_COURS_TYPE_COUNT")
   *//** 已选的某个课程类别的门数，不包括重修的 */
  /*
   * private Map<CourseType, Integer> electedCourseTypeCourseCount = CollectUtils.newHashMap();
   * public Integer getElectedCourseCount() {
   * return electedCourseCount;
   * }
   * public void setElectedCourseCount(Integer electedCourseCount) {
   * this.electedCourseCount = electedCourseCount;
   * }
   * public Map<CourseType, Integer> getElectedCourseTypeCourseCount() {
   * return electedCourseTypeCourseCount;
   * }
   * public void setElectedCourseTypeCourseCount(Map<CourseType, Integer>
   * electedCourseTypeCourseCount) {
   * this.electedCourseTypeCourseCount = electedCourseTypeCourseCount;
   * }
   * public void addElectedCourseCount(Clazz clazz) {
   * Integer electedCourseTypeCount = this.electedCourseTypeCourseCount.get(clazz.getCourseType());
   * if (electedCourseTypeCount == null) {
   * this.electedCourseTypeCourseCount.put(clazz.getCourseType(), 1);
   * } else {
   * this.electedCourseTypeCourseCount.put(clazz.getCourseType(),electedCourseTypeCount + 1);
   * }
   * if (this.electedCourseCount == null) {
   * this.electedCourseCount = 1;
   * } else {
   * this.electedCourseCount += 1;
   * }
   * }
   * public void removeElectedCourseCount(Clazz clazz) {
   * Integer electedCourseTypeCount = this.electedCourseTypeCourseCount.get(clazz.getCourseType());
   * if (electedCourseTypeCount == null) {
   * electedCourseTypeCount = 0;
   * }
   * electedCourseTypeCount -= 1;
   * if (electedCourseTypeCount < 0) {
   * this.electedCourseTypeCourseCount.remove(clazz.getCourseType());
   * } else {
   * this.electedCourseTypeCourseCount.put(clazz.getCourseType(), electedCourseTypeCount);
   * }
   * if (this.electedCourseCount == null) {
   * this.electedCourseCount = 0;
   * } else {
   * this.electedCourseCount = this.electedCourseCount - 1 < 0 ? 0 : this.electedCourseCount - 1;
   * }
   * }
   */
}
