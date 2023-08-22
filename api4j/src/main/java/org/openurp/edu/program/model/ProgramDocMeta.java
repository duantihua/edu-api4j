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
package org.openurp.edu.program.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 培养方案中的章节定义
 *
 *
 */
@Entity(name = "org.openurp.edu.program.model.ProgramDocMeta")
public class ProgramDocMeta extends LongIdObject {

  private static final long serialVersionUID = 8026279634177263188L;
  /** 名称 */
  @NotNull
  @Size(max = 100)
  private String name;

  /** 最大长度 */
  @NotNull
  private int maxlength;

  /** 顺序号 */
  @NotNull
  private int indexno;

  /** 模板 */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private ProgramDocTemplate template;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMaxlength() {
    return maxlength;
  }

  public void setMaxlength(int maxlength) {
    this.maxlength = maxlength;
  }

  public ProgramDocTemplate getTemplate() {
    return template;
  }

  public void setTemplate(ProgramDocTemplate template) {
    this.template = template;
  }

  public int getIndexno() {
    return indexno;
  }

  public void setIndexno(int indexno) {
    this.indexno = indexno;
  }

}
