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
import java.util.Iterator;
import java.util.List;

import org.beangle.commons.lang.Objects;

/**
 * 统计段<br>
 * 排序按照降序进行 [min,max]
 *
 *
 */
public class FloatSegment implements Comparable<Object> {

  float min;

  float max;

  int count;

  public FloatSegment() {
    this(0, 0);
  }

  public FloatSegment(float min, float max) {
    this.min = min;
    this.max = max;
    count = 0;
  }

  public boolean add(Float score) {
    return add(score.floatValue());
  }

  public boolean add(float score) {
    if (score <= max && score >= min) {
      count++;
      return true;
    } else {
      return false;
    }
  }

  public int getCount() {
    return count;
  }

  public float getMax() {
    return max;
  }

  public void setMax(float max) {
    this.max = max;
  }

  public float getMin() {
    return min;
  }

  public void setMin(float min) {
    this.min = min;
  }

  public void setCount(int count) {
    this.count = count;
  }

  /**
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(Object object) {
    FloatSegment myClass = (FloatSegment) object;
    return Float.compare(myClass.getMin(), this.getMin());
  }

  public Object clone() {
    return new FloatSegment(getMin(), getMax());
  }

  public boolean emptySeg() {
    if (min == 0 && max == 0) return true;
    else return false;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return Objects.toStringBuilder(this.getClass()).add("min", this.getMin()).add("max", this.getMax())
        .add("count", this.count).toString();
  }

  /**
   * 构造分段区[start,start+level-1],[start+level,start+2*level-1]..
   *
   * @param start
   * @param level
   * @param count
   * @return
   */
  public static List<FloatSegment> buildSegments(int start, int level, int count) {
    List<FloatSegment> segmentList = new ArrayList<FloatSegment>();
    for (int i = 0; i < count; i++) {
      segmentList.add(new FloatSegment(start, start + level - 1));
      start += level;
    }
    return segmentList;
  }

  public static void countSegments(List<FloatSegment> segs, List<Number> numbers) {
    for (Iterator<Number> iter = numbers.iterator(); iter.hasNext();) {
      Number number = iter.next();
      if (null == number) continue;
      for (Iterator<FloatSegment> iterator = segs.iterator(); iterator.hasNext();) {
        FloatSegment element = iterator.next();
        if (element.add(number.floatValue())) break;
      }
    }
  }
}
