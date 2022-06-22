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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.orm.hibernate.udt.WeekState;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 用于将数字数组变成用户可以看懂的文字的工具类
 */
public class NumberRangeDigestor {

  /**
   * 使用 {@link NumberRangeFormatter} 构造文字
   *
   * @param numberSequence
   * @param textResource
   * @return
   */
  public static String digest(int[] numberSequence, TextResource textResource) {
    return digest(numberSequence, textResource, NumberRangeFormatter.getInstance());
  }

  /**
   * 根据输入的数字序列，返回[1-10]单，[2-10]双，[4-12]之类的文字
   *
   * @param numberSequence
   *
   *          <pre>
   *
   * 数字序列，顺序无所谓。会自动排序。
   * 从而达到对周状态Digestor的效果
   *          </pre>
   *
   * @param textResource 国际化资源
   * @param formatter 格式配置
   * @return 如果输入的是null或者长度为0的数组，返回""
   */
  public static String digest(int[] numberSequence, TextResource textResource,
      NumberRangeFormatter formatter) {
    if (numberSequence == null || numberSequence.length == 0) { return ""; }
    Arrays.sort(numberSequence);

    List<NumberRange> patterns = CollectUtils.newArrayList();
    NumberRange lastPattern = NumberRange.newInstance(numberSequence[0]);
    patterns.add(lastPattern);
    for (int i = 1; i < numberSequence.length; i++) {
      int number = numberSequence[i];
      if (!lastPattern.test(number)) {
        lastPattern = lastPattern.guessNextPattern(number);
        patterns.add(lastPattern);
      }
    }
    StringBuilder sb = new StringBuilder();
    for (Iterator<NumberRange> iterator = patterns.iterator(); iterator.hasNext();) {
      NumberRange pattern = iterator.next();
      if (!pattern.isAbandon()) {
        sb.append(formatter.format(pattern, textResource, iterator.hasNext()));
      }
    }
    return sb.toString();
  }

  /**
   * @see #digest(int[], TextResource)
   * @param numberSequence
   * @param textResource
   * @return
   */
  public static String digest(Integer[] numberSequence, TextResource textResource) {
    if (numberSequence == null || numberSequence.length == 0) { return ""; }
    int[] integers = new int[numberSequence.length];
    for (int i = 0; i < numberSequence.length; i++) {
      integers[i] = numberSequence[i];
    }
    return digest(integers, textResource);
  }

  /**
   * @see #digest(int[], TextResource, NumberRangeFormatter)
   * @param numberSequence
   * @param textResource
   * @param formatter
   * @return
   */
  public static String digest(Integer[] numberSequence, TextResource textResource,
      NumberRangeFormatter formatter) {
    if (numberSequence == null || numberSequence.length == 0) { return null; }
    int[] integers = new int[numberSequence.length];
    for (int i = 0; i < numberSequence.length; i++) {
      integers[i] = numberSequence[i];
    }
    return digest(integers, textResource, formatter);
  }

  public static String digest(WeekState state) {
    if (null == state) return "";
    Integer[] weekIndecies = state.getWeekList().toArray(new Integer[0]);
    String digest = NumberRangeDigestor.digest(weekIndecies, null);
    return digest.replace("[", "").replace("]", "").replace("number.range.odd", "单")
        .replace("number.range.even", "双");
  }
}
