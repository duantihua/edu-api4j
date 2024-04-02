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
package org.openurp.edu.clazz.service;

import org.beangle.commons.entity.Entity;
import org.openurp.edu.clazz.model.ClazzRestriction;
import org.openurp.edu.clazz.model.ClazzRestrictionMeta;

/**
 * 课程限制条件组构建器
 *
 *
 */
public interface RestrictionBuilder {

  /**
   * 添加一到多个年级
   *
   * @param grades
   * @return
   */
  RestrictionBuilder inGrades(String... grades);

  /**
   * @param grades
   * @return
   */
  RestrictionBuilder notInGrades(String... grades);

  /**
   * 添加一到多个实体属性
   *
   * @param entities
   * @return
   */
  <T extends Entity<?>> RestrictionBuilder in(T... entities);

  /**
   * 添加一到多个实体属性
   *
   * @param entities
   * @return
   */
  <T extends Entity<?>> RestrictionBuilder notIn(T... entities);

  /**
   * 清除一个限制项
   *
   * @param meta
   * @return
   */
  RestrictionBuilder clear(ClazzRestrictionMeta meta);

  /**
   * 进行构建
   *
   * @return
   */
  ClazzRestriction build();
}
