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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Operation;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.code.edu.model.ExamStatus;
import org.openurp.code.edu.model.ExamType;
import org.openurp.code.edu.model.GradeType;
import org.openurp.base.std.model.Student;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.exam.model.ExamTaker;
import org.openurp.edu.grade.course.model.CourseGrade;
import org.openurp.edu.grade.course.model.CourseGradeSetting;
import org.openurp.edu.grade.course.model.CourseGradeState;
import org.openurp.edu.grade.course.model.ExamGrade;
import org.openurp.edu.grade.course.service.CourseGradePublishListener;
import org.openurp.edu.grade.course.service.CourseGradeSettings;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 在发布总评时，根据期末成绩生成补考和缓考记录
 */
public class ExamTakerGeneratePublishListener extends BaseServiceImpl implements CourseGradePublishListener {

  private CourseGradeSettings settings;

  /**
   * 禁止补考的课程名称
   */
  private String[] forbiddenCourseNames = new String[0];
  /**
   * 禁止补考的课程类型
   */
  private String[] forbiddenCourseTypeNames = new String[0];
  /**
   * 禁止补考的修读类别
   */
  private String[] forbiddenCourseTakeTypeNames = new String[0];

  private static final ExamType Makeup = new ExamType(ExamType.MAKEUP);
  private static final ExamType Delay = new ExamType(ExamType.DELAY);

  public List<Operation> onPublish(Collection<CourseGrade> grades, CourseGradeState gradeState,
                                   GradeType[] gradeTypes) {
    List<Operation> operations = CollectUtils.newArrayList();
    boolean hasEndGa = false;
    for (GradeType gradeType : gradeTypes) {
      if (gradeType.getId().equals(GradeType.GA_ID)) {
        hasEndGa = true;
        break;
      }
    }
    if (!hasEndGa) return operations;

    if (isClazzForbidden(gradeState.getClazz())) return operations;
    CourseGradeSetting setting = settings.getSetting(gradeState.getClazz().getProject());
    Map<Student, ExamTaker> examTakers = getExamTakers(gradeState.getClazz());
    for (CourseGrade grade : grades)
      operations.addAll(publishOneGrade(grade, setting, gradeTypes, examTakers));
    return operations;
  }

  public List<Operation> onPublish(CourseGrade grade, GradeType[] gradeTypes) {
    List<Operation> operations = CollectUtils.newArrayList();
    boolean hasGa = false;
    for (GradeType gradeType : gradeTypes) {
      if (gradeType.getId().equals(GradeType.GA_ID)) {
        hasGa = true;
        break;
      }
    }
    if (!hasGa) return operations;
    final Clazz clazz = grade.getClazz();
    if (isClazzForbidden(clazz)) return operations;
    CourseGradeSetting setting = settings.getSetting(clazz.getProject());
    Map<Student, ExamTaker> examTakers = getExamTakers(clazz, grade.getStd());
    operations.addAll(publishOneGrade(grade, setting, gradeTypes, examTakers));
    return operations;
  }

  protected boolean isClazzForbidden(Clazz clazz) {
    if (null != clazz) {
      for (String courseName : forbiddenCourseNames)
        if (clazz.getCourse().getName().contains(courseName)) return true;

      for (String courseTypeName : forbiddenCourseTypeNames)
        if (clazz.getCourseType().getName().contains(courseTypeName)) return true;
    }
    return false;
  }

  protected boolean isCourseTakeTypeForbidden(CourseGrade grade) {
    for (String takeTypeName : forbiddenCourseTakeTypeNames)
      if (grade.getCourseTakeType().getName().contains(takeTypeName)) return true;
    return false;
  }

  /**
   * 判断学生参加补考/缓考/null
   *
   * @param endGrade
   * @return
   */
  protected Integer getMakeupOrDelayExamTypeId(CourseGradeSetting setting, ExamGrade endGrade) {
    // 不允许补/缓考的修读类别
    if (isCourseTakeTypeForbidden(endGrade.getCourseGrade())) return null;
    ExamStatus examStatus = endGrade.getExamStatus();
    // 缓考
    if (examStatus.isHasDeferred()) {
      return ExamType.DELAY;
    } else {
      if (setting.getNoMakeupTakeTypes().contains(endGrade.getCourseGrade().getCourseTakeType())) {
        return null;
      } else {
        if (setting.getNoMakeupExamStatuses().contains(examStatus)) {
          return null;
        } else {
          return ExamType.MAKEUP;
        }
      }
    }
  }

  private Map<Student, ExamTaker> getExamTakers(Clazz clazz) {
    OqlBuilder<ExamTaker> builder = OqlBuilder.from(ExamTaker.class, "examTaker");
    builder.where("examTaker.clazz=:clazz and examTaker.examType in (:examTypes)", clazz,
            new ExamType[]{Makeup, Delay});
    List<ExamTaker> examTakers = entityDao.search(builder);
    Map<Student, ExamTaker> takers = CollectUtils.newHashMap();
    for (ExamTaker examTaker : examTakers) {
      takers.put(examTaker.getStd(), examTaker);
    }
    return takers;
  }

  private Map<Student, ExamTaker> getExamTakers(Clazz clazz, Student std) {
    OqlBuilder<ExamTaker> builder = OqlBuilder.from(ExamTaker.class, "examTaker");
    builder.where(
            "examTaker.std=:std and examTaker.clazz=:clazz and examTaker.examType in (:examTypes) and examTaker.activity is null",
            std, clazz, new ExamType[]{Makeup, Delay});
    List<ExamTaker> examTakers = entityDao.search(builder);
    Map<Student, ExamTaker> takers = CollectUtils.newHashMap();
    for (ExamTaker examTaker : examTakers) {
      takers.put(examTaker.getStd(), examTaker);
    }
    return takers;
  }

  public List<Operation> publishOneGrade(CourseGrade grade, CourseGradeSetting setting,
                                         GradeType[] gradeTypes, Map<Student, ExamTaker> examTakers) {
    List<Operation> operations = CollectUtils.newArrayList();
    // 查找期末考试成绩
    ExamGrade endGrade = grade.getExamGrade(new GradeType(GradeType.END_ID));
    if (null == endGrade) return operations;

    Clazz clazz = grade.getClazz();
    Student std = grade.getStd();

    ExamTaker taker = null;
    // if (!grade.isPassed() && !endGrade.isPassed()) {
    if (!grade.isPassed()) {
      Integer examTypeId = getMakeupOrDelayExamTypeId(setting, endGrade);
      if (null != examTypeId) taker = getOrCreateExamTaker(std, clazz, new ExamType(examTypeId), examTakers);
      if (null == taker) {
        // 删除补考和缓考记录
        addRemoveExamTakers(operations, std, examTakers, Makeup, Delay);
      } else {
        operations.addAll(Operation.saveOrUpdate(taker).build());
        if (taker.getExamType().equals(Makeup)) addRemoveExamTakers(operations, std, examTakers, Delay);
        if (taker.getExamType().equals(Delay)) addRemoveExamTakers(operations, std, examTakers, Makeup);
      }
    } else {
      // 删除补考记录
      if (null != grade.getExamGrade(new GradeType(GradeType.DELAY_ID)))
        addRemoveExamTakers(operations, std, examTakers, Makeup);
      // 删除缓考记录
      if (null != grade.getGrade(new GradeType(GradeType.MAKEUP_GA_ID)))
        addRemoveExamTakers(operations, std, examTakers, Delay);
    }
    return operations;
  }

  private void addRemoveExamTakers(List<Operation> operations, Student std,
                                   Map<Student, ExamTaker> examTakers, ExamType... examTypes) {
    ExamTaker taker = examTakers.get(std);
    if (null != taker) {
      for (ExamType examType : examTypes) {
        if (taker.getExamType().equals(examType)) operations.addAll(Operation.remove(taker).build());
      }
    }
  }

  /**
   * 查询和创建应考记录
   *
   * @param std
   * @param clazz
   * @param examType
   * @return
   */
  private ExamTaker getOrCreateExamTaker(Student std, Clazz clazz, ExamType examType,
                                         Map<Student, ExamTaker> examTakers) {
    ExamTaker taker = examTakers.get(std);
    if (null == taker) {
      taker = new ExamTaker();
      taker.setStd(std);
      taker.setClazz(clazz);
      taker.setSemester(clazz.getSemester());
      taker.setExamType(examType);
      taker.setExamStatus(new ExamStatus(ExamStatus.NORMAL));
    }
    return taker;
  }

  public void setForbiddenCourseNames(String names) {
    forbiddenCourseNames = Strings.split(names, ",");
    if (null == forbiddenCourseNames) forbiddenCourseNames = new String[0];
  }

  public void setForbiddenCourseTypeNames(String names) {
    forbiddenCourseTypeNames = Strings.split(names, ",");
    if (null == forbiddenCourseTypeNames) forbiddenCourseTypeNames = new String[0];
  }

  public void setForbiddenCourseTakeTypeNames(String names) {
    forbiddenCourseTakeTypeNames = Strings.split(names, ",");
    if (null == forbiddenCourseTakeTypeNames) forbiddenCourseTakeTypeNames = new String[0];
  }

  public void setSettings(CourseGradeSettings settings) {
    this.settings = settings;
  }

}
