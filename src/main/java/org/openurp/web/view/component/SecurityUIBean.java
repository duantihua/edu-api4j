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
package org.openurp.web.view.component;

import com.opensymphony.xwork2.util.ValueStack;
import org.beangle.commons.security.DefaultRequest;
import org.beangle.security.Securities;
import org.beangle.security.authz.Authorizer;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.struts2.view.component.UIBean;

import java.io.Writer;

/**
 * 基于授权访问的bean
 *
 * @author chaostone
 * @version $Id: SecurityUIBean.java Jul 2, 2011 9:24:56 AM chaostone $
 */
public abstract class SecurityUIBean extends UIBean {

  private Authorizer authorizer;

  public SecurityUIBean(ValueStack stack, Authorizer authorizer) {
    super(stack);
    this.authorizer = authorizer;
  }

  public boolean start(Writer writer) {
    return isAuthorize(getResource());
  }

  protected boolean isAuthorize(String res) {
    if (null == res) {
      return false;
    }
    int queryIndex = res.indexOf('?');
    if (-1 != queryIndex) res = res.substring(0, queryIndex);
    if ('/' != res.charAt(0)) {
      String refer = Securities.getResource();
      res = refer.substring(0, refer.lastIndexOf("/")) + "/" + res;
    }
    if (!res.endsWith(".action")) {
      res += ".action";
    }
    return authorizer.isPermitted(SecurityContext.get(), new DefaultRequest(res, null));
  }

  protected abstract String getResource();
}
