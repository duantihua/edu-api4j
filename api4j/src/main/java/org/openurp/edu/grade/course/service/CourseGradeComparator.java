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
package org.openurp.edu.grade.course.service;

import java.text.Collator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.openurp.code.edu.model.GradeType;
import org.openurp.edu.grade.Grade;
import org.openurp.edu.grade.course.model.CourseGrade;

/**
 * 课程成绩比较
 *
 * @see StdGrade#sortGrades(CourseGradeComparator)
 */
public class CourseGradeComparator implements Comparator<Object> {

  String cmpWhat;

  boolean isAsc;

  Map<String, GradeType> gradeTypeMap;

  public CourseGradeComparator(String cmpWhat, boolean isAsc, List<GradeType> gradeTypes) {
    this.cmpWhat = cmpWhat;
    this.isAsc = isAsc;
    this.gradeTypeMap = CollectUtils.newHashMap();
    for (Iterator<GradeType> iter = gradeTypes.iterator(); iter.hasNext();) {
      GradeType gradeType = iter.next();
      gradeTypeMap.put(gradeType.getId().toString(), gradeType);
    }
  }

  public int compare(Object arg0, Object arg1) {
    CourseGrade g0 = (CourseGrade) arg0;
    CourseGrade g1 = (CourseGrade) arg1;
    if (cmpWhat.startsWith("gradeType")) {
      String cmp = cmpWhat.substring(cmpWhat.indexOf(".") + 1);
      GradeType gradeType = (GradeType) gradeTypeMap.get(cmp);
      Grade eg0 = g0.getGrade(gradeType);
      Grade eg1 = g1.getGrade(gradeType);
      return cmpScore((null == eg0 ? null : eg0.getScore()), (eg1 == null ? null : eg1.getScore()), isAsc);
    } else {
      try {
        Collator myCollator = Collator.getInstance();
        Object what0 = PropertyUtils.getProperty(arg0, cmpWhat);
        Object what1 = PropertyUtils.getProperty(arg1, cmpWhat);
        if (isAsc) {
          return myCollator.compare((null == what0) ? "" : what0.toString(),
              (null == what1) ? "" : what1.toString());
        } else {
          return myCollator.compare((null == what1) ? "" : what1.toString(),
              (null == what0) ? "" : what0.toString());
        }
      } catch (Exception e) {
        throw new RuntimeException("[CourseGradeComparator]reflection error:" + cmpWhat + e.getMessage());
      }
    }
  }

  private int cmpScore(Float score0, Float score1, boolean isAsc) {
    float fs0 = (null == score0) ? 0 : score0.floatValue();
    float fs1 = (null == score1) ? 0 : score1.floatValue();
    if (isAsc) {
      return Float.compare(fs0, fs1);
    } else {
      return Float.compare(fs1, fs0);
    }
  }
}
