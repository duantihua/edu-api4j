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

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;
import org.springframework.core.io.ClassPathResource;

/**
 * 回显到web的输出观察者
 */
public class OutputWebObserver extends OutputProcessObserver {

  protected String path;

  protected String base;

  public OutputWebObserver() {
  }

  public OutputWebObserver(String base, PrintWriter writer, TextResource textResource, String path) {
    super(writer, textResource);
    this.base = base;
    this.path = path;
    outputTemplate();
  }

  public void outputTemplate() {
    try {
      ClassPathResource cpr = new ClassPathResource("/template/" + path);
      LineNumberReader reader = new LineNumberReader(new InputStreamReader(cpr.getInputStream(), "UTF-8"));
      String lineContent = null;
      do {
        lineContent = reader.readLine();
        if (null != lineContent) {
          lineContent = Strings.replace(lineContent, "${base}", base);
          writer.write(lineContent + "\r\n");
        }
      } while (null != lineContent);
      writer.flush();
    } catch (Exception e) {
      logger.warn("exception", e);
    }
  }

  public OutputWebObserver(PrintWriter writer, TextResource textResource) {
    this.writer = writer;
    this.textResource = textResource;
  }

  public void outputNotify(int level, OutputMessage msgObj) {
    try {
      switch (level) {
      case 1:
        writer.print(message(msgObj));
        writer.flush();
        break;
      case 2:
        writer.print("<font color=blue>");
        writer.print(message(msgObj));
        writer.print("</font>");
        writer.flush();
        break;
      case 3:
        writer.print("<font color=red>");
        writer.print(message(msgObj));
        writer.print("</font>");
        writer.flush();
        break;
      case 4:
        writer.print("<font color='green'>");
        writer.print(message(msgObj));
        writer.print("</font>");
        writer.flush();
      }
    } catch (Exception e) {
      logger.warn("exception", e);
    }
  }

  public void outputNotify(OutputMessage msgObj) {
    outputNotify(good, msgObj);
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setBase(String base) {
    this.base = base;
  }

}
