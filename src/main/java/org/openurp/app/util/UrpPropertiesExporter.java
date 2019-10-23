/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.app.util;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.openurp.app.Urp;
import org.openurp.app.UrpApp;

public class UrpPropertiesExporter implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    for (Map.Entry<String, String> entry : Urp.Instance.getProperties().entrySet()) {
      if (entry.getKey().contains(".")) System.setProperty(entry.getKey(), entry.getValue());
    }

    for (Map.Entry<String, String> entry : UrpApp.Instance.getProperties().entrySet()) {
      if (entry.getKey().contains(".")) System.setProperty(entry.getKey(), entry.getValue());
    }

    sce.getServletContext().setAttribute("static_base", Urp.Instance.getStaticBase());
    // if (null == sce.getServletContext().getAttribute(SystemConfig.SYSTEM_CONFIG)) {
    // sce.getServletContext().setAttribute(SystemConfig.SYSTEM_CONFIG,
    // SystemConfigLoader.getConfig());
    // }

  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }

}
