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
package org.openurp.edu.clazz.app.model.constraint;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openurp.base.std.model.Student;

/**
 * 学生个人全程选课学分上限<br>
 * 限制全程总学分，<br>
 */

@Entity(name = "org.openurp.edu.clazz.app.model.constraint.StdTotalCreditConstraint")
@Table(name = "STD_TOTAL_CREDIT_CONS")
public class StdTotalCreditConstraint extends AbstractCreditConstraint {

  private static final long serialVersionUID = -2522394689697765724L;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }
}
