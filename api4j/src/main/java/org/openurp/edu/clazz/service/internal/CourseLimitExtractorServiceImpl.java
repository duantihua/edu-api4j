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
package org.openurp.edu.clazz.service.internal;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.tuple.Pair;
import org.openurp.base.std.code.StdType;
import org.openurp.base.edu.model.Direction;
import org.openurp.base.edu.model.Major;
import org.openurp.base.std.model.Squad;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.person.model.Gender;
import org.openurp.edu.clazz.model.Restriction;
import org.openurp.edu.clazz.model.RestrictionItem;
import org.openurp.edu.clazz.model.RestrictionMeta;
import org.openurp.edu.clazz.service.CourseLimitExtractorService;

import java.util.ArrayList;
import java.util.List;

public class CourseLimitExtractorServiceImpl extends BaseServiceImpl implements CourseLimitExtractorService {

  public List<EducationLevel> extractEducations(Restriction restriction) {
    Pair<Boolean, List<EducationLevel>> res = xtractEducationLimit(restriction);
    if (res.getLeft()) {
      return res.getRight();
    }
    return CollectUtils.newArrayList();
  }

  public List<Squad> extractSquades(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Squad.equals(item.getMeta()) && (item.isIncluded())) {
        return entityDao.get(Squad.class, Strings.splitToLong(item.getContents()));
      }
    }
    return CollectUtils.newArrayList();
  }

  public String extractGrade(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Grade.equals(item.getMeta()) && (item.isIncluded())) {
        return item.getContents();
      }
    }
    return null;
  }

  public List<StdType> extractStdTypes(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.StdType.equals(item.getMeta()) && (item.isIncluded())) {
        return entityDao.get(StdType.class, Strings.splitToInt(item.getContents()));
      }
    }
    return CollectUtils.newArrayList();
  }

  public List<Major> extractMajors(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Major.equals(item.getMeta().getId()) && (item.isIncluded())) {
        return entityDao.get(Major.class, Strings.splitToLong(item.getContents()));
      }
    }
    return CollectUtils.newArrayList();
  }

  public List<Direction> extractDirections(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Direction.equals(item.getMeta().getId()) && (item.isIncluded())) {
        return entityDao.get(Direction.class, Strings.splitToLong(item.getContents()));
      }
    }
    return CollectUtils.newArrayList();
  }

  public List<Department> extractAttendDeparts(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Department.equals(item.getMeta().getId()) && (item.isIncluded())) {
        return entityDao.get(Department.class, Strings.splitToInt(item.getContents()));
      }
    }
    return CollectUtils.newArrayList();
  }

  public Gender extractGender(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Gender.equals(item.getMeta().getId()) && (item.isIncluded())) {
        return entityDao.get(Gender.class, Strings.splitToInt(item.getContents())).get(0);
      }
    }
    return null;
  }

  public Pair<Boolean, List<EducationLevel>> xtractEducationLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Level.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<EducationLevel>>(item.isIncluded(), entityDao.get(EducationLevel.class, Strings.splitToInt(item.getContents())));
      }
    }
    return new Pair<Boolean, List<EducationLevel>>(Boolean.TRUE, new ArrayList<EducationLevel>());
  }

  public Pair<Boolean, List<Squad>> xtractSquadLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Squad.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<Squad>>(item.isIncluded(), entityDao.get(Squad.class, Strings.splitToLong(item.getContents())));
      }
    }
    return new Pair<Boolean, List<Squad>>(Boolean.TRUE, new ArrayList<Squad>());
  }

  public Pair<Boolean, List<Department>> xtractAttendDepartLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Department.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<Department>>(item.isIncluded(), entityDao.get(Department.class, Strings.splitToInt(item.getContents())));
      }
    }
    return new Pair<Boolean, List<Department>>(Boolean.TRUE, new ArrayList<Department>());
  }

  public Pair<Boolean, List<Direction>> xtractDirectionLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Direction.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<Direction>>(item.isIncluded(), entityDao.get(Direction.class, Strings.splitToLong(item.getContents())));
      }
    }
    return new Pair<Boolean, List<Direction>>(Boolean.TRUE, new ArrayList<Direction>());
  }

  public Pair<Boolean, List<String>> xtractGradeLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Gender.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<String>>(item.isIncluded(), CollectUtils.newArrayList(item.getContents()));
      }
    }
    return new Pair<Boolean, List<String>>(Boolean.TRUE, new ArrayList<String>());
  }

  public Pair<Boolean, List<Major>> xtractMajorLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.Major.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<Major>>(item.isIncluded(), entityDao.get(Major.class, Strings.splitToLong(item.getContents())));
      }
    }
    return new Pair<Boolean, List<Major>>(Boolean.TRUE, new ArrayList<Major>());
  }

  public Pair<Boolean, List<StdType>> xtractStdTypeLimit(Restriction restriction) {
    for (RestrictionItem item : restriction.getItems()) {
      if (RestrictionMeta.StdType.equals(item.getMeta().getId())) {
        return new Pair<Boolean, List<StdType>>(item.isIncluded(), entityDao.get(StdType.class, Strings.splitToInt(item.getContents())));
      }
    }
    return new Pair<Boolean, List<StdType>>(Boolean.TRUE, new ArrayList<StdType>());
  }

}
