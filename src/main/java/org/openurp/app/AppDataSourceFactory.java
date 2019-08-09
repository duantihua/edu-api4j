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

import org.beangle.commons.io.IOs;
import org.beangle.commons.io.StringBuilderWriter;
import org.beangle.commons.lang.Charsets;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openurp.app.util.AesEncryptor;
import org.openurp.app.util.DataSourceUtils;
import org.openurp.app.util.DatasourceConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppDataSourceFactory implements FactoryBean<DataSource>, InitializingBean, DisposableBean {
  private String url;
  private String user;
  private String password;
  private String driver;
  private String name;
  private Map<String, String> props = new HashMap<String, String>();

  private DataSource _result;

  @Override
  public DataSource getObject() {
    return _result;
  }

  @Override
  public Class<?> getObjectType() {
    return DataSource.class;
  }

  @Override
  public void destroy() {
    DataSourceUtils.close(_result);
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  public void fetchConf() {
    if (null == name) name = "default";
    File appFile = UrpApp.getUrpAppFile();
    if (appFile.exists()) {
      this.url = UrpApp.getUrpAppFile().getAbsolutePath();
    } else {
      this.url =
        Urp.getInstance().getApi() + "/platform/config/datasources/" + UrpApp.getName() + "/" + this.name + ".json?secret=" + UrpApp.Instance.getSecret();
    }
    try {
      postInit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    fetchConf();
    _result = DataSourceUtils.build(driver, user, password, props);
  }

  public void postInit() throws Exception {
    if (null != url) {
      boolean isXML = url.endsWith(".xml");
      if (url.startsWith("jdbc:")) {
        if (null == driver) {
          driver = Strings.substringBetween(url, "jdbc:", ":");
          props.put("url", url);
        }
      } else if (url.startsWith("http")) {
        String text = HttpUtils.getResponseText(url);
        if (Strings.isNotBlank(text)) {
          InputStream is = new ByteArrayInputStream(text.getBytes());
          merge(readConf(is, this.name, isXML));
        }
      } else {
        File f = new java.io.File(url);
        URL urlAddr = f.exists() ? f.toURI().toURL() : new URL(url);
        merge(readConf(urlAddr.openStream(), this.name, isXML));
      }
    }
    if (password != null && password.startsWith("?")) {
      this.password = new AesEncryptor(UrpApp.Instance.getSecret()).decrypt(password.substring(1));
    }
  }

  public static DatasourceConfig readConf(InputStream is, String dsname, boolean isXML) {
    DatasourceConfig conf = null;
    if (isXML) {
      try {
        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        List nodes = document.selectNodes("/app/resources/datasource");
        Set<String> predefined = Set.of("user", "password", "driver", "props");
        for (Object o : nodes) {
          if (o instanceof Node) {
            Node node = (Node) o;
            String name = node.valueOf("@name");
            if (name.equals(dsname)) {
              conf = new DatasourceConfig();
              conf.user = node.selectSingleNode("user").getText();
              conf.password = node.selectSingleNode("password").getText();
              conf.driver = node.selectSingleNode("driver").getText();
              conf.name = name;
              List propNodes = node.selectNodes("props/prop");
              for (Object po : propNodes) {
                Node pn = (Node) po;
                conf.props.put(pn.valueOf("@name"), pn.valueOf("@value"));
              }
              List children = node.selectNodes("*");
              for (Object o1 : children) {
                Node node1 = (Node) o1;
                if (!predefined.contains(node1.getName())) {
                  conf.props.put(node1.getName(), node1.getText());
                }
              }
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      StringBuilderWriter sw = new StringBuilderWriter();
      Charset charset = Charsets.UTF_8;
      try {
        IOs.copy(new InputStreamReader(is, charset.name()), sw);
        conf = new DatasourceConfig(DataSourceUtils.parseJson(sw.toString()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return conf;
  }

  private void merge(DatasourceConfig conf) {
    if (null == user) user = conf.user;
    if (null == password) password = conf.password;
    if (null == driver) driver = conf.driver;
    if (null == name) name = conf.name;
    for (Map.Entry<String, String> e : conf.props.entrySet()) {
      if (!props.containsKey(e.getKey())) props.put(e.getKey(), e.getValue());
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getProps() {
    return props;
  }

  public void setProps(Map<String, String> props) {
    this.props = props;
  }


}
