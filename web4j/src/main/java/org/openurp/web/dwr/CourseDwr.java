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
package org.openurp.web.dwr;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.service.CourseService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CourseDwr {

  private CourseService courseService;

  private EntityDao entityDao;

  public Course getCourse(Project project, String code) {
    return courseService.getCourse(project, code);
  }

  /**
   * 页面上填充多个课程代码，逗号相隔
   *
   * @param courseCodes
   * @return [课程名称(学分)*]
   */
  public String[] getCourseByCodes(String courseCodes) {
    if (Strings.isNotEmpty(courseCodes)) {
      OqlBuilder<Course> query = OqlBuilder.from(Course.class).where("code  in (:codes)",
          Strings.split(courseCodes.toUpperCase(), ','));
      List<Course> list = (List<Course>) entityDao.search(query);
      String[] courseinfos = new String[list.size()];
      if (!list.isEmpty()) {
        int i = 0;
        for (Iterator<Course> iter = list.iterator(); iter.hasNext(); ) {
          Course course =  iter.next();
          courseinfos[i] = course.getName() + "(" + course.getDefaultCredits() + ")";
          i++;
        }
      }
      return courseinfos;
    }
    return null;
  }

  public Map<String, Object> getCourseByIdDwr(Long id) {
    List<Course> list = entityDao
        .search(OqlBuilder.from(Course.class, "course").where("course.id=:id", id).cacheable());
    if (CollectUtils.isEmpty(list)) {
      return null;
    }
    return convert(list.get(0));
  }

  private Map<String, Object> convert(Course c) {
    Map<String, Object> data = CollectUtils.newHashMap();
    data.put("id", c.getId().toString());
    Map<String, Object> depart = CollectUtils.newHashMap();
    depart.put("id", c.getDepartment().getId());
    depart.put("name", c.getDepartment().getName());

    data.put("department", depart);
    data.put("code", c.getCode());
    data.put("name", c.getName());
    data.put("enName", c.getEnName());
    data.put("defaultCredits", c.getDefaultCredits());
    data.put("creditHours", c.getCreditHours());
    data.put("weeks", c.getWeeks());
    data.put("weekHours", c.getWeekHours());
    return data;
  }

  private List<Map<String, Object>> convert(List<Course> cs) {
    List<Map<String, Object>> rs = CollectUtils.newArrayList();
    for (Course c : cs) {
      rs.add(convert(c));
    }
    return rs;
  }

  /**
   * dwr用
   *
   * @param code
   * @return
   */
  public List<Map<String, Object>> searchCoursesByCodeOrName(String codeOrName) {
    String like = '%' + codeOrName + '%';
    if (Strings.isNotBlank(codeOrName) && codeOrName.contains("(")) {
      String[] str = codeOrName.split("\\(");
      if (str.length > 1) {
        String code = str[1].trim();
        if (code.endsWith(")")) {
          code = code.substring(0, code.length() - 1);
        }
        return convert(entityDao.search(OqlBuilder.from(Course.class, "course").where(
            "(course.name like :name and course.code like :code) or course.code like :codeOrName or course.name like :codeOrName",
            "%" + str[0].trim() + "%", "%" + code + "%", like).where("course.endOn is null")
            .orderBy("course.code").orderBy("course.name").limit(1, 10)));
      }
    }
    return convert(entityDao.search(OqlBuilder.from(Course.class, "course")
        .where("course.code like :code or course.name like :code", like).where("course.endOn is null")
        .orderBy("course.code").orderBy("course.name").limit(1, 10)));
  }

  /**
   * DWR用
   *
   * @param studentCode
   * @param codeOrName
   * @param projectId
   * @return
   */
  public List<Map<String, Object>> searchCourseByProjectAndCodeOrName(String studentCode, String codeOrName,
                                                                      String projectId) {
    String like = '%' + codeOrName + '%';
    Long proId = Long.parseLong(projectId);
    return convert(entityDao.search(OqlBuilder.from(Course.class, "course")
        .where("course.code like :code or course.name like :code", like)
        .where("course.project.id=:projectId", proId).orderBy("course.code").orderBy("course.name")
        .limit(1, 10)));
  }

  public void setCourseService(CourseService courseService) {
    this.courseService = courseService;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
