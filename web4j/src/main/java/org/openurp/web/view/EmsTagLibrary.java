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
package org.openurp.web.view;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import org.beangle.security.authz.Authorizer;
import org.beangle.struts2.view.tag.AbstractTagLibrary;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author chaostone
 * @since 2.4
 */
public class EmsTagLibrary extends AbstractTagLibrary {

  Authorizer authorizer;

  public Object getModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    if (null == authorizer) {
      Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
      ObjectFactory objectFactory = container.getInstance(ObjectFactory.class);
      try {
        authorizer = (Authorizer) objectFactory.buildBean("org.beangle.ems.app.security.service.RemoteAuthorizer", null, false);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    EmsModels models = new EmsModels(stack, req, res);
    models.authorizer = authorizer;
    return models;
  }

}
