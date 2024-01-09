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
import org.openurp.base.hr.model.Teacher;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 上课调整变更申请<br>
 * 对教学运行中的教学任务进行变更申请，该申请的处理需要管理员进行审批。<br>
 * 申请调课、换课的实际变动需要管理员在课程安排模块进行手工调整。
 */
@Entity(name = "org.openurp.edu.clazz.app.model.CurriculumChangeApplication")
public class CurriculumChangeApplication extends LongIdObject {

  private static final long serialVersionUID = 828477765227607522L;

  /** 教师 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Teacher teacher;

  /** 教学任务 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /**
   * 变更具体内容
   */
  @Size(max = 300)
  private String requisition;

  /**
   * 更新时间
   */
  @NotNull
  private Date time;

  /**
   * 审核是否通过
   */
  private Boolean passed;

  /**
   * 牵涉的课时数
   */
  private float schoolHours;

  /**
   * 备注
   */
  @Size(max = 2000)
  private String remark;

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public float getSchoolHours() {
    return schoolHours;
  }

  public void setSchoolHours(float schoolHours) {
    this.schoolHours = schoolHours;
  }

  public String getRequisition() {
    return requisition;
  }

  public void setRequisition(String requisition) {
    this.requisition = requisition;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  /**
   * @return the passed
   */
  public Boolean getPassed() {
    return passed;
  }

  /**
   * @param passed
   *          the passed to set
   */
  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

}
