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

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * 邮件模版
 */
@Entity(name = "org.openurp.edu.clazz.app.model.ElectMailTemplate")
public class ElectMailTemplate extends LongIdObject {
  private static final long serialVersionUID = -4430290657221915091L;

  public static final Long WITHDRAW = 1L;

  /** 主题 */
  @NotNull
  private String title;

  /** 内容 */
  @NotNull
  private String content;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
