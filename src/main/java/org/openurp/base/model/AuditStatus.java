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
package org.openurp.base.model;

import org.beangle.commons.lang.IDEnum;

/**
 * 通用的审核状态枚举类，有4种状态：未提交、已提交、通过、不通过
 */
public enum AuditStatus implements IDEnum {

  UNSUBMITTED(0, "未提交"), SUBMITTED(1, "已提交"),
  ACCEPTED(100, "通过"), REJECTED(99, "不通过");

  private final int id;
  private final String fullName;

  AuditStatus(int id, String fullName) {
    this.id = id;
    this.fullName = fullName;
  }

  public String getEnName() {
    return this.name();
  }

  public String getName() {
    return this.name();
  }

  public String getFullName() {
    return this.fullName;
  }

  @Override
  public int getId() {
    return id;
  }
}
