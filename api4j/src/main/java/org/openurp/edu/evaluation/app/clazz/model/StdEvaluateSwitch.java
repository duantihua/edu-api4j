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
package org.openurp.edu.evaluation.app.clazz.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;

@Entity(name = "org.openurp.edu.evaluation.app.clazz.model.StdEvaluateSwitch")
public class StdEvaluateSwitch extends LongIdObject {
  private static final long serialVersionUID = -8355313886517480105L;

  /** 项目 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 开启时间 */
  private java.util.Date beginAt;

  /** 关闭时间 */
  private java.util.Date endAt;

  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Semester semester;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public java.util.Date getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(java.util.Date beginAt) {
    this.beginAt = beginAt;
  }

  public java.util.Date getEndAt() {
    return endAt;
  }

  public void setEndAt(java.util.Date endAt) {
    this.endAt = endAt;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }
}
