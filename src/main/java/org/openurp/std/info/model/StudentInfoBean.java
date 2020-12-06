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
package org.openurp.std.info.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.edu.model.Student;
import org.openurp.base.edu.model.StudentBasedEntity;

/**
 * @depend - - - Student
 */
@MappedSuperclass
public class StudentInfoBean extends LongIdObject implements StudentBasedEntity {
  private static final long serialVersionUID = 8654193228650042903L;

  /** 学生 */
  @OneToOne
  @NaturalId
  private Student std;

  /** 修改时间 */
  protected Date updatedAt;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
