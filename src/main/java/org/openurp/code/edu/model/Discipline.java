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
package org.openurp.code.edu.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.industry;

/**
 * 学科
 * （来自本专科科学科目录、研究生学术性学位目录、研究生专业性学位目录的学科）<br>
 * 一般认为4位代码的是一级学科，2位代码的是二级学科，但实际上可能没有一级学科、二级学科这种叫法
 *
 *
 * @since 2005-9-7
 * @since 3.0.0
 */
@Entity(name = "org.openurp.code.edu.model.Discipline")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AttributeOverrides(@AttributeOverride(name = "code", column = @Column(unique = false)))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "code", "catalog_id" }))
@industry
public class Discipline extends Code<Integer> {

  private static final long serialVersionUID = 8987618194169407607L;

  /**
   * 所属学科门类
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private DisciplineCategory category;

  /**
   * 所属学科目录
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private DisciplineCatalog catalog;

  /** 下级学科 */
  @OneToMany(mappedBy = "parent")
  private List<Discipline> children = CollectUtils.newArrayList();

  /** 上级学科 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Discipline parent;

  public DisciplineCategory getCategory() {
    return category;
  }

  public void setCategory(DisciplineCategory category) {
    this.category = category;
  }

  public DisciplineCatalog getCatalog() {
    return catalog;
  }

  public void setCatalog(DisciplineCatalog catalog) {
    this.catalog = catalog;
  }

  public List<Discipline> getChildren() {
    return children;
  }

  public void setChildren(List<Discipline> children) {
    this.children = children;
  }

  public Discipline getParent() {
    return parent;
  }

  public void setParent(Discipline parent) {
    this.parent = parent;
  }

}
