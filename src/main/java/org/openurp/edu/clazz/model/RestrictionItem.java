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

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meta")
  private RestrictionMeta meta;

  /** 所在限制组 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Restriction restriction;

  private boolean includeIn;

  /** 限制内容 */
  @NotNull
  private String contents;

  public RestrictionItem() {
    super();
  }

  public RestrictionItem(RestrictionMeta meta, String contents, boolean includeIn) {
    super();
    this.meta = meta;
    this.contents = contents;
    this.includeIn = includeIn;
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

  public boolean isIncludeIn() {
    return includeIn;
  }

  public void setIncludeIn(boolean includeIn) {
    this.includeIn = includeIn;
  }

  public Object clone() {
    try {
      RestrictionItem clone = (RestrictionItem) super.clone();
      RestrictionMeta meta = new RestrictionMeta();
      meta.setId(this.getMeta().getId());
      clone.setMeta(meta);
      clone.setId(null);
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @see CourseLimitItemContentProvider
   * @see CourseLimitItemContentProviderFactory
   * @see CourseLimitMetaEnum
   */
  @Deprecated
  public String getContentForHql() {
    if (null != contents) {
      if (null != meta && "GRADE".equals(meta.getName())) {
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
