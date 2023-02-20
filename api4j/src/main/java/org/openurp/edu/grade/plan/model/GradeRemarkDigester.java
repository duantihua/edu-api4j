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
package org.openurp.edu.grade.plan.model;

import org.beangle.commons.bean.comparators.PropertyComparator;
import org.beangle.commons.lang.Strings;
import org.openurp.code.edu.model.ExamStatus;
import org.openurp.code.edu.model.GradeType;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.ExamGrade;
import org.openurp.edu.grade.course.model.GaGrade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GradeRemarkDigester {

  public String digest(List<CourseGrade> grades) {
    List<CourseGrade> newGrades = new ArrayList(grades);
    StringBuilder remarkSB = new StringBuilder();
    Collections.sort(newGrades, new PropertyComparator("semester"));
    String brString = "";
    for (CourseGrade grade : newGrades) {
      remarkSB.append(brString);
      remarkSB.append(grade.getSemester().getSchoolYear()).append(grade.getSemester().getName())
              .append(" ");
      GaGrade ga = grade.getGaGrade(new GradeType(GradeType.GA_ID));
      remarkSB.append("期末");
      if (null == ga || Strings.isBlank(ga.getScoreText() )) {
        remarkSB.append(disgetExamGrade(grade.getExamGrade(new GradeType(GradeType.END_ID))));
      } else {
        remarkSB.append(ga.getScoreText());
      }

      GaGrade delay = grade.getGaGrade(new GradeType(GradeType.DELAY_GA_ID));
      if (null == delay) {
        remarkSB.append("  补考");
        GaGrade makeup = grade.getGaGrade(new GradeType(GradeType.MAKEUP_GA_ID));
        if (null == makeup || Strings.isBlank(makeup.getScoreText() )) {
          remarkSB.append(disgetExamGrade(grade.getExamGrade(new GradeType(GradeType.MAKEUP_ID))));
        } else {
          remarkSB.append(makeup.getScoreText());
        }
      } else {
        remarkSB.append("  缓考");
        if (Strings.isBlank(delay.getScoreText())) {
          remarkSB.append(disgetExamGrade(grade.getExamGrade(new GradeType(GradeType.DELAY_ID))));
        } else {
          remarkSB.append(delay.getScoreText());
        }
      }
      brString = "\n";
    }
    return remarkSB.toString();
  }

  public String disgetExamGrade(ExamGrade eg) {
    if (null == eg) {
      return "--";
    } else if (Strings.isNotBlank(eg.getScoreText()) && eg.getExamStatus().getId().equals(ExamStatus.NORMAL)) {
      return eg.getScoreText();
    } else {
      return eg.getExamStatus().getName();
    }
  }
}
