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
package org.openurp.web.util;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;

/**
 * 输出消息
 *
 *
 */
public class OutputMessage {

  protected String key;

  protected String message;

  public OutputMessage() {
  }

  public OutputMessage(String key, String message) {
    this.key = key;
    this.message = message;
  }

  public OutputMessage(String key, String message, String engMessage) {
    this.key = key;
    this.message = message;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMessage() {
    return message;
  }

  /**
   * to be overrided by subclass
   *
   * @param resourses
   * @param locale
   * @return
   */
  public String getMessage(TextResource textResource) {
    if (Strings.isNotEmpty(key)) return textResource.getText(key);
    else return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
