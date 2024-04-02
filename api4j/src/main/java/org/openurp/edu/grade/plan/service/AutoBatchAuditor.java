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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.QueryPage;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.plan.service.observers.PlanAuditObserverStack;
import org.openurp.edu.grade.plan.service.observers.PlanAuditPersistObserver;

import java.util.Date;
import java.util.List;

public class AutoBatchAuditor extends AbstractJob {

  private PlanAuditService planAuditService;

  private PlanAuditPersistObserver planAuditPersistObserver;

  private int bulkSize = 300;

  @Override
  protected void doExecute() {
    OqlBuilder<Student> query = OqlBuilder.from(Student.class, "s");
//    query.where("not exists(from " + PlanAuditResult.class.getName()
//        + " r where r.std=s and r.updatedAt > :updatedAt)", java.sql.Date.valueOf(LocalDate.now()));
    query.where("s.state.inschool=true");
    query.where("s.beginOn <= :now and s.endOn >= :now", new Date());
    query.orderBy("s.code");
    var page = new QueryPage<>(query, entityDao);
    query.limit(1, bulkSize);
    long startAt = System.currentTimeMillis();
    logger.info("start auto gew ...");
    List<Student> stds = CollectUtils.newArrayList();
    var stdIter = page.iterator();
    while (stdIter.hasNext()) {
      int i = 0;
      while (stdIter.hasNext() && i < bulkSize) {
        stds.add(stdIter.next());
      }
      PlanAuditObserverStack observerStack = new PlanAuditObserverStack(planAuditPersistObserver);
      planAuditService.batchAudit(stds, null, observerStack, null);
      logger.info("auto gew: " + stds.get(0).getCode() + "~" + stds.get(stds.size() - 1).getCode() + "["
          + stds.size() + "] using " + (System.currentTimeMillis() - startAt) / 1000.0 + "s");
    }
    logger.info("auto gew: all result is updated today!");
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
