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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.Code;
import org.openurp.code.school;

/**
 * 教学任务标签
 *
 */
@Entity(name = "org.openurp.code.edu.model.ClazzTag")
@school
public class ClazzTag extends Code<Integer> {

  private static final long serialVersionUID = 3506636994495312207L;

  public static final Integer GUAPAI = new Integer(1);

  public static enum PredefinedTags {
    GUAPAI(1), ELECTABLE(2);
    private int id;

    private PredefinedTags(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  /** 颜色 */
  @NotNull
  @Size(max = 50)
  private String color;

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
