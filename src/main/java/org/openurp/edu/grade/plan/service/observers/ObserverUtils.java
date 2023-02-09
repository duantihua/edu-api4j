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
package org.openurp.edu.grade.plan.service.observers;

import org.beangle.commons.lang.Strings;
import org.beangle.ems.rule.model.SimpleContext;
import org.openurp.web.util.OutputMessage;
import org.openurp.web.util.OutputWebObserver;

/**
 *
 */
public class ObserverUtils {

  private static String delimiter = Strings.repeat("-", 100);

  /**
   * 在前台输出信息
   *
   * @param context
   *          参数传输容器
   * @param level
   *          级别：1 - 信息（good），2 - 警告（warnning），3 - 错误（error）
   * @param msg
   *          输出信息
   * @param increaceProcess
   *          是否增长
   */
  public static void outputMessage(SimpleContext context, int level, String msg, boolean increaceProcess) {
    OutputWebObserver weboutput = (OutputWebObserver) context.getParams().get("_weboutput");
    outputMessage(weboutput, level, msg, increaceProcess);
  }

  public static void outputMessage(OutputWebObserver weboutput, int level, String msg,
      boolean increaceProcess) {
    if (weboutput != null) {
      weboutput.outputNotify(level, new OutputMessage(msg, ""), increaceProcess);
    }
  }

  public static void delimiter(SimpleContext context) {
    OutputWebObserver weboutput = (OutputWebObserver) context.getParams().get("_weboutput");
    delimiter(weboutput);
  }

  public static void delimiter(OutputWebObserver weboutput) {
    outputMessage(weboutput, OutputWebObserver.warnning, delimiter, false);
  }

  public static void notifyStart(OutputWebObserver weboutput, String summary, int count, String[] msgs) {
    if (weboutput != null) {
      weboutput.notifyStart(summary, count, msgs);
    }
  }
}
