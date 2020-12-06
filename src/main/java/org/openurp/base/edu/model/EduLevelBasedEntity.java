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

import javax.persistence.MappedSuperclass;

import org.openurp.code.edu.model.EducationLevel;

/**
 * 基于培养层次的实体接口
 * </p>
 * 基于项目和培养层次的实体接口，标准化了培养层次的属性名称。
 *
 * @see Squad
 * @see Student
 */
@MappedSuperclass
public interface EduLevelBasedEntity<ID extends Number> extends ProjectBasedEntity<ID> {

  /**
   * 获取培养层次
   *
   * @return 培养层次
   */
  public EducationLevel getLevel();

  /**
   * 设置培养层次
   *
   * @param education
   *          培养层次
   */
  public void setLevel(EducationLevel level);

}
