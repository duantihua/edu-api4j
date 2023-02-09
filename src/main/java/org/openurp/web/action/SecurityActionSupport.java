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
package org.openurp.web.action;

import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.Securities;
import org.beangle.security.core.userdetail.Profile;
import org.beangle.struts2.action.EntityDrivenAction;
import org.openurp.web.helper.SecurityHelper;

public abstract class SecurityActionSupport extends EntityDrivenAction {

  protected SecurityHelper securityHelper;

  protected List<Profile> getProfiles() {
    return securityHelper.getProfiles();
  }

  protected <T> List<T> getProperties(String name) {
    return securityHelper.getProperties(name);
  }

  protected void applyPermission(OqlBuilder<?> query) {
    securityHelper.applyPermission(query);
  }

  public void setSecurityHelper(SecurityHelper securityHelper) {
    this.securityHelper = securityHelper;
  }

  protected String getUsername() {
    return Securities.getUsername();
  }
}
