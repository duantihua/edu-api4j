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
package org.openurp.edu.clazz.service.limit.impl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openurp.code.person.model.Gender;
import org.openurp.code.service.CodeService;
import org.openurp.edu.clazz.model.RestrictionMeta;

public class CourseLimitGenderProvider extends AbstractCourseLimitEntityProvider<Gender> {
  protected CodeService codeService;

  protected Set<Integer> excludedIds = new HashSet<Integer>();

  public void setExcludedIds(Set<Integer> excludedIds) {
    this.excludedIds = excludedIds;
  }

  @Override
  protected Map<String, Gender> getContentMap(Object[] content) {
    Map<String, Gender> contentMap = super.getContentMap(content);
    Map<String, Gender> results = new LinkedHashMap<String, Gender>();
    for (Entry<String, Gender> entry : contentMap.entrySet()) {
      Gender gender = entry.getValue();
      Integer id = gender.getId();
      if (!excludedIds.contains(id)) {
        results.put(entry.getKey(), gender);
      }
    }
    return results;
  }

  @Override
  public RestrictionMeta getMeta() {
    return RestrictionMeta.Gender;
  }
}
