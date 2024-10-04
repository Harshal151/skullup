package com.kovidRMS.service;

import javax.servlet.http.HttpServletRequest;

import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LibraryForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.StudentForm;
import com.kovidRMS.form.VotingForm;

public interface kovidRMSServiceInf {

	String registerUser(LoginForm form, String realPath, HttpServletRequest request, int organizationID);

	String editUserDetail(LoginForm form, int organizationID, String realPath, HttpServletRequest request);

	String addStudent(StudentForm studform, int id);

	String editStudentDetail(StudentForm studform);

	String registerAcademicYear(LoginForm form, int organizationID);

	String registerExamination(ConfigurationForm conform, int academicYearID);

	String registerAcademicYearList(ConfigurationForm conform, String activityStatus, int AcademicYearID);

	String transferStudent(StudentForm studform);

	String configureSubjectAssessment(ConfigurationForm conform);

	String addStudentAssessment(ConfigurationForm conform, int activityAssessmentCheck);

	String editAcademicYear(LoginForm form, int organizationID);

	String bulkUpdateStudent(StudentForm studform, String activityStatus);

	String addStudentAttendance(StudentForm studform, int AcademicYearID);

	String editAttendance(LoginForm form, int organizationID, String realPath, HttpServletRequest request);

	String activateAcademicYear(LoginForm form, int academicYearID, int organizationID);

	String addTimeTable(LoginForm form);

	String editSubject(StudentForm studform, int organizationID);

	String addStudentAssessmentForCoScholastic(ConfigurationForm conform);

	String registerLeavingCertificate(ConfigurationForm conform);

	String addStudentStatusDetails(ConfigurationForm conform);

	String registerStandards(LoginForm form, int organizationID);

	String editRegisterStandard(LoginForm form, int organizationID);

	String registerSubject(StudentForm studform, int organizationID);

	String registerDivision(LoginForm form);

	String registerupdateDivision(LoginForm form);

	String registerOrganization(LoginForm form);

	String updateOrganization(LoginForm form);

	String registerRules(LibraryForm form, int libraryID);

	String editRulesFor(LibraryForm form);

	String configureCupboard(LibraryForm form);

	String configureShelf(LibraryForm form);

	String registerBooks(LibraryForm form, int academicYearID, int libraryID);

	String updateBookStatus(LibraryForm form);

	String moveBooks(LibraryForm form);

	String configureVotingDetails(VotingForm form);

	String updateVotingDetails(VotingForm form, int academicYearID);


}
