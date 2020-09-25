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
package org.openurp.std.info.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.code.geo.model.Division;

@Entity(name = "org.openurp.std.info.model.Examinee")
public class Examinee extends StudentInfoBean {

  private static final long serialVersionUID = 8781039132558421121L;

  /** 考生号 */
  private String code;

  /** 准考证号 */
  private String examNo;

  /** 生源地 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Division originDivision;

  /** 毕业学校编号 */
  private String schoolNo;

  /** 毕业学校名称 */
  private String schoolName;

  /** 毕业日期 */
  private java.sql.Date graduateOn;

  /** 招生录取总分 */
  private Float score;

  /** 各科分数 科目->浮点数 */
  @ElementCollection
  @MapKeyColumn(name = "type_id")
  @Column(name = "score")
  private Map<Integer, Float> scores = CollectUtils.newHashMap();

  public Division getOriginDivision() {
    return originDivision;
  }

  public void setOriginDivision(Division originDivision) {
    this.originDivision = originDivision;
  }

  public String getExamNo() {
    return examNo;
  }

  public void setExamNo(String examNumber) {
    this.examNo = examNumber;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String examineeCode) {
    this.code = examineeCode;
  }

  public String getSchoolNo() {
    return schoolNo;
  }

  public void setSchoolNo(String schoolNo) {
    this.schoolNo = schoolNo;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public java.sql.Date getGraduateOn() {
    return graduateOn;
  }

  public void setGraduateOn(java.sql.Date graduateOn) {
    this.graduateOn = graduateOn;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public Map<Integer, Float> getScores() {
    return scores;
  }

  public void setScores(Map<Integer, Float> scores) {
    this.scores = scores;
  }

}
