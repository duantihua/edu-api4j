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
package org.openurp.base.util.stat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatUtils {

  public static void setValueToMap(String key, Object tempValue, String tempType, Map m) {
    if ("integer".equals(tempType)) {
      if (m.containsKey(key)) {
        m.put(key, new Integer(((Integer) m.get(key)).intValue() + ((Integer) tempValue).intValue()));
      } else {
        m.put(key, (Integer) tempValue);
      }
    } else if ("float".equals(tempType)) {
      if (m.containsKey(key)) {
        m.put(key, new Float(((Float) m.get(key)).floatValue() + ((Float) tempValue).floatValue()));
      } else {
        m.put(key, (Float) (tempValue));
      }
    } else if ("list".equals(tempType)) {
      List tempList = new ArrayList();
      if (m.containsKey(key)) {
        tempList = (ArrayList) m.get(key);
      }
      tempList.add(tempValue);
      m.put(key, tempList);
    } else if ("set".equals(tempType)) {
      Set tempSet = new HashSet();
      if (m.containsKey(key)) {
        tempSet = (HashSet) m.get(key);
      }
      tempSet.add(tempValue);
      m.put(key, tempSet);
    }
  }
}
