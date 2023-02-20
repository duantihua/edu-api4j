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
import org.apache.commons.lang3.StringUtils;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.struts2.view.component.UIBean;
import org.beangle.ems.app.Ems;
import org.openurp.base.model.User;

public class Avatar extends UIBean {

  private String user;

  private String url;

  private String width = "40px";

  private String height = "55px";

  private String style;

  public Avatar(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    try {
      url = Ems.Instance.getBase() + "/api/platform/user/avatars/" + EncryptUtil.encode(user) + ".jpg";
    } catch (Exception e) {
      e.printStackTrace();
      url = StringUtils.EMPTY;
    }
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

}
