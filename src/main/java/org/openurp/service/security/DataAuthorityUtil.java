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
package org.openurp.service.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.util.DataAuthorityPredicate;

/**
 * 数据权限工具类
 *
 *
 */
public class DataAuthorityUtil {

  public static Map predicates = new HashMap();

  public static DataAuthorityPredicate predicateWithSimpleName;

  public static DataAuthorityPredicate departPpredicate;

  static {
    predicateWithSimpleName = new DataAuthorityPredicate("", "", "stdType", "department");
    departPpredicate = new DataAuthorityPredicate("", "", "", "department");
    DataAuthorityPredicate taskForTeachDepartPredicate = new DataAuthorityPredicate("", "",
        "teachclass.stdType", "teachDepart");
    /**
     * 注释掉是为了避免依赖eams-teach-schedule造成循环依赖
     * predicates.put("TaskGroup", new TaskGroupInDataRealmPredicate());
     */
    predicates.put("Squad", predicateWithSimpleName);
    predicates.put("Teacher", departPpredicate);
    predicates.put("Major", predicateWithSimpleName);
    predicates.put("Course", predicateWithSimpleName);
    predicates.put("TeachPlan", predicateWithSimpleName);
    predicates.put("TeachTaskForTeachDepart", taskForTeachDepartPredicate);
    predicates.put("Student", new DataAuthorityPredicate("", "", "stdType", "department"));
  }

  /**
   * 判断实体对象是否在权限范围内.<br>
   *
   * @param category
   *          要判别的权限的数据种类.<br>
   *          （没有使用数据实体的类名，应为由像CGLIB之类的库会伪装成代码中引用的类型）
   * @param entity
   * @param stdTypeIdSeq
   * @param departIdSeq
   * @return
   */
  public static boolean isInDataRealm(String category, Object entity, String stdTypeIdSeq,
      String departIdSeq) {
    return getPredicate(category, entity, stdTypeIdSeq, departIdSeq).apply(entity);
  }

  public static boolean isInDataRealm(DataAuthorityPredicate predicate, Object entity, String stdTypeIdSeq,
      String departIdSeq) {
    predicate.setStdTypeDataRealm(stdTypeIdSeq);
    predicate.setDepartDataRealm(departIdSeq);
    return predicate.apply(entity);
  }

  /**
   * @param entity
   * @return
   */
  private static DataAuthorityPredicate getPredicate(String category, Object entity, String stdTypeIdSeq,
      String departIdSeq) {
    DataAuthorityPredicate predicate = (DataAuthorityPredicate) predicates.get(category);
    if (null == predicate)
      throw new RuntimeException("un registed predicate for " + entity.getClass().getName());
    predicate.setStdTypeDataRealm(stdTypeIdSeq);
    predicate.setDepartDataRealm(departIdSeq);
    return predicate;
  }

  /**
   * @param statWhat
   * @return
   */
  private static DataAuthorityPredicate getPredicate(String predicateName, String stdTypeIdSeq,
      String departIdSeq) {
    DataAuthorityPredicate predicate = (DataAuthorityPredicate) predicates.get(predicateName);
    if (null == predicate) throw new RuntimeException("un registed predicate for " + predicateName);
    predicate.setStdTypeDataRealm(stdTypeIdSeq);
    predicate.setDepartDataRealm(departIdSeq);
    return predicate;
  }

  /**
   * 将目标实体列表，按照数据权限进行过滤
   *
   * @param entities
   * @param stdTypeIdSeq
   * @param departIdSeq
   * @return
   */
  public static void filter(Collection entities, String category, String stdTypeIdSeq, String departIdSeq) {
    if (null == entities || entities.isEmpty()) return;
    CollectUtils.filter(entities,
        getPredicate(category, entities.iterator().next(), stdTypeIdSeq, departIdSeq));
  }

  /**
   * 将目标实体列表，按照数据权限进行过滤
   *
   * @param entities
   * @param stdTypeIdSeq
   * @param departIdSeq
   * @return
   */
  public static void filter(String predicateName, Collection entities, String stdTypeIdSeq,
      String departIdSeq) {
    if (null == entities || entities.isEmpty()) return;
    CollectUtils.filter(entities, getPredicate(predicateName, stdTypeIdSeq, departIdSeq));
  }

  /**
   * 不改变目标实体列表，返回按照数据权限进行过滤的新结果
   *
   * @param entities
   * @param stdTypeIdSeq
   * @param departIdSeq
   * @return
   */
  public static List select(String predicateName, List entities, String stdTypeIdSeq, String departIdSeq) {
    return CollectUtils.select(entities, getPredicate(predicateName, stdTypeIdSeq, departIdSeq));
  }

  /**
   * 不改变目标实体列表，返回按照数据权限进行过滤的新结果
   *
   * @param entities
   * @param stdTypeIdSeq
   * @param departIdSeq
   * @return
   */
  public static List select(List entities, String category, String stdTypeIdSeq, String departIdSeq) {
    return CollectUtils.select(entities,
        getPredicate(category, entities.iterator().next(), stdTypeIdSeq, departIdSeq));
  }

  public static void register(Class entityClass, DataAuthorityPredicate predicate) {
    predicates.put(entityClass.getName(), predicate);
  }

  public static void register(String predicateName, DataAuthorityPredicate predicate) {
    predicates.put(predicateName, predicate);
  }
}
