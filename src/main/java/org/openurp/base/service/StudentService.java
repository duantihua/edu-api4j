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
package org.openurp.base.service;

import java.util.Date;

import org.beangle.commons.event.Event;
import org.openurp.base.model.Person;
import org.openurp.code.edu.model.StudentStatus;
import org.openurp.base.std.model.Student;
import org.openurp.base.std.model.StudentState;
import org.openurp.base.std.model.Graduate;

/**
 * 学生学籍管理的服务类
 */
public interface StudentService {

  /**
   * 获取一个学生的学籍信息
   *
   * @param studentId
   * @return
   */
  public Student getStudent(Long studentId);

  /**
   * 获取一个学生的学籍信息
   *
   * @param code
   * @return
   */
  public Student getStudent(Integer projectId, String code);

  public boolean isInschool(Student student, Date date);

  public boolean isInschool(Student student);

  public StudentStatus getStdStatus(Student student);

  public StudentStatus getStdStatus(Student student, Date date);

  public StudentState getJournal(Student student);

  public StudentState getJournal(Student student, Date date);

  public boolean isActive(Student student);

  public boolean isActive(Student student, Date date);

  public void publish(Event e);

  /**
   * 获取这个学生一专学籍
   *
   * @param stdPerson
   * @return
   */
  public Student getMajorProjectStudent(Person stdPerson);

  /**
   * 获取这个学生二专学籍
   *
   * @param std
   * @return
   */
  public Student getMinorProjectStudent(Person stdPerson);

  public Graduate getGraduate(Student std);

  void endJournal(Student std, java.sql.Date endOn, StudentStatus graduated);
}
