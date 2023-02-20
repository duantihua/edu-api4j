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
package org.openurp.edu.program.plan.service;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.std.code.StdType;
import org.openurp.base.edu.model.Direction;
import org.openurp.base.edu.model.Major;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.program.model.ExecutionPlan;

/**
 *
 */
public class ExecutionPlanQueryBuilder {

  private ExecutionPlanQueryBuilder() {
  }

  public static OqlBuilder<ExecutionPlan> build(Squad squad) {
    OqlBuilder<ExecutionPlan> query = OqlBuilder.from(ExecutionPlan.class, "plan");
    query.where("plan.program.grade = :grade", squad.getGrade())
        .where("plan.program.stdType = :stdType", squad.getStdType())
        .where("plan.program.major = :major", squad.getMajor());

    if (null == squad.getDirection()) {
      query.where("plan.program.direction is null");
    } else {
      query.where("plan.program.direction =:direction", squad.getDirection());
    }
    return query;
  }

  public static OqlBuilder<ExecutionPlan> build(String grade, StdType stdType, Major major, Direction direction) {
    OqlBuilder<ExecutionPlan> query = OqlBuilder.from(ExecutionPlan.class, "plan");
    query.where("plan.program.grade = :grade", grade).where("plan.program.major = :major", major);

    if (null != stdType) {
      query.where("plan.program.stdType = :stdType", stdType);
    }

    if (null == direction) {
      // 注意：这里是故意这样写的，不是BUG
      // query.where("plan.program.direction is null");
    } else {
      query.where("plan.program.direction =:direction", direction);
    }
    return query;
  }
}
