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
package org.openurp.edu.base.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.CourseAbilityRate;
import org.openurp.edu.base.code.model.CourseCategory;
import org.openurp.edu.base.code.model.CourseHourType;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.base.code.model.Education;
import org.openurp.edu.base.code.model.ExamMode;
import org.openurp.edu.base.code.model.ScoreMarkStyle;

/**
 * 课程基本信息
 *
 *
 * @since 2008-09-24
 */
@Entity(name = "org.openurp.edu.base.model.Course")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class Course extends ProjectBasedObject<Long> implements Comparable<Course> {

  private static final long serialVersionUID = 732786365746405676L;

  /** 课程代码 */
  @Column(unique = true)
  @NotNull
  @Size(max = 32)
  protected String code;

  /** 课程名称 */
  @NotNull
  @Size(max = 255)
  protected String name;

  /** 课程英文名 */
  @Size(max = 300)
  protected String enName;

  /** 培养层次 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private List<Education> educations = new ArrayList<Education>();

  /** 课程种类代码 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseCategory category;

  /** 学分 */
  private float credits;

  /** 学时/总课时 */
  @NotNull
  private int period;

  /** 分类课时 */
  @OneToMany(mappedBy = "course", cascade = { CascadeType.ALL }, orphanRemoval = true)
  private List<CourseHour> hours = CollectUtils.newArrayList();

  /** 周课时 */
  private int weekHour;

  /** 周数 */
  private Integer weeks;

  /** 院系 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 建议课程类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType courseType;

  /** 考试方式 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ExamMode examMode;

  /** 成绩记录方式 */
  @ManyToOne(fetch = FetchType.LAZY)
  private ScoreMarkStyle markStyle;

  /** 能力等级 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<CourseAbilityRate> abilityRates = CollectUtils.newHashSet();

  /** 针对专业 */
  @ManyToMany
  private Set<Major> majors = CollectUtils.newHashSet();

  /** 排除专业 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Major> xmajors = CollectUtils.newHashSet();

  /** 常用教材 */
  @ManyToMany
  private Set<Textbook> textbooks = CollectUtils.newHashSet();

  private java.sql.Date beginOn;
  private java.sql.Date endOn;

  // /** 先修课程 */
  // @ManyToMany
  // @JoinTable(inverseJoinColumns = @JoinColumn(name = "precourse_id", referencedColumnName = "id")
  // )
  // private Set<Course> prerequisites = CollectUtils.newHashSet();

  /** 课程备注 */
  @Size(max = 500)
  protected String remark;

  public Course() {
    super();
  }

  public Course(Long id) {
    super(id);
  }

  public List<Education> getEducations() {
    return educations;
  }

  public void setEducations(List<Education> educations) {
    this.educations = educations;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public CourseCategory getCategory() {
    return category;
  }

  public void setCategory(CourseCategory category) {
    this.category = category;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public int compareTo(Course other) {
    return Objects.compareBuilder().add(getCode(), other.getCode()).toComparison();
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public int getWeekHour() {
    return weekHour;
  }

  public void setWeekHour(int weekHour) {
    this.weekHour = weekHour;
  }

  public Integer getWeeks() {
    return weeks;
  }

  public void setWeeks(Integer weeks) {
    this.weeks = weeks;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public ExamMode getExamMode() {
    return examMode;
  }

  public void setExamMode(ExamMode examMode) {
    this.examMode = examMode;
  }

  public Set<CourseAbilityRate> getAbilityRates() {
    return abilityRates;
  }

  public void setAbilityRates(Set<CourseAbilityRate> abilityRates) {
    this.abilityRates = abilityRates;
  }

  public Set<Major> getMajors() {
    return majors;
  }

  public void setMajors(Set<Major> majors) {
    this.majors = majors;
  }

  public Set<Major> getXmajors() {
    return xmajors;
  }

  public void setXmajors(Set<Major> xmajors) {
    this.xmajors = xmajors;
  }

  public Set<Textbook> getTextbooks() {
    return textbooks;
  }

  public void setTextbooks(Set<Textbook> textbooks) {
    this.textbooks = textbooks;
  }

  public String getWeekHourString() {
    if (hours.isEmpty()) {
      return "";
    } else {
      StringBuffer sb = new StringBuffer();
      for (Iterator<CourseHour> iter = hours.iterator(); iter.hasNext();) {
        CourseHour courseHour = iter.next();
        Integer value = courseHour.getWeekHour();
        sb.append(value);
        if (iter.hasNext()) {
          sb.append('+');
        }
      }
      return sb.toString();
    }
  }

  public Integer getHour(CourseHourType type) {
    for (Iterator<CourseHour> iter = hours.iterator(); iter.hasNext();) {
      CourseHour courseHour = iter.next();
      if (null != type && courseHour.getHourType().equals(type)) { return courseHour.getPeriod(); }
    }
    return null;
  }

  public Integer getHourById(Integer typeId) {
    for (Iterator<CourseHour> iter = hours.iterator(); iter.hasNext();) {
      CourseHour courseHour = iter.next();
      if (null != typeId
          && courseHour.getHourType().getId().equals(typeId)) { return courseHour.getPeriod(); }
    }
    return null;
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("id", this.getId()).add("code", this.code).add("name", this.name)
        .toString();
  }

  public List<CourseHour> getHours() {
    return hours;
  }

  public void setHours(List<CourseHour> courseHours) {
    this.hours = courseHours;
  }

  // public Map<Long, Integer> getHours() {
  // Map<Long, Integer> courseHourMap = new HashMap<Long, Integer>();
  // for (CourseHour courseHour : getCourseHours()) {
  // courseHourMap.put(courseHour.getType().getId(), courseHour.getPeriod());
  // }
  // return courseHourMap;
  // }

  public ScoreMarkStyle getMarkStyle() {
    return markStyle;
  }

  public void setMarkStyle(ScoreMarkStyle markStyle) {
    this.markStyle = markStyle;
  }

  public boolean isEnabled() {
    return null == endOn;
  }

  public void setEnabled(boolean e) {
    if (e) {
      if (null == beginOn) beginOn = new java.sql.Date(System.currentTimeMillis());
      endOn = null;
    } else {
      endOn = new java.sql.Date(System.currentTimeMillis());
    }
  }
}
