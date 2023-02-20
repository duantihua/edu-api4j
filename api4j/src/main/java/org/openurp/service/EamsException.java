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
package org.openurp.service;

/**
 * 教务管理系统自己使用的Exception，里面放的是i18n key，用于在页面上显示的<br>
 * 其他业务模块的Exception应该继承自这个Exception
 */
public class EamsException extends RuntimeException {

  private String i18nKey;

  public EamsException(String i18nKey) {
    super(i18nKey);
    this.i18nKey = i18nKey;
  }

  public String getI18nKey() {
    return i18nKey;
  }

}
