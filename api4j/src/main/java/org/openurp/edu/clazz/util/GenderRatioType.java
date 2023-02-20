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
package org.openurp.edu.clazz.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.openurp.edu.clazz.util.GenderRatio;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class GenderRatioType implements UserType {

  public int[] sqlTypes() {
    return new int[] { Types.SMALLINT };
  }

  public Class<?> returnedClass() {
    return GenderRatio.class;
  }

  public boolean equals(Object x, Object y) {
    if (x instanceof GenderRatio && y instanceof GenderRatio) {
      try {
        return ((GenderRatio) x).value == ((GenderRatio) y).value;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  public int hashCode(Object x) {
    return x.hashCode();
  }

  public Object deepCopy(Object value) {
    return value;
  }

  public boolean isMutable() {
    return false;
  }

  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  public Object replace(Object original, Object target, Object owner) {
    return original;
  }

  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
      throws HibernateException, SQLException {
    Short value = resultSet.getShort(names[0]);
    if (resultSet.wasNull()) return null;
    else return new GenderRatio(value);
  }

  public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      statement.setNull(index, Types.SMALLINT);
    } else {
      statement.setShort(index, ((GenderRatio) value).value);
    }
  }
}
