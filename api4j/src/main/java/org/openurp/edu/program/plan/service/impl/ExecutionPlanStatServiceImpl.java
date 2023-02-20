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
package org.openurp.edu.program.plan.service.impl;

import java.util.List;

import org.openurp.service.security.DataRealm;
import org.openurp.edu.program.plan.dao.ExecutionPlanStatDao;
import org.openurp.edu.program.plan.service.ExecutionPlanStatService;

public class ExecutionPlanStatServiceImpl implements ExecutionPlanStatService {

  ExecutionPlanStatDao executePlanStatDao;

  public List statByDepart(DataRealm realm, String grade) {
    return executePlanStatDao.statByDepart(realm, grade);
  }

  public List statByStdType(DataRealm realm, String grade) {
    return executePlanStatDao.statByStdType(realm, grade);
  }

  public List getGrades(DataRealm realm) {
    return executePlanStatDao.getGrades(realm);
  }

  public void setExecutionPlanStatDao(ExecutionPlanStatDao executePlanStatDao) {
    this.executePlanStatDao = executePlanStatDao;
  }

}
