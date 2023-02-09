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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;

public class StatGroup implements StatCountor {

  Object what;

  List items = new ArrayList();

  public StatGroup(Object entity) {
    this.what = entity;
  }

  public StatGroup(Object entity, List items) {
    this.what = entity;
    this.items = items;
  }

  /**
   * 添加数据
   *
   * @param data
   * @param from
   *          data的起始下标(不是counter的下标)
   * @param to
   *          data的结束下标
   * @param counters
   *          举例子: [depart,grade,stdcount,ddcount] ->0,3,2
   */
  public void addData(Object[] data, int from, int to, int counters) {
    if (to - from == counters) {
      StatItem statItem = new StatItem(data[from]);
      Comparable[] cts = new Comparable[counters];
      System.arraycopy(data, from + 1, cts, 0, counters);
      statItem.setCountors(cts);
      this.items.add(statItem);
    } else if (to - from > counters) {
      StatGroup subItem = new StatGroup(data[from]);
      int index = items.indexOf(subItem);
      if (-1 == index) {
        items.add(subItem);
      } else {
        subItem = (StatGroup) items.get(index);
      }
      subItem.addData(data, from + 1, to, counters);
    } else {
      return;
    }
  }

  /**
   * 根据平行的统计数据构建一个数据统计模型。默认计数器在最后为一个
   *
   * @param datas
   * @return
   */
  public static List buildStatGroups(List datas) {
    return buildStatGroups(datas, 1);
  }

  public static StatGroup buildStatGroup(List datas, int counters) {
    StatGroup result = new StatGroup(null);
    if (!(datas == null || datas.isEmpty())) {
      // (!CollectUtils.isEmpty(datas)) {
      for (Iterator iter = datas.iterator(); iter.hasNext();) {
        Object[] data = (Object[]) iter.next();
        result.addData(data, 0, data.length - 1, counters);
      }
    }
    return result;
  }

  public static List buildStatGroups(List datas, int counters) {
    List stats = new ArrayList();
    if (!CollectUtils.isEmpty(datas)) {
      Map rs = new HashMap();
      for (Iterator iter = datas.iterator(); iter.hasNext();) {
        Object[] data = (Object[]) iter.next();
        StatGroup result = (StatGroup) rs.get(data[0]);
        if (null == result) {
          result = new StatGroup(data[0]);
          rs.put(data[0], result);
          stats.add(result);
        }
        result.addData(data, 1, data.length - 1, counters);
      }
    }
    return stats;
  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  public boolean equals(Object object) {
    if (!(object instanceof StatGroup)) { return false; }
    StatGroup rhs = (StatGroup) object;
    return Objects.equals(this.what, rhs.what);
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return (null != what) ? 629 : this.what.hashCode();
  }

  public Object getWhat() {
    return what;
  }

  public void setWhat(Object entity) {
    this.what = entity;
  }

  public List getItems() {
    return items;
  }

  public void setItems(List items) {
    this.items = items;
  }

  public List getItemEntities() {
    List entities = new ArrayList();
    for (Iterator iter = items.iterator(); iter.hasNext();) {
      StatCountor obj = (StatCountor) iter.next();
      entities.add(obj.getWhat());
    }
    return entities;
  }

  public List getSubItemEntities() {
    Set entities = new HashSet();
    if (items.isEmpty()) return CollectUtils.newArrayList();
    if (items.get(0) instanceof StatGroup) {
      for (Iterator iter = items.iterator(); iter.hasNext();) {
        Object obj = (Object) iter.next();
        entities.addAll(((StatGroup) obj).getItemEntities());
      }
    }
    return new ArrayList(entities);
  }

  public Object getItem(Object statWhat) {
    for (Iterator iter = items.iterator(); iter.hasNext();) {
      StatCountor element = (StatCountor) iter.next();
      if (Objects.equals(element.getWhat(), statWhat)) { return element; }
    }
    return null;
  }

  /**
   * 汇总组内所有StatItem的指定位置的计数器之和
   *
   * @param counterIndex
   * @return
   */
  public Number sumItemCounter(int counterIndex) {
    double sum = 0;
    for (Iterator iterator = items.iterator(); iterator.hasNext();) {
      StatItem item = (StatItem) iterator.next();
      if (null != item.getCountors()[counterIndex]) {
        sum += ((Number) (item.getCountors()[counterIndex])).doubleValue();
      }
    }
    return new Double(sum);
  }
}
