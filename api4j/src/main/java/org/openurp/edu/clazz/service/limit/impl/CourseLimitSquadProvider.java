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
package org.openurp.edu.clazz.service.limit.impl;

import java.util.Map;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.clazz.model.RestrictionMeta;

public class CourseLimitSquadProvider  extends AbstractCourseLimitEntityProvider<Squad> {

  @Override
  protected void addCascadeQuery(OqlBuilder<Squad> builder, Map<RestrictionMeta, String> cascadeField) {
    builder.where("entity.project = :project",projectContext.getProject());

    if (cascadeField.isEmpty()) { return; }
    String grades = cascadeField.get(RestrictionMeta.Grade);
    String levelIds = cascadeField.get(RestrictionMeta.Level);
    String stdTypeIds = cascadeField.get(RestrictionMeta.StdType);
    String departIds = cascadeField.get(RestrictionMeta.Department);
    String majorIds = cascadeField.get(RestrictionMeta.Major);
    String directionIds = cascadeField.get(RestrictionMeta.Direction);
    if (Strings.isNotBlank(grades)) {
      builder.where("entity.grade in (:grades)", Strings.split(grades));
    }
    if (Strings.isNotBlank(levelIds)) {
      builder.where("entity.level.id in (:levelIds)", Strings.splitToInt(levelIds));
    }
    if (Strings.isNotBlank(stdTypeIds)) {
      builder.where("entity.stdType.id in (:stdTypeIds)", Strings.splitToInt(stdTypeIds));
    }
    if (Strings.isNotBlank(departIds)) {
      builder.where("entity.department.id in (:departIds)", Strings.splitToInt(departIds));
    }
    if (Strings.isNotBlank(majorIds)) {
      builder.where("entity.major.id in (:majorIds)", Strings.splitToInt(majorIds));
    }
    if (Strings.isNotBlank(directionIds)) {
      builder.where("entity.direction.id in (:directionIds)", Strings.splitToInt(directionIds));
    }
  }

  @Override
  public RestrictionMeta getMeta() {
    return RestrictionMeta.Squad;
  }
}
