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
package org.openurp.edu.grade.plan.service;

import java.util.Collection;

import org.openurp.base.std.model.Student;
import org.openurp.service.OutputObserver;
import org.openurp.edu.grade.app.model.AuditSetting;
import org.openurp.edu.grade.plan.model.AuditPlanResult;
import org.openurp.edu.grade.plan.service.observers.PlanAuditObserverStack;

/**
 * 培养计划审核服务接口
 */
public interface PlanAuditService {

  AuditPlanResult audit(Student student, AuditPlanContext context);

  /**
   * 即时审核一个学生，结果不保存<br>
   *
   * @param student
   * @return
   */
  AuditPlanResult audit(Student student);

  void batchAudit(Collection<Student> stds, String[] auditTerms, PlanAuditObserverStack observerStack,
      OutputObserver webObserver);

  /**
   * 获得学生的计划完成审核结果<br>
   * 这个计划完成审核结果可能是部分审核的结果，也可能是全部审核的结果<br>
   * 如果作为毕业审核使用，那么就应该使用 {@link PlanAuditService#getSeriousResult(Student)}
   *
   * @param std
   * @return
   */
  AuditPlanResult getResult(Student std);

  AuditSetting getSetting(Student student);

}
