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
package org.openurp.web.view.component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.beangle.struts2.view.component.IterableUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class HierarchyUIBean<T extends HierarchyUIBean<?>> extends IterableUIBean {
  public HierarchyUIBean(ValueStack stack) {
    super(stack);
  }

  private T parent;

  private List<T> children = new ArrayList<T>();

  public T getParent() {
    return parent;
  }

  public void setParent(T parent) {
    this.parent = parent;
  }

  public List<T> getChildren() {
    return children;
  }

  public boolean addChild(T child) {
    return children.add(child);
  }

  protected abstract boolean nextChild();

  protected abstract boolean parentNext();

  @Override
  public boolean start(Writer writer) {
    evaluateParams();
    return next();
  }

  @Override
  public boolean doEnd(Writer writer, String body) {
    iterator(writer, body);
    return next();
  }
}
