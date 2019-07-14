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
package org.openurp.edu.course.model;

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
import org.openurp.edu.base.model.Course;
import org.openurp.edu.base.model.ProjectBasedObject;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Squad;

/**
 * 教学任务组
 */
@Entity(name = "org.openurp.edu.course.model.ClazzGroup")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class ClazzGroup extends ProjectBasedObject<Long> {

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
  private Set<Clazz> clazzes = CollectUtils.newHashSet();

  /** 任务集合 */
  @ManyToMany
  private Set<Squad> squads = CollectUtils.newHashSet();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Clazz> getClazzes() {
    return clazzes;
  }

  public void setClazzes(Set<Clazz> clazzes) {
    this.clazzes = clazzes;
  }

  public boolean addClazz(Clazz clazz) {
    if (null != clazz) {
      if (this.clazzes.add(clazz)) { return true; }
    }
    return false;
  }

  public boolean removeClazz(Clazz clazz) {
    if (null != clazz) {
      if (this.clazzes.remove(clazz)) { return true; }
    }
    return false;
  }

  public void clearClazz() {
    this.clazzes.clear();
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

  public Set<Squad> getSquads() {
    return squads;
  }

  public void setSquads(Set<Squad> classes) {
    this.squads = classes;
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
