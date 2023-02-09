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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.dao.query.builder.SqlQuery;
import org.openurp.base.edu.model.CourseUnit;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.service.OccupyProcessor;
import org.openurp.base.service.SqlDao;
import org.openurp.base.service.StudentSource;
import org.openurp.base.service.wrapper.TimeZone;
import org.openurp.base.std.model.Student;
import org.openurp.base.service.wrapper.StdOccupy;
import org.openurp.edu.clazz.model.CourseTaker;

public class CourseTakerOccupyProvider extends AbstractStdOccupyProvider {

  private SqlDao sqlDao;

  public CourseTakerOccupyProvider() {
  }

  public CourseTakerOccupyProvider(Semester semester, EntityDao entityDao) {
    this.semester = semester;
    this.entityDao = entityDao;
  }

  // FIXME 93,43
  public Map getOccupyCount(StudentSource source, TimeZone zone) {
    List sqls = new ArrayList();
    // tmp="create global temporary table tmp_stds(stdId number(19)) on commit preserve rows";
    String tmpTable = "tmp_" + System.currentTimeMillis();
    String createTableSql = "create  table " + tmpTable + "(stdId number(19) primary key)";
    sqls.add(createTableSql);
    for (Student std : source.getStudents()) {
      StringBuilder sb = new StringBuilder(80);
      sb.append("insert into ").append(tmpTable);
      sb.append(" values(").append(std.getId()).append(")");
      sqls.add(sb.toString());
    }
    sqlDao.batchUpdate(sqls);
    String occupyQuery = "select count(*) as c from jxbxs_t jxb, jxrw_t rw where jxb.jxrwid=rw.id "
        + " and exists(select * from " + tmpTable + " tmpstd where tmpstd.stdId=jxb.xsid)"
        + " and rw.xnxqid=:semesterId" + " and rw.kclbid not in(93,43)"
        + " and (exists (select hd.id from pk_kchd_t hd, jszy_t zy where rw.id=hd.jxrwid"
        + " and hd.jszyid=zy.id and zy.zj=:weekId and zy.qssj<:endTime and zy.jssj>:startTime"
        + " and bitand(zy.yxzsz, :weekState)>0))";
    SqlQuery query = new SqlQuery(occupyQuery);
    Map params = new HashMap();
    query.setParams(params);
    params.put("semesterId", semester.getId());
    Map occupis = executeOccupyQuery(query, zone, new OccupyProcessor() {
      public void process(Map weekOccupy, CourseUnit unit, List datas) {
        Number count = (Number) weekOccupy.get(unit);
        if (null == count) {
          count = new Integer(0);
        }
        count = new Integer(count.intValue() + ((Number) datas.get(0)).intValue());
        weekOccupy.put(unit, count);
      }
    });
    sqls.clear();
    sqls.add("drop table " + tmpTable);
    sqlDao.batchUpdate(sqls);
    return occupis;
  }

  public Map getOccupyInfo(StudentSource source, TimeZone zone) {
    OqlBuilder query = OqlBuilder.from(CourseTaker.class, "taker");
    query.where("taker.std in (:stds)");
    query.where("taker.clazz.semester=:semester");
    query
        .where("exists( from taker.clazz.schedule.sessions activity where activity.time.weekday=:weekId"
            + " and activity.time.startOn < :startOn" + " and activity.time.beginAt < :endTime"
            + " and activity.time.endAt >:startTime" + " and bitand(activity.time.weekstate,:weekState)>0 )");
    Map params = new HashMap();
    params.put("stds", source.getStudents());
    params.put("semester", semester);
    query.params(params);

    Map occupis = executeOccupyQuery(query, zone, new OccupyProcessor() {
      public void process(Map weekOccupy, CourseUnit unit, List datas) {
        List list = (List) weekOccupy.get(unit);
        if (null == list) {
          list = new ArrayList();
        }
        list.addAll(courseTakerToOccupy(datas));
        weekOccupy.put(unit, list);
      }

      private List<StdOccupy> courseTakerToOccupy(List<CourseTaker> takers) {
        List<StdOccupy> o = new ArrayList();
        for (CourseTaker taker : takers) {
          StdOccupy occupy = new StdOccupy();
          occupy.setStd(taker.getStd());
          occupy.setCourse(taker.getClazz().getCourse());
          occupy.setRemark("上课");
          o.add(occupy);
        }
        return o;
      }
    });
    return occupis;
  }

  public void setSqlDao(SqlDao sqlDao) {
    this.sqlDao = sqlDao;
  }

}
