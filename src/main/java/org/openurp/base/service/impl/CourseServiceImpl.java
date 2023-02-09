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
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.service.CourseService;

public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

  public Course getCourse(Project project, String code) {
    OqlBuilder<Course> query = OqlBuilder.from(Course.class, "course").cacheable()
        .where("course.code = :code", code).where("course.project = :project", project);
    List<Course> courses = entityDao.search(query);
    if (CollectUtils.isNotEmpty(courses)) { return courses.get(0); }
    return null;
  }

  /**
   * @see org.openurp.base.edu.service.shufe.service.system.baseinfo.CourseService#saveCourse(org.openurp.base.edu.model.shufe.model.system.baseinfo.Course)
   */
  public void saveOrUpdate(Course course) {
    saveSetting(course);
    entityDao.saveOrUpdate(course);
  }

  private void saveSetting(Course course) {
    course.setUpdatedAt(new Date(System.currentTimeMillis()));
  }

}
