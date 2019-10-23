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
package org.openurp.edu.grade.course.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;
import org.openurp.edu.base.code.model.CourseTakeType;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.base.code.model.ExamMode;
import org.openurp.edu.base.code.model.GradeType;
import org.openurp.edu.base.model.Course;
import org.openurp.edu.grade.AbstractGrade;
import org.openurp.edu.grade.Grade;
import org.openurp.edu.grade.course.domain.GradeTypeConstants;
import org.openurp.edu.lesson.model.CourseTaker;
import org.openurp.edu.lesson.model.Lesson;

/**
 * 课程成绩实现
 */
@Entity(name = "org.openurp.edu.grade.course.model.CourseGrade")
public class CourseGrade extends AbstractGrade {

  private static final long serialVersionUID = 1L;
  /*** 课程 */
  @NaturalId(mutable = true)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;
  /**
   * 课程序号
   * 向后兼容需要
   */
  private String lessonNo;

  /** 教学任务 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  /*** 课程类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType courseType;

  /*** 上课信息 修读类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseTakeType courseTakeType;

  /*** 考试成绩 */
  @OneToMany(mappedBy = "courseGrade", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  private Set<ExamGrade> examGrades = CollectUtils.newHashSet();

  @OneToMany(mappedBy = "courseGrade", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  private Set<GaGrade> gaGrades = CollectUtils.newHashSet();

  /*** 绩点 */
  private Float gp;

  private transient Map<GradeType, Grade> gradeMap;

  /** 考核方式 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamMode examMode;

  /** 是否免听 */
  private boolean freeListening;

  /** 备注 */
  @Size(max = 255)
  private String remark;

  public CourseGrade() {
  }

  /**
   * 依照上课名单进行实例化课程成绩
   *
   * @param take
   */
  public CourseGrade(CourseTaker taker) {
    setStd(taker.getStd());
    setLesson(taker.getLesson());
    setLessonNo(lesson.getNo());
    setSemester(lesson.getSemester());
    setCourse(lesson.getCourse());
    setCourseType(lesson.getCourseType());
    setCourseTakeType(taker.getTakeType());
    setFreeListening(taker.isFreeListening());
  }

  /**
   * 将集合映射成map<GradeType,ExamGrade>
   *
   * @return
   */
  private Map<GradeType, Grade> getGradeMap() {
    if (null == gradeMap || gradeMap.size() != (examGrades.size() + gaGrades.size())) {
      gradeMap = new HashMap<GradeType, Grade>();
      for (ExamGrade grade : examGrades) {
        gradeMap.put(grade.getGradeType(), grade);
      }
      for (GaGrade grade : gaGrades) {
        gradeMap.put(grade.getGradeType(), grade);
      }
    }
    return gradeMap;
  }

  /**
   * 找到第一个出现该成绩类别的成绩
   *
   * @param gradeType
   * @return
   */
  public Grade getGrade(GradeType gradeType) {
    return getGradeMap().get(gradeType);
  }

  /**
   * 找到第一个出现该成绩类别的成绩
   *
   * @param gradeType
   * @return
   */
  public ExamGrade getExamGrade(GradeType gradeType) {
    if (gradeType.isGa()) throw new RuntimeException(gradeType.getId() + " is not a exam grade   type.");
    return (ExamGrade) getGradeMap().get(gradeType);
  }

  public GaGrade getGaGrade(GradeType gradeType) {
    if (!gradeType.isGa()) throw new RuntimeException(gradeType.getId() + " is not a gA grade   type.");
    return (GaGrade) getGradeMap().get(gradeType);
  }

  /**
   * 添加考试成绩
   */
  public CourseGrade addExamGrade(ExamGrade examGrade) {
    getExamGrades().add(examGrade);
    this.gradeMap = null;
    examGrade.setCourseGrade(this);
    return this;
  }

  /**
   * 添加总评成绩
   */
  public CourseGrade addGaGrade(GaGrade gaGrade) {
    getGaGrades().add(gaGrade);
    this.gradeMap = null;
    gaGrade.setCourseGrade(this);
    return this;
  }

  /**
   * 查询各种成绩
   *
   * @param gradeType
   * @return
   */
  public String getScoreText(GradeType gradeType, Integer status) {
    String score = null;
    if (gradeType.getId().equals(GradeTypeConstants.FINAL_ID)) {
      score = getScoreText();
    } else {
      Grade grade = getGrade(gradeType);
      if (null == grade) return null;
      if (null == status || status.intValue() == grade.getStatus()) {
        score = grade.getScoreText();
      }
    }
    return score;
  }

  /**
   * 查询各种成绩
   *
   * @param gradeType
   * @return
   */
  public String getScoreText(GradeType gradeType) {
    return getScoreText(gradeType, null);
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public Float getGp() {
    return gp;
  }

  public void setGp(Float gp) {
    this.gp = gp;
  }

  public Set<ExamGrade> getExamGrades() {
    return examGrades;
  }

  public void setExamGrades(Set<ExamGrade> examGrades) {
    this.examGrades = examGrades;
  }

  public CourseTakeType getCourseTakeType() {
    return courseTakeType;
  }

  public void setCourseTakeType(CourseTakeType courseTakeType) {
    this.courseTakeType = courseTakeType;
  }

  public String getLessonNo() {
    return lessonNo;
  }

  public void setLessonNo(String lessonNo) {
    this.lessonNo = lessonNo;
  }

  public ExamMode getExamMode() {
    return examMode;
  }

  public void setExamMode(ExamMode examMode) {
    this.examMode = examMode;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Set<GaGrade> getGaGrades() {
    return gaGrades;
  }

  public void setGaGrades(Set<GaGrade> gaGrades) {
    this.gaGrades = gaGrades;
  }

  public boolean isFreeListening() {
    return freeListening;
  }

  public void setFreeListening(boolean freeListening) {
    this.freeListening = freeListening;
  }

}
