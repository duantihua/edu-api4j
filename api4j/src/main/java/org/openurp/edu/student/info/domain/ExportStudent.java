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
package org.openurp.edu.student.info.domain;

import org.openurp.base.std.model.Student;
import org.openurp.std.info.model.Contact;
import org.openurp.base.std.model.Graduate;
import org.openurp.std.info.model.Home;

/**
 * 学生导出模型
 */
public class ExportStudent {

  /** 是否在籍 */
  private String active;

  /** 是否在校 */
  private String inschool;

  /** 学籍状态 */
  private String stdStatus;

  /** 学生 */
  private Student std;

  /** 联系方式信息 */
  private Contact contact;

  /** 家庭信息 */
  private Home home;

  /** 毕业信息 */
  private Graduate graduate;

  public ExportStudent() {
    super();
  }

  public ExportStudent(Student std, Graduate graduate) {
    super();
    this.std = std;
    this.graduate = graduate;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

  public Home getHome() {
    return home;
  }

  public void setHome(Home home) {
    this.home = home;
  }

  public Graduate getGraduate() {
    return graduate;
  }

  public void setGraduate(Graduate graduate) {
    this.graduate = graduate;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public String getInschool() {
    return inschool;
  }

  public void setInschool(String inschool) {
    this.inschool = inschool;
  }

  public Contact getStdContact() {
    return contact;
  }

  public void setStdContact(Contact contact) {
    this.contact = contact;
  }

  public Home getStdHome() {
    return home;
  }

  public void setStdHome(Home home) {
    this.home = home;
  }

  public String getStdStatus() {
    return stdStatus;
  }

  public void setStdStatus(String stdStatus) {
    this.stdStatus = stdStatus;
  }

}
