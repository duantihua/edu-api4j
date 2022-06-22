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
package org.openurp.base.edu.model;

import java.sql.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.sin.model.BookCategory;
import org.openurp.code.sin.model.Press;
import org.openurp.base.edu.code.BookAwardType;
import org.openurp.base.edu.code.BookType;

/**
 * 教材
 */
@Entity(name = "org.openurp.base.edu.model.Textbook")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "edu.course")
public class Textbook extends LongIdObject {

  private static final long serialVersionUID = 6059248193975178607L;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** isbn号 */
  @Column(unique = true)
  @Size(max = 50)
  private String isbn;

  /** 名称 */
  @NotNull
  @Size(max = 200)
  private String name;

  /** 作者 */
  @Size(max = 100)
  private String author;

  /** 出版社 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Press press;

  /** 版本 */
  private String edition;

  /** 价格 */
  private Integer price = 0;

  /** 说明 */
  @Size(max = 500)
  private String description;

  /** 备注 */
  @Size(max = 500)
  private String remark;

  /** 教材类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private BookType bookType;

  /** 出版年月 */
  private Date publishedOn;

  /** 分类 */
  @ManyToOne(fetch = FetchType.LAZY)
  private BookCategory category;

  /** 获奖等级 */
  @ManyToOne(fetch = FetchType.LAZY)
  private BookAwardType awardType;

  /** 奖项授予单位 */
  private String awardOrg;

  private String series;

  private String translator;

  /** 是否自编 */
  private boolean madeInSchool = true;

  /** 生效时间 */
  @NotNull
  private java.sql.Date beginOn;

  /** 失效时间 */
  private java.sql.Date endOn;

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Press getPress() {
    return press;
  }

  public void setPress(Press press) {
    this.press = press;
  }

  public String getEdition() {
    return edition;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public BookType getBookType() {
    return bookType;
  }

  public void setBookType(BookType bookType) {
    this.bookType = bookType;
  }

  public Date getPublishedOn() {
    return publishedOn;
  }

  public void setPublishedOn(Date publishedOn) {
    this.publishedOn = publishedOn;
  }

  public BookAwardType getAwardType() {
    return awardType;
  }

  public void setAwardType(BookAwardType awardType) {
    this.awardType = awardType;
  }

  public boolean isMadeInSchool() {
    return madeInSchool;
  }

  public void setMadeInSchool(boolean madeInSchool) {
    this.madeInSchool = madeInSchool;
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

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public BookCategory getCategory() {
    return category;
  }

  public void setCategory(BookCategory category) {
    this.category = category;
  }

  public String getAwardOrg() {
    return awardOrg;
  }

  public void setAwardOrg(String awardOrg) {
    this.awardOrg = awardOrg;
  }

  public String getSeries() {
    return series;
  }

  public void setSeries(String series) {
    this.series = series;
  }

  public String getTranslator() {
    return translator;
  }

  public void setTranslator(String translator) {
    this.translator = translator;
  }

}
