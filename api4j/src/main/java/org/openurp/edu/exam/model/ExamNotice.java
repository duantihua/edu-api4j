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
package org.openurp.edu.exam.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.code.edu.model.ExamType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** 考务通知 */
@Entity(name = "org.openurp.edu.exam.model.ExamNotice")
public class ExamNotice extends LongIdObject {

  private static final long serialVersionUID = -1602331947637519150L;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;

  @Size(max = 2000)
  private String studentNotice;

  @Size(max = 2000)
  private String managerNotice;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public String getStudentNotice() {
    return studentNotice;
  }

  public void setStudentNotice(String studentNotice) {
    this.studentNotice = studentNotice;
  }

  public String getManagerNotice() {
    return managerNotice;
  }

  public void setManagerNotice(String managerNotice) {
    this.managerNotice = managerNotice;
  }

}
