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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 课程限制条件组
 */
@Entity(name = "org.openurp.edu.clazz.model.Restriction")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class Restriction extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -6284931594985772061L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /** 条件列表 */
  @OneToMany(mappedBy = "restriction", orphanRemoval = true, cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private List<RestrictionItem> items = CollectUtils.newArrayList();

  /** 最大人数 */
  @NotNull
  private int maxCount;

  /** 当前人数 */
  @NotNull
  private int curCount;

  /** 授课对象? */
  private boolean prime = true;

  public List<RestrictionItem> getItems() {
    return items;
  }

  public void setItems(List<RestrictionItem> items) {
    this.items = items;
  }

  public int getMaxCount() {
    return maxCount;
  }

  public void setMaxCount(int maxCount) {
    this.maxCount = maxCount;
  }

  public int getCurCount() {
    return curCount;
  }

  public void setCurCount(int curCount) {
    this.curCount = curCount;
  }

  public Object clone() {
    try {
      Restriction clone = (Restriction) super.clone();
      clone.setId(null);
      clone.setClazz(null);
      clone.setItems(new ArrayList<RestrictionItem>());
      for (RestrictionItem item : getItems()) {
        RestrictionItem clone_item = (RestrictionItem) item.clone();
        clone_item.setRestriction(clone);
        clone.getItems().add(clone_item);
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public boolean isPrime() {
    return prime;
  }

  public void setPrime(boolean prime) {
    this.prime = prime;
  }

}
