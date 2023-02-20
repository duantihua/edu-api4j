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

import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.script.ExpressionEvaluator;
import org.openurp.edu.grade.course.model.CourseGrade;

public class ScriptGradeFilter implements GradeFilter {

  String script;

  ExpressionEvaluator expressionEvaluator;

  public ScriptGradeFilter() {
    super();
  }

  public ScriptGradeFilter(String script, ExpressionEvaluator expressionEvaluator) {
    super();
    this.script = script;
    this.expressionEvaluator = expressionEvaluator;
  }

  public List<CourseGrade> filter(List<CourseGrade> grades) {
    if (Strings.isEmpty(script)) return grades;
    List<CourseGrade> newGrades = CollectUtils.newArrayList();
    for (CourseGrade grade : grades) {
      Boolean rs = expressionEvaluator.eval(script, Collections.singletonMap("grade", grade), Boolean.class);
      if (rs.booleanValue()) newGrades.add(grade);
    }
    return newGrades;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public void setExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
    this.expressionEvaluator = expressionEvaluator;
  }

  public String getScript() {
    return script;
  }

}
