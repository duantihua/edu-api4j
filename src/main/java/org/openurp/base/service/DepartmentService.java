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
package org.openurp.base.service;

import java.util.Collection;
import java.util.List;

import org.openurp.base.model.Department;

public interface DepartmentService {

  /**
   * 根据指定的部门代码，返回部门详细信息. 没有对应的部门代码，则返回null.
   *
   * @param id
   * @return
   */
  public Department getDepartment(Integer id);

  /**
   * 返回所有有效的部门信息
   *
   * @return
   */
  public List<Department> getDepartments();

  /**
   * 返回部门代码字符串中指定的部门列表.形式如"xx,xx,xx"
   *
   * @param idSeq
   * @return
   */
  public List<Department> getDepartments(String idSeq);

  /**
   * 返回部门代码数组中指定的部门列表
   *
   * @param ids
   * @return
   */
  public List<Department> getDepartments(Integer ids[]);

  /**
   * 返回所有有效的学院信息
   *
   * @return
   */
  public List<Department> getColleges();

  /**
   * 返回部门代码字符串中指定的院系部门列表
   *
   * @param idSeq
   * @return
   */
  public List<Department> getColleges(String idSeq);

  /**
   * 返回部门代码字符串中指定的院系部门列表
   *
   * @param ids
   * @return
   */
  public List<Department> getColleges(Integer ids[]);

  /**
   * 查询开课院系
   *
   * @param idSeq
   * @return
   */
  public List<Department> getTeachDeparts(String idSeq);

  /**
   * 返回所有有效的管理部门信息
   *
   * @return
   */
  public List<Department> getAdministatives();

  /**
   * 返回部门代码字符串中指定的管理部门列表
   *
   * @param idSeq
   * @return
   */
  public List<Department> getAdministatives(String idSeq);

  /**
   * 返回部门代码字符串中指定的管理部门列表
   *
   * @param ids
   * @return
   */
  public List<Department> getAdministatives(Integer ids[]);

  /**
   * 删除制定部门代码的部门，谨慎调用. 如果要删除的部门信息并不存在，则返回null.
   *
   * @param id
   */
  public void removeDepartment(Integer id);

  public Collection<Department> getRelatedDeparts(String stdTypeIds);

  /**
   * 保存新建的部门信息，如果已经存在相同部门代码的部门，则抛出异常.
   *
   * @param Department
   */
  public void saveOrUpdate(Department department);

  /**
   * 得到所有的开课院系
   *
   * @return
   */
  public List<Department> getTeachDeparts();

}
