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
package org.openurp.edu.exam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;

/**
 * 监考人员配额
 *
 * @author chaostone
 */
@Entity(name = "org.openurp.edu.exam.model.InvigilationQuotaDetail")
public class InvigilationQuotaDetail extends LongIdObject {

  private static final long serialVersionUID = -7518943329513743175L;

  /** 监考人 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected InvigilationQuota invigilationQuota;

  /** 校区 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Campus campus;

  /** 开课部门 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department depart;

  /** 次数 */
  private float quota;

  public InvigilationQuotaDetail() {
    super();
  }

  public InvigilationQuotaDetail(Campus campus, Department depart, float quota) {
    super();
    this.campus = campus;
    this.depart = depart;
    this.quota = quota;
  }

  public InvigilationQuota getInvigilationQuota() {
    return invigilationQuota;
  }

  public void setInvigilationQuota(InvigilationQuota invigilationQuota) {
    this.invigilationQuota = invigilationQuota;
  }

  public Department getDepart() {
    return depart;
  }

  public void setDepart(Department depart) {
    this.depart = depart;
  }

  public float getQuota() {
    return quota;
  }

  public void setQuota(float quota) {
    this.quota = quota;
  }

  public Campus getCampus() {
    return campus;
  }

  public void setCampus(Campus campus) {
    this.campus = campus;
  }

}
