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
package org.openurp.app.security.service;

import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.security.Request;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.userdetail.DefaultAccount;
import org.openurp.app.security.FuncResource;

public class CacheableAuthorityManager extends BaseServiceImpl implements AuthorityManager, Initializing {
  /** 角色权限 */
  protected Map<String, Set<?>> authorities = CollectUtils.newHashMap();

  /** 公开资源name */
  protected Set<String> publicResources;

  /** 公有资源names */
  protected Set<?> protectedResources;

  protected Set<String> roots;

  protected RemoteAuthorityService authorityService;

  protected Set<String> ignores = CollectUtils.newHashSet();

  /**
   * 资源是否被授权<br>
   * 1)检查是否是属于公有资源<br>
   * 2)检查角色权限<br>
   */
  public boolean isAuthorized(SecurityContext context) {
    return isAuthorized(context, context.getRequest());
  }

  @Override
  public boolean isAuthorized(SecurityContext context, Request request) {
    String resourceName = request.getResource().toString();
    if (ignores.contains(resourceName)) { return true; }
    if (publicResources.contains(resourceName)) { return true; }
    if (null == context.getSession()) { return false; }
    if (protectedResources.contains(resourceName)) { return true; }
    DefaultAccount account = (DefaultAccount) context.getSession().getPrincipal();
    boolean isRoot = context.isRoot();
    if (isRoot) return true;
    String authorities = account.getAuthorities();
    for (String authority : Strings.split(authorities)) {
      if (isAuthorizedByRole(authority, resourceName)) { return true; }
    }
    return false;
  }

  /**
   * 判断组内是否含有该资源
   *
   * @param authority
   * @param resource
   */
  private boolean isAuthorizedByRole(String authority, Object resource) {
    Set<?> actions = authorities.get(authority);
    if (null == actions) {
      actions = refreshRolePermissions(authority);
    }
    return actions.contains(resource);
  }

  public Set<?> refreshRolePermissions(String authority) {
    Set<?> actions = authorityService.getResourceNamesByRole(authority);
    authorities.put(authority, actions);
    logger.debug("Refresh role:{}'s permissions:{}", authority, actions);
    return actions;
  }

  /** 加载三类资源 */
  public void refreshCache() {
    publicResources = authorityService.getResourceNamesByScope(FuncResource.Scope.Public);
    protectedResources = authorityService.getResourceNamesByScope(FuncResource.Scope.Protected);
    roots = authorityService.getRoots();
  }

  public void init() throws Exception {
    refreshCache();
  }

  public void setAuthorityService(RemoteAuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  @Override
  public boolean isRoot(String user) {
    return this.roots.contains(user);
  }

  public void setIgnores(Set<String> ignores) {
    this.ignores = ignores;
  }

}
