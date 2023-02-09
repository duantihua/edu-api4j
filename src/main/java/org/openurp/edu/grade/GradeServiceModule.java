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
package org.openurp.edu.grade;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.grade.app.service.impl.GradeInputSwithServiceImpl;
import org.openurp.edu.grade.app.service.impl.TranscriptTemplateServiceImpl;
import org.openurp.edu.grade.course.service.CourseGradePublishStack;
import org.openurp.edu.grade.course.service.GradingModeHelper;
import org.openurp.edu.grade.course.service.impl.BestGpaStatService;
import org.openurp.edu.grade.course.service.impl.BestGradeFilter;
import org.openurp.edu.grade.course.service.impl.BestOriginGradeFilter;
import org.openurp.edu.grade.course.service.impl.DefaultCourseGradeCalculator;
import org.openurp.edu.grade.course.service.impl.DefaultGpaPolicy;
import org.openurp.edu.grade.course.service.impl.DefaultGpaService;
import org.openurp.edu.grade.course.service.impl.DefaultGradeTypePolicy;
import org.openurp.edu.grade.course.service.impl.DefaultGradingModeStrategy;
import org.openurp.edu.grade.course.service.impl.ExamTakerGeneratePublishListener;
import org.openurp.edu.grade.course.service.impl.MakeupByExamStrategy;
import org.openurp.edu.grade.course.service.impl.MakeupGradeFilter;
import org.openurp.edu.grade.course.service.impl.More01ReserveMethod;
import org.openurp.edu.grade.course.service.impl.MoreHalfReserveMethod;
import org.openurp.edu.grade.course.service.impl.PassedGradeFilter;
import org.openurp.edu.grade.course.service.impl.RecalcGpPublishListener;
import org.openurp.edu.grade.course.service.impl.ScriptGradeFilter;
import org.openurp.edu.grade.course.service.impl.SpringGradeFilterRegistry;
import org.openurp.edu.grade.course.service.impl.StdGradeServiceImpl;
import org.openurp.edu.grade.course.service.internal.BestGradeCourseGradeProviderImpl;
import org.openurp.edu.grade.course.service.internal.CourseGradeProviderImpl;
import org.openurp.edu.grade.course.service.internal.CourseGradeServiceImpl;
import org.openurp.edu.grade.course.service.internal.GradeCourseTypeProviderImpl;
import org.openurp.edu.grade.course.service.internal.GradeRateServiceImpl;
import org.openurp.edu.grade.setting.service.impl.CourseGradeSettingsImpl;
import org.openurp.edu.grade.transcript.service.impl.SpringTranscriptDataProviderRegistry;
import org.openurp.edu.grade.transcript.service.impl.TranscriptGpaProvider;
import org.openurp.edu.grade.transcript.service.impl.TranscriptPlanCourseProvider;
import org.openurp.edu.grade.transcript.service.impl.TranscriptCertificateGradeProvider;
import org.openurp.edu.grade.transcript.service.impl.TranscriptPublishedGradeProvider;
import org.openurp.edu.grade.transcript.service.impl.TranscriptStdGraduateProvider;
import org.openurp.edu.grade.plan.service.internal.AuditSettingServiceImpl;
import org.openurp.edu.grade.plan.service.internal.PlanAuditServiceImpl;
import org.openurp.edu.grade.plan.service.listeners.PlanAuditAlternativeCourseListener;
import org.openurp.edu.grade.plan.service.listeners.PlanAuditCommonElectiveListener;
import org.openurp.edu.grade.plan.service.listeners.PlanAuditCourseTakerListener;
import org.openurp.edu.grade.plan.service.listeners.PlanAuditCourseTypeMatchListener;
import org.openurp.edu.grade.plan.service.observers.PlanAuditPersistObserver;

public class GradeServiceModule extends AbstractBindModule {
  @Override
  protected void doBinding() {
    bind("bestGradeCourseGradeProvider", BestGradeCourseGradeProviderImpl.class);
    bind(CourseGradeSettingsImpl.class);

    bind("gradeRateService", GradeRateServiceImpl.class);
    bind("bestGradeFilter", BestGradeFilter.class);
    bind("gpaPolicy", DefaultGpaPolicy.class);
    bind("bestOriginGradeFilter", BestOriginGradeFilter.class);
    bind("passedGradeFilter", PassedGradeFilter.class);
    bind("gradeFilterRegistry", SpringGradeFilterRegistry.class);

    bind("courseGradeService", CourseGradeServiceImpl.class);
    bind("gradeInputSwithService", GradeInputSwithServiceImpl.class);
    bind(TranscriptTemplateServiceImpl.class);
    bind("scriptGradeFilter", ScriptGradeFilter.class);
    bind("courseGradeProvider", CourseGradeProviderImpl.class);
    bind("courseGradeCalculator", DefaultCourseGradeCalculator.class);
    bind("gpaService", DefaultGpaService.class);
    bind("bestGpaStatService", BestGpaStatService.class);
    bind("gradeCourseTypeProvider", GradeCourseTypeProviderImpl.class);

    bind("makeupStdStrategy", MakeupByExamStrategy.class);
    bind("gradingModeHelper", GradingModeHelper.class);
    bind("gradingModeStrategy", DefaultGradingModeStrategy.class);
    bind("stdGradeService", StdGradeServiceImpl.class);
    bind("makeupGradeFilter", MakeupGradeFilter.class);
    bind("recalcGpPublishListener", RecalcGpPublishListener.class);
    bind("examTakerGeneratePublishListener", ExamTakerGeneratePublishListener.class);
    bind("courseGradePublishStack", CourseGradePublishStack.class).property("listeners",
        list(ref("recalcGpPublishListener"), ref("examTakerGeneratePublishListener")));
    bind(DefaultGradeTypePolicy.class);
    bind(MoreHalfReserveMethod.class, More01ReserveMethod.class).shortName();

    bind(TranscriptPlanCourseProvider.class, TranscriptGpaProvider.class,
        TranscriptPublishedGradeProvider.class, TranscriptStdGraduateProvider.class,
        SpringTranscriptDataProviderRegistry.class, TranscriptCertificateGradeProvider.class)
            .shortName();

    // 这些监听器再配置文件中顺序应该按照以下顺序
    bind("planAuditAlternativeCourseListener", PlanAuditAlternativeCourseListener.class);
    bind("planAuditCourseTakerListener", PlanAuditCourseTakerListener.class);
    bind("planAuditCourseTypeMatchListener", PlanAuditCourseTypeMatchListener.class);
    bind("planAuditCommonElectiveListener", PlanAuditCommonElectiveListener.class);

    bind("planAuditPersistObserver", PlanAuditPersistObserver.class);

    bind("planAuditService", PlanAuditServiceImpl.class).property("listeners",
        list(ref("planAuditAlternativeCourseListener"),
            ref("planAuditCourseTakerListener"), ref("planAuditCourseTypeMatchListener"),
            ref("planAuditCommonElectiveListener")));

    bind("auditSettingService", AuditSettingServiceImpl.class);
  }
}
