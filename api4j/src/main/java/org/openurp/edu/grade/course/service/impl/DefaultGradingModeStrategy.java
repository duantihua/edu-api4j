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

import org.beangle.commons.dao.EntityDao;
import org.openurp.code.edu.model.GradeType;
import org.openurp.code.edu.model.GradingMode;
import org.openurp.edu.grade.course.model.CourseGradeState;
import org.openurp.edu.grade.course.model.ExamGradeState;
import org.openurp.edu.grade.course.model.GradeState;
import org.openurp.edu.grade.course.service.GradingModeStrategy;

import java.util.List;

/**
 * 默认成绩记录方式配置方法
 */
public class DefaultGradingModeStrategy implements GradingModeStrategy {

  private EntityDao entityDao;

  public DefaultGradingModeStrategy() {
    super();
  }

  private boolean isDefault(GradingMode style) {
    return null == style || style.getId().equals(GradingMode.Percent);
  }

  public final void configGradingMode(CourseGradeState gradeState, List<GradeType> gradeTypes) {
    if (isDefault(gradeState.getGradingMode()))
      gradeState.setGradingMode(getDefaultCourseGradeGradingMode(gradeState));

    for (GradeType type : gradeTypes) {
      GradeState typeState = getState(gradeState, type);
      if (null == typeState.getGradingMode()) {
        typeState.setGradingMode(getDefaultExamGradeGradingMode(gradeState, typeState));
      }
    }
    entityDao.saveOrUpdate(gradeState);
  }

  /**
   * 查询缺省的总成绩记录方式
   *
   * @param state
   * @return
   */
  protected GradingMode getDefaultCourseGradeGradingMode(CourseGradeState state) {
    // 默认按照课程的记录方式
    return state.getClazz().getCourse().getGradingMode();
  }

  /**
   * 查询缺省的考试成绩类型对应的记录方式
   *
   * @param typeState
   * @param setting
   * @return
   */
  protected GradingMode getDefaultExamGradeGradingMode(CourseGradeState gradeState, GradeState typeState) {
    // 是否是最终成绩的可选项
    if (typeState.getGradeType().isGa()) {
      return gradeState.getGradingMode();
    } else {
      // 如果缓考不作为最终，就按照期末成绩记录方式，如果没有期末成绩，直接按照最终
      if (typeState.getGradeType().getId().equals(GradeType.DELAY_ID)) {
        ExamGradeState endGradeState = (ExamGradeState) gradeState.getState(new GradeType(GradeType.END_ID));
        if (null == endGradeState) return gradeState.getGradingMode();
        else return endGradeState.getGradingMode();
      } else {
        return entityDao.get(GradingMode.class, GradingMode.Percent);
      }
    }
  }

  private GradeState getState(CourseGradeState gradeState, GradeType gradeType) {
    return gradeState.getOrCreateState(gradeType);
  }

  public final void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
