package com.kovidRMS.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
import com.kovidRMS.util.AWSS3Connect;
import com.kovidRMS.util.ActivityStatus;
import com.kovidRMS.util.ConfigXMLUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.kovidRMS.util.ExcelUtil;

public class StudentAction extends ActionSupport
		implements ModelDriven<StudentForm>, SessionAware, ServletResponseAware {
	String message = null;
	LoginForm form = new LoginForm();
	StudentForm studform = new StudentForm();

	ExcelUtil excelUtil = null;

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	private Map<String, Object> sessionAttribute = null;

	HashMap<Integer, String> StandardList = null;

	HashMap<Integer, String> DivisionList = null;

	LinkedHashMap<String, String> MonthList = null;

	HashMap<Integer, String> SubjectList = null;

	HashMap<Integer, String> AcademicYearNameList = null;

	HashMap<Integer, String> CommutationList = null;

	HashMap<Integer, String> SubjectListByStandard = null;

	HashMap<String, Integer> NewExaminationList = null;

	List<StudentForm> StudentList = null;

	HashMap<String, String> StandardDivisionList = null;

	HashMap<String, String> ExamList = null;

	List<StudentForm> searchStudentList = null;
	List<StudentForm> signedUpStudentList = null;

	List<StudentForm> searchSubjectList = null;
	List<StudentForm> SubjectEditList;

	List<StudentForm> ActivityList;

	List<StudentForm> searchCommutationList = null;
	List<StudentForm> CommutationEditList;

	HashMap<String, String> CreativeActivitiesList = null;

	HashMap<String, String> PhysicalActivitiesList = null;

	HashMap<String, String> CompulsoryActivitiesList = null;

	List<StudentForm> siblingList = null;

	List<StudentForm> ConditionList = null;

	List<StudentForm> studentsList = null;

	List<String> studentsBasedCustomReportList = null;

	List<Integer> StudentIDList = null;

	List<StudentForm> studentsBasedExamCustomReportList = null;

	List<String> studentFinalExamCustomReportList = null;

	List<StudentForm> NewStudentsList = null;

	public List<StudentForm> getConditionList() {
		return ConditionList;
	}

	public void setConditionList(List<StudentForm> conditionList) {
		ConditionList = conditionList;
	}

	public List<StudentForm> getSiblingList() {
		return siblingList;
	}

	public void setSiblingList(List<StudentForm> siblingList) {
		this.siblingList = siblingList;
	}

	public HashMap<String, String> getCreativeActivitiesList() {
		return CreativeActivitiesList;
	}

	public void setCreativeActivitiesList(HashMap<String, String> creativeActivitiesList) {
		CreativeActivitiesList = creativeActivitiesList;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response;

	LoginDAOInf daoInf1 = null;
	StuduntDAOInf daoInf = new StudentDAOImpl();
	kovidRMSServiceInf serviceInf = null;
	/* setters of form */

	public String renderaddStudent() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStudent() throws Exception {
		serviceInf = new kovidRMSServiceImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting CreativeActivitiesList and PhysicalActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		request.setAttribute("classTeacherCheck", "Yes");

		message = serviceInf.addStudent(studform, studform.getId());

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("New student added successfully.");

			// getting StandardList value
			StandardList = daoInf1.getStandard(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Add Student", form.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			// System.out.println("The entered Aadhar No already exists into Student table.
			// Try with different Aadhar No.");
			addActionError("Aadhar No already exists. Please use different Aadhar No.");

			// getting StandardList value
			StandardList = daoInf1.getStandard(userForm.getOrganizationID());

			return INPUT;
		} else {

			addActionError("Failed to register student.Please check server logs for more details.");

			// getting StandardList value
			StandardList = daoInf1.getStandard(userForm.getOrganizationID());
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Add Student Exception occurred", form.getUserID());

			return ERROR;
		}
	}

	public String renderManageEditStudentList() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchStudent() throws Exception {

		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		if (userForm.getRole().equals("administrator")) {

			searchStudentList = daoInf.searchStudent(studform.getSearchStudentName(), studform.getStandardID(),
					studform.getDivisionID());
		} else {

			searchStudentList = daoInf.searchStudent1(studform.getSearchStudentName(), userForm.getUserID());
		}

		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());
		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());
		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());
		/*
		 * Checking whether userList is empty or not, if empty give error message saying
		 * User with name not found
		 */
		request.setAttribute("classTeacherCheck", "Yes");

		if (searchStudentList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "Student with name '" + studform.getSearchStudentName() + "' not found.";

			addActionError(errorMsg);

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditStudentList() throws Exception {
		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		if (userForm.getRole().equals("administrator")) {

			signedUpStudentList = daoInf.retriveEditStudentList(studform.getStandardID(), studform.getDivisionID(),
					studform.getSearchStudentName());

		} else {

			signedUpStudentList = daoInf.retriveEditStudentList1(userForm.getUserID(), studform.getSearchStudentName());
		}
		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());
		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		/* retriving present CreativeActivities List from Registration table */
		request.setAttribute("CreativeActivities", daoInf.retriveCreativeActivities(studform.getStudentID()));

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		/* retriving present PhysicalActivities List from Registration table */
		request.setAttribute("PhysicalActivities", daoInf.retrivePhysicalActivities(studform.getStudentID()));

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		request.setAttribute("CompulsoryActivities", daoInf.retriveCompulsoryActivities(studform.getStudentID()));

		request.setAttribute("classTeacherCheck", "Yes");

		if (signedUpStudentList.size() > 0) {

			request.setAttribute("userListEnable", "userListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No student found. Please add new student.";

			addActionError(errorMsg);

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditStudent() throws Exception {

		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		signedUpStudentList = daoInf.retreiveStudentDetailByStudentID(studform.getStudentID(),
				studform.getSearchStudentName());

		String dateOfBirth = daoInf.retreiveStudentsDateOfBirth(studform.getStudentID());

		request.setAttribute("dateOfBirth", dateOfBirth);

		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());
		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		// getting siblingID, siblingList value
		String siblingID = daoInf.retrieveSiblingID(studform.getStudentID());

		siblingList = daoInf.retrivesiblingList(siblingID);

		// getting ConditionList value
		ConditionList = daoInf.retriveConditionList(studform.getStudentID());

		request.setAttribute("ConditionList", ConditionList);
		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		/* retriving present CreativeActivities List from Registration table */
		request.setAttribute("CreativeActivities", daoInf.retriveCreativeActivities(studform.getStudentID()));

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		/* retriving present PhysicalActivities List from Registration table */
		request.setAttribute("PhysicalActivities", daoInf.retrivePhysicalActivities(studform.getStudentID()));

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		request.setAttribute("CompulsoryActivities", daoInf.retriveCompulsoryActivities(studform.getStudentID()));

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("activityStatus", signedUpStudentList);

		String commutationMode = daoInf.retrieveCommutationMode(studform.getStudentID());

		System.out.println("Commutation Mode :: " + commutationMode);

		request.setAttribute("commutationMode", commutationMode);

		return SUCCESS;
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String editStudent() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		daoInf = new StudentDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting clinicID from session
		 */

		// getting organizationList value
		CommutationList = daoInf.getCommutation(userForm.getOrganizationID());

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		/* retriving present CreativeActivities List from Registration table */
		request.setAttribute("CreativeActivities", daoInf.retriveCreativeActivities(studform.getStudentID()));

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		/* retriving present PhysicalActivities List from Registration table */
		request.setAttribute("PhysicalActivities", daoInf.retrivePhysicalActivities(studform.getStudentID()));

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		request.setAttribute("CompulsoryActivities", daoInf.retriveCompulsoryActivities(studform.getStudentID()));

		request.setAttribute("classTeacherCheck", "Yes");

		String dateOfBirth = daoInf.retreiveStudentsDateOfBirth(studform.getStudentID());

		request.setAttribute("dateOfBirth", dateOfBirth);

		LoginForm loginForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.editStudentDetail(studform);
		if (message.equalsIgnoreCase("success")) {

			System.out.println("Student detail updates successfully.");
			System.out.println("Student id after everything is :::: " + studform.getStudentID());
			addActionMessage("Successfully updated Student details.");

			signedUpStudentList = daoInf.retreiveStudentDetailByStudentID(studform.getStudentID(),
					studform.getSearchStudentName());

			String siblingID = daoInf.retrieveSiblingID(studform.getStudentID());

			if (siblingID.startsWith(",")) {
				siblingID = siblingID.substring(1);
			}

			siblingList = daoInf.retrivesiblingList(siblingID);

			// getting ConditionList value
			ConditionList = daoInf.retriveConditionList(studform.getStudentID());

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Student", loginForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Student with same Aadhaar No already exists. Try using different credentials.");
			signedUpStudentList = daoInf.retreiveStudentDetailByStudentID(studform.getStudentID(),
					studform.getSearchStudentName());

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Student Exception occurred", loginForm.getUserID());

			String siblingID = daoInf.retrieveSiblingID(studform.getStudentID());

			siblingList = daoInf.retrivesiblingList(siblingID);

			// getting ConditionList value
			ConditionList = daoInf.retriveConditionList(studform.getStudentID());

			return INPUT;
		} else {

			System.out.println("Exception occurred while updating Student detail.");
			addActionError("Exception occurred while updating Student detail. Please check the logs for more details.");
			signedUpStudentList = daoInf.retreiveStudentDetailByStudentID(studform.getStudentID(),
					studform.getSearchStudentName());

			String siblingID = daoInf.retrieveSiblingID(studform.getStudentID());

			siblingList = daoInf.retrivesiblingList(siblingID);

			// getting ConditionList value
			ConditionList = daoInf.retriveConditionList(studform.getStudentID());

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Student Exception occurred", loginForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteStudent() throws Exception {
		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		System.out.println("Student ID to be disabled..." + studform.getStudentID());

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		request.setAttribute("classTeacherCheck", "Yes");

		message = daoInf.rejectStudent(studform);

		if (message.equalsIgnoreCase("success")) {

			System.out.println("Student disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Student disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Student Disabled", userForm.getUserID());

			return SUCCESS;
		} else {
			System.out.println("Error while updating user activity status to Inactive into database.");
			addActionError("Failed to disable Student.");
			return ERROR;
		}
	}

	public String rendertransferStudent() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardList = daoInf1.getStandardExceptXStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		request.setAttribute("standardID", studform.getStandardID());

		// retriving Draft Academic Year ID value
		int DraftAcademicYearID = 0;
		DraftAcademicYearID = daoInf2.retriveInactiveAcademicYearID(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			if (DraftAcademicYearID == 0) {
				addActionError("Please add draft academic year first");
				request.setAttribute("classTeacherCheck", "No");
			} else {
				request.setAttribute("classTeacherCheck", "Yes");
			}

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;
	}

	public String loadTransferStudent() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting StandardList value
		StandardList = daoInf1.getStandardExceptXStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		request.setAttribute("standardID", studform.getStandardID());

		/* retriving present AcademicYearClassList from AYClass table */
		StudentList = daoInf.retriveStudentList(studform.getStandardID(), studform.getOldAyID());

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

		String standardValue = daoInf.retrieveStandardByStandardID(studform.getStandardID());

		int index = daoInf.findIndex(standardString, standardValue);

		System.out.println("index: " + index + "....." + standardValue);

		String indexValue = "";

		if (standardValue.equals("IX")) {
			indexValue = standardString[standardString.length - 1];
		} else {
			indexValue = standardString[index + 1];
		}

		System.out.println("indexValue: " + indexValue);

		int nextStandardID = daoInf.retrieveStandardIDByStandardName(indexValue);

		request.setAttribute("nextStandardID", nextStandardID);

		request.setAttribute("nextStandard", indexValue);

		request.setAttribute("StudentList", StudentList);

		request.setAttribute("standardID", studform.getStandardID());

		request.setAttribute("classTeacherCheck", "Yes");

		int divisionCount = daoInf.retrieveDivisionCountByStandardID(nextStandardID);

		int draftAYClassIDCount = daoInf.retrieveDraftAyclassIDCountByStandardID(nextStandardID, studform.getAyID());

		if (StudentList.size() > 0) {

			if (divisionCount != 0) {
				if (divisionCount == draftAYClassIDCount) {
					request.setAttribute("loadStudentSearch", "Enabled");
				} else {

					addActionError(
							"Please configure " + indexValue + " standards all divisions for draft academic year.");

					request.setAttribute("loadStudentSearch", "Disabled");
				}
			}
		} else {
			addActionError("Students data are already transferred.");
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transferStudent() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting StandardList value
		StandardList = daoInf1.getStandardExceptXStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		/* retriving present AcademicYearClassList from AYClass table */
		StudentList = daoInf.retriveStudentList(studform.getStandardID(), studform.getOldAyID());

		request.setAttribute("classTeacherCheck", "Yes");

		request.setAttribute("StudentList", StudentList);

		request.setAttribute("standardID", studform.getStandardID());

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = serviceInf.transferStudent(studform);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Student Transfered successfully.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Student Transfered List", userForm.getUserID());

			return SUCCESS;

		} else {
			addActionError("Error while Transfering Student. Please check logs for more details.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Transfering Student Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	public String renderBulkUpdateStudent() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

			request.setAttribute("StandardID", 0);

			request.setAttribute("DivisionID", 0);

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;

	}

	public String bulkUpdateStudentsByAcademicYear() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String ActivityStatus = daoInf1.getActivityStatus(studform.getAcademicYearID());

		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacherNew(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);

		if (check) {

			String StandardName = daoInf1.retrieveStandardName1(userForm.getUserID(), studform.getAcademicYearID(),
					ActivityStatus);
			request.setAttribute("StandardName", StandardName);

			String DivisionName = daoInf1.retrieveDivisionName1(userForm.getUserID(), studform.getAcademicYearID(),
					ActivityStatus);
			request.setAttribute("DivisionName", DivisionName);

			int StandardID = daoInf1.retrieveStandardID1(userForm.getUserID(), studform.getAcademicYearID(),
					ActivityStatus);
			request.setAttribute("StandardID", StandardID);

			int DivisionID = daoInf1.retrieveDivisionID1(userForm.getUserID(), studform.getAcademicYearID(),
					ActivityStatus);
			request.setAttribute("DivisionID", DivisionID);

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		request.setAttribute("AcademicYearCheck", "Yes");

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	public String loadBulkStudent() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String ActivityStatus = daoInf1.getActivityStatus(studform.getAcademicYearID());
		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		request.setAttribute("classTeacherCheck", "Yes");

		request.setAttribute("AcademicYearCheck", "Yes");

		int StandardID = daoInf1.retrieveStandardID1(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);

		request.setAttribute("StandardID", StandardID);

		int DivisionID = daoInf1.retrieveDivisionID1(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);
		request.setAttribute("DivisionID", DivisionID);

		String StandardName = daoInf1.retrieveStandardName1(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);
		request.setAttribute("StandardName", StandardName);

		String DivisionName = daoInf1.retrieveDivisionName1(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);
		request.setAttribute("DivisionName", DivisionName);

		/* retriving present Bulk Students List from Student table */
		StudentList = daoInf.retriveBulkStudentList1(StandardID, DivisionID, studform.getAcademicYearID(),
				ActivityStatus);

		request.setAttribute("StudentList", StudentList);

		request.setAttribute("loadStudentSearch", "Enabled");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bulkUpdateStudent() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		// getting CreativeActivitiesList value
		CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

		// getting PhysicalActivitiesList value
		PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

		CompulsoryActivitiesList = daoInf.getCompulsoryActivitiesList(userForm.getOrganizationID());

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String ActivityStatus = daoInf1.getActivityStatus(studform.getAcademicYearID());

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		request.setAttribute("classTeacherCheck", "Yes");

		int StandardID = daoInf1.retrieveStandardID1(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);
		request.setAttribute("StandardID", StandardID);

		int DivisionID = daoInf1.retrieveDivisionID1(userForm.getUserID(), studform.getAcademicYearID(),
				ActivityStatus);
		request.setAttribute("DivisionID", DivisionID);

		message = serviceInf.bulkUpdateStudent(studform, ActivityStatus);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Bulk Students Updated successfully.");

			// getting StandardList value
			StandardList = daoInf1.getStandard(userForm.getOrganizationID());

			// getting CreativeActivitiesList value
			CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

			// getting PhysicalActivitiesList value
			PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

			/* retriving present Bulk Students List from Student table */
			StudentList = daoInf.retriveBulkStudentList1(StandardID, DivisionID, studform.getAcademicYearID(),
					ActivityStatus);

			request.setAttribute("StudentList", StudentList);

			request.setAttribute("loadStudentSearch", "Enabled");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Bulk Students Updated List", userForm.getUserID());

			return SUCCESS;

		} else {

			// getting StandardList value
			StandardList = daoInf1.getStandard(userForm.getOrganizationID());
			// getting CreativeActivitiesList value
			CreativeActivitiesList = daoInf.getCreativeActivitiesList(userForm.getOrganizationID());

			// getting PhysicalActivitiesList value
			PhysicalActivitiesList = daoInf.getPhysicalActivitiesList(userForm.getOrganizationID());

			/* retriving present Bulk Students List from Student table */
			StudentList = daoInf.retriveBulkStudentList1(StandardID, DivisionID, studform.getAcademicYearID(),
					ActivityStatus);

			request.setAttribute("StudentList", StudentList);

			request.setAttribute("loadStudentSearch", "Enabled");

			addActionError("Error while Updating Bulk Students. Please check logs for more details.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Updating Bulk Student Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllStudents() throws Exception {
		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(studform.getStandardID());

		/* retriving present AcademicYearClassList from AYClass table */
		StudentList = daoInf.retriveStudentList(studform.getStandardID(), studform.getAyID());

		request.setAttribute("StudentList", StudentList);

		if (StudentList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Student List found. Please add new Student.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchSubjects() throws Exception {

		daoInf = new StudentDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		searchSubjectList = daoInf.searchSubjectList(studform.getSearchSubject(), userForm.getOrganizationID());

		request.setAttribute("searchSubjectList", searchSubjectList);

		if (searchSubjectList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Subject found for : " + studform.getSearchSubject());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllSubjects() throws Exception {
		daoInf = new StudentDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		/*
		 * Retrieving existing Organization list from table PVFrequency
		 */
		searchSubjectList = daoInf.retrieveExistingSubjectList(userForm.getOrganizationID());

		request.setAttribute("searchSubjectList", searchSubjectList);

		if (searchSubjectList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Subject found. Please add new Subject.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditSubject() throws Exception {
		daoInf = new StudentDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchSubject = studform.getSearchSubject();

		String SubjectType = studform.getSubjectType();
		request.setAttribute("SubjectType", SubjectType);

		String SubjectName = studform.getName();
		request.setAttribute("SubjectName", SubjectName);

		// getting ConditionList value
		ActivityList = daoInf.retriveActivityList(studform.getSubjectID());

		request.setAttribute("ActivityList", ActivityList);

		SubjectEditList = daoInf.retrieveSubjectListByID(studform.getSubjectID(), searchSubject, SubjectType);

		if (searchSubject == null || searchSubject == "") {

			searchSubjectList = daoInf.retrieveExistingSubjectList(userForm.getOrganizationID());
		} else {

			searchSubjectList = daoInf.searchSubjectList(searchSubject, userForm.getOrganizationID());
		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("searchSubjectList", searchSubjectList);

		request.setAttribute("componentEdit", "edit");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addSubject() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		daoInf = new StudentDAOImpl();
		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.registerSubject(studform, userForm.getOrganizationID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Subject configured successfully.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Subject", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Name or sort order already exist. Please check add new details.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Add subject name already exist Exception occurred",
					userForm.getUserID());
			return ERROR;
		} else {
			addActionError("Error while configuring Subject. Please check logs for more details.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Subject Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editSubject() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		daoInf = new StudentDAOImpl();
		daoInf1 = new LoginDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchSubject = studform.getSearchSubject();

		String SubjectType = studform.getSubjectType();
		request.setAttribute("SubjectType", SubjectType);

		String SubjectName = studform.getName();
		request.setAttribute("SubjectName", SubjectName);

		message = serviceInf.editSubject(studform, userForm.getOrganizationID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Subject udpated successfully.");

			SubjectEditList = daoInf.retrieveSubjectListByID(studform.getSubjectID(), searchSubject, SubjectType);

			if (searchSubject == null || searchSubject == "") {

				searchSubjectList = daoInf.retrieveExistingSubjectList(userForm.getOrganizationID());
			} else {

				searchSubjectList = daoInf.searchSubjectList(searchSubject, userForm.getOrganizationID());
			}

			// getting ConditionList value
			ActivityList = daoInf.retriveActivityList(studform.getSubjectID());

			request.setAttribute("ActivityList", ActivityList);

			request.setAttribute("componentMsg", "available");

			request.setAttribute("searchSubjectList", searchSubjectList);

			request.setAttribute("componentEdit", "edit");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Subject", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {
			addActionError("Name or sort order already exist. Please check add new details.");

			SubjectEditList = daoInf.retrieveSubjectListByID(studform.getSubjectID(), searchSubject, SubjectType);

			if (searchSubject == null || searchSubject == "") {

				searchSubjectList = daoInf.retrieveExistingSubjectList(userForm.getOrganizationID());

			} else {

				searchSubjectList = daoInf.searchSubjectList(searchSubject, userForm.getOrganizationID());
			}

			// getting ConditionList value
			ActivityList = daoInf.retriveActivityList(studform.getSubjectID());

			request.setAttribute("ActivityList", ActivityList);
			request.setAttribute("componentMsg", "available");
			request.setAttribute("searchSubjectList", searchSubjectList);
			request.setAttribute("componentEdit", "edit");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit subject name already exist Exception occurred",
					userForm.getUserID());

			return ERROR;
		} else {
			addActionError("Failed to udpated Subject. Please check logs for more details.");

			SubjectEditList = daoInf.retrieveSubjectListByID(studform.getSubjectID(), searchSubject, SubjectType);

			if (searchSubject == null || searchSubject == "") {

				searchSubjectList = daoInf.retrieveExistingSubjectList(userForm.getOrganizationID());
			} else {

				searchSubjectList = daoInf.searchSubjectList(searchSubject, userForm.getOrganizationID());
			}

			// getting ConditionList value
			ActivityList = daoInf.retriveActivityList(studform.getSubjectID());

			request.setAttribute("ActivityList", ActivityList);
			request.setAttribute("componentMsg", "available");
			request.setAttribute("searchSubjectList", searchSubjectList);
			request.setAttribute("componentEdit", "edit");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Subject Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteSubject() throws Exception {
		daoInf = new StudentDAOImpl();
		daoInf1 = new LoginDAOImpl();

		System.out.println("Subject ID to be disabled..." + studform.getSubjectID());

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.rejectSubject(studform);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Subject disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Subject disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Subject Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (studform.getSearchSubject() == null || studform.getSearchSubject() == "") {

				searchSubjectList = daoInf.retrieveExistingSubjectList(userForm.getOrganizationID());

				if (searchSubjectList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Student found. Please add new user.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchSubjectList = daoInf.searchStudent(studform.getSearchSubject(), studform.getStandardID(),
						studform.getDivisionID());

				if (searchSubjectList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Subject with name '" + studform.getSearchSubject() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating Subject activity status to Inactive into database.");
			addActionError("Failed to disable Subject.");
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchCommutations() throws Exception {

		daoInf = new StudentDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		searchCommutationList = daoInf.searchCommutationList(studform.getSearchCommutation(),
				studform.getSearchCriteria(), userForm.getOrganizationID());

		if (searchCommutationList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Commutation found for : " + studform.getSearchCommutation());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllCommutations() throws Exception {
		daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		/*
		 * Retrieving existing Organization list from table PVFrequency
		 */
		searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

		if (searchCommutationList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Commutation found. Please add new Commutation.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditCommutation() throws Exception {
		daoInf = new StudentDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchCommutation = studform.getSearchCommutation();

		CommutationEditList = daoInf.retrieveCommutationListByID(studform.getCommutationID(),
				studform.getSearchCommutation());

		if (searchCommutation == null || searchCommutation == "") {

			searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

		} else {

			searchCommutationList = daoInf.searchCommutationList(searchCommutation, studform.getSearchCriteria(),
					userForm.getOrganizationID());

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
	public String addCommutation() throws Exception {
		daoInf = new StudentDAOImpl();
		daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		/*
		 * INserting Organization into PVFrequency table
		 */
		message = daoInf.insertCommutation(studform, userForm.getOrganizationID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Commutation configured successfully.");

			searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

			// getting organizationList value
			CommutationList = daoInf.getCommutation(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Commutation", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Commutation. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

			// getting organizationList value
			CommutationList = daoInf.getCommutation(userForm.getOrganizationID());

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Commutation Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editCommutation() throws Exception {

		daoInf = new StudentDAOImpl();
		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String searchCommutation = studform.getSearchCommutation();

		message = daoInf.updateCommutation(studform);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Commutation udpated successfully.");

			CommutationEditList = daoInf.retrieveCommutationListByID(studform.getCommutationID(), searchCommutation);

			if (searchCommutation == null || searchCommutation == "") {

				searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

			} else {

				searchCommutationList = daoInf.searchCommutationList(searchCommutation, studform.getSearchCriteria(),
						userForm.getOrganizationID());

			}
			/*
			 * System.out.println("...request" +request); System.out.println("...userForm+"
			 * +userForm);
			 */
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Commutation", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated Commutation. Please check logs for more details.");

			CommutationEditList = daoInf.retrieveCommutationListByID(form.getOrganizationsID(), searchCommutation);

			if (searchCommutation == null || searchCommutation == "") {

				searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

			} else {

				searchCommutationList = daoInf.searchCommutationList(searchCommutation, studform.getSearchCriteria(),
						userForm.getOrganizationID());

			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Commutation Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteCommutation() throws Exception {
		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		System.out.println("Commutation ID to be disabled..." + studform.getCommutationID());

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.rejectCommutation(studform);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Commutation disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Commutation disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Commutation Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (studform.getSearchCommutation() == null || studform.getSearchCommutation() == "") {

				searchCommutationList = daoInf.retrieveExistingCommutationList(userForm.getOrganizationID());

				if (searchCommutationList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Commutation found. Please add new Commutation.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchCommutationList = daoInf.searchCommutationList(studform.getSearchCommutation(),
						studform.getSearchCriteria(), userForm.getOrganizationID());

				if (searchCommutationList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Commutation with name '" + studform.getSearchCommutation() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating Subject activity status to Inactive into database.");
			addActionError("Failed to disable Commutation.");
			return ERROR;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void deleteMedConditionRow() throws Exception {
		daoInf = new StudentDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteMedConditionRow(studform.getMedConditionID());

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

	public void deleteActivityRow() throws Exception {
		daoInf = new StudentDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteActivityRow(studform.getActivityID());

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

	public String renderStudentAttendance() throws Exception {

		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;

	}

	public void retrieveworkingMonthForAttendance() throws Exception {
		StuduntDAOInf daoInf = new StudentDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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
			System.out.println("studform.getStandardID(): " + studform.getStandardID());
			values = daoInf.retrieveworkingMonthForAttendance(studform.getTerm(), userForm.getAcademicYearID(),
					studform.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Working Month List based on Term for AcademicYear");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String loadStudentForAttendance() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		MonthList = daoInf.retrieveMonthListByTerm(studform.getTerm(), userForm.getAcademicYearID(),
				studform.getStandardID());

		request.setAttribute("workingDays", daoInf.retrieveworkingDays(studform.getTerm(), userForm.getAcademicYearID(),
				studform.getStandardID(), studform.getWorkingMonth()));

		/*
		 * int workingDays = daoInf.retrieveworkingDays(studform.getTerm(),
		 * AcademicYearID, studform.getStandardID(),studform.getWorkingMonth());
		 */

		request.setAttribute("classTeacherCheck", "Yes");

		System.out.println("StandardID:" + studform.getStandardID() + "\nDivisionID:" + studform.getDivisionID()
				+ "\nTerm:" + studform.getTerm() + "\nWorkingMonth:" + studform.getWorkingMonth() + "\nAcademicYearID"
				+ userForm.getAcademicYearID());

		studentsList = daoInf.retrieveClassStudentListForAttendance(studform.getStandardID(), studform.getDivisionID(),
				studform.getTerm(), studform.getWorkingMonth(), userForm.getAcademicYearID());

		NewStudentsList = daoInf.retrieveExistingClassStudentListForAttendance(studform.getTerm(),
				studform.getWorkingMonth(), userForm.getAcademicYearID(), studform.getStandardID(),
				studform.getDivisionID());

		request.setAttribute("studentsList", studentsList);

		request.setAttribute("NewStudentsList", NewStudentsList);

		if (studentsList == null && NewStudentsList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentsList.size() == 0 && NewStudentsList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String addAttendance() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		serviceInf = new kovidRMSServiceImpl();

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		MonthList = daoInf.retrieveMonthListByTerm(studform.getTerm(), userForm.getAcademicYearID(),
				studform.getStandardID());

		request.setAttribute("workingDays", daoInf.retrieveworkingDays(studform.getTerm(), userForm.getAcademicYearID(),
				studform.getStandardID(), studform.getWorkingMonth()));

		request.setAttribute("classTeacherCheck", "Yes");

		message = serviceInf.addStudentAttendance(studform, userForm.getAcademicYearID());

		if (message.equals("success")) {

			addActionMessage("Details added successfully.");

			studentsList = daoInf.retrieveClassStudentListForAttendance(studform.getStandardID(),
					studform.getDivisionID(), studform.getTerm(), studform.getWorkingMonth(),
					userForm.getAcademicYearID());

			NewStudentsList = daoInf.retrieveExistingClassStudentListForAttendance(studform.getTerm(),
					studform.getWorkingMonth(), userForm.getAcademicYearID(), studform.getStandardID(),
					studform.getDivisionID());

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		} else {

			addActionError("Failed to add details. Please check server logs for more details.");

			studentsList = daoInf.retrieveClassStudentListForAttendance(studform.getStandardID(),
					studform.getDivisionID(), studform.getTerm(), studform.getWorkingMonth(),
					userForm.getAcademicYearID());

			NewStudentsList = daoInf.retrieveExistingClassStudentListForAttendance(studform.getTerm(),
					studform.getWorkingMonth(), userForm.getAcademicYearID(), studform.getStandardID(),
					studform.getDivisionID());

			request.setAttribute("loadStudentSearch", "Enabled");

			return ERROR;
		}
	}

	public String renderStudentsCustomReport() throws Exception {

		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		return SUCCESS;

	}

	public String renderExamCustomReport() throws Exception {

		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String term = studform.getTerm();

		request.setAttribute("term", term);

		String StandardName = daoInf1.retrieveStandardName(userForm.getUserID(), userForm.getAcademicYearID());

		int StandardID = daoInf1.retrieveStandardID(userForm.getUserID(), userForm.getAcademicYearID());

		String DivisionName = daoInf1.retrieveDivisionName(userForm.getUserID(), userForm.getAcademicYearID());

		int DivisionID = daoInf1.retrieveDivisionID(userForm.getUserID(), userForm.getAcademicYearID());

		// getting DivisionList value
		StandardDivisionList = daoInf1.getDivisionList(StandardID);

		ExamList = daoInf2.getExamListByTermAcademicYearID(StandardID, userForm.getAcademicYearID(), term);

		SubjectListByStandard = new HashMap<Integer, String>();

		request.setAttribute("subjectType", daoInf.retrieveSubjectType(studform.getSubjectID()));

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;
	}

	public void retrieveExamListByTermAndAcademicYearID() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		daoInf = new StudentDAOImpl();

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
			 * String TermValue = studform.getTerm();
			 * 
			 * if (TermValue.contains("%27")) { TermValue = TermValue.replaceAll("%27",
			 * "'"); }
			 */

			String TermValue = "";
			if (studform.getTerm().equals("All")) {
				TermValue = "'Term I','Term II'";
			} else {
				TermValue = "'" + studform.getTerm() + "'";
			}

			values = daoInf.retrieveExamListByTermAndAcademicYearID(userForm.getAcademicYearID(), TermValue,
					studform.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving ExamList List based on Term and AcademicYearID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void verifySubjeType() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		daoInf = new StudentDAOImpl();

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

			System.out.println("studform.getSubjectID():" + studform.getSubjectID());
			values = daoInf.verifySubjectType(studform.getSubjectID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving ExamList List based on Term and AcademicYearID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveExamTermEnd() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		daoInf = new StudentDAOImpl();

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

			String TermValue = "";
			if (studform.getTerm().equals("All")) {
				TermValue = "'Term I','Term II'";
			} else {
				TermValue = "'" + studform.getTerm() + "'";
			}

			values = daoInf.retrieveExamTermEnd(userForm.getAcademicYearID(), TermValue);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Exam Name TermEnd based on Term and AcademicYearID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectListByUserIDAndstandard() throws Exception {
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		daoInf = new StudentDAOImpl();

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

			values = daoInf.retrieveSubjectListByUserIDByStandard(studform.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and Standard");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectListBystandardForNonScholastic() throws Exception {
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		daoInf = new StudentDAOImpl();

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

			values = daoInf.retrieveSubjectListBystandardForNonScholastic(studform.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and Standard");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String loadExamCustomStudents() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String StandardName = daoInf1.retrieveStandardName(userForm.getUserID(), userForm.getAcademicYearID());

		int StandardID = daoInf1.retrieveStandardID(userForm.getUserID(), userForm.getAcademicYearID());

		String DivisionName = daoInf1.retrieveDivisionName(userForm.getUserID(), userForm.getAcademicYearID());

		int DivisionID = daoInf1.retrieveDivisionID(userForm.getUserID(), userForm.getAcademicYearID());

		String Stage = daoInf2.getStandardStageByStandardID(StandardID);

		int AYClassID = 0;

		if (studform.getDivision().contains(",")) {

			AYClassID = daoInf2.retrieveAYCLassIDForAll(studform.getStandardID(), userForm.getAcademicYearID());
		} else {
			AYClassID = daoInf2.retrieveAYCLassID(studform.getStandardID(), Integer.parseInt(studform.getDivision()),
					userForm.getAcademicYearID());
		}

		// getting DivisionList value
		StandardDivisionList = daoInf1.getDivisionList(StandardID);

		if (studform.getContainsName().equals("GradeBased")) {

			if (studform.getTerm().equals("All")) {
				ExamList = daoInf.retrieveAllExamTermEndAndAcademicYearID(userForm.getAcademicYearID());
			} else {
				ExamList = daoInf.retrieveExamTermEndAndAcademicYearID(userForm.getAcademicYearID(),
						studform.getTerm());
			}

			SubjectListByStandard = daoInf.getSubjectListByStandardForNonScholastic(studform.getStandardID());

		} else {

			if (studform.getTerm().equals("All")) {
				ExamList = daoInf2.getALLExamListByTermAcademicYearID(StandardID, userForm.getAcademicYearID());
			} else {
				ExamList = daoInf2.getExamListByTermAcademicYearID(StandardID, userForm.getAcademicYearID(),
						studform.getTerm());
			}

			SubjectListByStandard = daoInf.getSubjectListByStandard(StandardID);
		}

		/* int subjectType = daoInf.retrieveSubjectType(studform.getSubjectID()); */

		request.setAttribute("subjectType", daoInf.retrieveSubjectType(studform.getSubjectID()));

		request.setAttribute("ContainsName", studform.getContainsName());

		request.setAttribute("Value", studform.getAgeValue());

		request.setAttribute("Marks", studform.getMarks());

		request.setAttribute("ExamList", ExamList);

		request.setAttribute("classTeacherCheck", "Yes");

		String checkBoxList;
		checkBoxList = studform.getCheckBoxList();

		System.out.println("checkBoxList: " + checkBoxList);

		String ExamCustomList = "Roll_No, Student_Name";

		String finalcheckBoxList = "";

		int SECheck = 0;

		int NBCheck = 0;

		int SENBCheck = 0;

		int counterNew = 0;

		String term1CheckList = "";

		String term2CheckList = "";

		String termEndTerm1 = "";
		String unitTestTerm1 = "";
		String SEATerm1 = "";
		String NBTerm1 = "";
		String termEndTerm2 = "";
		String unitTestTerm2 = "";
		String SEATerm2 = "";
		String NBTerm2 = "";

		if (checkBoxList != null) {

			String[] newcheckBoxList = checkBoxList.split(",");

			for (int i = 0; i < newcheckBoxList.length; i++) {
				String[] array = newcheckBoxList[i].split("\\$");

				int examinationID = Integer.parseInt(array[1]);

				String term = daoInf.retrieveTermByExaminationID(examinationID);

				String examType = daoInf.retrieveExaminationType(examinationID);

				if (term.equals("Term I")) {
					if (examType.equals("Term End")) {
						termEndTerm1 = newcheckBoxList[i];
					} else if (examType.equals("Unit Test")) {
						unitTestTerm1 = newcheckBoxList[i];
					} else if (examType.equals("Subject Enrichment")) {
						SEATerm1 = SEATerm1 + "," + newcheckBoxList[i];
					} else if (examType.equals("Notebook")) {
						NBTerm1 = NBTerm1 + "," + newcheckBoxList[i];
					}

				} else {
					if (examType.equals("Term End")) {
						termEndTerm2 = newcheckBoxList[i];
					} else if (examType.equals("Unit Test")) {
						unitTestTerm2 = newcheckBoxList[i];
					} else if (examType.equals("Subject Enrichment")) {
						SEATerm2 = SEATerm2 + "," + newcheckBoxList[i];
					} else if (examType.equals("Notebook")) {
						NBTerm2 = NBTerm2 + "," + newcheckBoxList[i];
					}
				}

			}

			term1CheckList = termEndTerm1 + "," + unitTestTerm1 + SEATerm1 + NBTerm1;

			term2CheckList = termEndTerm2 + "," + unitTestTerm2 + SEATerm2 + NBTerm2;

			if (studform.getTerm().contains("All")) {
				if (term1CheckList.startsWith(",")) {
					term1CheckList = term1CheckList.substring(1);
				}

				checkBoxList = term1CheckList + "," + term2CheckList;
			} else if (studform.getTerm().contains("Term I")) {
				if (term1CheckList.startsWith(",")) {
					term1CheckList = term1CheckList.substring(1);
				}
				checkBoxList = term1CheckList;
			} else {

				if (term2CheckList.startsWith(",")) {
					term2CheckList = term2CheckList.substring(1);
				}
				checkBoxList = term2CheckList;
			}
		}

		System.out.println("...checkBoxList..." + checkBoxList);

		if (checkBoxList != null) {
			if (checkBoxList.contains(",")) {

				String[] newcheckBoxList = checkBoxList.split(",");

				for (int i = 0; i < newcheckBoxList.length; i++) {

					String[] newList = newcheckBoxList[i].split("\\$");

					if (Stage.equals("Primary")) {
						if (studform.getMarks().equals("marksObtained")) {

							finalcheckBoxList = finalcheckBoxList + "," + newList[0];
						} else {

							finalcheckBoxList = finalcheckBoxList + "," + newList[0];

							if (studform.getTotalValue().equals("Total")) {

								String examType = daoInf.retrieveExaminationType(Integer.parseInt(newList[1]));

								if (examType.equals("Subject Enrichment") || examType.equals("Notebook")) {
									counterNew++;

									if (counterNew > 2) {
										/*
										 * finalcheckBoxList = finalcheckBoxList + "," +
										 * "Best of Subject Enrichment + Notebook";
										 */
										SENBCheck = 1;
										counterNew = 0;
									}
								} /*
									 * else {
									 * 
									 * finalcheckBoxList = finalcheckBoxList + "," + newList[0]; }
									 */

							} /*
								 * else {
								 * 
								 * finalcheckBoxList = finalcheckBoxList + "," + newList[0];
								 * 
								 * }
								 */

						}

					} else {

						if (studform.getMarks().equals("marksObtained")) {
							finalcheckBoxList = finalcheckBoxList + "," + newList[0];
						} else {

							String examType = daoInf.retrieveExaminationType(Integer.parseInt(newList[1]));

							if (examType.equals("Subject Enrichment")) {

								SECheck = 1;

							}

							if (examType.equals("Notebook")) {

								NBCheck = 1;

							}
							finalcheckBoxList = finalcheckBoxList + "," + newList[0];

						}
					}
				}
			} else {

				String[] newList = checkBoxList.split("\\$");

				finalcheckBoxList = finalcheckBoxList + "," + newList[0];
			}

			if (studform.getTerm().equals("All")) {
				if (SENBCheck == 1) {
					finalcheckBoxList = finalcheckBoxList + ","
							+ "Best of Subject Enrichment + Notebook Term I,Best of Subject Enrichment + Notebook Term II";
				}
			} else {
				if (SENBCheck == 1) {
					finalcheckBoxList = finalcheckBoxList + "," + "Best of Subject Enrichment + Notebook";
				}
			}

			if (studform.getTerm().equals("All")) {
				if (NBCheck == 1 && SECheck == 1) {
					finalcheckBoxList = finalcheckBoxList + ","
							+ "Best Of Subject Enrichment Term I,Best Of Subject Enrichment Term II,Best Of Notebook I,Best Of Notebook II";
				} else if (SECheck == 1) {
					finalcheckBoxList = finalcheckBoxList + ","
							+ "Best Of Subject Enrichment Term I,Best Of Subject Enrichment Term II";
				} else if (NBCheck == 1) {
					finalcheckBoxList = finalcheckBoxList + "," + "Best Of Notebook I,Best Of Notebook II";
				}
			} else {
				if (NBCheck == 1 && SECheck == 1) {
					finalcheckBoxList = finalcheckBoxList + "," + "Best Of Subject Enrichment,Best Of Notebook";
				} else if (SECheck == 1) {
					finalcheckBoxList = finalcheckBoxList + "," + "Best Of Subject Enrichment";
				} else if (NBCheck == 1) {
					finalcheckBoxList = finalcheckBoxList + "," + "Best Of Notebook";
				}
			}

			if (studform.getTotalValue().equals("Total")) {
				ExamCustomList = ExamCustomList + finalcheckBoxList + "," + studform.getTotalValue() + "," + "Grade";

			} else {
				ExamCustomList = ExamCustomList + finalcheckBoxList;
			}
		} else {

			ExamCustomList = ExamCustomList;
		}
		request.setAttribute("ExamCustomList", ExamCustomList);

		if (checkBoxList != null) {
			String examID = "";
			if (checkBoxList.contains(",")) {
				String[] newcheckBox = checkBoxList.split(",");

				for (int i = 0; i < newcheckBox.length; i++) {

					String[] newList = newcheckBox[i].split("\\$");
					examID = examID + "," + newList[1];

					if (examID.startsWith(",")) {
						examID = examID.substring(1);
					}
				}
			} else {

				String[] newList = checkBoxList.split("\\$");
				examID = newList[1];
			}

			if (studform.getDivision().contains(",")) {

				studentsBasedExamCustomReportList = daoInf.retrievestudentsBasedAllExamCustomReportList(
						studform.getStandardID(), userForm.getAcademicYearID(), examID, studform.getSubjectID(),
						studform.getSearchFields(), studform.getContainsName(), studform.getSearchFieldsNew(),
						studform.getAgeValue());
			} else {
				studentsBasedExamCustomReportList = daoInf.retrievestudentsBasedExamCustomReportList(
						studform.getStandardID(), studform.getDivision(), userForm.getAcademicYearID(), examID,
						studform.getSubjectID(), studform.getSearchFields(), studform.getContainsName(),
						studform.getSearchFieldsNew(), studform.getAgeValue());
			}

			studentFinalExamCustomReportList = new ArrayList<String>();

			for (StudentForm studentsExamFormName : studentsBasedExamCustomReportList) {

				int SECheckCounter = 0;

				int NBCheckCounter = 0;

				int counter = 0;

				StudentForm form = new StudentForm();

				String FinalList = "";
				String FinalListNew = "";
				String FinalListMarks = "";
				String FinalSEANBMarks = "";
				String FinalSEANB1Marks = "";
				String grade = "";
				String Studentgrade = "";
				double TotalMarksGrade = 0.0;
				double TotalMarksGrade1 = 0.0;
				double Marks = 0.0;
				double NewMarks = 0.0;
				int OutOfMarks = 0;
				int FinalOutOfMarks = 0;
				double MarksNew = 0.0;
				double TotalMarks = 0;
				double FinalTotalMarks = 0;

				int seCheckCounter = 1;

				String SubjectTypeValue = daoInf.retrieveSubjectTypeValue(studform.getSubjectID());

				String NewFinalGrade = studform.getGradeValue();

				String NewGrade = "";
				String NewGradeNew = "";

				int counterAll = 1;
				String Term = "";

				if (checkBoxList != null) {
					if (checkBoxList.contains(",")) {

						String[] newcheckBoxList = checkBoxList.split(",");

						for (int i = 0; i < newcheckBoxList.length; i++) {

							if (counterAll == 1) {
								Term = "Term I";
							} else {
								Term = "Term II";
							}

							String[] newList = newcheckBoxList[i].split("\\$");

							if (studform.getContainsName().equals("GradeBased")) {

								if (studform.getDivision().contains(",")) {

									if (SubjectTypeValue.equals("Scholastic")) {

										FinalListNew = studentsExamFormName.getRollNumber() + "="
												+ studentsExamFormName.getStudentName();

										/*---------------------------------------------------------------*/
										if (Stage.equals("Primary")) {

											if (studform.getTerm().contains("All")) {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), Term, AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value2 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value3 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value4 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												outOfMarks = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												outOfMarks1 = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												outOfMarks2 = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID());

												finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

												value5 = (value2 + value3 + value4);

												String numberAsString = Double.toString(value5);

												String marks[] = numberAsString.split("\\.");

												if (Long.parseLong(marks[1]) > 0) {

													value6 = Integer.parseInt(marks[0]) + 1;

												} else {

													value6 = Integer.parseInt(marks[0]);
												}

												scaleTo = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo3 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment2"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo4 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														studform.getSubjectID());

												finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
														studform.getSubjectID(), studentsExamFormName.getStudentID(), 0,
														Term);

												totalMarksScaled = (value + value1 + finalSEAMarks);

												scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

												toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Unit Test"));

												boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0, Term,
														userForm.getAcademicYearID());

												if (termEndCheck) {
													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEANBCheck) {
													grade = "ex";
												} else {
													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
														grade = "D";
													} else {
														grade = "E";
													}
												}
												NewGrade += "=" + grade;

												NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

												if (studform.getGradeValue().equals("-1")) {

													FinalList = FinalListNew + NewGrade;
												} else {

													if (NewGradeNew.equals(NewGrade)) {

														FinalList = FinalListNew + NewGrade;
													}
												}
												counterAll++;

											} else {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

												/* double = 0D; */

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value2 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value3 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value4 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												outOfMarks = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												outOfMarks1 = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												outOfMarks2 = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID());

												finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

												value5 = (value2 + value3 + value4);

												String numberAsString = Double.toString(value5);

												String marks[] = numberAsString.split("\\.");

												if (Long.parseLong(marks[1]) > 0) {

													value6 = Integer.parseInt(marks[0]) + 1;

												} else {

													value6 = Integer.parseInt(marks[0]);
												}

												scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo3 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment2"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														studform.getSubjectID());

												finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
														studform.getSubjectID(), studentsExamFormName.getStudentID(), 0,
														studform.getTerm());

												totalMarksScaled = (value + value1 + finalSEAMarks);

												scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

												toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Unit Test"));

												boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0, studform.getTerm(),
														userForm.getAcademicYearID());

												if (termEndCheck) {

													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEANBCheck) {
													grade = "ex";
												} else {
													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
														grade = "D";
													} else {
														grade = "E";
													}
												}
												if (studform.getGradeValue().equals("-1")) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												} else {

													if (NewFinalGrade.equals(grade)) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													}
												}
											}

										} else {

											if (studform.getTerm().contains("All")) {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), Term, AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Subject Enrichment", studentsExamFormName.getStudentID(), 0,
														Term);

												value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Notebook", studentsExamFormName.getStudentID(), 0, Term);

												scaleTo = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo4 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														studform.getSubjectID());

												totalMarksScaled = (value + value1 + value2 + value4);

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												if (SubjectName.equals("Hindi")) {
													toatlScaleTo1 = totalMarksScaled;
												} else {

													if (toatlScaleTo == 25) {
														toatlScaleTo1 = (totalMarksScaled * 4);
													} else if (toatlScaleTo == 50) {

														toatlScaleTo1 = (totalMarksScaled * 2);
													} else if (toatlScaleTo > 100) {

														toatlScaleTo1 = (totalMarksScaled / 200) * 100;
													} else {
														toatlScaleTo1 = totalMarksScaled;
													}
												}

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Unit Test"));

												boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Subject Enrichment", 0, Term, userForm.getAcademicYearID());

												boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Notebook", 0, Term, userForm.getAcademicYearID());

												if (termEndCheck) {
													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEAAbsentCheck) {
													grade = "ex";
												} else if (NBAbsentCheck) {
													grade = "ex";
												} else {
													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
														grade = "D";
													} else {
														grade = "E";
													}
												}
												NewGrade += "=" + grade;

												NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

												if (studform.getGradeValue().equals("-1")) {

													FinalList = FinalListNew + NewGrade;
												} else {

													if (NewGradeNew.equals(NewGrade)) {

														FinalList = FinalListNew + NewGrade;
													}
												}
												counterAll++;

											} else {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Subject Enrichment", studentsExamFormName.getStudentID(), 0,
														studform.getTerm());

												value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Notebook", studentsExamFormName.getStudentID(), 0,
														studform.getTerm());

												scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														studform.getSubjectID());

												totalMarksScaled = (value + value1 + value2 + value4);

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												if (SubjectName.equals("Hindi")) {
													toatlScaleTo1 = totalMarksScaled;
												} else {
													if (toatlScaleTo == 25) {

														toatlScaleTo1 = (totalMarksScaled * 4);
													} else if (toatlScaleTo == 50) {

														toatlScaleTo1 = (totalMarksScaled * 2);
													} else if (toatlScaleTo > 100) {

														toatlScaleTo1 = (totalMarksScaled / 200) * 100;
													} else {
														toatlScaleTo1 = totalMarksScaled;
													}
												}

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), 0,
														NewExaminationList.get("Unit Test"));

												boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Subject Enrichment", 0, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Notebook", 0, studform.getTerm(),
														userForm.getAcademicYearID());

												if (termEndCheck) {
													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEAAbsentCheck) {
													grade = "ex";
												} else if (NBAbsentCheck) {
													grade = "ex";
												} else {

													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
														grade = "D";
													} else {
														grade = "E";
													}
												}
												if (studform.getGradeValue().equals("-1")) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												} else {

													if (NewFinalGrade.equals(grade)) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													}
												}

											}
										}

									} else {

										FinalListNew = studentsExamFormName.getRollNumber() + "="
												+ studentsExamFormName.getStudentName();

										grade = daoInf.retrievrAllGradesForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										NewGrade += "=" + grade;

										if (studform.getGradeValue().equals("-1")) {

											FinalList = FinalListNew + NewGrade;
										} else {

											if (studform.getTerm().contains("All")) {

												NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

												if (NewGradeNew.equals(NewGrade)) {

													FinalList = FinalListNew + NewGrade;
												}
											} else {

												FinalList = FinalListNew + NewGrade;
											}
										}
									}

								} else {

									if (SubjectTypeValue.equals("Scholastic")) {

										FinalListNew = studentsExamFormName.getRollNumber() + "="
												+ studentsExamFormName.getStudentName();

										/*---------------------------------------------------------------*/
										if (Stage.equals("Primary")) {

											if (studform.getTerm().contains("All")) {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), Term, AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value2 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value3 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value4 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												outOfMarks = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												outOfMarks1 = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												outOfMarks2 = daoInf.retrievAllTotalMarks(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID());

												finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

												value5 = (value2 + value3 + value4);

												String numberAsString = Double.toString(value5);

												String marks[] = numberAsString.split("\\.");

												if (Long.parseLong(marks[1]) > 0) {

													value6 = Integer.parseInt(marks[0]) + 1;

												} else {

													value6 = Integer.parseInt(marks[0]);
												}

												scaleTo = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo3 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment2"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo4 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														studform.getSubjectID());

												finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														AYClassID, Term);

												totalMarksScaled = (value + value1 + finalSEAMarks);

												scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

												// toatlScaleTo1 = totalMarksScaled;

												toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Unit Test"));

												boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID, Term,
														userForm.getAcademicYearID());

												if (termEndCheck) {
													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEANBCheck) {
													grade = "ex";
												} else {

													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
														grade = "D";
													} else {
														grade = "E";
													}
												}

												NewGrade += "=" + grade;

												NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

												if (studform.getGradeValue().equals("-1")) {

													FinalList = FinalListNew + NewGrade;
												} else {

													if (NewGradeNew.equals(NewGrade)) {

														FinalList = FinalListNew + NewGrade;
													}
												}
												counterAll++;

											} else {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value2 = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value3 = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value4 = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												outOfMarks = daoInf.retrievTotalMarks(
														NewExaminationList.get("Subject Enrichment1"),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID());

												outOfMarks1 = daoInf.retrievTotalMarks(
														NewExaminationList.get("Subject Enrichment2"),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID());

												outOfMarks2 = daoInf.retrievTotalMarks(
														NewExaminationList.get("Notebook"), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID());

												finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

												value5 = (value2 + value3 + value4);

												String numberAsString = Double.toString(value5);

												String marks[] = numberAsString.split("\\.");

												if (Long.parseLong(marks[1]) > 0) {

													value6 = Integer.parseInt(marks[0]) + 1;

												} else {

													value6 = Integer.parseInt(marks[0]);
												}

												scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo3 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment2"),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												finalSEAMarks = daoInf2.retrieveScholasticGradeList1(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														AYClassID, studform.getTerm());

												totalMarksScaled = (value + value1 + finalSEAMarks);

												scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

												toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Unit Test"));

												boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														studform.getTerm(), userForm.getAcademicYearID());

												if (termEndCheck) {

													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEANBCheck) {
													grade = "ex";
												} else {

													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
														grade = "D";
													} else {
														grade = "E";
													}
												}
												if (studform.getGradeValue().equals("-1")) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												} else {

													if (NewFinalGrade.equals(grade)) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													}
												}
											}

										} else {

											if (studform.getTerm().contains("All")) {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), Term, AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrAllMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												/*
												 * value2 = daoInf.retrievrAllMarksScaledForStudent(
												 * NewExaminationList.get("Subject Enrichment1"),
												 * studform.getSubjectID(), studform.getStandardID(), AcademicYearID,
												 * studentsExamFormName.getStudentID());
												 * 
												 * value4 = daoInf.retrievrAllMarksScaledForStudent(
												 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
												 * studform.getStandardID(), AcademicYearID,
												 * studentsExamFormName.getStudentID());
												 */

												value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Subject Enrichment", studentsExamFormName.getStudentID(),
														AYClassID, Term);

												value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Notebook", studentsExamFormName.getStudentID(), AYClassID,
														Term);

												scaleTo = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(), studform.getSubjectID());

												scaleTo4 = daoInf.retrieveAllScaledMarks(Term,
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														studform.getSubjectID());

												totalMarksScaled = (value + value1 + value2 + value4);

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												/*
												 * if(SubjectName.equals("Hindi")) { toatlScaleTo1 = totalMarksScaled;
												 * }else { if (toatlScaleTo == 50) {
												 * 
												 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1 >
												 * 100) {
												 * 
												 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else {
												 * toatlScaleTo1 = totalMarksScaled; } }
												 */

												toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Unit Test"));

												boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Subject Enrichment", AYClassID, Term,
														userForm.getAcademicYearID());

												boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Notebook", AYClassID, Term, userForm.getAcademicYearID());

												if (termEndCheck) {
													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEAAbsentCheck) {
													grade = "ex";
												} else if (NBAbsentCheck) {
													grade = "ex";
												} else {
													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
														grade = "D";
													} else {
														grade = "E";
													}
												}

												NewGrade += "=" + grade;

												NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

												if (studform.getGradeValue().equals("-1")) {

													FinalList = FinalListNew + NewGrade;
												} else {

													if (NewGradeNew.equals(NewGrade)) {

														FinalList = FinalListNew + NewGrade;
													}
												}
												counterAll++;

											} else {

												NewExaminationList = daoInf2.retrieveExaminationList(
														userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

												double value, value1, value2, value3, value4, finalOutOfMarks,
														totalMarksScaled, toatlScaleTo1 = 0;
												int marksObtained, marksObtained1, marksObtained2, marksObtained3,
														marksObtained4 = 0;
												double value5, value6, finalSEAMarks = 0D;
												int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
												int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2,
														finalSEAmarksvalue = 0;

												value = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Term End"), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												value1 = daoInf.retrievrMarksScaledForStudent(
														NewExaminationList.get("Unit Test"), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												/*
												 * value2 = daoInf.retrievrMarksScaledForStudent(
												 * NewExaminationList.get("Subject Enrichment1"),
												 * studform.getSubjectID(), studform.getStandardID(),
												 * Integer.parseInt(studform.getDivision()), AcademicYearID,
												 * studentsExamFormName.getStudentID());
												 * 
												 * value4 = daoInf.retrievrMarksScaledForStudent(
												 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
												 * studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												 * AcademicYearID, studentsExamFormName.getStudentID());
												 */

												value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Subject Enrichment", studentsExamFormName.getStudentID(),
														AYClassID, studform.getTerm());

												value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
														"Notebook", studentsExamFormName.getStudentID(), AYClassID,
														studform.getTerm());

												scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Term End"), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Unit Test"), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Subject Enrichment1"),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
														userForm.getAcademicYearID(),
														NewExaminationList.get("Notebook"), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														studform.getSubjectID());

												totalMarksScaled = (value + value1 + value2 + value4);

												toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												/*
												 * if(SubjectName.equals("Hindi")) { toatlScaleTo1 = totalMarksScaled;
												 * }else { if (toatlScaleTo == 50) {
												 * 
												 * toatlScaleTo1 = (totalMarksScaled * 2); } else if (toatlScaleTo1 >
												 * 100) {
												 * 
												 * toatlScaleTo1 = (totalMarksScaled / 200) * 100; } else {
												 * toatlScaleTo1 = totalMarksScaled; } }
												 */

												toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

												boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Term End"));

												boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
														studentsExamFormName.getStudentID(), AYClassID,
														NewExaminationList.get("Unit Test"));

												boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Subject Enrichment", AYClassID, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Notebook", AYClassID, studform.getTerm(),
														userForm.getAcademicYearID());

												if (termEndCheck) {
													grade = "ex";
												} else if (unitTestCheck) {
													grade = "ex";
												} else if (SEAAbsentCheck) {
													grade = "ex";
												} else if (NBAbsentCheck) {
													grade = "ex";
												} else {
													/* Calculating Final Grades */
													if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
														grade = "A1";
													} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
														grade = "A2";
													} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
														grade = "B1";
													} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
														grade = "B2";
													} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
														grade = "C1";
													} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
														grade = "C2";
													} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
														grade = "D";
													} else {
														grade = "E";
													}
												}

												if (studform.getGradeValue().equals("-1")) {
													FinalList += studentsExamFormName.getRollNumber() + "="
															+ studentsExamFormName.getStudentName() + "=" + grade;
												} else {

													if (NewFinalGrade.equals(grade)) {
														FinalList += studentsExamFormName.getRollNumber() + "="
																+ studentsExamFormName.getStudentName() + "=" + grade;
													}
												}
											}

										}

										/*---------------------------------------------------------------*/

									} else {

										FinalListNew = studentsExamFormName.getRollNumber() + "="
												+ studentsExamFormName.getStudentName();

										grade = daoInf.retrievrGradesForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										NewGrade += "=" + grade;

										if (studform.getGradeValue().equals("-1")) {

											FinalList = FinalListNew + NewGrade;
										} else {

											if (studform.getTerm().contains("All")) {

												NewGradeNew = "=" + NewFinalGrade + "=" + NewFinalGrade;

												if (NewGradeNew.equals(NewGrade)) {

													FinalList = FinalListNew + NewGrade;
												}
											} else {

												FinalList = FinalListNew + NewGrade;
											}
										}
									}
								}

							} else {

								FinalListNew = studentsExamFormName.getRollNumber() + "="
										+ studentsExamFormName.getStudentName();

								if (studform.getDivision().contains(",")) {
									if (Stage.equals("Primary")) {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValue().equals("Total")) {

												/*
												 * int check = daoInf.verifyExamType(Integer.parseInt(newList[1]),
												 * "Subject Enrichment");
												 */

												String exampType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												/*
												 * int ScaledMarks = daoInf.retrieveAllScaledMarks(AcademicYearID,
												 * studform.getStandardID(), studform.getSubjectID());
												 */

												String subjectList = "Subject Enrichment, Notebook";

												if (subjectList.contains(exampType)) {

													counter++;

													OutOfMarks = daoInf.retrievAllTotalMarks(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), userForm.getAcademicYearID());

													FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

													/*
													 * MarksNew = daoInf.retrievrAllMarksForStudent(
													 * Integer.parseInt(newList[1]), studform.getSubjectID(),
													 * studform.getStandardID(), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 */

													/*
													 * MarksNew = daoInf2.retrieveMaxScaledMarksAllForStudent(
													 * studform.getSubjectID(), "Subject Enrichment",
													 * studentsExamFormName.getStudentID(), studform.getStandardID(),
													 * AcademicYearID);
													 */

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), userForm.getAcademicYearID(),
															studentsExamFormName.getStudentID());

													// FinalTotalMarks = FinalTotalMarks + MarksNew;

													if (SENBCheck == 1 && seCheckCounter == 1) {

														if (studform.getTerm().equals("All")) {

															double SENBBestMarks = daoInf2.retrieveScholasticGradeList1(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), 0, "Term I");

															double SENBBestMarksTermII = daoInf2
																	.retrieveScholasticGradeList1(
																			studform.getSubjectID(),
																			studentsExamFormName.getStudentID(), 0,
																			"Term II");

															TotalMarks = TotalMarks + SENBBestMarks
																	+ SENBBestMarksTermII;

															FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks
																	+ "=" + SENBBestMarksTermII;

															seCheckCounter++;

														} else {

															double SENBBestMarks = daoInf2.retrieveScholasticGradeList1(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), 0,
																	studform.getTerm());

															TotalMarks = TotalMarks + SENBBestMarks;

															FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks;

															seCheckCounter++;
														}

														System.out.println("TotalMarks...." + TotalMarks);

													}

													/*
													 * NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;
													 * 
													 * String numberAsString = Double.toString(NewMarks);
													 * 
													 * String valueMarks[] = numberAsString.split("\\.");
													 * 
													 * if (Long.parseLong(valueMarks[1]) > 0) {
													 * 
													 * Marks = Integer.parseInt(valueMarks[0]) + 1;
													 * 
													 * } else {
													 * 
													 * Marks = Integer.parseInt(valueMarks[0]); }
													 */

													if (counter > 2) {

														TotalMarks = TotalMarks + FinalTotalMarks;
														// System.out.println("TotalMarks: "+TotalMarks);

														// System.out.println("TotalMarks 1 " + TotalMarks);

														boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Term End", 0,
																studform.getTerm(), userForm.getAcademicYearID());

														boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Unit Test", 0,
																studform.getTerm(), userForm.getAcademicYearID());

														boolean SEANBCheck = daoInf2.verifySEANBAbsent(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), 0, Term,
																userForm.getAcademicYearID());

														if (termEndCheck) {
															Studentgrade = "ex";
														} else if (unitTestCheck) {
															Studentgrade = "ex";
														} else if (SEANBCheck) {
															Studentgrade = "ex";
														} else {

															if (studform.getTerm().contains("All")) {

																/* Calculating Final Grades */
																if (TotalMarks >= 91 && TotalMarks <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																	Studentgrade = "D";
																} else {

																	Studentgrade = "E";
																}
																System.out.println("Studentgrade: " + Studentgrade);
															} else {

																/* Calculating Final Grades */
																if (TotalMarks >= 46 && TotalMarks <= 50) {
																	Studentgrade = "A1";
																} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																	Studentgrade = "A2";
																} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																	Studentgrade = "B1";
																} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																	Studentgrade = "B2";
																} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																	Studentgrade = "C1";
																} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																	Studentgrade = "C2";
																} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}

																System.out.println("Studentgrade 1 : " + Studentgrade);

															}

														}

														counter = 0;
													}

													FinalListMarks += "=" + Marks;
												} else {

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), userForm.getAcademicYearID(),
															studentsExamFormName.getStudentID());

													System.out.println("..Marks..." + Marks);

													TotalMarks = TotalMarks + Marks;
													/* Calculating Final Grades */

													System.out.println("..TotalMarks..." + TotalMarks);

													boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Term End", 0,
															studform.getTerm(), userForm.getAcademicYearID());

													boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Unit Test", 0,
															studform.getTerm(), userForm.getAcademicYearID());

													boolean SEANBCheck = daoInf2.verifySEANBAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), 0, Term,
															userForm.getAcademicYearID());

													if (termEndCheck) {
														Studentgrade = "ex";
													} else if (unitTestCheck) {
														Studentgrade = "ex";
													} else if (SEANBCheck) {
														Studentgrade = "ex";
													} else {

														if (TotalMarks >= 46 && TotalMarks <= 50) {
															Studentgrade = "A1";
														} else if (TotalMarks >= 41 && TotalMarks <= 45) {
															Studentgrade = "A2";
														} else if (TotalMarks >= 36 && TotalMarks <= 40) {
															Studentgrade = "B1";
														} else if (TotalMarks >= 31 && TotalMarks <= 35) {
															Studentgrade = "B2";
														} else if (TotalMarks >= 26 && TotalMarks <= 30) {
															Studentgrade = "C1";
														} else if (TotalMarks >= 21 && TotalMarks <= 25) {
															Studentgrade = "C2";
														} else if (TotalMarks >= 17 && TotalMarks <= 20) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}

														System.out.println("....Studentgrade....." + Studentgrade);
													}

													FinalListMarks += "=" + Marks;
												}

											} else {

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;
											}
										}

									} else {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValue().equals("Total")) {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												String subjectList = "Subject Enrichment, Notebook";

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												if (!subjectList.contains(examType)) {
													TotalMarks = TotalMarks + Marks;
												}

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												if (studform.getTerm().equals("All")) {
													if (SubjectName.equals("Hindi")) {
														TotalMarksGrade1 = TotalMarks;
													} else {
														TotalMarksGrade1 = (TotalMarks / 200) * 100;
													}

												} else {
													TotalMarksGrade1 = TotalMarks;
												}

												String numberAsString = Double.toString(TotalMarksGrade1);

												String marks[] = numberAsString.split("\\.");

												if (Long.parseLong(marks[1]) > 0) {

													TotalMarksGrade = Integer.parseInt(marks[0]) + 1;

												} else {

													TotalMarksGrade = Integer.parseInt(marks[0]);
												}

												boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Term End", 0, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Unit Test", 0, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Subject Enrichment", 0, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Notebook", 0, studform.getTerm(),
														userForm.getAcademicYearID());

												if (termEndCheck) {
													Studentgrade = "ex";
												} else if (unitTestCheck) {
													Studentgrade = "ex";
												} else if (SEAAbsentCheck) {
													Studentgrade = "ex";
												} else if (NBAbsentCheck) {
													Studentgrade = "ex";
												} else {

													/* Calculating Final Grades */
													if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
														Studentgrade = "A1";
													} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
														Studentgrade = "A2";
													} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
														Studentgrade = "B1";
													} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
														Studentgrade = "B2";
													} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
														Studentgrade = "C1";
													} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
														Studentgrade = "C2";
													} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
														Studentgrade = "D";
													} else {
														Studentgrade = "E";
													}
												}

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

															TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade1 = TotalMarks;
																} else {
																	TotalMarksGrade1 = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade1 = TotalMarks;
															}

															String numberAsString1 = Double.toString(TotalMarksGrade1);

															String marks1[] = numberAsString1.split("\\.");

															if (Long.parseLong(marks1[1]) > 0) {

																TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

															} else {

																TotalMarksGrade = Integer.parseInt(marks1[0]);
															}

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																/* Calculating Final Grades */
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}

														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;

															TotalMarks = TotalMarks + maxSEA;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade1 = TotalMarks;
																} else {
																	TotalMarksGrade1 = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade1 = TotalMarks;
															}

															String numberAsString1 = Double.toString(TotalMarksGrade1);

															String marks1[] = numberAsString1.split("\\.");

															if (Long.parseLong(marks1[1]) > 0) {

																TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

															} else {

																TotalMarksGrade = Integer.parseInt(marks1[0]);
															}

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																/* Calculating Final Grades */
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}

														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

															TotalMarks = TotalMarks + maxNB + maxNBTerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade1 = TotalMarks;
																} else {
																	TotalMarksGrade1 = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade1 = TotalMarks;
															}

															String numberAsString1 = Double.toString(TotalMarksGrade1);

															String marks1[] = numberAsString1.split("\\.");

															if (Long.parseLong(marks1[1]) > 0) {

																TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

															} else {

																TotalMarksGrade = Integer.parseInt(marks1[0]);
															}

															/* Calculating Final Grades */

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;

															TotalMarks = TotalMarks + maxNB;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade1 = TotalMarks;
																} else {
																	TotalMarksGrade1 = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade1 = TotalMarks;
															}

															String numberAsString1 = Double.toString(TotalMarksGrade1);

															String marks1[] = numberAsString1.split("\\.");

															if (Long.parseLong(marks1[1]) > 0) {

																TotalMarksGrade = Integer.parseInt(marks1[0]) + 1;

															} else {

																TotalMarksGrade = Integer.parseInt(marks1[0]);
															}

															/* Calculating Final Grades */
															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}
														}
														NBCheckCounter++;
													}
												}

											} else {

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0, "Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), 0,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;
														}

														NBCheckCounter++;
													}
												}
											}
										}

									}

								} else {

									if (Stage.equals("Primary")) {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValue().equals("Total")) {

												int check = daoInf.verifyExamType(Integer.parseInt(newList[1]), "");
												String exampType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												/*
												 * int ScaledMarks = daoInf.retrieveAllScaledMarks(AcademicYearID,
												 * studform.getStandardID(), studform.getSubjectID());
												 */

												String subjectList = "Subject Enrichment, Notebook";

												if (subjectList.contains(exampType)) {

													counter++;

													OutOfMarks = daoInf.retrievTotalMarks(Integer.parseInt(newList[1]),
															studform.getSubjectID(), studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															userForm.getAcademicYearID());

													FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

													/*
													 * MarksNew = daoInf.retrievrMarksForStudent(
													 * Integer.parseInt(newList[1]), studform.getSubjectID(),
													 * studform.getStandardID(),
													 * Integer.parseInt(studform.getDivision()), AcademicYearID,
													 * studentsExamFormName.getStudentID());
													 */

													Marks = daoInf.retrievrAllMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(), userForm.getAcademicYearID(),
															studentsExamFormName.getStudentID());

													// FinalTotalMarks = FinalTotalMarks + MarksNew;

													System.out.println("SENBCheck.." + SENBCheck);

													if (SENBCheck == 1 && seCheckCounter == 1) {

														if (studform.getTerm().equals("All")) {

															double SENBBestMarks = daoInf2.retrieveScholasticGradeList1(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), AYClassID,
																	"Term I");

															double SENBBestMarksTermII = daoInf2
																	.retrieveScholasticGradeList1(
																			studform.getSubjectID(),
																			studentsExamFormName.getStudentID(),
																			AYClassID, "Term II");

															System.out.println("SENBBestMarks...." + SENBBestMarks);

															TotalMarks = TotalMarks + SENBBestMarks
																	+ SENBBestMarksTermII;

															FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks
																	+ "=" + SENBBestMarksTermII;

															seCheckCounter++;

														} else {

															double SENBBestMarks = daoInf2.retrieveScholasticGradeList1(
																	studform.getSubjectID(),
																	studentsExamFormName.getStudentID(), AYClassID,
																	studform.getTerm());

															System.out.println("SENBBestMarks...." + SENBBestMarks);

															TotalMarks = TotalMarks + SENBBestMarks;

															FinalSEANBMarks = FinalSEANBMarks + "=" + SENBBestMarks;

															seCheckCounter++;
														}

													}

													/*
													 * NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;
													 * 
													 * String numberAsString = Double.toString(NewMarks);
													 * 
													 * String valueMarks[] = numberAsString.split("\\.");
													 * 
													 * if (Long.parseLong(valueMarks[1]) > 0) {
													 * 
													 * Marks = Integer.parseInt(valueMarks[0]) + 1;
													 * 
													 * } else {
													 * 
													 * Marks = Integer.parseInt(valueMarks[0]); }
													 */

													if (counter > 2) {

														TotalMarks = TotalMarks + FinalTotalMarks;
														// System.out.println("TotalMarks: "+TotalMarks);

														boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Term End",
																AYClassID, studform.getTerm(),
																userForm.getAcademicYearID());

														boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), "Unit Test",
																AYClassID, studform.getTerm(),
																userForm.getAcademicYearID());

														boolean SEANBCheck = daoInf2.verifySEANBAbsent(
																studform.getSubjectID(),
																studentsExamFormName.getStudentID(), AYClassID, Term,
																userForm.getAcademicYearID());

														if (termEndCheck) {
															Studentgrade = "ex";
														} else if (unitTestCheck) {
															Studentgrade = "ex";
														} else if (SEANBCheck) {
															Studentgrade = "ex";
														} else {

															if (studform.getTerm().contains("All")) {
																/* Calculating Final Grades */

																if (TotalMarks >= 91 && TotalMarks <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarks >= 81 && TotalMarks <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarks >= 71 && TotalMarks <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarks >= 61 && TotalMarks <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarks >= 51 && TotalMarks <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarks >= 41 && TotalMarks <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarks >= 33 && TotalMarks <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}

															} else {
																/* Calculating Final Grades */

																if (TotalMarks >= 46 && TotalMarks <= 50) {
																	Studentgrade = "A1";
																} else if (TotalMarks >= 41 && TotalMarks <= 45) {
																	Studentgrade = "A2";
																} else if (TotalMarks >= 36 && TotalMarks <= 40) {
																	Studentgrade = "B1";
																} else if (TotalMarks >= 31 && TotalMarks <= 35) {
																	Studentgrade = "B2";
																} else if (TotalMarks >= 26 && TotalMarks <= 30) {
																	Studentgrade = "C1";
																} else if (TotalMarks >= 21 && TotalMarks <= 25) {
																	Studentgrade = "C2";
																} else if (TotalMarks >= 17 && TotalMarks <= 20) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}

															}
														}

														counter = 0;
													}

													FinalListMarks += "=" + Marks;

												} else {

													Marks = daoInf.retrievrMarksScaledForStudent(
															Integer.parseInt(newList[1]), studform.getSubjectID(),
															studform.getStandardID(),
															Integer.parseInt(studform.getDivision()),
															userForm.getAcademicYearID(),
															studentsExamFormName.getStudentID());

													TotalMarks = TotalMarks + Marks;

													boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Term End", AYClassID,
															studform.getTerm(), userForm.getAcademicYearID());

													boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), "Unit Test", AYClassID,
															studform.getTerm(), userForm.getAcademicYearID());

													boolean SEANBCheck = daoInf2.verifySEANBAbsent(
															studform.getSubjectID(),
															studentsExamFormName.getStudentID(), AYClassID, Term,
															userForm.getAcademicYearID());
													if (termEndCheck) {
														Studentgrade = "ex";
													} else if (unitTestCheck) {
														Studentgrade = "ex";
													} else if (SEANBCheck) {
														Studentgrade = "ex";
													} else {

														/* Calculating Final Grades */
														if (TotalMarks >= 46 && TotalMarks <= 50) {
															Studentgrade = "A1";
														} else if (TotalMarks >= 41 && TotalMarks <= 45) {
															Studentgrade = "A2";
														} else if (TotalMarks >= 36 && TotalMarks <= 40) {
															Studentgrade = "B1";
														} else if (TotalMarks >= 31 && TotalMarks <= 35) {
															Studentgrade = "B2";
														} else if (TotalMarks >= 26 && TotalMarks <= 30) {
															Studentgrade = "C1";
														} else if (TotalMarks >= 21 && TotalMarks <= 25) {
															Studentgrade = "C2";
														} else if (TotalMarks >= 17 && TotalMarks <= 20) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}

													}
													FinalListMarks += "=" + Marks;

												}

											} else {

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;
											}
										}

									} else {
										if (studform.getMarks().equals("marksObtained")) {

											Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

										} else {

											if (studform.getTotalValue().equals("Total")) {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												String subjectList = "Subject Enrichment, Notebook";

												String SubjectName = daoInf
														.retrieveSubjectBySubjectID(studform.getSubjectID());

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												if (!subjectList.contains(examType)) {
													TotalMarks = TotalMarks + Marks;
												}

												if (studform.getTerm().equals("All")) {
													if (SubjectName.equals("Hindi")) {
														TotalMarksGrade = TotalMarks;
													} else {
														TotalMarksGrade = (TotalMarks / 200) * 100;
													}

												} else {
													TotalMarksGrade = TotalMarks;
												}

												/* Calculating Final Grades */

												boolean termEndCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Term End", AYClassID, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean unitTestCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Unit Test", AYClassID, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Subject Enrichment", AYClassID, studform.getTerm(),
														userForm.getAcademicYearID());

												boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(
														studform.getSubjectID(), studentsExamFormName.getStudentID(),
														"Notebook", AYClassID, studform.getTerm(),
														userForm.getAcademicYearID());

												if (termEndCheck) {
													Studentgrade = "ex";
												} else if (unitTestCheck) {
													Studentgrade = "ex";
												} else if (SEAAbsentCheck) {
													Studentgrade = "ex";
												} else if (NBAbsentCheck) {
													Studentgrade = "ex";
												} else {

													if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
														Studentgrade = "A1";
													} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
														Studentgrade = "A2";
													} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
														Studentgrade = "B1";
													} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
														Studentgrade = "B2";
													} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
														Studentgrade = "C1";
													} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
														Studentgrade = "C2";
													} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
														Studentgrade = "D";
													} else {
														Studentgrade = "E";
													}
												}

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

															TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}

														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;

															TotalMarks = TotalMarks + maxSEA;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade1 = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {

																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

															TotalMarks = TotalMarks + maxNB + maxNBTerm2;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */
															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}

														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;

															TotalMarks = TotalMarks + maxNB;

															if (studform.getTerm().equals("All")) {
																if (SubjectName.equals("Hindi")) {
																	TotalMarksGrade = TotalMarks;
																} else {
																	TotalMarksGrade = (TotalMarks / 200) * 100;
																}

															} else {
																TotalMarksGrade = TotalMarks;
															}

															/* Calculating Final Grades */

															if (termEndCheck) {
																Studentgrade = "ex";
															} else if (unitTestCheck) {
																Studentgrade = "ex";
															} else if (SEAAbsentCheck) {
																Studentgrade = "ex";
															} else if (NBAbsentCheck) {
																Studentgrade = "ex";
															} else {
																if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
																	Studentgrade = "A1";
																} else if (TotalMarksGrade >= 81
																		&& TotalMarksGrade <= 90) {
																	Studentgrade = "A2";
																} else if (TotalMarksGrade >= 71
																		&& TotalMarksGrade <= 80) {
																	Studentgrade = "B1";
																} else if (TotalMarksGrade >= 61
																		&& TotalMarksGrade <= 70) {
																	Studentgrade = "B2";
																} else if (TotalMarksGrade >= 51
																		&& TotalMarksGrade <= 60) {
																	Studentgrade = "C1";
																} else if (TotalMarksGrade >= 41
																		&& TotalMarksGrade <= 50) {
																	Studentgrade = "C2";
																} else if (TotalMarksGrade >= 33
																		&& TotalMarksGrade <= 40) {
																	Studentgrade = "D";
																} else {
																	Studentgrade = "E";
																}
															}

														}

														NBCheckCounter++;
													}
												}
											} else {

												String examType = daoInf
														.retrieveExaminationType(Integer.parseInt(newList[1]));

												String subjectList = "Subject Enrichment, Notebook";

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												FinalListMarks += "=" + Marks;

												if (SECheck == 1 && examType.equals("Subject Enrichment")) {

													if (studform.getTerm().equals("All")) {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

														}

														SECheckCounter++;

													} else {
														double maxSEA = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Subject Enrichment",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (SECheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANBMarks += "=" + maxSEA;
														}

														SECheckCounter++;
													}
												}

												if (NBCheck == 1 && examType.equals("Notebook")) {

													if (studform.getTerm().equals("All")) {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term I");

														double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																"Term II");

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxSEA;
															FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
														}

														NBCheckCounter++;

													} else {
														double maxNB = daoInf2.retrieveScholasticGradeListNew(
																studform.getSubjectID(), "Notebook",
																studentsExamFormName.getStudentID(), AYClassID,
																studform.getTerm());

														if (NBCheckCounter == 0) {
															// FinalListMarks += "=" + maxNB;
															FinalSEANB1Marks += "=" + maxNB;
														}

														NBCheckCounter++;
													}
												}
											}
										}
									}
								}

								FinalList = FinalListNew + FinalListMarks + FinalSEANBMarks + FinalSEANB1Marks;
							}
						}

					} else {

						String[] newList = checkBoxList.split("\\$");

						if (studform.getContainsName().equals("GradeBased")) {

							if (studform.getDivision().contains(",")) {

								if (SubjectTypeValue.equals("Scholastic")) {

									/*---------------------------------------------------------------*/
									if (Stage.equals("Primary")) {

										NewExaminationList = daoInf2.retrieveExaminationList(
												userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

										/* double = 0D; */

										double value, value1, value2, value3, value4, finalOutOfMarks, totalMarksScaled,
												toatlScaleTo1 = 0;
										int marksObtained, marksObtained1, marksObtained2, marksObtained3,
												marksObtained4 = 0;
										double value5, value6, finalSEAMarks = 0D;
										int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
										int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue = 0;

										value = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Term End"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value1 = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Unit Test"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value2 = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value3 = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Subject Enrichment2"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value4 = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Notebook"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										outOfMarks = daoInf.retrievAllTotalMarks(
												NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID());

										outOfMarks1 = daoInf.retrievAllTotalMarks(
												NewExaminationList.get("Subject Enrichment2"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID());

										outOfMarks2 = daoInf.retrievAllTotalMarks(NewExaminationList.get("Notebook"),
												studform.getSubjectID(), studform.getStandardID(),
												userForm.getAcademicYearID());

										finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

										value5 = (value2 + value3 + value4);

										String numberAsString = Double.toString(value5);

										String marks[] = numberAsString.split("\\.");

										if (Long.parseLong(marks[1]) > 0) {

											value6 = Integer.parseInt(marks[0]) + 1;

										} else {

											value6 = Integer.parseInt(marks[0]);
										}

										scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Term End"),
												studform.getStandardID(), studform.getSubjectID());

										scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Unit Test"),
												studform.getStandardID(), studform.getSubjectID());

										scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(),
												NewExaminationList.get("Subject Enrichment1"), studform.getStandardID(),
												studform.getSubjectID());

										scaleTo3 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(),
												NewExaminationList.get("Subject Enrichment2"), studform.getStandardID(),
												studform.getSubjectID());

										scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Notebook"),
												studform.getStandardID(), studform.getSubjectID());

										finalSEAMarks = daoInf2.retrieveScholasticGradeList1(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), 0, studform.getTerm());

										totalMarksScaled = (value + value1 + finalSEAMarks);

										// totalMarksScaled = (value + value1 + value2 + value3 + value4);

										scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

										toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

										/* Calculating Final Grades */

										toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

										boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), 0,
												NewExaminationList.get("Term End"));

										boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), 0,
												NewExaminationList.get("Unit Test"));

										boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), 0, Term,
												userForm.getAcademicYearID());

										if (termEndCheck) {
											grade = "ex";
										} else if (unitTestCheck) {
											grade = "ex";
										} else if (SEANBCheck) {
											grade = "ex";
										} else {
											if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
												grade = "A1";
											} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
												grade = "A2";
											} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
												grade = "B1";
											} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
												grade = "B2";
											} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
												grade = "C1";
											} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
												grade = "C2";
											} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
												grade = "D";
											} else {
												grade = "E";
											}
										}

										if (studform.getGradeValue().equals("-1")) {
											FinalList += studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName() + "=" + grade;

										} else {

											if (NewFinalGrade.equals(grade)) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											}
										}

									} else {

										NewExaminationList = daoInf2.retrieveExaminationList(
												userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

										double value, value1, value2, value3, value4, finalOutOfMarks, totalMarksScaled,
												toatlScaleTo1 = 0;
										int marksObtained, marksObtained1, marksObtained2, marksObtained3,
												marksObtained4 = 0;
										double value5, value6, finalSEAMarks = 0D;
										int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
										int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue = 0;

										value = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Term End"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value1 = daoInf.retrievrAllMarksScaledForStudent(
												NewExaminationList.get("Unit Test"), studform.getSubjectID(),
												studform.getStandardID(), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
												"Subject Enrichment", studentsExamFormName.getStudentID(), 0,
												studform.getTerm());

										value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
												"Notebook", studentsExamFormName.getStudentID(), 0, studform.getTerm());

										scaleTo = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Term End"),
												studform.getStandardID(), studform.getSubjectID());

										scaleTo1 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Unit Test"),
												studform.getStandardID(), studform.getSubjectID());

										scaleTo2 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(),
												NewExaminationList.get("Subject Enrichment1"), studform.getStandardID(),
												studform.getSubjectID());

										scaleTo4 = daoInf.retrieveAllScaledMarks(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Notebook"),
												studform.getStandardID(), studform.getSubjectID());

										totalMarksScaled = (value + value1 + value2 + value4);

										toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

										String SubjectName = daoInf.retrieveSubjectBySubjectID(studform.getSubjectID());

										toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

										/* Calculating Final Grades */

										boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), 0,
												NewExaminationList.get("Term End"));

										boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), 0,
												NewExaminationList.get("Unit Test"));

										boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), "Subject Enrichment", 0,
												studform.getTerm(), userForm.getAcademicYearID());

										boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), "Notebook", 0, studform.getTerm(),
												userForm.getAcademicYearID());

										if (termEndCheck) {
											grade = "ex";
										} else if (unitTestCheck) {
											grade = "ex";
										} else if (SEAAbsentCheck) {
											grade = "ex";
										} else if (NBAbsentCheck) {
											grade = "ex";
										} else {
											if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
												grade = "A1";
											} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
												grade = "A2";
											} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
												grade = "B1";
											} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
												grade = "B2";
											} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
												grade = "C1";
											} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
												grade = "C2";
											} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
												grade = "D";
											} else {
												grade = "E";
											}
										}

										if (studform.getGradeValue().equals("-1")) {
											FinalList += studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName() + "=" + grade;
										} else {

											if (NewFinalGrade.equals(grade)) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											}
										}
									}

									/*---------------------------------------------------------------*/

								} else {

									FinalListNew = studentsExamFormName.getRollNumber() + "="
											+ studentsExamFormName.getStudentName();

									grade = daoInf.retrievrAllGradesForStudent(Integer.parseInt(newList[1]),
											studform.getSubjectID(), studform.getStandardID(),
											userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

									if (studform.getGradeValue().equals("-1")) {

										NewGrade += "=" + grade;
										FinalList += FinalListNew + NewGrade;
									} else {

										if (NewFinalGrade.equals(grade)) {
											NewGrade += "=" + grade;
											FinalList += FinalListNew + NewGrade;
										}
									}
								}

							} else {

								if (SubjectTypeValue.equals("Scholastic")) {

									/*---------------------------------------------------------------*/
									if (Stage.equals("Primary")) {

										NewExaminationList = daoInf2.retrieveExaminationList(
												userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

										/* double = 0D; */

										double value, value1, value2, value3, value4, finalOutOfMarks, totalMarksScaled,
												toatlScaleTo1 = 0;
										int marksObtained, marksObtained1, marksObtained2, marksObtained3,
												marksObtained4 = 0;
										double value5, value6, finalSEAMarks = 0D;
										int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
										int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue = 0;

										value = daoInf.retrievrMarksScaledForStudent(NewExaminationList.get("Term End"),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value1 = daoInf.retrievrMarksScaledForStudent(
												NewExaminationList.get("Unit Test"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										value2 = daoInf.retrievrMarksScaledForStudent(
												NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										value3 = daoInf.retrievrMarksScaledForStudent(
												NewExaminationList.get("Subject Enrichment2"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										value4 = daoInf.retrievrMarksScaledForStudent(
												NewExaminationList.get("Notebook"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										outOfMarks = daoInf.retrievTotalMarks(
												NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID());

										outOfMarks1 = daoInf.retrievTotalMarks(
												NewExaminationList.get("Subject Enrichment2"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID());

										outOfMarks2 = daoInf.retrievTotalMarks(NewExaminationList.get("Notebook"),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID());

										finalOutOfMarks = (outOfMarks + outOfMarks1 + outOfMarks2);

										value5 = (value2 + value3 + value4);

										String numberAsString = Double.toString(value5);

										String marks[] = numberAsString.split("\\.");

										if (Long.parseLong(marks[1]) > 0) {

											value6 = Integer.parseInt(marks[0]) + 1;

										} else {

											value6 = Integer.parseInt(marks[0]);
										}

										scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Term End"),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												studform.getSubjectID());

										scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Unit Test"),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												studform.getSubjectID());

										scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(),
												NewExaminationList.get("Subject Enrichment1"), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), studform.getSubjectID());

										scaleTo3 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(),
												NewExaminationList.get("Subject Enrichment2"), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), studform.getSubjectID());

										scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Notebook"),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												studform.getSubjectID());

										finalSEAMarks = daoInf2.retrieveScholasticGradeList1(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), AYClassID, studform.getTerm());

										totalMarksScaled = (value + value1 + finalSEAMarks);

										scaleTo5 = (scaleTo2 + scaleTo3 + scaleTo4) / 3;

										toatlScaleTo = (scaleTo + scaleTo1 + scaleTo5);

										// toatlScaleTo1 = totalMarksScaled;

										toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

										/* Calculating Final Grades */

										boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), AYClassID,
												NewExaminationList.get("Term End"));

										boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), AYClassID,
												NewExaminationList.get("Unit Test"));

										boolean SEANBCheck = daoInf2.verifySEANBAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), AYClassID, Term,
												userForm.getAcademicYearID());

										if (termEndCheck) {
											grade = "ex";
										} else if (unitTestCheck) {
											grade = "ex";
										} else if (SEANBCheck) {
											grade = "ex";
										} else {
											if (toatlScaleTo1 >= 46 && toatlScaleTo1 <= 50) {
												grade = "A1";
											} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 45) {
												grade = "A2";
											} else if (toatlScaleTo1 >= 36 && toatlScaleTo1 <= 40) {
												grade = "B1";
											} else if (toatlScaleTo1 >= 31 && toatlScaleTo1 <= 35) {
												grade = "B2";
											} else if (toatlScaleTo1 >= 26 && toatlScaleTo1 <= 30) {
												grade = "C1";
											} else if (toatlScaleTo1 >= 21 && toatlScaleTo1 <= 25) {
												grade = "C2";
											} else if (toatlScaleTo1 >= 17 && toatlScaleTo1 <= 20) {
												grade = "D";
											} else {
												grade = "E";
											}
										}

										if (studform.getGradeValue().equals("-1")) {
											FinalList += studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName() + "=" + grade;
										} else {

											if (NewFinalGrade.equals(grade)) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											}
										}

									} else {

										NewExaminationList = daoInf2.retrieveExaminationList(
												userForm.getAcademicYearID(), studform.getTerm(), AYClassID);

										double value, value1, value2, value3, value4, finalOutOfMarks, totalMarksScaled,
												toatlScaleTo1 = 0;
										int marksObtained, marksObtained1, marksObtained2, marksObtained3,
												marksObtained4 = 0;
										double value5, value6, finalSEAMarks = 0D;
										int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
										int toatlScaleTo, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue = 0;

										value = daoInf.retrievrMarksScaledForStudent(NewExaminationList.get("Term End"),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										value1 = daoInf.retrievrMarksScaledForStudent(
												NewExaminationList.get("Unit Test"), studform.getSubjectID(),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										/*
										 * value2 = daoInf.retrievrMarksScaledForStudent(
										 * NewExaminationList.get("Subject Enrichment1"), studform.getSubjectID(),
										 * studform.getStandardID(), Integer.parseInt(studform.getDivision()),
										 * AcademicYearID, studentsExamFormName.getStudentID());
										 * 
										 * value4 = daoInf.retrievrMarksScaledForStudent(
										 * NewExaminationList.get("Notebook"), studform.getSubjectID(),
										 * studform.getStandardID(), Integer.parseInt(studform.getDivision()),
										 * AcademicYearID, studentsExamFormName.getStudentID());
										 */

										value2 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
												"Subject Enrichment", studentsExamFormName.getStudentID(), AYClassID,
												studform.getTerm());

										value4 = daoInf2.retrieveScholasticGradeListNew(studform.getSubjectID(),
												"Notebook", studentsExamFormName.getStudentID(), AYClassID,
												studform.getTerm());

										scaleTo = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Term End"),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												studform.getSubjectID());

										scaleTo1 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Unit Test"),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												studform.getSubjectID());

										scaleTo2 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(),
												NewExaminationList.get("Subject Enrichment1"), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), studform.getSubjectID());

										scaleTo4 = daoInf.retrieveScaledMarksNew(studform.getTerm(),
												userForm.getAcademicYearID(), NewExaminationList.get("Notebook"),
												studform.getStandardID(), Integer.parseInt(studform.getDivision()),
												studform.getSubjectID());

										totalMarksScaled = (value + value1 + value2 + value4);

										toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo4);

										String SubjectName = daoInf.retrieveSubjectBySubjectID(studform.getSubjectID());

										toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

										/* Calculating Final Grades */

										boolean termEndCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), AYClassID,
												NewExaminationList.get("Term End"));

										boolean unitTestCheck = daoInf2.verifyAbsent(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), AYClassID,
												NewExaminationList.get("Unit Test"));

										boolean SEAAbsentCheck = daoInf2.verifySEANBAbsentCheck(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), "Subject Enrichment", AYClassID,
												studform.getTerm(), userForm.getAcademicYearID());

										boolean NBAbsentCheck = daoInf2.verifySEANBAbsentCheck(studform.getSubjectID(),
												studentsExamFormName.getStudentID(), "Notebook", AYClassID,
												studform.getTerm(), userForm.getAcademicYearID());

										if (termEndCheck) {
											grade = "ex";
										} else if (unitTestCheck) {
											grade = "ex";
										} else if (SEAAbsentCheck) {
											grade = "ex";
										} else if (NBAbsentCheck) {
											grade = "ex";
										} else {
											if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
												grade = "A1";
											} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
												grade = "A2";
											} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
												grade = "B1";
											} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
												grade = "B2";
											} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
												grade = "C1";
											} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
												grade = "C2";
											} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
												grade = "D";
											} else {
												grade = "E";
											}
										}

										if (studform.getGradeValue().equals("-1")) {
											FinalList += studentsExamFormName.getRollNumber() + "="
													+ studentsExamFormName.getStudentName() + "=" + grade;
										} else {

											if (NewFinalGrade.equals(grade)) {
												FinalList += studentsExamFormName.getRollNumber() + "="
														+ studentsExamFormName.getStudentName() + "=" + grade;
											}
										}
									}

								} else {

									FinalListNew = studentsExamFormName.getRollNumber() + "="
											+ studentsExamFormName.getStudentName();

									grade = daoInf.retrievrGradesForStudent(Integer.parseInt(newList[1]),
											studform.getSubjectID(), studform.getStandardID(),
											Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID(),
											studentsExamFormName.getStudentID());

									if (studform.getGradeValue().equals("-1")) {

										NewGrade += "=" + grade;
										FinalList = FinalListNew + NewGrade;
									} else {

										if (NewFinalGrade.equals(grade)) {
											NewGrade += "=" + grade;
											FinalList = FinalListNew + NewGrade;
										}
									}
								}
							}

						} else {

							FinalListNew = studentsExamFormName.getRollNumber() + "="
									+ studentsExamFormName.getStudentName();

							if (studform.getDivision().contains(",")) {
								if (Stage.equals("Primary")) {
									if (studform.getMarks().equals("marksObtained")) {

										Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										FinalListMarks += "=" + Marks;

									} else {

										if (studform.getTotalValue().equals("Total")) {

											int check = daoInf.verifyExamType(Integer.parseInt(newList[1]), "");
											int ScaledMarks = daoInf.retrieveAllScaledMarks(
													userForm.getAcademicYearID(), studform.getStandardID(),
													studform.getSubjectID());

											if (check == 1) {

												counter++;

												OutOfMarks = daoInf.retrievAllTotalMarks(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														userForm.getAcademicYearID());

												FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

												MarksNew = daoInf.retrievrAllMarksForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												FinalTotalMarks = FinalTotalMarks + MarksNew;

												NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;

												String numberAsString = Double.toString(NewMarks);

												String valueMarks[] = numberAsString.split("\\.");

												if (Long.parseLong(valueMarks[1]) > 0) {

													Marks = Integer.parseInt(valueMarks[0]) + 1;

												} else {

													Marks = Integer.parseInt(valueMarks[0]);
												}

												if (counter > 2) {

													if (studform.getTerm().contains("All")) {
														/* Calculating Final Grades */
														if (TotalMarks >= 91 && TotalMarks <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarks >= 81 && TotalMarks <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarks >= 71 && TotalMarks <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarks >= 61 && TotalMarks <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarks >= 51 && TotalMarks <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarks >= 41 && TotalMarks <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarks >= 33 && TotalMarks <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													} else {
														/* Calculating Final Grades */
														if (TotalMarks >= 46 && TotalMarks <= 50) {
															Studentgrade = "A1";
														} else if (TotalMarks >= 41 && TotalMarks <= 45) {
															Studentgrade = "A2";
														} else if (TotalMarks >= 36 && TotalMarks <= 40) {
															Studentgrade = "B1";
														} else if (TotalMarks >= 31 && TotalMarks <= 35) {
															Studentgrade = "B2";
														} else if (TotalMarks >= 26 && TotalMarks <= 30) {
															Studentgrade = "C1";
														} else if (TotalMarks >= 21 && TotalMarks <= 25) {
															Studentgrade = "C2";
														} else if (TotalMarks >= 17 && TotalMarks <= 20) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													FinalListMarks += "=" + Marks;
													counter = 0;
												}

											} else {

												Marks = daoInf.retrievrAllMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(), userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												TotalMarks = TotalMarks + Marks;
												/* Calculating Final Grades */
												if (TotalMarks >= 46 && TotalMarks <= 50) {
													Studentgrade = "A1";
												} else if (TotalMarks >= 41 && TotalMarks <= 45) {
													Studentgrade = "A2";
												} else if (TotalMarks >= 36 && TotalMarks <= 40) {
													Studentgrade = "B1";
												} else if (TotalMarks >= 31 && TotalMarks <= 35) {
													Studentgrade = "B2";
												} else if (TotalMarks >= 26 && TotalMarks <= 30) {
													Studentgrade = "C1";
												} else if (TotalMarks >= 21 && TotalMarks <= 25) {
													Studentgrade = "C2";
												} else if (TotalMarks >= 17 && TotalMarks <= 20) {
													Studentgrade = "D";
												} else {
													Studentgrade = "E";
												}

												FinalListMarks += "=" + Marks;
											}

										} else {

											Marks = daoInf.retrievrAllMarksScaledForStudent(
													Integer.parseInt(newList[1]), studform.getSubjectID(),
													studform.getStandardID(), userForm.getAcademicYearID(),
													studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;
										}
									}

								} else {
									if (studform.getMarks().equals("marksObtained")) {

										Marks = daoInf.retrievrAllMarksForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

										FinalListMarks += "=" + Marks;

									} else {

										if (studform.getTotalValue().equals("Total")) {

											String examType = daoInf
													.retrieveExaminationType(Integer.parseInt(newList[1]));

											String subjectList = "Subject Enrichment, Notebook";

											String SubjectName = daoInf
													.retrieveSubjectBySubjectID(studform.getSubjectID());

											Marks = daoInf.retrievrAllMarksScaledForStudent(
													Integer.parseInt(newList[1]), studform.getSubjectID(),
													studform.getStandardID(), userForm.getAcademicYearID(),
													studentsExamFormName.getStudentID());

											if (!subjectList.contains(examType)) {
												TotalMarks = TotalMarks + Marks;
											}

											if (studform.getTerm().equals("All")) {
												if (SubjectName.equals("Hindi")) {
													TotalMarksGrade = TotalMarks;
												} else {
													TotalMarksGrade = (TotalMarks / 200) * 100;
												}

											} else {
												TotalMarksGrade = TotalMarks;
											}

											/* Calculating Final Grades */
											if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
												Studentgrade = "A1";
											} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
												Studentgrade = "A2";
											} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
												Studentgrade = "B1";
											} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
												Studentgrade = "B2";
											} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
												Studentgrade = "C1";
											} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
												Studentgrade = "C2";
											} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
												Studentgrade = "D";
											} else {
												Studentgrade = "E";
											}

											FinalListMarks += "=" + Marks;

											if (SECheck == 1 && examType.equals("Subject Enrichment")) {

												if (studform.getTerm().equals("All")) {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, "Term I");

													double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, "Term II");

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

														TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													SECheckCounter++;

												} else {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA;

														TotalMarks = TotalMarks + maxSEA;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													SECheckCounter++;
												}
											}

											if (NBCheck == 1 && examType.equals("Notebook")) {

												if (studform.getTerm().equals("All")) {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, "Term I");

													double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, "Term II");

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

														TotalMarks = TotalMarks + maxNB + maxNBTerm2;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													NBCheckCounter++;

												} else {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxNB;
														FinalSEANB1Marks += "=" + maxNB;

														TotalMarks = TotalMarks + maxNB;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													NBCheckCounter++;
												}
											}

										} else {

											String examType = daoInf
													.retrieveExaminationType(Integer.parseInt(newList[1]));

											Marks = daoInf.retrievrAllMarksScaledForStudent(
													Integer.parseInt(newList[1]), studform.getSubjectID(),
													studform.getStandardID(), userForm.getAcademicYearID(),
													studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

											if (SECheck == 1 && examType.equals("Subject Enrichment")) {

												if (studform.getTerm().equals("All")) {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, "Term I");

													double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, "Term II");

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;
													}

													SECheckCounter++;

												} else {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA;
													}

													SECheckCounter++;
												}
											}

											if (NBCheck == 1 && examType.equals("Notebook")) {

												if (studform.getTerm().equals("All")) {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, "Term I");

													double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, "Term II");

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
													}

													NBCheckCounter++;

												} else {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), 0, studform.getTerm());

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxNB;
														FinalSEANB1Marks += "=" + maxNB;
													}

													NBCheckCounter++;
												}
											}
										}
									}
								}
							} else {

								if (Stage.equals("Primary")) {
									if (studform.getMarks().equals("marksObtained")) {

										Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										FinalListMarks += "=" + Marks;

									} else {

										if (studform.getTotalValue().equals("Total")) {

											int check = daoInf.verifyExamType(Integer.parseInt(newList[1]), "");
											int ScaledMarks = daoInf.retrieveScaledMarks(studform.getTerm(),
													userForm.getAcademicYearID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()), studform.getSubjectID());

											if (check == 1) {

												counter++;

												OutOfMarks = daoInf.retrievTotalMarks(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID());

												FinalOutOfMarks = FinalOutOfMarks + OutOfMarks;

												MarksNew = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
														studform.getSubjectID(), studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												FinalTotalMarks = FinalTotalMarks + MarksNew;

												NewMarks = (FinalTotalMarks / FinalOutOfMarks) * ScaledMarks;

												String numberAsString = Double.toString(NewMarks);

												String valueMarks[] = numberAsString.split("\\.");

												if (Long.parseLong(valueMarks[1]) > 0) {

													Marks = Integer.parseInt(valueMarks[0]) + 1;

												} else {

													Marks = Integer.parseInt(valueMarks[0]);
												}

												if (counter > 2) {

													if (studform.getTerm().contains("All")) {
														/* Calculating Final Grades */
														if (TotalMarks >= 91 && TotalMarks <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarks >= 81 && TotalMarks <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarks >= 71 && TotalMarks <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarks >= 61 && TotalMarks <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarks >= 51 && TotalMarks <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarks >= 41 && TotalMarks <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarks >= 33 && TotalMarks <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													} else {
														TotalMarks = TotalMarks + Marks;
														/* Calculating Final Grades */
														if (TotalMarks >= 46 && TotalMarks <= 50) {
															Studentgrade = "A1";
														} else if (TotalMarks >= 41 && TotalMarks <= 45) {
															Studentgrade = "A2";
														} else if (TotalMarks >= 36 && TotalMarks <= 40) {
															Studentgrade = "B1";
														} else if (TotalMarks >= 31 && TotalMarks <= 35) {
															Studentgrade = "B2";
														} else if (TotalMarks >= 26 && TotalMarks <= 30) {
															Studentgrade = "C1";
														} else if (TotalMarks >= 21 && TotalMarks <= 25) {
															Studentgrade = "C2";
														} else if (TotalMarks >= 17 && TotalMarks <= 20) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}
													FinalListMarks += "=" + Marks;

													counter = 0;
												}

											} else {

												Marks = daoInf.retrievrMarksScaledForStudent(
														Integer.parseInt(newList[1]), studform.getSubjectID(),
														studform.getStandardID(),
														Integer.parseInt(studform.getDivision()),
														userForm.getAcademicYearID(),
														studentsExamFormName.getStudentID());

												TotalMarks = TotalMarks + Marks;
												/* Calculating Final Grades */
												if (TotalMarks >= 46 && TotalMarks <= 50) {
													Studentgrade = "A1";
												} else if (TotalMarks >= 41 && TotalMarks <= 45) {
													Studentgrade = "A2";
												} else if (TotalMarks >= 36 && TotalMarks <= 40) {
													Studentgrade = "B1";
												} else if (TotalMarks >= 31 && TotalMarks <= 35) {
													Studentgrade = "B2";
												} else if (TotalMarks >= 26 && TotalMarks <= 30) {
													Studentgrade = "C1";
												} else if (TotalMarks >= 21 && TotalMarks <= 25) {
													Studentgrade = "C2";
												} else if (TotalMarks >= 17 && TotalMarks <= 20) {
													Studentgrade = "D";
												} else {
													Studentgrade = "E";
												}

												FinalListMarks += "=" + Marks;
											}

										} else {

											Marks = daoInf.retrievrMarksScaledForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;
										}
									}

								} else {
									if (studform.getMarks().equals("marksObtained")) {

										Marks = daoInf.retrievrMarksForStudent(Integer.parseInt(newList[1]),
												studform.getSubjectID(), studform.getStandardID(),
												Integer.parseInt(studform.getDivision()), userForm.getAcademicYearID(),
												studentsExamFormName.getStudentID());

										FinalListMarks += "=" + Marks;

									} else {

										if (studform.getTotalValue().equals("Total")) {

											String examType = daoInf
													.retrieveExaminationType(Integer.parseInt(newList[1]));

											String subjectList = "Subject Enrichment, Notebook";

											String SubjectName = daoInf
													.retrieveSubjectBySubjectID(studform.getSubjectID());

											Marks = daoInf.retrievrMarksScaledForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											if (!subjectList.contains(examType)) {
												TotalMarks = TotalMarks + Marks;
											}

											if (studform.getTerm().equals("All")) {
												if (SubjectName.equals("Hindi")) {
													TotalMarksGrade = TotalMarks;
												} else {
													TotalMarksGrade = (TotalMarks / 200) * 100;
												}

											} else {
												TotalMarksGrade = TotalMarks;
											}

											/* Calculating Final Grades */
											if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
												Studentgrade = "A1";
											} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
												Studentgrade = "A2";
											} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
												Studentgrade = "B1";
											} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
												Studentgrade = "B2";
											} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
												Studentgrade = "C1";
											} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
												Studentgrade = "C2";
											} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
												Studentgrade = "D";
											} else {
												Studentgrade = "E";
											}

											FinalListMarks += "=" + Marks;

											if (SECheck == 1 && examType.equals("Subject Enrichment")) {

												if (studform.getTerm().equals("All")) {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID, "Term I");

													double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID, "Term II");

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;

														TotalMarks = TotalMarks + maxSEA + maxSEATerm2;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													SECheckCounter++;

												} else {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA;

														TotalMarks = TotalMarks + maxSEA;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													SECheckCounter++;
												}
											}

											if (NBCheck == 1 && examType.equals("Notebook")) {

												if (studform.getTerm().equals("All")) {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID, "Term I");

													double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID, "Term II");

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;

														TotalMarks = TotalMarks + maxNB + maxNBTerm2;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													NBCheckCounter++;

												} else {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxNB;
														FinalSEANB1Marks += "=" + maxNB;

														TotalMarks = TotalMarks + maxNB;

														if (studform.getTerm().equals("All")) {
															if (SubjectName.equals("Hindi")) {
																TotalMarksGrade = TotalMarks;
															} else {
																TotalMarksGrade = (TotalMarks / 200) * 100;
															}

														} else {
															TotalMarksGrade = TotalMarks;
														}

														/* Calculating Final Grades */
														if (TotalMarksGrade >= 91 && TotalMarksGrade <= 100) {
															Studentgrade = "A1";
														} else if (TotalMarksGrade >= 81 && TotalMarksGrade <= 90) {
															Studentgrade = "A2";
														} else if (TotalMarksGrade >= 71 && TotalMarksGrade <= 80) {
															Studentgrade = "B1";
														} else if (TotalMarksGrade >= 61 && TotalMarksGrade <= 70) {
															Studentgrade = "B2";
														} else if (TotalMarksGrade >= 51 && TotalMarksGrade <= 60) {
															Studentgrade = "C1";
														} else if (TotalMarksGrade >= 41 && TotalMarksGrade <= 50) {
															Studentgrade = "C2";
														} else if (TotalMarksGrade >= 33 && TotalMarksGrade <= 40) {
															Studentgrade = "D";
														} else {
															Studentgrade = "E";
														}
													}

													NBCheckCounter++;
												}
											}

										} else {

											String examType = daoInf
													.retrieveExaminationType(Integer.parseInt(newList[1]));

											Marks = daoInf.retrievrMarksScaledForStudent(Integer.parseInt(newList[1]),
													studform.getSubjectID(), studform.getStandardID(),
													Integer.parseInt(studform.getDivision()),
													userForm.getAcademicYearID(), studentsExamFormName.getStudentID());

											FinalListMarks += "=" + Marks;

											if (SECheck == 1 && examType.equals("Subject Enrichment")) {

												if (studform.getTerm().equals("All")) {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID, "Term I");

													double maxSEATerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID, "Term II");

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA + "=" + maxSEATerm2;
													}

													SECheckCounter++;

												} else {
													double maxSEA = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Subject Enrichment",
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													if (SECheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANBMarks += "=" + maxSEA;
													}

													SECheckCounter++;
												}
											}

											if (NBCheck == 1 && examType.equals("Notebook")) {

												if (studform.getTerm().equals("All")) {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID, "Term I");

													double maxNBTerm2 = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID, "Term II");

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxSEA;
														FinalSEANB1Marks += "=" + maxNB + "=" + maxNBTerm2;
													}

													NBCheckCounter++;

												} else {
													double maxNB = daoInf2.retrieveScholasticGradeListNew(
															studform.getSubjectID(), "Notebook",
															studentsExamFormName.getStudentID(), AYClassID,
															studform.getTerm());

													if (NBCheckCounter == 0) {
														// FinalListMarks += "=" + maxNB;
														FinalSEANB1Marks += "=" + maxNB;
													}

													NBCheckCounter++;
												}
											}
										}
									}
								}
							}
							FinalList = FinalListNew + FinalListMarks + FinalSEANBMarks + FinalSEANB1Marks;
						}
					}
				} else {
					FinalList = FinalList;
				}

				if (studform.getTotalValue().equals("Total")) {
					System.out.println("StudentgradeNew: " + Studentgrade);
					FinalList += "=" + TotalMarks + "=" + Studentgrade;
				}

				studentFinalExamCustomReportList.add(FinalList);
			}

			request.setAttribute("studentFinalExamCustomReportList", studentFinalExamCustomReportList);

			request.setAttribute("checkBoxList", checkBoxList);

			request.setAttribute("CheckValue", studform.getContainsName());

			if (studentFinalExamCustomReportList == null) {

				addActionError("No records found.");

				request.setAttribute("loadStudentSearch", "Disabled");

				return ERROR;

			} else if (studentFinalExamCustomReportList.size() == 0) {

				addActionError("No records found.");

				request.setAttribute("loadStudentSearch", "Disabled");

				return ERROR;

			} else {

				request.setAttribute("loadStudentSearch", "Enabled");

				return SUCCESS;

			}

		} else {

			addActionError("No records found.");
			return ERROR;
		}

	}

	public void retrieveStandardForacademicYear() throws Exception {
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

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

			values = daoInf.retrieveStandardListForacademicYear(studform.getAcademicYearID(), userForm.getUserID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Division List based on Division or StandardID for AcademicYear");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveDivisionForacademicYear() throws Exception {
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

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

			values = daoInf.retrieveDivisionForacademicYear(studform.getAcademicYearID(), userForm.getUserID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Division List based on Division or StandardID for AcademicYear");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String loadCustomStudents() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		request.setAttribute("classTeacherCheck", "Yes");

		request.setAttribute("ContainsName", studform.getContainsName());

		request.setAttribute("Value", studform.getAgeValue());

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String StandardName = daoInf1.retrieveStandardName(userForm.getUserID(), studform.getAcademicYearID());

		int StandardID = daoInf1.retrieveStandardID(userForm.getUserID(), studform.getAcademicYearID());

		String DivisionName = daoInf1.retrieveDivisionName(userForm.getUserID(), studform.getAcademicYearID());

		int DivisionID = daoInf1.retrieveDivisionID(userForm.getUserID(), studform.getAcademicYearID());

		String checkBoxList;
		checkBoxList = studform.getCheckBoxList();

		System.out.println("checkBoxList...." + checkBoxList);

		String StudentCustomList = "Roll_No, Student_Name";

		String finalcheckBoxList = "";

		if (checkBoxList != null) {
			String[] newcheckBoxList = checkBoxList.split(" AS ");

			for (int i = 0; i < newcheckBoxList.length; i++) {

				if (i == 0) {

					continue;
				} else {
					if (newcheckBoxList[i].contains(",")) {
						String[] newList = newcheckBoxList[i].split(",");

						finalcheckBoxList = finalcheckBoxList + "," + newList[0];
					} else {

						finalcheckBoxList = finalcheckBoxList + "," + newcheckBoxList[i];
					}
				}
			}

			if (finalcheckBoxList.contains("$")) {

				finalcheckBoxList = finalcheckBoxList.replaceAll("\\$", ",");
			}

			StudentCustomList = StudentCustomList + finalcheckBoxList;

		} else {

			StudentCustomList = StudentCustomList;
		}

		request.setAttribute("StudentCustomList", StudentCustomList);

		if (studform.getContainsName().contains("Parent")) {

			studentsBasedCustomReportList = new ArrayList<String>();

			StudentIDList = daoInf.retrievestudentsIDBasedList(studform.getStandardID(), studform.getDivisionID(),
					studform.getAcademicYearID());

			for (int i = 0; i < StudentIDList.size(); i++) {

				studentsBasedCustomReportList.add(daoInf.retrieveStudentsBasedParentsCustomReportList(
						studform.getStandardID(), studform.getDivisionID(), studform.getAcademicYearID(),
						studform.getSearchFields(), checkBoxList, StudentIDList.get(i)));

			}

		} else {

			studentsBasedCustomReportList = daoInf.retrievestudentsBasedCustomReportList(studform.getStandardID(),
					studform.getDivisionID(), studform.getAcademicYearID(), studform.getSearchFields(),
					studform.getContainsName(), checkBoxList, studform.getAgeValue(), studform.getSearchFieldsNew());
		}

		request.setAttribute("studentsBasedCustomReportList", studentsBasedCustomReportList);

		request.setAttribute("radioValue", studform.getRadioValue());

		request.setAttribute("checkBoxList", checkBoxList);

		request.setAttribute("CheckValue", studform.getContainsName());

		if (studentsBasedCustomReportList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentsBasedCustomReportList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateAllStudentReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		request.setAttribute("classTeacherCheck", "Yes");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "Students-Customised-Report_For_" + studform.getStandard() + "_" + studform.getDivision()
				+ ".xls";

		String checkBoxList;
		checkBoxList = studform.getCheckBoxList();

		String finalcheckBoxList = "";

		if (checkBoxList != null) {
			String[] newcheckBoxList = checkBoxList.split(" AS ");

			for (int i = 0; i < newcheckBoxList.length; i++) {

				if (i == 0) {

					continue;
				} else {
					if (newcheckBoxList[i].contains(",")) {
						String[] newList = newcheckBoxList[i].split(",");

						finalcheckBoxList = finalcheckBoxList + "," + newList[0];
					} else {

						finalcheckBoxList = finalcheckBoxList + "," + newcheckBoxList[i];
					}
				}
			}

			if (finalcheckBoxList.contains("$")) {

				finalcheckBoxList = finalcheckBoxList.replaceAll("\\$", ",");
			}

			if (finalcheckBoxList.startsWith(",")) {
				finalcheckBoxList = finalcheckBoxList.substring(1);
			}
		}

		if (studform.getContainsName().contains("Parent")) {

			studentsBasedCustomReportList = new ArrayList<String>();

			StudentIDList = daoInf.retrievestudentsIDBasedList(studform.getStandardID(), studform.getDivisionID(),
					studform.getAcademicYearID());

			for (int i = 0; i < StudentIDList.size(); i++) {

				studentsBasedCustomReportList.add(daoInf.retrieveStudentsBasedParentsCustomReportList(
						studform.getStandardID(), studform.getDivisionID(), studform.getAcademicYearID(),
						studform.getSearchFields(), checkBoxList, StudentIDList.get(i)));

			}

		} else {

			studentsBasedCustomReportList = daoInf.retrievestudentsBasedCustomReportList(studform.getStandardID(),
					studform.getDivisionID(), studform.getAcademicYearID(), studform.getSearchFields(),
					studform.getContainsName(), checkBoxList, studform.getAgeValue(), studform.getSearchFieldsNew());
		}

		request.setAttribute("studentsBasedCustomReportList", studentsBasedCustomReportList);

		request.setAttribute("radioValue", studform.getRadioValue());

		request.setAttribute("checkBoxList", checkBoxList);

		message = excelUtil.generateStudentsCustomisedReport(studform.getStandard(), studform.getDivision(),
				finalcheckBoxList, studentsBasedCustomReportList, excelFileName, realPath);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students customised excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students customised Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students customised excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students customised Report Excel Sheet", form.getUserID());

			return ERROR;

		}

	}

	public String generateAllExamReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		request.setAttribute("classTeacherCheck", "Yes");

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String divisionName = daoInf.retrieveDivisionName(studform.getDivision());

		// retrieving division name separated by underscore in order to append it to the
		// excel file name
		String excelFileName = "Exam-Customised-Report_For_" + studform.getStandard() + "_" + divisionName + ".xls";

		message = excelUtil.generateExamsCustomisedReport(studform, userForm.getUserID(), userForm.getAcademicYearID(),
				realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students customised excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students customised Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students customised excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students customised Report Excel Sheet", form.getUserID());

			return ERROR;

		}
	}

	public String generateAllExamHistoryReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		request.setAttribute("classTeacherCheck", "Yes");

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "Exam-History-Report_For_" + studform.getStandard() + "_" + studform.getDivision()
				+ ".xls";

		message = excelUtil.generateExamsCustomisedReport(studform, userForm.getUserID(), userForm.getAcademicYearID(),
				realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students customised excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students customised Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students customised excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students customised Report Excel Sheet", form.getUserID());

			return ERROR;
		}
	}

	public String studentExcelReport() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		String StandardName = daoInf2.getStandardNameByStandardID(studform.getStandardID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		// excel file name
		String excelFileName = studform.getStudentName().replaceAll(" ", "_") + "_marks_report" + ".xls";

		message = excelUtil.generateStudentsMarksReport(studform.getStudentID(), studform.getRegistrationID(),
				studform.getStandardID(), studform.getTerm(), studform.getAyclassID(), userForm.getUserID(),
				userForm.getOrganizationID(), userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students marks excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Marks Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students marks excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students Marks Report Excel Sheet", form.getUserID());

			return ERROR;

		}
	}

	public String emailStudentExcelReport() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		String StandardName = daoInf2.getStandardNameByStandardID(studform.getStandardID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		// excel file name
		String excelFileName = studform.getStudentName().replaceAll(" ", "_") + "_marks_report" + ".xls";

		message = excelUtil.generateStudentsMarksReport(studform.getStudentID(), studform.getRegistrationID(),
				studform.getStandardID(), studform.getTerm(), studform.getAyclassID(), userForm.getUserID(),
				userForm.getOrganizationID(), userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students marks excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Marks Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students marks excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students Marks Report Excel Sheet", form.getUserID());

			return ERROR;

		}
	}

	public String renderConfigureConsolidatedSheet() throws Exception {

		daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;
	}

	public String generateConsolidatedExamReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = studform.getStandard() + "_Standard_Consolidated_Sheet" + ".xls";

		message = excelUtil.generateExamsConsolidatedReport(studform, studform.getStandard(), studform.getStandardID(),
				userForm.getOrganizationID(), userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students Consolidated excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Consolidated Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students Consolidated excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students Consolidated Report Excel Sheet", form.getUserID());

			return ERROR;
		}
	}

	public String generateBifurcationExamReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		StuduntDAOInf daoInf = new StudentDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3rdmlFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = xmlUtil.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = studform.getStandard() + "_Standard_Consolidated_Sheet" + ".xls";

		message = excelUtil.generateBifurcationExamReport(studform, studform.getStandard(), studform.getStandardID(),
				studform.getDivision(), studform.getDivisionID(), studform.getTerm(), userForm.getOrganizationID(),
				userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students Bifurcation excel report generated successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			studform.setFileInputStream(s3ObjectInputStream);

			studform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Consolidated Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to generate students Consolidated excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Students Consolidated Report Excel Sheet", form.getUserID());

			return ERROR;
		}
	}

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

	public LinkedHashMap<String, String> getMonthList() {
		return MonthList;
	}

	public void setMonthList(LinkedHashMap<String, String> monthList) {
		MonthList = monthList;
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

	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}

	public StudentForm getModel() {
		// TODO Auto-generated method stub
		return studform;
	}

	public List<StudentForm> getSearchStudentList() {
		return searchStudentList;
	}

	public void setSearchStudentList(List<StudentForm> searchStudentList) {
		this.searchStudentList = searchStudentList;
	}

	public List<StudentForm> getSearchSubjectList() {
		return searchSubjectList;
	}

	public void setSearchSubjectList(List<StudentForm> searchSubjectList) {
		this.searchSubjectList = searchSubjectList;
	}

	public List<StudentForm> getSignedUpStudentList() {
		return signedUpStudentList;
	}

	public void setSignedUpStudentList(List<StudentForm> signedUpStudentList) {
		this.signedUpStudentList = signedUpStudentList;
	}

	public List<StudentForm> getSubjectEditList() {
		return SubjectEditList;
	}

	public void setSubjectEditList(List<StudentForm> subjectEditList) {
		SubjectEditList = subjectEditList;
	}

	public HashMap<Integer, String> getSubjectList() {
		return SubjectList;
	}

	public void setSubjectList(HashMap<Integer, String> subjectList) {
		SubjectList = subjectList;
	}

	public List<StudentForm> getSearchCommutationList() {
		return searchCommutationList;
	}

	public void setSearchCommutationList(List<StudentForm> searchCommutationList) {
		this.searchCommutationList = searchCommutationList;
	}

	public List<StudentForm> getCommutationEditList() {
		return CommutationEditList;
	}

	public void setCommutationEditList(List<StudentForm> commutationEditList) {
		CommutationEditList = commutationEditList;
	}

	public HashMap<Integer, String> getCommutationList() {
		return CommutationList;
	}

	public HashMap<Integer, String> getStandardList() {
		return StandardList;
	}

	public void setStandardList(HashMap<Integer, String> standardList) {
		StandardList = standardList;
	}

	public HashMap<Integer, String> getDivisionList() {
		return DivisionList;
	}

	public void setDivisionList(HashMap<Integer, String> divisionList) {
		DivisionList = divisionList;
	}

	public void setCommutationList(HashMap<Integer, String> commutationList) {
		CommutationList = commutationList;
	}

	public List<StudentForm> getStudentList() {
		return StudentList;
	}

	public void setStudentList(List<StudentForm> studentList) {
		StudentList = studentList;
	}

	public HashMap<String, String> getExamList() {
		return ExamList;
	}

	public void setExamList(HashMap<String, String> examList) {
		ExamList = examList;
	}

	public HashMap<String, String> getPhysicalActivitiesList() {
		return PhysicalActivitiesList;
	}

	public void setPhysicalActivitiesList(HashMap<String, String> physicalActivitiesList) {
		PhysicalActivitiesList = physicalActivitiesList;
	}

	public List<StudentForm> getStudentsList() {
		return studentsList;
	}

	public void setStudentsList(List<StudentForm> studentsList) {
		this.studentsList = studentsList;
	}

	public List<StudentForm> getNewStudentsList() {
		return NewStudentsList;
	}

	public void setNewStudentsList(List<StudentForm> newStudentsList) {
		NewStudentsList = newStudentsList;
	}

	public List<String> getStudentsBasedCustomReportList() {
		return studentsBasedCustomReportList;
	}

	public void setStudentsBasedCustomReportList(List<String> studentsBasedCustomReportList) {
		this.studentsBasedCustomReportList = studentsBasedCustomReportList;
	}

	public HashMap<Integer, String> getAcademicYearNameList() {
		return AcademicYearNameList;
	}

	public void setAcademicYearNameList(HashMap<Integer, String> academicYearNameList) {
		AcademicYearNameList = academicYearNameList;
	}

	public List<StudentForm> getActivityList() {
		return ActivityList;
	}

	public void setActivityList(List<StudentForm> activityList) {
		ActivityList = activityList;
	}

	public List<StudentForm> getStudentsBasedExamCustomReportList() {
		return studentsBasedExamCustomReportList;
	}

	public void setStudentsBasedExamCustomReportList(List<StudentForm> studentsBasedExamCustomReportList) {
		this.studentsBasedExamCustomReportList = studentsBasedExamCustomReportList;
	}

	public HashMap<Integer, String> getSubjectListByStandard() {
		return SubjectListByStandard;
	}

	public void setSubjectListByStandard(HashMap<Integer, String> subjectListByStandard) {
		SubjectListByStandard = subjectListByStandard;
	}

	public List<String> getStudentFinalExamCustomReportList() {
		return studentFinalExamCustomReportList;
	}

	public void setStudentFinalExamCustomReportList(List<String> studentFinalExamCustomReportList) {
		this.studentFinalExamCustomReportList = studentFinalExamCustomReportList;
	}

	public HashMap<String, String> getStandardDivisionList() {
		return StandardDivisionList;
	}

	public void setStandardDivisionList(HashMap<String, String> standardDivisionList) {
		StandardDivisionList = standardDivisionList;
	}

	public HashMap<String, Integer> getNewExaminationList() {
		return NewExaminationList;
	}

	public void setNewExaminationList(HashMap<String, Integer> newExaminationList) {
		NewExaminationList = newExaminationList;
	}

	public List<Integer> getStudentIDList() {
		return StudentIDList;
	}

	public void setStudentIDList(List<Integer> studentIDList) {
		StudentIDList = studentIDList;
	}

	/**
	 * @return the compulsoryActivitiesList
	 */
	public HashMap<String, String> getCompulsoryActivitiesList() {
		return CompulsoryActivitiesList;
	}

	/**
	 * @param compulsoryActivitiesList the compulsoryActivitiesList to set
	 */
	public void setCompulsoryActivitiesList(HashMap<String, String> compulsoryActivitiesList) {
		CompulsoryActivitiesList = compulsoryActivitiesList;
	}

}
