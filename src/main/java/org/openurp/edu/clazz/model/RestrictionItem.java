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

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * 课程限制项
 */
@Entity(name = "org.openurp.edu.clazz.model.RestrictionItem")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class RestrictionItem extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -6697398004696236934L;

  /** 限制具体项目 */
  @NotNull
  @Type(type = "org.beangle.orm.hibernate.udt.IDEnumType")
  private RestrictionMeta meta;

  /** 所在限制组 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Restriction restriction;

  @Column(name = "include_in")
  private boolean included;

  /** 限制内容 */
  @NotNull
  private String contents;

  public RestrictionItem() {
    super();
  }

  public RestrictionItem(RestrictionMeta meta, String contents, boolean included) {
    super();
    this.meta = meta;
    this.contents = contents;
    this.included = included;
  }

  public RestrictionMeta getMeta() {
    return meta;
  }

  public void setMeta(RestrictionMeta meta) {
    this.meta = meta;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public Restriction getRestriction() {
    return restriction;
  }

  public void setRestriction(Restriction restriction) {
    this.restriction = restriction;
  }

  public boolean isIncluded() {
    return included;
  }

  public void setIncluded(boolean included) {
    this.included = included;
  }

  public Object clone() {
    try {
      RestrictionItem clone = (RestrictionItem) super.clone();
      clone.setId(null);
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   */
  @Deprecated
  public String getContentForHql() {
    if (null != contents) {
      if (null != meta && RestrictionMeta.Grade.equals(meta)) {
        contents = "'" + contents + "'";
      }
    }
    return contents;
  }

  @Override
  public String toString() {
    return contents;
  }

}
