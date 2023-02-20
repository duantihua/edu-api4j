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

import java.util.Collection;

import org.openurp.edu.clazz.model.Clazz;
import org.openurp.edu.clazz.model.CourseTaker;
import org.openurp.edu.clazz.model.Enrollment;
import org.openurp.edu.clazz.model.Restriction;
import org.openurp.edu.clazz.model.RestrictionItem;

public class ClazzElectionUtil {

  /**
   * 将所有应该指向Clazz的Clazz内部属性指向正确的clazz
   *
   * @param clazz
   */
  public static void normalizeTeachClass(Clazz clazz) {
    for (CourseTaker taker : clazz.getEnrollment().getCourseTakers()) {
      taker.setClazz(clazz);
    }
    // for (ExamTaker taker : clazz.getEnrollment().getExamTakers()) {
    // taker.setClazz(clazz);
    // }
    for (Restriction group : clazz.getEnrollment().getRestrictions()) {
      group.setClazz(clazz);
      for (RestrictionItem item : group.getItems()) {
        item.setRestriction(group);
      }
    }
  }

  /**
   * 为教学班添加courseTaker<br>
   * 会将courseTaker里的clazz属性指向正确的clazz<br>
   * 会更新教学班的实际人数
   *
   * @param teachclass
   * @param takers
   */
  public static void addCourseTaker(Enrollment teachclass, CourseTaker taker) {
    teachclass.getCourseTakers().add(taker);
    teachclass.setActual(teachclass.getCourseTakers().size());
  }

  /**
   * 为教学班添加courseTaker<br>
   * 会将courseTaker里的clazz属性指向正确的clazz<br>
   * 会更新教学班的实际人数
   *
   * @param teachclass
   * @param takers
   */
  public static void addCourseTakers(Enrollment teachclass, Collection<CourseTaker> takers) {
    for (CourseTaker taker : takers) {
      addCourseTaker(teachclass, taker);
    }
  }

}
