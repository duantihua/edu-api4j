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
package org.openurp.app.security.service;

import org.beangle.commons.bean.Initializing;
import org.beangle.security.ids.CasConfig;
import org.beangle.security.ids.session.HttpSessionRepo;

public class CasHttpSessionRepo extends HttpSessionRepo implements Initializing {

  private CasConfig config;

  @Override
  public void init() {
    this.setGeturl(config.getCasServer() + "/session/{id}?format=application/x-protobuf");
    this.setAccessUrl(config.getCasServer() + "/session/{id}/access?time={time}");
    this.setExpireUrl(config.getCasServer() + "/session/{id}/expire");
    super.init();
  }

  public void setConfig(CasConfig config) {
    this.config = config;
  }

}
