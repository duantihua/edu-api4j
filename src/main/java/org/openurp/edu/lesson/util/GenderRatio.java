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
package org.openurp.edu.lesson.util;

import java.io.Serializable;

import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

public class GenderRatio implements Serializable, Comparable<GenderRatio> {

  private static final long serialVersionUID = -8453864023951172823L;

  public final static GenderRatio empty = new GenderRatio((short) 0);

  public final short value;

  public GenderRatio(short value) {
    this.value = value;
  }

  public boolean isAllMale() {
    return value == 0x0100;
  }

  public boolean isAllFemale() {
    return value == 1;
  }

  public static GenderRatio of(String value) {
    if (value == null) throw new RuntimeException("value cannot be null");
    if (-1 == value.indexOf(':')) throw new RuntimeException("value should contain ':'");
    else {
      String[] pair = Strings.split(value, ':');
      if (!(Numbers.isNumber(pair[0])
          && Numbers.isNumber(pair[1]))) { throw new RuntimeException("bad format of ratio :" + value); }
      int res = (Numbers.toInt(pair[0]) << 8) + Numbers.toInt(pair[1]);
      return new GenderRatio((short) res);
    }
  }

  public boolean isEmpty() {
    return value == 0;
  }

  @Override
  public int compareTo(GenderRatio o) {
    return this.value - o.value;
  }

  @Override
  public String toString() {
    if (value == 0) return "";
    else return (value >> 8) + ":" + (value & 0xFF);
  }

  @Override
  public boolean equals(Object arg0) {
    return ((GenderRatio) arg0).value == this.value;
  }

  @Override
  public int hashCode() {
    return value;
  }

}
