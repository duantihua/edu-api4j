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
package org.openurp.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.io.IOs;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Urp {

  private static final Logger logger = LoggerFactory.getLogger(Urp.class);

  private final String home;

  private final String base;

  private final String cas;

  private final String api;

  private final String webapp;

  private final String portal;

  private final String cdn;

  private final Map<String, String> properties;

  public Urp(String home, Map<String, String> properties) {
    this.home = home;
    this.properties = properties;
    logger.info("Openurp Home:{}", this.home);

    if (properties.containsKey("openurp.base")) {
      this.base = processUrl(properties.get("openurp.base"));
    } else {
      throw new RuntimeException("Cannot find openurp.base");
    }
    this.cas = readBase("openurp.cas");
    this.api = readBase("openurp.api");
    this.webapp = readBase("openurp.webapp");
    this.portal = readBase("openurp.portal");
    this.cdn = readBase("openurp.static");
  }

  private String processUrl(String b) {
    if (b.endsWith("/")) {
      b = b.substring(0, b.length() - 1);
    }
    if (!b.startsWith("http")) {
      b = "http://" + b;
    }
    return b;
  }

  private String readBase(String property) {
    String b = properties.get(property);
    if (null == b) {
      if (property.equals("openurp.webapp")) {
        b = properties.get("openurp.base");
      } else {
        b = properties.get("openurp.base") + "/" + Strings.replace(property, "openurp.", "");
      }
    }
    return processUrl(b);
  }

  public String getHome() {
    return home;
  }

  public String getWebappPath(String appPath) {
    return webapp + appPath;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public String getBase() {
    return base;
  }

  public String getApi() {
    return api;
  }

  public String getCas() {
    return cas;
  }

  public String getPortal() {
    return portal;
  }

  public String getCdn() {
    return cdn;
  }

  public String getWebapp() {
    return webapp;
  }

  public String getStatic() {
    return cdn;
  }

  public static Urp getInstance() {
    return Instance;
  }

  public static Urp Instance = new Urp(findHome(), readConfig(findHome()));

  public static String findHome() {
    String home = System.getProperty("openurp.home");
    if (home == null) {
      home = SystemInfo.getUser().getHome() + "/.openurp";
    }
    return home;
  }

  public static Map<String, String> readConfig(String home) {
    Map<String, String> properties = CollectUtils.newHashMap();
    File f = new File(home + "/conf.properties");
    if (f.exists()) {
      Properties p = new Properties();
      InputStream is = null;
      try {
        is = new FileInputStream(f);
        p.load(is);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        IOs.close(is);
      }
      for (Object k : p.keySet()) {
        properties.put(k.toString(), p.getProperty(k.toString()).toString());
      }
    }
    return properties;
  }
}
