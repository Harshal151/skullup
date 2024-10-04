package com.kovidRMS.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.print.attribute.Size2DSyntax;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kovidRMS.daoInf.ConfigurationDAOInf;
import com.kovidRMS.daoInf.LoginDAOInf;
import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.StudentForm;
import com.kovidRMS.util.ActivityStatus;
import com.kovidRMS.util.ConfigXMLUtil;
import com.kovidRMS.util.ConfigurationUtil;
import com.kovidRMS.util.DAOConnection;
import com.kovidRMS.util.DateToWords;
import com.kovidRMS.util.JDBCHelper;
import com.kovidRMS.util.QueryMaker;

public class ConfigurationDAOImpl extends DAOConnection implements ConfigurationDAOInf {

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

	static int counter = 1;

	static boolean check = false;

	static int extraMinutes = 0;

	LoginDAOInf daoInf = null;

	ConfigurationUtil configurationUtil = null;

	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	public String insertExamination(String term, String examName, String examType, String startDate, String endDate,
			int academicYearID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		ConfigurationForm conform = new ConfigurationForm();

		try {
			connection = getConnection();

			String insertExaminationQuery = QueryMaker.INSERT_Examination;

			preparedStatement = connection.prepareStatement(insertExaminationQuery);

			preparedStatement.setString(1, term);

			preparedStatement.setString(2, examName);

			preparedStatement.setString(3, examType);

			// Inserting Start Date
			if (startDate == null || startDate == "" || startDate.isEmpty()) {

				conform.setStartDate(null);
				preparedStatement.setString(4, startDate);

			} else {
				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(startDate)));
			}

			// Inserting End Date
			if (endDate == null || endDate == "" || endDate.isEmpty()) {

				conform.setEndDate(null);
				preparedStatement.setString(5, endDate);

			} else {
				preparedStatement.setString(5, dateToBeFormatted.format(dateFormat.parse(endDate)));
			}

			/*
			 * preparedStatement.setString(4, startDate);
			 * 
			 * preparedStatement.setString(5, endDate);
			 */

			preparedStatement.setInt(6, academicYearID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Examination detail into table");

			/*
			 * preparedStatement.close();
			 * 
			 * connection.close();
			 */

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Examination into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<ConfigurationForm> retriveExaminationlInfo(int ExaminationID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm conform = new ConfigurationForm();

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			connection = getConnection();

			String retrievePatientIDQuery = QueryMaker.RETRIEVE_ExaminationlInfo;

			preparedStatement = connection.prepareStatement(retrievePatientIDQuery);

			preparedStatement.setInt(1, ExaminationID);

			resultSet = preparedStatement.executeQuery();
			status = "success";

			while (resultSet.next()) {

				conform.setExaminationID(resultSet.getInt("id"));
				conform.setTerm(resultSet.getString("term"));
				conform.setExamName(resultSet.getString("examName"));
				conform.setExamType(resultSet.getString("examType"));
				// Inserting Start Date
				if (resultSet.getString("startDate") == null || resultSet.getString("startDate") == "") {

					// form.setStartDate(null);
					conform.setStartDate("");

				} else if (resultSet.getString("startDate").isEmpty()) {
					conform.setStartDate("");

				} else {
					conform.setStartDate(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("startDate"))));
				}

				// Inserting End Date
				if (resultSet.getString("endDate") == null || resultSet.getString("endDate") == "") {

					// form.setEndDate(null);
					conform.setEndDate("");

				} else if (resultSet.getString("endDate").isEmpty()) {
					conform.setEndDate("");

				} else {

					conform.setEndDate(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("endDate"))));
				}

				conform.setAcademicYearID(resultSet.getInt("academicYearID"));

			}
			list.add(conform);

			/*
			 * preparedStatement.close(); connection.close();
			 */

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving student id from Student table due to:::"
					+ exception.getMessage());

		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public JSONObject retrieveDivisionListForStandard(int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveDivisionListForStandardQuery = QueryMaker.RETRIEVE_DivisionList_For_Standard_BY_Standard_ID;

			preparedStatement = connection.prepareStatement(retrieveDivisionListForStandardQuery);

			preparedStatement.setInt(1, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				if (resultSet.getString("division") == null || resultSet.getString("division") == "") {
					System.out.println("Null found.");
				} else {
					if (resultSet.getString("division").isEmpty()) {
						System.out.println("Null found.");
					} else {
						object = new JSONObject();

						object.put("divisionID", resultSet.getInt("id"));
						object.put("division", resultSet.getString("division"));
						object.put("check", check1);

						array.add(object);

						values.put("Release", array);
					}
				}
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

	public List<ConfigurationForm> retriveExaminationList(int academicYearID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {

			connection = getConnection();

			String retriveExaminationListQuery = QueryMaker.RETREIVE_Examination_LIST;

			preparedStatement = connection.prepareStatement(retriveExaminationListQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setExaminationID(resultSet.getInt("id"));

				form.setTerm(resultSet.getString("term"));

				form.setExamName(resultSet.getString("examName"));

				form.setExamType(resultSet.getString("examType"));

				// Inserting Start Date
				if (resultSet.getString("startDate") == null || resultSet.getString("startDate") == "") {

					// form.setStartDate(null);
					form.setStartDate("");

				} else if (resultSet.getString("startDate").isEmpty()) {
					form.setStartDate("");

				} else {
					form.setStartDate(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("startDate"))));
				}

				// Inserting End Date
				if (resultSet.getString("endDate") == null || resultSet.getString("endDate") == "") {

					// form.setEndDate(null);
					form.setEndDate("");

				} else if (resultSet.getString("endDate").isEmpty()) {
					form.setEndDate("");

				} else {

					form.setEndDate(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("endDate"))));
				}

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Examination list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public JSONObject deleteRow(int deleteID, String active) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteRowQuery = QueryMaker.DELETE_Row;

			preparedStatement = connection.prepareStatement(deleteRowQuery);

			preparedStatement.setInt(1, deleteID);

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

	public String updateExamination(String term, String examName, String examType, String startDate, String endDate,
			int academicYearID, int examID) {

		ConfigurationForm conform = new ConfigurationForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String updateExaminationQuery = QueryMaker.update_Examination;

			preparedStatement = connection.prepareStatement(updateExaminationQuery);

			preparedStatement.setString(1, term);

			preparedStatement.setString(2, examName);

			preparedStatement.setString(3, examType);

			// updating Start Date
			if (startDate == null || startDate == "") {

				conform.setStartDate(null);
				preparedStatement.setString(4, startDate);

			} else if (startDate.isEmpty()) {

				conform.setStartDate(null);
				preparedStatement.setString(4, startDate);

			} else {
				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(startDate)));
			}

			// updating End Date
			if (endDate == null || endDate == "") {

				conform.setEndDate(null);
				preparedStatement.setString(5, endDate);

			} else if (endDate.isEmpty()) {

				conform.setEndDate(null);
				preparedStatement.setString(5, endDate);

			} else {
				preparedStatement.setString(5, dateToBeFormatted.format(dateFormat.parse(endDate)));
			}

			preparedStatement.setInt(6, examID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated Examination detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Examination into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public JSONObject retrieveSubjectListForStandard(int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		LoginDAOInf daoInf = new LoginDAOImpl();

		String subjectList = "";

		try {
			connection = getConnection();

			String retrieveSubjectListForStandardQuery = QueryMaker.RETRIEVE_SubjectList_BY_Standard_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForStandardQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String Subject = "";

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = daoInf.retrievesubjectNameBySubjectID(Integer.parseInt(subArr[j].trim()));

					object = new JSONObject();

					object.put("subjectList", Subject);
					object.put("subjectID", Integer.parseInt(subArr[j].trim()));

					array.add(object);

					values.put("Release", array);
				}

				// subjectList = resultSet.getString("subjectList");
				// subjectList = Subject;

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

	public JSONObject retrieveStandardName(int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStandardNameQuery = QueryMaker.Retrieve_Standard_Name;

			preparedStatement = connection.prepareStatement(retrieveStandardNameQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("standard", resultSet.getString("standard"));

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

	public JSONObject retrieveDivisionName(int divisionID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveDivisionNameQuery = QueryMaker.Retrieve_Division_Name;

			preparedStatement = connection.prepareStatement(retrieveDivisionNameQuery);

			preparedStatement.setInt(1, divisionID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("division", resultSet.getString("division"));

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

	public JSONObject retrieveTeacherName(int teacherID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveTeacherNameQuery = QueryMaker.Retrieve_Teacher_Name;

			preparedStatement = connection.prepareStatement(retrieveTeacherNameQuery);

			preparedStatement.setInt(1, teacherID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String fullName = "";

				fullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");

				check1 = 1;

				object = new JSONObject();

				object.put("fullName", fullName);

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

	public JSONObject retrieveSubjectName(int subjectID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectNameQuery = QueryMaker.Retrieve_Subject_Name;

			preparedStatement = connection.prepareStatement(retrieveSubjectNameQuery);

			preparedStatement.setInt(1, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("name", resultSet.getString("name"));

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

	public JSONObject retrieveSubjectTeacherName(String teacherNameID) {

		ConfigurationForm conform = new ConfigurationForm();
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveTeacherNameQuery = QueryMaker.Retrieve_Teacher_Name;

			preparedStatement = connection.prepareStatement(retrieveTeacherNameQuery);

			String fullName = "";

			if (teacherNameID.contains(",")) {

				String teachArr[] = teacherNameID.split(",");

				for (int j = 0; j < teachArr.length; j++) {
					preparedStatement.setInt(1, Integer.parseInt(teachArr[j]));
					preparedStatement.setString(2, ActivityStatus.ACTIVE);

					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {

						fullName += "," + resultSet.getString("firstName") + " " + resultSet.getString("lastName");

						check1 = 1;

					}

					if (fullName.startsWith(",")) {
						fullName = fullName.substring(1);
					}

					object = new JSONObject();

					object.put("fullName", fullName);

					array.add(object);

					values.put("Release", array);

				}
				if (check1 == 0) {

					object = new JSONObject();

					object.put("check", check1);

					array.add(object);

					values.put("Release", array);

				}
			} else {

				preparedStatement.setInt(1, Integer.parseInt(teacherNameID));
				preparedStatement.setString(2, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					fullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");

					check1 = 1;

					object = new JSONObject();

					object.put("fullName", fullName);

					array.add(object);

					values.put("Release", array);

				}

				if (check1 == 0) {

					object = new JSONObject();

					object.put("check", check1);

					array.add(object);

					values.put("Release", array);

				}
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

	public List<ConfigurationForm> retriveAcademicYearClassList(int academicYearID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		try {

			connection = getConnection();

			String retriveAcademicYearClassListQuery = QueryMaker.RETREIVE_AcademicYearClass_LIST;

			preparedStatement = connection.prepareStatement(retriveAcademicYearClassListQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setAYClassID(resultSet.getInt("id"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setDivisionID(resultSet.getInt("divisionID"));

				form.setTeacherID(resultSet.getInt("teacherID"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Examination list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertAcademicYearList(int standardID, int divisionID, int teacherID, int academicYearID,
			String activityStatus) {

		try {
			connection = getConnection();

			String insertAcademicYearListQuery = QueryMaker.INSERT_AcademicYearList;

			preparedStatement = connection.prepareStatement(insertAcademicYearListQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setInt(2, divisionID);

			preparedStatement.setInt(3, teacherID);

			preparedStatement.setInt(4, academicYearID);

			preparedStatement.setString(5, activityStatus);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted AcademicYear List detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting AcademicYear List into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateAcademicYearList(int standardID, int divisionID, int teacherID, int academicYearID,
			int AYClassID1) {

		// ConfigurationForm conform = new ConfigurationForm();

		try {
			connection = getConnection();

			String updateAcademicYearListQuery = QueryMaker.update_AcademicYear_List;

			preparedStatement = connection.prepareStatement(updateAcademicYearListQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setInt(2, divisionID);

			preparedStatement.setInt(3, teacherID);

			preparedStatement.setInt(4, AYClassID1);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated AcademicYear List detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating AcademicYearList into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int retrieveAYClassID() {
		int AYID = 0;

		try {
			connection = getConnection();

			String retrieveAYClassIDQuery = QueryMaker.RETRIEVE_AYClassID;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDQuery);

			// preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AYID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving student id from Student table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return AYID;
	}

	public String insertConfigDetails(String ConfigEvent, int AYClassID) {

		try {

			connection = getConnection();

			String insertAdverseEventDetailsQuery = QueryMaker.INSERT_Config_EVENT;

			preparedStatement = connection.prepareStatement(insertAdverseEventDetailsQuery);

			// Splitting adverseEvent string by '||' in order to get all adverse
			// event details
			String[] ConfigEventArray = ConfigEvent.split("\\$");

			// System.out.println("ConfigEventArray......... :"+ConfigEventArray[0]
			// +ConfigEventArray[1] +ConfigEventArray[2] +ConfigEventArray[3]);
			preparedStatement.setString(1, ConfigEventArray[0]);

			if (ConfigEventArray[2].contains(",")) {
				String teachArr[] = ConfigEventArray[2].split(",");

				if (teachArr[0].startsWith(",")) {
					teachArr[0] = teachArr[0].substring(1);
				}
				preparedStatement.setString(2, teachArr[0]);

				if (teachArr[1].startsWith(",")) {
					teachArr[1] = teachArr[1].substring(1);
				}
				preparedStatement.setString(3, teachArr[1]);

			} else {
				preparedStatement.setString(2, ConfigEventArray[2]);
				preparedStatement.setString(3, "0");
			}

			preparedStatement.setInt(4, AYClassID);

			preparedStatement.execute();

			System.out.println("Configuration events added successfully.");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Configuration event details into AYSubject table due to:::"
							+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public JSONObject retrieveNewSubjectListForStandard(int ayClassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		String subjectList = "";

		try {
			connection = getConnection();

			String retrieveSubjectListForStandardQuery = QueryMaker.RETRIEVE_NewSubjectList_BY_AYClass_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForStandardQuery);

			preparedStatement.setInt(1, ayClassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subject");

			}

			object = new JSONObject();

			object.put("subjectList", subjectList);

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

	public JSONObject deleteAYClassRow(int deleteID, String active) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteAYClassRowQuery = QueryMaker.DELETE_AYClassRow;

			preparedStatement = connection.prepareStatement(deleteAYClassRowQuery);

			preparedStatement.setInt(1, deleteID);

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

	public JSONObject retrieveSubTeacherListByAYCLassID(int ayClassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;
		String teacherName = "";

		try {

			connection = getConnection();

			String retrieveSubTeacherListByAYCLassIDQuery = QueryMaker.RETRIEVE_SUB_TEACHER_LIST_BY_AYCLASS_ID;

			preparedStatement = connection.prepareStatement(retrieveSubTeacherListByAYCLassIDQuery);

			preparedStatement.setInt(1, ayClassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("check", check);
				object.put("aySubjectID", resultSet.getInt("id"));
				object.put("subject", resultSet.getString("subjectID"));
				if (resultSet.getString("teacher2") == null) {
					teacherName = resultSet.getString("teacher1");
					object.put("teacherName", teacherName);

				} else {
					teacherName = resultSet.getString("teacher1") + "," + resultSet.getString("teacher2");
					object.put("teacherName", teacherName);
				}
				/*
				 * object.put("teacherName",
				 * retrieveSubjectTeacherNameByIDs(Integer.parseInt(resultSet.getString(
				 * "teacherID1")))); object.put("teacherID1",
				 * resultSet.getString("teacherID1")); object.put("teacherName",
				 * retrieveSubjectTeacherNameByIDs(Integer.parseInt(resultSet.getString(
				 * "teacherID2")))); object.put("teacherID2",
				 * resultSet.getString("teacherID2"));
				 */
				// object.put("aySubjectID", resultSet.getInt("id"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {
				object = new JSONObject();

				check = 1;

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			object = new JSONObject();

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public JSONObject deleteAYSUbjectByID(int aySubjectID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteAYSUbjectByIDQuery = QueryMaker.DELETE_AY_SUBJECT_BY_ID;

			preparedStatement = connection.prepareStatement(deleteAYSUbjectByIDQuery);

			preparedStatement.setInt(1, aySubjectID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			check = 0;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public int retriveInactiveAcademicYearID(int OrganizationID) {

		int YearID = 0;
		try {
			connection = getConnection();

			String retrieveAcademicYearNameQuery = QueryMaker.RETRIEVE_AcademicYear_ID;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearNameQuery);

			preparedStatement.setInt(1, OrganizationID);

			preparedStatement.setString(2, ActivityStatus.DRAFT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				YearID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving inactive Academic Year list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return YearID;
	}

	public HashMap<Integer, String> getExamList(int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String getExamListQuery = QueryMaker.RETRIEVE_Exam_LIST;

			preparedStatement = connection.prepareStatement(getExamListQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("examName"));

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

	public HashMap<Integer, String> getExaminationList(int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String getExaminationListQuery = QueryMaker.RETRIEVE_Examination_LIST1;

			preparedStatement = connection.prepareStatement(getExaminationListQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("examName"));

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

	public int retrieveAYCLassID(int standardID, int divisionID, int academicYearID) {

		int ayCLassID = 0;

		try {

			connection = getConnection();

			String retrieveAYCLassIDQuery = QueryMaker.RETRIEVE_AYClassID1;

			preparedStatement = connection.prepareStatement(retrieveAYCLassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ayCLassID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ayCLassID;
	}

	public JSONObject retrieveExistingSubjectAssessmentList(int ayClassID, int examinationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveExistingSubjectAssessmentListQuery = QueryMaker.RETRIEVE_SUBJECT_ASSESSMENT_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingSubjectAssessmentListQuery);

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, examinationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("check", check);
				object.put("totalMarks", resultSet.getDouble("totalMarks"));
				object.put("subAssmntID", resultSet.getInt("id"));
				object.put("subject", resultSet.getString("subject"));
				object.put("scaleTo", resultSet.getDouble("scaleTo"));
				object.put("gradeBased", resultSet.getInt("gradeBased"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			check = 0;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public String insertSubjectAssessment(int examID, int subjectID, double totalMarks, double scaleTo, int gradeBased,
			int ayCLassID) {

		try {

			connection = getConnection();

			String insertSubjectAssessmentQuery = QueryMaker.INSERT_SUBJECT_ASSESSMENT;

			preparedStatement = connection.prepareStatement(insertSubjectAssessmentQuery);

			preparedStatement.setInt(1, examID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setDouble(3, totalMarks);
			preparedStatement.setDouble(4, scaleTo);
			preparedStatement.setInt(5, gradeBased);
			preparedStatement.setInt(6, ayCLassID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Subject Assessment detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Subject Assessment into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateSubjectAssessment(int examID, int subjectID, double totalMarks, double scaleTo, int gradeBased,
			int ayCLassID, int subjectAssessmentID) {

		try {
			connection = getConnection();

			String updateSubjectAssessmentQuery = QueryMaker.update_SUBJECT_ASSESSMENT;

			preparedStatement = connection.prepareStatement(updateSubjectAssessmentQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setDouble(2, totalMarks);
			preparedStatement.setDouble(3, scaleTo);
			preparedStatement.setInt(4, gradeBased);
			preparedStatement.setInt(5, examID);
			preparedStatement.setInt(6, subjectAssessmentID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully updated Subject Assessment detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Subject Assessment into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public HashMap<String, String> getStandardListByTeacher(int UserID) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String getStandardListByTeacherQuery = QueryMaker.RETRIEVE_Standard_LIST_By_Teacher;

			preparedStatement = connection.prepareStatement(getStandardListByTeacherQuery);

			preparedStatement.setInt(1, UserID);
			preparedStatement.setInt(2, UserID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String key = resultSet.getInt("standardID") + "-" + resultSet.getInt("divisionID") + "-"
						+ resultSet.getInt("ayclassID");

				String value = resultSet.getString("standardName") + "-" + resultSet.getString("divisionName");

				map.put(key, value);

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

	public JSONObject retrieveSubjectListByUserID(int userID, int ayClassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		int counter = 1;
		int counter1 = 1;

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDQuery);

			String subjectList = "";
			int subjectID = 0;

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subject");

				subjectID = resultSet.getInt("subjectID");

				System.out.println("subjectList :" + subjectList);
				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", subjectID);

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

		/*
		 * try {
		 * 
		 * connection = getConnection();
		 * 
		 * String retrieveSubjectListByUserIDQuery =
		 * QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID;
		 * 
		 * preparedStatement =
		 * connection.prepareStatement(retrieveSubjectListByUserIDQuery);
		 * 
		 * preparedStatement.setInt(1, academicYearID); preparedStatement.setInt(2,
		 * userID); preparedStatement.setInt(3, userID);
		 * 
		 * resultSet = preparedStatement.executeQuery();
		 * 
		 * while (resultSet.next()) { list.add(resultSet.getString("subject")); }
		 * 
		 * resultSet.close(); preparedStatement.close(); connection.close(); } catch
		 * (Exception exception) { exception.printStackTrace(); }
		 * 
		 * return list;
		 */
	}

	public List<ConfigurationForm> retrieveStudentListForExaminationAndSubject(int ayClassID, int examID, int subjectID,
			int userID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			/*
			 * Check whether subjectID is positive i.e. other than -1 or -2, then search
			 * based on subjectID else search based on physical or creative activity
			 */
			String subjectTypeCheck = daoInf2.verifySubjectType1(subjectID);

			if (subjectTypeCheck == "Physical Found") {
				String retrieveStudentListForExaminationAndSubjectQuery = QueryMaker.RETRIEVE_STUDENT_LIST_FOR_EXAMINATION_FOR_PHYSICAL;

				preparedStatement = connection.prepareStatement(retrieveStudentListForExaminationAndSubjectQuery);

				/*
				 * preparedStatement.setString(1, "Extra-curricular: Physical");
				 * preparedStatement.setInt(2, subjectID); preparedStatement.setInt(3, userID);
				 * preparedStatement.setInt(4, ayClassID);
				 */
				preparedStatement.setInt(1, subjectID);
				preparedStatement.setInt(2, examID);
				preparedStatement.setInt(3, ayClassID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setString(5, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					configurationForm = new ConfigurationForm();

					configurationForm.setStudentID(resultSet.getInt("studentID"));

					String studetName = "";

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");

					}

					configurationForm.setStudentName(studetName);
					// configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
					configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
					configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
					configurationForm.setSubjectAssessmentID(resultSet.getInt("id"));
					/*
					 * configurationForm.setCreativeActivity(resultSet.getString(
					 * "creativeActivities"));
					 * configurationForm.setSubjectID(resultSet.getInt("physicalSubjectID"));
					 * configurationForm.setSubjectAssessmentID(resultSet.getInt("physicalSbjAssID")
					 * );
					 */

					list.add(configurationForm);
				}

			} else if (subjectTypeCheck == "Creative Found") {

				String retrieveStudentListForExaminationAndSubjectQuery = QueryMaker.RETRIEVE_STUDENT_LIST_FOR_EXAMINATION_FOR_CREATIVE;

				preparedStatement = connection.prepareStatement(retrieveStudentListForExaminationAndSubjectQuery);

				/*
				 * preparedStatement.setString(1, "Extra-curricular: Creative");
				 * preparedStatement.setInt(2, userID); preparedStatement.setInt(3, userID);
				 * preparedStatement.setInt(4, ayClassID);
				 */
				preparedStatement.setInt(1, subjectID);
				preparedStatement.setInt(2, examID);
				preparedStatement.setInt(3, ayClassID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setString(5, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					configurationForm = new ConfigurationForm();

					configurationForm.setStudentID(resultSet.getInt("studentID"));

					String studetName = "";

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");

					}

					configurationForm.setStudentName(studetName);
					// configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
					configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
					// configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
					configurationForm.setCreativeActivity(resultSet.getString("creativeActivities"));
					configurationForm.setSubjectAssessmentID(resultSet.getInt("id"));
					/*
					 * configurationForm.setSubjectID(resultSet.getInt("creativeSubjectID"));
					 * configurationForm.setSubjectAssessmentID(resultSet.getInt("creativeSbjAssID")
					 * );
					 */

					list.add(configurationForm);
				}

			} else {

				String retrieveStudentListForExaminationAndSubjectQuery = QueryMaker.RETRIEVE_STUDENT_LIST_FOR_EXAMINATION;

				preparedStatement = connection.prepareStatement(retrieveStudentListForExaminationAndSubjectQuery);

				preparedStatement.setInt(1, ayClassID);
				preparedStatement.setInt(2, subjectID);
				preparedStatement.setInt(3, examID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);
				preparedStatement.setString(5, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					configurationForm = new ConfigurationForm();

					configurationForm.setStudentID(resultSet.getInt("studentID"));

					String studetName = "";

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");

					}

					configurationForm.setStudentName(studetName);
					configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
					configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
					configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
					configurationForm.setCreativeActivity(resultSet.getString("creativeActivities"));
					configurationForm.setSubjectID(resultSet.getInt("subjectID"));
					configurationForm.setSubjectAssessmentID(resultSet.getInt("id"));

					list.add(configurationForm);

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public int retrieveSubjectAssessmentID(int examinationID, int ayCLassID, int subjectID) {

		int subAssmentID = 0;

		try {

			connection = getConnection();

			String retrieveSubjectAssessmentIDQuery = QueryMaker.RETRIEVE_SUBJECT_ASSESSMENT_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectAssessmentIDQuery);

			preparedStatement.setInt(1, ayCLassID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, examinationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subAssmentID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return subAssmentID;
	}

	public int retrieveRegistrationID(int studentID) {

		int registrationID = 0;

		try {

			connection = getConnection();

			String retrieveRegistrationIDQuery = QueryMaker.RETRIEVE_REGISTRATION_ID;

			preparedStatement = connection.prepareStatement(retrieveRegistrationIDQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				registrationID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return registrationID;
	}

	public String insertStudentAssessmentDetails(int subjectAssessmentID, int registrationID, double marksObtained,
			String grade, double scaledMarks, int absentFlag) {

		try {

			connection = getConnection();

			String insertStudentAssessmentDetailsQuery = QueryMaker.INSERT_STUDENT_ASSESSMENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertStudentAssessmentDetailsQuery);

			preparedStatement.setInt(1, subjectAssessmentID);
			preparedStatement.setDouble(2, marksObtained);
			preparedStatement.setInt(3, registrationID);
			preparedStatement.setString(4, grade);
			preparedStatement.setDouble(5, scaledMarks);
			preparedStatement.setInt(6, absentFlag);
			preparedStatement.setString(7, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<ConfigurationForm> retrieveExistingStudentAssessmentList(int ayClassID, int examinationID,
			int subjectID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrieveExistingStudentAssessmentListQuery = QueryMaker.RETRIEVE_EXISTING_STUDENT_ASSESSMENT_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingStudentAssessmentListQuery);

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, examinationID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			System.out.println(
					"retrieveExistingStudentAssessmentListQuery:" + retrieveExistingStudentAssessmentListQuery);
			System.out.println(ayClassID + "  " + subjectID + "  " + examinationID);
			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setStudentID(resultSet.getInt("studentID"));

				String studetName = "";

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {
					studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else if (resultSet.getString("middleName").isEmpty()) {
					studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else {
					studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName");
				}

				configurationForm.setStudentName(studetName);
				configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
				configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
				configurationForm.setAbsentFlag(resultSet.getInt("absentFlag"));
				configurationForm.setMarksObtained(resultSet.getDouble("marksObtained"));
				configurationForm.setGrade(resultSet.getString("grade"));
				configurationForm.setStudentAssessmentID(resultSet.getInt("id"));
				configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
				configurationForm.setCreativeActivity(resultSet.getString("creativeActivities"));
				configurationForm.setSubjectAssessmentID(resultSet.getInt("subjectAssessmentID"));
				configurationForm.setScaleTo(resultSet.getDouble("scaleTo"));
				configurationForm.setGradeBased(resultSet.getInt("gradeBased"));
				configurationForm.setRegistrationID(resultSet.getInt("registrationID"));

				list.add(configurationForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public String updateStudentAssessmentDetails(int studentAssessmentID, double marksObtained, String grade,
			double marksScaled, int editabsentFlag) {

		try {

			connection = getConnection();

			String updateStudentAssessmentDetailsQuery = QueryMaker.UPDATE_STUDENT_ASSESSMENT_DETAILS;

			preparedStatement = connection.prepareStatement(updateStudentAssessmentDetailsQuery);

			preparedStatement.setDouble(1, marksObtained);
			preparedStatement.setString(2, grade);
			preparedStatement.setDouble(3, marksScaled);
			preparedStatement.setInt(4, editabsentFlag);
			preparedStatement.setInt(5, studentAssessmentID);

			preparedStatement.executeUpdate();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public HashMap<Integer, String> retrieveSubjectListForCLassTeacher(int userID, int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		int counter = 1;
		int counter1 = 1;

		try {

			connection = getConnection();

			String retrieveSubjectListForCLassTeacherQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForCLassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subject"));
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

	public boolean verifyClassTeacher(int userID, int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyClassTeacherQuery = QueryMaker.VERIFY_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(verifyClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

	public String retrieveStandardForClassTeacher(int userID, int academicYearID) {

		String value = "";

		try {

			connection = getConnection();

			String retrieveStandardForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveStandardForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				value = resultSet.getString("standardName") + "-" + resultSet.getString("divisionName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return value;
	}

	public int retrieveAYClassIDForClassTeacher(int userID, int academicYearID) {

		int ID = 0;

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ID;
	}

	public List<ConfigurationForm> retrieveExistingsubjectAssessmentList(int ayClassID, int examinationID,
			String subArr) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;

		try {

			connection = getConnection();

			String retrieveExistingsubjectAssessmentListQuery = QueryMaker.RETRIEVE_EXISTING_subject_ASSESSMENT_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingsubjectAssessmentListQuery);

			// System.out.println(".." + subArr);

			String[] array = subArr.split("\\$");

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setInt(3, Integer.parseInt(array[1].trim()));

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setSubjectID(resultSet.getInt("subjectID"));
				configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
				configurationForm.setScaleTo(resultSet.getDouble("scaleTo"));
				configurationForm.setGradeBased(resultSet.getInt("gradeBased"));
				configurationForm.setSubject(resultSet.getString("subject"));
				configurationForm.setSubjectType(resultSet.getString("subjectType"));
				configurationForm.setSubjectAssessmentID(resultSet.getInt("id"));

				list.add(configurationForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public String retrieveSubjectListByStandardID(int standardID) {

		String value = "";
		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveSubjectListByStandardIDQuery = QueryMaker.RETRIEVE_Subject_LIST_BY_StandardID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByStandardIDQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				value = resultSet.getString("subjectList");

			}

			String subArr[] = value.split(",");

			value = "";

			for (int j = 0; j < subArr.length; j++) {

				value = value + ',' + daoInf.retrievesubjectNameBySubjectID(Integer.parseInt(subArr[j].trim())) + '$'
						+ Integer.parseInt(subArr[j].trim());
			}

			if (value.startsWith(",")) {
				value = value.substring(1);
			}

			// System.out.println("value.." + value);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return value;

	}

	public String retrieveSubjectListByStandardIDExamType(int standardID) {

		String value = "";
		String value1 = "";

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveSubjectListByStandardIDExamTypeQuery = QueryMaker.RETRIEVE_Subject_LIST_BY_StandardID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByStandardIDExamTypeQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				value = resultSet.getString("subjectList");
			}

			String subArr[] = value.split(",");

			for (int j = 0; j < subArr.length; j++) {

				value = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

				if (value == null) {

					continue;

				} else {

					value1 = value1 + ',' + value + '$' + Integer.parseInt(subArr[j].trim());
				}
			}

			if (value1.startsWith(",")) {

				value1 = value1.substring(1);
			}

			// System.out.println("value.." + value);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return value1;

	}

	public List<ConfigurationForm> retrieveSubjectListByStandard(int standardID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;

		try {

			connection = getConnection();

			String retrieveExistingsubjectAssessmentListQuery = QueryMaker.RETRIEVE_Subject_LIST_BY_StandardID;

			preparedStatement = connection.prepareStatement(retrieveExistingsubjectAssessmentListQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setSubject("subjectList");

				list.add(configurationForm);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifySubjects(int ayClassID, int examinationID, String subject) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifySubjectsQuery = QueryMaker.VERIFY_Subjects;

			preparedStatement = connection.prepareStatement(verifySubjectsQuery);

			String[] array = subject.split("\\$");

			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(3, Integer.parseInt(array[1].trim()));

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

	public HashMap<Integer, String> retrieveDivisionListByStandardID(int stdID) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		try {

			connection = getConnection();

			String retrieveDivisionListForStandardQuery = QueryMaker.RETRIEVE_DivisionList_For_Standard_BY_Standard_ID;

			preparedStatement = connection.prepareStatement(retrieveDivisionListForStandardQuery);

			preparedStatement.setInt(1, stdID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("division"));
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

	public String retrieveSubjectTeacherNameByIDs(int teacherIDStr) {

		String teacherName = "";

		try {

			connection1 = getConnection();

			String retrieveSubjectTeacherNameByIDsQuery = QueryMaker.Retrieve_Teacher_Name;

			preparedStatement1 = connection1.prepareStatement(retrieveSubjectTeacherNameByIDsQuery);

			preparedStatement1.setInt(1, teacherIDStr);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				teacherName += "," + resultSet1.getString("firstName") + " " + resultSet1.getString("lastName");
			}

			if (teacherName.startsWith(",")) {
				teacherName = teacherName.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return teacherName;
	}

	public JSONObject retrieveStudentListForStandardAndDivision(int standardID, int divisionID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		ConfigurationForm conform = new ConfigurationForm();

		try {
			connection = getConnection();

			String retrieveStudentListForStandardAndDivisionQuery = QueryMaker.Retrieve_Student_Name;

			preparedStatement = connection.prepareStatement(retrieveStudentListForStandardAndDivisionQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				conform = new ConfigurationForm();

				String studentName = "";
				int studentID = 0;

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					studentName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else if (resultSet.getString("middleName").isEmpty()) {

					studentName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else {

					studentName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName");

				}

				conform.setStudentID(resultSet.getInt("studentID"));

				check1 = 1;

				object = new JSONObject();

				object.put("studentName", studentName);

				object.put("studentID", resultSet.getInt("studentID"));

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

	public JSONObject retrieveStudentName(String studentNameID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStudentNameQuery = QueryMaker.Retrieve_Students_Name;

			preparedStatement = connection.prepareStatement(retrieveStudentNameQuery);

			String fullName = "";

			if (studentNameID.contains(",")) {

				String studArr[] = studentNameID.split(",");

				for (int j = 0; j < studArr.length; j++) {
					preparedStatement.setInt(1, Integer.parseInt(studArr[j]));
					preparedStatement.setString(2, ActivityStatus.ACTIVE);

					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {

						if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

							fullName += "," + resultSet.getString("lastName") + " " + resultSet.getString("firstName");

						} else if (resultSet.getString("middleName").isEmpty()) {

							fullName += "," + resultSet.getString("lastName") + " " + resultSet.getString("firstName");

						} else {

							fullName += "," + resultSet.getString("lastName") + " " + resultSet.getString("firstName")
									+ " " + resultSet.getString("middleName");
						}

						check1 = 1;

					}

					if (fullName.startsWith(",")) {
						fullName = fullName.substring(1);
					}

					object = new JSONObject();

					object.put("fullName", fullName);

					array.add(object);

					values.put("Release", array);

				}
				if (check1 == 0) {

					object = new JSONObject();

					object.put("check", check1);

					array.add(object);

					values.put("Release", array);

				}
			} else {

				preparedStatement.setInt(1, Integer.parseInt(studentNameID));
				preparedStatement.setString(2, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");
					}

					check1 = 1;

					object = new JSONObject();

					object.put("fullName", fullName);

					array.add(object);

					values.put("Release", array);

				}

				if (check1 == 0) {

					object = new JSONObject();

					object.put("check", check1);

					array.add(object);

					values.put("Release", array);

				}
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

	public JSONObject retrieveCondition(String studentNameID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveStudentNameQuery = QueryMaker.Retrieve_Condition_Name;

			preparedStatement = connection.prepareStatement(retrieveStudentNameQuery);

			String medCondition = "";

			preparedStatement.setInt(1, Integer.parseInt(studentNameID));
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				medCondition = resultSet.getString("medCondition");

				check1 = 1;

				object = new JSONObject();

				object.put("medCondition", medCondition);

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

	public boolean verifyExamType(int examinationID, int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyExamTypeQuery = QueryMaker.VERIFY_ExamType;

			preparedStatement = connection.prepareStatement(verifyExamTypeQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, "Term End");

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

	public JSONObject retrieveSubjectListByUserIDByExamType(int userID, int ayClassID, int organizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDByExamTypeQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExamType;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByExamTypeQuery);

			String subjectList = "";

			preparedStatement.setString(1, "Scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectName");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public int retrieveGradeBasedValue(int examinationID, int subjectID, int ayclassID) {

		int check = 0;

		try {

			connection = getConnection();

			String retrieveGradeBasedValueQuery = QueryMaker.Retrieve_GradeBased_Value;

			preparedStatement = connection.prepareStatement(retrieveGradeBasedValueQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, ayclassID);
			preparedStatement.setInt(3, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = resultSet.getInt("gradeBased");
			}
			System.out.println("check : " + check);

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public HashMap<Integer, String> retrieveSubjectListByExamID(int userID, int ayClassID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		int counter = 1;
		int counter1 = 1;

		try {

			connection = getConnection();

			String retrieveSubjectListByUserIDQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDQuery);

			String subjectList = "";
			int subjectID = 0;

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subject"));

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

	public HashMap<Integer, String> retrieveSubjectListByExamIDByExamType(int userID, int ayClassID,
			int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveSubjectListByUserIDByExamTypeQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExamType;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByExamTypeQuery);

			String subjectList = "";

			preparedStatement.setString(1, "Scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subjectName"));

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

	public String verifySubjectType(int subjectID) {

		String check = "";

		try {

			connection = getConnection();

			String verifySubjectTypeQuery = QueryMaker.VERIFY_Subject_Type;

			preparedStatement = connection.prepareStatement(verifySubjectTypeQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, "Personality Development & Life Skills");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = "1";
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "0";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public HashMap<Integer, String> retrieveSubjectListForCLassTeacherByExamType(int userID, int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveSubjectListForCLassTeacherQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_FOR_CLASS_TEACHER_ByExamType;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForCLassTeacherQuery);

			preparedStatement.setString(1, "Scholastic");
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subject"));

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

	public JSONObject retrieveSubjectListForCLassTeacher1(int userID, int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		int counter = 1;
		int counter1 = 1;

		try {
			connection = getConnection();

			String subjectList = "";
			int subjectID = 0;

			String retrieveSubjectListForCLassTeacher1Query = QueryMaker.RETRIEVE_SUBJECT_LIST_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForCLassTeacher1Query);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subject");

				subjectID = resultSet.getInt("subjectID");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", subjectID);

				array.add(object);

				values.put("Release", array);
			}

			// subjectList = resultSet.getString("subject");

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

	public JSONObject retrieveSubjectListForCLassTeacherByExamType1(int userID, int academicYearID,
			int organizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String subjectList = "";
			int subjectID = 0;

			String retrieveSubjectListForCLassTeacherByExamType1Query = QueryMaker.RETRIEVE_SUBJECT_LIST_FOR_CLASS_TEACHER_ByExamType;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForCLassTeacherByExamType1Query);

			preparedStatement.setString(1, "Scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, userID);
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subject");

				subjectID = resultSet.getInt("subjectID");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", subjectID);

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

	public List<ConfigurationForm> retrieveClassStudentListForClassTeacher(int standardID, int divisionID,
			int academicYearID, String term) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		try {

			connection = getConnection();

			String retrieveClassStudentListForClassTeacherQuery = QueryMaker.RETREIVE_Class_Student_LIST;

			preparedStatement = connection.prepareStatement(retrieveClassStudentListForClassTeacherQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRegistrationID(resultSet.getInt("id"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setAYClassID(resultSet.getInt("aYClassID"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setGrNumber(resultSet.getString("GRNO"));

				form.setResult(resultSet.getString("studentStatus"));

				form.setTerm(term);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Student list from database due to:::" + exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public List<ConfigurationForm> retrieveStudentDetailsList(int studentID, String term, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveStudentDetailsListQuery = QueryMaker.RETREIVE_Student_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsListQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setStudentName(resultSet.getString("studentName"));

				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					form.setDateOfBirth("");
				} else if (resultSet.getString("dateOfBirth").isEmpty()) {
					form.setDateOfBirth("");
				} else {
					form.setDateOfBirth(dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfBirth"))));
				}

				form.setGrNumber(resultSet.getString("grNumber"));

				form.setStandard(resultSet.getString("standard"));

				form.setDivision(resultSet.getString("division"));

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRegistrationID(resultSet.getInt("id"));

				form.setTerm(term);

				form.setAYClassID(resultSet.getInt("ayClassID"));

				form.setResult(resultSet.getString("studentStatus"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Student Details list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public List<ConfigurationForm> retrievecoScholasticGradeList(int studentID, String term, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrievecoScholasticGradeListQuery = QueryMaker.RETREIVE_Student_Grade_LIST;

			preparedStatement = connection.prepareStatement(retrievecoScholasticGradeListQuery);

			preparedStatement.setString(1, "Co-scholastic");
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				SubjectID = resultSet.getInt("subjectID");

				form.setSubject(resultSet.getString("subject"));

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade(resultSet.getString("grade"));
				}

				// form.setGrade(resultSet.getString("grade"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Co-Scholastic Grade list from database due to:::"
							+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public List<ConfigurationForm> retrieveExtraCurricularGradeList(int studentID, String term, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrieveExtraCurricularGradeListQuery = QueryMaker.RETREIVE_Student_ExtraCurricular_Grade_LIST;

			preparedStatement = connection.prepareStatement(retrieveExtraCurricularGradeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Academic");
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				form.setSubject(resultSet.getString("subject"));

				SubjectID = resultSet.getInt("subjectID");

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade(resultSet.getString("grade"));
				}

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			preparedStatement = connection.prepareStatement(retrieveExtraCurricularGradeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Physical");
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				form.setSubject(resultSet.getString("subject"));

				SubjectID = resultSet.getInt("subjectID");

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade(resultSet.getString("grade"));
				}

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			preparedStatement = connection.prepareStatement(retrieveExtraCurricularGradeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Creative");
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				form.setSubject(resultSet.getString("subject"));

				SubjectID = resultSet.getInt("subjectID");

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade(resultSet.getString("grade"));
				}

				list.add(form);
			}

			// System.out.println("list: " + list);
			resultSet.close();
			preparedStatement.close();

			preparedStatement = connection.prepareStatement(retrieveExtraCurricularGradeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Compulsory");
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				form.setSubject(resultSet.getString("subject"));

				SubjectID = resultSet.getInt("subjectID");

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade(resultSet.getString("grade"));
				}

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Extra-curriclar Activity Grade list from database due to:::"
							+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public HashMap<Integer, String> retrieveSubjectListForCLass(int standardID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		LoginDAOInf daoInf = new LoginDAOImpl();

		try {
			connection = getConnection();

			String retrieveSubjectListForCLassQuery = QueryMaker.RETRIEVE_Subject_List_For_CLass;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForCLassQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String Subject = "";
				// LoginDAOInf daoInf = new LoginDAOImpl();

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = Subject + ',' + daoInf.retrievesubjectNameBySubjectID(Integer.parseInt(subArr[j].trim()));

					if (Subject.startsWith(",")) {
						Subject = Subject.substring(1);
					}

					map.put(Integer.parseInt(subArr[j].trim()), Subject);
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Subject List For CLass from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return map;

	}

	public List<ConfigurationForm> retrievePersonalityDevelopmentGradeList(int studentID, String term, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		try {

			connection = getConnection();

			String retrievePersonalityDevelopmentGradeListQuery = QueryMaker.RETREIVE_Student_Grade_LIST;

			preparedStatement = connection.prepareStatement(retrievePersonalityDevelopmentGradeListQuery);

			preparedStatement.setString(1, "Personality Development & Life Skills");
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				form.setSubject(resultSet.getString("subject"));

				SubjectID = resultSet.getInt("subjectID");

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade(resultSet.getString("grade"));
				}
				// form.setGrade(resultSet.getString("grade"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Personality Development Grade list from database due to:::"
							+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String verifyPhysicalCreativeActivity(int subjectID) {

		String check = "notFound";

		try {
			connection = getConnection();

			String verifyPhysicalCreativeActivityQuery = QueryMaker.VERIFY_PHYSICAL_CREATIVE_ACTIVITY;

			preparedStatement = connection.prepareStatement(verifyPhysicalCreativeActivityQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, "Extra-curricular: Physical");
			preparedStatement.setString(3, "Extra-curricular: Creative");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("subjectType").equals("Extra-curricular: Physical")) {
					check = "Physicalfound";
				} else {
					check = "Creativefound";
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "notFound";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public double retrieveSubjectScaleTo(int subjectAssessmentID) {

		double scaleTo = 0D;

		try {

			connection = getConnection();

			String retrieveSubjectScaleToQuery = QueryMaker.RETRIEVE_SUBJECT_SCALE_TO;

			preparedStatement = connection.prepareStatement(retrieveSubjectScaleToQuery);

			preparedStatement.setInt(1, subjectAssessmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getDouble("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public String retrieveSubjectListForStandardByStandardID(int standardID) {

		String Subject = "";

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveSubjectListForStandardByStandardIDQuery = QueryMaker.RETRIEVE_SubjectList_BY_Standard_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForStandardByStandardIDQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String subList = resultSet.getString("subjectList");

				String retrieveSubjectListBySortOrderQuery = "SELECT * FROM Subject WHERE id IN(" + subList
						+ ") AND subjectType = 'Scholastic' ORDER BY sortOrder ASC";

				System.out.println("retrieveSubjectListBySortOrderQuery: " + retrieveSubjectListBySortOrderQuery);
				preparedStatement1 = connection.prepareStatement(retrieveSubjectListBySortOrderQuery);

				resultSet1 = preparedStatement1.executeQuery();

				while (resultSet1.next()) {

					String Subject1 = "";

					Subject1 = "" + resultSet1.getInt("id");

					System.out.println("Subject1: " + Subject1);
					if (Subject1 == null) {
						continue;
					} else {
						Subject = Subject + ',' + Subject1;
					}

					if (Subject.startsWith(",")) {
						Subject = Subject.substring(1);
					}
				}
				/*
				 * String subArr[] = resultSet.getString("subjectList").split(",");
				 * 
				 * for (int j = 0; j < subArr.length; j++) {
				 * 
				 * int Subject1 = 0;
				 * 
				 * Subject1 =
				 * daoInf.retrieveSubjectIDBySubjectType(Integer.parseInt(subArr[j].trim()));
				 * 
				 * if (Subject1 == 0) { continue; } else { Subject = Subject + ',' + Subject1; }
				 * 
				 * if (Subject.startsWith(",")) { Subject = Subject.substring(1); } }
				 */

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Subject List For Standard By StandardID "
					+ "from database due to:::" + exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		System.out.println("Subject: " + Subject);
		return Subject;
	}

	public HashMap<String, Integer> retrieveExaminationList(int academicYearID, String term, int aYClassID) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		LoginDAOInf daoInf = new LoginDAOImpl();

		int counter = 1;
		int counter1 = 1;
		int counter2 = 1;

		try {
			connection = getConnection();

			String retrieveExaminationListQuery = QueryMaker.RETRIEVE_Examination_List;

			preparedStatement = connection.prepareStatement(retrieveExaminationListQuery);

			preparedStatement.setInt(1, aYClassID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, term);
			System.out.println("query 11 ::: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString("examType").equals("Term End")) {

					map.put("Term End", resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Unit Test")) {

					map.put("Unit Test", resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Subject Enrichment")) {

					String[] array = resultSet.getString("examName").split(" ");

					counter = Integer.parseInt(array[array.length - 1]);

					map.put("Subject Enrichment" + counter, resultSet.getInt("id"));

					/*
					 * if(resultSet.getString("examName").equals("Subject Enrichment " + counter)) {
					 * 
					 * 
					 * 
					 * counter++; }else { counter++; map.put("Subject Enrichment" + counter,
					 * resultSet.getInt("id")); counter = 1; }
					 */

				} else if (resultSet.getString("examType").equals("Notebook")) {

					map.put("Notebook", resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Portfolio")) {

					String[] array = resultSet.getString("examName").split(" ");

					counter1 = Integer.parseInt(array[array.length - 1]);

					map.put("Portfolio" + counter1, resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Multiple Assessment")) {

					String[] array = resultSet.getString("examName").split(" ");

					counter2 = Integer.parseInt(array[array.length - 1]);

					map.put("Multiple Assessment" + counter2, resultSet.getInt("id"));

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Examination list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return map;
	}

	public int retrieveScholasticGradeList(int subjectID, Integer examId, int studentID, int ayClassID) {

		int scaleTo = 0;

		try {

			connection = getConnection();

			String retrieveScholasticGradeListQuery = QueryMaker.RETRIEVE_Scholastic_Grade_List;

			preparedStatement = connection.prepareStatement(retrieveScholasticGradeListQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, examId);
			preparedStatement.setInt(3, studentID);
			preparedStatement.setInt(4, ayClassID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getInt("marksScaled");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public String retrieveSubject(int subjectID) {

		String Subject = "";

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveSubjectQuery = QueryMaker.RETRIEVE_Subject_SubjectID;

			preparedStatement = connection.prepareStatement(retrieveSubjectQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Subject = resultSet.getString("name");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Subject List For Standard By StandardID "
					+ "from database due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Subject;
	}

	public int retrieveScaleTo(int subjectID, Integer examId, int ayClassID) {

		int scaleTo = 0;

		try {

			connection = getConnection();

			String retrieveScaleToQuery = QueryMaker.RETRIEVE_Scale_To_List;

			preparedStatement = connection.prepareStatement(retrieveScaleToQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setInt(3, examId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			// exception.printStackTrace();
			System.out.println("Exception occurred while retrieving scale to due to.." + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public String getStandardNameByStandardID(int standardID) {

		String Subject = "";

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String getStandardNameByStandardIDQuery = QueryMaker.RETRIEVE_StandardName_By_StandardID;

			preparedStatement = connection.prepareStatement(getStandardNameByStandardIDQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Subject = resultSet.getString("standard");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving StandardName By StandardID "
					+ "from database due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Subject;
	}

	public String verifySubjectType1(int subjectID) {

		String check = "";

		try {

			connection1 = getConnection();

			String verifySubjectType1Query = QueryMaker.VERIFY_Subject_Type1;

			preparedStatement1 = connection1.prepareStatement(verifySubjectType1Query);

			preparedStatement1.setInt(1, subjectID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				if (resultSet1.getString("subjectType").equals("Extra-curricular: Physical")) {

					check = "Physical Found";
				} else if (resultSet1.getString("subjectType").equals("Extra-curricular: Creative")) {
					check = "Creative Found";

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = "Error";
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return check;
	}

	public String retrieveMonthsforStudent(int attendanceID) {

		String month = "";

		try {

			connection1 = getConnection();

			String retrieveMonthsforStudentQuery = QueryMaker.RETREIVE_Month_List_For_Student;

			preparedStatement1 = connection1.prepareStatement(retrieveMonthsforStudentQuery);

			preparedStatement1.setInt(1, attendanceID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				month = resultSet1.getString("workingMonth");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retriving Students Month List for Student from database due to:::"
							+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}
		return month;
	}

	public String retrieveAttendanceIDforStudent(String term, int academicYearID, int standardID) {

		String AttendanceID = "";

		try {

			connection = getConnection();

			String retrieveAttendanceIDforStudentQuery = QueryMaker.RETREIVE_AttendanceID_For_Student;

			preparedStatement = connection.prepareStatement(retrieveAttendanceIDforStudentQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AttendanceID = AttendanceID + "," + resultSet.getInt("id");
			}

			if (AttendanceID.startsWith(",")) {

				AttendanceID = AttendanceID.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving AttendanceID from database due to:::" + exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return AttendanceID;
	}

	public int retrievedaysPresentforStudent(int studentID, int attendanceID) {

		int daysPresent = 0;

		try {

			connection = getConnection();

			String retrievedaysPresentforStudentQuery = QueryMaker.RETREIVE_daysPresent_For_Student;

			preparedStatement = connection.prepareStatement(retrievedaysPresentforStudentQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, attendanceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				daysPresent = resultSet.getInt("daysPresent");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving STudents Present Days Of Attendance from database due to:::"
							+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return daysPresent;
	}

	public boolean verifyAbsent(int subjectID, int studentID, int ayClassID, int examID) {
		System.out.println("test data :: " + subjectID + " " + studentID + " " + ayClassID + " " + examID);
		boolean check = false;

		try {

			connection = getConnection();

			if (ayClassID == 0) {

				String verifyAbsentQuery = QueryMaker.VERIFY_ABSENT1;

				preparedStatement = connection.prepareStatement(verifyAbsentQuery);

				preparedStatement.setInt(1, studentID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, subjectID);
				preparedStatement.setInt(4, examID);

			} else {
				String verifyAbsentQuery = QueryMaker.VERIFY_ABSENT;

				preparedStatement = connection.prepareStatement(verifyAbsentQuery);

				preparedStatement.setInt(1, studentID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, subjectID);
				preparedStatement.setInt(4, ayClassID);
				preparedStatement.setInt(5, examID);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getInt("absentFlag") == 1) {
					check = true;
				} else {
					check = false;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Absent Flag for Scholastic Subject type from database due to:::"
							+ exception.getMessage());

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return check;
	}

	public boolean verifyAbsentFlag(int subjectID, int studentID, int ayClassID, String term) {

		boolean check = false;

		try {

			connection1 = getConnection();

			String verifyAbsentFlagQuery = QueryMaker.VERIFY_ABSENT_Flag;

			preparedStatement1 = connection1.prepareStatement(verifyAbsentFlagQuery);

			preparedStatement1.setInt(1, studentID);
			preparedStatement1.setInt(2, subjectID);
			preparedStatement1.setInt(3, ayClassID);
			preparedStatement1.setString(4, term);
			preparedStatement1.setString(5, "Term End");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				if (resultSet1.getInt("absentFlag") == 1) {
					check = true;
				} else {
					check = false;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Absent Flag for Gradebased Subjets from database due to:::"
							+ exception.getMessage());

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}
		return check;
	}

	public String disableOldStudentID(int studentID) {
		// TODO Auto-generated method stub
		try {
			connection = getConnection();

			String disableOldStudentIDQuery = QueryMaker.UPDATE_STUDENT_ASSESSMENT_ACTIVITY_STATUS;

			preparedStatement = connection.prepareStatement(disableOldStudentIDQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, studentID);

			preparedStatement.execute();

			System.out.println("Disabled Old Student Assessment details successfully..");
			status = "success";

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while disabling Old Student Assessment detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int retrieveoutOfMarksForSubject(int subjectID, Integer examId, int ayClassID) {

		int outOfMarks = 0;

		try {

			connection = getConnection();

			String retrieveoutOfMarksForSubjectQuery = QueryMaker.RETRIEVE_outOf_Marks;

			preparedStatement = connection.prepareStatement(retrieveoutOfMarksForSubjectQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setInt(3, examId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				outOfMarks = resultSet.getInt("totalMarks");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return outOfMarks;
	}

	public int retrieveScholasticGradeList1(int subjectID, int studentID, int ayClassID, String term) {

		int marksScaled = 0;

		try {

			connection = getConnection();

			if (ayClassID == 0) {
				String retrieveScholasticGradeList1Query = QueryMaker.RETRIEVE_Scholastic_Grade1_List1;

				preparedStatement = connection.prepareStatement(retrieveScholasticGradeList1Query);

				preparedStatement.setInt(1, subjectID);
				preparedStatement.setString(2, "Subject Enrichment");
				preparedStatement.setString(3, "Notebook");
				preparedStatement.setString(4, term);
				preparedStatement.setInt(5, studentID);
				preparedStatement.setString(6, ActivityStatus.ACTIVE);
			} else {
				String retrieveScholasticGradeList1Query = QueryMaker.RETRIEVE_Scholastic_Grade1_List;

				preparedStatement = connection.prepareStatement(retrieveScholasticGradeList1Query);

				preparedStatement.setInt(1, subjectID);
				preparedStatement.setString(2, "Subject Enrichment");
				preparedStatement.setString(3, "Notebook");
				preparedStatement.setString(4, term);
				preparedStatement.setInt(5, studentID);
				preparedStatement.setInt(6, ayClassID);
				preparedStatement.setString(7, ActivityStatus.ACTIVE);
			}
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				marksScaled = resultSet.getInt("marksScaled");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return marksScaled;
	}

	public JSONObject retrieveStudentAssessmentHistory(int studentID, int subjectAssessmentID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveStudentAssessmentHistoryQuery = QueryMaker.RETRIEVE_STUDENT_ASSESSMENT_HISTORY;

			preparedStatement = connection.prepareStatement(retrieveStudentAssessmentHistoryQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, subjectAssessmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("check", check);
				object.put("StudentAssessmentID", resultSet.getInt("id"));
				object.put("studentName", resultSet.getString("studentName"));
				object.put("marksObtained", resultSet.getInt("marksObtained"));
				object.put("marksScaled", resultSet.getInt("marksScaled"));
				object.put("grade", resultSet.getString("grade"));
				object.put("activityStatus", resultSet.getString("activityStatus"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {
				object = new JSONObject();

				object.put("check", check);
				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving studnet assessment details due to:::" + exception.getMessage());

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public List<ConfigurationForm> retrieveAttendanceListforStudent(int studentID, String attendanceIDList,
			String standard) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrieveAttendanceListforStudentQuery = "SELECT sum(at.workingDays)AS workingDays, at.term, sum(sat.daysPresent)AS daysPresent FROM Attendance AS at, StudentAttendance AS sat WHERE at.id = sat.attendanceID AND sat.registrationID = (SELECT id FROM Registration WHERE studentID = "
					+ studentID + " AND activityStatus = 'Active') AND sat.attendanceID IN (" + attendanceIDList
					+ ") GROUP BY at.term";

			/*
			 * System.out.println("retrieveAttendanceListforStudentQuery:"+
			 * retrieveAttendanceListforStudentQuery);
			 */

			preparedStatement = connection.prepareStatement(retrieveAttendanceListforStudentQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				String[] array = attendanceIDList.split(",");

				// System.out.println("array.length-1 :"+(array.length));
				// form.setWorkingMonth(daoInf2.retrieveMonthsforStudent(Integer.parseInt(array[0]))
				// + "-" +
				// daoInf2.retrieveMonthsforStudent(Integer.parseInt(array[array.length-1])));
				form.setWorkingMonth(resultSet.getString("term"));

				/*
				 * if (standard.equals("VIII")) {
				 * form.setStudentWorkingDays(resultSet.getInt("daysPresent") + "/100"); } else
				 * {
				 */
				form.setStudentWorkingDays(resultSet.getInt("daysPresent") + "/" + resultSet.getInt("workingDays"));
				/* } */

				list.add(form);
			}
			System.out.println("list:" + list);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Attendance List from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String retrieveGradeValue(int subjectID, int studentID, int ayClassID, Integer examId) {

		String gradeValue = "";

		try {

			connection1 = getConnection();

			String retrieveGradeValueQuery = QueryMaker.RETREIVE_Grade_Value_For_Student;

			preparedStatement1 = connection1.prepareStatement(retrieveGradeValueQuery);

			preparedStatement1.setInt(1, subjectID);
			preparedStatement1.setInt(2, ayClassID);
			preparedStatement1.setInt(3, examId);
			preparedStatement1.setInt(4, studentID);
			preparedStatement1.setString(5, ActivityStatus.ACTIVE);
			System.out.println("preparedStatement1 :::::: " + preparedStatement1);
			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				gradeValue = resultSet1.getString("grade");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Grade from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}
		return gradeValue;
	}

	public LinkedHashMap<Integer, String> retrieveExistingStudentAssessmentListForClassTeacher(int ayClassID) {

		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveExistingStudentAssessmentListForClassTeacherQuery = QueryMaker.RETRIEVE_Existing_StudentAssessmentList_ForClassTeacher;

			preparedStatement = connection.prepareStatement(retrieveExistingStudentAssessmentListForClassTeacherQuery);

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"),
						resultSet.getString("studentName") + "=" + resultSet.getInt("rollNumber"));
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

	public int retrievemarksObtained(int subjectID, Integer examID, int studentID, int ayClassID) {

		int marksObtained = 0;

		try {

			connection = getConnection();

			String retrievemarksObtainedQuery = QueryMaker.RETRIEVE_Marks_Obtained;

			preparedStatement = connection.prepareStatement(retrievemarksObtainedQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, examID);
			preparedStatement.setInt(3, studentID);
			preparedStatement.setInt(4, ayClassID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				marksObtained = resultSet.getInt("marksObtained");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return marksObtained;
	}

	public String getStandardStageByStandardID(int standardID) {

		String Stage = "";

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String getStandardStageByStandardIDQuery = QueryMaker.RETRIEVE_Standard_Stage_By_StandardID;

			preparedStatement = connection.prepareStatement(getStandardStageByStandardIDQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Stage = resultSet.getString("stage");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Standard Stage By StandardID "
					+ "from database due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Stage;
	}

	public String retrieveExamName(int academicYearID) {

		String ExamName = "";

		try {

			connection = getConnection();

			String retrieveExamNameQuery = QueryMaker.RETRIEVE_Exam_Name;

			preparedStatement = connection.prepareStatement(retrieveExamNameQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Term End");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ExamName = resultSet.getString("examName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ExamName;
	}

	public int retrieveExamID(int academicYearID) {

		int ExamID = 0;

		try {

			connection = getConnection();

			String retrieveExamIDQuery = QueryMaker.RETRIEVE_Exam_Name;

			preparedStatement = connection.prepareStatement(retrieveExamIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Term End");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ExamID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ExamID;
	}

	public JSONObject retrieveSubjectListByUserIDForPersonalityDevelopment(int userID, int ayClassID,
			int organizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDForPersonalityDevelopmentQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExamType;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDForPersonalityDevelopmentQuery);

			String subjectList = "";

			preparedStatement.setString(1, "Personality Development & Life Skills");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectName");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public HashMap<Integer, String> retrieveSubjectListByExamIDByForPersonalityDevelopment(int userID, int ayClassID,
			int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveSubjectListByExamIDByForPersonalityDevelopmentQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExamType;

			preparedStatement = connection
					.prepareStatement(retrieveSubjectListByExamIDByForPersonalityDevelopmentQuery);

			String subjectList = "";

			preparedStatement.setString(1, "Personality Development & Life Skills");

			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subjectName"));

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

	public String retriveStandarNameByStage(String stage, int examinationID) {

		String list = "";
		try {

			connection = getConnection();

			String retriveStandarNameByStageQuery = QueryMaker.RETRIEVE_StandardName_By_Stage;

			preparedStatement = connection.prepareStatement(retriveStandarNameByStageQuery);

			preparedStatement.setString(1, stage);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.setInt(3, examinationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list = list + "," + resultSet.getString("standard");

			}

			if (list.startsWith(",")) {
				list = list.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public HashMap<Integer, String> retrieveStandardWiseSubjectList(int standardID, int ayClassID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		LoginDAOInf daoInf = new LoginDAOImpl();

		try {
			connection = getConnection();

			String retrieveStandardWiseSubjectListQuery = QueryMaker.RETRIEVE_Subject_List_For_CLass;

			preparedStatement = connection.prepareStatement(retrieveStandardWiseSubjectListQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String Subject = "";

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					String Subject1 = daoInf
							.retrieveScholasticSubjectNameBySubjectID(Integer.parseInt(subArr[j].trim()), "Scholastic");

					if (Subject1 == null) {

						continue;
					} else if (Subject1.equals("Computer")) {

						continue;
					} else if (Subject1.equals("Marathi")
							&& !daoInf.verifySubjectIsMarkBased(Integer.parseInt(subArr[j].trim()), ayClassID)) {
						continue;
					} else {

						map.put(Integer.parseInt(subArr[j].trim()), Subject1);
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Subject List For CLass from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return map;

	}

	public HashMap<Integer, String> retrieveSubjectListByExamIDByForExtraCurricularActivity(int userID, int ayClassID,
			int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveSubjectListByExamIDByForExtraCurricularActivityQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExtraCurricularActivity;

			preparedStatement = connection
					.prepareStatement(retrieveSubjectListByExamIDByForExtraCurricularActivityQuery);

			String subjectList = "";

			preparedStatement.setString(1, "%Extra-curricular%");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subjectName"));

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

	public JSONObject retrieveSubjectListByUserIDForExtraCurricular(int userID, int ayClassID, int organizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDForExtraCurricularQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExtraCurricular;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDForExtraCurricularQuery);

			String subjectList = "";

			preparedStatement.setString(1, "%Extra-curricular%");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectName");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public HashMap<Integer, String> retrieveSubjectListByUserIDByStandard(int userID, int ayClassID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveSubjectListByUserIDByStandardQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_Standard;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByStandardQuery);

			String subjectList = "";

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subjectName"));

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

	public JSONObject retrieveSubjectListByUserIDStandard(int userID, int ayClassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDStandardQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_Standard;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDStandardQuery);

			String subjectList = "";

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectName");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public JSONObject retrieveSubjectListForClassTeacherBYUserID(int userID, int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectListForClassTeacherBYUserIDQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveSubjectListForClassTeacherBYUserIDQuery);

			String subjectList = "";

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subject");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public HashMap<String, String> getStandardListByTeacherForPersonalityDevelopment(int userID, int organizationID) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String getStandardListByTeacherForPersonalityDevelopmentQuery = QueryMaker.RETRIEVE_Standard_LIST_By_Teacher_Personality_Development;

			preparedStatement = connection.prepareStatement(getStandardListByTeacherForPersonalityDevelopmentQuery);

			preparedStatement.setString(1, "Personality Development & Life Skills");

			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, userID);
			preparedStatement.setInt(4, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String key = resultSet.getInt("standardID") + "-" + resultSet.getInt("divisionID") + "-"
						+ resultSet.getInt("ayclassID");
				String value = resultSet.getString("standardName") + "-" + resultSet.getString("divisionName");

				map.put(key, value);

			}
			// System.out.println("List..." + map);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public HashMap<String, String> getStandardListByTeacherForExtraCurricular(int userID, int organizationID) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String getStandardListByTeacherForExtraCurricularQuery = QueryMaker.RETRIEVE_Standard_LIST_By_Teacher_ExtraCurricular;

			preparedStatement = connection.prepareStatement(getStandardListByTeacherForExtraCurricularQuery);

			preparedStatement.setString(1, "%Extra-curricular%");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, userID);
			preparedStatement.setInt(4, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String key = resultSet.getInt("standardID") + "-" + resultSet.getInt("divisionID") + "-"
						+ resultSet.getInt("ayclassID");
				String value = resultSet.getString("standardName") + "-" + resultSet.getString("divisionName");

				map.put(key, value);

			}
			// System.out.println("List..." + map);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public JSONObject deleteStudentAssessmentRow(int studentID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteAYClassRowQuery = QueryMaker.DELETE_StudentAssessment_Row;

			preparedStatement = connection.prepareStatement(deleteAYClassRowQuery);

			preparedStatement.setInt(1, studentID);

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

	public boolean verifyClassTeacherNew(int userID, int academicYearID, String activityStatus) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyClassTeacherQuery = QueryMaker.VERIFY_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(verifyClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, activityStatus);

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

	public JSONObject retrieveExamNameByExamID(int examinationID, int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveExamNameByExamIDQuery = QueryMaker.Retrieve_ExamName_By_ExamID;

			preparedStatement = connection.prepareStatement(retrieveExamNameByExamIDQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("examName", resultSet.getString("examName"));

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

	public HashMap<Integer, String> retrieveExaminationListForTimeTable(int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrieveExaminationListForTimeTableQuery = QueryMaker.RETRIEVE_Examination_LIST_For_TimeTable;

			preparedStatement = connection.prepareStatement(retrieveExaminationListForTimeTableQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("examID"), resultSet.getString("examName"));

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

	public List<String> retrieveDateList(int examinationID) {

		List<String> list = new ArrayList<String>();

		ConfigurationForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String Date = "";

		try {
			connection = getConnection();

			String DateListQuery = QueryMaker.RETRIEVE_EXISTING_Date_List;

			preparedStatement = connection.prepareStatement(DateListQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setString(2, "Primary");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				// Inserting Start Date
				if (resultSet.getString("examDate") == null || resultSet.getString("examDate") == "") {

					// form.setStartDate(null);
					Date = "";

				} else if (resultSet.getString("examDate").isEmpty()) {
					Date = "";

				} else {
					Date = (String) dateFormat.format(dateToBeFormatted.parse(resultSet.getString("examDate")));
				}

				list.add(Date);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<Integer> retrieveStandardIDList(String stage, int examinationID, String date) {

		List<Integer> list = new ArrayList<Integer>();

		ConfigurationForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		int StandardID = 0;

		try {
			connection = getConnection();

			String retrieveStandardIDListQuery = QueryMaker.RETRIEVE_EXISTING_StandardID_List;

			preparedStatement = connection.prepareStatement(retrieveStandardIDListQuery);

			preparedStatement.setString(1, stage);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.setInt(3, examinationID);

			String Date = dateToBeFormatted.format(dateFormat.parse(date));

			preparedStatement.setString(4, Date);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				StandardID = resultSet.getInt("standardID");

				list.add(StandardID);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<String> retrieveSubjectList(int examinationID, String date, int standardID) {

		List<String> list = new ArrayList<String>();

		ConfigurationForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String Subject = "";

		try {
			connection = getConnection();

			String retrieveSubjectListQuery = QueryMaker.RETRIEVE_EXISTING_Subject_List;

			preparedStatement = connection.prepareStatement(retrieveSubjectListQuery);

			preparedStatement.setInt(1, examinationID);

			String Date = dateToBeFormatted.format(dateFormat.parse(date));

			preparedStatement.setString(2, Date);

			preparedStatement.setInt(3, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				Subject = resultSet.getString("subject");

				list.add(Subject);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public String retrieveTerm(int academicYearID) {

		String Term = "";

		try {

			connection = getConnection();

			String retrieveExamNameQuery = QueryMaker.RETRIEVE_Exam_Name;

			preparedStatement = connection.prepareStatement(retrieveExamNameQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Term End");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Term = resultSet.getString("term");
			}
			System.out.println("Term: " + Term);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return Term;
	}

	public int retrieveExamIDForTermI(int academicYearID) {

		int ExamID = 0;

		try {

			connection = getConnection();

			String retrieveExamIDQuery = QueryMaker.RETRIEVE_ExamID_For_TermI;

			preparedStatement = connection.prepareStatement(retrieveExamIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Term End");
			preparedStatement.setString(3, "Term I");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ExamID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ExamID;
	}

	public String retrieveStdDivName(int studentID) {

		String Name = "";

		try {

			connection = getConnection();

			String retrieveStdDivNameQuery = QueryMaker.RETRIEVE_StdDivName_studentID;

			preparedStatement = connection.prepareStatement(retrieveStdDivNameQuery);

			preparedStatement.setInt(1, studentID);

			preparedStatement.setString(2, ActivityStatus.DRAFT);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Name = resultSet.getString("standard") + resultSet.getString("division");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return Name;
	}

	public String retrieveStdDivNameForLeavivingCertificate(int studentID) {

		String Name = "";

		try {

			connection = getConnection();

			String retrieveStdDivNameQuery = QueryMaker.RETRIEVE_StdDivName_studentID;

			preparedStatement = connection.prepareStatement(retrieveStdDivNameQuery);

			preparedStatement.setInt(1, studentID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Name = resultSet.getString("standard") + resultSet.getString("division");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return Name;
	}

	public String retrieveSubjectTypeBySubjectID(int subjectID) {

		String subjectType = "";

		try {

			connection = getConnection();

			String retrieveSubjectTypeBySubjectIDQuery = QueryMaker.RETRIEVE_SubjectType_By_SubjectID;

			preparedStatement = connection.prepareStatement(retrieveSubjectTypeBySubjectIDQuery);

			preparedStatement.setInt(1, subjectID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectType = resultSet.getString("subjectType");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return subjectType;
	}

	public JSONObject retrieveActivitiesName(int subjectID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		String activity = "";
		int activityID = 0;

		try {
			connection = getConnection();

			String retrieveActivitiesNameQuery = QueryMaker.RETRIEVE_Activity_LIST;

			preparedStatement = connection.prepareStatement(retrieveActivitiesNameQuery);

			preparedStatement.setInt(1, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("activity", resultSet.getString("activity"));
				object.put("activityID", resultSet.getInt("id"));
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

	public int retrievesubjectAssessmentID(int examinationID, int subjectID, int ayCLassID) {

		int subjectAssessmentID = 0;

		try {

			connection = getConnection();

			String retrievesubjectAssessmentIDQuery = QueryMaker.RETRIEVE_Subject_Assessment_ID;

			preparedStatement = connection.prepareStatement(retrievesubjectAssessmentIDQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, ayCLassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				subjectAssessmentID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return subjectAssessmentID;
	}

	public String insertActivityAssessment(int subjectAssessmentID, int activityID, int totalMarks) {

		try {

			connection = getConnection();

			String insertActivityAssessmentQuery = QueryMaker.INSERT_Activity_ASSESSMENT;

			preparedStatement = connection.prepareStatement(insertActivityAssessmentQuery);

			preparedStatement.setInt(1, subjectAssessmentID);
			preparedStatement.setInt(2, activityID);
			preparedStatement.setDouble(3, totalMarks);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Activity Assessment detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Activity Assessment into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public JSONObject retrieveActivityNameByactivityID(int activityID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		String activity = "";

		try {
			connection = getConnection();

			String retrieveActivityNameByactivityIDQuery = QueryMaker.RETRIEVE_Activity_Name_By_ActivityID;

			preparedStatement = connection.prepareStatement(retrieveActivityNameByactivityIDQuery);

			preparedStatement.setInt(1, activityID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("activity", resultSet.getString("activity"));
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

	public boolean verifySubjectIDIsCoScholastic(int subjectID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyClassTeacherQuery = QueryMaker.VERIFY_SubjectID_Is_CoScholastic;

			preparedStatement = connection.prepareStatement(verifyClassTeacherQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, "Co-scholastic");

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

	public JSONObject retrieveActivitiesListBysubjAssmntID(int subjAssmntID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check = 0;
		String teacherName = "";

		try {

			connection = getConnection();

			String retrieveActivitiesListBysubjAssmntIDQuery = QueryMaker.RETRIEVE_Activities_List_By_subjAssmntID;

			preparedStatement = connection.prepareStatement(retrieveActivitiesListBysubjAssmntIDQuery);

			preparedStatement.setInt(1, subjAssmntID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("check", check);
				object.put("activityAssessmentID", resultSet.getInt("id"));
				object.put("activity", resultSet.getString("activity"));
				object.put("totalMarks", resultSet.getString("totalMarks"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {
				object = new JSONObject();

				check = 0;

				object.put("check", check);

				array.add(object);

				values.put("Release", array);
			}

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			object = new JSONObject();

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public HashMap<String, String> getStandardListByTeacherForCoScholastic(int userID, int organizationID) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String getStandardListByTeacherForPersonalityDevelopmentQuery = QueryMaker.RETRIEVE_Standard_LIST_By_Teacher_Personality_Development;

			preparedStatement = connection.prepareStatement(getStandardListByTeacherForPersonalityDevelopmentQuery);

			preparedStatement.setString(1, "Co-scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, userID);
			preparedStatement.setInt(4, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String key = resultSet.getInt("standardID") + "-" + resultSet.getInt("divisionID") + "-"
						+ resultSet.getInt("ayclassID");
				String value = resultSet.getString("standardName") + "-" + resultSet.getString("divisionName");

				map.put(key, value);

			}
			// System.out.println("List..." + map);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public HashMap<Integer, String> retrieveSubjectListByExamIDByForCoScholastic(int userID, int ayClassID,
			int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveSubjectListByExamIDByForPersonalityDevelopmentQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExamType;

			preparedStatement = connection
					.prepareStatement(retrieveSubjectListByExamIDByForPersonalityDevelopmentQuery);

			String subjectList = "";

			preparedStatement.setString(1, "Co-scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("subjectID"), resultSet.getString("subjectName"));

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

	public JSONObject retrieveSubjectListByUserIDForCoscholastic(int userID, int ayClassID, int organizationID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDForCoscholasticQuery = QueryMaker.RETRIEVE_SUBJECT_LIST_BY_USER_ID_AND_ExamType;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDForCoscholasticQuery);

			String subjectList = "";

			preparedStatement.setString(1, "Co-scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectName");

				check1 = 1;

				object = new JSONObject();

				object.put("subjectList", subjectList);
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public String retrieveActivitiesNameBySubjectID(int subjectID, int subjectAssessmentID) {

		String list = "";
		try {

			connection = getConnection();

			String retriveStandarNameByStageQuery = QueryMaker.RETRIEVE_ActivitiesName_By_SubjectID;

			preparedStatement = connection.prepareStatement(retriveStandarNameByStageQuery);

			preparedStatement.setInt(1, subjectID);

			preparedStatement.setInt(2, subjectAssessmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list = list + "," + resultSet.getString("activity") + "(" + resultSet.getInt("totalMarks") + ")" + "$"
						+ resultSet.getInt("totalMarks") + "=" + resultSet.getInt("id");
			}

			if (list.startsWith(",")) {
				list = list.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public JSONObject deleteactivityAssessment(int activityAssessmentID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteactivityAssessmentQuery = QueryMaker.DELETE_activityAssessment_BY_ID;

			preparedStatement = connection.prepareStatement(deleteactivityAssessmentQuery);

			preparedStatement.setInt(1, activityAssessmentID);

			preparedStatement.executeUpdate();

			check = 1;

			object.put("check", check);

			array.add(object);

			values.put("Release", array);

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting row due to:::" + exception.getMessage());

			check = 0;

			object.put("check", check);
			array.add(object);

			values.put("Release", array);

			return values;
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public HashMap<String, String> getExamListByTermAcademicYearID(int standardID, int academicYearID, String term) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			// String getExamListQuery = QueryMaker.RETRIEVE_Exam_LIST_ByTermAcademicYearID;

			String getExamListQuery = "select distinct e.id, e.examName from SubjectAssessment as sa, Examination as e where sa.examinationID=e.id and "
					+ "sa.ayClassID in(select id from AYClass where standardID =" + standardID
					+ " and academicYearID =e.academicYearID) and " + "e.term = '" + term + "' and e.academicYearID="
					+ academicYearID + " ORDER BY e.id ASC";

			preparedStatement = connection.prepareStatement(getExamListQuery);

			/*
			 * preparedStatement.setInt(1, standardID); preparedStatement.setString(2,
			 * term); preparedStatement.setInt(3, academicYearID);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("examName") + "$" + resultSet.getInt("id"),
						resultSet.getString("examName"));

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

	public HashMap<String, String> getALLExamListByTermAcademicYearID(int standardID, int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String getExamListQuery = QueryMaker.RETRIEVE_AllExam_LIST_ByTermAcademicYearID;

			preparedStatement = connection.prepareStatement(getExamListQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("examName") + "$" + resultSet.getInt("id"),
						resultSet.getString("examName"));

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

	public int retrievestudentAssessmentID(int subjectAssessmentID, int registrationID) {

		int studentAssessmentID = 0;

		try {

			connection = getConnection();

			String retrievestudentAssessmentIDQuery = QueryMaker.RETRIEVE_StudentAssessmentID;

			preparedStatement = connection.prepareStatement(retrievestudentAssessmentIDQuery);

			preparedStatement.setInt(1, registrationID);

			preparedStatement.setInt(2, subjectAssessmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				studentAssessmentID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return studentAssessmentID;
	}

	public String insertStudentActivityAssessmentDetails(int studentAssessmentID, int activityAssessmentID,
			int totalMarks) {

		try {

			connection = getConnection();

			String insertStudentActivityAssessmentDetailsQuery = QueryMaker.INSERT_STUDENT_Activity_ASSESSMENT_DETAILS;

			preparedStatement = connection.prepareStatement(insertStudentActivityAssessmentDetailsQuery);

			preparedStatement.setInt(1, studentAssessmentID);
			preparedStatement.setInt(2, activityAssessmentID);
			preparedStatement.setInt(3, totalMarks);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<ConfigurationForm> retrievestudentActivityAssessmentAssessmentList(int studentAssessmentID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrievestudentActivityAssessmentAssessmentListQuery = QueryMaker.Retrieve_StudentActivityAssessment_List;

			preparedStatement = connection.prepareStatement(retrievestudentActivityAssessmentAssessmentListQuery);

			preparedStatement.setInt(1, studentAssessmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setStudentActivityAssessmentID(resultSet.getInt("id"));
				form.setTotalMarks(resultSet.getDouble("totalMarks"));
				form.setActivityAssessmentID(resultSet.getInt("activityAssessmentID"));
				form.setOutOfMarks(resultSet.getDouble("outOfMarks"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Attendance List from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String updateStudentActivityAssessmentDetails(double totalMarks, int studentActivityAssessmentID) {

		try {

			connection = getConnection();

			String updateStudentAssessmentDetailsQuery = QueryMaker.UPDATE_STUDENT_Activity_ASSESSMENT_DETAILS;

			preparedStatement = connection.prepareStatement(updateStudentAssessmentDetailsQuery);

			preparedStatement.setDouble(1, totalMarks);
			preparedStatement.setInt(2, studentActivityAssessmentID);

			preparedStatement.executeUpdate();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int verifyTermIEndDate(int academicYearID) {

		int check = 0;

		try {

			connection = getConnection();

			String verifyTermIEndDateQuery = QueryMaker.Verify_TermI_End_Date;

			preparedStatement = connection.prepareStatement(verifyTermIEndDateQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			check = 0;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public HashMap<Integer, String> getExaminationListByTerm(int academicYearID, String term) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String getExaminationListByTermQuery = QueryMaker.RETRIEVE_Examination_LIST_By_Term;

			preparedStatement = connection.prepareStatement(getExaminationListByTermQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("examName"));

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

	public JSONObject retrieveExamListByTerm(String term, int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveDivisionListForStandardQuery = QueryMaker.RETRIEVE_Examination_LIST_By_Term;

			preparedStatement = connection.prepareStatement(retrieveDivisionListForStandardQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("examID", resultSet.getInt("id"));
				object.put("examName", resultSet.getString("examName"));
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

	public JSONObject retrieveStandardListForAcademicYear(int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveDivisionListForStandardQuery = QueryMaker.RETRIEVE_StandardList_For_ACademicYear;

			preparedStatement = connection.prepareStatement(retrieveDivisionListForStandardQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("standardID", resultSet.getInt("standardID"));
				object.put("standard", resultSet.getString("standard"));
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

	public List<ConfigurationForm> retrieveClassStudentHistoryListForClassTeacher(int standardID, int divisionID,
			int academicYearID, String term) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		try {

			connection = getConnection();

			String retrieveClassStudentListForClassTeacherQuery = QueryMaker.RETREIVE_Class_Student_History_LIST;

			preparedStatement = connection.prepareStatement(retrieveClassStudentListForClassTeacherQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRegistrationID(resultSet.getInt("id"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setAYClassID(resultSet.getInt("aYClassID"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setTerm(term);

				form.setAcademicYearID(academicYearID);

				form.setStandardID(standardID);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Student list from database due to:::" + exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public List<ConfigurationForm> retrieveInActiveStudentDetailsList(int studentID, String term, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveStudentDetailsListQuery = QueryMaker.RETREIVE_Student_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsListQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setString(3, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setStudentName(resultSet.getString("studentName"));

				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					form.setDateOfBirth("");
				} else if (resultSet.getString("dateOfBirth").isEmpty()) {
					form.setDateOfBirth("");
				} else {
					form.setDateOfBirth(dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfBirth"))));
				}

				form.setGrNumber(resultSet.getString("grNumber"));

				form.setStandard(resultSet.getString("standard"));

				form.setDivision(resultSet.getString("division"));

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRegistrationID(resultSet.getInt("id"));

				form.setTerm(term);

				form.setAYClassID(resultSet.getInt("ayClassID"));

				form.setResult(resultSet.getString("studentStatus"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Student Details list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public int retrieveInActiveAYCLassID(int standardID, int divisionID, int academicYearID) {

		int ayCLassID = 0;

		try {

			connection = getConnection();

			String retrieveAYCLassIDQuery = QueryMaker.RETRIEVE_AYClassID1;

			preparedStatement = connection.prepareStatement(retrieveAYCLassIDQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ayCLassID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ayCLassID;
	}

	public String retrieveScholasticRetestMarks(int subjectID, Integer examId, int studentID, int ayClassID) {

		String scaleTo = "";

		try {

			connection = getConnection();

			String retrieveScholasticGradeListQuery = QueryMaker.RETRIEVE_Scholastic_Grade_List;

			preparedStatement = connection.prepareStatement(retrieveScholasticGradeListQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, examId);
			preparedStatement.setInt(3, studentID);
			preparedStatement.setInt(4, ayClassID);
			preparedStatement.setString(5, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo += "," + resultSet.getInt("marksScaled");
			}
			if (scaleTo.startsWith(",")) {
				scaleTo = scaleTo.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public List<String> retrieveDateList1(int examinationID) {

		List<String> list = new ArrayList<String>();

		ConfigurationForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String Date = "";

		try {
			connection = getConnection();

			String DateListQuery = QueryMaker.RETRIEVE_EXISTING_Date_List;

			preparedStatement = connection.prepareStatement(DateListQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setString(2, "Secondary");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				// Inserting Start Date
				if (resultSet.getString("examDate") == null || resultSet.getString("examDate") == "") {

					// form.setStartDate(null);
					Date = "";

				} else if (resultSet.getString("examDate").isEmpty()) {
					Date = "";

				} else {
					Date = (String) dateFormat.format(dateToBeFormatted.parse(resultSet.getString("examDate")));
				}

				list.add(Date);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public int verifyClassTeacherForStandard(int userID, int academicYearID, int ayClassID) {

		int check = 0;

		try {

			connection = getConnection();

			String verifyClassTeacherQuery = QueryMaker.VERIFY_CLASS_TEACHER_New;

			preparedStatement = connection.prepareStatement(verifyClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, ayClassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public List<ConfigurationForm> retrieveExistingStudentAssessmentListNew(int ayClassID, int examID, int subjectID,
			int subjectAssessmentID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String subjectTypeCheck = daoInf2.verifySubjectType1(subjectID);
			if (subjectTypeCheck == "Physical Found") {

				String retrieveExistingStudentAssessmentListQuery = QueryMaker.RETRIEVE_EXISTING_STUDENT_ASSESSMENT_LIST_FOR_PHYSICAL;

				preparedStatement = connection.prepareStatement(retrieveExistingStudentAssessmentListQuery);

				/*
				 * preparedStatement.setInt(1, ayClassID); preparedStatement.setString(2,
				 * "Extra-curricular: Physical"); preparedStatement.setInt(3, examinationID);
				 */

				preparedStatement.setInt(1, subjectID);
				preparedStatement.setInt(2, examID);
				preparedStatement.setInt(3, ayClassID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					configurationForm = new ConfigurationForm();

					configurationForm.setStudentID(resultSet.getInt("studentID"));

					String studetName = "";

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");

					}

					configurationForm.setStudentName(studetName);
					configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
					configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
					configurationForm.setAbsentFlag(resultSet.getInt("absentFlag"));
					configurationForm.setMarksObtained(resultSet.getDouble("marksObtained"));
					configurationForm.setGrade(resultSet.getString("grade"));
					configurationForm.setStudentAssessmentID(resultSet.getInt("studentAssmntID"));
					configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
					// configurationForm.setCreativeActivity(resultSet.getString("creativeActivities"));
					configurationForm.setSubjectAssessmentID(subjectAssessmentID);
					configurationForm.setScaleTo(resultSet.getDouble("scaleTo"));
					configurationForm.setGradeBased(resultSet.getInt("gradeBased"));
					configurationForm.setRegistrationID(resultSet.getInt("registrationID"));

					list.add(configurationForm);
				}

			} else if (subjectTypeCheck == "Creative Found") {

				String retrieveExistingStudentAssessmentListQuery = QueryMaker.RETRIEVE_EXISTING_STUDENT_ASSESSMENT_LIST_FOR_CREATIVE;

				preparedStatement = connection.prepareStatement(retrieveExistingStudentAssessmentListQuery);

				/*
				 * preparedStatement.setInt(1, ayClassID); preparedStatement.setString(2,
				 * "Extra-curricular: Creative"); preparedStatement.setInt(3, examinationID);
				 */

				preparedStatement.setInt(1, subjectID);
				preparedStatement.setInt(2, examID);
				preparedStatement.setInt(3, ayClassID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					configurationForm = new ConfigurationForm();

					configurationForm.setStudentID(resultSet.getInt("studentID"));

					String studetName = "";

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");

					}

					configurationForm.setStudentName(studetName);
					configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
					configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
					configurationForm.setAbsentFlag(resultSet.getInt("absentFlag"));
					configurationForm.setMarksObtained(resultSet.getDouble("marksObtained"));
					configurationForm.setGrade(resultSet.getString("grade"));
					configurationForm.setStudentAssessmentID(resultSet.getInt("studentAssmntID"));
					// configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
					configurationForm.setCreativeActivity(resultSet.getString("creativeActivities"));
					configurationForm.setSubjectAssessmentID(subjectAssessmentID);
					configurationForm.setScaleTo(resultSet.getDouble("scaleTo"));
					configurationForm.setGradeBased(resultSet.getInt("gradeBased"));
					configurationForm.setRegistrationID(resultSet.getInt("registrationID"));

					list.add(configurationForm);
				}

			} else {

				String retrieveExistingStudentAssessmentListQuery = QueryMaker.RETRIEVE_EXISTING_STUDENT_ASSESSMENT_LIST;

				preparedStatement = connection.prepareStatement(retrieveExistingStudentAssessmentListQuery);

				preparedStatement.setInt(1, ayClassID);
				preparedStatement.setInt(2, subjectID);
				preparedStatement.setInt(3, examID);
				preparedStatement.setString(4, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					configurationForm = new ConfigurationForm();

					configurationForm.setStudentID(resultSet.getInt("studentID"));

					String studetName = "";

					if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else if (resultSet.getString("middleName").isEmpty()) {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

					} else {

						studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
								+ resultSet.getString("middleName");
					}

					configurationForm.setStudentName(studetName);
					configurationForm.setTotalMarks(resultSet.getDouble("totalMarks"));
					configurationForm.setRollNumber(resultSet.getInt("rollNumber"));
					configurationForm.setAbsentFlag(resultSet.getInt("absentFlag"));
					configurationForm.setMarksObtained(resultSet.getDouble("marksObtained"));
					configurationForm.setGrade(resultSet.getString("grade"));
					configurationForm.setStudentAssessmentID(resultSet.getInt("id"));
					configurationForm.setPhysicalActivity(resultSet.getString("physicalActivities"));
					configurationForm.setCreativeActivity(resultSet.getString("creativeActivities"));
					configurationForm.setSubjectAssessmentID(subjectAssessmentID);
					configurationForm.setScaleTo(resultSet.getDouble("scaleTo"));
					configurationForm.setGradeBased(resultSet.getInt("gradeBased"));
					configurationForm.setRegistrationID(resultSet.getInt("registrationID"));

					list.add(configurationForm);
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<ConfigurationForm> retrieveAYClassDetails(int academicYearID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;

		try {

			connection = getConnection();

			String retrieveAcademicYearConfigurationsByAcademicYearIDQuery = QueryMaker.RETRIEVE_AcademicYear_Configurations_By_AcademicYearID;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearConfigurationsByAcademicYearIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setAYClassID(resultSet.getInt("id"));
				configurationForm.setTeacherNameID(resultSet.getString("username"));
				configurationForm.setStandard(resultSet.getString("standard"));
				configurationForm.setDivision(resultSet.getString("division"));
				configurationForm.setAcademicYear(resultSet.getString("yearName"));

				list.add(configurationForm);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<ConfigurationForm> retrieveAYSubjectDetails(int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;

		try {

			connection = getConnection();

			String retrieveAYSubjectConfigurationsByAYClassIDQuery = QueryMaker.RETRIEVE_SUB_TEACHER_BY_AYCLASS_ID;

			preparedStatement = connection.prepareStatement(retrieveAYSubjectConfigurationsByAYClassIDQuery);

			preparedStatement.setInt(1, ayClassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setTeacherName1(resultSet.getString("teacher1"));
				configurationForm.setTeacherName2(resultSet.getString("teacher2"));
				configurationForm.setSubject(resultSet.getString("name"));

				list.add(configurationForm);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public int retrieveScaleTo1(int subjectID, int ayClassID) {

		int scaleTo = 0;

		try {

			connection = getConnection();

			String retrieveScaleToQuery = QueryMaker.RETRIEVE_Scale_To_List1;

			preparedStatement = connection.prepareStatement(retrieveScaleToQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setString(3, "Subject Enrichment");
			preparedStatement.setString(4, "Notebook");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public int retrieveScholasticGradeListNew(int subjectID, String examType, int studentID, int ayClassID,
			String term) {

		int marksScaled = 0;

		try {

			connection = getConnection();

			if (ayClassID == 0) {
				String retrieveScholasticGradeListNewQuery = QueryMaker.RETRIEVE_Scholastic_Grade1_List_New1;

				preparedStatement = connection.prepareStatement(retrieveScholasticGradeListNewQuery);

				preparedStatement.setInt(1, subjectID);
				preparedStatement.setString(2, examType);
				preparedStatement.setString(3, term);
				preparedStatement.setInt(4, studentID);
				preparedStatement.setString(5, ActivityStatus.ACTIVE);
			} else {
				String retrieveScholasticGradeListNewQuery = QueryMaker.RETRIEVE_Scholastic_Grade1_List_New;

				preparedStatement = connection.prepareStatement(retrieveScholasticGradeListNewQuery);

				preparedStatement.setInt(1, subjectID);
				preparedStatement.setString(2, examType);
				preparedStatement.setString(3, term);
				preparedStatement.setInt(4, studentID);
				preparedStatement.setInt(5, ayClassID);
				preparedStatement.setString(6, ActivityStatus.ACTIVE);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				marksScaled = resultSet.getInt("marksScaled");
			}

		} catch (Exception exception) {
			// exception.printStackTrace();
			System.out.println(
					"Exception occurred while retrieving scholastic grade list due to..." + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return marksScaled;
	}

	public int retrieveScaleToNew(int subjectID, String examType, int ayClassID, int academicYearID, String term) {

		int scaleTo = 0;

		try {

			connection = getConnection();

			String retrieveScaleToNewQuery = QueryMaker.RETRIEVE_Scale_To_List_New;

			preparedStatement = connection.prepareStatement(retrieveScaleToNewQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setString(3, examType);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setString(5, term);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public int verifyActivityAssessmentBySubjectAssessmentID(int subjectAssessmentID) {

		int check = 0;

		try {

			connection = getConnection();

			String verifyActivityAssessmentBySubjectAssessmentIDQuery = QueryMaker.VERIFY_ActivityAssessment_By_SubjectAssessmentID;

			preparedStatement = connection.prepareStatement(verifyActivityAssessmentBySubjectAssessmentIDQuery);

			preparedStatement.setInt(1, subjectAssessmentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String retrievemarksObtainedForSubjectEnrichment(int subjectID, Integer examID, int studentID,
			int ayClassID) {

		String marksObtained = "";

		try {

			connection = getConnection();

			String retrievemarksObtainedQuery = QueryMaker.RETRIEVE_Marks_Obtained_NEW;

			preparedStatement = connection.prepareStatement(retrievemarksObtainedQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, examID);
			preparedStatement.setInt(3, studentID);
			preparedStatement.setInt(4, ayClassID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				marksObtained = resultSet.getInt("marksObtained") + "$" + resultSet.getInt("marksScaled") + "$"
						+ resultSet.getInt("absentFlag");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return marksObtained;
	}

	public double retrieveMaxScaledMarksAllForStudent(int subjectID, String examType, int studentID, int standardID,
			int academicYearID) {

		int marksScaled = 0;

		try {

			connection = getConnection();

			String retrieveMaxScaledMarksAllForStudentQuery = QueryMaker.RETRIEVE_Scholastic_Grade1_List_New;

			preparedStatement = connection.prepareStatement(retrieveMaxScaledMarksAllForStudentQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, examType);
			preparedStatement.setInt(3, studentID);
			preparedStatement.setInt(4, standardID);
			preparedStatement.setInt(5, academicYearID);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				marksScaled = resultSet.getInt("marksScaled");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return marksScaled;
	}

	public HashMap<String, String> getStandardDivisionListByTeacher(int userID, int academicYearID, String standard) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		try {

			connection = getConnection();

			String getStandardDivisionListByTeacherQuery = QueryMaker.RETRIEVE_Standard_Division_LIST_By_Teacher;

			preparedStatement = connection.prepareStatement(getStandardDivisionListByTeacherQuery);

			preparedStatement.setString(1, standard);
			preparedStatement.setInt(2, 0);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, userID);
			preparedStatement.setInt(6, userID);
			preparedStatement.setInt(7, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String key = resultSet.getInt("standardID") + "-" + resultSet.getInt("divisionID") + "-"
						+ resultSet.getInt("id");

				String value = resultSet.getString("standard") + "-" + resultSet.getString("division");

				map.put(key, value);

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

	public boolean verifyXthSTDTeacher(int userID, int academicYearID, String standard) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyXthSTDTeacherQuery = QueryMaker.VERIFY_Xth_STD_TEACHER;

			preparedStatement = connection.prepareStatement(verifyXthSTDTeacherQuery);

			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, standard);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, userID);
			preparedStatement.setInt(6, userID);
			preparedStatement.setInt(7, userID);

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

	public JSONObject retrieveXthSTDSubjectListForClassTeacherBYUserID(int userID, int academicYearID, int ayClassID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveXthSTDSubjectListForClassTeacherBYUserIDQuery = QueryMaker.RETRIEVE_Xth_STD_SUBJECT_LIST_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveXthSTDSubjectListForClassTeacherBYUserIDQuery);

			String subjectList = "";

			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, userID);
			preparedStatement.setInt(6, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("subjectName", resultSet.getString("name"));
				object.put("subjectID", resultSet.getInt("subjectID"));

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

	public HashMap<String, Integer> retrieveExaminationListForCostumReport(int academicYearID, String term,
			String aYClassID) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		LoginDAOInf daoInf = new LoginDAOImpl();

		int counter = 1;
		int counter1 = 1;

		try {
			connection = getConnection();

			String retrieveExaminationListQuery = "SELECT e.id, e.examName, e.examType FROM Examination AS e, SubjectAssessment AS sa WHERE sa.examinationID=e.id AND "
					+ "sa.ayclassID IN ('" + aYClassID + "') AND e.academicYearID = " + academicYearID
					+ " AND e.term IN ('" + term + "') ORDER BY e.id ASC";

			preparedStatement = connection.prepareStatement(retrieveExaminationListQuery);

			/*
			 * preparedStatement.setString(1, aYClassID); preparedStatement.setInt(2,
			 * academicYearID); preparedStatement.setString(3, term);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString("examType").equals("Term End")) {

					map.put("Term End", resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Unit Test")) {

					map.put("Unit Test", resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Subject Enrichment")) {

					String[] array = resultSet.getString("examName").split(" ");

					counter = Integer.parseInt(array[array.length - 1]);

					map.put("Subject Enrichment" + counter, resultSet.getInt("id"));

				} else if (resultSet.getString("examType").equals("Notebook")) {

					map.put("Notebook", resultSet.getInt("id"));

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Examination list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return map;
	}

	public String retrieveAYCLassIDForCustomReport(int standardID, String divisionID, int academicYearID) {

		String ayCLassID = "";

		try {

			connection = getConnection();

			String retrieveAYCLassIDQuery = "SELECT id FROM AYClass WHERE activityStatus = 'Active' AND standardID = "
					+ standardID + " AND divisionID IN ('" + divisionID + "') AND academicYearID = " + academicYearID
					+ " ";

			preparedStatement = connection.prepareStatement(retrieveAYCLassIDQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ayCLassID += "," + resultSet.getInt("id");
			}

			if (ayCLassID.startsWith(",")) {
				ayCLassID = ayCLassID.substring(1);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ayCLassID;
	}

	public double retrieveMarksScaledForStudentSEA(int subjectID, int studentID, String ayClassID, String termValue) {

		double marksScaled = 0;

		try {

			connection = getConnection();

			// String retrieveScholasticGradeList1Query =
			// QueryMaker.RETRIEVE_Scholastic_Grade1_List;

			String retrieveScholasticGradeList1Query = "SELECT MAX(sa.marksScaled)AS marksScaled FROM SubjectAssessment as sba, StudentAssessment as sa, Subject as sb "
					+ "WHERE sba.subjectID = sb.id AND sba.id = sa.subjectAssessmentID AND sba.subjectID = " + subjectID
					+ " AND sba.examinationID IN (SELECT id FROM Examination WHERE "
					+ "examType IN ('Subject Enrichment', 'Notebook') AND term IN ('" + termValue
					+ "') ) and sa.registrationID = " + studentID + " and " + "sba.ayClassID IN ('" + ayClassID
					+ "') and sa.activityStatus = 'Active' ";

			preparedStatement = connection.prepareStatement(retrieveScholasticGradeList1Query);

			/*
			 * preparedStatement.setInt(1, subjectID); preparedStatement.setString(2,
			 * "Subject Enrichment"); preparedStatement.setString(3, "Notebook");
			 * preparedStatement.setString(4, termValue); preparedStatement.setInt(5,
			 * studentID); preparedStatement.setString(6, ayClassID);
			 * preparedStatement.setString(7, ActivityStatus.ACTIVE);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				marksScaled = resultSet.getInt("marksScaled");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return marksScaled;
	}

	public boolean verify10thClassTeacher(int userID, int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verify10thClassTeacherQuery = QueryMaker.VERIFY_10Th_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(verify10thClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, "X");

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

	public List<ConfigurationForm> retrieveStudentDetailsForLCList(int studentID, int registrationID, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		LoginDAOInf daoInf = new LoginDAOImpl();

		DateToWords dateToWords = new DateToWords();

		String Subject = "";

		String Subject1 = "";

		String dateInWords = "";

		try {

			connection = getConnection();

			String retrieveStudentDetailsForLCListQuery = QueryMaker.RETREIVE_Student_Details_LIST_For_LC;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsForLCListQuery);

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, studentID);
			preparedStatement.setInt(3, registrationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setAadhaar(resultSet.getString("aadhaar"));

				form.setStudentName(resultSet.getString("studentName"));

				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					form.setDateOfBirth("");
				} else if (resultSet.getString("dateOfBirth").isEmpty()) {
					form.setDateOfBirth("");
				} else {
					form.setDateOfBirth(dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfBirth"))));
				}

				dateInWords = dateToWords.convertDateToWords(form.getDateOfBirth());

				form.setDateOfBirthInWords(dateInWords);

				form.setGrNumber(resultSet.getString("grNumber"));

				form.setReligion(resultSet.getString("religion"));

				form.setCategory(resultSet.getString("category"));

				form.setPhysicalActivity(resultSet.getString("Activities"));

				form.setMotherName(resultSet.getString("motherName"));

				form.setFatherName(resultSet.getString("fatherName"));

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {
					Subject = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {

						continue;

					} else {

						Subject1 = Subject1 + ',' + Subject;
					}
				}

				if (Subject1.startsWith(",")) {
					Subject1 = Subject1.substring(1);
				}

				form.setSubject(Subject1);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Student Details list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public boolean verifyleavingCertificate(String table, int studentID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyExamTypeQuery = "SELECT id FROM " + table + " WHERE studentID = " + studentID;

			preparedStatement = connection.prepareStatement(verifyExamTypeQuery);

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

	public List<ConfigurationForm> retrieveStudentDetailsFromLeavingCertificate(int studentID, int registrationID,
			int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		DateToWords dateToWords = new DateToWords();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		LoginDAOInf daoInf = new LoginDAOImpl();

		String Subject = "";

		String Subject1 = "";

		String dateInWords = "";

		try {

			connection = getConnection();

			String retrieveStudentDetailsFromLeavingCertificateQuery = QueryMaker.RETREIVE_Student_Details_From_LeavingCertificate;

			preparedStatement = connection.prepareStatement(retrieveStudentDetailsFromLeavingCertificateQuery);

			preparedStatement.setInt(1, ayClassID);
			preparedStatement.setInt(2, studentID);
			preparedStatement.setInt(3, registrationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setAadhaar(resultSet.getString("aadhaar"));

				form.setSerialNo(resultSet.getString("serialNo"));

				form.setStudentName(resultSet.getString("studentName"));

				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {
					form.setDateOfBirth("");
				} else if (resultSet.getString("dateOfBirth").isEmpty()) {
					form.setDateOfBirth("");
				} else {
					form.setDateOfBirth(dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfBirth"))));
				}

				dateInWords = dateToWords.convertDateToWords(form.getDateOfBirth());

				form.setDateOfBirthInWords(dateInWords);

				form.setGrNumber(resultSet.getString("grNumber"));

				form.setReligion(resultSet.getString("religion"));

				form.setCategory(resultSet.getString("category"));

				form.setPhysicalActivity(resultSet.getString("Activities"));

				form.setMotherName(resultSet.getString("motherName"));

				form.setFatherName(resultSet.getString("fatherName"));

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {
					Subject = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {

						continue;

					} else {

						Subject1 = Subject1 + ',' + Subject;
					}
				}

				if (Subject1.startsWith(",")) {
					Subject1 = Subject1.substring(1);
				}

				form.setSubject(Subject1);

				form.setRemarks(resultSet.getString("remarks"));

				form.setReasons(resultSet.getString("reasons"));

				if (resultSet.getString("dateOfCertificate") == null
						|| resultSet.getString("dateOfCertificate") == "") {
					form.setDateOfCertificate("");
				} else if (resultSet.getString("dateOfCertificate").isEmpty()) {
					form.setDateOfCertificate("");
				} else {
					form.setDateOfCertificate(
							dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfCertificate"))));
				}

				if (resultSet.getString("dateOfapplication") == null
						|| resultSet.getString("dateOfapplication") == "") {
					form.setDateOfapplication("");
				} else if (resultSet.getString("dateOfapplication").isEmpty()) {
					form.setDateOfapplication("");
				} else {
					form.setDateOfapplication(
							dateFormat.format(dateFormat1.parse(resultSet.getString("dateOfapplication"))));
				}

				form.setAcademicProgress(resultSet.getString("academicProgress"));

				form.setGeneralConduct(resultSet.getString("generalConduct"));

				form.setAchievement(resultSet.getString("achievement"));

				form.setNccGuide(resultSet.getString("nccGuide"));

				form.setPresentDays(resultSet.getInt("presentDays"));

				form.setWorkingDays(resultSet.getInt("workingDays"));

				form.setConcession(resultSet.getString("concession"));

				form.setDuesMonths(resultSet.getString("duesMonths"));

				form.setFeeConcession(resultSet.getString("feeConcession"));

				form.setWichClassWords(resultSet.getString("wichClassWords"));

				form.setWichClass(resultSet.getString("wichClass"));

				form.setHigherClass(resultSet.getString("higherClass"));

				form.setResultClass(resultSet.getString("resultClass"));

				form.setResult(resultSet.getString("result"));

				form.setLastStudiedClass(resultSet.getString("lastStudiedClass"));

				form.setLastStudiedClassNo(resultSet.getString("lastStudiedClassNo"));

				form.setLastStudiedClassWords(resultSet.getString("lastStudiedClassWords"));

				form.setFirstClass(resultSet.getString("firstClass"));

				if (resultSet.getString("admissionDate") == null || resultSet.getString("admissionDate") == "") {
					form.setAdmissionDate("");
				} else if (resultSet.getString("admissionDate").isEmpty()) {
					form.setAdmissionDate("");
				} else {
					form.setAdmissionDate(dateFormat.format(dateFormat1.parse(resultSet.getString("admissionDate"))));
				}

				form.setLastSchoolClass(resultSet.getString("lastSchoolClass"));

				form.setLastSchoolAttended(resultSet.getString("lastSchoolAttended"));

				form.setSubCaste(resultSet.getString("subCaste"));

				form.setCaste(resultSet.getString("caste"));

				form.setMotherTongue(resultSet.getString("motherTongue"));

				form.setNationality(resultSet.getString("nationality"));

				form.setBirthPlace(resultSet.getString("birthPlace"));

				form.setBookNo(resultSet.getString("bookNo"));

				form.setStudentNo(resultSet.getString("studentNo"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Student Details list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertLeavingCertificate(ConfigurationForm conform) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertLeavingCertificateQuery = QueryMaker.INSERT_LeavingCertificate;

			preparedStatement = connection.prepareStatement(insertLeavingCertificateQuery);

			preparedStatement.setString(1, conform.getRemarks());

			preparedStatement.setString(2, conform.getReasons());

			// Inserting Start Date
			if (conform.getDateOfCertificate() == null || conform.getDateOfCertificate() == ""
					|| conform.getDateOfCertificate().isEmpty()) {

				conform.setStartDate(null);
				preparedStatement.setString(3, conform.getDateOfCertificate());

			} else {
				preparedStatement.setString(3,
						dateToBeFormatted.format(dateFormat.parse(conform.getDateOfCertificate())));
			}

			// Inserting End Date
			if (conform.getDateOfapplication() == null || conform.getDateOfapplication() == ""
					|| conform.getDateOfapplication().isEmpty()) {

				conform.setEndDate(null);
				preparedStatement.setString(4, conform.getDateOfapplication());

			} else {
				preparedStatement.setString(4,
						dateToBeFormatted.format(dateFormat.parse(conform.getDateOfapplication())));
			}

			preparedStatement.setString(5, conform.getAcademicProgress());

			preparedStatement.setString(6, conform.getGeneralConduct());

			preparedStatement.setString(7, conform.getAchievement());

			preparedStatement.setString(8, conform.getNccGuide());

			preparedStatement.setInt(9, conform.getPresentDays());

			preparedStatement.setInt(10, conform.getWorkingDays());

			preparedStatement.setString(11, conform.getConcession());

			preparedStatement.setString(12, conform.getDuesMonths());

			preparedStatement.setString(13, conform.getFeeConcession());

			preparedStatement.setString(14, conform.getWichClassWords());

			preparedStatement.setString(15, conform.getWichClass());

			preparedStatement.setString(16, conform.getHigherClass());

			preparedStatement.setString(17, conform.getResultClass());

			preparedStatement.setString(18, conform.getResult());

			preparedStatement.setString(19, conform.getLastStudiedClass());

			preparedStatement.setString(20, conform.getLastStudiedClassNo());

			preparedStatement.setString(21, conform.getLastStudiedClassWords());

			preparedStatement.setString(22, conform.getFirstClass());

			// Inserting Start Date
			if (conform.getAdmissionDate() == null || conform.getAdmissionDate() == ""
					|| conform.getAdmissionDate().isEmpty()) {

				conform.setStartDate(null);
				preparedStatement.setString(23, conform.getAdmissionDate());

			} else {

				preparedStatement.setString(23, dateToBeFormatted.format(dateFormat.parse(conform.getAdmissionDate())));
			}

			preparedStatement.setString(24, conform.getLastSchoolClass());

			preparedStatement.setString(25, conform.getLastSchoolAttended());

			preparedStatement.setString(26, conform.getSubCaste());

			preparedStatement.setString(27, conform.getCaste());

			preparedStatement.setString(28, conform.getMotherTongue());

			preparedStatement.setString(29, conform.getNationality());

			preparedStatement.setString(30, conform.getBirthPlace());

			preparedStatement.setString(31, conform.getStudentNo());

			preparedStatement.setString(32, conform.getCategory());

			preparedStatement.setString(33, conform.getDateOfBirthInWords());

			preparedStatement.setString(34, conform.getSerialNo());

			preparedStatement.setString(35, conform.getBookNo());

			preparedStatement.setInt(36, conform.getStudentID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Leaving Certificate detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Leaving Certificate into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

	}

	public String updateLeavingCertificate(ConfigurationForm conform) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String updateLeavingCertificateQuery = QueryMaker.UPDATE_LeavingCertificate;

			preparedStatement = connection.prepareStatement(updateLeavingCertificateQuery);

			preparedStatement.setString(1, conform.getRemarks());

			preparedStatement.setString(2, conform.getReasons());

			// Inserting Start Date
			if (conform.getDateOfCertificate() == null || conform.getDateOfCertificate() == ""
					|| conform.getDateOfCertificate().isEmpty()) {

				conform.setDateOfCertificate(null);
				preparedStatement.setString(3, conform.getDateOfCertificate());

			} else {
				preparedStatement.setString(3,
						dateToBeFormatted.format(dateFormat.parse(conform.getDateOfCertificate())));
			}

			// Inserting End Date
			if (conform.getDateOfapplication() == null || conform.getDateOfapplication() == ""
					|| conform.getDateOfapplication().isEmpty()) {

				conform.setDateOfapplication(null);
				preparedStatement.setString(4, conform.getDateOfapplication());

			} else {
				preparedStatement.setString(4,
						dateToBeFormatted.format(dateFormat.parse(conform.getDateOfapplication())));
			}

			preparedStatement.setString(5, conform.getAcademicProgress());

			preparedStatement.setString(6, conform.getGeneralConduct());

			preparedStatement.setString(7, conform.getAchievement());

			preparedStatement.setString(8, conform.getNccGuide());

			preparedStatement.setInt(9, conform.getPresentDays());

			preparedStatement.setInt(10, conform.getWorkingDays());

			preparedStatement.setString(11, conform.getConcession());

			preparedStatement.setString(12, conform.getDuesMonths());

			preparedStatement.setString(13, conform.getFeeConcession());

			preparedStatement.setString(14, conform.getWichClassWords());

			preparedStatement.setString(15, conform.getWichClass());

			preparedStatement.setString(16, conform.getHigherClass());

			preparedStatement.setString(17, conform.getResultClass());

			preparedStatement.setString(18, conform.getResult());

			preparedStatement.setString(19, conform.getLastStudiedClass());

			preparedStatement.setString(20, conform.getLastStudiedClassNo());

			preparedStatement.setString(21, conform.getLastStudiedClassWords());

			preparedStatement.setString(22, conform.getFirstClass());

			// Inserting Start Date
			if (conform.getAdmissionDate() == null || conform.getAdmissionDate() == ""
					|| conform.getAdmissionDate().isEmpty()) {

				conform.setAdmissionDate(null);
				preparedStatement.setString(23, conform.getAdmissionDate());

			} else {

				preparedStatement.setString(23, dateToBeFormatted.format(dateFormat.parse(conform.getAdmissionDate())));
			}

			preparedStatement.setString(24, conform.getLastSchoolClass());

			preparedStatement.setString(25, conform.getLastSchoolAttended());

			preparedStatement.setString(26, conform.getSubCaste());

			preparedStatement.setString(27, conform.getCaste());

			preparedStatement.setString(28, conform.getMotherTongue());

			preparedStatement.setString(29, conform.getNationality());

			preparedStatement.setString(30, conform.getBirthPlace());

			preparedStatement.setString(31, conform.getStudentNo());

			preparedStatement.setString(32, conform.getCategory());

			preparedStatement.setString(33, conform.getDateOfBirthInWords());

			preparedStatement.setString(34, conform.getSerialNo());

			preparedStatement.setString(35, conform.getBookNo());

			preparedStatement.setInt(36, conform.getStudentID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated Leaving Certificate detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Leaving Certificate into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

	}

	public boolean verifysubjectAssessmentIDPresentOrNot(int examinationID, int subjectID, int AYClassID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifysubjectAssessmentIDPresentOrNotQuery = QueryMaker.Verify_SubjectAssessmentID_Present_Or_Not;

			preparedStatement = connection.prepareStatement(verifysubjectAssessmentIDPresentOrNotQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, AYClassID);

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

	public boolean verifySubjectTeacher(int userID, int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifySubjectTeacherQuery = QueryMaker.VERIFY_Subject_TEACHER;

			preparedStatement = connection.prepareStatement(verifySubjectTeacherQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, userID);

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

	public String retrieveStandardNameDivisionName(int standardID, int divisionID) {

		String StandardDivisionName = "";

		try {

			connection = getConnection();

			String retrieveStandardNameDivisionNameQuery = QueryMaker.RETRIEVE_Standard_DivisionName;

			preparedStatement = connection.prepareStatement(retrieveStandardNameDivisionNameQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				StandardDivisionName = resultSet.getString("standard") + "-" + resultSet.getString("division");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return StandardDivisionName;
	}

	public boolean verifySEANBAbsent(int subjectID, int studentID, int ayClassID, String term, int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			if (ayClassID == 0) {
				String verifySEANBAbsentQuery = QueryMaker.VERIFY_SEANB_ABSENT1;

				preparedStatement = connection.prepareStatement(verifySEANBAbsentQuery);

				preparedStatement.setInt(1, studentID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, subjectID);
				preparedStatement.setString(4, "Subject Enrichment");
				preparedStatement.setString(5, "Notebook");
				preparedStatement.setInt(6, academicYearID);
				preparedStatement.setString(7, term);
			} else {
				String verifySEANBAbsentQuery = QueryMaker.VERIFY_SEANB_ABSENT;

				preparedStatement = connection.prepareStatement(verifySEANBAbsentQuery);

				preparedStatement.setInt(1, studentID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, subjectID);
				preparedStatement.setInt(4, ayClassID);
				preparedStatement.setString(5, "Subject Enrichment");
				preparedStatement.setString(6, "Notebook");
				preparedStatement.setInt(7, academicYearID);
				preparedStatement.setString(8, term);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getInt("absentFlag") == 1) {
					check = true;
				} else {
					check = false;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Absent Flag for Scholastic Subject type from database due to:::"
							+ exception.getMessage());

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return check;
	}

	public boolean verifySEANBAbsentCheck(int subjectID, int studentID, String examType, int ayClassID, String term,
			int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			if (ayClassID == 0) {
				String verifySEANBAbsentQuery = QueryMaker.VERIFY_SEANB_ABSENT_CHECK1;

				preparedStatement = connection.prepareStatement(verifySEANBAbsentQuery);

				preparedStatement.setInt(1, studentID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, subjectID);
				preparedStatement.setString(4, examType);
				preparedStatement.setInt(5, academicYearID);
				preparedStatement.setString(6, term);
			} else {
				String verifySEANBAbsentQuery = QueryMaker.VERIFY_SEANB_ABSENT_CHECK;

				preparedStatement = connection.prepareStatement(verifySEANBAbsentQuery);

				preparedStatement.setInt(1, studentID);
				preparedStatement.setString(2, ActivityStatus.ACTIVE);
				preparedStatement.setInt(3, subjectID);
				preparedStatement.setInt(4, ayClassID);
				preparedStatement.setString(5, examType);
				preparedStatement.setInt(6, academicYearID);
				preparedStatement.setString(7, term);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getInt("absentFlag") == 1) {
					check = true;
				} else {
					check = false;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Absent Flag for Scholastic Subject type from database due to:::"
							+ exception.getMessage());

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return check;
	}

	public int retrieveAYCLassIDForAll(int standardID, int academicYearID) {

		int ayCLassID = 0;

		try {

			connection = getConnection();

			String retrieveAYCLassIDQuery = QueryMaker.RETRIEVE_AYClassID1_For_All;

			preparedStatement = connection.prepareStatement(retrieveAYCLassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ayCLassID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ayCLassID;
	}

	public String retriveInaActiveAcdemicYearByAcdemicYearID(int academicYearID) {

		String InaActiveAcdemicYearName = "";

		try {

			connection = getConnection();

			String retriveInaActiveAcdemicYearByAcdemicYearIDQuery = QueryMaker.RETRIEVE_InaActive_AcdemicYear_By_AcdemicYearID;

			preparedStatement = connection.prepareStatement(retriveInaActiveAcdemicYearByAcdemicYearIDQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				InaActiveAcdemicYearName = resultSet.getString("yearName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return InaActiveAcdemicYearName;
	}

	public LinkedHashMap<Integer, String> retrieveExistingStudentHistoryAssessmentList(int aYClassID) {

		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveExistingStudentHistoryAssessmentListQuery = QueryMaker.RETRIEVE_Existing_StudentAssessment_History_List;

			preparedStatement = connection.prepareStatement(retrieveExistingStudentHistoryAssessmentListQuery);

			preparedStatement.setInt(1, aYClassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"),
						resultSet.getString("studentName") + "=" + resultSet.getInt("rollNumber"));
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

	public List<ConfigurationForm> retrievePersonalityDevelopmentAbsentList(int studentID, String term, int ayClassID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		try {

			connection = getConnection();

			String retrievePersonalityDevelopmentGradeListQuery = QueryMaker.RETREIVE_Student_Grade_LIST_For_Absent;

			preparedStatement = connection.prepareStatement(retrievePersonalityDevelopmentGradeListQuery);

			preparedStatement.setString(1, "Personality Development & Life Skills");
			preparedStatement.setString(2, term);
			preparedStatement.setInt(3, studentID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, ayClassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int SubjectID = 0;
				form = new ConfigurationForm();

				form.setSubject(resultSet.getString("subject"));

				SubjectID = resultSet.getInt("subjectID");

				boolean termEdCheck = daoInf2.verifyAbsentFlag(SubjectID, studentID, ayClassID, term);

				if (termEdCheck) {

					form.setGrade("ex");

				} else {

					form.setGrade("-");
				}

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Students Personality Development Absent Grade list from database due to:::"
							+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertStudentStatusDetails(String status, int studentID) {

		try {
			connection = getConnection();

			String insertStudentStatusDetailsQuery = QueryMaker.INSERT_Student_Status;

			preparedStatement = connection.prepareStatement(insertStudentStatusDetailsQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, studentID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted students status detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting students status details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public boolean verifyStudentData(int studentID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyStudentDataQuery = QueryMaker.VERIFY_Student_Data;

			preparedStatement = connection.prepareStatement(verifyStudentDataQuery);

			preparedStatement.setInt(1, studentID);

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

	public String updateStudentStatusDetails(String status, int studentID) {

		try {
			connection = getConnection();

			String updateStudentStatusDetailsQuery = QueryMaker.Update_Student_Status;

			preparedStatement = connection.prepareStatement(updateStudentStatusDetailsQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, studentID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated student status detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating student status into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public double retrieveactivityMarksForCoScholosticSubject(Integer activityID, Integer subjectID, int ayclassID,
			int examinationID, int studentID) {

		double Marks = 0;

		try {

			connection = getConnection();

			String retrieveactivityMarksForCoScholosticSubjectQuery = QueryMaker.RETREIVE_Marks_For_CoScholostic_Activities;

			preparedStatement = connection.prepareStatement(retrieveactivityMarksForCoScholosticSubjectQuery);

			preparedStatement.setInt(1, activityID);
			preparedStatement.setInt(2, studentID);
			preparedStatement.setInt(3, examinationID);
			preparedStatement.setInt(4, subjectID);
			preparedStatement.setInt(5, ayclassID);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Marks = resultSet.getDouble("totalMarks");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving activities Marks list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Marks;
	}

	public HashMap<Integer, String> retrieveActivityNameBySubjectID(Integer subjectNameID, int ayclassID,
			int examinationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveActivityNameBySubjectIDQuery = QueryMaker.RETRIEVE_ActivitiesName;

			preparedStatement = connection.prepareStatement(retrieveActivityNameBySubjectIDQuery);

			preparedStatement.setInt(1, subjectNameID);
			preparedStatement.setInt(2, examinationID);
			preparedStatement.setInt(3, subjectNameID);
			preparedStatement.setInt(4, ayclassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("activity"));
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

	public int retrieveAYClassIDByCLassTeacherID(int academicYearID, int divisionID, int standardID,
			int classTeacherID) {
		int AYID = 0;

		try {
			connection = getConnection();

			String retrieveAYClassIDByCLassTeacherIDQuery = QueryMaker.VERIFY_ClassTeacheID;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDByCLassTeacherIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, classTeacherID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AYID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving ayclassID from AYClass table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return AYID;
	}

	public int retrieveoutOfMarksForSubjectNew(int subjectID, String examType, int ayClassID, int academicYearID,
			String term) {

		int scaleTo = 0;

		try {

			connection = getConnection();

			String retrieveoutOfMarksForSubjectNewQuery = QueryMaker.RETRIEVE_OutOfMarks_List_New;

			preparedStatement = connection.prepareStatement(retrieveoutOfMarksForSubjectNewQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setInt(2, ayClassID);
			preparedStatement.setString(3, examType);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setString(5, term);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	public String retrieveParentsEmailByStudentID(int studentID, String relation) {

		String email = "";

		try {

			connection1 = getConnection();

			String retrieveParentsEmailByStudentIDQuery = QueryMaker.RETRIEVE_Parents_Email_BY_StudentID;

			preparedStatement1 = connection1.prepareStatement(retrieveParentsEmailByStudentIDQuery);

			preparedStatement1.setInt(1, studentID);
			preparedStatement1.setString(2, relation);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				email = resultSet1.getString("emailId");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return email;
	}

	public List<ConfigurationForm> retrieveStudentListForReportCardEmail(int standardID, int divisionID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		try {

			connection = getConnection();

			String retrieveStudentIDBYStandardAndDivisionQuery = QueryMaker.RETRIEVE_StudentID_List;

			preparedStatement = connection.prepareStatement(retrieveStudentIDBYStandardAndDivisionQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRegistrationID(resultSet.getInt("id"));

				form.setResult(resultSet.getString("studentStatus"));

				form.setStudentName(resultSet.getString("studentName"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students details list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String retrieveUserSignatureFile(int userID) {

		String signImage = "";

		try {

			connection1 = getConnection();

			String retrieveUserSignatureFileQuery = QueryMaker.RETREIVE_USER_BY_USER_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveUserSignatureFileQuery);

			preparedStatement1.setInt(1, userID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				signImage = resultSet1.getString("signImage");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return signImage;

	}

	public int retrieveScaleToNew1(Integer subjectName, int aYClassID, int academicYearID, String term) {

		int scaleTo = 0;

		try {

			connection = getConnection();

			String retrieveScaleToNewQuery = QueryMaker.RETRIEVE_Scale_To_List_New1;

			preparedStatement = connection.prepareStatement(retrieveScaleToNewQuery);

			preparedStatement.setInt(1, subjectName);
			preparedStatement.setInt(2, aYClassID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, term);
			preparedStatement.setString(5, "Subject Enrichment");
			preparedStatement.setString(6, "Notebook");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				scaleTo = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return scaleTo;
	}

	@Override
	public List<ConfigurationForm> retrieveAttendanceListforStudent1(int studentID, String term, int standardID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrieveAttendanceListforStudentQuery = QueryMaker.RETRIEVE_AttendanceListforStudent;

			preparedStatement = connection.prepareStatement(retrieveAttendanceListforStudentQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, term);
			preparedStatement.setInt(4, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();
				form.setWorkingMonth(resultSet.getString("workingMonth"));

				form.setPresentDays(resultSet.getInt("daysPresent"));

				form.setWorkingDays(resultSet.getInt("workingDays"));

				list.add(form);
			}
			System.out.println("list:" + list);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Students Attendance List from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	@Override
	public List<ConfigurationForm> retrieveStudentListForLeavingCertificate(int standardID, int divisionID,
			int academicYearID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm form = null;

		try {

			connection = getConnection();

			String retrieveStudentListForLeavingCertificateQuery = QueryMaker.RETREIVE_Leaving_Certificate_Student_LIST;

			preparedStatement = connection.prepareStatement(retrieveStudentListForLeavingCertificateQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setInt(3, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new ConfigurationForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRegistrationID(resultSet.getInt("id"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setAYClassID(resultSet.getInt("aYClassID"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setGrNumber(resultSet.getString("GRNO"));

				form.setResult(resultSet.getString("studentStatus"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving Student list from database due to:::" + exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	@Override
	public boolean checkMarksPresent(int subjectAssessmentID, int registrationID) {

		boolean check = false;

		try {

			connection = getConnection();

			String checkMarksPresentQuery = QueryMaker.VERIFY_Marks_Present;

			preparedStatement = connection.prepareStatement(checkMarksPresentQuery);

			preparedStatement.setInt(1, subjectAssessmentID);
			preparedStatement.setInt(2, registrationID);

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
	public boolean verify9th10thClassTeacher(int userID, int academicYearID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verify9th10thClassTeacherQuery = "SELECT id FROM AYClass WHERE teacherID = ? "
					+ "AND academicYearID = ? AND activityStatus = ? AND standardID IN(SELECT id FROM Standard WHERE standard IN ('IX','X'))";

			preparedStatement = connection.prepareStatement(verify9th10thClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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
	public String retrieveLeavingCertLastSerialNo() {

		String serialNo = "";

		try {

			connection = getConnection();

			String retrieveLeavingCertLastSerialNoQuery = QueryMaker.RETRIEVE_LEAVING_CERT_LAST_SERIAL_NO;

			preparedStatement = connection.prepareStatement(retrieveLeavingCertLastSerialNoQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				serialNo = resultSet.getString("serialNo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return serialNo;
	}

}
