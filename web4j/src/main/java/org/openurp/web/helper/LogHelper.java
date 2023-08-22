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
package org.openurp.web.helper;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class LogHelper {

  private Logger logger = LoggerFactory.getLogger(LogHelper.class);

  private EntityDao entityDao;

  /**
   * 检查参数
   *
   * @param request
   * @param actionClass
   * @param id
   * @throws Exception
   */
  private void checkParams(String content) {
    if (Strings.isEmpty(content)) { throw new RuntimeException(
        "==> the params of [" + this.getClass().getName() + ".info] of method is exception."); }
  }

  /**
   * 记录一个系统日志
   *
   * @param request
   * @param content
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public void info(String content) {
    // checkParams(content);
    // // 新建一个系统日志
    // SystemLog sysLog = new SystemLog();
    // Long userId = SecurityUtils.getUserId();
    // if (null == userId) throw new AuthenticationException();
    //
    // sysLog.setOperater(entityDao.get(User.class, userId).getName());
    // sysLog.setOperation(Strings.abbreviate(content, 400));
    // sysLog.setFunction(RequestUtils.getServletPath(ServletActionContext.getRequest()));
    // sysLog.setOperateAt(new Timestamp(System.currentTimeMillis()));
    // // 操作参数
    // StringBuffer params = new StringBuffer("");
    // Enumeration<String> paramNames = ServletActionContext.getRequest().getParameterNames();
    // while (paramNames.hasMoreElements()) {
    // String key = paramNames.nextElement();
    // params.append(key).append(" = ");
    // params.append(Params.get(key)).append("\n");
    // }
    // sysLog.setParams(Strings.abbreviate(params.toString(), 800));
    // sysLog.setIp(RequestUtils.getIpAddr(ServletActionContext.getRequest()));
    // // 保存
    // entityDao.saveOrUpdate(sysLog);
  }

  public void info(String content, Exception e) {
    info(content);
    logger.info(content, e);
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
