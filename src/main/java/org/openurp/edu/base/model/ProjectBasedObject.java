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
package org.openurp.edu.base.model;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.openurp.base.model.NumberIdTimeObject;

/**
 * 基于项目的实体基类
 */
@MappedSuperclass
public abstract class ProjectBasedObject<ID extends Number> extends NumberIdTimeObject<ID>
    implements ProjectBasedEntity<ID> {

  private static final long serialVersionUID = -8392607889755402883L;

  /** 项目 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  public ProjectBasedObject() {
    super();
  }

  public ProjectBasedObject(ID id) {
    super();
    this.id = id;
  }

  public ProjectBasedObject(Project project) {
    super();
    this.project = project;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
