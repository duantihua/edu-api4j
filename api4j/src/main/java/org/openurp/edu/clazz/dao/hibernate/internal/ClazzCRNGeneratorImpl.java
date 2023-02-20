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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.dao.ClazzCRNGenerator;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 课程序号生成器,这部分代码暂时生成4位的连续课程序号(字符型).
 */

public class ClazzCRNGeneratorImpl extends HibernateEntityDao implements ClazzCRNGenerator {

  public final static String initSeqNo = "0001";

  public void genClazzSeqNo(Clazz clazz) {
    if (Strings.isNotEmpty(clazz.getCrn())) { return; }
    synchronized (this) {
      String hql = MessageFormat.format("select crn from " + Clazz.class.getName()
          + " clazz where clazz.semester.id={0} and clazz.project.id={1} and length(clazz.crn)=4 order by crn",
          clazz.getSemester().getId().toString(), clazz.getProject().getId().toString());
      // 为了解决拆分已经有courseTakers的教学任务所发生的错误
      // FIXME 注释掉则拆分出错，不注释掉则复制出错
      List<String> crns = search(hql);
      // Collections.sort(crns);
      int newNo = 0;
      for (String seqNo : crns) {
        if (seqNo.matches(".*[^\\d]+.*")) {
          // 如果存在非数字的字符,就不能计算
          continue;
        }
        if (Numbers.toInt(seqNo) - newNo >= 2) {
          break;
        } else {
          newNo = Numbers.toInt(seqNo);
        }
      }
      newNo++;
      clazz.setCrn(Strings.repeat("0", 4 - String.valueOf(newNo).length()) + newNo);
    }
  }

  /**
   * 分配一组序号
   *
   * @param tasks
   */
  public void genClazzSeqNos(Collection<Clazz> clazzes) {
    Map<Semester, List<Clazz>> semesterTasks = CollectUtils.newHashMap();
    for (Clazz clazz : clazzes) {
      if (Strings.isEmpty(clazz.getCrn())) {
        List<Clazz> matches = semesterTasks.get(clazz.getSemester());
        if (null == matches) {
          matches = new ArrayList<Clazz>();
          semesterTasks.put(clazz.getSemester(), matches);
        }
        matches.add(clazz);
      }
    }
    for (Iterator<Semester> iter = semesterTasks.keySet().iterator(); iter.hasNext();) {
      Semester semester = iter.next();
      genClazzSeqNos(semester, semesterTasks.get(semester));
    }
  }

  private void genClazzSeqNos(Semester semester, Collection tasks) {
    if (tasks.isEmpty()) { return; }
    synchronized (this) {
      Iterator iter1 = tasks.iterator();
      Integer projectId = null;
      if (iter1.hasNext()) {
        projectId = ((Clazz) iter1.next()).getProject().getId();
      }
      String hql = MessageFormat.format(
          "select crn from org.openurp.edu.clazz.model.Clazz clazz where clazz.semester.id={0} and clazz.project.id={1} order by crn",
          semester.getId().toString(), projectId.toString());
      List allSeqNos = search(hql);
      int newSeqNo = 0;
      int seq = 0;
      int allocated = 0;
      Iterator taskIter = tasks.iterator();
      for (Iterator iter = allSeqNos.iterator(); iter.hasNext();) {
        String seqNo = (String) iter.next();
        seq = Numbers.toInt(seqNo);
        // 中间有空隙
        if ((seq - newSeqNo >= 2)) {
          int gap = seq - newSeqNo - 1;
          for (int i = 0; i < gap; i++) {
            allocated++;
            newSeqNo++;
            Clazz clazz = (Clazz) taskIter.next();
            clazz.setCrn(Strings.repeat("0", 4 - String.valueOf(newSeqNo).length()) + newSeqNo);
            if (allocated >= tasks.size()) break;
          }
          if (allocated >= tasks.size()) break;
        }
        newSeqNo = seq;
      }
      // 剩余末尾的序号
      while (allocated < tasks.size()) {
        newSeqNo++;
        allocated++;
        Clazz clazz = (Clazz) taskIter.next();
        clazz.setCrn(Strings.repeat("0", 4 - String.valueOf(newSeqNo).length()) + newSeqNo);
      }
    }
  }
}
