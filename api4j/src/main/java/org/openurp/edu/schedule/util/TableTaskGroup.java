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
package org.openurp.edu.schedule.util;

import java.util.ArrayList;
import java.util.List;

import org.openurp.base.edu.code.CourseType;
import org.openurp.edu.clazz.model.Clazz;

public class TableTaskGroup {

  public TableTaskGroup() {
    super();
  }

  public TableTaskGroup(CourseType type) {
    this.type = type;
    credit = new Float(0);
    actualCredit = new Float(0);
  }

  CourseType type;

  List<Clazz> tasks = new ArrayList<Clazz>();

  Float credit;

  Float actualCredit;

  public CourseType getType() {
    return type;
  }

  public void setType(CourseType type) {
    this.type = type;
  }

  public Float getActualCredit() {
    return actualCredit;
  }

  public void setActualCredit(Float actualCredit) {
    this.actualCredit = actualCredit;
  }

  public Float getCredit() {
    return credit;
  }

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public List<Clazz> getTasks() {
    return tasks;
  }

  public void setTasks(List<Clazz> tasks) {
    this.tasks = tasks;
  }

  public void addTask(Clazz clazz) {
    tasks.add(clazz);
    if (null == actualCredit) {
      actualCredit = new Float(clazz.getCourse().getDefaultCredits());
    } else {
      actualCredit = new Float(actualCredit.floatValue() + clazz.getCourse().getDefaultCredits());
    }
  }

}
