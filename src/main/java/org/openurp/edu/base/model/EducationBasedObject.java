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
import javax.validation.constraints.NotNull;

import org.openurp.edu.base.code.model.Education;

/**
 * 基于培养层次的基类
 */
@MappedSuperclass
public abstract class EducationBasedObject<ID extends Number> extends ProjectBasedObject<ID>
    implements EducationBasedEntity<ID> {

  private static final long serialVersionUID = -9217621589608446691L;

  /** 培养层次 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Education education;

  public Education getEducation() {
    return education;
  }

  public void setEducation(Education education) {
    this.education = education;
  }

  public EducationBasedObject() {
    super();
  }

  public EducationBasedObject(ID id) {
    super(id);
  }

  public EducationBasedObject(Project project) {
    super(project);
  }

  public EducationBasedObject(Education education) {
    super();
    this.education = education;
  }

}
