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
package org.openurp.code.edu.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 其他资格考试科目
 *
 * @author chaostone
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.code.edu.model.Certificate")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class Certificate extends Code<Integer> {

  private static final long serialVersionUID = 5479972774606103066L;

  /** 其他资格考试类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CertificateCategory category;

  public Certificate() {
    super();
  }

  public Certificate(Integer id) {
    super(id);
  }

  public CertificateCategory getCategory() {
    return category;
  }

  public void setCategory(CertificateCategory category) {
    this.category = category;
  }

}
