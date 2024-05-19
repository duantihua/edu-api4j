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
package org.openurp.edu.program.plan.dao.copy;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.openurp.base.std.model.Student;
import org.openurp.edu.program.model.ExecutivePlan;
import org.openurp.edu.program.plan.service.ExecutivePlanGenException;
import org.openurp.edu.program.plan.service.ProgramGenParameter;

public class PersonalPlanGenParameter extends ProgramGenParameter {

  private Student std;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  /**
   * 从一个专业计划来制作一个复制专业计划的参数
   *
   * @param plan
   * @throws ExecutivePlanGenException
   */

  public PersonalPlanGenParameter() {
  }

  public PersonalPlanGenParameter(ExecutivePlan plan) {
    try {
      PropertyUtils.copyProperties(this, plan);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

}
