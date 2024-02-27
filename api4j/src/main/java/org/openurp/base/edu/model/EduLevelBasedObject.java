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

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.openurp.code.edu.model.EducationType;
import org.openurp.code.edu.model.EducationLevel;

/**
 * 基于培养层次的基类
 */
@MappedSuperclass
public abstract class EduLevelBasedObject<ID extends Number> extends ProjectBasedObject<ID>
    implements EduLevelBasedEntity<ID> {

  private static final long serialVersionUID = -9217621589608446691L;

  /** 培养层次 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;
  /**
   * 培养类型
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationType eduType;

  public EduLevelBasedObject() {
    super();
  }

  public EduLevelBasedObject(ID id) {
    super(id);
  }

  public EduLevelBasedObject(Project project) {
    super(project);
  }

  public EduLevelBasedObject(EducationLevel level) {
    super();
    this.level = level;
  }

  public EducationLevel getLevel() {
    return level;
  }

  public void setLevel(EducationLevel level) {
    this.level = level;
  }

  public EducationType getEduType() {
    return eduType;
  }

  public void setEduType(EducationType eduType) {
    this.eduType = eduType;
  }
}
