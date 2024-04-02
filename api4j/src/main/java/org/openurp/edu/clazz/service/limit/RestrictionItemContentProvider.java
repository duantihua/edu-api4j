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
package org.openurp.edu.clazz.service.limit;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.openurp.edu.clazz.model.ClazzRestrictionMeta;

public interface RestrictionItemContentProvider<T> {

  /**
   * 根据所给的内容，返回 identifier->entity 的Map<br>
   * 比如：<br>
   * 如果LimitItem是年级，那么返回 "2011"->"2011"<br>
   * 如果LimitItem是专业，那么返回 "专业ID" -> 专业
   *
   * @param content
   *          可以是用逗号分割的id，也可以直接是字符串，比如年级就是这样 ,2011,2012,2013,
   * @return
   */
  public Map<String, T> getContents(String content);

  /**
   * 根据所给的内容，返回 identifier->entityName 的Map<br>
   * 比如：<br>
   * 如果LimitItem是年级，那么返回 "2011"->"2011"<br>
   * 如果LimitItem是专业，那么返回 "专业ID" -> 专业名称
   *
   * @param content
   *          可以是用逗号分割的id，也可以直接是字符串，比如年级就是这样 ,2011,2012,2013,
   * @return
   */
  public Map<String, String> getContentIdTitleMap(String content);

  /**
   * 根据name和code查询，获得content代表的entity集合以外的其他entity，比如年级、学生类别、培养层次、院系等
   *
   * @param content
   *          可以是用逗号分割的id，也可以直接是字符串，比如年级就是这样 ,2011,2012,2013,
   * @param term
   *          用于作为查询条件的entity的name或者code
   * @param limit
   *          分页要求
   * @return
   */
  public List<T> getOtherContents(String content, String term, PageLimit limit);

  /**
   * 级联获得content代表的entity集合以外的其他entity集合，比如专业、方向、班级、计划
   *
   * @param content
   *          可以是用逗号分割的id，也可以直接是字符串，比如年级就是这样 ,2011,2012,2013,
   * @param term
   *          用于作为查询条件的entity的name或者code
   * @param limit
   *          分页要求
   * @param cascadeField
   *          级联查询所需要的数据，RestrictionItemMeta.id->content
   * @return
   */
  public List<T> getCascadeContents(String content, String term, PageLimit limit,
      Map<ClazzRestrictionMeta, String> cascadeField);

  /**
   * 获得本Provider所对应的RestrictionMeta
   *
   * @return
   */
  public ClazzRestrictionMeta getMeta();

}
