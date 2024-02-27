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
package org.openurp.edu.clazz.util;

import java.text.MessageFormat;
import java.util.List;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.std.model.StdType;
import org.openurp.base.edu.model.Project;

/**
 * 用来给查询教学任务的OqlBuilder添加数据级权限的类，目前没有完成
 */
public class ClazzDataRealmBuilder {

  private EntityDao entityDao;

  private OqlBuilder query;

  private String clazzAlias;

  private static final String IN_PROJECTS = "{0}.project in (:projects_{1})";
  private static final String IN_TEACT_DEPARTS = "{0}.teachDepart in (:departs_{1})";
  private static final String IN_STDTYPES = "{0} in (:stdTypes_{1})";
  private static final String IN_EDUCATIONS = "{0} in (:levels_{1})";

  private static final String IN_PROJECT_IDS = "{0}.project.id in (:projectIds_{1})";
  private static final String IN_TEACH_DEPART_IDS = "{0}.teachDepart.id in (:departIds_{1})";
  private static final String IN_STDTYPE_IDS = "{0}.id in (:stdTypeIds_{1})";
  private static final String IN_EDUCATION_IDS = "{0}.id in (:levelIds_{1})";

  private ClazzDataRealmBuilder() {

  }

  private ClazzDataRealmBuilder(EntityDao entityDao, OqlBuilder query, String clazzAlias) {
    this.entityDao = entityDao;
    this.query = query;
    this.clazzAlias = clazzAlias;
  }

  public static ClazzDataRealmBuilder start(EntityDao entityDao, OqlBuilder query, String clazzAlias) {
    return new ClazzDataRealmBuilder(entityDao, query, clazzAlias);
  }

  public ClazzDataRealmBuilder restrictProjects(List<Project> projects) {
    query.where(MessageFormat.format(IN_PROJECTS, clazzAlias, System.currentTimeMillis()), projects);
    return this;
  }

  public ClazzDataRealmBuilder restrictTeachDeparts(List<Department> departs) {
    query.where(MessageFormat.format(IN_TEACT_DEPARTS, clazzAlias, System.currentTimeMillis()), departs);
    return this;
  }

  public ClazzDataRealmBuilder restrictStdTypes(List<StdType> stdTypes) {
    // TODO
    return this;
  }

  public ClazzDataRealmBuilder restrictEducations(List<EducationLevel> levels) {
    // TODO
    return this;
  }

  public ClazzDataRealmBuilder restrictProjects(Integer[] projectIds) {
    query.where(MessageFormat.format(IN_PROJECT_IDS, clazzAlias, System.currentTimeMillis()), projectIds);
    return this;
  }

  public ClazzDataRealmBuilder restrictTeachDeparts(Long[] departIds) {
    query.where(MessageFormat.format(IN_TEACH_DEPART_IDS, clazzAlias, System.currentTimeMillis()),
        departIds);
    return this;
  }

  public ClazzDataRealmBuilder restrictStdTypes(Integer[] stdTypeIds) {
    // TODO
    return this;
  }

  public ClazzDataRealmBuilder restrictEducations(Integer[] levelIds) {
    // TODO
    return this;
  }

  public OqlBuilder finish() {
    return query;
  }

}
