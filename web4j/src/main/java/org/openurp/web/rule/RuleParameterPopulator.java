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
package org.openurp.web.rule;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleParameter;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.PopulateHelper;

/**
 * 保存rule
 */
public class RuleParameterPopulator {

  private EntityDao entityDao;

  /**
   * @param rule
   *          已经populate一部分的rule
   * @param business
   */
  public Set<RuleParameter> populateParams(Rule rule, String prefix) {
    int size = Params.getInt(prefix + "_param_size");
    Set<RuleParameter> params = CollectUtils.newHashSet();
    for (int i = 0; i < size; i++) {
      RuleParameter ruleParam = populateEntity(RuleParameter.class, prefix + "_" + i);
      if (Strings.isBlank(ruleParam.getName())) {
        continue;
      }
      //      ruleParam.setId(null);
      ruleParam.setChildren(populateParams(rule, prefix + "_" + i));
      ruleParam.setRule(rule);
      params.add(ruleParam);
    }
    return params;
  }

  @SuppressWarnings("unchecked")
  protected <T> T populateEntity(Class<T> entityClass, String shortName) {
    EntityType type = null;
    if (entityClass.isInterface()) {
      type = Model.getType(entityClass.getName());
    } else {
      type = Model.getType(entityClass);
    }
    return (T) populateEntity(type.getEntityName(), shortName);
  }

  protected Entity<?> populateEntity(String entityName, String shortName) {
    Serializable entityId = getId(shortName, Model.getType(entityName).getIdType());
    Entity<?> entity = null;
    if (null == entityId) {
      entity = (Entity<?>) PopulateHelper.populate(entityName, shortName);
    } else {
      entity = getModel(entityName, entityId);
      populate(entity, entityName, Params.sub(shortName));
    }
    return entity;
  }

  private final void populate(Entity<?> entity, String entityName, Map<String, Object> params) {
    Model.getPopulator().populate(entity, Model.getType(entityName), params);
  }

  private final <T> T getId(String name, Class<T> clazz) {
    Object[] entityIds = Params.getAll(name + ".id");
    if (Arrays.isEmpty(entityIds))
      entityIds = Params.getAll(name + "Id");
    if (Arrays.isEmpty(entityIds))
      entityIds = Params.getAll("id");
    if (Arrays.isEmpty(entityIds))
      return null;
    else {
      String entityId = entityIds[0].toString();
      int commaIndex = entityId.indexOf(',');
      if (commaIndex != -1)
        entityId = entityId.substring(0, commaIndex);
      return Params.converter.convert(entityId, clazz);
    }
  }

  private Entity<?> getModel(String entityName, Serializable id) {
    return (Entity<?>) entityDao.get(entityName, id);
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }
}
