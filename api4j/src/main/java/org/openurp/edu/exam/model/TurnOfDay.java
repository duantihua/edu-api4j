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

import org.beangle.commons.lang.Objects;
import org.beangle.orm.hibernate.udt.HourMinute;

public class TurnOfDay implements Comparable<TurnOfDay> {

  /**
   * 开始时间 格式采用数字.800,表示8:00
   */
  private HourMinute beginAt;

  /**
   * 结束时间 格式采用数字.1400,表示14:00
   */
  private HourMinute endAt;

  public HourMinute getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(HourMinute beginAt) {
    this.beginAt = beginAt;
  }

  public HourMinute getEndAt() {
    return endAt;
  }

  public void setEndAt(HourMinute endAt) {
    this.endAt = endAt;
  }

  public int compareTo(TurnOfDay other) {
    return Objects.compareBuilder().add(getBeginAt(), other.getBeginAt()).toComparison();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((beginAt == null) ? 0 : beginAt.hashCode());
    result = prime * result + ((endAt == null) ? 0 : endAt.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TurnOfDay other = (TurnOfDay) obj;
    if (beginAt == null) {
      if (other.beginAt != null) return false;
    } else if (!beginAt.equals(other.beginAt)) return false;
    if (endAt == null) {
      if (other.endAt != null) return false;
    } else if (!endAt.equals(other.endAt)) return false;
    return true;
  }

}
