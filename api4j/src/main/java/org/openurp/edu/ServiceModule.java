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
package org.openurp.edu;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.commons.inject.bind.BeanConfig.ReferenceValue;
import org.beangle.commons.lang.tuple.Pair;
import org.openurp.base.service.impl.*;
import org.openurp.code.service.impl.CodeServiceImpl;
import org.openurp.edu.clazz.dao.hibernate.internal.ClazzCRNGeneratorImpl;
import org.openurp.edu.clazz.dao.hibernate.internal.ClazzDaoHibernate;
import org.openurp.edu.clazz.dao.hibernate.internal.CoursePrefixSeqNoGeneratorImpl;
import org.openurp.edu.clazz.service.ClazzFilterStrategy;
import org.openurp.edu.clazz.service.ClazzLogHelper;
import org.openurp.edu.clazz.service.internal.ClazzServiceImpl;
import org.openurp.edu.clazz.service.internal.CourseLimitExtractorServiceImpl;
import org.openurp.edu.clazz.service.internal.CourseLimitServiceImpl;
import org.openurp.edu.clazz.service.internal.DefaultTeachClassNameStrategy;
import org.openurp.edu.clazz.service.internal.filterStrategy.*;
import org.openurp.edu.program.plan.service.impl.AlternativeCourseServiceImpl;
import org.openurp.edu.program.plan.service.impl.CoursePlanProviderImpl;
import org.openurp.edu.service.impl.TeachResourceServiceImpl;
import org.openurp.edu.student.info.service.impl.StudentInfoServiceImpl;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

public class ServiceModule extends AbstractBindModule {
  @Override
  protected void doBinding() {
    bind("codeService", CodeServiceImpl.class);
    bind("projectPropertyService", ProjectPropertyServiceImpl.class);
    bind("semesterService", SemesterServiceImpl.class);
    bind("teacherService", TeacherServiceImpl.class);
    bind("baseInfoService", BaseInfoServiceImpl.class);
    bind("studentService", StudentServiceImpl.class);
    bind("departmentService", DepartmentServiceImpl.class);
    bind("classroomService", ClassroomServiceImpl.class);
    bind("squadService", SquadServiceImpl.class);

    bind("timeSettingService", TimeSettingServiceImpl.class);

    bind("courseService", CourseServiceImpl.class);
    bind("clazzService", ClazzServiceImpl.class);
    bind("alternativeCourseService", AlternativeCourseServiceImpl.class);

    bind("teachResourceService", TeachResourceServiceImpl.class);

    bind("studentInfoService", StudentInfoServiceImpl.class);

    bind("coursePlanProvider", CoursePlanProviderImpl.class);
    bind("courseLimitService", CourseLimitServiceImpl.class);
    bind("courseLimitExtractorService", CourseLimitExtractorServiceImpl.class);
    bind("teachclassNameStrategy", DefaultTeachClassNameStrategy.class);
    bind("clazzLogHelper", ClazzLogHelper.class);

    bind("clazzDao", TransactionProxyFactoryBean.class).proxy("target", bean(ClazzDaoHibernate.class))
        .parent("baseTransactionProxy").property("transactionAttributes", props("*=PROPAGATION_REQUIRED"));

    bind("clazzSeqNoGeneratorImpl", TransactionProxyFactoryBean.class)
        .proxy("target", ClazzCRNGeneratorImpl.class).parent("baseTransactionProxy").primary();

    bind("coursePrefixSeqNoGeneratorImpl", TransactionProxyFactoryBean.class)
        .proxy("target", CoursePrefixSeqNoGeneratorImpl.class).parent("baseTransactionProxy");

    // 任务过滤策略
    bind("clazzFilterBySquadStrategy", ClazzFilterBySquadStrategy.class);
    bind("clazzFilterByCourseTypeStrategy", ClazzFilterByCourseTypeStrategy.class);
    bind("clazzFilterByDirectionStrategy", ClazzFilterByDirectionStrategy.class);
    bind("clazzFilterByMajorStrategy", ClazzFilterByMajorStrategy.class);
    bind("clazzFilterByStdStrategy", ClazzFilterByStdStrategy.class);
    bind("clazzFilterByStdTypeStrategy", ClazzFilterByStdTypeStrategy.class);
    bind("clazzFilterByTeachDepartStrategy", ClazzFilterByTeachDepartStrategy.class);
    bind("clazzFilterByTeachCLassDepartStrategy", ClazzFilterByTeachCLassDepartStrategy.class);
    bind("clazzFilterByTeacherStrategy", ClazzFilterByTeacherStrategy.class);

    bind("clazzFilterStrategyFactory", DefaultClazzFilterStrategyFactory.class).property(
        "clazzFilterStrategies",
        map(new Pair<String, ReferenceValue>(ClazzFilterStrategy.SQUAD,
            ref("clazzFilterBySquadStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.COURSE_TYPE,
                ref("clazzFilterByCourseTypeStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.DIRECTION,
                ref("clazzFilterByDirectionStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.MAJOR, ref("clazzFilterByMajorStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.STD, ref("clazzFilterByStdStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.STD_TYPE,
                ref("clazzFilterByStdTypeStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.TEACH_DEPART,
                ref("clazzFilterByTeachDepartStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.TEACHCLASS_DEPART,
                ref("clazzFilterByTeachCLassDepartStrategy")),
            new Pair<String, ReferenceValue>(ClazzFilterStrategy.TEACHER,
                ref("clazzFilterByTeacherStrategy"))));
  }
}
