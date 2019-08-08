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
package org.openurp.app.util;

import java.util.HashMap;
import java.util.Map;

public class DatasourceConfig {
  public String name;
  public String driver;
  public String user;
  public String password;

  public Map<String, String> props = new HashMap<String, String>();

  public DatasourceConfig(Map<String, String> properties) {
    for (Map.Entry<String, String> e : properties.entrySet()) {
      String key = e.getKey();
      String v = e.getValue();
      switch (key) {
        case "user":
          this.user = v;
          break;
        case "password":
          this.password = v;
          break;
        case "name":
          this.name = v;
          break;
        case "driver":
        case "catalog":
        case "schema":
          break;
        default:
          props.put(key, v);
      }
    }
  }
}
