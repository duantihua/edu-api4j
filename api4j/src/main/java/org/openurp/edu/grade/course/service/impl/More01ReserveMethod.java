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

public class More01ReserveMethod implements NumPrecisionReserveMethod {

  public float reserve(final float num, final int precision) {
    int mutilply = (int) Math.pow(10, precision + 1);// 89.14 ,10
    float res = num * mutilply;// 891.4
    if (res % 10 >= 1) res += 10;// 900.4
    res -= res % 10;// 900
    return res / mutilply;

  }

  public double reserve(final double num, final int precision) {
    int mutilply = (int) Math.pow(10, precision + 1);
    double res = num * mutilply;
    if (res % 10 >= 1) res += 10;
    res -= res % 10;
    return res / mutilply;
  }

}
