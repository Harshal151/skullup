package com.kovidRMS.form;

import java.util.HashMap;
import java.util.List;

public class ReportRow {
	String grNumber;
	String firstName;
	String middleName;
	String lastName;
	String standard;
	String division;
	int rollNumber;
	String address;
	String dateOfBirth;
	double weight;
	double height;
	String bloodgroup;
	String hasSpectacles;
	String name;
	String phone;
	String aadhaar;
	String house;
	String physicalActivities;
	String creativeActivities;
	String religion;
	String category;
	String siblingID;
	String commutationMode;
	String nameOfDriver;
	String medCondition;
	String AcademicYear;
	String term;
	String examName;
	String examType;
	String startDate;
	String endDate;
	String subject;
	String month;
	String workigDays;
	String classTeacher;
	String teacher1;
	String teacher2;
	String POccupation;
	String PemailId;
	String MOccupation;
	String MemailId;
	String PfirstName;
	String PmiddleName;
	String PlastName;
	String MfirstName;
	String MmiddleName;
	String MlastName;
	String Pmobile;
	String Mmobile;
	HashMap<String, List<String>> subjectMap;
	int totalMarks;
	int scaleTo;
	String gradeBased;
	private String bookName;
	private String author;
	private String genre;
	private String publication; 
	private String edition; 
	private String accNum;
	private String pages; 
	private String description; 
	private String barcode;
	private String publicationYear; 
	private String status; 
	private String regDate; 
	private String dateInactive; 
	private String type; 
	private String location;
	private String cupboard;
	private String shelf;
	private String section;
	private String colNo;
	private String vendorName;
	private String price;
	private String className;
	
	
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the colNo
	 */
	public String getColNo() {
		return colNo;
	}

	/**
	 * @param colNo the colNo to set
	 */
	public void setColNo(String colNo) {
		this.colNo = colNo;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the cupboard
	 */
	public String getCupboard() {
		return cupboard;
	}

	/**
	 * @param cupboard the cupboard to set
	 */
	public void setCupboard(String cupboard) {
		this.cupboard = cupboard;
	}

	/**
	 * @return the shelf
	 */
	public String getShelf() {
		return shelf;
	}

	/**
	 * @param shelf the shelf to set
	 */
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the dateInactive
	 */
	public String getDateInactive() {
		return dateInactive;
	}

	/**
	 * @param dateInactive the dateInactive to set
	 */
	public void setDateInactive(String dateInactive) {
		this.dateInactive = dateInactive;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the publication
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * @param publication the publication to set
	 */
	public void setPublication(String publication) {
		this.publication = publication;
	}

	/**
	 * @return the edition
	 */
	public String getEdition() {
		return edition;
	}

	/**
	 * @param edition the edition to set
	 */
	public void setEdition(String edition) {
		this.edition = edition;
	}

	/**
	 * @return the accNum
	 */
	public String getAccNum() {
		return accNum;
	}

	/**
	 * @param accNum the accNum to set
	 */
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	/**
	 * @return the pages
	 */
	public String getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(String pages) {
		this.pages = pages;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the publicationYear
	 */
	public String getPublicationYear() {
		return publicationYear;
	}

	/**
	 * @param publicationYear the publicationYear to set
	 */
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the totalMarks
	 */
	public int getTotalMarks() {
		return totalMarks;
	}

	/**
	 * @param totalMarks the totalMarks to set
	 */
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}

	/**
	 * @return the scaleTo
	 */
	public int getScaleTo() {
		return scaleTo;
	}

	/**
	 * @param scaleTo the scaleTo to set
	 */
	public void setScaleTo(int scaleTo) {
		this.scaleTo = scaleTo;
	}

	/**
	 * @return the gradeBased
	 */
	public String getGradeBased() {
		return gradeBased;
	}

	/**
	 * @param gradeBased the gradeBased to set
	 */
	public void setGradeBased(String gradeBased) {
		this.gradeBased = gradeBased;
	}

	public HashMap<String, List<String>> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(HashMap<String, List<String>> subjectMap) {
		this.subjectMap = subjectMap;
	}

	public String getPmobile() {
		return Pmobile;
	}

	public void setPmobile(String pmobile) {
		Pmobile = pmobile;
	}

	public String getMmobile() {
		return Mmobile;
	}

	public void setMmobile(String mmobile) {
		Mmobile = mmobile;
	}

	public String getPOccupation() {
		return POccupation;
	}

	public void setPOccupation(String pOccupation) {
		POccupation = pOccupation;
	}

	public String getPemailId() {
		return PemailId;
	}

	public void setPemailId(String pemailId) {
		PemailId = pemailId;
	}

	public String getMOccupation() {
		return MOccupation;
	}

	public void setMOccupation(String mOccupation) {
		MOccupation = mOccupation;
	}

	public String getMemailId() {
		return MemailId;
	}

	public void setMemailId(String memailId) {
		MemailId = memailId;
	}

	public String getPfirstName() {
		return PfirstName;
	}

	public void setPfirstName(String pfirstName) {
		PfirstName = pfirstName;
	}

	public String getPmiddleName() {
		return PmiddleName;
	}

	public void setPmiddleName(String pmiddleName) {
		PmiddleName = pmiddleName;
	}

	public String getPlastName() {
		return PlastName;
	}

	public void setPlastName(String plastName) {
		PlastName = plastName;
	}

	public String getMfirstName() {
		return MfirstName;
	}

	public void setMfirstName(String mfirstName) {
		MfirstName = mfirstName;
	}

	public String getMmiddleName() {
		return MmiddleName;
	}

	public void setMmiddleName(String mmiddleName) {
		MmiddleName = mmiddleName;
	}

	public String getMlastName() {
		return MlastName;
	}

	public void setMlastName(String mlastName) {
		MlastName = mlastName;
	}

	public String getGrNumber() {
		return grNumber;
	}

	public void setGrNumber(String grNumber) {
		this.grNumber = grNumber;
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

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getBloodgroup() {
		return bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getHasSpectacles() {
		return hasSpectacles;
	}

	public void setHasSpectacles(String hasSpectacles) {
		this.hasSpectacles = hasSpectacles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getPhysicalActivities() {
		return physicalActivities;
	}

	public void setPhysicalActivities(String physicalActivities) {
		this.physicalActivities = physicalActivities;
	}

	public String getCreativeActivities() {
		return creativeActivities;
	}

	public void setCreativeActivities(String creativeActivities) {
		this.creativeActivities = creativeActivities;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSiblingID() {
		return siblingID;
	}

	public void setSiblingID(String siblingID) {
		this.siblingID = siblingID;
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

	public String getMedCondition() {
		return medCondition;
	}

	public void setMedCondition(String medCondition) {
		this.medCondition = medCondition;
	}

	public String getAcademicYear() {
		return AcademicYear;
	}

	public void setAcademicYear(String academicYear) {
		AcademicYear = academicYear;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWorkigDays() {
		return workigDays;
	}

	public void setWorkigDays(String workigDays) {
		this.workigDays = workigDays;
	}

	public String getClassTeacher() {
		return classTeacher;
	}

	public void setClassTeacher(String classTeacher) {
		this.classTeacher = classTeacher;
	}

	public String getTeacher1() {
		return teacher1;
	}

	public void setTeacher1(String teacher1) {
		this.teacher1 = teacher1;
	}

	public String getTeacher2() {
		return teacher2;
	}

	public void setTeacher2(String teacher2) {
		this.teacher2 = teacher2;
	}

}
