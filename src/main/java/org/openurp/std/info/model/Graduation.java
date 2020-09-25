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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.openurp.code.edu.model.Degree;
import org.openurp.code.edu.model.EducationResult;

/**
 * 毕业信息实现
 */
@Entity(name = "org.openurp.std.info.model.Graduation")
public class Graduation extends StudentInfoBean {

  private static final long serialVersionUID = -4102691429295031076L;

  /** 毕业证书编号（电子注册号） */
  private String code;

  /** 毕结业日期 */
  private java.sql.Date graduateOn;

  /** 毕结业情况 */
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationResult educationResult;

  /** 学位 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Degree degree;

  /** 学位授予日期 */
  private java.sql.Date degreeAwardOn;

  /** 学位证书号 */
  private String diplomaNo;

  /** 外语通过年月 */
  private java.sql.Date foreignLangPassedOn;

  public java.sql.Date getDegreeAwardOn() {
    return degreeAwardOn;
  }

  public void setDegreeAwardOn(java.sql.Date degreeAwardOn) {
    this.degreeAwardOn = degreeAwardOn;
  }

  public String getDiplomaNo() {
    return diplomaNo;
  }

  public void setDiplomaNo(String diplomaNo) {
    this.diplomaNo = diplomaNo;
  }

  public Degree getDegree() {
    return degree;
  }

  public void setDegree(Degree degree) {
    this.degree = degree;
  }

  public EducationResult getEducationResult() {
    return educationResult;
  }

  public void setEducationResult(EducationResult educationResult) {
    this.educationResult = educationResult;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the graduateOn
   */
  public java.sql.Date getGraduateOn() {
    return graduateOn;
  }

  /**
   * @param graduateOn the graduateOn to set
   */
  public void setGraduateOn(java.sql.Date graduateOn) {
    this.graduateOn = graduateOn;
  }

  public java.sql.Date getForeignLangPassedOn() {
    return foreignLangPassedOn;
  }

  public void setForeignLangPassedOn(java.sql.Date foreignLangPassedOn) {
    this.foreignLangPassedOn = foreignLangPassedOn;
  }

}
