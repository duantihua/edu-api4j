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

import com.opensymphony.xwork2.util.ValueStack;
import org.beangle.security.authz.Authorizer;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.tag.AbstractModels;
import org.beangle.struts2.view.tag.TagModel;
import org.openurp.web.view.component.Guard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author chaostone
 * @since 3.0.2
 */
public class EmsModels extends AbstractModels {

  Authorizer authorizer;

  public EmsModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    super(stack, req, res);
  }

  public TagModel getGuard() {
    TagModel model = models.get(Guard.class);
    if (null == model) {
      model = new TagModel(stack) {
        protected Component getBean() {
          return new Guard(stack, authorizer);
        }
      };
      models.put(Guard.class, model);
    }
    return model;
  }

}
