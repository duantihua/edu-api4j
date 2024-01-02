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
package org.openurp.edu.exam.config;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Project;
import org.openurp.code.edu.model.ExamType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 补缓考申请开关
 */
@Entity(name = "org.openurp.edu.exam.config.ExamDeferSetting")
public class ExamDeferSetting extends LongIdObject {

  private static final long serialVersionUID = -3164169215972572221L;

  /**
   * 教学项目
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamType examType;
  /**
   * 开始时间
   */
  private Boolean applyOpened;

  private int daysBeforeApply;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

  public Boolean getApplyOpened() {
    return applyOpened;
  }

  public void setApplyOpened(Boolean applyOpened) {
    this.applyOpened = applyOpened;
  }

  public int getDaysBeforeApply() {
    return daysBeforeApply;
  }

  public void setDaysBeforeApply(int daysBeforeApply) {
    this.daysBeforeApply = daysBeforeApply;
  }
}
