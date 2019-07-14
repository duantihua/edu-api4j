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
package org.openurp.edu.graduation.plan.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.edu.base.model.Student;

@Entity(name = "org.openurp.edu.graduation.plan.model.OffsetCredit")
public class OffsetCredit extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = 3672322121742603088L;

  /** 对应学生 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 冲抵学分 */
  private Float credits;

  /** 备注 */
  private String remark;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Float getCredits() {
    return credits;
  }

  public void setCredits(Float credits) {
    this.credits = credits;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
