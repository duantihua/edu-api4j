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
package org.openurp.edu.clazz.model;

import org.beangle.commons.lang.tuple.Pair;

import java.util.List;

public class RestrictionPair {

  private Restriction restriction;
  private Pair<Boolean, List<?>> gradeLimit;
  private Pair<Boolean, List<?>> genderLimit;
  private Pair<Boolean, List<?>> levelLimit;
  private Pair<Boolean, List<?>> eduTypeLimit;
  private Pair<Boolean, List<?>> majorLimit;
  private Pair<Boolean, List<?>> departmentLimit;
  private Pair<Boolean, List<?>> squadLimit;
  private Pair<Boolean, List<?>> directionLimit;
  private Pair<Boolean, List<?>> stdTypeLimit;

  public RestrictionPair() {
    super();
  }

  public RestrictionPair(Restriction restriction) {
    this.restriction = restriction;
  }

  public Restriction getRestriction() {
    return restriction;
  }

  public void setRestriction(Restriction restriction) {
    this.restriction = restriction;
  }

  public Pair<Boolean, List<?>> getGradeLimit() {
    return gradeLimit;
  }

  public void setGradeLimit(Pair<Boolean, List<?>> gradeLimit) {
    this.gradeLimit = gradeLimit;
  }

  public Pair<Boolean, List<?>> getGenderLimit() {
    return genderLimit;
  }

  public void setGenderLimit(Pair<Boolean, List<?>> genderLimit) {
    this.genderLimit = genderLimit;
  }

  public Pair<Boolean, List<?>> getLevelLimit() {
    return levelLimit;
  }

  public void setLevelLimit(Pair<Boolean, List<?>> levelLimit) {
    this.levelLimit = levelLimit;
  }

  public Pair<Boolean, List<?>> getEduTypeLimit() {
    return eduTypeLimit;
  }

  public void setEduTypeLimit(Pair<Boolean, List<?>> eduTypeLimit) {
    this.eduTypeLimit = eduTypeLimit;
  }

  public Pair<Boolean, List<?>> getMajorLimit() {
    return majorLimit;
  }

  public void setMajorLimit(Pair<Boolean, List<?>> majorLimit) {
    this.majorLimit = majorLimit;
  }

  public Pair<Boolean, List<?>> getDepartmentLimit() {
    return departmentLimit;
  }

  public void setDepartmentLimit(Pair<Boolean, List<?>> departmentLimit) {
    this.departmentLimit = departmentLimit;
  }

  public Pair<Boolean, List<?>> getSquadLimit() {
    return squadLimit;
  }

  public void setSquadLimit(Pair<Boolean, List<?>> squadLimit) {
    this.squadLimit = squadLimit;
  }

  public Pair<Boolean, List<?>> getDirectionLimit() {
    return directionLimit;
  }

  public void setDirectionLimit(Pair<Boolean, List<?>> directionLimit) {
    this.directionLimit = directionLimit;
  }

  public Pair<Boolean, List<?>> getStdTypeLimit() {
    return stdTypeLimit;
  }

  public void setStdTypeLimit(Pair<Boolean, List<?>> stdTypeLimit) {
    this.stdTypeLimit = stdTypeLimit;
  }

}
