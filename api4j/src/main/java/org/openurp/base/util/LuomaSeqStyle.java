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

import org.beangle.commons.text.seq.HanZiSeqStyle;

public class LuomaSeqStyle extends HanZiSeqStyle {
  // 支持的最大数字
  public static final int MAX = 99999;

  /**
   * I(1)，V(5)，X(10)，L(50)，C(100)，D(500)，M(1000)
   */

  public static final String levels[][] = { { "I", "V", "X" }, { "X", "L", "C" }, { "C", "D", "M" } };

  public String buildText(String f) {
    String n = f;
    if (!isNuneric(n)) {
      for (int c = 0; c < n.length(); c++) {
        char chr = n.toLowerCase().charAt(c);
        if (chr != 'i' & chr != 'v' & chr != 'x' & chr != 'l' & chr != 'c' & chr != 'd'
            & chr != 'm') { return null; }
      }
    }
    n = toRoman(f);
    return n;
  }

  public String toRoman(String n) {
    String r = "";
    for (int c = 0; c < n.length(); c++)
      r += calcDigit(Integer.parseInt(n.charAt(c) + ""), n.length() - c - 1);
    return r;
  }

  public String calcDigit(Integer d, int l) {
    if (l > 2) {
      String str = "";
      for (int m = 1; m <= d * Math.pow(10, l - 3); m++)
        str += "M";
      return str;
    }

    else if (d == 1) return levels[l][0];
    else if (d == 2) return levels[l][0] + levels[l][0];
    else if (d == 3) return levels[l][0] + levels[l][0] + levels[l][0];
    else if (d == 4) return levels[l][0] + levels[l][1];
    else if (d == 5) return levels[l][1];
    else if (d == 6) return levels[l][1] + levels[l][0];
    else if (d == 7) return levels[l][1] + levels[l][0] + levels[l][0];
    else if (d == 8) return levels[l][1] + levels[l][0] + levels[l][0] + levels[l][0];
    else if (d == 9) return levels[l][0] + levels[l][2];
    else return "";
  }

  public boolean isNuneric(String str) {
    for (int c = 0; c < str.length(); c++) {
      char chr = str.charAt(c);
      if (chr != '0' & chr != '1' & chr != '2' & chr != '3' & chr != '4' & chr != '5' & chr != '6'
          & chr != '7' & chr != '8' & chr != '9')
        return false;
    }
    return true;
  }

  public static void main(String[] args) {
    LuomaSeqStyle luomaSeqStyle = new LuomaSeqStyle();
    for (int i = 0; i < 1000; i++) {
      System.out.println(luomaSeqStyle.buildText(String.valueOf(i + 1)));
    }
  }
}
