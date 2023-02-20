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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.util.MakeIterator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.HierarchyEntity;
import org.beangle.struts2.view.component.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class Menu extends ClosingUIBean {
  private String label, title, multi, name, onChange, onClick, initCallback;

  // FIXME
  private String autocomplete = "false";

  private Object empty, required;

  private List<?> items = new ArrayList<Object>();

  private int allIndex = -1;

  private Option curOption;

  private String var;

  private String value;

  private Map<String, String> filters = CollectUtils.newHashMap();

  public Menu(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    if (null != label) label = getText(label, label);
    if (null != title) {
      title = getText(title);
    } else {
      title = label;
    }
    required = "true".equals(required + "");
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMulti() {
    return multi;
  }

  public void setMulti(String multi) {
    this.multi = multi;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOnChange() {
    return onChange;
  }

  public void setOnChange(String onChange) {
    this.onChange = onChange;
  }

  public String getOnClick() {
    return onClick;
  }

  public void setOnClick(String onClick) {
    this.onClick = onClick;
  }

  public String getInitCallback() {
    return initCallback;
  }

  public void setInitCallback(String initCallback) {
    this.initCallback = initCallback;
  }

  public String getAutocomplete() {
    return autocomplete;
  }

  public void setAutocomplete(String autocomplete) {
    this.autocomplete = autocomplete;
  }

  public Object getEmpty() {
    return empty;
  }

  public void setEmpty(Object empty) {
    this.empty = empty;
  }

  public Object getRequired() {
    return required;
  }

  public void setRequired(Object required) {
    this.required = required;
  }

  public List<?> getItems() {
    return items;
  }

  public void setItems(List<?> items) {
    this.items = items;
  }

  public String getVar() {
    return var;
  }

  public void setVar(String var) {
    this.var = var;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Map<String, String> getFilters() {
    return filters;
  }

  public void setFilters(Map<String, String> filters) {
    this.filters = filters;
  }

  public static class Option extends HierarchyUIBean<Option> {
    private Menu menu;
    private String var_index;
    private String allVar_index;
    private Iterator<?> iterator;
    private int index = -1;
    protected Object curObj;

    public Option(ValueStack stack) {
      super(stack);
      menu = (Menu) findAncestor(Menu.class);
      Object iteratorTarget = menu.items;
      iterator = MakeIterator.convert(iteratorTarget);
      if (!iterator.hasNext()) {
        iterator = Collections.singleton(null).iterator();
      }
      this.var_index = menu.var + "_index";
      this.var_index = menu.var + "_all_index";
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected boolean next() {
      boolean result = false;
      if (curObj instanceof HierarchyEntity) {
        if (!((HierarchyEntity) curObj).getChildren().isEmpty()) {
          menu.curOption = this;
          result = nextChild();
        }
      } else if (iterator != null && iterator.hasNext()) {
        curObj = iterator.next();
        result = true;
      } else {
        result = parentNext();
      }
      if (result) {
        index++;
        menu.allIndex++;
        stack.getContext().put(menu.var, curObj);
        stack.getContext().put(var_index, index);
        stack.getContext().put(allVar_index, menu.allIndex);
      } else {
        stack.getContext().remove(menu.var);
        stack.getContext().remove(var_index);
        stack.getContext().remove(allVar_index);
      }
      return result;
    }

    @SuppressWarnings("unchecked")
    protected boolean nextChild() {
      @SuppressWarnings("rawtypes")
      Iterator<HierarchyEntity> childIterator = ((HierarchyEntity) curObj).getChildren().iterator();
      if (childIterator.hasNext()) {
        curObj = childIterator.next();
        return true;
      }
      return false;
    }

    @Override
    protected boolean parentNext() {
      if (null != menu.curOption && null != menu.curOption.iterator && menu.curOption.iterator.hasNext()) {
        curObj = menu.curOption.iterator.next();
      }
      return false;
    }
  }
}
