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
package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 考试情况
 * 正常、作弊、旷考等
 *
 * @since 2005-9-7
 */
@Entity(name = "org.openurp.edu.base.code.model.ExamStatus")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class ExamStatus extends Code<Integer> {

  private static final long serialVersionUID = 2044358459280080337L;

  /** 正常 */
  public static final Integer NORMAL = 1;
  public static final Integer DELAY = 2;
  public static final Integer ABSENT = 3;
  public static final Integer Unqualified=6;
  private boolean deferred = false;

  private boolean cheating = false;

  /**
   * 是否参加考试
   */
  private boolean attended;

  public ExamStatus() {
    super();
  }

  public ExamStatus(Integer id) {
    super(id);
  }

  public boolean isAttended() {
    return attended;
  }

  public void setAttended(boolean attend) {
    this.attended = attend;
  }

  public boolean isDeferred() {
    return deferred;
  }

  public void setDeferred(boolean deferred) {
    this.deferred = deferred;
  }

  public boolean isCheating() {
    return cheating;
  }

  public void setCheating(boolean cheating) {
    this.cheating = cheating;
  }

}
