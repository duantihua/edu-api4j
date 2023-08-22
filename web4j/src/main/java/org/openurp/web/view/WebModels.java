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
import org.beangle.security.codec.EncryptUtil;
import org.beangle.struts2.view.tag.AbstractModels;
import org.beangle.struts2.view.tag.TagModel;
import org.beangle.ems.app.Ems;
import org.openurp.web.view.component.*;
import org.openurp.web.view.component.semester.SemesterCalendar;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebModels extends AbstractModels {
  public WebModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    super(stack, req, res);
  }

  public String avatar_url(String userCode) {
    return Ems.Instance.getBase() + "/api/platform/user/avatars/" + EncryptUtil.encode(userCode)
        + ".jpg";
  }

  public TagModel getProjectUI() {
    return get(ProjectUI.class);
  }

  public TagModel getAvatar() {
    return get(Avatar.class);
  }

  public TagModel getSemesterBar() {
    return get(SemesterBar.class);
  }

  public TagModel getNumRange() {
    return get(NumRange.class);
  }

  public TagModel getSemesterCalendar() {
    return get(SemesterCalendar.class);
  }

  public TagModel getMenu() {
    return get(Menu.class);
  }

}
