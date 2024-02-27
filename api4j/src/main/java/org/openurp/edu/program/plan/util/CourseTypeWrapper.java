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
package org.openurp.edu.program.plan.util;

import org.beangle.commons.lang.functor.Transformer;

/**
 *
 */
public class CourseTypeWrapper {

  public static final Transformer WRAPPER = new WrapperTransformer();
  public static final Transformer UNWRAPPER = new UnWrapperTransformer();

  private String courseType;

  public CourseTypeWrapper(String object) {
    this.courseType = object;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((courseType == null) ? 0 : courseType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;

    CourseTypeWrapper other = (CourseTypeWrapper) obj;

    if (courseType == null) {
      if (other.courseType != null) return false;
    } else if (!courseType.equals(other.courseType)) return false;
    return true;
  }

  private static class WrapperTransformer implements Transformer {
    public Object apply(Object object) {
      if (object.getClass().equals(String.class)) {
        return new CourseTypeWrapper((String) object);
      }
      throw new IllegalArgumentException("cannot accept object other than stdType of CourseType");
    }
  }

  private static class UnWrapperTransformer implements Transformer {
    public Object apply(Object object) {
      if (object.getClass()
          .equals(CourseTypeWrapper.class)) {
        return ((CourseTypeWrapper) object).getCourseType();
      }
      throw new IllegalArgumentException("cannot accept object other than stdType of CourseTypeWrapper");
    }
  }

  @Override
  public String toString() {
    return "CourseTypeWrapper [courseType=" + courseType + "]";
  }

}
