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
package org.openurp.edu.schedule.util;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.bean.comparators.PropertyComparator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.entity.Entity;

/**
 * 多个资源一张课程表
 *
 *
 */
public class MultiCourseTable {

  List<CourseTable> tables = CollectUtils.newArrayList();

  List<Entity<?>> resources = CollectUtils.newArrayList();

  Order order;

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public List<CourseTable> getTables() {
    return tables;
  }

  public void setTables(List<CourseTable> tables) {
    this.tables = tables;
  }

  public List<Entity<?>> getResources() {
    return resources;
  }

  public void setResources(List<Entity<?>> resources) {
    this.resources = resources;
  }

  public void sortTables() {
    if (null != order) {
      Collections.sort(tables, new PropertyComparator(order.getProperty(), order.isAscending()));
    }
  }
}
