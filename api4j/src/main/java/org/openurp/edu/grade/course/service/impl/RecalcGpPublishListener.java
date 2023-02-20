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
package org.openurp.edu.grade.course.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Operation;
import org.openurp.code.edu.model.GradeType;

import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.CourseGradeState;
import org.openurp.edu.grade.course.service.CourseGradeCalculator;
import org.openurp.edu.grade.course.service.CourseGradePublishListener;

public class RecalcGpPublishListener implements CourseGradePublishListener {

  protected CourseGradeCalculator calculator;

  public List<Operation> onPublish(CourseGrade grade, GradeType[] gradeTypes) {
    for (GradeType gradeType : gradeTypes) {
      if (gradeType.getId().equals(GradeType.MAKEUP_GA_ID)
          || gradeType.getId().equals(GradeType.DELAY_GA_ID)) {
        calculator.calcMakeupDelayGa(grade, null);
        return Operation.saveOrUpdate(grade).build();
      }
    }
    return Collections.emptyList();
  }

  public List<Operation> onPublish(Collection<CourseGrade> grades, CourseGradeState gradeState,
      GradeType[] gradeTypes) {
    List<Operation> operations = CollectUtils.newArrayList();
    boolean hasMakeupOrDelay = false;
    for (GradeType gradeType : gradeTypes) {
      if (gradeType.getId().equals(GradeType.MAKEUP_GA_ID)
          || gradeType.getId().equals(GradeType.DELAY_GA_ID)) {
        hasMakeupOrDelay = true;
        break;
      }
    }
    if (!hasMakeupOrDelay) return operations;

    for (CourseGrade grade : grades) {
      calculator.calcMakeupDelayGa(grade, gradeState);
      operations.addAll(Operation.saveOrUpdate(grade).build());
    }
    return operations;
  }

  public void setCalculator(CourseGradeCalculator calculator) {
    this.calculator = calculator;
  }

}
