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
package org.openurp.edu.web.action;

import org.openurp.base.edu.model.Semester;

import com.opensymphony.xwork2.ActionContext;

/**
 * 本Action作为中坚层Action多余，因为几乎所有子类都把index方法覆盖掉了
 */
public abstract class SemesterSupportAction extends RestrictionSupportAction {

  /**
   * 管理信息主页面
   */
  public String index() {
    put("stdTypeList", getStdTypes());
    put("departmentList", getColleges());
    put("project",getProject());
    indexSetting();
    return forward();
  }

  public final Semester getSemester() {
    // 为调整freemarker优先从ValueStack取semester而不从ActionContext中获取key为semester的对象
    Semester semester = (Semester) ActionContext.getContext().getContextMap().get("semester");
    return semester != null ? semester : projectContext.getSemester();
  }

}
