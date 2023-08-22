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

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.rule.model.RuleConfig;
import org.beangle.commons.entity.pojo.NumberIdTimeObject;

/**
 * 选课方案
 */
@Entity(name = "org.openurp.edu.clazz.app.model.ElectPlan")
public class ElectPlan extends NumberIdTimeObject<Long> {

  private static final long serialVersionUID = -979938480073949863L;

  /** 名称 */
  @NotNull
  private String name;

  /** 描述 */
  private String description;

  /** 规则列表 */
  @ManyToMany
  private Set<RuleConfig> ruleConfigs = CollectUtils.newHashSet();

  public ElectPlan() {
    super();
  }

  private ElectPlan(String name, String description, Set<RuleConfig> ruleConfigs) {
    this();
    setName(name);
    setDescription(description);
    setRuleConfigs(ruleConfigs);
  }

  public static ElectPlan create(String name, String description, Set<RuleConfig> ruleConfigs,
      Date createdAt) {
    ElectPlan plan = new ElectPlan(name, description, ruleConfigs);
    plan.setUpdatedAt(createdAt);
    return plan;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<RuleConfig> getRuleConfigs() {
    return ruleConfigs;
  }

  public void setRuleConfigs(Set<RuleConfig> ruleConfigs) {
    this.ruleConfigs = ruleConfigs;
  }
}
