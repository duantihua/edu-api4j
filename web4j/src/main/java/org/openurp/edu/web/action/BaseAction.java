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

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventMulticaster;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.security.Securities;
import org.beangle.struts2.convention.route.Action;
import org.openurp.base.model.User;
import org.openurp.code.service.CodeService;
import org.openurp.base.service.BaseInfoService;
import org.openurp.base.service.DepartmentService;
import org.openurp.base.service.SemesterService;
import org.openurp.web.helper.LogHelper;
import org.openurp.web.util.OutputProcessObserver;
import org.openurp.web.util.OutputWebObserver;
import org.openurp.web.action.SecurityActionSupport;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class BaseAction extends SecurityActionSupport {

  protected CodeService codeService;

  protected BaseInfoService baseInfoService;

  protected EventMulticaster eventMulticaster;

  /**
   * 教学日历服务对象
   */
  protected SemesterService semesterService;

  /**
   * 部门服务对象
   */
  protected DepartmentService departmentService;

  /** 系统日志 */
  protected LogHelper logHelper;

  /**
   * FIXME according school
   *
   * @return
   */
  protected User getLoginUser() {
    OqlBuilder<User> builder = OqlBuilder.from(User.class, "user");
    builder.where("user.code =:code", Securities.getUsername());
    List<User> users = entityDao.search(builder);
    if (users.isEmpty()) return null;
    else return users.get(0);
  }

  protected String forwardError(String message) {
    addError(message);
    return "error";
  }

  protected String forwardError(String[] messages) {
    int i = 0;
    while (i < messages.length) {
      addMessage(messages[i++]);
    }
    return "error";
  }
  protected OutputWebObserver getOutputProcessObserver(String forwardName,
      Class<? extends OutputWebObserver> observerClass) {
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/html; charset=utf-8");
    OutputWebObserver observer;
    try {
      observer = observerClass.newInstance();
      observer.setBase(ServletActionContext.getRequest().getContextPath());
      observer.setTextResource(getTextResource());
      observer.setWriter(response.getWriter());
      observer.setPath(forwardName);
      observer.outputTemplate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return observer;
  }

  protected String redirect(Action action, String message, String[] prefixes) {
    HttpParameters params = ActionContext.getContext().getParameters();
    // 串接参数
    if (null != prefixes && prefixes.length > 0) {
      for (String key : params.keySet()) {
        if (!Objects.equals("params", key)) {
          for (int i = 0; i < prefixes.length; i++) {
            if (key.startsWith(prefixes[i])) {
              String value = get(key);
              if (Strings.isNotEmpty(value)) {
                action.getParams().put(key, value);
              }
              break;
            }
          }
        }
      }
    }
    return redirect(action, message);
  }

  protected OutputProcessObserver getOutputProcessObserver() {
    return getOutputProcessObserver("processDisplay.ftl", OutputWebObserver.class);
  }

  protected OutputProcessObserver getOutputProcessObserver(Class<? extends OutputWebObserver> observerClass) {
    return getOutputProcessObserver("processDisplay.ftl", observerClass);
  }

  /**
   * 发布应用事件
   *
   * @param event
   * @since 2012-05-25
   */
  protected void publish(Event event) {
    eventMulticaster.multicast(event);
  }

  public void setCodeService(CodeService codeService) {
    this.codeService = codeService;
  }

  public void setBaseInfoService(BaseInfoService baseInfoService) {
    this.baseInfoService = baseInfoService;
  }

  protected SemesterService getSemesterService() {
    return semesterService;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

  protected DepartmentService getDepartmentService() {
    return departmentService;
  }

  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  public void setLogHelper(LogHelper logHelper) {
    this.logHelper = logHelper;
  }

  protected void debug(Object debubObj) {
    logger.debug(String.valueOf(debubObj));
  }

  protected void debug(Object debubObj, Exception e) {
    logger.debug(String.valueOf(debubObj), e);
  }

  protected void info(Object infoObj) {
    logger.info(String.valueOf(infoObj));
  }

  protected void info(Object infoObj, Exception e) {
    logger.info(String.valueOf(infoObj), e);
  }

  protected void error(Object errorObj) {
    logger.error(String.valueOf(errorObj));
  }

  protected void error(Object errorObj, Exception e) {
    logger.error(String.valueOf(errorObj), e);
  }

  public void setEventMulticaster(EventMulticaster eventMulticaster) {
    this.eventMulticaster = eventMulticaster;
  }

}
