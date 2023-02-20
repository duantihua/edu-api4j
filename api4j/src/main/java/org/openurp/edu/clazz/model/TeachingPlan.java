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

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(name = "org.openurp.edu.clazz.model.TeachingPlan")
public class TeachingPlan extends LongIdObject {

  @ManyToOne(fetch = FetchType.LAZY)
  private Clazz clazz;

  private Locale docLocale;

  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  @ManyToOne(fetch = FetchType.LAZY)
  private User author;
  /**
   * 教学内容
   */
  @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Lesson> lessons = new ArrayList<Lesson>();

  private int fileSize;

  private String mimeType;

  private String filePath;

  private Boolean passed;

  @ManyToOne(fetch = FetchType.LAZY)
  private User auditor;

  private java.util.Date auditAt;
  private java.util.Date updatedAt;

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(Clazz clazz) {
    this.clazz = clazz;
  }

  public Locale getDocLocale() {
    return docLocale;
  }

  public void setDocLocale(Locale docLocale) {
    this.docLocale = docLocale;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  public int getFileSize() {
    return fileSize;
  }

  public void setFileSize(int fileSize) {
    this.fileSize = fileSize;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public Boolean getPassed() {
    return passed;
  }

  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

  public User getAuditor() {
    return auditor;
  }

  public void setAuditor(User auditor) {
    this.auditor = auditor;
  }

  public Date getAuditAt() {
    return auditAt;
  }

  public void setAuditAt(Date auditAt) {
    this.auditAt = auditAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
