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

import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.hibernate.Query;
import org.openurp.service.security.DataRealm;
import org.openurp.base.util.stat.CountItem;
import org.openurp.edu.program.plan.dao.ExecutionPlanStatDao;
import org.openurp.edu.program.model.ExecutionPlan;

public class ExecutionPlanStatDaoHibernate extends HibernateEntityDao implements ExecutionPlanStatDao {

  public List statByDepart(DataRealm realm, String grade) {
    String statHql = "select new " + CountItem.class.getName() + "(count(*), plan.department) " + " from "
        + ExecutionPlan.class.getName() + " as plan" + " where plan.program.grade=  :grade "
        + " and plan.department.id in (:departIds)" + " and plan.program.stdType.id in (:stdTypeIds)"
        + " group by  plan.department.id";
    Query query = getSession().createQuery(statHql);
    query.setParameterList("departIds", Strings.splitToLong(realm.getDepartmentIdSeq()));
    query.setParameterList("stdTypeIds", Strings.splitToLong(realm.getStudentTypeIdSeq()));
    query.setParameter("grade", grade);

    return query.list();
  }

  public List statByStdType(DataRealm realm, String grade) {

    String statHql = "select new " + CountItem.class.getName() + "(count(*), plan.program.stdType) "
        + " from " + ExecutionPlan.class.getName() + " as plan" + " where plan.program.grade=  :grade "
        + " and plan.department.id in (:departIds)" + " and plan.program.stdType.id in (:stdTypeIds)"
        + " group by  plan.program.stdType.id";
    Query query = getSession().createQuery(statHql);
    query.setParameterList("departIds", Strings.splitToLong(realm.getDepartmentIdSeq()));
    query.setParameterList("stdTypeIds", Strings.splitToLong(realm.getStudentTypeIdSeq()));
    query.setParameter("grade", grade);

    return query.list();
  }

  public List getGrades(DataRealm realm) {
    String statHql = "select distinct plan.program.grade from " + ExecutionPlan.class.getName() + " as plan "
        + " where plan.department.id in (:departIds) " + " and plan.program.stdType.id in(:stdTypeIds)"
        + " order by  plan.program.grade desc";
    Query query = getSession().createQuery(statHql);
    query.setParameterList("departIds", Strings.splitToLong(realm.getDepartmentIdSeq()));
    query.setParameterList("stdTypeIds", Strings.splitToLong(realm.getStudentTypeIdSeq()));
    return query.list();

  }

}
