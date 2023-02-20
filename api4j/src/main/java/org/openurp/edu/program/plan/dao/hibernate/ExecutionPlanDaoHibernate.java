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
package org.openurp.edu.program.plan.dao.hibernate;

import java.util.List;

import org.apache.commons.lang3.Range;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.base.edu.model.Major;
import org.openurp.base.service.SemesterService;
import org.openurp.edu.program.plan.dao.ExecutionPlanCourseGroupDao;
import org.openurp.edu.program.plan.dao.ExecutionPlanDao;
import org.openurp.edu.program.model.ExecutionPlan;

/**
 */
public class ExecutionPlanDaoHibernate extends HibernateEntityDao implements ExecutionPlanDao {

  public List<ExecutionPlan> getExecutionPlanList(String grade, Major major, EducationLevel level) {
    OqlBuilder<ExecutionPlan> query = OqlBuilder.from(ExecutionPlan.class, "plan");
    query.where("plan.program.grade=:grade", grade).where("plan.program.major=:major", major)
        .where("plan.program.level=:level", level);
    return search(query);
  }

  protected SemesterService semesterService;

  protected ExecutionPlanCourseGroupDao executePlanCourseGroupDao;

  protected ExecutionPlan getExecutionPlan(Long id) {
    return get(ExecutionPlan.class, id);
  }

  public List<ExecutionPlan> getExecutionPlans(Long[] planIds) {
    OqlBuilder<ExecutionPlan> query = OqlBuilder.from(ExecutionPlan.class, "plan");
    query.where("plan.id in (:ids)", planIds);
    return search(query);
  }

  public Float getCreditByTerm(ExecutionPlan plan, int term) {
    Range<Integer> termRange = Range.between(1, plan.getTermsCount());
    if (!termRange.contains(term)) {
      throw new RuntimeException("term out range");
    } else {
      return null;
    }
  }

  public void setExecutionPlanCourseGroupDao(ExecutionPlanCourseGroupDao executePlanCourseGroupDao) {
    this.executePlanCourseGroupDao = executePlanCourseGroupDao;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

}
