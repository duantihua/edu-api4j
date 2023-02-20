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
package org.openurp.edu.grade.course.service.impl;

import java.util.Date;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Student;
import org.openurp.edu.clazz.model.CourseTaker;

import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.service.StdGradeService;

public class StdGradeServiceImpl implements StdGradeService {

  private EntityDao entityDao;

  public Student getStdByCode(String stdCode, Project project, List<Department> departments,
      EntityDao entityDao) {
    OqlBuilder<Student> query = OqlBuilder.from(Student.class, "std");
    query.where("std.code=:code", stdCode);
    if (project == null || CollectUtils.isEmpty(departments)) {
      query.where("std is null");
    } else {
      query.where("std.project = :project", project);
      query.where("std.state.department in (:departments)", departments);
    }
    // 缺少权限限制
    List<Student> stds = entityDao.search(query);
    if (CollectUtils.isEmpty(stds)) { return null; }
    if (stds.size() == 1) {
      return stds.get(0);
    } else {
      throw new RuntimeException("数据异常");
    }
  }

  /**
   * 得到录入教学任务时，符合条件的成绩类别
   *
   * @return
   */
  public OqlBuilder<GradeType> buildGradeTypeQuery() {
    OqlBuilder<GradeType> query = OqlBuilder.from(GradeType.class, "gradeType");
    query.where("gradeType.id not in (:ids)", new Integer[] { GradeType.FINAL_ID });
    query.where("gradeType.beginOn <= :now and (gradeType.endOn is null or gradeType.endOn >= :now)",
        new Date());
    query.orderBy(Order.parse("gradeType.code asc"));
    return query;
  }

  /**
   * 得到教学任务的学生选课状态
   *
   * @return
   */
  public Object[] getStatus(String crn, String stdId, String semesterId, EntityDao entityDao) {
    OqlBuilder<CourseTaker> query = OqlBuilder.from(CourseTaker.class, "taker");
    query.where("taker.clazz.crn = :crn", crn);
    query.where("taker.std.id = :stdId", new Long(stdId));
    query.where("not exists(from " + CourseGrade.class.getName()
        + " grade where grade.std.id = :stdId and grade.clazz.crn=:crn)", new Long(stdId), crn);
    query.where("taker.clazz.semester.id = :semesterId", new Long(semesterId));
    query.select(
        "taker.clazz.id,taker.clazz.course.code,taker.clazz.course.name,taker.clazz.gradeState.gradingMode.id,taker.clazz.gradeState.gradingMode.name");
    List<?> takers = entityDao.search(query);

    if (CollectUtils.isEmpty(takers)) { return null; }
    if (takers.size() == 1) {
      return (Object[]) takers.get(0);
    } else {
      throw new RuntimeException("数据异常");
    }
  }

  /**
   * 判断一个学生在某一学期内某一门课程成绩是否存在
   *
   * @param std 学生
   * @param semester 学年学期
   * @param course 课程
   * @param project 项目
   * @return false 不存在 true 存在
   */

  public boolean checkStdGradeExists(Student std, Semester semester, Course course, Project project) {
    OqlBuilder<CourseGrade> builder = OqlBuilder.from(CourseGrade.class, "courseGrade");
    builder.where("courseGrade.semester = :semester", semester);
    builder.where("courseGrade.project = :project", project);
    builder.where("courseGrade.std = :student", std);
    builder.where("courseGrade.course = :course", course);
    List<CourseGrade> courseGrades = entityDao.search(builder);
    if (courseGrades.size() > 0) { return true; }
    return false;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
