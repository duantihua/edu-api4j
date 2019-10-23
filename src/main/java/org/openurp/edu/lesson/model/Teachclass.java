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
package org.openurp.edu.lesson.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Component;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.CourseAbilityRate;
import org.openurp.edu.base.code.model.ExamType;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.lesson.util.GenderRatio;

/**
 * 教学任务中的教学班.
 */
@Embeddable
public class Teachclass implements Component, Cloneable, Serializable {
  private static final long serialVersionUID = 895173901324223302L;

  /** 教学班简称 */
  @NotNull
  @Size(max = 4000)
  @Column(name = "class_name")
  private String name;

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
  private int stdCount;

  /** 最大人数 */
  private int limitCount;

  /**
   * 是否锁定人数上限<br>
   * 工程技术大学用到该字段
   */
  private boolean limitLocked;

  /**
   * 保留人数<br>
   * 一个任务的真实的人数上限 = limitCount - reservedCount
   */
  private int reservedCount;

  /** 上课名单 */
  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
  private Set<CourseTaker> courseTakers = new HashSet<CourseTaker>();

  /** 语言等级 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private List<CourseAbilityRate> abilityRates = new ArrayList<CourseAbilityRate>();

  /**
   * 考试名单
   */
  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
  private Set<ExamTaker> examTakers = new HashSet<ExamTaker>();

  /**
   * 限制条件组
   */
  @OneToMany(mappedBy = "lesson", orphanRemoval = true, cascade = { CascadeType.ALL })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  @OrderBy("id")
  private List<CourseLimitGroup> limitGroups = CollectUtils.newArrayList();

  public Teachclass() {
    super();
  }

  public List<ExamTaker> getExamTakers(ExamType examType) {
    if (CollectUtils.isEmpty(getExamTakers())) {
      return Collections.emptyList();
    } else {
      List<ExamTaker> rs = CollectUtils.newArrayList();
      for (ExamTaker taker : getExamTakers()) {
        if (examType.equals(taker.getExamType())) {
          rs.add(taker);
        }
      }
      return rs;
    }
  }

  /**
   * 计算某一考试类型的考生人数
   *
   * @param examType
   * @return
   */
  public int calcExamCount(ExamType examType) {
    return getExamTakers(examType).size();
  }

  /**
   * 复制一个教学班，但并不复制他所在的教学任务引用<br>
   * 和教学班中的实际学生修读信息和实际学生数.
   */
  public Teachclass clone() {
    try {
      Teachclass clone = (Teachclass) super.clone();
      clone.setLimitGroups(new ArrayList<CourseLimitGroup>());
      for (CourseLimitGroup group : getLimitGroups()) {
        CourseLimitGroup clone_group = (CourseLimitGroup) group.clone();
        clone.getLimitGroups().add(clone_group);
      }

      clone.setCourseTakers(new HashSet<CourseTaker>());
      for (CourseTaker taker : getCourseTakers()) {
        CourseTaker clone_taker = (CourseTaker) taker.clone();
        clone.getCourseTakers().add(clone_taker);
      }
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

  public void addLimitGroups(Lesson lesson, List<CourseLimitGroup> groups) {
    for (CourseLimitGroup group : groups) {
      group.setLesson(lesson);
      limitGroups.add(group);
    }
  }

  public void addLimitGroups(Lesson lesson, CourseLimitGroup... groups) {
    for (CourseLimitGroup group : groups) {
      group.setLesson(lesson);
      limitGroups.add(group);
    }
  }

  /**
   * 获取或者创建forClass=true的限制组<br>
   * 如果已经有，那么返回第一个forClass=true的限制组
   *
   * @return
   */
  public CourseLimitGroup getOrCreateDefaultLimitGroup() {
    for (CourseLimitGroup limitGroup : limitGroups) {
      if (limitGroup.isForClass()) { return limitGroup; }
    }
    CourseLimitGroup forClassGroup = new CourseLimitGroup();
    forClassGroup.setForClass(true);
    getLimitGroups().add(forClassGroup);
    return forClassGroup;
  }

  public CourseTaker getCourseTaker(Student std) {
    return getCourseTaker(std.getId());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStdCount() {
    return stdCount;
  }

  public void setStdCount(int stdCount) {
    this.stdCount = stdCount;
  }

  public Set<CourseTaker> getCourseTakers() {
    return courseTakers;
  }

  public void setCourseTakers(Set<CourseTaker> courseTakers) {
    this.courseTakers = courseTakers;
  }

  public int getLimitCount() {
    return limitCount;
  }

  public void setLimitCount(int limitCount) {
    this.limitCount = limitCount;
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

  public Set<ExamTaker> getExamTakers() {
    return examTakers;
  }

  public void setExamTakers(Set<ExamTaker> examTakers) {
    this.examTakers = examTakers;
  }

  public boolean isLimitLocked() {
    return limitLocked;
  }

  public void setLimitLocked(boolean limitLocked) {
    this.limitLocked = limitLocked;
  }

  public List<CourseLimitGroup> getLimitGroups() {
    return limitGroups;
  }

  public void setLimitGroups(List<CourseLimitGroup> limitGroups) {
    this.limitGroups = limitGroups;
  }

  public int getReservedCount() {
    return reservedCount;
  }

  public void setReservedCount(int reservedCount) {
    this.reservedCount = reservedCount;
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
}
