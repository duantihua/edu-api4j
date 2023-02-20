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

import java.io.PrintWriter;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;
import org.openurp.service.OutputMessage;
import org.openurp.service.OutputObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可以体现进度条的内容输出者
 *
 *
 */
public class OutputProcessObserver implements OutputObserver {

  protected PrintWriter writer;

  protected TextResource textResource;

  protected Logger logger = LoggerFactory.getLogger(OutputProcessObserver.class);

  public OutputProcessObserver() {
    super();
  }

  public OutputProcessObserver(PrintWriter writer, TextResource textResource) {
    this.writer = writer;
    this.textResource = textResource;
  }

  public void setSummary(String msg) {
    try {
      writer.write("<script>setSummary('" + msg + "')</script>\n");
      writer.flush();
    } catch (Exception e) {
      logger.warn("exception", e);
    }
  }

  public void setOverallCount(int count) {
    try {
      writer.write("<script>count=" + count + "</script>" + "\n");
      writer.flush();
    } catch (Exception e) {
      logger.warn("exception", e);
    }
  }

  /**
   * @see OutputWebObserver#notifyStart()
   */
  public void notifyStart(String summary, int count, String[] msgs) {
    try {
      setSummary(summary);
      setOverallCount(count);
      if (null != msgs) {
        for (int i = 0; i < msgs.length; i++) {
          writer.write("<script>addProcessMsg('" + msgs[i] + "');</script>\n");
        }
        writer.flush();
      }
    } catch (Exception e) {
      logger.warn("exception", e);
    }
  }

  /**
   * @see OutputWebObserver#outputNotify(int, java.lang.Object)
   */
  public void outputNotify(int level, OutputMessage msgObj, boolean increaceProcess) {
    try {
      if (increaceProcess)
        writer.print("<script>addProcessMsg(" + level + ",\"" + message(msgObj) + "\",1);</script>\n");
      else writer.print("<script>addProcessMsg(" + level + ",\"" + message(msgObj) + "\",0);</script>\n");
      writer.flush();
    } catch (Exception e) {
      logger.warn("exception", e);
    }
  }

  public void outputNotify(int level, OutputMessage msgObj) {
    outputNotify(level, msgObj, true);
  }

  public String message(OutputMessage msgObj) {
    return msgObj.getMessage(textResource);
  }

  public PrintWriter getWriter() {
    return writer;
  }

  public void setWriter(PrintWriter writer) {
    this.writer = writer;
  }

  public String messageOf(String key) {
    if (Strings.isNotEmpty(key)) {
      return textResource.getText(key);
    } else {
      return "";
    }
  }

  public String messageOf(String key, Object arg0) {
    if (Strings.isNotEmpty(key)) {
      return textResource.getText(key, key, arg0);
    } else {
      return "";
    }
  }

  public void notifyFinish() {
    writer.println("finish");
  }

  public void notifyStart() {
    writer.println("start");
  }

  public TextResource getTextResource() {
    return textResource;
  }

  public void setTextResource(TextResource textResource) {
    this.textResource = textResource;
  }

}
