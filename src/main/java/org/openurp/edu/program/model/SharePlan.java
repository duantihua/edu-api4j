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
package org.openurp.edu.program.model;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.code.edu.model.AcademicLevel;
import org.openurp.edu.base.model.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共共享计划
 */
@Entity(name = "org.openurp.edu.program.model.SharePlan")
public class SharePlan extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -9012605313915822262L;

  /**
   * 名称
   */
  @NotNull
  @Size(max = 255)
  private String name;

  /**
   * 开始年级
   */
  @NotNull
  @Size(max = 10)
  private String fromGrade;
  /**
   * 截至年级
   */
  @NotNull
  @Size(max = 10)
  private String toGrade;

  /**
   * 项目
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /**
   * 培养层次
   */
  @ManyToOne(fetch = FetchType.LAZY)
  private AcademicLevel level;

  /**
   * 共享课程组
   */
  @OneToMany(mappedBy = "plan", targetEntity = ShareCourseGroup.class)
  @OrderBy("indexno")
  @Cascade(CascadeType.ALL)
  protected List<ShareCourseGroup> groups = CollectUtils.newArrayList();

  /**
   * 开始日期
   */
  @NotNull
  private Date beginOn;

  /**
   * 结束日期 结束日期包括在有效期内
   */
  private Date endOn;

  /**
   * 创建时间
   */
  protected java.util.Date createdAt;

  /**
   * 最后修改时间
   */
  protected java.util.Date updatedAt;

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  @Size(max = 100)
  private String remark;

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }


  public List<ShareCourseGroup> getTopCourseGroups() {
    if (getGroups() == null) { return new ArrayList<ShareCourseGroup>(); }
    List<ShareCourseGroup> res = new ArrayList<ShareCourseGroup>();
    for (ShareCourseGroup group : getGroups()) {
      if (group != null && group.getParent() == null) res.add(group);
    }
    return res;
  }


  public Object clone() throws CloneNotSupportedException {
    SharePlan shareCoursePlan = (SharePlan) super.clone();
    shareCoursePlan.setGroups(new ArrayList<ShareCourseGroup>());
    shareCoursePlan.setId(null);
    for (ShareCourseGroup cg : getGroups()) {
      if (null == cg.getParent()) {
        ShareCourseGroup cgClone = (ShareCourseGroup) cg.clone();
        cgClone.setPlan(shareCoursePlan);
        shareCoursePlan.addGroup(cgClone);
      }
    }
    return shareCoursePlan;
  }

  public void addGroup(ShareCourseGroup group) {
    if (null == getGroups()) {
      setGroups(new ArrayList<ShareCourseGroup>());
    }
    getGroups().add(group);
    group.updateCoursePlan(this);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AcademicLevel getLevel() {
    return level;
  }

  public void setLevel(AcademicLevel level) {
    this.level = level;
  }

  public List<ShareCourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<ShareCourseGroup> groups) {
    this.groups = groups;
  }

  public Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(Date beginOn) {
    this.beginOn = beginOn;
  }

  public Date getEndOn() {
    return endOn;
  }

  public void setEndOn(Date endOn) {
    this.endOn = endOn;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public java.util.Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(java.util.Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getFromGrade() {
    return fromGrade;
  }

  public void setFromGrade(String fromGrade) {
    this.fromGrade = fromGrade;
  }

  public String getToGrade() {
    return toGrade;
  }

  public void setToGrade(String toGrade) {
    this.toGrade = toGrade;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
