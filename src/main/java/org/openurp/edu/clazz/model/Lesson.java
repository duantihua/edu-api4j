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

import org.beangle.commons.entity.pojo.LongIdObject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "org.openurp.edu.clazz.model.Lesson")
public class Lesson extends LongIdObject {
  /**
   * 教学进度表
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected TeachingPlan plan;

  private int idx;

  @Size(max = 300)
  private String contents;

  @ManyToOne(fetch = FetchType.LAZY)
  private Subclazz subclazz;

  public TeachingPlan getPlan() {
    return plan;
  }

  public void setPlan(TeachingPlan plan) {
    this.plan = plan;
  }

  public int getIdx() {
    return idx;
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public Subclazz getSubclazz() {
    return subclazz;
  }

  public void setSubclazz(Subclazz subclazz) {
    this.subclazz = subclazz;
  }
}