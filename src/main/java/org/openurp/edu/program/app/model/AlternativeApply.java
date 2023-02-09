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
package org.openurp.edu.program.app.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.User;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Student;

@Entity(name = "org.openurp.edu.program.app.model.AlternativeApply")
public class AlternativeApply extends LongIdObject {

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 被替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "alternative_applies_olds")
  private Set<Course> olds = CollectUtils.newHashSet();

  /** 已替代的课程 */
  @ManyToMany
  @JoinColumn(nullable = false)
  @JoinTable(name = "alternative_applies_news")
  private Set<Course> news = CollectUtils.newHashSet();

  /** 最后修改时间 */
  protected Date updatedAt;

  private Boolean approved;

  @Size(max = 400)
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

  public Set<Course> getOlds() {
    return olds;
  }

  public void setOlds(Set<Course> olds) {
    this.olds = olds;
  }

  public Set<Course> getNews() {
    return news;
  }

  public void setNews(Set<Course> news) {
    this.news = news;
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

  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public Date getAuditAt() {
    return auditAt;
  }

  public void setAuditAt(Date auditAt) {
    this.auditAt = auditAt;
  }
}
