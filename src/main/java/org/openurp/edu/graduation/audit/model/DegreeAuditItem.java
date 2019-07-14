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
package org.openurp.edu.graduation.audit.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 学位审核条目结果
 */
@Entity(name = "org.openurp.edu.graduation.audit.model.DegreeAuditItem")
public class DegreeAuditItem extends LongIdObject {

  private static final long serialVersionUID = -5306832627413494440L;

  /** 项目名称 */
  @Size(max = 255)
  @NotNull
  private String name;

  /**
   * 是否通过
   */
  private Boolean passed;

  /** 具体状态信息 */
  @Size(max = 500)
  private String comments;

  /** 毕业审核结果 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private DegreeResult result;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getPassed() {
    return passed;
  }

  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public DegreeResult getResult() {
    return result;
  }

  public void setResult(DegreeResult result) {
    this.result = result;
  }

}
