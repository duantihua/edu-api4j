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

import java.util.List;

import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.ClazzRestriction;

/**
 * 课程限制项命名策略
 * 主要负责命名限制项和条件组.
 */
public interface ClazzNameStrategy {
  public String genName(List<ClazzRestriction> groups);

  public String genName(Clazz clazz);

  public String genName(String fullname);

  public void autoName(Clazz clazz);
}
