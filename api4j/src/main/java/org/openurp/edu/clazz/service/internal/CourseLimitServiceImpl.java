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
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.tuple.Pair;
import org.openurp.code.edu.model.EducationType;
import org.openurp.code.std.model.StdType;
import org.openurp.base.edu.model.Direction;
import org.openurp.base.edu.model.Major;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.CourseTakeType;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.code.edu.model.ElectionMode;
import org.openurp.code.person.model.Gender;
import org.openurp.edu.clazz.model.*;
import org.openurp.edu.clazz.service.ClazzNameStrategy;
import org.openurp.edu.clazz.service.CourseLimitService;
import org.openurp.edu.clazz.service.RestrictionBuilder;

import java.util.*;

@SuppressWarnings("unchecked")
public class CourseLimitServiceImpl extends BaseServiceImpl implements CourseLimitService {
  ClazzNameStrategy teachclassNameStrategy;

  @Deprecated
  public void mergeAll(Enrollment target, Enrollment source) {
    // TODO
  }

  @Deprecated
  public void merge(Long mergeType, Enrollment target, Enrollment source) {
    if (RestrictionMeta.Squad.equals(mergeType)) {
      Set<Squad> tmp_collection = new HashSet<Squad>();
      List<Squad> targetCollection = extractSquades(target);
      if (CollectUtils.isNotEmpty(targetCollection)) {
        tmp_collection.addAll(targetCollection);
      }

      List<Squad> sourceCollection = extractSquades(source);
      if (CollectUtils.isNotEmpty(sourceCollection)) {
        tmp_collection.addAll(sourceCollection);
      }
      limitEnrollment(true, target, tmp_collection.toArray(new Squad[0]));
    } else if (RestrictionMeta.Department.equals(mergeType)) {
      Set<Department> tmp_collection = new HashSet<Department>();
      List<Department> targetCollection = extractAttendDeparts(target);
      if (CollectUtils.isNotEmpty(targetCollection)) {
        tmp_collection.addAll(targetCollection);
      }

      List<Department> sourceCollection = extractAttendDeparts(source);
      if (CollectUtils.isNotEmpty(sourceCollection)) {
        tmp_collection.addAll(sourceCollection);
      }
      limitEnrollment(true, target, tmp_collection.toArray(new Department[0]));
    } else if (RestrictionMeta.StdType.equals(mergeType)) {
      Set<StdType> tmp_collection = new HashSet<StdType>();
      List<StdType> targetCollection = extractStdTypes(target);
      if (CollectUtils.isNotEmpty(targetCollection)) {
        tmp_collection.addAll(targetCollection);
      }

      List<StdType> sourceCollection = extractStdTypes(source);
      if (CollectUtils.isNotEmpty(sourceCollection)) {
        tmp_collection.addAll(sourceCollection);
      }
      limitEnrollment(true, target, tmp_collection.toArray(new StdType[0]));
    } else if (RestrictionMeta.Direction.equals(mergeType)) {
      Set<Direction> tmp_collection = new HashSet<Direction>();
      List<Direction> targetCollection = extractDirections(target);
      if (CollectUtils.isNotEmpty(targetCollection)) {
        tmp_collection.addAll(targetCollection);
      }

      List<Direction> sourceCollection = extractDirections(source);
      if (CollectUtils.isNotEmpty(sourceCollection)) {
        tmp_collection.addAll(sourceCollection);
      }
      limitEnrollment(true, target, tmp_collection.toArray(new Direction[0]));
    } else if (RestrictionMeta.Gender.equals(mergeType)) {
      Gender targetGender = extractGender(target);
      Gender sourceGender = extractGender(source);

      Gender tmp_gender = null;
      if (targetGender != null && sourceGender != null) {
        if (targetGender.equals(sourceGender)) {
          tmp_gender = targetGender;
        }
      }
      if (targetGender != null && sourceGender == null) {
        tmp_gender = targetGender;
      }
      if (targetGender == null && sourceGender != null) {
        tmp_gender = sourceGender;
      }
      if (tmp_gender != null) {
        limitEnrollment(true, target, tmp_gender);
      }
    } else if (RestrictionMeta.Grade.equals(mergeType)) {
      String tmp_grade = "";
      String targetGrade = extractGrade(target);
      if (Strings.isNotBlank(targetGrade)) {
        tmp_grade = targetGrade;
      }
      String sourceGrade = extractGrade(source);
      if (Strings.isNotBlank(sourceGrade)) {
        tmp_grade += "," + sourceGrade;
      }
      String[] grades = Strings.split(tmp_grade);
      if (grades.length > 0) {
        limitEnrollment(true, target, grades);
      }
    } else if (RestrictionMeta.Major.equals(mergeType)) {
      Set<Major> tmp_collection = new HashSet<Major>();
      List<Major> targetCollection = extractMajors(target);
      if (CollectUtils.isNotEmpty(targetCollection)) {
        tmp_collection.addAll(targetCollection);
      }

      List<Major> sourceCollection = extractMajors(source);
      if (CollectUtils.isNotEmpty(sourceCollection)) {
        tmp_collection.addAll(sourceCollection);
      }
      limitEnrollment(true, target, tmp_collection.toArray(new Major[0]));
    } else if (RestrictionMeta.Level.equals(mergeType)) {
      Set<EducationLevel> tmp_collection = new HashSet<EducationLevel>();
      List<EducationLevel> targetCollection = extractEducations(target);
      if (CollectUtils.isNotEmpty(targetCollection)) {
        tmp_collection.addAll(targetCollection);
      }

      List<EducationLevel> sourceCollection = extractEducations(source);
      if (CollectUtils.isNotEmpty(sourceCollection)) {
        tmp_collection.addAll(sourceCollection);
      }
      limitEnrollment(true, target, tmp_collection.toArray(new EducationLevel[0]));
    } else {
      throw new RuntimeException("unsupported limit meta merge");
    }
  }

  public List<EducationLevel> extractEducations(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<EducationLevel>>> res = xtractEducationLimit(teachclass);
    List<EducationLevel> levels = CollectUtils.newArrayList();
    for (Pair<Boolean, List<EducationLevel>> tmpRes : res.values()) {
      if (tmpRes._1) {
        for (EducationLevel level : tmpRes._2) {
          if (!levels.contains(level)) {
            levels.add(level);
          }
        }
      }
    }
    return levels;
  }

  public List<EducationLevel> extractEducations(Restriction group) {
    Pair<Boolean, List<EducationLevel>> res = xtractEducationLimit(group);
    if (res._1) {
      return res._2;
    }
    return CollectUtils.newArrayList();
  }

  public List<Squad> extractSquades(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<Squad>>> res = xtractSquadLimit(teachclass);
    List<Squad> squades = CollectUtils.newArrayList();
    for (Pair<Boolean, List<Squad>> tmpRes : res.values()) {
      if (tmpRes._1) {
        for (Squad squad : tmpRes._2) {
          if (!squades.contains(squad)) {
            squades.add(squad);
          }
        }
      }
    }
    return squades;
  }

  public List<Squad> extractSquades(Restriction group) {
    Pair<Boolean, List<Squad>> res = xtractSquadLimit(group);
    if (res._1) {
      return res._2;
    }
    return CollectUtils.newArrayList();
  }

  public String extractGrade(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<String>>> res = xtractGradeLimit(teachclass);
    Set<String> grades = CollectUtils.newHashSet();
    for (Pair<Boolean, List<String>> tmpRes : res.values()) {
      if (tmpRes._1) {
        grades.addAll(tmpRes._2);
      }
    }
    String grade = "";
    for (String str : grades) {
      if (Strings.isNotEmpty(grade)) {
        grade += ",";
      }
      grade += str;
    }
    return grade;
  }

  public String extractGrade(Restriction group) {
    Pair<Boolean, List<String>> res = xtractGradeLimit(group);
    if (res._1) {
      if (CollectUtils.isNotEmpty(res._2)) {
        return res._2.get(0);
      }
    }
    return null;
  }

  public List<StdType> extractStdTypes(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<StdType>>> res = xtractStdTypeLimit(teachclass);
    List<StdType> stdTypes = CollectUtils.newArrayList();
    for (Pair<Boolean, List<StdType>> tmpRes : res.values()) {
      if (tmpRes._1) {
        for (StdType stdType : tmpRes._2) {
          if (!stdTypes.contains(stdType)) {
            stdTypes.add(stdType);
          }
        }
      }
    }
    return stdTypes;
  }

  public List<StdType> extractStdTypes(Restriction group) {
    Pair<Boolean, List<StdType>> res = xtractStdTypeLimit(group);
    if (res._1) {
      return res._2;
    }
    return CollectUtils.newArrayList();
  }

  public List<Major> extractMajors(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<Major>>> res = xtractMajorLimit(teachclass);
    List<Major> majors = CollectUtils.newArrayList();
    for (Pair<Boolean, List<Major>> tmpRes : res.values()) {
      if (tmpRes._1) {
        for (Major major : tmpRes._2) {
          if (!majors.contains(major)) {
            majors.add(major);
          }
        }
      }
    }
    return majors;
  }

  public List<Major> extractMajors(Restriction group) {
    Pair<Boolean, List<Major>> res = xtractMajorLimit(group);
    if (res._1) {
      return res._2;
    }
    return CollectUtils.newArrayList();
  }

  public List<Direction> extractDirections(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<Direction>>> res = xtractDirectionLimit(teachclass);
    List<Direction> directions = CollectUtils.newArrayList();
    for (Pair<Boolean, List<Direction>> tmpRes : res.values()) {
      if (tmpRes._1) {
        for (Direction direction : tmpRes._2) {
          if (!directions.contains(direction)) {
            directions.add(direction);
          }
        }
      }
    }
    return directions;
  }

  public List<Direction> extractDirections(Restriction group) {
    Pair<Boolean, List<Direction>> res = xtractDirectionLimit(group);
    if (res._1) {
      return res._2;
    }
    return CollectUtils.newArrayList();
  }

  public List<Department> extractAttendDeparts(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<Department>>> res = xtractAttendDepartLimit(teachclass);
    List<Department> departments = CollectUtils.newArrayList();
    for (Pair<Boolean, List<Department>> tmpRes : res.values()) {
      if (tmpRes._1) {
        for (Department department : tmpRes._2) {
          if (!departments.contains(department)) {
            departments.add(department);
          }
        }
      }
    }
    return departments;
  }

  public List<Department> extractAttendDeparts(Restriction group) {
    Pair<Boolean, List<Department>> res = xtractAttendDepartLimit(group);
    if (res._1) {
      return res._2;
    }
    return CollectUtils.newArrayList();
  }

  @Deprecated
  public Gender extractGender(Enrollment teachclass) {
    List<Restriction> groups = teachclass.getRestrictions();
    for (Restriction group : groups) {
      if (!group.isPrime()) {
        continue;
      }
      for (RestrictionItem item : group.getItems()) {
        if (RestrictionMeta.Gender.equals(item.getMeta()) && item.isIncluded()) {
          return entityDao.get(Gender.class, Strings.splitToInt(item.getContents()))
              .get(0);
        }
      }
    }
    return null;
  }

  @Deprecated
  public Gender extractGender(Restriction group) {
    if (group == null) {
      return null;
    }
    for (RestrictionItem item : group.getItems()) {
      if (RestrictionMeta.Gender.equals(item.getMeta()) && item.isIncluded()) {
        return entityDao.get(Gender.class, Strings.splitToInt(item.getContents()))
            .get(0);
      }
    }
    return null;
  }

  public RestrictionBuilder builder() {
    return new DefaultRestrictionBuilder();
  }

  private RestrictionBuilder builder(Restriction group) {
    return new DefaultRestrictionBuilder(group);
  }

  public RestrictionBuilder builder(Enrollment teachclass) {
    Enrollment clb = (Enrollment) teachclass;
    Restriction group = clb.getOrCreateDefautRestriction();
    return new DefaultRestrictionBuilder(group);
  }

  public Set<CourseTaker> extractLonelyTakers(Enrollment teachclass) {
    Set<CourseTaker> takers = teachclass.getCourseTakers();
    List<Squad> squades = extractSquades(teachclass);

    Set<CourseTaker> lonelyTakers = new HashSet<CourseTaker>();

    for (CourseTaker taker : takers) {
      boolean lonely = true;
      for (Squad squad : squades) {
        if (squad.equals(taker.getStd().getSquad())) {
          lonely = false;
          break;
        }
      }
      if (lonely) {
        lonelyTakers.add(taker);
      }
    }
    return lonelyTakers;
  }

  public Set<CourseTaker> extractPossibleCourseTakers(Clazz clazz) {
    Enrollment teachclass = clazz.getEnrollment();
    // 这个会按照学号排序的
    Set<CourseTaker> possibleTakers = new TreeSet<CourseTaker>(new Comparator<CourseTaker>() {
      public int compare(CourseTaker o1, CourseTaker o2) {
        return o1.getStd().getCode().compareTo(o2.getStd().getCode());
      }
    });

    if (CollectUtils.isNotEmpty(teachclass.getCourseTakers())) {
      possibleTakers.addAll(teachclass.getCourseTakers());
      return possibleTakers;
    }

    List<Squad> squades = extractSquades(teachclass);
    ElectionMode eleMode = new ElectionMode();
    eleMode.setId(ElectionMode.Assigned);
    for (Squad squad : squades) {
      for (Student std : squad.getStudents(clazz.getSemester().getBeginOn())) {
        CourseTaker taker = new CourseTaker(clazz, std, new CourseTakeType(CourseTakeType.Normal));
        taker.setCourseType(clazz.getCourseType());
        taker.setElectionMode(eleMode);
        possibleTakers.add(taker);
      }
    }
    return possibleTakers;
  }

  public void limitEnrollment(boolean inclusive, Enrollment teachclass, String... grades) {
    if (grades.length > 0) {
      Enrollment clb = (Enrollment) teachclass;
      Restriction group = clb.getOrCreateDefautRestriction();
      RestrictionBuilder builder = builder(group);
      builder.clear(RestrictionMeta.Grade);
      if (inclusive) {
        builder.inGrades(grades);
      } else {
        builder.notInGrades(grades);
      }
    }
  }

  public <T extends Entity<?>> void limitEnrollment(boolean inclusive, Enrollment teachclass, T... entities) {
    if (entities.length > 0) {
      Enrollment clb = (Enrollment) teachclass;
      Restriction group = clb.getOrCreateDefautRestriction();
      RestrictionBuilder builder = builder(group);

      T first = entities[0];
      if (first instanceof Squad) {
        builder.clear(RestrictionMeta.Squad);
      } else if (first instanceof StdType) {
        builder.clear(RestrictionMeta.StdType);
      } else if (first instanceof Major) {
        builder.clear(RestrictionMeta.Major);
      } else if (first instanceof Direction) {
        builder.clear(RestrictionMeta.Direction);
      } else if (first instanceof Department) {
        builder.clear(RestrictionMeta.Department);
      } else if (first instanceof EducationLevel) {
        builder.clear(RestrictionMeta.Level);
      } else if (first instanceof Gender) {
        builder.clear(RestrictionMeta.Gender);
      } else if (first instanceof EducationType) {
        builder.clear(RestrictionMeta.EduType);
      } else {
        throw new RuntimeException("not supported limit meta class " + first.getClass().getName());
      }

      if (inclusive) {
        builder.in(entities);
      } else {
        builder.notIn(entities);
      }
    }
  }

  private Pair<Boolean, List<?>> xtractLimitDirtyWork(Restriction group, RestrictionMeta limitMeta) {
    if (group == null) {
      return new Pair<Boolean, List<?>>(null, CollectUtils.newArrayList());
    }
    for (RestrictionItem item : group.getItems()) {
      if (RestrictionMeta.Level.equals(limitMeta)) {
        if (RestrictionMeta.Level.equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(EducationLevel.class, Strings.splitToInt(item.getContents())));
        }
      } else if (RestrictionMeta.Squad.equals(limitMeta)) {
        if (RestrictionMeta.Squad
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(Squad.class, Strings.splitToLong(item.getContents())));
        }
      } else if (RestrictionMeta.Department.equals(limitMeta)) {
        if (RestrictionMeta.Department
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(Department.class, Strings.splitToInt(item.getContents())));
        }
      } else if (RestrictionMeta.Major.equals(limitMeta)) {
        if (RestrictionMeta.Major
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(Major.class, Strings.splitToLong(item.getContents())));
        }
      } else if (RestrictionMeta.Direction.equals(limitMeta)) {
        if (RestrictionMeta.Direction
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(Direction.class, Strings.splitToLong(item.getContents())));
        }
      } else if (RestrictionMeta.StdType.equals(limitMeta)) {
        if (RestrictionMeta.StdType
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(StdType.class, Strings.splitToInt(item.getContents())));
        }
      } else if (RestrictionMeta.Grade.equals(limitMeta)) {
        if (RestrictionMeta.Grade
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              Arrays.asList(Strings.split(item.getContents())));
        }
      } else if (RestrictionMeta.Gender.equals(limitMeta)) {
        if (RestrictionMeta.Gender
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(Gender.class, Strings.splitToInt(item.getContents())));
        }
      } else if (RestrictionMeta.EduType.equals(limitMeta)) {
        if (RestrictionMeta.EduType
            .equals(item.getMeta())) {
          return new Pair<Boolean, List<?>>(item.isIncluded(),
              entityDao.get(EducationType.class, Strings.splitToInt(item.getContents())));
        }
      }
    }
    return new Pair<Boolean, List<?>>(Boolean.TRUE, CollectUtils.newArrayList());
  }

  private Map<Restriction, Pair<Boolean, List<?>>> xtractLimitDirtyWork(Enrollment teachclass,
                                                                        RestrictionMeta limitMeta) {
    List<Restriction> groups = teachclass.getRestrictions();
    Map<Restriction, Pair<Boolean, List<?>>> groupDatas = CollectUtils.newHashMap();
    for (Restriction group : groups) {
      if (!group.isPrime()) {
        continue;
      }
      groupDatas.put(group, xtractLimitDirtyWork(group, limitMeta));
    }
    return groupDatas;
  }

  public Map<Restriction, Pair<Boolean, List<EducationLevel>>> xtractEducationLimit(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<?>>> tmpRes = xtractLimitDirtyWork(teachclass,
        RestrictionMeta.Level);
    Map<Restriction, Pair<Boolean, List<EducationLevel>>> results = CollectUtils.newHashMap();
    for (Map.Entry<Restriction, Pair<Boolean, List<?>>> tmpEntrySet : tmpRes.entrySet()) {
      Pair<Boolean, List<?>> tmpPair = tmpEntrySet.getValue();
      results.put(tmpEntrySet.getKey(),
          new Pair<Boolean, List<EducationLevel>>(tmpPair._1, (List<EducationLevel>) tmpPair._2));
    }
    return results;
  }

  public Pair<Boolean, List<EducationLevel>> xtractEducationLimit(Restriction group) {
    Pair<Boolean, List<?>> tmpRes = xtractLimitDirtyWork(group, RestrictionMeta.Level);
    return new Pair<Boolean, List<EducationLevel>>(tmpRes._1, (List<EducationLevel>) tmpRes._2);
  }

  public Map<Restriction, Pair<Boolean, List<Squad>>> xtractSquadLimit(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<?>>> tmpRes = xtractLimitDirtyWork(teachclass,
        RestrictionMeta.Squad);
    Map<Restriction, Pair<Boolean, List<Squad>>> results = CollectUtils.newHashMap();
    for (Map.Entry<Restriction, Pair<Boolean, List<?>>> tmpEntrySet : tmpRes.entrySet()) {
      Pair<Boolean, List<?>> tmpPair = tmpEntrySet.getValue();
      results.put(tmpEntrySet.getKey(), new Pair<>(tmpPair._1, (List<Squad>) tmpPair._2));
    }
    return results;
  }

  public Pair<Boolean, List<Squad>> xtractSquadLimit(Restriction group) {
    Pair<Boolean, List<?>> tmpRes = xtractLimitDirtyWork(group, RestrictionMeta.Squad);
    return new Pair<Boolean, List<Squad>>(tmpRes._1, (List<Squad>) tmpRes._2);
  }

  public Map<Restriction, Pair<Boolean, List<Department>>> xtractAttendDepartLimit(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<?>>> tmpRes = xtractLimitDirtyWork(teachclass,
        RestrictionMeta.Department);
    Map<Restriction, Pair<Boolean, List<Department>>> results = CollectUtils.newHashMap();
    for (Map.Entry<Restriction, Pair<Boolean, List<?>>> tmpEntrySet : tmpRes.entrySet()) {
      Pair<Boolean, List<?>> tmpPair = tmpEntrySet.getValue();
      results.put(tmpEntrySet.getKey(),
          new Pair<Boolean, List<Department>>(tmpPair._1, (List<Department>) tmpPair._2));
    }
    return results;
  }

  public Pair<Boolean, List<Department>> xtractAttendDepartLimit(Restriction group) {
    Pair<Boolean, List<?>> tmpRes = xtractLimitDirtyWork(group, RestrictionMeta.Department);
    return new Pair<Boolean, List<Department>>(tmpRes._1, (List<Department>) tmpRes._2);
  }

  public Map<Restriction, Pair<Boolean, List<Direction>>> xtractDirectionLimit(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<?>>> tmpRes = xtractLimitDirtyWork(teachclass,
        RestrictionMeta.Direction);
    Map<Restriction, Pair<Boolean, List<Direction>>> results = CollectUtils.newHashMap();
    for (Map.Entry<Restriction, Pair<Boolean, List<?>>> tmpEntrySet : tmpRes.entrySet()) {
      Pair<Boolean, List<?>> tmpPair = tmpEntrySet.getValue();
      results.put(tmpEntrySet.getKey(),
          new Pair<Boolean, List<Direction>>(tmpPair._1, (List<Direction>) tmpPair._2));
    }
    return results;
  }

  public Pair<Boolean, List<Direction>> xtractDirectionLimit(Restriction group) {
    Pair<Boolean, List<?>> tmpRes = xtractLimitDirtyWork(group, RestrictionMeta.Direction);
    return new Pair<Boolean, List<Direction>>(tmpRes._1, (List<Direction>) tmpRes._2);
  }

  public Map<Restriction, Pair<Boolean, List<String>>> xtractGradeLimit(Enrollment teachclass) {
    List<Restriction> groups = teachclass.getRestrictions();
    Map<Restriction, Pair<Boolean, List<String>>> groupDatas = CollectUtils.newHashMap();
    for (Restriction group : groups) {
      if (!group.isPrime()) {
        continue;
      }
      groupDatas.put(group, xtractGradeLimit(group));
    }
    return groupDatas;
  }

  public Pair<Boolean, List<String>> xtractGradeLimit(Restriction group) {
    for (RestrictionItem item : group.getItems()) {
      if (RestrictionMeta.Grade.equals(item.getMeta())) {
        return new Pair<Boolean, List<String>>(item.isIncluded(),
            CollectUtils.newArrayList(item.getContents()));
      }
    }
    return new Pair<Boolean, List<String>>(null, new ArrayList<String>());
  }

  public Map<Restriction, Pair<Boolean, List<Major>>> xtractMajorLimit(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<?>>> tmpRes = xtractLimitDirtyWork(teachclass,
        RestrictionMeta.Major);
    Map<Restriction, Pair<Boolean, List<Major>>> results = CollectUtils.newHashMap();
    for (Map.Entry<Restriction, Pair<Boolean, List<?>>> tmpEntrySet : tmpRes.entrySet()) {
      Pair<Boolean, List<?>> tmpPair = tmpEntrySet.getValue();
      results.put(tmpEntrySet.getKey(), new Pair<Boolean, List<Major>>(tmpPair._1, (List<Major>) tmpPair._2));
    }
    return results;
  }

  public Pair<Boolean, List<Major>> xtractMajorLimit(Restriction group) {
    Pair<Boolean, List<?>> tmpRes = xtractLimitDirtyWork(group, RestrictionMeta.Major);
    return new Pair<Boolean, List<Major>>(tmpRes._1, (List<Major>) tmpRes._2);
  }

  public Map<Restriction, Pair<Boolean, List<StdType>>> xtractStdTypeLimit(Enrollment teachclass) {
    Map<Restriction, Pair<Boolean, List<?>>> tmpRes = xtractLimitDirtyWork(teachclass,
        RestrictionMeta.StdType);
    Map<Restriction, Pair<Boolean, List<StdType>>> results = CollectUtils.newHashMap();
    for (Map.Entry<Restriction, Pair<Boolean, List<?>>> tmpEntrySet : tmpRes.entrySet()) {
      Pair<Boolean, List<?>> tmpPair = tmpEntrySet.getValue();
      results.put(tmpEntrySet.getKey(),
          new Pair<Boolean, List<StdType>>(tmpPair._1, (List<StdType>) tmpPair._2));
    }
    return results;
  }

  public Pair<Boolean, List<StdType>> xtractStdTypeLimit(Restriction group) {
    Pair<Boolean, List<?>> tmpRes = xtractLimitDirtyWork(group, RestrictionMeta.StdType);
    return new Pair<Boolean, List<StdType>>(tmpRes._1, (List<StdType>) tmpRes._2);
  }

  public boolean isAutoName(Clazz clazz) {
    // 检查是否需要自动命名
    boolean isAutoName = true;
    if (clazz.getId() != null) {
      String teachclassName = clazz.getClazzName();
      String autoName = teachclassNameStrategy.genName(clazz);
      if (teachclassName != null && !teachclassName.equals(autoName)) {
        isAutoName = false;
      }
    }
    return isAutoName;
  }

  public RestrictionPair xtractLimitGroup(Restriction group) {
    RestrictionPair pair = new RestrictionPair(group);
    Pair<Boolean, List<?>> gradeLimit = xtractLimitDirtyWork(group, RestrictionMeta.Grade);
    if (gradeLimit._1 != null) pair.setGradeLimit(gradeLimit);

    Pair<Boolean, List<?>> stdTypeLimit = xtractLimitDirtyWork(group, RestrictionMeta.StdType);
    if (stdTypeLimit._1 != null) pair.setStdTypeLimit(stdTypeLimit);

    Pair<Boolean, List<?>> genderLimit = xtractLimitDirtyWork(group, RestrictionMeta.Gender);
    if (genderLimit._1 != null) pair.setGenderLimit(genderLimit);

    Pair<Boolean, List<?>> departLimit = xtractLimitDirtyWork(group, RestrictionMeta.Department);
    if (departLimit._1 != null) pair.setDepartmentLimit(departLimit);

    Pair<Boolean, List<?>> majorLimit = xtractLimitDirtyWork(group, RestrictionMeta.Major);
    if (majorLimit._1 != null) pair.setMajorLimit(majorLimit);

    Pair<Boolean, List<?>> directionLimit = xtractLimitDirtyWork(group, RestrictionMeta.Direction);
    if (directionLimit._1 != null) pair.setDirectionLimit(directionLimit);

    Pair<Boolean, List<?>> squadLimit = xtractLimitDirtyWork(group, RestrictionMeta.Squad);
    if (squadLimit._1 != null) pair.setSquadLimit(squadLimit);

    Pair<Boolean, List<?>> levelLimit = xtractLimitDirtyWork(group, RestrictionMeta.Level);
    if (levelLimit._1 != null) pair.setLevelLimit(levelLimit);

    Pair<Boolean, List<?>> eduTypeLimit = xtractLimitDirtyWork(group, RestrictionMeta.EduType);
    if (levelLimit._1 != null) pair.setEduTypeLimit(eduTypeLimit);

    return pair;
  }

  public void setEnrollmentNameStrategy(ClazzNameStrategy teachclassNameStrategy) {
    this.teachclassNameStrategy = teachclassNameStrategy;
  }
}
