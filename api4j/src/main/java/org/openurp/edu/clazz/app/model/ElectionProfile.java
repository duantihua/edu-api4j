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
package org.openurp.edu.clazz.app.model;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.time.DateUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.beangle.ems.rule.model.RuleConfig;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.edu.model.ProjectBasedObject;
import org.openurp.base.edu.model.Semester;
import org.openurp.edu.clazz.app.model.enums.ElectRuleType;
import org.openurp.edu.clazz.app.model.enums.ElectionProfileType;

/**
 * 选课参数
 * 选课参数中的project仅仅作为管理员数据级权限过滤的依据，不作为学生是否能看到的依据<br>
 * 学生是否能看到依据的是projects
 */
@Entity(name = "org.openurp.edu.clazz.app.model.ElectionProfile")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class ElectionProfile extends ProjectBasedObject<Long> {
  private static final long serialVersionUID = -6606179153423814495L;

  /** 参数类型 */
  @NotNull
  @Enumerated(EnumType.STRING)
  private ElectionProfileType profileType = ElectionProfileType.STD;

  /** 名称 */
  @NotNull
  private String name = "";

  /** 选课轮次 */
  @NotNull
  private int turn = 1;

  /** 教学日历 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 参数内允许选课的学生类别 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "STD_TYPE_ID", nullable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Integer> stdTypes = CollectUtils.newHashSet();

  /** 参数内允许选课的培养类型 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "EDUCATION_ID", nullable = false)
  @JoinTable(name = "ELECTION_PROFILES_EDUCATIONS")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Integer> levels = CollectUtils.newHashSet();

  /** 参数内允许选课的学生所在院系 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "DEPART_ID", nullable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Integer> departs = CollectUtils.newHashSet();

  /** 参数允许的专业 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "MAJOR_ID", nullable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Long> majors = CollectUtils.newHashSet();

  /** 参数允许的方向 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "DIRECTION_ID", nullable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Long> directions = CollectUtils.newHashSet();

  /** 参数内允许选课的年级 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "GRADE", nullable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<String> grades = CollectUtils.newHashSet();

  /** 参数内允许的特殊学生 */
  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "STD_ID", nullable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Long> stds = CollectUtils.newHashSet();

  /** 开始时间 */
  @NotNull
  private Date beginAt;

  /** 结束时间 */
  @NotNull
  private Date endAt;

  /** 选课开始时间 */
  private Date electBeginAt;

  /** 选课结束时间 */
  private Date electEndAt;

  /** 退课开始时间 */
  private Date withdrawBeginAt;

  /** 退课结束时间 */
  private Date withdrawEndAt;

  /** 选课是否开放 */
  @NotNull
  private boolean openElection;

  /** 退课是否开放 */
  @NotNull
  private boolean openWithdraw;

  /** 注意事项 */
  @NotNull
  private String notice;

  /** 全局规则 */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "RULE_CONFIG_ID", nullable = false)
  @JoinTable(name = "ELECTION_PROFILES_GENERAL_CFGS")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<RuleConfig> generalConfigs = CollectUtils.newHashSet();

  /** 选课规则 */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "RULE_CONFIG_ID", nullable = false)
  @JoinTable(name = "ELECTION_PROFILES_ELECT_CFGS")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<RuleConfig> electConfigs = CollectUtils.newHashSet();

  /** 开放选课的教学任务 */
  @ElementCollection(fetch = FetchType.LAZY)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "LESSON_ID", nullable = false)
  @JoinTable(name = "ELECTION_PROFILES_ELT_LESSONS")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Long> electableClazzes = CollectUtils.newHashSet();

  /** 退课规则 */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "RULE_CONFIG_ID", nullable = false)
  @JoinTable(name = "ELECTION_PROFILES_WD_CFGS")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<RuleConfig> withdrawConfigs = CollectUtils.newHashSet();

  /** 开放退课的教学任务 */
  @ElementCollection(fetch = FetchType.LAZY)
  @JoinColumn(name = "ELECTION_PROFILE_ID")
  @Column(name = "LESSON_ID", nullable = false)
  @JoinTable(name = "ELECTION_PROFILES_WD_LESSONS")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private Set<Long> withdrawableClazzes = CollectUtils.newHashSet();

  public ElectionProfile() {
    super();
  }

  /**
   * "yyyy-MM-dd HH:mm:ss"<br>
   *
   * @param beginDateStr
   * @param endDateStr
   */
  public ElectionProfile(String beginDate, String endDate) {
    try {
      this.beginAt = DateUtils.parseDate(beginDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
      this.endAt = DateUtils.parseDate(endDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public ElectionProfile(Date beginAt, Date endAt) {
    super();
    this.beginAt = beginAt;
    this.endAt = endAt;
  }

  public Set<RuleConfig> getElectConfigs() {
    return electConfigs;
  }

  public void setElectConfigs(Set<RuleConfig> electConfigs) {
    this.electConfigs = electConfigs;
  }

  public Date getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(Date beginAt) {
    this.beginAt = beginAt;
  }

  public Date getEndAt() {
    return endAt;
  }

  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public int getTurn() {
    return turn;
  }

  public void setTurn(int turn) {
    this.turn = turn;
  }

  public boolean isTimeSuitable() {
    return (beginAt.getTime() <= System.currentTimeMillis())
        && (System.currentTimeMillis() <= endAt.getTime());
  }

  public boolean isTimeSuitable(Date date) {
    return (beginAt.getTime() <= date.getTime()) && (date.getTime() <= endAt.getTime());
  }

  public boolean isElectionTimeSuitable(Date date) {
    return openElection && (electBeginAt.getTime() <= date.getTime())
        && (date.getTime() <= electEndAt.getTime());
  }

  public boolean isWithdrawTimeSuitable(Date date) {
    return openWithdraw && (withdrawBeginAt.getTime() <= date.getTime())
        && (date.getTime() <= withdrawEndAt.getTime());
  }

  public boolean isOutOfDate() {
    return System.currentTimeMillis() >= endAt.getTime();
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("semester", this.semester).add("turn", turn)
        .add("beginAt", beginAt).add("endAt", endAt).toString();
  }

  public List<?> getCheckerConfigs() {
    return null;
  }

  public void setCheckerConfigs(List<?> checkers) {

  }

  public Set<Integer> getDeparts() {
    return departs;
  }

  public void setDeparts(Set<Integer> departs) {
    this.departs = departs;
  }

  public Set<String> getGrades() {
    return grades;
  }

  public void setGrades(Set<String> grades) {
    this.grades = grades;
  }

  public Set<Long> getDirections() {
    return directions;
  }

  public void setDirections(Set<Long> directions) {
    this.directions = directions;
  }

  public Set<Long> getMajors() {
    return majors;
  }

  public void setMajors(Set<Long> majors) {
    this.majors = majors;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }

  public Set<Long> getStds() {
    return stds;
  }

  public void setStds(Set<Long> stds) {
    this.stds = stds;
  }

  public Set<Integer> getStdTypes() {
    return stdTypes;
  }

  public void setStdTypes(Set<Integer> stdTypes) {
    this.stdTypes = stdTypes;
  }

  public Set<Integer> getLevels() {
    return levels;
  }

  public void setEducations(Set<Integer> levels) {
    this.levels = levels;
  }

  public void setOpenElection(boolean openElection) {
    this.openElection = openElection;
  }

  public boolean isOpenElection() {
    return openElection;
  }

  public Set<RuleConfig> getWithdrawConfigs() {
    return withdrawConfigs;
  }

  public void setWithdrawConfigs(Set<RuleConfig> withdrawConfigs) {
    this.withdrawConfigs = withdrawConfigs;
  }

  public Date getElectBeginAt() {
    return electBeginAt;
  }

  public void setElectBeginAt(Date electBeginAt) {
    this.electBeginAt = electBeginAt;
  }

  public Date getElectEndAt() {
    return electEndAt;
  }

  public void setElectEndAt(Date electEndAt) {
    this.electEndAt = electEndAt;
  }

  public Date getWithdrawBeginAt() {
    return withdrawBeginAt;
  }

  public void setWithdrawBeginAt(Date withdrawBeginAt) {
    this.withdrawBeginAt = withdrawBeginAt;
  }

  public Date getWithdrawEndAt() {
    return withdrawEndAt;
  }

  public void setWithdrawEndAt(Date withdrawEndAt) {
    this.withdrawEndAt = withdrawEndAt;
  }

  public Set<Long> getElectableClazzes() {
    return electableClazzes;
  }

  public void setElectableClazzes(Set<Long> electableClazzes) {
    this.electableClazzes = electableClazzes;
  }

  public Set<Long> getWithdrawableClazzes() {
    return withdrawableClazzes;
  }

  public void setWithdrawableClazzes(Set<Long> withdrawableClazzes) {
    this.withdrawableClazzes = withdrawableClazzes;
  }

  public Set<RuleConfig> getGeneralConfigs() {
    return generalConfigs;
  }

  public void setGeneralConfigs(Set<RuleConfig> generalConfigs) {
    this.generalConfigs = generalConfigs;
  }

  public Set<RuleConfig> getConfigs(ElectRuleType type) {
    switch (type) {
    case ELECTION:
      return getElectConfigs();
    case WITHDRAW:
      return getWithdrawConfigs();
    default:
      return getGeneralConfigs();
    }
  }

  public boolean isOpenWithdraw() {
    return openWithdraw;
  }

  public void setOpenWithdraw(boolean openWithdraw) {
    this.openWithdraw = openWithdraw;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ElectionProfileType getProfileType() {
    return profileType;
  }

  public void setProfileType(ElectionProfileType profileType) {
    this.profileType = profileType;
  }
}
