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

import com.opensymphony.xwork2.util.ValueStack;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.view.component.Select;

public class ProjectUI extends Select {
  private String semesterName, onChange, initCallback, onSemesterChange;

  private Object semesterEmpty;

  private Object semesterValue;

  public ProjectUI(ValueStack stack) {
    super(stack);
    if (Strings.isBlank(onChange)) {
      onChange = null;
    }
    if (Strings.isBlank(onSemesterChange)) {
      onSemesterChange = null;
    }
    if (Strings.isBlank(initCallback)) {
      initCallback = null;
    }
  }

  @Override
  protected void evaluateParams() {
    super.evaluateParams();
    Object project=getRequest().getAttribute("project");
    if(null==this.getValue() && null!=project){
      this.setValue(project);
    }
    if (null == semesterName) {
      semesterName = "semester";
    }
  }

  public Object getSemesterEmpty() {
    return semesterEmpty;
  }

  public void setSemesterEmpty(Object semesterEmpty) {
    this.semesterEmpty = semesterEmpty;
  }

  public Object getSemesterValue() {
    return semesterValue;
  }

  public void setSemesterValue(Object semesterValue) {
    this.semesterValue = semesterValue;
  }

  public String getOnChange() {
    return onChange;
  }

  public void setOnChange(String onChange) {
    this.onChange = onChange;
  }

  public String getSemesterName() {
    return semesterName;
  }

  public void setSemesterName(String semesterName) {
    this.semesterName = semesterName;
  }

  public String getOnSemesterChange() {
    return onSemesterChange;
  }

  public void setOnSemesterChange(String onSemesterChange) {
    this.onSemesterChange = onSemesterChange;
  }

  public String getInitCallback() {
    return initCallback;
  }

  public void setInitCallback(String initCallback) {
    this.initCallback = initCallback;
  }

}
