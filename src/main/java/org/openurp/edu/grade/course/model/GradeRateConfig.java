/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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

import java.text.NumberFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.edu.base.code.model.ScoreMarkStyle;
import org.openurp.edu.base.model.Project;

/**
 * 成绩分级配置
 */
@Entity(name = "org.openurp.edu.grade.course.model.GradeRateConfig")
public class GradeRateConfig extends LongIdObject {

  private static final long serialVersionUID = 7557740151486177737L;

  /** 成绩记录方式 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ScoreMarkStyle markStyle;

  /** 对应培养类型(默认為空) */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 成绩分级配置项 */
  @OneToMany(mappedBy = "config", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @OrderBy("maxScore desc")
  private List<GradeRateItem> items;

  /** 及格线 */
  private float passScore;

  public float getPassScore() {
    return passScore;
  }

  public void setPassScore(float passScore) {
    this.passScore = passScore;
  }

  /**
   * 将字符串按照成绩记录方式转换成数字.<br>
   * 空成绩将转换成null
   *
   * @param score
   * @param markStyle
   * @return
   */
  public String convert(Float score) {
    if (null == score) return "";
    if (markStyle.isNumStyle()) return NumberFormat.getInstance().format(score.floatValue());
    for (GradeRateItem item : items) {
      if (item.contains(score)) { return item.getGrade(); }
    }
    return "";
  }

  public ScoreMarkStyle getMarkStyle() {
    return markStyle;
  }

  public void setMarkStyle(ScoreMarkStyle scoreMarkStyle) {
    this.markStyle = scoreMarkStyle;
  }

  public List<GradeRateItem> getItems() {
    return items;
  }

  public void setItems(List<GradeRateItem> items) {
    this.items = items;
  }

  public final Project getProject() {
    return project;
  }

  public final void setProject(Project project) {
    this.project = project;
  }
}
