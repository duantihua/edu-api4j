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
package org.openurp.edu.clazz.app.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 排课冲突临时对象
 */
@Entity(name = "org.openurp.edu.clazz.app.model.CollisionResource")
public class CollisionResource extends LongIdObject {

  private static final long serialVersionUID = 1L;

  /** 学年学期 */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 教学任务 */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /** 资源ID */
  @NaturalId
  private String resourceId;

  /** 资源类型 */
  @NaturalId
  @Enumerated(value = EnumType.STRING)
  private ResourceType resourceType;

  public CollisionResource() {
    super();
  }

  public CollisionResource(Semester semester, Clazz clazz, String resourceId, ResourceType resourceType) {
    super();
    this.semester = semester;
    this.clazz = clazz;
    this.resourceId = resourceId;
    this.resourceType = resourceType;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  public static enum ResourceType {
    SQUAD, CLASSROOM, TEACHER, PROGRAM;
  }
}
