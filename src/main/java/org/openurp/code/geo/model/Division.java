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
package org.openurp.code.geo.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.nation;

/**
 * 行政区划
 *
 *
 * @since 2010-9-7
 * @since 3.0.0
 */
@Entity(name = "org.openurp.code.geo.model.Division")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@nation
public class Division extends Code<Integer> {

  private static final long serialVersionUID = -8303016071363886718L;

  /** 下级区划列表 */
  @OneToMany(mappedBy = "parent")
  private List<Division> children = CollectUtils.newArrayList();

  /** 上级区划 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Division parent;

  public Division() {
  }

  public Division(Integer id) {
    super(id);
  }

  public Division getParent() {
    return parent;
  }

  public Division getTop() {
    Division belongDivision = this;
    while (null != belongDivision && null != belongDivision.getParent()) {
      belongDivision = belongDivision.getParent();
    }
    return belongDivision;
  }

  public void setParent(Division superDivision) {
    this.parent = superDivision;
  }

  public boolean hasSubDivision() {
    return this.children.isEmpty();
  }

  public List<Division> getChildren() {
    return children;
  }

  public void setChildren(List<Division> subDivisions) {
    this.children = subDivisions;
  }

  public boolean addSubDivision(Division subDivision) {
    return this.children.add(subDivision);
  }

  public boolean removeSubDivision(Division subDivision) {
    return this.children.remove(subDivision);
  }

  public boolean addSubDivisions(Collection<? extends Division> subDivisions) {
    return this.children.addAll(subDivisions);
  }

  public boolean removeSubDivisions(Collection<? extends Division> subDivisions) {
    return this.children.removeAll(subDivisions);
  }

  public void clearSubDivisions() {
    this.children.clear();
  }
}
