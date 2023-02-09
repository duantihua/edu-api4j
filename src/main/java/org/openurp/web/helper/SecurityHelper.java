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

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.StrutsStatics;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.Securities;
import org.beangle.security.authz.AccessDeniedException;
import org.beangle.security.core.userdetail.Profile;
import org.beangle.security.data.Permission;
import org.beangle.security.data.ProfileService;
import org.beangle.ems.app.security.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;
import org.openurp.base.service.EmsCookie;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SecurityHelper {

  private ProfileService profileService;

  public List<Profile> getProfiles() {
    return getProfiles(Securities.getUsername(), Securities.getResource());
  }

  private HttpServletRequest getRequest() {
    ActionContext ac = ActionContext.getContext();
    return (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
  }

  private List<Profile> getProfiles(String user, String path) {
    List<Profile> profiles = profileService.getProfiles(user, path);
    EmsCookie cookie = EmsCookie.get(getRequest(),null);
    long cookieProfileId = cookie.getProfile();

    if (profiles.isEmpty()) return profiles;
    Profile result = null;
    if (cookieProfileId > 0) {
      for (Profile p : profiles) {
        if (p.id == cookieProfileId) {
          result = p;
          break;
        }
      }
      if (null == result) {
        result = profiles.get(0);
      }
    } else {
      result = profiles.get(0);
    }
    return Collections.singletonList(result);
  }

  protected boolean isAdmin() {
    return Securities.isRoot();
  }

  public <T> List<T> getProperties(String name) {
    List<Profile> profiles = getProfiles(Securities.getUsername(), Securities.getResource());
    Set<T> results = CollectUtils.newHashSet();
    for (Profile profile : profiles) {
      Object prop = profile.getProperty(name);
      if (prop instanceof Collection<?>) results.addAll((Collection<T>) prop);
      else results.add((T) prop);
    }
    return CollectUtils.newArrayList(results);
  }

  public void applyPermission(OqlBuilder<?> query) {
    String user = Securities.getUsername();
    if (Securities.isRoot()) return;
    Permission dp = profileService.getPermission(user, query.getEntityClass().getName(),
      Securities.getResource());
    if (null == dp) return;
    List<Profile> profiles = getProfiles(user, Securities.getResource());

    if (profiles.isEmpty())
      throw new AccessDeniedException(Securities.getResource(), "error.security.errorprofile");

    SecurityUtils.apply(query, dp, profiles);
  }

  public void setProfileService(ProfileService profileService) {
    this.profileService = profileService;
  }

  public ProfileService getProfileService() {
    return profileService;
  }

}
