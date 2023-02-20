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
package org.openurp.edu.grade.course.service.impl;

import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.grade.course.model.StdGpa;
import org.openurp.edu.grade.course.model.StdSemesterGpa;

/**
 * 多个学生的绩点汇总
 */
public class MultiStdGpa {

  List<Semester> semesters;

  List<StdGpa> stdGpas = CollectUtils.newArrayList();

  public MultiStdGpa() {
    super();
  }

  public List<Semester> getSemesters() {
    return semesters;
  }

  public void statSemestersFromStdGpa() {
    Set<Semester> semesterFromStdGpa = CollectUtils.newHashSet();
    for (StdGpa stdGp : stdGpas) {
      for (StdSemesterGpa stdSemesterGpa : stdGp.getSemesterGpas()) {
        semesterFromStdGpa.add(stdSemesterGpa.getSemester());
      }
    }
    semesters = CollectUtils.newArrayList(semesterFromStdGpa);
  }

  public void setSemesters(List<Semester> semesters) {
    this.semesters = semesters;
  }

  public List<StdGpa> getStdGpas() {
    return stdGpas;
  }

  public void setStdGpas(List<StdGpa> stdGpas) {
    this.stdGpas = stdGpas;
  }

}
