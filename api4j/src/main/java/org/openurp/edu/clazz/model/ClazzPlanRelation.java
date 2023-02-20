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
package org.openurp.edu.clazz.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.edu.program.model.ExecutionPlan;

/**
 * 教学任务和专业培养计划关系
 * 记录教学任务和专业培养计划之间的关系，也就是专业计划生成了哪些教学任务<br>
 * clazz和plan应该是联合主键唯一
 */
@Entity(name = "org.openurp.edu.clazz.model.ClazzPlanRelation")
public class ClazzPlanRelation extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = -4283799914546064432L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /** 专业计划 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExecutionPlan plan;

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public ExecutionPlan getPlan() {
    return plan;
  }

  public void setPlan(ExecutionPlan plan) {
    this.plan = plan;
  }

}
