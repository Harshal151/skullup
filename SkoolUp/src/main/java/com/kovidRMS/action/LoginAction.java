package com.kovidRMS.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kovidRMS.daoImpl.ConfigurationDAOImpl;
import com.kovidRMS.daoImpl.LoginDAOImpl;
import com.kovidRMS.daoImpl.StudentDAOImpl;
import com.kovidRMS.daoInf.ConfigurationDAOInf;
import com.kovidRMS.daoInf.LoginDAOInf;
import com.kovidRMS.daoInf.StuduntDAOInf;
import com.kovidRMS.form.ConfigurationForm;
import com.kovidRMS.form.LoginForm;
import com.kovidRMS.form.StudentForm;
import com.kovidRMS.service.kovidRMSServiceImpl;
import com.kovidRMS.service.kovidRMSServiceInf;
import com.kovidRMS.util.ActivityStatus;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @author shweta
 *
 */
public class LoginAction extends ActionSupport implements ModelDriven<LoginForm>, SessionAware, ServletResponseAware {

	String message = null;
	LoginForm form = new LoginForm();
	StudentForm studform = new StudentForm();

	private Map<String, Object> sessionAttribute = null;

	HashMap<Integer, String> subjectTypeList = null;
	HashMap<Integer, String> CoscholasticsubjectTypeList = null;
	HashMap<Integer, String> AcademicsubjectTypeList = null;
	HashMap<Integer, String> PhysicalsubjectTypeList = null;
	HashMap<Integer, String> CreativesubjectTypeList = null;
	HashMap<Integer, String> CompulsoryActivitiesList = null;
	HashMap<Integer, String> PersonalitysubjectTypeList = null;

	HashMap<Integer, String> organizationList = null;

	HashMap<Integer, String> StandardList = null;

	HashMap<Integer, String> DivisionList = null;

	HashMap<Integer, String> AppuserList = null;

	HashMap<Integer, String> ExamList = null;

	List<LoginForm> searchUserList = null;
	List<LoginForm> signedUpUserList = null;
	List<LoginForm> searchOrganizationList = null;
	List<LoginForm> OrganizationEditList;
	List<LoginForm> searchAcademicYearList = null;
	List<LoginForm> AcademicYearEditList;
	List<LoginForm> attendanceList;
	List<LoginForm> attendance1List;
	List<LoginForm> searchviewStandardList = null;
	List<LoginForm> searchStandardList = null;
	List<LoginForm> StandardEditList;
	List<LoginForm> searchDivisionList = null;
	List<LoginForm> DivisionEditList;
	List<LoginForm> AcademicYearList = null;
	List<LoginForm> TimeTableList = null;

	HashMap<Integer, String> LibraryList = null;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response;

	LoginDAOInf daoInf = null;
	kovidRMSServiceInf serviceInf = null;
	/* setters of form */

	public LoginForm getForm() {
		return form;
	}

	public void setForm(LoginForm form) {
		this.form = form;
	}

	public StudentForm getStudform() {
		return studform;
	}

	public void setStudform(StudentForm studform) {
		this.studform = studform;
	}

	/**
	 * 
	 */
	public String execute() throws Exception {

		// Setting method name as useraction to be insert into Audit table
		String actionName = "Login";

		daoInf = new LoginDAOImpl();

		message = daoInf.verifyUserCredentials(form);

		if (message.equals("success")) {
			System.out.println("Succcessfully Logged in...");

			form = daoInf.getUserDetail(form.getUsername());

			sessionAttribute.put("USER", form);

			// Inserting values into Audit table for user action
			daoInf.insertAudit(request.getRemoteAddr(), actionName, form.getUserID());

			System.out.println("officeAdmin:" + form.getRole() + "--" + form.getMedium());
			if (form.getRole().equals("officeAdmin")) {
				return INPUT;
			} else {
				return SUCCESS;
			}

		} else {
			System.out.println("Please check the user credetials. Wrong credentials entered.");
			addActionError("Enter valid username and password");
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUser() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		System.out.println("user details: " + userForm.getOrganizationID() + "--" + form.getUsername());
		message = serviceInf.registerUser(form, realPath, request, userForm.getOrganizationID());

		if (message.equalsIgnoreCase("success")) {
			System.out.println("New Sign Up user registered successfully. Record inserted successfully into database.");
			addActionMessage("User registered successfully.");

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Add User", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {
			System.out.println("the entered username already exists into User table. Try with different username.");
			addActionError("Username already exists. Please use different username.");

			// Inserting values into Audit table for add user errors
			daoInf.insertAudit(request.getRemoteAddr(), "Add User error: username already exists.",
					userForm.getUserID());
			return INPUT;
		} else {
			System.out.println("Error while inserting record into database.");
			addActionError("Failed to register user.Please check server logs for more details.");

			// Inserting values into Audit table for add user exception
			daoInf.insertAudit(request.getRemoteAddr(), "Add User Exception occurred.", userForm.getUserID());
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchUser() throws Exception {
		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		searchUserList = daoInf.searchUser(form.getSearchUserName(), userForm.getOrganizationID());

		request.setAttribute("searchUserList", searchUserList);

		/*
		 * Checking whether userList is empty or not, if empty give error message saying
		 * User with name not found
		 */
		if (searchUserList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "User with name '" + form.getSearchUserName() + "' not found.";

			addActionError(errorMsg);

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditUserList() throws Exception {
		daoInf = new LoginDAOImpl();

		LoginForm loginForm = (LoginForm) sessionAttribute.get("USER");

		signedUpUserList = daoInf.retriveEditUSerList(loginForm.getOrganizationID());

		request.setAttribute("signedUpUserList", signedUpUserList);

		if (signedUpUserList.size() > 0) {

			request.setAttribute("userListEnable", "userListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No user found. Please add new user.";

			addActionError(errorMsg);

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditUser() throws Exception {

		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm loginForm = (LoginForm) sessionAttribute.get("USER");

		signedUpUserList = daoInf.retreiveUserDetailByUserID(form.getUserID());

		for (LoginForm formVal : signedUpUserList) {
			form.setLibraryID(formVal.getLibraryID());
		}

		request.setAttribute("LibraryID", form.getLibraryID());

		LibraryList = daoInf.retrieveLibraryList(loginForm.getOrganizationID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUser() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm loginForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.editUserDetail(form, loginForm.getOrganizationID(), realPath, request);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Successfully updated user details.");

			signedUpUserList = daoInf.retreiveUserDetailByUserID(form.getUserID());

			LibraryList = daoInf.retrieveLibraryList(loginForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User", form.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Password cannot repeat any of your previous 5 passwords. Please try different password.");

			signedUpUserList = daoInf.retreiveUserDetailByUserID(form.getUserID());

			LibraryList = daoInf.retrieveLibraryList(loginForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User error: password history check failed",
					form.getUserID());
			return INPUT;

		} else {

			addActionError("Failed to update user details. Please check logs for more details.");

			signedUpUserList = daoInf.retreiveUserDetailByUserID(form.getUserID());

			LibraryList = daoInf.retrieveLibraryList(loginForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User exception occurred.", form.getUserID());
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() throws Exception {
		daoInf = new LoginDAOImpl();
		System.out.println("User ID to be disabled..." + form.getUserID());

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.rejectUser(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("User disabled successfully.Activity Status changed to Inactive");
			addActionMessage("User disabled successfully.");

			signedUpUserList = daoInf.retriveEditUSerList(userForm.getOrganizationID());

			LibraryList = daoInf.retrieveLibraryList(userForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "User Disabled", userForm.getUserID());

			return SUCCESS;
		} else {
			System.out.println("Error while updating user activity status to Inactive into database.");
			addActionError("Failed to disable user.");
			return ERROR;
		}
	}

	public String enableUser() throws Exception {
		daoInf = new LoginDAOImpl();
		System.out.println("User ID to be enabled..." + form.getUserID());

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.enableUser(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("User enabled successfully.Activity Status changed to Active");
			addActionMessage("User enabled successfully.");

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "User Enabled", userForm.getUserID());

			return SUCCESS;
		} else {
			System.out.println("Error while updating user activity status to Active into database.");
			addActionError("Failed to enable user.");
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUserProfile() throws Exception {
		serviceInf = new kovidRMSServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.editUserDetail(form, userForm.getOrganizationID(), realPath, request);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Profile updated successfully.");

			signedUpUserList = new ArrayList<LoginForm>();

			signedUpUserList.add(form);

			LibraryList = daoInf.retrieveLibraryList(userForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User Profile", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Password cannot repeat any of your previous 5 passwords. Please try different password.");

			signedUpUserList = new ArrayList<LoginForm>();

			signedUpUserList.add(form);

			LibraryList = daoInf.retrieveLibraryList(userForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User Profile error: password history check failed",
					userForm.getUserID());

			return INPUT;

		} else {

			addActionError("Failed to update profile details. Please check logs for more details.");

			signedUpUserList = new ArrayList<LoginForm>();

			signedUpUserList.add(form);

			LibraryList = daoInf.retrieveLibraryList(userForm.getOrganizationID());

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Edit User Profile exception occurred.", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditProfile() throws Exception {

		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		System.out.println("UserID: " + userForm.getUserID());
		signedUpUserList = daoInf.retreiveUserDetailByUserID(userForm.getUserID());

		for (LoginForm formVal : signedUpUserList) {
			form.setLibraryID(formVal.getLibraryID());
		}

		request.setAttribute("LibraryID", form.getLibraryID());

		LibraryList = daoInf.retrieveLibraryList(userForm.getOrganizationID());
		System.out.println("LibraryID: " + form.getLibraryID() + "--" + LibraryList.size());
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchOrganizations() throws Exception {

		daoInf = new LoginDAOImpl();

		searchOrganizationList = daoInf.searchOrganizationList(form.getSearchOrganization());

		if (searchOrganizationList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No organization found for : " + form.getSearchOrganization());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllOrganizations() throws Exception {
		daoInf = new LoginDAOImpl();

		/*
		 * Retrieving existing Organization list from table PVFrequency
		 */
		searchOrganizationList = daoInf.retrieveExistingOrganizationList();

		if (searchOrganizationList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No organization found. Please add new Organization.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditOrganization() throws Exception {
		daoInf = new LoginDAOImpl();

		String searchOrganization = form.getSearchOrganization();

		OrganizationEditList = daoInf.retrieveOrganizationListByID(form.getOrganizationsID(),
				form.getSearchOrganization());

		for (LoginForm formVal : OrganizationEditList) {
			form.setBoard(formVal.getBoard());
		}
		request.setAttribute("Board", form.getBoard());

		System.out.println("OrganizationEditList: " + OrganizationEditList.size());

		LibraryList = daoInf.retrieveLibraryList(form.getOrganizationsID());

		if (searchOrganization == null || searchOrganization == "") {

			searchOrganizationList = daoInf.retrieveExistingOrganizationList();
		} else {

			searchOrganizationList = daoInf.searchOrganizationList(searchOrganization);
		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addOrganization() throws Exception {
		daoInf = new LoginDAOImpl();

		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		/*
		 * INserting Organization into PVFrequency table
		 */
		message = serviceInf.registerOrganization(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Organization configured successfully.");

			searchOrganizationList = daoInf.retrieveExistingOrganizationList();

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Organization", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring organization. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchOrganizationList = daoInf.retrieveExistingOrganizationList();

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Organization Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editOrganization() throws Exception {

		serviceInf = new kovidRMSServiceImpl();

		daoInf = new LoginDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchOrganization = form.getSearchOrganization();

		message = serviceInf.updateOrganization(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Organization udpated successfully.");

			OrganizationEditList = daoInf.retrieveOrganizationListByID(form.getOrganizationsID(), searchOrganization);

			LibraryList = daoInf.retrieveLibraryList(form.getOrganizationsID());

			if (searchOrganization == null || searchOrganization == "") {

				searchOrganizationList = daoInf.retrieveExistingOrganizationList();

			} else {

				searchOrganizationList = daoInf.searchOrganizationList(searchOrganization);

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Organization", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated organization. Please check logs for more details.");

			OrganizationEditList = daoInf.retrieveOrganizationListByID(form.getOrganizationsID(), searchOrganization);

			LibraryList = daoInf.retrieveLibraryList(form.getOrganizationsID());

			if (searchOrganization == null || searchOrganization == "") {

				searchOrganizationList = daoInf.retrieveExistingOrganizationList();
			} else {

				searchOrganizationList = daoInf.searchOrganizationList(searchOrganization);
			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Organization Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	public String renderAcademicYear() throws Exception {
		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		searchAcademicYearList = new ArrayList<LoginForm>();

		request.setAttribute("searchAcademicYearList", searchAcademicYearList);

		int academicYearID = daoInf.retrieveAcademicYearID(userForm.getOrganizationID());

		attendanceList = daoInf.retrieveattendanceList(academicYearID, "Term I");

		request.setAttribute("attendanceList", attendanceList);

		attendance1List = daoInf.retrieveattendanceList(academicYearID, "Term II");

		request.setAttribute("attendance1List", attendance1List);

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchAcademicYear() throws Exception {

		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		searchAcademicYearList = daoInf.searchAcademicYearList(form.getSearchAcademicYear(),
				userForm.getOrganizationID());

		request.setAttribute("searchAcademicYearList", searchAcademicYearList);

		int academicYearID = daoInf.retrieveAcademicYearID(userForm.getOrganizationID());

		attendanceList = daoInf.retrieveattendanceList(academicYearID, "Term I");

		request.setAttribute("attendanceList", attendanceList);

		attendance1List = daoInf.retrieveattendanceList(academicYearID, "Term II");

		request.setAttribute("attendance1List", attendance1List);

		if (searchAcademicYearList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No academic year found for : " + form.getSearchAcademicYear());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllAcademicYear() throws Exception {
		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		int academicYearID = daoInf.retrieveAcademicYearID(userForm.getOrganizationID());

		attendanceList = daoInf.retrieveattendanceList(academicYearID, "Term I");

		request.setAttribute("attendanceList", attendanceList);

		attendance1List = daoInf.retrieveattendanceList(academicYearID, "Term II");

		request.setAttribute("attendance1List", attendance1List);
		/*
		 * Retrieving existing AcademicYear list from table PVFrequency
		 */
		searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

		request.setAttribute("searchAcademicYearList", searchAcademicYearList);

		if (searchAcademicYearList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;
		} else {

			addActionError("No academic year found. Please add new academic year.");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditAcademicYear() throws Exception {
		daoInf = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchAcademicYear = form.getSearchAcademicYear();

		AcademicYearEditList = daoInf.retrieveAcademicYearListByID(form.getAcademicYearID(),
				form.getSearchAcademicYear());

		attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

		request.setAttribute("attendanceList", attendanceList);

		attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

		request.setAttribute("attendance1List", attendance1List);

		if (searchAcademicYear == null || searchAcademicYear == "") {

			searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			request.setAttribute("searchAcademicYearList", searchAcademicYearList);

		} else if (searchAcademicYear.isEmpty()) {

			searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			request.setAttribute("searchAcademicYearList", searchAcademicYearList);

		} else {
			System.out.println("bye");
			searchAcademicYearList = daoInf.searchAcademicYearList(searchAcademicYear, userForm.getOrganizationID());

			request.setAttribute("searchAcademicYearList", searchAcademicYearList);
		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	public String activateAcademicYear() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		attendanceList = daoInf.retrieveattendanceList(userForm.getAcademicYearID(), "Term I");

		request.setAttribute("attendanceList", attendanceList);

		attendance1List = daoInf.retrieveattendanceList(userForm.getAcademicYearID(), "Term II");

		request.setAttribute("attendance1List", attendance1List);

		LibraryList = daoInf.retrieveLibraryList(userForm.getAcademicYearID());

		message = serviceInf.activateAcademicYear(form, form.getAcademicYearID(), userForm.getOrganizationID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Academic Year configured successfully.");

			searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			// Inserting values into Audit table

			daoInf.insertAudit(request.getRemoteAddr(), "Configure Academic Year", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring academic year. Please check logs for more details.");

			/*
			 * Retrieving existing Academic Year list from table PVFrequency
			 */
			searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure academic year Exception occurred",
					userForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAcademicYear() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.registerAcademicYear(form, userForm.getOrganizationID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Academic Year configured successfully.");

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			// Inserting values into Audit table

			daoInf.insertAudit(request.getRemoteAddr(), "Configure Academic Year", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Academic year name already exist. Please add new academic year name.");

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Academic year name already exist Exception occurred",
					userForm.getUserID());

			return ERROR;
		} else {
			addActionError("Error while configuring academic year. Please check logs for more details.");

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure academic year Exception occurred",
					userForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editAcademicYear() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchAcademicYear = form.getSearchAcademicYear();

		// getting organizationList value
		organizationList = daoInf.getOrganization();

		message = serviceInf.editAcademicYear(form, userForm.getOrganizationID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Academic year udpated successfully.");

			AcademicYearEditList = daoInf.retrieveAcademicYearListByID(form.getAcademicYearID(), searchAcademicYear);

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			if (searchAcademicYear == null || searchAcademicYear == "") {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else if (searchAcademicYear.isEmpty()) {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else {

				searchAcademicYearList = daoInf.searchAcademicYearList(searchAcademicYear,
						userForm.getOrganizationID());

			}

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			/*
			 * System.out.println("...request" +request); System.out.println("...userForm+"
			 * +userForm);
			 */
			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Academic year", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Academic year name already exist. Please update new academic year name.");

			AcademicYearEditList = daoInf.retrieveAcademicYearListByID(form.getAcademicYearID(), searchAcademicYear);

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			if (searchAcademicYear == null || searchAcademicYear == "") {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else if (searchAcademicYear.isEmpty()) {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else {

				searchAcademicYearList = daoInf.searchAcademicYearList(searchAcademicYear,
						userForm.getOrganizationID());

			}

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Academic year name already exist Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			request.setAttribute("componentEdit", "edit");

			return ERROR;
		} else {
			addActionError("Failed to udpated Academic Year. Please check logs for more details.");

			AcademicYearEditList = daoInf.retrieveAcademicYearListByID(form.getAcademicYearID(), searchAcademicYear);

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			if (searchAcademicYear == null || searchAcademicYear == "") {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else if (searchAcademicYear.isEmpty()) {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else {

				searchAcademicYearList = daoInf.searchAcademicYearList(searchAcademicYear,
						userForm.getOrganizationID());

			}

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure academic year Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	public String editAttendance() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchAcademicYear = form.getSearchAcademicYear();

		// getting organizationList value
		organizationList = daoInf.getOrganization();

		message = serviceInf.editAttendance(form, userForm.getOrganizationID(), realPath, request);
		// message = daoInf.updateAcademicYear(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Attendance udpated successfully.");

			AcademicYearEditList = daoInf.retrieveAcademicYearListByID(form.getAcademicYearID(), searchAcademicYear);

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			if (searchAcademicYear == null || searchAcademicYear == "") {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());

			} else {

				searchAcademicYearList = daoInf.searchAcademicYearList(searchAcademicYear,
						userForm.getOrganizationID());

			}

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Attendance", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated Attendance. Please check logs for more details.");

			AcademicYearEditList = daoInf.retrieveAcademicYearListByID(form.getAcademicYearID(), searchAcademicYear);

			attendanceList = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term I");

			request.setAttribute("attendanceList", attendanceList);

			attendance1List = daoInf.retrieveattendanceList(form.getAcademicYearID(), "Term II");

			request.setAttribute("attendance1List", attendance1List);

			if (searchAcademicYear == null || searchAcademicYear == "") {

				searchAcademicYearList = daoInf.retrieveExistingAcademicYearList(userForm.getOrganizationID());
			} else {

				searchAcademicYearList = daoInf.searchAcademicYearList(searchAcademicYear,
						userForm.getOrganizationID());
			}

			// getting organizationList value
			organizationList = daoInf.getOrganization();

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure academic year Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	public String renderManageStandard() throws Exception {

		daoInf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		return SUCCESS;
	}

	public String searchStandard() throws Exception {

		daoInf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting SubjectList value
		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		searchStandardList = daoInf.searchStandardList(form.getSearchStandard(), userForm.getOrganizationID());

		if (searchStandardList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Standard found for : " + form.getSearchStandard());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllStandards() throws Exception {
		daoInf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting SubjectList value
		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		/*
		 * Retrieving existing Organization list from table PVFrequency
		 */
		searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

		if (searchStandardList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Standard found. Please add new Standard.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditStandard() throws Exception {
		daoInf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting SubjectList value
		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		/* retriving present subject List from Standard table */
		// request.setAttribute("subject",
		// daoInf.retrievesubjectListbyStandardID(form.getStandardID()));

		String searchStandard = form.getSearchStandard();

		StandardEditList = daoInf.retrieveStandardListByID(form.getStandardID(), form.getSearchStandard());

		if (searchStandard == null || searchStandard == "") {

			searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

		} else {

			searchStandardList = daoInf.searchStandardList(searchStandard, userForm.getOrganizationID());

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStandard() throws Exception {
		daoInf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();

		serviceInf = new kovidRMSServiceImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting SubjectList value
		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		/*
		 * INserting Organization into PVFrequency table
		 */
		message = serviceInf.registerStandards(form, userForm.getOrganizationID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Standard configured successfully.");

			searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Standard", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Standard name already exist. Please add new standard name.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add standard name already exist Exception occurred",
					userForm.getUserID());
			return ERROR;
		} else {
			addActionError("Error while configuring Standard. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Standard Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editStandard() throws Exception {

		daoInf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();
		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting SubjectList value
		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		String searchStandard = form.getSearchStandard();

		message = serviceInf.editRegisterStandard(form, userForm.getOrganizationID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Standard udpated successfully.");

			StandardEditList = daoInf.retrieveStandardListByID(form.getStandardID(), searchStandard);

			if (searchStandard == null || searchStandard == "") {

				searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

			} else {

				searchStandardList = daoInf.searchStandardList(searchStandard, userForm.getOrganizationID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Standard", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Standard name already exist. Please update new standard name.");

			StandardEditList = daoInf.retrieveStandardListByID(form.getStandardID(), searchStandard);

			if (searchStandard == null || searchStandard == "") {

				searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

			} else {

				searchStandardList = daoInf.searchStandardList(searchStandard, userForm.getOrganizationID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit standard name already exist Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		} else {
			addActionError("Failed to udpated organization. Please check logs for more details.");

			StandardEditList = daoInf.retrieveStandardListByID(form.getStandardID(), searchStandard);

			if (searchStandard == null || searchStandard == "") {

				searchStandardList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

			} else {

				searchStandardList = daoInf.searchStandardList(searchStandard, userForm.getOrganizationID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Standard Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	public String deleteStandard() throws Exception {
		daoInf = new LoginDAOImpl();

		StuduntDAOInf daoInf1 = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting SubjectList value
		subjectTypeList = daoInf1.retrieveScholasticsubjectList(userForm.getOrganizationID());
		CoscholasticsubjectTypeList = daoInf1.retrieveCoscholasticsubjectList(userForm.getOrganizationID());
		AcademicsubjectTypeList = daoInf1.retrieveAcademicsubjectList(userForm.getOrganizationID());
		PersonalitysubjectTypeList = daoInf1.retrievePersonalitysubjectList(userForm.getOrganizationID());
		PhysicalsubjectTypeList = daoInf1.retrievePhysicalsubjectTypeList(userForm.getOrganizationID());
		CreativesubjectTypeList = daoInf1.retrieveCreativesubjectTypeList(userForm.getOrganizationID());
		CompulsoryActivitiesList = daoInf1.retrieveCompulsorysubjectTypeList(userForm.getOrganizationID());

		String searchStandard = form.getSearchStandard();

		message = daoInf.rejectStandard(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Standard disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Standard disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf.insertAudit(request.getRemoteAddr(), "Standard Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (form.getSearchStandard() == null || form.getSearchStandard() == "") {

				StandardEditList = daoInf.retrieveExistingStandardList(userForm.getOrganizationID());

				if (StandardEditList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Standard found. Please add new Standard.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchStandardList = daoInf.searchStandardList(form.getSearchStandard(), userForm.getOrganizationID());

				if (searchStandardList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Standard with name '" + form.getSearchStandard() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating Standard activity status to Inactive into database.");
			addActionError("Failed to disable Standard.");
			return ERROR;
		}
	}

	public String renderDivision() throws Exception {
		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting StandardList value
		StandardList = daoInf.getStandard(userForm.getOrganizationID());

		searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchDivision() throws Exception {

		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		searchDivisionList = daoInf.searchDivisionList(form.getSearchDivision(), userForm.getOrganizationID());

		// getting StandardList value
		StandardList = daoInf.getStandard(userForm.getOrganizationID());

		if (searchDivisionList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Division found for : " + form.getSearchDivision());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllDivisions() throws Exception {
		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting StandardList value
		StandardList = daoInf.getStandard(userForm.getOrganizationID());
		/*
		 * Retrieving existing AcademicYear list from table PVFrequency
		 */
		searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

		if (searchDivisionList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Division found. Please add new Division.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditDivision() throws Exception {
		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		String searchDivision = form.getSearchDivision();

		// getting StandardList value
		StandardList = daoInf.getStandard(userForm.getOrganizationID());

		DivisionEditList = daoInf.retrieveDivisionListByID(form.getDivisionID(), form.getSearchDivision());

		if (searchDivision == null || searchDivision == "") {

			searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

		} else {

			if (searchDivision.isEmpty()) {
				searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());
			} else {
				searchDivisionList = daoInf.searchDivisionList(searchDivision, userForm.getOrganizationID());
			}

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addDivision() throws Exception {
		daoInf = new LoginDAOImpl();
		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		/*
		 * INserting Organization into PVFrequency table
		 */
		// message = daoInf.insertDivision(form);
		message = serviceInf.registerDivision(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Division configured successfully.");

			searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

			// getting DivisionList value
			DivisionList = daoInf.getDivision(form.getStandardID());

			// getting StandardList value
			StandardList = daoInf.getStandard(userForm.getOrganizationID());

			daoInf.insertAudit(request.getRemoteAddr(), "Configure Division", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Division name with same standard already exist. Please add new name.");

			searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

			// getting DivisionList value
			DivisionList = daoInf.getDivision(form.getStandardID());

			// getting StandardList value
			StandardList = daoInf.getStandard(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add division name already exist Exception occurred",
					userForm.getUserID());
			return ERROR;
		} else {
			addActionError("Error while configuring Division. Please check logs for more details.");

			searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

			// getting DivisionList value
			DivisionList = daoInf.getDivision(form.getStandardID());

			// getting StandardList value
			StandardList = daoInf.getStandard(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Division Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editDivision() throws Exception {

		daoInf = new LoginDAOImpl();
		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchDivision = form.getSearchDivision();

		// getting StandardList value
		StandardList = daoInf.getStandard(userForm.getOrganizationID());

		// message = daoInf.updateDivision(form, realPath, request);
		message = serviceInf.registerupdateDivision(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Division udpated successfully.");

			DivisionEditList = daoInf.retrieveDivisionListByID(form.getDivisionID(), searchDivision);

			if (searchDivision == null || searchDivision == "") {

				searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

			} else {

				searchDivisionList = daoInf.searchDivisionList(searchDivision, userForm.getOrganizationID());

			}

			// getting StandardList value
			StandardList = daoInf.getStandard(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Division", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Division name with same Standard already exist. Please add new name.");

			DivisionEditList = daoInf.retrieveDivisionListByID(form.getDivisionID(), searchDivision);

			if (searchDivision == null || searchDivision == "") {

				searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

			} else {

				searchDivisionList = daoInf.searchDivisionList(searchDivision, userForm.getOrganizationID());

			}

			// getting StandardList value
			StandardList = daoInf.getStandard(userForm.getOrganizationID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit division name already exist Exception occurred",
					userForm.getUserID());
			return ERROR;
		} else {
			addActionError("Failed to udpated Academic Year. Please check logs for more details.");

			DivisionEditList = daoInf.retrieveDivisionListByID(form.getDivisionID(), searchDivision);

			if (searchDivision == null || searchDivision == "") {

				searchDivisionList = daoInf.retrieveExistingDivisionList(userForm.getOrganizationID());

			} else {

				searchDivisionList = daoInf.searchDivisionList(searchDivision, userForm.getOrganizationID());

			}

			// getting StandardList value
			StandardList = daoInf.getStandard(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Configure Division Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	public String renderManageTimeTable() throws Exception {

		daoInf = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		TimeTableList = daoInf.retriveTimeTableList(userForm.getAcademicYearID());

		request.setAttribute("TimeTableList", TimeTableList);

		return SUCCESS;

	}

	public String addTimeTable() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.addTimeTable(form);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Time Table List configured successfully.");

			/* retriving present AcademicYearClassList from AYClass table */
			TimeTableList = daoInf.retriveTimeTableList(userForm.getAcademicYearID());

			request.setAttribute("TimeTableList", TimeTableList);

			daoInf.insertAudit(request.getRemoteAddr(), "Configure Time Table List", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Time Table List. Please check logs for more details.");

			/* retriving present AcademicYearClassList from AYClass table */
			TimeTableList = daoInf.retriveTimeTableList(userForm.getAcademicYearID());

			request.setAttribute("TimeTableList", TimeTableList);

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Configure Time Table List Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void deleteAttendanceRow() throws Exception {
		LoginDAOInf daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteAttendanceRow(form.getDeleteID(), ActivityStatus.ACTIVE);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while deleting the row");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void checkAttendanceAvailability() throws Exception {
		LoginDAOInf daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		try {

			/*
			 * deleting details accordingly.
			 */
			System.out.println("values: " + form.getMonth() + " term: " + form.getTerm() + " standardID: "
					+ form.getStandardID() + " academicYearID: " + form.getAcademicYearID());
			values = daoInf.checkAttendanceAvailability(form.getMonth(), form.getTerm(), form.getStandardID(),
					form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while checking the details");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	public void retrieveScholasticSubjectListForStandard() throws Exception {
		LoginDAOInf daoInf = new LoginDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		try {

			values = daoInf.retrieveSubjectListForStandard(form.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on StandardID.");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logoutUser() throws Exception {

		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// Inserting values into Audit table for user action
		daoInf.insertAudit(request.getRemoteAddr(), "Logout", userForm.getId());

		sessionAttribute.remove("USER");
		addActionMessage("You are successfully logged out");
		if (sessionAttribute.get("USER") == "" || sessionAttribute.get("USER") == null) {
			System.out.println("session removed");
		} else {
			System.out.println("error removing session object");
		}
		return SUCCESS;
	}

	public String changeOrganization() throws Exception {

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		System.out.println("organizationID: " + form.getChangedOrganizationID());

		int changedOrganizationID = form.getChangedOrganizationID();

		userForm.setOrganizationID(changedOrganizationID);

		System.out.println("Organization chagned successfully.");

		return SUCCESS;
	}

	public LoginForm getModel() {
		// TODO Auto-generated method stub
		return form;
	}

	/**
	 * @return the compulsoryActivitiesList
	 */
	public HashMap<Integer, String> getCompulsoryActivitiesList() {
		return CompulsoryActivitiesList;
	}

	/**
	 * @param compulsoryActivitiesList the compulsoryActivitiesList to set
	 */
	public void setCompulsoryActivitiesList(HashMap<Integer, String> compulsoryActivitiesList) {
		CompulsoryActivitiesList = compulsoryActivitiesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public List<LoginForm> getSearchOrganizationList() {
		return searchOrganizationList;
	}

	public void setSearchOrganizationList(List<LoginForm> searchOrganizationList) {
		this.searchOrganizationList = searchOrganizationList;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public List<LoginForm> getSearchUserList() {
		return searchUserList;
	}

	public void setSearchUserList(List<LoginForm> searchUserList) {
		this.searchUserList = searchUserList;
	}

	public List<LoginForm> getSignedUpUserList() {
		return signedUpUserList;
	}

	public void setSignedUpUserList(List<LoginForm> signedUpUserList) {
		this.signedUpUserList = signedUpUserList;
	}

	public List<LoginForm> getOrganizationEditList() {
		return OrganizationEditList;
	}

	public List<LoginForm> getSearchAcademicYearList() {
		return searchAcademicYearList;
	}

	public void setSearchAcademicYearList(List<LoginForm> searchAcademicYearList) {
		this.searchAcademicYearList = searchAcademicYearList;
	}

	public List<LoginForm> getAcademicYearEditList() {
		return AcademicYearEditList;
	}

	public void setAcademicYearEditList(List<LoginForm> academicYearEditList) {
		AcademicYearEditList = academicYearEditList;
	}

	public List<LoginForm> getSearchviewStandardList() {
		return searchviewStandardList;
	}

	public void setSearchviewStandardList(List<LoginForm> searchviewStandardList) {
		this.searchviewStandardList = searchviewStandardList;
	}

	public HashMap<Integer, String> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(HashMap<Integer, String> organizationList) {
		this.organizationList = organizationList;
	}

	public void setOrganizationEditList(List<LoginForm> organizationEditList) {
		OrganizationEditList = organizationEditList;
	}

	public List<LoginForm> getSearchStandardList() {
		return searchStandardList;
	}

	public void setSearchStandardList(List<LoginForm> searchStandardList) {
		this.searchStandardList = searchStandardList;
	}

	public List<LoginForm> getStandardEditList() {
		return StandardEditList;
	}

	public void setStandardEditList(List<LoginForm> standardEditList) {
		StandardEditList = standardEditList;
	}

	public HashMap<Integer, String> getStandardList() {
		return StandardList;
	}

	public void setStandardList(HashMap<Integer, String> standardList) {
		StandardList = standardList;
	}

	public List<LoginForm> getSearchDivisionList() {
		return searchDivisionList;
	}

	public void setSearchDivisionList(List<LoginForm> searchDivisionList) {
		this.searchDivisionList = searchDivisionList;
	}

	public List<LoginForm> getDivisionEditList() {
		return DivisionEditList;
	}

	public void setDivisionEditList(List<LoginForm> divisionEditList) {
		DivisionEditList = divisionEditList;
	}

	public List<LoginForm> getAcademicYearList() {
		return AcademicYearList;
	}

	public void setAcademicYearList(List<LoginForm> academicYearList) {
		AcademicYearList = academicYearList;
	}

	public HashMap<Integer, String> getDivisionList() {
		return DivisionList;
	}

	public void setDivisionList(HashMap<Integer, String> divisionList) {
		DivisionList = divisionList;
	}

	public HashMap<Integer, String> getAppuserList() {
		return AppuserList;
	}

	public void setAppuserList(HashMap<Integer, String> appuserList) {
		AppuserList = appuserList;
	}

	public List<LoginForm> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<LoginForm> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public HashMap<Integer, String> getSubjectTypeList() {
		return subjectTypeList;
	}

	public void setSubjectTypeList(HashMap<Integer, String> subjectTypeList) {
		this.subjectTypeList = subjectTypeList;
	}

	public HashMap<Integer, String> getCoscholasticsubjectTypeList() {
		return CoscholasticsubjectTypeList;
	}

	public void setCoscholasticsubjectTypeList(HashMap<Integer, String> coscholasticsubjectTypeList) {
		CoscholasticsubjectTypeList = coscholasticsubjectTypeList;
	}

	public HashMap<Integer, String> getAcademicsubjectTypeList() {
		return AcademicsubjectTypeList;
	}

	public void setAcademicsubjectTypeList(HashMap<Integer, String> academicsubjectTypeList) {
		AcademicsubjectTypeList = academicsubjectTypeList;
	}

	public HashMap<Integer, String> getPersonalitysubjectTypeList() {
		return PersonalitysubjectTypeList;
	}

	public void setPersonalitysubjectTypeList(HashMap<Integer, String> personalitysubjectTypeList) {
		PersonalitysubjectTypeList = personalitysubjectTypeList;
	}

	public HashMap<Integer, String> getPhysicalsubjectTypeList() {
		return PhysicalsubjectTypeList;
	}

	public void setPhysicalsubjectTypeList(HashMap<Integer, String> physicalsubjectTypeList) {
		PhysicalsubjectTypeList = physicalsubjectTypeList;
	}

	public HashMap<Integer, String> getCreativesubjectTypeList() {
		return CreativesubjectTypeList;
	}

	public void setCreativesubjectTypeList(HashMap<Integer, String> creativesubjectTypeList) {
		CreativesubjectTypeList = creativesubjectTypeList;
	}

	public List<LoginForm> getAttendance1List() {
		return attendance1List;
	}

	public void setAttendance1List(List<LoginForm> attendance1List) {
		this.attendance1List = attendance1List;
	}

	public HashMap<Integer, String> getExamList() {
		return ExamList;
	}

	public void setExamList(HashMap<Integer, String> examList) {
		ExamList = examList;
	}

	public List<LoginForm> getTimeTableList() {
		return TimeTableList;
	}

	public void setTimeTableList(List<LoginForm> timeTableList) {
		TimeTableList = timeTableList;
	}

	public HashMap<Integer, String> getLibraryList() {
		return LibraryList;
	}

	public void setLibraryList(HashMap<Integer, String> libraryList) {
		LibraryList = libraryList;
	}

}
