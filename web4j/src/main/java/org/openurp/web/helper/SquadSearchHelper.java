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
package org.openurp.web.helper;

import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.QueryHelper;
import org.openurp.base.std.model.Squad;
import org.openurp.base.edu.model.Teacher;

public class SquadSearchHelper extends SearchHelper {

  public OqlBuilder<Squad> buildQuery(Teacher teacher) {
    OqlBuilder<Squad> builder = OqlBuilder.from(Squad.class, "squad");
    QueryHelper.populateConditions(builder);
    builder.where("exists(from squad.instructors instructor where instructor=:teacher) "
        + "or exists(from squad.tutors tutor where tutor=:teacher)", teacher);
    builder.where("squad.beginOn <= :now and (squad.endOn is null or squad.endOn >= :now)",
        new java.util.Date());
    builder.select("select distinct squad");
    builder.limit(QueryHelper.getPageLimit());
    String order = Params.get(Order.ORDER_STR);
    builder.orderBy(Strings.isEmpty(order) ? "squad.grade desc" : order);
    return builder;
  }
}
