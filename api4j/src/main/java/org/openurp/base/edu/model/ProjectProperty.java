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
package org.openurp.base.edu.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "org.openurp.base.edu.model.ProjectProperty")
@Cacheable
@org.hibernate.annotations.Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectProperty extends LongIdObject {
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;
  private String name;
  @Column(name = "value_", length = 4000)
  private String value;
  private String typeName;
  private String description;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
