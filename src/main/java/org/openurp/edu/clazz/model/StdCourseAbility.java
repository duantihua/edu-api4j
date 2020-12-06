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
package org.openurp.edu.clazz.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.code.model.CourseAbilityRate;
import org.openurp.base.edu.model.Student;

/**
 * 学生课程等级能力
 */
@Entity(name = "org.openurp.edu.clazz.model.StdCourseAbility")
public class StdCourseAbility extends LongIdObject {

  private static final long serialVersionUID = 4198510825366495986L;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 分数 */
  private Float score;

  /** 获得英语等级，比如A级、B级、C级 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseAbilityRate rate;

  /** 状态，已发布/未发布，默认状态就是未发布 */
  @NotNull
  private boolean published;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public CourseAbilityRate getRate() {
    return rate;
  }

  public void setRate(CourseAbilityRate rate) {
    this.rate = rate;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
  }
}
