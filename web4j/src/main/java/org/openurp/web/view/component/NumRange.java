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

import java.math.BigDecimal;
import java.util.Map;

import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.view.component.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class NumRange extends ClosingUIBean {

  /**
   * 平常的标签
   */
  private String label, name, comment, required, format;

  /**
   * 最小值，最大值
   */
  private Object minRange, maxRange;

  /**
   * 初始最小值，初始最大值
   */
  private Object minVal, maxVal;

  /**
   * 调试用的
   */
  private Object debug;

  /**
   * 在页面上生成的两个输入框
   */
  private NumTextfield minField, maxField;

  public NumRange(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    String[] nameArray = Strings.split(name, ',');
    String[] requiredArray = Strings.split(required, ',');
    String[] commentArray = Strings.split(comment, ',');
    String[] labelArray = Strings.split(label, ',');
    String[] formatArray = Strings.split(format, ",");

    minField = new NumTextfield(stack);
    maxField = new NumTextfield(stack);
    minField.setValue(minVal);
    maxField.setValue(maxVal);
    if (nameArray != null) {
      if (nameArray.length == 1) {
        minField.setName(name + "Min");
        maxField.setName(name + "Max");
      } else {
        minField.setName(nameArray[0]);
        maxField.setName(nameArray[1]);
      }
    }
    if (requiredArray != null) {
      if (nameArray.length == 1) {
        minField.setRequired(required);
        maxField.setRequired(required);
      } else {
        minField.setRequired(requiredArray[0]);
        maxField.setRequired(requiredArray[1]);
      }
    }
    if (commentArray != null) {
      if (nameArray.length == 1) {
        minField.setRequired(comment);
        maxField.setRequired(comment);
      } else {
        minField.setRequired(commentArray[0]);
        maxField.setRequired(commentArray[1]);
      }
    }
    if (formatArray != null) {
      if (nameArray.length == 1) {
        minField.setRequired(format);
        maxField.setRequired(format);
      } else {
        minField.setRequired(formatArray[0]);
        maxField.setRequired(formatArray[1]);
      }
    }
    if (labelArray != null) {
      if (labelArray.length == 1) {
        minField.setLabel(label);
      } else {
        minField.setLabel(labelArray[0]);
        maxField.setLabel(labelArray[1]);
      }
    }
    minField.setTitle(minField.getLabel());
    maxField.setTitle(maxField.getLabel());
    minField.evalParams();
    maxField.evalParams();
    for (Map.Entry<String, Object> entry : parameters.entrySet()) {
      String key = entry.getKey();
      String[] paramVals = entry.getValue().toString().split(",");
      if (paramVals.length == 1) {
        minField.getParameters().put(key, entry.getValue());
        maxField.getParameters().put(key, entry.getValue());
      } else {
        minField.getParameters().put(key, paramVals[0]);
        maxField.getParameters().put(key, paramVals[1]);
      }
    }
    if (minRange != null) {
      if (minRange instanceof Number) {
        minField.setMin(minRange.toString());
        maxField.setMin(minRange.toString());
      }
    } else {
      maxField.setMin("#" + minField.getId());
    }
    if (maxRange != null) {
      if (maxRange instanceof Number) {
        minField.setMax(maxRange.toString());
        maxField.setMax(maxRange.toString());
      }
    } else {
      minField.setMax("#" + maxField.getId());
    }
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Object getMinVal() {
    return minVal;
  }

  public void setMinVal(Object minVal) {
    if (null != minVal) {
      if (Numbers.isDigits(minVal.toString())) {
        this.minVal = new BigDecimal(minVal.toString());
      }
    }
  }

  public Object getMaxVal() {
    return maxVal;
  }

  public void setMaxVal(Object maxVal) {
    if (null != maxVal) {
      if (Numbers.isDigits(maxVal.toString())) {
        this.maxVal = new BigDecimal(maxVal.toString());
      }
    }
  }

  public void setMinRange(Object minRange) {
    this.minRange = minRange;
  }

  public void setMaxRange(Object maxRange) {
    this.maxRange = maxRange;
  }

  public NumTextfield getMinField() {
    return minField;
  }

  public NumTextfield getMaxField() {
    return maxField;
  }

  public Object getDebug() {
    return debug;
  }

  public void setDebug(Object debug) {
    this.debug = debug;
  }

}
