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

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.base.std.model.Student;
import org.openurp.edu.program.model.ExecutivePlan;
import org.openurp.edu.program.model.StdPlan;
import org.openurp.edu.program.plan.service.CoursePlanProvider;
import org.openurp.edu.program.plan.service.PersonalPlanService;
import org.openurp.edu.program.plan.service.ProgramGenParameter;
import org.openurp.edu.program.plan.util.ProgramNamingHelper;

public class PersonalPlanServiceImpl extends BaseServiceImpl implements PersonalPlanService {

  private CoursePlanProvider coursePlanProvider;

  public ExecutivePlan getExecutivePlanForDiff(Student std) {
    return coursePlanProvider.getExecutivePlan(std);
  }

  public StdPlan genPersonalPlan(Student std) {
    ProgramGenParameter genParameter = new ProgramGenParameter();
    genParameter.setStudent(std);
    ExecutivePlan executePlan = coursePlanProvider.getExecutivePlan(std);
    if (executePlan == null) {
      // 没有专业培养计划，那么就根据学生自己的属性来设置
      genParameter.setGrade(std.getGrade());
      genParameter.setDepartment(std.getDepartment());
      genParameter.setDirection(std.getDirection());
      genParameter.setDuration(std.getDuration());
      genParameter.setLevel(std.getLevel());
      genParameter.setMajor(std.getMajor());
      genParameter.setStdType(std.getStdType());
      genParameter.setStudyType(std.getStudyType());
      genParameter.setBeginOn(std.getBeginOn());
      genParameter.setEndOn(std.getEndOn());
    }
    genParameter.setName(ProgramNamingHelper.name(std));
    return null;
  }

  public void setCoursePlanProvider(CoursePlanProvider coursePlanProvider) {
    this.coursePlanProvider = coursePlanProvider;
  }

}
