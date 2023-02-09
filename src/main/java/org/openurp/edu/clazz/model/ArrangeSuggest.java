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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.edu.model.Classroom;

/**
 * 排课建议
 *
 *
 *         2013-04-08 修改
 *         changed: otherSuggest -> remark
 *         removed: weekdayArrange
 *         added: activities
 */
@Entity(name = "org.openurp.edu.clazz.model.ArrangeSuggest")
public class ArrangeSuggest extends LongIdObject {

  private static final long serialVersionUID = -8849071317188023800L;

  /** 教学任务 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /** 建议活动 */
  @OneToMany(mappedBy = "arrangeSuggest", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<SuggestActivity> activities = new HashSet<SuggestActivity>();

  /** 建议使用教室 */
  @ManyToMany
  private Set<Classroom> rooms = CollectUtils.newHashSet();

  /** 文字建议(其他建议) */
  @Size(max = 500)
  private String remark;

  public ArrangeSuggest() {
    super();
  }

  public ArrangeSuggest(Long id) {
    super(id);
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public Set<Classroom> getRooms() {
    return rooms;
  }

  public void setRooms(Set<Classroom> rooms) {
    this.rooms = rooms;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String otherSuggest) {
    this.remark = otherSuggest;
  }

  public boolean addRoom(Classroom room) {
    return this.rooms.add(room);
  }

  public boolean addRooms(Collection<Classroom> rooms) {
    return this.rooms.addAll(rooms);
  }

  public boolean removeRoom(Classroom room) {
    return this.rooms.remove(room);
  }

  public boolean removeRooms(Collection<Classroom> rooms) {
    return this.rooms.removeAll(rooms);
  }

  public Set<SuggestActivity> getActivities() {
    return activities;
  }

  public void setActivities(Set<SuggestActivity> activities) {
    this.activities = activities;
  }

  public void addActivity(SuggestActivity activity) {
    activity.setArrangeSuggest(this);
    this.activities.add(activity);
  }

  public void addActivities(Collection<SuggestActivity> activities) {
    for (SuggestActivity activity : activities) {
      addActivity(activity);
    }
  }

}
