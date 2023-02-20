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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.plan.model.PlanAuditResult;
import org.openurp.edu.grade.plan.service.observers.PlanAuditObserverStack;
import org.openurp.edu.grade.plan.service.observers.PlanAuditPersistObserver;

public class AutoBatchAuditor extends AbstractJob {

  private PlanAuditService planAuditService;

  private PlanAuditPersistObserver planAuditPersistObserver;

  private int bulkSize = 15;

  @Override
  protected void doExecute() {
    OqlBuilder<Student> query = OqlBuilder.from(Student.class, "s");
    query.where("not exists(from " + PlanAuditResult.class.getName()
        + " r where r.std=s and r.updatedAt > :updatedAt)", java.sql.Date.valueOf(LocalDate.now()));

    query.where("s.state.inschool=true");
    query.where("s.beginOn <= :now and s.endOn >= :now and s.registed = true",new Date());
    query.orderBy("s.code");
    query.limit(1, bulkSize);
    List<Student> stds = entityDao.search(query);
    long startAt = System.currentTimeMillis();
    logger.info("start auto gew ...");
    PlanAuditObserverStack observerStack = new PlanAuditObserverStack(planAuditPersistObserver);
    planAuditService.batchAudit(stds, null, observerStack, null);
    if (stds.size() > 0) {
      logger.info("auto gew: " + stds.get(0).getCode() + "~" + stds.get(stds.size() - 1).getCode() + "["
          + stds.size() + "] using " + (System.currentTimeMillis() - startAt) / 1000.0 + "s");
    } else {
      logger.info("auto gew: all result is updated today!");
    }
  }

  public int getBulkSize() {
    return bulkSize;
  }

  public void setBulkSize(int bulkSize) {
    this.bulkSize = bulkSize;
  }

  public void setPlanAuditService(PlanAuditService planAuditService) {
    this.planAuditService = planAuditService;
  }

  public void setPlanAuditPersistObserver(PlanAuditPersistObserver planAuditPersistObserver) {
    this.planAuditPersistObserver = planAuditPersistObserver;
  }

}
