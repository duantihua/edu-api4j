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
package org.openurp.edu.clazz.service;

import org.beangle.commons.collection.CollectUtils;
import org.openurp.base.std.model.Student;
import org.openurp.edu.clazz.model.*;

import java.util.*;

public class RestrictionHelper {

  private static RestrictionComparator comparator = new RestrictionComparator();

  public static void autoMatches(Clazz clazz) {
    List<ClazzRestriction> groups = clazz.getEnrollment().getRestrictions();
    Collections.sort(groups, comparator);
    Map<ClazzRestriction, Integer> cnts = CollectUtils.newHashMap();
    for (CourseTaker taker : clazz.getEnrollment().getCourseTakers()) {
      ClazzRestriction limitGroup = RestrictionHelper.getMatchRestriction(clazz, taker.getStd());
      if (null != limitGroup) {
        Integer old = cnts.get(limitGroup);
        if (null == old) {
          cnts.put(limitGroup, 1);
        } else {
          cnts.put(limitGroup, old.intValue() + 1);
        }
      }
    }
  }

  public static ClazzRestriction getMatchRestriction(Clazz clazz, Student student) {
    for (ClazzRestriction group : clazz.getEnrollment().getRestrictions()) {
      boolean groupPass = true;
      for (ClazzRestrictionItem item : group.getItems()) {
        boolean itemPass = true;
        ClazzRestrictionMeta meta = item.getMeta();
        Set<String> values = CollectUtils.newHashSet(item.getContents().split(","));
        String value = null;
        if (meta.equals(ClazzRestrictionMeta.Squad)) {
          if (student.getSquad() == null) {
            value = "";
          } else {
            value = student.getSquad().getId() + "";
          }
        } else if (meta.equals(ClazzRestrictionMeta.Department)) {
          value = student.getDepartment().getId() + "";
        } else if (meta.equals(ClazzRestrictionMeta.Direction)) {
          if (student.getDirection() == null) {
            value = "";
          } else {
            value = student.getDirection().getId() + "";
          }
        } else if (meta.equals(ClazzRestrictionMeta.Level)) {
          value = student.getLevel().getId() + "";
        } else if (meta.equals(ClazzRestrictionMeta.Gender)) {
          value = student.getGender().getId() + "";
        } else if (meta.equals(ClazzRestrictionMeta.Grade)) {
          value = student.getGrade().getCode();
        } else if (meta.equals(ClazzRestrictionMeta.Major)) {
          value = student.getMajor().getId() + "";
        } else if (meta.equals(ClazzRestrictionMeta.StdType)) {
          value = student.getStdType().getId() + "";
        }else if (meta.equals(ClazzRestrictionMeta.EduType)) {
          value = student.getEduType().getId() + "";
        }
        if (item.isIncluded()) {
          itemPass = values.isEmpty() || values.contains(value);
        } else {
          itemPass = !values.isEmpty() && !values.contains(value);
        }
        if (!itemPass) {
          groupPass = false;
          break;
        }
      }
      if (groupPass) {
        return group;
      }
    }
    return null;
  }

  public static RestrictionComparator getComparator() {
    return comparator;
  }

  private static class RestrictionComparator implements Comparator<ClazzRestriction> {

    private static final int MAXPRIORITY = 20000;
    private static final int HIGHPRIORITY = 10000;
    private static final int NORMALPRIORITY = 5000;
    private static final int LOWPRIORITY = 2500;
    private static final int ZEROPRIORITY = 500;

    private List<ClazzRestrictionMeta> restrictionMetas = CollectUtils.newArrayList(ClazzRestrictionMeta.Squad,
        ClazzRestrictionMeta.Direction, ClazzRestrictionMeta.Major,
        ClazzRestrictionMeta.Department);

    public int compare(ClazzRestriction o1, ClazzRestriction o2) {
      int priorty1 = getPriority(o1);
      int priorty2 = getPriority(o2);
      if (priorty1 == priorty2) {
        return (o2.getMaxCount() - o2.getCurCount()) - (o1.getMaxCount() - o1.getCurCount());
      } else {
        return priorty2 - priorty1;
      }
    }

    private int getPriority(ClazzRestriction group) {
      int priority = 0;
      boolean hasMax = false;
      for (ClazzRestrictionItem courseLimitItem : group.getItems()) {
        if (!courseLimitItem.isIncluded()) {
          if (ClazzRestrictionMeta.Squad.equals(courseLimitItem.getMeta())) {
            if (!hasMax) {
              priority += MAXPRIORITY / 2;
            }
          } else if (ClazzRestrictionMeta.Direction.equals(courseLimitItem.getMeta())) {
            priority += HIGHPRIORITY / 2;
          } else if (ClazzRestrictionMeta.Major.equals(courseLimitItem.getMeta())) {
            priority += NORMALPRIORITY / 2;
          } else if (ClazzRestrictionMeta.Department.equals(courseLimitItem.getMeta())) {
            priority += LOWPRIORITY / 2;
          } else if (!restrictionMetas.contains(courseLimitItem.getMeta())) {
            priority += ZEROPRIORITY / 2;
          }
        } else {
          if (ClazzRestrictionMeta.Squad.equals(courseLimitItem.getMeta())) {
            if (!hasMax) {
              priority += MAXPRIORITY;
            }
          } else if (ClazzRestrictionMeta.Direction.equals(courseLimitItem.getMeta())) {
            priority += HIGHPRIORITY;
          } else if (ClazzRestrictionMeta.Major.equals(courseLimitItem.getMeta())) {
            priority += NORMALPRIORITY;
          } else if (ClazzRestrictionMeta.Department.equals(courseLimitItem.getMeta())) {
            priority += LOWPRIORITY;
          } else if (!restrictionMetas.contains(courseLimitItem.getMeta())) {
            priority += ZEROPRIORITY;
          }
        }

      }
      return priority;
    }
  }
}
