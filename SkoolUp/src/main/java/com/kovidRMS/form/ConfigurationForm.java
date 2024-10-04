package com.kovidRMS.form;

import java.io.File;
import java.io.InputStream;

public class ConfigurationForm {

	private int id;
	private int standardID;
	private int ExaminationID;
	private int AYClassID;
	private int divisionID;
	private int teacherID;
	private String teacherNameID;
	private String teacherName1;
	private String teacherName2;
	private int subjectID;
	private int academicYearID;
	private String academicYear;
	private int newAcademicYearID;
	private String activityStatus;
	private String term;
	private String examName;
	private String examType;
	private String check;
	private String[] editExamID;
	private String[] editExamNameID;
	private String[] editExamTypeID;
	private String[] editStartDateID;
	private String[] editEndDateID;
	private String[] newTerm;
	private String[] newExamName;
	private String[] newExamType;
	private String[] newStartDate;
	private String[] newEndDate;
	private int deleteID;
	private String[] editTerm;
	private String[] newsubject;
	private String[] newteacher1;
	private String[] newStandard;
	private String[] newAcademicID;
	private String[] newDivision;
	private String[] newTeacher;
	private String[] newConfID;
	private String[] editStandardID;
	private String[] editDivisionID;
	private String[] editTeacherID;
	private String[] AYClassID1;
	private String[] editConfText;
	private int aySubjectID;
	private String startDate;
	private String endDate;
	private String subject;
	private double totalMarks;
	private double scaleTo;
	private int gradeBased;
	private String[] newSubjectName;
	private String[] newSubjectID;
	private String[] newGradeBase;
	private String[] newMaximumMark;
	private String[] newScaleTo;
	private String[] editSubjectName;
	private String[] editSubjectID;
	private String[] editGradeBase;
	private String[] editMaximumMark;
	private String[] editScaleTo;
	private String[] editSubjectAssessmentID;
	private double marksObtained;
	private double marksScaled;
	private String grade;
	private int registrationID;
	private int subjectAssessmentID;
	private String standardDivName;
	private int studentID;
	private String studentName;
	private int rollNumber;
	private String[] marksObtainedArr;
	private String[] gradeObtainedArr;
	private String[] activityAssessmentArr;
	private String[] studID;
	private String[] studAssessmntID;
	private int studentAssessmentID;
	private String[] editMarksObtainedArr;
	private String[] editGradeObtainedArr;
	private String[] editStudID;
	private String subjAssmntID;
	private String studentNameID;
	private String dateOfBirth;
	private String dateOfBirthInWords;
	private String grNumber;
	private String standard;
	private String division;
	private String physicalActivity;
	private String creativeActivity;
	private String[] newSubjAssmntID;
	private String[] editSubjAssmntID;
	private String[] newTotalMarksID;
	private String[] editTotalMarks;
	private String termEndMarks;
	private String unitTestMarks;
	private String notebookMarks;
	private String subjectEnrichmentMarks;
	private String subjectEnrichmentMarks1;
	private String finalTotalMarks;
	private InputStream fileInputStream;
	private String fileName;
	private String workingMonth;
	private int daysPresent;
	private int presentDays;
	private String[] newAbsentFlag;
	private String[] editAbsentFlag;
	private int absentFlag;
	private String[] editRetestArr;
	private String[] editRegsitrationID;
	private int workingDays;
	private String studentWorkingDays;
	private String studentdaysPresent;
	private String termEndMarksObtained;
	private String unitTestMarksObtained;
	private String notebookMarksObtained;
	private String notebookMarks1;
	private String subjectEnrichmentMarksObtained;
	private String subjectEnrichmentMarks1Obtained;
	private String value;
	private String standardDivision;
	private String examDate;
	private String SubjectType;
	private String[] newsubjectAssmntID;
	private String[] newtotalMarks;
	private String[] newactivityID;
	private int activityID;
	private String activityString;
	private int activityAssessmentID;
	private int studentActivityAssessmentID;
	private String[] studentActivityAssessmentIDArr;
	private double outOfMarks;
	private String address;
	private double weight;
	private double height;
	private String aadhaar;
	private String bloodgroup;
	private String category;
	private String hasSpectacles;
	private String name;
	private String phone;
	private String house;
	private String religion;
	private String sibling;
	private String commutationMode;
	private String nameOfDriver;
	private String medCondition;
	private String emergencyPhone;
	private String emergencyName;
	private File excelFileName;
	private String finalBestMarks;
	private String finalReducedMarks;
	private String motherName;
	private String fatherName;
	private String remarks;
	private String serialNo;
	private String bookNo;
	private String reasons;
	private String dateOfCertificate;
	private String dateOfapplication;
	private String academicProgress;
	private String generalConduct;
	private String achievement;
	private String nccGuide;
	private String concession;
	private String duesMonths;
	private String feeConcession;
	private String wichClassWords;
	private String wichClass;
	private String higherClass;
	private String resultClass;
	private String result;
	private String lastStudiedClass;
	private String lastStudiedClassWords;
	private String lastStudiedClassNo;
	private String firstClass;
	private String admissionDate;
	private String lastSchoolClass;
	private String lastSchoolAttended;
	private String subCaste;
	private String caste;
	private String motherTongue;
	private String nationality;
	private String birthPlace;
	private String studentNo;
	private String[] studentStatus;
	private String portfolioMarks;
	private String portfolioFinalMarks;
	private String multipleAssessmentMarks;
	private String multipleAssessmentFinalMarks;
	private String portfolioMarks1;
	private String multipleAssessmentMarks1;

	
	
	/**
	 * @return the portfolioMarks1
	 */
	public String getPortfolioMarks1() {
		return portfolioMarks1;
	}

	/**
	 * @param portfolioMarks1 the portfolioMarks1 to set
	 */
	public void setPortfolioMarks1(String portfolioMarks1) {
		this.portfolioMarks1 = portfolioMarks1;
	}

	/**
	 * @return the multipleAssessmentMarks1
	 */
	public String getMultipleAssessmentMarks1() {
		return multipleAssessmentMarks1;
	}

	/**
	 * @param multipleAssessmentMarks1 the multipleAssessmentMarks1 to set
	 */
	public void setMultipleAssessmentMarks1(String multipleAssessmentMarks1) {
		this.multipleAssessmentMarks1 = multipleAssessmentMarks1;
	}

	/**
	 * @return the portfolioFinalMarks
	 */
	public String getPortfolioFinalMarks() {
		return portfolioFinalMarks;
	}

	/**
	 * @param portfolioFinalMarks the portfolioFinalMarks to set
	 */
	public void setPortfolioFinalMarks(String portfolioFinalMarks) {
		this.portfolioFinalMarks = portfolioFinalMarks;
	}

	/**
	 * @return the multipleAssessmentFinalMarks
	 */
	public String getMultipleAssessmentFinalMarks() {
		return multipleAssessmentFinalMarks;
	}

	/**
	 * @param multipleAssessmentFinalMarks the multipleAssessmentFinalMarks to set
	 */
	public void setMultipleAssessmentFinalMarks(String multipleAssessmentFinalMarks) {
		this.multipleAssessmentFinalMarks = multipleAssessmentFinalMarks;
	}

	/**
	 * @return the portfolioMarks
	 */
	public String getPortfolioMarks() {
		return portfolioMarks;
	}

	/**
	 * @param portfolioMarks the portfolioMarks to set
	 */
	public void setPortfolioMarks(String portfolioMarks) {
		this.portfolioMarks = portfolioMarks;
	}

	/**
	 * @return the multipleAssessmentMarks
	 */
	public String getMultipleAssessmentMarks() {
		return multipleAssessmentMarks;
	}

	/**
	 * @param multipleAssessmentMarks the multipleAssessmentMarks to set
	 */
	public void setMultipleAssessmentMarks(String multipleAssessmentMarks) {
		this.multipleAssessmentMarks = multipleAssessmentMarks;
	}

	/**
	 * @return the studentStatus
	 */
	public String[] getStudentStatus() {
		return studentStatus;
	}

	/**
	 * @param studentStatus the studentStatus to set
	 */
	public void setStudentStatus(String[] studentStatus) {
		this.studentStatus = studentStatus;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the bookNo
	 */
	public String getBookNo() {
		return bookNo;
	}

	/**
	 * @param bookNo the bookNo to set
	 */
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	/**
	 * @return the dateOfBirthInWords
	 */
	public String getDateOfBirthInWords() {
		return dateOfBirthInWords;
	}

	/**
	 * @param dateOfBirthInWords the dateOfBirthInWords to set
	 */
	public void setDateOfBirthInWords(String dateOfBirthInWords) {
		this.dateOfBirthInWords = dateOfBirthInWords;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the reasons
	 */
	public String getReasons() {
		return reasons;
	}

	/**
	 * @param reasons the reasons to set
	 */
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	/**
	 * @return the dateOfCertificate
	 */
	public String getDateOfCertificate() {
		return dateOfCertificate;
	}

	/**
	 * @param dateOfCertificate the dateOfCertificate to set
	 */
	public void setDateOfCertificate(String dateOfCertificate) {
		this.dateOfCertificate = dateOfCertificate;
	}

	/**
	 * @return the dateOfapplication
	 */
	public String getDateOfapplication() {
		return dateOfapplication;
	}

	/**
	 * @param dateOfapplication the dateOfapplication to set
	 */
	public void setDateOfapplication(String dateOfapplication) {
		this.dateOfapplication = dateOfapplication;
	}

	/**
	 * @return the academicProgress
	 */
	public String getAcademicProgress() {
		return academicProgress;
	}

	/**
	 * @param academicProgress the academicProgress to set
	 */
	public void setAcademicProgress(String academicProgress) {
		this.academicProgress = academicProgress;
	}

	/**
	 * @return the generalConduct
	 */
	public String getGeneralConduct() {
		return generalConduct;
	}

	/**
	 * @param generalConduct the generalConduct to set
	 */
	public void setGeneralConduct(String generalConduct) {
		this.generalConduct = generalConduct;
	}

	/**
	 * @return the achievement
	 */
	public String getAchievement() {
		return achievement;
	}

	/**
	 * @param achievement the achievement to set
	 */
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	/**
	 * @return the nccGuide
	 */
	public String getNccGuide() {
		return nccGuide;
	}

	/**
	 * @param nccGuide the nccGuide to set
	 */
	public void setNccGuide(String nccGuide) {
		this.nccGuide = nccGuide;
	}

	/**
	 * @return the concession
	 */
	public String getConcession() {
		return concession;
	}

	/**
	 * @param concession the concession to set
	 */
	public void setConcession(String concession) {
		this.concession = concession;
	}

	/**
	 * @return the duesMonths
	 */
	public String getDuesMonths() {
		return duesMonths;
	}

	/**
	 * @param duesMonths the duesMonths to set
	 */
	public void setDuesMonths(String duesMonths) {
		this.duesMonths = duesMonths;
	}

	/**
	 * @return the feeConcession
	 */
	public String getFeeConcession() {
		return feeConcession;
	}

	/**
	 * @param feeConcession the feeConcession to set
	 */
	public void setFeeConcession(String feeConcession) {
		this.feeConcession = feeConcession;
	}

	/**
	 * @return the wichClassWords
	 */
	public String getWichClassWords() {
		return wichClassWords;
	}

	/**
	 * @param wichClassWords the wichClassWords to set
	 */
	public void setWichClassWords(String wichClassWords) {
		this.wichClassWords = wichClassWords;
	}

	/**
	 * @return the wichClass
	 */
	public String getWichClass() {
		return wichClass;
	}

	/**
	 * @param wichClass the wichClass to set
	 */
	public void setWichClass(String wichClass) {
		this.wichClass = wichClass;
	}

	/**
	 * @return the higherClass
	 */
	public String getHigherClass() {
		return higherClass;
	}

	/**
	 * @param higherClass the higherClass to set
	 */
	public void setHigherClass(String higherClass) {
		this.higherClass = higherClass;
	}

	/**
	 * @return the resultClass
	 */
	public String getResultClass() {
		return resultClass;
	}

	/**
	 * @param resultClass the resultClass to set
	 */
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the lastStudiedClass
	 */
	public String getLastStudiedClass() {
		return lastStudiedClass;
	}

	/**
	 * @param lastStudiedClass the lastStudiedClass to set
	 */
	public void setLastStudiedClass(String lastStudiedClass) {
		this.lastStudiedClass = lastStudiedClass;
	}

	/**
	 * @return the lastStudiedClassWords
	 */
	public String getLastStudiedClassWords() {
		return lastStudiedClassWords;
	}

	/**
	 * @param lastStudiedClassWords the lastStudiedClassWords to set
	 */
	public void setLastStudiedClassWords(String lastStudiedClassWords) {
		this.lastStudiedClassWords = lastStudiedClassWords;
	}

	/**
	 * @return the lastStudiedClassNo
	 */
	public String getLastStudiedClassNo() {
		return lastStudiedClassNo;
	}

	/**
	 * @param lastStudiedClassNo the lastStudiedClassNo to set
	 */
	public void setLastStudiedClassNo(String lastStudiedClassNo) {
		this.lastStudiedClassNo = lastStudiedClassNo;
	}

	/**
	 * @return the firstClass
	 */
	public String getFirstClass() {
		return firstClass;
	}

	/**
	 * @param firstClass the firstClass to set
	 */
	public void setFirstClass(String firstClass) {
		this.firstClass = firstClass;
	}

	/**
	 * @return the admissionDate
	 */
	public String getAdmissionDate() {
		return admissionDate;
	}

	/**
	 * @param admissionDate the admissionDate to set
	 */
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	/**
	 * @return the lastSchoolClass
	 */
	public String getLastSchoolClass() {
		return lastSchoolClass;
	}

	/**
	 * @param lastSchoolClass the lastSchoolClass to set
	 */
	public void setLastSchoolClass(String lastSchoolClass) {
		this.lastSchoolClass = lastSchoolClass;
	}

	/**
	 * @return the lastSchoolAttended
	 */
	public String getLastSchoolAttended() {
		return lastSchoolAttended;
	}

	/**
	 * @param lastSchoolAttended the lastSchoolAttended to set
	 */
	public void setLastSchoolAttended(String lastSchoolAttended) {
		this.lastSchoolAttended = lastSchoolAttended;
	}

	/**
	 * @return the subCaste
	 */
	public String getSubCaste() {
		return subCaste;
	}

	/**
	 * @param subCaste the subCaste to set
	 */
	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
	}

	/**
	 * @return the caste
	 */
	public String getCaste() {
		return caste;
	}

	/**
	 * @param caste the caste to set
	 */
	public void setCaste(String caste) {
		this.caste = caste;
	}

	/**
	 * @return the motherTongue
	 */
	public String getMotherTongue() {
		return motherTongue;
	}

	/**
	 * @param motherTongue the motherTongue to set
	 */
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the birthPlace
	 */
	public String getBirthPlace() {
		return birthPlace;
	}

	/**
	 * @param birthPlace the birthPlace to set
	 */
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	

	/**
	 * @return the studentNo
	 */
	public String getStudentNo() {
		return studentNo;
	}

	/**
	 * @param studentNo the studentNo to set
	 */
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	/**
	 * @return the motherName
	 */
	public String getMotherName() {
		return motherName;
	}

	/**
	 * @param motherName the motherName to set
	 */
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	/**
	 * @return the fatherName
	 */
	public String getFatherName() {
		return fatherName;
	}

	/**
	 * @param fatherName the fatherName to set
	 */
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFinalBestMarks() {
		return finalBestMarks;
	}

	public void setFinalBestMarks(String finalBestMarks) {
		this.finalBestMarks = finalBestMarks;
	}

	public String getFinalReducedMarks() {
		return finalReducedMarks;
	}

	public void setFinalReducedMarks(String finalReducedMarks) {
		this.finalReducedMarks = finalReducedMarks;
	}

	public File getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(File excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}

	public String getMedCondition() {
		return medCondition;
	}

	public void setMedCondition(String medCondition) {
		this.medCondition = medCondition;
	}

	public String getCommutationMode() {
		return commutationMode;
	}

	public void setCommutationMode(String commutationMode) {
		this.commutationMode = commutationMode;
	}

	public String getNameOfDriver() {
		return nameOfDriver;
	}

	public void setNameOfDriver(String nameOfDriver) {
		this.nameOfDriver = nameOfDriver;
	}

	public String getSibling() {
		return sibling;
	}

	public void setSibling(String sibling) {
		this.sibling = sibling;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getBloodgroup() {
		return bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getHasSpectacles() {
		return hasSpectacles;
	}

	public void setHasSpectacles(String hasSpectacles) {
		this.hasSpectacles = hasSpectacles;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getOutOfMarks() {
		return outOfMarks;
	}

	public void setOutOfMarks(double outOfMarks) {
		this.outOfMarks = outOfMarks;
	}

	public String[] getStudentActivityAssessmentIDArr() {
		return studentActivityAssessmentIDArr;
	}

	public void setStudentActivityAssessmentIDArr(String[] studentActivityAssessmentIDArr) {
		this.studentActivityAssessmentIDArr = studentActivityAssessmentIDArr;
	}

	public int getStudentActivityAssessmentID() {
		return studentActivityAssessmentID;
	}

	public void setStudentActivityAssessmentID(int studentActivityAssessmentID) {
		this.studentActivityAssessmentID = studentActivityAssessmentID;
	}

	public String[] getActivityAssessmentArr() {
		return activityAssessmentArr;
	}

	public void setActivityAssessmentArr(String[] activityAssessmentArr) {
		this.activityAssessmentArr = activityAssessmentArr;
	}

	public int getActivityAssessmentID() {
		return activityAssessmentID;
	}

	public void setActivityAssessmentID(int activityAssessmentID) {
		this.activityAssessmentID = activityAssessmentID;
	}

	public String getActivityString() {
		return activityString;
	}

	public void setActivityString(String activityString) {
		this.activityString = activityString;
	}

	public int getActivityID() {
		return activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String[] getNewactivityID() {
		return newactivityID;
	}

	public void setNewactivityID(String[] newactivityID) {
		this.newactivityID = newactivityID;
	}

	public String[] getNewtotalMarks() {
		return newtotalMarks;
	}

	public void setNewtotalMarks(String[] newtotalMarks) {
		this.newtotalMarks = newtotalMarks;
	}

	public String[] getNewsubjectAssmntID() {
		return newsubjectAssmntID;
	}

	public void setNewsubjectAssmntID(String[] newsubjectAssmntID) {
		this.newsubjectAssmntID = newsubjectAssmntID;
	}

	public String getSubjectType() {
		return SubjectType;
	}

	public void setSubjectType(String subjectType) {
		SubjectType = subjectType;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String[] getNewAcademicID() {
		return newAcademicID;
	}

	public void setNewAcademicID(String[] newAcademicID) {
		this.newAcademicID = newAcademicID;
	}

	public String getStandardDivision() {
		return standardDivision;
	}

	public void setStandardDivision(String standardDivision) {
		this.standardDivision = standardDivision;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTermEndMarksObtained() {
		return termEndMarksObtained;
	}

	public void setTermEndMarksObtained(String termEndMarksObtained) {
		this.termEndMarksObtained = termEndMarksObtained;
	}

	public String getUnitTestMarksObtained() {
		return unitTestMarksObtained;
	}

	public void setUnitTestMarksObtained(String unitTestMarksObtained) {
		this.unitTestMarksObtained = unitTestMarksObtained;
	}

	public String getNotebookMarksObtained() {
		return notebookMarksObtained;
	}

	public void setNotebookMarksObtained(String notebookMarksObtained) {
		this.notebookMarksObtained = notebookMarksObtained;
	}
	
	public String getNotebookMarks1() {
		return notebookMarks1;
	}

	public void setNotebookMarks1(String notebookMarks1) {
		this.notebookMarks1 = notebookMarks1;
	}

	public String getSubjectEnrichmentMarksObtained() {
		return subjectEnrichmentMarksObtained;
	}

	public void setSubjectEnrichmentMarksObtained(String subjectEnrichmentMarksObtained) {
		this.subjectEnrichmentMarksObtained = subjectEnrichmentMarksObtained;
	}

	public String getSubjectEnrichmentMarks1Obtained() {
		return subjectEnrichmentMarks1Obtained;
	}

	public void setSubjectEnrichmentMarks1Obtained(String subjectEnrichmentMarks1Obtained) {
		this.subjectEnrichmentMarks1Obtained = subjectEnrichmentMarks1Obtained;
	}

	public String getSubjectEnrichmentMarks1() {
		return subjectEnrichmentMarks1;
	}

	public void setSubjectEnrichmentMarks1(String subjectEnrichmentMarks1) {
		this.subjectEnrichmentMarks1 = subjectEnrichmentMarks1;
	}

	public String getStudentdaysPresent() {
		return studentdaysPresent;
	}

	public void setStudentdaysPresent(String studentdaysPresent) {
		this.studentdaysPresent = studentdaysPresent;
	}

	public String getStudentWorkingDays() {
		return studentWorkingDays;
	}

	public void setStudentWorkingDays(String studentWorkingDays) {
		this.studentWorkingDays = studentWorkingDays;
	}

	public int getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}

	public String[] getEditRegsitrationID() {
		return editRegsitrationID;
	}

	public void setEditRegsitrationID(String[] editRegsitrationID) {
		this.editRegsitrationID = editRegsitrationID;
	}

	public String[] getEditRetestArr() {
		return editRetestArr;
	}

	public void setEditRetestArr(String[] editRetestArr) {
		this.editRetestArr = editRetestArr;
	}

	public String[] getEditAbsentFlag() {
		return editAbsentFlag;
	}

	public void setEditAbsentFlag(String[] editAbsentFlag) {
		this.editAbsentFlag = editAbsentFlag;
	}

	public int getAbsentFlag() {
		return absentFlag;
	}

	public void setAbsentFlag(int absentFlag) {
		this.absentFlag = absentFlag;
	}

	public String[] getNewAbsentFlag() {
		return newAbsentFlag;
	}

	public void setNewAbsentFlag(String[] newAbsentFlag) {
		this.newAbsentFlag = newAbsentFlag;
	}

	public int getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}

	public String getWorkingMonth() {
		return workingMonth;
	}

	public void setWorkingMonth(String workingMonth) {
		this.workingMonth = workingMonth;
	}

	public int getDaysPresent() {
		return daysPresent;
	}

	public void setDaysPresent(int daysPresent) {
		this.daysPresent = daysPresent;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTermEndMarks() {
		return termEndMarks;
	}

	public void setTermEndMarks(String termEndMarks) {
		this.termEndMarks = termEndMarks;
	}

	public String getUnitTestMarks() {
		return unitTestMarks;
	}

	public void setUnitTestMarks(String unitTestMarks) {
		this.unitTestMarks = unitTestMarks;
	}

	public String getNotebookMarks() {
		return notebookMarks;
	}

	public void setNotebookMarks(String notebookMarks) {
		this.notebookMarks = notebookMarks;
	}

	public String getSubjectEnrichmentMarks() {
		return subjectEnrichmentMarks;
	}

	public void setSubjectEnrichmentMarks(String subjectEnrichmentMarks) {
		this.subjectEnrichmentMarks = subjectEnrichmentMarks;
	}

	public String getFinalTotalMarks() {
		return finalTotalMarks;
	}

	public void setFinalTotalMarks(String finalTotalMarks) {
		this.finalTotalMarks = finalTotalMarks;
	}

	public String[] getEditTotalMarks() {
		return editTotalMarks;
	}

	public void setEditTotalMarks(String[] editTotalMarks) {
		this.editTotalMarks = editTotalMarks;
	}

	public String[] getNewTotalMarksID() {
		return newTotalMarksID;
	}

	public void setNewTotalMarksID(String[] newTotalMarksID) {
		this.newTotalMarksID = newTotalMarksID;
	}

	public String[] getNewSubjAssmntID() {
		return newSubjAssmntID;
	}

	public void setNewSubjAssmntID(String[] newSubjAssmntID) {
		this.newSubjAssmntID = newSubjAssmntID;
	}

	public String[] getEditSubjAssmntID() {
		return editSubjAssmntID;
	}

	public void setEditSubjAssmntID(String[] editSubjAssmntID) {
		this.editSubjAssmntID = editSubjAssmntID;
	}

	public String getPhysicalActivity() {
		return physicalActivity;
	}

	public void setPhysicalActivity(String physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public String getCreativeActivity() {
		return creativeActivity;
	}

	public void setCreativeActivity(String creativeActivity) {
		this.creativeActivity = creativeActivity;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGrNumber() {
		return grNumber;
	}

	public void setGrNumber(String grNumber) {
		this.grNumber = grNumber;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String[] getNewSubjectID() {
		return newSubjectID;
	}

	public void setNewSubjectID(String[] newSubjectID) {
		this.newSubjectID = newSubjectID;
	}

	public String[] getEditSubjectID() {
		return editSubjectID;
	}

	public void setEditSubjectID(String[] editSubjectID) {
		this.editSubjectID = editSubjectID;
	}

	public String[] getEditGradeObtainedArr() {
		return editGradeObtainedArr;
	}

	public void setEditGradeObtainedArr(String[] editGradeObtainedArr) {
		this.editGradeObtainedArr = editGradeObtainedArr;
	}

	public String[] getGradeObtainedArr() {
		return gradeObtainedArr;
	}

	public void setGradeObtainedArr(String[] gradeObtainedArr) {
		this.gradeObtainedArr = gradeObtainedArr;
	}

	public String getStudentNameID() {
		return studentNameID;
	}

	public void setStudentNameID(String studentNameID) {
		this.studentNameID = studentNameID;
	}

	public String getTeacherNameID() {
		return teacherNameID;
	}

	public void setTeacherNameID(String teacherNameID) {
		this.teacherNameID = teacherNameID;
	}
	
	public String getTeacherName1() {
		return teacherName1;
	}

	public void setTeacherName1(String teacherName1) {
		this.teacherName1 = teacherName1;
	}

	public String getTeacherName2() {
		return teacherName2;
	}

	public void setTeacherName2(String teacherName2) {
		this.teacherName2 = teacherName2;
	}

	public String getSubjAssmntID() {
		return subjAssmntID;
	}

	public void setSubjAssmntID(String subjAssmntID) {
		this.subjAssmntID = subjAssmntID;
	}

	public String[] getEditMarksObtainedArr() {
		return editMarksObtainedArr;
	}

	public void setEditMarksObtainedArr(String[] editMarksObtainedArr) {
		this.editMarksObtainedArr = editMarksObtainedArr;
	}

	public String[] getEditStudID() {
		return editStudID;
	}

	public void setEditStudID(String[] editStudID) {
		this.editStudID = editStudID;
	}

	public String[] getStudAssessmntID() {
		return studAssessmntID;
	}

	public void setStudAssessmntID(String[] studAssessmntID) {
		this.studAssessmntID = studAssessmntID;
	}

	public int getStudentAssessmentID() {
		return studentAssessmentID;
	}

	public void setStudentAssessmentID(int studentAssessmentID) {
		this.studentAssessmentID = studentAssessmentID;
	}

	public String[] getMarksObtainedArr() {
		return marksObtainedArr;
	}

	public void setMarksObtainedArr(String[] marksObtainedArr) {
		this.marksObtainedArr = marksObtainedArr;
	}

	public String[] getStudID() {
		return studID;
	}

	public void setStudID(String[] studID) {
		this.studID = studID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getStandardDivName() {
		return standardDivName;
	}

	public void setStandardDivName(String standardDivName) {
		this.standardDivName = standardDivName;
	}

	public double getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(double marksObtained) {
		this.marksObtained = marksObtained;
	}

	public double getMarksScaled() {
		return marksScaled;
	}

	public void setMarksScaled(double marksScaled) {
		this.marksScaled = marksScaled;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getRegistrationID() {
		return registrationID;
	}

	public void setRegistrationID(int registrationID) {
		this.registrationID = registrationID;
	}

	public int getSubjectAssessmentID() {
		return subjectAssessmentID;
	}

	public void setSubjectAssessmentID(int subjectAssessmentID) {
		this.subjectAssessmentID = subjectAssessmentID;
	}

	public String[] getNewSubjectName() {
		return newSubjectName;
	}

	public void setNewSubjectName(String[] newSubjectName) {
		this.newSubjectName = newSubjectName;
	}

	public String[] getNewGradeBase() {
		return newGradeBase;
	}

	public void setNewGradeBase(String[] newGradeBase) {
		this.newGradeBase = newGradeBase;
	}

	public String[] getNewMaximumMark() {
		return newMaximumMark;
	}

	public void setNewMaximumMark(String[] newMaximumMark) {
		this.newMaximumMark = newMaximumMark;
	}

	public String[] getNewScaleTo() {
		return newScaleTo;
	}

	public void setNewScaleTo(String[] newScaleTo) {
		this.newScaleTo = newScaleTo;
	}

	public String[] getEditSubjectName() {
		return editSubjectName;
	}

	public void setEditSubjectName(String[] editSubjectName) {
		this.editSubjectName = editSubjectName;
	}

	public String[] getEditGradeBase() {
		return editGradeBase;
	}

	public void setEditGradeBase(String[] editGradeBase) {
		this.editGradeBase = editGradeBase;
	}

	public String[] getEditMaximumMark() {
		return editMaximumMark;
	}

	public void setEditMaximumMark(String[] editMaximumMark) {
		this.editMaximumMark = editMaximumMark;
	}

	public String[] getEditScaleTo() {
		return editScaleTo;
	}

	public void setEditScaleTo(String[] editScaleTo) {
		this.editScaleTo = editScaleTo;
	}

	public String[] getEditSubjectAssessmentID() {
		return editSubjectAssessmentID;
	}

	public void setEditSubjectAssessmentID(String[] editSubjectAssessmentID) {
		this.editSubjectAssessmentID = editSubjectAssessmentID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public double getScaleTo() {
		return scaleTo;
	}

	public void setScaleTo(double scaleTo) {
		this.scaleTo = scaleTo;
	}

	public int getGradeBased() {
		return gradeBased;
	}

	public void setGradeBased(int gradeBased) {
		this.gradeBased = gradeBased;
	}

	public String[] getEditStartDateID() {
		return editStartDateID;
	}

	public void setEditStartDateID(String[] editStartDateID) {
		this.editStartDateID = editStartDateID;
	}

	public String[] getEditEndDateID() {
		return editEndDateID;
	}

	public void setEditEndDateID(String[] editEndDateID) {
		this.editEndDateID = editEndDateID;
	}

	public String[] getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(String[] newStartDate) {
		this.newStartDate = newStartDate;
	}

	public String[] getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(String[] newEndDate) {
		this.newEndDate = newEndDate;
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

	public String[] getEditConfText() {
		return editConfText;
	}

	public void setEditConfText(String[] editConfText) {
		this.editConfText = editConfText;
	}

	public int getAySubjectID() {
		return aySubjectID;
	}

	public void setAySubjectID(int aySubjectID) {
		this.aySubjectID = aySubjectID;
	}

	public String[] getNewConfID() {
		return newConfID;
	}

	public void setNewConfID(String[] newConfID) {
		this.newConfID = newConfID;
	}

	public String[] getAYClassID1() {
		return AYClassID1;
	}

	public void setAYClassID1(String[] aYClassID1) {
		AYClassID1 = aYClassID1;
	}

	public String[] getEditStandardID() {
		return editStandardID;
	}

	public void setEditStandardID(String[] editStandardID) {
		this.editStandardID = editStandardID;
	}

	public String[] getEditDivisionID() {
		return editDivisionID;
	}

	public void setEditDivisionID(String[] editDivisionID) {
		this.editDivisionID = editDivisionID;
	}

	public String[] getEditTeacherID() {
		return editTeacherID;
	}

	public void setEditTeacherID(String[] editTeacherID) {
		this.editTeacherID = editTeacherID;
	}

	public int getAYClassID() {
		return AYClassID;
	}

	public void setAYClassID(int aYClassID) {
		AYClassID = aYClassID;
	}

	public String[] getNewsubject() {
		return newsubject;
	}

	public void setNewsubject(String[] newsubject) {
		this.newsubject = newsubject;
	}

	public String[] getNewteacher1() {
		return newteacher1;
	}

	public void setNewteacher1(String[] newteacher1) {
		this.newteacher1 = newteacher1;
	}

	public String[] getNewStandard() {
		return newStandard;
	}

	public void setNewStandard(String[] newStandard) {
		this.newStandard = newStandard;
	}

	public String[] getNewDivision() {
		return newDivision;
	}

	public void setNewDivision(String[] newDivision) {
		this.newDivision = newDivision;
	}

	public String[] getNewTeacher() {
		return newTeacher;
	}

	public void setNewTeacher(String[] newTeacher) {
		this.newTeacher = newTeacher;
	}

	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public String[] getEditTerm() {
		return editTerm;
	}

	public void setEditTerm(String[] editTerm) {
		this.editTerm = editTerm;
	}

	public int getDeleteID() {
		return deleteID;
	}

	public void setDeleteID(int deleteID) {
		this.deleteID = deleteID;
	}

	public String[] getEditExamID() {
		return editExamID;
	}

	public void setEditExamID(String[] editExamID) {
		this.editExamID = editExamID;
	}

	public String[] getEditExamNameID() {
		return editExamNameID;
	}

	public void setEditExamNameID(String[] editExamNameID) {
		this.editExamNameID = editExamNameID;
	}

	public String[] getEditExamTypeID() {
		return editExamTypeID;
	}

	public void setEditExamTypeID(String[] editExamTypeID) {
		this.editExamTypeID = editExamTypeID;
	}

	public String[] getNewTerm() {
		return newTerm;
	}

	public void setNewTerm(String[] newTerm) {
		this.newTerm = newTerm;
	}

	public String[] getNewExamName() {
		return newExamName;
	}

	public void setNewExamName(String[] newExamName) {
		this.newExamName = newExamName;
	}

	public String[] getNewExamType() {
		return newExamType;
	}

	public void setNewExamType(String[] newExamType) {
		this.newExamType = newExamType;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getExaminationID() {
		return ExaminationID;
	}

	public void setExaminationID(int examinationID) {
		ExaminationID = examinationID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStandardID() {
		return standardID;
	}

	public void setStandardID(int standardID) {
		this.standardID = standardID;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public void setDivisionID(int divisionID) {
		this.divisionID = divisionID;
	}

	public int getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}

	public int getAcademicYearID() {
		return academicYearID;
	}

	public void setAcademicYearID(int academicYearID) {
		this.academicYearID = academicYearID;
	}

	
	
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public int getNewAcademicYearID() {
		return newAcademicYearID;
	}

	public void setNewAcademicYearID(int newAcademicYearID) {
		this.newAcademicYearID = newAcademicYearID;
	}

}
