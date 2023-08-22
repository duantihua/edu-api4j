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
package org.openurp.base.service.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.openurp.base.edu.model.Project;
import org.openurp.base.service.SquadService;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;

public class SquadServiceImpl extends BaseServiceImpl implements SquadService {

  public Squad getSquad(Long id) {
    return (Squad) entityDao.get(Squad.class, id);

  }

  public Squad getSquad(String code) {
    OqlBuilder query = OqlBuilder.from(Squad.class, "squad");
    query.where("squad.code=:code", code);
    List rs = entityDao.search(query);
    if (rs.isEmpty()) return null;
    else return (Squad) rs.get(0);
  }

  public void removeSquad(Long id) {
    if (null == id) return;
    entityDao.remove(entityDao.get(Squad.class, id));

  }

  public void saveOrUpdate(Squad squad) {
    // EntityUtils.evictEmptyProperty(squad);
    squad.setUpdatedAt(new Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(squad);
  }

  public int updateActualStdCount(Long squadId) {
    int stdCount = 0;
    String updateHQL = "update Squad cls set cls.actualStdCount=(\n"
        + "select count(std.id) from Squad class1 join class1.students std where class1.id=cls.id and std.inSchool=1\n"
        + ") where cls.id=:id";
    java.util.Map params = new HashMap();
    params.put("id", squadId);
    try {
      entityDao.executeUpdate(updateHQL, params);
    } catch (RuntimeException e) {
      logger.info("execproduct is failed" + "in update_classactualstdcount" + Throwables.getStackTrace(e));
      throw e;
    }
    return stdCount;
  }

  public int updateStdCount(Long squadId) {
    int stdCount = 0;
    String updateHQL = "update Squad cls set cls.stdCount=(\n"
        + "select count(std.id) from Squad class1 join class1.students std where class1.id=cls.id and std.active=1\n"
        + ") where cls.id=:id";
    java.util.Map params = new HashMap();
    params.put("id", squadId);
    try {
      entityDao.executeUpdate(updateHQL, params);
    } catch (RuntimeException e) {
      logger.info("execproduct is failed" + "in update_classstdcount" + Throwables.getStackTrace(e));
      throw e;
    }
    return stdCount;
  }

  public void batchUpdateStdCountOfClass(String squadIdSeq) {
    Long[] squadIds = Strings.transformToLong(Strings.split(squadIdSeq));
    if (null != squadIds) {
      for (int i = 0; i < squadIds.length; i++) {
        updateActualStdCount(squadIds[i]);
        updateStdCount(squadIds[i]);
      }
    }
  }

  public void batchUpdateStdCountOfClass(Long[] squadIds) {
    if (null != squadIds) {
      for (int i = 0; i < squadIds.length; i++) {
        updateActualStdCount(squadIds[i]);
        updateStdCount(squadIds[i]);
      }
    }
  }

  public void batchAddStudentClass(List students, List squades) {
    for (Iterator iterator = squades.iterator(); iterator.hasNext();) {
      Squad squad = (Squad) iterator.next();
      Set studentSet = squad.getAllStudents();
      for (Iterator iter = students.iterator(); iter.hasNext();) {
        Student student = (Student) iter.next();
        if (!studentSet.contains(student)) {
          studentSet.add(student);
          /*
           * student.getSquades().add(squad);
           * if (student.isActive()) {
           * squad.setStdCount(squad.getStdCount() + 1);
           * }
           * if (student.isInSchool()) {
           * squad.setActualStdCount(squad.getActualStdCount() + 1);
           * }
           */
        }
      }
    }
    entityDao.saveOrUpdate(students);
    entityDao.saveOrUpdate(squades);
  }

  public void batchRemoveStudentClass(List students, List squades) {
    for (Iterator iterator = squades.iterator(); iterator.hasNext();) {
      Squad squad = (Squad) iterator.next();
      Set studentSet = squad.getAllStudents();
      for (Iterator iter = students.iterator(); iter.hasNext();) {
        Student student = (Student) iter.next();
        if (studentSet.contains(student)) {
          studentSet.remove(student);
          /*
           * student.getSquades().remove(squad);
           * if (student.isActive()) {
           * squad.setStdCount(squad.getStdCount() - 1);
           * }
           * if (student.isInSchool()) {
           * squad.setActualStdCount(squad.getActualStdCount() - 1);
           * }
           */
        }
      }
    }
    entityDao.saveOrUpdate(students);
    entityDao.saveOrUpdate(squades);
  }

  public void updateStudentSquad(Student std, Collection squades, Project project) {
    List orig = EntityUtils.extractIds(squades);
    List dest = EntityUtils.extractIds(Collections.singleton(std.getSquad()));
    List addClassList = CollectUtils.subtract(orig, dest);
    List subClassList = CollectUtils.subtract(dest, orig);
    batchRemoveStudentClass(Collections.singletonList(std),
        entityDao.get(Squad.class, "id", subClassList.toArray()));
    batchAddStudentClass(Collections.singletonList(std),
        entityDao.get(Squad.class, "id", addClassList.toArray()));
  }

}
