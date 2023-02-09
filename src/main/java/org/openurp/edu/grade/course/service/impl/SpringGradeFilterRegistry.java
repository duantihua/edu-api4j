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
package org.openurp.edu.grade.course.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 基于spring的过滤器注册表
 */
public class SpringGradeFilterRegistry
    implements GradeFilterRegistry, ApplicationContextAware, InitializingBean {

  final Map<String, GradeFilter> filters = CollectUtils.newHashMap();
  ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public void afterPropertiesSet() throws Exception {
    if (null == applicationContext) return;
    String[] names = applicationContext.getBeanNamesForType(GradeFilter.class);
    if (null != names && names.length > 0) {
      for (String name : names) {
        filters.put(name, (GradeFilter) applicationContext.getBean(name));
      }
    }
  }

  public GradeFilter getFilter(String name) {
    return filters.get(name);
  }

  public List<GradeFilter> getFilters(String name) {
    if (Strings.isBlank(name)) return Collections.emptyList();
    String[] filterNames = Strings.split(name, new char[] { '|', ',' });
    List<GradeFilter> myFilters = CollectUtils.newArrayList();
    for (String filterName : filterNames) {
      GradeFilter filter = filters.get(filterName);
      if (null != filter) myFilters.add(filter);
    }
    return myFilters;
  }

}
