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
import java.util.List;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.TimeEntity;
import org.beangle.commons.lang.functor.Predicate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.model.Campus;
import org.openurp.base.model.Department;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.base.model.School;
import org.openurp.base.model.TemporalEntity;
import org.openurp.code.edu.model.EduCategory;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.edu.base.code.model.StdLabel;
import org.openurp.edu.base.code.model.StdType;

/**
 * 项目
 */
@Entity(name = "org.openurp.edu.base.model.Project")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project extends NumberIdTimeObject<Integer> implements TimeEntity, TemporalEntity {
  private static final long serialVersionUID = 1905920232617502052L;

  public static final Predicate<Department> TEACHING = new Predicate<Department>() {
    public Boolean apply(Department depart) {
      return depart.isTeaching();
    }
  };
  /** 名称 */
  @Column(unique = true)
  @NotNull
  @Size(max = 100)
  private String name;

  /** 名称 */
  @Column(unique = true)
  @NotNull
  @Size(max = 100)
  private String code;

  /** 适用学校 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private School school;

  /** 校区列表 */
  @ManyToMany
  @NotNull
  @Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<Campus> campuses = CollectUtils.newArrayList();

  /** 部门列表 */
  @ManyToMany(targetEntity = Department.class)
  @NotNull
  @Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @OrderColumn(name = "idx")
  private List<Department> departments = CollectUtils.newArrayList();

  /** 培养层次列表 */
  @ManyToMany
  @NotNull
  @Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<EducationLevel> levels = CollectUtils.newArrayList();

  /** 学生分类列表 */
  @ManyToMany
  @NotNull
  @Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<StdLabel> stdLabels = CollectUtils.newArrayList();

  /** 学生类别列表 */
  @ManyToMany
  @NotNull
  @Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<StdType> stdTypes = CollectUtils.newArrayList();

  /** 使用校历 */
  @ManyToMany
  @NotNull
  @Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<Calendar> calendars = CollectUtils.newArrayList();

  /** 描述 */
  @Size(max = 500)
  private String description;

  /** 是否辅修 */
  @NotNull
  private boolean minor;

  /**教育类别*/
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EduCategory category;
  /** 生效时间 */
  @NotNull
  protected java.sql.Date beginOn;

  /** 失效时间 */
  protected java.sql.Date endOn;

  /**
   * 项目配置项
   */
  @ElementCollection
  @MapKeyColumn(name = "name")
  @Column(name = "value_", length = 4000)
  private Map<String, String> properties = CollectUtils.newHashMap();

  public List<Semester> getSemesters() {
    List<Semester> semesters = new ArrayList<Semester>();
    for (Calendar calendar : calendars) {
      semesters.addAll(calendar.getSemesters());
    }
    return semesters;
  }

  public Project() {
    super();
  }

  public Project(Integer id) {
    super();
    this.setId(id);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<EducationLevel> getLevels() {
    return levels;
  }

  public void setLevels(List<EducationLevel> levels) {
    this.levels = levels;
  }

  public List<Calendar> getCalendars() {
    return calendars;
  }

  public void setCalendars(List<Calendar> calendars) {
    this.calendars = calendars;
  }

  public List<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(List<Department> departments) {
    this.departments = departments;
  }

  public List<Campus> getCampuses() {
    return campuses;
  }

  public void setCampuses(List<Campus> campuses) {
    this.campuses = campuses;
  }

  public List<StdLabel> getStdLabels() {
    return stdLabels;
  }

  public void setStdLabels(List<StdLabel> labels) {
    this.stdLabels = labels;
  }

  public List<StdType> getStdTypes() {
    return stdTypes;
  }

  public void setStdTypes(List<StdType> types) {
    this.stdTypes = types;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isMinor() {
    return minor;
  }

  public void setMinor(boolean minor) {
    this.minor = minor;
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

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  public List<Department> getTeachingDeparts() {
    return CollectUtils.select(this.departments, TEACHING);
  }

  public List<Department> getColleges() {
    return CollectUtils.select(this.departments, TEACHING);
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public EduCategory getCategory() {
    return category;
  }

  public void setCategory(EduCategory category) {
    this.category = category;
  }

}
