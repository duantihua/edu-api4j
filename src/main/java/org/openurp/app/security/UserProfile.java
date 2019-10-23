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
package org.openurp.app.security;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.beangle.security.data.Profile;

/**
 * 用户配置
 *
 * @author chaostone
 */
public class UserProfile implements Profile, Serializable {

  private static final long serialVersionUID = -9047586316477373803L;
  /**
   * 用户自定义属性
   */
  private final Map<String, Object> properties = CollectUtils.newHashMap();

  public Map<String, Object> getProperties() {
    return properties;
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("properties", properties).toString();
  }

}
