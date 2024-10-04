package com.kovidRMS.form;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class LibraryForm {
	private String activityStatus;
	private String searchUserName;
	private String searchStaffName;
	private String fullName;
	private String activity;
	private String searchLibrary;
	private int LibrariesID;
	private int libraryID;
	private int totalStrength;
	private int bookTypeID;
	private String type;
	private String searchBookType;
	private int genreID;
	private String genre;
	private String searchGenre;
	private int sectionID;
	private String section;
	private String searchSection;
	private int studentStrength;
	String level;
	private String[] bookCountArr;
	private String[] issueDaysArr;
	private String[] finePerDayArr;
	private String[] ruleForArr;
	private String[] bookCountEditArr;
	private String[] issueDaysEditArr;
	private String[] finePerDayEditArr;
	private String[] ruleForEditArr;
	private int bookCount;
	private int issueDays;
	private int finePerDay;
	private String ruleFor;
	private int ruleForID;
	private String[] ruleForNewID;
	private String[] newteacher1;
	private String[] newYear; 
	private String[] newStrartDate; 
	private String[] newEndDate;
	private String[] newConfID;
	private String[] editstartDate; 
	private String[] editendDate;
	private String[] edityearName;
	private String[] editConfText;
	private String searchVendor;
	private int vendorID;
	private String agency;
	private String vatNumber;
	private String registrationDate;
	private String[] genreArr;
	private String mobile;
	private String name;
	private String email;
	private int cupboardID;
	private int shelfID;
	private File excelFileNameNew;
	private int academicYearID;
	private String bookName;
	private String author; 
	private String editName;
	private int bookID;
	private String searchBooksName;
	private String searchCriteria;
	private int statusID;
	private int noOfCopies; 
	private String publication; 
	private String edition; 
	private String accNum; 
	private String[] newAccNum; 
	private String pages; 
	private String description; 
	private String barcode; 
	private String[] newBarcode;
	private String publicationYear; 
	private String regDate; 
	private String status; 
	private String dateInactive;
	private String location;
	private String colNo;
	private double price;
	private int ayclassID;
	private List<String> classNameListValues;
	private String className;
	private String[] newstatus;
	private String[] newdate;
	private String classNameID;
	private int studentID;
	private String expectedReturnDate;
	private String issueDate;
	private String returnDate;
	private int delayDays;
	private double fineAmount;
	private int staffID;
	private int staffIssueID;
	private int studentIssueID;
	private String bookScanValue;
	private String role;
	private InputStream fileInputStream;
	private String fileName;
	private String studentString;
	private String staffString;
	private String schoolSection;
	private String checkboxValue;
	private int voteCount;
	private String votingStatus;
	private String[] studentName;
	private String[] nameType;
	private String[] editStudentName;
	private String[] editNameType;
	private String[] houseColorID;
	private String[] updateNameType;
	private int headGirlID;
	private int headBoyID;
	private int houseCaptainRedID;
	private int houseCaptainBlueID;
	private int houseCaptainGreenID;
	private int houseCaptainYellowID;
	private String standard;
	private List<String> standardListValues;
	
	
	
	
	/**
	 * @return the standardListValues
	 */
	public List<String> getStandardListValues() {
		return standardListValues;
	}
	/**
	 * @param standardListValues the standardListValues to set
	 */
	public void setStandardListValues(List<String> standardListValues) {
		this.standardListValues = standardListValues;
	}
	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * @return the voteCount
	 */
	public int getVoteCount() {
		return voteCount;
	}
	/**
	 * @param voteCount the voteCount to set
	 */
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	/**
	 * @return the votingStatus
	 */
	public String getVotingStatus() {
		return votingStatus;
	}
	/**
	 * @param votingStatus the votingStatus to set
	 */
	public void setVotingStatus(String votingStatus) {
		this.votingStatus = votingStatus;
	}
	/**
	 * @return the studentName
	 */
	public String[] getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String[] studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the nameType
	 */
	public String[] getNameType() {
		return nameType;
	}
	/**
	 * @param nameType the nameType to set
	 */
	public void setNameType(String[] nameType) {
		this.nameType = nameType;
	}
	/**
	 * @return the editStudentName
	 */
	public String[] getEditStudentName() {
		return editStudentName;
	}
	/**
	 * @param editStudentName the editStudentName to set
	 */
	public void setEditStudentName(String[] editStudentName) {
		this.editStudentName = editStudentName;
	}
	/**
	 * @return the editNameType
	 */
	public String[] getEditNameType() {
		return editNameType;
	}
	/**
	 * @param editNameType the editNameType to set
	 */
	public void setEditNameType(String[] editNameType) {
		this.editNameType = editNameType;
	}
	/**
	 * @return the houseColorID
	 */
	public String[] getHouseColorID() {
		return houseColorID;
	}
	/**
	 * @param houseColorID the houseColorID to set
	 */
	public void setHouseColorID(String[] houseColorID) {
		this.houseColorID = houseColorID;
	}
	/**
	 * @return the updateNameType
	 */
	public String[] getUpdateNameType() {
		return updateNameType;
	}
	/**
	 * @param updateNameType the updateNameType to set
	 */
	public void setUpdateNameType(String[] updateNameType) {
		this.updateNameType = updateNameType;
	}
	/**
	 * @return the headGirlID
	 */
	public int getHeadGirlID() {
		return headGirlID;
	}
	/**
	 * @param headGirlID the headGirlID to set
	 */
	public void setHeadGirlID(int headGirlID) {
		this.headGirlID = headGirlID;
	}
	/**
	 * @return the headBoyID
	 */
	public int getHeadBoyID() {
		return headBoyID;
	}
	/**
	 * @param headBoyID the headBoyID to set
	 */
	public void setHeadBoyID(int headBoyID) {
		this.headBoyID = headBoyID;
	}
	/**
	 * @return the houseCaptainRedID
	 */
	public int getHouseCaptainRedID() {
		return houseCaptainRedID;
	}
	/**
	 * @param houseCaptainRedID the houseCaptainRedID to set
	 */
	public void setHouseCaptainRedID(int houseCaptainRedID) {
		this.houseCaptainRedID = houseCaptainRedID;
	}
	/**
	 * @return the houseCaptainBlueID
	 */
	public int getHouseCaptainBlueID() {
		return houseCaptainBlueID;
	}
	/**
	 * @param houseCaptainBlueID the houseCaptainBlueID to set
	 */
	public void setHouseCaptainBlueID(int houseCaptainBlueID) {
		this.houseCaptainBlueID = houseCaptainBlueID;
	}
	/**
	 * @return the houseCaptainGreenID
	 */
	public int getHouseCaptainGreenID() {
		return houseCaptainGreenID;
	}
	/**
	 * @param houseCaptainGreenID the houseCaptainGreenID to set
	 */
	public void setHouseCaptainGreenID(int houseCaptainGreenID) {
		this.houseCaptainGreenID = houseCaptainGreenID;
	}
	/**
	 * @return the houseCaptainYellowID
	 */
	public int getHouseCaptainYellowID() {
		return houseCaptainYellowID;
	}
	/**
	 * @param houseCaptainYellowID the houseCaptainYellowID to set
	 */
	public void setHouseCaptainYellowID(int houseCaptainYellowID) {
		this.houseCaptainYellowID = houseCaptainYellowID;
	}
	/**
	 * @return the checkboxValue
	 */
	public String getCheckboxValue() {
		return checkboxValue;
	}
	/**
	 * @param checkboxValue the checkboxValue to set
	 */
	public void setCheckboxValue(String checkboxValue) {
		this.checkboxValue = checkboxValue;
	}
	/**
	 * @return the studentString
	 */
	public String getStudentString() {
		return studentString;
	}
	/**
	 * @param studentString the studentString to set
	 */
	public void setStudentString(String studentString) {
		this.studentString = studentString;
	}
	/**
	 * @return the staffString
	 */
	public String getStaffString() {
		return staffString;
	}
	/**
	 * @param staffString the staffString to set
	 */
	public void setStaffString(String staffString) {
		this.staffString = staffString;
	}
	/**
	 * @return the schoolSection
	 */
	public String getSchoolSection() {
		return schoolSection;
	}
	/**
	 * @param schoolSection the schoolSection to set
	 */
	public void setSchoolSection(String schoolSection) {
		this.schoolSection = schoolSection;
	}
	/**
	 * @return the fileInputStream
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	/**
	 * @param fileInputStream the fileInputStream to set
	 */
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the bookScanValue
	 */
	public String getBookScanValue() {
		return bookScanValue;
	}
	/**
	 * @param bookScanValue the bookScanValue to set
	 */
	public void setBookScanValue(String bookScanValue) {
		this.bookScanValue = bookScanValue;
	}
	/**
	 * @return the studentIssueID
	 */
	public int getStudentIssueID() {
		return studentIssueID;
	}
	/**
	 * @param studentIssueID the studentIssueID to set
	 */
	public void setStudentIssueID(int studentIssueID) {
		this.studentIssueID = studentIssueID;
	}
	/**
	 * @return the staffID
	 */
	public int getStaffID() {
		return staffID;
	}
	/**
	 * @param staffID the staffID to set
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	/**
	 * @return the staffIssueID
	 */
	public int getStaffIssueID() {
		return staffIssueID;
	}
	/**
	 * @param staffIssueID the staffIssueID to set
	 */
	public void setStaffIssueID(int staffIssueID) {
		this.staffIssueID = staffIssueID;
	}
	/**
	 * @return the expectedReturnDate
	 */
	public String getExpectedReturnDate() {
		return expectedReturnDate;
	}
	/**
	 * @param expectedReturnDate the expectedReturnDate to set
	 */
	public void setExpectedReturnDate(String expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}
	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}
	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	/**
	 * @return the returnDate
	 */
	public String getReturnDate() {
		return returnDate;
	}
	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @return the delayDays
	 */
	public int getDelayDays() {
		return delayDays;
	}
	/**
	 * @param delayDays the delayDays to set
	 */
	public void setDelayDays(int delayDays) {
		this.delayDays = delayDays;
	}
	/**
	 * @return the fineAmount
	 */
	public double getFineAmount() {
		return fineAmount;
	}
	/**
	 * @param fineAmount the fineAmount to set
	 */
	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}
	/**
	 * @return the studentID
	 */
	public int getStudentID() {
		return studentID;
	}
	/**
	 * @param studentID the studentID to set
	 */
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	/**
	 * @return the classNameID
	 */
	public String getClassNameID() {
		return classNameID;
	}
	/**
	 * @param classNameID the classNameID to set
	 */
	public void setClassNameID(String classNameID) {
		this.classNameID = classNameID;
	}
	/**
	 * @return the newstatus
	 */
	public String[] getNewstatus() {
		return newstatus;
	}
	/**
	 * @param newstatus the newstatus to set
	 */
	public void setNewstatus(String[] newstatus) {
		this.newstatus = newstatus;
	}
	/**
	 * @return the newdate
	 */
	public String[] getNewdate() {
		return newdate;
	}
	/**
	 * @param newdate the newdate to set
	 */
	public void setNewdate(String[] newdate) {
		this.newdate = newdate;
	}
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
	 * @return the classNameListValues
	 */
	public List<String> getClassNameListValues() {
		return classNameListValues;
	}
	/**
	 * @param classNameListValues the classNameListValues to set
	 */
	public void setClassNameListValues(List<String> classNameListValues) {
		this.classNameListValues = classNameListValues;
	}
	/**
	 * @return the ayclassID
	 */
	public int getAyclassID() {
		return ayclassID;
	}
	/**
	 * @param ayclassID the ayclassID to set
	 */
	public void setAyclassID(int ayclassID) {
		this.ayclassID = ayclassID;
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
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the noOfCopies
	 */
	public int getNoOfCopies() {
		return noOfCopies;
	}
	/**
	 * @param noOfCopies the noOfCopies to set
	 */
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
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
	 * @return the newAccNum
	 */
	public String[] getNewAccNum() {
		return newAccNum;
	}
	/**
	 * @param newAccNum the newAccNum to set
	 */
	public void setNewAccNum(String[] newAccNum) {
		this.newAccNum = newAccNum;
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
	 * @return the newBarcode
	 */
	public String[] getNewBarcode() {
		return newBarcode;
	}
	/**
	 * @param newBarcode the newBarcode to set
	 */
	public void setNewBarcode(String[] newBarcode) {
		this.newBarcode = newBarcode;
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
	 * @return the statusID
	 */
	public int getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID the statusID to set
	 */
	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	/**
	 * @return the bookID
	 */
	public int getBookID() {
		return bookID;
	}
	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	/**
	 * @return the searchBooksName
	 */
	public String getSearchBooksName() {
		return searchBooksName;
	}
	/**
	 * @param searchBooksName the searchBooksName to set
	 */
	public void setSearchBooksName(String searchBooksName) {
		this.searchBooksName = searchBooksName;
	}
	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}
	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	/**
	 * @return the editName
	 */
	public String getEditName() {
		return editName;
	}
	/**
	 * @param editName the editName to set
	 */
	public void setEditName(String editName) {
		this.editName = editName;
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
	 * @return the excelFileNameNew
	 */
	public File getExcelFileNameNew() {
		return excelFileNameNew;
	}
	/**
	 * @param excelFileNameNew the excelFileNameNew to set
	 */
	public void setExcelFileNameNew(File excelFileNameNew) {
		this.excelFileNameNew = excelFileNameNew;
	}
	/**
	 * @return the academicYearID
	 */
	public int getAcademicYearID() {
		return academicYearID;
	}
	/**
	 * @param academicYearID the academicYearID to set
	 */
	public void setAcademicYearID(int academicYearID) {
		this.academicYearID = academicYearID;
	}
	/**
	 * @return the cupboardID
	 */
	public int getCupboardID() {
		return cupboardID;
	}
	/**
	 * @param cupboardID the cupboardID to set
	 */
	public void setCupboardID(int cupboardID) {
		this.cupboardID = cupboardID;
	}
	/**
	 * @return the shelfID
	 */
	public int getShelfID() {
		return shelfID;
	}
	/**
	 * @param shelfID the shelfID to set
	 */
	public void setShelfID(int shelfID) {
		this.shelfID = shelfID;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the activityStatus
	 */
	public String getActivityStatus() {
		return activityStatus;
	}
	/**
	 * @param activityStatus the activityStatus to set
	 */
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
	/**
	 * @return the searchUserName
	 */
	public String getSearchUserName() {
		return searchUserName;
	}
	/**
	 * @param searchUserName the searchUserName to set
	 */
	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}
	/**
	 * @return the searchStaffName
	 */
	public String getSearchStaffName() {
		return searchStaffName;
	}
	/**
	 * @param searchStaffName the searchStaffName to set
	 */
	public void setSearchStaffName(String searchStaffName) {
		this.searchStaffName = searchStaffName;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	/**
	 * @return the searchLibrary
	 */
	public String getSearchLibrary() {
		return searchLibrary;
	}
	/**
	 * @param searchLibrary the searchLibrary to set
	 */
	public void setSearchLibrary(String searchLibrary) {
		this.searchLibrary = searchLibrary;
	}
	/**
	 * @return the librariesID
	 */
	public int getLibrariesID() {
		return LibrariesID;
	}
	/**
	 * @param librariesID the librariesID to set
	 */
	public void setLibrariesID(int librariesID) {
		LibrariesID = librariesID;
	}
	/**
	 * @return the libraryID
	 */
	public int getLibraryID() {
		return libraryID;
	}
	/**
	 * @param libraryID the libraryID to set
	 */
	public void setLibraryID(int libraryID) {
		this.libraryID = libraryID;
	}
	/**
	 * @return the totalStrength
	 */
	public int getTotalStrength() {
		return totalStrength;
	}
	/**
	 * @param totalStrength the totalStrength to set
	 */
	public void setTotalStrength(int totalStrength) {
		this.totalStrength = totalStrength;
	}
	/**
	 * @return the bookTypeID
	 */
	public int getBookTypeID() {
		return bookTypeID;
	}
	/**
	 * @param bookTypeID the bookTypeID to set
	 */
	public void setBookTypeID(int bookTypeID) {
		this.bookTypeID = bookTypeID;
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
	 * @return the searchBookType
	 */
	public String getSearchBookType() {
		return searchBookType;
	}
	/**
	 * @param searchBookType the searchBookType to set
	 */
	public void setSearchBookType(String searchBookType) {
		this.searchBookType = searchBookType;
	}
	/**
	 * @return the genreID
	 */
	public int getGenreID() {
		return genreID;
	}
	/**
	 * @param genreID the genreID to set
	 */
	public void setGenreID(int genreID) {
		this.genreID = genreID;
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
	 * @return the searchGenre
	 */
	public String getSearchGenre() {
		return searchGenre;
	}
	/**
	 * @param searchGenre the searchGenre to set
	 */
	public void setSearchGenre(String searchGenre) {
		this.searchGenre = searchGenre;
	}
	/**
	 * @return the sectionID
	 */
	public int getSectionID() {
		return sectionID;
	}
	/**
	 * @param sectionID the sectionID to set
	 */
	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
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
	 * @return the searchSection
	 */
	public String getSearchSection() {
		return searchSection;
	}
	/**
	 * @param searchSection the searchSection to set
	 */
	public void setSearchSection(String searchSection) {
		this.searchSection = searchSection;
	}
	/**
	 * @return the studentStrength
	 */
	public int getStudentStrength() {
		return studentStrength;
	}
	/**
	 * @param studentStrength the studentStrength to set
	 */
	public void setStudentStrength(int studentStrength) {
		this.studentStrength = studentStrength;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the bookCountArr
	 */
	public String[] getBookCountArr() {
		return bookCountArr;
	}
	/**
	 * @param bookCountArr the bookCountArr to set
	 */
	public void setBookCountArr(String[] bookCountArr) {
		this.bookCountArr = bookCountArr;
	}
	/**
	 * @return the issueDaysArr
	 */
	public String[] getIssueDaysArr() {
		return issueDaysArr;
	}
	/**
	 * @param issueDaysArr the issueDaysArr to set
	 */
	public void setIssueDaysArr(String[] issueDaysArr) {
		this.issueDaysArr = issueDaysArr;
	}
	/**
	 * @return the finePerDayArr
	 */
	public String[] getFinePerDayArr() {
		return finePerDayArr;
	}
	/**
	 * @param finePerDayArr the finePerDayArr to set
	 */
	public void setFinePerDayArr(String[] finePerDayArr) {
		this.finePerDayArr = finePerDayArr;
	}
	/**
	 * @return the ruleForArr
	 */
	public String[] getRuleForArr() {
		return ruleForArr;
	}
	/**
	 * @param ruleForArr the ruleForArr to set
	 */
	public void setRuleForArr(String[] ruleForArr) {
		this.ruleForArr = ruleForArr;
	}
	/**
	 * @return the bookCountEditArr
	 */
	public String[] getBookCountEditArr() {
		return bookCountEditArr;
	}
	/**
	 * @param bookCountEditArr the bookCountEditArr to set
	 */
	public void setBookCountEditArr(String[] bookCountEditArr) {
		this.bookCountEditArr = bookCountEditArr;
	}
	/**
	 * @return the issueDaysEditArr
	 */
	public String[] getIssueDaysEditArr() {
		return issueDaysEditArr;
	}
	/**
	 * @param issueDaysEditArr the issueDaysEditArr to set
	 */
	public void setIssueDaysEditArr(String[] issueDaysEditArr) {
		this.issueDaysEditArr = issueDaysEditArr;
	}
	/**
	 * @return the finePerDayEditArr
	 */
	public String[] getFinePerDayEditArr() {
		return finePerDayEditArr;
	}
	/**
	 * @param finePerDayEditArr the finePerDayEditArr to set
	 */
	public void setFinePerDayEditArr(String[] finePerDayEditArr) {
		this.finePerDayEditArr = finePerDayEditArr;
	}
	/**
	 * @return the ruleForEditArr
	 */
	public String[] getRuleForEditArr() {
		return ruleForEditArr;
	}
	/**
	 * @param ruleForEditArr the ruleForEditArr to set
	 */
	public void setRuleForEditArr(String[] ruleForEditArr) {
		this.ruleForEditArr = ruleForEditArr;
	}
	/**
	 * @return the bookCount
	 */
	public int getBookCount() {
		return bookCount;
	}
	/**
	 * @param bookCount the bookCount to set
	 */
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	/**
	 * @return the issueDays
	 */
	public int getIssueDays() {
		return issueDays;
	}
	/**
	 * @param issueDays the issueDays to set
	 */
	public void setIssueDays(int issueDays) {
		this.issueDays = issueDays;
	}
	/**
	 * @return the finePerDay
	 */
	public int getFinePerDay() {
		return finePerDay;
	}
	/**
	 * @param finePerDay the finePerDay to set
	 */
	public void setFinePerDay(int finePerDay) {
		this.finePerDay = finePerDay;
	}
	/**
	 * @return the ruleFor
	 */
	public String getRuleFor() {
		return ruleFor;
	}
	/**
	 * @param ruleFor the ruleFor to set
	 */
	public void setRuleFor(String ruleFor) {
		this.ruleFor = ruleFor;
	}
	/**
	 * @return the ruleForID
	 */
	public int getRuleForID() {
		return ruleForID;
	}
	/**
	 * @param ruleForID the ruleForID to set
	 */
	public void setRuleForID(int ruleForID) {
		this.ruleForID = ruleForID;
	}
	/**
	 * @return the ruleForNewID
	 */
	public String[] getRuleForNewID() {
		return ruleForNewID;
	}
	/**
	 * @param ruleForNewID the ruleForNewID to set
	 */
	public void setRuleForNewID(String[] ruleForNewID) {
		this.ruleForNewID = ruleForNewID;
	}
	/**
	 * @return the newteacher1
	 */
	public String[] getNewteacher1() {
		return newteacher1;
	}
	/**
	 * @param newteacher1 the newteacher1 to set
	 */
	public void setNewteacher1(String[] newteacher1) {
		this.newteacher1 = newteacher1;
	}
	/**
	 * @return the newYear
	 */
	public String[] getNewYear() {
		return newYear;
	}
	/**
	 * @param newYear the newYear to set
	 */
	public void setNewYear(String[] newYear) {
		this.newYear = newYear;
	}
	/**
	 * @return the newStrartDate
	 */
	public String[] getNewStrartDate() {
		return newStrartDate;
	}
	/**
	 * @param newStrartDate the newStrartDate to set
	 */
	public void setNewStrartDate(String[] newStrartDate) {
		this.newStrartDate = newStrartDate;
	}
	/**
	 * @return the newEndDate
	 */
	public String[] getNewEndDate() {
		return newEndDate;
	}
	/**
	 * @param newEndDate the newEndDate to set
	 */
	public void setNewEndDate(String[] newEndDate) {
		this.newEndDate = newEndDate;
	}
	/**
	 * @return the newConfID
	 */
	public String[] getNewConfID() {
		return newConfID;
	}
	/**
	 * @param newConfID the newConfID to set
	 */
	public void setNewConfID(String[] newConfID) {
		this.newConfID = newConfID;
	}
	/**
	 * @return the editstartDate
	 */
	public String[] getEditstartDate() {
		return editstartDate;
	}
	/**
	 * @param editstartDate the editstartDate to set
	 */
	public void setEditstartDate(String[] editstartDate) {
		this.editstartDate = editstartDate;
	}
	/**
	 * @return the editendDate
	 */
	public String[] getEditendDate() {
		return editendDate;
	}
	/**
	 * @param editendDate the editendDate to set
	 */
	public void setEditendDate(String[] editendDate) {
		this.editendDate = editendDate;
	}
	/**
	 * @return the edityearName
	 */
	public String[] getEdityearName() {
		return edityearName;
	}
	/**
	 * @param edityearName the edityearName to set
	 */
	public void setEdityearName(String[] edityearName) {
		this.edityearName = edityearName;
	}
	/**
	 * @return the editConfText
	 */
	public String[] getEditConfText() {
		return editConfText;
	}
	/**
	 * @param editConfText the editConfText to set
	 */
	public void setEditConfText(String[] editConfText) {
		this.editConfText = editConfText;
	}
	/**
	 * @return the searchVendor
	 */
	public String getSearchVendor() {
		return searchVendor;
	}
	/**
	 * @param searchVendor the searchVendor to set
	 */
	public void setSearchVendor(String searchVendor) {
		this.searchVendor = searchVendor;
	}
	/**
	 * @return the vendorID
	 */
	public int getVendorID() {
		return vendorID;
	}
	/**
	 * @param vendorID the vendorID to set
	 */
	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}
	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}
	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}
	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	/**
	 * @return the registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	/**
	 * @return the genreArr
	 */
	public String[] getGenreArr() {
		return genreArr;
	}
	/**
	 * @param genreArr the genreArr to set
	 */
	public void setGenreArr(String[] genreArr) {
		this.genreArr = genreArr;
	}
	
	
	
}
