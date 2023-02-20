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
package org.openurp.edu.grade.course.service.stat;

import static org.openurp.code.edu.model.GradeType.FINAL_ID;

import java.util.Date;

import org.beangle.commons.collection.Order;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Project;

/**
 * 报表设置
 */
public class GradeReportSetting {
  /**
   * 打印绩点
   */
  boolean printGpa;

  /** 是否打印每学期绩点 */
  boolean printTermGpa;

  /**
   * 打印成绩类型<br>
   */
  String gradeFilters;

  /**
   * 每页打印的成绩数量
   */
  Integer pageSize;

  /**
   * 成绩中的字体大小
   */
  Integer fontSize;

  /**
   * 第一专业成绩
   */
  Project project;

  /**
   * 打印奖励学分
   */
  Boolean printAwardCredit;

  /** 是否打印校外考试成绩 */
  Boolean printOtherGrade;

  /**
   * 成绩依照什么进行排序,具体含义要依照报表样式
   */
  Order order;

  /**
   * 打印成绩的类型
   */
  GradeType gradeType;

  /** 打印责任人 */
  String printBy;

  /** 打印模板 */
  String template;

  /** 打印时间 */
  Date printAt;

  public GradeReportSetting() {
    printGpa = true;
    printTermGpa = false;
    pageSize = new Integer(80);
    fontSize = new Integer(10);
    printAwardCredit = Boolean.TRUE;
    order = new Order();
    gradeType = new GradeType(FINAL_ID);
    printOtherGrade = Boolean.TRUE;
    printAt = new Date();
  }

  public Boolean getPrintOtherGrade() {
    return printOtherGrade;
  }

  public void setPrintOtherGrade(Boolean printOtherGrade) {
    this.printOtherGrade = printOtherGrade;
  }

  public Integer getFontSize() {
    return fontSize;
  }

  public void setFontSize(Integer fontSize) {
    this.fontSize = fontSize;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Boolean getPrintAwardCredit() {
    return printAwardCredit;
  }

  public void setPrintAwardCredit(Boolean printAwardCredit) {
    this.printAwardCredit = printAwardCredit;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public GradeType getGradeType() {
    return gradeType;
  }

  public void setGradeType(GradeType gradeType) {
    this.gradeType = gradeType;
  }

  public String getPrintBy() {
    return printBy;
  }

  public void setPrintBy(String printBy) {
    this.printBy = printBy;
  }

  /**
   * @return the template
   */
  public String getTemplate() {
    return template;
  }

  /**
   * @param template
   *          the template to set
   */
  public void setTemplate(String template) {
    this.template = template;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Date getPrintAt() {
    return printAt;
  }

  public void setPrintAt(Date printAt) {
    this.printAt = printAt;
  }

  public boolean isPrintGpa() {
    return printGpa;
  }

  public void setPrintGpa(boolean printGpa) {
    this.printGpa = printGpa;
  }

  public boolean isPrintTermGpa() {
    return printTermGpa;
  }

  public void setPrintTermGpa(boolean printTermGpa) {
    this.printTermGpa = printTermGpa;
  }

  public final String getGradeFilters() {
    return gradeFilters;
  }

  public final void setGradeFilters(String gradeFilters) {
    this.gradeFilters = gradeFilters;
  }
}
