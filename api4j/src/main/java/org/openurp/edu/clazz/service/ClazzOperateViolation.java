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

/**
 * 用户对任务进行新增、保存、修改、删除的违例类型
 */
public enum ClazzOperateViolation {
  // 没有任何违例，可以操作任务
  NO_VIOLATION,
  // // 该任务目前关联的计划/即将关联的计划的任务包 已经提交/审核通过
  // PLAN_VIOLATION,
  // 该任务已经被提交/审核通过
  LESSON_VIOLATION,
  // 院系任务操作开关已经关闭,
  PERMIT_VIOLATION,
}
