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
package org.openurp.base.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.HierarchyEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 部门组织机构信息
 *
 *
 *
 * @since 2005
 */
@Entity(name = "org.openurp.base.model.Department")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Department extends AbstractBaseInfo implements HierarchyEntity<Department, Integer> {
  private static final long serialVersionUID = -4028414709010529995L;

  /** 是否开课 */
  @NotNull
  private boolean teaching;

  /** 上级单位 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department parent;

  /** 学校 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  /** 下级部门集合 */
  @OneToMany(mappedBy = "parent")
  private List<Department> children;

  /** 索引号 */
  @Size(max = 30)
  @NotNull
  protected String indexno;

  public Department() {
    super();
  }

  public Department(Integer id) {
    super();
    this.setId(id);
  }

  // 根据所属院系得到院系的上级单位
  public Department getTop() {
    Department belongDepart = this;
    while (null != belongDepart && null != belongDepart.getParent()) {
      belongDepart = belongDepart.getParent();
    }
    return belongDepart;
  }

  public boolean isTeaching() {
    return teaching;
  }

  public void setTeaching(boolean teaching) {
    this.teaching = teaching;
  }

  public Department getParent() {
    return parent;
  }

  public void setParent(Department parent) {
    this.parent = parent;
  }

  public List<Department> getChildren() {
    return children;
  }

  public void setChildren(List<Department> children) {
    this.children = children;
  }

  public String getIndexno() {
    return indexno;
  }

  public void setIndexno(String indexno) {
    this.indexno = indexno;
  }

}
