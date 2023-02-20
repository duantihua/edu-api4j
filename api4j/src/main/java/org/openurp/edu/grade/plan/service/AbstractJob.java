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

import org.beangle.commons.dao.EntityDao;
import org.beangle.orm.hibernate.internal.SessionHolder;
import org.beangle.orm.hibernate.internal.SessionUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class AbstractJob {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SessionFactory sessionFactory;

  protected EntityDao entityDao;

  private Session getSession() {
    Session session = sessionFactory.openSession();
    session.setFlushMode(FlushMode.MANUAL);
    return session;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public final void execute() {
    Session session = getSession();
    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    try {
      doExecute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
          .unbindResource(sessionFactory);
      logger.debug("Closing single Hibernate Session in OpenSessionInViewInterceptor");
      SessionUtils.closeSession(sessionHolder.getSession());
    }
  }

  protected abstract void doExecute();
}
