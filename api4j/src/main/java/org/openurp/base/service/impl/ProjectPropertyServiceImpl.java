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
package org.openurp.base.service.impl;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.ProjectProperty;
import org.openurp.base.service.ProjectPropertyService;

import java.util.List;

public class ProjectPropertyServiceImpl implements ProjectPropertyService {
  private EntityDao entityDao;

  public String get(Project project, String name, String defaultValue) {
    OqlBuilder<ProjectProperty> query = OqlBuilder.from(ProjectProperty.class, "pp");
    query.where("pp.project=:project and pp.name=:name", project, name);
    query.cacheable();
    List<ProjectProperty> properties = entityDao.search(query);
    if (properties.isEmpty()) return defaultValue;
    else return properties.get(0).getValue();
  }

  @Override
  public void set(Project project, String name, String newValue, String typeName, String description) {
    OqlBuilder<ProjectProperty> query = OqlBuilder.from(ProjectProperty.class, "pp");
    query.where("pp.project=:project and pp.name=:name", project, name);
    List<ProjectProperty> properties = entityDao.search(query);
    ProjectProperty property = null;
    if (!properties.isEmpty()) {
      property = properties.get(0);
    } else {
      property = new ProjectProperty();
      property.setProject(project);
      property.setName(name);
    }
    property.setValue(newValue);
    property.setTypeName(typeName);
    property.setDescription(description);
    entityDao.saveOrUpdate(property);
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }
}
