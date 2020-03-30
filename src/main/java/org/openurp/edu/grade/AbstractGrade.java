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
package org.openurp.edu.grade;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.code.model.ScoreMarkStyle;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.base.model.Student;

/**
 * 成绩的抽象类
 * </p>
 * 学生成绩抽象类
 *
 * @depend - - - Student
 * @depend - - - Semester
 * @depend - - - ScoreMarkStyle
 * @see Grade
 *
 * @since 2007
 */
@MappedSuperclass
public abstract class AbstractGrade extends NumberIdTimeObject<Long> implements Grade {

  private static final long serialVersionUID = -5413230219080754536L;

  /**
   * 学生
   */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Student std;

  /**
   * 教学日历
   */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Semester semester;

  /** 教学项目 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;
  /**
   * 得分
   */
  protected Float score;

  /**
   * 得分等级/等分文本内容
   */
  protected String scoreText;
  /**
   * 是否合格
   */
  protected boolean passed;

  /**
   * 状态
   */
  protected int status;

  /**
   * 成绩记录方式
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected ScoreMarkStyle markStyle;

  /** 操作者 */
  @Size(max = 50)
  protected String operator;

  // 大的成绩放前面
  public int compareTo(Grade grade) {
    if (null == getScore()) return 1;
    else if (null == grade.getScore()) return -1;
    return grade.getScore().compareTo(getScore());
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public boolean isConfirmed() {
    return ((getStatus() & Grade.Status.CONFIRMED) > 0);
  }

  public boolean isPassed() {
    return passed;
  }

  public boolean isPublished() {
    return ((getStatus() & Grade.Status.PUBLISHED) > 0);
  }

  public ScoreMarkStyle getMarkStyle() {
    return markStyle;
  }

  public Float getScore() {
    return score;
  }

  public int getStatus() {
    return status;
  }

  public Student getStd() {
    return std;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setPassed(boolean passed) {
    this.passed = passed;
  }

  public void setMarkStyle(ScoreMarkStyle markStyle) {
    this.markStyle = markStyle;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public String getScoreText() {
    return scoreText;
  }

  public void setScoreText(String scoreText) {
    this.scoreText = scoreText;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

}
