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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.beangle.commons.entity.util.ValidEntityPredicate;
import org.beangle.commons.lang.Strings;
import org.openurp.code.std.model.StdType;
import org.openurp.edu.program.plan.dao.PersonalPlanDao;
import org.openurp.edu.program.model.ExecutivePlan;

/**
 *
 *
 */
public class PersonalPlanDaoHibernate extends ExecutivePlanDaoHibernate implements PersonalPlanDao {

  public Float getCreditByTerm(ExecutivePlan plan, int term) {
    Range<Integer> termRange = Range.between(1, plan.getProgram().getTermsCount());
    if (!termRange.contains(term)) {
      throw new RuntimeException("term out range");
    } else {
      return null;
    }
  }

  /**
   * 收集所有的查询学生类别的子类别
   *
   * @param stdType
   * @param stdTypeIdSeq
   * @return
   */
  protected String intersectStdTypeIdSeq(StdType stdType, String stdTypeIdSeq) {
    if (ValidEntityPredicate.Instance.apply(stdType)) {
      stdType = get(StdType.class, stdType.getId());
      List stdTypes = new ArrayList();
      stdTypes.add(stdType);
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < stdTypes.size(); i++) {
        StdType one = (StdType) stdTypes.get(i);
        sb.append(one.getId().toString()).append(", ");
      }
      stdTypeIdSeq = Strings.intersectSeq(stdTypeIdSeq, sb.toString());
    }
    return stdTypeIdSeq;
  }

}
