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
package org.openurp.edu.grade.config;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.code.edu.model.GradeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * 成绩录入开关
 */
@Entity(name = "org.openurp.edu.grade.config.GradeInputSwitch")
public class GradeInputSwitch extends LongIdObject {

  private static final long serialVersionUID = 6765368922449105678L;

  /** 教学项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 教学日历 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 开始时间 */
  private Date beginAt;

  /** 关闭时间 */
  private Date endAt;

  /** 允许录入成绩类型 */
  @ManyToMany
  private Set<GradeType> types = CollectUtils.newHashSet();

  /** 成绩录入验证开关 */
  private boolean needValidate = false;

  /** 备注 */
  private String remark;

  /**
   * 检查该开关是否开放
   *
   * @param date
   * @return
   */
  public boolean checkOpen(Date date) {
    if (null == getBeginAt() || null == getEndAt()) { return false; }
    if (date.after(getEndAt()) || getBeginAt().after(date)) {
      return false;
    }
    return true;
  }

  public boolean checkOpen() {
    return checkOpen(new Date());
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Date getEndAt() {
    return endAt;
  }

  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }

  public Date getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(Date beginAt) {
    this.beginAt = beginAt;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Set<GradeType> getTypes() {
    return types;
  }

  public void setTypes(Set<GradeType> types) {
    this.types = types;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public boolean isNeedValidate() {
    return needValidate;
  }

  public void setNeedValidate(boolean needValidate) {
    this.needValidate = needValidate;
  }

}
