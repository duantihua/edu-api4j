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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.beangle.struts2.view.component.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class NumTextfield extends ClosingUIBean {
  private String name, label, title, comment, check, format;

  private Object required;

  private Object value = "";

  /*
   * 最小值，如果以#开头，那么就说明数值不能比某个id为...的值小
   */
  private String min;

  /*
   * 最大值，如果以#开头，那么就说明数值不能比某个id为...的值大
   */
  private String max;

  public NumTextfield(ValueStack stack) {
    super(stack);
    if (null == this.id) generateIdIfEmpty();
  }

  void evalParams() {
    super.evaluateParams();
    if (null == this.name) this.name = this.id;
    if (null == required) required = false;
    else required = "1".equals(required.toString()) || "true".equals(required.toString());
    if (null != format) {
      NumberFormat numberFormat = new DecimalFormat(format);
      try {
        this.value = numberFormat.format(numberFormat.parseObject(value.toString()));
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Object getRequired() {
    return required;
  }

  public void setRequired(Object required) {
    this.required = Boolean.TRUE.toString().equals(required + "");
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getMin() {
    return min;
  }

  public String getMax() {
    return max;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public void setMax(String max) {
    this.max = max;
  }
}
