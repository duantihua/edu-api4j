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
package org.openurp.edu.grade.setting.service.impl;

import com.google.gson.Gson;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Strings;
import org.openurp.base.edu.model.Project;
import org.openurp.base.service.ProjectPropertyService;
import org.openurp.code.edu.model.GradeType;
import org.openurp.code.service.CodeService;
import org.openurp.edu.grade.course.model.CourseGradeSetting;
import org.openurp.edu.grade.course.service.CourseGradeSettings;

import java.util.List;
import java.util.Map;

public class CourseGradeSettingsImpl extends BaseServiceImpl implements CourseGradeSettings {

  private static final String COURSEGRADESETTING = "edu.grade.setting";

  private Map<Integer, CourseGradeSetting> cache = CollectUtils.newHashMap();

  private CodeService codeService;

  private ProjectPropertyService projectPropertyService;

  public CourseGradeSetting getSetting(Project project) {
    String settingStr = projectPropertyService.get(project, COURSEGRADESETTING, "");
    CourseGradeSetting setting = null;
    if (Strings.isNotBlank(settingStr)) {
      Gson gson = new Gson();
      try {
        setting = gson.fromJson(settingStr, CourseGradeSetting.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (null == setting) {
      setting = cache.get(project.getId());
      if (setting == null) {
        setting = new CourseGradeSetting(project);
        List<GradeType> allTypes = codeService.getCodes(GradeType.class);
        setting.getGaElementTypes().retainAll(allTypes);
        // Gson gson = new Gson();
        // System.out.println(gson.toJson(setting));
        cache.put(project.getId(), setting);
      }
    }
    return setting;
  }

  public void setCodeService(CodeService codeService) {
    this.codeService = codeService;
  }

  public void setProjectPropertyService(ProjectPropertyService projectPropertyService) {
    this.projectPropertyService = projectPropertyService;
  }
}
