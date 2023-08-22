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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadHelper {

  private static Logger logger = LoggerFactory.getLogger(DownloadHelper.class);

  public static void download(HttpServletRequest request, HttpServletResponse response, File file) {
    download(request, response, file, file.getName());
  }

  public static void download(HttpServletRequest request, HttpServletResponse response, URL url,
      String display) {
    try {
      download(request, response, url.openStream(), url.getFile(), display);
    } catch (Exception e) {
      logger.warn("download file error=" + display, e);
    }
  }

  public static void download(HttpServletRequest request, HttpServletResponse response, File file,
      String display) {
    try {
      download(request, response, new FileInputStream(file), file.getAbsolutePath(), display);
    } catch (Exception e) {
      logger.warn("download file error=" + display, e);
    }
  }

  public static void download(HttpServletRequest request, HttpServletResponse response, InputStream inStream,
      String name, String display) {
    String attch_name = "";
    byte[] b = new byte[1024];
    int len = 0;
    try {
      String ext = Strings.substringAfterLast(name, ".");
      if (Strings.isBlank(display)) {
        attch_name = getAttachName(name);
      } else {
        attch_name = display;
        if (!attch_name.endsWith("." + ext)) {
          attch_name += "." + ext;
        }
      }
      response.reset();
      String contentType = response.getContentType();
      if (null == contentType) {
        contentType = "application/x-msdownload";
        // if (Strings.isEmpty(ext)) {
        // contentType = "application/x-msdownload";
        // } else {
        // contentType = MimeTypeProvider.getMimeType(ext,
        // "application/x-msdownload");
        // }
        response.setContentType(contentType);
        logger.debug("set content stdType {} for {}", contentType, attch_name);
      }
      response.addHeader("Content-Disposition",
          "attachment; filename=\"" + RequestUtils.encodeAttachName(request, attch_name) + "\"");
      while ((len = inStream.read(b)) > 0) {
        response.getOutputStream().write(b, 0, len);
      }
      inStream.close();
    } catch (Exception e) {
      logger.warn("download file error=" + attch_name, e);
    }
  }

  /**
   * 根据路径得到真实的文件名.
   *
   * @param file_name
   * @return
   */
  public static String getAttachName(String file_name) {
    if (file_name == null) return "";
    file_name = file_name.trim();
    int iPos = 0;
    iPos = file_name.lastIndexOf("\\");
    if (iPos > -1) {
      file_name = file_name.substring(iPos + 1);
    }
    iPos = file_name.lastIndexOf("/");
    if (iPos > -1) {
      file_name = file_name.substring(iPos + 1);
    }
    iPos = file_name.lastIndexOf(File.separator);
    if (iPos > -1) {
      file_name = file_name.substring(iPos + 1);
    }
    return file_name;
  }
}
