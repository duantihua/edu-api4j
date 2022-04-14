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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Component;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Department;
import org.openurp.base.edu.code.model.CourseAbilityRate;
import org.openurp.base.edu.model.Student;
import org.openurp.edu.clazz.util.GenderRatio;

/**
 * 教学任务中的教学班.
 */
@Embeddable
public class Enrollment implements Component, Cloneable, Serializable {
  private static final long serialVersionUID = 895173901324223302L;

  /** 入学年份 */
  private String grade;

  /** 男女比例 */
  @NotNull
  @Type(type = "org.beangle.data.jpa.hibernate.udt.GenderRatioType")
  private GenderRatio genderRatio = GenderRatio.empty;

  /** 学生所在部门 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department depart;

  /** 学生人数 */
  @NotNull
  private int actual;

  /** 最大人数 */
  private int capacity;

  /**
   * 是否锁定人数上限
   */
  private boolean locked;

  /**
   * 保留人数<br>
   * 一个任务的真实的人数上限 = capacity - reserved
   */
  private int reserved;

  /** 上课名单 */
  @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
  private Set<CourseTaker> courseTakers = new HashSet<CourseTaker>();

  /** 上课名单 */
  @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL,orphanRemoval = true)
  private List<Subclazz> subclazzes = new ArrayList<Subclazz>();

  /** 语言等级 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private List<CourseAbilityRate> abilityRates = new ArrayList<CourseAbilityRate>();

  /**
   * 限制条件组
   */
  @OneToMany(mappedBy = "clazz", orphanRemoval = true, cascade = { CascadeType.ALL })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  @OrderBy("id")
  private List<Restriction> restrictions = CollectUtils.newArrayList();

  public Enrollment() {
    super();
  }

  /**
   * 复制一个教学班，但并不复制他所在的教学任务引用<br>
   * 和教学班中的实际学生修读信息和实际学生数.
   */
  public Enrollment clone() {
    try {
      Enrollment clone = (Enrollment) super.clone();
      clone.setRestrictions(new ArrayList<Restriction>());
      for (Restriction group : getRestrictions()) {
        Restriction clone_group = (Restriction) group.clone();
        clone.getRestrictions().add(clone_group);
      }

      clone.setCourseTakers(new HashSet<CourseTaker>());
      for (CourseTaker taker : getCourseTakers()) {
        CourseTaker clone_taker = (CourseTaker) taker.clone();
        clone.getCourseTakers().add(clone_taker);
      }
      clone.setSubclazzes(new ArrayList<Subclazz>());
      clone.setAbilityRates(new ArrayList<CourseAbilityRate>(getAbilityRates()));
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  public CourseTaker getCourseTaker(Long stdId) {
    for (CourseTaker taker : getCourseTakers()) {
      if (taker.getStd().getId().equals(stdId)) return taker;
    }
    return null;
  }

  /**
   * 返回正常参加上课的上课名单CourseTaker
   *
   * @return
   */
  public Set<CourseTaker> getNormalCourseTakers() {
    Set<CourseTaker> normalTakers = CollectUtils.newHashSet();
    if (CollectUtils.isNotEmpty(courseTakers)) {
      for (CourseTaker courseTaker : courseTakers) {
        if (!courseTaker.isFreeListening()) {
          normalTakers.add(courseTaker);
        }
      }
    }
    return normalTakers;
  }

  public void addLimitGroups(Clazz clazz, List<Restriction> groups) {
    for (Restriction group : groups) {
      group.setClazz(clazz);
      restrictions.add(group);
    }
  }

  public void addLimitGroups(Clazz clazz, Restriction... groups) {
    for (Restriction group : groups) {
      group.setClazz(clazz);
      restrictions.add(group);
    }
  }

  /**
   * 获取或者创建Prime=true的限制组<br>
   * 如果已经有，那么返回第一个Prime=true的限制组
   */
  public Restriction getOrCreateDefautRestriction() {
    for (Restriction limitGroup : restrictions) {
      if (limitGroup.isPrime()) { return limitGroup; }
    }
    Restriction forClassGroup = new Restriction();
    forClassGroup.setPrime(true);
    getRestrictions().add(forClassGroup);
    return forClassGroup;
  }

  public CourseTaker getCourseTaker(Student std) {
    return getCourseTaker(std.getId());
  }

  public int getActual() {
    return actual;
  }

  public void setActual(int stdCount) {
    this.actual = stdCount;
  }

  public Set<CourseTaker> getCourseTakers() {
    return courseTakers;
  }

  public void setCourseTakers(Set<CourseTaker> courseTakers) {
    this.courseTakers = courseTakers;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int limitCount) {
    this.capacity = limitCount;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String year) {
    this.grade = year;
  }

  public Department getDepart() {
    return depart;
  }

  public void setDepart(Department depart) {
    this.depart = depart;
  }

  public List<Restriction> getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(List<Restriction> limitGroups) {
    this.restrictions = limitGroups;
  }

  public GenderRatio getGenderRatio() {
    return genderRatio;
  }

  public void setGenderRatio(GenderRatio genderRatio) {
    this.genderRatio = genderRatio;
  }

  public List<CourseAbilityRate> getAbilityRates() {
    return abilityRates;
  }

  public void setAbilityRates(List<CourseAbilityRate> abilityRates) {
    this.abilityRates = abilityRates;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public int getReserved() {
    return reserved;
  }

  public void setReserved(int reserved) {
    this.reserved = reserved;
  }

  public List<Subclazz> getSubclazzes() {
    return subclazzes;
  }

  public void setSubclazzes(List<Subclazz> subclazzes) {
    this.subclazzes = subclazzes;
  }
}
