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
package org.openurp.edu.clazz.config;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * 学期排课发布
 */
@Entity(name = "org.openurp.edu.clazz.config.ScheduleSetting")
public class ScheduleSetting extends LongIdObject {

  private static final long serialVersionUID = -1830248177687127758L;

  /**
   * 学期
   */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /**
   * 项目
   */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /**
   * 是否发布*
   */
  private boolean timePublished;
  private boolean placePublished;
  private boolean clazzEditable;
  private java.util.Date beginAt;
  private java.util.Date endAt;

  public ScheduleSetting() {
  }

  public ScheduleSetting(Semester semester) {
    this.timePublished = false;
    this.placePublished = false;
    this.clazzEditable = false;
    this.semester = semester;
  }

  public ScheduleSetting(Semester semester, Project project) {
    this.timePublished = false;
    this.placePublished = false;
    this.clazzEditable = false;
    this.semester = semester;
    this.project = project;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public boolean isTimePublished() {
    return timePublished;
  }

  public void setTimePublished(boolean timePublished) {
    this.timePublished = timePublished;
  }

  public boolean isPlacePublished() {
    return placePublished;
  }

  public void setPlacePublished(boolean placePublished) {
    this.placePublished = placePublished;
  }

  public boolean isClazzEditable() {
    return clazzEditable;
  }

  public void setClazzEditable(boolean clazzEditable) {
    this.clazzEditable = clazzEditable;
  }

  public Date getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(Date beginAt) {
    this.beginAt = beginAt;
  }

  public Date getEndAt() {
    return endAt;
  }

  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }
}
