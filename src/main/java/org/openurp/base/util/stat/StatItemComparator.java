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
package org.openurp.base.util.stat;

import java.util.Comparator;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.Order;

/**
 * 统计选项的比较
 *
 *
 */
public class StatItemComparator implements Comparator {

  private Order order;

  public StatItemComparator(Order order) {
    this.order = order;
  }

  public int compare(Object arg0, Object arg1) {
    StatItem item0, item1;
    if (order.isAscending()) {
      item0 = (StatItem) arg0;
      item1 = (StatItem) arg1;
    } else {
      item1 = (StatItem) arg0;
      item0 = (StatItem) arg1;
    }
    try {
      if (null == item0.getWhat() || null == item1.getWhat()) {
        if (null == item0) {
          return 1;
        } else {
          return -1;
        }
      }
      Comparable c0 = (Comparable) PropertyUtils.getProperty(item0, order.getProperty());
      Comparable c1 = (Comparable) PropertyUtils.getProperty(item1, order.getProperty());

      // if (c0 instanceof String) {
      // return new String(((String) c0).getBytes("utf-8"), "GB2312")
      // .compareTo(new String(((String) c1).getBytes("utf-8"),
      // "GB2312"));
      // } else {
      if (null == c0 || null == c1) {
        if (null == c0) {
          return 1;
        } else {
          return -1;
        }
      }
      return c0.compareTo(c1);
      // }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
