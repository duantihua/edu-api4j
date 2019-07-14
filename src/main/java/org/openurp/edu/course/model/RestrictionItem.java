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
package org.openurp.edu.course.model;

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
@Entity(name = "org.openurp.edu.course.model.RestrictionItem")
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

  private boolean inclusive;

  /** 限制内容 */
  @NotNull
  private String content;

  public RestrictionItem() {
    super();
  }

  public RestrictionItem(RestrictionMeta meta, String content, boolean inclusive) {
    super();
    this.meta = meta;
    this.content = content;
    this.inclusive = inclusive;
  }

  public RestrictionMeta getMeta() {
    return meta;
  }

  public void setMeta(RestrictionMeta meta) {
    this.meta = meta;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Restriction getRestriction() {
    return restriction;
  }

  public void setRestriction(Restriction restriction) {
    this.restriction = restriction;
  }

  public boolean isInclusive() {
    return inclusive;
  }

  public void setInclusive(boolean inclusive) {
    this.inclusive = inclusive;
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
    if (null != content) {
      if (null != meta && "GRADE".equals(meta.getName())) {
        content = "'" + content + "'";
      }
    }
    return content;
  }

  @Override
  public String toString() {
    return content;
  }

}
