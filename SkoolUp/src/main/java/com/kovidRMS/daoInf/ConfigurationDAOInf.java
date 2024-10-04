package com.kovidRMS.daoInf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.StudentForm;

public interface ConfigurationDAOInf {

	String insertExamination(String term, String examName, String examType, String startDate, String endDate,
			int academicYearID);

	List<ConfigurationForm> retriveExaminationlInfo(int ExaminationID);

	JSONObject retrieveDivisionListForStandard(int standardID);

	List<ConfigurationForm> retriveExaminationList(int academicYearID);

	int retriveInactiveAcademicYearID(int OrganizationID);

	JSONObject deleteRow(int deleteID, String active);

	String updateExamination(String term, String examName, String examType, String startDate, String endDate,
			int academicYearID, int examID);

	JSONObject retrieveSubjectListForStandard(int standardID);

	JSONObject retrieveStandardName(int standardID);

	JSONObject retrieveDivisionName(int divisionID);

	JSONObject retrieveTeacherName(int teacherID);

	JSONObject retrieveSubjectName(int subjectID);

	JSONObject retrieveSubjectTeacherName(String teacherNameID);

	List<ConfigurationForm> retriveAcademicYearClassList(int academicYearID);

	String insertAcademicYearList(int standardID, int divisionID, int teacherID, int academicYearID, String activityStatus);

	String updateAcademicYearList(int standardID, int divisionID, int teacherID, int academicYearID, int AYClassID1);

	int retrieveAYClassID();

	String insertConfigDetails(String ConfigEvent, int AYClassID);

	JSONObject retrieveNewSubjectListForStandard(int ayClassID);

	JSONObject deleteAYClassRow(int deleteID, String active);

	JSONObject retrieveSubTeacherListByAYCLassID(int ayClassID);

	JSONObject deleteAYSUbjectByID(int aySubjectID);

	HashMap<Integer, String> getExamList(int academicYearID);
	
	public String retrieveExamName(int academicYearID);
	
	public String retrieveTerm(int academicYearID);
	
	public int retrieveExamID(int academicYearID) ;

	HashMap<Integer, String> getExaminationList(int academicYearID);
	
	public int verifyTermIEndDate(int academicYearID);
	
	HashMap<Integer, String> getExaminationListByTerm(int academicYearID, String term);
	
	HashMap<Integer, String> retrieveExaminationListForTimeTable(int academicYearID);
	
	List<String>retrieveDateList(int examinationID);
	
	List<String>retrieveDateList1(int examinationID);
	
	List<Integer>retrieveStandardIDList(String stage, int examinationID, String date);
	
	List<String>retrieveSubjectList(int examinationID, String date, int standardID);

	public int retrieveAYCLassID(int standardID, int divisionID, int academicYearID);

	JSONObject retrieveExistingSubjectAssessmentList(int ayClassID, int examinationID);

	public String insertSubjectAssessment(int examID, int subject, double totalMarks, double scaleTo, int gradeBased,
			int ayCLassID);

	public String updateSubjectAssessment(int examID, int subjectID, double totalMarks, double scaleTo, int gradeBased,
			int ayCLassID, int subjectAssessmentID);

	HashMap<String, String> getStandardListByTeacher(int UserID);

	public JSONObject retrieveSubjectListByUserID(int userID, int ayClassID);

	public List<ConfigurationForm> retrieveStudentListForExaminationAndSubject(int ayClassID, int examID, int subjectID,
			int userID);

	public int retrieveSubjectAssessmentID(int examinationID, int ayCLassID, int subjectID);

	public int retrieveRegistrationID(int studentID);

	public String insertStudentAssessmentDetails(int subjectAssessmentID, int registrationID, double marksObtained,
			String grade, double scaledMarks, int absentFlag);

	public List<ConfigurationForm> retrieveExistingStudentAssessmentList(int ayClassID, int examinationID,
			int subjectID);

	public String updateStudentAssessmentDetails(int studentAssessmentID, double marksObtained, String grade,
			double marksScaled, int editabsentFlag);

	public HashMap<Integer, String> retrieveSubjectListForCLassTeacher(int userID, int academicYearID);

	public boolean verifyClassTeacher(int userID, int academicYearID);

	public String retrieveStandardForClassTeacher(int userID, int academicYearID);

	public int retrieveAYClassIDForClassTeacher(int userID, int academicYearID);
	
	public String retrieveSubjectListByStandardID(int standardID);

	List<ConfigurationForm> retrieveExistingsubjectAssessmentList(int ayClassID, int examinationID, String subArr);

	List<ConfigurationForm> retrieveSubjectListByStandard(int standardID);

	boolean verifySubjects(int ayClassID, int examinationID, String subject);

	public HashMap<Integer, String> retrieveDivisionListByStandardID(int stdID);

	public String retrieveSubjectTeacherNameByIDs(int teacherIDStr);

	JSONObject retrieveStudentListForStandardAndDivision(int standardID, int divisionID);

	JSONObject retrieveStudentName(String studentNameID);

	JSONObject retrieveCondition(String studentNameID);

	boolean verifyExamType(int examinationID, int academicYearID);

	JSONObject retrieveSubjectListByUserIDByExamType(int userID, int ayClassID, int organizationID);

	int retrieveGradeBasedValue(int examinationID, int subjectID, int ayclassID);

	HashMap<Integer, String> retrieveSubjectListByExamID(int userID, int ayClassID);

	HashMap<Integer, String> retrieveSubjectListByExamIDByExamType(int userID, int ayClassID, int organizationID);

	String verifySubjectType(int subjectID);

	JSONObject retrieveSubjectListForCLassTeacher1(int userID, int academicYearID);

	JSONObject retrieveSubjectListForCLassTeacherByExamType1(int userID, int academicYearID, int organizationID);

	List<ConfigurationForm> retrieveClassStudentListForClassTeacher(int standardID, int divisionID, int academicYearID, String term);

	List<ConfigurationForm> retrieveStudentDetailsList(int studentID, String term, int ayClassID);

	List<ConfigurationForm> retrievecoScholasticGradeList(int studentID, String term, int ayClassID);

	List<ConfigurationForm> retrieveExtraCurricularGradeList(int studentID, String term, int ayClassID);

	String retrieveSubjectListByStandardIDExamType(int standardID);

	HashMap<Integer, String> retrieveSubjectListForCLass(int standardID);

	List<ConfigurationForm> retrievePersonalityDevelopmentGradeList(int studentID, String term, int ayClassID);

	public String verifyPhysicalCreativeActivity(int subjectID);

	public double retrieveSubjectScaleTo(int subjectAssessmentID);

	public String retrieveSubjectListForStandardByStandardID(int standardID);

	HashMap<String, Integer> retrieveExaminationList(int academicYearID, String term, int aYClassID);

	public int retrieveScholasticGradeList(int subjectID, Integer examId, int studentID, int ayClassID);

	public String retrieveSubject(int subjectID);

	public int retrieveScaleTo(int subjectID, Integer examId, int ayClassID);

	public String getStandardNameByStandardID(int standardID);

	String verifySubjectType1(int subjectID);

	String retrieveMonthsforStudent(int attendanceID);

	String retrieveAttendanceIDforStudent(String term, int academicYearID, int standardID);

	int retrievedaysPresentforStudent(int studentID, int attendanceID);

	public boolean verifyAbsent(int subjectID, int studentID, int ayClassID, int examID);

	boolean verifyAbsentFlag(int subjectID, int studentID, int ayClassID, String term);

	String disableOldStudentID(int studentID);

	int retrieveoutOfMarksForSubject(int subjectID, Integer examId, int ayClassID);

	int retrieveScholasticGradeList1(int subjectID, int studentID, int ayClassID, String term);

	public JSONObject retrieveStudentAssessmentHistory(int studentID, int subjectAssessmentID);

	public List<ConfigurationForm> retrieveAttendanceListforStudent(int studentID, String attendanceIDList, String standard);

	public String retrieveGradeValue(int subjectID, int studentID, int ayClassID, Integer examId);

	public LinkedHashMap<Integer, String> retrieveExistingStudentAssessmentListForClassTeacher(int ayClassID);

	public int retrievemarksObtained(int subjectID, Integer examID, int studentID, int ayClassID);

	public String getStandardStageByStandardID(int standardID);

	public JSONObject retrieveSubjectListByUserIDForPersonalityDevelopment(int userID, int ayClassID, int organizationID);

	public HashMap<Integer, String> retrieveSubjectListByExamIDByForPersonalityDevelopment(int userID, int ayClassID, int organizationID);

	public String retriveStandarNameByStage(String stage, int examinationID);

	public HashMap<Integer, String> retrieveStandardWiseSubjectList(int standardID, int ayClassID);

	public HashMap<Integer, String> retrieveSubjectListByExamIDByForExtraCurricularActivity(int userID, int ayClassID, int organizationID);

	public JSONObject retrieveSubjectListByUserIDForExtraCurricular(int userID, int ayClassID, int organizationID);

	public HashMap<Integer, String> retrieveSubjectListByUserIDByStandard(int userID, int ayClassID);

	public JSONObject retrieveSubjectListByUserIDStandard(int userID, int ayClassID);

	public JSONObject retrieveSubjectListForClassTeacherBYUserID(int userID, int academicYearID);

	public HashMap<String, String> getStandardListByTeacherForPersonalityDevelopment(int userID, int organizationID);

	public HashMap<String, String> getStandardListByTeacherForExtraCurricular(int userID, int organizationID);

	public JSONObject deleteStudentAssessmentRow(int studentID);

	public boolean verifyClassTeacherNew(int userID, int academicYearID, String activityStatus);

	public JSONObject retrieveExamNameByExamID(int examinationID, int academicYearID);

	public int retrieveExamIDForTermI(int academicYearID);

	public String retrieveStdDivName(int studentID);

	public String retrieveSubjectTypeBySubjectID(int subjectID);

	public JSONObject retrieveActivitiesName(int subjectID);

	public int retrievesubjectAssessmentID(int examinationID, int subjectID, int ayCLassID);

	public String insertActivityAssessment(int subjectAssessmentID, int activityID, int totalMarks);

	public JSONObject retrieveActivityNameByactivityID(int activityID);

	public boolean verifySubjectIDIsCoScholastic(int subjectID);

	public JSONObject retrieveActivitiesListBysubjAssmntID(int subjAssmntID);

	public HashMap<String, String> getStandardListByTeacherForCoScholastic(int userID, int organizationID);

	public HashMap<Integer, String> retrieveSubjectListByExamIDByForCoScholastic(int userID, int ayClassID, int organizationID);

	public JSONObject retrieveSubjectListByUserIDForCoscholastic(int userID, int ayClassID, int organizationID);

	public String retrieveActivitiesNameBySubjectID(int subjectID, int subjectAssessmentID);

	public JSONObject deleteactivityAssessment(int activityAssessmentID);

	public HashMap<String, String> getExamListByTermAcademicYearID(int standardID, int academicYearID, String term);

	public HashMap<String, String> getALLExamListByTermAcademicYearID(int standardID, int academicYearID);

	public int retrievestudentAssessmentID(int subjectAssessmentID, int registrationID);

	public String insertStudentActivityAssessmentDetails(int studentAssessmentID, int activityAssessmentID, int totalMarks);

	public List<ConfigurationForm> retrievestudentActivityAssessmentAssessmentList(int studentAssessmentID);

	public String updateStudentActivityAssessmentDetails(double totalMarks, int studentActivityAssessmentID);

	public JSONObject retrieveExamListByTerm(String term, int academicYearID);

	public JSONObject retrieveStandardListForAcademicYear(int academicYearID);

	public List<ConfigurationForm> retrieveClassStudentHistoryListForClassTeacher(int standardID, int divisionID,
			int academicYearID, String term);

	public List<ConfigurationForm> retrieveInActiveStudentDetailsList(int studentID, String term, int ayClassID);

	public int retrieveInActiveAYCLassID(int standardID, int divisionID, int academicYearID);

	public String retrieveScholasticRetestMarks(int subjectID, Integer examId, int studentID, int ayClassID);

	public int verifyClassTeacherForStandard(int userID, int academicYearID, int ayClassID);

	public List<ConfigurationForm> retrieveExistingStudentAssessmentListNew(int ayClassID, int examID, int subjectID,
			int subjectAssessmentID);

	public List<ConfigurationForm> retrieveAYClassDetails(int academicYearID);

	public List<ConfigurationForm> retrieveAYSubjectDetails(int ayClassID);

	public int retrieveScaleTo1(int subjectID, int ayClassID);

	public int retrieveScholasticGradeListNew(int subjectID, String examType, int studentID, int ayClassID, String term);

	public int retrieveScaleToNew(int subjectID, String examType, int ayClassID, int academicYearID, String term);

	public int verifyActivityAssessmentBySubjectAssessmentID(int subjectAssessmentID);

	String retrievemarksObtainedForSubjectEnrichment(int subjectID, Integer examID, int studentID, int ayClassID);

	double retrieveMaxScaledMarksAllForStudent(int subjectID, String examType, int studentID, int standardID,
			int academicYearID);

	HashMap<String, String> getStandardDivisionListByTeacher(int userID, int academicYearID, String standard);

	boolean verifyXthSTDTeacher(int userID, int academicYearID, String standard);

	JSONObject retrieveXthSTDSubjectListForClassTeacherBYUserID(int userID, int academicYearID, int ayClassID);

	HashMap<String, Integer> retrieveExaminationListForCostumReport(int academicYearID, String term, String aYClassID);

	String retrieveAYCLassIDForCustomReport(int standardID, String divisionID, int academicYearID);

	double retrieveMarksScaledForStudentSEA(int subjectID, int studentID, String ayClassID, String termValue);

	boolean verify10thClassTeacher(int userID, int academicYearID);
	
	boolean verify9th10thClassTeacher(int userID, int academicYearID);

	List<ConfigurationForm> retrieveStudentDetailsForLCList(int studentID, int registrationID, int ayClassID);

	boolean verifyleavingCertificate(String table, int studentID);

	List<ConfigurationForm> retrieveStudentDetailsFromLeavingCertificate(int studentID, int registrationID,
			int ayClassID);

	String insertLeavingCertificate(ConfigurationForm conform);

	String updateLeavingCertificate(ConfigurationForm conform);

	boolean verifysubjectAssessmentIDPresentOrNot(int examinationID, int subjectID, int AYClassID);

	boolean verifySubjectTeacher(int userID, int academicYearID);

	String retrieveStandardNameDivisionName(int standardID, int divisionID);

	String retrieveStdDivNameForLeavivingCertificate(int studentID);

	boolean verifySEANBAbsent(int subjectID, int studentID, int ayClassID, String term, int academicYearID);

	boolean verifySEANBAbsentCheck(int subjectID, int studentID, String examType, int ayClassID, String term,
			int academicYearID);

	int retrieveAYCLassIDForAll(int standardID, int academicYearID);

	String retriveInaActiveAcdemicYearByAcdemicYearID(int academicYearID);

	LinkedHashMap<Integer, String> retrieveExistingStudentHistoryAssessmentList(int aYClassID);

	List<ConfigurationForm> retrievePersonalityDevelopmentAbsentList(int studentID, String term, int ayClassID);

	String insertStudentStatusDetails(String status, int studentID);

	boolean verifyStudentData(int studentID);

	String updateStudentStatusDetails(String status, int studentID);

	double retrieveactivityMarksForCoScholosticSubject(Integer activityID, Integer subjectID, int ayclassID,
			int examinationID, int studentID);

	HashMap<Integer, String> retrieveActivityNameBySubjectID(Integer subjectNameID, int ayclassID, int examinationID);

	int retrieveAYClassIDByCLassTeacherID(int academicYearID, int divisionID, int standardID, int classTeacherID);

	int retrieveoutOfMarksForSubjectNew(int subjectID, String examType, int ayClassID, int academicYearID, String term);

	String retrieveParentsEmailByStudentID(int studentID, String relation);

	List<ConfigurationForm> retrieveStudentListForReportCardEmail(int standardID, int divisionID);

	String retrieveUserSignatureFile(int userID);

	int retrieveScaleToNew1(Integer subjectName, int aYClassID, int academicYearID, String term);

	List<ConfigurationForm> retrieveAttendanceListforStudent1(int studentID, String term, int standardID);

	List<ConfigurationForm> retrieveStudentListForLeavingCertificate(int standardID, int divisionID,
			int academicYearID);

	boolean checkMarksPresent(int subjectAssessmentID, int registrationID);

	String retrieveLeavingCertLastSerialNo();
	
}
