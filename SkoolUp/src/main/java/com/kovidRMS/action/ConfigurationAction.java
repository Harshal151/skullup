package com.kovidRMS.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

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
import com.kovidRMS.util.ConfigurationUtil;
import com.kovidRMS.util.ConvertToPDFUtil;
import com.kovidRMS.util.EmailUtil;
import com.kovidRMS.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ConfigurationAction extends ActionSupport
		implements ModelDriven<ConfigurationForm>, SessionAware, ServletResponseAware {
	String message = null;
	LoginForm form = new LoginForm();
	StudentForm studform = new StudentForm();
	ConfigurationForm conform = new ConfigurationForm();

	ConfigurationUtil util = new ConfigurationUtil();

	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

	ExcelUtil excelUtil = null;

	EmailUtil emailUtil = new EmailUtil();

	private Map<String, Object> sessionAttribute = null;

	HashMap<Integer, String> organizationList = null;

	HashMap<Integer, String> StandardList = null;

	HashMap<Integer, String> AcademicYearNameList = null;

	HashMap<Integer, String> AcademicYearNameListNew = null;

	HashMap<Integer, String> DivisionList = null;

	HashMap<Integer, String> AppuserList = null;

	HashMap<Integer, String> SubjectList = null;

	HashMap<Integer, String> StandardWiseSubjectList = null;

	HashMap<Integer, String> SubjectListByExamType = null;

	HashMap<Integer, String> SubjectListForPersonalityDevelopment = null;

	HashMap<Integer, String> SubjectListForExtraCurricularActivity = null;

	HashMap<Integer, String> SubjectListForExtracurricular = null;

	HashMap<Integer, String> SubjectListByCoScholastic = null;

	HashMap<String, Integer> NewExaminationList = null;

	List<ConfigurationForm> examinatrionList = null;

	List<ConfigurationForm> AcademicYearClassList = null;

	HashMap<Integer, String> ExamList = null;

	List<ConfigurationForm> studentsList = null;

	List<StudentForm> StudentList = null;

	List<ConfigurationForm> studentDetailsList = null;

	HashMap<String, List<String>> EmailHistoryDetails = null;

	List<String> EmailValues = null;
	List<String> StudentValues = null;
	List<String> StatusValues = null;

	List<ConfigurationForm> coScholasticGradeList = null;

	List<ConfigurationForm> ExtraCurricularGradeList = null;

	List<ConfigurationForm> ScholasticGradeList = null;

	List<ConfigurationForm> PersonalityDevelopmentGradeList = null;

	List<ConfigurationForm> PersonalityDevelopmentGradeListNew = null;

	List<ConfigurationForm> AttendanceList = null;

	List<ConfigurationForm> NewAttendanceList = null;

	List<StudentForm> StudentListByStandardAndDivision = null;

	HashMap<Integer, String> subjectNameList = null;

	List<ConfigurationForm> studentAssessmentList = null;

	HashMap<String, String> StandardListByTeacher = null;

	HashMap<String, String> StandardListForPersonalityDevelopment = null;

	HashMap<String, String> StandardListByCoScholastic = null;

	HashMap<String, String> StandardListForExtraCurricular = null;

	List<ConfigurationForm> subjectAssessmentList = null;

	List<ConfigurationForm> subjectAssessmentListNew = null;

	List<ConfigurationForm> subjectNewList = null;

	List<ConfigurationForm> studentsCustomReportList = null;

	LinkedHashMap<Integer, String> studentCustomList = null;

	HashMap<Integer, String> InActiveAcademicYearList = null;

	List<String> SubjectEnrichmentList = null;

	List<String> NotebookList = null;

	List<String> PortfolioList = null;

	List<String> MultipleAssessmentList = null;

	HashMap<Integer, String> LibraryList = null;

	public List<ConfigurationForm> getSubjectNewList() {
		return subjectNewList;
	}

	public void setSubjectNewList(List<ConfigurationForm> subjectNewList) {
		this.subjectNewList = subjectNewList;
	}

	public List<ConfigurationForm> getSubjectAssessmentListNew() {
		return subjectAssessmentListNew;
	}

	public void setSubjectAssessmentListNew(List<ConfigurationForm> subjectAssessmentListNew) {
		this.subjectAssessmentListNew = subjectAssessmentListNew;
	}

	List<ConfigurationForm> SubjectList1 = null;

	public List<ConfigurationForm> getSubjectList1() {
		return SubjectList1;
	}

	public void setSubjectList1(List<ConfigurationForm> subjectList1) {
		SubjectList1 = subjectList1;
	}

	public HashMap<Integer, String> getDivisionList() {
		return DivisionList;
	}

	public void setDivisionList(HashMap<Integer, String> divisionList) {
		DivisionList = divisionList;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response;

	LoginDAOInf daoInf1 = null;
	kovidRMSServiceInf serviceInf = null;

	/* configure AcademicYear */

	public String renderconfigureAcademicYear() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		conform = new ConfigurationForm();
		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(form.getStandardID());

		// getting AppuserList value
		AppuserList = daoInf1.getAppUser(userForm.getOrganizationID());

		// getting SubjectList value
		SubjectList = daoInf1.getSubjectList();

		/* retriving present AcademicYearClassList from AYClass table */
		AcademicYearClassList = daoInf.retriveAcademicYearClassList(conform.getAcademicYearID());

		request.setAttribute("AcademicYearClassList", AcademicYearClassList);

		return SUCCESS;

	}

	public String renderImportExport() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		LibraryList = daoInf1.retrieveLibraryList(userForm.getOrganizationID());

		return SUCCESS;

	}

	public String exportStudentHistoryReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

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
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "Students_Excel_Sheet" + ".xls";

		message = excelUtil.exportStudentHistoryReportExcel(conform, conform.getAcademicYearID(), realPath,
				excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to export students excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Students Excel Sheet",
					form.getUserID());

			return ERROR;

		}
	}

	public String importStudentHistoryReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		// File excelFileName = conform.getExcelFileName();

		message = excelUtil.importStudentHistoryReportExcel(conform, conform.getExcelFileName(),
				conform.getAcademicYearID());
		
		LibraryList = daoInf1.retrieveLibraryList(userForm.getOrganizationID());

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students excel sheet imported successfully.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Imported students Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Invalid file format. Please upload a valid file");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Importing Students Excel Sheet",
					form.getUserID());

			return ERROR;

		}
	}

	public String importConfiguration() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());
		
		LibraryList = daoInf1.retrieveLibraryList(userForm.getOrganizationID());

		// File excelFileName = conform.getExcelFileName();

		message = excelUtil.importConfigurationsReportExcel(conform, conform.getExcelFileName(),
				conform.getAcademicYearID(), userForm.getOrganizationID());

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Configurations excel sheet imported successfully.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Imported Configurations Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Invalid file format. Please upload a valid file");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Importing Configurations Excel Sheet",
					form.getUserID());

			return ERROR;

		}
	}

	public String exportConfiguration() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "Configuration_Excel_Sheet" + ".xls";

		message = excelUtil.exportConfigurationsExcel(conform, conform.getAcademicYearID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Configuration excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating configuration Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to export configuration excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Configuration Excel Sheet",
					form.getUserID());

			return ERROR;

		}
	}

	public String renderLoadAcademicYear() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting AcademicYearNameList value
		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String ActivityStatus = daoInf1.getActivityStatus(conform.getAcademicYearID());

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		// getting DivisionList value
		DivisionList = daoInf1.getDivision(form.getStandardID());

		// getting AppuserList value
		AppuserList = daoInf1.getAppUser(userForm.getOrganizationID());

		// getting SubjectList value
		SubjectList = daoInf1.getSubjectList();

		/* retriving present AcademicYearClassList from AYClass table */
		AcademicYearClassList = daoInf.retriveAcademicYearClassList(conform.getAcademicYearID());

		request.setAttribute("AcademicYearClassList", AcademicYearClassList);

		request.setAttribute("AcademicYear", "Enabled");

		return SUCCESS;

	}

	public String configureAcademicYear() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

		String ActivityStatus = daoInf1.getActivityStatus(conform.getAcademicYearID());

		message = serviceInf.registerAcademicYearList(conform, ActivityStatus, conform.getAcademicYearID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("AcademicYear Class List configured successfully.");

			// getting AcademicYearNameList value
			AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

			/* retriving present AcademicYearClassList from AYClass table */
			AcademicYearClassList = daoInf.retriveAcademicYearClassList(conform.getAcademicYearID());

			request.setAttribute("AcademicYearClassList", AcademicYearClassList);

			request.setAttribute("AcademicYear", "Enabled");
			// Inserting values into Audit table

			daoInf1.insertAudit(request.getRemoteAddr(), "Configure AcademicYear Class List", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring AcademicYear Class List. Please check logs for more details.");

			// getting AcademicYearNameList value
			AcademicYearNameList = daoInf1.getAcademicYearNameList(userForm.getOrganizationID());

			/* retriving present AcademicYearClassList from AYClass table */
			AcademicYearClassList = daoInf.retriveAcademicYearClassList(conform.getAcademicYearID());

			request.setAttribute("AcademicYearClassList", AcademicYearClassList);

			request.setAttribute("AcademicYear", "Enabled");
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure AcademicYear Class List Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	public String renderconfigureExamination() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		conform = new ConfigurationForm();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		/* retriving present ExaminationList from Examination table */
		examinatrionList = daoInf.retriveExaminationList(userForm.getAcademicYearID());

		request.setAttribute("examinationList", examinatrionList);

		return SUCCESS;

	}

	public String configureExamination() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = serviceInf.registerExamination(conform, userForm.getAcademicYearID());

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Examination configured successfully.");

			/* retriving present ExaminationList from Examination table */
			examinatrionList = daoInf.retriveExaminationList(userForm.getAcademicYearID());

			request.setAttribute("examinationList", examinatrionList);

			// getting getStandard List value
			ExamList = daoInf.getExamList(userForm.getAcademicYearID());

			// Inserting values into Audit table

			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Examination", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Examination. Please check logs for more details.");

			/* retriving present ExaminationList from Examination table */
			examinatrionList = daoInf.retriveExaminationList(userForm.getAcademicYearID());

			request.setAttribute("examinationList", examinatrionList);

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Examination Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void deleteRow() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteRow(conform.getDeleteID(), ActivityStatus.ACTIVE);

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
	 */
	public void retrieveDivisionListForStandard() throws Exception {
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

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

			values = daoInf.retrieveDivisionListForStandard(conform.getStandardID());

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

	public void retrieveExaminationListByTerm() throws Exception {
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

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

			values = daoInf.retrieveExamListByTerm(conform.getTerm(), userForm.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Exam List based on Term for AcademicYear");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveStudentListForStandardAndDivision() throws Exception {
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

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

			values = daoInf.retrieveStudentListForStandardAndDivision(conform.getStandardID(), conform.getDivisionID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	/**
	 * 
	 */
	public void retrieveSubjectListForStandard() throws Exception {
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

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

			values = daoInf.retrieveSubjectListForStandard(conform.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on StandardID for Examination");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveStandardName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveStandardName(conform.getStandardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Standard Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveExamNameByExamID() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveExamNameByExamID(conform.getExaminationID(), userForm.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Exam Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveDivisionName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveDivisionName(conform.getDivisionID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Division Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveStudentName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveStudentName(conform.getStudentNameID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveCondition() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveCondition(conform.getStudentNameID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Condition Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveTeacherName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveTeacherName(conform.getTeacherID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Teacher Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveSubjectName(conform.getSubjectID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveActivitiesName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveActivitiesName(conform.getSubjectID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Activities Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveActivityNameByactivityID() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveActivityNameByactivityID(conform.getActivityID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Activities Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectTeacherName() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveSubjectTeacherName(conform.getTeacherNameID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject Teacher Name");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveNewSubjectListForStandard() throws Exception {
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

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

			values = daoInf.retrieveNewSubjectListForStandard(conform.getAYClassID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Subject List based on AYClassID for Configure AYClass");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void deleteAYClassRow() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteAYClassRow(conform.getDeleteID(), ActivityStatus.ACTIVE);

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

	public void deleteStudentAssessmentRow() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteStudentAssessmentRow(conform.getStudentAssessmentID());

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
	public void RetrieveSubTeacherListByAYClassID() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * Retrieving subject & teacher list based on AYClassID from AYSubject table
			 */
			values = daoInf.retrieveSubTeacherListByAYCLassID(conform.getAYClassID());

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

	public void retrieveActivitiesListBysubjAssmntID() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * Retrieving subject & teacher list based on AYClassID from AYSubject table
			 */
			System.out.println("conform.getSubjectAssessmentID(): " + conform.getSubjectAssessmentID());
			values = daoInf.retrieveActivitiesListBysubjAssmntID(conform.getSubjectAssessmentID());

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

	public void removeAYSubjectRow() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
			System.out.println("AySubjectID : " + conform.getAySubjectID());
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteAYSUbjectByID(conform.getAySubjectID());

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

	public void removeactivityAssessmentRow() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteactivityAssessment(conform.getActivityAssessmentID());

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

	public void retrieveSubjectAssessmentDetails() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			System.out.println("..." + conform.getStandardID() + " ..." + conform.getDivisionID());

			// Retrieving ayClassID based on stanadrdID, divsionID and previously retrieved
			// academicYearID
			int ayClassID = daoInf.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
					userForm.getAcademicYearID());

			/*
			 * System.out.println("Academic YEar ID ..." + academicYearID + " AYCLassID ..."
			 * + ayClassID + "ExamID..." + conform.getExaminationID());
			 */

			/*
			 * Retrieving subject assessment list based on examinationID and ayClassID
			 */
			values = daoInf.retrieveExistingSubjectAssessmentList(ayClassID, conform.getExaminationID());

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

	public String renderconfigureSubjectAssessment() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		ExamList = daoInf2.getExaminationList(userForm.getAcademicYearID());

		return SUCCESS;
	}

	public String configureSubjectAssessment() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		serviceInf = new kovidRMSServiceImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		daoInf1 = new LoginDAOImpl();

		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		ExamList = daoInf.getExaminationListByTerm(userForm.getAcademicYearID(), conform.getTerm());

		conform.setAcademicYearID(userForm.getAcademicYearID());

		int ayClassID = daoInf.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID());
		/*
		 * System.out.println("AYClassID : "+ayClassID);
		 * System.out.println("ExaminationID : "+conform.getExaminationID());
		 */

		DivisionList = daoInf.retrieveDivisionListByStandardID(conform.getStandardID());

		String SubjectListByStandardID = "";

		boolean check = daoInf.verifyExamType(conform.getExaminationID(), userForm.getAcademicYearID());

		if (check) {

			SubjectListByStandardID = daoInf.retrieveSubjectListByStandardID(conform.getStandardID());

		} else {

			SubjectListByStandardID = daoInf.retrieveSubjectListByStandardIDExamType(conform.getStandardID());
		}

		subjectAssessmentListNew = new ArrayList<ConfigurationForm>();

		// check whether string contains comma saparated values or not if exist the
		// remove comma
		if (SubjectListByStandardID.contains(",")) {

			String subArr[] = SubjectListByStandardID.split(",");

			for (int j = 0; j < subArr.length; j++) {

				subjectAssessmentList = daoInf.retrieveExistingsubjectAssessmentList(ayClassID,
						conform.getExaminationID(), subArr[j].trim());

				if (subjectAssessmentList == null || subjectAssessmentList.size() == 0) {

					System.out.println("Null List Found");

				} else {

					for (ConfigurationForm subjectFormName : subjectAssessmentList) {
						ConfigurationForm form = new ConfigurationForm();

						form.setSubject(subjectFormName.getSubject());
						form.setSubjectType(subjectFormName.getSubjectType());
						form.setTotalMarks(subjectFormName.getTotalMarks());
						form.setScaleTo(subjectFormName.getScaleTo());
						form.setGradeBased(subjectFormName.getGradeBased());
						form.setSubjectAssessmentID(subjectFormName.getSubjectAssessmentID());
						form.setSubjectID(subjectFormName.getSubjectID());

						subjectAssessmentListNew.add(form);
					}

				}

				request.setAttribute("loadSubjectSearch", "Enabled");
			}
		} else {

			subjectAssessmentList = daoInf.retrieveExistingsubjectAssessmentList(ayClassID, conform.getExaminationID(),
					SubjectListByStandardID.trim());

			if (subjectAssessmentList == null || subjectAssessmentList.size() == 0) {

				System.out.println("Null List Found");

			} else {

				for (ConfigurationForm subjectFormName : subjectAssessmentList) {
					ConfigurationForm form = new ConfigurationForm();

					form.setSubject(subjectFormName.getSubject());
					form.setSubjectType(subjectFormName.getSubjectType());
					form.setTotalMarks(subjectFormName.getTotalMarks());
					form.setScaleTo(subjectFormName.getScaleTo());
					form.setGradeBased(subjectFormName.getGradeBased());
					form.setSubjectAssessmentID(subjectFormName.getSubjectAssessmentID());
					form.setSubjectID(subjectFormName.getSubjectID());

					subjectAssessmentListNew.add(form);
				}

			}

			request.setAttribute("loadSubjectSearch", "Enabled");

		}

		request.setAttribute("subjectAssessmentListNew", subjectAssessmentListNew);
		/* System.out.println("Sizeeeeee...." + subjectAssessmentListNew.size()); */

		subjectNewList = new ArrayList<ConfigurationForm>();

		if (SubjectListByStandardID.contains(",")) {

			String subNewArr[] = SubjectListByStandardID.split(",");

			for (int i = 0; i < subNewArr.length; i++) {

				UUID uuid = UUID.randomUUID();
				String randomUUIDString = uuid.toString();

				String randomNo = randomUUIDString.split("-")[1];

				boolean check1 = daoInf.verifySubjects(ayClassID, conform.getExaminationID(), subNewArr[i].trim());

				if (check1 == true) {

					System.out.println("Record Found");

				} else {

					System.out.println("Record not Found");

					ConfigurationForm form = new ConfigurationForm();

					String[] array = subNewArr[i].trim().split("\\$");

					String subjectType = daoInf.retrieveSubjectTypeBySubjectID(Integer.parseInt(array[1].trim()));

					form.setSubject(array[0]);
					form.setSubjectID(Integer.parseInt(array[1].trim()));
					form.setSubjectType(subjectType);
					form.setTotalMarks('0');
					form.setScaleTo('0');
					form.setGradeBased('0');
					form.setSubjAssmntID(randomNo);

					subjectNewList.add(form);
					request.setAttribute("subjectNewList", subjectNewList);
				}
			}
		} else {

			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();

			String randomNo = randomUUIDString.split("-")[1];

			boolean check1 = daoInf.verifySubjects(ayClassID, conform.getExaminationID(),
					SubjectListByStandardID.trim());

			if (check1 == true) {

				System.out.println("Record Found");

			} else {

				System.out.println("Record not Found");

				ConfigurationForm form = new ConfigurationForm();

				String[] array = SubjectListByStandardID.trim().split("\\$");

				String subjectType = daoInf.retrieveSubjectTypeBySubjectID(Integer.parseInt(array[1].trim()));

				form.setSubject(array[0]);
				form.setSubjectID(Integer.parseInt(array[1].trim()));
				form.setSubjectType(subjectType);
				form.setTotalMarks('0');
				form.setScaleTo('0');
				form.setGradeBased('0');
				form.setSubjAssmntID(randomNo);

				subjectNewList.add(form);
				request.setAttribute("subjectNewList", subjectNewList);
			}

			request.setAttribute("loadSubjectSearch", "Enabled");
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addSubjectAssessment() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		serviceInf = new kovidRMSServiceImpl();

		daoInf1 = new LoginDAOImpl();
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		// getting StandardList value
		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		ExamList = daoInf.getExaminationListByTerm(userForm.getAcademicYearID(), conform.getTerm());

		conform.setAcademicYearID(userForm.getAcademicYearID());

		DivisionList = daoInf.retrieveDivisionListByStandardID(conform.getStandardID());

		message = serviceInf.configureSubjectAssessment(conform);

		if (message.equals("success")) {

			addActionMessage("Details added successfully.");

			// SubjectListByStandardID =
			// daoInf.retrieveSubjectListByStandardID(conform.getStandardID

			// Retrieving ayClassID based on stanadrdID, divsionID and previously retrieved
			// academicYearID

			int ayClassID = daoInf.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
					userForm.getAcademicYearID());

			System.out.println("ayclassID : " + ayClassID);

			if (ayClassID == 0) {
				addActionError("Failed to add details. Please check server logs for more details.");
			} else {
				// subjectAssessmentList =
				// daoInf.retrieveExistingsubjectAssessmentList(ayClassID,
				// conform.getExaminationID());

				String SubjectListByStandardID = "";

				boolean check = daoInf.verifyExamType(conform.getExaminationID(), userForm.getAcademicYearID());

				if (check) {

					SubjectListByStandardID = daoInf.retrieveSubjectListByStandardID(conform.getStandardID());

				} else {

					SubjectListByStandardID = daoInf.retrieveSubjectListByStandardIDExamType(conform.getStandardID());

				}

				subjectAssessmentListNew = new ArrayList<ConfigurationForm>();

				// check whether string contains comma saparated values or not if exist the
				// remove comma
				if (SubjectListByStandardID.contains(",")) {

					String subArr[] = SubjectListByStandardID.split(",");
					for (int j = 0; j < subArr.length; j++) {

						subjectAssessmentList = daoInf.retrieveExistingsubjectAssessmentList(ayClassID,
								conform.getExaminationID(), subArr[j].trim());

						if (subjectAssessmentList == null || subjectAssessmentList.size() == 0) {

							System.out.println("Null List Found");

						} else {

							for (ConfigurationForm subjectFormName : subjectAssessmentList) {
								ConfigurationForm form = new ConfigurationForm();

								form.setSubject(subjectFormName.getSubject());
								form.setSubjectType(subjectFormName.getSubjectType());
								form.setTotalMarks(subjectFormName.getTotalMarks());
								form.setScaleTo(subjectFormName.getScaleTo());
								form.setGradeBased(subjectFormName.getGradeBased());
								form.setSubjectAssessmentID(subjectFormName.getSubjectAssessmentID());
								form.setSubjectID(subjectFormName.getSubjectID());

								subjectAssessmentListNew.add(form);
							}
						}

						request.setAttribute("loadSubjectSearch", "Enabled");
					}
				} else {

					subjectAssessmentList = daoInf.retrieveExistingsubjectAssessmentList(ayClassID,
							conform.getExaminationID(), SubjectListByStandardID.trim());

					if (subjectAssessmentList == null || subjectAssessmentList.size() == 0) {

						System.out.println("Null List Found");

					} else {

						for (ConfigurationForm subjectFormName : subjectAssessmentList) {
							ConfigurationForm form = new ConfigurationForm();

							form.setSubject(subjectFormName.getSubject());
							form.setSubjectType(subjectFormName.getSubjectType());
							form.setTotalMarks(subjectFormName.getTotalMarks());
							form.setScaleTo(subjectFormName.getScaleTo());
							form.setGradeBased(subjectFormName.getGradeBased());
							form.setSubjectAssessmentID(subjectFormName.getSubjectAssessmentID());
							form.setSubjectID(subjectFormName.getSubjectID());

							subjectAssessmentListNew.add(form);
						}

					}

					request.setAttribute("loadSubjectSearch", "Enabled");

				}

				request.setAttribute("subjectAssessmentListNew", subjectAssessmentListNew);
				/* System.out.println("Sizeeeeee...." + subjectAssessmentListNew.size()); */

				subjectNewList = new ArrayList<ConfigurationForm>();

				if (SubjectListByStandardID.contains(",")) {

					String subNewArr[] = SubjectListByStandardID.split(",");

					for (int i = 0; i < subNewArr.length; i++) {

						UUID uuid = UUID.randomUUID();
						String randomUUIDString = uuid.toString();

						String randomNo = randomUUIDString.split("-")[1];

						boolean check1 = daoInf.verifySubjects(ayClassID, conform.getExaminationID(),
								subNewArr[i].trim());

						if (check1 == true) {

							System.out.println("Record Found");

						} else {

							System.out.println("Record not Found");

							ConfigurationForm form = new ConfigurationForm();

							String[] subNewarray = subNewArr[i].split("\\$");

							String subjectType = daoInf
									.retrieveSubjectTypeBySubjectID(Integer.parseInt(subNewarray[1].trim()));

							form.setSubject(subNewarray[0]);
							form.setSubjectID(Integer.parseInt(subNewarray[1].trim()));
							form.setSubjectType(subjectType);
							form.setTotalMarks('0');
							form.setScaleTo('0');
							form.setGradeBased('0');
							form.setSubjAssmntID(randomNo);

							subjectNewList.add(form);
							request.setAttribute("subjectNewList", subjectNewList);
						}
					}
				} else {

					UUID uuid = UUID.randomUUID();
					String randomUUIDString = uuid.toString();

					String randomNo = randomUUIDString.split("-")[1];

					boolean check1 = daoInf.verifySubjects(ayClassID, conform.getExaminationID(),
							SubjectListByStandardID.trim());

					if (check1 == true) {

						System.out.println("Record Found");

					} else {

						System.out.println("Record not Found");

						ConfigurationForm form = new ConfigurationForm();

						String[] Newarray = SubjectListByStandardID.split("\\$");

						String subjectType = daoInf
								.retrieveSubjectTypeBySubjectID(Integer.parseInt(Newarray[1].trim()));

						form.setSubject(Newarray[0].trim());
						form.setSubjectID(Integer.parseInt(Newarray[1].trim()));
						form.setSubjectType(subjectType);
						form.setTotalMarks('0');
						form.setScaleTo('0');
						form.setGradeBased('0');
						form.setSubjAssmntID(randomNo);

						subjectNewList.add(form);
						request.setAttribute("subjectNewList", subjectNewList);
					}

					request.setAttribute("loadSubjectSearch", "Enabled");
				}
			}

			return SUCCESS;

		} else {

			addActionError("Failed to add details. Please check server logs for more details.");

			// Retrieving ayClassID based on stanadrdID, divsionID and previously retrieved
			// academicYearID
			int ayClassID = daoInf.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
					userForm.getAcademicYearID());

			if (ayClassID == 0) {
				addActionError("Failed to add details. Please check server logs for more details.");
			} else {
				String SubjectListByStandardID = "";

				boolean check = daoInf.verifyExamType(conform.getExaminationID(), userForm.getAcademicYearID());

				if (check) {

					SubjectListByStandardID = daoInf.retrieveSubjectListByStandardID(conform.getStandardID());

				} else {

					SubjectListByStandardID = daoInf.retrieveSubjectListByStandardIDExamType(conform.getStandardID());

				}

				subjectAssessmentListNew = new ArrayList<ConfigurationForm>();

				// check whether string contains comma saparated values or not if exist the
				// remove comma
				if (SubjectListByStandardID.contains(",")) {

					String subArr[] = SubjectListByStandardID.split(",");
					for (int j = 0; j < subArr.length; j++) {

						subjectAssessmentList = daoInf.retrieveExistingsubjectAssessmentList(ayClassID,
								conform.getExaminationID(), subArr[j].trim());

						/*
						 * System.out.println("subjectAssessmentList size : " +
						 * subjectAssessmentList.size()); System.out.println("subject size : " +
						 * subArr.length); System.out.println("AYClassID : " + ayClassID);
						 * System.out.println("ExaminationID : " + conform.getExaminationID());
						 * System.out.println("subject : " + subArr[j]);
						 */

						if (subjectAssessmentList == null || subjectAssessmentList.size() == 0) {

							System.out.println("Null List Found");

						} else {

							for (ConfigurationForm subjectFormName : subjectAssessmentList) {
								ConfigurationForm form = new ConfigurationForm();

								form.setSubject(subjectFormName.getSubject());
								form.setSubjectType(subjectFormName.getSubjectType());
								form.setTotalMarks(subjectFormName.getTotalMarks());
								form.setScaleTo(subjectFormName.getScaleTo());
								form.setGradeBased(subjectFormName.getGradeBased());
								form.setSubjectAssessmentID(subjectFormName.getSubjectAssessmentID());
								form.setSubjectID(subjectFormName.getSubjectID());

								subjectAssessmentListNew.add(form);
							}

						}

						request.setAttribute("loadSubjectSearch", "Enabled");
					}
				} else {

					subjectAssessmentList = daoInf.retrieveExistingsubjectAssessmentList(ayClassID,
							conform.getExaminationID(), SubjectListByStandardID.trim());

					if (subjectAssessmentList == null || subjectAssessmentList.size() == 0) {

						System.out.println("Null List Found");

					} else {

						for (ConfigurationForm subjectFormName : subjectAssessmentList) {
							ConfigurationForm form = new ConfigurationForm();

							form.setSubject(subjectFormName.getSubject());
							form.setSubjectType(subjectFormName.getSubjectType());
							form.setTotalMarks(subjectFormName.getTotalMarks());
							form.setScaleTo(subjectFormName.getScaleTo());
							form.setGradeBased(subjectFormName.getGradeBased());
							form.setSubjectAssessmentID(subjectFormName.getSubjectAssessmentID());
							form.setSubjectID(subjectFormName.getSubjectID());

							subjectAssessmentListNew.add(form);
						}
					}

					request.setAttribute("loadSubjectSearch", "Enabled");
				}

				request.setAttribute("subjectAssessmentListNew", subjectAssessmentListNew);
				/* System.out.println("Sizeeeeee...." + subjectAssessmentListNew.size()); */

				subjectNewList = new ArrayList<ConfigurationForm>();

				if (SubjectListByStandardID.contains(",")) {

					String subNewArr[] = SubjectListByStandardID.split(",");

					for (int i = 0; i < subNewArr.length; i++) {

						UUID uuid = UUID.randomUUID();
						String randomUUIDString = uuid.toString();

						String randomNo = randomUUIDString.split("-")[1];

						boolean check1 = daoInf.verifySubjects(ayClassID, conform.getExaminationID(),
								subNewArr[i].trim());

						if (check1 == true) {

							System.out.println("Record Found");

						} else {

							System.out.println("Record not Found");

							ConfigurationForm form = new ConfigurationForm();

							String[] Newarray = subNewArr[i].split("\\$");
							String subjectType = daoInf
									.retrieveSubjectTypeBySubjectID(Integer.parseInt(Newarray[1].trim()));

							form.setSubject(Newarray[0]);
							form.setSubjectID(Integer.parseInt(Newarray[1]));
							form.setSubjectType(subjectType);
							form.setTotalMarks('0');
							form.setScaleTo('0');
							form.setGradeBased('0');
							form.setSubjAssmntID(randomNo);

							subjectNewList.add(form);
						}
					}
				} else {

					UUID uuid = UUID.randomUUID();
					String randomUUIDString = uuid.toString();

					String randomNo = randomUUIDString.split("-")[1];

					boolean check1 = daoInf.verifySubjects(ayClassID, conform.getExaminationID(),
							SubjectListByStandardID.trim());

					if (check1 == true) {

						System.out.println("Record Found");

					} else {

						System.out.println("Record not Found");

						ConfigurationForm form = new ConfigurationForm();

						String[] Newarray = SubjectListByStandardID.split("\\$");
						String subjectType = daoInf
								.retrieveSubjectTypeBySubjectID(Integer.parseInt(Newarray[1].trim()));

						form.setSubject(Newarray[0]);
						form.setSubjectID(Integer.parseInt(Newarray[1]));
						form.setSubjectType(subjectType);
						form.setTotalMarks('0');
						form.setScaleTo('0');
						form.setGradeBased('0');
						form.setSubjAssmntID(randomNo);

						subjectNewList.add(form);
					}

					request.setAttribute("loadSubjectSearch", "Enabled");
				}
			}
			return ERROR;
		}
	}

	public String renderConfigureMySubject() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListByTeacher = configXMLUtil.sortHashMap(daoInf2.getStandardListByTeacher(userForm.getUserID()));

		if (StandardListByTeacher == null || StandardListByTeacher.size() == 0) {

			addActionError("You are not assigned as subject teacher.");
			request.setAttribute("Teacher", "Yes");
		}

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		SubjectListByExamType = new HashMap<Integer, String>();

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), conform.getAYClassID()));

		int subjectAssessmentID = daoInf2.retrievesubjectAssessmentID(conform.getExaminationID(),
				conform.getSubjectID(), conform.getAYClassID());

		/*
		 * Check whether subjectAssessmentID is present in ActivityAssessment table or
		 * not
		 */

		int ActivityAssessmentCheck = daoInf2.verifyActivityAssessmentBySubjectAssessmentID(subjectAssessmentID);

		request.setAttribute("ActivityAssessmentCheck", ActivityAssessmentCheck);

		return SUCCESS;
	}

	public void retrieveSubjectListByUserID() throws Exception {
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

			/*
			 * Getting ayClassID by splitting check string by -
			 */
			String[] strArray = conform.getCheck().split("-");

			values = daoInf2.retrieveSubjectListByUserIDByExamType(userForm.getUserID(), Integer.parseInt(strArray[2]),
					userForm.getOrganizationID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectListByUserIDForPersonalityDevelopment() throws Exception {

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

			/*
			 * Getting ayClassID by splitting check string by -
			 */
			String[] strArray = conform.getCheck().split("-");
			System.out.println(
					"Integer.parseInt(strArray[2]):" + userForm.getUserID() + "_" + Integer.parseInt(strArray[2]));
			values = daoInf2.retrieveSubjectListByUserIDForPersonalityDevelopment(userForm.getUserID(),
					Integer.parseInt(strArray[2]), userForm.getOrganizationID());

			// values = daoInf2.retrieveSubjectListByUserID(userForm.getUserID(),
			// AcademicYearID);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void verifySubjectAssessmentIDPresent() throws Exception {

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

			/*
			 * Getting ayClassID by splitting check string by -
			 */
			String[] strArray = conform.getStandardDivName().split("-");

			System.out.println("Values..:" + conform.getExaminationID() + "_" + conform.getSubjectID() + "_"
					+ Integer.parseInt(strArray[2]));

			boolean check = daoInf2.verifysubjectAssessmentIDPresentOrNot(conform.getExaminationID(),
					conform.getSubjectID(), Integer.parseInt(strArray[2]));

			if (check) {
				object.put("check", "1");

				array.add(object);

				values.put("Release", array);
			} else {
				object.put("check", "0");

				array.add(object);

				values.put("Release", array);
			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectListByUserIDForCoScholastic() throws Exception {

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

			/*
			 * Getting ayClassID by splitting check string by -
			 */
			String[] strArray = conform.getCheck().split("-");
			System.out.println(
					"Integer.parseInt(strArray[2]):" + userForm.getUserID() + "_" + Integer.parseInt(strArray[2]));
			values = daoInf2.retrieveSubjectListByUserIDForCoscholastic(userForm.getUserID(),
					Integer.parseInt(strArray[2]), userForm.getOrganizationID());

			// values = daoInf2.retrieveSubjectListByUserID(userForm.getUserID(),
			// AcademicYearID);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveSubjectListByUserIDForExtraCurricular() throws Exception {

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

			/*
			 * Getting ayClassID by splitting check string by -
			 */
			String[] strArray = conform.getCheck().split("-");

			values = daoInf2.retrieveSubjectListByUserIDForExtraCurricular(userForm.getUserID(),
					Integer.parseInt(strArray[2]), userForm.getOrganizationID());

			// values = daoInf2.retrieveSubjectListByUserID(userForm.getUserID(),
			// AcademicYearID);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String loadStudents() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListByTeacher = configXMLUtil.sortHashMap(daoInf2.getStandardListByTeacher(userForm.getUserID()));

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		/*
		 * Getting ayClassID by splitting check string by -
		 */
		String[] strArray = conform.getStandardDivName().split("-");

		SubjectListByExamType = daoInf2.retrieveSubjectListByExamIDByExamType(userForm.getUserID(),
				Integer.parseInt(strArray[2]), userForm.getOrganizationID());
		/* } */

		String checkNew = daoInf2.verifySubjectType(conform.getSubjectID());

		request.setAttribute("SubjectType", checkNew);

		int subjectAssessmentID = daoInf2.retrievesubjectAssessmentID(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2]));

		String ActivityHeader = daoInf2.retrieveActivitiesNameBySubjectID(conform.getSubjectID(), subjectAssessmentID);

		request.setAttribute("ActivityHeader", ActivityHeader);

		/*
		 * Check whether subjectAssessmentID is present in ActivityAssessment table or
		 * not
		 */

		int ActivityAssessmentCheck = daoInf2.verifyActivityAssessmentBySubjectAssessmentID(subjectAssessmentID);

		request.setAttribute("ActivityAssessmentCheck", ActivityAssessmentCheck);

		/*
		 * check whether subjectID is -1 or -2 if so then set subjectTypeCheck value to
		 * Physical and creative found respectively, else set it to notFound
		 */
		String subjectTypeCheck = daoInf2.verifySubjectType1(conform.getSubjectID());

		// System.out.println("subjectTypeCheck : " + subjectTypeCheck);
		request.setAttribute("subjectTypeCheck", subjectTypeCheck);

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2])));

		examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(strArray[2]),
				conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

		studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(strArray[2]),
				conform.getExaminationID(), conform.getSubjectID());

		if (examinatrionList == null && studentAssessmentList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (examinatrionList.size() == 0 && studentAssessmentList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String renderConfigurePersonlityDevelopment() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListForPersonalityDevelopment = configXMLUtil.sortHashMap(daoInf2
				.getStandardListByTeacherForPersonalityDevelopment(userForm.getUserID(), userForm.getOrganizationID()));

		if (StandardListForPersonalityDevelopment == null || StandardListForPersonalityDevelopment.size() == 0) {

			addActionError("You are not assigned as subject teacher.");
			request.setAttribute("Teacher", "Yes");
		}

		// Verifying classTeacher for Standard
		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {
			request.setAttribute("classTeacherCheck", "Yes");
		} else {
			request.setAttribute("classTeacherCheck", "No");
		}

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		SubjectListForPersonalityDevelopment = new HashMap<Integer, String>();

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), conform.getAYClassID()));

		return SUCCESS;
	}

	public String loadStudentsForPersonlityDevelopment() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListForPersonalityDevelopment = configXMLUtil.sortHashMap(daoInf2
				.getStandardListByTeacherForPersonalityDevelopment(userForm.getUserID(), userForm.getOrganizationID()));

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		String[] strArray = conform.getStandardDivName().split("-");

		SubjectListForPersonalityDevelopment = daoInf2.retrieveSubjectListByExamIDByForPersonalityDevelopment(
				userForm.getUserID(), Integer.parseInt(strArray[2]), userForm.getOrganizationID());

		String checkNew = daoInf2.verifySubjectType(conform.getSubjectID());

		request.setAttribute("SubjectType", checkNew);

		/*
		 * check whether subjectID is -1 or -2 if so then set subjectTypeCheck value to
		 * Physical and creative found respectively, else set it to notFound
		 */
		String subjectTypeCheck = daoInf2.verifySubjectType1(conform.getSubjectID());

		// System.out.println("subjectTypeCheck : " + subjectTypeCheck);
		request.setAttribute("subjectTypeCheck", subjectTypeCheck);

		String[] array = conform.getStandardDivName().split("-");

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(array[2])));

		boolean check = daoInf2.verifysubjectAssessmentIDPresentOrNot(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2]));
		if (check) {

			request.setAttribute("PersonalityGradeCheck", "Found");
		} else {

			request.setAttribute("PersonalityGradeCheck", "NotFound");
		}

		examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(array[2]),
				conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

		studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(array[2]),
				conform.getExaminationID(), conform.getSubjectID());

		if (examinatrionList == null && studentAssessmentList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (examinatrionList.size() == 0 && studentAssessmentList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String renderConfigureCoScholastic() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListByCoScholastic = configXMLUtil.sortHashMap(
				daoInf2.getStandardListByTeacherForCoScholastic(userForm.getUserID(), userForm.getOrganizationID()));

		if (StandardListByCoScholastic == null || StandardListByCoScholastic.size() == 0) {

			request.setAttribute("classTeacherCheck", "No");

			request.setAttribute("Teacher", "Yes");
		}

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		SubjectListByCoScholastic = new HashMap<Integer, String>();

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), conform.getAYClassID()));

		return SUCCESS;
	}

	public String loadStudentsByCoScholastic() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListByCoScholastic = configXMLUtil.sortHashMap(
				daoInf2.getStandardListByTeacherForCoScholastic(userForm.getUserID(), userForm.getOrganizationID()));

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		String[] strArray = conform.getStandardDivName().split("-");

		SubjectListByCoScholastic = daoInf2.retrieveSubjectListByExamIDByForCoScholastic(userForm.getUserID(),
				Integer.parseInt(strArray[2]), userForm.getOrganizationID());
		/* } */

		String checkNew = daoInf2.verifySubjectType(conform.getSubjectID());

		request.setAttribute("SubjectType", checkNew);

		/*
		 * check whether subjectID is -1 or -2 if so then set subjectTypeCheck value to
		 * Physical and creative found respectively, else set it to notFound
		 */
		String subjectTypeCheck = daoInf2.verifySubjectType1(conform.getSubjectID());

		// System.out.println("subjectTypeCheck : " + subjectTypeCheck);
		request.setAttribute("subjectTypeCheck", subjectTypeCheck);

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2])));

		int subjectAssessmentID = daoInf2.retrievesubjectAssessmentID(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2]));

		String ActivityHeader = daoInf2.retrieveActivitiesNameBySubjectID(conform.getSubjectID(), subjectAssessmentID);

		request.setAttribute("ActivityHeader", ActivityHeader);

		examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(strArray[2]),
				conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

		studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(strArray[2]),
				conform.getExaminationID(), conform.getSubjectID());

		if (examinatrionList == null && studentAssessmentList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (examinatrionList.size() == 0 && studentAssessmentList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String generateGradesForPersonalityDevelopment() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListForPersonalityDevelopment = configXMLUtil.sortHashMap(daoInf2
				.getStandardListByTeacherForPersonalityDevelopment(userForm.getUserID(), userForm.getOrganizationID()));

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		String[] strArray = conform.getStandardDivName().split("-");

		SubjectListForPersonalityDevelopment = daoInf2.retrieveSubjectListByExamIDByForPersonalityDevelopment(
				userForm.getUserID(), Integer.parseInt(strArray[2]), userForm.getOrganizationID());

		String checkNew = daoInf2.verifySubjectType(conform.getSubjectID());

		request.setAttribute("SubjectType", checkNew);

		/*
		 * check whether subjectID is -1 or -2 if so then set subjectTypeCheck value to
		 * Physical and creative found respectively, else set it to notFound
		 */
		String subjectTypeCheck = daoInf2.verifySubjectType1(conform.getSubjectID());

		// System.out.println("subjectTypeCheck : " + subjectTypeCheck);
		request.setAttribute("subjectTypeCheck", subjectTypeCheck);

		String[] array = conform.getStandardDivName().split("-");

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(array[2])));

		int ExamID = daoInf2.retrieveExamIDForTermI(userForm.getAcademicYearID());

		int subjectAssessmentID = daoInf2.retrievesubjectAssessmentID(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(array[2]));

		studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentListNew(Integer.parseInt(array[2]), ExamID,
				conform.getSubjectID(), subjectAssessmentID);

		request.setAttribute("generatedGrades", "Yes");

		if (studentAssessmentList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentAssessmentList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String renderConfigureExtraCurricularActivity() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListForExtraCurricular = configXMLUtil.sortHashMap(
				daoInf2.getStandardListByTeacherForExtraCurricular(userForm.getUserID(), userForm.getOrganizationID()));

		if (StandardListForExtraCurricular == null || StandardListForExtraCurricular.size() == 0) {

			addActionError("You are not assigned as subject teacher.");
			request.setAttribute("Teacher", "Yes");
		}

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		SubjectListForExtraCurricularActivity = new HashMap<Integer, String>();

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), conform.getAYClassID()));

		return SUCCESS;
	}

	public String loadStudentsForExtraCurricular() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListForExtraCurricular = configXMLUtil.sortHashMap(
				daoInf2.getStandardListByTeacherForExtraCurricular(userForm.getUserID(), userForm.getOrganizationID()));

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		String[] strArray = conform.getStandardDivName().split("-");

		SubjectListForExtraCurricularActivity = daoInf2.retrieveSubjectListByExamIDByForExtraCurricularActivity(
				userForm.getUserID(), Integer.parseInt(strArray[2]), userForm.getOrganizationID());

		String checkNew = daoInf2.verifySubjectType(conform.getSubjectID());

		request.setAttribute("SubjectType", checkNew);

		/*
		 * check whether subjectID is -1 or -2 if so then set subjectTypeCheck value to
		 * Physical and creative found respectively, else set it to notFound
		 */
		String subjectTypeCheck = daoInf2.verifySubjectType1(conform.getSubjectID());

		// System.out.println("subjectTypeCheck : " + subjectTypeCheck);
		request.setAttribute("subjectTypeCheck", subjectTypeCheck);

		String[] array = conform.getStandardDivName().split("-");

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(array[2])));

		examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(array[2]),
				conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

		studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(array[2]),
				conform.getExaminationID(), conform.getSubjectID());

		System.out.println("Id's: " + Integer.parseInt(array[2]) + "-" + conform.getExaminationID() + "-"
				+ conform.getSubjectID());

		System.out.println("examinatrionList: " + examinatrionList);

		System.out.println("studentAssessmentList: " + studentAssessmentList);

		if (examinatrionList == null && studentAssessmentList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (examinatrionList.size() == 0 && studentAssessmentList.size() == 0) {

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
	public String addStudentAssessment() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String value = conform.getValue();

		if (value.equals("Scholastic")) {

			StandardListByTeacher = configXMLUtil.sortHashMap(daoInf2.getStandardListByTeacher(userForm.getUserID()));
		} else if (value.equals("Personality Development & Life Skills")) {

			StandardListForPersonalityDevelopment = configXMLUtil
					.sortHashMap(daoInf2.getStandardListByTeacherForPersonalityDevelopment(userForm.getUserID(),
							userForm.getOrganizationID()));
		} else if (value.equals("Extra-curricular")) {

			StandardListForExtraCurricular = configXMLUtil.sortHashMap(daoInf2
					.getStandardListByTeacherForExtraCurricular(userForm.getUserID(), userForm.getOrganizationID()));
		}

		conform.setAcademicYearID(userForm.getAcademicYearID());

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		String checkNew = daoInf2.verifySubjectType(conform.getSubjectID());

		request.setAttribute("SubjectType", checkNew);

		/*
		 * check whether subjectID is -1 or -2 if so then set subjectTypeCheck value to
		 * Physical and creative found respectively, else set it to notFound
		 */
		String subjectTypeCheck = "";

		if (conform.getSubjectID() == -1) {
			subjectTypeCheck = "Physicalfound";
		} else if (conform.getSubjectID() == -2) {
			subjectTypeCheck = "Creativefound";
		} else {
			subjectTypeCheck = "notFound";
		}

		request.setAttribute("subjectTypeCheck", subjectTypeCheck);

		request.setAttribute("PersonalityGradeCheck", "Found");

		/*
		 * Getting ayClassID by splitting check string by -
		 */
		String[] strArray = conform.getStandardDivName().split("-");

		int subjectAssessmentID = daoInf2.retrievesubjectAssessmentID(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2]));

		String ActivityHeader = daoInf2.retrieveActivitiesNameBySubjectID(conform.getSubjectID(), subjectAssessmentID);

		request.setAttribute("ActivityHeader", ActivityHeader);

		/*
		 * Check whether subjectAssessmentID is present in ActivityAssessment table or
		 * not
		 */

		int ActivityAssessmentCheck = daoInf2.verifyActivityAssessmentBySubjectAssessmentID(subjectAssessmentID);

		request.setAttribute("ActivityAssessmentCheck", ActivityAssessmentCheck);

		if (value.equals("Scholastic")) {

			SubjectListByExamType = daoInf2.retrieveSubjectListByExamIDByExamType(userForm.getUserID(),
					Integer.parseInt(strArray[2]), userForm.getOrganizationID());
		} else if (value.equals("Personality Development & Life Skills")) {

			SubjectListForPersonalityDevelopment = daoInf2.retrieveSubjectListByExamIDByForPersonalityDevelopment(
					userForm.getUserID(), Integer.parseInt(strArray[2]), userForm.getOrganizationID());
		} else if (value.equals("Extra-curricular")) {

			SubjectListForExtraCurricularActivity = daoInf2.retrieveSubjectListByExamIDByForExtraCurricularActivity(
					userForm.getUserID(), Integer.parseInt(strArray[2]), userForm.getOrganizationID());
		}

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(strArray[2])));

		conform.setAYClassID(Integer.parseInt(strArray[2]));

		message = serviceInf.addStudentAssessment(conform, ActivityAssessmentCheck);

		if (message.equals("success")) {

			addActionMessage("Details added successfully.");

			examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(strArray[2]),
					conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

			studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(strArray[2]),
					conform.getExaminationID(), conform.getSubjectID());

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		} else {

			addActionError("Failed to add details. Please check server logs for more details.");

			examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(strArray[2]),
					conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

			studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(strArray[2]),
					conform.getExaminationID(), conform.getSubjectID());

			request.setAttribute("loadStudentSearch", "Enabled");

			return ERROR;
		}
	}

	public String addStudentAssessmentForCoScholastic() throws Exception {

		StuduntDAOInf daoInf = new StudentDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String value = conform.getValue();

		StandardListByCoScholastic = configXMLUtil.sortHashMap(
				daoInf2.getStandardListByTeacherForCoScholastic(userForm.getUserID(), userForm.getOrganizationID()));

		conform.setAcademicYearID(userForm.getAcademicYearID());

		ExamList = daoInf2.getExamList(userForm.getAcademicYearID());

		String[] strArray = conform.getStandardDivName().split("-");

		SubjectListByCoScholastic = daoInf2.retrieveSubjectListByExamIDByForCoScholastic(userForm.getUserID(),
				Integer.parseInt(strArray[2]), userForm.getOrganizationID());

		String[] array = conform.getStandardDivName().split("-");

		int subjectAssessmentID = daoInf2.retrievesubjectAssessmentID(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(array[2]));

		String ActivityHeader = daoInf2.retrieveActivitiesNameBySubjectID(conform.getSubjectID(), subjectAssessmentID);

		request.setAttribute("ActivityHeader", ActivityHeader);

		request.setAttribute("GradeBased", daoInf2.retrieveGradeBasedValue(conform.getExaminationID(),
				conform.getSubjectID(), Integer.parseInt(array[2])));

		conform.setAYClassID(Integer.parseInt(array[2]));

		message = serviceInf.addStudentAssessmentForCoScholastic(conform);

		if (message.equals("success")) {

			addActionMessage("Details added successfully.");

			examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(array[2]),
					conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

			studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(array[2]),
					conform.getExaminationID(), conform.getSubjectID());

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		} else {

			addActionError("Failed to add details. Please check server logs for more details.");

			examinatrionList = daoInf2.retrieveStudentListForExaminationAndSubject(Integer.parseInt(array[2]),
					conform.getExaminationID(), conform.getSubjectID(), userForm.getUserID());

			studentAssessmentList = daoInf2.retrieveExistingStudentAssessmentList(Integer.parseInt(array[2]),
					conform.getExaminationID(), conform.getSubjectID());

			request.setAttribute("loadStudentSearch", "Enabled");

			return ERROR;
		}
	}

	public void retrieveSubjectListByUserIDExamType() throws Exception {
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

			boolean check = daoInf2.verifyExamType(conform.getExaminationID(), userForm.getAcademicYearID());

			if (check) {

				values = daoInf2.retrieveSubjectListForCLassTeacher1(userForm.getUserID(),
						userForm.getAcademicYearID());

			} else {

				values = daoInf2.retrieveSubjectListForCLassTeacherByExamType1(userForm.getUserID(),
						userForm.getAcademicYearID(), userForm.getOrganizationID());
			}

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String loadMyClassStudent() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String[] strArray = conform.getStandardDivName().split("-");

		int AYClassID = daoInf2.retrieveAYCLassID(Integer.parseInt(strArray[0]), Integer.parseInt(strArray[1]),
				userForm.getAcademicYearID());

		// getting StandardList value
		StandardListByTeacher = configXMLUtil.sortHashMap(daoInf2.getStandardListByTeacher(userForm.getUserID()));

		int check = daoInf2.verifyClassTeacherForStandard(userForm.getUserID(), userForm.getAcademicYearID(),
				Integer.parseInt(strArray[2]));

		if (check == 1) {

			subjectNameList = daoInf2.retrieveSubjectListForCLassTeacher(userForm.getUserID(),
					userForm.getAcademicYearID());

			request.setAttribute("classTeacherCheck", "Yes");

		} else {

			subjectNameList = daoInf2.retrieveSubjectListByUserIDByStandard(userForm.getUserID(),
					Integer.parseInt(strArray[2]));

			request.setAttribute("classTeacherCheck", "No");
		}

		/*
		 * subjectNameList =
		 * daoInf2.retrieveSubjectListForCLassTeacher(userForm.getUserID(),
		 * AcademicYearID);
		 * 
		 * request.setAttribute("classTeacherCheck", "Yes");
		 */

		String StandardName = daoInf2.getStandardNameByStandardID(Integer.parseInt(strArray[0]));

		String Stage = daoInf2.getStandardStageByStandardID(Integer.parseInt(strArray[0]));

		System.out.println("Standard Stage: " + Stage);

		if (Stage.equals("Primary")) {

			request.setAttribute("StudentsFound", "Found");

			studentCustomList = daoInf2.retrieveExistingStudentAssessmentListForClassTeacher(AYClassID);

			NewExaminationList = daoInf2.retrieveExaminationList(userForm.getAcademicYearID(), conform.getTerm(),
					AYClassID);

			request.setAttribute("ExaminationList", NewExaminationList);

			/* double = 0D; */

			int value, value1, value4, finalOutOfMarks = 0;
			int value3 = 0, value2 = 0;
			int marksObtained, marksObtained1, marksObtained2, marksObtained3, marksObtained4 = 0;
			double value5, value6, finalSEAMarks = 0D;
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
			int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
					totalMarksScaled = 0;

			String grade = "";

			studentsCustomReportList = new ArrayList<ConfigurationForm>();

			SubjectEnrichmentList = new ArrayList<String>();

			NotebookList = new ArrayList<String>();

			for (Integer studentID : studentCustomList.keySet()) {

				String marksObtainedValue = "", marksObtainedValue1 = "", marksObtainedNewValue = "",
						marksObtainedNewValue1 = "";

				String[] array = studentCustomList.get(studentID).split("=");

				String studentName = array[0];

				String rollNo = array[1];

				ConfigurationForm form = new ConfigurationForm();

				/*
				 * request.setAttribute("GradeBased",
				 * daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
				 * conform.getSubjectID(), AYClassID));
				 */
				int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
						conform.getSubjectID(), AYClassID);

				request.setAttribute("GradeBased", gradeCheck);
				// System.out.println("grade check.." + gradeCheck);

				if (gradeCheck == 1) {

					boolean termEdCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Term End"));

					grade = daoInf2.retrieveGradeValue(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Term End"));
					if (grade.isEmpty()) {
						continue;
					}
					System.out.println("grade ::::" + grade);
					if (termEdCheck) {
						grade = "ex";
					}

					form.setStudentName(studentName);
					form.setRollNumber(Integer.parseInt(rollNo));
					form.setTermEndMarks("-");
					form.setTermEndMarksObtained("-");
					form.setUnitTestMarksObtained("-");
					form.setFinalTotalMarks("-");
					form.setUnitTestMarks("-");
					form.setSubjectEnrichmentMarksObtained("-");
					form.setSubjectEnrichmentMarks1Obtained("-");
					form.setNotebookMarksObtained("-");
					form.setNotebookMarks("-");
					form.setGrade(grade);

					studentsCustomReportList.add(form);

				} else {

					boolean termEdCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Term End"));

					boolean unitTestCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Unit Test"));

					marksObtained = daoInf2.retrievemarksObtained(conform.getSubjectID(),
							NewExaminationList.get("Term End"), studentID, AYClassID);

					marksObtained1 = daoInf2.retrievemarksObtained(conform.getSubjectID(),
							NewExaminationList.get("Unit Test"), studentID, AYClassID);

					for (Entry<String, Integer> Subjectentry : NewExaminationList.entrySet()) {
						if (Subjectentry.getKey().startsWith("Subject Enrichment")) {

							marksObtainedNewValue = daoInf2.retrievemarksObtainedForSubjectEnrichment(
									conform.getSubjectID(), Subjectentry.getValue(), studentID, AYClassID);

							marksObtainedValue += ", " + marksObtainedNewValue;

						}
					}

					if (marksObtainedValue.startsWith(",")) {
						marksObtainedValue = marksObtainedValue.substring(1);
					}

					SubjectEnrichmentList.add(marksObtainedValue);

					request.setAttribute("SubjectEnrichmentMarksObtainedValue", SubjectEnrichmentList);

					for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
						if (entry.getKey().startsWith("Notebook")) {

							marksObtainedNewValue1 = daoInf2.retrievemarksObtainedForSubjectEnrichment(
									conform.getSubjectID(), entry.getValue(), studentID, AYClassID);

							marksObtainedValue1 += ", " + marksObtainedNewValue1;
							// System.out.println("marksObtainedValue1 :"+marksObtainedValue1);
						}
					}

					if (marksObtainedValue1.startsWith(",")) {
						marksObtainedValue1 = marksObtainedValue1.substring(1);
					}

					NotebookList.add(marksObtainedValue1);

					request.setAttribute("NotebookMarksObtainedValue", NotebookList);

					value = daoInf2.retrieveScholasticGradeList(conform.getSubjectID(),
							NewExaminationList.get("Term End"), studentID, AYClassID);

					value1 = daoInf2.retrieveScholasticGradeList(conform.getSubjectID(),
							NewExaminationList.get("Unit Test"), studentID, AYClassID);

					value4 = daoInf2.retrieveScholasticGradeList1(conform.getSubjectID(), studentID, AYClassID,
							conform.getTerm());

					scaleTo = daoInf2.retrieveScaleTo(conform.getSubjectID(), NewExaminationList.get("Term End"),
							AYClassID);

					scaleTo1 = daoInf2.retrieveScaleTo(conform.getSubjectID(), NewExaminationList.get("Unit Test"),
							AYClassID);

					scaleTo4 = daoInf2.retrieveScaleTo1(conform.getSubjectID(), AYClassID);

					toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

					// System.out.println("value4 :"+value4);

					totalMarksScaled = (value + value1 + value4);

					// toatlScaleTo1 = totalMarksScaled;
					/* } */

					toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;
					System.out.println("toatlScaleTo1 ::::: " + toatlScaleTo1);
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

					/*
					 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
					 * else set grade as calculated
					 * ,marksObtained1,marksObtained2,marksObtained3,marksObtained4
					 */
					if (termEdCheck) {
						grade = "ex";
						form.setTermEndMarksObtained("ex");
						form.setTermEndMarks("ex");
						form.setFinalTotalMarks("ex");
					} else {
						form.setTermEndMarksObtained("" + marksObtained);
						form.setTermEndMarks("" + value);
						form.setFinalTotalMarks("" + totalMarksScaled);
					}

					if (unitTestCheck) {
						form.setUnitTestMarksObtained("ex");
						form.setUnitTestMarks("ex");
						grade = "ex";
						form.setFinalTotalMarks("ex");
					} else {
						form.setUnitTestMarksObtained("" + marksObtained1);
						form.setUnitTestMarks("" + value1);
						form.setFinalTotalMarks("" + totalMarksScaled);
					}

					form.setNotebookMarks("" + value4);
					form.setRollNumber(Integer.parseInt(rollNo));
					form.setStudentName(studentName);
					form.setGrade(grade);

					studentsCustomReportList.add(form);

				}

			}

			request.setAttribute("studentsCustomReportList", studentsCustomReportList);

		} else {

			request.setAttribute("StudentsFound", "NotFound");

			studentCustomList = daoInf2.retrieveExistingStudentAssessmentListForClassTeacher(AYClassID);

			NewExaminationList = daoInf2.retrieveExaminationList(userForm.getAcademicYearID(), conform.getTerm(),
					AYClassID);

			request.setAttribute("ExaminationList", NewExaminationList);

			/* double = 0D; */

			int value, value1, value2, value3, value4, value8, value7, finalOutOfMarks = 0;
			int marksObtained, marksObtained1, marksObtained2, marksObtained3, marksObtained4 = 0;
			double value5, value6, finalSEAMarks = 0D;
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
			int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
					totalMarksScaled = 0;

			String grade = "";

			studentsCustomReportList = new ArrayList<ConfigurationForm>();

			SubjectEnrichmentList = new ArrayList<String>();

			NotebookList = new ArrayList<String>();

			PortfolioList = new ArrayList<String>();

			MultipleAssessmentList = new ArrayList<String>();

			for (Integer studentID : studentCustomList.keySet()) {

				String marksObtainedValue = "", marksObtainedValue1 = "", marksObtainedValue2 = "",
						marksObtainedValue3 = "", marksObtainedNewValue = "", marksObtainedNewValue1 = "",
						marksObtainedNewValue2 = "", marksObtainedNewValue3 = "";

				String[] array = studentCustomList.get(studentID).split("=");

				String studentName = array[0];

				String rollNo = array[1];

				ConfigurationForm form = new ConfigurationForm();

				int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
						conform.getSubjectID(), AYClassID);

				// System.out.println("grade check.." + gradeCheck);
				request.setAttribute("GradeBased", gradeCheck);
				if (gradeCheck == 1) {

					// String subjectName = daoInf2.retrieveSubject(conform.getSubjectID());

					boolean termEdCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Term End"));
					/* System.out.println("termEdCheck: " + termEdCheck); */
					grade = daoInf2.retrieveGradeValue(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Term End"));
					System.out.println("grade: " + grade);
					if (grade.isEmpty()) {
						continue;
					}
					if (termEdCheck) {
						grade = "ex";
					}
					// System.out.println("studentName: " + studentName);

					form.setStudentName(studentName);
					form.setRollNumber(Integer.parseInt(rollNo));
					form.setTermEndMarks("-");
					form.setTermEndMarksObtained("-");
					form.setUnitTestMarksObtained("-");
					form.setFinalTotalMarks("-");
					form.setUnitTestMarks("-");
					form.setSubjectEnrichmentMarksObtained("-");
					form.setSubjectEnrichmentMarks("-");
					form.setNotebookMarksObtained("-");
					form.setNotebookMarks("-");
					form.setPortfolioMarks("-");
					form.setPortfolioFinalMarks("-");
					form.setMultipleAssessmentMarks("-");
					form.setMultipleAssessmentFinalMarks("-");

					form.setGrade(grade);

					studentsCustomReportList.add(form);

				} else {

					boolean termEdCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Term End"));

					boolean unitTestCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID, AYClassID,
							NewExaminationList.get("Unit Test"));

					/*
					 * boolean sbjEnrch1Check = daoInf2.verifyAbsent(conform.getSubjectID(),
					 * studentID, AYClassID, NewExaminationList.get("Subject Enrichment1"));
					 * 
					 * 
					 * boolean sbjEnrch2Check =
					 * daoInf2.verifyAbsent(conform.getSubjectID(),studentID,
					 * AYClassID,NewExaminationList.get("Subject Enrichment2"));
					 * 
					 * 
					 * boolean noteBkCheck = daoInf2.verifyAbsent(conform.getSubjectID(), studentID,
					 * AYClassID, NewExaminationList.get("Notebook"));
					 */

					marksObtained = daoInf2.retrievemarksObtained(conform.getSubjectID(),
							NewExaminationList.get("Term End"), studentID, AYClassID);

					marksObtained1 = daoInf2.retrievemarksObtained(conform.getSubjectID(),
							NewExaminationList.get("Unit Test"), studentID, AYClassID);

					for (Entry<String, Integer> Subjectentry : NewExaminationList.entrySet()) {
						if (Subjectentry.getKey().startsWith("Subject Enrichment")) {

							// System.out.println("Subject Enrichment: " + Subjectentry.getValue());
							marksObtainedNewValue = daoInf2.retrievemarksObtainedForSubjectEnrichment(
									conform.getSubjectID(), Subjectentry.getValue(), studentID, AYClassID);

							marksObtainedValue += ", " + marksObtainedNewValue;

							/* System.out.println("marksObtainedNewValue: " + marksObtainedValue); */
						}
					}

					if (marksObtainedValue.startsWith(",")) {
						marksObtainedValue = marksObtainedValue.substring(1);
					}

					SubjectEnrichmentList.add(marksObtainedValue);

					request.setAttribute("SubjectEnrichmentMarksObtainedValue", SubjectEnrichmentList);

					for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
						if (entry.getKey().startsWith("Notebook")) {

							marksObtainedNewValue1 = daoInf2.retrievemarksObtainedForSubjectEnrichment(
									conform.getSubjectID(), entry.getValue(), studentID, AYClassID);

							marksObtainedValue1 += ", " + marksObtainedNewValue1;
							/* System.out.println("marksObtainedValue1 :" + marksObtainedValue1); */
						}
					}

					if (marksObtainedValue1.startsWith(",")) {
						marksObtainedValue1 = marksObtainedValue1.substring(1);
					}

					NotebookList.add(marksObtainedValue1);

					request.setAttribute("NotebookMarksObtainedValue", NotebookList);

					for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
						if (entry.getKey().startsWith("Portfolio")) {

							marksObtainedNewValue2 = daoInf2.retrievemarksObtainedForSubjectEnrichment(
									conform.getSubjectID(), entry.getValue(), studentID, AYClassID);

							marksObtainedValue2 += ", " + marksObtainedNewValue2;
							System.out.println("marksObtainedValue Portfolio :" + marksObtainedValue2);
						}
					}

					if (marksObtainedValue2.startsWith(",")) {
						marksObtainedValue2 = marksObtainedValue2.substring(1);
					}

					if (marksObtainedValue2 != "" || marksObtainedValue2 != null) {
						PortfolioList.add(marksObtainedValue2);
					}

					// System.out.println("PortfolioList size :"+PortfolioList.size());

					request.setAttribute("PortfolioLMarksObtainedValue", PortfolioList);

					for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
						if (entry.getKey().startsWith("Multiple Assessment")) {

							marksObtainedNewValue3 = daoInf2.retrievemarksObtainedForSubjectEnrichment(
									conform.getSubjectID(), entry.getValue(), studentID, AYClassID);

							marksObtainedValue3 += ", " + marksObtainedNewValue3;
							System.out.println("marksObtainedValue3 :" + marksObtainedValue3);
						}
					}

					if (marksObtainedValue3.startsWith(",")) {
						marksObtainedValue3 = marksObtainedValue3.substring(1);
					}

					MultipleAssessmentList.add(marksObtainedValue3);

					request.setAttribute("MultipleAssessmentMarksObtainedValue", MultipleAssessmentList);

					value = daoInf2.retrieveScholasticGradeList(conform.getSubjectID(),
							NewExaminationList.get("Term End"), studentID, AYClassID);

					value1 = daoInf2.retrieveScholasticGradeList(conform.getSubjectID(),
							NewExaminationList.get("Unit Test"), studentID, AYClassID);

					value2 = daoInf2.retrieveScholasticGradeListNew(conform.getSubjectID(), "Subject Enrichment",
							studentID, AYClassID, conform.getTerm());

					value3 = daoInf2.retrieveScholasticGradeListNew(conform.getSubjectID(), "Notebook", studentID,
							AYClassID, conform.getTerm());

					value7 = daoInf2.retrieveScholasticGradeListNew(conform.getSubjectID(), "Portfolio", studentID,
							AYClassID, conform.getTerm());

					value8 = daoInf2.retrieveScholasticGradeListNew(conform.getSubjectID(), "Multiple Assessment",
							studentID, AYClassID, conform.getTerm());

					scaleTo = daoInf2.retrieveScaleTo(conform.getSubjectID(), NewExaminationList.get("Term End"),
							AYClassID);

					scaleTo1 = daoInf2.retrieveScaleTo(conform.getSubjectID(), NewExaminationList.get("Unit Test"),
							AYClassID);

					scaleTo2 = daoInf2.retrieveScaleToNew(conform.getSubjectID(), "Subject Enrichment", AYClassID,
							userForm.getAcademicYearID(), conform.getTerm());

					scaleTo3 = daoInf2.retrieveScaleToNew(conform.getSubjectID(), "Notebook", AYClassID,
							userForm.getAcademicYearID(), conform.getTerm());

					scaleTo4 = daoInf2.retrieveScaleToNew(conform.getSubjectID(), "Portfolio", AYClassID,
							userForm.getAcademicYearID(), conform.getTerm());

					scaleTo5 = daoInf2.retrieveScaleToNew(conform.getSubjectID(), "Multiple Assessment", AYClassID,
							userForm.getAcademicYearID(), conform.getTerm());

					totalMarksScaled = (value + value1 + value2 + value3 + value7 + value8);

					toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

					toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;
					System.out.println("totalMarksScaled:::" + totalMarksScaled);
					System.out.println("toatlScaleTo1 ::::::::" + toatlScaleTo1);
					/* Calculating Final Grades */
					if (toatlScaleTo1 >= 91 && toatlScaleTo1 <= 100) {
						System.out.println("toatlScaleTo1 A1::::::::" + toatlScaleTo1);
						grade = "A1";
					} else if (toatlScaleTo1 >= 81 && toatlScaleTo1 <= 90) {
						System.out.println("toatlScaleTo1 A2::::::::" + toatlScaleTo1);
						grade = "A2";
					} else if (toatlScaleTo1 >= 71 && toatlScaleTo1 <= 80) {
						System.out.println("toatlScaleTo1 B1::::::::" + toatlScaleTo1);
						grade = "B1";
					} else if (toatlScaleTo1 >= 61 && toatlScaleTo1 <= 70) {
						System.out.println("toatlScaleTo1 B2::::::::" + toatlScaleTo1);
						grade = "B2";
					} else if (toatlScaleTo1 >= 51 && toatlScaleTo1 <= 60) {
						System.out.println("toatlScaleTo1 C1::::::::" + toatlScaleTo1);
						grade = "C1";
					} else if (toatlScaleTo1 >= 41 && toatlScaleTo1 <= 50) {
						System.out.println("toatlScaleTo1 C2::::::::" + toatlScaleTo1);
						grade = "C2";
					} else if (toatlScaleTo1 >= 33 && toatlScaleTo1 <= 40) {
						System.out.println("toatlScaleTo1 D::::::::" + toatlScaleTo1);
						grade = "D";
					} else {
						System.out.println("toatlScaleTo1 E::::::::" + toatlScaleTo1);
						grade = "E";
					}

					/*
					 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
					 * else set grade as calculated
					 * ,marksObtained1,marksObtained2,marksObtained3,marksObtained4
					 */
					if (termEdCheck) {
						grade = "ex";
						form.setTermEndMarksObtained("ex");
						form.setTermEndMarks("ex");
						form.setFinalTotalMarks("ex");
					} else {
						form.setTermEndMarksObtained("" + marksObtained);
						form.setTermEndMarks("" + value);
						form.setFinalTotalMarks("" + totalMarksScaled);
					}

					if (unitTestCheck) {
						grade = "ex";
						form.setUnitTestMarksObtained("ex");
						form.setUnitTestMarks("ex");
						form.setFinalTotalMarks("ex");
					} else {
						form.setUnitTestMarksObtained("" + marksObtained1);
						form.setUnitTestMarks("" + value1);
						form.setFinalTotalMarks("" + totalMarksScaled);
					}
					System.out.println("toatlScaleTo1 ::::::::" + toatlScaleTo1 + "Grade :::: " + grade);
					form.setNotebookMarks("" + value3);
					form.setSubjectEnrichmentMarks("" + value2);
					form.setPortfolioFinalMarks("" + value7);
					form.setMultipleAssessmentFinalMarks("" + value8);
					form.setRollNumber(Integer.parseInt(rollNo));
					form.setStudentName(studentName);
					form.setGrade(grade);

					studentsCustomReportList.add(form);
				}
			}
			request.setAttribute("studentsCustomReportList", studentsCustomReportList);
		}

		return SUCCESS;
	}

	public void retrieveSubjectListByUserIDandStandardID() throws Exception {
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
			/*
			 * Getting ayClassID by splitting check string by -
			 */

			String[] strArray = conform.getCheck().split("-");

			int check = daoInf2.verifyClassTeacherForStandard(userForm.getUserID(), userForm.getAcademicYearID(),
					Integer.parseInt(strArray[2]));

			if (check == 1) {

				values = daoInf2.retrieveSubjectListForClassTeacherBYUserID(userForm.getUserID(),
						userForm.getAcademicYearID());

			} else {

				values = daoInf2.retrieveSubjectListByUserIDStandard(userForm.getUserID(),
						Integer.parseInt(strArray[2]));
			}
			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Division and StandardID");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String renderClassSubject() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		// getting StandardList value
		StandardListByTeacher = configXMLUtil.sortHashMap(daoInf2.getStandardListByTeacher(userForm.getUserID()));

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			request.setAttribute("classTeacherCheck", "Yes");

			subjectNameList = new HashMap<Integer, String>();

		} else {

			boolean checkSubjectTeacher = daoInf2.verifySubjectTeacher(userForm.getUserID(),
					userForm.getAcademicYearID());
			if (checkSubjectTeacher) {

				request.setAttribute("classTeacherCheck", "No");

				subjectNameList = new HashMap<Integer, String>();

			} else {

				addActionError("You are not assigned as a class teacher to any class.");
			}
		}

		return SUCCESS;
	}

	public String renderClassStudents() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		boolean check = daoInf2.verifyClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());

		if (check) {

			boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
			if (check10th) {

				request.setAttribute("classTeacherCheck", "Yes");
				request.setAttribute("classTeacher10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacherCheck", "Yes");
				request.setAttribute("classTeacher10thCheck", "No");
			}

		} else {

			addActionError("You are not assigned as a class teacher to any class.");

			request.setAttribute("classTeacherCheck", "No");
		}

		return SUCCESS;
	}

	public String renderReportCardReview() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
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

	public String loadClassStudent() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
		if (check10th) {

			request.setAttribute("classTeacherCheck", "Yes");
			request.setAttribute("classTeacher10thCheck", "Yes");
		} else {

			request.setAttribute("classTeacherCheck", "Yes");
			request.setAttribute("classTeacher10thCheck", "No");
		}

		studentsList = daoInf2.retrieveClassStudentListForClassTeacher(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID(), conform.getTerm());

		request.setAttribute("studentsList", studentsList);

		request.setAttribute("standardID", conform.getStandardID());

		request.setAttribute("divisionID", conform.getDivisionID());

		request.setAttribute("termValue", conform.getTerm());

		if (studentsList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentsList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String loadClassStudentReview() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		request.setAttribute("classTeacherCheck", "Yes");

		studentsList = daoInf2.retrieveClassStudentListForClassTeacher(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID(), conform.getTerm());

		request.setAttribute("studentsList", studentsList);

		if (studentsList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentsList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String configureReport() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());
		// retriving Academic Year ID value

		String GradeValue = "";

		String StandardName = daoInf2.getStandardNameByStandardID(conform.getStandardID());

		String Stage = daoInf2.getStandardStageByStandardID(conform.getStandardID());

		if (conform.getTerm().equals("Term II")) {
			String StdDivName = daoInf2.retrieveStdDivName(conform.getStudentID());

			request.setAttribute("StdDivName", StdDivName);
		} else {
			request.setAttribute("StdDivName", "");
		}

		request.setAttribute("statusValue", conform.getResult());

		System.out.println("Standard Stage: " + Stage);

		if (Stage.equals("Primary")) {

			request.setAttribute("StudentsFound", "Found");

			studentDetailsList = daoInf2.retrieveStudentDetailsList(conform.getStudentID(), conform.getTerm(),
					conform.getAYClassID());

			// System.out.println("conform.getAYClassID() : " + conform.getAYClassID());
			coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			request.setAttribute("PersonalityDevelopmentGradeListNew", PersonalityDevelopmentGradeListNew);

			String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(conform.getTerm(),
					userForm.getAcademicYearID(), conform.getStandardID());

			// System.out.println("attendanceIDList" + attendanceIDList);

			if (attendanceIDList == null || attendanceIDList == "") {

				AttendanceList = new ArrayList<ConfigurationForm>();
				NewAttendanceList = new ArrayList<ConfigurationForm>();

			} else if (attendanceIDList.isEmpty()) {

				AttendanceList = new ArrayList<ConfigurationForm>();
				NewAttendanceList = new ArrayList<ConfigurationForm>();

			} else {

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(conform.getStudentID(), attendanceIDList,
						StandardName);
				NewAttendanceList = daoInf2.retrieveAttendanceListforStudent1(conform.getStudentID(), conform.getTerm(),
						conform.getStandardID());
			}

			String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(conform.getStandardID());
			System.out.println("NewSubjectList size: " + NewSubjectList.length());
			NewExaminationList = daoInf2.retrieveExaminationList(userForm.getAcademicYearID(), conform.getTerm(),
					conform.getAYClassID());

			int value, value1, value2, value3, value4, finalOutOfMarks, internalMarksValue = 0;
			double value5, value6, finalSEAMarks = 0D;
			int scaleTo, scaleTo1, scaleTo4 = 0;
			int toatlScaleTo, toatlScaleTo1, finalSEAmarksvalue, totalMarksScaled = 0;

			String grade = "";

			if (NewSubjectList.contains(",")) {

				String subArr[] = NewSubjectList.split(",");

				ScholasticGradeList = new ArrayList<ConfigurationForm>();

				for (int j = 0; j < subArr.length; j++) {

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
							Integer.parseInt(subArr[j]), conform.getAYClassID());

					if (gradeCheck == 1) {

						grade = "";

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), conform.getRegistrationID(),
								conform.getAYClassID(), NewExaminationList.get("Term End"));

						if (termEdCheck) {
							grade = "ex";
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;
						}

						form.setTermEndMarks("-");
						form.setFinalTotalMarks("-");
						form.setUnitTestMarks("-");
						// form.setSubjectEnrichmentMarks("-");
						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					} else {

						grade = "";

						boolean seAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Subject Enrichment")
									|| entry.getKey().startsWith("Notebook")) {

								boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!noteBkCheck) {

									System.out.println("noteBkCheck: " + noteBkCheck);

									seAbsentCheck = false;
								}
							}
						}

						System.out.println("seAbsentCheck new: " + seAbsentCheck);

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Unit Test"));

						value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getAYClassID());

						scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getAYClassID());

						scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), conform.getAYClassID());

						internalMarksValue = (value1 + value2);

						totalMarksScaled = (value + internalMarksValue);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

						toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

						System.out.println("toatlScaleTo1: " + toatlScaleTo1 + "-" + toatlScaleTo + "-"
								+ internalMarksValue + "-" + totalMarksScaled);

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

						/*
						 * To show only grade forEnhanced Marathi Subject in report card and show '-'
						 * for all exams
						 */
						if (subjectName.equals("Enhanced Marathi")) {

							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							form.setTermEndMarks("-");
							form.setUnitTestMarks("-");
							// form.setSubjectEnrichmentMarks("-");
							form.setFinalTotalMarks("-");

							if (termEdCheck) {
								grade = "ex";
							}

							if (unitTestCheck) {
								grade = "ex";
							}

							System.out.println("seAbsentCheck: " + seAbsentCheck);
							if (seAbsentCheck) {
								grade = "ex";
							}

						} else {

							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
							 * else set grade as calculated
							 */
							if (termEdCheck) {
								grade = "ex";
								form.setTermEndMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setTermEndMarks("" + value);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (unitTestCheck || seAbsentCheck) {
								form.setUnitTestMarks("ex");
								grade = "ex";
								form.setFinalTotalMarks("ex");
							} else {
								form.setUnitTestMarks("" + internalMarksValue);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (termEdCheck || unitTestCheck || seAbsentCheck) {

								form.setFinalTotalMarks("ex");
							} else {
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
						}

						if (grade.equals("E")) {
							GradeValue += ',' + subjectName;
						}

						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					}
				}
			}

		} else {

			boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
			if (check10th) {

				request.setAttribute("classTeacher10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacher10thCheck", "No");
			}

			boolean check9th10th = daoInf2.verify9th10thClassTeacher(userForm.getUserID(),
					userForm.getAcademicYearID());
			if (check9th10th) {

				request.setAttribute("classTeacher9th10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacher9th10thCheck", "No");
			}

			request.setAttribute("StudentsFound", "NotFound");
			studentDetailsList = daoInf2.retrieveStudentDetailsList(conform.getStudentID(), conform.getTerm(),
					conform.getAYClassID());

			coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());
			
			request.setAttribute("extraCurricularCheck", ExtraCurricularGradeList.size());

			System.out.println("ExtraCurricularGradeList: " + ExtraCurricularGradeList.size());

			PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			request.setAttribute("PersonalityDevelopmentGradeListNew", PersonalityDevelopmentGradeListNew);

			System.out.println("Term: " + conform.getTerm());

			String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(conform.getTerm(),
					userForm.getAcademicYearID(), conform.getStandardID());

			if (attendanceIDList == null || attendanceIDList == "") {

				AttendanceList = new ArrayList<ConfigurationForm>();
				NewAttendanceList = new ArrayList<ConfigurationForm>();
			} else if (attendanceIDList.isEmpty()) {

				AttendanceList = new ArrayList<ConfigurationForm>();
				NewAttendanceList = new ArrayList<ConfigurationForm>();
			} else {

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(conform.getStudentID(), attendanceIDList,
						StandardName);
				NewAttendanceList = daoInf2.retrieveAttendanceListforStudent1(conform.getStudentID(), conform.getTerm(),
						conform.getStandardID());
			}

			String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(conform.getStandardID());

			NewExaminationList = daoInf2.retrieveExaminationList(userForm.getAcademicYearID(), conform.getTerm(),
					conform.getAYClassID());

			int value, value1, value2, value3, value4, value5 = 0;
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
					totalMarksScaled, internalMarksValue = 0;
			String grade = "";

			if (NewSubjectList.contains(",")) {

				String subArr[] = NewSubjectList.split(",");

				ScholasticGradeList = new ArrayList<ConfigurationForm>();

				for (int j = 0; j < subArr.length; j++) {

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
							Integer.parseInt(subArr[j]), conform.getAYClassID());

					if (gradeCheck == 1) {

						grade = "";

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), conform.getRegistrationID(),
								conform.getAYClassID(), NewExaminationList.get("Term End"));

						if (termEdCheck) {
							grade = "ex";
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;

						}

						form.setTermEndMarks("-");
						form.setFinalTotalMarks("-");
						form.setSubject(subjectName);
						form.setUnitTestMarks("-");
						/*
						 * form.setNotebookMarks("-"); form.setSubjectEnrichmentMarks("-");
						 * form.setPortfolioMarks("-"); form.setMultipleAssessmentMarks("-");
						 */
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					} else {

						grade = "";

						boolean seAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Subject Enrichment")) {

								System.out.println(entry.getValue());
								boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!sbjEnrch1Check) {
									seAbsentCheck = false;
								}

							}
						}

						boolean nbAbsentCheck = false;
						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Notebook")) {

								System.out.println(entry.getValue());
								boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!noteBkCheck) {
									nbAbsentCheck = false;
								}

							}
						}

						boolean portAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Portfolio")) {

								System.out.println(entry.getValue());
								boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!portCheck) {
									portAbsentCheck = false;
								}

							}
						}

						boolean multiAbsentCheck = false;
						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Multiple Assessment")) {

								System.out.println(entry.getValue());
								boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!multiCheck) {
									multiAbsentCheck = false;
								}

							}
						}

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Unit Test"));

						value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
								"Subject Enrichment", conform.getRegistrationID(), conform.getAYClassID(),
								conform.getTerm());

						value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						value4 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Portfolio",
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						value5 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
								"Multiple Assessment", conform.getRegistrationID(), conform.getAYClassID(),
								conform.getTerm());

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getAYClassID());

						scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getAYClassID());

						scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Multiple Assessment",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						internalMarksValue = (value1 + value2 + value3 + value4 + value5);

						totalMarksScaled = (value + internalMarksValue);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

						System.out.println(
								"toatlScaleTo: " + totalMarksScaled + "--" + toatlScaleTo + "--" + subjectName);

						toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

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

						/*
						 * To show only grade forEnhanced Marathi Subject in report card and show '-'
						 * for all exams
						 */
						if (subjectName.equals("Enhanced Marathi")) {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							form.setTermEndMarks("-");
							form.setUnitTestMarks("-");
							form.setFinalTotalMarks("-");

							if (termEdCheck) {
								grade = "ex";
							}

							if (unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
									|| multiAbsentCheck) {
								grade = "ex";
							}

						} else {

							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */

							if (termEdCheck) {
								grade = "ex";
								form.setTermEndMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setTermEndMarks("" + value);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
									|| multiAbsentCheck) {

								form.setUnitTestMarks("ex");
								grade = "ex";
								form.setFinalTotalMarks("ex");
							} else {
								form.setUnitTestMarks("" + internalMarksValue);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (termEdCheck || unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
									|| multiAbsentCheck) {

								form.setFinalTotalMarks("ex");
							} else {
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
						}

						if (grade.equals("E")) {
							GradeValue += ',' + subjectName;
						}

						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);
					}
				}
			}
		}

		if (GradeValue.startsWith(",")) {
			GradeValue = GradeValue.substring(1);
		}
		request.setAttribute("GradeValue", GradeValue);

		return SUCCESS;
	}

	public String configureReportReview() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());
		// retriving Academic Year ID value

		String GradeValue = "";

		String StandardName = daoInf2.getStandardNameByStandardID(conform.getStandardID());

		String Stage = daoInf2.getStandardStageByStandardID(conform.getStandardID());

		if (conform.getTerm().equals("Term II")) {
			String StdDivName = daoInf2.retrieveStdDivName(conform.getStudentID());

			request.setAttribute("StdDivName", StdDivName);
		} else {
			request.setAttribute("StdDivName", "");
		}

		if (Stage.equals("Primary")) {

			request.setAttribute("StudentsFound", "Found");

			studentDetailsList = daoInf2.retrieveStudentDetailsList(conform.getStudentID(), conform.getTerm(),
					conform.getAYClassID());

			request.setAttribute("statusValue", conform.getResult());

			// System.out.println("conform.getAYClassID() : " + conform.getAYClassID());
			coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			request.setAttribute("PersonalityDevelopmentGradeListNew", PersonalityDevelopmentGradeListNew);

			String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(conform.getTerm(),
					userForm.getAcademicYearID(), conform.getStandardID());

			if (attendanceIDList == null || attendanceIDList == "") {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else if (attendanceIDList.isEmpty()) {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else {

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(conform.getStudentID(), attendanceIDList,
						StandardName);
			}

			String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(conform.getStandardID());

			NewExaminationList = daoInf2.retrieveExaminationList(userForm.getAcademicYearID(), conform.getTerm(),
					conform.getAYClassID());

			int value, value1, value2, value3, value4, finalOutOfMarks = 0;
			String retestMarks, retestMarksNew = "";
			double value5, value6, finalSEAMarks = 0D;
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
			int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
					totalMarksScaled = 0;

			String grade = "";

			if (NewSubjectList.contains(",")) {

				String subArr[] = NewSubjectList.split(",");

				ScholasticGradeList = new ArrayList<ConfigurationForm>();

				for (int j = 0; j < subArr.length; j++) {

					// grade = "";

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
							Integer.parseInt(subArr[j]), conform.getAYClassID());

					// System.out.println("grade check.." + gradeCheck);

					if (gradeCheck == 1) {

						grade = "";

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), conform.getRegistrationID(),
								conform.getAYClassID(), NewExaminationList.get("Term End"));

						// System.out.println("comp grade..." + grade);

						if (termEdCheck) {
							grade = "ex";
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;

						}

						form.setTermEndMarks("-");
						form.setFinalTotalMarks("-");
						form.setUnitTestMarks("-");
						form.setSubjectEnrichmentMarks("-");
						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					} else {

						grade = "";

						boolean seAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Subject Enrichment")
									|| entry.getKey().startsWith("Notebook")) {

								System.out.println(entry.getValue());
								boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!noteBkCheck) {
									seAbsentCheck = false;
								}

							}
						}

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Unit Test"));

						value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						retestMarks = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						retestMarksNew = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						// System.out.println("value6 :" + value6);
						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getAYClassID());

						scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getAYClassID());

						scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), conform.getAYClassID());
						// System.out.println("scaleTo4 :" + scaleTo4);

						totalMarksScaled = (value + value1 + value2);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

						toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

						/*
						 * if (toatlScaleTo == 50) {
						 * 
						 * toatlScaleTo1 = (totalMarksScaled * 2); } else {
						 */

						// toatlScaleTo1 = totalMarksScaled;
						/* } */

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

						/*
						 * To show only grade forEnhanced Marathi Subject in report card and show '-'
						 * for all exams
						 */
						if (subjectName.equals("Enhanced Marathi")) {

							form.setTermEndMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setFinalTotalMarks("-");

							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							if (retestMarks == null || retestMarks == "") {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							} else if (retestMarks.isEmpty()) {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							}

							if (retestMarksNew == null || retestMarksNew == "") {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							} else if (retestMarksNew.isEmpty()) {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							}

							if (seAbsentCheck) {
								grade = "ex";
							}

						} else {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
							 * else set grade as calculated
							 */
							if (retestMarks == null || retestMarks == "") {

								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

							} else if (retestMarks.isEmpty()) {
								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							} else {
								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value + "(" + retestMarks + ")");
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							if (retestMarksNew == null || retestMarksNew == "") {
								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							} else if (retestMarksNew.isEmpty()) {
								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							} else {
								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1 + "(" + retestMarksNew + ")");
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							if (seAbsentCheck) {
								grade = "ex";
								form.setSubjectEnrichmentMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setSubjectEnrichmentMarks("" + value2);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (termEdCheck || unitTestCheck || seAbsentCheck) {

								form.setFinalTotalMarks("ex");
							} else {
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
						}

						if (grade.equals("E")) {
							// System.out.println("GradeValue: "+grade);
							GradeValue += ',' + subjectName;
						}

						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					}
				}
			}

		} else {

			request.setAttribute("StudentsFound", "NotFound");
			studentDetailsList = daoInf2.retrieveStudentDetailsList(conform.getStudentID(), conform.getTerm(),
					conform.getAYClassID());

			request.setAttribute("statusValue", conform.getResult());

			coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());
			
			request.setAttribute("extraCurricularCheck", ExtraCurricularGradeList.size());

			PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			request.setAttribute("PersonalityDevelopmentGradeListNew", PersonalityDevelopmentGradeListNew);

			String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(conform.getTerm(),
					userForm.getAcademicYearID(), conform.getStandardID());

			if (attendanceIDList == null || attendanceIDList == "") {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else if (attendanceIDList.isEmpty()) {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else {

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(conform.getStudentID(), attendanceIDList,
						StandardName);
			}

			// System.out.println("AttendanceList:" + AttendanceList);

			String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(conform.getStandardID());

			NewExaminationList = daoInf2.retrieveExaminationList(userForm.getAcademicYearID(), conform.getTerm(),
					conform.getAYClassID());

			int value, value1, value2, value3, value4, value5 = 0;
			String retestMarks, retestMarksNew = "";
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
					totalMarksScaled = 0;
			String grade = "";

			if (NewSubjectList.contains(",")) {

				String subArr[] = NewSubjectList.split(",");

				ScholasticGradeList = new ArrayList<ConfigurationForm>();

				for (int j = 0; j < subArr.length; j++) {

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
							Integer.parseInt(subArr[j]), conform.getAYClassID());

					if (gradeCheck == 1) {

						grade = "";

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), conform.getRegistrationID(),
								conform.getAYClassID(), NewExaminationList.get("Term End"));

						if (termEdCheck) {
							grade = "ex";
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;

						}

						form.setTermEndMarks("-");
						form.setFinalTotalMarks("-");
						form.setSubject(subjectName);
						form.setUnitTestMarks("-");
						form.setNotebookMarks("-");
						form.setSubjectEnrichmentMarks("-");
						form.setPortfolioMarks("-");
						form.setMultipleAssessmentMarks("-");
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					} else {

						grade = "";

						boolean seAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Subject Enrichment")) {

								System.out.println(entry.getValue());
								boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!sbjEnrch1Check) {
									seAbsentCheck = false;
								}
							}
						}

						boolean nbAbsentCheck = false;
						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Notebook")) {

								System.out.println(entry.getValue());
								boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!noteBkCheck) {
									nbAbsentCheck = false;
								}
							}
						}

						boolean portAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Portfolio")) {

								System.out.println(entry.getValue());
								boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!portCheck) {
									portAbsentCheck = false;
								}
							}
						}

						boolean multiAbsentCheck = false;
						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Multiple Assessment")) {

								System.out.println(entry.getValue());
								boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!multiCheck) {
									multiAbsentCheck = false;
								}
							}
						}

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Unit Test"));

						value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						retestMarks = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						retestMarksNew = daoInf2.retrieveScholasticRetestMarks(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
								"Subject Enrichment", conform.getRegistrationID(), conform.getAYClassID(),
								conform.getTerm());

						value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						value4 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Portfolio",
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						value5 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
								"Multiple Assessment", conform.getRegistrationID(), conform.getAYClassID(),
								conform.getTerm());

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getAYClassID());

						scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getAYClassID());

						scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());
						scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Multiple Assessment",
								conform.getAYClassID(), userForm.getAcademicYearID(), conform.getTerm());

						totalMarksScaled = (value + value1 + value2 + value3 + value4 + value5);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

						toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

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

						/*
						 * To show only grade forEnhanced Marathi Subject in report card and show '-'
						 * for all exams
						 */
						if (subjectName.equals("Enhanced Marathi")) {

							form.setTermEndMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setNotebookMarks("-");
							form.setPortfolioMarks("-");
							form.setMultipleAssessmentMarks("-");
							form.setFinalTotalMarks("-");

							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							if (retestMarks == null || retestMarks == "") {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							} else if (retestMarks.isEmpty()) {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							}

							if (retestMarksNew == null || retestMarksNew == "") {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							} else if (retestMarksNew.isEmpty()) {
								if (termEdCheck) {
									grade = "ex";
								}

								if (unitTestCheck) {
									grade = "ex";
								}
							}

							if (seAbsentCheck) {
								grade = "ex";
							}

							if (nbAbsentCheck) {
								grade = "ex";
							}

							if (portAbsentCheck) {
								grade = "ex";
							}

							if (multiAbsentCheck) {
								grade = "ex";
							}

						} else {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */

							if (retestMarks == null || retestMarks == "") {

								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							} else if (retestMarks.isEmpty()) {

								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}

							} else {

								if (termEdCheck) {
									grade = "ex";
									form.setTermEndMarks("ex");
									form.setFinalTotalMarks("ex");
								} else {
									form.setTermEndMarks("" + value + "(" + retestMarks + ")");
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							if (retestMarksNew == null || retestMarksNew == "") {

								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							} else if (retestMarksNew.isEmpty()) {
								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1);
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							} else {

								if (unitTestCheck) {
									form.setUnitTestMarks("ex");
									grade = "ex";
									form.setFinalTotalMarks("ex");
								} else {
									form.setUnitTestMarks("" + value1 + "(" + retestMarksNew + ")");
									form.setFinalTotalMarks("" + totalMarksScaled);
								}
							}

							if (seAbsentCheck) {
								grade = "ex";
								form.setSubjectEnrichmentMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setSubjectEnrichmentMarks("" + value2);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (nbAbsentCheck) {
								grade = "ex";
								form.setNotebookMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setNotebookMarks("" + value3);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (portAbsentCheck) {
								grade = "ex";
								form.setPortfolioMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setPortfolioMarks("" + value4);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (multiAbsentCheck) {
								grade = "ex";
								form.setMultipleAssessmentMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setMultipleAssessmentMarks("" + value5);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (termEdCheck || unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
									|| multiAbsentCheck) {

								form.setFinalTotalMarks("ex");
							} else {
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;
						}

						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);
					}
				}
			}
		}

		if (GradeValue.startsWith(",")) {
			GradeValue = GradeValue.substring(1);
		}
		request.setAttribute("GradeValue", GradeValue);

		return SUCCESS;
	}

	public String renderReportCardHistory() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandardList(conform.getAcademicYearID());

		InActiveAcademicYearList = daoInf1.getInActiveAcademicYearList();

		DivisionList = daoInf1.getDivision(conform.getStandardID());

		if (userForm.getRole().equals("administrator")) {

			request.setAttribute("classTeacherCheck", "Yes");

		} else {
			addActionError(
					"You are not assigned as a administrator, only administrator have access to this history report page.");
		}

		return SUCCESS;
	}

	/**
	 * 
	 */
	public void retrieveStandardListForacademicYear() throws Exception {
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

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

			values = daoInf.retrieveStandardListForAcademicYear(conform.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Standard List based AcademicYearID for AcademicYear");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String loadClassStudentHistoryReport() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		request.setAttribute("classTeacherCheck", "Yes");

		StandardList = daoInf1.getStandardList(conform.getAcademicYearID());

		InActiveAcademicYearList = daoInf1.getInActiveAcademicYearList();

		DivisionList = daoInf1.getDivision(conform.getStandardID());

		studentsList = daoInf2.retrieveClassStudentHistoryListForClassTeacher(conform.getStandardID(),
				conform.getDivisionID(), conform.getAcademicYearID(), conform.getTerm());

		request.setAttribute("studentsList", studentsList);

		if (studentsList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentsList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		}
	}

	public String configureHistoryReport() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());
		// retriving Academic Year ID value

		String GradeValue = "";

		String StandardName = daoInf2.getStandardNameByStandardID(conform.getStandardID());

		String Stage = daoInf2.getStandardStageByStandardID(conform.getStandardID());

		if (conform.getTerm().equals("Term II")) {
			String StdDivName = daoInf2.retrieveStdDivName(conform.getStudentID());

			request.setAttribute("StdDivName", StdDivName);
		} else {
			request.setAttribute("StdDivName", "");
		}

		request.setAttribute("AcademicYearID", conform.getAcademicYearID());

		if (Stage.equals("Primary")) {

			request.setAttribute("StudentsFound", "Found");

			studentDetailsList = daoInf2.retrieveInActiveStudentDetailsList(conform.getStudentID(), conform.getTerm(),
					conform.getAYClassID());

			request.setAttribute("statusValue", conform.getResult());

			// System.out.println("conform.getAYClassID() : " + conform.getAYClassID());
			coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			request.setAttribute("PersonalityDevelopmentGradeListNew", PersonalityDevelopmentGradeListNew);

			String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(conform.getTerm(),
					conform.getAcademicYearID(), conform.getStandardID());

			// System.out.println("attendanceIDList" + attendanceIDList);

			if (attendanceIDList == null || attendanceIDList == "") {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else if (attendanceIDList.isEmpty()) {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else {

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(conform.getStudentID(), attendanceIDList,
						StandardName);
			}

			// AttendanceList = retrieveAttendanceDetailsOfStudent(conform.getTerm(),
			// AcademicYearID,conform.getStudentID(),attendanceIDList);

			// System.out.println("AttendanceList:" + AttendanceList);

			String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(conform.getStandardID());

			NewExaminationList = daoInf2.retrieveExaminationList(conform.getAcademicYearID(), conform.getTerm(),
					conform.getAYClassID());

			/* double = 0D; */

			int value, value1, value2, value3, value4, finalOutOfMarks = 0;
			double value5, value6, finalSEAMarks = 0D;
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5 = 0;
			int toatlScaleTo, toatlScaleTo1, outOfMarks, outOfMarks1, outOfMarks2, finalSEAmarksvalue,
					totalMarksScaled = 0;

			String grade = "";

			if (NewSubjectList.contains(",")) {

				String subArr[] = NewSubjectList.split(",");

				// System.out.println("NewSubjectList : " + NewSubjectList);

				ScholasticGradeList = new ArrayList<ConfigurationForm>();

				for (int j = 0; j < subArr.length; j++) {

					// grade = "";

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
							Integer.parseInt(subArr[j]), conform.getAYClassID());

					// System.out.println("grade check.." + gradeCheck);

					if (gradeCheck == 1) {

						grade = "";

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), conform.getRegistrationID(),
								conform.getAYClassID(), NewExaminationList.get("Term End"));

						// System.out.println("comp grade..." + grade);

						if (termEdCheck) {
							grade = "ex";
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;

						}

						form.setTermEndMarks("-");
						form.setFinalTotalMarks("-");
						form.setUnitTestMarks("-");
						form.setSubjectEnrichmentMarks("-");
						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					} else {

						grade = "";

						boolean seAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Subject Enrichment")
									|| entry.getKey().startsWith("Notebook")) {

								System.out.println(entry.getValue());
								boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!noteBkCheck) {
									seAbsentCheck = false;
								}

							}
						}

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Unit Test"));

						value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						value2 = daoInf2.retrieveScholasticGradeList1(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						// System.out.println("value6 :" + value6);
						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getAYClassID());

						scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getAYClassID());

						scaleTo4 = daoInf2.retrieveScaleTo1(Integer.parseInt(subArr[j]), conform.getAYClassID());
						// System.out.println("scaleTo4 :" + scaleTo4);

						totalMarksScaled = (value + value1 + value2);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo4);

						/*
						 * if (toatlScaleTo == 50) {
						 * 
						 * toatlScaleTo1 = (totalMarksScaled * 2); } else {
						 */

						// toatlScaleTo1 = totalMarksScaled;
						/* } */

						toatlScaleTo1 = (totalMarksScaled * 50) / toatlScaleTo;

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

						/*
						 * To show only grade forEnhanced Marathi Subject in report card and show '-'
						 * for all exams
						 */
						if (subjectName.equals("Enhanced Marathi")) {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							form.setTermEndMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setFinalTotalMarks("-");

							if (termEdCheck) {
								grade = "ex";
							}

							if (unitTestCheck) {
								grade = "ex";
							}

							if (seAbsentCheck) {
								grade = "ex";
							}

						} else {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to ex
							 * else set grade as calculated
							 */
							if (termEdCheck) {
								grade = "ex";
								form.setTermEndMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setTermEndMarks("" + value);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (unitTestCheck) {
								form.setUnitTestMarks("ex");
								grade = "ex";
								form.setFinalTotalMarks("ex");
							} else {
								form.setUnitTestMarks("" + value1);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
							if (seAbsentCheck) {
								grade = "ex";
								form.setSubjectEnrichmentMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setSubjectEnrichmentMarks("" + value2);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (termEdCheck || unitTestCheck || seAbsentCheck) {

								form.setFinalTotalMarks("ex");
							} else {
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

						}

						if (grade.equals("E")) {
							// System.out.println("GradeValue: "+grade);
							GradeValue += ',' + subjectName;

						}

						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					}
				}
			}

		} else {

			request.setAttribute("StudentsFound", "NotFound");

			studentDetailsList = daoInf2.retrieveInActiveStudentDetailsList(conform.getStudentID(), conform.getTerm(),
					conform.getAYClassID());

			request.setAttribute("statusValue", conform.getResult());

			coScholasticGradeList = daoInf2.retrievecoScholasticGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());

			ExtraCurricularGradeList = daoInf2.retrieveExtraCurricularGradeList(conform.getRegistrationID(),
					conform.getTerm(), conform.getAYClassID());
			
			request.setAttribute("extraCurricularCheck", ExtraCurricularGradeList.size());

			PersonalityDevelopmentGradeList = daoInf2.retrievePersonalityDevelopmentGradeList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			PersonalityDevelopmentGradeListNew = daoInf2.retrievePersonalityDevelopmentAbsentList(
					conform.getRegistrationID(), conform.getTerm(), conform.getAYClassID());

			request.setAttribute("PersonalityDevelopmentGradeListNew", PersonalityDevelopmentGradeListNew);

			String attendanceIDList = daoInf2.retrieveAttendanceIDforStudent(conform.getTerm(),
					conform.getAcademicYearID(), conform.getStandardID());

			// System.out.println("attendanceIDList" + attendanceIDList);

			if (attendanceIDList == null || attendanceIDList == "") {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else if (attendanceIDList.isEmpty()) {

				AttendanceList = new ArrayList<ConfigurationForm>();

			} else {

				AttendanceList = daoInf2.retrieveAttendanceListforStudent(conform.getStudentID(), attendanceIDList,
						StandardName);
			}

			// System.out.println("AttendanceList:" + AttendanceList);

			String NewSubjectList = daoInf2.retrieveSubjectListForStandardByStandardID(conform.getStandardID());

			NewExaminationList = daoInf2.retrieveExaminationList(conform.getAcademicYearID(), conform.getTerm(),
					conform.getAYClassID());

			int value, value1, value2, value3, value4, value5 = 0;
			int scaleTo, scaleTo1, scaleTo2, scaleTo3, scaleTo4, scaleTo5, toatlScaleTo, toatlScaleTo1,
					totalMarksScaled = 0;
			String grade = "";

			if (NewSubjectList.contains(",")) {

				String subArr[] = NewSubjectList.split(",");

				ScholasticGradeList = new ArrayList<ConfigurationForm>();

				for (int j = 0; j < subArr.length; j++) {

					ConfigurationForm form = new ConfigurationForm();

					int gradeCheck = daoInf2.retrieveGradeBasedValue(NewExaminationList.get("Term End"),
							Integer.parseInt(subArr[j]), conform.getAYClassID());

					if (gradeCheck == 1) {

						grade = "";

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						grade = daoInf2.retrieveGradeValue(Integer.parseInt(subArr[j]), conform.getRegistrationID(),
								conform.getAYClassID(), NewExaminationList.get("Term End"));

						if (termEdCheck) {
							grade = "ex";
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;

						}

						form.setTermEndMarks("-");
						form.setFinalTotalMarks("-");
						form.setSubject(subjectName);
						form.setUnitTestMarks("-");
						form.setNotebookMarks("-");
						form.setSubjectEnrichmentMarks("-");
						form.setPortfolioMarks("-");
						form.setMultipleAssessmentMarks("-");
						form.setGrade(grade);

						ScholasticGradeList.add(form);

					} else {

						grade = "";

						boolean seAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Subject Enrichment")) {

								System.out.println(entry.getValue());
								boolean sbjEnrch1Check = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!sbjEnrch1Check) {
									seAbsentCheck = false;
								}
							}
						}

						boolean nbAbsentCheck = false;
						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Notebook")) {

								System.out.println(entry.getValue());
								boolean noteBkCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!noteBkCheck) {
									nbAbsentCheck = false;
								}
							}
						}

						boolean portAbsentCheck = false;

						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Portfolio")) {

								System.out.println(entry.getValue());
								boolean portCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!portCheck) {
									portAbsentCheck = false;
								}
							}
						}

						boolean multiAbsentCheck = false;
						for (Entry<String, Integer> entry : NewExaminationList.entrySet()) {
							if (entry.getKey().startsWith("Multiple Assessment")) {

								System.out.println(entry.getValue());
								boolean multiCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
										conform.getRegistrationID(), conform.getAYClassID(), entry.getValue());

								if (!multiCheck) {
									multiAbsentCheck = false;
								}
							}
						}

						boolean termEdCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Term End"));

						boolean unitTestCheck = daoInf2.verifyAbsent(Integer.parseInt(subArr[j]),
								conform.getRegistrationID(), conform.getAYClassID(),
								NewExaminationList.get("Unit Test"));

						value = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getRegistrationID(),
								conform.getAYClassID());

						value1 = daoInf2.retrieveScholasticGradeList(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getRegistrationID(),
								conform.getAYClassID());

						value2 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
								"Subject Enrichment", conform.getRegistrationID(), conform.getAYClassID(),
								conform.getTerm());

						value3 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Notebook",
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						value4 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]), "Portfolio",
								conform.getRegistrationID(), conform.getAYClassID(), conform.getTerm());

						value5 = daoInf2.retrieveScholasticGradeListNew(Integer.parseInt(subArr[j]),
								"Multiple Assessment", conform.getRegistrationID(), conform.getAYClassID(),
								conform.getTerm());

						String subjectName = daoInf2.retrieveSubject(Integer.parseInt(subArr[j]));

						scaleTo = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Term End"), conform.getAYClassID());

						scaleTo1 = daoInf2.retrieveScaleTo(Integer.parseInt(subArr[j]),
								NewExaminationList.get("Unit Test"), conform.getAYClassID());

						scaleTo2 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Subject Enrichment",
								conform.getAYClassID(), conform.getAcademicYearID(), conform.getTerm());

						scaleTo3 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Notebook",
								conform.getAYClassID(), conform.getAcademicYearID(), conform.getTerm());

						scaleTo4 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Portfolio",
								conform.getAYClassID(), conform.getAcademicYearID(), conform.getTerm());

						scaleTo5 = daoInf2.retrieveScaleToNew(Integer.parseInt(subArr[j]), "Multiple Assessment",
								conform.getAYClassID(), conform.getAcademicYearID(), conform.getTerm());

						totalMarksScaled = (value + value1 + value2 + value3 + value4 + value5);

						toatlScaleTo = (scaleTo + scaleTo1 + scaleTo2 + scaleTo3 + scaleTo4 + scaleTo5);

						System.out.println("toatlScaleTo: " + "-" + toatlScaleTo + "-" + scaleTo + "-" + scaleTo1 + "-"
								+ scaleTo2 + "-" + scaleTo3 + "-" + scaleTo4 + "-" + scaleTo5);
						toatlScaleTo1 = (totalMarksScaled * 100) / toatlScaleTo;

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

						/*
						 * To show only grade forEnhanced Marathi Subject in report card and show '-'
						 * for all exams
						 */
						if (subjectName.equals("Enhanced Marathi")) {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							form.setTermEndMarks("-");
							form.setUnitTestMarks("-");
							form.setSubjectEnrichmentMarks("-");
							form.setNotebookMarks("-");
							form.setPortfolioMarks("-");
							form.setMultipleAssessmentMarks("-");
							form.setFinalTotalMarks("-");

							if (termEdCheck) {
								grade = "ex";
							}

							if (unitTestCheck) {
								grade = "ex";
							}

							if (seAbsentCheck) {
								grade = "ex";
							}

							if (nbAbsentCheck) {
								grade = "ex";
							}

							if (portAbsentCheck) {
								grade = "ex";
							}

							if (multiAbsentCheck) {
								grade = "ex";
							}

						} else {
							/*
							 * Verifying whether absentFlag is true for any of exam, if so set grade to Ex
							 * else set grade as calculated
							 */
							if (termEdCheck) {
								grade = "ex";
								form.setTermEndMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setTermEndMarks("" + value);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (unitTestCheck) {
								form.setUnitTestMarks("ex");
								grade = "ex";
								form.setFinalTotalMarks("ex");
							} else {
								form.setUnitTestMarks("" + value1);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
							if (seAbsentCheck) {
								grade = "ex";
								form.setSubjectEnrichmentMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setSubjectEnrichmentMarks("" + value2);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
							if (nbAbsentCheck) {
								grade = "ex";
								form.setNotebookMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setNotebookMarks("" + value3);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (portAbsentCheck) {
								grade = "ex";
								form.setPortfolioMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setPortfolioMarks("" + value4);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (multiAbsentCheck) {
								grade = "ex";
								form.setMultipleAssessmentMarks("ex");
								form.setFinalTotalMarks("ex");
							} else {
								form.setMultipleAssessmentMarks("" + value5);
								form.setFinalTotalMarks("" + totalMarksScaled);
							}

							if (termEdCheck || unitTestCheck || seAbsentCheck || nbAbsentCheck || portAbsentCheck
									|| multiAbsentCheck) {
								form.setFinalTotalMarks("ex");
							} else {
								form.setFinalTotalMarks("" + totalMarksScaled);
							}
						}

						if (grade.equals("E")) {

							GradeValue += ',' + subjectName;
						}

						form.setSubject(subjectName);
						form.setGrade(grade);

						ScholasticGradeList.add(form);
					}
				}
			}
		}

		if (GradeValue.startsWith(",")) {
			GradeValue = GradeValue.substring(1);
		}
		request.setAttribute("GradeValue", GradeValue);

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exceptin
	 */
	public String studentReportPDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String pdfFIleName = conform.getStudentName().replaceAll(" ", "_") + "_" + conform.getStudentID()
				+ "_report.pdf";

		message = convertToPDFUtil.convertOPDPDF(conform.getStudentID(), conform.getRegistrationID(),
				conform.getStandardID(), conform.getTerm(), conform.getAYClassID(), conform.getResult(),
				userForm.getOrganizationID(), userForm.getUserID(), userForm.getAcademicYearID(), realPath,
				pdfFIleName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + pdfFIleName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfFIleName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, pdfFIleName)).getObjectContent();

			System.out.println("s3ObjectInputStream.........: " + s3ObjectInputStream + "--" + pdfFIleName);

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exceptin
	 */
	public String emailStudentsReportPDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String AcademicYear = daoInf1.retrieveAcademicYearName(userForm.getOrganizationID());

		String organizationName = daoInf1.retrieveOrganizationName(userForm.getOrganizationID());

		String StandardName = daoInf1.retrieveStandardName(userForm.getUserID(), userForm.getAcademicYearID());

		String DivisionName = daoInf1.retrieveDivisionName(userForm.getUserID(), userForm.getAcademicYearID());

		String fromEmailID = daoInf1.retrieveOrganizationEmail(userForm.getOrganizationID());

		String fromEmailPassword = daoInf1.retrieveOrganizationEmailPassword(userForm.getOrganizationID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String pdfFIleName = conform.getStudentName().replaceAll(" ", "_") + "_" + conform.getStudentID()
				+ "_report.pdf";

		message = convertToPDFUtil.convertOPDPDF(conform.getStudentID(), conform.getRegistrationID(),
				conform.getStandardID(), conform.getTerm(), conform.getAYClassID(), conform.getResult(),
				userForm.getOrganizationID(), userForm.getUserID(), userForm.getAcademicYearID(), realPath,
				pdfFIleName);

		if (message.equals("success")) {

			boolean MotherEmailCheck = util.verifyCommunicationCheck(conform.getStudentID(), "Mother");

			boolean FatherEmailCheck = util.verifyCommunicationCheck(conform.getStudentID(), "Father");

			System.out.println("MotherEmailCheck: " + MotherEmailCheck);

			System.out.println("FatherEmailCheck: " + FatherEmailCheck);

			if (MotherEmailCheck) {

				/*
				 * Retrieving student's email ID, if not null, then sending student's Report
				 * card
				 */
				String toEmailID = daoInf2.retrieveParentsEmailByStudentID(conform.getStudentID(), "Mother");

				/*
				 * Sending report card pdf mail to students mother
				 */
				message = emailUtil.sendReportCardMailToParents(conform.getStudentID(), userForm.getOrganizationID(),
						realPath, pdfFIleName, toEmailID, fromEmailID, fromEmailPassword, conform.getStudentName(),
						StandardName, DivisionName, conform.getTerm(), AcademicYear, organizationName);

				if (message.equals("success")) {
					addActionMessage("Student " + conform.getStudentName()
							+ " Report card pdf sent successfully to mother's Email ID.");
					message = "success";
				} else {
					addActionError("Exception occured while sending Report card pdf Email to mother.");
					message = "error";
				}

			} else if (FatherEmailCheck) {
				/*
				 * Retrieving student's email ID, if not null, then sending student's Report
				 * card
				 */
				String toEmailID = daoInf2.retrieveParentsEmailByStudentID(conform.getStudentID(), "Father");

				/*
				 * Sending report card pdf mail to students father
				 */
				message = emailUtil.sendReportCardMailToParents(conform.getStudentID(), userForm.getOrganizationID(),
						realPath, pdfFIleName, toEmailID, fromEmailID, fromEmailPassword, conform.getStudentName(),
						StandardName, DivisionName, conform.getTerm(), AcademicYear, organizationName);

				if (message.equals("success")) {
					addActionMessage("Student " + conform.getStudentName()
							+ " Report card pdf sent successfully to father's Email ID.");
					message = "success";
				} else {
					addActionError("Exception occured while sending Report card pdf Email to father.");
					message = "error";
				}

			} else {
				addActionError("Email ID not found for this student " + conform.getStudentName()
						+ ". Please add parents Email ID first.");
				message = "error";
			}

			StandardList = daoInf1.getStandard(userForm.getOrganizationID());

			boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
			if (check10th) {

				request.setAttribute("classTeacherCheck", "Yes");
				request.setAttribute("classTeacher10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacherCheck", "Yes");
				request.setAttribute("classTeacher10thCheck", "No");
			}

			boolean check9th10th = daoInf2.verify9th10thClassTeacher(userForm.getUserID(),
					userForm.getAcademicYearID());
			if (check9th10th) {

				request.setAttribute("classTeacher9th10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacher9th10thCheck", "No");
			}

			studentsList = daoInf2.retrieveClassStudentListForClassTeacher(conform.getStandardID(),
					conform.getDivisionID(), userForm.getAcademicYearID(), conform.getTerm());

			request.setAttribute("studentsList", studentsList);

			request.setAttribute("standardID", conform.getStandardID());

			request.setAttribute("divisionID", conform.getDivisionID());

			request.setAttribute("termValue", conform.getTerm());

			request.setAttribute("loadStudentSearch", "Enabled");

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			StandardList = daoInf1.getStandard(userForm.getOrganizationID());

			boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
			if (check10th) {

				request.setAttribute("classTeacherCheck", "Yes");
				request.setAttribute("classTeacher10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacherCheck", "Yes");
				request.setAttribute("classTeacher10thCheck", "No");
			}

			boolean check9th10th = daoInf2.verify9th10thClassTeacher(userForm.getUserID(),
					userForm.getAcademicYearID());
			if (check9th10th) {

				request.setAttribute("classTeacher9th10thCheck", "Yes");
			} else {

				request.setAttribute("classTeacher9th10thCheck", "No");
			}

			studentsList = daoInf2.retrieveClassStudentListForClassTeacher(conform.getStandardID(),
					conform.getDivisionID(), userForm.getAcademicYearID(), conform.getTerm());

			request.setAttribute("studentsList", studentsList);

			request.setAttribute("standardID", conform.getStandardID());

			request.setAttribute("divisionID", conform.getDivisionID());

			request.setAttribute("termValue", conform.getTerm());

			request.setAttribute("loadStudentSearch", "Enabled");

			message = "error";
		}

		return message;
	}

	/**
	 * 
	 * @return
	 * @throws Exceptin
	 */
	public String allStudentsEmailReportPDF() throws Exception {

		excelUtil = new ExcelUtil();

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String fromEmailID = daoInf1.retrieveOrganizationEmail(userForm.getOrganizationID());

		String fromEmailPassword = daoInf1.retrieveOrganizationEmailPassword(userForm.getOrganizationID());

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		String AcademicYear = daoInf1.retrieveAcademicYearName(userForm.getOrganizationID());

		String organizationName = daoInf1.retrieveOrganizationName(userForm.getOrganizationID());

		String StandardName = daoInf1.retrieveStandardName(userForm.getUserID(), userForm.getAcademicYearID());

		String DivisionName = daoInf1.retrieveDivisionName(userForm.getUserID(), userForm.getAcademicYearID());

		boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
		if (check10th) {

			request.setAttribute("classTeacherCheck", "Yes");
			request.setAttribute("classTeacher10thCheck", "Yes");
		} else {

			request.setAttribute("classTeacherCheck", "Yes");
			request.setAttribute("classTeacher10thCheck", "No");
		}

		boolean check9th10th = daoInf2.verify9th10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
		if (check9th10th) {

			request.setAttribute("classTeacher9th10thCheck", "Yes");
		} else {

			request.setAttribute("classTeacher9th10thCheck", "No");
		}

		System.out.println("Details: " + conform.getStandardID() + "-" + conform.getDivisionID() + "-"
				+ userForm.getAcademicYearID() + "-" + conform.getTerm() + "-" + userForm.getUserID());
		studentsList = daoInf2.retrieveClassStudentListForClassTeacher(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID(), conform.getTerm());

		request.setAttribute("studentsList", studentsList);

		request.setAttribute("standardID", conform.getStandardID());

		request.setAttribute("divisionID", conform.getDivisionID());

		request.setAttribute("termValue", conform.getTerm());

		request.setAttribute("loadStudentSearch", "Enabled");

		studentDetailsList = daoInf2.retrieveStudentListForReportCardEmail(conform.getStandardID(),
				conform.getDivisionID());

		int ayCLassID = daoInf2.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID());

		EmailValues = new ArrayList<String>();
		StudentValues = new ArrayList<String>();
		StatusValues = new ArrayList<String>();

		EmailHistoryDetails = new HashMap<String, List<String>>();

		for (ConfigurationForm formval : studentDetailsList) {
			System.out.println("studentID: " + formval.getStudentID() + "userID" + userForm.getUserID());
			StudentValues.add(formval.getStudentName());

			EmailHistoryDetails.put("studentName", StudentValues);

			String pdfFIleName = formval.getStudentName().replaceAll(" ", "_") + "_" + formval.getStudentID()
					+ "_report.pdf";

			message = convertToPDFUtil.convertOPDPDF(formval.getStudentID(), formval.getRegistrationID(),
					conform.getStandardID(), conform.getTerm(), ayCLassID, formval.getResult(),
					userForm.getOrganizationID(), userForm.getUserID(), userForm.getAcademicYearID(), realPath,
					pdfFIleName);

			if (message.equals("success")) {

				boolean MotherEmailCheck = util.verifyCommunicationCheck(formval.getStudentID(), "Mother");

				boolean FatherEmailCheck = util.verifyCommunicationCheck(formval.getStudentID(), "Father");

				if (MotherEmailCheck) {

					/*
					 * Retrieving student's email ID, if not null, then sending student's Report
					 * card
					 */
					String toEmailID = daoInf2.retrieveParentsEmailByStudentID(formval.getStudentID(), "Mother");

					EmailValues.add(toEmailID);

					EmailHistoryDetails.put("emailID", EmailValues);

					/*
					 * Sending report card pdf mail to students mother
					 */
					message = emailUtil.sendReportCardMailToParents(formval.getStudentID(),
							userForm.getOrganizationID(), realPath, pdfFIleName, toEmailID, fromEmailID,
							fromEmailPassword, formval.getStudentName(), StandardName, DivisionName, conform.getTerm(),
							AcademicYear, organizationName);

					if (message.equals("success")) {

						StatusValues.add("Report card pdf sent successfully to mother's Email ID.");

						EmailHistoryDetails.put("status", StatusValues);

						message = "success";
					} else {

						StatusValues.add(message);

						EmailHistoryDetails.put("status", StatusValues);

						message = "error";
					}

				} else if (FatherEmailCheck) {
					/*
					 * Retrieving student's email ID, if not null, then sending student's Report
					 * card
					 */
					String toEmailID = daoInf2.retrieveParentsEmailByStudentID(formval.getStudentID(), "Father");

					EmailValues.add(toEmailID);

					EmailHistoryDetails.put("emailID", EmailValues);

					/*
					 * Sending report card pdf mail to students father
					 */
					message = emailUtil.sendReportCardMailToParents(formval.getStudentID(),
							userForm.getOrganizationID(), realPath, pdfFIleName, toEmailID, fromEmailID,
							fromEmailPassword, formval.getStudentName(), StandardName, DivisionName, conform.getTerm(),
							AcademicYear, organizationName);

					if (message.equals("success")) {

						StatusValues.add("Report card pdf sent successfully to father's Email ID.");

						EmailHistoryDetails.put("status", StatusValues);

						message = "success";
					} else {

						StatusValues.add(message);

						EmailHistoryDetails.put("status", StatusValues);

						message = "error";
					}

				} else {

					EmailValues.add("");

					EmailHistoryDetails.put("emailID", EmailValues);

					StatusValues.add("Email ID not found for this student. Please add parents Email ID first.");

					EmailHistoryDetails.put("status", StatusValues);

					message = "error";
				}

			} else {

				EmailValues.add("");

				EmailHistoryDetails.put("emailID", EmailValues);

				StatusValues.add("Failed to create report PDF. Please check server logs for more details.");

				EmailHistoryDetails.put("status", StatusValues);

				message = "error";
			}
		}

		String excelFileName = "Standard " + StandardName + DivisionName + "_Email_History_Report" + ".xls";

		message = excelUtil.generateStandardWiseStudentsEmailReport(EmailHistoryDetails, StandardName, DivisionName,
				realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage(
					"Email successfully sent to all students parents. Excel sheet of email sent record exported successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			message = "success";
		} else {
			addActionError(
					"Failed to generate email sent history excel sheet. Please check server logs for more details.");
			message = "error";
		}

		return message;
	}

	/**
	 * 
	 * @return
	 * @throws Exceptin
	 */
	public String studentReportReviewPDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String pdfFIleName = conform.getStudentName().replaceAll(" ", "_") + "_" + conform.getStudentID()
				+ "_report_review.pdf";

		message = convertToPDFUtil.convertReportReviewPDF(conform.getStudentID(), conform.getRegistrationID(),
				conform.getStandardID(), conform.getTerm(), conform.getAYClassID(), userForm.getOrganizationID(),
				userForm.getUserID(), userForm.getAcademicYearID(), realPath, pdfFIleName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + pdfFIleName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfFIleName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, pdfFIleName)).getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}

	}

	public String allStudentReportPDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		int ayCLassID = daoInf2.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID());

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

		String pdfFIleName = "Standard_" + conform.getStandard() + "-" + conform.getDivision() + "_report.pdf";

		// System.out.println("..." + conform.getTerm() + " ... " + ayCLassID);

		message = convertToPDFUtil.convertOPDPDFALL(conform.getStandardID(), conform.getDivisionID(), conform.getTerm(),
				ayCLassID, userForm.getOrganizationID(), userForm.getUserID(), userForm.getAcademicYearID(), realPath,
				pdfFIleName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + pdfFIleName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfFIleName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, pdfFIleName)).getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}
	}

	public String studentHistoryReportPDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String pdfFIleName = conform.getStudentName().replaceAll(" ", "_") + "_" + conform.getStudentID()
				+ "_report.pdf";

		message = convertToPDFUtil.convertHistoryReportOPDPDF(conform.getStudentID(), conform.getRegistrationID(),
				conform.getStandardID(), conform.getTerm(), conform.getAYClassID(), userForm.getOrganizationID(),
				conform.getAcademicYearID(), userForm.getUserID(), realPath, pdfFIleName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + pdfFIleName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfFIleName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, pdfFIleName)).getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}

	}

	public String studentsCustomReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String termName = conform.getTerm().replace(" ", "_");

		String[] strArray = conform.getStandardDivName().split("-");

		String excelFileName = termName + "_" + Integer.parseInt(strArray[0]) + "-" + Integer.parseInt(strArray[1])
				+ ".xls";

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String[] standardDivision = conform.getStandardDivision().split("-");

		message = excelUtil.convertCustomReportToExcel(Integer.parseInt(strArray[0]), standardDivision[0],
				Integer.parseInt(strArray[1]), standardDivision[1], conform.getTerm(), userForm.getOrganizationID(),
				userForm.getUserID(), conform.getSubjectID(), userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students customised Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {
			addActionError("Failed to create Excel Sheet. Please check server logs for more details.");

			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Generating Students Excel Sheet",
					form.getUserID());

			return ERROR;
		}

	}

	public String studentsHistoryCustomReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String termName = conform.getTerm().replace(" ", "-");

		String standardName = daoInf2.retrieveStandardNameDivisionName(conform.getStandardID(),
				conform.getDivisionID());

		String[] standardDivision = standardName.split("-");

		String AcademicYearName = daoInf2.retriveInaActiveAcdemicYearByAcdemicYearID(conform.getAcademicYearID());

		String excelFileName = termName + "_" + standardName + "_" + AcademicYearName + ".xls";

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String SubjectNameList = daoInf2.retrieveSubjectListByStandardIDExamType(conform.getStandardID());

		message = excelUtil.convertCustomHistoryReportToExcel(conform.getStandardID(), standardDivision[0],
				conform.getDivisionID(), standardDivision[1], conform.getTerm(), conform.getAcademicYearID(),
				userForm.getOrganizationID(), userForm.getUserID(), SubjectNameList, realPath, excelFileName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students customised Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {
			addActionError("Failed to create Excel Sheet. Please check server logs for more details.");

			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Generating Students Excel Sheet",
					form.getUserID());

			return ERROR;
		}

	}

	public void retrieveStudentAssessmentHistroy() throws Exception {

		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveStudentAssessmentHistory(conform.getStudentID(), conform.getSubjectAssessmentID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving studnet assessment details");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String renderXthSTDStudentsReport() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		boolean check = daoInf2.verifyXthSTDTeacher(userForm.getUserID(), userForm.getAcademicYearID(), "X");

		if (check) {
			System.out.println("user details: " + userForm.getUserID() + "--" + userForm.getAcademicYearID());
			request.setAttribute("classTeacherCheck", "Yes");

			// getting StandardList value
			StandardListByTeacher = configXMLUtil.sortHashMap(
					daoInf2.getStandardDivisionListByTeacher(userForm.getUserID(), userForm.getAcademicYearID(), "X"));

			subjectNameList = new HashMap<Integer, String>();

		} else {

			request.setAttribute("classTeacherCheck", "No");

			addActionError("You are not authorised user to access this page.");

			StandardListByTeacher = new HashMap<String, String>();

			subjectNameList = new HashMap<Integer, String>();
		}

		return SUCCESS;
	}

	public void retrieveXthSTDSubjectListByUserIDandStandardID() throws Exception {
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
			/*
			 * Getting ayClassID by splitting check string by -
			 */

			String[] strArray = conform.getCheck().split("-");

			values = daoInf2.retrieveXthSTDSubjectListForClassTeacherBYUserID(userForm.getUserID(),
					userForm.getAcademicYearID(), Integer.parseInt(strArray[2]));

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Subject List based on Teacher");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String xthSTDStudentsCustomReportExcel() throws Exception {
		System.out.println("xthSTDStudentsCustomReportExcel");
		excelUtil = new ExcelUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String SubjectName = daoInf2.retrieveSubject(conform.getSubjectID());

		String[] strArray = conform.getStandardDivName().split("-");

		String[] standardDivision = conform.getStandardDivision().split("-");

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = SubjectName + "_" + standardDivision[0] + "-" + standardDivision[1] + ".xls";
		System.out.println("test");
		message = excelUtil.convertXthSTDCustomReportToExcel(Integer.parseInt(strArray[0]), standardDivision[0],
				Integer.parseInt(strArray[1]), standardDivision[1], Integer.parseInt(strArray[2]), userForm.getUserID(),
				conform.getSubjectID(), SubjectName, userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating Xth STD students customised Report Excel Sheet",
					form.getUserID());

			return SUCCESS;

		} else {
			addActionError("Failed to create Excel Sheet. Please check server logs for more details.");

			daoInf1.insertAudit(request.getRemoteAddr(),
					"Eception Occurred While Generating Xth STD Students Excel Sheet", form.getUserID());

			return ERROR;
		}
	}

	public String renderloadLeavingStudent() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		return SUCCESS;
	}

	public String loadLeavingStudent() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());

		DivisionList = daoInf1.getDivision(conform.getStandardID());

		studentsList = daoInf2.retrieveStudentListForLeavingCertificate(conform.getStandardID(),
				conform.getDivisionID(), conform.getAcademicYearID());

		request.setAttribute("studentsList", studentsList);

		if (studentsList == null) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else if (studentsList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadStudentSearch", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;
		}
	}

	public String leavingCertificate() throws Exception {

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		boolean check = daoInf2.verifyleavingCertificate("LeavingCertificate", conform.getStudentID());

		if (check) {

			request.setAttribute("LeavingCertificatePrint", "Enabled");
			studentDetailsList = daoInf2.retrieveStudentDetailsFromLeavingCertificate(conform.getStudentID(),
					conform.getRegistrationID(), conform.getAYClassID());

		} else {

			studentDetailsList = daoInf2.retrieveStudentDetailsForLCList(conform.getStudentID(),
					conform.getRegistrationID(), conform.getAYClassID());
		}

		return SUCCESS;
	}

	public String configureLeavingCertificate() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		LoginDAOInf daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = serviceInf.registerLeavingCertificate(conform);

		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Leaving Certificate configured successfully.");

			request.setAttribute("LeavingCertificatePrint", "Enabled");

			studentDetailsList = daoInf.retrieveStudentDetailsFromLeavingCertificate(conform.getStudentID(),
					conform.getRegistrationID(), conform.getAYClassID());

			// Inserting values into Audit table

			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Leaving Certificate", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Leaving Certificate. Please check logs for more details.");

			request.setAttribute("LeavingCertificatePrint", "Disabled");

			studentDetailsList = daoInf.retrieveStudentDetailsFromLeavingCertificate(conform.getStudentID(),
					conform.getRegistrationID(), conform.getAYClassID());
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Leaving Certificate Exception occurred",
					userForm.getUserID());

			return ERROR;
		}
	}

	public String leavingCertificatePDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String StdDivName = daoInf2.retrieveStdDivNameForLeavivingCertificate(conform.getStudentID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String pdfFIleName = conform.getStudentName() + "-" + StdDivName + "_report.pdf";

		// System.out.println("..." + conform.getTerm() + " ... " + ayCLassID);

		message = convertToPDFUtil.convertToLeavingCertificatePDF(conform.getStudentID(), conform.getRegistrationID(),
				conform.getAYClassID(), userForm.getOrganizationID(), realPath, pdfFIleName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + pdfFIleName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfFIleName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, pdfFIleName)).getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}
	}

	public String allStudentLeavingReportPDF() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String StdDivName = daoInf2.retrieveStandardNameDivisionName(conform.getStandardID(), conform.getDivisionID());

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String pdfFIleName = "Standard_" + StdDivName + "_report.pdf";

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());

		DivisionList = daoInf1.getDivision(conform.getStandardID());

		studentsList = daoInf2.retrieveStudentListForLeavingCertificate(conform.getStandardID(),
				conform.getDivisionID(), conform.getAcademicYearID());

		request.setAttribute("studentsList", studentsList);

		int AYClassID = daoInf2.retrieveAYCLassID(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID());

		// System.out.println("..." + conform.getTerm() + " ... " + ayCLassID);

		message = convertToPDFUtil.convertToLeavingCertificatePDFALL(AYClassID, userForm.getOrganizationID(), realPath,
				pdfFIleName);

		if (message.equals("success")) {

			File inputFile = new File(realPath + "/" + pdfFIleName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, pdfFIleName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, pdfFIleName)).getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}
	}

	public String studentsPersonalityReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String[] strArray = conform.getStandardDivName().split("-");

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "Personality_Development_Excel_Sheet" + ".xls";

		message = excelUtil.exportStudentPersonalityReportExcel(userForm.getUserID(), conform.getExaminationID(),
				conform.getAcademicYearID(), Integer.parseInt(strArray[0]), Integer.parseInt(strArray[1]),
				Integer.parseInt(strArray[2]), userForm.getOrganizationID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to export students excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Students Excel Sheet",
					form.getUserID());

			return ERROR;
		}
	}

	public String studentsCoScholasticReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String[] strArray = conform.getStandardDivName().split("-");

		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "CoScholastic_Excel_Sheet" + ".xls";

		message = excelUtil.exportStudentCoScholasticReportExcel(conform.getExaminationID(),
				Integer.parseInt(strArray[0]), Integer.parseInt(strArray[1]), Integer.parseInt(strArray[2]), realPath,
				excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to export students excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Students Excel Sheet",
					form.getUserID());

			return ERROR;

		}
	}

	public String studentsAttendanceReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		LoginDAOInf daoInf1 = new LoginDAOImpl();

		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

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

		String excelFileName = conform.getStandard() + conform.getDivision() + "_Students_Attendance_Excel_Sheet"
				+ ".xls";

		message = excelUtil.exportStudentAttendanceReportExcel(userForm.getUserID(), conform.getTerm(),
				conform.getStandard(), conform.getStandardID(), conform.getDivision(), conform.getDivisionID(),
				userForm.getAcademicYearID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Students excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" + excelFileName);

			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);

			S3ObjectInputStream s3ObjectInputStream = s3
					.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName))
					.getObjectContent();

			conform.setFileInputStream(s3ObjectInputStream);

			conform.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating students Excel Sheet", form.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to export students excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Students Excel Sheet",
					form.getUserID());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureClassStudentsStatus() throws Exception {

		LoginDAOInf daoInf1 = new LoginDAOImpl();
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();

		serviceInf = new kovidRMSServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardList = daoInf1.getStandard(userForm.getOrganizationID());

		boolean check10th = daoInf2.verify10thClassTeacher(userForm.getUserID(), userForm.getAcademicYearID());
		if (check10th) {

			request.setAttribute("classTeacherCheck", "Yes");
			request.setAttribute("classTeacher10thCheck", "Yes");
		} else {

			request.setAttribute("classTeacherCheck", "Yes");
			request.setAttribute("classTeacher10thCheck", "No");
		}

		request.setAttribute("standardID", conform.getStandardID());

		request.setAttribute("divisionID", conform.getDivisionID());

		request.setAttribute("termValue", conform.getTerm());

		request.setAttribute("statusValue", conform.getResult());

		studentsList = daoInf2.retrieveClassStudentListForClassTeacher(conform.getStandardID(), conform.getDivisionID(),
				userForm.getAcademicYearID(), conform.getTerm());

		request.setAttribute("studentsList", studentsList);

		message = serviceInf.addStudentStatusDetails(conform);

		if (message.equals("success")) {

			addActionMessage("Status Details configured successfully.");

			request.setAttribute("loadStudentSearch", "Enabled");

			return SUCCESS;

		} else {

			addActionError("Failed to add status details. Please check server logs for more details.");

			request.setAttribute("loadStudentSearch", "Enabled");

			return ERROR;

		}
	}

	/* setters of form */

	public List<ConfigurationForm> getStudentAssessmentList() {
		return studentAssessmentList;
	}

	public HashMap<Integer, String> getSubjectNameList() {
		return subjectNameList;
	}

	public void setSubjectNameList(HashMap<Integer, String> subjectNameList) {
		this.subjectNameList = subjectNameList;
	}

	public void setStudentAssessmentList(List<ConfigurationForm> studentAssessmentList) {
		this.studentAssessmentList = studentAssessmentList;
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

	public ConfigurationForm getConform() {
		return conform;
	}

	public void setConform(ConfigurationForm conform) {
		this.conform = conform;
	}

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

	public ConfigurationForm getModel() {
		// TODO Auto-generated method stub
		return conform;
	}

	public HashMap<Integer, String> getSubjectList() {
		return SubjectList;
	}

	public HashMap<Integer, String> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(HashMap<Integer, String> organizationList) {
		this.organizationList = organizationList;
	}

	public HashMap<Integer, String> getStandardList() {
		return StandardList;
	}

	public void setStandardList(HashMap<Integer, String> standardList) {
		StandardList = standardList;
	}

	public HashMap<Integer, String> getAppuserList() {
		return AppuserList;
	}

	public void setAppuserList(HashMap<Integer, String> appuserList) {
		AppuserList = appuserList;
	}

	public List<ConfigurationForm> getExaminatrionList() {
		return examinatrionList;
	}

	public void setExaminatrionList(List<ConfigurationForm> examinatrionList) {
		this.examinatrionList = examinatrionList;
	}

	public void setSubjectList(HashMap<Integer, String> subjectList) {
		SubjectList = subjectList;
	}

	public List<ConfigurationForm> getAcademicYearClassList() {
		return AcademicYearClassList;
	}

	public void setAcademicYearClassList(List<ConfigurationForm> academicYearClassList) {
		AcademicYearClassList = academicYearClassList;
	}

	public HashMap<Integer, String> getExamList() {
		return ExamList;
	}

	public void setExamList(HashMap<Integer, String> examList) {
		ExamList = examList;
	}

	public List<StudentForm> getStudentList() {
		return StudentList;
	}

	public void setStudentList(List<StudentForm> studentList) {
		StudentList = studentList;
	}

	public HashMap<String, String> getStandardListByTeacher() {
		return StandardListByTeacher;
	}

	public void setStandardListByTeacher(HashMap<String, String> standardListByTeacher) {
		StandardListByTeacher = standardListByTeacher;
	}

	public List<StudentForm> getStudentListByStandardAndDivision() {
		return StudentListByStandardAndDivision;
	}

	public void setStudentListByStandardAndDivision(List<StudentForm> studentListByStandardAndDivision) {
		StudentListByStandardAndDivision = studentListByStandardAndDivision;
	}

	public List<ConfigurationForm> getSubjectAssessmentList() {
		return subjectAssessmentList;
	}

	public void setSubjectAssessmentList(List<ConfigurationForm> subjectAssessmentList) {
		this.subjectAssessmentList = subjectAssessmentList;
	}

	public HashMap<Integer, String> getSubjectListByExamType() {
		return SubjectListByExamType;
	}

	public void setSubjectListByExamType(HashMap<Integer, String> subjectListByExamType) {
		SubjectListByExamType = subjectListByExamType;
	}

	public List<ConfigurationForm> getStudentsList() {
		return studentsList;
	}

	public void setStudentsList(List<ConfigurationForm> studentsList) {
		this.studentsList = studentsList;
	}

	public List<ConfigurationForm> getStudentDetailsList() {
		return studentDetailsList;
	}

	public void setStudentDetailsList(List<ConfigurationForm> studentDetailsList) {
		this.studentDetailsList = studentDetailsList;
	}

	public List<ConfigurationForm> getCoScholasticGradeList() {
		return coScholasticGradeList;
	}

	public void setCoScholasticGradeList(List<ConfigurationForm> coScholasticGradeList) {
		this.coScholasticGradeList = coScholasticGradeList;
	}

	public List<ConfigurationForm> getExtraCurricularGradeList() {
		return ExtraCurricularGradeList;
	}

	public void setExtraCurricularGradeList(List<ConfigurationForm> extraCurricularGradeList) {
		ExtraCurricularGradeList = extraCurricularGradeList;
	}

	public List<ConfigurationForm> getScholasticGradeList() {
		return ScholasticGradeList;
	}

	public void setScholasticGradeList(List<ConfigurationForm> scholasticGradeList) {
		ScholasticGradeList = scholasticGradeList;
	}

	public List<ConfigurationForm> getPersonalityDevelopmentGradeList() {
		return PersonalityDevelopmentGradeList;
	}

	public void setPersonalityDevelopmentGradeList(List<ConfigurationForm> personalityDevelopmentGradeList) {
		PersonalityDevelopmentGradeList = personalityDevelopmentGradeList;
	}

	public HashMap<String, Integer> getNewExaminationList() {
		return NewExaminationList;
	}

	public void setNewExaminationList(HashMap<String, Integer> newExaminationList) {
		NewExaminationList = newExaminationList;
	}

	public List<ConfigurationForm> getAttendanceList() {
		return AttendanceList;
	}

	public void setAttendanceList(List<ConfigurationForm> attendanceList) {
		AttendanceList = attendanceList;
	}

	public LinkedHashMap<Integer, String> getStudentCustomList() {
		return studentCustomList;
	}

	public void setStudentCustomList(LinkedHashMap<Integer, String> studentCustomList) {
		this.studentCustomList = studentCustomList;
	}

	public List<ConfigurationForm> getStudentsCustomReportList() {
		return studentsCustomReportList;
	}

	public void setStudentsCustomReportList(List<ConfigurationForm> studentsCustomReportList) {
		this.studentsCustomReportList = studentsCustomReportList;
	}

	public HashMap<Integer, String> getSubjectListForPersonalityDevelopment() {
		return SubjectListForPersonalityDevelopment;
	}

	public void setSubjectListForPersonalityDevelopment(HashMap<Integer, String> subjectListForPersonalityDevelopment) {
		SubjectListForPersonalityDevelopment = subjectListForPersonalityDevelopment;
	}

	public HashMap<Integer, String> getSubjectListForExtracurricular() {
		return SubjectListForExtracurricular;
	}

	public void setSubjectListForExtracurricular(HashMap<Integer, String> subjectListForExtracurricular) {
		SubjectListForExtracurricular = subjectListForExtracurricular;
	}

	public HashMap<Integer, String> getStandardWiseSubjectList() {
		return StandardWiseSubjectList;
	}

	public void setStandardWiseSubjectList(HashMap<Integer, String> standardWiseSubjectList) {
		StandardWiseSubjectList = standardWiseSubjectList;
	}

	public HashMap<Integer, String> getSubjectListForExtraCurricularActivity() {
		return SubjectListForExtraCurricularActivity;
	}

	public HashMap<String, String> getStandardListForPersonalityDevelopment() {
		return StandardListForPersonalityDevelopment;
	}

	public void setStandardListForPersonalityDevelopment(
			HashMap<String, String> standardListForPersonalityDevelopment) {
		StandardListForPersonalityDevelopment = standardListForPersonalityDevelopment;
	}

	public void setSubjectListForExtraCurricularActivity(
			HashMap<Integer, String> subjectListForExtraCurricularActivity) {
		SubjectListForExtraCurricularActivity = subjectListForExtraCurricularActivity;
	}

	public HashMap<String, String> getStandardListForExtraCurricular() {
		return StandardListForExtraCurricular;
	}

	public void setStandardListForExtraCurricular(HashMap<String, String> standardListForExtraCurricular) {
		StandardListForExtraCurricular = standardListForExtraCurricular;
	}

	public HashMap<Integer, String> getAcademicYearNameList() {
		return AcademicYearNameList;
	}

	public void setAcademicYearNameList(HashMap<Integer, String> academicYearNameList) {
		AcademicYearNameList = academicYearNameList;
	}

	public HashMap<Integer, String> getSubjectListByCoScholastic() {
		return SubjectListByCoScholastic;
	}

	public void setSubjectListByCoScholastic(HashMap<Integer, String> subjectListByCoScholastic) {
		SubjectListByCoScholastic = subjectListByCoScholastic;
	}

	public HashMap<String, String> getStandardListByCoScholastic() {
		return StandardListByCoScholastic;
	}

	public void setStandardListByCoScholastic(HashMap<String, String> standardListByCoScholastic) {
		StandardListByCoScholastic = standardListByCoScholastic;
	}

	public HashMap<Integer, String> getInActiveAcademicYearList() {
		return InActiveAcademicYearList;
	}

	public void setInActiveAcademicYearList(HashMap<Integer, String> inActiveAcademicYearList) {
		InActiveAcademicYearList = inActiveAcademicYearList;
	}

	public HashMap<Integer, String> getAcademicYearNameListNew() {
		return AcademicYearNameListNew;
	}

	public void setAcademicYearNameListNew(HashMap<Integer, String> academicYearNameListNew) {
		AcademicYearNameListNew = academicYearNameListNew;
	}

	public List<String> getSubjectEnrichmentList() {
		return SubjectEnrichmentList;
	}

	public void setSubjectEnrichmentList(List<String> subjectEnrichmentList) {
		SubjectEnrichmentList = subjectEnrichmentList;
	}

	public List<String> getNotebookList() {
		return NotebookList;
	}

	public void setNotebookList(List<String> notebookList) {
		NotebookList = notebookList;
	}

	/**
	 * @return the personalityDevelopmentGradeListNew
	 */
	public List<ConfigurationForm> getPersonalityDevelopmentGradeListNew() {
		return PersonalityDevelopmentGradeListNew;
	}

	/**
	 * @param personalityDevelopmentGradeListNew the
	 *                                           personalityDevelopmentGradeListNew
	 *                                           to set
	 */
	public void setPersonalityDevelopmentGradeListNew(List<ConfigurationForm> personalityDevelopmentGradeListNew) {
		PersonalityDevelopmentGradeListNew = personalityDevelopmentGradeListNew;
	}

	/**
	 * @return the portfolioList
	 */
	public List<String> getPortfolioList() {
		return PortfolioList;
	}

	/**
	 * @param portfolioList the portfolioList to set
	 */
	public void setPortfolioList(List<String> portfolioList) {
		PortfolioList = portfolioList;
	}

	/**
	 * @return the multipleAssessmentList
	 */
	public List<String> getMultipleAssessmentList() {
		return MultipleAssessmentList;
	}

	/**
	 * @param multipleAssessmentList the multipleAssessmentList to set
	 */
	public void setMultipleAssessmentList(List<String> multipleAssessmentList) {
		MultipleAssessmentList = multipleAssessmentList;
	}

	/**
	 * @return the emailHistoryDetails
	 */
	public HashMap<String, List<String>> getEmailHistoryDetails() {
		return EmailHistoryDetails;
	}

	/**
	 * @param emailHistoryDetails the emailHistoryDetails to set
	 */
	public void setEmailHistoryDetails(HashMap<String, List<String>> emailHistoryDetails) {
		EmailHistoryDetails = emailHistoryDetails;
	}

	/**
	 * @return the emailValues
	 */
	public List<String> getEmailValues() {
		return EmailValues;
	}

	/**
	 * @param emailValues the emailValues to set
	 */
	public void setEmailValues(List<String> emailValues) {
		EmailValues = emailValues;
	}

	/**
	 * @return the studentValues
	 */
	public List<String> getStudentValues() {
		return StudentValues;
	}

	/**
	 * @param studentValues the studentValues to set
	 */
	public void setStudentValues(List<String> studentValues) {
		StudentValues = studentValues;
	}

	/**
	 * @return the statusValues
	 */
	public List<String> getStatusValues() {
		return StatusValues;
	}

	/**
	 * @param statusValues the statusValues to set
	 */
	public void setStatusValues(List<String> statusValues) {
		StatusValues = statusValues;
	}

	public List<ConfigurationForm> getNewAttendanceList() {
		return NewAttendanceList;
	}

	public void setNewAttendanceList(List<ConfigurationForm> newAttendanceList) {
		NewAttendanceList = newAttendanceList;
	}

	/**
	 * @return the libraryList
	 */
	public HashMap<Integer, String> getLibraryList() {
		return LibraryList;
	}

	/**
	 * @param libraryList the libraryList to set
	 */
	public void setLibraryList(HashMap<Integer, String> libraryList) {
		LibraryList = libraryList;
	}

}