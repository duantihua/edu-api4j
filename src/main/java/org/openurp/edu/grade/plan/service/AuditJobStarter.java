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

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Throwables;
import org.hibernate.SessionFactory;
import org.openurp.edu.grade.plan.service.observers.PlanAuditPersistObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditJobStarter implements Initializing {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected SessionFactory sessionFactory;

  protected EntityDao entityDao;

  private PlanAuditService planAuditService;

  private PlanAuditPersistObserver planAuditPersistObserver;

  /** 间隔 60 secs 自动刷新 */
  private static final long refreshInterval = 1000 * 60;

  @Override
  public void init() throws Exception {
    System.out.println("gew job starting...");
    new Thread(new Runnable() {

      public void run() {
        try {
          System.out.println("gew job started");
          while (true) {
            AutoBatchAuditor auditor = new AutoBatchAuditor();
            auditor.setPlanAuditPersistObserver(planAuditPersistObserver);
            auditor.setBulkSize(15);
            auditor.setPlanAuditService(planAuditService);
            auditor.setEntityDao(entityDao);
            auditor.setSessionFactory(sessionFactory);
            auditor.execute();
            Thread.sleep(refreshInterval);
          }
        } catch (InterruptedException e) {
          logger.error(Throwables.getStackTrace(e));
        }
      }
    }).start();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setPlanAuditService(PlanAuditService planAuditService) {
    this.planAuditService = planAuditService;
  }

  public void setPlanAuditPersistObserver(PlanAuditPersistObserver planAuditPersistObserver) {
    this.planAuditPersistObserver = planAuditPersistObserver;
  }
}
