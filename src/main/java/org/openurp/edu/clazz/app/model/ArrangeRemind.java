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
package org.openurp.edu.clazz.app.model;

import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 排课提醒类<br>
 * 排课提醒id：1
 */
@Entity(name = "org.openurp.edu.clazz.app.model.ArrangeRemind")
public class ArrangeRemind extends LongIdObject {

  private static final long serialVersionUID = -6965891450697232446L;

  /** 是否开放 */
  private boolean open = false;

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }
}
