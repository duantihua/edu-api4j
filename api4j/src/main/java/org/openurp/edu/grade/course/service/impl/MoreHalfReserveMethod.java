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
package org.openurp.edu.grade.course.service.impl;

import org.openurp.edu.grade.course.service.NumPrecisionReserveMethod;

/**
 * 四舍五入进位法
 */
public class MoreHalfReserveMethod implements NumPrecisionReserveMethod {

  public float reserve(float num, int precision) {
    int mutilply = (int) Math.pow(10, precision + 1);
    num *= mutilply;
    if (num % 10 >= 5) num += 10;
    num -= num % 10;
    return num / mutilply;
  }

  public double reserve(double num, int precision) {
    int mutilply = (int) Math.pow(10, precision + 1);
    num *= mutilply;
    if (num % 10 >= 5) num += 10;
    num -= num % 10;
    return num / mutilply;
  }

}
