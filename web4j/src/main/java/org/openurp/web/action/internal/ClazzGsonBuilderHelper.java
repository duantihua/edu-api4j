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
package org.openurp.web.action.internal;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClazzGsonBuilderHelper {

  public static Map<String, Object> genGroupResult(List entities, String warnings,
      ClazzGsonBuilderWorker worker) {
    Map<String, Object> groupResult = new TreeMap<String, Object>();
    groupResult.put("warnings", warnings);
    groupResult.put("groups", new TreeMap<String, Object>());

    Map<String, Object> groups = (TreeMap<String, Object>) groupResult.get("groups");
    for (Object entity : entities) {
      worker.dirtywork(entity, groups);
    }

    return groupResult;
  }
}
