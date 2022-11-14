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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.std.code.StdType;
import org.openurp.base.edu.model.EduLevelBasedObject;

/**
 * 培养方案模板
 * <p>
 * <li>模板的适应范围为项目\层次</li>
 * <li>模板的适用年限为生效日期到失效日期.</li>
 * <li>可以针对不同语言定义模板</li>
 * </p>
 *
 *
 */
@Entity(name = "org.openurp.edu.program.model.ProgramDocTemplate")
public class ProgramDocTemplate extends EduLevelBasedObject<Long> {

  private static final long serialVersionUID = 2045096188167471704L;

  /** 模板名称 */
  @NotNull
  @Size(max = 50)
  private String name;

  /** 模板中的章节列表定义 */
  @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("indexno")
  private List<ProgramDocMeta> metas = CollectUtils.newArrayList();
  /** 针对语言 */
  @NotNull
  private Locale docLocale;

  /** 学生类别列表 */
  @ManyToMany
  @NotNull
  private Set<StdType> types = CollectUtils.newHashSet();

  /** 生效日期 */
  @NotNull
  private java.sql.Date beginOn;

  /** 失效日期 */
  private java.sql.Date endOn;

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ProgramDocMeta> getMetas() {
    return metas;
  }

  public void setMetas(List<ProgramDocMeta> metas) {
    this.metas = metas;
  }

  public Locale getDocLocale() {
    return docLocale;
  }

  public void setDocLocale(Locale docLocale) {
    this.docLocale = docLocale;
  }

  public Set<StdType> getTypes() {
    return types;
  }

  public void setTypes(Set<StdType> types) {
    this.types = types;
  }

}
