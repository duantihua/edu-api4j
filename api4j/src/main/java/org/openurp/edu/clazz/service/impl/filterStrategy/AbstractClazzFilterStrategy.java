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
package org.openurp.edu.clazz.service.impl.filterStrategy;

import org.hibernate.Query;
import org.hibernate.Session;
import org.openurp.edu.clazz.service.ClazzFilterStrategy;

/**
 * 教学任务过滤策略
 *
 *
 */
public abstract class AbstractClazzFilterStrategy implements ClazzFilterStrategy {

  /**
   * 策略名
   */
  private String name;

  private String prefix = "from Clazz as clazz ";

  private String postfix;

  public AbstractClazzFilterStrategy() {
    super();
  }

  protected AbstractClazzFilterStrategy(String name) {
    this();
    this.name = name;
  }

  /**
   * @return Returns the category.
   */
  public String getName() {
    return name;
  }

  /**
   * @return Returns the postfix.
   */
  public String getPostfix() {
    return postfix;
  }

  /**
   * @param postfix
   *          The postfix to set.
   */
  public void setPostfix(String postfix) {
    this.postfix = postfix;
  }

  /**
   * @return Returns the prefix.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * @param prefix
   *          The prefix to set.
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * 形成过滤的匹配串
   *
   * @return
   */
  public abstract String getFilterString();

  public Query createQuery(Session session) {
    return createQuery(session, null, null);
  }

  public String getQueryString() {
    return getQueryString(null, null);
  }

  public Query createQuery(Session session, String prefix, String postfix) {
    prefix = (null == prefix) ? this.prefix : prefix;
    postfix = (null == postfix) ? this.postfix : postfix;
    return session.createQuery(prefix + getFilterString() + postfix);
  }

  public String getQueryString(String prefix, String postfix) {
    prefix = (null == prefix) ? this.prefix : prefix;
    postfix = (null == postfix) ? this.postfix : postfix;
    return prefix + getFilterString() + postfix;
  }

  /**
   * 从开课部门角度过滤
   *//*
     * public static final ClazzFilterCategory TEACHDEPART = new
     * AbstractClazzFilterStrategy("teachDepart") {
     * public String getFilterString() {
     * return " where clazz.teachDepart.id = :id ";
     * }
     * };
     * static {
     * INSTANCES.put(TEACHDEPART.getCategoryName(), TEACHDEPART);
     * INSTANCES.put(DEPART.getCategoryName(), DEPART);
     * INSTANCES.put(SPECIALITY.getCategoryName(), SPECIALITY);
     * INSTANCES.put(SPECIALITY_ASPECT.getCategoryName(), SPECIALITY_ASPECT);
     * INSTANCES.put(COURSETYPE.getCategoryName(), COURSETYPE);
     * INSTANCES.put(SQUAD.getCategoryName(), SQUAD);
     * INSTANCES.put(STDTYPE.getCategoryName(), STDTYPE);
     * INSTANCES.put(TEACHER.getCategoryName(), TEACHER);
     * INSTANCES.put(STD.getCategoryName(), STD);
     * }
     */
}
