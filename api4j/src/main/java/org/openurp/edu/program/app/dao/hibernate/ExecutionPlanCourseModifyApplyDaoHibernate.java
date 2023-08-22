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
package org.openurp.edu.program.app.dao.hibernate;

import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.edu.program.app.dao.ExecutionPlanCourseModifyApplyDao;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModify;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailAfter;
import org.openurp.edu.program.app.model.ExecutionPlanCourseModifyDetailBefore;

public class ExecutionPlanCourseModifyApplyDaoHibernate extends HibernateEntityDao
    implements ExecutionPlanCourseModifyApplyDao {

  public void saveModifyApply(ExecutionPlanCourseModify apply, ExecutionPlanCourseModifyDetailBefore before,
      ExecutionPlanCourseModifyDetailAfter after) {
    saveOrUpdate(apply);
    if (before != null) {
      apply.setOldPlanCourse(before);
      before.setApply(apply);
      saveOrUpdate(before);
    }
    if (after != null) {
      apply.setNewPlanCourse(after);
      after.setApply(apply);
      saveOrUpdate(after);
    }
  }

}
