/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.security.data.Permission;
import org.beangle.security.data.Profile;
import org.beangle.security.data.ProfileService;
import org.openurp.app.Urp;
import org.openurp.app.UrpApp;
import org.openurp.app.security.DataPermission;
import org.openurp.app.security.Dimension;
import org.openurp.app.security.FuncResource;
import org.openurp.app.security.FuncResource.Scope;
import org.openurp.app.security.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * http://localhost:8080/platform/user/dimensions/departments.json
 * http://localhost:8080/platform/user/profiles/root.json?domain=edu
 * http://localhost:8080/platform/user/accounts/root.json
 * http://localhost:8080/platform/security/func/edu-lesson-adminapp/resources.json
 * http://localhost:8080/platform/security/func/edu-lesson-adminapp/permissions/role/1.json
 * http://localhost:8080/platform/security/func/edu-lesson-adminapp/resources.json?scope=Private
 * http://localhost:8080/platform/security/data/permissions/user/root.json?data=org.openurp.edu.base
 * .model.Student&app=edu-lesson-adminapp
 *
 * @author chaostone
 */
public class RemoteAuthorityService implements ProfileService, Initializing {
  private static final Logger logger = LoggerFactory.getLogger(RemoteAuthorityService.class);

  private Cache userCache = null;

  private Cache sysCache = null;

  private Map<String, FuncResource> resourceCache = CollectUtils.newHashMap();

  private Set<String> roots = null;

  private UserDataResolver dataResolver;

  @Override
  public void init() throws Exception {
    userCache = CacheManager.getInstance().getCache("user_profile");
    if (null == userCache) { throw new RuntimeException("Cannot find user_profile cache in ehcache.xml"); }

    sysCache = CacheManager.getInstance().getCache("user_data_permission");
    if (null == sysCache) { throw new RuntimeException(
        "Cannot find user_data_permission cache in ehcache.xml"); }
    roots = getRoots();
  }

  public Dimension getDimension(String fieldName) {
    Element dimensionElem = sysCache.get("dimension_" + fieldName);
    Dimension dimension = (dimensionElem == null) ? null : (Dimension) dimensionElem.getObjectValue();
    if (null == dimension) {
      String url = Urp.Instance.getPlatformBase() + "/user/dimensions/" + fieldName + ".json";
      String resources = HttpUtils.getResponseText(url);
      Map rs = new Gson().fromJson(resources, Map.class);
      if (rs.isEmpty()) return null;
      dimension = toDimension(rs);
      sysCache.put(new Element("dimension_" + fieldName, dimension));
    }
    return dimension;
  }

  private Dimension toDimension(Map data) {
    Dimension dimension = new Dimension();
    for (Object o : data.entrySet()) {
      Map.Entry<String, Object> entry = (Map.Entry) o;
      PropertyUtils.copyProperty(dimension, entry.getKey(), entry.getValue());
    }
    dimension.setMultiple(true);
    return dimension;
  }

  public List<Profile> getProfiles(String userCode, String function) {
    Element ele = ((Element) userCache.get(userCode));

    if (null == ele) {
      String url = Urp.Instance.getPlatformBase() + "/user/profiles/" + userCode + ".json?domain=edu";
      String resources = HttpUtils.getResponseText(url);
      List rs = new Gson().fromJson(resources, List.class);
      if (rs.isEmpty()) return Collections.emptyList();
      List<Profile> profiles = new ArrayList<Profile>();
      for (Object o : rs) {
        Collection properties = (Collection) ((Map) o).get("properties");
        UserProfile profile = new UserProfile();
        profiles.add(profile);
        for (Object p : properties) {
          Map property = (Map) p;
          Dimension dimension = toDimension((Map) property.get("dimension"));
          Object v = property.get("value");
          if (!v.equals(Profile.AllValue)) v = unmarshal((String) v, dimension);
          profile.getProperties().put(dimension.getName(), v);
        }
      }
      userCache.put(new Element(userCode, profiles));
      return profiles;
    } else {
      return (List<Profile>) ele.getValue();
    }
  }

  @SuppressWarnings("rawtypes")
  public Permission getPermission(String user, String dataResource, String functionResource) {
    String key = user + "_" + dataResource;

    Element ele = sysCache.get(key);
    if (null == ele) {
      String url = Urp.Instance.getPlatformBase() + "/security/data/permissions/user/" + user + ".json?data="
          + dataResource + "&app=" + UrpApp.getName();
      String resources = HttpUtils.getResponseText(url);
      Map rs = new Gson().fromJson(resources, Map.class);
      if (rs.isEmpty()) return null;
      else {
        DataPermission dp = new DataPermission();
        dp.setFilters((String) rs.get("filters"));
        dp.setActions((String) rs.get("actions"));
        sysCache.put(new Element(key, dp));
        return dp;
      }
    } else {
      return (DataPermission) ele.getValue();
    }
  }

  /**
   * 获取数据限制的某个属性的值
   *
   * @param property
   * @param restriction
   */
  private Object unmarshal(String value, Dimension property) {
    try {
      List<Object> returned = dataResolver.unmarshal(property, value);
      if (property.isMultiple()) return returned;
      else return (1 != returned.size()) ? null : returned.get(0);
    } catch (Exception e) {
      logger.error("exception with param type:" + property.getTypeName() + " value:" + value, e);
      return null;
    }
  }

  public FuncResource getResource(String name) {
    if (resourceCache.isEmpty()) {
      synchronized (this) {
        if (resourceCache.isEmpty()) {
          String url = Urp.Instance.getPlatformBase() + "/security/func/" + UrpApp.getName()
              + "/resources.json";
          String resources = HttpUtils.getResponseText(url);
          List rs = new Gson().fromJson(resources, List.class);
          for (Object o : rs) {
            Map<String, Object> m = (Map<String, Object>) o;
            FuncResource resource = new FuncResource();
            resource.setName(m.get("name").toString());
            resource.setTitle(m.get("title").toString());
            resource.setScope(Scope.valueOf(m.get("scope").toString()));
            resource.setId((Double.valueOf(m.get("id").toString())).intValue());
            resourceCache.put(resource.getName(), resource);
          }
        }
      }
    }
    return resourceCache.get(name);
  }

  public Set<String> getRoots() {
    String url = Urp.Instance.getPlatformBase() + "/user/roots.json?app=" + UrpApp.getName();
    try {
      String resources = HttpUtils.getResponseText(url);
      List rs = new Gson().fromJson(resources, List.class);
      return new HashSet<String>(rs);
    } catch (Exception e) {
      logger.error("Cannot access {}", url);
      return Collections.emptySet();
    }
  }

  public Set<String> getResourceNamesByRole(String roleId) {
    String url = Urp.Instance.getPlatformBase() + "/security/func/" + UrpApp.getName() + "/permissions/role/"
        + roleId + ".json";
    String resources = HttpUtils.getResponseText(url);
    List rs = new Gson().fromJson(resources, List.class);
    Set<String> s = CollectUtils.newHashSet();
    for (Object o : rs) {
      Map<String, Object> m = (Map<String, Object>) o;
      s.add(m.get("name").toString());
    }
    return s;
  }

  public Set<String> getResourceNamesByScope(Scope scope) {
    String url = Urp.Instance.getPlatformBase() + "/security/func/" + UrpApp.getName()
        + "/resources.json?scope=" + scope.toString();
    try {
      String resources = HttpUtils.getResponseText(url);
      List rs = new Gson().fromJson(resources, List.class);
      Set<String> s = CollectUtils.newHashSet();
      for (Object o : rs) {
        Map<String, Object> m = (Map<String, Object>) o;
        s.add(m.get("name").toString());
      }
      return s;
    } catch (Exception e) {
      logger.error("Cannot access {}", url);
      return Collections.emptySet();
    }
  }

  public String extractResource(String uri) {
    int lastDot = -1;
    for (int i = 0; i < uri.length(); i++) {
      char a = uri.charAt(i);
      if (a == '.' || a == '!') {
        lastDot = i;
        break;
      }
    }
    if (lastDot < 0) {
      lastDot = uri.length();
    }
    return uri.substring(0, lastDot);
  }

  public void setDataResolver(UserDataResolver dataResolver) {
    this.dataResolver = dataResolver;
  }

}
