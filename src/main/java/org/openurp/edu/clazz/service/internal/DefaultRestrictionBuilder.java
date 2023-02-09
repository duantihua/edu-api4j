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
package org.openurp.edu.clazz.service.internal;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.openurp.base.std.code.StdType;
import org.openurp.base.edu.model.Direction;
import org.openurp.base.edu.model.Major;
import org.openurp.base.std.model.Squad;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.person.model.Gender;
import org.openurp.edu.clazz.model.Restriction;
import org.openurp.edu.clazz.model.RestrictionItem;
import org.openurp.edu.clazz.model.RestrictionMeta;
import org.openurp.edu.clazz.service.RestrictionBuilder;

public class DefaultRestrictionBuilder implements RestrictionBuilder {

  Restriction group = new Restriction();

  DefaultTeachClassNameStrategy strategy = new DefaultTeachClassNameStrategy();

  public DefaultRestrictionBuilder() {
    super();
  }

  public DefaultRestrictionBuilder(Restriction group) {
    super();
    this.group = group;
  }

  public RestrictionBuilder inGrades(String... grades) {
    if (grades.length > 0 && grades[0] != null) {
      RestrictionItem item = getOrCreateItem(RestrictionMeta.Grade);
      addValues(item, true, grades);
    }
    return this;
  }

  public RestrictionBuilder notInGrades(String... grades) {
    RestrictionItem item = getOrCreateItem(RestrictionMeta.Grade);
    addValues(item, false, grades);
    return this;
  }

  public <T extends Entity<?>> RestrictionBuilder in(T... entities) {
    if (entities.length > 0 && entities[0] != null) {
      T first = entities[0];
      RestrictionItem item = getItem(first);
      addValues(item, true, getIds(entities));
    }
    if (entities.length > 0 && entities[0] instanceof Squad) {
      clear(RestrictionMeta.Grade);
      clear(RestrictionMeta.StdType);
      clear(RestrictionMeta.Department);
      clear(RestrictionMeta.Major);
      clear(RestrictionMeta.Direction);
      clear(RestrictionMeta.Level);
    }

    return this;
  }

  public <T extends Entity<?>> RestrictionBuilder notIn(T... entities) {
    if (entities.length >= 0 && entities[0] != null) {
      T first = entities[0];
      RestrictionItem item = getItem(first);
      addValues(item, false, getIds(entities));
    }
    return this;
  }

  public RestrictionBuilder clear(RestrictionMeta meta) {
    RestrictionItem removed = null;
    for (RestrictionItem item : group.getItems()) {
      if (item.getMeta().equals(meta)) {
        removed = item;
      }
    }
    if (null != removed) {
      group.getItems().remove(removed);
    }
    return this;
  }

  public Restriction build() {
    return group;
  }

  /**
   * 获取对象的id字符串数组
   *
   * @param entities
   * @return
   */
  private String[] getIds(Entity<?>... entities) {
    String[] ids = new String[entities.length];
    for (int i = 0; i < entities.length; i++) {
      if (entities[i] == null) {
        continue;
      }
      ids[i] = String.valueOf(entities[i].getId());
    }
    return ids;
  }

  /**
   * 向item中添加多个值
   *
   * @param item
   * @param values
   */
  private void addValues(RestrictionItem item, boolean contain, String... values) {
    String old = item.getContents();
    if (old.length() > 0) {
      old = Strings.concat(old, ",", Strings.join(values, ","));
    } else {
      old = Strings.join(values, ",");
    }
    if (-1 != old.indexOf(',')) {
      if (!old.startsWith(",")) old = "," + old;
      if (!old.endsWith(",")) old = old + ",";
    }
    item.setContents(old);
    item.setIncluded(contain);
  }

  private RestrictionItem getOrCreateItem(RestrictionMeta meta) {
    for (RestrictionItem item : group.getItems()) {
      if (item.getMeta().equals(meta)) {
        return item;
      }
    }
    RestrictionItem item = new RestrictionItem();
    item.setMeta(meta);
    item.setIncluded(true);
    item.setContents("");
    item.setRestriction(group);
    group.getItems().add(item);
    return item;
  }

  private <T> RestrictionItem getItem(T first) {
    RestrictionItem item = null;
    if (first instanceof Gender) {
      item = getOrCreateItem(RestrictionMeta.Gender);
    } else if (first instanceof StdType) {
      item = getOrCreateItem(RestrictionMeta.StdType);
    } else if (first instanceof Department) {
      item = getOrCreateItem(RestrictionMeta.Department);
    } else if (first instanceof Major) {
      item = getOrCreateItem(RestrictionMeta.Major);
    } else if (first instanceof Direction) {
      item = getOrCreateItem(RestrictionMeta.Direction);
    } else if (first instanceof Squad) {
      item = getOrCreateItem(RestrictionMeta.Squad);
    } else if (first instanceof EducationLevel) {
      item = getOrCreateItem(RestrictionMeta.Level);
    } else {
      throw new RuntimeException("no support limit meta class " + first.getClass().getName());
    }
    return item;
  }

}
