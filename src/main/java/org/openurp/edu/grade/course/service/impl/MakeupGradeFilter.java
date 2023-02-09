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

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.Model;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.GaGrade;
import org.openurp.edu.grade.course.service.GradeRateService;
import org.openurp.edu.grade.course.service.ScoreConverter;

/**
 * 将补考成绩作为成绩中的一个。
 */
public class MakeupGradeFilter implements GradeFilter {

  private GradeRateService gradeRateService;

  public List<CourseGrade> filter(List<CourseGrade> grades) {
    List<CourseGrade> gradeList = CollectUtils.newArrayList();
    for (CourseGrade courseGrade : grades) {
      boolean finded = false;
      for (GaGrade gaGrade : courseGrade.getGaGrades()) {
        if (null == gaGrade.getGradeType()) {
          continue;
        }
        if (gaGrade.getGradeType().isGa()) {
          CourseGrade newGrade = Model.newInstance(CourseGrade.class);
          newGrade.setStd(courseGrade.getStd());
          newGrade.setSemester(courseGrade.getSemester());
          newGrade.setClazz(courseGrade.getClazz());
          newGrade.setCourse(courseGrade.getCourse());
          newGrade.setCourseType(courseGrade.getCourseType());
          newGrade.setCrn(courseGrade.getCrn());
          newGrade.setCourseType(courseGrade.getCourseType());
          newGrade.setCourseTakeType(courseGrade.getCourseTakeType());
          newGrade.setFreeListening(courseGrade.isFreeListening());

          newGrade.setScore(gaGrade.getScore());
          newGrade.setPassed(gaGrade.isPassed());
          newGrade.setGradingMode(gaGrade.getGradingMode());
          ScoreConverter converter = gradeRateService.getConverter(courseGrade.getProject(),
              gaGrade.getGradingMode());
          newGrade.setGp(converter.calcGp(newGrade.getScore()));

          finded = true;
          gradeList.add(newGrade);
        }
      }
      if (!finded) {
        gradeList.add(courseGrade);
      }
    }
    return gradeList;
  }

  public void setGradeRateService(GradeRateService gradeRateService) {
    this.gradeRateService = gradeRateService;
  }

}
