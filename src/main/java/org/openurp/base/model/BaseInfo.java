/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.base.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.beangle.commons.entity.Entity;

/**
 * 基础信息抽象接口
 * </p>
 * 提供专业、方向、部门、校区、教室等五个基础信息的公共部分,<br>
 * 一共8个属性,其中id为非业务主键，code为业务编码，enabled为基础信息的状态字段，<br>
 * 一般为"使用"和"不使用".<br>
 *
 *
 * @since 2005
 */
@MappedSuperclass
public interface BaseInfo extends Entity<Integer>, Comparable<BaseInfo> {
  /**
   * 获得代码
   *
   * @return 代码
   */
  String getCode();

  /**
   * 设置代码
   *
   * @param code
   *          代码
   */
  void setCode(String code);

  /**
   * 获得名称
   *
   * @return 名称
   */
  String getName();

  /**
   * 设置名称
   *
   * @param name
   *          名称
   */
  void setName(String name);

  /**
   * 获得英文名
   *
   * @return 英文名
   */
  String getEnName();

  /**
   * 设置英文名
   *
   * @param phoneticName
   *          英文名
   */
  void setEnName(String enName);

  /**
   * 获得简称
   *
   * @return 简称
   */
  String getShortName();

  /**
   * 设置简称
   *
   * @param shortName
   *          简称
   */
  void setShortName(String shortName);

  /**
   * 获得备注
   *
   * @return 备注
   */
  String getRemark();

  /**
   * 设置备注
   *
   * @param remark
   *          备注
   */
  void setRemark(String remark);

  Date getUpdatedAt();

  void setUpdatedAt(Date updatedAt);

  /**
   * 获得起始时间
   *
   * @return 起始日期
   */
  java.sql.Date getBeginOn();

  /**
   * 设置起始日期
   *
   * @param beginAt
   *          起始日期
   */
  void setBeginOn(java.sql.Date beginOn);

  /**
   * 获得结束日期
   *
   * @return 结束日期
   */
  java.sql.Date getEndOn();

  /**
   * 设置结束日期
   *
   * @param endAt
   *          结束日期
   */
  void setEndOn(java.sql.Date endOn);
}
