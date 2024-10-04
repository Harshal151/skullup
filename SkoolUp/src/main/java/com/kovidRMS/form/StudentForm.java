package com.kovidRMS.form;

import java.io.InputStream;
import java.util.List;

public class StudentForm {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String gender;
	private String aadhaar;
	private String bloodgroup;
	private String category;
	private String hasSpectacles;
	private String activityStatus;
	private String phone;
	private String address;
	private String city;
	private String state;
	private String country;
	private int pinCode;
	private int studentID;
	private String grNumber;
	private String house;
	private String dateUpdated;
	private String religion;
	private String searchStudentName;
	private String fullName;
	private String name;
	private String subjectType;
	private String searchSubject;
	private int SubjectID;
	private String searchCommutation;
	private String commutationMode;
	private int commutationID;
	private String nameOfDriver;
	private String vehicleRegNumber;
	private String driverMobile;
	private String searchCriteria;
	private int rollNumber;
	private double weight;
	private double height;
	private int ayclassID;
	private String creativeActivities;
	private String physicalActivities;
	private String compulsoryActivities;
	private int divisionID;
	private int standardID;
	private String standard;
	private String division;
	private String studentName;
	private String standardName;
	private String divisionName;
	private int ayID;
	private int oldAyID;
	private String[] newStudentID;
	private String[] oldStandardID;
	private String[] oldDivisionID;
	private String[] newStandardID;
	private String[] newDivisionID;
	private String[] toRollNumber;
	private String[] newCreativeActivities;
	private String[] newPhysicalActivities;
	private String[] newCompulsoryActivities;
	private String[] newGrNumber;
	private String[] newHeight;
	private String[] newWeight;
	private String sibling;
	private String relation;
	private String mobile;
	private int primaryContact;
	private String occupation;
	private String emailId;
	private String parentfirstName;
	private String parentmiddleName;
	private String parentlastName;
	private String parentPhone;
	private String parentAddress;
	private int parentpinCode;
	private String parentcountry;
	private String parentstate;
	private String parentcity;
	private String motheroccupation;
	private int motherpinCode;
	private String mothercountry;
	private String motherstate;
	private String mothercity;
	private String motherAddress;
	private String motherPhone;
	private String mothermobile;
	private String motheremailId;
	private String motherrelation;
	private String motherfirstName;
	private String mothermiddleName;
	private String motherlastName;
	private String emergencyPhone;
	private String emergencyName;
	private String medCondition;
	private String[] newCondition;
	private int medConditionID;
	private String[] editmedCondition;
	private String[] editmedID;
	private String term;
	private String workingMonth;
	private int workingMonthID;
	private int daysPresent;
	private List<String> creativeActivitiesValues;
	private List<String> physicalActivitiesValues;
	private List<String> compulsoryActivitiesValues;
	private String[] daysPresentArr;
	private String[] editdaysPresentArr;
	private String[] studID;
	private String[] studAttndsID;
	private int studentAttendanceID;
	private String age;
	private String searchFields;
	private String checkBoxList;
	private String containsName;
	private String radioValue;
	private InputStream fileInputStream;
	private String fileName;
	private int inactiveacademicYearID;
	private int academicYearID;
	private String searchFieldsNew;
	private String ageValue;
	private String activity;
	private int activityID;
	private String[] newactivity;
	private String[] editactivityID;
	private String[] editactivity;
	private String marks;
	private String totalValue;
	private String totalValueString;
	private String gradeValue;
	private int registrationID;
	private int nextStandardID;
	private String nextStandard;
	private String birthPlace;
	private int sortOrder;

	/**
	 * @return the newCompulsoryActivities
	 */
	public String[] getNewCompulsoryActivities() {
		return newCompulsoryActivities;
	}

	/**
	 * @param newCompulsoryActivities the newCompulsoryActivities to set
	 */
	public void setNewCompulsoryActivities(String[] newCompulsoryActivities) {
		this.newCompulsoryActivities = newCompulsoryActivities;
	}

	/**
	 * @return the compulsoryActivitiesValues
	 */
	public List<String> getCompulsoryActivitiesValues() {
		return compulsoryActivitiesValues;
	}

	/**
	 * @param compulsoryActivitiesValues the compulsoryActivitiesValues to set
	 */
	public void setCompulsoryActivitiesValues(List<String> compulsoryActivitiesValues) {
		this.compulsoryActivitiesValues = compulsoryActivitiesValues;
	}

	/**
	 * @return the compulsoryActivities
	 */
	public String getCompulsoryActivities() {
		return compulsoryActivities;
	}

	/**
	 * @param compulsoryActivities the compulsoryActivities to set
	 */
	public void setCompulsoryActivities(String compulsoryActivities) {
		this.compulsoryActivities = compulsoryActivities;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	 * @return the nextStandardID
	 */
	public int getNextStandardID() {
		return nextStandardID;
	}

	/**
	 * @param nextStandardID the nextStandardID to set
	 */
	public void setNextStandardID(int nextStandardID) {
		this.nextStandardID = nextStandardID;
	}

	/**
	 * @return the nextStandard
	 */
	public String getNextStandard() {
		return nextStandard;
	}

	/**
	 * @param nextStandard the nextStandard to set
	 */
	public void setNextStandard(String nextStandard) {
		this.nextStandard = nextStandard;
	}

	/**
	 * @return the registrationID
	 */
	public int getRegistrationID() {
		return registrationID;
	}

	/**
	 * @param registrationID the registrationID to set
	 */
	public void setRegistrationID(int registrationID) {
		this.registrationID = registrationID;
	}

	public String getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(String gradeValue) {
		this.gradeValue = gradeValue;
	}

	public String getTotalValueString() {
		return totalValueString;
	}

	public void setTotalValueString(String totalValueString) {
		this.totalValueString = totalValueString;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String[] getEditactivityID() {
		return editactivityID;
	}

	public void setEditactivityID(String[] editactivityID) {
		this.editactivityID = editactivityID;
	}

	public String[] getEditactivity() {
		return editactivity;
	}

	public void setEditactivity(String[] editactivity) {
		this.editactivity = editactivity;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getActivityID() {
		return activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String[] getNewactivity() {
		return newactivity;
	}

	public void setNewactivity(String[] newactivity) {
		this.newactivity = newactivity;
	}

	public String getSearchFieldsNew() {
		return searchFieldsNew;
	}

	public void setSearchFieldsNew(String searchFieldsNew) {
		this.searchFieldsNew = searchFieldsNew;
	}

	public String getAgeValue() {
		return ageValue;
	}

	public void setAgeValue(String ageValue) {
		this.ageValue = ageValue;
	}

	public int getInactiveacademicYearID() {
		return inactiveacademicYearID;
	}

	public void setInactiveacademicYearID(int inactiveacademicYearID) {
		this.inactiveacademicYearID = inactiveacademicYearID;
	}

	public int getAcademicYearID() {
		return academicYearID;
	}

	public void setAcademicYearID(int academicYearID) {
		this.academicYearID = academicYearID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
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

	public String getRadioValue() {
		return radioValue;
	}

	public void setRadioValue(String radioValue) {
		this.radioValue = radioValue;
	}

	public String getContainsName() {
		return containsName;
	}

	public void setContainsName(String containsName) {
		this.containsName = containsName;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getCheckBoxList() {
		return checkBoxList;
	}

	public void setCheckBoxList(String checkBoxList) {
		this.checkBoxList = checkBoxList;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String[] getEditdaysPresentArr() {
		return editdaysPresentArr;
	}

	public void setEditdaysPresentArr(String[] editdaysPresentArr) {
		this.editdaysPresentArr = editdaysPresentArr;
	}

	public String[] getStudAttndsID() {
		return studAttndsID;
	}

	public void setStudAttndsID(String[] studAttndsID) {
		this.studAttndsID = studAttndsID;
	}

	public int getStudentAttendanceID() {
		return studentAttendanceID;
	}

	public void setStudentAttendanceID(int studentAttendanceID) {
		this.studentAttendanceID = studentAttendanceID;
	}

	public String[] getStudID() {
		return studID;
	}

	public void setStudID(String[] studID) {
		this.studID = studID;
	}

	public String[] getDaysPresentArr() {
		return daysPresentArr;
	}

	public void setDaysPresentArr(String[] daysPresentArr) {
		this.daysPresentArr = daysPresentArr;
	}

	public int getWorkingMonthID() {
		return workingMonthID;
	}

	public void setWorkingMonthID(int workingMonthID) {
		this.workingMonthID = workingMonthID;
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

	public List<String> getCreativeActivitiesValues() {
		return creativeActivitiesValues;
	}

	public void setCreativeActivitiesValues(List<String> creativeActivitiesValues) {
		this.creativeActivitiesValues = creativeActivitiesValues;
	}

	public List<String> getPhysicalActivitiesValues() {
		return physicalActivitiesValues;
	}

	public void setPhysicalActivitiesValues(List<String> physicalActivitiesValues) {
		this.physicalActivitiesValues = physicalActivitiesValues;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String[] getNewGrNumber() {
		return newGrNumber;
	}

	public void setNewGrNumber(String[] newGrNumber) {
		this.newGrNumber = newGrNumber;
	}

	public String[] getNewHeight() {
		return newHeight;
	}

	public void setNewHeight(String[] newHeight) {
		this.newHeight = newHeight;
	}

	public String[] getNewWeight() {
		return newWeight;
	}

	public void setNewWeight(String[] newWeight) {
		this.newWeight = newWeight;
	}

	public String[] getEditmedID() {
		return editmedID;
	}

	public void setEditmedID(String[] editmedID) {
		this.editmedID = editmedID;
	}

	public int getMedConditionID() {
		return medConditionID;
	}

	public void setMedConditionID(int medConditionID) {
		this.medConditionID = medConditionID;
	}

	public String[] getEditmedCondition() {
		return editmedCondition;
	}

	public void setEditmedCondition(String[] editmedCondition) {
		this.editmedCondition = editmedCondition;
	}

	public String[] getNewCondition() {
		return newCondition;
	}

	public void setNewCondition(String[] newCondition) {
		this.newCondition = newCondition;
	}

	public String getMedCondition() {
		return medCondition;
	}

	public void setMedCondition(String medCondition) {
		this.medCondition = medCondition;
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

	public String getMotheroccupation() {
		return motheroccupation;
	}

	public void setMotheroccupation(String motheroccupation) {
		this.motheroccupation = motheroccupation;
	}

	public int getMotherpinCode() {
		return motherpinCode;
	}

	public void setMotherpinCode(int motherpinCode) {
		this.motherpinCode = motherpinCode;
	}

	public String getMothercountry() {
		return mothercountry;
	}

	public void setMothercountry(String mothercountry) {
		this.mothercountry = mothercountry;
	}

	public String getMotherstate() {
		return motherstate;
	}

	public void setMotherstate(String motherstate) {
		this.motherstate = motherstate;
	}

	public String getMothercity() {
		return mothercity;
	}

	public void setMothercity(String mothercity) {
		this.mothercity = mothercity;
	}

	public String getMotherAddress() {
		return motherAddress;
	}

	public void setMotherAddress(String motherAddress) {
		this.motherAddress = motherAddress;
	}

	public String getMotherPhone() {
		return motherPhone;
	}

	public void setMotherPhone(String motherPhone) {
		this.motherPhone = motherPhone;
	}

	public String getMothermobile() {
		return mothermobile;
	}

	public void setMothermobile(String mothermobile) {
		this.mothermobile = mothermobile;
	}

	public String getMotheremailId() {
		return motheremailId;
	}

	public void setMotheremailId(String motheremailId) {
		this.motheremailId = motheremailId;
	}

	public String getMotherrelation() {
		return motherrelation;
	}

	public void setMotherrelation(String motherrelation) {
		this.motherrelation = motherrelation;
	}

	public String getMotherfirstName() {
		return motherfirstName;
	}

	public void setMotherfirstName(String motherfirstName) {
		this.motherfirstName = motherfirstName;
	}

	public String getMothermiddleName() {
		return mothermiddleName;
	}

	public void setMothermiddleName(String mothermiddleName) {
		this.mothermiddleName = mothermiddleName;
	}

	public String getMotherlastName() {
		return motherlastName;
	}

	public void setMotherlastName(String motherlastName) {
		this.motherlastName = motherlastName;
	}

	public String getParentfirstName() {
		return parentfirstName;
	}

	public void setParentfirstName(String parentfirstName) {
		this.parentfirstName = parentfirstName;
	}

	public String getParentmiddleName() {
		return parentmiddleName;
	}

	public void setParentmiddleName(String parentmiddleName) {
		this.parentmiddleName = parentmiddleName;
	}

	public String getParentlastName() {
		return parentlastName;
	}

	public void setParentlastName(String parentlastName) {
		this.parentlastName = parentlastName;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public String getParentAddress() {
		return parentAddress;
	}

	public void setParentAddress(String parentAddress) {
		this.parentAddress = parentAddress;
	}

	public int getParentpinCode() {
		return parentpinCode;
	}

	public void setParentpinCode(int parentpinCode) {
		this.parentpinCode = parentpinCode;
	}

	public String getParentcountry() {
		return parentcountry;
	}

	public void setParentcountry(String parentcountry) {
		this.parentcountry = parentcountry;
	}

	public String getParentstate() {
		return parentstate;
	}

	public void setParentstate(String parentstate) {
		this.parentstate = parentstate;
	}

	public String getParentcity() {
		return parentcity;
	}

	public void setParentcity(String parentcity) {
		this.parentcity = parentcity;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(int primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSibling() {
		return sibling;
	}

	public void setSibling(String sibling) {
		this.sibling = sibling;
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

	public String getCommutationMode() {
		return commutationMode;
	}

	public void setCommutationMode(String commutationMode) {
		this.commutationMode = commutationMode;
	}

	public String[] getNewStudentID() {
		return newStudentID;
	}

	public void setNewStudentID(String[] newStudentID) {
		this.newStudentID = newStudentID;
	}

	public String[] getOldStandardID() {
		return oldStandardID;
	}

	public void setOldStandardID(String[] oldStandardID) {
		this.oldStandardID = oldStandardID;
	}

	public String[] getOldDivisionID() {
		return oldDivisionID;
	}

	public void setOldDivisionID(String[] oldDivisionID) {
		this.oldDivisionID = oldDivisionID;
	}

	public String[] getNewStandardID() {
		return newStandardID;
	}

	public void setNewStandardID(String[] newStandardID) {
		this.newStandardID = newStandardID;
	}

	public String[] getNewDivisionID() {
		return newDivisionID;
	}

	public void setNewDivisionID(String[] newDivisionID) {
		this.newDivisionID = newDivisionID;
	}

	public String[] getToRollNumber() {
		return toRollNumber;
	}

	public void setToRollNumber(String[] toRollNumber) {
		this.toRollNumber = toRollNumber;
	}

	public String[] getNewCreativeActivities() {
		return newCreativeActivities;
	}

	public void setNewCreativeActivities(String[] newCreativeActivities) {
		this.newCreativeActivities = newCreativeActivities;
	}

	public String[] getNewPhysicalActivities() {
		return newPhysicalActivities;
	}

	public void setNewPhysicalActivities(String[] newPhysicalActivities) {
		this.newPhysicalActivities = newPhysicalActivities;
	}

	public int getAyID() {
		return ayID;
	}

	public void setAyID(int ayID) {
		this.ayID = ayID;
	}

	public int getOldAyID() {
		return oldAyID;
	}

	public void setOldAyID(int oldAyID) {
		this.oldAyID = oldAyID;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public void setDivisionID(int divisionID) {
		this.divisionID = divisionID;
	}

	public int getStandardID() {
		return standardID;
	}

	public void setStandardID(int standardID) {
		this.standardID = standardID;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public int getAyclassID() {
		return ayclassID;
	}

	public void setAyclassID(int ayclassID) {
		this.ayclassID = ayclassID;
	}

	public String getCreativeActivities() {
		return creativeActivities;
	}

	public void setCreativeActivities(String creativeActivities) {
		this.creativeActivities = creativeActivities;
	}

	public String getPhysicalActivities() {
		return physicalActivities;
	}

	public void setPhysicalActivities(String physicalActivities) {
		this.physicalActivities = physicalActivities;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getSearchCommutation() {
		return searchCommutation;
	}

	public void setSearchCommutation(String searchCommutation) {
		this.searchCommutation = searchCommutation;
	}

	public int getCommutationID() {
		return commutationID;
	}

	public void setCommutationID(int commutationID) {
		this.commutationID = commutationID;
	}

	public String getNameOfDriver() {
		return nameOfDriver;
	}

	public void setNameOfDriver(String nameOfDriver) {
		this.nameOfDriver = nameOfDriver;
	}

	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public int getSubjectID() {
		return SubjectID;
	}

	public void setSubjectID(int subjectID) {
		SubjectID = subjectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getSearchSubject() {
		return searchSubject;
	}

	public void setSearchSubject(String searchSubject) {
		this.searchSubject = searchSubject;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSearchStudentName() {
		return searchStudentName;
	}

	public void setSearchStudentName(String searchStudentName) {
		this.searchStudentName = searchStudentName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getGrNumber() {
		return grNumber;
	}

	public void setGrNumber(String grNumber) {
		this.grNumber = grNumber;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

}
