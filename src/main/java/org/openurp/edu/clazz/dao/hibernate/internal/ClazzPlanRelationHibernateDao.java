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
package org.openurp.edu.clazz.dao.hibernate.internal;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.openurp.base.std.code.StdType;
import org.openurp.edu.program.plan.service.ExecutionPlanQueryBuilder;
import org.openurp.base.edu.model.Direction;
import org.openurp.base.edu.model.Major;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.clazz.dao.ClazzPlanRelationDao;
import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.ClazzPlanRelation;
import org.openurp.edu.clazz.service.CourseLimitService;
import org.openurp.edu.program.model.ExecutionPlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClazzPlanRelationHibernateDao extends HibernateEntityDao implements ClazzPlanRelationDao {

  private CourseLimitService courseLimitService;

  public List<Clazz> relatedClazzes(ExecutionPlan plan) {
    OqlBuilder query = OqlBuilder.from(ClazzPlanRelation.class, "relation");
    query.select("relation.clazz").where("relation.plan = :plan", plan);
    return search(query);
  }

  public List<Clazz> relatedClazzes(ExecutionPlan plan, Semester semester) {
    OqlBuilder query = OqlBuilder.from(ClazzPlanRelation.class, "relation");
    query.select("relation.clazz").where("relation.clazz.semester = :semester", semester)
        .where("relation.plan = :plan", plan);
    return search(query);
  }

  public List<ExecutionPlan> relatedPlans(Clazz clazz) {
    OqlBuilder query = OqlBuilder.from(ClazzPlanRelation.class, "relation");
    query.select("relation.plan").where("relation.clazz = :clazz", clazz);
    return search(query);
  }

  public List<ExecutionPlan> possibleRelatePlans(Clazz clazz) {
    List<ExecutionPlan> plans = new ArrayList<ExecutionPlan>();

    List<Squad> squades = courseLimitService.extractSquades(clazz.getEnrollment());
    // 如果有行政班的，那么就关联到行政班对应的培养计划
    if (CollectUtils.isNotEmpty(squades)) {
      for (Squad squad : squades) {
        plans.addAll(search(ExecutionPlanQueryBuilder.build(squad)));
      }
      return plans;
    }

    // 如果没有行政班，则通过年级、专业、方向来匹配到培养计划
    String grade = courseLimitService.extractGrade(clazz.getEnrollment());
    if (Strings.isEmpty(grade)) { return CollectUtils.newArrayList(); }

    List<Major> majors = courseLimitService.extractMajors(clazz.getEnrollment());
    if (CollectUtils.isEmpty(majors)) { return CollectUtils.newArrayList(); }

    List<StdType> stdTypes = courseLimitService.extractStdTypes(clazz.getEnrollment());
    if (CollectUtils.isEmpty(stdTypes)) {
      stdTypes.add(null);
    }

    List<Direction> directions = courseLimitService.extractDirections(clazz.getEnrollment());
    if (CollectUtils.isEmpty(directions)) {
      directions.add(null);
    }

    for (Major major : majors) {
      for (StdType stdType : stdTypes) {
        for (Direction direction : directions) {
          plans.addAll(search(ExecutionPlanQueryBuilder.build(grade, stdType, major, direction)));
        }
      }
    }

    return plans;
  }

  public List<ClazzPlanRelation> relations(Clazz clazz) {
    OqlBuilder<ClazzPlanRelation> query = OqlBuilder.from(ClazzPlanRelation.class, "relation");
    query.where("relation.clazz = :clazz", clazz);
    return search(query);
  }

  public List<ClazzPlanRelation> relations(ExecutionPlan plan) {
    OqlBuilder<ClazzPlanRelation> query = OqlBuilder.from(ClazzPlanRelation.class, "relation");
    query.where("relation.plan = :plan", plan);
    return search(query);
  }

  public List<ClazzPlanRelation> relations(ExecutionPlan plan, Semester semester) {
    OqlBuilder<ClazzPlanRelation> query = OqlBuilder.from(ClazzPlanRelation.class, "relation");
    query.where("relation.plan = :plan", plan).where("relation.clazz.semester = :semester", semester);
    return search(query);
  }

  public void saveRelation(ExecutionPlan plan, Clazz clazz) {
    ClazzPlanRelation relation = new ClazzPlanRelation();
    relation.setPlan(plan);
    relation.setClazz(clazz);
    relation.setUpdatedAt(new Date());
    save(relation);
  }

  public void removeRelation(Clazz clazz) {
    List<ClazzPlanRelation> relations = relations(clazz);
    if (CollectUtils.isNotEmpty(relations)) {
      remove(relations);
    }
  }

  public void removeRelation(ExecutionPlan plan, Semester semester) {
    remove(relations(plan, semester));
  }

  public void updateRelation(Clazz clazz) {
    removeRelation(clazz);
    List<ExecutionPlan> plans = possibleRelatePlans(clazz);
    if (CollectUtils.isNotEmpty(plans)) {
      for (ExecutionPlan plan : plans) {
        saveRelation(plan, clazz);
      }
    } else {
      saveRelation(null, clazz);
    }
  }

  public void setCourseLimitService(CourseLimitService courseLimitService) {
    this.courseLimitService = courseLimitService;
  }

}
