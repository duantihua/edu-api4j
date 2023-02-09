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
package org.openurp.edu.clazz.dao.hibernate.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.dao.ClazzCRNGenerator;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 课程代码为前缀，后边加上两位序号的编码方式
 *
 *
 * @since 2011-09-23
 */
public class CoursePrefixSeqNoGeneratorImpl extends HibernateEntityDao implements ClazzCRNGenerator {

  public final static String initSeqNo = "01";

  private final static int tail_length = 2;

  private String infix = ".";

  public void genClazzSeqNo(Clazz clazz) {
    if (Strings.isNotEmpty(clazz.getCrn())) { return; }
    String prefix = getPrefix(clazz);
    synchronized (this) {
      List<String> seqNos = getClazzNos(clazz.getProject(), clazz.getSemester(), clazz.getCourse());

      String newSeqNo = "00";
      for (String seqNo : seqNos) {
        if (gap(seqNo, newSeqNo) >= 2) {
          break;
        } else {
          newSeqNo = seqNo;
        }
      }
      newSeqNo = rollUp(newSeqNo);

      clazz.setCrn(prefix + leftPadding(newSeqNo));
    }
  }

  private List<String> allocate(List<String> seqNos, int count) {
    String newSeqNo = "00";
    int allocated = 0;
    List<String> allocatedSeqnos = CollectUtils.newArrayList();
    for (Iterator<String> iter = seqNos.iterator(); iter.hasNext();) {
      String seqNo = iter.next();
      // 中间有空隙
      if (gap(seqNo, newSeqNo) >= 2) {
        int emptySlots = gap(seqNo, newSeqNo) - 1;
        for (int i = 0; i < emptySlots; i++) {
          newSeqNo = rollUp(newSeqNo);
          allocatedSeqnos.add(leftPadding(newSeqNo));
          allocated++;
          if (allocated >= count) {
            break;
          }
        }
        if (allocated >= count) {
          break;
        }
      }
      newSeqNo = seqNo;
    }
    // 剩余末尾的序号
    while (allocated < count) {
      newSeqNo = rollUp(newSeqNo);
      allocatedSeqnos.add(leftPadding(newSeqNo));
      allocated++;
    }
    return allocatedSeqnos;
  }

  protected int gap(String a, String b) {
    if (a.equals("A0") && b.equals("99")) { return 1; }
    char[] ac = a.toCharArray();
    char[] bc = b.toCharArray();
    return ac[0] * 10 + ac[1] - (bc[0] * 10 + bc[1]);
  }

  protected String rollUp(String a) {
    int tenPos = 0; // 十位
    int onePos = 1; // 个位
    char[] ac = a.toCharArray();
    if (ac[onePos] < '9') {
      ac[onePos]++;
    } else {
      ac[onePos] = '0';
      if (ac[tenPos] < '9') {
        ac[tenPos]++;
      } else if (ac[tenPos] == '9') {
        ac[tenPos] = 'A';
      } else if (ac[tenPos] >= 'A') {
        ac[tenPos]++;
      }
    }
    return String.valueOf(ac);
  }

  public synchronized void genClazzSeqNos(Collection<Clazz> clazzes) {
    if (clazzes.isEmpty()) return;
    Map<Course, List<Clazz>> courseClazzes = CollectUtils.newHashMap();
    for (Clazz clazz : clazzes) {
      if (Strings.isEmpty(clazz.getCrn())) {
        List<Clazz> matches = courseClazzes.get(clazz.getCourse());
        if (null == matches) {
          matches = new ArrayList<Clazz>();
          courseClazzes.put(clazz.getCourse(), matches);
        }
        matches.add(clazz);
      }
    }
    for (Course course : courseClazzes.keySet()) {
      Collection<Clazz> myClazzes = (Collection<Clazz>) courseClazzes.get(course);
      Clazz firstClazz = myClazzes.iterator().next();
      List<String> allSeqNos = getClazzNos(firstClazz.getProject(), firstClazz.getSemester(), course);
      genClazzSeqNos(myClazzes, getPrefix(firstClazz), allSeqNos);
    }
  }

  /**
   * 针对一批课程生成课程序号
   * FIXME 需要测试
   *
   * @param clazzes
   *          带生成序号的课程
   * @param prefix
   *          前缀
   * @param seqNos
   *          已有的序号
   */
  protected void genClazzSeqNos(Collection<Clazz> clazzes, String prefix, List<String> seqNos) {
    Iterator<Clazz> clazzIter = clazzes.iterator();
    List<String> newSeqNos = allocate(seqNos, clazzes.size());
    for (Iterator<String> iter = newSeqNos.iterator(); iter.hasNext();) {
      String seqNo = iter.next();
      Clazz clazz = (Clazz) clazzIter.next();
      clazz.setCrn(prefix + seqNo);
    }
  }

  private String leftPadding(String newSeqNo) {
    return Strings.repeat("0", tail_length - newSeqNo.length()) + newSeqNo;
  }

  /**
   * 根据任务确定前缀
   *
   * @param clazz
   * @return
   */
  protected String getPrefix(Clazz clazz) {
    String courseCode = clazz.getCourse().getCode();
    if (Strings.isEmpty(courseCode)) {
      courseCode = get(Course.class, clazz.getCourse().getId()).getCode();
    }
    return Strings.isBlank(infix) ? courseCode : (courseCode + infix);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private List<String> getClazzNos(Project project, Semester semester, Course course) {
    String courseCode = course.getCode();
    if (Strings.isEmpty(courseCode)) {
      courseCode = get(Course.class, course.getId()).getCode();
    }
    String select = "substr(clazz.crn," + (courseCode.length() + 1 + (null == infix ? 0 : infix.length()))
        + ")";
    OqlBuilder builder = OqlBuilder.from(Clazz.class, "clazz").select(select)
        .where("clazz.project = :project and clazz.semester=:semster", project, semester)
        .where("clazz.course=:course and clazz.crn is not null", course)
        .where("length(" + select + ")=" + initSeqNo.length()).orderBy("clazz.crn");
    return (List<String>) search(builder);
  }

  public String getInfix() {
    return infix;
  }

  public void setInfix(String infix) {
    this.infix = infix;
  }

}
