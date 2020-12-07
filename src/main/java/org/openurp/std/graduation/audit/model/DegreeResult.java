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
package org.openurp.std.graduation.audit.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.Degree;
import org.openurp.base.edu.model.Student;

@Entity(name = "org.openurp.std.graduation.audit.model.DegreeResult")
public class DegreeResult extends LongIdObject {

  private static final long serialVersionUID = -4311694956398918521L;

  /**
   * 所属的毕业审核批次
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GraduateSession session;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  private int batch;
  /**
   * GPA
   */
  private float gpa;

  /**
   * 平均分
   */
  private float ga;

  /**
   * 学位审核详细结果
   */
  @OneToMany(mappedBy = "result", orphanRemoval = true, targetEntity = DegreeAuditItem.class, cascade = {
          CascadeType.ALL})
  private List<DegreeAuditItem> items = CollectUtils.newArrayList();

  /**
   * 是否通过毕业审核
   * 可以为空，空代表还没有审核过
   */
  private Boolean passed;

  /**
   * 锁定毕业审核结果
   */
  private boolean locked;

  /**
   * 是否已发布
   */
  @NotNull
  private boolean published;

  /**
   * 毕业备注
   */
  @Size(max = 500)
  @Column(name="degree_comments")
  private String comments;

  /**
   * 更新时间
   */
  private java.util.Date updatedAt;

  /**
   * 学位
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private Degree degree;

  /**
   * 外语通过年月
   */
  private java.sql.Date foreignLangPassedOn;

  /**
   * @return the items
   */
  public List<DegreeAuditItem> getItems() {
    return items;
  }

  /**
   * @param items the items to set
   */
  public void setItems(List<DegreeAuditItem> items) {
    this.items = items;
  }

  /**
   * @return the passed
   */
  public Boolean getPassed() {
    return passed;
  }

  /**
   * @param passed the passed to set
   */
  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

  /**
   * @return the locked
   */
  public boolean isLocked() {
    return locked;
  }

  /**
   * @param locked the locked to set
   */
  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  /**
   * @return the published
   */
  public boolean isPublished() {
    return published;
  }

  /**
   * @param published the published to set
   */
  public void setPublished(boolean published) {
    this.published = published;
  }

  /**
   * @return the comments
   */
  public String getComments() {
    return comments;
  }

  /**
   * @param comments the comments to set
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * @return the updatedAt
   */
  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * @param updatedAt the updatedAt to set
   */
  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * @return the degree
   */
  public Degree getDegree() {
    return degree;
  }

  /**
   * @param degree the degree to set
   */
  public void setDegree(Degree degree) {
    this.degree = degree;
  }

  public GraduateSession getSession() {
    return session;
  }

  public void setSession(GraduateSession session) {
    this.session = session;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public float getGpa() {
    return gpa;
  }

  public void setGpa(float gpa) {
    this.gpa = gpa;
  }

  public float getGa() {
    return ga;
  }

  public void setGa(float ga) {
    this.ga = ga;
  }

  public java.sql.Date getForeignLangPassedOn() {
    return foreignLangPassedOn;
  }

  public void setForeignLangPassedOn(java.sql.Date foreignLangPassedOn) {
    this.foreignLangPassedOn = foreignLangPassedOn;
  }

  public int getBatch() {
    return batch;
  }

  public void setBatch(int batch) {
    this.batch = batch;
  }
}
