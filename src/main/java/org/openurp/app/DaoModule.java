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
package org.openurp.app;

import org.beangle.commons.entity.metadata.impl.ConvertPopulatorBean;
import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.orm.hibernate.internal.HibernateModelMeta;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

public class DaoModule extends AbstractBindModule {
  @Override
  protected void doBinding() {
    bind("dataSource", AppDataSourceFactory.class);

    bind("hibernateConfig", PropertiesFactoryBean.class).property("properties",
      props("hibernate.max_fetch_depth=1", "hibernate.default_batch_fetch_size=800",
        "hibernate.batch_fetch_style=dynamic", "hibernate.jdbc.fetch_size=800",
        "hibernate.jdbc.batch_size=100", "hibernate.jdbc.batch_versioned_data=true",
        "hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
        "hibernate.temp.use_jdbc_metadata_defaults=false",
        "hibernate.jdbc.use_streams_for_binary=true", "hibernate.jdbc.use_get_generated_keys=true",
        "hibernate.cache.region.factory_class=org.hibernate.cache.EhCacheRegionFactory",
        "hibernate.cache.use_second_level_cache=true", "hibernate.cache.use_query_cache=true",
        "hibernate.query.substitutions=true 1, false 0, yes 'Y', no 'N'",
        "hibernate.show_sql=" + devEnabled()));

    bind("sessionFactory", SessionFactoryBean.class)
      .property("configurationClass", "org.beangle.orm.hibernate.internal.OverrideConfiguration")
      .property("hibernateProperties", ref("hibernateConfig"))
      .property("configLocations", "classpath*:META-INF/hibernate.cfg.xml")
      .property("persistLocations", "classpath*:META-INF/beangle/persist.properties")
      .property("staticHbm", "classpath:hibernate.hbm.xml");

    bind("transactionManager", HibernateTransactionManager.class);

    bind("baseTransactionProxy", TransactionProxyFactoryBean.class).setAbstract()
      .property("transactionAttributes", props("save*=PROPAGATION_REQUIRED", "update*=PROPAGATION_REQUIRED",
        "delete*=PROPAGATION_REQUIRED", "batch*=PROPAGATION_REQUIRED", "execute*=PROPAGATION_REQUIRED",
        "remove*=PROPAGATION_REQUIRED", "create*=PROPAGATION_REQUIRED", "init*=PROPAGATION_REQUIRED",
        "authorize*=PROPAGATION_REQUIRED", "*=PROPAGATION_REQUIRED,readOnly"));

    bind(RailsNamingStrategy.class).shortName();
    bind(DefaultTableNamingStrategy.class).property("resources",
      ";classpath*:META-INF/beangle/table.properties;classpath:beangle/table.properties");

    bind(HibernateModelMeta.class, ConvertPopulatorBean.class);

    bind("entityDao", TransactionProxyFactoryBean.class).proxy("target", HibernateEntityDao.class)
      .parent("baseTransactionProxy");

    bind(DefaultLobHandler.class).shortName();
  }

}
