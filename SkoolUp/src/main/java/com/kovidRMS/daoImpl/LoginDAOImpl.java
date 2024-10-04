package com.kovidRMS.daoImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.struts2.views.jsp.ui.TokenTag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kovidRMS.util.AWSS3Connect;
import com.kovidRMS.util.ActivityStatus;
import com.kovidRMS.util.EncDescUtil;
import com.kovidRMS.util.JDBCHelper;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.kovidRMS.daoInf.LoginDAOInf;
import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LibraryForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.StudentForm;
import com.kovidRMS.util.ConfigXMLUtil;
import com.kovidRMS.util.ConfigurationUtil;
import com.kovidRMS.util.DAOConnection;
import com.kovidRMS.util.QueryMaker;

public class LoginDAOImpl extends DAOConnection implements LoginDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connection1 = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;
	String status = "error";

	static int counter = 1;

	static boolean check = false;

	static int extraMinutes = 0;

	LoginDAOInf daoInf = null;

	ConfigurationUtil configurationUtil = null;

	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	public String verifyUserCredentials(LoginForm loginform) {
		try {

			connection = getConnection();

			String verifyUserCredentialsQuery = QueryMaker.RETRIEVE_USER_CREDENTIALS;

			preparedStatement = connection.prepareStatement(verifyUserCredentialsQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				/*
				 * decrypt the password from database and store it in one string variable
				 */
				String decryptedPassword = null;
				decryptedPassword = EncDescUtil.DecryptText(resultSet.getString("password").trim());

				System.out.println("Decrypted password is::::::" + decryptedPassword);

				System.out
						.println("User credentials are:::::" + loginform.getUsername() + " " + loginform.getPassword());

				if (((resultSet.getString("username")).equals(loginform.getUsername()))
						&& (decryptedPassword.equals(loginform.getPassword()))) {
					System.out.println("Credetials Matched.....");
					status = "success";
					return status;
				} else {
					status = "error";
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while checking user credentials while login due to:::" + exception.getMessage());
			status = "exception";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#insertUser(com.edhanvantari
	 * .form.RegistrationForm)
	 */
	public String insertUser(LoginForm loginform, int organizationID) {

		try {
			connection = getConnection();

			// username, password, userType, activityStatus, specialization,
			// defaultClinicID, pin, practiceID

			String insertUserQuery = QueryMaker.INSERT_USER_DETAIL;

			preparedStatement = connection.prepareStatement(insertUserQuery);

			preparedStatement.setString(1, loginform.getUsername());

			/*
			 * Encrypt the password before inserting into database
			 */
			String ecryptedPassword = EncDescUtil.EncryptText(loginform.getPassword());

			/*
			 * Encrypt PIN before inserting into User table
			 */
			// String encryptedPIN = EncDescUtil.EncryptText(loginform.getPIN());

			preparedStatement.setString(2, ecryptedPassword);
			preparedStatement.setString(3, loginform.getFirstName());
			preparedStatement.setString(4, loginform.getMiddleName());
			preparedStatement.setString(5, loginform.getLastName());
			preparedStatement.setString(6, loginform.getEmailId());
			preparedStatement.setString(7, loginform.getMobile());
			preparedStatement.setString(8, loginform.getAddress());
			preparedStatement.setString(9, loginform.getPhone());
			preparedStatement.setString(10, loginform.getCity());
			preparedStatement.setString(11, loginform.getState());
			preparedStatement.setString(12, loginform.getCountry());
			preparedStatement.setInt(13, loginform.getPinCode());
			preparedStatement.setString(14, loginform.getRole());
			preparedStatement.setString(15, ActivityStatus.ACTIVE);
			preparedStatement.setString(16, loginform.getProfilePicDBName());
			preparedStatement.setString(17, loginform.getSignatureDBName());
			preparedStatement.setInt(18, loginform.getLibraryID());
			preparedStatement.setInt(19, organizationID);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting user detail into table due to:::" + exception.getMessage());
			status = "input";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String insertPasswordHistory(int userID, String password) {

		/*
		 * Encrypting password
		 */
		String encryptedPass = EncDescUtil.EncryptText(password);
		try {

			connection = getConnection();

			String insertPasswordHistoryQuery = QueryMaker.INSERT_PASSWORD_HISTORY;

			preparedStatement = connection.prepareStatement(insertPasswordHistoryQuery);

			preparedStatement.setString(1, encryptedPass);
			preparedStatement.setInt(2, userID);

			preparedStatement.execute();

			System.out.println("Successfully insertd password into PasswordHistory table");

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

	public boolean verifyPasswordHistory(int userID, String password) {

		// Encrypting password
		String encryptedPass = EncDescUtil.EncryptText(password);

		boolean result = false;

		try {

			connection = getConnection();

			String verifyPasswordHistoryQuery = QueryMaker.VERIFY_PASSWORD_HISTORY;

			preparedStatement = connection.prepareStatement(verifyPasswordHistoryQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, encryptedPass);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			result = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#verifyUsername(java.lang.
	 * String )
	 */
	public int verifyUsername(String username, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String verifyUsernameQuery = QueryMaker.VERIFY_USERNAME;

			preparedStatement = connection.prepareStatement(verifyUsernameQuery);

			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving user ID from User table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retrieveUserIDByUsername(java
	 * .lang.String)
	 */
	public int retrieveUserIDByUsername(String username, int organizationID) {
		int userId = 0;

		try {
			connection = getConnection();

			String retrieveUserIDByUsernameQuery = QueryMaker.RETRIEVE_USERID;

			preparedStatement = connection.prepareStatement(retrieveUserIDByUsernameQuery);

			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userId = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving user ID from User table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return userId;
	}

	public List<LoginForm> searchUser(String searchUserName, int organizationID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;

		try {

			connection = getConnection();

			String searchUserQuery = QueryMaker.SEARCH_USER_BY_USER_NAME;

			preparedStatement = connection.prepareStatement(searchUserQuery);

			if (searchUserName.contains(" ")) {
				searchUserName = searchUserName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchUserName + "%");

			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LoginForm();

				String fullName = null;

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					fullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");

				} else {

					fullName = resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " "
							+ resultSet.getString("lastName");

				}

				form.setUserID(resultSet.getInt("id"));
				form.setFullName(fullName);
				form.setUsername(resultSet.getString("username"));
				form.setRole(resultSet.getString("role"));
				form.setActivityStatus(resultSet.getString("activityStatus"));
				form.setSearchUserName(searchUserName);

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

	public List<LoginForm> retriveEditUSerList(int organizationID) {

		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm registrationForm = null;

		try {

			connection = getConnection();

			String retriveEditUSerListQuery = QueryMaker.RETREIVE_EDIT_USER_LIST;

			preparedStatement = connection.prepareStatement(retriveEditUSerListQuery);
			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				registrationForm = new LoginForm();

				registrationForm.setUserID(resultSet.getInt("id"));

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					registrationForm
							.setFullName(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
				} else {

					registrationForm.setFullName(resultSet.getString("firstName") + " "
							+ resultSet.getString("middleName") + " " + resultSet.getString("lastName"));

				}
				registrationForm.setUsername(resultSet.getString("username"));

				registrationForm.setRole(resultSet.getString("role"));

				registrationForm.setActivityStatus(resultSet.getString("activityStatus"));

				list.add(registrationForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving edit user list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public List<LoginForm> retreiveUserDetailByUserID(int userID) {

		List<LoginForm> userList = new ArrayList<LoginForm>();
		LoginForm form = new LoginForm();

		try {
			connection = getConnection();

			String retreiveUserDetailByUserIDQuery = QueryMaker.RETREIVE_USER_BY_USER_ID;

			preparedStatement = connection.prepareStatement(retreiveUserDetailByUserIDQuery);
			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form.setUserID(resultSet.getInt("id"));
				form.setUsername(resultSet.getString("username"));
				String decryPass = EncDescUtil.DecryptText(resultSet.getString("password"));
				form.setPassword(decryPass);
				form.setActivityStatus(resultSet.getString("activityStatus"));
				form.setFirstName(resultSet.getString("firstName"));
				form.setMiddleName(resultSet.getString("middleName"));
				form.setLastName(resultSet.getString("lastName"));
				form.setAddress(resultSet.getString("address"));
				form.setCity(resultSet.getString("city"));
				form.setState(resultSet.getString("state"));
				form.setCountry(resultSet.getString("country"));
				form.setMobile(resultSet.getString("mobile"));
				form.setPhone(resultSet.getString("phone"));
				form.setEmailId(resultSet.getString("emailId"));
				form.setRole(resultSet.getString("role"));
				form.setPinCode(resultSet.getInt("pinCode"));
				form.setProfilePicDBName(resultSet.getString("profilePic"));
				form.setSignatureDBName(resultSet.getString("signImage"));
				form.setLibraryID(resultSet.getInt("libraryID"));
				userList.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retreiving user detail based on user id from database due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return userList;
	}

	public boolean verifyUsernameWithUserID(String username, int userID, int organizationID) {

		boolean status = true;

		try {
			connection = getConnection();

			String verifyUsernameWithUserIDQuery = QueryMaker.VERIFY_USERNAME_WITH_USER_ID;

			preparedStatement = connection.prepareStatement(verifyUsernameWithUserIDQuery);

			preparedStatement.setString(1, username);
			preparedStatement.setInt(3, userID);
			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = false;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving user ID from User table due to:::" + exception.getMessage());

			status = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public boolean verifyPassword(int userID, String password) {

		boolean result = false;
		// Encrypting password
		String encryptedPass = EncDescUtil.EncryptText(password);

		try {

			connection = getConnection();

			String verifyPasswordQuery = QueryMaker.VERIFY_PASSWORD;

			preparedStatement = connection.prepareStatement(verifyPasswordQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, encryptedPass);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			result = false;
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return result;
	}

	public String updateUserDetail(LoginForm form) {
		try {
			connection = getConnection();

			String updateUserDetailQuery = QueryMaker.UPDATE_USER_DETAIL;

			preparedStatement = connection.prepareStatement(updateUserDetailQuery);

			/*
			 * Encrypt the password before inserting into database
			 */
			String ecryptedPassword = EncDescUtil.EncryptText(form.getPassword());

			preparedStatement.setString(1, ecryptedPassword);
			preparedStatement.setString(2, form.getFirstName());
			preparedStatement.setString(3, form.getMiddleName());
			preparedStatement.setString(4, form.getLastName());
			preparedStatement.setString(5, form.getAddress());
			preparedStatement.setString(6, form.getCity());
			preparedStatement.setString(7, form.getState());
			preparedStatement.setString(8, form.getCountry());
			preparedStatement.setString(9, form.getPhone());
			preparedStatement.setString(10, form.getMobile());
			preparedStatement.setString(11, form.getEmailId());
			preparedStatement.setString(12, form.getRole());
			preparedStatement.setInt(13, form.getPinCode());
			preparedStatement.setString(14, form.getProfilePicDBName());
			preparedStatement.setString(15, form.getUsername());
			preparedStatement.setString(16, form.getSignatureDBName());
			preparedStatement.setInt(17, form.getLibraryID());
			preparedStatement.setInt(18, form.getUserID());

			preparedStatement.execute();

			System.out.println("User detail updated successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail into database due to:::" + exception.getMessage());
			status = "input";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String rejectUser(LoginForm form) {

		try {
			connection = getConnection();

			String rejectUserQuery = QueryMaker.REJECT_USER;

			preparedStatement = connection.prepareStatement(rejectUserQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, form.getUserID());

			preparedStatement.execute();

			System.out.println("User detail updated (For disable user) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail for disabling activity status of user from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public LoginForm getUserDetail(String username) {
		LoginForm form = new LoginForm();
		try {
			connection = getConnection();

			String getUserDetailQuery = QueryMaker.RETRIEVE_USER_DETAILS;

			preparedStatement = connection.prepareStatement(getUserDetailQuery);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setUserID(resultSet.getInt("id"));
				form.setFirstName(resultSet.getString("firstName"));
				form.setLastName(resultSet.getString("lastName"));
				form.setMiddleName(resultSet.getString("middleName"));

				if (resultSet.getString("middleName") == null || resultSet.getString("middleName") == "") {

					String fullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");

					form.setFullName(fullName);

				} else {

					String fullName = resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " "
							+ resultSet.getString("lastName");

					form.setFullName(fullName);
				}

				form.setRole(resultSet.getString("role"));
				form.setMedium(resultSet.getString("medium"));
				form.setBoard(resultSet.getString("board"));
				form.setOrganizationID(resultSet.getInt("organizationID"));
				form.setLibraryID(resultSet.getInt("libraryID"));
				form.setAcademicYearID(resultSet.getInt("academicYearID"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Logged in user details from User due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return form;
	}

	public String insertAudit(String ipAddress, String activity, int userID) {
		try {
			connection = getConnection();

			String insertAuditQuery = QueryMaker.INSERT_LOGIN_AUDIT;

			preparedStatement = connection.prepareStatement(insertAuditQuery);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, activity);
			preparedStatement.setString(3, ipAddress);

			preparedStatement.execute();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting audit detail into Audit table due to:::"
					+ exception.getMessage());
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public List<LoginForm> searchOrganizationList(String searchOrganization) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		try {
			connection = getConnection();

			String searchOrganizationListQuery = QueryMaker.SEARCH_Organization_LIST;

			preparedStatement = connection.prepareStatement(searchOrganizationListQuery);

			if (searchOrganization.contains(" ")) {
				searchOrganization = searchOrganization.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchOrganization + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setOrganizationsID(resultSet.getInt("id"));
				form.setName((resultSet.getString("name")));
				form.setSearchOrganization(searchOrganization);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching Organization list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveExistingOrganizationList() {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		try {
			connection = getConnection();

			String retrieveExistingOrganizationListQuery = QueryMaker.RETRIEVE_Organization_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingOrganizationListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setOrganizationsID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Organization list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveOrganizationListByID(int organizationsID, String searchOrganization) {
		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm form = new LoginForm();

		try {

			connection = getConnection();

			String retrieveOrganizationListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationListByIDQuery);

			preparedStatement.setInt(1, organizationsID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setOrganizationsID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setEmail(resultSet.getString("email"));
				form.setEmailPass(resultSet.getString("emailPass"));
				form.setAddress(resultSet.getString("address"));
				form.setPhone(resultSet.getString("phone"));
				form.setWebsite(resultSet.getString("website"));
				form.setTagline(resultSet.getString("tagline"));
				form.setLogoDBName(resultSet.getString("logo"));
				form.setBoardLogoDBName(resultSet.getString("boardLogo"));
				form.setSignatureDBName(resultSet.getString("signImage"));
				form.setSealDBName(resultSet.getString("sealImage"));
				form.setBoard(resultSet.getString("board"));
				form.setMedium(resultSet.getString("medium"));
				form.setSignOnRC(resultSet.getString("signOnRC"));
				form.setSignatureDBPrimary(resultSet.getString("primaryHeadSign"));
				form.setSealDBPrimary(resultSet.getString("primaryHeadSeal"));
				form.setSignatureDBSecondary(resultSet.getString("secondaryHeadSign"));
				form.setSealDBSecondary(resultSet.getString("secondaryHeadSeal"));

				form.setSearchOrganization(searchOrganization);
			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Organization list from Organization ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertOrganization(LoginForm form) {

		try {
			connection = getConnection();

			String insertOrganizationQuery = QueryMaker.INSERT_Organization;

			preparedStatement = connection.prepareStatement(insertOrganizationQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getLogoDBName());
			preparedStatement.setString(3, form.getBoardLogoDBName());
			preparedStatement.setString(4, form.getAddress());
			preparedStatement.setString(5, form.getPhone());
			preparedStatement.setString(6, form.getEmail());
			preparedStatement.setString(7, form.getWebsite());
			preparedStatement.setString(8, form.getTagline());

			preparedStatement.setString(9, form.getEmailPass());

			preparedStatement.setString(10, form.getSignatureDBName());

			preparedStatement.setString(11, form.getSealDBName());

			preparedStatement.setString(12, form.getBoard());

			preparedStatement.setString(13, form.getMedium());
			preparedStatement.setString(14, form.getSignatureDBPrimary());
			preparedStatement.setString(15, form.getSealDBPrimary());
			preparedStatement.setString(16, form.getSignatureDBSecondary());
			preparedStatement.setString(17, form.getSealDBSecondary());
			preparedStatement.setString(18, form.getSignOnRC());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted organization into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting organization into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateOrganization(LoginForm form) {

		try {
			connection = getConnection();

			String updateOrganizationQuery = QueryMaker.UPDATE_CONFIGURATION_Organization;

			preparedStatement = connection.prepareStatement(updateOrganizationQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getLogoDBName());
			preparedStatement.setString(3, form.getBoardLogoDBName());
			preparedStatement.setString(4, form.getAddress());
			preparedStatement.setString(5, form.getPhone());
			preparedStatement.setString(6, form.getEmail());
			preparedStatement.setString(7, form.getWebsite());
			preparedStatement.setString(8, form.getTagline());
			/*
			 * Encrypt the password before inserting into database
			 */
			// String ecryptedPassword = EncDescUtil.EncryptText(form.getEmailPass());

			preparedStatement.setString(9, form.getEmailPass());

			preparedStatement.setString(10, form.getSignatureDBName());
			preparedStatement.setString(11, form.getSealDBName());
			preparedStatement.setString(12, form.getBoard());
			preparedStatement.setString(13, form.getMedium());
			preparedStatement.setString(14, form.getSignatureDBPrimary());
			preparedStatement.setString(15, form.getSealDBPrimary());
			preparedStatement.setString(16, form.getSignatureDBSecondary());
			preparedStatement.setString(17, form.getSealDBSecondary());
			preparedStatement.setString(18, form.getSignOnRC());
			preparedStatement.setInt(19, form.getOrganizationsID());

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

	public List<LoginForm> searchAcademicYearList(String searchAcademicYear, int organizationsID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		try {
			connection = getConnection();

			String searchAcademicYearListQuery = QueryMaker.SEARCH_AcademicYear_LIST;

			preparedStatement = connection.prepareStatement(searchAcademicYearListQuery);

			if (searchAcademicYear.contains(" ")) {
				searchAcademicYear = searchAcademicYear.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchAcademicYear + "%");

			preparedStatement.setInt(2, organizationsID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setAcademicYearID(resultSet.getInt("id"));
				form.setYearName(resultSet.getString("yearName"));
				form.setActivityStatus(resultSet.getString("activityStatus"));
				form.setSearchAcademicYear(searchAcademicYear);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching Academic Year list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveExistingAcademicYearList(int organizationsID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		try {
			connection = getConnection();

			String retrieveExistingAcademicYearListQuery = QueryMaker.RETRIEVE_AcademicYear_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingAcademicYearListQuery);

			preparedStatement.setInt(1, organizationsID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setAcademicYearID(resultSet.getInt("id"));
				form.setYearName(resultSet.getString("yearName"));
				form.setActivityStatus(resultSet.getString("activityStatus"));
				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Academic Year list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveAcademicYearListByID(int AcademicYearID, String searchAcademicYear) {
		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm form = new LoginForm();

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {

			connection = getConnection();

			String retrieveAcademicYearListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_AcademicYear_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearListByIDQuery);

			preparedStatement.setInt(1, AcademicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setAcademicYearID(resultSet.getInt("id"));
				form.setYearName(resultSet.getString("yearName"));

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

				// Inserting Term I End Date
				if (resultSet.getString("termIendDate") == null || resultSet.getString("termIendDate") == "") {

					// form.setTermIendDate(null);
					form.setTermIendDate("");

				} else if (resultSet.getString("termIendDate").isEmpty()) {
					form.setTermIendDate("");

				} else {

					form.setTermIendDate(
							dateFormat.format(dateToBeFormatted.parse(resultSet.getString("termIendDate"))));
				}

				form.setSearchAcademicYear(searchAcademicYear);

				if (resultSet.getString("ageFromDate") == null || resultSet.getString("ageFromDate") == "") {

					// form.setTermIendDate(null);
					form.setAgeFromDate("");

				} else if (resultSet.getString("ageFromDate").isEmpty()) {
					form.setAgeFromDate("");

				} else {

					form.setAgeFromDate(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("ageFromDate"))));
				}

				form.setOrganizationID(resultSet.getInt("organizationID"));

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Academic Year list from AcademicYear ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertAcademicYear(LoginForm form, int organizationID, String status) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String insertAcademicYearQuery = QueryMaker.INSERT_AcademicYear;

			preparedStatement = connection.prepareStatement(insertAcademicYearQuery);

			preparedStatement.setString(1, form.getYearName());

			// Inserting Start Date
			if (form.getStartDate() == null || form.getStartDate() == "" || form.getStartDate().isEmpty()) {

				form.setStartDate(null);
				preparedStatement.setString(2, form.getStartDate());

			} else {
				preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(form.getStartDate())));
			}

			// Inserting End Date
			if (form.getEndDate() == null || form.getEndDate() == "" || form.getEndDate().isEmpty()) {

				form.setEndDate(null);
				preparedStatement.setString(3, form.getEndDate());

			} else {
				preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(form.getEndDate())));
			}

			if (form.getTermIendDate() == null || form.getTermIendDate() == "" || form.getTermIendDate().isEmpty()) {

				form.setTermIendDate(null);
				preparedStatement.setString(4, form.getTermIendDate());

			} else {
				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(form.getTermIendDate())));
			}

			preparedStatement.setInt(5, organizationID);

			preparedStatement.setString(6, status);

			preparedStatement.setString(7, dateToBeFormatted.format(dateFormat.parse(form.getAgeFromDate())));

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted academic year into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting academic year into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateAcademicYear(LoginForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String updateAcademicYearQuery = QueryMaker.UPDATE_CONFIGURATION_AcademicYear;

			preparedStatement = connection.prepareStatement(updateAcademicYearQuery);
			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setString(1, form.getYearName());

			// updating Start Date
			if (form.getStartDate() == null || form.getStartDate() == "" || form.getStartDate().isEmpty()) {

				form.setStartDate(null);
				preparedStatement.setString(2, form.getStartDate());

			} else {
				preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(form.getStartDate())));
			}

			// updating End Date
			if (form.getEndDate() == null || form.getEndDate() == "" || form.getEndDate().isEmpty()) {

				form.setEndDate(null);
				preparedStatement.setString(3, form.getEndDate());

			} else {
				preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(form.getEndDate())));
			}

			// updating Term I End Date
			if (form.getTermIendDate() == null || form.getTermIendDate() == "" || form.getTermIendDate().isEmpty()) {

				form.setTermIendDate(null);
				preparedStatement.setString(4, form.getTermIendDate());

			} else {
				preparedStatement.setString(4, dateToBeFormatted.format(dateFormat.parse(form.getTermIendDate())));
			}

			preparedStatement.setString(5, dateToBeFormatted.format(dateFormat.parse(form.getAgeFromDate())));

			preparedStatement.setInt(6, form.getAcademicYearID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated academic year into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating AcademicYear into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public HashMap<Integer, String> getOrganization() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getOrganizationListQuery = QueryMaker.RETRIEVE_Organizations_LIST;

			preparedStatement = connection.prepareStatement(getOrganizationListQuery);

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

	public List<LoginForm> searchStandardList(String searchStandard, int organizationID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		LoginDAOInf daoInf = new LoginDAOImpl();

		try {
			connection = getConnection();

			String searchStandardListQuery = QueryMaker.SEARCH_Standard_LIST;

			preparedStatement = connection.prepareStatement(searchStandardListQuery);

			if (searchStandard.contains(" ")) {
				searchStandard = searchStandard.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchStandard + "%");

			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();
				form.setStandardID(resultSet.getInt("id"));
				form.setStandard((resultSet.getString("standard")));

				String Subject = "";

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = Subject + ',' + daoInf.retrievesubjectNameBySubjectID(Integer.parseInt(subArr[j].trim()));

					if (Subject.startsWith(",")) {
						Subject = Subject.substring(1);
					}
					form.setSubjectList(Subject);

				}

				form.setSearchStandard(searchStandard);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Standard list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveExistingStandardList(int organizationID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		LoginDAOInf daoInf = new LoginDAOImpl();

		try {
			connection = getConnection();

			String retrieveExistingStandardListQuery = QueryMaker.RETRIEVE_Standard_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingStandardListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setStandardID(resultSet.getInt("id"));
				form.setStandard(resultSet.getString("standard"));

				String Subject = "";
				// LoginDAOInf daoInf = new LoginDAOImpl();

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = Subject + ',' + daoInf.retrievesubjectNameBySubjectID(Integer.parseInt(subArr[j].trim()));

					if (Subject.startsWith(",")) {
						Subject = Subject.substring(1);
					}
					form.setSubjectList(Subject);
				}

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Standard list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveStandardListByID(int StandardID, String searchStandard) {
		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm form = new LoginForm();

		try {

			connection = getConnection();

			String retrieveStandardListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Standard_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveStandardListByIDQuery);

			preparedStatement.setInt(1, StandardID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setStandardID(resultSet.getInt("id"));

				form.setStandard(resultSet.getString("standard"));

				form.setStage(resultSet.getString("stage"));

				List<Integer> OriginalSubjectList = new ArrayList<Integer>();

				String Subject = resultSet.getString("subjectList");

				String subArr[] = Subject.split(",");

				for (int j = 0; j < subArr.length; j++) {

					OriginalSubjectList.add(Integer.parseInt(subArr[j].trim()));
					System.out.println("OriginalSubjectList :" + OriginalSubjectList);
				}
				form.setSubjectListValues(OriginalSubjectList);

				// form.setSubjectList(resultSet.getString("subjectList"));
				form.setSearchStandard(searchStandard);

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Standard list from Standard ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertStandard(LoginForm form, int organizationID) {
		try {
			connection = getConnection();

			String insertStandardQuery = QueryMaker.INSERT_Standard;

			preparedStatement = connection.prepareStatement(insertStandardQuery);

			preparedStatement.setString(1, form.getStandard());
			preparedStatement.setString(2, form.getSubjectList());
			preparedStatement.setString(3, form.getStage());
			preparedStatement.setInt(4, organizationID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);
			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Standard into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Standard into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateStandard(LoginForm form) {
		try {
			connection = getConnection();

			String updateStandardQuery = QueryMaker.UPDATE_CONFIGURATION_Standard;

			preparedStatement = connection.prepareStatement(updateStandardQuery);

			preparedStatement.setString(1, form.getStandard());
			preparedStatement.setString(2, form.getStage());
			preparedStatement.setString(3, form.getSubjectList());
			preparedStatement.setInt(4, form.getStandardID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated Standard into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating Standard into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public HashMap<Integer, String> getStandard(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getStandardListQuery = QueryMaker.RETRIEVE_Standard_LIST;

			preparedStatement = connection.prepareStatement(getStandardListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("standard"));
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

	public List<LoginForm> searchDivisionList(String searchDivision, int organizationID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		try {
			connection = getConnection();

			String searchDivisionListQuery = QueryMaker.SEARCH_Division_LIST;

			preparedStatement = connection.prepareStatement(searchDivisionListQuery);

			preparedStatement.setInt(1, organizationID);

			if (searchDivision.contains(" ")) {
				searchDivision = searchDivision.replace(" ", "%");
			}

			preparedStatement.setString(2, "%" + searchDivision + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setDivisionID(resultSet.getInt("id"));
				form.setDivision((resultSet.getString("division")));
				form.setStandardID((resultSet.getInt("standardID")));
				form.setStandard(resultSet.getString("standard"));
				form.setSearchDivision(searchDivision);

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching Division list from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveExistingDivisionList(int organizationID) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;
		try {
			connection = getConnection();

			String retrieveExistingDivisionListQuery = QueryMaker.RETRIEVE_Division_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingDivisionListQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new LoginForm();

				form.setDivisionID(resultSet.getInt("id"));
				form.setDivision((resultSet.getString("division")));
				form.setStandardID((resultSet.getInt("standardID")));
				form.setStandard(resultSet.getString("standard"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Division list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	public List<LoginForm> retrieveDivisionListByID(int DivisionID, String searchDivision) {
		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm form = new LoginForm();

		try {

			connection = getConnection();

			String retrieveDivisionListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Division_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveDivisionListByIDQuery);

			preparedStatement.setInt(1, DivisionID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setDivisionID(resultSet.getInt("id"));
				form.setDivision(resultSet.getString("division"));

				form.setSearchDivision(searchDivision);

				form.setStandardID(resultSet.getInt("standardID"));

			}
			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving divisionID list from division ID due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public String insertDivision(LoginForm form) {

		try {
			connection = getConnection();

			String insertDivisionQuery = QueryMaker.INSERT_Division;

			preparedStatement = connection.prepareStatement(insertDivisionQuery);

			preparedStatement.setString(1, form.getDivision());

			preparedStatement.setInt(2, form.getStandardID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Division into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Division into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String updateDivision(LoginForm form) {

		try {
			connection = getConnection();

			String updateDivisionQuery = QueryMaker.UPDATE_CONFIGURATION_Division;

			preparedStatement = connection.prepareStatement(updateDivisionQuery);

			preparedStatement.setString(1, form.getDivision());

			preparedStatement.setInt(2, form.getStandardID());

			preparedStatement.setInt(3, form.getDivisionID());

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated Division into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating Division into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public HashMap<Integer, String> getDivision(int standardID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getDivisionListQuery = QueryMaker.RETRIEVE_Divisions_LIST;

			preparedStatement = connection.prepareStatement(getDivisionListQuery);

			preparedStatement.setInt(1, standardID);

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

	public HashMap<Integer, String> getAppUser(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getAppUserListQuery = QueryMaker.RETRIEVE_AppUser_LIST;

			preparedStatement = connection.prepareStatement(getAppUserListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LoginForm form = new LoginForm();

				form.setUserID(resultSet.getInt("id"));

				String fullName = "";

				fullName = resultSet.getString("lastName") + " " + resultSet.getString("firstName");

				map.put(resultSet.getInt("id"), fullName);

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

	public HashMap<Integer, String> getSubjectList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getSubjectListQuery = QueryMaker.RETRIEVE_SubjectList_By_StandardID;

			preparedStatement = connection.prepareStatement(getSubjectListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LoginForm form = new LoginForm();

				map.put(resultSet.getInt("id"), resultSet.getString("subjectList"));

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

	public int UpdateAcademicYearStatus(String activityStatus) {
		int check = 0;
		try {
			connection = getConnection();

			String UpdateAcademicYearStatusQuery = QueryMaker.UPDATE_Academic_Year_Status;

			preparedStatement = connection.prepareStatement(UpdateAcademicYearStatusQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			preparedStatement.execute();

			check = 1;

			status = "success";

			System.out.println("Successfully udpated Academic Year Status into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating AcademicYearStatus into table due to:::"
					+ exception.getMessage());
			status = "error";

			check = 0;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return check;

	}

	public String retrieveAcademicYearName(int organizationID) {
		String name = "";
		try {
			connection = getConnection();

			String retrieveAcademicYearNameQuery = QueryMaker.RETRIEVE_AcademicYear_Name;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearNameQuery);
			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				name = resultSet.getString("yearName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Academic Year list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return name;
	}

	public String retrieveAcademicYearName1() {
		String name = "";
		try {
			connection = getConnection();

			String retrieveINACTIVEAcademicYearNameQuery = QueryMaker.RETRIEVE_INACTIVE_AcademicYear_Name;

			preparedStatement = connection.prepareStatement(retrieveINACTIVEAcademicYearNameQuery);

			preparedStatement.setString(1, ActivityStatus.DRAFT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				name = resultSet.getString("yearName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving existing INACTIVE Academic Year list from table due to:::"
							+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return name;
	}

	public String rejectStandard(LoginForm form) {

		try {
			connection = getConnection();

			String rejectStandardQuery = QueryMaker.REJECT_Standard;

			preparedStatement = connection.prepareStatement(rejectStandardQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, form.getStandardID());

			preparedStatement.execute();

			System.out.println("Standard detail updated (For disable Standard) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Standard detail for disabling activity status of Standard from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int retriveactiveteacherID() {
		int teacherID = 0;

		LoginForm form = new LoginForm();

		try {
			connection = getConnection();

			String retriveactiveteacherIDQuery = QueryMaker.RETRIEVE_teacher_ID_By_teacherName;

			preparedStatement = connection.prepareStatement(retriveactiveteacherIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				teacherID = resultSet.getInt("id");
			}

			System.out.println("TeacherID = " + teacherID);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing TeacherID from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return teacherID;
	}

	public String retrievesubjectListbyStandardID(int standardID) {

		LoginForm form = null;

		String Subject = "";

		try {

			connection = getConnection();

			String retriveCreativeActivitiesQuery = QueryMaker.RETREIVE_SubjectList_LIST;

			preparedStatement = connection.prepareStatement(retriveCreativeActivitiesQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LoginForm();

				List<Integer> OriginalSubjectList = new ArrayList<Integer>();

				Subject = resultSet.getString("subjectList");

				String subArr[] = Subject.split(",");

				for (int j = 0; j < subArr.length; j++) {

					OriginalSubjectList.add(Integer.parseInt(subArr[j]));

				}
				form.setSubjectListValues(OriginalSubjectList);
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
		return Subject;
	}

	public String insertAttendance(String attendanceEvent, int academicYearID) {

		try {

			connection = getConnection();

			String insertAttendanceQuery = QueryMaker.INSERT_Attendance;

			preparedStatement = connection.prepareStatement(insertAttendanceQuery);

			String[] attendanceEventArray = attendanceEvent.split("\\$");

			preparedStatement.setInt(1, Integer.parseInt(attendanceEventArray[1]));

			preparedStatement.setString(2, attendanceEventArray[0]);

			preparedStatement.setString(3, attendanceEventArray[2]);

			preparedStatement.setString(4, attendanceEventArray[3]);

			preparedStatement.setInt(5, academicYearID);

			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Attendance detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Attendance into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

	}

	public int retrieveAcademicYearID(int organizationID) {
		int academicYearID = 0;

		try {
			connection = getConnection();

			String retrieveAcademicYearIDQuery = QueryMaker.RETRIEVE_AcademicYearID;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearIDQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				academicYearID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving AcademicYear ID from User table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return academicYearID;
	}

	public List<LoginForm> retrieveattendanceList(int academicYearID, String term) {

		List<LoginForm> list = new ArrayList<LoginForm>();

		LoginForm form = null;

		try {
			connection = getConnection();

			String retrieveattendanceListQuery = QueryMaker.RETRIEVE_EXISTING_Attendance_LIST;

			preparedStatement = connection.prepareStatement(retrieveattendanceListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, term);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LoginForm();

				form.setAttendanceID(resultSet.getInt("id"));

				form.setEditTerm(resultSet.getString("term"));

				form.setStandard(resultSet.getString("standard"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setMonth(resultSet.getString("workingMonth"));

				form.setWorkingDays(resultSet.getInt("workingDays"));

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

	public String updateAttendance(int editWorkdays, int attendanceID) {

		try {
			connection = getConnection();

			String updateAttendanceQuery = QueryMaker.UPDATE_Attendance;

			preparedStatement = connection.prepareStatement(updateAttendanceQuery);

			preparedStatement.setInt(1, editWorkdays);

			preparedStatement.setInt(2, attendanceID);

			preparedStatement.execute();

			status = "success";

			// System.out.println("Successfully udpated Attendance year into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Attendance into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public JSONObject deleteAttendanceRow(int deleteID, String active) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteRowQuery = QueryMaker.DELETE_AttendanceRow;

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

	public String retrieveStandardName(int userID, int academicYearID) {

		String StandardName = "";

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StandardName = resultSet.getString("standardName");
			}
			// System.out.println("StandardName"+StandardName);

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return StandardName;
	}

	public int retrieveStandardID(int userID, int academicYearID) {

		int StandardID = 0;

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StandardID = resultSet.getInt("standardID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return StandardID;
	}

	public String retrieveDivisionName(int userID, int academicYearID) {

		String DivisionName = "";

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DivisionName = resultSet.getString("divisionName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return DivisionName;
	}

	public int retrieveDivisionID(int userID, int academicYearID) {

		int DivisionID = 0;

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DivisionID = resultSet.getInt("divisionID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return DivisionID;
	}

	public String retrieveProfilePic(int userID) {
		String profilePicName = null;

		try {

			connection = getConnection();

			String retrieveProfilePicQuery = QueryMaker.RETRIEVE_USER_PROFILE_PIC_FILE_NAME;

			preparedStatement = connection.prepareStatement(retrieveProfilePicQuery);

			preparedStatement.setInt(1, userID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				profilePicName = resultSet.getString("profilePic");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving profile pic name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return profilePicName;
	}

	public String retrievesubjectNameBySubjectID(int subjectID) {

		String SubjectName = null;

		try {

			connection = getConnection();

			String retrievesubjectNameBySubjectIDQuery = QueryMaker.RETRIEVE_Subject_Name_By_SubjectID;

			preparedStatement = connection.prepareStatement(retrievesubjectNameBySubjectIDQuery);

			preparedStatement.setInt(1, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectName = resultSet.getString("name");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectName;
	}

	public String retrieveBoardLogo(int organizationID) {
		String BoardLogo = null;

		try {

			connection = getConnection();

			String retrieveBoardLogoQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveBoardLogoQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BoardLogo = resultSet.getString("boardLogo");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Board Logo due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return BoardLogo;
	}

	public String retrieveOrganizationLogo(int organizationID) {
		String OrganizationLogo = null;

		try {

			connection = getConnection();

			String retrieveOrganizationLogoQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationLogoQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationLogo = resultSet.getString("logo");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Logo due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationLogo;
	}

	public String retrieveOrganizationName(int organizationID) {
		String OrganizationName = null;

		try {

			connection = getConnection();

			String retrieveOrganizationNameQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationNameQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationName = resultSet.getString("name");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationName;
	}

	public String retrieveOrganizationAddress(int organizationID) {
		String OrganizationAddress = null;

		try {

			connection = getConnection();

			String retrieveOrganizationAddressQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationAddressQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationAddress = resultSet.getString("address");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Organization Address due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationAddress;
	}

	public String retrieveOrganizationPhone(int organizationID) {
		String OrganizationPhone = null;

		try {

			connection = getConnection();

			String retrieveOrganizationPhoneQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationPhoneQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationPhone = resultSet.getString("phone");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Organization Phone due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationPhone;
	}

	public String retrieveOrganizationTagLine(int organizationID) {
		String OrganizationTagLine = null;

		try {

			connection = getConnection();

			String retrieveOrganizationTagLineQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationTagLineQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationTagLine = resultSet.getString("tagline");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Organization TagLine due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationTagLine;
	}

	public String retrievesubjectNameBySubjectIDSubjectType(int subjectID) {

		String SubjectName = null;

		try {

			connection1 = getConnection();

			String retrievesubjectNameBySubjectIDSubjectTypeQuery = QueryMaker.RETRIEVE_Subject_Name_By_SubjectID_SubjectType;

			preparedStatement1 = connection1.prepareStatement(retrievesubjectNameBySubjectIDSubjectTypeQuery);

			preparedStatement1.setInt(1, subjectID);
			preparedStatement1.setString(2, "Scholastic");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				SubjectName = resultSet1.getString("name");
			}

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return SubjectName;
	}

	public int retrieveSubjectIDBySubjectType(int subjectID) {

		int SubjectID = 0;

		try {

			connection = getConnection();

			String retrieveSubjectIDBySubjectTypeQuery = QueryMaker.RETRIEVE_SubjectID_By_SubjectType;

			preparedStatement = connection.prepareStatement(retrieveSubjectIDBySubjectTypeQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, "Scholastic");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectID = resultSet.getInt("id");

			}

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectID;
	}

	public String insertAttendanceNew(String term, String month, int workingDays, int academicYearID, int StandardID) {

		try {

			connection = getConnection();

			String insertAttendanceQuery = QueryMaker.INSERT_Attendance;

			preparedStatement = connection.prepareStatement(insertAttendanceQuery);

			preparedStatement.setInt(1, StandardID);

			preparedStatement.setString(2, term);

			preparedStatement.setString(3, month);

			preparedStatement.setInt(4, workingDays);

			preparedStatement.setInt(5, academicYearID);

			preparedStatement.setString(6, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Attendance detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting Attendance into table due to:::" + exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

	}

	public String retrieveScholasticSubjectNameBySubjectID(int subjectID, String subjectType) {

		String SubjectName = null;

		try {

			connection = getConnection();

			String retrieveScholasticSubjectNameBySubjectIDQuery = QueryMaker.RETRIEVE_Scholastic_Subject_Name_By_SubjectID;

			preparedStatement = connection.prepareStatement(retrieveScholasticSubjectNameBySubjectIDQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, subjectType);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectName = resultSet.getString("name");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectName;
	}

	public String enableUser(LoginForm form) {

		try {
			connection = getConnection();

			String enableUserQuery = QueryMaker.REJECT_USER;

			preparedStatement = connection.prepareStatement(enableUserQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, form.getUserID());

			preparedStatement.execute();

			System.out.println("User detail updated (For enable user) successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating user detail for enabling activity status of user from database due to:::"
							+ exception.getMessage());
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public HashMap<Integer, String> getAcademicYearNameList(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getAcademicYearNameListQuery = QueryMaker.RETRIEVE_AcademicYear_NameList_LIST;

			preparedStatement = connection.prepareStatement(getAcademicYearNameListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, ActivityStatus.DRAFT);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"),
						resultSet.getString("yearName") + " - " + resultSet.getString("activityStatus"));

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

	public String getActivityStatus(int academicYearID) {

		String ActivityStatus = null;

		try {

			connection = getConnection();

			String getActivityStatusQuery = QueryMaker.RETRIEVE_ActivityStatus;

			preparedStatement = connection.prepareStatement(getActivityStatusQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ActivityStatus = resultSet.getString("activityStatus");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving ActivityStatus to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ActivityStatus;
	}

	public int getActiveacademicYearID(int organizationID) {

		int ademicYearID = 0;

		try {

			connection = getConnection();

			String getActiveacademicYearIDQuery = QueryMaker.RETRIEVE_Active_academicYearID;

			preparedStatement = connection.prepareStatement(getActiveacademicYearIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ademicYearID = resultSet.getInt("id");

			}

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving ademicYear ID due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ademicYearID;
	}

	public int InActiveAcademicYearStatus(int academicYear) {
		int check = 0;
		try {
			connection = getConnection();

			String InActiveAcademicYearStatusQuery = QueryMaker.UPDATE_Academic_Year;

			preparedStatement = connection.prepareStatement(InActiveAcademicYearStatusQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, academicYear);

			preparedStatement.execute();

			check = 1;

			status = "success";

			System.out.println("Successfully udpated Academic Year Status into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating AcademicYear Status into table due to:::"
					+ exception.getMessage());
			status = "error";

			check = 0;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return check;

	}

	public String UpdateAcademicYear(int academicYearID) {

		try {
			connection = getConnection();

			String UpdateAcademicYearQuery = QueryMaker.UPDATE_Academic_Year;

			preparedStatement = connection.prepareStatement(UpdateAcademicYearQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);

			preparedStatement.setInt(2, academicYearID);

			preparedStatement.execute();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating AcademicYear into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

		/*
		 * 
		 * String check = "0";
		 * 
		 * try { connection = getConnection();
		 * 
		 * String UpdateAcademicYearQuery = QueryMaker.UPDATE_Academic_Year;
		 * 
		 * preparedStatement = connection.prepareStatement(UpdateAcademicYearQuery);
		 * 
		 * 
		 * preparedStatement.setString(1, ActivityStatus.ACTIVE);
		 * preparedStatement.setInt(2, academicYearID);
		 * 
		 * preparedStatement.execute();
		 * 
		 * check = "1";
		 * 
		 * status = "success";
		 * 
		 * System.out.println("Successfully udpated Academic Year Status into table");
		 * 
		 * preparedStatement.close();
		 * 
		 * connection.close();
		 * 
		 * } catch (Exception exception) { exception.printStackTrace(); System.out.
		 * println("Exception occured while updating AcademicYear Status into table due to:::"
		 * + exception.getMessage()); status = "error";
		 * 
		 * check = "0"; } return check;
		 */

	}

	public List<Integer> getAYClassIDList(int academicYear) {

		List<Integer> list = new ArrayList<Integer>();

		int ID = 0;

		try {
			connection = getConnection();

			String getAYClassIDListQuery = QueryMaker.RETRIEVE_EXISTING_AYClassID_LIST;

			preparedStatement = connection.prepareStatement(getAYClassIDListQuery);

			preparedStatement.setInt(1, academicYear);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ID = resultSet.getInt("id");

				list.add(ID);
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

	public void UpdateAYClassID(int ayclassID) {
		int check = 0;
		try {
			connection = getConnection();

			String UpdateAYClassIDQuery = QueryMaker.UPDATE_AYClassID;

			preparedStatement = connection.prepareStatement(UpdateAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, ayclassID);

			preparedStatement.execute();

			check = 1;

			status = "success";

			System.out.println("Successfully udpated AYClassID Status into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating AYClassID Status into table due to:::" + exception.getMessage());
			status = "error";

			check = 0;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public List<Integer> getDraftAYClassIDList(int academicYearID) {

		List<Integer> list = new ArrayList<Integer>();

		int ID = 0;

		try {
			connection = getConnection();

			String getAYClassIDListQuery = QueryMaker.RETRIEVE_EXISTING_AYClassID_LIST;

			preparedStatement = connection.prepareStatement(getAYClassIDListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.DRAFT);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ID = resultSet.getInt("id");

				list.add(ID);
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

	public void UpdateDraftAYClassID(int ayclassID) {

		int check = 0;
		try {
			connection = getConnection();

			String UpdateAYClassIDQuery = QueryMaker.UPDATE_AYClassID;

			preparedStatement = connection.prepareStatement(UpdateAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, ayclassID);

			preparedStatement.execute();

			check = 1;

			status = "success";

			System.out.println("Successfully udpated AYClassID Status into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating AYClassID Status into table due to:::" + exception.getMessage());
			status = "error";

			check = 0;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public void UpdateRegistrationAYClassID(int ayclassID) {

		int check = 0;
		try {
			connection = getConnection();

			String UpdateRegistrationAYClassIDQuery = QueryMaker.UPDATE_Registration_AYClassID;

			preparedStatement = connection.prepareStatement(UpdateRegistrationAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, ayclassID);

			preparedStatement.execute();

			check = 1;

			status = "success";

			System.out.println("Successfully udpated AYClassID Status into Registration table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating AYClassID Status into Registration table due to:::"
					+ exception.getMessage());
			status = "error";

			check = 0;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public void UpdateRegistrationDraftAYClassID(int ayclassID) {

		int check = 0;
		try {
			connection = getConnection();

			String UpdateRegistrationAYClassIDQuery = QueryMaker.UPDATE_Registration_AYClassID;

			preparedStatement = connection.prepareStatement(UpdateRegistrationAYClassIDQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, ayclassID);

			preparedStatement.execute();

			check = 1;

			status = "success";

			System.out.println("Successfully udpated AYClassID Status into Registration table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating AYClassID Status into Registration table due to:::"
					+ exception.getMessage());
			status = "error";

			check = 0;
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

	}

	public String retrieveStandardName1(int userID, int academicYearID, String activityStatus) {

		String StandardName = "";

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StandardName = resultSet.getString("standardName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return StandardName;
	}

	public String retrieveDivisionName1(int userID, int academicYearID, String activityStatus) {

		String DivisionName = "";

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			/* preparedStatement.setString(3, activityStatus); */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DivisionName = resultSet.getString("divisionName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return DivisionName;
	}

	public int retrieveStandardID1(int userID, int academicYearID, String activityStatus) {

		int StandardID = 0;

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			/* preparedStatement.setString(3, activityStatus); */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StandardID = resultSet.getInt("standardID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return StandardID;
	}

	public int retrieveDivisionID1(int userID, int academicYearID, String activityStatus) {

		int DivisionID = 0;

		try {

			connection = getConnection();

			String retrieveAYClassIDForClassTeacherQuery = QueryMaker.RETRIEVE_STANDARD_DIVISION_FOR_CLASS_TEACHER;

			preparedStatement = connection.prepareStatement(retrieveAYClassIDForClassTeacherQuery);

			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, academicYearID);
			/* preparedStatement.setString(3, activityStatus); */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DivisionID = resultSet.getInt("divisionID");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return DivisionID;
	}

	public String insertTimeTable(int standardID, int examinationID, String date, String subject) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		LoginForm form = new LoginForm();

		try {

			connection = getConnection();

			String insertTimeTableQuery = QueryMaker.INSERT_TimeTable;

			preparedStatement = connection.prepareStatement(insertTimeTableQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setInt(2, examinationID);

			// Inserting Start Date
			if (date == null || date == "" || date.isEmpty()) {

				form.setExamDate(null);
				preparedStatement.setString(3, date);

			} else {

				preparedStatement.setString(3, dateToBeFormatted.format(dateFormat.parse(date)));
			}

			preparedStatement.setString(4, subject);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted TimeTable detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting TimeTable into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;

	}

	public List<LoginForm> retriveTimeTableList(int academicYearID) {

		List<LoginForm> list = new ArrayList<LoginForm>();
		LoginForm form = null;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {

			connection = getConnection();

			String retriveTimeTableListQuery = QueryMaker.RETREIVE_TimeTable_LIST;

			preparedStatement = connection.prepareStatement(retriveTimeTableListQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new LoginForm();

				form.setTimeTableID(resultSet.getInt("id"));

				form.setStandardID(resultSet.getInt("standardID"));

				form.setExamID(resultSet.getInt("examID"));

				form.setSubject(resultSet.getString("subject"));

				/* form.setExamDate(resultSet.getString("examDate")); */

				if (resultSet.getString("examDate") == null || resultSet.getString("examDate") == "") {

					// form.setStartDate(null);
					form.setExamDate("");

				} else if (resultSet.getString("examDate").isEmpty()) {
					form.setExamDate("");

				} else {
					form.setExamDate(dateFormat.format(dateToBeFormatted.parse(resultSet.getString("examDate"))));
				}

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving TimeTable list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	public HashMap<String, String> getsubject(int standardID) {

		HashMap<String, String> map = new HashMap<String, String>();
		LoginDAOInf daoInf = new LoginDAOImpl();

		try {

			connection = getConnection();

			String getsubjectListQuery = QueryMaker.RETRIEVE_subject_LIST;

			preparedStatement = connection.prepareStatement(getsubjectListQuery);

			preparedStatement.setInt(1, standardID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int subjectID = 0;

				String Subject = "";
				String Subject1 = "";

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {

						continue;

					} else {

						Subject1 = Subject;
					}
					subjectID = Integer.parseInt(subArr[j].trim());

					map.put(Subject1, Subject1);
				}
			}

			map.put("School", "School");

			map.put("Holiday", "Holiday");

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public String updateTimeTableList(int standardID, String date, String subject, int examinationID, int timeTableID) {

		LoginForm form = new LoginForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = getConnection();

			String updateTimeTableListQuery = QueryMaker.update_Time_Table_List;

			preparedStatement = connection.prepareStatement(updateTimeTableListQuery);

			preparedStatement.setInt(1, standardID);

			/* preparedStatement.setString(2, date); */

			// updating Start Date
			if (date == null || date == "") {

				form.setExamDate(null);
				preparedStatement.setString(2, date);

			} else if (date.isEmpty()) {

				form.setExamDate(null);
				preparedStatement.setString(2, date);

			} else {
				preparedStatement.setString(2, dateToBeFormatted.format(dateFormat.parse(date)));
			}

			preparedStatement.setString(3, subject);

			preparedStatement.setInt(4, examinationID);

			preparedStatement.setInt(5, timeTableID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated TimeTable List detail into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating TimeTable List into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public JSONObject retrieveSubjectListForStandard(int standardID) {

		JSONObject values = null;

		JSONObject values1 = null;
		JSONObject values2 = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONArray array1 = new JSONArray();
		values1 = new JSONObject();

		JSONArray array2 = new JSONArray();
		values2 = new JSONObject();

		JSONObject object = null;

		JSONObject object1 = null;

		JSONObject object2 = null;

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
				String Subject1 = "";

				String subArr[] = resultSet.getString("subjectList").split(",");

				for (int j = 0; j < subArr.length; j++) {

					Subject = daoInf.retrievesubjectNameBySubjectIDSubjectType(Integer.parseInt(subArr[j].trim()));

					if (Subject == null) {
						continue;
					} else {
						Subject1 = Subject;
					}
					object = new JSONObject();

					object.put("subjectList", Subject1);

					object.put("subjectID", Integer.parseInt(subArr[j].trim()));

					array.add(object);

					values.put("Release", array);
				}
			}

			object1 = new JSONObject();

			object1.put("subjectList", "School");

			array.add(object1);

			values.put("Release", array);

			object2 = new JSONObject();

			object2.put("subjectList", "Holiday");

			array.add(object2);

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

	public String retrievesubjectNameBySubjectIDForNonScholastic(int subjectID) {

		String SubjectName = null;

		try {

			connection = getConnection();

			String retrievesubjectNameBySubjectIDForNonScholasticQuery = QueryMaker.RETRIEVE_Subject_Name_By_SubjectID_SubjectType1;

			preparedStatement = connection.prepareStatement(retrievesubjectNameBySubjectIDForNonScholasticQuery);

			preparedStatement.setInt(1, subjectID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectName = resultSet.getString("name");
			}

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectName;
	}

	public HashMap<String, String> getDivisionList(int standardID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String getDivisionListQuery = QueryMaker.RETRIEVE_Divisions_LIST;

			preparedStatement = connection.prepareStatement(getDivisionListQuery);

			preparedStatement.setInt(1, standardID);

			resultSet = preparedStatement.executeQuery();

			String divisionID = "";

			while (resultSet.next()) {

				map.put("" + resultSet.getInt("id"), resultSet.getString("division"));

				divisionID += "," + resultSet.getInt("id");
			}

			if (divisionID.startsWith(",")) {

				divisionID = divisionID.substring(1);
			}

			map.put(divisionID, "All");
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public HashMap<Integer, String> getInActiveAcademicYearList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getInActiveAcademicYearListQuery = QueryMaker.RETRIEVE_InAcademicYear_NameList_LIST;

			preparedStatement = connection.prepareStatement(getInActiveAcademicYearListQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("yearName"));

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

	public HashMap<Integer, String> getStandardList(int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getDivisionListQuery = QueryMaker.RETRIEVE_StandardList_For_ACademicYear;

			preparedStatement = connection.prepareStatement(getDivisionListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.INACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("standardID"), resultSet.getString("standard"));
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

	public String retrieveInActiveAcademicYearName(int academicYearID) {
		String name = "";
		try {
			connection = getConnection();

			String retrieveAcademicYearNameQuery = QueryMaker.RETRIEVE_InActive_AcademicYear_Name;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearNameQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, academicYearID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				name = resultSet.getString("yearName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing Academic Year list from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return name;
	}

	public int CheckAcademicYearName(String yearName, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckAcademicYearNameQuery = QueryMaker.VERIFY_AcademicYear_Name;

			preparedStatement = connection.prepareStatement(CheckAcademicYearNameQuery);

			preparedStatement.setString(1, yearName);
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving academicYear ID from AcademicYear table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckAcademicYearNameEdit(String yearName, int academicYearID, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckAcademicYearNameEditQuery = QueryMaker.VERIFY_Edit_AcademicYear_Name;

			preparedStatement = connection.prepareStatement(CheckAcademicYearNameEditQuery);

			preparedStatement.setString(1, yearName);
			preparedStatement.setInt(2, organizationID);
			preparedStatement.setInt(3, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving AcademicYear ID from AcademicYear table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String retrieveOrganizationEmail(int organizationID) {
		String OrganizationEmail = null;

		try {

			connection = getConnection();

			String retrieveOrganizationEmailQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationEmailQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationEmail = resultSet.getString("email");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Organization Phone due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationEmail;
	}

	public String retrievesubjectNameBySubjectIDForCoScholasticSubject(int subjectID) {

		String SubjectName = null;

		try {

			connection = getConnection();

			String retrievesubjectNameBySubjectIDForCoScholasticSubjectQuery = QueryMaker.RETRIEVE_Subject_Name_By_SubjectID_SubjectType;

			preparedStatement = connection.prepareStatement(retrievesubjectNameBySubjectIDForCoScholasticSubjectQuery);

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, "Co-scholastic");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SubjectName = resultSet.getString("name");
			}

		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SubjectName;
	}

	public HashMap<Integer, String> getStandardExceptXStandard(int organizationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getStandardListQuery = QueryMaker.RETRIEVE_Standard_LIST;

			preparedStatement = connection.prepareStatement(getStandardListQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getString("standard").equals("X")) {
					continue;
				} else {
					map.put(resultSet.getInt("id"), resultSet.getString("standard"));
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

	public int CheckStandardName(String standard, String stage, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckStandardNameQuery = QueryMaker.RETRIEVE_StandardName;

			preparedStatement = connection.prepareStatement(CheckStandardNameQuery);

			preparedStatement.setString(1, standard);
			preparedStatement.setString(2, stage);
			preparedStatement.setInt(3, organizationID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving standard ID from Standard table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckStandardNameEdit(String standard, String stage, int standardID, int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckStandardNameEditQuery = QueryMaker.VERIFY_Edit_Standard_Name;

			preparedStatement = connection.prepareStatement(CheckStandardNameEditQuery);

			preparedStatement.setString(1, standard);
			preparedStatement.setString(2, stage);
			preparedStatement.setInt(3, organizationID);
			preparedStatement.setInt(4, standardID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving standardID from User table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckDivisionName(String division, int standardID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckDivisionNameQuery = QueryMaker.RETRIEVE_DivisionID_By_StandardDivision;

			preparedStatement = connection.prepareStatement(CheckDivisionNameQuery);

			preparedStatement.setInt(1, standardID);
			preparedStatement.setString(2, division);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving division ID from Division table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public int CheckDivisionNameEdit(String division, int standardID, int divisionID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckDivisionNameEditQuery = QueryMaker.VERIFY_Edit_Division_Name;

			preparedStatement = connection.prepareStatement(CheckDivisionNameEditQuery);

			preparedStatement.setString(1, division);
			preparedStatement.setInt(2, standardID);
			preparedStatement.setInt(3, divisionID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retieving divisionID from User table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String retrieveAcademicYearAgeFromDate(int academicYearID) {

		String ageFromDate = null;

		try {

			connection = getConnection();

			String retrieveAcademicYearAgeFromDateQuery = QueryMaker.RETRIEVE_AGE_FROM_DATE;

			preparedStatement = connection.prepareStatement(retrieveAcademicYearAgeFromDateQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ageFromDate = resultSet.getString("ageFromDate");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ageFromDate;
	}

	public String retrieveOrganizationEmailPassword(int organizationID) {
		String OrganizationEmailPass = null;

		try {

			connection = getConnection();

			String retrieveOrganizationEmailPassQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationEmailPassQuery);

			preparedStatement.setInt(1, organizationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationEmailPass = resultSet.getString("emailPass");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Organization Phone due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return OrganizationEmailPass;
	}

	public String retrievePrincipalSignatureFile(int organisationID) {
		String Signature = null;

		try {

			connection = getConnection();

			String retrievePrincipalSignatureFileQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePrincipalSignatureFileQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Signature = resultSet.getString("signImage");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Principal Signature due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return Signature;
	}

	public String retrieveOrganizationSeal(int organisationID) {
		String OrganizationSeal = null;

		try {

			connection = getConnection();

			String retrieveOrganizationSealQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationSealQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				OrganizationSeal = resultSet.getString("sealImage");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Seal due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return OrganizationSeal;
	}

	@Override
	public String insertLibrary(String library, int organisationID) {

		try {
			connection = getConnection();

			String insertLibraryQuery = QueryMaker.INSERT_Library;

			preparedStatement = connection.prepareStatement(insertLibraryQuery);

			preparedStatement.setString(1, library);
			preparedStatement.setInt(2, organisationID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted Library into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while inserting Library into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public HashMap<Integer, String> retrieveLibraryList(int organisationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {
			connection = getConnection();

			String retrieveLibraryListQuery = QueryMaker.RETRIEVE_EXISTING_Library_LIST;

			preparedStatement = connection.prepareStatement(retrieveLibraryListQuery);

			preparedStatement.setInt(1, organisationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

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
	public String updateLibrary(String library, int libraryID) {
		try {
			connection = getConnection();

			String updateLibraryQuery = QueryMaker.UPDATE_Library;

			preparedStatement = connection.prepareStatement(updateLibraryQuery);

			preparedStatement.setString(1, library);
			preparedStatement.setInt(2, libraryID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated Library into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating Library into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public int retrieveOrganizationID() {
		int organizationID = 0;

		try {
			connection = getConnection();

			String retrieveOrganizationIDQuery = QueryMaker.RETRIEVE_OrganizationID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationIDQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				organizationID = resultSet.getInt("id");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving Organization ID from User table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return organizationID;
	}

	@Override
	public HashMap<Integer, String> getAcademicYearList(int organisationID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getAcademicYearListQuery = QueryMaker.RETRIEVE_All_AcademicYear_LIST;

			preparedStatement = connection.prepareStatement(getAcademicYearListQuery);

			preparedStatement.setInt(1, organisationID);
			preparedStatement.setString(2, ActivityStatus.DRAFT);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("yearName"));
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
	public HashMap<String, String> getStandardNameList(int organisationID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String getStandardListQuery = QueryMaker.RETRIEVE_ClassName_LIST;

			preparedStatement = connection.prepareStatement(getStandardListQuery);

			preparedStatement.setInt(1, organisationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("standard"), resultSet.getString("standard"));

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
	public String editBookDetail(LibraryForm form) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new java.util.Date();

		String NewDate = dateFormat.format(date);

		try {
			connection = getConnection();

			String editBookDetailQuery = QueryMaker.UPDATE_Book_DETAIL;
			String editBookStatusQuery = QueryMaker.INSERT_Books_Status;

			preparedStatement = connection.prepareStatement(editBookDetailQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getAuthor());
			preparedStatement.setString(3, form.getGenre());
			preparedStatement.setString(4, form.getPublication());
			preparedStatement.setString(5, form.getEdition());
			preparedStatement.setString(6, form.getAccNum());
			preparedStatement.setString(7, form.getPages());
			preparedStatement.setString(8, form.getDescription());
			preparedStatement.setString(9, form.getBarcode());
			preparedStatement.setString(10, form.getPublicationYear());
			// preparedStatement.setString(11, studform.getRegDate());

			// updating Start Date
			if (form.getRegDate() == null || form.getRegDate() == "" || form.getRegDate().isEmpty()) {

				form.setRegDate(null);
				preparedStatement.setString(11, form.getRegDate());

			} else {

				preparedStatement.setString(11, dateToBeFormatted.format(dateFormat.parse(form.getRegDate())));
			}

			preparedStatement.setString(12, form.getStatus());
			// preparedStatement.setString(13, studform.getDateInactive());

			// updating Start Date
			if (form.getDateInactive() == null || form.getDateInactive() == "" || form.getDateInactive().isEmpty()) {

				form.setDateInactive(null);
				preparedStatement.setString(13, form.getDateInactive());

			} else {

				preparedStatement.setString(13, dateToBeFormatted.format(dateFormat.parse(form.getDateInactive())));
			}

			preparedStatement.setString(14, form.getType());
			preparedStatement.setString(15, form.getColNo());
			preparedStatement.setString(16, form.getSection());
			preparedStatement.setInt(17, form.getShelfID());
			preparedStatement.setInt(18, form.getVendorID());
			preparedStatement.setDouble(19, form.getPrice());
			preparedStatement.setInt(20, form.getBookID());

			preparedStatement1 = connection.prepareStatement(editBookStatusQuery);

			preparedStatement1.setString(1, form.getStatus());
			preparedStatement1.setString(2, dateToBeFormatted.format(dateFormat.parse(NewDate)));
			preparedStatement1.setInt(3, form.getBookID());

			preparedStatement.execute();
			preparedStatement1.execute();

			System.out.println("Book details updated successfully.");
			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating Book details into database due to:::" + exception.getMessage());
			status = "input";
		} finally {
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public HashMap<Integer, String> getStandardDivisionList(int academicYearID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String getStandardDivisionListQuery = QueryMaker.RETRIEVE_Standard_Division_LIST;

			preparedStatement = connection.prepareStatement(getStandardDivisionListQuery);

			preparedStatement.setInt(1, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"),
						resultSet.getString("standardName") + "-" + resultSet.getString("divisionName"));
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
	public String retrieveClassNameByClassID(int ayclassID) {
		String ClassName = null;

		try {

			connection = getConnection();

			String retrieveClassNameByClassIDQuery = QueryMaker.RETRIEVE_ClassName_BY_ClassID;

			preparedStatement = connection.prepareStatement(retrieveClassNameByClassIDQuery);

			preparedStatement.setInt(1, ayclassID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ClassName = resultSet.getString("standardName") + "-" + resultSet.getString("divisionName");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Principal Signature due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ClassName;
	}

	@Override
	public int CheckFirstEntryAcademicYear(int organizationID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckFirstEntryAcademicYearQuery = QueryMaker.Check_FirstEntry_AcademicYear;

			preparedStatement = connection.prepareStatement(CheckFirstEntryAcademicYearQuery);

			preparedStatement.setInt(1, organizationID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving academicYear ID from AcademicYear table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public JSONObject checkAttendanceAvailability(String month, String term, int standardID, int academicYearID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {
			connection = getConnection();

			String checkAttendanceAvailabilityQuery = QueryMaker.Check_Attendance_Availability;

			preparedStatement = connection.prepareStatement(checkAttendanceAvailabilityQuery);

			preparedStatement.setString(1, term);
			preparedStatement.setString(2, month);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setInt(4, standardID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("check", check1);

				object.put("errMsg", "Attendance for this month & standard already exist. Please new details.");
				System.out.println("attendance check is: " + check1);
				array.add(object);

				values.put("Release", array);
			}

			if (check1 == 0) {
				System.out.println("attendance check if: " + check1);
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
	public int CheckAttendance(int standardID, String term, String workingMonth, int academicYearID) {
		int status = 0;

		try {
			connection = getConnection();

			String CheckAcademicYearNameQuery = QueryMaker.Check_Attendance_Availability;

			preparedStatement = connection.prepareStatement(CheckAcademicYearNameQuery);

			preparedStatement.setString(1, term);
			preparedStatement.setString(2, workingMonth);
			preparedStatement.setInt(3, academicYearID);
			preparedStatement.setInt(4, standardID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				status = 1;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retieving attendance ID from Attendance table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	public String retrievePrimaryHeadmistressSignature(int organisationID) {
		String PrimaryHeadmistressSignature = null;

		try {

			connection = getConnection();

			String retrieveOrganizationSealQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationSealQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				PrimaryHeadmistressSignature = resultSet.getString("primaryHeadSign");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Seal due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return PrimaryHeadmistressSignature;
	}

	public String retrievePrimaryHeadmistressSeal(int organisationID) {
		String PrimaryHeadmistressSeal = null;

		try {

			connection = getConnection();

			String retrieveOrganizationSealQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationSealQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				PrimaryHeadmistressSeal = resultSet.getString("primaryHeadSeal");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Seal due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return PrimaryHeadmistressSeal;
	}

	public String retrieveSecondaryHeadmistressSignatur(int organisationID) {
		String SecondaryHeadmistressSignatur = null;

		try {

			connection = getConnection();

			String retrieveOrganizationSealQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationSealQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SecondaryHeadmistressSignatur = resultSet.getString("secondaryHeadSign");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Seal due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SecondaryHeadmistressSignatur;
	}

	public String retrieveSecondaryHeadmistressSeal(int organisationID) {
		String SecondaryHeadmistressSeal = null;

		try {

			connection = getConnection();

			String retrieveOrganizationSealQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationSealQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				SecondaryHeadmistressSeal = resultSet.getString("secondaryHeadSeal");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Seal due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return SecondaryHeadmistressSeal;
	}

	public String retrieveSignOnRC(int organisationID) {
		String signOnRC = null;

		try {

			connection = getConnection();

			String retrieveOrganizationSealQuery = QueryMaker.RETRIEVE_CONFIGURATION_Organization_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOrganizationSealQuery);

			preparedStatement.setInt(1, organisationID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				signOnRC = resultSet.getString("signOnRC");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving Organization Seal due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return signOnRC;
	}

	@Override
	public String getStandardStageByStandardID(int standardID) {
		// TODO Auto-generated method stub
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

	@Override
	public boolean verifySubjectIsMarkBased(int subjectID, int ayClassID) {

		boolean verify = false;

		try {

			connection1 = getConnection();

			String verifySubjectIsMarkBasedQuery = QueryMaker.RETRIEVE_SUBJECT_GRADE_TYPE;

			preparedStatement1 = connection1.prepareStatement(verifySubjectIsMarkBasedQuery);

			preparedStatement1.setInt(1, ayClassID);
			preparedStatement1.setInt(2, subjectID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				if (resultSet1.getInt("totalMarks") > 0) {
					verify = true;
				} else {
					verify = false;
				}

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving subject name due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeConnection(connection1);
		}

		return verify;
	}
}
