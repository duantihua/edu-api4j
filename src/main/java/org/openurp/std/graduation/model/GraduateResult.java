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
package org.openurp.std.graduation.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.code.edu.model.EducationResult;
import org.openurp.base.std.model.Student;

@Entity(name = "org.openurp.std.graduation.model.GraduateResult")
public class GraduateResult extends LongIdObject {
  private static final long serialVersionUID = -1856339635822733136L;

  /** 所属的毕业审核批次 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GraduateSession session;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  private int batchNo;

  /** 获得学分 */
  private float acquiredCredits;

  /** 要求学分 */
  private float requiredCredits;

  /** 修读学分 */
  private float electedCredits;

  private java.util.Date updatedAt;

  /** 毕业审核详细结果 */
  @OneToMany(mappedBy = "result", orphanRemoval = true, targetEntity = GraduateAuditItem.class, cascade = {
      CascadeType.ALL })
  private List<GraduateAuditItem> items = CollectUtils.newArrayList();

  /**
   * 是否通过毕业审核
   * 可以为空，空代表还没有审核过
   */
  private Boolean passed;

  /** 锁定毕业审核结果 */
  @NotNull
  private boolean locked;

  /** 是否已发布 */
  @NotNull
  private boolean published;

  /** 毕业备注 */
  @Size(max = 500)
  @Column(name="graduate_comments")
  private String comments;

  /** 毕结业情况 */
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationResult educationResult;

  /**
   * @return the items
   */
  public List<GraduateAuditItem> getItems() {
    return items;
  }

  /**
   * @param items the items to set
   */
  public void setItems(List<GraduateAuditItem> items) {
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
   * @return the educationResult
   */
  public EducationResult getEducationResult() {
    return educationResult;
  }

  /**
   * @param educationResult the educationResult to set
   */
  public void setEducationResult(EducationResult educationResult) {
    this.educationResult = educationResult;
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

  public float getAcquiredCredits() {
    return acquiredCredits;
  }

  public void setAcquiredCredits(float acquiredCredits) {
    this.acquiredCredits = acquiredCredits;
  }

  public float getRequiredCredits() {
    return requiredCredits;
  }

  public void setRequiredCredits(float requiredCredits) {
    this.requiredCredits = requiredCredits;
  }

  public float getElectedCredits() {
    return electedCredits;
  }

  public void setElectedCredits(float electedCredits) {
    this.electedCredits = electedCredits;
  }

  public int getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(int batchNo) {
    this.batchNo = batchNo;
  }
}
