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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.time.HourMinute;
import org.hibernate.annotations.Type;
import org.openurp.edu.base.model.Project;

/**
 * 考试场次
 *
 *  考试场次是定义每场考试的具体时间段。
 */
@Entity(name = "org.openurp.edu.exam.model.ExamTurn")
public class ExamTurn extends LongIdObject implements Comparable<ExamTurn> {

  private static final long serialVersionUID = -5591926891640269453L;

  /** 序号 */
  @Size(max = 20)
  private String code;

  /** 场次中文名 */
  @Size(max = 100)
  private String name;

  /**
   * 开始时间 格式采用数字.800,表示8:00
   */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute beginAt;

  /**
   * 结束时间 格式采用数字.1400,表示14:00
   */
  @Type(type = "org.beangle.commons.lang.time.hibernate.HourMinuteType")
  private HourMinute endAt;

  /** 教学项目 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  /**
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(ExamTurn other) {
    return Objects.compareBuilder().add(getCode(), other.getCode()).add(getBeginAt(), other.getBeginAt())
        .toComparison();
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
