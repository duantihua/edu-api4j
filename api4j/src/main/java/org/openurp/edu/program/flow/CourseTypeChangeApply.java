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
package org.openurp.edu.program.flow;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.User;
import org.openurp.code.edu.model.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity(name = "org.openurp.edu.program.flow.CourseTypeChangeApply")
public class CourseTypeChangeApply extends LongIdObject {
  /**
   * 学生
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType oldType;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType newType;

  /**
   * 最后修改时间
   */
  protected Date updatedAt;

  private Boolean approved;

  @Size(max = 600)
  private String remark;

  @ManyToOne(fetch = FetchType.LAZY)
  private User auditor;

  @Size(max = 600)
  private String reply;

  private Date auditAt;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public CourseType getOldType() {
    return oldType;
  }

  public void setOldType(CourseType oldType) {
    this.oldType = oldType;
  }

  public CourseType getNewType() {
    return newType;
  }

  public void setNewType(CourseType newType) {
    this.newType = newType;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Boolean getApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public User getAuditor() {
    return auditor;
  }

  public void setAuditor(User auditor) {
    this.auditor = auditor;
  }

  public Date getAuditAt() {
    return auditAt;
  }

  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public void setAuditAt(Date auditAt) {
    this.auditAt = auditAt;
  }

}
