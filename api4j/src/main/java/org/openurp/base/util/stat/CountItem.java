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

public class CountItem implements Comparable {

  Number count;

  Object what;

  public CountItem(Number count, Object what) {
    this.count = count;
    this.what = what;
  }

  public int compareTo(Object arg0) {
    return count.intValue() - ((CountItem) arg0).count.intValue();
  }

  public Number getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Object getWhat() {
    return what;
  }

  public void setWhat(Object what) {
    this.what = what;
  }

}
