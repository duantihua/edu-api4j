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
package org.openurp.edu.program.plan.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.edu.base.code.model.Education;
import org.openurp.edu.base.model.Project;

/**
 * 公共共享计划
 */
@Entity(name = "org.openurp.edu.program.plan.model.SharePlan")
public class SharePlan extends AbstractCoursePlan implements Cloneable {

  private static final long serialVersionUID = -9012605313915822262L;

  /** 名称 */
  @NotNull
  @Size(max = 255)
  private String name;

  /** 开始年级 */
  @NotNull
  @Size(max = 10)
  private String fromGrade;
  /** 截至年级 */
  @NotNull
  @Size(max = 10)
  private String toGrade;

  /** 项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 培养层次 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Education education;

  /** 共享课程组 */
  @OneToMany(mappedBy = "plan", targetEntity = ShareCourseGroup.class)
  @OrderBy("indexno")
  @Cascade(CascadeType.ALL)
  protected List<CourseGroup> groups = CollectUtils.newArrayList();

  /** 开始日期 */
  @NotNull
  private Date beginOn;

  /** 结束日期 结束日期包括在有效期内 */
  private Date endOn;

  /** 创建时间 */
  protected java.util.Date createdAt;

  /** 最后修改时间 */
  protected java.util.Date updatedAt;

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Object clone() throws CloneNotSupportedException {
    SharePlan shareCoursePlan = (SharePlan) super.clone();
    shareCoursePlan.setGroups(new ArrayList<CourseGroup>());
    shareCoursePlan.setId(null);
    for (CourseGroup cg : getGroups()) {
      if (null == cg.getParent()) {
        CourseGroup cgClone = (CourseGroup) cg.clone();
        cgClone.setPlan(shareCoursePlan);
        shareCoursePlan.addGroup(cgClone);
      }
    }
    return shareCoursePlan;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Education getEducation() {
    return education;
  }

  public void setEducation(Education education) {
    this.education = education;
  }

  public List<CourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<CourseGroup> groups) {
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

}
