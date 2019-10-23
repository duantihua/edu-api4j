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
package org.openurp.app.security.service;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.security.DefaultRequest;
import org.beangle.commons.security.Request;
import org.beangle.commons.web.security.RequestConvertor;
import org.beangle.commons.web.util.RequestUtils;

public class ActionRequestConvertor implements RequestConvertor {

  @Override
  public Request convert(HttpServletRequest request) {
    String sp = RequestUtils.getServletPath((HttpServletRequest) request);
    int actionIdx = sp.indexOf(".action");
    if (actionIdx > 0) {
      sp = sp.substring(0, actionIdx);
    }
    int alIdx = sp.indexOf("!");
    if (alIdx > 0) {
      sp = sp.substring(0, alIdx);
    }
    return new DefaultRequest(sp, request.getMethod());
  }

}
