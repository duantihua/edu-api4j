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
package org.openurp.web.helper;

import jakarta.servlet.http.HttpServletRequest;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.security.Securities;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.QueryHelper;
import org.openurp.base.service.DepartmentService;
import org.openurp.base.service.SemesterService;
import org.openurp.base.service.ProjectContext;

public abstract class SearchHelper {

  protected DepartmentService departmentService;

  protected EntityDao entityDao;

  protected SemesterService semesterService;

  protected ProjectContext projectContext;

  /**
   * 从request的参数或者cookie中(参数优先)取得分页信息
   *
   * @param request
   * @return
   */
  public PageLimit getPageLimit(HttpServletRequest request) {
    PageLimit limit = new PageLimit();
    limit.setPageIndex(QueryHelper.getPageIndex());
    limit.setPageSize(QueryHelper.getPageSize());
    return limit;
  }

  /**
   * 将request中的参数设置到clazz对应的bean。 <br>
   * FIXME 加上了泛型 zq 2011-05-18
   *
   * @param request
   * @param clazz
   * @param name
   * @return
   */
  public <T> T populate(HttpServletRequest request, Class<T> clazz, String name) {
    try {
      T t = clazz.newInstance();
      Model.populate(t, Params.sub(name));
      return t;
    } catch (Exception e) {
      return null;
    }
  }

  public DepartmentService getDepartmentService() {
    return departmentService;
  }

  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  public EntityDao getEntityDao() {
    return entityDao;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

  public static String getResourceName() {
    return Securities.getResource();
  }

  public void setProjectContext(ProjectContext projectContext) {
    this.projectContext = projectContext;
  }

}
