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
package org.openurp.edu.clazz.service.impl;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.dao.ClazzPlanRelationDao;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.ClazzPlanRelation;
import org.openurp.edu.clazz.service.ClazzPlanRelationService;
import org.openurp.edu.program.model.ExecutionPlan;

import java.util.List;

public class ClazzPlanRelationServiceImpl extends BaseServiceImpl implements ClazzPlanRelationService {

  private ClazzPlanRelationDao clazzPlanRelationDao;

  public List<Clazz> relatedClazzes(ExecutionPlan plan) {
    return clazzPlanRelationDao.relatedClazzes(plan);
  }

  public List<Clazz> relatedClazzes(ExecutionPlan plan, Semester semester) {
    return clazzPlanRelationDao.relatedClazzes(plan, semester);
  }

  public List<ExecutionPlan> relatedPlans(Clazz clazz) {
    return clazzPlanRelationDao.relatedPlans(clazz);
  }

  public List<ClazzPlanRelation> relations(ExecutionPlan plan) {
    return clazzPlanRelationDao.relations(plan);
  }

  public List<ClazzPlanRelation> relations(ExecutionPlan plan, Semester semester) {
    return clazzPlanRelationDao.relations(plan, semester);
  }

  public List<ClazzPlanRelation> relations(Clazz clazz) {
    return clazzPlanRelationDao.relations(clazz);
  }

  public void setClazzPlanRelationDao(ClazzPlanRelationDao clazzPlanRelationDao) {
    this.clazzPlanRelationDao = clazzPlanRelationDao;
  }

  public List<ExecutionPlan> possibleRelatePlans(Clazz clazz) {
    return clazzPlanRelationDao.possibleRelatePlans(clazz);
  }

}
