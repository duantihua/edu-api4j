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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.openurp.base.model.Department;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.edu.model.Textbook;

/**
 * 教学资料
 *
 *
 */
@Entity(name = "org.openurp.edu.clazz.model.CourseMaterial")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
public class CourseMaterial extends LongIdObject {

  private static final long serialVersionUID = -5651820906879989473L;

  /** 课程 */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;

  /** 院系 */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 学期 */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 教材列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "eams.teach")
  private List<Textbook> books = CollectUtils.newArrayList();

  /** 参考书 */
  @Size(max = 500)
  private String referenceBooks;

  /** 其它资料 */
  @Size(max = 500)
  private String extra;

  /** 是否审核通过 */
  private Boolean passed;

  /** 审核时间 */
  private Date auditAt;

  public Date getAuditAt() {
    return auditAt;
  }

  public void setAuditAt(Date auditAt) {
    this.auditAt = auditAt;
  }

  /** 其它说明 */
  @Size(max = 500)
  private String remark;

  /** 选用说明 */
  @Size(max = 500)
  private String useExplain;

  public String getUseExplain() {
    return useExplain;
  }

  public void setUseExplain(String useExplain) {
    this.useExplain = useExplain;
  }

  /** 教材指定状态 */
  @NotNull
  @Enumerated(value = EnumType.STRING)
  private CourseMaterialStatus status = CourseMaterialStatus.ASSIGNED;

  public CourseMaterialStatus getStatus() {
    return status;
  }

  public void setStatus(CourseMaterialStatus status) {
    this.status = status;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public List<Textbook> getBooks() {
    return books;
  }

  public boolean addBook(Textbook book) {
    return this.books.add(book);
  }

  public boolean addBooks(Collection<Textbook> textbooks) {
    return this.books.addAll(textbooks);
  }

  public boolean removeBook(Textbook book) {
    return this.books.remove(book);
  }

  public boolean removeBooks(Collection<Textbook> textbooks) {
    return this.books.removeAll(textbooks);
  }

  public void setBooks(List<Textbook> books) {
    this.books = books;
  }

  public String getReferenceBooks() {
    return referenceBooks;
  }

  public void setReferenceBooks(String referenceBooks) {
    this.referenceBooks = referenceBooks;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public Boolean getPassed() {
    return passed;
  }

  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  /**
   * 课程教材指定状态
   */
  public enum CourseMaterialStatus {
    PUBLISHED("教材已发"), DONT_ASSIGNED("不需教材"), ASSIGNED("已指定");

    private String fullName;

    private CourseMaterialStatus() {
    }

    private CourseMaterialStatus(String fullName) {
      this.fullName = fullName;
    }

    public String getFullName() {
      return this.fullName;
    }
  }

}
