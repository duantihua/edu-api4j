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
package org.openurp.edu.program.app;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.program.app.dao.hibernate.ExecutionPlanCourseGroupModifyApplyDaoHibernate;
import org.openurp.edu.program.app.dao.hibernate.ExecutionPlanCourseGroupModifyAuditDaoHibernate;
import org.openurp.edu.program.app.dao.hibernate.ExecutionPlanCourseModifyApplyDaoHibernate;
import org.openurp.edu.program.app.dao.hibernate.ExecutionPlanCourseModifyAuditDaoHibernate;
import org.openurp.edu.program.app.service.impl.ExecutionPlanCourseGroupModifyApplyServiceImpl;
import org.openurp.edu.program.app.service.impl.ExecutionPlanCourseGroupModifyAuditServiceImpl;
import org.openurp.edu.program.app.service.impl.ExecutionPlanCourseModifyApplyServiceImpl;
import org.openurp.edu.program.app.service.impl.ExecutionPlanCourseModifyAuditServiceImpl;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("executePlanCourseModifyApplyDao", TransactionProxyFactoryBean.class)
        .proxy("target", ExecutionPlanCourseModifyApplyDaoHibernate.class).parent("baseTransactionProxyExt");
    bind("executePlanCourseModifyAuditDao", TransactionProxyFactoryBean.class)
        .proxy("target", ExecutionPlanCourseModifyAuditDaoHibernate.class).parent("baseTransactionProxyExt");
    bind("executePlanCourseGroupModifyApplyDao", TransactionProxyFactoryBean.class)
        .proxy("target", ExecutionPlanCourseGroupModifyApplyDaoHibernate.class).parent("baseTransactionProxyExt");
    bind("executePlanCourseGroupModifyAuditDao", TransactionProxyFactoryBean.class)
        .proxy("target", ExecutionPlanCourseGroupModifyAuditDaoHibernate.class).parent("baseTransactionProxyExt");

    bind("executePlanCourseModifyApplyService", ExecutionPlanCourseModifyApplyServiceImpl.class);
    bind("executePlanCourseModifyAuditService", ExecutionPlanCourseModifyAuditServiceImpl.class);
    bind("executePlanCourseGroupModifyApplyService", ExecutionPlanCourseGroupModifyApplyServiceImpl.class);
    bind("executePlanCourseGroupModifyAuditService", ExecutionPlanCourseGroupModifyAuditServiceImpl.class);

  }

}
