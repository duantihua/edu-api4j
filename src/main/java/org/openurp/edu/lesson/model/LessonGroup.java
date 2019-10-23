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

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.base.model.Semester;
import org.openurp.edu.base.model.Adminclass;
import org.openurp.edu.base.model.Course;
import org.openurp.edu.base.model.ProjectBasedObject;

/**
 * 教学任务组
 */
@Entity(name = "org.openurp.edu.lesson.model.LessonGroup")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class LessonGroup extends ProjectBasedObject<Long> {

  private static final long serialVersionUID = 2894364425087924997L;

  /** 组名称 */
  @Size(max = 255)
  @NotNull
  private String name;

  /** 学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 开课部门 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department teachDepart;

  /** 课程 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;

  /** 年级 */
  @Size(max = 20)
  private String grade;

  /** 校区 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /** 任务集合 */
  @OneToMany(mappedBy = "group")
  private Set<Lesson> lessons = CollectUtils.newHashSet();

  /** 任务集合 */
  @ManyToMany
  private Set<Adminclass> classes = CollectUtils.newHashSet();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(Set<Lesson> lessons) {
    this.lessons = lessons;
  }

  public boolean addLesson(Lesson lesson) {
    if (null != lesson) {
      if (this.lessons.add(lesson)) { return true; }
    }
    return false;
  }

  public boolean removeLesson(Lesson lesson) {
    if (null != lesson) {
      if (this.lessons.remove(lesson)) { return true; }
    }
    return false;
  }

  public void clearLesson() {
    this.lessons.clear();
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Department getTeachDepart() {
    return teachDepart;
  }

  public void setTeachDepart(Department teachDepart) {
    this.teachDepart = teachDepart;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Set<Adminclass> getClasses() {
    return classes;
  }

  public void setClasses(Set<Adminclass> classes) {
    this.classes = classes;
  }

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

}
