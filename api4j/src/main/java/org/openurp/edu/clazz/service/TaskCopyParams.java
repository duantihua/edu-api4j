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
package org.openurp.edu.clazz.service;

import org.openurp.base.edu.model.Semester;

/**
 * 教学任务拷贝参数
 *
 *
 */
public class TaskCopyParams {

  Semester semester;

  /**
   * 复制学生名单
   */
  boolean copyCourseTakers;

  /**
   * 每个任务的复制数量
   */
  int copyCount;

  public int getCopyCount() {
    return copyCount;
  }

  public void setCopyCount(int copyCount) {
    this.copyCount = copyCount;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public boolean isCopyCourseTakers() {
    return copyCourseTakers;
  }

  public void setCopyCourseTakers(boolean copyCourseTakers) {
    this.copyCourseTakers = copyCourseTakers;
  }

}
