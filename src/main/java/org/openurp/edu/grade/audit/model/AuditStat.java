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
package org.openurp.edu.grade.audit.model;

import java.util.Set;

import javax.persistence.Embeddable;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Component;
import org.openurp.edu.base.model.Course;

/**
 * 课程审核统计结果
 *
 *
 */
@Embeddable
public class AuditStat implements Component {

  /** 要求学分 */
  private float creditsRequired;

  /** 总的通过学分 */
  private float creditsCompleted;

  /** 要求门数 */
  private int numRequired;

  /** 总的通过门数 */
  private int numCompleted;

  /** 总的通过课程 */
  private transient Set<Course> passedCourses = CollectUtils.newHashSet();

  /**
   * 转换学分（例如：非任选课多出的学分可以转换冲抵任选课的学分）
   */
  private float creditsConverted;

  public AuditStat() {
    super();
  }

  public AuditStat(float creditCompleted, int totalNum) {
    this.creditsCompleted = creditCompleted;
    this.numCompleted = totalNum;
  }

  public void addCredits(float credits) {
    this.creditsCompleted += credits;
  }

  public void addNum(int num) {
    this.numCompleted += num;
  }

  public boolean isPassed() {
    return getCreditsRequired() <= (getCreditsCompleted() + getCreditsConverted())
        && getNumRequired() <= getNumCompleted();
  }

  /**
   * 获取所修学分和所需学分的差值
   *
   * @param returnNegative
   *          是否返回负数
   * @return
   */
  public float getCreditNeeded(boolean returnNegative) {
    float needToComplete = creditsRequired - creditsConverted - creditsCompleted;
    if (needToComplete < 0) {
      if (returnNegative) {
        return needToComplete;
      } else {
        return 0;
      }
    } else {
      return needToComplete;
    }
  }

  public int getNumRequired() {
    return numRequired;
  }

  public void setNumRequired(int courseNumRequired) {
    this.numRequired = courseNumRequired;
  }

  public float getCreditsCompleted() {
    return creditsCompleted;
  }

  public void setCreditsCompleted(float creditsCompleted) {
    this.creditsCompleted = creditsCompleted;
  }

  public float getCreditsRequired() {
    return creditsRequired;
  }

  public void setCreditsRequired(float creditsRequired) {
    this.creditsRequired = creditsRequired;
  }

  public Set<Course> getPassedCourses() {
    return passedCourses;
  }

  public void setPassedCourses(Set<Course> passCourses) {
    this.passedCourses = passCourses;
  }

  public int getNumCompleted() {
    return numCompleted;
  }

  public void setNumCompleted(int totalCourseNum) {
    this.numCompleted = totalCourseNum;
  }

  public float getCreditsConverted() {
    return creditsConverted;
  }

  public void setCreditsConverted(float creditsConverted) {
    this.creditsConverted = creditsConverted;
  }

  /**
   * 减去要求学分和门数
   *
   * @param num
   */
  public void reduceRequired(float credits, int num) {
    this.creditsRequired -= credits;
    this.creditsRequired = this.creditsRequired < 0 ? 0 : this.creditsRequired;
    this.numRequired -= num;
    this.numRequired = this.numRequired < 0 ? 0 : this.numRequired;
  }
}
