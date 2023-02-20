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

import org.openurp.base.edu.model.Project;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;

/**
 * getAll...意味着忽略班级的有效性进行查找
 *
 *
 */
public interface SquadService {

  /**
   * 返回指定班级号码的学生行政班级信息. 若指定的班级不存在,则返回.
   *
   * @param id
   * @return
   */
  public Squad getSquad(Long id);

  /**
   * 根据班级代码找到班级
   *
   * @param code
   * @return
   */
  public Squad getSquad(String code);

  /**
   * 保存新建的行政班级信息，如果已经存在同样的班级，则抛出异常<code>EntityExistException</code>.
   * 1)记录班级的创建时间和更新时间
   *
   * @param squad
   */
  public void saveOrUpdate(Squad squad);

  /**
   * 删除指定班级号码的行政班信息，如果不存在这样的班级，则返回. 谨慎调用.
   *
   * @param id
   */
  public void removeSquad(Long id);

  /**
   * 计算并更新班级内学籍有效人数
   *
   * @param squadId
   */
  public int updateStdCount(Long squadId);

  /**
   * 计算并更新班级内实际在校人数
   *
   * @param squadId
   * @return
   */
  public int updateActualStdCount(Long squadId);

  /**
   * 批量计算并更新班级内实际在校人数和学籍有效人数
   *
   * @param squadIdSeq
   */
  public void batchUpdateStdCountOfClass(String squadIdSeq);

  /**
   * 批量计算并更新班级内实际在校人数和学籍有效人数
   *
   * @param squadIdSeq
   */
  public void batchUpdateStdCountOfClass(Long[] squadIds);

  /**
   * 批量从班级中移出学生
   *
   * @param studentIdArray
   * @param squadIdArray
   */
  public void batchRemoveStudentClass(List students, List squades);

  /**
   * 批量增加学生的班级项
   *
   * @param studentIdArray
   * @param parameterMap
   */
  public void batchAddStudentClass(List students, List squades);

  public void updateStudentSquad(Student std, Collection squades, Project project);
}
