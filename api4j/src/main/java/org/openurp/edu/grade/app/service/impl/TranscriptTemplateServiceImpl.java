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
package org.openurp.edu.grade.app.service.impl;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.edu.model.Project;
import org.openurp.edu.grade.config.TranscriptTemplate;
import org.openurp.edu.grade.app.service.TranscriptTemplateService;

/**
 * 报表模板默认实现
 */
public class TranscriptTemplateServiceImpl extends BaseServiceImpl implements TranscriptTemplateService {

  public TranscriptTemplate getTemplate(Project project, String code) {
    OqlBuilder<TranscriptTemplate> builder = OqlBuilder.from(TranscriptTemplate.class, "rt");
    builder.where("rt.project =:project and rt.code=:code", project, code).cacheable();
    List<TranscriptTemplate> templates = entityDao.search(builder);
    return (templates.isEmpty()) ? null : templates.get(0);
  }

  public List<TranscriptTemplate> getCategoryTemplates(Project project, String category) {
    OqlBuilder<TranscriptTemplate> builder = OqlBuilder.from(TranscriptTemplate.class, "rt");
    builder.where("rt.project =:project and rt.category=:category", project, category).cacheable();
    return entityDao.search(builder);
  }

}
