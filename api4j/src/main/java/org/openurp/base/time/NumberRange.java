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

/**
 * 数字序列的模式<br>
 * 存有[start, end]和i18nKey<br>
 * 都是闭区间
 */
public abstract class NumberRange {

  /**
   * 返回默认的NumberRange（连续模式）
   *
   * @param number
   * @return
   */
  public static NumberRange newInstance(int number) {
    return new ContinueRange(number);
  }

  /**
   * 这个周状态模式的起始周
   */
  protected final Integer start;

  /**
   * 这个周状态模式的结束周
   */
  protected Integer end;

  /**
   * end上一次的值
   */
  protected Integer lastEnd;

  /**
   * 本NumberRange是否丢弃
   */
  protected boolean abandon = false;

  protected String i18nKey;

  protected NumberRange(int number) {
    this.start = number;
    this.end = number;
  }

  public Integer getStart() {
    return start;
  }

  public Integer getEnd() {
    return end;
  }

  public boolean isAbandon() {
    return abandon;
  }

  public String getI18nKey() {
    return i18nKey;
  }

  /**
   * 测试新的number是否符合本Range所对应的模式。<br>
   * 如果符合，那么新的number就会变成{@link #end}<br>
   * number必须大于等于 {@link #end}
   *
   * @param number
   * @return
   */
  public boolean test(int number) {
    boolean result = internalTest(number);
    if (result && !this.end.equals(number)) {
      this.lastEnd = this.end;
      this.end = number;
    }
    return result;
  }

  protected abstract boolean internalTest(int weekIndex);

  /**
   * <pre>
   * 当新输入的number不符合当前Range时，猜测一个新的Range
   * 注意:前提必须是test的结果不符合Range
   * 一旦guess过之后，本Range就不应该再被使用了
   * </pre>
   *
   * @param number
   * @return
   */
  public NumberRange guessNextPattern(int number) {
    if (this.getClass().equals(Skip1Range.class)) {
      // 如果自己是每隔一周的形式，那么下面无论怎么弄都是不会匹配到的，因为如果能匹配到的话，test就肯定返回true了
      return newInstance(number);
    }
    // 到这里就说明当前模式是连续周，那么就返回一个从头开始的连续周Pattern
    if (!this.end.equals(this.start)) {
      // 尽量保持连续性
      return newInstance(number);
    }
    /*
     * 到这里说明start==end，且是连续周 尝试用单、双模式来实验
     */
    NumberRange mayBePattern = null;
    if (this.end % 2 == 0) {
      mayBePattern = new Skip1Range(this.end, "number.range.even");
    } else {
      mayBePattern = new Skip1Range(this.end, "number.range.odd");
    }
    if (mayBePattern.test(number)) {
      if (this.lastEnd == null) {
        // 说明刚刚guess出来就错了，那么这个pattern要抛弃掉
        this.abandon = true;
      }
      // 说明新的Pattern奏效了，那么当前Pattern的end要归到前一个值
      this.end = this.lastEnd;
      return mayBePattern;
    }
    // 到这里就说明新的Pattern完全不奏效，那么还是返回一个默认的Pattern
    return newInstance(number);
  }
}

class ContinueRange extends NumberRange {

  public ContinueRange(int start) {
    super(start);
    this.i18nKey = "";
  }

  @Override
  public boolean internalTest(int number) {
    if (number == this.end) { return true; }
    if (number - this.end == 1) { return true; }
    return false;
  }
}

class Skip1Range extends NumberRange {

  public Skip1Range(int number, String i18nKey) {
    super(number);
    this.i18nKey = i18nKey;
  }

  @Override
  public boolean internalTest(int number) {
    if (number == this.end) { return true; }
    if (number - this.end == 2) { return true; }
    return false;
  }

}
