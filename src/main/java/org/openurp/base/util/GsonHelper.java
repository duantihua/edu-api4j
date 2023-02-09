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
package org.openurp.base.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonHelper {

  public static boolean isNull(JsonElement element) {
    return element == null || element.isJsonNull();
  }

  public static boolean isNotNull(JsonElement element) {
    return !isNull(element);
  }

  public static Long getLong(JsonObject json, String property) {
    return getAsLong(json.get(property));
  }

  public static long getPrimitiveLong(JsonObject json, String property) {
    return getAsPrimitiveLong(json.get(property));
  }

  public static Integer getInteger(JsonObject json, String property) {
    return getAsInteger(json.get(property));
  }

  public static int getPrimitiveInteger(JsonObject json, String property) {
    return getAsPrimitiveInt(json.get(property));
  }

  public static Double getDouble(JsonObject json, String property) {
    return getAsDouble(json.get(property));
  }

  public static double getPrimitiveDouble(JsonObject json, String property) {
    return getAsPrimitiveDouble(json.get(property));
  }

  public static Float getFloat(JsonObject json, String property) {
    return getAsFloat(json.get(property));
  }

  public static float getPrimitiveFloat(JsonObject json, String property) {
    return getAsPrimitiveFloat(json.get(property));
  }

  public static Boolean getBoolean(JsonObject json, String property) {
    return getAsBoolean(json.get(property));
  }

  public static String getString(JsonObject json, String property) {
    return getAsString(json.get(property));
  }

  public static String getAsString(JsonElement element) {
    return isNull(element) ? null : element.getAsString();
  }

  public static Long getAsLong(JsonElement element) {
    return isNull(element) ? null : element.getAsLong();
  }

  public static long getAsPrimitiveLong(JsonElement element) {
    Long r = getAsLong(element);
    return r == null ? 0l : r;
  }

  public static Integer getAsInteger(JsonElement element) {
    return isNull(element) ? null : element.getAsInt();
  }

  public static int getAsPrimitiveInt(JsonElement element) {
    Integer r = getAsInteger(element);
    return r == null ? 0 : r;
  }

  public static Boolean getAsBoolean(JsonElement element) {
    return isNull(element) ? null : element.getAsBoolean();
  }

  public static boolean getAsPrimitiveBool(JsonElement element) {
    Boolean r = getAsBoolean(element);
    return r == null ? false : r.booleanValue();
  }

  public static Double getAsDouble(JsonElement element) {
    return isNull(element) ? null : element.getAsDouble();
  }

  public static double getAsPrimitiveDouble(JsonElement element) {
    Double r = getAsDouble(element);
    return r == null ? 0d : r;
  }

  public static Float getAsFloat(JsonElement element) {
    return isNull(element) ? null : element.getAsFloat();
  }

  public static float getAsPrimitiveFloat(JsonElement element) {
    Float r = getAsFloat(element);
    return r == null ? 0f : r;
  }

}
