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

import java.util.HashMap;
import java.util.Map;

import org.beangle.commons.text.i18n.TextResource;
import org.openurp.edu.clazz.model.Clazz;

public class TaskPerClassPropertyExtractor extends TeachTaskPropertyExtractor {

  Map courseAndClassMap = new HashMap();

  public TaskPerClassPropertyExtractor(TextResource textResource) {
    super(textResource);
  }

  public Object getPropertyValue(Object target, String property) throws Exception {
    Clazz clazz = (Clazz) target;
    if ("teachclass.adminClasses".equals(property)) {
      // TODO 从Restriction里面拿班级
      // for (Iterator iter = clazz.getEnrollment().getSquades().iterator(); iter.hasNext();)
      // {
      // Squad adminClass = (Squad) iter.next();
      // if (null == courseAndClassMap.get(adminClass.getId() + ","
      // + clazz.getCourse().getId())) {
      // courseAndClassMap.put(adminClass.getId() + "," + clazz.getCourse().getId(),
      // Boolean.TRUE);
      // return adminClass.getName();
      // }
      // }
      return null;
    } else return super.getPropertyValue(target, property);
  }

}
