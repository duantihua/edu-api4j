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
package org.openurp.edu.clazz.app.model.enums;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public enum ElectRuleType {
  ELECTION, WITHDRAW, GENERAL;

  public String getCnName() {
    return valueMap().get(this.toString());
  }

  public static Map<String, String> valueMap() {
    Map<String, String> result = CollectUtils.newHashMap();
    result.put(ELECTION.toString(), "选课");
    result.put(WITHDRAW.toString(), "退课");
    result.put(GENERAL.toString(), "登录");
    return result;
  }

  public static List<String> strValues() {
    ElectRuleType[] values = values();
    List<String> result = CollectUtils.newArrayList();
    for (ElectRuleType electRuleType : values) {
      result.add(electRuleType.toString());
    }
    return result;
  }

  public static Map<String, String> getElectTypes() {
    Map<String, String> result = valueMap();
    result.remove(GENERAL.toString());
    return result;
  }
}
