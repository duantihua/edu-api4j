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

import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.model.NumberIdTimeObject;

/**
 * 培养方案文档
 *
 *
 */
@Entity(name = "org.openurp.edu.program.model.ProgramDoc")
public class ProgramDoc extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = -6651783681783600013L;

  /** 培养方案 */
  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Program program;

  /** 针对语言 */
  @NotNull
  private Locale docLocale;

  /** 模板中的章节列表定义 */
  @OneToMany(mappedBy = "doc", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("code")
  private List<ProgramDocSection> sections = CollectUtils.newArrayList();

  public Program getProgram() {
    return program;
  }

  public void setProgram(Program program) {
    this.program = program;
  }

  public List<ProgramDocSection> getSections() {
    return sections;
  }

  public void setSections(List<ProgramDocSection> sections) {
    this.sections = sections;
  }

  public Locale getDocLocale() {
    return docLocale;
  }

  public void setDocLocale(Locale docLocale) {
    this.docLocale = docLocale;
  }

}
