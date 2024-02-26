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
/*
 *
 */
package org.openurp.edu.web.action;

import java.util.List;

import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.std.model.StdType;
import org.openurp.base.edu.model.Project;
import org.openurp.base.service.ProjectContext;
import org.openurp.service.security.DataRealm;

/**
 * 数据级权限支持类
 */
public abstract class RestrictionSupportAction extends AdminBaseAction {

  /**
   * FIXME 有问题
   *
   * <pre>
   * stdTypeList, dartmentList 并不是在所有在页面上都被使用的
   * </pre>
   */
  public String index() {
    put("stdTypeList", getStdTypes());
    put("departmentList", getColleges());
    indexSetting();
    return forward();
  }

  /**
   * 获得用户所拥有的所有教学项目维度上的数据级权限
   *
   * @return
   */
  protected List<Project> getProjects() {
    return projectContext.getProjects();
  }

  /**
   * 获得用户当前所处的教学项目
   *
   * @return
   */
  protected Project getProject() {
    return projectContext.getProject();
  }

  protected List<EducationLevel> getLevels() {
    return projectContext.getEducationLevels();
  }

  protected List<StdType> getStdTypes() {
    return projectContext.getStdTypes();
  }

  protected List<Department> getDeparts() {
    return projectContext.getDeparts();
  }

  protected List<Department> getColleges() {
    return projectContext.getColleges();
  }

  protected List<Department> getTeachDeparts() {
    return projectContext.getTeachDeparts();
  }

  protected String getDepartIdSeq() {
    return projectContext.getDepartIdSeq();
  }

  protected String getCollegeIdSeq() {
    return projectContext.getCollegeIdSeq();
  }

  protected String getTeachDepartIdSeq() {
    return projectContext.getTeachDepartIdSeq();
  }

  protected String getStdTypeIdSeq() {
    return projectContext.getStdTypeIdSeq();
  }

  protected String getEducationIdSeq() {
    return projectContext.getEducationIdSeq();
  }

  /**
   * @deprecated 返回的东西里面只有院系、学生类别。已经不能满足现在的按照项目、院系、学生类别的数据级权限的要求
   * @return
   */
  protected DataRealm getDataRealm() {
    return new DataRealm(projectContext.getStdTypeIdSeq(), projectContext.getDepartIdSeq());
  }

  public void setProjectContext(ProjectContext projectContext) {
    this.projectContext = projectContext;
  }

}
