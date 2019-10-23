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
@Entity(name = "org.openurp.edu.lesson.model.CourseLimitGroup")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseLimitGroup extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -6284931594985772061L;

  /** 教学任务 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  /** 条件列表 */
  @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private List<CourseLimitItem> items = CollectUtils.newArrayList();

  /** 最大人数 */
  @NotNull
  private int maxCount;

  /** 当前人数 */
  @NotNull
  private int curCount;

  /** 授课对象还是选课对象 */
  private boolean forClass = true;

  public List<CourseLimitItem> getItems() {
    return items;
  }

  public void setItems(List<CourseLimitItem> items) {
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

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public Object clone() {
    try {
      CourseLimitGroup clone = (CourseLimitGroup) super.clone();
      clone.setId(null);
      clone.setLesson(null);
      clone.setItems(new ArrayList<CourseLimitItem>());
      for (CourseLimitItem item : getItems()) {
        CourseLimitItem clone_item = (CourseLimitItem) item.clone();
        clone_item.setGroup(clone);
        clone.getItems().add(clone_item);
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public boolean isForClass() {
    return forClass;
  }

  public void setForClass(boolean forClass) {
    this.forClass = forClass;
  }

}
