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
package org.openurp.edu.program.major.service;

import org.openurp.base.model.AuditStatus;
import org.openurp.edu.program.model.Program;
import org.openurp.edu.program.model.MajorPlan;

import java.util.List;

/**
 * 培养计划审核服务类
 */
public interface MajorPlanAuditService {

  /**
   * 审核培养计划<br>
   * 专业计划状态为：提交审核、审核不通过 的可以被审核通过<br>
   * 专业计划状态为：提交审核 的可以被审核不通过<br>
   * 专业计划状态为：未提交审核、审核通过 的不能被审核
   *
   * @param plans
   */
  void audit(List<MajorPlan> plans, AuditStatus status);

  /**
   * 提交审核申请<br>
   * 只能对“未提交”、“审核不通过”的计划提交审核申请
   *
   * @param plans
   */
  void submit(List<MajorPlan> plans);

  /**
   * 退回审核的计划<br>
   * 只能对“审核通过”的计划进行退回审核
   *
   * @param plans
   */
  void revokeAccepted(List<MajorPlan> plans);

  /**
   * 召回已经提交的培养计划<br>
   * 只能对处于“已提交”状态的培养计划进行召回审核
   *
   * @param plans
   */
  void revokeSubmitted(List<Program> plans);

  /**
   * 查找一个专业培养计划的原始计划<br>
   * 专业培养计划必须是已经审核通过的，否则会找不到它所对应的原始计划
   *
   * @param majorPlanId
   * @return
   */
  MajorPlan getMajorMajorPlan(Long majorPlanId);
}
