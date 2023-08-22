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

public class HanZi2SeqStyle extends HanZiSeqStyle {
  public String buildText(String str1) {
    StringBuilder sb = new StringBuilder();
    int prev = 0;
    for (int i = 0; i < str1.length(); i++) {
      char numChar = str1.charAt(i);
      String temp = basicOf(numChar - '0');
      if (numChar - '0' > 0) {
        if (i - prev > 1) temp = CHINESE_NAMES[0] + temp;
        prev = i;
        temp = temp + priorityOf(str1.length() - i);
        sb.append(temp);
      }
    }
    String result = sb.toString();
    if (result.startsWith("一十")) result = result.substring(1);
    return result;
  }

  public static void main(String[] args) {
    HanZi2SeqStyle s = new HanZi2SeqStyle();
    for (int i = 1; i < 1101; i++) {
      System.out.println(s.buildText(String.valueOf(i)));
    }
  }
}
