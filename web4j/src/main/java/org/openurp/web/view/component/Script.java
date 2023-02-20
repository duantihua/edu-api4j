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
package org.openurp.web.view.component;

import java.util.LinkedHashSet;
import java.util.Set;

import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.view.component.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class Script extends ClosingUIBean {

  protected String type;

  protected boolean defer;

  protected String charset;

  protected String tmpl;

  protected String src;

  protected String require;

  protected Set<String> requireCss = new LinkedHashSet<String>();

  protected Set<String> requireJs = new LinkedHashSet<String>();

  private boolean compressed = true;

  private boolean compressBody = false;

  public Script(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    String devMode = getRequestParameter("devMode");
    if (null != devMode) setCompressed(!("true".equals(devMode) || "on".equals(devMode)));

    if (Strings.isEmpty(this.id)) {
      generateIdIfEmpty();
    }
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isDefer() {
    return defer;
  }

  public void setDefer(boolean defer) {
    this.defer = defer;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getTmpl() {
    return tmpl;
  }

  public void setTmpl(String tmpl) {
    this.tmpl = tmpl;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  private void requireCss(String css) {
    requireCss.add(css);
  }

  private void requireJs(String js) {
    requireJs.add(js);
  }

  public void setRequire(String require) {
    this.require = require;
    String[] requireArr = Strings.split(",");
    if (!Arrays.isEmpty(requireArr)) {
      for (String requireItem : requireArr) {
        if (requireItem.toLowerCase().endsWith(".js")) {
          requireJs(requireItem);
        } else if (requireItem.toLowerCase().endsWith(".css")) {
          requireCss(requireItem);
        }
      }
    }
  }

  public boolean isCompressed() {
    return compressed;
  }

  public void setCompressed(boolean compressed) {
    this.compressed = compressed;
  }

  public boolean isCompressBody() {
    return compressBody;
  }

  public void setCompressBody(boolean compressBody) {
    this.compressBody = compressBody;
  }
}
