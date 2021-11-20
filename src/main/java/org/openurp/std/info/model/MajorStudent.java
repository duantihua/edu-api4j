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

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Student;
import org.openurp.code.edu.model.DisciplineCategory;
import org.openurp.code.edu.model.Institution;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "org.openurp.std.info.model.MajorStudent")
public class MajorStudent extends LongIdObject {
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  private String code;

  private String majorName;

  private String enMajorName;

  @ManyToOne(fetch = FetchType.LAZY)
  private Institution school;
  @ManyToOne(fetch = FetchType.LAZY)
  private DisciplineCategory majorCategory;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getEnMajorName() {
    return enMajorName;
  }

  public void setEnMajorName(String enMajorName) {
    this.enMajorName = enMajorName;
  }

  public Institution getSchool() {
    return school;
  }

  public void setSchool(Institution school) {
    this.school = school;
  }

  public DisciplineCategory getMajorCategory() {
    return majorCategory;
  }

  public void setMajorCategory(DisciplineCategory majorCategory) {
    this.majorCategory = majorCategory;
  }
}
