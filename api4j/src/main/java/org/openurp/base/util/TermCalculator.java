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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.service.SemesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 计算相对学期的工具类
 */
public class TermCalculator {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  private SemesterService semesterService;

  private Map<String, Integer> termCalcCache;

  private Semester semester;

  private static Map<String, Set<Integer>> termMap = CollectUtils.newHashMap();

  static {
    Set<Integer> autumn = CollectUtils.newHashSet(1, 3, 5, 7, 9, 11);
    Set<Integer> spring = CollectUtils.newHashSet(2, 3, 4, 8, 10, 12);
    Set<Integer> all = CollectUtils.newHashSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    termMap.put("春", spring);
    termMap.put("秋", autumn);
    termMap.put("春季", spring);
    termMap.put("春秋", all);
    termMap.put("春,秋", all);
  }

  /**
   * 判断给定的学期是否在学期字符串内
   *
   * @param termStr
   * @param term
   * @return
   */
  public static final boolean inTerm(String termStr, Integer term) {
    if (Strings.contains(termStr, "*")) return true;
    Set<Integer> termSet = termMap.get(termStr);
    if (null == termSet) {
      String[] terms = Strings.split(termStr, ",");
      termSet = new HashSet<Integer>(3);
      for (String one : terms) {
        termSet.add(Numbers.toInt(one));
      }
      termMap.put(termStr, termSet);
    }
    return termSet.contains(term);
  }

  /**
   * 判断给定的学期是否在学期字符串内
   *
   * @param termStr
   * @param term
   * @return
   */
  public static final boolean lessOrEqualTerm(String termStr, Integer term) {
    if (Strings.contains(termStr, "*")) return true;
    Set<Integer> termSet = termMap.get(termStr);
    if (null == termSet) {
      String[] terms = Strings.split(termStr, ",");
      termSet = new HashSet<Integer>(3);
      for (String one : terms) {
        termSet.add(Numbers.toInt(one));
      }
      termMap.put(termStr, termSet);
    }
    for (Integer t : termSet) {
      if (t.compareTo(term) <= 0) return true;
    }
    return false;
  }

  public TermCalculator(SemesterService semesterService, Semester semester) {
    this.semesterService = semesterService;
    termCalcCache = CollectUtils.newHashMap();
    this.semester = semester;
    this.semester.getCalendar().getId();
  }

  /**
   * 计算first到second教学日历之间的学期数.<br>
   * first在second之前则返回正整数，否则返回1或负整数.<br>
   * [first,second]包含两段的学期数.<br>
   * 如果给出两个教学日历中的学生类别不一致，则返回null<br>
   * 相同教学日历,则返回1<br>
   *
   * @param pre
   * @param post
   * @param omitSmallTerm
   * @return
   */
  public int getTermBetween(Semester pre, Semester post, boolean omitSmallTerm) {
    return semesterService.getTermsBetween(pre, post, omitSmallTerm);
  }

  /**
   * 添加的方法，获得给定一段时间范围，获得和它相交的第一个学期<br>
   * 然后计算这个学期和semester之间的差距，算出学期数，目前这个方法被TeachTaskGenServiceImpl使用
   *
   * @param begOn
   * @param endOn
   * @param omitSmallTerm
   * @return
   */
  public int getTerm(java.util.Date begOn, java.util.Date endOn, boolean omitSmallTerm) {
    Integer term = termCalcCache.get(begOn.toString() + "~" + endOn.toString());
    if (term != null) {
      return term;
    }
    // 获取该年级的入学学期
    Semester enrollSemester = semesterService.getSemester(semester.getCalendar(), new Date(begOn.getTime()),
        new Date(endOn.getTime()));
    if (logger.isDebugEnabled()) {
      logger.info("calculate a term for [{}~{}] and calculate term is " + term, begOn.toString(), endOn.toString());
    }
    if (null == enrollSemester) {
      logger.info("cannot find enrollterm for grade {}~{}", begOn.toString(), endOn.toString());
      term = -1;
    } else {
      term = Integer.valueOf(semesterService.getTermsBetween(enrollSemester, semester, omitSmallTerm));
    }
    logger.info("enrollSemester is " + enrollSemester.getCode());
    logger.info("calculate a term for [{}~{}] and calculate term is " + term, begOn.toString(), endOn.toString());
    termCalcCache.put(begOn.toString() + "~" + endOn.toString(), term);
    return term == null ? -1 : term;
  }

  public int getTerm(java.util.Date date1, boolean omitSmallTerm) {
    Integer term = termCalcCache.get(date1.toString());
    if (term != null) {
      return term;
    }
    // 向后延长一个月，往往计划的开始时间或者学生时间是9-1,而学期是9月1x号
    java.util.Calendar jCalendar = java.util.Calendar.getInstance();
    jCalendar.setTime(date1);
    jCalendar.add(java.util.Calendar.DAY_OF_YEAR, 30);

    java.util.Date date = jCalendar.getTime();
    Semester enrollSemester = semesterService.getSemester(semester.getCalendar(), new Date(date.getTime()));
    if (logger.isDebugEnabled()) {
      logger.debug("calculate a term for [{}]", date.toString());
    }
    if (null == enrollSemester) {
      logger.info("cannot find enrollterm for grade {}", date.toString());
      term = -1;
    } else {
      term = Integer.valueOf(semesterService.getTermsBetween(enrollSemester, semester, omitSmallTerm));
    }
    termCalcCache.put(date.toString(), term);

    return term == null ? -1 : term;
  }

}
