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

import java.io.Serializable;

public abstract class AbstractIntArrayOccupyTable implements OccupyTable, Serializable, Cloneable {
  private static final long serialVersionUID = -2264610685600348371L;

  // 7*units
  protected int[][] occupy;

  public Number getWeekState(int week, int unit) {
    return new Integer(occupy[week][unit]);
  }

  public String getWeekStateStr(int week, int unit) {
    return Integer.toBinaryString(occupy[week][unit]);
  }

  public int[][] getOccupy() {
    return occupy;
  }

  public void setOccupy(int[][] occupy) {
    this.occupy = occupy;
  }

}
