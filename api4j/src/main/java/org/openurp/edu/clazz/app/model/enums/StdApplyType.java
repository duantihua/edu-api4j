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
package org.openurp.edu.clazz.app.model.enums;

public enum StdApplyType {
  APPLY_TYPE_ABSENCE, APPLY_TYPE_GIVEUP, APPLY_TYPE_OPEN_CLASS, APPLY_TYPE_SUBSTITUTION;

  public String getName() {
    switch (this) {
    case APPLY_TYPE_ABSENCE:
      return "申请免听";
    case APPLY_TYPE_GIVEUP:
      return "本学期放弃";
    case APPLY_TYPE_OPEN_CLASS:
      return "申请开班";
    case APPLY_TYPE_SUBSTITUTION:
      return "申请替代";
    default:
      return "";
    }
  }
}
