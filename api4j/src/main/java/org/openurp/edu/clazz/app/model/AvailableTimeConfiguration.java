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
package org.openurp.edu.clazz.app.model;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 任务可用时间配置
 */
public class AvailableTimeConfiguration extends LongIdObject {

  private static final long serialVersionUID = 3240925805188364485L;
  private String name;

  private String availTime;

  private Boolean isDefault = Boolean.FALSE;

  public AvailableTimeConfiguration() {
    super();
  }

  public AvailableTimeConfiguration(String name, String availTime, Boolean isDefault) {
    super();
    this.name = name;
    this.availTime = availTime;
    this.isDefault = isDefault;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvailTime() {
    return availTime;
  }

  public void setAvailTime(String availTime) {
    this.availTime = availTime;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }

}
