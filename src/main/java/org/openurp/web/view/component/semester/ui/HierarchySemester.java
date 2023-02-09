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
package org.openurp.web.view.component.semester.ui;

import java.util.ArrayList;
import java.util.List;

import org.beangle.commons.entity.HierarchyEntity;

public class HierarchySemester implements HierarchyEntity<HierarchySemester, Integer> {
  private static final long serialVersionUID = 1306627177271312843L;

  private String name;

  private Integer id;

  private HierarchySemester parent;

  private List<HierarchySemester> children = new ArrayList<HierarchySemester>();

  public Integer getIdentifier() {
    return id;
  }

  public boolean isPersisted() {
    return false;
  }

  public boolean isTransient() {
    return true;
  }

  public HierarchySemester getParent() {
    return parent;
  }

  public void setParent(HierarchySemester parent) {
    this.parent = parent;
  }

  public List<HierarchySemester> getChildren() {
    return children;
  }

  public void setChildren(List<HierarchySemester> children) {
    this.children = children;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIndexno() {
    // TODO
    return null;
  }
}
