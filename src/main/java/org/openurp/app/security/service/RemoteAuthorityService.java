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

import com.google.gson.Gson;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.security.Securities;
import org.beangle.security.core.userdetail.DefaultAccount;
import org.beangle.security.data.Permission;
import org.beangle.security.data.Profile;
import org.beangle.security.data.ProfileService;
import org.openurp.app.Urp;
import org.openurp.app.UrpApp;
import org.openurp.app.security.DataPermission;
import org.openurp.app.security.Dimension;
import org.openurp.app.security.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * http://localhost:8080/platform/user/dimensions/departments.json
 * http://localhost:8080/platform/security/func/edu-lesson-adminapp/permissions/role/1.json
 * http://localhost:8080/platform/security/data/permissions/user/root.json?data=org.openurp.edu.base
 * .model.Student&app=edu-lesson-adminapp
 *
 * @author chaostone
 */
public class RemoteAuthorityService implements ProfileService, Initializing {
  private static final Logger logger = LoggerFactory.getLogger(RemoteAuthorityService.class);

  private Cache userCache = null;

  private Cache sysCache = null;

  private Map<String, Dimension> dimensionMap = CollectUtils.newHashMap();

  private UserDataResolver dataResolver;

  @Override
  public void init() throws Exception {
    userCache = CacheManager.getInstance().getCache("user_profile");
    if (null == userCache) {
      throw new RuntimeException("Cannot find user_profile cache in ehcache.xml");
    }

    sysCache = CacheManager.getInstance().getCache("user_data_permission");
    if (null == sysCache) {
      throw new RuntimeException(
              "Cannot find user_data_permission cache in ehcache.xml");
    }
  }

  public Dimension getDimension(String fieldName) {
    Dimension dimension = dimensionMap.get(fieldName);
    if (null == dimension) {
      String url = Urp.Instance.getApi() + "/platform/user/dimensions/" + fieldName + ".json";
      String resources = HttpUtils.getResponseText(url);
      Map rs = new Gson().fromJson(resources, Map.class);
      if (rs.isEmpty()) return null;
      dimension = toDimension(rs);
      dimensionMap.put(fieldName, dimension);
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
      DefaultAccount account = (DefaultAccount) Securities.getSession().getPrincipal();
      String profileStr = (String) account.getDetails().get("profiles_edu");
      if (Strings.isEmpty(profileStr)) {
        return Collections.emptyList();
      } else {
        List<Profile> profiles = new ArrayList<Profile>();
        List<Map<String, String>> rs = new Gson().fromJson(profileStr, List.class);
        for (Map<String, String> o : rs) {
          UserProfile profile = new UserProfile();
          profiles.add(profile);
          for (Map.Entry<String, String> e : o.entrySet()) {
            Dimension dimension = this.getDimension(e.getKey());
            String v = e.getValue();
            Object value = v;
            if (!v.equals(Profile.AllValue)) value = unmarshal(v, dimension);
            profile.getProperties().put(dimension.getName(), value);
          }
        }
        userCache.put(new Element(userCode, profiles));
        return profiles;
      }
    } else {
      return (List<Profile>) ele.getValue();
    }
  }

  @SuppressWarnings("rawtypes")
  public Permission getPermission(String user, String dataResource, String functionResource) {
    String key = user + "_" + dataResource;

    Element ele = sysCache.get(key);
    if (null == ele) {
      String url = Urp.Instance.getApi() + "/platform/security/data/permissions/user/" + user + ".json?data="
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
   * @param value
   * @param property
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
