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
package org.openurp.web;

import java.lang.reflect.Type;

import org.openurp.base.edu.model.Classroom;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ClassroomBeanGsonAdapter implements JsonSerializer<Classroom>, JsonDeserializer<Classroom> {

  public JsonElement serialize(Classroom src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    json.addProperty("id", src.getId());
    json.addProperty("code", src.getCode());
    json.addProperty("name", src.getName());
    json.addProperty("capacity", src.getCourseCapacity());

    // 校区
    if (src.getCampus() != null) {
      JsonObject campusJson = new JsonObject();
      campusJson.addProperty("id", src.getCampus().getId());
      campusJson.addProperty("name", src.getCampus().getName());
      campusJson.addProperty("code", src.getCampus().getCode());
      json.add("campus", campusJson);
    } else {
      json.add("campus", JsonNull.INSTANCE);
    }

    // 教学楼
    if (src.getBuilding() != null) {
      JsonObject buildingJson = new JsonObject();
      buildingJson.addProperty("id", src.getBuilding().getId());
      buildingJson.addProperty("name", src.getBuilding().getName());
      buildingJson.addProperty("code", src.getBuilding().getCode());
      json.add("building", buildingJson);
    } else {
      json.add("building", JsonNull.INSTANCE);
    }

    // 类型
    if (src.getRoomType() != null) {
      JsonObject typeJson = new JsonObject();
      typeJson.addProperty("id", src.getRoomType().getId());
      typeJson.addProperty("name", src.getRoomType().getName());
      typeJson.addProperty("code", src.getRoomType().getCode());
      json.add("classroomType", typeJson);
    } else {
      json.add("classroomType", JsonNull.INSTANCE);
    }

    return json;
  }

  public Classroom deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    // TODO Auto-generated method stub
    return null;
  }

}
