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
package org.openurp.app.security;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

/**
 * 系统功能资源
 * 代表一组系统功能点的集合.<br>
 * <p>
 * 系统模块之间存在基于代码表示上的父子级联关系.<br>
 * 上下级关系是通过模块代码的包含关系体现的。<br>
 * 系统模块作为权限分配的基本单位.
 * <p>
 *
 * @author chaostone 2005-9-26
 * @since 3.0.0
 */
public class FuncResource extends IntegerIdObject {
  private static final long serialVersionUID = -8285208615351119572L;

  /** 模块名字 */
  private String name;

  /** 模块标题 */
  private String title;

  /** 资源访问范围 */
  private Scope scope = Scope.Private;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return Strings.concat(name, "[", title, "]");
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("name", this.name).add("id", this.id).toString();
  }

  public static enum Scope {

    /** 不受保护的公共资源 */
    Public(0),
    /** 受保护的公有资源 */
    Protected(1),
    /** 受保护的私有资源 */
    Private(2);

    int value;

    private Scope(int value) {
      this.value = value;
    }
  }
}
