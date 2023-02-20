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
package org.openurp.edu.grade.course.model;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.code.edu.model.CourseTakeType;
import org.openurp.code.edu.model.ExamStatus;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.ProjectBasedObject;

import java.util.List;
import java.util.Set;

/**
 * 课程成绩配置
 */
public class CourseGradeSetting extends ProjectBasedObject<Long> {

  private static final long serialVersionUID = -261419832818823702L;

  /**
   * 总评成绩的组成部分
   */
  private List<GradeType> gaElementTypes = CollectUtils.newArrayList(3);

  /**
   * 不设补考的考试情况
   */
  private Set<ExamStatus> noMakeupExamStatuses = CollectUtils.newHashSet();

  /**
   * 不设补考的修读类别
   */
  private Set<CourseTakeType> noMakeupTakeTypes = CollectUtils.newHashSet();

  /**
   * 不允许录入成绩的考试类型列表
   */
  private Set<ExamStatus> emptyScoreStatuses = CollectUtils.newHashSet();

  /**
   * 是否提交即发布
   */
  private boolean submitIsPublish = false;

  public CourseGradeSetting() {
    super();
  }

  public CourseGradeSetting(Project project) {
    this();
    gaElementTypes.add(new GradeType(GradeType.USUAL_ID));
    gaElementTypes.add(new GradeType(GradeType.END_ID));
  }

  public Set<ExamStatus> getNoMakeupExamStatuses() {
    return noMakeupExamStatuses;
  }

  public void setNoMakeupExamStatuses(Set<ExamStatus> noMakeupExamStatuses) {
    this.noMakeupExamStatuses = noMakeupExamStatuses;
  }

  public Set<CourseTakeType> getNoMakeupTakeTypes() {
    return noMakeupTakeTypes;
  }

  public void setNoMakeupTakeTypes(Set<CourseTakeType> noMakeupTakeTypes) {
    this.noMakeupTakeTypes = noMakeupTakeTypes;
  }

  public List<GradeType> getGaElementTypes() {
    return gaElementTypes;
  }

  public void setGaElementTypes(List<GradeType> gaElementTypes) {
    this.gaElementTypes = gaElementTypes;
  }

  public Set<ExamStatus> getEmptyScoreStatuses() {
    return emptyScoreStatuses;
  }

  public void setEmptyScoreStatuses(Set<ExamStatus> emptyScoreStatuses) {
    this.emptyScoreStatuses = emptyScoreStatuses;
  }

  /**
   * 是否提交即发布
   *
   * @return
   */
  public boolean isSubmitIsPublish() {
    return submitIsPublish;
  }

  public void setSubmitIsPublish(boolean submitIsPublish) {
    this.submitIsPublish = submitIsPublish;
  }
}
