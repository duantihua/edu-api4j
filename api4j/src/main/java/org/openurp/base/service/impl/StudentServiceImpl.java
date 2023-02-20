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
package org.openurp.base.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Person;
import org.openurp.base.service.StudentService;
import org.openurp.code.edu.model.StudentStatus;
import org.openurp.base.std.model.Student;
import org.openurp.base.std.model.StudentState;
import org.openurp.base.std.model.Graduate;

public class StudentServiceImpl extends BaseServiceImpl implements StudentService {

  public Student getStudent(Long studentId) {
    return entityDao.get(Student.class, studentId);
  }

  public Student getStudent(Integer projectId, String code) {
    List<Student> list = entityDao.search(OqlBuilder.from(Student.class, "s")
        .where("s.project.id=:projectId and s.code = :code", projectId, code));
    if (list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public StudentState getJournal(Student student) {
    return getJournal(student, new Date());
  }

  public StudentState getJournal(Student student, Date date) {
    OqlBuilder<StudentState> builder = OqlBuilder.from(StudentState.class, "stdJournal").where(
        "stdJournal.std = :std and stdJournal.beginOn<=:now and (stdJournal.endOn is null or stdJournal.endOn>=:now)",
        student, date).orderBy("stdJournal.id desc");
    List<StudentState> rs = entityDao.search(builder);
    // 防止对尚未进入journal时间区间的学籍进行更改时无法找到相应的journal
    if (rs.isEmpty()) {
      builder = OqlBuilder.from(StudentState.class, "stdJournal")
          .where("stdJournal.std = :std and stdJournal.beginOn > :now", student,
              new java.sql.Date(System.currentTimeMillis()))
          .orderBy("stdJournal.id");
      rs = entityDao.search(builder);
    }
    return rs.isEmpty() ? null : rs.get(0);
  }

  public void endJournal(Student std, java.sql.Date endOn, StudentStatus graduated) {
    StudentStatus inschool = entityDao.get(StudentStatus.class, 1);
    // 找到可以更新的毕业学籍记录
    StudentState graduateState = null;
    for (StudentState ss : std.getStates()) {
      if (ss.getStatus().equals(graduated) && !ss.getBeginOn().equals(std.getBeginOn())
          || ss.getBeginOn().equals(endOn)) {
        graduateState = ss;
        break;
      }
    }

    if (null == graduateState) {
      // 更改现有的状态，主要更改结束日期，有必要更改学籍状态和是否在校
      StudentState state = std.getState();
      java.sql.Date previousDay = java.sql.Date.valueOf(endOn.toLocalDate().plusDays(-1));
      state.setEndOn(previousDay);
      if (state.getStatus().equals(graduated)) {
        state.setStatus(inschool);
        state.setInschool(true);
      }
      entityDao.saveOrUpdate(state);

      StudentState newState = new StudentState();
      newState.setStd(state.getStd());
      newState.setGrade(state.getGrade());
      newState.setDepartment(state.getDepartment());
      newState.setCampus(state.getCampus());
      newState.setMajor(state.getMajor());
      newState.setDirection(state.getDirection());
      newState.setSquad(state.getSquad());
      graduateState = newState;
    }

    graduateState.setInschool(false);
    graduateState.setStatus(graduated);
    graduateState.setBeginOn(endOn);
    graduateState.setEndOn(graduateState.getBeginOn());
    std.setState(graduateState);
    std.setEndOn(endOn);
    entityDao.saveOrUpdate(graduateState, std);
  }

  public boolean isInschool(Student student) {
    return isInschool(student, new Date());
  }

  public boolean isInschool(Student student, Date date) {
    StudentState journal = getJournal(student, date);
    return journal == null ? false : journal.isInschool();
  }

  public boolean isActive(Student student) {
    return isActive(student, new Date());
  }

  public boolean isActive(Student student, Date date) {
    return student.isRegisted() && student.getBeginOn().before(date) && student.getEndOn().after(date);
  }

  public StudentStatus getStdStatus(Student student) {
    return getStdStatus(student, new Date());
  }

  public StudentStatus getStdStatus(Student student, Date date) {
    StudentState journal = getJournal(student, date);
    if (journal != null) return journal.getStatus();
    else return null;
  }

  public Student getStudentByCode(String code) {
    List<Student> list = entityDao.get(Student.class, "code", code);
    if (list.isEmpty()) {
      return null;
    } else {
      return (Student) list.get(0);
    }
  }

  public Student getMajorProjectStudent(Person stdPerson) {
    OqlBuilder<Student> query = OqlBuilder.from(Student.class, "std");
    query.where("std.person = :stdPerson", stdPerson).where("std.project.minor = false");
    return entityDao.uniqueResult(query);
  }

  public Student getMinorProjectStudent(Person stdPerson) {
    OqlBuilder<Student> query = OqlBuilder.from(Student.class, "std");
    query.where("std.person = :stdPerson", stdPerson).where("std.project.minor = true");
    return entityDao.uniqueResult(query);
  }

  // public <T extends StudentBasedEntity> T getStudentInfo(Class<T> stdClass,
  // Student std) {
  // OqlBuilder<T> builder = OqlBuilder.from(stdClass,
  // "stdInfo").where("stdInfo.std=:std", std);
  // List<T> rs = entityDao.search(builder);
  // if (!rs.isEmpty()) return rs.get(0);
  // else return null;
  // }
  //
  // public <T extends StdPersonInfoBean> T getPersonInfo(Class<T> stdClass,
  // Person person) {
  // OqlBuilder<T> builder = OqlBuilder.from(stdClass,
  // "stdInfo").where("stdInfo.person =:person", person);
  // List<T> rs = entityDao.search(builder);
  // if (!rs.isEmpty()) return rs.get(0);
  // else return null;
  // }

  // public void createUser(Student std) {
  // User user = userService.get(std.getUser().getCode());
  // if (user != null) { return; }
  // List<Contact> stdContacts = entityDao.get(Contact.class, "person",
  // std.getPerson());
  // User creator = userService.get(Securities.user());
  // user = new UserBean();
  // user.setName(std.getUser().getCode());
  // user.setFullname(std.getName());
  // user.setEnabled(true);
  // user.setDefaultCategory(stdCategory);
  // user.setCategories(stdCategories);
  // user.setBeginOn(new Date());
  // String mail = "";
  // if (!stdContacts.isEmpty()) {
  // mail = stdContacts.get(0).getMail();
  // }
  // user.setMail(Strings.isBlank(mail) ? " " : mail);
  // String password = std.getPerson() == null ? null :
  // std.getPerson().getIdcard();
  // if (Strings.isNotBlank(password)) {
  // // 将证件号中非字母非数字的统统去掉，并且所有字母都是大写
  // password = Strings.upperCase(password).replaceAll("[^A-Z0-9]", "");
  // if (password.length() > 6) {
  // password = password.substring(password.length() - 6);
  // }
  // }
  // if (Strings.isNotBlank(password)) {
  // user.setPassword(EncryptUtil.encode(password));
  // } else if (!user.isPersisted()) {
  // password = User.DEFAULT_PASSWORD;
  // user.setPassword(EncryptUtil.encode(password));
  // }
  // userService.createUser(creator, user);
  // }

  // private Category stdCategory = new CategoryBean();
  // {
  // stdCategory.setId(1l);
  // }
  //
  // private Set<Category> stdCategories = new HashSet<Category>();
  // {
  // stdCategories.add(stdCategory);
  // }

  // public void setUserService(UserService userService) {
  // this.userService = userService;
  // }

  public Graduate getGraduate(Student std) {
    List<Graduate> graduations = entityDao.get(Graduate.class, "std", std);
    return CollectionUtils.isEmpty(graduations) ? null : graduations.get(0);
  }
}
