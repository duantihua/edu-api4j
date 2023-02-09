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
import org.beangle.security.authz.Authorizer;

import java.io.Writer;

/**
 * 对资源和内置区域进行守护
 *
 * @author chaostone
 */
public class Guard extends SecurityUIBean {

  private String res;

  public Guard(ValueStack stack, Authorizer authorizer) {
    super(stack, authorizer);
  }

  public boolean end(Writer writer, String body) {
    return end(writer, body, true);
  }

  @Override
  protected String getResource() {
    return res;
  }

  public void setRes(String res) {
    this.res = res;
  }

}
