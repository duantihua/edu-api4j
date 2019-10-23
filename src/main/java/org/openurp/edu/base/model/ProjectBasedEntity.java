/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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

import javax.persistence.MappedSuperclass;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.TimeEntity;

/**
 * 基于项目的业务实体接口
 *
 *
 * @since 3.0.0
 */
@MappedSuperclass
public interface ProjectBasedEntity<ID extends Number> extends Entity<ID>, TimeEntity {

  /**
   * 获得项目
   *
   * @return 项目
   */
  public Project getProject();

  /**
   * 设置项目
   *
   * @param project
   *          项目
   */
  public void setProject(Project project);
}
