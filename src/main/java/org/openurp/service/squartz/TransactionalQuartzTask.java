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
package org.openurp.service.squartz;

import org.beangle.orm.hibernate.internal.SessionUtils;
import org.hibernate.SessionFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * QuartzJob事务拓展类，所有定时任务中使用entityDao的类建议继承本类，可安全读取懒加载对象
 *
 *
 */
public abstract class TransactionalQuartzTask extends QuartzJobBean {

  private SessionFactory sessionFactory;

  /**
   * 给QuartzJob绑定session,使定时任务能实现懒加载
   */
  protected final void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
    SessionUtils.enableBinding(sessionFactory);
    try {
      executeTransactional(ctx);
    } finally {
      SessionUtils.disableBinding(sessionFactory);
      SessionUtils.closeSession(sessionFactory);
    }
  }

  protected abstract void executeTransactional(JobExecutionContext ctx) throws JobExecutionException;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
