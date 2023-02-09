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
import org.beangle.struts2.view.component.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class SemesterBar extends ClosingUIBean {

  private String formName, action, target, semesterName, name, onChange, onSemesterChange, initCallback,
      label, submitValue, value, divId;

  private Object submit;

  private Object semesterEmpty;

  private Object empty;

  private Object semesterValue;

  public SemesterBar(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (Strings.isEmpty(this.id)) {
      generateIdIfEmpty();
    }
    if(null==formName){
      formName="semesterForm";
    }
  }

  /**
   * @return the formName
   */
  public String getFormName() {
    return formName;
  }

  /**
   * @param formName
   *          the formName to set
   */
  public void setFormName(String formName) {
    this.formName = formName;
  }

  /**
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * @param action
   *          the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * @return the target
   */
  public String getTarget() {
    return target;
  }

  /**
   * @param target
   *          the target to set
   */
  public void setTarget(String target) {
    this.target = target;
  }

  /**
   * @return the semesterName
   */
  public String getSemesterName() {
    return semesterName;
  }

  /**
   * @param semesterName
   *          the semesterName to set
   */
  public void setSemesterName(String semesterName) {
    this.semesterName = semesterName;
  }

  /**
   * @return the onChange
   */
  public String getOnChange() {
    return onChange;
  }

  /**
   * @param onChange
   *          the onChange to set
   */
  public void setOnChange(String onChange) {
    this.onChange = onChange;
  }

  /**
   * @return the initCallback
   */
  public String getInitCallback() {
    return initCallback;
  }

  /**
   * @param initCallback
   *          the initCallback to set
   */
  public void setInitCallback(String initCallback) {
    this.initCallback = initCallback;
  }

  /**
   * @return the onSemesterChange
   */
  public String getOnSemesterChange() {
    return onSemesterChange;
  }

  /**
   * @param onSemesterChange
   *          the onSemesterChange to set
   */
  public void setOnSemesterChange(String onSemesterChange) {
    this.onSemesterChange = onSemesterChange;
  }

  /**
   * @return the semesterEmpty
   */
  public Object getSemesterEmpty() {
    return semesterEmpty;
  }

  /**
   * @param semesterEmpty
   *          the semesterEmpty to set
   */
  public void setSemesterEmpty(Object semesterEmpty) {
    this.semesterEmpty = semesterEmpty;
  }

  /**
   * @return the empty
   */
  public Object getEmpty() {
    return empty;
  }

  /**
   * @param empty
   *          the empty to set
   */
  public void setEmpty(Object empty) {
    this.empty = empty;
  }

  /**
   * @return the semesterValue
   */
  public Object getSemesterValue() {
    return semesterValue;
  }

  /**
   * @param semesterValue
   *          the semesterValue to set
   */
  public void setSemesterValue(Object semesterValue) {
    this.semesterValue = semesterValue;
  }

  /**
   * @return the submitValue
   */
  public String getSubmitValue() {
    return submitValue;
  }

  /**
   * @param submitValue
   *          the submitValue to set
   */
  public void setSubmitValue(String submitValue) {
    this.submitValue = submitValue;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label
   *          the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @return the divId
   */
  public String getDivId() {
    return divId;
  }

  /**
   * @param divId
   *          the divId to set
   */
  public void setDivId(String divId) {
    this.divId = divId;
  }

  public Object getSubmit() {
    return submit;
  }

  public void setSubmit(Object submit) {
    this.submit = submit;
  }

}
