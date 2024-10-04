package com.kovidRMS.daoInf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.ReportRow;
import com.kovidRMS.form.StudentForm;

public interface StuduntDAOInf {

	public String insertStudentDetails(StudentForm studform, int id);

	public int retrievestudentID(String aadhaar);

	public String insertStudentContact(StudentForm studform, int studentID);

	public String insertStudentPersonalInfo(StudentForm studform, int studentID);

	public int verifyAadhaar(String aadhaar);

	public List<StudentForm> searchStudent(String searchStudentName, int standardID, int divisionID);

	public List<StudentForm> retriveEditStudentList(int standardID, int divisionID, String searchName);

	public List<StudentForm> retreiveStudentDetailByStudentID(int studentID, String searchName);

	public String verifyStudentCredential(StudentForm studform);

	public String updateStudentDetail(StudentForm studform);

	public String updateStudentContacts(StudentForm studform);

	public String updateStudentInfo(StudentForm studform);

	public String verifyStudentDetail(String aadhaar);

	public String rejectStudent(StudentForm studform);

	public List<StudentForm> searchSubjectList(String searchSubject, int organizationID);

	public List<StudentForm> retrieveExistingSubjectList(int organizationID);

	public List<String> retrievesubjectList(int organizationID);

	public List<StudentForm> retrieveSubjectListByID(int SubjectID, String searchSubject, String subjectType);

	public String insertSubject(StudentForm studform, int organizationID);

	public String updateSubject(StudentForm studform);

	public String rejectSubject(StudentForm studform);

	public List<StudentForm> retrieveExistingCommutationList(int organizationID);

	public List<StudentForm> retrieveCommutationListByID(int CommutationID, String searchCommutation);

	public String insertCommutation(StudentForm studform, int organizationID);

	public HashMap<Integer, String> getCommutation(int organizationID);

	public String updateCommutation(StudentForm studform);

	public String rejectCommutation(StudentForm studform);

	public List<StudentForm> searchCommutationList(String searchCommutation, String searchCriteria, int organizationID);

	public String insertRegistrationInfo(StudentForm studform, int studentID, int AYClassID);

	public int retrieveAYClassID(int standardID, int divisionID);

	public String updateRegistrationInfo(StudentForm studform);

	public List<StudentForm> retriveStudentList(int standardID, int academicYearID);

	public int retrieveAYCLassID(int standardID, int divisionID, int ayID);

	public String insertNewStudentDetails(int studentID, String physicalActivities, String creativeActivities,
			String compulsoryActivities, double weight, double height, int ayID);

	public int retrieveAyIDByStandardID(int standardID, int divisionID, int academicYEarID);

	public String disableOldStudentDetails(int studentID, int oldAyID);

	public int retrivecommutationID(StudentForm studform);

	public String retrieveCommutationMode(int studentID);

	public List<StudentForm> retriveStudentListByStandardAndDivision(int standardID, int divisionID);

	public HashMap<String, String> getCreativeActivitiesList(int organizationID);

	public HashMap<String, String> getPhysicalActivitiesList(int organizationID);

	public HashMap<String, String> getCompulsoryActivitiesList(int organizationID);

	public String retriveCreativeActivities(int studentID);

	public String retrivePhysicalActivities(int studentID);

	public String retriveCompulsoryActivities(int studentID);

	public String retrieveSiblingID(int studentID);

	public List<StudentForm> retrivesiblingList(String siblingID);

	public String insertFatherContact(String firstName, String middleName, String lastName, String relation,
			String mobile, String phone, String Address, String city, String state, String country, int pinCode,
			String occupation, String emailId, int studentID);

	public String insertMotherContact(String motherfirstName, String mothermiddleName, String motherlastName,
			String motherrelation, String mothermobile, String motherPhone, String motherAddress, String mothercity,
			String motherstate, String mothercountry, int motherpinCode, String motheroccupation, String motheremailId,
			int studentID);

	public String updateFathersInfo(String parentfirstName, String parentmiddleName, String parentlastName,
			String relation, String mobile, String parentPhone, String parentAddress, String parentcity,
			String parentstate, String parentcountry, int parentpinCode, String occupation, String emailId,
			int studentID);

	public String updateMothersInfo(String motherfirstName, String mothermiddleName, String motherlastName,
			String motherrelation, String mothermobile, String motherPhone, String motherAddress, String mothercity,
			String motherstate, String mothercountry, int motherpinCode, String motheroccupation, String motheremailId,
			int studentID);

	public String insertEmergencyContacts(StudentForm studform, int studentID);

	public String updateEmergencyContact(StudentForm studform);

	public String insertCondition(String condition, int studentID);

	public List<StudentForm> retriveConditionList(int studentID);

	public JSONObject deleteMedConditionRow(int medConditionID);

	public String updateCondition(String editmedCondition, int studentID);

	public List<StudentForm> retriveEditStudentList1(int userID, String searchName);

	public List<StudentForm> searchStudent1(String searchStudentName, int userID);

	public List<StudentForm> retriveBulkStudentList(int standardID, int academicYearID, int divisionID);

	public String updateBulkStudentRegistration(int rollnumber, String creativeActivities, String physicalActivities,
			String compulsoryActivities, double height, double weight, int studentID, String activityStatus);

	public String updateBulkStudentDetails(String grNumber, int studentID);

	public List<StudentForm> retriveBulkStudentList1(int standardID, int divisionID, int academicYearID,
			String activityStatus);

	public HashMap<Integer, String> retrieveScholasticsubjectList(int organizationID);

	public HashMap<Integer, String> retrieveCoscholasticsubjectList(int organizationID);

	public HashMap<Integer, String> retrieveAcademicsubjectList(int organizationID);

	public HashMap<Integer, String> retrievePhysicalsubjectTypeList(int organizationID);

	public HashMap<Integer, String> retrieveCreativesubjectTypeList(int organizationID);
	
	public HashMap<Integer, String> retrieveCompulsorysubjectTypeList(int organizationID);

	public HashMap<Integer, String> retrievePersonalitysubjectList(int organizationID);

	public List<StudentForm> retrieveClassStudentListForAttendance(int standardID, int divisionID, String term,
			String workingMonth, int academicYearID);

	public JSONObject retrieveworkingMonthForAttendance(String term, int academicYearID, int standardID);

	public LinkedHashMap<String, String> retrieveMonthListByTerm(String term, int academicYearID, int standardID);

	public int retrieveRegistrationID(int studentID);

	public int retrieveattendanceID(String term, String workingMonth, int academicYearID, int standardID);

	public String insertStudentAttendanceDetails(int registrationID, int daysPresent, int attendanceID);

	public List<StudentForm> retrieveExistingClassStudentListForAttendance(String term, String workingMonth,
			int academicYearID, int standardID, int divisionID);

	public String updateStudentAttandanceDetails(int StudAttndsID, int daysPresent);

	public List<String> retrievestudentsBasedCustomReportList(int standardID, int divisionID, int academicYearID,
			String searchField, String containsName, String checkBoxList, String ageValue, String searchFieldNew);

	public String retrieveworkingDays(String term, int academicYearID, int standardID, String workingMonth);

	public List<StudentForm> retriveActivityList(int subjectID);

	public JSONObject deleteActivityRow(int activityID);

	public String insertActivity(String activity, int subjectID);

	public String updateActivity(String activity, int activityID);

	public JSONObject retrieveExamListByTermAndAcademicYearID(int academicYearID, String term, int standardID);

	public JSONObject retrieveExamTermEnd(int academicYearID, String term);

	public List<StudentForm> retrievestudentsBasedExamCustomReportList(int standardID, String divisionID,
			int academicYearID, String examID, int subjectID, String searchFields, String containsName,
			String searchFieldsNew, String Value);

	public JSONObject retrieveSubjectListByUserIDByStandard(int standardID);

	public HashMap<Integer, String> getSubjectListByStandard(int standardID);

	public HashMap<String, String> retrieveExamTermEndAndAcademicYearID(int academicYearID, String term);

	public HashMap<Integer, String> getSubjectListByStandardForNonScholastic(int standardID);

	public JSONObject retrieveSubjectListBystandardForNonScholastic(int standardID);

	public String retrievrGradesForStudent(int examinationID, int subjectID, int standardID, int divisionID,
			int academicYearID, int studentID);

	public double retrievrMarksForStudent(int examinationID, int subjectID, int standardID, int divisionID,
			int academicYearID, int studentID);

	public HashMap<String, String> retrieveAllExamTermEndAndAcademicYearID(int academicYearID);

	public JSONObject retrieveAllExamListByTermAndAcademicYearID(int academicYearID, int standardID);

	public JSONObject retrieveAllExamTermEnd(int academicYearID);

	public List<StudentForm> retrievestudentsBasedAllExamCustomReportList(int standardID, int academicYearID,
			String examID, int subjectID, String searchFields, String containsName, String searchFieldsNew,
			String Value);

	public double retrievrMarksScaledForStudent(int examinationID, int subjectID, int standardID, int divisionID,
			int academicYearID, int studentID);

	public String retrievrAllGradesForStudent(int examinationID, int subjectID, int standardID, int academicYearID,
			int studentID);

	public double retrievrAllMarksForStudent(int examinationID, int subjectID, int standardID, int academicYearID,
			int studentID);

	public int verifyExamType(int examinationID, String examType);

	public int retrieveScaledMarks(String term, int academicYearID, int standardID, int divisionID, int subjectID);

	public int retrievTotalMarks(int examinationID, int subjectID, int standardID, int divisionID, int academicYearID);

	public int retrieveAllScaledMarks(int academicYearID, int standardID, int subjectID);

	public int retrieveSubjectType(int subjectID);

	public int retrieveAllScaledMarks(String term, int academicYearID, Integer examinationID, int standardID,
			int subjectID);

	public JSONObject verifySubjectType(int subjectID);

	public String retrieveSubjectTypeValue(int subjectID);

	public int retrieveScaledMarksNew(String term, int academicYearID, Integer examinationID, int standardID,
			int divisionID, int subjectID);

	public JSONObject retrieveStandardListForacademicYear(int academicYearID, int userID);

	public JSONObject retrieveDivisionForacademicYear(int academicYearID, int standardID);

	public String importStudentDetails(ReportRow reportRow);

	public String importStudentContact(ReportRow reportRow, int studentID);

	public String importStudentPersonalInfo(ReportRow reportRow, int studentID, int commutationID);

	public int retrieveCommutationID(String nameOfDriver);

	public String importEmergencyContacts(ReportRow reportRow, int studentID);

	public int retrieveAYClassIDNew(String standard, String division, int academicYearID);

	public String importRegistrationInfo(ReportRow reportRow, int studentID, int AYClassID);

	public boolean verifyParentsDetail(int studentID, String relation);

	public boolean verifyAcademicYearID(String academicYear, int academicYearID);

	public String importExaminationDetails(ReportRow reportRow, int academicYearID);

	public int retrieveStandardIDByStandardName(String standard);

	public String importAttendanceDetails(ReportRow reportRow1, int academicYearID, int standardID);

	public String importTimeTableDetails(ReportRow reportRow2, int academicYearID, int examID, int standardID);

	public int retrieveExamIDByExamName(String examName, String Date, int academicYearID);

	public String importParentsDetails(String firstName, String middleName, String lastName, String mobile,
			String emailID, String occupation, String relation, int studentID);

	public List<Integer> retrievestudentsIDBasedList(int standardID, int divisionID, int academicYearID);

	public String retrieveStudentsBasedParentsCustomReportList(int standardID, int divisionID, int academicYearID,
			String searchField, String checkBoxList, Integer studentID);

	public int retrieveDivisionIDByStandardDivision(int standardID, String division);

	public int retrieveClassTeacherIDByclassTeacherName(String classTeacher);

	public String importAYClassDetails(ReportRow reportRow, int academicYearID, int divisionID, int standardID,
			int classTeacherID);

	public int retrieveSubjectIDBySubjectName(String subject, int organizationID);

	public String importAYSubjectDetails(ReportRow reportRow, int AYClassID, int subjectNameID, int teacher1id,
			int teacher2id);

	public double retrievrAllMarksScaledForStudent(Integer examinationID, int subjectID, int standardID,
			int academicYearID, int studentID);

	public int retrievAllTotalMarks(Integer examinationID, int subjectID, int standardID, int academicYearID);

	/**
	 * 
	 * @param examinatioNID
	 * @return
	 */
	public String retrieveExaminationType(int examinatioNID);

	/**
	 * 
	 * @param divisionIDStr
	 * @return
	 */
	public String retrieveDivisionName(String divisionIDStr);

	/**
	 * 
	 * @param examinationID
	 * @return
	 */
	public String retrieveTermByExaminationID(int examinationID);

	public String retrieveSubjectBySubjectID(int subjectID);

	public List<StudentForm> retrievStudentsListByStandardID(int standardID, int academicYearID);

	public List<StudentForm> retriveStudentDetailsListByStandardIDAndDivisionID(int standardID, int divisionID);

	public String retrievePersonalityDevelopmentMarksBySubjectID(int ayClassID, int examID, int studentID,
			Integer subjectID);

	public int retrieveExaminationID(String term, String examName, int academicYearID);

	public String importSubjectAssessmentDetails(int gradeChack, int scaleVal, int totalMarks, int ayclassID,
			int examinationID, int subjectID);

	public int retrieveStudentsAttendanceByMonth(int studentID, Integer monthNameID);

	public LinkedHashMap<Integer, String> retrieveMonthListByTermAndStandard(String term, int academicYearID,
			int standardID);

	public LinkedHashMap<Integer, String> retrieveMonthListByStandard(int academicYearID, int standardID);

	public HashMap<Integer, String> retrieveCoscholasticsubjectListByStandardID(int standardID);

	public String retrieveTotalMarksForCoscholasticSubject(int studentID, Integer subjectNameID, int ayclassID,
			int examinationID);

	public boolean verifyTeacherID(int academicYearID, int divisionID, int standardID, int classTeacherID);

	public String retreiveStudentsDateOfBirth(int studentID);

	public int findIndex(String[] standardString, String standard);

	public String retrieveStandardByStandardID(int standardID);

	public int retrieveDivisionCountByStandardID(int nextStandardID);

	public int retrieveDraftAyclassIDCountByStandardID(int nextStandardID, int ayID);

	public int CheckSubjectName(String name, String subjectType, int organizationID2);

	public int CheckSubjectNameEdit(String name, String subjectType, int subjectID, int organizationID);

	public int CheckSubjectActivityName(String activity, int subjectID);

	public int CheckEditSubjectActivityName(String activity, int activityID, int subjectID);

	public boolean verifySubjectAvailabilityInStandard(int subjectNameID, int standardID);

	public int CheckSubjectOrderEdit(String subjectType, int sortOrder, int subjectID, int organizationID);

	public int CheckSubjectOrder(String subjectType, int sortOrder, int organizationID);

}
