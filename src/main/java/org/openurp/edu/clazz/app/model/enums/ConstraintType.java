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
package org.openurp.edu.clazz.app.model.enums;

public enum ConstraintType {
  stdCreditConstraint, stdTotalCreditConstraint, stdCourseCountConstraint, courseTypeCreditConstraint;

  public String getName() {
    switch (this) {
    case stdCreditConstraint:
      return "个人学分";
    case stdTotalCreditConstraint:
      return "全程学分";
    case courseTypeCreditConstraint:
      return "课程类别学分";
    default:
      return "课程门数限制";
    }
  }
}
