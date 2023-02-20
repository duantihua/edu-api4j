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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.base.model.User;

/**
 * 课程邮件设定
 */
@Entity(name = "org.openurp.edu.clazz.app.model.CourseMailSetting")
public class CourseMailSetting extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = -234456414948729061L;

  /** 邮件设置描述 */
  @NotNull
  @Column(unique = true)
  private String name;

  /** 邮件模板 */
  @NotNull
  @Size(max = 3000)
  private String module;

  /** 邮件标题 */
  @NotNull
  private String title;

  /** 创建者 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User creator;

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
