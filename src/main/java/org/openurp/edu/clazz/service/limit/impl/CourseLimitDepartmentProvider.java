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

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Arrays;
import org.openurp.base.model.Department;
import org.openurp.edu.clazz.model.RestrictionMeta;

public class CourseLimitDepartmentProvider extends AbstractCourseLimitEntityProvider<Department> {

  public OqlBuilder<Department> getQueryBuilder(Serializable[] content, String term, PageLimit limit) {
    OqlBuilder<Department> queryBuilder = OqlBuilder.from(getMeta().getContentType().getName(), "entity");
    if (!Arrays.isEmpty(content)) {
      queryBuilder.where("entity.id not in(:ids)", content);
    }
    queryBuilder.where("entity.school = :school", projectContext.getProject().getSchool());
    if (null != term) {
      addTermCondition(queryBuilder, term);
    }
    queryBuilder.orderBy("id");
    queryBuilder.limit(limit);
    return queryBuilder;
  }

  @Override
  public RestrictionMeta getMeta() {
    return RestrictionMeta.Department;
  }
}
