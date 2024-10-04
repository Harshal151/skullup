package com.kovidRMS.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kovidRMS.daoInf.LibraryDAOInf;
import com.kovidRMS.form.LibraryForm;
import com.kovidRMS.form.ReportRow;
import com.kovidRMS.form.StudentForm;
import com.kovidRMS.util.*;

public class LibraryDAOImpl extends DAOConnection implements LibraryDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connection1 = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;
	Connection connection2 = null;
	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;
	String status = "error";

	@Override
	public HashMap<String, String> retrieveGenreList() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveGenreListQuery = QueryMaker.RETRIEVE_Genre_LIST;

			preparedStatement = connection.prepareStatement(retrieveGenreListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("genre"), resultSet.getString("genre"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	@Override
	public boolean verifyRules(int libraryID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyRulesQuery = QueryMaker.VERIFY_Rules;

			preparedStatement = connection.prepareStatement(verifyRulesQuery);

			preparedStatement.setInt(1, libraryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public List<LibraryForm> retrieveRulesListByLibraryID(String rules) {
		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {

			connection = getConnection();

			String retrieveRulesListByLibraryIDQuery = QueryMaker.RETRIEVE_Rules_List_By_LibraryID;

			preparedStatement = connection.prepareStatement(retrieveRulesListByLibraryIDQuery);

			preparedStatement.setString(1, rules);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LibraryForm();

				form.setRuleForID(resultSet.getInt("id"));
				form.setRuleFor(resultSet.getString("ruleFor"));
				form.setBookCount(resultSet.getInt("bookCount"));
				form.setIssueDays(resultSet.getInt("issueDays"));
				form.setFinePerDay(resultSet.getInt("finePerDay"));
				form.setGenre(resultSet.getString("genre"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Rules list from librariesID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	@Override
	public String insertRulesFor(String ruleFor, int bookCount, int issueDays, int finePerDay, String genre,
			int libraryID) {

		try {
			connection = getConnection();

			// username, password, userType, activityStatus, specialization,

			String insertRulesQuery = QueryMaker.INSERT_Rules_DETAIL;

			preparedStatement = connection.prepareStatement(insertRulesQuery);

			preparedStatement.setString(1, ruleFor);
			preparedStatement.setInt(2, bookCount);
			preparedStatement.setInt(3, issueDays);
			preparedStatement.setInt(4, finePerDay);
			preparedStatement.setString(5, genre);
			preparedStatement.setInt(6, libraryID);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting rules detail into table due to:::" + exception.getMessage());
			status = "input";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateRulesFor(String ruleFor, int bookCount, int issueDays, int finePerDay, int ruleForID) {

		try {
			connection = getConnection();

			String updateRulesForQuery = QueryMaker.UPDATE_Rules;

			preparedStatement = connection.prepareStatement(updateRulesForQuery);

			preparedStatement.setInt(1, bookCount);

			preparedStatement.setInt(2, issueDays);

			preparedStatement.setInt(3, finePerDay);

			preparedStatement.setInt(4, ruleForID);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Rules into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public List<LibraryForm> searchBookTypeList(String searchBookType) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String searchBookTypeListQuery = QueryMaker.SEARCH_BookType_LIST;

			preparedStatement = connection.prepareStatement(searchBookTypeListQuery);

			if (searchBookType.contains(" ")) {
				searchBookType = searchBookType.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchBookType + "%");

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();
				form.setBookTypeID(resultSet.getInt("id"));
				form.setType(resultSet.getString("type"));

				form.setSearchBookType(searchBookType);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Book Type list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<LibraryForm> retrieveExistingBookTypeList() {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String retrieveExistingBookTypeListQuery = QueryMaker.RETRIEVE_BookType_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingBookTypeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setBookTypeID(resultSet.getInt("id"));
				form.setType(resultSet.getString("type"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing BookType list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<LibraryForm> retrieveBookTypeListByID(int bookTypeID, String searchBookType) {
		List<LibraryForm> list = new ArrayList<LibraryForm>();
		LibraryForm form = null;

		try {

			connection = getConnection();

			String retrieveBookTypeListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_BookType_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveBookTypeListByIDQuery);

			preparedStatement.setInt(1, bookTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LibraryForm();

				form.setBookTypeID(resultSet.getInt("id"));

				form.setType(resultSet.getString("type"));

				form.setSearchBookType(searchBookType);

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Book Type list from Book Type ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	@Override
	public String insertBookType(LibraryForm form) {
		try {
			connection = getConnection();

			String insertBookTypeQuery = QueryMaker.INSERT_Book_Type;

			preparedStatement = connection.prepareStatement(insertBookTypeQuery);

			preparedStatement.setString(1, form.getType());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Book Type into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Book Type into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String updateBookType(LibraryForm form) {

		try {
			connection = getConnection();

			String updateBookTypeQuery = QueryMaker.UPDATE_CONFIGURATION_BookType;

			preparedStatement = connection.prepareStatement(updateBookTypeQuery);

			preparedStatement.setString(1, form.getType());
			preparedStatement.setInt(2, form.getBookTypeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("form.getType():" + form.getType());
			System.out.println("Successfully udpated Book Type into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Book Type into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectBookType(LibraryForm form) {

		try {
			connection = getConnection();

			String rejectBookTypeQuery = QueryMaker.REJECT_BookType;

			preparedStatement = connection.prepareStatement(rejectBookTypeQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, form.getBookTypeID());

			preparedStatement.execute();

			System.out.println("Book Type detail updated (For disable Book Type) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Book Type detail for disabling activity status of Book Type from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<LibraryForm> searchGenreList(String searchGenre) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String searchGenreListQuery = QueryMaker.SEARCH_Genre_LIST;

			preparedStatement = connection.prepareStatement(searchGenreListQuery);

			if (searchGenre.contains(" ")) {
				searchGenre = searchGenre.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchGenre + "%");

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setGenreID(resultSet.getInt("id"));
				form.setGenre(resultSet.getString("genre"));

				form.setSearchGenre(searchGenre);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Genre list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveExistingGenreList() {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String retrieveExistingGenreListQuery = QueryMaker.RETRIEVE_Genre_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingGenreListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setGenreID(resultSet.getInt("id"));
				form.setGenre(resultSet.getString("genre"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Genre list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveGenreListByID(int genreID, String searchGenre) {
		List<LibraryForm> list = new ArrayList<LibraryForm>();
		LibraryForm form = null;

		try {

			connection = getConnection();

			String retrieveBookTypeListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Genre_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveBookTypeListByIDQuery);

			preparedStatement.setInt(1, genreID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LibraryForm();

				form.setGenreID(resultSet.getInt("id"));

				form.setGenre(resultSet.getString("genre"));

				form.setSearchGenre(searchGenre);

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Genre list from Genre ID due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertGenre(LibraryForm form) {
		try {
			connection = getConnection();

			String insertGenreQuery = QueryMaker.INSERT_Genre;

			preparedStatement = connection.prepareStatement(insertGenreQuery);

			preparedStatement.setString(1, form.getGenre());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Genre into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Genre into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateGenre(LibraryForm form) {

		try {
			connection = getConnection();

			String updateGenreQuery = QueryMaker.UPDATE_CONFIGURATION_Genre;

			preparedStatement = connection.prepareStatement(updateGenreQuery);

			preparedStatement.setString(1, form.getGenre());
			preparedStatement.setInt(2, form.getGenreID());

			preparedStatement.execute();

			status = "success";
			// System.out.println("form.getType():"+form.getType());
			System.out.println("Successfully udpated Genre into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Genre into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectGenre(LibraryForm form) {

		try {
			connection = getConnection();

			String rejectGenreQuery = QueryMaker.REJECT_Genre;

			preparedStatement = connection.prepareStatement(rejectGenreQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, form.getGenreID());

			preparedStatement.execute();

			System.out.println("Genre detail updated (For disable Genre) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Genre detail for disabling activity status of Genre from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public List<LibraryForm> searchSectionList(String searchSection) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String searchSectionListQuery = QueryMaker.SEARCH_Section_LIST;

			preparedStatement = connection.prepareStatement(searchSectionListQuery);

			if (searchSection.contains(" ")) {
				searchSection = searchSection.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchSection + "%");

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setSectionID(resultSet.getInt("id"));
				form.setSection(resultSet.getString("section"));
				form.setStandard(resultSet.getString("standard"));

				form.setSearchSection(searchSection);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Genre list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveExistingSectionList() {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String retrieveExistingSectionListQuery = QueryMaker.RETRIEVE_Section_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingSectionListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setSectionID(resultSet.getInt("id"));
				form.setSection(resultSet.getString("section"));
				form.setStandard(resultSet.getString("standard"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Section list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveSectionListByID(int sectionID, String searchSection) {
		List<LibraryForm> list = new ArrayList<LibraryForm>();
		LibraryForm form = null;

		try {

			connection = getConnection();

			String retrieveSectionListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Section_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSectionListByIDQuery);

			preparedStatement.setInt(1, sectionID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LibraryForm();

				form.setSectionID(resultSet.getInt("id"));

				form.setSection(resultSet.getString("section"));

				List<String> OriginalStandardList = new ArrayList<String>();

				String Standard = resultSet.getString("standard");

				if (Standard != null) {
					String stdArr[] = Standard.split(",");

					for (int j = 0; j < stdArr.length; j++) {
						OriginalStandardList.add(stdArr[j].trim());
					}

					form.setStandardListValues(OriginalStandardList);

					form.setSearchSection(searchSection);
				}

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Genre list from Genre ID due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertSection(LibraryForm form) {
		try {
			connection = getConnection();

			String insertSectionQuery = QueryMaker.INSERT_Section;

			preparedStatement = connection.prepareStatement(insertSectionQuery);

			preparedStatement.setString(1, form.getSection());
			preparedStatement.setString(2, form.getStandard());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Section into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while inserting Section into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateSection(LibraryForm form) {

		try {
			connection = getConnection();

			String updateSectionQuery = QueryMaker.UPDATE_CONFIGURATION_Section;

			preparedStatement = connection.prepareStatement(updateSectionQuery);

			preparedStatement.setString(1, form.getSection());
			preparedStatement.setString(2, form.getStandard());
			preparedStatement.setInt(3, form.getSectionID());

			preparedStatement.execute();

			status = "success";
			System.out.println("Successfully udpated Section into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating Section into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectSection(LibraryForm form) {

		try {
			connection = getConnection();

			String rejectSectionQuery = QueryMaker.REJECT_Section;

			preparedStatement = connection.prepareStatement(rejectSectionQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, form.getSectionID());

			preparedStatement.execute();

			System.out.println("Section detail updated (For disable Section) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Section detail for disabling activity status of Section from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<LibraryForm> searchVendorsList(String searchVendor) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String searchVendorsListQuery = QueryMaker.SEARCH_Vendors_LIST;

			preparedStatement = connection.prepareStatement(searchVendorsListQuery);

			if (searchVendor.contains(" ")) {
				searchVendor = searchVendor.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchVendor + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setVendorID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setMobile(resultSet.getString("mobile"));

				form.setSearchVendor(searchVendor);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Vendors list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveExistingVendorsList() {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {
			connection = getConnection();

			String retrieveExistingVendorsListQuery = QueryMaker.RETRIEVE_Vendors_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingVendorsListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setVendorID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setMobile(resultSet.getString("mobile"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Vendors list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveVendorsListByID(int vendorID, String searchVendor) {
		List<LibraryForm> list = new ArrayList<LibraryForm>();
		LibraryForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {

			connection = getConnection();

			String retrieveVendorsListByIDQuery = QueryMaker.RETRIEVE_Vendors_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveVendorsListByIDQuery);

			preparedStatement.setInt(1, vendorID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setVendorID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setMobile(resultSet.getString("mobile"));
				form.setAgency(resultSet.getString("agency"));
				form.setEmail(resultSet.getString("email"));

				if (resultSet.getString("registrationDate") == null || resultSet.getString("registrationDate") == "") {
					form.setRegistrationDate("");

				} else if (resultSet.getString("registrationDate").isEmpty()) {
					form.setRegistrationDate("");

				} else {
					form.setRegistrationDate(
							dateFormat.format(dateToBeFormatted.parse(resultSet.getString("registrationDate"))));
				}

				// form.setRegistrationDate(resultSet.getString("registrationDate"));
				form.setVatNumber(resultSet.getString("vatNumber"));
				form.setSearchVendor(searchVendor);

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Vendor list from Vendor ID due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertVendor(LibraryForm form) {

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			connection = getConnection();

			String insertVendorQuery = QueryMaker.INSERT_Vendor;

			preparedStatement = connection.prepareStatement(insertVendorQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getAgency());
			preparedStatement.setString(3, form.getMobile());
			preparedStatement.setString(4, form.getEmail());
			preparedStatement.setString(5, form.getVatNumber());

			// Inserting Start Date
			if (form.getRegistrationDate() == null || form.getRegistrationDate() == ""
					|| form.getRegistrationDate().isEmpty()) {

				form.setRegistrationDate(null);
				preparedStatement.setString(6, form.getRegistrationDate());

			} else {
				preparedStatement.setString(6, dateToBeFormatted.format(dateFormat.parse(form.getRegistrationDate())));
			}

			preparedStatement.setString(7, ActivityStatus.ACTIVE);
			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Vendor details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Vendor details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateVendors(LibraryForm form) {

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			connection = getConnection();

			String updateVendorsQuery = QueryMaker.UPDATE_Vendors_Details;

			preparedStatement = connection.prepareStatement(updateVendorsQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getAgency());
			preparedStatement.setString(3, form.getMobile());
			preparedStatement.setString(4, form.getEmail());
			preparedStatement.setString(5, form.getVatNumber());

			// Inserting Start Date
			if (form.getRegistrationDate() == null || form.getRegistrationDate() == ""
					|| form.getRegistrationDate().isEmpty()) {

				form.setRegistrationDate(null);
				preparedStatement.setString(6, form.getRegistrationDate());

			} else {
				preparedStatement.setString(6, dateToBeFormatted.format(dateFormat.parse(form.getRegistrationDate())));
			}
			preparedStatement.setInt(7, form.getVendorID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated Vendor details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Vendor details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectVendors(LibraryForm form) {

		try {
			connection = getConnection();

			String rejectVendorsQuery = QueryMaker.REJECT_Vendors;

			preparedStatement = connection.prepareStatement(rejectVendorsQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, form.getVendorID());

			preparedStatement.execute();

			System.out.println("Class detail updated (For disable Vendor) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Vendor detail for disabling activity status of Vendor from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public List<LibraryForm> retrieveCupboardList() {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;
		try {
			connection = getConnection();

			String retrieveCupboardListQuery = QueryMaker.RETRIEVE_Cupboard_LIST;

			preparedStatement = connection.prepareStatement(retrieveCupboardListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setCupboardID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Cupboard list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retrieveShelfList(int cupboardID) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;
		try {
			connection = getConnection();

			String retrieveShelfListQuery = QueryMaker.RETRIEVE_Shelf_LIST;

			preparedStatement = connection.prepareStatement(retrieveShelfListQuery);

			preparedStatement.setInt(1, cupboardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setShelfID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setGenre(resultSet.getString("genre"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Shelves list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public String insertCupboard(String name) {

		try {
			connection = getConnection();

			String insertCupboardDetailsQuery = QueryMaker.INSERT_Cupboard_Details;

			preparedStatement = connection.prepareStatement(insertCupboardDetailsQuery);

			preparedStatement.setString(1, name);

			preparedStatement.execute();

			status = "success";
			System.out.println("New Cupboard Details inserted successfully into table Cupboard.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting New Cupboard Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateCupboard(String editName, int cupboardID) {

		try {
			connection = getConnection();

			String updateCupboardQuery = QueryMaker.UPDATE_Cupboard_Details;

			preparedStatement = connection.prepareStatement(updateCupboardQuery);

			preparedStatement.setString(1, editName);
			preparedStatement.setInt(2, cupboardID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Cupboard Details updated successfully into table Cupboard.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Cupboard Details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertShelf(String name, String genre, int cupboardID) {

		try {
			connection = getConnection();

			String insertShelfDetailsQuery = QueryMaker.INSERT_Shelf_Details;

			preparedStatement = connection.prepareStatement(insertShelfDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, genre);
			preparedStatement.setInt(3, cupboardID);

			preparedStatement.execute();

			status = "success";
			System.out.println("New Shelf Details inserted successfully into table Shelf.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting New Shelf Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateShelf(String editName, String genre, int shelfID) {

		try {
			connection = getConnection();

			String updateShelfQuery = QueryMaker.UPDATE_Shelf_Details;

			preparedStatement = connection.prepareStatement(updateShelfQuery);

			preparedStatement.setString(1, editName);
			preparedStatement.setString(2, genre);
			preparedStatement.setInt(3, shelfID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Shelf Details updated successfully into table Shelf.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Shelf Details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public JSONObject retrieveShelfDetails(int cupboardID) {

		LibraryForm form = new LibraryForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveShelfDetailsQuery = QueryMaker.RETRIEVE_Shelf_LIST;

			preparedStatement = connection.prepareStatement(retrieveShelfDetailsQuery);

			preparedStatement.setInt(1, cupboardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("shelfID", resultSet.getInt("id"));

				object.put("shelfName", resultSet.getString("name"));

				object.put("genreName", resultSet.getString("genre"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public HashMap<Integer, String> retrieveCupboardDetailsList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveCupboardDetailsListQuery = QueryMaker.RETRIEVE_Cupboard_LIST;

			preparedStatement = connection.prepareStatement(retrieveCupboardDetailsListQuery);

			// preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public HashMap<Integer, String> retrieveShelvesDetailsList(int cupboardID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveShelvesDetailsListQuery = QueryMaker.RETRIEVE_Shelf_LIST;

			preparedStatement = connection.prepareStatement(retrieveShelvesDetailsListQuery);

			preparedStatement.setInt(1, cupboardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	@Override
	public JSONObject retrieveCupboardName(String name) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveCupboardNameQuery = QueryMaker.RETRIEVE_Cupboard_Name;

			preparedStatement = connection.prepareStatement(retrieveCupboardNameQuery);

			preparedStatement.setString(1, name);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("cupboardID", resultSet.getInt("id"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public JSONObject retrieveShelfName(String name, int cupboardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveShelfNameQuery = QueryMaker.RETRIEVE_Shelf_Name;

			preparedStatement = connection.prepareStatement(retrieveShelfNameQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, cupboardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("shelfID", resultSet.getInt("id"));
				object.put("genre", resultSet.getString("genre"));
				object.put("cupboard", resultSet.getString("cupboard"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public int retrieveShelfID(String shelf, String cupboard) {

		int ShelfID = 0;

		try {

			connection = getConnection();

			String retrieveShelfIDQuery = QueryMaker.RETRIEVE_ShelfID;

			preparedStatement = connection.prepareStatement(retrieveShelfIDQuery);

			preparedStatement.setString(1, shelf);
			preparedStatement.setString(2, cupboard);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ShelfID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ShelfID;
	}

	@Override
	public String importBookDetails(ReportRow reportRow, int shelfID, int vendorID, int academicYearID, int libraryID) {

		// for my use only
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// for sevasadan use only
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertStudentDetailsQuery = QueryMaker.INSERT_Books_DETAILS_New;

			preparedStatement = connection.prepareStatement(insertStudentDetailsQuery);

			preparedStatement.setString(1, reportRow.getBookName());
			preparedStatement.setString(2, reportRow.getAuthor());
			preparedStatement.setString(3, reportRow.getGenre());
			preparedStatement.setString(4, reportRow.getPublication());
			preparedStatement.setString(5, reportRow.getEdition());
			preparedStatement.setString(6, reportRow.getAccNum());

			preparedStatement.setString(7, reportRow.getPages());
			preparedStatement.setString(8, reportRow.getDescription());
			preparedStatement.setString(9, reportRow.getBarcode());
			preparedStatement.setString(10, reportRow.getPublicationYear());

			// inserting Reg Date
			if (reportRow.getRegDate() == null || reportRow.getRegDate() == "") {

				reportRow.setRegDate(null);
				preparedStatement.setString(11, reportRow.getRegDate());

			} else if (reportRow.getRegDate().isEmpty()) {

				reportRow.setRegDate(null);
				preparedStatement.setString(11, reportRow.getRegDate());

			} else if (reportRow.getRegDate().equals(null)) {
				reportRow.setRegDate(null);
				preparedStatement.setString(11, reportRow.getRegDate());
			} else {

				preparedStatement.setString(11, dateToBeFormatted.format(dateFormat.parse(reportRow.getRegDate())));
			}

			// inserting Inactive Date
			if (reportRow.getDateInactive() == null || reportRow.getDateInactive() == "") {

				reportRow.setDateInactive(null);
				preparedStatement.setString(12, reportRow.getDateInactive());

			} else if (reportRow.getDateInactive().isEmpty()) {
				reportRow.setDateInactive(null);
				preparedStatement.setString(12, reportRow.getDateInactive());
			} else {

				preparedStatement.setString(12,
						dateToBeFormatted.format(dateFormat.parse(reportRow.getDateInactive())));
			}

			preparedStatement.setString(13, reportRow.getSection());
			preparedStatement.setString(14, reportRow.getStatus());
			preparedStatement.setString(15, reportRow.getType());
			preparedStatement.setString(16, reportRow.getClassName());
			preparedStatement.setInt(17, shelfID);
			preparedStatement.setString(18, reportRow.getColNo());
			preparedStatement.setInt(19, vendorID);

			double priceVal;

			if (reportRow.getPrice() == null || reportRow.getPrice() == "") {
				priceVal = 0;
			} else if (reportRow.getPrice().isEmpty()) {
				priceVal = 0;
			} else if (reportRow.getPrice().equals(null)) {
				priceVal = 0;
			} else {
				priceVal = Double.parseDouble(reportRow.getPrice());
			}

			preparedStatement.setDouble(20, priceVal);

			preparedStatement.setInt(21, academicYearID);

			preparedStatement.setInt(22, libraryID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Book details imported successfully into table Books.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while importing Student detail into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public int retrieveVendorsID(String vendorName) {

		int VendorsID = 0;

		try {

			connection = getConnection();

			String retrieveVendorsIDQuery = QueryMaker.RETRIEVE_VendorsID;

			preparedStatement = connection.prepareStatement(retrieveVendorsIDQuery);

			preparedStatement.setString(1, vendorName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				VendorsID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return VendorsID;
	}

	@Override
	public int retrieveBookIDNew() {

		int BookID = 0;

		try {

			connection = getConnection();

			String retrieveBookIDNewQuery = QueryMaker.RETRIEVE_BookID_New;

			preparedStatement = connection.prepareStatement(retrieveBookIDNewQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BookID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return BookID;
	}

	@Override
	public String updateBookStatusInStatusHistory(String status, String newDate, int bookID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String updateBookStatusQuery = QueryMaker.INSERT_Books_Status;

			preparedStatement = connection.prepareStatement(updateBookStatusQuery);

			preparedStatement.setString(1, status);

			preparedStatement.setInt(3, bookID);

			// Inserting Reg Date
			if (newDate == null || newDate == "" || newDate.isEmpty()) {

				preparedStatement.setString(2, newDate);

			} else {
				preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(newDate)));
			}

			preparedStatement.execute();

			status = "success";
			System.out.println("Book Status inserted successfully into table StatusHistory.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Book Status into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public HashMap<String, String> retrieveBookTypeList() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveBookTypeListQuery = QueryMaker.RETRIEVE_BookType_LIST;

			preparedStatement = connection.prepareStatement(retrieveBookTypeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("type"), resultSet.getString("type"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public HashMap<String, String> retrieveSectionList() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveSectionListQuery = QueryMaker.RETRIEVE_Section_LIST;

			preparedStatement = connection.prepareStatement(retrieveSectionListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				System.out.println("standard: " + resultSet.getString("standard"));
				if ((resultSet.getString("standard") == null) || (resultSet.getString("standard") == "")) {

					map.put(resultSet.getString("section"), resultSet.getString("section"));

				} else if (resultSet.getString("standard").isEmpty()) {

					map.put(resultSet.getString("section"), resultSet.getString("section"));

				} else {
					map.put(resultSet.getString("section") + " - " + resultSet.getString("standard"),
							resultSet.getString("section") + " - " + resultSet.getString("standard"));
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	@Override
	public HashMap<Integer, String> retrieveVendorsList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveVendorsListQuery = QueryMaker.RETRIEVE_Vendors_LIST;

			preparedStatement = connection.prepareStatement(retrieveVendorsListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	@Override
	public String insertBooks(String name, String author, String genre, String publication, String edition,
			String accNum, String pages, String description, String barcode, String publicationYear, String regDate,
			String status, String dateInactive, String type, String section, int shelfID, String colNo, int vendorID,
			double price, int libraryID, int academicYearID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertNewStudentDetailsQuery = QueryMaker.INSERT_Books_Details;

			preparedStatement = connection.prepareStatement(insertNewStudentDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, author);
			preparedStatement.setString(3, genre);
			preparedStatement.setString(4, publication);
			preparedStatement.setString(5, edition);
			preparedStatement.setString(6, accNum);
			preparedStatement.setString(7, pages);
			preparedStatement.setString(8, description);
			preparedStatement.setString(9, barcode);
			preparedStatement.setString(10, publicationYear);
			preparedStatement.setString(11, status);
			preparedStatement.setString(12, type);

			// Inserting Reg Date
			if (regDate == null || regDate == "" || regDate.isEmpty()) {

				preparedStatement.setString(13, regDate);

			} else {
				preparedStatement.setString(13, dateToBeFormatted.format(dateFormat.parse(regDate)));
			}

			System.out.println("dateInactive: " + dateInactive);
			// Inserting Date Inactive
			if (dateInactive == "" || dateInactive == null) {
				preparedStatement.setString(14, "");
				System.out.println("dateInactive3: " + dateInactive);
			} else if (dateInactive.isEmpty()) {
				System.out.println("dateInactive2: " + dateInactive);

				preparedStatement.setString(14, "");

			} else {
				System.out.println("dateInactive1: " + dateInactive);
				preparedStatement.setString(14, dateToBeFormatted.format(dateFormat.parse(dateInactive)));
			}

			preparedStatement.setString(15, section);
			preparedStatement.setInt(16, shelfID);
			preparedStatement.setString(17, colNo);
			preparedStatement.setInt(18, vendorID);
			preparedStatement.setDouble(19, price);
			preparedStatement.setInt(20, libraryID);
			preparedStatement.setInt(21, academicYearID);

			preparedStatement.execute();

			status = "success";
			System.out.println("New Book Details inserted successfully into table Book.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting New Student Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public int retrieveBookID(String name, String status) {

		int BookID = 0;

		try {

			connection = getConnection();

			String retrieveBookIDQuery = QueryMaker.RETRIEVE_BookID;

			preparedStatement = connection.prepareStatement(retrieveBookIDQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, status);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BookID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return BookID;
	}

	@Override
	public String updateBookStatus(String status, String date, int bookID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String updateBookStatusQuery = QueryMaker.INSERT_Books_Status;
			String updateBookStatusInBookTableQuery = QueryMaker.update_Books_Status_In_Book_Table;

			preparedStatement = connection.prepareStatement(updateBookStatusQuery);

			preparedStatement.setString(1, status);

			preparedStatement.setInt(3, bookID);

			// Inserting Reg Date
			if (date == null || date == "" || date.isEmpty()) {

				preparedStatement.setString(2, date);
			} else {
				preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(date)));
			}

			preparedStatement1 = connection.prepareStatement(updateBookStatusInBookTableQuery);

			preparedStatement1.setString(1, status);
			preparedStatement1.setInt(2, bookID);

			preparedStatement.execute();
			preparedStatement1.execute();

			status = "success";
			System.out.println("Book Status inserted successfully into table.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Book Status into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<LibraryForm> searchBook(String searchBooksName, String searchCriteria, int academicYearID,
			int libraryID) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;

		try {

			connection = getConnection();

			if (searchCriteria.equals("BookName")) {

				String searchBookByBookNameQuery = QueryMaker.SEARCH_Book_BY_BookName;

				preparedStatement = connection.prepareStatement(searchBookByBookNameQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");

				preparedStatement.setInt(2, academicYearID);

				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("Sections")) {

				if (searchBooksName.equals("primary")) {

					String searchBookByAuthorNameQuery = "SELECT * FROM Books WHERE accNum REGEXP '^[[:digit:]]' AND academicYearID = "
							+ academicYearID + " AND libraryID = " + libraryID + " ORDER BY id DESC";

					preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

					resultSet = preparedStatement.executeQuery();
				} else if (searchBooksName.equals("CD_primary")) {
					String searchBookByAuthorNameQuery = "SELECT * FROM Books WHERE accNum REGEXP '^P +[[:digit:]]+' AND academicYearID = "
							+ academicYearID + " AND libraryID = " + libraryID + " ORDER BY id DESC";

					preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

					resultSet = preparedStatement.executeQuery();
				} else if (searchBooksName.equals("CD_secondary")) {
					String searchBookByAuthorNameQuery = "SELECT * FROM Books WHERE accNum REGEXP '^S +[[:digit:]]+$' AND academicYearID = "
							+ academicYearID + " AND libraryID = " + libraryID + " ORDER BY id DESC";

					preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

					resultSet = preparedStatement.executeQuery();
				} else {
					String searchBookByAuthorNameQuery = QueryMaker.SEARCH_Book_By_AccessionNumber;

					preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

					preparedStatement.setString(1, searchBooksName + "%");
					;
					preparedStatement.setInt(2, academicYearID);
					preparedStatement.setInt(3, libraryID);

					resultSet = preparedStatement.executeQuery();
				}
			} else if (searchCriteria.equals("AuthorName")) {

				String searchBookByAuthorNameQuery = QueryMaker.SEARCH_Book_By_AuthorName;

				preparedStatement = connection.prepareStatement(searchBookByAuthorNameQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");
				preparedStatement.setInt(2, academicYearID);
				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("Genre")) {

				String searchBookByGenreQuery = QueryMaker.SEARCH_Book_By_Genre;

				preparedStatement = connection.prepareStatement(searchBookByGenreQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");
				preparedStatement.setInt(2, academicYearID);
				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("Type")) {

				String searchBookByTypeQuery = QueryMaker.SEARCH_Book_By_Type;

				preparedStatement = connection.prepareStatement(searchBookByTypeQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");
				preparedStatement.setInt(2, academicYearID);
				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("AccessionNumber")) {

				String searchBookByAccessionNumberQuery = QueryMaker.SEARCH_Book_By_AccessionNumber;

				preparedStatement = connection.prepareStatement(searchBookByAccessionNumberQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");
				preparedStatement.setInt(2, academicYearID);
				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("Status")) {

				String searchBookByStatusQuery = QueryMaker.SEARCH_Book_By_Status;

				preparedStatement = connection.prepareStatement(searchBookByStatusQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");
				preparedStatement.setInt(2, academicYearID);
				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();

			} else {

				String searchBookByBarcodeQuery = QueryMaker.SEARCH_Book_By_Barcodeo;

				preparedStatement = connection.prepareStatement(searchBookByBarcodeQuery);

				preparedStatement.setString(1, "%" + searchBooksName + "%");
				preparedStatement.setInt(2, academicYearID);
				preparedStatement.setInt(3, libraryID);

				resultSet = preparedStatement.executeQuery();
			}

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setBookID(resultSet.getInt("id"));
				form.setAccNum(resultSet.getString("accNum"));
				form.setName(resultSet.getString("name"));
				form.setGenre(resultSet.getString("genre"));
				form.setAuthor(resultSet.getString("author"));
				form.setType(resultSet.getString("type"));
				form.setBarcode(resultSet.getString("barcode"));
				form.setStatus(resultSet.getString("status"));
				form.setSearchBooksName(searchBooksName);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Books list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retriveExistingBookList(int academicYearID, int libraryID) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;
		try {
			connection = getConnection();

			String retriveExistingBookListQuery = QueryMaker.RETRIEVE_Book_LIST;

			preparedStatement = connection.prepareStatement(retriveExistingBookListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setInt(2, libraryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setBookID(resultSet.getInt("id"));
				form.setAccNum(resultSet.getString("accNum"));
				form.setName(resultSet.getString("name"));
				form.setAuthor(resultSet.getString("author"));
				form.setGenre(resultSet.getString("genre"));
				form.setType(resultSet.getString("type"));
				form.setBarcode(resultSet.getString("barcode"));
				form.setStatus(resultSet.getString("status"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Book list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LibraryForm> retreiveBOokDetailByBookID(int bookID) {

		List<LibraryForm> staffList = new ArrayList<LibraryForm>();
		LibraryForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("dd/MM/yyyy");

		try {
			connection = getConnection();

			String retreiveUserDetailByUserIDQuery = QueryMaker.RETREIVE_BooksDetails_BY_Book_ID;

			preparedStatement = connection.prepareStatement(retreiveUserDetailByUserIDQuery);
			preparedStatement.setInt(1, bookID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LibraryForm();

				form.setBookID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setAuthor(resultSet.getString("author"));
				form.setGenre(resultSet.getString("genre"));
				form.setPublication(resultSet.getString("publication"));
				form.setEdition(resultSet.getString("edition"));
				form.setAccNum(resultSet.getString("accNum"));
				form.setPages(resultSet.getString("pages"));
				form.setDescription(resultSet.getString("description"));
				form.setBarcode(resultSet.getString("barcode"));
				form.setPublicationYear(resultSet.getString("publicationYear"));

				// Inserting Reg Date
				if (resultSet.getString("regDate") == null || resultSet.getString("regDate") == "") {

					// form.setRegDate(null);
					form.setRegDate("");

				} else if (resultSet.getString("regDate").isEmpty()) {
					form.setRegDate("");

				} else {
					form.setRegDate(dateToBeFormatted.format(dateFormat.parse(resultSet.getString("regDate"))));
				}

				form.setStatus(resultSet.getString("status"));

				// Inserting Reg Date
				if (resultSet.getString("dateInactive") == null || resultSet.getString("dateInactive") == "") {

					// form.setRegDate(null);
					form.setDateInactive("");

				} else if (resultSet.getString("dateInactive").isEmpty()) {
					form.setDateInactive("");

				} else {
					form.setDateInactive(
							dateToBeFormatted.format(dateFormat.parse(resultSet.getString("dateInactive"))));
				}

				form.setType(resultSet.getString("type"));
				/* form.setLocation(resultSet.getString("location")); */
				form.setSection(resultSet.getString("section"));
				form.setShelfID(resultSet.getInt("shelfID"));
				form.setCupboardID(resultSet.getInt("cupboardID"));
				form.setColNo(resultSet.getString("colNo"));
				form.setVendorID(resultSet.getInt("vendorID"));
				form.setPrice(resultSet.getDouble("price"));

				staffList.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retreiving books details based on book id from database due to:::"
							+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return staffList;
	}

	@Override
	public int retrievecupboardIDByBookID(int bookID) {

		int CupboardID = 0;

		try {

			connection = getConnection();

			String retrieveCupboardIDQuery = QueryMaker.RETRIEVE_CupboardID;

			preparedStatement = connection.prepareStatement(retrieveCupboardIDQuery);

			preparedStatement.setInt(1, bookID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				CupboardID = resultSet.getInt("cupboardID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return CupboardID;
	}

	@Override
	public JSONObject retrieveStatusDetailsByBookID(int bookID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("dd/MM/yyyy");
		StudentForm form = new StudentForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveStatusDetailsByBookIDQuery = QueryMaker.RETRIEVE_StatusDetails_By_BookID;

			preparedStatement = connection.prepareStatement(retrieveStatusDetailsByBookIDQuery);

			preparedStatement.setInt(1, bookID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("status", resultSet.getString("status"));

				// Inserting Reg Date
				if (resultSet.getString("statusDate") == null || resultSet.getString("statusDate") == "") {

					object.put("statusDate", "");

				} else if (resultSet.getString("statusDate").isEmpty()) {
					object.put("statusDate", "");

				} else {
					object.put("statusDate",
							dateToBeFormatted.format(dateFormat.parse(resultSet.getString("statusDate"))));
				}

				object.put("statusID", resultSet.getInt("id"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public JSONObject deleteStatusRow(int statusID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteActivityRowQuery = QueryMaker.DELETE_Status_Row;

			preparedStatement = connection.prepareStatement(deleteActivityRowQuery);

			preparedStatement.setInt(1, statusID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveBookNameCount(String name, String author, int libraryID) {

		StudentForm form = new StudentForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		int value = 0;

		try {
			connection = getConnection();

			String retrieveBookDetailsQuery = QueryMaker.RETRIEVE_Book_Count_BY_Book_Name;

			preparedStatement = connection.prepareStatement(retrieveBookDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, author);
			preparedStatement.setInt(3, libraryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				value = resultSet.getInt("count");
				if (value != 0) {
					check1 = 1;
				}
				System.out.println("inside while loop: " + value + "--" + check1);
			}

			object = new JSONObject();

			object.put("value", value);
			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveBookDetails(String name, String author, int libraryID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("dd/MM/yyyy");
		StudentForm form = new StudentForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveBookDetailsQuery = QueryMaker.RETRIEVE_Book_Details_BY_Book_Name;

			preparedStatement = connection.prepareStatement(retrieveBookDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, author);
			preparedStatement.setInt(3, libraryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("name", resultSet.getString("name"));
				object.put("author", resultSet.getString("author"));
				object.put("genre", resultSet.getString("genre"));
				object.put("publication", resultSet.getString("publication"));
				object.put("edition", resultSet.getString("edition"));
				object.put("accNum", resultSet.getString("accNum"));
				object.put("pages", resultSet.getString("pages"));
				object.put("description", resultSet.getString("description"));
				object.put("barcode", resultSet.getString("barcode"));
				object.put("publicationYear", resultSet.getString("publicationYear"));
				object.put("status", resultSet.getString("status"));

				// Inserting Reg Date
				if (resultSet.getString("regDate") == null || resultSet.getString("regDate") == "") {

					// form.setRegDate(null);
					object.put("regDate", "");

				} else if (resultSet.getString("regDate").isEmpty()) {
					object.put("regDate", "");

				} else {
					object.put("regDate", dateToBeFormatted.format(dateFormat.parse(resultSet.getString("regDate"))));
				}

				// Inserting Reg Date
				if (resultSet.getString("dateInactive") == null || resultSet.getString("dateInactive") == "") {

					// form.setRegDate(null);
					object.put("dateInactive", "");

				} else if (resultSet.getString("dateInactive").isEmpty()) {
					object.put("dateInactive", "");

				} else {
					object.put("dateInactive",
							dateToBeFormatted.format(dateFormat.parse(resultSet.getString("dateInactive"))));
				}

				object.put("type", resultSet.getString("type"));
				object.put("section", resultSet.getString("section"));

				object.put("shelfID", resultSet.getInt("shelfID"));
				object.put("shelf", resultSet.getString("shelf"));
				object.put("cupboardID", resultSet.getInt("cupboardID"));
				object.put("cupboard", resultSet.getString("cupboard"));
				object.put("colNo", resultSet.getString("colNo"));
				object.put("vendorID", resultSet.getInt("vendorID"));
				object.put("vendor", resultSet.getString("vendor"));
				object.put("price", resultSet.getString("price"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveStudentDetailsByClassID(int ayclassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStudentDetailsByClassIDQuery = QueryMaker.RETRIEVE_Student_Details_By_ClassID;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsByClassIDQuery);

			preparedStatement.setInt(1, ayclassID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("studentName", resultSet.getString("studentName"));
				object.put("studentID", resultSet.getInt("id"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveClassLevelByClassID(int ayclassID) {
		System.out.println("ayclassID: " + ayclassID);
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveClassLevelByClassIDQuery = QueryMaker.RETRIEVE_Class_Level_By_ClassID;

			preparedStatement = connection.prepareStatement(retrieveClassLevelByClassIDQuery);

			preparedStatement.setInt(1, ayclassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("level", resultSet.getString("stage"));

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveRulesDetailsByClassLevel(String level, String genre, int libraryID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveRulesDetailsByClassLevelQuery = "SELECT * FROM Rules WHERE ruleFor LIKE '%" + level
					+ "%' AND genre = '" + genre + "' AND libraryID = " + libraryID + "";
			preparedStatement = connection.prepareStatement(retrieveRulesDetailsByClassLevelQuery);
			System.out.println("Inside impl......" + retrieveRulesDetailsByClassLevelQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				System.out.println("values are: " + resultSet.getInt("bookCount") + "--" + resultSet.getInt("issueDays")
						+ "--" + resultSet.getDouble("finePerDay"));

				check1 = 1;

				object = new JSONObject();

				object.put("bookCount", resultSet.getInt("bookCount"));
				object.put("issueDays", resultSet.getInt("issueDays"));
				object.put("finePerDay", resultSet.getDouble("finePerDay"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
				System.out.println("Inside check1......");
			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveStudentsBookIssueCount(int bookCount, int studentID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;
		int NewCount = 0;

		try {
			connection = getConnection();

			String retrieveStudentsBookIssueCountQuery = QueryMaker.RETRIEVE_Students_Book_Issue_Count;

			preparedStatement = connection.prepareStatement(retrieveStudentsBookIssueCountQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				NewCount = resultSet.getInt("count");

				array.add(object);

				values.put("Release", array);
			}

			if (NewCount == bookCount) {

				object = new JSONObject();

				check1 = 1;

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			} else {

				object = new JSONObject();

				check1 = 0;

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public String insertIssuedBooks(LibraryForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new java.util.Date();

		String NewDate = dateFormat.format(date);

		try {

			connection = getConnection();

			String insertIssuedBooksQuery = QueryMaker.Issue_Book_Details;

			String updateBookStatusInBookTableQuery = QueryMaker.update_Books_Status_In_Book_Table;

			String editBookStatusQuery = QueryMaker.INSERT_Books_Status;

			preparedStatement = connection.prepareStatement(insertIssuedBooksQuery);

			preparedStatement.setInt(1, form.getStudentID());

			preparedStatement.setInt(2, form.getBookID());

			// Inserting Issue Date
			if (form.getIssueDate() == null || form.getIssueDate() == "" || form.getIssueDate().isEmpty()) {

				form.setIssueDate(null);
				preparedStatement.setString(3, form.getIssueDate());

			} else {

				preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(form.getIssueDate())));
			}

			// Inserting ExpectedReturn Date
			if (form.getExpectedReturnDate() == null || form.getExpectedReturnDate() == ""
					|| form.getExpectedReturnDate().isEmpty()) {

				form.setExpectedReturnDate(null);
				preparedStatement.setString(4, form.getExpectedReturnDate());

			} else {

				preparedStatement.setString(4,
						dateToBeFormatted.format(dateFormat.parse(form.getExpectedReturnDate())));
			}

			preparedStatement.setString(5, ActivityStatus.Issued);

			preparedStatement1 = connection.prepareStatement(updateBookStatusInBookTableQuery);

			preparedStatement1.setString(1, ActivityStatus.Issued);
			preparedStatement1.setInt(2, form.getBookID());

			preparedStatement2 = connection.prepareStatement(editBookStatusQuery);

			preparedStatement2.setString(1, ActivityStatus.Issued);
			preparedStatement2.setString(2, dateToBeFormatted.format(dateFormat.parse(NewDate)));
			preparedStatement2.setInt(3, form.getBookID());

			preparedStatement.execute();
			preparedStatement1.execute();
			preparedStatement2.execute();

			status = "success";

			System.out.println("Successfully Issued Books detail into table");

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while Issuing Books into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String returnBooks(LibraryForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new java.util.Date();

		String NewDate = dateFormat.format(date);

		try {

			connection = getConnection();

			String returnBooksQuery = QueryMaker.Return_Book_Details;

			String updateBookStatusInBookTableQuery = QueryMaker.update_Books_Status_In_Book_Table;

			String editBookStatusQuery = QueryMaker.INSERT_Books_Status;

			preparedStatement = connection.prepareStatement(returnBooksQuery);

			// Inserting Issue Date
			if (form.getReturnDate() == null || form.getReturnDate() == "" || form.getReturnDate().isEmpty()) {

				form.setReturnDate(null);
				preparedStatement.setString(1, "");

			} else {

				preparedStatement.setString(1, dateToBeFormatted.format(dateFormat.parse(form.getReturnDate())));
			}

			preparedStatement.setInt(2, form.getDelayDays());

			preparedStatement.setDouble(3, form.getFineAmount());

			preparedStatement.setString(4, ActivityStatus.Returned);

			preparedStatement.setInt(5, form.getStudentIssueID());

			preparedStatement1 = connection.prepareStatement(updateBookStatusInBookTableQuery);

			preparedStatement1.setString(1, ActivityStatus.Available);
			preparedStatement1.setInt(2, form.getBookID());

			preparedStatement2 = connection.prepareStatement(editBookStatusQuery);

			preparedStatement2.setString(1, ActivityStatus.Available);
			preparedStatement2.setString(2, dateToBeFormatted.format(dateFormat.parse(NewDate)));
			preparedStatement2.setInt(3, form.getBookID());

			preparedStatement.execute();
			preparedStatement1.execute();
			preparedStatement2.execute();

			status = "success";

			System.out.println("Successfully Issued Books detail into table");

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while Issuing Books into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public JSONObject retrieveBooksByScanValue(String bookScanValue) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveBooksByScanValueQuery = QueryMaker.RETRIEVE_Issued_Books_By_ScanValue;

			preparedStatement = connection.prepareStatement(retrieveBooksByScanValueQuery);

			preparedStatement.setString(1, bookScanValue);
			preparedStatement.setString(2, bookScanValue);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("bookName", resultSet.getString("name"));
				object.put("bookID", resultSet.getInt("id"));
				object.put("accNum", resultSet.getString("accNum"));
				object.put("genre", resultSet.getString("genre"));
				object.put("barcode", resultSet.getString("barcode"));
				object.put("status", resultSet.getString("status"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
	}

	@Override
	public JSONObject retrieveStudentDetailsForBookReturn(int studentIssueID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatNew = new SimpleDateFormat("MM-dd-yyyy");

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStudentDetailsForBookReturnQuery = QueryMaker.RETRIEVE_Students_Book_Details_For_Returning_Book;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsForBookReturnQuery);

			preparedStatement.setInt(1, studentIssueID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				// For issueDate
				if (resultSet.getString("issueDate") == null || resultSet.getString("issueDate") == "") {

					object.put("issueDate", "");

				} else if (resultSet.getString("issueDate").isEmpty()) {

					object.put("issueDate", "");

				} else {

					object.put("issueDate", dateFormat.format(dateFormat1.parse(resultSet.getString("issueDate"))));
				}

				// For expectedReturnDate
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDate", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDate", "");

				} else {

					object.put("expectedReturnDate",
							dateFormat.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				// For expectedReturnDate in the form of MM/dd/YYYY
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDateNew", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDateNew", "");

				} else {

					object.put("expectedReturnDateNew",
							dateFormatNew.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				object.put("bookName", resultSet.getString("name"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveIssuedBooksByStudentID(int studentID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveIssuedBooksByStudentIDQuery = QueryMaker.RETRIEVE_Issued_Books_By_StudentID;

			preparedStatement = connection.prepareStatement(retrieveIssuedBooksByStudentIDQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("studentIssueID", resultSet.getInt("id"));
				object.put("bookName", resultSet.getString("name"));
				object.put("bookID", resultSet.getInt("bookID"));
				object.put("accNum", resultSet.getString("accNum"));
				object.put("genre", resultSet.getString("genre"));

				// For issueDate
				if (resultSet.getString("issueDate") == null || resultSet.getString("issueDate") == "") {

					object.put("issueDate", "");

				} else if (resultSet.getString("issueDate").isEmpty()) {

					object.put("issueDate", "");

				} else {

					object.put("issueDate", dateFormat.format(dateFormat1.parse(resultSet.getString("issueDate"))));
				}

				// For expectedReturnDate
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDate", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDate", "");

				} else {

					object.put("expectedReturnDate",
							dateFormat.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
				// System.out.println("inside :"+check1);
			}

			if (check1 == 0) {
				// System.out.println("outside :"+check1);
				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveStaffDetailsByRole(String role, int OrganizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			if (role.equals("NonTeaching")) {

				String retrieveStaffDetailsByRoleQuery = QueryMaker.RETRIEVE_Staff_Details_By_NonTeaching_Role;
				preparedStatement = connection.prepareStatement(retrieveStaffDetailsByRoleQuery);

			} else {

				String retrieveStaffDetailsByRoleQuery = QueryMaker.RETRIEVE_Staff_Details_By_Teaching_Role;
				preparedStatement = connection.prepareStatement(retrieveStaffDetailsByRoleQuery);
			}

			preparedStatement.setInt(1, OrganizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("staffName", resultSet.getString("staffName"));
				object.put("staffID", resultSet.getInt("id"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveIssuedBooksByStaffID(int staffID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveIssuedBooksByStaffIDQuery = QueryMaker.RETRIEVE_Issued_Books_By_StaffID;

			preparedStatement = connection.prepareStatement(retrieveIssuedBooksByStaffIDQuery);

			preparedStatement.setInt(1, staffID);
			preparedStatement.setString(2, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("staffIssueID", resultSet.getInt("id"));
				object.put("bookName", resultSet.getString("name"));
				object.put("bookID", resultSet.getInt("bookID"));
				object.put("accNum", resultSet.getString("accNum"));

				// For issueDate
				if (resultSet.getString("issueDate") == null || resultSet.getString("issueDate") == "") {

					object.put("issueDate", "");

				} else if (resultSet.getString("issueDate").isEmpty()) {

					object.put("issueDate", "");

				} else {

					object.put("issueDate", dateFormat.format(dateFormat1.parse(resultSet.getString("issueDate"))));
				}

				// For expectedReturnDate
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDate", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDate", "");

				} else {

					object.put("expectedReturnDate",
							dateFormat.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public JSONObject retrieveStaffsBookIssueCount(int bookCount, int staffID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;
		int NewCount = 0;

		try {
			connection = getConnection();

			String retrieveStaffsBookIssueCountQuery = QueryMaker.RETRIEVE_Staffs_Book_Issue_Count;

			preparedStatement = connection.prepareStatement(retrieveStaffsBookIssueCountQuery);

			preparedStatement.setInt(1, staffID);
			preparedStatement.setString(2, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				NewCount = resultSet.getInt("count");

				array.add(object);

				values.put("Release", array);
			}

			if (NewCount == bookCount) {

				object = new JSONObject();

				check1 = 1;

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			} else {

				object = new JSONObject();

				check1 = 0;

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public String insertIssuedBooksForStaff(LibraryForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new java.util.Date();
		String NewDate = dateFormat.format(date);

		try {

			connection = getConnection();

			String insertIssuedBooksForStaffQuery = QueryMaker.Issue_Book_Details_For_Staff;

			String updateBookStatusInBookTableQuery = QueryMaker.update_Books_Status_In_Book_Table;

			String editBookStatusQuery = QueryMaker.INSERT_Books_Status;

			preparedStatement = connection.prepareStatement(insertIssuedBooksForStaffQuery);

			preparedStatement.setInt(1, form.getStaffID());

			preparedStatement.setInt(2, form.getBookID());

			// Inserting Issue Date
			if (form.getIssueDate() == null || form.getIssueDate() == "" || form.getIssueDate().isEmpty()) {

				form.setIssueDate(null);
				preparedStatement.setString(3, form.getIssueDate());

			} else {

				preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(form.getIssueDate())));
			}

			// Inserting ExpectedReturn Date
			if (form.getExpectedReturnDate() == null || form.getExpectedReturnDate() == ""
					|| form.getExpectedReturnDate().isEmpty()) {

				form.setExpectedReturnDate(null);
				preparedStatement.setString(4, form.getExpectedReturnDate());

			} else {

				preparedStatement.setString(4,
						dateToBeFormatted.format(dateFormat.parse(form.getExpectedReturnDate())));
			}

			preparedStatement.setString(5, ActivityStatus.Issued);

			preparedStatement1 = connection.prepareStatement(updateBookStatusInBookTableQuery);

			preparedStatement1.setString(1, ActivityStatus.Issued);
			preparedStatement1.setInt(2, form.getBookID());

			preparedStatement2 = connection.prepareStatement(editBookStatusQuery);

			preparedStatement2.setString(1, ActivityStatus.Issued);
			preparedStatement2.setString(2, dateToBeFormatted.format(dateFormat.parse(NewDate)));
			preparedStatement2.setInt(3, form.getBookID());

			preparedStatement.execute();
			preparedStatement1.execute();
			preparedStatement2.execute();

			status = "success";

			System.out.println("Successfully Issued Books detail into table");

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while Issuing Books into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public JSONObject retrieveStaffDetailsForBookReturn(int staffIssueID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatNew = new SimpleDateFormat("MM-dd-yyyy");

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStaffDetailsForBookReturnQuery = QueryMaker.RETRIEVE_Staffs_Book_Details_For_Returning_Book;

			preparedStatement = connection.prepareStatement(retrieveStaffDetailsForBookReturnQuery);

			preparedStatement.setInt(1, staffIssueID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				// For issueDate
				if (resultSet.getString("issueDate") == null || resultSet.getString("issueDate") == "") {

					object.put("issueDate", "");

				} else if (resultSet.getString("issueDate").isEmpty()) {

					object.put("issueDate", "");

				} else {

					object.put("issueDate", dateFormat.format(dateFormat1.parse(resultSet.getString("issueDate"))));
				}

				// For expectedReturnDate
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDate", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDate", "");

				} else {

					object.put("expectedReturnDate",
							dateFormat.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				// For expectedReturnDate in the form of MM/dd/YYYY
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDateNew", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDateNew", "");

				} else {

					object.put("expectedReturnDateNew",
							dateFormatNew.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				object.put("bookName", resultSet.getString("name"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public String returnStaffBooks(LibraryForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new java.util.Date();

		String NewDate = dateFormat.format(date);

		try {

			connection = getConnection();

			String returnStaffBooksQuery = QueryMaker.Return_Staffs_Books_Details;

			String updateBookStatusInBookTableQuery = QueryMaker.update_Books_Status_In_Book_Table;

			String editBookStatusQuery = QueryMaker.INSERT_Books_Status;

			preparedStatement = connection.prepareStatement(returnStaffBooksQuery);

			// Inserting Issue Date
			if (form.getReturnDate() == null || form.getReturnDate() == "" || form.getReturnDate().isEmpty()) {

				form.setReturnDate(null);
				preparedStatement.setString(1, "");

			} else {

				preparedStatement.setString(1, dateToBeFormatted.format(dateFormat.parse(form.getReturnDate())));
			}

			preparedStatement.setInt(2, form.getDelayDays());

			preparedStatement.setDouble(3, form.getFineAmount());

			preparedStatement.setString(4, ActivityStatus.Returned);

			preparedStatement.setInt(5, form.getStaffIssueID());

			preparedStatement1 = connection.prepareStatement(updateBookStatusInBookTableQuery);

			preparedStatement1.setString(1, ActivityStatus.Available);
			preparedStatement1.setInt(2, form.getBookID());

			preparedStatement2 = connection.prepareStatement(editBookStatusQuery);

			preparedStatement2.setString(1, ActivityStatus.Available);
			preparedStatement2.setString(2, dateToBeFormatted.format(dateFormat.parse(NewDate)));
			preparedStatement2.setInt(3, form.getBookID());

			preparedStatement.execute();
			preparedStatement1.execute();
			preparedStatement2.execute();

			status = "success";

			System.out.println("Successfully Issued Books detail into table");

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while Issuing Books into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public JSONObject retrieveStandardDivisionListByAcademicYearID(int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStandardDivisionListByAcademicYearIDQuery = QueryMaker.RETRIEVE_Standard_Division_LIST;

			preparedStatement = connection.prepareStatement(retrieveStandardDivisionListByAcademicYearIDQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("standardName",
						resultSet.getString("standardName") + "-" + resultSet.getString("divisionName"));
				object.put("ayclassID", resultSet.getInt("id"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public List<LibraryForm> retrieveBooks(int shelfID) {

		List<LibraryForm> list = new ArrayList<LibraryForm>();

		LibraryForm form = null;
		try {
			connection = getConnection();

			String retrieveBooksQuery = QueryMaker.RETRIEVE_Book_LIST_By_ShelfID;

			preparedStatement = connection.prepareStatement(retrieveBooksQuery);

			preparedStatement.setInt(1, shelfID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LibraryForm();

				form.setBookID(resultSet.getInt("id"));
				form.setAccNum(resultSet.getString("accNum"));
				form.setName(resultSet.getString("name"));
				form.setGenre(resultSet.getString("genre"));
				form.setSection(resultSet.getString("section"));
				form.setType(resultSet.getString("type"));
				form.setCupboardID(resultSet.getInt("cupboardID"));
				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Book list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public String transferBooks(LibraryForm form, int shelfID, int bookID) {

		try {
			connection = getConnection();

			String transferBooksQuery = QueryMaker.transfer_Books;

			preparedStatement = connection.prepareStatement(transferBooksQuery);

			preparedStatement.setInt(1, shelfID);
			preparedStatement.setInt(2, bookID);

			form.setCupboardID(-1);

			preparedStatement.execute();

			status = "success";
			System.out.println("Books moved successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while moving books Details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public JSONObject retrieveDelayedStudents() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveDelayedStudentsQuery = QueryMaker.RETRIEVE_Delayed_Students_Book_Details;

			preparedStatement = connection.prepareStatement(retrieveDelayedStudentsQuery);

			preparedStatement.setString(1, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				// For expectedReturnDate
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDate", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDate", "");

				} else {

					object.put("expectedReturnDate",
							dateFormat.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				object.put("studentName", resultSet.getString("studentName"));
				object.put("bookName", resultSet.getString("name"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public JSONObject retrieveDelayedStaffs() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveDelayedStaffsQuery = QueryMaker.RETRIEVE_Delayed_Staffs_Book_Details;

			preparedStatement = connection.prepareStatement(retrieveDelayedStaffsQuery);

			preparedStatement.setString(1, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				// For expectedReturnDate
				if (resultSet.getString("expectedReturnDate") == null
						|| resultSet.getString("expectedReturnDate") == "") {

					object.put("expectedReturnDate", "");

				} else if (resultSet.getString("expectedReturnDate").isEmpty()) {

					object.put("expectedReturnDate", "");

				} else {

					object.put("expectedReturnDate",
							dateFormat.format(dateFormat1.parse(resultSet.getString("expectedReturnDate"))));
				}

				object.put("staffName", resultSet.getString("staffName"));
				object.put("bookName", resultSet.getString("name"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public int retriveStudentCount() {

		int count = 0;

		try {
			connection = getConnection();

			String retriveStudentCountQuery = QueryMaker.RETRIEVE_Student_Count;

			preparedStatement = connection.prepareStatement(retriveStudentCountQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving total Student count from Books table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveStaffCount() {

		int count = 0;

		try {
			connection = getConnection();

			String retriveStaffCountQuery = QueryMaker.RETRIEVE_Staff_Count;

			preparedStatement = connection.prepareStatement(retriveStaffCountQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving total Staff count from Books table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveStudentsBooksIssuedCount(String status) {

		int count = 0;

		try {
			connection = getConnection();

			String retriveStudentsBooksIssuedCountQuery = QueryMaker.RETRIEVE_Students_Issued_Book_Count;

			preparedStatement = connection.prepareStatement(retriveStudentsBooksIssuedCountQuery);

			preparedStatement.setString(1, status);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Students Issued books count from Books table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveStaffsBooksIssuedCount(String status) {

		int count = 0;

		try {
			connection = getConnection();

			String retriveStaffsBooksIssuedCountQuery = QueryMaker.RETRIEVE_Staffs_Issued_Book_Count;

			preparedStatement = connection.prepareStatement(retriveStaffsBooksIssuedCountQuery);

			preparedStatement.setString(1, status);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Staffs Issued books count from Books table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveStudentsBooksDelayedCount() {

		int count = 0;

		try {
			connection = getConnection();

			String retriveStudentsBooksDelayedCountQuery = QueryMaker.RETRIEVE_Studentss_Delayed_Book_Count;

			preparedStatement = connection.prepareStatement(retriveStudentsBooksDelayedCountQuery);

			preparedStatement.setString(1, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Students Delayed books count from Books table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveStaffsBooksDelayedCount() {

		int count = 0;

		try {
			connection = getConnection();

			String retriveStaffsBooksDelayedCountQuery = QueryMaker.RETRIEVE_Staffs_Delayed_Book_Count;

			preparedStatement = connection.prepareStatement(retriveStaffsBooksDelayedCountQuery);

			preparedStatement.setString(1, ActivityStatus.Issued);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Staffs Delayed books count from Books table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveBookCount() {

		int count = 0;

		try {
			connection = getConnection();

			String retriveBookCountQuery = QueryMaker.RETRIEVE_Book_Count;

			preparedStatement = connection.prepareStatement(retriveBookCountQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving total books count from Books table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	public int retriveBookCountOfStatus(String status) {

		int count = 0;

		try {
			connection = getConnection();

			String retriveBookCountQuery = QueryMaker.RETRIEVE_Book_Count_Of_Status;

			preparedStatement = connection.prepareStatement(retriveBookCountQuery);

			preparedStatement.setString(1, status);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				count = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving books status count from Books table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return count;
	}

	@Override
	public JSONObject removeShelfRow(int shelfID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String removeShelfRowQuery = QueryMaker.DELETE_Shelf_Row;

			preparedStatement = connection.prepareStatement(removeShelfRowQuery);

			preparedStatement.setInt(1, shelfID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject removeCupboardRow(int cupboardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String removeCupboardRowQuery = QueryMaker.DELETE_Cupboard_Row;

			preparedStatement = connection.prepareStatement(removeCupboardRowQuery);

			preparedStatement.setInt(1, cupboardID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveStudentsBookDetailsByClassID(int ayclassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStudentDetailsByClassIDQuery = QueryMaker.RETRIEVE_Students_Book_Details_By_ClassID;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsByClassIDQuery);

			preparedStatement.setInt(1, ayclassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("studentName", resultSet.getString("studentName"));
				object.put("studentID", resultSet.getInt("id"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	@Override
	public JSONObject retrieveStaffsBookDetailsByRole(String role, int organizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			if (role.equals("NonTeaching")) {

				String retrieveStaffDetailsByRoleQuery = "SELECT DISTINCT(a.id), CONCAT(a.lastName,' ', a.firstName)AS staffName FROM AppUser AS a, StaffIssue as st WHERE st.userID = a.id AND a.role NOT IN ('teacher','administrator', 'superAdmin') AND a.organizationID = "
						+ organizationID + " AND a.activityStatus = 'Active' ORDER BY id ASC";
				preparedStatement = connection.prepareStatement(retrieveStaffDetailsByRoleQuery);

			} else {

				String retrieveStaffDetailsByRoleQuery = "SELECT DISTINCT(a.id), CONCAT(a.lastName,' ', a.firstName)AS staffName FROM AppUser AS a, StaffIssue as st WHERE st.userID = a.id AND a.role NOT IN ('officeAdmin', 'librarian', 'Non-Teaching') AND a.organizationID = "
						+ organizationID + " AND a.activityStatus = 'Active' ORDER BY id ASC";
				preparedStatement = connection.prepareStatement(retrieveStaffDetailsByRoleQuery);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("staffName", resultSet.getString("staffName"));
				object.put("staffID", resultSet.getInt("id"));
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

}
