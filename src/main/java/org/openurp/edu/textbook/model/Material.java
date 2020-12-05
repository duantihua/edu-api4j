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
package org.openurp.edu.textbook.model;

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
import org.openurp.edu.base.model.Textbook;
import org.openurp.edu.clazz.model.Clazz;

/**
 * 教学资料
 */
@Entity(name = "org.openurp.edu.textbook.model.Material")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class Material extends LongIdObject {

  private static final long serialVersionUID = 5112094284404799375L;

  /** 教学任务 */
  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  /** 教材列表 */
  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
  private List<Textbook> books = CollectUtils.newArrayList();

  /** 参考书 */
  @Size(max = 500)
  private String materials;

  /** 是否审核通过 */
  private Boolean passed;

  /** 提交时间 */
  private Date submitAt;

  /** 其它说明 */
  @Size(max = 200)
  private String remark;

  /**已经订购*/
  private boolean ordered;

  /** 教材指定状态 */
  @NotNull
  @Enumerated
  private BookAdoption adoption = BookAdoption.None;

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
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

  public String getMaterials() {
    return materials;
  }

  public void setMaterials(String materials) {
    this.materials = materials;
  }

  public Date getSubmitAt() {
    return submitAt;
  }

  public void setSubmitAt(Date submitAt) {
    this.submitAt = submitAt;
  }

  public boolean isOrdered() {
    return ordered;
  }

  public void setOrdered(boolean ordered) {
    this.ordered = ordered;
  }

  public BookAdoption getAdoption() {
    return adoption;
  }

  public void setAdoption(BookAdoption adoption) {
    this.adoption = adoption;
  }

  /**
   * 任务教材指定状态
   */
  public enum BookAdoption {
    None("推荐参考书"), UseTextBook("使用教材"), UseLecture("使用教义");

    private String fullName;

    private BookAdoption() {
    }

    private BookAdoption(String fullName) {
      this.fullName = fullName;
    }

    public String getFullName() {
      return this.fullName;
    }
  }
}
