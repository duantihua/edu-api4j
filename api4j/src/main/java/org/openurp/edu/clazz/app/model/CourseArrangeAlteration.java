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
package org.openurp.edu.clazz.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.User;
import org.openurp.base.edu.model.Semester;

/**
 * 排课变更信息
 */
@Entity(name = "org.openurp.edu.clazz.app.model.CourseArrangeAlteration")
public class CourseArrangeAlteration extends LongIdObject {

  private static final long serialVersionUID = 1L;

  /** 教学任务ID */
  @NotNull
  private Long clazzId;

  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 调课前信息 */
  @Size(max = 500)
  private String alterationBefore;

  /** 调课后信息 */
  @Size(max = 500)
  private String alterationAfter;

  /** 调课人 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User alterBy;

  /** 访问路径 */
  @Size(max = 100)
  private String alterFrom;

  /** 调课发生时间 */
  @NotNull
  private Date alterationAt;

  /**
   * @return the alterationAfter
   */
  public String getAlterationAfter() {
    return alterationAfter;
  }

  /**
   * @param alterationAfter
   *          the alterationAfter to set
   */
  public void setAlterationAfter(String alterationAfter) {
    this.alterationAfter = alterationAfter;
  }

  /**
   * @return the alterationAt
   */
  public Date getAlterationAt() {
    return alterationAt;
  }

  /**
   * @param alterationAt
   *          the alterationAt to set
   */
  public void setAlterationAt(Date alterationAt) {
    this.alterationAt = alterationAt;
  }

  /**
   * @return the alterationBefore
   */
  public String getAlterationBefore() {
    return alterationBefore;
  }

  /**
   * @param alterationBefore
   *          the alterationBefore to set
   */
  public void setAlterationBefore(String alterationBefore) {
    this.alterationBefore = alterationBefore;
  }

  public Long getClazzId() {
    return clazzId;
  }

  public void setClazzId(Long clazzId) {
    this.clazzId = clazzId;
  }

  /**
   * @return the alterBy
   */
  public User getAlterBy() {
    return alterBy;
  }

  /**
   * @param alterBy
   *          the alterBy to set
   */
  public void setAlterBy(User alterBy) {
    this.alterBy = alterBy;
  }

  /**
   * @return the alterFrom
   */
  public String getAlterFrom() {
    return alterFrom;
  }

  /**
   * @param alterFrom
   *          the alterFrom to set
   */
  public void setAlterFrom(String alterFrom) {
    this.alterFrom = alterFrom;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

}
