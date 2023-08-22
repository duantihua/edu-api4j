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
package org.openurp.code.edu.model;

import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.openurp.code.industry;

/**
 * 教育类别
 *
 * @author zhouqi 2018年11月9日
 */
@Entity(name = "org.openurp.code.edu.model.EduCategory")
@industry
public class EduCategory extends Code<Integer> {

  private static final long serialVersionUID = 6690822086442394949L;

  /** 继续教育 */
  public static final Integer JXJY_ID = new Integer(1);

}
