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
package org.openurp.edu.clazz.app.model.constraint;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.code.CourseType;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.app.model.CourseTypeCreditConstraint;
import org.openurp.edu.clazz.app.model.enums.ConstraintType;

/**
 * 学分限制操作日志
 */
@Entity(name = "org.openurp.edu.clazz.app.model.constraint.ConstraintLogger")
public class ConstraintLogger extends LongIdObject {
  private static final long serialVersionUID = -3518168114908289841L;

  /** 学年学期 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 限制类型 */
  @NotNull
  @Enumerated(value = EnumType.STRING)
  private ConstraintType constraintType;

  /** 类型 */
  @NotNull
  @Size(max = 30)
  private String type;

  /** 关键字 */
  @NotNull
  private String key;

  /** 值 */
  private String value;

  /** 创建时间 */
  @NotNull
  private Date createdAt;

  /** 操作者 */
  @NotNull
  private String operator;

  public ConstraintLogger() {
    super();
  }

  public ConstraintType getConstraintType() {
    return constraintType;
  }

  public String getType() {
    return type;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getOperator() {
    return operator;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public static ConstraintLogger genLogger(StdCreditConstraint constraint, String type) {
    ConstraintLogger logger = new ConstraintLogger();
    logger.constraintType = ConstraintType.stdCreditConstraint;
    StringBuilder sb = new StringBuilder();
    sb.append(constraint.getSemester().getSchoolYear()).append(constraint.getSemester().getName());
    sb.append(",").append(constraint.getStd().getCode());
    logger.semester = constraint.getSemester();
    logger.key = sb.toString();
    logger.value = null == constraint.getMaxCredit() ? null : constraint.getMaxCredit().toString();
    logger.type = type;
    return logger;
  }

  public static ConstraintLogger genLogger(StdTotalCreditConstraint constraint, String type) {
    ConstraintLogger logger = new ConstraintLogger();
    logger.constraintType = ConstraintType.stdTotalCreditConstraint;
    logger.key = constraint.getStd().getCode();
    logger.value = null == constraint.getMaxCredit() ? null : constraint.getMaxCredit().toString();
    logger.type = type;
    return logger;
  }

  public static ConstraintLogger genLogger(CourseTypeCreditConstraint constraint, String type) {
    ConstraintLogger logger = new ConstraintLogger();
    logger.constraintType = ConstraintType.courseTypeCreditConstraint;
    StringBuilder sb = new StringBuilder();
    sb.append(constraint.getSemester().getSchoolYear()).append(constraint.getSemester().getName());
    sb.append(",[").append(constraint.getGrades()).append("],");
    sb.append(",").append(constraint.getLevel().getName());
    sb.append(",").append(constraint.getCourseType().getName()).append("(")
        .append(constraint.getCourseType().getCode()).append("),");
    logger.semester = constraint.getSemester();
    logger.key = sb.toString();
    logger.value = constraint.getLimitCredit() + "";
    logger.type = type;
    return logger;
  }

  public static ConstraintLogger genLogger(StdCourseCountConstraint constraint, CourseType courseType,
      String type) {
    ConstraintLogger logger = new ConstraintLogger();
    logger.constraintType = ConstraintType.stdCourseCountConstraint;
    StringBuilder sb = new StringBuilder();
    sb.append(constraint.getSemester().getSchoolYear()).append(constraint.getSemester().getName());
    sb.append(",").append(constraint.getStd().getCode());
    if (null == courseType) {
      sb.append(",总门数上限");
      logger.value = null == constraint.getMaxCourseCount() ? null : constraint.getMaxCourseCount() + "";
    } else {
      sb.append(",").append(courseType.getName()).append("(").append(courseType.getCode()).append(")");
      Integer maxCourseCount = constraint.getCourseTypeMaxCourseCount().get(courseType);
      logger.value = null == maxCourseCount ? null : maxCourseCount + "";
    }
    logger.semester = constraint.getSemester();
    logger.key = sb.toString();
    logger.type = type;
    return logger;
  }

  public static ConstraintLogger genLogger(StdCourseCountConstraint constraint, String type) {
    return genLogger(constraint, null, type);
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public void setConstraintType(ConstraintType constraintType) {
    this.constraintType = constraintType;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
