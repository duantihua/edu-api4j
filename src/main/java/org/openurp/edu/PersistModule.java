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

import org.beangle.commons.entity.orm.AbstractPersistModule;
import org.openurp.base.edu.code.*;
import org.openurp.base.edu.model.MajorDiscipline;
import org.openurp.base.edu.model.ProjectProperty;
import org.openurp.base.edu.model.SchoolLength;
import org.openurp.base.std.code.StdLabel;
import org.openurp.base.std.code.StdLabelType;
import org.openurp.base.std.code.StdType;
import org.openurp.code.asset.model.BuildingType;
import org.openurp.code.asset.model.RoomType;
import org.openurp.code.edu.model.*;
import org.openurp.code.geo.model.Country;
import org.openurp.code.geo.model.Division;
import org.openurp.code.geo.model.RailwayStation;
import org.openurp.code.hr.model.UserCategory;
import org.openurp.code.hr.model.WorkStatus;
import org.openurp.code.job.model.ProfessionalGrade;
import org.openurp.code.job.model.ProfessionalTitle;
import org.openurp.code.job.model.TutorType;
import org.openurp.code.person.model.*;
import org.openurp.code.sin.model.*;
import org.openurp.code.std.model.*;
import org.openurp.edu.clazz.config.ScheduleSetting;
import org.openurp.edu.extern.code.model.CertificateCategory;
import org.openurp.edu.extern.code.model.CertificateSubject;
import org.openurp.std.award.Punishment;
import org.openurp.std.fee.config.TuitionConfig;
import org.openurp.std.graduation.model.DegreeResult;

public class PersistModule extends AbstractPersistModule {

  @Override
  protected void doConfig() {
    add(RoomType.class, BuildingType.class, Institution.class, AdmissionType.class, EducationMode.class,
        EnrollMode.class, StdAlterReason.class, StdAlterType.class, HskLevel.class, DisciplineCatalog.class,
        Discipline.class, ClassroomType.class, StudentStatus.class, UeeSubjectType.class, FeeOrigin.class,
        ActivityType.class, AcademicLevel.class, Press.class, PressGrade.class, PublicationGrade.class,
        BookCategory.class, Publication.class, Language.class, DisciplineCategory.class, Degree.class,
        StudyType.class, EducationResult.class, Gender.class, MaritalStatus.class, Nation.class,
        PoliticalStatus.class, IdType.class, FamilyRelationship.class, PassportType.class,
        HouseholdType.class, VisaType.class, Country.class, Division.class, RailwayStation.class,
        ProfessionalTitle.class, ProfessionalGrade.class, TutorType.class, WorkStatus.class,
        EduCategory.class, EducationType.class).cache("openurp.base");

    add(UserCategory.class, DayPart.class, BookType.class, BookAwardType.class, CourseAbilityRate.class,
        TeachingNature.class, CourseType.class, CourseCategory.class, CourseNature.class, TeachingMethod.class,
        CourseTakeType.class, EducationLevel.class, ExamMode.class, ExamForm.class, ExamStatus.class,
        ElectionMode.class, ExamType.class, GradeType.class, GradingMode.class, StdLabel.class,
        StdLabelType.class, StdType.class, TeachLangType.class)
        .cache("openurp.base");

    add(org.openurp.base.model.School.class,
        org.openurp.base.model.Room.class,
        org.openurp.base.model.Building.class, org.openurp.base.model.Campus.class,
        org.openurp.base.model.Department.class, org.openurp.base.model.User.class,
        org.openurp.base.model.Staff.class, org.openurp.base.std.model.GraduateSeason.class,

        org.openurp.base.edu.model.Calendar.class,
        org.openurp.base.edu.model.CalendarStage.class,
        org.openurp.base.edu.model.Semester.class, org.openurp.base.edu.model.SemesterStage.class,
        org.openurp.base.edu.model.TimeSetting.class, org.openurp.base.edu.model.CourseUnit.class,

        org.openurp.base.edu.model.Teacher.class,
        org.openurp.base.edu.model.MajorJournal.class, SchoolLength.class, org.openurp.base.edu.model.Major.class,
        MajorDiscipline.class, org.openurp.base.edu.model.DirectionJournal.class, org.openurp.base.edu.model.Direction.class,

        org.openurp.base.edu.model.Project.class,
        org.openurp.base.edu.model.ProjectCode.class, org.openurp.base.edu.model.Course.class,
        org.openurp.base.edu.model.Classroom.class, org.openurp.base.edu.model.CourseHour.class,
        org.openurp.base.edu.model.Textbook.class, org.openurp.edu.clazz.model.StdCourseAbility.class,
        org.openurp.base.edu.model.CourseLevel.class,

        org.openurp.base.std.model.Grade.class,
        org.openurp.base.std.model.Graduate.class,
        org.openurp.base.std.model.Mentor.class,
        org.openurp.base.std.model.Squad.class,
        org.openurp.base.std.model.Student.class,
        org.openurp.base.std.model.StudentState.class,
        ProjectProperty.class,

        org.openurp.base.model.Person.class,

        org.openurp.edu.program.model.Program.class,
        org.openurp.edu.program.model.TermCampus.class,
        org.openurp.edu.program.model.MajorAlternativeCourse.class,
        org.openurp.edu.program.model.StdAlternativeCourse.class,

        org.openurp.edu.program.model.ProgramDoc.class,
        org.openurp.edu.program.model.ProgramDocSection.class,
        org.openurp.edu.program.model.ProgramDocTemplate.class,
        org.openurp.edu.program.model.ProgramDocMeta.class,

        org.openurp.edu.program.model.SharePlan.class,
        org.openurp.edu.program.model.SharePlanCourse.class,
        org.openurp.edu.program.model.ShareCourseGroup.class,

        org.openurp.edu.program.model.MajorPlan.class,
        org.openurp.edu.program.model.MajorPlanCourse.class,
        org.openurp.edu.program.model.MajorCourseGroup.class,

        org.openurp.edu.program.model.ExecutionPlan.class,
        org.openurp.edu.program.model.ExecutionPlanCourse.class,
        org.openurp.edu.program.model.ExecutionCourseGroup.class,

        org.openurp.edu.program.model.StdPlan.class,
        org.openurp.edu.program.model.StdPlanCourse.class,
        org.openurp.edu.program.model.StdCourseGroup.class,

        org.openurp.edu.clazz.code.ClazzTag.class, org.openurp.edu.clazz.model.CourseMaterial.class,
        org.openurp.edu.clazz.model.Session.class,
        org.openurp.edu.clazz.model.NormalClass.class, org.openurp.edu.clazz.model.Clazz.class,
        org.openurp.edu.clazz.model.CourseTaker.class, org.openurp.edu.clazz.model.ClazzGroup.class,
        org.openurp.edu.clazz.model.ArrangeSuggest.class, org.openurp.edu.clazz.model.SuggestActivity.class,
        org.openurp.edu.clazz.model.Restriction.class, org.openurp.edu.clazz.model.RestrictionItem.class,
        org.openurp.edu.clazz.model.ClazzPlanRelation.class,
        org.openurp.edu.clazz.config.ScheduleSetting.class,

        org.openurp.edu.clazz.model.Subclazz.class,
        org.openurp.edu.clazz.model.Lesson.class,
        org.openurp.edu.clazz.model.TeachingPlan.class,

        org.openurp.edu.exam.model.ExamActivity.class, org.openurp.edu.exam.model.ExamRoom.class,
        org.openurp.edu.exam.model.ExamTaker.class, org.openurp.edu.exam.model.ExamTask.class,
        org.openurp.edu.exam.model.ExamRoomGroup.class,
        org.openurp.edu.exam.model.ExamGroup.class, org.openurp.edu.exam.config.ExamAllocSetting.class,
        org.openurp.edu.exam.model.ExamTurn.class, org.openurp.edu.exam.model.Invigilation.class,
        org.openurp.edu.exam.model.InvigilationQuotaDetail.class,
        org.openurp.edu.exam.model.InvigilationQuota.class,
        org.openurp.edu.exam.model.InvigilationClazzQuota.class,

        org.openurp.edu.grade.course.model.ExamGradeState.class,
        org.openurp.edu.grade.course.model.ExamGrade.class,
        org.openurp.edu.grade.course.model.CourseGradeState.class,
        org.openurp.edu.grade.course.model.CourseGrade.class,
        org.openurp.edu.grade.course.model.GaGradeState.class,
        org.openurp.edu.grade.course.model.GaGrade.class,
        org.openurp.edu.grade.config.GradeRateConfig.class,
        org.openurp.edu.grade.config.GradeRateItem.class,
        org.openurp.edu.grade.config.GradeInputSwitch.class,
        org.openurp.edu.grade.config.TranscriptTemplate.class,
        org.openurp.edu.grade.course.model.StdGpa.class,
        org.openurp.edu.grade.course.model.StdSemesterGpa.class,
        org.openurp.edu.grade.course.model.StdYearGpa.class,
        org.openurp.edu.grade.plan.model.GroupAuditResult.class,
        org.openurp.edu.grade.plan.model.PlanAuditResult.class,
        org.openurp.edu.grade.plan.model.CourseAuditResult.class,

        org.openurp.edu.room.model.Occupancy.class,
        org.openurp.edu.room.model.RoomOccupyApp.class,
        org.openurp.edu.room.model.RoomAvailableTime.class,

        org.openurp.edu.textbook.model.Material.class,
        org.openurp.std.info.model.Contact.class, org.openurp.std.info.model.Home.class,
        org.openurp.std.info.model.Examinee.class, org.openurp.std.info.model.Admission.class,
        org.openurp.std.info.model.MajorStudent.class,
        org.openurp.std.register.model.Register.class,
        org.openurp.std.alter.model.StdAlteration.class,
        org.openurp.std.alter.model.StdAlterationItem.class,

        Punishment.class,

        org.openurp.base.std.code.FeeType.class, org.openurp.std.fee.model.Bill.class,
        TuitionConfig.class,
        StdPunishmentType.class,
        UncheckinReason.class,
        UnregisteredReason.class,

        org.openurp.edu.extern.model.CertificateGrade.class,
        CertificateSubject.class, CertificateCategory.class,

        org.openurp.edu.evaluation.course.model.EvaluateResult.class,
        org.openurp.edu.evaluation.course.model.QuestionnaireClazz.class,
        org.openurp.edu.evaluation.app.clazz.model.StdEvaluateSwitch.class,

        org.openurp.edu.clazz.app.model.ElectionProfile.class,
        org.openurp.edu.clazz.app.model.CourseTypeCreditConstraint.class,
        org.openurp.edu.clazz.app.model.constraint.StdCreditConstraint.class,
        org.openurp.edu.clazz.app.model.constraint.StdTotalCreditConstraint.class,
        org.openurp.edu.clazz.app.model.constraint.StdCourseCountConstraint.class,
        org.openurp.edu.clazz.app.model.constraint.ConstraintLogger.class,
        org.openurp.edu.clazz.app.model.constraint.CreditAwardCriteria.class,
        org.openurp.edu.clazz.app.model.ElectPlan.class, org.openurp.edu.clazz.app.model.ElectLogger.class,
        org.openurp.edu.clazz.app.model.StdApplyLog.class,
        org.openurp.edu.clazz.app.model.ElectMailTemplate.class,
        org.openurp.edu.clazz.app.model.CurriculumChangeApplication.class,
        org.openurp.edu.clazz.app.model.CollisionResource.class,
        org.openurp.edu.clazz.app.model.CourseArrangeAlteration.class,
        org.openurp.edu.clazz.app.model.CourseMailSetting.class,
        ScheduleSetting.class,

        org.openurp.std.graduation.model.DegreeAuditItem.class,
        org.openurp.std.graduation.model.GraduateAuditItem.class,
        org.openurp.std.graduation.model.GraduateResult.class, DegreeResult.class,
        org.openurp.std.graduation.model.GraduateBatch.class,
        org.openurp.std.graduation.model.GraduateAuditLog.class,
        org.openurp.std.graduation.model.GraduateAuditStandard.class
        );
  }

}
