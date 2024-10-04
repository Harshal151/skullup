package com.kovidRMS.daoInf;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.kovidRMS.form.LibraryForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.StudentForm;

public interface LoginDAOInf {

	/**
	 * 
	 * @param loginForm
	 * @return
	 */
	public String verifyUserCredentials(LoginForm loginform);

	/**
	 * 
	 * @param loginform
	 * @param organizationID
	 * @return
	 */
	public String insertUser(LoginForm loginform, int organizationID);

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public String insertPasswordHistory(int userID, String password);

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
	public boolean verifyPasswordHistory(int userID, String password);

	/**
	 * 
	 * @param username
	 * @param organizationID
	 * @return
	 */
	public int verifyUsername(String username, int organizationID);

	public int retrieveUserIDByUsername(String username, int organizationID);

	/**
	 * 
	 * @param searchUserName
	 * @param organizationID
	 * @return
	 */
	public List<LoginForm> searchUser(String searchUserName, int organizationID);

	public List<LoginForm> retriveEditUSerList(int organizationID);

	public List<LoginForm> retreiveUserDetailByUserID(int userID);

	public boolean verifyUsernameWithUserID(String username, int userID, int organizationID);

	public boolean verifyPassword(int userID, String password);

	public String updateUserDetail(LoginForm form);

	public String rejectUser(LoginForm form);

	public LoginForm getUserDetail(String username);

	public String insertAudit(String ipAddress, String actionName, int userID);

	public List<LoginForm> searchStandardList(String searchStandard, int organizationID);

	public List<LoginForm> retrieveExistingStandardList(int organizationID);

	public List<LoginForm> retrieveStandardListByID(int StandardID, String searchStandard);

	public String insertStandard(LoginForm form, int organizationID);

	public String updateStandard(LoginForm form);

	public HashMap<Integer, String> getStandard(int organizationID);

	public List<LoginForm> searchDivisionList(String searchDivision, int organizationID);

	public List<LoginForm> retrieveExistingDivisionList(int organizationID);

	public List<LoginForm> retrieveDivisionListByID(int DivisionID, String searchDivision);

	public String insertDivision(LoginForm form);

	public String updateDivision(LoginForm form);

	public HashMap<Integer, String> getOrganization();

	public List<LoginForm> searchOrganizationList(String searchOrganization);

	public List<LoginForm> retrieveExistingOrganizationList();

	public List<LoginForm> retrieveOrganizationListByID(int organizationsID, String searchOrganization);

	public String insertOrganization(LoginForm form);

	public String updateOrganization(LoginForm form);

	public List<LoginForm> searchAcademicYearList(String searchAcademicYear, int organizationsID);

	public List<LoginForm> retrieveExistingAcademicYearList(int organizationsID);

	public List<LoginForm> retrieveAcademicYearListByID(int AcademicYearID, String searchAcademicYear);

	public String insertAcademicYear(LoginForm form, int organizationID, String status);

	public String updateAcademicYear(LoginForm form);

	public HashMap<Integer, String> getDivision(int standardID);

	public HashMap<String, String> getsubject(int standardID);

	public HashMap<Integer, String> getAppUser(int organizationID);

	public HashMap<Integer, String> getSubjectList();

	public int UpdateAcademicYearStatus(String activityStatus);

	public String retrieveAcademicYearName(int organizationID);

	public String retrieveInActiveAcademicYearName(int academicYearID);

	public String retrieveAcademicYearName1();

	public String rejectStandard(LoginForm form);

	public int retriveactiveteacherID();

	public String retrievesubjectListbyStandardID(int standardID);

	public int retrieveAcademicYearID(int organizationID);

	public List<LoginForm> retrieveattendanceList(int academicYearID, String term);

	public String updateAttendance(int editWorkdays, int attendanceID);

	public JSONObject deleteAttendanceRow(int deleteID, String active);

	public String retrieveStandardName(int userID, int academicYearID);

	public int retrieveStandardID(int userID, int academicYearID);

	public String retrieveDivisionName(int userID, int academicYearID);

	public int retrieveDivisionID(int userID, int academicYearID);

	public String retrieveProfilePic(int userID);

	public String retrievesubjectNameBySubjectID(int subjectID);

	public String retrieveBoardLogo(int organizationID);

	public String retrieveOrganizationLogo(int organizationID);

	public String retrieveOrganizationName(int organizationID);

	public String retrieveOrganizationAddress(int organizationID);

	public String retrieveOrganizationPhone(int organizationID);

	public String retrieveOrganizationEmail(int organizationID);

	public String retrieveOrganizationTagLine(int organizationID);

	public String retrievesubjectNameBySubjectIDSubjectType(int subjectID);

	public int retrieveSubjectIDBySubjectType(int subjectID);

	public String insertAttendance(String attendanceEvent, int academicYearID);

	public String insertAttendanceNew(String term, String month, int workingDays, int academicYearID, int StandardID);

	public String retrieveScholasticSubjectNameBySubjectID(int subjectID, String subjectType);

	/**
	 * 
	 * @param subjectID
	 * @param ayClassID
	 * @return
	 */
	public boolean verifySubjectIsMarkBased(int subjectID, int ayClassID);

	public String enableUser(LoginForm form);

	public HashMap<Integer, String> getAcademicYearNameList(int organizationID);

	public String getActivityStatus(int academicYearID);

	public int getActiveacademicYearID(int organizationID);

	int InActiveAcademicYearStatus(int academicYear);

	String UpdateAcademicYear(int academicYearID);

	List<Integer> getAYClassIDList(int academicYear);

	void UpdateAYClassID(int ayclassID);

	List<Integer> getDraftAYClassIDList(int academicYearID);

	void UpdateDraftAYClassID(int ayclassID);

	void UpdateRegistrationAYClassID(int ayclassID);

	void UpdateRegistrationDraftAYClassID(int ayclassID);

	String retrieveStandardName1(int userID, int academicYearID, String activityStatus);

	String retrieveDivisionName1(int userID, int academicYearID, String activityStatus);

	int retrieveStandardID1(int userID, int academicYearID, String activityStatus);

	int retrieveDivisionID1(int userID, int academicYearID, String activityStatus);

	String insertTimeTable(int standardID, int examinationID, String date, String subject);

	List<LoginForm> retriveTimeTableList(int academicYearID);

	String updateTimeTableList(int standardID, String date, String subject, int examinationID, int timeTableID);

	JSONObject retrieveSubjectListForStandard(int standardID);

	String retrievesubjectNameBySubjectIDForNonScholastic(int subjectID);

	HashMap<String, String> getDivisionList(int standardID);

	HashMap<Integer, String> getInActiveAcademicYearList();

	HashMap<Integer, String> getStandardList(int academicYearID);

	public int CheckAcademicYearName(String yearName, int organizationID);

	public int CheckAcademicYearNameEdit(String yearName, int academicYearID, int organizationID);

	public String retrievesubjectNameBySubjectIDForCoScholasticSubject(int parseInt);

	public HashMap<Integer, String> getStandardExceptXStandard(int organizationID);

	int CheckStandardName(String standard, String stage, int organizationID);

	int CheckStandardNameEdit(String standard, String stage, int standardID, int organizationID);

	int CheckDivisionName(String division, int standardID);

	int CheckDivisionNameEdit(String division, int standardID, int divisionID);

	public String retrieveAcademicYearAgeFromDate(int academicYearID);

	public String retrieveOrganizationEmailPassword(int organizationID);

	public String retrievePrincipalSignatureFile(int organisationID);

	public String retrieveOrganizationSeal(int organisationID);

	public String insertLibrary(String library, int organisationID);

	public HashMap<Integer, String> retrieveLibraryList(int organisationID);

	public String updateLibrary(String library, int libraryID);

	public int retrieveOrganizationID();

	public HashMap<Integer, String> getAcademicYearList(int organisationID);

	public String editBookDetail(LibraryForm form);

	public HashMap<String, String> getStandardNameList(int organisationID);

	public HashMap<Integer, String> getStandardDivisionList(int academicYearID);

	public String retrieveClassNameByClassID(int ayclassID);

	public int CheckFirstEntryAcademicYear(int organizationID);

	public JSONObject checkAttendanceAvailability(String month, String term, int standardID, int academicYearID);

	public int CheckAttendance(int standardID, String term, String workingMonth, int academicYearID);

	public String retrievePrimaryHeadmistressSignature(int organisationID);

	public String retrievePrimaryHeadmistressSeal(int organisationID);

	public String retrieveSecondaryHeadmistressSignatur(int organisationID);

	public String retrieveSecondaryHeadmistressSeal(int organisationID);

	public String retrieveSignOnRC(int organisationID);

	public String getStandardStageByStandardID(int standardID);

}
