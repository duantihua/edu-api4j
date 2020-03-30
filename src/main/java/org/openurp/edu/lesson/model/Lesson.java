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
package org.openurp.edu.lesson.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.Model;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Target;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.base.code.model.TeachLangType;
import org.openurp.edu.base.model.AuditState;
import org.openurp.edu.base.model.Course;
import org.openurp.edu.base.model.ProjectBasedObject;
import org.openurp.edu.base.model.Teacher;
import org.openurp.edu.lesson.code.model.LessonTag;

/**
 * 教学任务
 *
 * @since 2005-10-16
 */
@Entity(name = "org.openurp.edu.lesson.model.Lesson")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "no", "semester_id", "project_id" }) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class Lesson extends ProjectBasedObject<Long> implements Cloneable {

  private static final long serialVersionUID = 1071972497531228225L;

  /** 课程序号 */
  @Size(max = 32)
  private String no;

  /** 课程 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;

  /** 课程类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType courseType;

  /** 开课院系 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department teachDepart;

  /** 授课教师 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  @OrderColumn(name = "idx", insertable = true, updatable = true, nullable = false)
  private List<Teacher> teachers = new ArrayList<Teacher>();

  /** 教学任务标签 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private List<LessonTag> tags = new ArrayList<LessonTag>();

  /** 开课校区 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /** 教学班 */
  @Target(Teachclass.class)
  private Teachclass teachclass;

  /** 教学日历 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 课程安排 */
  private CourseSchedule courseSchedule;

  /** 考试安排 */
  private ExamSchedule examSchedule;

  /** 备注 */
  @Size(max = 500)
  private String remark;

  /** 授课语言类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private TeachLangType langType;

  /** 所属课程组 */
  @ManyToOne(fetch = FetchType.LAZY)
  private LessonGroup group;

  /** 审核状态 */
  @NotNull
  @Enumerated(value = EnumType.STRING)
  private AuditState auditStatus = AuditState.UNSUBMITTED;

  /**
   * 默认构造函数
   */
  public Lesson() {
    super();
  }

  public Lesson(Long lessonId) {
    this.id = lessonId;
  }

  public static Lesson getDefault() {
    Lesson task = (Lesson) Model.newInstance(Lesson.class);
    task.setTeachclass(new Teachclass());
    task.setCourseSchedule(new CourseSchedule());
    task.setExamSchedule(new ExamSchedule());
    return task;
  }

  /**
   * 得到第一次上课时间
   *
   * @return
   */
  public Date getFirstCourseTime() {
    if (null != semester && semester.isPersisted()) {
      Date date = null;
      if (null != this.getCourseSchedule().getActivities()) {
        for (CourseActivity courseActivity : getCourseSchedule().getActivities()) {
          Date myDate = courseActivity.getFirstActivityTime();
          if (date == null || myDate.before(date)) {
            date = myDate;
          }
        }
      }
      return date;
    }
    return null;
  }

  /**
   * @return Returns the semester.
   */
  public Semester getSemester() {
    return semester;
  }

  /**
   * @param semester
   *          The semester to set.
   */
  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  /**
   * @return Returns the course.
   */
  public Course getCourse() {
    return course;
  }

  /**
   * @param course
   *          The course to set.
   */
  public void setCourse(Course course) {
    this.course = course;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  /**
   * @return Returns the courseType.
   */
  public CourseType getCourseType() {
    return courseType;
  }

  /**
   * @param courseType
   *          The courseType to set.
   */
  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public Teachclass getTeachclass() {
    return teachclass;
  }

  public void setTeachclass(Teachclass teachclass) {
    this.teachclass = teachclass;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String toString() {
    return " [id:" + this.getId() + "]  " + getCourse().getName() + " " + this.getTeachclass().getName();
  }

  public CourseSchedule getCourseSchedule() {
    return courseSchedule;
  }

  public void setCourseSchedule(CourseSchedule courseSchedule) {
    this.courseSchedule = courseSchedule;
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
  }

  public ExamSchedule getExamSchedule() {
    return examSchedule;
  }

  public void setExamSchedule(ExamSchedule examSchedule) {
    this.examSchedule = examSchedule;
  }

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  /**
   * 获得上课教师名称
   *
   * @return
   */
  public String getTeacherNames() {
    if (null != getTeachers() && !getTeachers().isEmpty()) {
      StringBuffer buf = new StringBuffer(10);
      for (Teacher teacher : getTeachers()) {
        buf.append(teacher.getName()).append(",");
      }
      buf.deleteCharAt(buf.length() - 1);
      return buf.toString();
    } else {
      return "";
    }
  }

  /**
   * 获得上课教师名称
   *
   * @return
   */
  public String getTeacherNamesWithCode() {
    if (null != getTeachers() && !getTeachers().isEmpty()) {
      StringBuffer buf = new StringBuffer(10);
      for (Teacher teacher : getTeachers()) {
        buf.append(teacher.getName()).append("[").append(teacher.getCode()).append("]").append(",");
      }
      buf.deleteCharAt(buf.length() - 1);
      return buf.toString();
    } else {
      return "";
    }
  }

  public Department getTeachDepart() {
    return teachDepart;
  }

  public void setTeachDepart(Department teachDepart) {
    this.teachDepart = teachDepart;
  }

  public TeachLangType getLangType() {
    return langType;
  }

  public void setLangType(TeachLangType langType) {
    this.langType = langType;
  }

  public List<LessonTag> getTags() {
    return tags;
  }

  public void setTags(List<LessonTag> tags) {
    this.tags = tags;
  }

  public LessonGroup getGroup() {
    return group;
  }

  public void setGroup(LessonGroup group) {
    this.group = group;
  }

  public AuditState getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(AuditState auditStatus) {
    this.auditStatus = auditStatus;
  }

  /**
   * id为null 课程序号为null
   */
  public Lesson clone() {
    try {
      Lesson one = (Lesson) super.clone();
      one.setId(null);
      one.setNo(null);
      one.setGroup(null);
      one.setTags(new ArrayList<LessonTag>());
      one.getTags().addAll(this.getTags());
      one.setTeachers(CollectUtils.<Teacher> newArrayList());
      one.getTeachers().addAll(this.getTeachers());
      one.setCourseSchedule(getCourseSchedule().clone());
      one.setExamSchedule(getExamSchedule().clone());
      one.setTeachclass(getTeachclass().clone());
      for (CourseTaker taker : one.getTeachclass().getCourseTakers()) {
        taker.setLesson(one);
      }
      for (CourseLimitGroup group : one.getTeachclass().getLimitGroups()) {
        group.setLesson(one);
      }
      one.getTeachclass().setExamTakers(new HashSet<ExamTaker>());
      Date createdAt = new Date(System.currentTimeMillis());
      one.setUpdatedAt(createdAt);

      return one;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

}
