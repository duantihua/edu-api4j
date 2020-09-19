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

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.edu.base.model.Student;

/**
 * 个人计划
 */
@Entity(name = "org.openurp.edu.program.model.StdPlan")
public class StdPlan extends AbstractCoursePlan {

  private static final long serialVersionUID = -3116489688046896991L;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 课程组 */
  @OneToMany(orphanRemoval = true, targetEntity = StdCourseGroup.class, cascade = { CascadeType.ALL })
  @OrderBy("indexno")
  @JoinColumn(name = "plan_id", nullable = false)
  private List<CourseGroup> groups = CollectUtils.newArrayList();

  /** 开始日期 */
  @NotNull
  private Date beginOn;

  /** 结束日期 结束日期包括在有效期内 */
  private Date endOn;

  /** 备注 */
  @Size(max = 800)
  private String remark;

  public List<CourseGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<CourseGroup> groups) {
    this.groups = groups;
  }

  public StdPlan() {
    super();
  }

  /**
   * 如果仅仅是克隆一个专业培养计划，而不会去保存，那么使用本方法没有问题<br>
   * <b> 但是如果需要保存一个克隆对象，那么不应该在这里克隆然后保存，应该使用对应的Service/Dao来克隆<br>
   * 因为保存一个培养计划及其课程组需要级联，而这种级联必须手动设置，Hibernate无法自动完成。 </b>
   */
  public Object clone() throws CloneNotSupportedException {
    return (StdPlan) super.clone();
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
