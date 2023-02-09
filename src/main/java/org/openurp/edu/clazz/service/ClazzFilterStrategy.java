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

import org.hibernate.Query;
import org.hibernate.Session;

public interface ClazzFilterStrategy {
  public static final String SQUAD = "squad";
  public static final String COURSE_TYPE = "courseType";
  public static final String DIRECTION = "direction";
  public static final String MAJOR = "major";
  public static final String STD = "std";
  public static final String STD_TYPE = "stdType";
  public static final String TEACH_DEPART = "teachDepart";
  public static final String TEACHER = "teacher";
  public static final String TEACHCLASS_DEPART = "depart";

  public String getName();

  public String getPostfix();

  public String getPrefix();

  public String getFilterString();

  public Query createQuery(Session session);

  public String getQueryString();

  public Query createQuery(Session session, String prefix, String postfix);

  public String getQueryString(String prefix, String postfix);
}
