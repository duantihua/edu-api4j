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
package org.openurp.edu.grade.config;

import org.openurp.base.edu.model.Project;
import org.openurp.base.model.NumberIdTimeObject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 系统报表模板定义<br>
 * 系统报表是项目范围内的各类业务的自定义报表定义。项目属性为空的，为缺省模板。
 * <ul>
 * <li>category 表示种类例如成绩部分、计划部分</li>
 * <li>code表示该报表代码</li>
 * <li>name表示该报表的名称</li>
 * <li>remark表示该报表的说明</li>
 * <li>template表示该报表的模板或者路径说明</li>
 * </ul>
 * 项目和代码 联合唯一
 */
@Entity(name = "org.openurp.edu.grade.config.TranscriptTemplate")
public class TranscriptTemplate extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = 3073741215864713333L;

  /** 项目 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  /** 类别 */
  @NotNull
  @Size(max = 255)
  private String category;

  /** 代码(项目内重复) */
  @NotNull
  @Size(max = 50)
  private String code;

  /** 名称 */
  @NotNull
  @Size(max = 100)
  private String name;

  /** 备注 */
  @Size(max = 500)
  private String remark;

  /** 模板路径 */
  @NotNull
  @Size(max = 200)
  private String template;

  /** 选项 */
  @Size(max = 500)
  private String options;

  /** 纸张大小 */
  @NotNull
  private String pageSize = "A4";

  /** 横向Portrait/纵向Landscape */
  private String orientation = "Portrait";

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getOptions() {
    return options;
  }

  public void setOptions(String options) {
    this.options = options;
  }

  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  public String getOrientation() {
    return orientation;
  }

  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }

}
