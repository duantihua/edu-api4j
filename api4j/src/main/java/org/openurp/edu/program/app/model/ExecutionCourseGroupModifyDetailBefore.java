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
package org.openurp.edu.program.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** 专业计划课程组修改前信息 */
@Entity(name = "org.openurp.edu.program.app.model.ExecutionCourseGroupModifyDetailBefore")
@Table(name = "EXECUTE_GROUP_MOD_BEFORS")
public class ExecutionCourseGroupModifyDetailBefore extends ExecutionCourseGroupModifyDetail {

  private static final long serialVersionUID = 6587820760564688486L;

  public ExecutionCourseGroupModifyDetailBefore() {
    super();
  }

  /** 申请记录 */
  @OneToOne(optional = false, targetEntity = ExecutionCourseGroupModify.class, mappedBy = "oldPlanCourseGroup")
  @JoinColumn(name = "MA_PLAN_CG_MOD_B_APPLY")
  protected ExecutionCourseGroupModify apply;

  public ExecutionCourseGroupModify getApply() {
    return apply;
  }

  public void setApply(ExecutionCourseGroupModify apply) {
    this.apply = apply;
  }

}
