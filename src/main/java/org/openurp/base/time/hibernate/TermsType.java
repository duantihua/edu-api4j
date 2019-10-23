/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.base.time.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.openurp.base.time.Terms;

public class TermsType implements UserType {

  /** @Override */
  public int[] sqlTypes() {
    return new int[] { Types.INTEGER };
  }

  /** @Override */
  public Class returnedClass() {
    return Terms.class;
  }

  /** @Override */
  public boolean equals(Object x, Object y) {
    if (x instanceof Terms && y instanceof Terms) {
      return ((Terms) x).value == ((Terms) y).value;
    } else {
      return false;
    }
  }

  /** @Override */
  public int hashCode(Object x) {
    return x.hashCode();
  }

  /** @Override */
  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
      throws HibernateException, SQLException {
    short value = resultSet.getShort(names[0]);
    if (resultSet.wasNull()) return null;
    else return new Terms(value);
  }

  /** @Override */
  public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      statement.setNull(index, Types.INTEGER);
    } else {
      statement.setInt(index, ((Terms) value).value);
    }
  }

  /** @Override */
  public Object deepCopy(Object value) {
    return value;
  }

  /** @Override */
  public boolean isMutable() {
    return false;
  }

  /** @Override */
  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  /** @Override */
  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  /** @Override */
  public Object replace(Object original, Object target, Object owner) {
    return original;
  }
}
