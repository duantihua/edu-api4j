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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Strings;
import org.openurp.base.std.model.Grade;
import org.openurp.edu.clazz.model.ClazzRestrictionMeta;

public class CourseLimitGradeProvider extends AbstractCourseLimitContentProvider<String> {

  protected Map<String, String> getContentMap(Object[] content) {
    Map<String, String> results = new LinkedHashMap<String, String>();
    for (Object value : content) {
      String grade = (String) value;
      results.put(grade, grade);
    }
    return results;
  }

  @Override
  protected List<String> getOtherContents(Object[] content, String term, PageLimit limit) {
    OqlBuilder<String> builder = OqlBuilder.from(Grade.class.getName() + " g");
    if (!Arrays.isEmpty(content)) {
      builder.where("g.code not in(:grades)", content);
    }
    if (Strings.isNotBlank(term)) {
      builder.where("g.code like :grade", "%" + term + "%");
    }
    builder.select("g.code");
    builder.orderBy("g.code");
    builder.limit(limit);
    return entityDao.search(builder);
  }

  public Map<String, String> getContentIdTitleMap(String content) {
    return super.getContents(content);
  }

  @Override
  protected List<String> getCascadeContents(Object[] content, String term, PageLimit limit,
      Map<ClazzRestrictionMeta, String> cascadeField) {
    return null;
  }

  @Override
  public ClazzRestrictionMeta getMeta() {
    return ClazzRestrictionMeta.Grade;
  }
}
