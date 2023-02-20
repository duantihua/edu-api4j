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

import org.beangle.commons.lang.Strings;
import org.beangle.struts2.view.component.Form;
import org.beangle.struts2.view.component.UIBean;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 基础代码
 *
 *
 */
public class Code extends UIBean {

  protected String name;
  private Object value;
  private String label;
  protected String title;

  private String keyName;
  private String valueName;

  protected String comment;
  protected String check;
  protected String required;

  protected String code;

  public Code(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == keyName) {
      keyName = "id";
      // for local
      valueName = "name";
    }
    if (null == this.id) generateIdIfEmpty();
    if (null != label) label = getText(label);
    if (null != title) {
      title = getText(title);
    } else {
      title = label;
    }
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) myform.addCheck(id, "require()");
      if (null != check) myform.addCheck(id, check);
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCheck() {
    return check;
  }

  public void setCheck(String check) {
    this.check = check;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getKeyName() {
    return keyName;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getValueName() {
    return valueName;
  }

  public void setValueName(String valueName) {
    this.valueName = valueName;
  }

  public void setOption(String option) {
    if (null != option) {
      if (Strings.contains(option, ",")) {
        keyName = Strings.substringBefore(option, ",");
        valueName = Strings.substringAfter(option, ",");
      }
    }
  }

}
