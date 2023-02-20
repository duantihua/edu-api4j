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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.MapConverter;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.conversion.impl.DefaultConversion;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Strings;
import org.openurp.edu.clazz.model.RestrictionMeta;
import org.openurp.edu.clazz.service.limit.RestrictionItemContentProvider;

public abstract class AbstractCourseLimitContentProvider<T> extends BaseServiceImpl
    implements RestrictionItemContentProvider<T> {
  public static final MapConverter converter = new MapConverter(DefaultConversion.Instance);

  private RestrictionMeta metaEnum;

  protected Object[] getContentValues(String content) {
    String[] strValues = Strings.split(content, ",");
    if (Arrays.isEmpty(strValues)) { return null; }
    Object[] values = converter.convert(strValues, getMeta().getContentValueType());
    if (Arrays.isEmpty(values)) { return null; }
    return values;
  }

  public List<T> getCascadeContents(String content, String term, PageLimit limit,
      Map<RestrictionMeta, String> cascadeField) {
    return getCascadeContents(getContentValues(content), term, limit, cascadeField);
  }

  protected abstract List<T> getCascadeContents(Object[] content, String term, PageLimit limit,
      Map<RestrictionMeta, String> cascadeField);

  public Map<String, T> getContents(String content) {
    return getContentMap(getContentValues(content));
  }

  public List<T> getOtherContents(String content, String term, PageLimit limit) {
    return getOtherContents(getContentValues(content), term, limit);
  }

  protected abstract List<T> getOtherContents(Object[] content, String term, PageLimit limit);

  protected abstract Map<String, T> getContentMap(Object[] content);

  public abstract RestrictionMeta getMeta();
}
