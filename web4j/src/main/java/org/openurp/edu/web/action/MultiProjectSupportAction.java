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
package org.openurp.edu.web.action;

import org.beangle.commons.entity.pojo.Code;
import org.beangle.commons.lang.Assert;
import org.beangle.struts2.convention.route.Action;
import org.openurp.base.model.BaseInfo;
import org.openurp.base.edu.model.Project;
import org.openurp.base.edu.model.Semester;
import org.openurp.service.security.EamsUserCategory;

import java.util.List;

/**
 * <pre>
 * 本Action是多教学项目的Wrapper Action
 * 本Action一般作为学生、教师功能的父类使用，因此和数据级权限无关
 * 因为学生和教师的功能所能看到的数据一般都是和自身有关的数据，且他们的帐号上也不需要设置数据级权限。
 * 具有获取当前教学项目、当前学期的方法
 * </pre>
 */
public abstract class MultiProjectSupportAction extends BaseAction {

  protected void addBasecode(String key, Class<? extends Code<Integer>> clazz) {
    Assert.notEmpty(key);
    put(key, codeService.getCodes(clazz));
  }

  protected void addBaseInfo(String key, Class<? extends BaseInfo> clazz) {
    put(key, baseInfoService.getBaseInfos(clazz,getProject().getSchool()));
  }

  protected List getBaseInfos(Class<? extends BaseInfo> clazz) {
    return baseInfoService.getBaseInfos(clazz, getProject().getSchool());
  }

  /**
   * 这个index页面是一个Wrapper页面<br>
   * 如果需要展现多个教学项目的数据，那么页面就会显示为一个项目一个Tab<br>
   * 如果只展现一个教学项目的数据，那么页面就会隐藏Tab<br>
   */
  public final String index() {
    List<Project> projects = getProjects();
    if (projects.size() == 0) {
      put("isStudent", getLoginUser().getCategory().getId().equals(EamsUserCategory.STD_USER));
      return forward("noproject");
    } else {
      if (projects.size() == 1) {
        put("project", projects.get(0));
        return redirect(new Action(getClass(), "innerIndex", "&projectId=" + projects.get(0).getId()), null,
          null);
      } else {
        // 在这里的把session中的项目ID变成用户所选择的项目ID
        Integer defaultProjectId = projects.get(0).getId();
        Integer givenProjectId = getInt("projectId");
        if (null != givenProjectId) {
          for (Project p : projects) {
            if (p.getId().equals(givenProjectId)) {
              defaultProjectId = givenProjectId;
              break;
            }
          }
        }
        put("defaultProjectId", defaultProjectId);
        put("projects", projects);
        return forward();
      }
    }
  }

  /**
   * 子类的真正的index是innerIndex，展现的是当前教学项目下的index页面
   *
   * @return
   */
  public abstract String innerIndex();

  /**
   * 决定展现给用户哪些教学项目，被index使用。<br>
   * 展现的教学项目由功能Action自行决定
   *
   * @return
   */
  abstract protected List<Project> getProjects();

  /**
   * 获得用户当前在什么教学项目下的方法
   *
   * @return
   */
  protected Project getProject() {
    Project project = (Project) getAttribute("project");
    if (null != project) {
      return project;
    }

    Integer projectId = getIntId("project");
    if (null == projectId) {
      List<Project> projects = getProjects();
      if (!projects.isEmpty()) {
        project = projects.get(0);
      }
    } else {
      project = entityDao.get(Project.class, projectId);
    }
    if (null != project) {
      put("project", project);
    }
    return project;
  }

  public Semester getSemester() {
    Project project = getProject();

    String semesterId = get("semester.id");
    Semester semester = (semesterId == null) ? null : entityDao.get(Semester.class, Integer.valueOf(semesterId));

    if (semester == null) {
      semester = semesterService.getCurSemester(project);
    } else {
      // 判断session/request的学期是否和当前项目匹配
      if (!project.getCalendar().equals(semester.getCalendar())) {
        semester = semesterService.getCurSemester(project);
      }
    }
    return semester;
  }
}
