/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.edu.lesson.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.edu.lesson.model.CourseLimitMeta.Operator;

/**
 * 课程限制项
 */
@Entity(name = "org.openurp.edu.lesson.model.CourseLimitItem")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseLimitItem extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -6697398004696236934L;

  /** 限制具体项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseLimitMeta meta;

  /** 所在限制组 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseLimitGroup group;

  /** 操作符 */
  @NotNull
  @Enumerated(EnumType.STRING)
  private Operator operator;

  /** 限制内容 */
  @NotNull
  private String content;

  public CourseLimitItem() {
    super();
  }

  public CourseLimitItem(CourseLimitMeta meta, Operator operator, String content) {
    super();
    this.meta = meta;
    this.operator = operator;
    this.content = content;
  }

  public CourseLimitMeta getMeta() {
    return meta;
  }

  public void setMeta(CourseLimitMeta meta) {
    this.meta = meta;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CourseLimitGroup getGroup() {
    return group;
  }

  public void setGroup(CourseLimitGroup group) {
    this.group = group;
  }

  public Object clone() {
    try {
      CourseLimitItem clone = (CourseLimitItem) super.clone();
      CourseLimitMeta meta = new CourseLimitMeta();
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
