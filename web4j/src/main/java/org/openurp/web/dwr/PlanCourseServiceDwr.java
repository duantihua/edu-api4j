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
package org.openurp.web.dwr;

import java.util.Map;

import org.beangle.commons.dao.EntityDao;
import org.beangle.orm.hibernate.udt.WeekStates;
import org.openurp.base.time.NumberRangeDigestor;
import org.openurp.edu.program.model.ExecutionPlanCourse;
import org.openurp.edu.program.model.MajorPlanCourse;
import org.openurp.edu.program.model.StdPlanCourse;

public class PlanCourseServiceDwr {

  private EntityDao entityDao;

  public StdPlanCourse getPersonalPlanCourse(Long id) {
    return entityDao.get(StdPlanCourse.class, id);
  }

  public Map<String, Object> getExecutionPlanCourse(Long id) {
    ExecutionPlanCourse pc = entityDao.get(ExecutionPlanCourse.class, id);
    Map<String, Object> datas = new java.util.HashMap<String, Object>();
    datas.put("id", pc.getId().toString());
    datas.put("course", pc.getCourse());
    datas.put("department", pc.getDepartment());
    datas.put("terms", pc.getTerms().toString());
    if (pc.getWeekstate() != null) datas.put("weekstate", NumberRangeDigestor.digest(pc.getWeekstate()));
    datas.put("compulsory", pc.isCompulsory());
    return datas;
  }

  public Map<String, Object> getMajorPlanCourse(Long id) {
    MajorPlanCourse pc = entityDao.get(MajorPlanCourse.class, id);
    Map<String, Object> datas = new java.util.HashMap<String, Object>();
    datas.put("id", pc.getId().toString());
    datas.put("course", pc.getCourse());
    datas.put("department", pc.getDepartment());
    datas.put("terms", pc.getTerms().toString());
    if (pc.getWeekstate() != null) datas.put("weekstate", NumberRangeDigestor.digest(pc.getWeekstate()));
    datas.put("compulsory", pc.isCompulsory());
    return datas;
  }
  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
