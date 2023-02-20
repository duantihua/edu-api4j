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

public class StatItem implements StatCountor {

  Object what;

  Comparable[] countors;

  public StatItem(Object what) {
    this.what = what;
  }

  public StatItem(Object what, Comparable count) {
    this.what = what;
    this.countors = new Comparable[] { count };
  }

  public StatItem(Object what, Comparable count1, Comparable count2, Comparable count3) {
    this.what = what;
    this.countors = new Comparable[] { count1, count2, count3 };
  }

  public StatItem(Object what, Comparable count1, Comparable count2, Comparable count3, Comparable count4) {
    this.what = what;
    this.countors = new Comparable[] { count1, count2, count3, count4 };
  }

  public StatItem(Object what, Comparable count1, Comparable count2) {
    this.what = what;
    this.countors = new Comparable[] { count1, count2 };
  }

  public Comparable[] getCountors() {
    return countors;
  }

  public void setCountors(Comparable[] countor) {
    this.countors = countor;
  }

  public Object getWhat() {
    return what;
  }

  public void setWhat(Object what) {
    this.what = what;
  }

}
