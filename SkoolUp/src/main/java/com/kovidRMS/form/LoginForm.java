package com.kovidRMS.form;

import java.io.File;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class LoginForm {

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address;
	private String emailId;
	private String mobile;
	private String phone;
	private String city;
	private String state;
	private String country;
	private int pinCode;
	private String role;
	private String activityStatus;
	private int userID;
	private String searchUserName;
	private String fullName;
	private String activity;
	private int timestamp;
	private String ipaddress;
	private File profilePic;
	private String profilePicFileName;
	private String profilePicDBName;
	private String profilePicContentType;
	private File signature;
	private String signatureFileName;
	private String signatureDBName;
	private String signatureContentType;
	private File seal;
	private String sealFileName;
	private String sealDBName;
	private String sealContentType;
	private String searchOrganization;
	private int OrganizationsID;
	private String name;
	private String email;
	private String emailPass;
	private File logo;
	private String logoFileName;
	private String logoDBName;
	private String logoContentType;
	private String boardLogoDBName;
	private File boardLogo;
	private String boardLogoFileName;
	private String boardLogoContentType;
	private String tagline;
	private String website;
	private String yearName;
	private String startDate;
	private String endDate;
	private String termIendDate;
	private String searchAcademicYear;
	private int AcademicYearID;
	private int organizationID;
	private String standard;
	private int totalStrength;
	private String division;
	private String searchDivision;
	private int DivisionID;
	private int standardID;
	private String searchStandard;
	private int studentStrength;
	private int SubjectID;
	private String subjectList;
	private String term;
	private int workingDays;
	private String workingMonth;
	private String newTerm;
	private String[] newMonth;
	private String[] newWorkdays;
	private String[] newStandard;
	private String[] editStandard;
	private String[] newstandardID;
	private String month;
	private String[] editworkingDaysID;
	private String[] editMonthID;
	private String editTerm;
	private String[] editTermID;
	private String[] newAddAttendance;
	private String[] editAddAttendance;
	private int attendanceID;
	private int deleteID;
	private List<Integer> subjectListValues;
	private String stage;
	private int AYClassID;
	private String[] newExam;
	private String[] newDate;
	private String[] newsubjectID;
	private String examDate;
	private int timeTableID;
	private int examID;
	private String subject;
	private String[] editStandardID;
	private String[] editExamID;
	private String[] editSubject;
	private String[] editExamDate;
	private String[] TimeTableID1;
	private String ageFromDate;
	private String board;
	private String medium;
	private String[] newLibrary;
	private int libraryID;
	private String[] editLibrary;
	private String[] editLibraryID;
	private String library;
	private int changedOrganizationID;
	private File signaturePrimary;
	private File sealPrimary;
	private File signatureSecondary;
	private File sealSecondary;
	private String signatureDBPrimary;
	private String sealDBPrimary;
	private String signatureDBSecondary;
	private String sealDBSecondary;
	private String signOnRC;
		
	
	public String getSignOnRC() {
		return signOnRC;
	}

	public void setSignOnRC(String signOnRC) {
		this.signOnRC = signOnRC;
	}

	public File getSignaturePrimary() {
		return signaturePrimary;
	}

	public void setSignaturePrimary(File signaturePrimary) {
		this.signaturePrimary = signaturePrimary;
	}

	public File getSealPrimary() {
		return sealPrimary;
	}

	public void setSealPrimary(File sealPrimary) {
		this.sealPrimary = sealPrimary;
	}

	public File getSignatureSecondary() {
		return signatureSecondary;
	}

	public void setSignatureSecondary(File signatureSecondary) {
		this.signatureSecondary = signatureSecondary;
	}

	public File getSealSecondary() {
		return sealSecondary;
	}

	public void setSealSecondary(File sealSecondary) {
		this.sealSecondary = sealSecondary;
	}

	public String getSignatureDBPrimary() {
		return signatureDBPrimary;
	}

	public void setSignatureDBPrimary(String signatureDBPrimary) {
		this.signatureDBPrimary = signatureDBPrimary;
	}

	public String getSealDBPrimary() {
		return sealDBPrimary;
	}

	public void setSealDBPrimary(String sealDBPrimary) {
		this.sealDBPrimary = sealDBPrimary;
	}

	public String getSignatureDBSecondary() {
		return signatureDBSecondary;
	}

	public void setSignatureDBSecondary(String signatureDBSecondary) {
		this.signatureDBSecondary = signatureDBSecondary;
	}

	public String getSealDBSecondary() {
		return sealDBSecondary;
	}

	public void setSealDBSecondary(String sealDBSecondary) {
		this.sealDBSecondary = sealDBSecondary;
	}

	/**
	 * @return the changedOrganizationID
	 */
	public int getChangedOrganizationID() {
		return changedOrganizationID;
	}

	/**
	 * @param changedOrganizationID the changedOrganizationID to set
	 */
	public void setChangedOrganizationID(int changedOrganizationID) {
		this.changedOrganizationID = changedOrganizationID;
	}

	public String[] getEditLibrary() {
		return editLibrary;
	}

	public void setEditLibrary(String[] editLibrary) {
		this.editLibrary = editLibrary;
	}

	public String[] getEditLibraryID() {
		return editLibraryID;
	}

	public void setEditLibraryID(String[] editLibraryID) {
		this.editLibraryID = editLibraryID;
	}

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String[] getNewLibrary() {
		return newLibrary;
	}

	public void setNewLibrary(String[] newLibrary) {
		this.newLibrary = newLibrary;
	}

	public int getLibraryID() {
		return libraryID;
	}

	public void setLibraryID(int libraryID) {
		this.libraryID = libraryID;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @return the seal
	 */
	public File getSeal() {
		return seal;
	}

	/**
	 * @param seal the seal to set
	 */
	public void setSeal(File seal) {
		this.seal = seal;
	}

	/**
	 * @return the sealFileName
	 */
	public String getSealFileName() {
		return sealFileName;
	}

	/**
	 * @param sealFileName the sealFileName to set
	 */
	public void setSealFileName(String sealFileName) {
		this.sealFileName = sealFileName;
	}

	/**
	 * @return the sealDBName
	 */
	public String getSealDBName() {
		return sealDBName;
	}

	/**
	 * @param sealDBName the sealDBName to set
	 */
	public void setSealDBName(String sealDBName) {
		this.sealDBName = sealDBName;
	}

	/**
	 * @return the sealContentType
	 */
	public String getSealContentType() {
		return sealContentType;
	}

	/**
	 * @param sealContentType the sealContentType to set
	 */
	public void setSealContentType(String sealContentType) {
		this.sealContentType = sealContentType;
	}

	/**
	 * @return the signature
	 */
	public File getSignature() {
		return signature;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(File signature) {
		this.signature = signature;
	}

	/**
	 * @return the signatureFileName
	 */
	public String getSignatureFileName() {
		return signatureFileName;
	}

	/**
	 * @param signatureFileName the signatureFileName to set
	 */
	public void setSignatureFileName(String signatureFileName) {
		this.signatureFileName = signatureFileName;
	}

	/**
	 * @return the signatureDBName
	 */
	public String getSignatureDBName() {
		return signatureDBName;
	}

	/**
	 * @param signatureDBName the signatureDBName to set
	 */
	public void setSignatureDBName(String signatureDBName) {
		this.signatureDBName = signatureDBName;
	}

	/**
	 * @return the signatureContentType
	 */
	public String getSignatureContentType() {
		return signatureContentType;
	}

	/**
	 * @param signatureContentType the signatureContentType to set
	 */
	public void setSignatureContentType(String signatureContentType) {
		this.signatureContentType = signatureContentType;
	}

	/**
	 * @return the ageFromDate
	 */
	public String getAgeFromDate() {
		return ageFromDate;
	}

	/**
	 * @param ageFromDate the ageFromDate to set
	 */
	public void setAgeFromDate(String ageFromDate) {
		this.ageFromDate = ageFromDate;
	}

	public String[] getEditStandardID() {
		return editStandardID;
	}

	public void setEditStandardID(String[] editStandardID) {
		this.editStandardID = editStandardID;
	}

	public String[] getEditExamID() {
		return editExamID;
	}

	public void setEditExamID(String[] editExamID) {
		this.editExamID = editExamID;
	}

	public String[] getEditSubject() {
		return editSubject;
	}

	public void setEditSubject(String[] editSubject) {
		this.editSubject = editSubject;
	}

	public String[] getEditExamDate() {
		return editExamDate;
	}

	public void setEditExamDate(String[] editExamDate) {
		this.editExamDate = editExamDate;
	}

	public String[] getTimeTableID1() {
		return TimeTableID1;
	}

	public void setTimeTableID1(String[] timeTableID1) {
		TimeTableID1 = timeTableID1;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTimeTableID() {
		return timeTableID;
	}

	public void setTimeTableID(int timeTableID) {
		this.timeTableID = timeTableID;
	}

	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String[] getNewExam() {
		return newExam;
	}

	public void setNewExam(String[] newExam) {
		this.newExam = newExam;
	}

	public String[] getNewDate() {
		return newDate;
	}

	public void setNewDate(String[] newDate) {
		this.newDate = newDate;
	}

	public String[] getNewsubjectID() {
		return newsubjectID;
	}

	public void setNewsubjectID(String[] newsubjectID) {
		this.newsubjectID = newsubjectID;
	}

	public int getAYClassID() {
		return AYClassID;
	}

	public void setAYClassID(int aYClassID) {
		AYClassID = aYClassID;
	}

	public String getTermIendDate() {
		return termIendDate;
	}

	public void setTermIendDate(String termIendDate) {
		this.termIendDate = termIendDate;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String[] getNewstandardID() {
		return newstandardID;
	}

	public void setNewstandardID(String[] newstandardID) {
		this.newstandardID = newstandardID;
	}

	public String[] getEditAddAttendance() {
		return editAddAttendance;
	}

	public void setEditAddAttendance(String[] editAddAttendance) {
		this.editAddAttendance = editAddAttendance;
	}

	public String[] getNewAddAttendance() {
		return newAddAttendance;
	}

	public void setNewAddAttendance(String[] newAddAttendance) {
		this.newAddAttendance = newAddAttendance;
	}

	public List<Integer> getSubjectListValues() {
		return subjectListValues;
	}

	public void setSubjectListValues(List<Integer> subjectListValues) {
		this.subjectListValues = subjectListValues;
	}

	public String getBoardLogoFileName() {
		return boardLogoFileName;
	}

	public void setBoardLogoFileName(String boardLogoFileName) {
		this.boardLogoFileName = boardLogoFileName;
	}

	public String getBoardLogoContentType() {
		return boardLogoContentType;
	}

	public void setBoardLogoContentType(String boardLogoContentType) {
		this.boardLogoContentType = boardLogoContentType;
	}

	public String getBoardLogoDBName() {
		return boardLogoDBName;
	}

	public void setBoardLogoDBName(String boardLogoDBName) {
		this.boardLogoDBName = boardLogoDBName;
	}

	public File getBoardLogo() {
		return boardLogo;
	}

	public void setBoardLogo(File boardLogo) {
		this.boardLogo = boardLogo;
	}

	public int getDeleteID() {
		return deleteID;
	}

	public void setDeleteID(int deleteID) {
		this.deleteID = deleteID;
	}

	public int getAttendanceID() {
		return attendanceID;
	}

	public void setAttendanceID(int attendanceID) {
		this.attendanceID = attendanceID;
	}

	public String[] getEditworkingDaysID() {
		return editworkingDaysID;
	}

	public void setEditworkingDaysID(String[] editworkingDaysID) {
		this.editworkingDaysID = editworkingDaysID;
	}

	public String[] getEditMonthID() {
		return editMonthID;
	}

	public void setEditMonthID(String[] editMonthID) {
		this.editMonthID = editMonthID;
	}

	public String getEditTerm() {
		return editTerm;
	}

	public void setEditTerm(String editTerm) {
		this.editTerm = editTerm;
	}

	public String[] getEditTermID() {
		return editTermID;
	}

	public void setEditTermID(String[] editTermID) {
		this.editTermID = editTermID;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getNewTerm() {
		return newTerm;
	}

	public void setNewTerm(String newTerm) {
		this.newTerm = newTerm;
	}

	public String[] getNewMonth() {
		return newMonth;
	}

	public void setNewMonth(String[] newMonth) {
		this.newMonth = newMonth;
	}

	public String[] getNewWorkdays() {
		return newWorkdays;
	}

	public void setNewWorkdays(String[] newWorkdays) {
		this.newWorkdays = newWorkdays;
	}

	public String[] getNewStandard() {
		return newStandard;
	}

	public void setNewStandard(String[] newStandard) {
		this.newStandard = newStandard;
	}

	public String[] getEditStandard() {
		return editStandard;
	}

	public void setEditStandard(String[] editStandard) {
		this.editStandard = editStandard;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}

	public String getWorkingMonth() {
		return workingMonth;
	}

	public void setWorkingMonth(String workingMonth) {
		this.workingMonth = workingMonth;
	}

	public String getSearchDivision() {
		return searchDivision;
	}

	public void setSearchDivision(String searchDivision) {
		this.searchDivision = searchDivision;
	}

	public int getDivisionID() {
		return DivisionID;
	}

	public void setDivisionID(int divisionID) {
		DivisionID = divisionID;
	}

	public String getSubjectList() {

		return subjectList;
	}

	public void setSubjectList(String subjectList) {
		this.subjectList = subjectList;
	}

	public String getSearchStandard() {
		return searchStandard;
	}

	public void setSearchStandard(String searchStandard) {
		this.searchStandard = searchStandard;
	}

	public int getStandardID() {
		return standardID;
	}

	public void setStandardID(int standardID) {
		this.standardID = standardID;
	}

	public int getSubjectID() {
		return SubjectID;
	}

	public void setSubjectID(int subjectID) {
		SubjectID = subjectID;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public int getTotalStrength() {
		return totalStrength;
	}

	public void setTotalStrength(int totalStrength) {
		this.totalStrength = totalStrength;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getStudentStrength() {
		return studentStrength;
	}

	public void setStudentStrength(int studentStrength) {
		this.studentStrength = studentStrength;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailPass
	 */
	public String getEmailPass() {
		return emailPass;
	}

	/**
	 * @param emailPass the emailPass to set
	 */
	public void setEmailPass(String emailPass) {
		this.emailPass = emailPass;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getTagline() {
		return tagline;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getLogoDBName() {
		return logoDBName;
	}

	public void setLogoDBName(String logoDBName) {
		this.logoDBName = logoDBName;
	}

	public String getLogoContentType() {
		return logoContentType;
	}

	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getOrganizationsID() {
		return OrganizationsID;
	}

	public void setOrganizationsID(int organizationsID) {
		OrganizationsID = organizationsID;
	}

	public String getSearchOrganization() {
		return searchOrganization;
	}

	public void setSearchOrganization(String searchOrganization) {
		this.searchOrganization = searchOrganization;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public File getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(File profilePic) {
		this.profilePic = profilePic;
	}

	public String getProfilePicFileName() {
		return profilePicFileName;
	}

	public void setProfilePicFileName(String profilePicFileName) {
		this.profilePicFileName = profilePicFileName;
	}

	public String getProfilePicDBName() {
		return profilePicDBName;
	}

	public void setProfilePicDBName(String profilePicDBName) {
		this.profilePicDBName = profilePicDBName;
	}

	public String getProfilePicContentType() {
		return profilePicContentType;
	}

	public String getSearchAcademicYear() {
		return searchAcademicYear;
	}

	public void setSearchAcademicYear(String searchAcademicYear) {
		this.searchAcademicYear = searchAcademicYear;
	}

	public int getAcademicYearID() {
		return AcademicYearID;
	}

	public void setAcademicYearID(int academicYearID) {
		AcademicYearID = academicYearID;
	}

	public void setProfilePicContentType(String profilePicContentType) {
		this.profilePicContentType = profilePicContentType;
	}

	public int getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(int organizationID) {
		this.organizationID = organizationID;
	}

}
