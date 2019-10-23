/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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

import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;

/**
 * 默认数字序列转成文字的格式<br>
 * [1~10]单,[12~20]双,[22~25],26,27
 */
public class NumberRangeFormatter {

  private static final NumberRangeFormatter me = new NumberRangeFormatter();

  public static NumberRangeFormatter getInstance() {
    return me;
  }

  public String format(NumberRange pattern, TextResource textResource, boolean hasNext) {
    StringBuilder sb = new StringBuilder();
    if (pattern.getStart().equals(pattern.getEnd())) {
      sb.append(pattern.getStart());
    } else {
      sb.append('[').append(pattern.getStart()).append('-').append(pattern.getEnd()).append(']');
    }
    if (Strings.isNotBlank(pattern.getI18nKey())) {
      if (textResource != null) {
        sb.append(textResource.getText(pattern.getI18nKey()));
      } else {
        sb.append(pattern.getI18nKey());
      }
    }
    if (hasNext) {
      sb.append(',');
    }
    return sb.toString();
  }

  public String format(NumberRange pattern, TextResource textResource) {
    return format(pattern, textResource, false);
  }

}
