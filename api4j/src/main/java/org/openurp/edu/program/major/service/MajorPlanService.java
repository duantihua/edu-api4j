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
package org.openurp.edu.program.major.service;

import org.openurp.base.edu.code.CourseType;
import org.openurp.base.edu.model.Course;
import org.openurp.base.std.model.Squad;
import org.openurp.edu.program.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 培养计划服务接口
 */
public interface MajorPlanService {

  List<MajorPlanCourse> getPlanCourses(MajorPlan plan);

  /**
   * 根据行政班的grade,stdType,department,major,direction,level属性来精确匹配找到一个专业培养计划
   *
   * @param clazz
   * @return
   */
  MajorPlan getMajorPlanByAdminClass(Squad clazz);

  /**
   * 保存新的培养计划
   *
   * @param plan
   * @return
   */
  void saveOrUpdateMajorPlan(MajorPlan plan);

  /**
   * 删除培养计划
   */
  void removeMajorPlan(MajorPlan plan);

  /**
   * 以一个培养计划为模板，生成这个培养计划的copy<br>
   * copy和原计划在一些属性上有区别，这个区别在targetPlan中体现。<br>
   * 但是原计划和copy计划中的课程设置是完全一样的
   *
   * @param sourcePlan 必须是一个persisitent，持久态对象
   * @param genParameter genParameter提供了欲生成的培养计划的模板
   * @return
   */
  MajorPlan genMajorPlan(MajorPlan sourcePlan, MajorPlanGenParameter genParameter)throws Exception;

  /**
   * 给定一批培养计划，以这些培养计划作为模板，生成这批培养计划的copy<br>
   * copy和原计划的区别在于grade, beginOn, endOn不同，他们的课程设置都是一样的<br>
   *
   * @param sourcePlans
   * @param partialParams 因为是批量生成计划，所以页面只传部分的参数：grade, beginOn, endOn
   * @return
   */
  List<MajorPlan> genMajorPlans(Collection<MajorPlan> sourcePlans, MajorPlanGenParameter partialParams)throws Exception;

  /**
   * 查找没有在某个培养计划使用的课程类别
   *
   * @param plan
   * @return
   */
  Set<String> getUnusedCourseTypeNames(MajorPlan plan);

  /**
   * 统计培养计划的总学分
   *
   * @param planId
   * @return
   */
  float statPlanCredits(Long planId);

  /**
   * 统计总学分
   */
  float statPlanCredits(MajorPlan plan);

  /**
   * 检测某个专业培养计划是否有这个课程
   *
   * @param plan
   * @param course
   * @return
   */
  boolean hasCourse(MajorCourseGroup cgroup, Course course);

  boolean hasCourse(MajorCourseGroup cgroup, Course course, PlanCourse planCourse);

}
