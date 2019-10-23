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
package org.openurp.base.time;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

public class Terms implements Serializable, Comparable<Terms> {
  private static final long serialVersionUID = -8846980902784025935L;

  public static final Terms Empty = new Terms(0);

  private static int valuesOf(String terms) {
    if (terms.equals("*") || terms.equals("")) return 0;
    int result = 0;
    for (String t : Strings.split(terms, ",")) {
      result |= (1 << Numbers.toInt(t));
    }
    return result;
  }

  public final int value;

  public Terms(int value) {
    super();
    this.value = value;
  }

  public Terms(String values) {
    this(valuesOf(values));
  }

  public boolean contains(int term) {
    return (value & (1 << term)) > 0;
  }

  public List<Integer> getTermList() {
    String str = Integer.toBinaryString(value);
    List<Integer> termList = CollectUtils.newArrayList();
    if (value > 0) {
      for (int i = str.length() - 1; i >= 0; i--) {
        if (str.charAt(i) == '1') termList.add(str.length() - i - 1);
      }
      return termList;
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    List<Integer> terms = getTermList();
    for (Integer a : terms) {
      sb.append(a).append(',');
    }
    if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  @Override
  public int compareTo(Terms o) {
    return this.value - o.value;
  }

  @Override
  public boolean equals(Object obj) {
    return ((Terms) obj).value == this.value;
  }

  @Override
  public int hashCode() {
    return value;
  }

}
