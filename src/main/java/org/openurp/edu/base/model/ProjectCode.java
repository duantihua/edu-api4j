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
package org.openurp.edu.base.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.ems.dictionary.model.CodeMeta;

/**
 * 项目基础代码配置
 * 表示项目使用了基础代码集合中的哪些基础代码
 *
 *
 */
@Entity(name = "org.openurp.edu.base.model.ProjectCode")
public class ProjectCode extends LongIdObject {

  private static final long serialVersionUID = 5354782058088117951L;

  /** 项目 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 代码元 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CodeMeta meta;

  /** 代码ID */
  private Integer codeId;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public CodeMeta getMeta() {
    return meta;
  }

  public void setMeta(CodeMeta meta) {
    this.meta = meta;
  }

  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
  }

}
