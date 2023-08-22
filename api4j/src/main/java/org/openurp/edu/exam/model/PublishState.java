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
package org.openurp.edu.exam.model;

public enum PublishState {
  None("未发布", false, false), TimeOnly("仅发布时间", true, false), TimeAndRoom("发布时间地点", true, true);

  private PublishState(String name, boolean timePublished, boolean roomPublished) {
    this.name = name;
    this.timePublished = timePublished;
    this.roomPublished = roomPublished;
  }

  private final String name;

  /** 是否发布时间 */
  private final boolean timePublished;

  /** 是否发布地点 */
  private final boolean roomPublished;

  public String getName() {
    return name;
  }

  public boolean isTimePublished() {
    return timePublished;
  }

  public boolean isRoomPublished() {
    return roomPublished;
  }
}
