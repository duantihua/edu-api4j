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
package org.openurp.base.util;

import org.beangle.commons.entity.Component;

/**
 * 区间，默认两端都是闭区间
 */
public class FloatRange implements Component {

  private float min;
  private boolean minInclusive = true;
  private float max;
  private boolean maxInclusive = true;

  /**
   * <p>
   * Constructs a new <code>FloatRange</code>, which is [0, 0].
   * </p>
   */
  public FloatRange() {
    this.min = 0f;
    this.max = 0f;
  }

  /**
   * <p>
   * Constructs a new <code>FloatRange</code>, which is [number, number].
   * </p>
   *
   * @param number the number to use for this range
   * @throws IllegalArgumentException if the number is <code>NaN</code>
   */
  public FloatRange(float number) {
    if (Float.isNaN(number)) { throw new IllegalArgumentException("The number must not be NaN"); }
  }

  /**
   * <p>
   * Constructs a new <code>FloatRange</code> with the specified
   * minimum and maximum numbers (both inclusive).
   * </p>
   * <p>
   * The arguments may be passed in the order (min,max) or (max,min). The
   * getMinimum and getMaximum methods will return the correct values.
   * </p>
   *
   * @param min first number that defines the edge of the range, inclusive
   * @param max second number that defines the edge of the range, inclusive
   * @throws IllegalArgumentException if either number is <code>NaN</code>
   */
  public FloatRange(float min, float max) {
    super();
    if (Float.isNaN(min)
        || Float.isNaN(max)) { throw new IllegalArgumentException("The numbers must not be NaN"); }
    if (max < min) {
      this.min = max;
      this.max = min;
    } else {
      this.min = min;
      this.max = max;
    }
  }

  public FloatRange(float min, boolean minInclusive, float max, boolean maxInclusive) {
    this(min, max);
    this.minInclusive = minInclusive;
    this.maxInclusive = maxInclusive;
  }

  public void minInclusive() {
    this.minInclusive = true;
  }

  public void minExclusive() {
    this.minInclusive = false;
  }

  public void inclusive() {
    this.minInclusive = true;
    this.maxInclusive = true;
  }

  public void exclusive() {
    this.minInclusive = false;
    this.maxInclusive = false;
  }

  public void maxInclusive() {
    this.maxInclusive = true;
  }

  public void maxExclusive() {
    this.maxInclusive = false;
  }

  public float getMin() {
    return min;
  }

  public void setMin(float min) {
    this.min = min;
  }

  public boolean isMinInclusive() {
    return minInclusive;
  }

  public void setMinInclusive(boolean minInclusive) {
    this.minInclusive = minInclusive;
  }

  public float getMax() {
    return max;
  }

  public void setMax(float max) {
    this.max = max;
  }

  public boolean isMaxInclusive() {
    return maxInclusive;
  }

  public void setMaxInclusive(boolean maxInclusive) {
    this.maxInclusive = maxInclusive;
  }

  public boolean containsFloat(float value) {
    boolean result = true;
    result = minInclusive ? min <= value && result : min < value && result;
    result = maxInclusive ? value <= max && result : value < max && result;
    return result;
  }

  /**
   * OVER 比大于给的值
   * BELOW 小于给的值
   * EQUAL 就是给的值
   */
  private static enum NearType {
    OVER, BELOW, EQUAL
  };

  private boolean containsFloat(float value, NearType nearType) {
    if (nearType == NearType.EQUAL) { return containsFloat(value); }

    if (nearType == NearType.OVER) {
      if (min == value && max > value) {
        return true;
      } else if (max == value) { return false; }
      return containsFloat(value);
    }

    // NearType.BELOW
    if (min == value) {
      return false;
    } else if (max == value && min < value) { return true; }
    return containsFloat(value);
  }

  public boolean containsRange(FloatRange range) {
    if (range == null) { return false; }
    if (equals(range)) { return true; }
    boolean result = true;
    // if my range is an empty set then return false
    if (min == max && (!minInclusive || !maxInclusive)) { return false; }
    // if range is an empty set then return true
    if (range.min == range.max && (!range.minInclusive || !range.maxInclusive)) { return true; }
    if (!range.minInclusive) {
      result = result && containsFloat(range.min, NearType.OVER);
    } else {
      result = result && containsFloat(range.min);
    }
    if (!range.maxInclusive) {
      result = result && containsFloat(range.max, NearType.BELOW);
    } else {
      result = result && containsFloat(range.max);
    }
    return result;
  }

  public boolean overlapsRange(FloatRange range) {
    if (range == null) { return false; }
    if (equals(range)) { return true; }
    boolean result = false;
    // if my range is an empty set then return false
    if (min == max && (!minInclusive || !maxInclusive)) { return false; }
    // if range is an empty set then return false
    if (range.min == range.max && (!range.minInclusive || !range.maxInclusive)) { return false; }
    if (!minInclusive) {
      result = result || range.containsFloat(min, NearType.OVER);
    } else {
      result = result || range.containsFloat(min);
    }
    if (!maxInclusive) {
      result = result || range.containsFloat(max, NearType.BELOW);
    } else {
      result = result || range.containsFloat(max);
    }
    if (!range.minInclusive) {
      result = result || containsFloat(range.min, NearType.OVER);
    } else {
      result = result || containsFloat(range.min);
    }
    return result;
  }

  public float getDistance() {
    return max - min;
  }

  private float minIn(float a, float b, float c, float d) {
    return Math.min(Math.min(Math.min(a, b), c), d);
  }

  private float maxIn(float a, float b, float c, float d) {
    return Math.max(Math.max(Math.max(a, b), c), d);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(max);
    result = prime * result + (maxInclusive ? 1231 : 1237);
    result = prime * result + Float.floatToIntBits(min);
    result = prime * result + (minInclusive ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    FloatRange other = (FloatRange) obj;
    if (Float.floatToIntBits(max) != Float.floatToIntBits(other.max)) return false;
    if (maxInclusive != other.maxInclusive) return false;
    if (Float.floatToIntBits(min) != Float.floatToIntBits(other.min)) return false;
    if (minInclusive != other.minInclusive) return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(minInclusive ? '[' : '(').append(min).append(", ").append(max).append(maxInclusive ? ']' : ')');
    return sb.toString();
  }

}
