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
package org.openurp.edu.grade.course.service.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.comparators.PropertyComparator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.openurp.base.edu.model.Course;
import org.openurp.base.edu.model.Semester;
import org.openurp.base.std.model.Squad;
import org.openurp.base.std.model.Student;
import org.openurp.edu.grade.course.model.CourseGrade;

/**
 * 多名学生的成绩打印模型<br>
 * 通常以一个班级为单位
 */
public class MultiStdGrade {

  private Semester semester;

  private Squad adminClass;

  private List<Course> courses;
  /**
   * @see StdGrade
   */
  private List<StdGrade> stdGrades;// [stdGrade列表]

  // 每个学生除了共同课程之外的其他课程[std.id.toString,courseGrades]
  private Map<String, List<CourseGrade>> extraGradeMap = CollectUtils.newHashMap();

  private Integer maxDisplay;// 最大显示列

  private Float ratio;

  public MultiStdGrade(Semester semester, Map<Student, List<CourseGrade>> grades, Float ratio) {
    if (grades.isEmpty()) { return; }
    this.semester = semester;
    this.ratio = ratio;
    Map<Long, StdGrade> gradesMap = CollectUtils.newHashMap();
    Map<Course, CourseStdNum> courseStdNumMap = CollectUtils.newHashMap();
    // 组装成绩,把每一个成绩放入对应学生的stdGrade中,并记录每一个成绩中课程对应的学生人数.
    for (Map.Entry<Student, List<CourseGrade>> entry : grades.entrySet()) {
      List<CourseGrade> personGrades=entry.getValue();
      if(personGrades.isEmpty()) continue;
      StdGrade stdGrade = new StdGrade(entry.getKey(), personGrades, null, null);
      gradesMap.put(entry.getKey().getId(), stdGrade);
      for (CourseGrade grade : entry.getValue()) {
        CourseStdNum courseStdNum = (CourseStdNum) courseStdNumMap.get(grade.getCourse());
        if (null == courseStdNum) {
          courseStdNumMap.put(grade.getCourse(), new CourseStdNum(grade.getCourse(), new Integer(1)));
        } else {
          courseStdNum.setCount(new Integer(courseStdNum.getCount().intValue() + 1));
        }
      }
    }
    this.stdGrades = new ArrayList<StdGrade>(gradesMap.values());
    // 按照课程人数倒序排列,找到符合人数底线的公共课程
    List<CourseStdNum> courseStdNums = new ArrayList<CourseStdNum>(courseStdNumMap.values());
    Collections.sort(courseStdNums);
    int maxStdCount = 0;
    if (CollectUtils.isNotEmpty(courseStdNums)) {
      maxStdCount = ((CourseStdNum) (courseStdNums.get(0))).getCount().intValue();
    }
    courses = CollectUtils.newArrayList();
    for (int i = 0; i < courseStdNums.size(); i++) {
      CourseStdNum rank = (CourseStdNum) courseStdNums.get(i);
      if (new Float(rank.getCount().intValue()).floatValue() / maxStdCount > ratio.floatValue()) {
        courses.add(rank.getCourse());
      }
    }
    int maxExtra = 0;
    // 记录每个学生超出公共课程的成绩,并找出最大的显示多余列
    for (Iterator<StdGrade> iter = stdGrades.iterator(); iter.hasNext();) {
      StdGrade stdGrade = iter.next();
      int myExtra = 0;
      List<CourseGrade> extraGrades = CollectUtils.newArrayList();
      Set<Course> commonCourseSet = new HashSet<Course>(courses);
      for (Iterator<CourseGrade> iterator = stdGrade.getGrades().iterator(); iterator.hasNext();) {
        CourseGrade courseGrade = iterator.next();
        if (!commonCourseSet.contains(courseGrade.getCourse())) {
          extraGrades.add(courseGrade);
          myExtra++;
        }
      }
      if (myExtra > maxExtra) {
        maxExtra = myExtra;
      }
      if (!extraGrades.isEmpty()) {
        extraGradeMap.put(stdGrade.getStd().getId().toString(), extraGrades);
      }
    }
    maxDisplay = new Integer(courses.size() + maxExtra);
  }

  public Squad getSquad() {
    return adminClass;
  }

  public void setSquad(Squad adminClass) {
    this.adminClass = adminClass;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public List<StdGrade> getStdGrades() {
    return stdGrades;
  }

  public void setStdGrades(List<StdGrade> stdGrades) {
    this.stdGrades = stdGrades;
  }

  public List getCourses() {
    return courses;
  }

  public void setCourses(List courseRankList) {
    this.courses = courseRankList;
  }

  public Integer getMaxDisplay() {
    return maxDisplay;
  }

  public void setMaxDisplay(Integer maxCourse) {
    this.maxDisplay = maxCourse;
  }

  public Float getRatio() {
    return ratio;
  }

  public void setRatio(Float ratio) {
    this.ratio = ratio;
  }

  public Map getExtraGradeMap() {
    return extraGradeMap;
  }

  public void setExtraGradeMap(Map extraGradeMap) {
    this.extraGradeMap = extraGradeMap;
  }

  public void sortStdGrades(String cmpWhat, boolean isAsc) {
    if (null != stdGrades) {
      PropertyComparator cmp = new PropertyComparator(cmpWhat, isAsc);
      Collections.sort(stdGrades, cmp);
    }
  }

  /**
   * 返回超出显示课程数量之外的课程数
   *
   * @return
   */
  public int getExtraCourseNum() {
    return getMaxDisplay().intValue() - getCourses().size();
  }
}

/**
 * 课程对应的学生人数
 */
class CourseStdNum implements Comparable {

  Course course;

  Integer count;

  public CourseStdNum(Course course2, Integer count) {
    this.course = course2;
    this.count = count;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  /**
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(Object object) {
    CourseStdNum myClass = (CourseStdNum) object;
    return Objects.compareBuilder().add(myClass.count, this.count).toComparison();
  }
}
