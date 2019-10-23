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
package org.openurp.base.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdObject;

/**
 * 学期阶段
 *
 *
 */
@Entity(name = "org.openurp.base.model.SemesterStage")
public class SemesterStage extends NumberIdObject<Integer> {

  private static final long serialVersionUID = 317801950546445267L;

  /** 学年学期 */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Semester semester;

  /** 名称 */
  @NotNull
  @Size(max = 40)
  private String name;

  /** 起始周 */
  private int startWeek;

  /** 结束周 */
  private int endWeek;

  /** 说明 */
  @Size(max = 255)
  private String remark;

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStartWeek() {
    return startWeek;
  }

  public void setStartWeek(int startWeek) {
    this.startWeek = startWeek;
  }

  public int getEndWeek() {
    return endWeek;
  }

  public void setEndWeek(int endWeek) {
    this.endWeek = endWeek;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
