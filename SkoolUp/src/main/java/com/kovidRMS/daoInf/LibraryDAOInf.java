package com.kovidRMS.daoInf;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.kovidRMS.form.LibraryForm;
import com.kovidRMS.form.ReportRow;
import com.kovidRMS.form.StudentForm;

public interface LibraryDAOInf {

	HashMap<String, String> retrieveGenreList();

	boolean verifyRules(int libraryID);

	List<LibraryForm> retrieveRulesListByLibraryID(String rules);

	String insertRulesFor(String ruleFor, int bookCount, int issueDays, int finePerDay, String genre, int libraryID);

	String updateRulesFor(String ruleFor, int bookCount, int issueDays, int finePerDay, int ruleForID);

	List<LibraryForm> searchBookTypeList(String searchBookType);

	List<LibraryForm> retrieveExistingBookTypeList();

	List<LibraryForm> retrieveBookTypeListByID(int bookTypeID, String searchBookType);

	String insertBookType(LibraryForm form);

	String updateBookType(LibraryForm form);

	String rejectBookType(LibraryForm form);

	List<LibraryForm> searchGenreList(String searchGenre);

	List<LibraryForm> retrieveExistingGenreList();

	List<LibraryForm> retrieveGenreListByID(int genreID, String searchGenre);

	String insertGenre(LibraryForm form);

	String updateGenre(LibraryForm form);

	String rejectGenre(LibraryForm form);

	List<LibraryForm> searchSectionList(String searchSection);

	List<LibraryForm> retrieveExistingSectionList();

	List<LibraryForm> retrieveSectionListByID(int sectionID, String searchSection);

	String insertSection(LibraryForm form);

	String updateSection(LibraryForm form);

	String rejectSection(LibraryForm form);

	List<LibraryForm> searchVendorsList(String searchVendor);

	List<LibraryForm> retrieveExistingVendorsList();

	List<LibraryForm> retrieveVendorsListByID(int vendorID, String searchVendor);

	String insertVendor(LibraryForm form);

	String updateVendors(LibraryForm form);

	String rejectVendors(LibraryForm form);

	List<LibraryForm> retrieveCupboardList();

	List<LibraryForm> retrieveShelfList(int cupboardID);

	JSONObject retrieveCupboardName(String name);

	JSONObject retrieveShelfName(String name, int cupboardID);

	JSONObject retrieveShelfDetails(int cupboardID);

	int retrieveShelfID(String shelf, String cupboard);

	String importBookDetails(ReportRow reportRow, int shelfID, int vendorID, int academicYearID, int libraryID);

	int retrieveVendorsID(String vendorName);

	int retrieveBookIDNew();

	String updateBookStatusInStatusHistory(String status, String newDate, int bookID);

	String insertCupboard(String name);

	String updateCupboard( String name, int cupboardID);

	String insertShelf(String name, String genre, int cupboardID);

	String updateShelf(String name, String genre, int shelfID);

	HashMap<String, String> retrieveBookTypeList();

	HashMap<String, String> retrieveSectionList();

	HashMap<Integer, String> retrieveVendorsList();

	HashMap<Integer, String> retrieveCupboardDetailsList();

	String insertBooks(String name, String author, String genre, String publication, String edition,
			String accNum, String pages, String description, String barcode, String publicationYear, String regDate,
			String status, String dateInactive, String type, String section, int shelfID, String colNo, 
			int vendorID, double price, int libraryID, int academicYearID);

	int retrieveBookID(String name, String status);

	String updateBookStatus(String status, String newDate, int bookID);

	List<LibraryForm> searchBook(String searchBooksName, String searchCriteria, int academicYearID, int libraryID);

	List<LibraryForm> retriveExistingBookList(int academicYearID, int libraryID);

	List<LibraryForm> retreiveBOokDetailByBookID(int bookID);

	int retrievecupboardIDByBookID(int bookID);

	HashMap<Integer, String> retrieveShelvesDetailsList(int cupboardID);

	JSONObject retrieveStatusDetailsByBookID(int bookID);

	JSONObject deleteStatusRow(int statusID);

	JSONObject retrieveBookNameCount(String name, String author, int libraryID);

	JSONObject retrieveBookDetails(String name, String author, int libraryID);

	JSONObject retrieveStudentDetailsByClassID(int ayclassID);

	JSONObject retrieveClassLevelByClassID(int ayclassID);

	JSONObject retrieveRulesDetailsByClassLevel(String level, String genre, int libraryID);

	JSONObject retrieveStudentsBookIssueCount(int bookCount, int studentID);

	String insertIssuedBooks(LibraryForm form);

	String returnBooks(LibraryForm form);

	JSONObject retrieveBooksByScanValue(String bookScanValue);

	JSONObject retrieveStudentDetailsForBookReturn(int studentIssueID);

	JSONObject retrieveIssuedBooksByStudentID(int studentID);

	JSONObject retrieveStaffDetailsByRole(String role, int OrganizationID);

	JSONObject retrieveIssuedBooksByStaffID(int staffID);

	JSONObject retrieveStaffsBookIssueCount(int bookCount, int staffID);

	String insertIssuedBooksForStaff(LibraryForm form);

	JSONObject retrieveStaffDetailsForBookReturn(int staffIssueID);

	String returnStaffBooks(LibraryForm form);

	JSONObject retrieveStandardDivisionListByAcademicYearID(int academicYearID);

	List<LibraryForm> retrieveBooks(int shelfID);

	String transferBooks(LibraryForm form, int shelfID, int bookID);

	JSONObject retrieveDelayedStudents();

	JSONObject retrieveDelayedStaffs();

	public int retriveStudentCount();
	
	public int retriveStaffCount();

	public int retriveStudentsBooksIssuedCount(String status);
	
	public int retriveStaffsBooksIssuedCount(String status);
	
	public int retriveStudentsBooksDelayedCount();
	
	public int retriveStaffsBooksDelayedCount();

	public int retriveBookCount();
	
	public int retriveBookCountOfStatus(String status);

	JSONObject removeShelfRow(int shelfID);

	JSONObject removeCupboardRow(int cupboardID);

	JSONObject retrieveStudentsBookDetailsByClassID(int ayclassID);

	JSONObject retrieveStaffsBookDetailsByRole(String role, int organizationID);
	
	
}
