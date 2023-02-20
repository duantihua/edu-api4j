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
package org.openurp.edu.clazz.dao;

import java.util.Collection;

import org.openurp.edu.clazz.model.Clazz;

/**
 * 教学任务课程序号产生器
 *
 *
 */
public interface ClazzCRNGenerator {

  /**
   * 依照教学任务的教学日历和学生类别进行生成序号
   *
   * @param task
   * @return
   */
  public void genClazzSeqNo(Clazz clazz);

  /**
   * 为一组任务生成课程序号
   *
   * @param tasks
   */
  public void genClazzSeqNos(Collection<Clazz> tasks);

}
