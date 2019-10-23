/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.edu.exam.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.base.model.Semester;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.base.model.Teacher;

/**
 * 监考人员
 * NatureId(teacher,semester)
 *
 * @author chaostone
 */
@Entity(name = "org.openurp.edu.exam.model.Invigilator")
public class Invigilator extends LongIdObject {

  private static final long serialVersionUID = -2403939113974261022L;

  /** 项目 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 学年学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 教师 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Teacher teacher;

  /** 次数 */
  private int quota;

  @OneToMany(mappedBy = "invigilator", cascade = CascadeType.ALL)
  private List<InvigilationQuota> quotas = new ArrayList<InvigilationQuota>();

  /** 排除的日期 */
  @ElementCollection
  @JoinColumn(name = "invigilator_id")
  @Column(name = "exclude_on", nullable = false)
  @JoinTable(name = "invigilators_exclude_dates")
  private List<java.sql.Date> excludeDates;

  /** 备注 */
  @Size(max = 200)
  private String remark;

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getRemark() {
    return remark;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public int getQuota() {
    return quota;
  }

  public void setQuota(int quota) {
    this.quota = quota;
  }

  public List<java.sql.Date> getExcludeDates() {
    return excludeDates;
  }

  public void setExcludeDates(List<java.sql.Date> excludeDates) {
    this.excludeDates = excludeDates;
  }

  public List<InvigilationQuota> getQuotas() {
    return quotas;
  }

  public void setQuotas(List<InvigilationQuota> quotas) {
    this.quotas = quotas;
  }

  public InvigilationQuota addQuota(Campus campus, Department depart, float quota) {
    InvigilationQuota finded = null;
    for (InvigilationQuota iq : quotas) {
      if (iq.getCampus().equals(campus) && iq.getDepart().equals(depart)) {
        iq.setQuota(iq.getQuota() + quota);
        finded = iq;
        break;
      }
    }
    if (null == finded) {
      finded = new InvigilationQuota(campus, depart, quota);
      finded.setInvigilator(this);
      this.getQuotas().add(finded);
    }
    double sum = 0f;
    for (InvigilationQuota iq : quotas) {
      sum += iq.getQuota();
    }
    this.quota = new Double(Math.round(sum)).intValue();
    return finded;
  }

  public void clearQuota() {
    this.quota = 0;
    for (InvigilationQuota iq : quotas) {
      iq.setQuota(0);
    }
  }

  public boolean cleanup() {
    List<InvigilationQuota> removed = CollectUtils.newArrayList();
    double sum = 0d;
    for (InvigilationQuota iq : quotas) {
      if (Float.compare(0, iq.getQuota()) == 0) {
        removed.add(iq);
      }
      iq.setQuota(new Double(Math.round(iq.getQuota())).intValue());
      sum += iq.getQuota();
    }
    setQuota(new Double(sum).intValue());
    return quotas.removeAll(removed);
  }

  public Set<Department> getDeparts() {
    Set<Department> departs = CollectUtils.newHashSet();
    for (InvigilationQuota iq : quotas) {
      departs.add(iq.getDepart());
    }
    return departs;
  }

  public Set<Campus> getCampuses() {
    Set<Campus> cs = CollectUtils.newHashSet();
    for (InvigilationQuota iq : quotas) {
      cs.add(iq.getCampus());
    }
    return cs;
  }

  public int getCampusQuota(Campus campus) {
    double sum = 0d;
    for (InvigilationQuota iq : quotas) {
      if (iq.getCampus().equals(campus)) sum += iq.getQuota();
    }
    return new Double(sum).intValue();
  }

}
