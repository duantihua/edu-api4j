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
package org.openurp.edu.lesson.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 课程限制元信息
 *
 *
 */
@Entity(name = "org.openurp.edu.lesson.model.CourseLimitMeta")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseLimitMeta extends LongIdObject {

  private static final long serialVersionUID = 2311660436512066941L;

  public static enum Operator {
    EQUAL("="), NOT_EQUAL("<>"), IN("in"), NOT_IN("not in"), GREATE_EQUAL_THAN(">="), LESS_EQUAL_THAN(
        "<="), NULL(" is null");
    Operator(String op) {
      this.op = op;
    }

    String op;
  }

  /** 名称 */
  @NotNull
  private String name;

  /** 备注 */
  @NotNull
  private String remark;

  public CourseLimitMeta() {
    super();
  }

  public CourseLimitMeta(Long id) {
    super(id);
  }

  public CourseLimitMeta(Long id, String name, String remark) {
    super(id);
    this.name = name;
    this.remark = remark;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
