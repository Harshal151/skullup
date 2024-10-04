package com.kovidRMS.daoImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kovidRMS.daoInf.ConfigurationDAOInf;
import com.kovidRMS.daoInf.LoginDAOInf;
import com.kovidRMS.daoInf.StuduntDAOInf;
import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.ReportRow;
import com.kovidRMS.form.StudentForm;
import com.kovidRMS.util.ActivityStatus;
import com.kovidRMS.util.ConfigXMLUtil;
import com.kovidRMS.util.ConfigurationUtil;
import com.kovidRMS.util.DAOConnection;
import com.kovidRMS.util.EncDescUtil;
import com.kovidRMS.util.JDBCHelper;
import com.kovidRMS.util.QueryMaker;

public class StudentDAOImpl extends DAOConnection implements StuduntDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatement1 = null;
	PreparedStatement preparedStatement2 = null;
	PreparedStatement preparedStatement3 = null;
	PreparedStatement preparedStatement4 = null;
	PreparedStatement preparedStatement5 = null;
	PreparedStatement preparedStatement6 = null;
	PreparedStatement preparedStatement7 = null;

	ResultSet resultSet = null;
	ResultSet resultSet1 = null;
	ResultSet resultSet2 = null;
	ResultSet resultSet3 = null;
	ResultSet resultSet4 = null;
	ResultSet resultSet5 = null;
	ResultSet resultSet6 = null;
	ResultSet resultSet7 = null;

	Connection connection1 = null;
	String status = "error";

	static int counter = 1;

	static boolean check = false;

	static int extraMinutes = 0;

	StuduntDAOInf daoInf = null;

	ConfigurationUtil configurationUtil = null;

	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	public String insertStudentDetails(StudentForm studform, int id) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertStudentDetailsQuery = QueryMaker.INSERT_Student_DETAILS;

			preparedStatement = connection.prepareStatement(insertStudentDetailsQuery);

			preparedStatement.setString(1, studform.getFirstName());
			preparedStatement.setString(2, studform.getMiddleName());
			preparedStatement.setString(3, studform.getLastName());

			// Inserting Start Date
			if (studform.getDateOfBirth() == null || studform.getDateOfBirth() == "") {

				studform.setDateOfBirth(null);

				preparedStatement.setString(4, studform.getDateOfBirth());
			} else if (studform.getDateOfBirth().isEmpty()) {

				preparedStatement.setString(4, studform.getDateOfBirth());

			} else {

				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(studform.getDateOfBirth())));
			}

			preparedStatement.setString(5, studform.getGender());
			preparedStatement.setString(6, studform.getAadhaar());
			preparedStatement.setString(7, studform.getBloodgroup());
			preparedStatement.setString(8, studform.getCategory());
			preparedStatement.setString(9, studform.getHasSpectacles());
			preparedStatement.setString(10, studform.getReligion());
			preparedStatement.setString(11, studform.getBirthPlace());
			preparedStatement.setString(12, ActivityStatus.ACTIVE);

			String sibling = studform.getSibling();

			System.out.println("Insert sibling:" + sibling);

			if (sibling == null || sibling == "") {
				System.out.println("Insert sibling1:" + sibling);

			} else if (sibling.isEmpty()) {
				System.out.println("Insert sibling2:" + sibling);
			} else {
				if (sibling.startsWith(",")) {
					sibling = sibling.substring(1);
				}
			}

			preparedStatement.setString(13, sibling);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student details inserted successfully into table Student.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Student detail into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrievestudentID(String aadhaar) {
		int studentID = 0;
		try {
			connection = getConnection();

			String retrievePatientIDQuery = QueryMaker.RETRIEVE_studentID;

			preparedStatement = connection.prepareStatement(retrievePatientIDQuery);

			preparedStatement.setString(1, aadhaar);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				studentID = resultSet.getInt("id");
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
		return studentID;
	}

	public String insertStudentContact(StudentForm studform, int studentID) {
		try {
			connection = getConnection();

			String insertStudentContactQuery = QueryMaker.INSERT_StudentContact;

			preparedStatement = connection.prepareStatement(insertStudentContactQuery);

			preparedStatement.setString(1, studform.getPhone());
			preparedStatement.setString(2, studform.getAddress());
			preparedStatement.setString(3, studform.getCity());
			preparedStatement.setString(4, studform.getState());
			preparedStatement.setString(5, studform.getCountry());
			preparedStatement.setInt(6, studform.getPinCode());
			preparedStatement.setInt(7, studentID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student contact details inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting student contact details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertStudentPersonalInfo(StudentForm studform, int studentID) {
		try {
			connection = getConnection();

			String insertStudentPersonalInfoQuery = QueryMaker.INSERT_StudentPersonalInfo;

			preparedStatement = connection.prepareStatement(insertStudentPersonalInfoQuery);

			preparedStatement.setString(1, studform.getGrNumber());
			preparedStatement.setString(2, studform.getHouse());
			/* preparedStatement.setString(3, studform.getDateUpdated()); */
			preparedStatement.setString(3, studform.getCommutationMode());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, studentID);
			preparedStatement.setInt(6, studform.getCommutationID());

			preparedStatement.execute();

			status = "success";
			System.out.println("Student Personal Information inserted successfully into table StudentDetails.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Student Personal Information into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int verifyAadhaar(String aadhaar) {
		int status = 0;

		try {
			connection = getConnection();

			String verifyAadhaarQuery = QueryMaker.VERIFY_Aadhaar;

			preparedStatement = connection.prepareStatement(verifyAadhaarQuery);

			preparedStatement.setString(1, aadhaar);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving Aadhaar No from Student table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<StudentForm> searchStudent(String searchStudentName, int standardID, int divisionID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm studform = null;

		try {

			connection = getConnection();

			String searchUserQuery = QueryMaker.SEARCH_STUDENT_BY_USER_NAME;

			preparedStatement = connection.prepareStatement(searchUserQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);

			String searchName = searchStudentName;

			if (searchStudentName.contains(" ")) {
				searchName = searchStudentName.replace(" ", "%");
			}

			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, "%" + searchName + "%");
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				studform = new StudentForm();

				String fullName = null;

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else {

					fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName");

				}

				studform.setStudentID(resultSet.getInt("studentID"));
				studform.setFullName(fullName);
				studform.setAadhaar(resultSet.getString("aadhaar"));
				studform.setStandardID(resultSet.getInt("standardID"));
				studform.setDivisionID(resultSet.getInt("divisionID"));
				/*
				 * studform.setActivityStatus(resultSet.getString("activityStatus"));
				 * studform.setStandardID(resultSet.getInt("standardID"));
				 */

				studform.setSearchStudentName(searchStudentName);

				list.add(studform);

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

	public List<StudentForm> retriveEditStudentList(int standardID, int divisionID, String searchName) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm studform = null;

		try {

			connection = getConnection();

			String retriveEditStudentListQuery = QueryMaker.RETREIVE_EDIT_STUDENT_LIST;

			preparedStatement = connection.prepareStatement(retriveEditStudentListQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				studform = new StudentForm();

				studform.setStudentID(resultSet.getInt("studentID"));

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					studform.setFullName(resultSet.getString("lastName") + " " + resultSet.getString("firstName"));
				} else {

					studform.setFullName(resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName"));

				}
				studform.setAadhaar(resultSet.getString("aadhaar"));

				studform.setStandardID(resultSet.getInt("standardID"));

				studform.setDivisionID(resultSet.getInt("divisionID"));

				studform.setSearchStudentName(searchName);

				list.add(studform);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving edit student list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public List<StudentForm> retreiveStudentDetailByStudentID(int studentID, String searchName) {
		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm stutform = new StudentForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("dd/MM/yyyy");

		try {

			connection = getConnection();

			String retriveStudentListByIDQuery = QueryMaker.RETREIVE_Student_LIST_BY_ID;
			String retrieveStudentContactByIDQuery = QueryMaker.RETREIVE_StudentContact_BY_ID;
			String retrieveStudentDetailByIDQuery = QueryMaker.RETREIVE_StudentDetail_LIST_BY_ID;
			String retrieveFathersDetailByIDQuery = QueryMaker.RETREIVE_ParentsDetail_LIST_BY_ID;
			String retrieveMothersDetailByIDQuery = QueryMaker.RETREIVE_ParentsDetail_LIST_BY_ID;
			String retrieveEmergencyContactsByIDQuery = QueryMaker.RETREIVE_EmergencyContacts_BY_ID;
			String retrieveMedicleConditionByIDQuery = QueryMaker.RETREIVE_MedicleCondition_BY_ID;
			String retrieveStudentRegistrationByIDQuery = QueryMaker.RETREIVE_StudentRegistration_LIST_BY_ID;

			// For retrieving patient details base on patientID
			preparedStatement = connection.prepareStatement(retriveStudentListByIDQuery);
			preparedStatement.setInt(1, studentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				stutform.setStudentID(resultSet.getInt("id"));
				stutform.setFirstName(resultSet.getString("firstName"));
				stutform.setLastName(resultSet.getString("lastName"));
				stutform.setMiddleName(resultSet.getString("middleName"));
				// stutform.setActivityStatus(resultSet.getString("ACTIVE"));

				// Inserting Start Date
				if (resultSet.getString("dateOfBirth") == null || resultSet.getString("dateOfBirth") == "") {

					// form.setStartDate(null);
					stutform.setDateOfBirth("");

				} else if (resultSet.getString("dateOfBirth").isEmpty()) {
					stutform.setDateOfBirth("");

				} else {
					stutform.setDateOfBirth(
							dateToBeFormatted.format(dateFormat.parse(resultSet.getString("dateOfBirth"))));
				}

				stutform.setGender(resultSet.getString("gender"));
				stutform.setAadhaar(resultSet.getString("aadhaar"));
				stutform.setBloodgroup(resultSet.getString("bloodgroup"));
				stutform.setCategory(resultSet.getString("category"));
				stutform.setHasSpectacles(resultSet.getString("hasSpectacles"));
				stutform.setReligion(resultSet.getString("religion"));
				stutform.setSibling(resultSet.getString("siblingID"));
				stutform.setBirthPlace(resultSet.getString("birthPlace"));
			}

			// For retrieving StudentContact detials based on Student iD
			preparedStatement1 = connection.prepareStatement(retrieveStudentContactByIDQuery);
			preparedStatement1.setInt(1, studentID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				stutform.setStudentID(resultSet1.getInt("studentID"));
				stutform.setPhone(resultSet1.getString("phone"));
				stutform.setAddress(resultSet1.getString("address"));
				stutform.setCity(resultSet1.getString("city"));
				stutform.setState(resultSet1.getString("state"));
				stutform.setCountry(resultSet1.getString("country"));
				stutform.setPinCode(resultSet1.getInt("pinCode"));
			}

			// For retrieving Student contact details based on studentID
			preparedStatement2 = connection.prepareStatement(retrieveStudentDetailByIDQuery);
			preparedStatement2.setInt(1, studentID);

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				stutform.setStudentID(resultSet2.getInt("studentID"));
				stutform.setGrNumber(resultSet2.getString("grNumber"));
				stutform.setHouse(resultSet2.getString("house"));
				stutform.setDateUpdated(resultSet2.getString("dateUpdated"));
				stutform.setCommutationID(resultSet2.getInt("commutationID"));
				stutform.setCommutationMode(resultSet2.getString("commutationMode"));

			}

			// For retrieving Parents details based on studentID
			preparedStatement4 = connection.prepareStatement(retrieveFathersDetailByIDQuery);
			preparedStatement4.setInt(1, studentID);
			preparedStatement4.setString(2, "Father");

			resultSet4 = preparedStatement4.executeQuery();

			while (resultSet4.next()) {

				stutform.setParentfirstName(resultSet4.getString("firstName"));
				stutform.setParentlastName(resultSet4.getString("lastName"));
				stutform.setParentmiddleName(resultSet4.getString("middleName"));
				stutform.setEmailId(resultSet4.getString("emailId"));
				stutform.setMobile(resultSet4.getString("mobile"));
				stutform.setParentPhone(resultSet4.getString("phone"));
				stutform.setParentAddress(resultSet4.getString("address"));
				stutform.setParentcity(resultSet4.getString("city"));
				stutform.setParentstate(resultSet4.getString("state"));
				stutform.setParentcountry(resultSet4.getString("country"));
				stutform.setParentpinCode(resultSet4.getInt("pinCode"));
				stutform.setOccupation(resultSet4.getString("occupation"));
				stutform.setRelation(resultSet4.getString("relation"));

			}

			// For retrieving Parents details based on studentID
			preparedStatement5 = connection.prepareStatement(retrieveMothersDetailByIDQuery);
			preparedStatement5.setInt(1, studentID);
			preparedStatement5.setString(2, "Mother");

			resultSet5 = preparedStatement5.executeQuery();

			while (resultSet5.next()) {

				stutform.setMotherfirstName(resultSet5.getString("firstName"));
				stutform.setMotherlastName(resultSet5.getString("lastName"));
				stutform.setMothermiddleName(resultSet5.getString("middleName"));
				stutform.setMotheremailId(resultSet5.getString("emailId"));
				stutform.setMothermobile(resultSet5.getString("mobile"));
				stutform.setMotherPhone(resultSet5.getString("phone"));
				stutform.setMotherAddress(resultSet5.getString("address"));
				stutform.setMothercity(resultSet5.getString("city"));
				stutform.setMotherstate(resultSet5.getString("state"));
				stutform.setMothercountry(resultSet5.getString("country"));
				stutform.setMotherpinCode(resultSet5.getInt("pinCode"));
				stutform.setMotheroccupation(resultSet5.getString("occupation"));
				stutform.setMotherrelation(resultSet5.getString("relation"));
			}

			// For retrieving Emergency Contacts details based on studentID
			preparedStatement6 = connection.prepareStatement(retrieveEmergencyContactsByIDQuery);
			preparedStatement6.setInt(1, studentID);

			resultSet6 = preparedStatement6.executeQuery();

			while (resultSet6.next()) {

				stutform.setEmergencyName(resultSet6.getString("name"));
				stutform.setEmergencyPhone(resultSet6.getString("phone"));

			}

			// For retrieving Medicle Condition details based on studentID
			preparedStatement7 = connection.prepareStatement(retrieveMedicleConditionByIDQuery);
			preparedStatement7.setInt(1, studentID);

			resultSet7 = preparedStatement7.executeQuery();

			while (resultSet7.next()) {

				stutform.setMedCondition(resultSet7.getString("medCondition"));

			}

			// For retrieving Student contact details based on patient ID
			preparedStatement3 = connection.prepareStatement(retrieveStudentRegistrationByIDQuery);
			preparedStatement3.setInt(1, studentID);
			preparedStatement3.setString(2, ActivityStatus.ACTIVE);

			resultSet3 = preparedStatement3.executeQuery();

			while (resultSet3.next()) {

				stutform.setStandardID(resultSet3.getInt("standard"));
				stutform.setDivisionID(resultSet3.getInt("division"));
				stutform.setStudentID(resultSet3.getInt("studentID"));
				stutform.setAyclassID(resultSet3.getInt("ayclassID"));
				stutform.setRollNumber(resultSet3.getInt("rollNumber"));
				stutform.setWeight(resultSet3.getDouble("weight"));
				stutform.setHeight(resultSet3.getDouble("height"));

				stutform.setSearchStudentName(searchName);

				List<String> OriginalcreativeActivities = new ArrayList<String>();

				String Subject = resultSet3.getString("creativeActivities");

				if (Subject == null) {

					Subject = "";
				} else if (Subject == "") {

					Subject = "";
				} else if (Subject.isEmpty()) {

					Subject = "";
				} else {
					if (Subject.contains(",")) {

						String subArr[] = Subject.split(",");
						for (int j = 0; j < subArr.length; j++) {

							OriginalcreativeActivities.add(subArr[j].trim());
							// System.out.println("OriginalcreativeActivitiesList :" +
							// OriginalcreativeActivities);
						}
					} else {

						OriginalcreativeActivities.add(Subject);
					}
				}

				stutform.setCreativeActivitiesValues(OriginalcreativeActivities);
				// stutform.setCreativeActivities(resultSet3.getString("creativeActivities"));

				List<String> OriginalphysicalActivities = new ArrayList<String>();

				String Subject1 = resultSet3.getString("physicalActivities");

				if (Subject1 == null) {

					Subject1 = "";
				} else if (Subject1 == "") {

					Subject1 = "";
				} else if (Subject1.isEmpty()) {

					Subject1 = "";
				} else {

					if (Subject1.contains(",")) {

						String subArr1[] = Subject1.split(",");

						for (int k = 0; k < subArr1.length; k++) {

							OriginalphysicalActivities.add(subArr1[k].trim());
							// System.out.println("OriginalphysicalActivitiesList :" +
							// OriginalphysicalActivities);
						}
					} else {

						OriginalphysicalActivities.add(Subject1);
					}
				}
				stutform.setPhysicalActivitiesValues(OriginalphysicalActivities);

				List<String> OriginalCompulsoryActivities = new ArrayList<String>();

				String Subject2 = resultSet3.getString("compulsoryActivities");

				if (Subject2 == null) {

					Subject2 = "";
				} else if (Subject2 == "") {

					Subject2 = "";
				} else if (Subject2.isEmpty()) {

					Subject2 = "";
				} else {

					if (Subject2.contains(",")) {

						String subArr1[] = Subject2.split(",");

						for (int k = 0; k < subArr1.length; k++) {

							OriginalCompulsoryActivities.add(subArr1[k].trim());
							// System.out.println("OriginalphysicalActivitiesList :" +
							// OriginalphysicalActivities);
						}
					} else {

						OriginalCompulsoryActivities.add(Subject2);
					}
				}
				stutform.setCompulsoryActivitiesValues(OriginalCompulsoryActivities);

				// stutform.setPhysicalActivities(resultSet3.getString("physicalActivities"));

				stutform.setAge(resultSet3.getString("age"));

			}

			list.add(stutform);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retriving student list based on student Id from database due to:::"
							+ exception.getMessage());
			status = "error";

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet3);
			JDBCHelper.closeStatement(preparedStatement3);

			JDBCHelper.closeResultSet(resultSet4);
			JDBCHelper.closeStatement(preparedStatement4);

			JDBCHelper.closeResultSet(resultSet5);
			JDBCHelper.closeStatement(preparedStatement5);

			JDBCHelper.closeResultSet(resultSet6);
			JDBCHelper.closeStatement(preparedStatement6);

			JDBCHelper.closeResultSet(resultSet7);
			JDBCHelper.closeStatement(preparedStatement7);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String verifyStudentCredential(StudentForm studform) {
		try {
			connection = getConnection();

			String verifyStudentCredentialQuery = QueryMaker.VERFIY_Student_CREDENTIALS;

			preparedStatement = connection.prepareStatement(verifyStudentCredentialQuery);
			preparedStatement.setInt(1, studform.getStudentID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String aadharNo = resultSet.getString("aadhaar");

				if (aadharNo.equals(studform.getAadhaar())) {
					status = "success";
					return status;
				} else {
					status = "error";
					return status;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying student detail from table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateStudentDetail(StudentForm studform) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String verifyStudentDetailQuery = QueryMaker.UPDATE_Student_DETAILS;

			preparedStatement = connection.prepareStatement(verifyStudentDetailQuery);

			preparedStatement.setString(1, studform.getFirstName());
			preparedStatement.setString(2, studform.getMiddleName());
			preparedStatement.setString(3, studform.getLastName());

			// updating Start Date
			if (studform.getDateOfBirth() == null || studform.getDateOfBirth() == ""
					|| studform.getDateOfBirth().isEmpty()) {

				studform.setDateOfBirth(null);
				preparedStatement.setString(4, studform.getDateOfBirth());
			} else {

				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(studform.getDateOfBirth())));
			}

			preparedStatement.setString(5, studform.getGender());
			preparedStatement.setString(6, studform.getAadhaar());
			preparedStatement.setString(7, studform.getBloodgroup());
			preparedStatement.setString(8, studform.getCategory());
			preparedStatement.setString(9, studform.getHasSpectacles());
			preparedStatement.setString(10, studform.getReligion());

			preparedStatement.setString(11, studform.getBirthPlace());

			String sibling = studform.getSibling();

			if (sibling == null || sibling == "") {
				sibling = "";
			} else if (sibling.isEmpty()) {
				sibling = "";
			} else {

				if (sibling.startsWith(",")) {
					sibling = sibling.substring(1);
				}
			}

			System.out.println("Update sibling:" + sibling);

			preparedStatement.setString(12, sibling);
			preparedStatement.setInt(13, studform.getStudentID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Student details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Student detail into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		System.out.println("status.." + status);

		return status;
	}

	public String updateStudentContacts(StudentForm studform) {
		try {
			connection = getConnection();

			String updateStudentContactQuery = QueryMaker.UPDATE_StudentContact_DETAILS;

			preparedStatement = connection.prepareStatement(updateStudentContactQuery);

			preparedStatement.setString(1, studform.getPhone());
			preparedStatement.setString(2, studform.getAddress());
			preparedStatement.setString(3, studform.getCity());
			preparedStatement.setString(4, studform.getState());
			preparedStatement.setString(5, studform.getCountry());
			preparedStatement.setInt(6, studform.getPinCode());
			preparedStatement.setInt(7, studform.getStudentID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Student contact details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Student contact details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateStudentInfo(StudentForm studform) {
		try {
			connection = getConnection();

			String updateStudentInfoQuery = QueryMaker.UPDATE_StudentInfo_DETAILS;

			preparedStatement = connection.prepareStatement(updateStudentInfoQuery);

			preparedStatement.setString(1, studform.getGrNumber());
			preparedStatement.setString(2, studform.getHouse());
			/* preparedStatement.setString(3, studform.getDateUpdated()); */
			preparedStatement.setInt(3, studform.getCommutationID());
			preparedStatement.setString(4, studform.getCommutationMode());
			preparedStatement.setInt(5, studform.getStudentID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Emergency contact information details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating emergency contact information detail into table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String verifyStudentDetail(String aadhaar) {
		try {
			connection = getConnection();

			String verifyStudentDetailQuery = QueryMaker.VERIFY_Student_DETAIL;

			preparedStatement = connection.prepareStatement(verifyStudentDetailQuery);

			preparedStatement.setString(1, aadhaar);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = "success";
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying Student detail from table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String rejectStudent(StudentForm studform) {

		try {
			connection = getConnection();

			String rejectStudentQuery = QueryMaker.REJECT_Student;
			String rejectStudentContactQuery = QueryMaker.REJECT_Student_Contact;
			String rejectStudentRegistrationQuery = QueryMaker.REJECT_Student_Registration;
			String rejectStudentParentQuery = QueryMaker.REJECT_Student_Parent;

			preparedStatement = connection.prepareStatement(rejectStudentQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, studform.getStudentID());

			preparedStatement.execute();

			System.out.println("Student detail updated (For disable Student) successfully.");
			status = "success";

			// Inactive Activity Status of Students Contact table
			preparedStatement1 = connection.prepareStatement(rejectStudentContactQuery);

			preparedStatement1.setString(1, ActivityStatus.INACTIVE);
			preparedStatement1.setInt(2, studform.getStudentID());

			preparedStatement1.execute();

			System.out.println("Student Contact detail updated (For disable Student) successfully.");
			status = "success";

			// Inactive Activity Status of Students Registration table
			preparedStatement2 = connection.prepareStatement(rejectStudentRegistrationQuery);

			preparedStatement2.setString(1, ActivityStatus.INACTIVE);
			preparedStatement2.setInt(2, studform.getStudentID());

			preparedStatement2.execute();

			System.out.println("Student Registration detail updated (For disable Student) successfully.");
			status = "success";

			// Inactive Activity Status of Students Parent table
			preparedStatement3 = connection.prepareStatement(rejectStudentParentQuery);

			preparedStatement3.setString(1, ActivityStatus.INACTIVE);
			preparedStatement3.setInt(2, studform.getStudentID());

			preparedStatement3.execute();

			System.out.println("Student Parents detail updated (For disable Student) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Student detail for disabling activity status of Student from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeStatement(preparedStatement3);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<StudentForm> searchSubjectList(String searchSubject, int organizationID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;
		try {
			connection = getConnection();

			String searchSubjectListQuery = QueryMaker.SEARCH_Subject_LIST;

			preparedStatement = connection.prepareStatement(searchSubjectListQuery);

			if (searchSubject.contains(" ")) {
				searchSubject = searchSubject.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchSubject + "%");

			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new StudentForm();

				form.setName((resultSet.getString("name")));
				form.setSubjectType((resultSet.getString("subjectType").trim()));
				form.setActivityStatus(resultSet.getString("activityStatus"));
				form.setSearchSubject(searchSubject);
				form.setSubjectID(resultSet.getInt("id"));
				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Subject list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<StudentForm> retrieveExistingSubjectList(int organizationID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;
		try {
			connection = getConnection();

			String retrieveExistingSubjectListQuery = QueryMaker.RETRIEVE_Subjects_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingSubjectListQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new StudentForm();

				form.setSubjectID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setSubjectType((resultSet.getString("subjectType").trim()));
				form.setActivityStatus(resultSet.getString("activityStatus"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Subject list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<StudentForm> retrieveSubjectListByID(int SubjectID, String searchSubject, String subjectType) {
		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = new StudentForm();

		try {

			connection = getConnection();

			String retrieveSubjectListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Subject_LIST_BY_ID;
			String retrieveActivityListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Activity_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByIDQuery);

			preparedStatement.setInt(1, SubjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setSubjectID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setSubjectType(subjectType.trim());
				form.setSortOrder(resultSet.getInt("sortOrder"));
				form.setSearchSubject(searchSubject);

			}

			// For retrieving Medicle Condition details based on studentID
			preparedStatement1 = connection.prepareStatement(retrieveActivityListByIDQuery);
			preparedStatement1.setInt(1, SubjectID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form.setActivity(resultSet1.getString("activity"));
			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Subject list from Subject ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertSubject(StudentForm studform, int organizationID) {

		try {
			connection = getConnection();

			String insertStudentQuery = QueryMaker.INSERT_Subject;

			preparedStatement = connection.prepareStatement(insertStudentQuery);

			preparedStatement.setString(1, studform.getName());
			preparedStatement.setString(2, studform.getSubjectType().trim());
			preparedStatement.setInt(3, studform.getSortOrder());
			preparedStatement.setInt(4, organizationID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Subject into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while inserting Subject into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

	}

	public String updateSubject(StudentForm studform) {
		try {
			connection = getConnection();

			String updateSubjectQuery = QueryMaker.UPDATE_CONFIGURATION_Subject;

			preparedStatement = connection.prepareStatement(updateSubjectQuery);

			preparedStatement.setString(1, studform.getName());
			preparedStatement.setString(2, studform.getSubjectType());
			preparedStatement.setInt(3, studform.getSortOrder());
			preparedStatement.setInt(4, studform.getSubjectID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated Subject into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating Subject into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectSubject(StudentForm studform) {

		try {
			connection = getConnection();

			String rejectSubjectQuery = QueryMaker.REJECT_Subject;

			preparedStatement = connection.prepareStatement(rejectSubjectQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.setInt(2, studform.getSubjectID());

			preparedStatement.execute();

			System.out.println("Subject details updated (For disable Subject) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Subject detail for disabling activity status of Subject from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<String> retrievesubjectList(int organizationID) {

		List<String> list = new ArrayList<String>();

		try {

			connection = getConnection();

			String retrievesubjectPageListQuery = QueryMaker.RETRIEVE_subjectT_PAGE_LIST;

			preparedStatement = connection.prepareStatement(retrievesubjectPageListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getString("name"));
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

	public List<StudentForm> searchCommutationList(String searchCommutation, String searchCriteria,
			int organizationID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;
		try {
			connection = getConnection();

			if (searchCriteria.equals("DriverName")) {

				String searchCommutationByDriverNameQuery = QueryMaker.SEARCH_Commutation_BY_DriverName;

				preparedStatement = connection.prepareStatement(searchCommutationByDriverNameQuery);

				preparedStatement.setString(1, "%" + searchCommutation + "%");
				preparedStatement.setInt(2, organizationID);
				preparedStatement.setString(3, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

			} else if (searchCriteria.equals("MobileNo")) {

				String searchCommutationByMobileNoQuery = QueryMaker.SEARCH_Commutation_BY_MobileNo;

				preparedStatement = connection.prepareStatement(searchCommutationByMobileNoQuery);

				preparedStatement.setString(1, searchCommutation);
				preparedStatement.setInt(2, organizationID);
				preparedStatement.setString(3, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();
			} else {

				String searchCommutationByVehicleNoNoQuery = QueryMaker.SEARCH_Commutation_BY_VehicleNo;

				preparedStatement = connection.prepareStatement(searchCommutationByVehicleNoNoQuery);

				preparedStatement.setString(1, "%" + searchCommutation + "%");

				preparedStatement.setInt(2, organizationID);

				preparedStatement.setString(3, ActivityStatus.ACTIVE);

				resultSet = preparedStatement.executeQuery();

			}

			while (resultSet.next()) {
				form = new StudentForm();

				form.setCommutationID(resultSet.getInt("id"));
				form.setNameOfDriver((resultSet.getString("nameOfDriver")));
				form.setVehicleRegNumber((resultSet.getString("vehicleRegNumber")));
				form.setDriverMobile((resultSet.getString("driverMobile")));
				form.setActivityStatus((resultSet.getString("activityStatus")));
				form.setSearchCommutation(searchCommutation);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Commutation list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<StudentForm> retrieveExistingCommutationList(int organizationID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;
		try {
			connection = getConnection();

			String retrieveExistingCommutationListQuery = QueryMaker.RETRIEVE_Commutation_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingCommutationListQuery);

			preparedStatement.setInt(1, organizationID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new StudentForm();

				form.setCommutationID(resultSet.getInt("id"));
				form.setNameOfDriver((resultSet.getString("nameOfDriver")));
				form.setVehicleRegNumber((resultSet.getString("vehicleRegNumber")));
				form.setDriverMobile((resultSet.getString("driverMobile")));
				form.setActivityStatus((resultSet.getString("activityStatus")));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Commutation list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<StudentForm> retrieveCommutationListByID(int CommutationID, String searchCommutation) {
		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = new StudentForm();

		try {

			connection = getConnection();

			String retrieveCommutationListByIDQuery = QueryMaker.RETRIEVE_Commutation_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveCommutationListByIDQuery);

			preparedStatement.setInt(1, CommutationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setCommutationID(resultSet.getInt("id"));
				form.setNameOfDriver(resultSet.getString("nameOfDriver"));
				form.setVehicleRegNumber(resultSet.getString("vehicleRegNumber"));
				form.setDriverMobile(resultSet.getString("driverMobile"));

				form.setSearchCommutation(searchCommutation);

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Commutation list from Commutation ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertCommutation(StudentForm studform, int organizationID) {
		try {
			connection = getConnection();

			String insertCommutationQuery = QueryMaker.INSERT_Commutation;

			preparedStatement = connection.prepareStatement(insertCommutationQuery);

			preparedStatement.setString(1, studform.getNameOfDriver());
			preparedStatement.setString(2, studform.getVehicleRegNumber());
			preparedStatement.setString(3, studform.getDriverMobile());
			preparedStatement.setInt(4, organizationID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Commutation into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Commutation into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public HashMap<Integer, String> getCommutation(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getCommutationListQuery = QueryMaker.RETRIEVE_Commutations_LIST;

			preparedStatement = connection.prepareStatement(getCommutationListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("nameOfDriver"));

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

	public String updateCommutation(StudentForm studform) {
		try {
			connection = getConnection();

			String updateCommutationQuery = QueryMaker.UPDATE_Commutation;

			preparedStatement = connection.prepareStatement(updateCommutationQuery);

			preparedStatement.setString(1, studform.getNameOfDriver());
			preparedStatement.setString(2, studform.getVehicleRegNumber());
			preparedStatement.setString(3, studform.getDriverMobile());

			preparedStatement.setInt(4, studform.getCommutationID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated organization into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating organization into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectCommutation(StudentForm studform) {

		try {
			connection = getConnection();

			String rejectCommutationQuery = QueryMaker.REJECT_Commutation;

			preparedStatement = connection.prepareStatement(rejectCommutationQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.setInt(2, studform.getCommutationID());

			preparedStatement.execute();

			System.out.println("Commutation details updated (For disable Commutation) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Commutation detail for disabling activity status of Commutation from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String insertRegistrationInfo(StudentForm studform, int studentID, int AYClassID) {
		try {
			connection = getConnection();

			String insertStudentRegistrationInfoQuery = QueryMaker.INSERT_StudentRegistrationInfo;

			preparedStatement = connection.prepareStatement(insertStudentRegistrationInfoQuery);

			preparedStatement.setInt(1, studform.getRollNumber());
			preparedStatement.setDouble(2, studform.getWeight());
			preparedStatement.setDouble(3, studform.getHeight());
			preparedStatement.setString(4, studform.getCreativeActivities());
			preparedStatement.setString(5, studform.getPhysicalActivities());
			preparedStatement.setString(6, ActivityStatus.ACTIVE);
			preparedStatement.setInt(7, studentID);
			preparedStatement.setInt(8, AYClassID);
			preparedStatement.setString(9, studform.getAge());
			preparedStatement.setString(10, studform.getCompulsoryActivities());

			preparedStatement.execute();

			status = "success";
			System.out.println("Student Registration Information inserted successfully into table Registration.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Student Registration Information into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveAYClassID(int standardID, int divisionID) {
		int AYID = 0;

		try {
			connection = getConnection();

			String retrieveAYClassIDQuery = QueryMaker.RETRIEVE_AYClassID_By_Standard;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AYID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving AYClass id from AYClass table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return AYID;
	}

	public String updateRegistrationInfo(StudentForm studform) {
		try {
			connection = getConnection();

			String updateRegistrationInfoQuery = QueryMaker.UPDATE_Registration_DETAILS;

			preparedStatement = connection.prepareStatement(updateRegistrationInfoQuery);

			preparedStatement.setInt(1, studform.getRollNumber());
			preparedStatement.setDouble(2, studform.getWeight());
			preparedStatement.setDouble(3, studform.getHeight());
			preparedStatement.setString(4, studform.getCreativeActivities());
			preparedStatement.setString(5, studform.getPhysicalActivities());
			preparedStatement.setInt(6, studform.getAyclassID());
			preparedStatement.setString(7, studform.getAge());
			preparedStatement.setString(8, studform.getCompulsoryActivities());
			preparedStatement.setInt(9, studform.getStudentID());
			preparedStatement.setString(10, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Registration information details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Registration information detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<StudentForm> retriveStudentList(int standardID, int academicYearID) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveStudentListQuery = QueryMaker.RETREIVE_Student_LIST;

			preparedStatement = connection.prepareStatement(retriveStudentListQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setString(5, ActivityStatus.DRAFT);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				String CreativeActivities = resultSet.getString("creativeActivities").replace(",", "=");

				String PhysicalActivities = resultSet.getString("physicalActivities").replace(",", "=");
				
				String CompulsoryActivities = "";
				
				if(resultSet.getString("compulsoryActivities") == null || resultSet.getString("compulsoryActivities") == "") {
					CompulsoryActivities = "";
				}else if(resultSet.getString("compulsoryActivities").isEmpty()) {
					CompulsoryActivities = "";
				}else {
					CompulsoryActivities = resultSet.getString("compulsoryActivities").replace(",", "=");
				}

				// System.out.println("..."+physicalAct);

				form.setCreativeActivities(CreativeActivities);

				form.setPhysicalActivities(PhysicalActivities);

				form.setCompulsoryActivities(CompulsoryActivities);

				form.setWeight(resultSet.getDouble("weight"));

				form.setHeight(resultSet.getDouble("height"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setDivisionID(resultSet.getInt("divisionID"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setStandardName(resultSet.getString("standardName"));

				form.setDivisionName(resultSet.getString("divisionName"));

				String[] standardString = new String[10];
				standardString[0] = "I";
				standardString[1] = "II";
				standardString[2] = "III";
				standardString[3] = "IV";
				standardString[4] = "V";
				standardString[5] = "VI";
				standardString[6] = "VII";
				standardString[7] = "VIII";
				standardString[8] = "IX";
				standardString[9] = "X";

				int index = findIndex(standardString, form.getStandardName());

				String indexValue = "";

				if (form.getStandardName().equals("IX")) {
					indexValue = standardString[standardString.length - 1];
				} else {
					indexValue = standardString[index + 1];
				}

				int nextStandardID = retrieveStandardIDByStandardName(indexValue);

				form.setNextStandardID(nextStandardID);

				form.setNextStandard(indexValue);

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

	public int findIndex(String[] standardString, String standard) {
		int index = Arrays.binarySearch(standardString, standard);
		return (index < 0) ? -1 : index;
	}

	public int retrieveAYCLassID(int standardID, int divisionID, int ayID) {
		int AYID = 0;

		try {
			connection = getConnection();

			String retrieveAYClassIDQuery = QueryMaker.RETRIEVE_AYClassID1;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, ayID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AYID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving AYClass id from AYClass table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return AYID;
	}

	public String disableOldStudentDetails(int studentID, int oldAyID) {
		// TODO Auto-generated method stub
		try {
			connection = getConnection();

			String disableOldStudentDetailsQuery = QueryMaker.disable_Old_Student_DETAILS;

			preparedStatement = connection.prepareStatement(disableOldStudentDetailsQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.setInt(2, oldAyID);

			preparedStatement.setInt(3, studentID);

			preparedStatement.execute();

			System.out.println("Disabled Old Student details successfully..");
			status = "success";

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while disabling Old Student detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String insertNewStudentDetails(int studentID, String physicalActivities, String creativeActivities,
			String compulsoryActivities, double weight, double height, int ayID) {
		try {
			connection = getConnection();

			String insertNewStudentDetailsQuery = QueryMaker.INSERT_New_Student_Details;

			preparedStatement = connection.prepareStatement(insertNewStudentDetailsQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, physicalActivities);
			preparedStatement.setString(3, creativeActivities);
			preparedStatement.setDouble(4, weight);
			preparedStatement.setDouble(5, height);
			preparedStatement.setString(6, ActivityStatus.DRAFT);
			preparedStatement.setInt(7, ayID);

			preparedStatement.execute();

			status = "success";
			System.out.println("New Student Details inserted successfully into table Registration.");

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

	public int retrieveAyIDByStandardID(int standardID, int divisionID, int academicYearID) {
		int AyID = 0;
		try {
			connection = getConnection();

			String retrieveAyIDByStandardIDQuery = QueryMaker.RETRIEVE_AyIDBy_StandardID;

			preparedStatement = connection.prepareStatement(retrieveAyIDByStandardIDQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setInt(2, divisionID);

			preparedStatement.setInt(3, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AyID = resultSet.getInt("id");
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
		return AyID;

	}

	public int retrivecommutationID(StudentForm studform) {

		int cID = 0;
		try {
			connection = getConnection();

			String retrivecommutationIDQuery = QueryMaker.RETRIEVE_Commutation_ID;

			preparedStatement = connection.prepareStatement(retrivecommutationIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			preparedStatement.setString(2, studform.getNameOfDriver());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				cID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing commutation ID from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return cID;
	}

	public String retrieveCommutationMode(int studentID) {

		String commutatioMode = "";

		try {

			connection = getConnection();

			String retrieveCommutationModeQuery = QueryMaker.RETRIEVE_COMMUTATION_MODE_BY_STIDENT_ID;

			preparedStatement = connection.prepareStatement(retrieveCommutationModeQuery);

			preparedStatement.setInt(1, studentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				commutatioMode = resultSet.getString("commutationMode");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return commutatioMode;
	}

	public List<StudentForm> retriveStudentListByStandardAndDivision(int standardID, int divisionID) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveStudentListByStandardAndDivisionQuery = QueryMaker.RETREIVE_Student_LIST1;

			preparedStatement = connection.prepareStatement(retriveStudentListByStandardAndDivisionQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setInt(2, divisionID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("id"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setStudentName(resultSet.getString("studentName"));

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

	public HashMap<String, String> getCreativeActivitiesList(int organizationID) {

		HashMap<String, String> map = new HashMap<String, String>();
		StudentForm form = null;

		try {

			form = new StudentForm();
			connection = getConnection();

			String getCreativeActivitiesListQuery = QueryMaker.RETRIEVE_CreativeActivities_LIST;

			preparedStatement = connection.prepareStatement(getCreativeActivitiesListQuery);

			preparedStatement.setString(1, "Extra-curricular: Creative");

			preparedStatement.setInt(2, organizationID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("name"), resultSet.getString("name"));

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

	public HashMap<String, String> getPhysicalActivitiesList(int organizationID) {

		HashMap<String, String> map = new HashMap<String, String>();
		StudentForm form = null;

		try {

			form = new StudentForm();
			connection = getConnection();

			String getPhysicalActivitiesListQuery = QueryMaker.RETRIEVE_CreativeActivities_LIST;

			preparedStatement = connection.prepareStatement(getPhysicalActivitiesListQuery);

			preparedStatement.setString(1, "Extra-curricular: Physical");

			preparedStatement.setInt(2, organizationID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("name"), resultSet.getString("name"));

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

	public String retriveCreativeActivities(int studentID) {

		StudentForm form = null;

		String CreativeActivity = "";

		try {

			connection = getConnection();

			String retriveCreativeActivitiesQuery = QueryMaker.RETREIVE_CreativeActivities_LIST1;

			preparedStatement = connection.prepareStatement(retriveCreativeActivitiesQuery);

			preparedStatement.setInt(1, studentID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				CreativeActivity = resultSet.getString("creativeActivities");

			}
			System.out.println("creativeActivities : " + CreativeActivity);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return CreativeActivity;
	}

	public String retrivePhysicalActivities(int studentID) {

		StudentForm form = null;

		String PhysicalActivities = "";

		try {

			connection = getConnection();

			String retriveCreativeActivitiesQuery = QueryMaker.RETREIVE_PhysicalActivities_LIST1;

			preparedStatement = connection.prepareStatement(retriveCreativeActivitiesQuery);

			preparedStatement.setInt(1, studentID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				PhysicalActivities = resultSet.getString("physicalActivities");

			}
			System.out.println("PhysicalActivities : " + PhysicalActivities);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return PhysicalActivities;
	}

	public String retrieveSiblingID(int studentID) {

		String SiblingID = "";

		try {

			connection = getConnection();

			String retriveSiblingIDQuery = QueryMaker.RETREIVE_SiblingID;

			preparedStatement = connection.prepareStatement(retriveSiblingIDQuery);

			preparedStatement.setInt(1, studentID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SiblingID = resultSet.getString("siblingID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving SiblingID from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return SiblingID;
	}

	public List<StudentForm> retrivesiblingList(String siblingID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			if (siblingID == null || siblingID == "") {

				return list;
			} else if (siblingID.isEmpty()) {

				return list;
			} else {

				String[] array = siblingID.split(",");

				int studentID = 0;

				for (int i = 0; i < array.length; i++) {
					connection = getConnection();

					String retrivesiblingListQuery = QueryMaker.RETRIEVE_EXISTING_Sibling_LIST;

					preparedStatement = connection.prepareStatement(retrivesiblingListQuery);

					if (array[i].startsWith(",")) {
						array[i] = array[i].substring(1);
					}
					preparedStatement.setInt(1, Integer.parseInt(array[i]));
					preparedStatement.setString(2, ActivityStatus.ACTIVE);

					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {

						form = new StudentForm();

						String fullName = "";

						if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

							fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

						} else if (resultSet.getString("middleName").isEmpty()) {
							fullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
						} else {
							fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
									+ resultSet.getString("middleName");
						}

						form.setName(fullName);
						form.setStandardID(resultSet.getInt("standardID"));
						form.setDivisionID(resultSet.getInt("divisionID"));
						form.setStandardName(resultSet.getString("standard"));
						form.setDivisionName(resultSet.getString("divison"));
						form.setStudentID(resultSet.getInt("id"));

						list.add(form);
					}

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

	public String insertFatherContact(String firstName, String middleName, String lastName, String relation,
			String mobile, String phone, String Address, String city, String state, String country, int pinCode,
			String occupation, String emailId, int studentID) {

		try {
			connection = getConnection();

			String insertFatherContactQuery = QueryMaker.INSERT_Parents_DETAILS;

			preparedStatement = connection.prepareStatement(insertFatherContactQuery);

			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, middleName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, relation);
			preparedStatement.setString(5, mobile);
			preparedStatement.setString(6, phone);
			preparedStatement.setString(7, Address);
			preparedStatement.setString(8, city);
			preparedStatement.setString(9, state);
			preparedStatement.setString(10, country);
			preparedStatement.setInt(11, pinCode);
			preparedStatement.setString(12, occupation);
			preparedStatement.setString(13, emailId);
			preparedStatement.setInt(14, studentID);
			preparedStatement.setString(15, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student's Father details inserted successfully into table Parent.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Student's Father details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertMotherContact(String motherfirstName, String mothermiddleName, String motherlastName,
			String motherrelation, String mothermobile, String motherPhone, String motherAddress, String mothercity,
			String motherstate, String mothercountry, int motherpinCode, String motheroccupation, String motheremailId,
			int studentID) {

		try {
			connection = getConnection();

			String insertMotherContactQuery = QueryMaker.INSERT_Parents_DETAILS;

			preparedStatement = connection.prepareStatement(insertMotherContactQuery);

			preparedStatement.setString(1, motherfirstName);
			preparedStatement.setString(2, mothermiddleName);
			preparedStatement.setString(3, motherlastName);
			preparedStatement.setString(4, motherrelation);
			preparedStatement.setString(5, mothermobile);
			preparedStatement.setString(6, motherPhone);
			preparedStatement.setString(7, motherAddress);
			preparedStatement.setString(8, mothercity);
			preparedStatement.setString(9, motherstate);
			preparedStatement.setString(10, mothercountry);
			preparedStatement.setInt(11, motherpinCode);
			preparedStatement.setString(12, motheroccupation);
			preparedStatement.setString(13, motheremailId);
			preparedStatement.setInt(14, studentID);
			preparedStatement.setString(15, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student's Mother details inserted successfully into table Parent.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Student's Mother details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateFathersInfo(String parentfirstName, String parentmiddleName, String parentlastName,
			String relation, String mobile, String parentPhone, String parentAddress, String parentcity,
			String parentstate, String parentcountry, int parentpinCode, String occupation, String emailId,
			int studentID) {

		try {
			connection = getConnection();

			String updateParentsInfoQuery = QueryMaker.UPDATE_Parents_DETAILS;

			preparedStatement = connection.prepareStatement(updateParentsInfoQuery);

			preparedStatement.setString(1, parentfirstName);
			preparedStatement.setString(2, parentmiddleName);
			preparedStatement.setString(3, parentlastName);
			preparedStatement.setString(4, emailId);
			preparedStatement.setString(5, mobile);
			preparedStatement.setString(6, parentPhone);
			preparedStatement.setString(7, parentAddress);
			preparedStatement.setString(8, parentcity);
			preparedStatement.setString(9, parentstate);
			preparedStatement.setString(10, parentcountry);
			preparedStatement.setInt(11, parentpinCode);
			preparedStatement.setString(12, occupation);
			preparedStatement.setInt(13, studentID);
			preparedStatement.setString(14, "Father");

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Father details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Father details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateMothersInfo(String motherfirstName, String mothermiddleName, String motherlastName,
			String motherrelation, String mothermobile, String motherPhone, String motherAddress, String mothercity,
			String motherstate, String mothercountry, int motherpinCode, String motheroccupation, String motheremailId,
			int studentID) {

		try {
			connection = getConnection();

			String updateParentsInfoQuery = QueryMaker.UPDATE_Parents_DETAILS;

			preparedStatement = connection.prepareStatement(updateParentsInfoQuery);

			preparedStatement.setString(1, motherfirstName);
			preparedStatement.setString(2, mothermiddleName);
			preparedStatement.setString(3, motherlastName);
			preparedStatement.setString(4, motheremailId);
			preparedStatement.setString(5, mothermobile);
			preparedStatement.setString(6, motherPhone);
			preparedStatement.setString(7, motherAddress);
			preparedStatement.setString(8, mothercity);
			preparedStatement.setString(9, motherstate);
			preparedStatement.setString(10, mothercountry);
			preparedStatement.setInt(11, motherpinCode);
			preparedStatement.setString(12, motheroccupation);
			preparedStatement.setInt(13, studentID);
			preparedStatement.setString(14, "Mother");

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Mother details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Mother details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertEmergencyContacts(StudentForm studform, int studentID) {

		try {
			connection = getConnection();

			String insertEmergencyContactsQuery = QueryMaker.INSERT_EmergencyContacts_DETAILS;

			preparedStatement = connection.prepareStatement(insertEmergencyContactsQuery);

			preparedStatement.setString(1, studform.getEmergencyName());

			preparedStatement.setString(2, studform.getEmergencyPhone());

			preparedStatement.setInt(3, studentID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student's Emergency Contacts details inserted successfully into table Parent.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Student's Emergency Contacts details into table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateEmergencyContact(StudentForm studform) {
		try {
			connection = getConnection();

			String updateEmergencyContactQuery = QueryMaker.UPDATE_EmergencyContact_DETAILS;

			preparedStatement = connection.prepareStatement(updateEmergencyContactQuery);

			preparedStatement.setString(1, studform.getEmergencyName());
			preparedStatement.setString(2, studform.getEmergencyPhone());
			preparedStatement.setInt(3, studform.getStudentID());

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Emergency contact information details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating emergency contact information detail into table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertCondition(String condition, int studentID) {

		try {
			connection = getConnection();

			String insertConditionQuery = QueryMaker.INSERT_Condition_DETAILS;

			preparedStatement = connection.prepareStatement(insertConditionQuery);

			preparedStatement.setString(1, condition);

			preparedStatement.setInt(2, studentID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student's Emergency Contacts details inserted successfully into table Parent.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Student's Emergency Contacts details into table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<StudentForm> retriveConditionList(int studentID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveConditionListQuery = QueryMaker.RETRIEVE_Condition_LIST;

			preparedStatement = connection.prepareStatement(retriveConditionListQuery);

			preparedStatement.setInt(1, studentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setMedConditionID(resultSet.getInt("id"));
				form.setMedCondition(resultSet.getString("medCondition"));

				list.add(form);
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

	public JSONObject deleteMedConditionRow(int medConditionID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteMedConditionRowQuery = QueryMaker.DELETE_MedConditionRow;

			preparedStatement = connection.prepareStatement(deleteMedConditionRowQuery);

			preparedStatement.setInt(1, medConditionID);

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

	public String updateCondition(String editmedCondition, int medConditionID) {

		try {
			connection = getConnection();

			String updateConditionQuery = QueryMaker.UPDATE_Condition;

			preparedStatement = connection.prepareStatement(updateConditionQuery);

			preparedStatement.setString(1, editmedCondition);

			preparedStatement.setInt(2, medConditionID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated Medical Condition into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Medical Condition into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<StudentForm> retriveEditStudentList1(int userID, String searchName) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveEditStudentList1Query = QueryMaker.RETREIVE_EDIT_Student_LIST1;

			preparedStatement = connection.prepareStatement(retriveEditStudentList1Query);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					form.setFullName(resultSet.getString("lastName") + " " + resultSet.getString("firstName"));

				} else {

					form.setFullName(resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName"));

				}

				form.setAadhaar(resultSet.getString("aadhaar"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setDivisionID(resultSet.getInt("divisionID"));

				form.setSearchStudentName(searchName);

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

	public List<StudentForm> searchStudent1(String searchStudentName, int userID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			connection = getConnection();

			String searchStudent1Query = QueryMaker.SEARCH_STUDENT1_BY_USER_NAME;

			preparedStatement = connection.prepareStatement(searchStudent1Query);

			if (searchStudentName.contains(" ")) {
				searchStudentName = searchStudentName.replace(" ", "%");
			}

			preparedStatement.setInt(1, userID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.setString(3, "%" + searchStudentName + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					form.setFullName(resultSet.getString("lastName") + " " + resultSet.getString("firstName"));

				} else {

					form.setFullName(resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName"));
				}

				form.setAadhaar(resultSet.getString("aadhaar"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setDivisionID(resultSet.getInt("divisionID"));

				form.setSearchStudentName(searchStudentName);

				System.out.println(searchStudentName);
				list.add(form);
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

	public List<StudentForm> retriveBulkStudentList(int standardID, int academicYearID, int divisionID) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveBulkStudentListQuery = QueryMaker.RETREIVE_Bulk_Student_LIST;

			preparedStatement = connection.prepareStatement(retriveBulkStudentListQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setGrNumber(resultSet.getString("grNumber"));

				form.setCreativeActivities(resultSet.getString("creativeActivities"));

				form.setPhysicalActivities(resultSet.getString("physicalActivities"));

				form.setHeight(resultSet.getDouble("height"));

				form.setWeight(resultSet.getDouble("weight"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Bulk Students list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String updateBulkStudentRegistration(int rollnumber, String creativeActivities, String physicalActivities,
			String compulsoryActivities, double height, double weight, int studentID, String activityStatus) {
		try {
			connection = getConnection();

			String updateBulkStudentRegistrationQuery = QueryMaker.UPDATE_BulkStudent_Registration_DETAILS;

			preparedStatement = connection.prepareStatement(updateBulkStudentRegistrationQuery);

			preparedStatement.setInt(1, rollnumber);
			preparedStatement.setDouble(2, weight);
			preparedStatement.setDouble(3, height);
			preparedStatement.setString(4, creativeActivities);
			preparedStatement.setString(5, physicalActivities);
			preparedStatement.setString(6, compulsoryActivities);
			preparedStatement.setInt(7, studentID);
			preparedStatement.setString(8, activityStatus);

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Registration information details of Bulk Student udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Registration information details of Bulk Student into table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateBulkStudentDetails(String grNumber, int studentID) {

		try {
			connection = getConnection();

			String updateBulkStudentDetailsQuery = QueryMaker.UPDATE_Bulk_StudentInfo_DETAILS;

			preparedStatement = connection.prepareStatement(updateBulkStudentDetailsQuery);

			preparedStatement.setString(1, grNumber);

			preparedStatement.setInt(2, studentID);

			preparedStatement.executeUpdate();
			status = "success";

			System.out.println("Bulk Students Details udpated successfully..");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating Students Bulk information detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<StudentForm> retriveBulkStudentList1(int standardID, int divisionID, int academicYearID,
			String activityStatus) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveBulkStudentList1Query = QueryMaker.RETREIVE_Bulk_Student_LIST1;

			preparedStatement = connection.prepareStatement(retriveBulkStudentList1Query);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, activityStatus);
			preparedStatement.setString(5, activityStatus);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setGrNumber(resultSet.getString("grNumber"));

				form.setCreativeActivities(resultSet.getString("creativeActivities"));

				String PhysicalActivities = resultSet.getString("physicalActivities").replace(",", "=");

				form.setPhysicalActivities(PhysicalActivities);
				if (resultSet.getString("compulsoryActivities") == null
						|| resultSet.getString("compulsoryActivities") == "") {

					form.setCompulsoryActivities(resultSet.getString("compulsoryActivities"));
				} else if (resultSet.getString("compulsoryActivities").isEmpty()) {

					form.setCompulsoryActivities(resultSet.getString("compulsoryActivities"));
				} else {
					String CompulsoryActivities = resultSet.getString("compulsoryActivities").replace(",", "=");

					form.setCompulsoryActivities(CompulsoryActivities);
				}

				form.setHeight(resultSet.getDouble("height"));

				form.setWeight(resultSet.getDouble("weight"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Bulk Students list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public HashMap<Integer, String> retrieveScholasticsubjectList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveScholasticsubjectListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrieveScholasticsubjectListQuery);

			preparedStatement.setString(1, "Scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			;

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

	public HashMap<Integer, String> retrieveCoscholasticsubjectList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveCoscholasticsubjectListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrieveCoscholasticsubjectListQuery);

			preparedStatement.setString(1, "Co-scholastic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

	public HashMap<Integer, String> retrieveAcademicsubjectList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveAcademicsubjectListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrieveAcademicsubjectListQuery);

			preparedStatement.setString(1, "Extra-curricular: Academic");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

	public HashMap<Integer, String> retrievePersonalitysubjectList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrievePersonalitysubjectListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrievePersonalitysubjectListQuery);

			preparedStatement.setString(1, "Personality Development & Life Skills");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

	public HashMap<Integer, String> retrievePhysicalsubjectTypeList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrievePhysicalsubjectTypeListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrievePhysicalsubjectTypeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Physical");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

	public HashMap<Integer, String> retrieveCreativesubjectTypeList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveCreativesubjectTypeListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrieveCreativesubjectTypeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Creative");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

	public List<StudentForm> retrieveClassStudentListForAttendance(int standardID, int divisionID, String term,
			String workingMonth, int academicYearID) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retrieveClassStudentListForAttendanceQuery = QueryMaker.RETREIVE_Student_Attendance_LIST;

			preparedStatement = connection.prepareStatement(retrieveClassStudentListForAttendanceQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, workingMonth);
			preparedStatement.setString(5, term);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);
			preparedStatement.setInt(7, standardID);
			preparedStatement.setString(8, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setDivisionID(resultSet.getInt("divisionID"));

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

	public JSONObject retrieveworkingMonthForAttendance(String term, int academicYearID, int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveworkingMonthForAttendanceQuery = QueryMaker.RETRIEVE_Working_Month_For_Attendance;

			preparedStatement = connection.prepareStatement(retrieveworkingMonthForAttendanceQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				if (resultSet.getString("workingMonth") == null || resultSet.getString("workingMonth") == "") {
					System.out.println("Null found.");
				} else {

					if (resultSet.getString("workingMonth").isEmpty()) {
						System.out.println("Null found.");
					} else {

						object = new JSONObject();

						object.put("workingMonthID", resultSet.getInt("id"));
						object.put("workingMonth", resultSet.getString("workingMonth"));
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

	public LinkedHashMap<String, String> retrieveMonthListByTerm(String term, int academicYearID, int standardID) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		try {

			connection = getConnection();

			String retrieveDivisionListForStandardQuery = QueryMaker.RETRIEVE_Working_Month_For_Attendance;

			preparedStatement = connection.prepareStatement(retrieveDivisionListForStandardQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getString("workingMonth"), resultSet.getString("workingMonth"));
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

	public int retrieveattendanceID(String term, String workingMonth, int academicYearID, int standardID) {

		int attendanceID = 0;

		try {

			connection = getConnection();

			String retrieveattendanceIDQuery = QueryMaker.RETRIEVE_Attendance_ID;

			preparedStatement = connection.prepareStatement(retrieveattendanceIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);
			preparedStatement.setString(3, workingMonth);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				attendanceID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return attendanceID;
	}

	public String insertStudentAttendanceDetails(int registrationID, int daysPresent, int attendanceID) {

		try {

			connection = getConnection();

			String insertStudentAttendanceDetailsQuery = QueryMaker.INSERT_STUDENT_Attendance_DETAILS;

			preparedStatement = connection.prepareStatement(insertStudentAttendanceDetailsQuery);

			preparedStatement.setInt(1, attendanceID);
			preparedStatement.setInt(2, daysPresent);
			preparedStatement.setInt(3, registrationID);

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

	public List<StudentForm> retrieveExistingClassStudentListForAttendance(String term, String workingMonth,
			int academicYearID, int standardID, int divisionID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			connection = getConnection();

			String retrieveExistingClassStudentListForAttendanceQuery = QueryMaker.RETRIEVE_EXISTING_STUDENT_Attendance_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingClassStudentListForAttendanceQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, workingMonth);
			preparedStatement.setString(3, term);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, standardID);
			preparedStatement.setInt(6, standardID);
			preparedStatement.setInt(7, divisionID);
			preparedStatement.setInt(8, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("studentID"));

				String studetName = "";

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else if (resultSet.getString("middleName").isEmpty()) {

					studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				} else {

					studetName = resultSet.getString("lastName") + " " + resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName");
				}

				form.setStudentName(studetName);
				form.setRollNumber(resultSet.getInt("rollNumber"));
				form.setDaysPresent(resultSet.getInt("daysPresent"));
				form.setStudentAttendanceID(resultSet.getInt("id"));

				list.add(form);
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

	public String updateStudentAttandanceDetails(int StudAttndsID, int daysPresent) {

		try {

			connection = getConnection();

			String updateStudentAttandanceDetailsQuery = QueryMaker.UPDATE_STUDENT_Attandance_DETAILS;

			preparedStatement = connection.prepareStatement(updateStudentAttandanceDetailsQuery);

			preparedStatement.setInt(1, daysPresent);
			preparedStatement.setInt(2, StudAttndsID);

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

	public List<String> retrievestudentsBasedCustomReportList(int standardID, int divisionID, int academicYearID,
			String searchField, String containsName, String checkBoxList, String ageValue, String searchFieldNew) {

		List<String> list = new ArrayList<String>();
		StudentForm form = null;

		try {

			form = new StudentForm();

			connection = getConnection();

			if (containsName.contains("MedicalHistory")) {

				if (checkBoxList == null) {

					String retrievestudentsBasedCustomReportListForMedicalQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName,"
							+ " r.rollNumber FROM Student AS s, MedicalHistory as m, Registration AS r WHERE (s.id = m.studentID AND s.id = r.studentID AND s.activityStatus = 'Active' AND "
							+ "r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
							+ " AND divisionID = " + divisionID + " AND academicYearID = " + academicYearID
							+ " )) AND medCondition LIKE '%" + searchField
							+ "%' AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForMedicalQuery);

				} else {

					String retrievestudentsBasedCustomReportListForMedicalQuery = "SELECT " + checkBoxList
							+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName,"
							+ " r.rollNumber FROM Student AS s, MedicalHistory as m, Registration AS r WHERE (s.id = m.studentID AND s.id = r.studentID AND s.activityStatus = 'Active' AND "
							+ "r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
							+ " AND divisionID = " + divisionID + " AND academicYearID = " + academicYearID
							+ " )) AND medCondition LIKE '%" + searchField
							+ "%' AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForMedicalQuery);
					// System.out.println("retrievestudentsBasedCustomReportListForParentQuery:"+retrievestudentsBasedCustomReportListForMedicalQuery);
				}

			} else if (containsName.contains("Commutation")) {

				if (checkBoxList == null) {

					String retrievestudentsBasedCustomReportListForCommutationQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, StudentDetails AS sd, Commutation AS c, Registration AS r WHERE (s.id = sd.studentID AND sd.commutationID = c.id AND s.id = r.studentID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = " + academicYearID
							+ " )) AND (c.nameOfDriver LIKE '%" + searchField + "%' " + "OR c.vehicleRegNumber LIKE '%"
							+ searchField + "%' OR c.driverMobile LIKE '%" + searchField
							+ "%') AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForCommutationQuery);

				} else {

					String retrievestudentsBasedCustomReportListForCommutationQuery = "SELECT " + checkBoxList
							+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, StudentDetails AS sd, Commutation AS c, Registration AS r WHERE (s.id = sd.studentID AND sd.commutationID = c.id AND s.id = r.studentID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = " + academicYearID
							+ " )) AND (c.nameOfDriver LIKE '%" + searchField + "%' " + "OR c.vehicleRegNumber LIKE '%"
							+ searchField + "%' OR c.driverMobile LIKE '%" + searchField
							+ "%') AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForCommutationQuery);
					// System.out.println("Query
					// :"+retrievestudentsBasedCustomReportListForCommutationQuery);
				}

			} else {

				if (checkBoxList == null) {

					if (containsName.equals("weight")) {

						if (ageValue.equals("between")) {

							String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.weight " + ageValue + searchField + " AND "
									+ searchFieldNew + ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						} else {

							String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.weight " + ageValue + searchField
									+ ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						}
					} else if (containsName.equals("height")) {

						if (ageValue.equals("between")) {

							String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.height " + ageValue + searchField + " AND "
									+ searchFieldNew + ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						} else {

							String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.height " + ageValue + searchField
									+ ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						}
					} else if (containsName.equals("age")) {

						if (ageValue.equals("between")) {

							String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.age " + ageValue + searchField + " AND "
									+ searchFieldNew + ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						} else {

							String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.age " + ageValue + searchField
									+ ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						}

					} else {

						String retrievestudentsBasedCustomReportListQuery = "SELECT s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
								+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
								+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
								+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
								+ academicYearID + " )) AND "
								+ "((CONCAT(s.lastName,' ',s.firstName,' ',s.middleName) LIKE '%" + searchField
								+ "%') OR (s.siblingID IN(SELECT id FROM Student WHERE CONCAT(lastName,' ',firstName,' ',middleName) LIKE '%"
								+ searchField + "%' )) " + "OR s.gender LIKE '%" + searchField
								+ "%' OR s.hasSpectacles LIKE '%" + searchField + "%' OR s.aadhaar LIKE '%"
								+ searchField + "%' OR  s.bloodgroup LIKE '%" + searchField + "%' OR s.category LIKE '%"
								+ searchField + "%' " + "OR s.religion LIKE '%" + searchField
								+ "%' OR sd.grNumber LIKE '%" + searchField + "%' OR sd.house LIKE '%" + searchField
								+ "%' OR sd.commutationMode LIKE '%" + searchField + "%' OR sc.address LIKE '%"
								+ searchField + "%' OR r.creativeActivities LIKE '%" + searchField + "%' "
								+ "OR r.physicalActivities LIKE '%" + searchField + "%' OR e.name LIKE '%" + searchField
								+ "%' OR e.phone LIKE '%" + searchField
								+ "%') AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

						preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);
						System.out.println("Queryt:" + retrievestudentsBasedCustomReportListQuery);
					}

				} else {

					if (containsName.equals("weight")) {

						if (ageValue.equals("between")) {

							String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
									+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.weight " + ageValue + searchField + " AND "
									+ searchFieldNew + ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						} else {

							String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
									+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.weight " + ageValue + searchField
									+ ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						}

					} else if (containsName.equals("height")) {

						if (ageValue.equals("between")) {

							String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
									+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.height " + ageValue + searchField + " AND "
									+ searchFieldNew + ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						} else {

							String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
									+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.height " + ageValue + searchField
									+ ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						}

					} else if (containsName.equals("age")) {

						if (ageValue.equals("between")) {

							String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
									+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.age " + ageValue + searchField + " AND "
									+ searchFieldNew + ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);

						} else {

							String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
									+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
									+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
									+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
									+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
									+ academicYearID + " )) AND " + "(r.age " + ageValue + searchField
									+ ") AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

							preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);
							System.out.println("query: " + retrievestudentsBasedCustomReportListQuery);
						}

					} else {

						String retrievestudentsBasedCustomReportListQuery = "SELECT " + checkBoxList
								+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber FROM Student AS s, "
								+ "StudentDetails AS sd, StudentContact AS sc, EmergencyContact AS e, Registration AS r WHERE (s.id = sd.studentID AND s.id = sc.studentID AND s.id = e.studentID AND s.id = r.studentID "
								+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
								+ standardID + " AND divisionID = " + divisionID + " AND academicYearID = "
								+ academicYearID + " )) AND "
								+ "((CONCAT(s.lastName,' ',s.firstName,' ',s.middleName) LIKE '%" + searchField
								+ "%') OR (s.siblingID IN(SELECT id FROM Student WHERE CONCAT(lastName,' ',firstName,' ',middleName) LIKE '%"
								+ searchField + "%' )) " + "OR s.gender LIKE '%" + searchField
								+ "%' OR s.hasSpectacles LIKE '%" + searchField + "%' OR s.aadhaar LIKE '%"
								+ searchField + "%' OR  s.bloodgroup LIKE '%" + searchField + "%' OR s.category LIKE '%"
								+ searchField + "%' " + "OR s.religion LIKE '%" + searchField
								+ "%' OR sd.grNumber LIKE '%" + searchField + "%' OR sd.house LIKE '%" + searchField
								+ "%' OR sd.commutationMode LIKE '%" + searchField + "%' OR sc.address LIKE '%"
								+ searchField + "%' OR r.creativeActivities LIKE '%" + searchField + "%' "
								+ "OR r.physicalActivities LIKE '%" + searchField + "%' OR e.name LIKE '%" + searchField
								+ "%' OR e.phone LIKE '%" + searchField
								+ "%') AND r.activityStatus = 'Active' ORDER BY r.rollNumber ASC";

						preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListQuery);
						System.out.println("retrievestudentsBasedCustomReportListQuery: "
								+ retrievestudentsBasedCustomReportListQuery);
					}
				}
			}

			resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			while (resultSet.next()) {

				int counter = 1;

				form = new StudentForm();

				String StudentData, StudentDetails = "";

				if (checkBoxList == null) {

					StudentData = resultSet.getInt("rollNumber") + " $ " + resultSet.getString("studentName");

					list.add(StudentData);

				} else {

					String[] List = checkBoxList.split(" AS ");

					for (int i = 0; i < List.length; i++) {

						// System.out.println("column no.." + metaData.getColumnCount());

						String columnType = metaData.getColumnTypeName(counter);

						// System.out.println("column type..." + columnType);

						if (i == 0) {

							continue;
						} else {
							if (List[i].contains(",")) {
								String[] newList = List[i].split(",");

								// System.out.println("column type of..."+newList[0].trim()+" ...
								// "+metaData.getColumnTypeName(counter));

								if (columnType.equals("DATE")) {
									if (resultSet.getString(newList[0].trim()) == null
											|| resultSet.getString(newList[0].trim()) == "") {
										StudentDetails = StudentDetails + " $ "
												+ resultSet.getString(newList[0].trim());
									} else if (resultSet.getString(newList[0].trim()).isEmpty()) {
										StudentDetails = StudentDetails + " $ "
												+ resultSet.getString(newList[0].trim());
									} else {
										StudentDetails = StudentDetails + " $ " + dateFormat
												.format(dateFormat1.parse(resultSet.getString(newList[0].trim())));
									}

								} else {
									StudentDetails = StudentDetails + " $ " + resultSet.getString(newList[0].trim());
								}

							} else {

								// System.out.println("column type of..."+List[i].trim()+" ...
								// "+metaData.getColumnTypeName(counter));

								if (columnType.equals("DATE")) {
									if (resultSet.getString(List[i].trim()) == null
											|| resultSet.getString(List[i].trim()) == "") {
										StudentDetails = StudentDetails + " $ " + resultSet.getString(List[i].trim());
									} else if (resultSet.getString(List[i].trim()).isEmpty()) {
										StudentDetails = StudentDetails + " $ " + resultSet.getString(List[i].trim());
									} else {
										StudentDetails = StudentDetails + " $ " + dateFormat
												.format(dateFormat1.parse(resultSet.getString(List[i].trim())));
									}
								} else {
									StudentDetails = StudentDetails + " $ " + resultSet.getString(List[i].trim());
								}

							}
						}

						counter++;
					}

					/*
					 * for (int j = 0; j < List.length; j++) {
					 * 
					 * String[] newList = List[1].split(",");
					 * 
					 * StudentDetails =StudentDetails + "$" +
					 * resultSet.getString(newList[0].trim());
					 * 
					 * }
					 */

					/*
					 * form.setRollNumber(resultSet.getInt("rollNumber"));
					 * 
					 * form.setStudentName(resultSet.getString("studentName"));
					 */

					StudentData = resultSet.getInt("rollNumber") + " $ " + resultSet.getString("studentName");

					// System.out.println("StudentDetails: "+StudentDetails);
					list.add(StudentData + StudentDetails);
				}
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

	public String retrieveworkingDays(String term, int academicYearID, int standardID, String workingMonth) {

		String workingDays = "";

		try {

			connection = getConnection();

			String retrieveworkingDaysQuery = QueryMaker.RETRIEVE_workingDays;

			preparedStatement = connection.prepareStatement(retrieveworkingDaysQuery);

			preparedStatement.setString(1, term);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setInt(3, standardID);
			preparedStatement.setString(4, workingMonth);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				workingDays = "" + resultSet.getInt("workingDays");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return workingDays;
	}

	public List<StudentForm> retriveActivityList(int subjectID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveConditionListQuery = QueryMaker.RETRIEVE_Activity_LIST;

			preparedStatement = connection.prepareStatement(retriveConditionListQuery);

			preparedStatement.setInt(1, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setActivityID(resultSet.getInt("id"));
				form.setActivity(resultSet.getString("activity"));

				list.add(form);
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

	public JSONObject deleteActivityRow(int activityID) {
		System.out.println("int activityID" + activityID);

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteActivityRowQuery = QueryMaker.DELETE_ActivityRow;

			preparedStatement = connection.prepareStatement(deleteActivityRowQuery);

			preparedStatement.setInt(1, activityID);

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

	public String insertActivity(String activity, int subjectID) {

		try {
			connection = getConnection();

			String insertActivityQuery = QueryMaker.INSERT_Activity_DETAILS;

			preparedStatement = connection.prepareStatement(insertActivityQuery);

			preparedStatement.setString(1, activity);

			preparedStatement.setInt(2, subjectID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Subject's Activity details inserted successfully into table Parent.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting Subject's Activity details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateActivity(String activity, int activityID) {

		try {
			connection = getConnection();

			String updateActivityQuery = QueryMaker.UPDATE_Activity;

			preparedStatement = connection.prepareStatement(updateActivityQuery);

			preparedStatement.setString(1, activity);

			preparedStatement.setInt(2, activityID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated Activity into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating Activity into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public JSONObject retrieveExamListByTermAndAcademicYearID(int academicYearID, String term, int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			// String retrieveExamListByTermAndAcademicYearIDQuery =
			// QueryMaker.RETRIEVE_Exam_LIST_ByTermAcademicYearID;

			String retrieveExamListByTermAndAcademicYearIDQuery = "select distinct e.id, e.examName from SubjectAssessment as sa, Examination as e where sa.examinationID=e.id and "
					+ "sa.ayClassID in(select id from AYClass where standardID =" + standardID
					+ " and academicYearID =e.academicYearID) and e.term IN (" + term + ") and e.academicYearID="
					+ academicYearID + " ";

			System.out.println(
					"retrieveExamListByTermAndAcademicYearIDQuery:" + retrieveExamListByTermAndAcademicYearIDQuery);

			preparedStatement = connection.prepareStatement(retrieveExamListByTermAndAcademicYearIDQuery);

			String ExamList = "";

			/*
			 * preparedStatement.setInt(1, standardID); preparedStatement.setString(2,
			 * term); preparedStatement.setInt(3, academicYearID);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ExamList = resultSet.getString("examName") + "$" + resultSet.getInt("id");

				check1 = 1;

				object = new JSONObject();

				object.put("ExamList", ExamList);
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("ExamList", "");
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

	public JSONObject retrieveExamTermEnd(int academicYearID, String term) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			// String retrieveExamTermEndQuery = QueryMaker.RETRIEVE_Exam_TermEnd;

			String retrieveExamTermEndQuery = "SELECT id, examName FROM Examination WHERE academicYearID = "
					+ academicYearID + " AND term IN (" + term + ") AND examType = 'Term End'";

			preparedStatement = connection.prepareStatement(retrieveExamTermEndQuery);

			String ExamList = "";

			/*
			 * preparedStatement.setInt(1, academicYearID); preparedStatement.setString(2,
			 * term); preparedStatement.setString(3, "Term End");
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ExamList = resultSet.getString("examName") + "$" + resultSet.getInt("id");

				check1 = 1;

				object = new JSONObject();

				object.put("ExamList", ExamList);

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

	public List<StudentForm> retrievestudentsBasedExamCustomReportList(int standardID, String divisionID,
			int academicYearID, String examID, int subjectID, String searchFields, String containsName,
			String searchFieldsNew, String Value) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			connection = getConnection();

			if (containsName.contains("GradeBased")) {
				if (searchFields == "" || searchFields == null || searchFields.isEmpty()) {

					String retrievestudentsBasedCustomReportListForParentQuery = "SELECT s.id, r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st WHERE (s.id = r.studentID AND r.id = st.registrationID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " AND divisionID IN ('" + divisionID + "') " + "AND academicYearID = "
							+ academicYearID
							+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
							+ "WHERE examinationID IN('" + examID
							+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
							+ " AND divisionID IN ('" + divisionID + "') AND " + "academicYearID =" + academicYearID
							+ ") AND subjectID = " + subjectID + ")) ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

				} else {

					String retrievestudentsBasedCustomReportListForParentQuery = " SELECT s.id, r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st WHERE (s.id = r.studentID AND r.id = st.registrationID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " AND divisionID IN ('" + divisionID + "') " + "AND academicYearID = "
							+ academicYearID
							+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
							+ "WHERE examinationID IN('" + examID
							+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
							+ " AND divisionID  IN ('" + divisionID + "') AND " + "academicYearID =" + academicYearID
							+ ") AND subjectID = " + subjectID + ") AND " + "(st.grade = '" + searchFields
							+ "')) ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);
				}

				// System.out.println("retrievestudentsBasedCustomReportListForParentQuery:"+
				// retrievestudentsBasedCustomReportListForParentQuery);
			} else {

				if (searchFields == "" || searchFields == null || searchFields.isEmpty()) {

					String retrievestudentsBasedCustomReportListForParentQuery = "SELECT s.id, r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st WHERE (s.id = r.studentID AND r.id = st.registrationID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " AND divisionID IN ('" + divisionID + "') AND " + "academicYearID = "
							+ academicYearID
							+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
							+ "WHERE examinationID IN('" + examID
							+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
							+ "AND divisionID IN ('" + divisionID + "') AND academicYearID =" + academicYearID
							+ ") AND subjectID = " + subjectID + ")) ORDER BY r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

					System.out.println("retrievestudentsBasedCustomReportListForParentQuery:"
							+ retrievestudentsBasedCustomReportListForParentQuery);

				} else {

					if (Value.equals("between")) {

						String retrievestudentsBasedCustomReportListForParentQuery = " SELECT s.id, r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
								+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st WHERE (s.id = r.studentID AND r.id = st.registrationID "
								+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
								+ standardID + " AND divisionID IN ('" + divisionID + "') " + "AND academicYearID = "
								+ academicYearID
								+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND "
								+ "st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment WHERE examinationID IN('"
								+ examID + "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
								+ " " + "AND divisionID IN ('" + divisionID + "') AND academicYearID =" + academicYearID
								+ ") AND subjectID = " + subjectID + ") AND " + "(st.marksObtained " + Value + " '"
								+ searchFields + "' AND '" + searchFieldsNew + "')) ORDER BY r.rollNumber ASC";

						preparedStatement = connection
								.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

					} else {

						String retrievestudentsBasedCustomReportListForParentQuery = " SELECT s.id, r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
								+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st WHERE (s.id = r.studentID AND r.id = st.registrationID "
								+ "AND s.activityStatus = 'Active' AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
								+ standardID + " AND divisionID IN ('" + divisionID + "') AND " + "academicYearID = "
								+ academicYearID
								+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
								+ "WHERE examinationID IN('" + examID
								+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
								+ " AND divisionID IN ('" + divisionID + "') AND " + "academicYearID =" + academicYearID
								+ ") AND subjectID = " + subjectID + ") AND " + "(st.marksObtained " + Value + " '"
								+ searchFields + "')) ORDER BY r.rollNumber ASC";

						preparedStatement = connection
								.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);
					}
				}

			}

			/*
			 * String retrievestudentsBasedExamCustomReportListQuery =
			 * QueryMaker.RETRIEVE_STUDENT_Based_Exam_CustomReport_LIST;
			 * 
			 * preparedStatement =
			 * connection.prepareStatement(retrievestudentsBasedExamCustomReportListQuery);
			 * 
			 * preparedStatement.setString(1, ActivityStatus.ACTIVE);
			 * preparedStatement.setInt(2, standardID); preparedStatement.setInt(3,
			 * divisionID); preparedStatement.setInt(4, academicYearID);
			 * preparedStatement.setString(5, ActivityStatus.ACTIVE);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("RegistrationID"));

				String studetName = "";

				form.setStudentName(resultSet.getString("studentName"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				list.add(form);
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

	public JSONObject retrieveSubjectListByUserIDByStandard(int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDByStandardQuery = QueryMaker.RETRIEVE_SubjectList_By_Standard;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByStandardQuery);

			String subjectList = "";

			String subject, subject1 = "";

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectList");

				String subArr[] = subjectList.split(",");

				for (int j = 0; j < subArr.length; j++) {

					subject = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

					if (subject == null) {

						continue;

					} else {

						check1 = 1;

						object = new JSONObject();

						object.put("subjectList", subject + '$' + Integer.parseInt(subArr[j].trim()));

						array.add(object);

						values.put("Release", array);
					}

				}

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("subjectList", "");

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

	public HashMap<Integer, String> getSubjectListByStandard(int standardID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveSubjectListByUserIDByExamTypeQuery = QueryMaker.RETRIEVE_SubjectList_By_Standard;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByExamTypeQuery);

			String subjectList, Subject, Subject1 = "";
			int SubjectID = 0;

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {
						continue;
					} else {
						Subject1 = Subject;
					}

					if (Subject1.startsWith(",")) {
						Subject1 = Subject1.substring(1);
					}

					SubjectID = Integer.parseInt(subArr[j].trim());

					map.put(SubjectID, Subject1);

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

	public HashMap<String, String> retrieveExamTermEndAndAcademicYearID(int academicYearID, String term) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			// String retrieveExamTermEndQuery = QueryMaker.RETRIEVE_Exam_TermEnd;

			String retrieveExamTermEndQuery = "SELECT id, examName FROM Examination WHERE academicYearID = "
					+ academicYearID + " AND term = '" + term + "' AND " + "examType = 'Term End' ORDER BY id ASC";

			preparedStatement = connection.prepareStatement(retrieveExamTermEndQuery);

			/*
			 * preparedStatement.setInt(1, academicYearID); preparedStatement.setString(2,
			 * term); preparedStatement.setString(3, "Term End");
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

	public HashMap<Integer, String> getSubjectListByStandardForNonScholastic(int standardID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		ConfigurationForm conform = new ConfigurationForm();

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveSubjectListByUserIDByExamTypeQuery = QueryMaker.RETRIEVE_SubjectList_By_Standard;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByExamTypeQuery);

			String subjectList, Subject, Subject1 = "";
			int SubjectID = 0;

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = daoInf.retrievesubjectNameBySubjectIDForNonScholastic(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {
						continue;
					} else {
						Subject1 = Subject;
					}

					if (Subject1.startsWith(",")) {
						Subject1 = Subject1.substring(1);
					}

					SubjectID = Integer.parseInt(subArr[j].trim());

					map.put(SubjectID, Subject1);

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

	public JSONObject retrieveSubjectListBystandardForNonScholastic(int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {
			connection = getConnection();

			String retrieveSubjectListByUserIDByStandardQuery = QueryMaker.RETRIEVE_SubjectList_By_Standard;

			preparedStatement = connection.prepareStatement(retrieveSubjectListByUserIDByStandardQuery);

			String subjectList = "";

			String subject, subject1 = "";

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				subjectList = resultSet.getString("subjectList");

				String subArr[] = subjectList.split(",");

				for (int j = 0; j < subArr.length; j++) {

					subject = daoInf.retrievesubjectNameBySubjectIDForNonScholastic(Integer.parseInt(subArr[j].trim()));

					if (subject == null) {

						continue;

					} else {

						check1 = 1;

						object = new JSONObject();

						object.put("subjectList", subject + '$' + Integer.parseInt(subArr[j].trim()));

						array.add(object);

						values.put("Release", array);
					}

				}

			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("subjectList", "");

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

	public String retrievrGradesForStudent(int examinationID, int subjectID, int standardID, int divisionID,
			int academicYearID, int studentID) {

		StudentForm form = null;

		String Grade = "";

		try {

			connection = getConnection();

			String retrievrGradesForStudentQuery = QueryMaker.RETREIVE_Grade_For_Student;

			preparedStatement = connection.prepareStatement(retrievrGradesForStudentQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setInt(5, subjectID);
			preparedStatement.setInt(6, studentID);
			preparedStatement.setString(7, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				Grade = resultSet.getString("grade");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Grade;
	}

	public double retrievrMarksForStudent(int examinationID, int subjectID, int standardID, int divisionID,
			int academicYearID, int studentID) {

		StudentForm form = null;

		double Marks = 0;

		try {

			connection = getConnection();

			String retrievrMarksForStudentQuery = QueryMaker.RETREIVE_Marks_For_Student;

			preparedStatement = connection.prepareStatement(retrievrMarksForStudentQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setInt(5, subjectID);
			preparedStatement.setInt(6, studentID);
			preparedStatement.setString(7, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				Marks = resultSet.getDouble("marksObtained");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Marks;
	}

	public HashMap<String, String> retrieveAllExamTermEndAndAcademicYearID(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationForm conform = new ConfigurationForm();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		try {

			connection = getConnection();

			String retrieveExamTermEndQuery = QueryMaker.RETRIEVE_AllExam_TermEnd;

			preparedStatement = connection.prepareStatement(retrieveExamTermEndQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Term End");

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

	public JSONObject retrieveAllExamListByTermAndAcademicYearID(int academicYearID, int standardID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveExamListByTermAndAcademicYearIDQuery = QueryMaker.RETRIEVE_AllExam_LIST_ByTermAcademicYearID;

			preparedStatement = connection.prepareStatement(retrieveExamListByTermAndAcademicYearIDQuery);

			String ExamList = "";

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ExamList = resultSet.getString("examName") + "$" + resultSet.getInt("id");

				check1 = 1;

				object = new JSONObject();

				object.put("ExamList", ExamList);
				object.put("check", check1);

				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {

				object = new JSONObject();

				object.put("ExamList", "");
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

	public JSONObject retrieveAllExamTermEnd(int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveExamTermEndQuery = QueryMaker.RETRIEVE_AllExam_TermEnd;

			preparedStatement = connection.prepareStatement(retrieveExamTermEndQuery);

			String ExamList = "";

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Term End");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ExamList = resultSet.getString("examName") + "$" + resultSet.getInt("id");

				check1 = 1;

				object = new JSONObject();

				object.put("ExamList", ExamList);

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

	public List<StudentForm> retrievestudentsBasedAllExamCustomReportList(int standardID, int academicYearID,
			String examID, int subjectID, String searchFields, String containsName, String searchFieldsNew,
			String Value) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm form = null;

		try {

			connection = getConnection();

			if (containsName.contains("GradeBased")) {
				if (searchFields == "" || searchFields == null || searchFields.isEmpty()) {

					String retrievestudentsBasedCustomReportListForParentQuery = "SELECT s.id,r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st, AYClass AS ay WHERE (s.id = r.studentID AND r.id = st.registrationID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID =ay.id AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " " + "AND academicYearID = " + academicYearID
							+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
							+ "WHERE examinationID IN('" + examID
							+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
							+ "AND academicYearID =" + academicYearID + ") AND subjectID = " + subjectID
							+ ")) ORDER BY ay.divisionID, r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

				} else {

					String retrievestudentsBasedCustomReportListForParentQuery = " SELECT s.id,r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st, AYClass AS ay WHERE (s.id = r.studentID AND r.id = st.registrationID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID =ay.id AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " " + "AND academicYearID = " + academicYearID
							+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
							+ "WHERE examinationID IN('" + examID
							+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
							+ " AND academicYearID =" + academicYearID + ") AND subjectID = " + subjectID + ") AND "
							+ "(st.grade = '" + searchFields + "')) ORDER BY ay.divisionID, r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);
				}

				// System.out.println("retrievestudentsBasedCustomReportListForParentQuery:"+
				// retrievestudentsBasedCustomReportListForParentQuery);
			} else {

				if (searchFields == "" || searchFields == null || searchFields.isEmpty()) {

					String retrievestudentsBasedCustomReportListForParentQuery = "SELECT s.id,r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
							+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st, AYClass AS ay WHERE (s.id = r.studentID AND r.id = st.registrationID "
							+ "AND s.activityStatus = 'Active' AND r.ayclassID =ay.id AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
							+ standardID + " " + "AND academicYearID = " + academicYearID
							+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
							+ "WHERE examinationID IN('" + examID
							+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
							+ "AND academicYearID =" + academicYearID + ") AND subjectID = " + subjectID
							+ ")) ORDER BY ay.divisionID, r.rollNumber ASC";

					preparedStatement = connection
							.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);
					System.out.println("Query New : " + retrievestudentsBasedCustomReportListForParentQuery);
				} else {

					if (Value.equals("between")) {

						String retrievestudentsBasedCustomReportListForParentQuery = " SELECT s.id,r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
								+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st, AYClass AS ay WHERE (s.id = r.studentID AND r.id = st.registrationID "
								+ "AND s.activityStatus = 'Active' AND r.ayclassID =ay.id AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
								+ standardID + " " + "AND academicYearID = " + academicYearID
								+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
								+ "WHERE examinationID IN('" + examID
								+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
								+ " AND academicYearID =" + academicYearID + ") AND subjectID = " + subjectID + ") AND "
								+ "(st.marksObtained " + Value + " '" + searchFields + "' AND '" + searchFieldsNew
								+ "')) ORDER BY ay.divisionID, r.rollNumber ASC";

						preparedStatement = connection
								.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

					} else {

						String retrievestudentsBasedCustomReportListForParentQuery = " SELECT s.id,r.id AS RegistrationID , CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, "
								+ "r.rollNumber FROM Student AS s, Registration AS r, StudentAssessment AS st, AYClass AS ay WHERE (s.id = r.studentID AND r.id = st.registrationID "
								+ "AND s.activityStatus = 'Active' AND r.ayclassID =ay.id AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = "
								+ standardID + " " + "AND academicYearID = " + academicYearID
								+ ") AND r.activityStatus = 'Active' AND st.activityStatus = 'Active' AND st.subjectAssessmentID IN(SELECT id FROM SubjectAssessment "
								+ "WHERE examinationID IN('" + examID
								+ "') AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
								+ " AND academicYearID =" + academicYearID + ") AND subjectID = " + subjectID + ") AND "
								+ "(st.marksObtained " + Value + " '" + searchFields
								+ "')) ORDER BY ay.divisionID, r.rollNumber ASC";

						preparedStatement = connection
								.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

						System.out.println("Query New : " + retrievestudentsBasedCustomReportListForParentQuery);
					}
				}

			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("RegistrationID"));

				String studetName = "";

				form.setStudentName(resultSet.getString("studentName"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				list.add(form);
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

	public double retrievrMarksScaledForStudent(int examinationID, int subjectID, int standardID, int divisionID,
			int academicYearID, int studentID) {

		StudentForm form = null;

		double Marks = 0;

		try {

			connection = getConnection();

			// String retrievrMarksForStudentQuery =
			// QueryMaker.RETREIVE_MarksScaled_For_Student;

			String retrievrMarksForStudentQuery = "SELECT marksScaled FROM StudentAssessment WHERE subjectAssessmentID IN(SELECT id FROM "
					+ "SubjectAssessment WHERE examinationID = " + examinationID
					+ " AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID + " "
					+ "AND divisionID IN ('" + divisionID + "') AND academicYearID = " + academicYearID
					+ " ) AND subjectID = " + subjectID + " ) AND registrationID = " + studentID
					+ " AND activityStatus = 'Active'";

			preparedStatement = connection.prepareStatement(retrievrMarksForStudentQuery);

			/*
			 * preparedStatement.setInt(1, examinationID); preparedStatement.setInt(2,
			 * standardID); preparedStatement.setInt(3, divisionID);
			 * preparedStatement.setInt(4, academicYearID); preparedStatement.setInt(5,
			 * subjectID); preparedStatement.setInt(6, studentID);
			 * preparedStatement.setString(7, ActivityStatus.ACTIVE);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				Marks = resultSet.getDouble("marksScaled");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Marks;
	}

	public String retrievrAllGradesForStudent(int examinationID, int subjectID, int standardID, int academicYearID,
			int studentID) {

		StudentForm form = null;

		String Grade = "";

		try {

			connection = getConnection();

			String retrievrGradesForStudentQuery = QueryMaker.RETREIVE_AllGrade_For_Student;

			preparedStatement = connection.prepareStatement(retrievrGradesForStudentQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setInt(4, subjectID);
			preparedStatement.setInt(5, studentID);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				Grade = resultSet.getString("grade");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Grade;
	}

	public double retrievrAllMarksForStudent(int examinationID, int subjectID, int standardID, int academicYearID,
			int studentID) {

		StudentForm form = null;

		double Marks = 0;

		try {

			connection = getConnection();

			String retrievrMarksForStudentQuery = QueryMaker.RETREIVE_AllMarks_For_Student;

			preparedStatement = connection.prepareStatement(retrievrMarksForStudentQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setInt(4, subjectID);
			preparedStatement.setInt(5, studentID);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				Marks = resultSet.getDouble("marksObtained");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Marks;
	}

	public int verifyExamType(int examinationID, String examType) {

		int check = 0;

		try {

			connection = getConnection();

			String verifyExamTypeQuery = QueryMaker.VERIFY_Exam_Type;

			preparedStatement = connection.prepareStatement(verifyExamTypeQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setString(2, examType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = resultSet.getInt("id");
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

	public int retrieveScaledMarks(String term, int academicYearID, int standardID, int divisionID, int subjectID) {

		int ScaledMarks = 0;

		try {

			connection = getConnection();

			String retrieveScaledMarksQuery = QueryMaker.RETRIEVE_Scaled_Marks;

			preparedStatement = connection.prepareStatement(retrieveScaledMarksQuery);

			preparedStatement.setString(1, term);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, "Notebook");
			preparedStatement.setInt(4, standardID);
			preparedStatement.setInt(5, divisionID);
			preparedStatement.setInt(6, academicYearID);
			preparedStatement.setString(7, ActivityStatus.ACTIVE);
			preparedStatement.setInt(8, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ScaledMarks = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ScaledMarks;
	}

	public int retrievTotalMarks(int examinationID, int subjectID, int standardID, int divisionID, int academicYearID) {

		int TotalMarks = 0;

		try {

			connection = getConnection();

			// String retrieveScaledMarksQuery = QueryMaker.RETRIEVE_Total_Marks;

			String retrieveScaledMarksQuery = "SELECT totalMarks FROM SubjectAssessment WHERE examinationID = "
					+ examinationID + " AND subjectID = " + subjectID + " "
					+ "AND ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
					+ " AND divisionID IN ('" + divisionID + "') AND " + "academicYearID = " + academicYearID
					+ " AND activityStatus = 'Active') ";

			preparedStatement = connection.prepareStatement(retrieveScaledMarksQuery);

			/*
			 * preparedStatement.setInt(1, examinationID); preparedStatement.setInt(2,
			 * subjectID); preparedStatement.setInt(3, standardID);
			 * preparedStatement.setInt(4, divisionID); preparedStatement.setInt(5,
			 * academicYearID); preparedStatement.setString(6, ActivityStatus.ACTIVE);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				TotalMarks = resultSet.getInt("totalMarks");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return TotalMarks;
	}

	public int retrieveAllScaledMarks(int academicYearID, int standardID, int subjectID) {

		int ScaledMarks = 0;

		try {

			connection = getConnection();

			String retrieveScaledMarksQuery = QueryMaker.RETRIEVE_All_Scaled_Marks;

			preparedStatement = connection.prepareStatement(retrieveScaledMarksQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Notebook");
			preparedStatement.setInt(3, standardID);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);
			preparedStatement.setInt(6, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ScaledMarks = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ScaledMarks;
	}

	public int retrieveSubjectType(int subjectID) {

		int SubjectType = 0;

		try {

			connection = getConnection();

			String retrieveSubjectTypeQuery = QueryMaker.RETRIEVE_SubjectType;

			preparedStatement = connection.prepareStatement(retrieveSubjectTypeQuery);

			preparedStatement.setInt(1, subjectID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.setString(3, "Personality Development & Life Skills");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectType = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectType;
	}

	public int retrieveAllScaledMarks(String term, int academicYearID, Integer examinationID, int standardID,
			int subjectID) {

		int ScaledMarks = 0;

		try {

			connection = getConnection();

			String retrieveScaledMarksQuery = QueryMaker.RETRIEVE_All_Scaled_Marks_New;

			preparedStatement = connection.prepareStatement(retrieveScaledMarksQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ScaledMarks = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ScaledMarks;
	}

	public JSONObject verifySubjectType(int subjectID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String retrieveExamListByTermAndAcademicYearIDQuery = QueryMaker.RETRIEVE_SubjectType;

			preparedStatement = connection.prepareStatement(retrieveExamListByTermAndAcademicYearIDQuery);

			String ExamList = "";

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Personality Development & Life Skills");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("hellllllo...........");
				check1 = 1;

				object = new JSONObject();

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

	public String retrieveSubjectTypeValue(int subjectID) {

		String SubjectType = "";

		try {

			connection = getConnection();

			String retrieveSubjectTypeQuery = QueryMaker.RETRIEVE_SubjectType_Value;

			preparedStatement = connection.prepareStatement(retrieveSubjectTypeQuery);

			preparedStatement.setInt(1, subjectID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectType = resultSet.getString("subjectType");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectType;
	}

	public int retrieveScaledMarksNew(String term, int academicYearID, Integer examinationID, int standardID,
			int divisionID, int subjectID) {

		int ScaledMarks = 0;

		try {

			connection = getConnection();

			// String retrieveScaledMarksQuery = QueryMaker.RETRIEVE_Scaled_Marks_New;

			String retrieveScaledMarksQuery = "SELECT distinct scaleTo FROM SubjectAssessment WHERE examinationID = "
					+ examinationID + " AND " + "ayClassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
					+ " AND divisionID IN ('" + divisionID + "') AND " + "academicYearID = " + academicYearID
					+ " AND activityStatus = 'Active') AND subjectID =" + subjectID + " ";

			preparedStatement = connection.prepareStatement(retrieveScaledMarksQuery);

			/*
			 * preparedStatement.setInt(1, examinationID); preparedStatement.setInt(2,
			 * standardID); preparedStatement.setInt(3, divisionID);
			 * preparedStatement.setInt(4, academicYearID); preparedStatement.setString(5,
			 * ActivityStatus.ACTIVE); preparedStatement.setInt(6, subjectID);
			 */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ScaledMarks = resultSet.getInt("scaleTo");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ScaledMarks;
	}

	public JSONObject retrieveStandardListForacademicYear(int academicYearID, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		ConfigurationForm conform = new ConfigurationForm();

		try {
			connection = getConnection();

			String retrieveStudentListForStandardAndDivisionQuery = QueryMaker.Retrieve_Standard_For_AcademicYear;

			preparedStatement = connection.prepareStatement(retrieveStudentListForStandardAndDivisionQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				conform = new ConfigurationForm();

				check1 = 1;

				object = new JSONObject();

				object.put("standard", resultSet.getString("standard"));

				object.put("standardID", resultSet.getString("standardID"));

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

	public JSONObject retrieveDivisionForacademicYear(int academicYearID, int userID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		ConfigurationForm conform = new ConfigurationForm();

		try {
			connection = getConnection();

			String retrieveStudentListForStandardAndDivisionQuery = QueryMaker.Retrieve_Division_For_AcademicYear;

			preparedStatement = connection.prepareStatement(retrieveStudentListForStandardAndDivisionQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setInt(2, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				conform = new ConfigurationForm();

				check1 = 1;

				object = new JSONObject();

				object.put("division", resultSet.getString("division"));

				object.put("divisionID", resultSet.getString("divisionID"));

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

	public String importStudentDetails(ReportRow reportRow) {
		System.out.println("date val is: " + reportRow.getDateOfBirth());

		// For date format for my use
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		// Date format for sevasadan use
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertStudentDetailsQuery = QueryMaker.INSERT_Student_DETAILS_New;

			preparedStatement = connection.prepareStatement(insertStudentDetailsQuery);

			// Inserting Start Date
			if (reportRow.getDateOfBirth() == null || reportRow.getDateOfBirth() == "") {

				// form.setDateOfBirth(null);
				preparedStatement.setString(4, reportRow.getDateOfBirth());

			} else if (reportRow.getDateOfBirth().isEmpty()) {

				preparedStatement.setString(4, reportRow.getDateOfBirth());

			} else {

				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(reportRow.getDateOfBirth())));
			}

			preparedStatement.setString(1, reportRow.getFirstName());
			preparedStatement.setString(2, reportRow.getMiddleName());
			preparedStatement.setString(3, reportRow.getLastName());
			preparedStatement.setString(5, "" + reportRow.getAadhaar());
			preparedStatement.setString(6, reportRow.getBloodgroup());
			preparedStatement.setString(7, reportRow.getCategory());
			preparedStatement.setString(8, reportRow.getHasSpectacles());
			preparedStatement.setString(9, reportRow.getReligion());
			preparedStatement.setString(10, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student details imported successfully into table Student.");

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

	public String importStudentContact(ReportRow reportRow, int studentID) {
		try {
			connection = getConnection();

			String insertStudentContactQuery = QueryMaker.INSERT_StudentContact_new;

			preparedStatement = connection.prepareStatement(insertStudentContactQuery);

			preparedStatement.setString(1, reportRow.getAddress());
			preparedStatement.setInt(2, studentID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student contact details imported successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing student contact details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String importStudentPersonalInfo(ReportRow reportRow, int studentID, int commutationID) {
		try {
			connection = getConnection();

			String insertStudentPersonalInfoQuery = QueryMaker.INSERT_StudentPersonalInfo_New;

			preparedStatement = connection.prepareStatement(insertStudentPersonalInfoQuery);

			preparedStatement.setString(1, "" + reportRow.getGrNumber());
			preparedStatement.setString(2, reportRow.getHouse());
			preparedStatement.setString(3, reportRow.getCommutationMode());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, studentID);
			preparedStatement.setInt(6, commutationID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student Personal Information Imported successfully into table StudentDetails.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing Student Personal Information into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveCommutationID(String nameOfDriver) {
		int CommutationID = 0;
		try {
			connection = getConnection();

			String retrievePatientIDQuery = QueryMaker.RETRIEVE_CommutationID;

			preparedStatement = connection.prepareStatement(retrievePatientIDQuery);

			preparedStatement.setString(1, nameOfDriver);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				CommutationID = resultSet.getInt("id");
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
		return CommutationID;
	}

	public String importEmergencyContacts(ReportRow reportRow, int studentID) {

		try {
			connection = getConnection();

			String insertEmergencyContactsQuery = QueryMaker.INSERT_EmergencyContacts_DETAILS;

			preparedStatement = connection.prepareStatement(insertEmergencyContactsQuery);

			preparedStatement.setString(1, reportRow.getName());

			preparedStatement.setString(2, "" + reportRow.getPhone());

			preparedStatement.setInt(3, studentID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student's Emergency Contacts details imported successfully into table Parent.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while importing Student's Emergency Contacts details into table due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveAYClassIDNew(String standard, String division, int academicYearID) {
		int AYID = 0;

		try {
			connection = getConnection();

			String retrieveAYClassIDQuery = QueryMaker.RETRIEVE_AYClassID_By_StandardName;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, standard);
			preparedStatement.setString(4, division);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				AYID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving AYClass id from AYClass table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return AYID;
	}

	public String importRegistrationInfo(ReportRow reportRow, int studentID, int AYClassID) {
		try {
			connection = getConnection();

			String insertStudentRegistrationInfoQuery = QueryMaker.INSERT_StudentRegistrationInfo_New;

			preparedStatement = connection.prepareStatement(insertStudentRegistrationInfoQuery);

			preparedStatement.setInt(1, reportRow.getRollNumber());
			preparedStatement.setDouble(2, reportRow.getWeight());
			preparedStatement.setDouble(3, reportRow.getHeight());
			preparedStatement.setString(4, reportRow.getCreativeActivities());
			preparedStatement.setString(5, reportRow.getPhysicalActivities());
			preparedStatement.setString(6, ActivityStatus.ACTIVE);
			preparedStatement.setInt(7, studentID);
			preparedStatement.setInt(8, AYClassID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student Registration Information imported successfully into table Registration.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing Student Registration Information into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public boolean verifyParentsDetail(int studentID, String relation) {

		boolean check = false;

		try {
			connection = getConnection();

			String verifyStudentDetailQuery = QueryMaker.VERIFY_Parent_DETAIL;

			preparedStatement = connection.prepareStatement(verifyStudentDetailQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, relation);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while verifying Student parents detail from table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public boolean verifyAcademicYearID(String academicYear, int academicYearID) {

		boolean check = false;

		try {
			connection = getConnection();

			String verifyStudentDetailQuery = QueryMaker.VERIFY_AcademicYearID_DETAIL;

			preparedStatement = connection.prepareStatement(verifyStudentDetailQuery);

			preparedStatement.setString(1, academicYear);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying academicYearID from table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String importExaminationDetails(ReportRow reportRow, int academicYearID) {

		// For date format for my use
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// Date format for sevasadan use
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertExaminationDetailsQuery = QueryMaker.INSERT_Examination;

			preparedStatement = connection.prepareStatement(insertExaminationDetailsQuery);

			preparedStatement.setString(1, reportRow.getTerm());
			preparedStatement.setString(2, reportRow.getExamName());
			preparedStatement.setString(3, reportRow.getExamType());

			// Inserting Start Date
			if (reportRow.getStartDate() == null || reportRow.getStartDate() == "") {
				preparedStatement.setString(4, reportRow.getStartDate());

			} else if (reportRow.getStartDate().isEmpty()) {
				preparedStatement.setString(4, reportRow.getStartDate());

			} else {
				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(reportRow.getStartDate())));
			}

			// Inserting End Date
			if (reportRow.getEndDate() == null || reportRow.getEndDate() == "") {
				preparedStatement.setString(5, reportRow.getEndDate());

			} else if (reportRow.getEndDate().isEmpty()) {
				preparedStatement.setString(5, reportRow.getEndDate());

			} else {
				preparedStatement.setString(5, dateToBeFormatted.format(dateFormat.parse(reportRow.getEndDate())));
			}

			preparedStatement.setInt(6, academicYearID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Examination details imported successfully into table Examination.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing examination detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveStandardIDByStandardName(String standard) {

		int StandardID = 0;

		try {
			connection1 = getConnection();

			String retrieveStandardIDByStandardNameQuery = QueryMaker.RETRIEVE_StandardID_By_StandardName;

			preparedStatement1 = connection1.prepareStatement(retrieveStandardIDByStandardNameQuery);

			preparedStatement1.setString(1, standard);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				StandardID = resultSet1.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Standard id from Standard table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}
		return StandardID;
	}

	public String importAttendanceDetails(ReportRow reportRow1, int academicYearID, int standardID) {
		int workingDays = 0;

		try {
			connection = getConnection();

			String insertExaminationDetailsQuery = QueryMaker.INSERT_Attendance;

			preparedStatement = connection.prepareStatement(insertExaminationDetailsQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, reportRow1.getTerm());
			preparedStatement.setString(3, reportRow1.getMonth());

			if (reportRow1.getWorkigDays() == "" || reportRow1.getWorkigDays() == null) {
				workingDays = 0;
			} else if (reportRow1.getWorkigDays().isEmpty()) {
				workingDays = 0;
			} else {
				workingDays = (int) Double.parseDouble(reportRow1.getWorkigDays());
			}

			preparedStatement.setInt(4, workingDays);
			preparedStatement.setInt(5, academicYearID);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("Attendance details imported successfully into table Attendance.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing attendance detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String importTimeTableDetails(ReportRow reportRow2, int academicYearID, int examID, int standardID) {

		// For date format for my use
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// Date format for sevasadan use
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertTimeTableDetailsQuery = QueryMaker.INSERT_TimeTable;

			preparedStatement = connection.prepareStatement(insertTimeTableDetailsQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, examID);

			// Inserting Date
			if (reportRow2.getStartDate() == null || reportRow2.getStartDate() == "") {
				preparedStatement.setString(3, reportRow2.getStartDate());

			} else if (reportRow2.getStartDate().isEmpty()) {
				preparedStatement.setString(3, reportRow2.getStartDate());

			} else {
				preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(reportRow2.getStartDate())));
			}

			preparedStatement.setString(4, reportRow2.getSubject());

			preparedStatement.execute();

			status = "success";
			System.out.println("Examination details imported successfully into table Examination.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing examination detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveExamIDByExamName(String examName, String Date, int academicYearID) {

		// For date format for my use
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// Date format for sevasadan use
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		int ExamID = 0;

		try {
			connection = getConnection();

			String retrieveExamIDByExamNameQuery = QueryMaker.RETRIEVE_ExamID_By_ExamName;

			preparedStatement = connection.prepareStatement(retrieveExamIDByExamNameQuery);

			preparedStatement.setString(1, examName);

			preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(Date)));

			preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(Date)));

			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ExamID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Exam id from Examination table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return ExamID;
	}

	public String importParentsDetails(String firstName, String middleName, String lastName, String mobile,
			String emailID, String occupation, String relation, int studentID) {
		try {
			connection = getConnection();

			String insertStudentRegistrationInfoQuery = QueryMaker.INSERT_StudentParents_Info;

			preparedStatement = connection.prepareStatement(insertStudentRegistrationInfoQuery);

			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, middleName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, mobile);
			preparedStatement.setString(5, emailID);
			preparedStatement.setString(6, occupation);
			preparedStatement.setString(7, relation);
			preparedStatement.setInt(8, studentID);
			preparedStatement.setString(9, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("Student Parents Information imported successfully into table Registration.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing Student Registration Information into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<Integer> retrievestudentsIDBasedList(int standardID, int divisionID, int academicYearID) {

		List<Integer> list = new ArrayList<Integer>();

		try {

			connection = getConnection();

			String retrievestudentsIDBasedListQuery = QueryMaker.RETRIEVE_StudentsID_Based_LIST;

			preparedStatement = connection.prepareStatement(retrievestudentsIDBasedListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.setInt(3, standardID);

			preparedStatement.setInt(4, divisionID);

			preparedStatement.setInt(5, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				list.add(resultSet.getInt("id"));

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

	public String retrieveStudentsBasedParentsCustomReportList(int standardID, int divisionID, int academicYearID,
			String searchField, String checkBoxList, Integer studentID) {

		StudentForm form = null;

		String StudentsParentData = "";

		try {

			form = new StudentForm();

			connection = getConnection();

			if (checkBoxList == null) {

				String retrievestudentsBasedCustomReportListForParentQuery = "SELECT DISTINCT(s.id), CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber "
						+ " FROM Student AS s, Parent AS p, Registration AS r WHERE (s.id = p.studentID AND s.id = r.studentID AND s.activityStatus = 'Active' AND r.activityStatus='Active' "
						+ "AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
						+ " AND divisionID = " + divisionID + " AND academicYearID = " + academicYearID
						+ " )) AND (CONCAT(p.lastName,' ',p.firstName,' ',p.middleName) LIKE '%" + searchField + "%' "
						+ "OR p.relation LIKE '%" + searchField + "%' OR p.emailId LIKE '%" + searchField
						+ "%' OR p.mobile LIKE '%" + searchField + "%' OR p.occupation LIKE '%" + searchField
						+ "%') AND s.id = " + studentID + " ORDER BY r.rollNumber ASC";

				preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);

				System.out.println("Query :" + retrievestudentsBasedCustomReportListForParentQuery);

			} else {

				String retrievestudentsBasedCustomReportListForParentQuery = "SELECT " + checkBoxList
						+ ", s.id, CONCAT(s.lastName,' ',s.firstName,' ',s.middleName)AS studentName, r.rollNumber "
						+ " FROM Student AS s, Parent AS p, Registration AS r WHERE (s.id = p.studentID AND s.id = r.studentID AND s.activityStatus = 'Active' AND r.activityStatus='Active' "
						+ "AND r.ayclassID IN(SELECT id FROM AYClass WHERE standardID = " + standardID
						+ " AND divisionID = " + divisionID + " AND academicYearID = " + academicYearID
						+ " )) AND (CONCAT(p.lastName,' ',p.firstName,' ',p.middleName) LIKE '%" + searchField + "%' "
						+ "OR p.relation LIKE '%" + searchField + "%' OR p.emailId LIKE '%" + searchField
						+ "%' OR p.mobile LIKE '%" + searchField + "%' OR p.occupation LIKE '%" + searchField
						+ "%') AND s.id = " + studentID + " ORDER BY r.rollNumber ASC";

				preparedStatement = connection.prepareStatement(retrievestudentsBasedCustomReportListForParentQuery);
				System.out.println("retrievestudentsBasedCustomReportListForParentQuery:"
						+ retrievestudentsBasedCustomReportListForParentQuery);
			}

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int counter = 1;

				form = new StudentForm();

				String StudentData = "", StudentDetails = "";

				if (checkBoxList == null) {

					StudentsParentData = resultSet.getInt("rollNumber") + "$" + resultSet.getString("studentName");

					// System.out.println("StudentData: "+StudentsParentData);
				} else {

					String[] List = checkBoxList.split(" AS ");

					for (int i = 0; i < List.length; i++) {

						if (i == 0) {

							continue;
						} else {
							if (List[i].contains(",")) {

								String[] newList = List[i].split(",");

								StudentDetails = StudentDetails + "$" + resultSet.getString(newList[0].trim());

							} else {

								StudentDetails = StudentDetails + "$" + resultSet.getString(List[i].trim());

							}
						}

						counter++;
					}

					StudentData = resultSet.getInt("rollNumber") + "$" + resultSet.getString("studentName");

					StudentsParentData = StudentData + StudentDetails;

					// System.out.println("StudentDetails: "+StudentsParentData);
				}
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
		return StudentsParentData;
	}

	public int retrieveDivisionIDByStandardDivision(int standardID, String division) {

		int DivisionID = 0;

		try {
			connection = getConnection();

			String retrieveDivisionIDByStandardDivisionQuery = QueryMaker.RETRIEVE_DivisionID_By_StandardDivision;

			preparedStatement = connection.prepareStatement(retrieveDivisionIDByStandardDivisionQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, division);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				DivisionID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Division id from Divivsion table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return DivisionID;

	}

	public int retrieveClassTeacherIDByclassTeacherName(String classTeacher) {

		int ClassTeacherID = 0;

		try {
			connection = getConnection();

			String retrieveClassTeacherIDByUserNameQuery = QueryMaker.RETRIEVE_ClassTeacherID_By_UserName;

			preparedStatement = connection.prepareStatement(retrieveClassTeacherIDByUserNameQuery);

			preparedStatement.setString(1, classTeacher);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ClassTeacherID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving ClassTeacher id from AppUser table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ClassTeacherID;

	}

	public String importAYClassDetails(ReportRow reportRow, int academicYearID, int divisionID, int standardID,
			int classTeacherID) {

		try {
			connection = getConnection();

			String insertAYClassDetailsQuery = QueryMaker.INSERT_AcademicYearList;

			preparedStatement = connection.prepareStatement(insertAYClassDetailsQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setInt(2, divisionID);
			preparedStatement.setInt(3, classTeacherID);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("AYClass details imported successfully into table AYClass.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while importing AYClass detail into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveSubjectIDBySubjectName(String subject, int organizationID) {

		int SubjectID = 0;

		try {
			connection = getConnection();

			String retrieveSubjectIDBySubjectNameQuery = QueryMaker.RETRIEVE_SubjectID_By_SubjectName;

			preparedStatement = connection.prepareStatement(retrieveSubjectIDBySubjectNameQuery);

			preparedStatement.setString(1, subject);
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subjectID id from Subject table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectID;

	}

	public String importAYSubjectDetails(ReportRow reportRow, int AYClassID, int subjectNameID, int teacher1id,
			int teacher2id) {

		try {
			connection = getConnection();

			String insertAYSubjectDetailsQuery = QueryMaker.INSERT_Config_EVENT;

			preparedStatement = connection.prepareStatement(insertAYSubjectDetailsQuery);

			preparedStatement.setInt(1, subjectNameID);
			preparedStatement.setInt(2, teacher1id);
			preparedStatement.setInt(3, teacher2id);
			preparedStatement.setInt(4, AYClassID);

			preparedStatement.execute();

			status = "success";
			System.out.println("AYSubject details imported successfully into table AYClass.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while importing AYSubject detail into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public double retrievrAllMarksScaledForStudent(Integer examinationID, int subjectID, int standardID,
			int academicYearID, int studentID) {

		StudentForm form = null;

		double Marks = 0;

		try {

			connection = getConnection();

			String retrievrMarksForStudentQuery = QueryMaker.RETREIVE_AllMarksScaled_For_Student;

			preparedStatement = connection.prepareStatement(retrievrMarksForStudentQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setInt(4, subjectID);
			preparedStatement.setInt(5, studentID);
			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				Marks = resultSet.getDouble("marksScaled");

			}

		} catch (Exception exception) {
			// exception.printStackTrace();
			System.out.println("Exception occured while retriving creativeActivities list from " + "database due to:::"
					+ exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Marks;
	}

	public int retrievAllTotalMarks(Integer examinationID, int subjectID, int standardID, int academicYearID) {

		int TotalMarks = 0;

		try {

			connection = getConnection();

			String retrieveScaledMarksQuery = QueryMaker.RETRIEVE_All_Total_Marks;

			preparedStatement = connection.prepareStatement(retrieveScaledMarksQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, standardID);
			preparedStatement.setInt(4, academicYearID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				TotalMarks = resultSet.getInt("totalMarks");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return TotalMarks;
	}

	public String retrieveExaminationType(int examinatioNID) {

		String examinationType = "";

		try {

			connection = getConnection();

			String retrieveExaminationTypeQuery = QueryMaker.RETRIEVE_ExaminationlInfo;

			preparedStatement = connection.prepareStatement(retrieveExaminationTypeQuery);

			preparedStatement.setInt(1, examinatioNID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				examinationType = resultSet.getString("examType");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return examinationType;
	}

	public String retrieveDivisionName(String divisionIDStr) {

		String divisionName = "";

		try {

			connection = getConnection();

			String retrieveExaminationTypeQuery = "SELECT GROUP_CONCAT(division SEPARATOR '_') AS division FROM Division WHERE id IN ("
					+ divisionIDStr + ")";

			preparedStatement = connection.prepareStatement(retrieveExaminationTypeQuery);

			// preparedStatement.setString(1, divisionIDStr);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				divisionName = resultSet.getString("division");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return divisionName;
	}

	public String retrieveTermByExaminationID(int examinationID) {

		String term = "";

		try {

			connection = getConnection();

			String retrieveTermByExaminationIDQuery = QueryMaker.RETRIEVE_ExaminationlInfo;

			preparedStatement = connection.prepareStatement(retrieveTermByExaminationIDQuery);

			preparedStatement.setInt(1, examinationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				term = resultSet.getString("term");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return term;
	}

	public String retrieveSubjectBySubjectID(int subjectID) {

		String Subject = "";

		try {
			connection = getConnection();

			String retrieveSubjectBySubjectIDQuery = QueryMaker.RETRIEVE_Subject_By_SubjectID;

			preparedStatement = connection.prepareStatement(retrieveSubjectBySubjectIDQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Subject = resultSet.getString("name");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject from SubjectID table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return Subject;

	}

	public List<StudentForm> retrievStudentsListByStandardID(int standardID, int academicYearID) {

		List<StudentForm> list = new ArrayList<StudentForm>();

		StudentForm studform = null;

		try {

			connection = getConnection();

			String retrievStudentsListByStandardIDQuery = QueryMaker.Retrieve_StudentsList_By_StandardID;

			preparedStatement = connection.prepareStatement(retrievStudentsListByStandardIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				studform = new StudentForm();

				studform.setStudentID(resultSet.getInt("RegistrationID"));
				studform.setStudentName(resultSet.getString("studentName"));
				studform.setRollNumber(resultSet.getInt("rollNumber"));
				studform.setAyclassID(resultSet.getInt("AYClassID"));

				list.add(studform);

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

	public List<StudentForm> retriveStudentDetailsListByStandardIDAndDivisionID(int standardID, int divisionID) {

		List<StudentForm> list = new ArrayList<StudentForm>();
		StudentForm form = null;

		try {

			connection = getConnection();

			String retriveStudentListByStandardAndDivisionQuery = QueryMaker.RETREIVE_StudentDetails_LIST;

			preparedStatement = connection.prepareStatement(retriveStudentListByStandardAndDivisionQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setInt(2, divisionID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				form.setStudentID(resultSet.getInt("id"));

				form.setRollNumber(resultSet.getInt("rollNumber"));

				form.setStudentName(resultSet.getString("studentName"));

				form.setGender(resultSet.getString("gender"));

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

	public String retrievePersonalityDevelopmentMarksBySubjectID(int ayClassID, int examID, int studentID,
			Integer subjectID) {

		String grade = "";

		try {

			connection = getConnection();

			String retrievePersonalityDevelopmentMarksBySubjectIDQuery = QueryMaker.RETRIEVE_PersonalityDevelopmentMarksInfo;

			preparedStatement = connection.prepareStatement(retrievePersonalityDevelopmentMarksBySubjectIDQuery);

			preparedStatement.setInt(1, examID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, ayClassID);
			preparedStatement.setInt(4, studentID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				StudentForm form = new StudentForm();

				if (resultSet.getInt("absentFlag") == 1) {

					grade = "ex";

				} else {

					grade = resultSet.getString("grade");
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return grade;
	}

	public int retrieveExaminationID(String term, String examName, int academicYearID) {

		int examID = 0;

		try {

			connection = getConnection();

			String retrieveExaminationIDQuery = QueryMaker.RETRIEVE_ExaminationID;

			preparedStatement = connection.prepareStatement(retrieveExaminationIDQuery);

			preparedStatement.setString(1, term);
			preparedStatement.setString(2, examName);
			preparedStatement.setInt(3, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				examID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return examID;
	}

	public String importSubjectAssessmentDetails(int gradeChack, int scaleVal, int totalMarks, int ayclassID,
			int examinationID, int subjectID) {

		try {
			connection = getConnection();

			String importSubjectAssessmentDetailsQuery = QueryMaker.INSERT_SUBJECT_ASSESSMENT;

			preparedStatement = connection.prepareStatement(importSubjectAssessmentDetailsQuery);

			preparedStatement.setInt(1, examinationID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, totalMarks);
			preparedStatement.setInt(4, scaleVal);
			preparedStatement.setInt(5, gradeChack);
			preparedStatement.setInt(6, ayclassID);

			preparedStatement.execute();

			status = "success";
			System.out.println("SubjectAssessment details imported successfully into table SubjectAssessment.");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while importing SubjectAssessment detail into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public int retrieveStudentsAttendanceByMonth(int studentID, Integer monthNameID) {

		int presentDays = 0;

		try {

			connection = getConnection();

			String retrieveStudentsAttendanceByMonthQuery = QueryMaker.RETREIVE_daysPresent_For_Student;

			preparedStatement = connection.prepareStatement(retrieveStudentsAttendanceByMonthQuery);

			preparedStatement.setInt(1, monthNameID);
			preparedStatement.setInt(2, studentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				presentDays = resultSet.getInt("daysPresent");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return presentDays;
	}

	public LinkedHashMap<Integer, String> retrieveMonthListByTermAndStandard(String term, int academicYearID,
			int standardID) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {

			connection = getConnection();

			String retrieveMonthListByTermAndStandardQuery = QueryMaker.RETRIEVE_Working_Month_For_Attendance;

			preparedStatement = connection.prepareStatement(retrieveMonthListByTermAndStandardQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, term);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("workingMonth"));
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

	public LinkedHashMap<Integer, String> retrieveMonthListByStandard(int academicYearID, int standardID) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {

			connection = getConnection();

			String retrieveMonthListByStandardQuery = QueryMaker.RETRIEVE_Working_Month_For_Attendance1;

			preparedStatement = connection.prepareStatement(retrieveMonthListByStandardQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("workingMonth"));
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

	public HashMap<Integer, String> retrieveCoscholasticsubjectListByStandardID(int standardID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String retrieveCoscholasticsubjectListByStandardIDQuery = QueryMaker.RETRIEVE_SubjectList_By_Standard;

			preparedStatement = connection.prepareStatement(retrieveCoscholasticsubjectListByStandardIDQuery);

			String subjectList = "";
			int SubjectID = 0;
			String Subject, Subject1 = "";

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = daoInf
							.retrievesubjectNameBySubjectIDForCoScholasticSubject(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {
						continue;
					} else {
						Subject1 = Subject;
						SubjectID = Integer.parseInt(subArr[j].trim());
					}

					if (Subject1.startsWith(",")) {
						Subject1 = Subject1.substring(1);
					}

					map.put(SubjectID, Subject1);
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

	public String retrieveTotalMarksForCoscholasticSubject(int studentID, Integer subjectNameID, int ayclassID,
			int examinationID) {

		String MarksGrade = "";

		try {

			connection = getConnection();

			String retrieveTotalMarksForCoscholasticSubjectQuery = QueryMaker.RETREIVE_TotalMarks_For_Coscholastic;

			preparedStatement = connection.prepareStatement(retrieveTotalMarksForCoscholasticSubjectQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, examinationID);
			preparedStatement.setInt(3, subjectNameID);
			preparedStatement.setInt(4, ayclassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				MarksGrade = resultSet.getInt("marksObtained") + "$" + resultSet.getString("grade");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return MarksGrade;
	}

	public boolean verifyTeacherID(int academicYearID, int divisionID, int standardID, int classTeacherID) {

		boolean check = false;

		try {
			connection = getConnection();

			String verifyTeacherIDQuery = QueryMaker.VERIFY_ClassTeacheID;

			preparedStatement = connection.prepareStatement(verifyTeacherIDQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);
			preparedStatement.setInt(4, classTeacherID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying classTeacherID from table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String retreiveStudentsDateOfBirth(int studentID) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("dd/MM/yyyy");

		String DateOfBirth = "";

		try {

			connection = getConnection();

			String retreiveStudentsDateOfBirthQuery = QueryMaker.RETREIVE_Student_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retreiveStudentsDateOfBirthQuery);

			preparedStatement.setInt(1, studentID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DateOfBirth = dateToBeFormatted.format(dateFormat.parse(resultSet.getString("dateOfBirth")));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return DateOfBirth;
	}

	public String retrieveStandardByStandardID(int standardID) {

		String Standard = "";

		try {
			connection1 = getConnection();

			String retrieveStandardIDByStandardNameQuery = QueryMaker.Retrieve_Standard_Name;

			preparedStatement1 = connection1.prepareStatement(retrieveStandardIDByStandardNameQuery);

			preparedStatement1.setInt(1, standardID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				Standard = resultSet1.getString("standard");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Standard id from Standard table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}
		return Standard;
	}

	public int retrieveDivisionCountByStandardID(int nextStandardID) {

		int divCount = 0;

		try {

			connection = getConnection();

			String retrieveDivisionCountByStandardIDQuery = QueryMaker.RETREIVE_Division_Count_By_StandardID;

			preparedStatement = connection.prepareStatement(retrieveDivisionCountByStandardIDQuery);

			preparedStatement.setInt(1, nextStandardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				divCount = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return divCount;
	}

	public int retrieveDraftAyclassIDCountByStandardID(int nextStandardID, int ayID) {

		int draftAyclassIDCount = 0;

		try {

			connection = getConnection();

			String retrieveDraftAyclassIDCountByStandardIDQuery = QueryMaker.RETREIVE_Draft_AyclassID_Count_By_StandardID;

			preparedStatement = connection.prepareStatement(retrieveDraftAyclassIDCountByStandardIDQuery);

			preparedStatement.setInt(1, nextStandardID);
			preparedStatement.setInt(2, ayID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				draftAyclassIDCount = resultSet.getInt("count");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return draftAyclassIDCount;
	}

	public int CheckSubjectName(String name, String subjectType, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckSubjectNameQuery = QueryMaker.RETRIEVE_SubjectName;

			preparedStatement = connection.prepareStatement(CheckSubjectNameQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, subjectType);
			preparedStatement.setInt(3, organizationID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving subject ID from Subject table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckSubjectNameEdit(String name, String subjectType, int subjectID, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckSubjectNameEditQuery = QueryMaker.RETRIEVE_SubjectName_Edit;

			preparedStatement = connection.prepareStatement(CheckSubjectNameEditQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, subjectType);
			preparedStatement.setInt(3, organizationID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving subject ID from Subject table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckSubjectActivityName(String activity, int subjectID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckSubjectActivityNameQuery = QueryMaker.RETRIEVE_Subject_ActivityName;

			preparedStatement = connection.prepareStatement(CheckSubjectActivityNameQuery);

			preparedStatement.setString(1, activity);
			preparedStatement.setInt(2, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving activityID from Activity table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckEditSubjectActivityName(String activity, int activityID, int subjectID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckEditSubjectActivityNameQuery = QueryMaker.RETRIEVE_EditSubject_ActivityName;

			preparedStatement = connection.prepareStatement(CheckEditSubjectActivityNameQuery);

			preparedStatement.setString(1, activity);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, activityID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving activityID from Activity table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public boolean verifySubjectAvailabilityInStandard(int subjectNameID, int standardID) {

		boolean check = false;

		try {
			connection = getConnection();

			String verifySubjectAvailabilityInStandardQuery = QueryMaker.VERIFY_SubjectID;

			preparedStatement = connection.prepareStatement(verifySubjectAvailabilityInStandardQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, "%, " + subjectNameID + ",%");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while verifying SubjectID from table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public int CheckSubjectOrderEdit(String subjectType, int sortOrder, int subjectID, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckSubjectOrderEditQuery = QueryMaker.RETRIEVE_SubjectOrder_Edit;

			preparedStatement = connection.prepareStatement(CheckSubjectOrderEditQuery);

			preparedStatement.setInt(1, sortOrder);
			preparedStatement.setString(2, subjectType);
			preparedStatement.setInt(3, organizationID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving subject ID from Subject table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public int CheckSubjectOrder(String subjectType, int sortOrder, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckSubjectOrderQuery = QueryMaker.RETRIEVE_SubjectOrder;

			preparedStatement = connection.prepareStatement(CheckSubjectOrderQuery);

			preparedStatement.setInt(1, sortOrder);
			preparedStatement.setString(2, subjectType);
			preparedStatement.setInt(3, organizationID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving subject ID from Subject table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public HashMap<String, String> getCompulsoryActivitiesList(int organizationID) {

		HashMap<String, String> map = new HashMap<String, String>();
		StudentForm form = null;

		try {

			form = new StudentForm();
			connection = getConnection();

			String getCompulsoryActivitiesListQuery = QueryMaker.RETRIEVE_CreativeActivities_LIST;

			preparedStatement = connection.prepareStatement(getCompulsoryActivitiesListQuery);

			preparedStatement.setString(1, "Extra-curricular: Compulsory");

			preparedStatement.setInt(2, organizationID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("name"), resultSet.getString("name"));

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
	public String retriveCompulsoryActivities(int studentID) {

		StudentForm form = null;

		String compulsoryActivities = "";

		try {

			connection = getConnection();

			String retriveCompulsoryActivitiesQuery = QueryMaker.RETREIVE_CompulsoryActivities_LIST1;

			preparedStatement = connection.prepareStatement(retriveCompulsoryActivitiesQuery);

			preparedStatement.setInt(1, studentID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new StudentForm();

				compulsoryActivities = resultSet.getString("compulsoryActivities");

			}
			System.out.println("compulsoryActivities : " + compulsoryActivities);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving compulsoryActivities list from "
					+ "database due to:::" + exception.getMessage());

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return compulsoryActivities;
	}

	@Override
	public HashMap<Integer, String> retrieveCompulsorysubjectTypeList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveCompulsorysubjectTypeListQuery = QueryMaker.RETRIEVE_Scholastic_subject_LIST;

			preparedStatement = connection.prepareStatement(retrieveCompulsorysubjectTypeListQuery);

			preparedStatement.setString(1, "Extra-curricular: Compulsory");
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

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

}
