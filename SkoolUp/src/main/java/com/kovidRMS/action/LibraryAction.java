package com.kovidRMS.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

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
import com.kovidRMS.daoImpl.*;
import com.kovidRMS.daoInf.*;
import com.kovidRMS.form.*;
import com.kovidRMS.service.*;
import com.kovidRMS.util.AWSS3Connect;
import com.kovidRMS.util.ConfigXMLUtil;
import com.kovidRMS.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LibraryAction extends ActionSupport implements ModelDriven<LibraryForm>, SessionAware, ServletResponseAware {

	String message = null;
	LibraryForm form = new LibraryForm();
	
	ExcelUtil excelUtil = null;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response;
	
	private Map<String, Object> sessionAttribute = null;
	
	LibraryDAOInf daoInf = null;
	kovidRMSServiceInf serviceInf = null;
	LoginDAOInf daoInf1 = new LoginDAOImpl();
	
	ConfigXMLUtil xmlUtil = new ConfigXMLUtil();
	
	List<LibraryForm> StaffRulesEditList;
	List<LibraryForm> PrimaryRulesEditList;
	List<LibraryForm> SecondaryRulesEditList;
	HashMap<String, String> GenreList = null;
	HashMap<String, String> SectionList = null;
	HashMap<String, String> BookTypeList = null;
	HashMap<Integer, String> VendorList = null;
	HashMap<String, String> StandardListNew = null;
	HashMap<Integer, String> StandardDivisionList = null;
	List<LibraryForm> searchBookTypeList = null;
	List<LibraryForm> BookTypeEditList;
	List<LibraryForm> searchVendorList = null;
	List<LibraryForm> VendorsEditList = null;	
	List<LibraryForm> searchGenreList = null;
	List<LibraryForm> GenreEditList;
	List<LibraryForm> searchSectionList = null;
	List<LibraryForm> SectionEditList;
	List<LibraryForm> CupboardList = null;
	List<LibraryForm> ShelfList = null;
	
	HashMap<Integer, String> CupboardDetailsList = null;
	HashMap<Integer, String> ShelvesDetailsList = null;
	HashMap<Integer, String> AcademicYearNameList = null;
	List<LibraryForm> searchBooksList = null;
	List<LibraryForm> signedUpBooksList = null;
	List<LibraryForm> BooksDetailsList = null;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderConfigureRules() throws Exception {
		
		daoInf = new LibraryDAOImpl();
		
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		GenreList = daoInf.retrieveGenreList();
		
		request.setAttribute("GenreList", GenreList);
		
		boolean check = daoInf.verifyRules(userForm.getLibraryID());
		System.out.println("check value: "+check+ "--"+userForm.getLibraryID());
		if (check) {
		
			StaffRulesEditList = daoInf.retrieveRulesListByLibraryID("Staff Rules");
			
			PrimaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Primary Classes");
			request.setAttribute("PrimaryRulesEditList", PrimaryRulesEditList);
			
			SecondaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Secondary Classes");
			request.setAttribute("SecondaryRulesEditList", SecondaryRulesEditList);
			
			request.setAttribute("Rules", "Edit");

		} else {

			request.setAttribute("Rules", "Add");

		}
		
		return SUCCESS;
	}
	
	public String configureRules() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		daoInf = new LibraryDAOImpl();
		daoInf1 = new LoginDAOImpl();
		
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = serviceInf.registerRules(form, userForm.getLibraryID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Rules configured successfully.");

			StaffRulesEditList = daoInf.retrieveRulesListByLibraryID("Staff Rules");
			PrimaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Primary Classes");
			SecondaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Secondary Classes");
			
			request.setAttribute("Rules", "Edit");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Class", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Rules. Please check logs for more details.");

			StaffRulesEditList = daoInf.retrieveRulesListByLibraryID("Staff Rules");
			PrimaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Primary Classes");
			SecondaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Secondary Classes");
			
			request.setAttribute("Rules", "Edit");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Rules Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	public String configureEditRules() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		daoInf = new LibraryDAOImpl();
		daoInf1 = new LoginDAOImpl();
	
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = serviceInf.editRulesFor(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Rules updated successfully.");

			StaffRulesEditList = daoInf.retrieveRulesListByLibraryID("Staff Rules");
			PrimaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Primary Classes");
			SecondaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Secondary Classes");
			
			request.setAttribute("Rules", "Edit");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Class", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while updating Rules. Please check logs for more details.");

			StaffRulesEditList = daoInf.retrieveRulesListByLibraryID("Staff Rules");
			PrimaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Primary Classes");
			SecondaryRulesEditList = daoInf.retrieveRulesListByLibraryID("Secondary Classes");
			
			request.setAttribute("Rules", "Edit");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Rules Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	public String searchBookType() throws Exception {

		daoInf = new LibraryDAOImpl();

		searchBookTypeList = daoInf.searchBookTypeList(form.getSearchBookType());

		if (searchBookTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Book Type found for : " + form.getSearchBookType());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllBookTypes() throws Exception {
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Retrieving existing Organization list from table PVFrequency
		 */
		searchBookTypeList = daoInf.retrieveExistingBookTypeList();

		if (searchBookTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Book Type found. Please add new Book Type.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditBookType() throws Exception {
		daoInf = new LibraryDAOImpl();

		String searchBookType = form.getSearchBookType();

		BookTypeEditList = daoInf.retrieveBookTypeListByID(form.getBookTypeID(), form.getSearchBookType());

		if (searchBookType == null || searchBookType == "") {

			searchBookTypeList = daoInf.retrieveExistingBookTypeList();

		} else {

			searchBookTypeList = daoInf.searchBookTypeList(searchBookType);
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
	public String addBookType() throws Exception {
		daoInf = new LibraryDAOImpl();
		daoInf1 = new LoginDAOImpl();
		
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = daoInf.insertBookType(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Book Type configured successfully.");

			searchBookTypeList = daoInf.retrieveExistingBookTypeList();

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Book Type", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Book Type. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchBookTypeList = daoInf.retrieveExistingBookTypeList();

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Book Type Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editBookType() throws Exception {

		daoInf = new LibraryDAOImpl();
		daoInf1 = new LoginDAOImpl();
		
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		String searchBookType = form.getSearchBookType();

		message = daoInf.updateBookType(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Book Type udpated successfully.");

			BookTypeEditList = daoInf.retrieveBookTypeListByID(form.getBookTypeID(), searchBookType);

			if (searchBookType == null || searchBookType == "") {

				searchBookTypeList = daoInf.retrieveExistingBookTypeList();

			} else {

				searchBookTypeList = daoInf.searchBookTypeList(searchBookType);

			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Book Type", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated Book Type. Please check logs for more details.");

			BookTypeEditList = daoInf.retrieveBookTypeListByID(form.getBookTypeID(), searchBookType);

			if (searchBookType == null || searchBookType == "") {

				searchBookTypeList = daoInf.retrieveExistingBookTypeList();

			} else {

				searchBookTypeList = daoInf.searchBookTypeList(searchBookType);

			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Book Type Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	public String deleteBookType() throws Exception {
		daoInf = new LibraryDAOImpl();

		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.rejectBookType(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Book Type disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Book Type disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Book Type Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (form.getSearchBookType() == null || form.getSearchBookType() == "") {

				BookTypeEditList = daoInf.retrieveExistingBookTypeList();

				if (BookTypeEditList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Book Type found. Please add new Book Type.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchBookTypeList = daoInf.searchBookTypeList(form.getSearchBookType());

				if (searchBookTypeList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Book Type with name '" + form.getSearchBookType() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating Book Type activity status to Inactive into database.");
			addActionError("Failed to disable Book Type.");
			return ERROR;
		}
	}	
	
	public String searchGenre() throws Exception {

		daoInf = new LibraryDAOImpl();

		searchGenreList = daoInf.searchGenreList(form.getSearchGenre());

		if (searchGenreList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Genre found for : " + form.getSearchGenre());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllGenres() throws Exception {
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Retrieving existing Genre list from table PVGenre
		 */
		searchGenreList = daoInf.retrieveExistingGenreList();

		if (searchGenreList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Genre found. Please add new Genre.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditGenre() throws Exception {
		daoInf = new LibraryDAOImpl();

		String searchGenre = form.getSearchGenre();

		GenreEditList = daoInf.retrieveGenreListByID(form.getGenreID(), form.getSearchGenre());

		if (searchGenre == null || searchGenre == "") {

			searchGenreList = daoInf.retrieveExistingGenreList();

		} else {

			searchGenreList = daoInf.searchGenreList(searchGenre);

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
	public String addGenre() throws Exception {
		daoInf = new LibraryDAOImpl();

		daoInf1 = new LoginDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = daoInf.insertGenre(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Genre configured successfully.");

			searchGenreList = daoInf.retrieveExistingGenreList();

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Genre", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Genre. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchGenreList = daoInf.retrieveExistingGenreList();

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Genre Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editGenre() throws Exception {

		daoInf = new LibraryDAOImpl();

		daoInf1 = new LoginDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		String searchGenre = form.getSearchGenre();
		
		message = daoInf.updateGenre(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Genre udpated successfully.");

			GenreEditList = daoInf.retrieveGenreListByID(form.getGenreID(), searchGenre);

			if (searchGenre == null || searchGenre == "") {

				searchGenreList = daoInf.retrieveExistingGenreList();

			} else {

				searchGenreList = daoInf.searchGenreList(searchGenre);
			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Genre", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated Genre. Please check logs for more details.");

			GenreEditList = daoInf.retrieveGenreListByID(form.getGenreID(), searchGenre);

			if (searchGenre == null || searchGenre == "") {

				searchGenreList = daoInf.retrieveExistingGenreList();

			} else {

				searchGenreList = daoInf.searchGenreList(searchGenre);
			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Genre Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	public String deleteGenre() throws Exception {
		daoInf = new LibraryDAOImpl();

		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.rejectGenre(form);

		if (message.equalsIgnoreCase("success")) {
			
			System.out.println("Genre disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Genre disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Genre Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (form.getSearchGenre() == null || form.getSearchGenre() == "") {

				GenreEditList = daoInf.retrieveExistingGenreList();

				if (GenreEditList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Genre found. Please add new Genre.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchGenreList = daoInf.searchGenreList(form.getSearchGenre());

				if (searchGenreList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Genre with name '" + form.getSearchGenre() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating Genre activity status to Inactive into database.");
			addActionError("Failed to disable Genre.");
			return ERROR;
		}
	}	
	
	
	public String renderConfigureSection() throws Exception {

		daoInf1 = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		// getting StandardList value
		StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
		
		StandardListNew.put("Teachers", "Teachers");
		
		return SUCCESS;
	}
	
	public String searchSection() throws Exception {

		daoInf = new LibraryDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		searchSectionList = daoInf.searchSectionList(form.getSearchSection());

		StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
		
		StandardListNew.put("Teachers", "Teachers");
		
		if (searchSectionList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Section found for : " + form.getSearchSection());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllSections() throws Exception {
		daoInf = new LibraryDAOImpl();
		
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		/*
		 * Retrieving existing Genre list from table PVGenre
		 */
		searchSectionList = daoInf.retrieveExistingSectionList();

		StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
		
		StandardListNew.put("Teachers", "Teachers");
		
		if (searchSectionList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Section found. Please add new Section.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditSection() throws Exception {
		daoInf = new LibraryDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		String searchSection = form.getSearchSection();

		SectionEditList = daoInf.retrieveSectionListByID(form.getSectionID(), form.getSearchSection());

		if (searchSection == null || searchSection == "") {

			searchSectionList = daoInf.retrieveExistingSectionList();

		} else {

			searchSectionList = daoInf.searchSectionList(searchSection);

		}

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
		
		StandardListNew.put("Teachers", "Teachers");
		
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addSection() throws Exception {
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
	
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = daoInf.insertSection(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Section configured successfully.");

			searchSectionList = daoInf.retrieveExistingSectionList();

			StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
			
			StandardListNew.put("Teachers", "Teachers");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Section", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Section. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchSectionList = daoInf.retrieveExistingSectionList();

			StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
			
			StandardListNew.put("Teachers", "Teachers");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Section Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editSection() throws Exception {

		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		String searchSection = form.getSearchSection();
		
		message = daoInf.updateSection(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Genre udpated successfully.");

			SectionEditList = daoInf.retrieveSectionListByID(form.getSectionID(), searchSection);

			if (searchSection == null || searchSection == "") {

				searchSectionList = daoInf.retrieveExistingSectionList();

			} else {

				searchSectionList = daoInf.searchSectionList(searchSection);

			}

			StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
			
			StandardListNew.put("Teachers", "Teachers");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Section", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated Section. Please check logs for more details.");

			SectionEditList = daoInf.retrieveSectionListByID(form.getSectionID(), searchSection);

			if (searchSection == null || searchSection == "") {

				searchSectionList = daoInf.retrieveExistingSectionList();

			} else {

				searchSectionList = daoInf.searchSectionList(searchSection);

			}

			StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
			
			StandardListNew.put("Teachers", "Teachers");
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Section Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			// request.setAttribute("componentEdit", "edit");

			return ERROR;
		}
	}

	public String deleteSection() throws Exception {
		daoInf1 = new LoginDAOImpl();
		
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		StandardListNew = daoInf1.getStandardNameList(userForm.getOrganizationID());
		
		StandardListNew.put("Teachers", "Teachers");
		
		message = daoInf.rejectSection(form);

		if (message.equalsIgnoreCase("success")) {
			
			System.out.println("Section disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Section disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Section Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (form.getSearchSection() == null || form.getSearchSection() == "") {

				SectionEditList = daoInf.retrieveExistingSectionList();

				if (SectionEditList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Section found. Please add new Section.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchSectionList = daoInf.searchSectionList(form.getSearchSection());

				if (searchSectionList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Section with name '" + form.getSearchSection() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}

			}

		} else {
			System.out.println("Error while updating Section activity status to Inactive into database.");
			addActionError("Failed to disable Section.");
			return ERROR;
		}
	}
	
	
	public String searchVendor() throws Exception {

		daoInf = new LibraryDAOImpl();

		searchVendorList = daoInf.searchVendorsList(form.getSearchVendor());

		if (searchVendorList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Vendor found for : " + form.getSearchVendor());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllVendors() throws Exception {
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Retrieving existing Organization list from table PVFrequency
		 */
		searchVendorList = daoInf.retrieveExistingVendorsList();

		if (searchVendorList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No vendor found. Please add new vendors.");

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditVendor() throws Exception {
		daoInf = new LibraryDAOImpl();

		String searchVendor = form.getSearchVendor();

		VendorsEditList = daoInf.retrieveVendorsListByID(form.getVendorID(), form.getSearchVendor());

		if (searchVendor == null || searchVendor == "") {

			searchVendorList = daoInf.retrieveExistingVendorsList();

		} else {

			searchVendorList = daoInf.searchVendorsList(searchVendor);
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
	public String addVendor() throws Exception {
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = daoInf.insertVendor(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Vendor configured successfully.");

			searchVendorList = daoInf.retrieveExistingVendorsList();

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Vendor", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Vendor. Please check logs for more details.");

			/*
			 * Retrieving existing Organization list from table PVFrequency
			 */
			searchVendorList = daoInf.retrieveExistingVendorsList();

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Vendor Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editVendor() throws Exception {

		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		String searchVendor = form.getSearchVendor();

		message = daoInf.updateVendors(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Vendor udpated successfully.");

			VendorsEditList = daoInf.retrieveVendorsListByID(form.getVendorID(), searchVendor);

			if (searchVendor == null || searchVendor == "") {

				searchVendorList = daoInf.retrieveExistingVendorsList();

			} else {

				searchVendorList = daoInf.searchVendorsList(searchVendor);

			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Vendor", userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return SUCCESS;
		} else {
			addActionError("Failed to udpated Vendor. Please check logs for more details.");

			VendorsEditList = daoInf.retrieveVendorsListByID(form.getVendorID(), searchVendor);

			if (searchVendor == null || searchVendor == "") {

				searchVendorList = daoInf.retrieveExistingVendorsList();

			} else {

				searchVendorList = daoInf.searchVendorsList(searchVendor);

			}

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Configure Vendor Exception occurred",
					userForm.getUserID());

			request.setAttribute("componentMsg", "available");

			return ERROR;
		}
	}

	public String deleteVendor() throws Exception {
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf.rejectVendors(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Vendor disabled successfully.Activity Status changed to Inactive");
			addActionMessage("Vendor disabled successfully.");

			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Vendor Disabled", userForm.getUserID());
			/*
			 * Depending upon whether searchUserName is null or not, displaying that
			 * particular div of user list
			 */
			if (form.getSearchVendor() == null || form.getSearchVendor() == "") {

				VendorsEditList = daoInf.retrieveExistingVendorsList();

				if (VendorsEditList.size() > 0) {

					request.setAttribute("userListEnable", "userListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "No Class found. Please add new Class.";

					addActionError(errorMsg);

					return ERROR;
				}

			} else {

				searchVendorList = daoInf.searchVendorsList(form.getSearchVendor());

				if (searchVendorList.size() > 0) {

					request.setAttribute("userListEnable", "userSearchListEnable");

					return SUCCESS;

				} else {

					String errorMsg = "Vendor with name '" + form.getSearchVendor() + "' not found.";

					addActionError(errorMsg);

					return ERROR;
				}
			}

		} else {
			System.out.println("Error while updating VendorsEditList activity status to Inactive into database.");
			addActionError("Failed to disable Vendor.");
			return ERROR;
		}
	}	
	
	
	public String renderManageCupboardShelves() throws Exception {
		
		daoInf = new LibraryDAOImpl();
		
		GenreList = daoInf.retrieveGenreList();
		
		CupboardList = daoInf.retrieveCupboardList();
		
		request.setAttribute("CupboardList", CupboardList);

		ShelfList = daoInf.retrieveShelfList(form.getCupboardID());
		
		request.setAttribute("ShelfList", ShelfList);
		
		if(CupboardList.size()>0) {
		
			request.setAttribute("CupboardListCheck", "Yes");
			
		}else {
			
			request.setAttribute("CupboardListCheck", "No");
		}
		
		if(ShelfList.size()>0) {
			
			request.setAttribute("ShelfListCheck", "Yes");
			
		}else {
			
			request.setAttribute("ShelfListCheck", "No");
		}
		
		return SUCCESS;
	}
	
	
	public String configureCupboard() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
	
		message = serviceInf.configureCupboard(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Cupboard configured successfully.");
			
			GenreList = daoInf.retrieveGenreList();
			
			CupboardList = daoInf.retrieveCupboardList();
			
			request.setAttribute("CupboardList", CupboardList);

			if(CupboardList.size()>0) {
				
				request.setAttribute("CupboardListCheck", "Yes");
				
			}else {
				
				request.setAttribute("CupboardListCheck", "No");
			}
			
			ShelfList = daoInf.retrieveShelfList(form.getCupboardID());
			
			request.setAttribute("ShelfList", ShelfList);
			
			if(ShelfList.size()>0) {
				
				request.setAttribute("ShelfListCheck", "Yes");
				
			}else {
				
				request.setAttribute("ShelfListCheck", "No");
			}
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configured Cupboard", userForm.getUserID());

			return SUCCESS;
		} else {
			
			addActionError("Error while configuring Cupboard. Please check logs for more details.");

			GenreList = daoInf.retrieveGenreList();
			
			CupboardList = daoInf.retrieveCupboardList();
			
			request.setAttribute("CupboardList", CupboardList);

			if(CupboardList.size()>0) {
				
				request.setAttribute("CupboardListCheck", "Yes");
				
			}else {
				
				request.setAttribute("CupboardListCheck", "No");
			}
			
			ShelfList = daoInf.retrieveShelfList(form.getCupboardID());
			
			request.setAttribute("ShelfList", ShelfList);
			
			if(ShelfList.size()>0) {
				
				request.setAttribute("ShelfListCheck", "Yes");
				
			}else {
				
				request.setAttribute("ShelfListCheck", "No");
			}
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configuring Cupboard Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	public String configureShelf() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = serviceInf.configureShelf(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Shelf configured successfully.");
	
			GenreList = daoInf.retrieveGenreList();
			
			CupboardList = daoInf.retrieveCupboardList();
			
			request.setAttribute("CupboardList", CupboardList);
	
			if(CupboardList.size()>0) {
				
				request.setAttribute("CupboardListCheck", "Yes");
				
			}else {
				
				request.setAttribute("CupboardListCheck", "No");
			}
			
			ShelfList = daoInf.retrieveShelfList(form.getCupboardID());
			
			request.setAttribute("ShelfList", ShelfList);
			
			if(ShelfList.size()>0) {
				
				request.setAttribute("ShelfListCheck", "Yes");
				
			}else {
				
				request.setAttribute("ShelfListCheck", "No");
			}
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configured Shelf", userForm.getUserID());
	
			return SUCCESS;
		} else {
			
			addActionError("Error while configuring Shelves. Please check logs for more details.");
	
			GenreList = daoInf.retrieveGenreList();
			
			CupboardList = daoInf.retrieveCupboardList();
			
			request.setAttribute("CupboardList", CupboardList);
	
			if(CupboardList.size()>0) {
				
				request.setAttribute("CupboardListCheck", "Yes");
				
			}else {
				
				request.setAttribute("CupboardListCheck", "No");
			}
			
			ShelfList = daoInf.retrieveShelfList(form.getCupboardID());
			
			request.setAttribute("ShelfList", ShelfList);
			
			if(ShelfList.size()>0) {
				
				request.setAttribute("ShelfListCheck", "Yes");
				
			}else {
				
				request.setAttribute("ShelfListCheck", "No");
			}
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configuring Shelves Exception occurred", userForm.getUserID());
	
			return ERROR;
		}
	}
	
	public void retrieveCupboardName() throws Exception {
		daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveCupboardName(form.getName());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Book details based on Book name ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public void retrieveShelfName() throws Exception {
		daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveShelfName(form.getName(), form.getCupboardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Book details based on Book name ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	
	public void retrieveShelfDetails() throws Exception {
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveShelfDetails(form.getCupboardID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Book details based on Book name ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public void removeShelfRow() throws Exception {
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.removeShelfRow(form.getShelfID());

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
	
	public void removeCupboardRow() throws Exception {
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.removeCupboardRow(form.getCupboardID());

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
	
	public String importBooksHistoryReportExcel() throws Exception {

		excelUtil = new ExcelUtil();

		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
	
		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = excelUtil.importBookHistoryReportExcel(form, realPath, form.getExcelFileNameNew(), form.getAcademicYearID());

		if (message.equalsIgnoreCase("success")) {
			
			addActionMessage("Students excel sheet imported successfully.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Imported students Excel Sheet", userForm.getUserID());

			return SUCCESS;

		} else {
			
			addActionError("Invalid file format. Please upload a valid file");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Importing Students Excel Sheet", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public String renderManageBook() throws Exception {
		
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		GenreList = daoInf.retrieveGenreList();
		BookTypeList = daoInf.retrieveBookTypeList();
		SectionList = daoInf.retrieveSectionList();
		VendorList = daoInf.retrieveVendorsList();
		
		CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
		
		return SUCCESS;
	}
	
	public String renderBookList() throws Exception {
		
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		 
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		
		SectionList = daoInf.retrieveSectionList();
		
		return SUCCESS;
	}
	
	public String addBooks() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		 daoInf1 = new LoginDAOImpl();
		 daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = serviceInf.registerBooks(form, userForm.getAcademicYearID(), userForm.getLibraryID());
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Books configured successfully.");

			GenreList = daoInf.retrieveGenreList();
			BookTypeList = daoInf.retrieveBookTypeList();
			SectionList = daoInf.retrieveSectionList();
			VendorList = daoInf.retrieveVendorsList();
		
			CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Books", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while configuring Books. Please check logs for more details.");

			GenreList = daoInf.retrieveGenreList();
			BookTypeList = daoInf.retrieveBookTypeList();
			SectionList = daoInf.retrieveSectionList();
			VendorList = daoInf.retrieveVendorsList();
			
			CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Configure Books Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchBooks() throws Exception {
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
	
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		
		searchBooksList = daoInf.searchBook(form.getSearchBooksName(),form.getSearchCriteria(), form.getAcademicYearID(), userForm.getLibraryID());
		
		request.setAttribute("searchBooksList", searchBooksList);
		
		/*
		 * Checking whether StaffList is empty or not, if empty give error message saying
		 * Staff with name not found
		 */
		if (searchBooksList.size() > 0) {

			request.setAttribute("BookListEnable", "userSearchListEnable");

			if(form.getSearchCriteria().equals("Sections")) {
				request.setAttribute("SearchCriteria", "Yes");
			}
			
			return SUCCESS;

		} else {

			String errorMsg = "Books with name '" + form.getSearchBooksName() + "' not found.";

			addActionError(errorMsg);

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditBooksList() throws Exception {
		 daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		
		signedUpBooksList = daoInf.retriveExistingBookList(form.getAcademicYearID(), userForm.getLibraryID());

		request.setAttribute("signedUpBooksList", signedUpBooksList);
		
		if (signedUpBooksList.size() > 0) {

			request.setAttribute("BookListEnable", "BookListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No Books found. Please add new Books.";

			addActionError(errorMsg);

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditBook() throws Exception {

		 daoInf1 = new LoginDAOImpl();
		 daoInf = new LibraryDAOImpl();
	
		GenreList = daoInf.retrieveGenreList();
		BookTypeList = daoInf.retrieveBookTypeList();
		SectionList = daoInf.retrieveSectionList();
		VendorList = daoInf.retrieveVendorsList();
	
		CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
		
		signedUpBooksList = daoInf.retreiveBOokDetailByBookID(form.getBookID());
		
		int cupboardID = daoInf.retrievecupboardIDByBookID(form.getBookID()); 
		
		ShelvesDetailsList = daoInf.retrieveShelvesDetailsList(cupboardID);
	
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editBook() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		 daoInf1 = new LoginDAOImpl();
		 daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		message = daoInf1.editBookDetail(form);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Successfully updated Book details.");

			GenreList = daoInf.retrieveGenreList();
			BookTypeList = daoInf.retrieveBookTypeList();
			SectionList = daoInf.retrieveSectionList();
			VendorList = daoInf.retrieveVendorsList();
			
			CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
			
			signedUpBooksList = daoInf.retreiveBOokDetailByBookID(form.getBookID());

			int cupboardID = daoInf.retrievecupboardIDByBookID(form.getBookID()); 
			
			ShelvesDetailsList = daoInf.retrieveShelvesDetailsList(cupboardID);
			
			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Book", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to update Book details. Please check logs for more details.");

			GenreList = daoInf.retrieveGenreList();
			BookTypeList = daoInf.retrieveBookTypeList();
			SectionList = daoInf.retrieveSectionList();
			VendorList = daoInf.retrieveVendorsList();
		
			CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
			
			signedUpBooksList = daoInf.retreiveBOokDetailByBookID(form.getBookID());
			
			int cupboardID = daoInf.retrievecupboardIDByBookID(form.getBookID()); 
			
			ShelvesDetailsList = daoInf.retrieveShelvesDetailsList(cupboardID);
			
			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Edit Book exception occurred.", userForm.getUserID());
			return ERROR;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateBook() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		 daoInf1 = new LoginDAOImpl();
		 daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		
		message = serviceInf.updateBookStatus(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Book status updated successfully.");
			addActionMessage("Book status updated successfully.");
			
			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Book status Updated", userForm.getUserID());
			
			return SUCCESS;
		} else {
			System.out.println("Error while updating Book status into database.");
			addActionError("Failed to update Book status.");
			
			// Inserting values into Audit table for add user
			daoInf1.insertAudit(request.getRemoteAddr(), "Exception Occurs While Updating Book status", userForm.getUserID());
			return ERROR;
		}
	}
	
	
	public void retrieveStatusByBookID() throws Exception {
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveStatusDetailsByBookID(form.getBookID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Book details based on Book name ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public void removeStatusRow() throws Exception {
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * deleting details accordingly.
			 */

			values = daoInf.deleteStatusRow(form.getStatusID());

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
	
	public void retrieveBookName() throws Exception {
		daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveBookDetails(form.getName(), form.getAuthor(), userForm.getLibraryID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Book details based on Book name ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	
	public void retrieveBookNameCount() throws Exception {
		daoInf = new LibraryDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveBookNameCount(form.getName(), form.getAuthor(), userForm.getLibraryID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Book details based on Book name ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public String renderconfigureStudentIssue() throws Exception {

		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		System.out.println("List val: "+userForm.getAcademicYearID());
		// getting StandardList value
		StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());

		return SUCCESS;

	}

	public void retrieveStudentDetailsByClassID() throws Exception {
		
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
System.out.println("Status: "+form.getStatus());
			if(form.getStatus().equals("Report")) {
				values = daoInf.retrieveStudentsBookDetailsByClassID(form.getAyclassID());
			}else {
				values = daoInf.retrieveStudentDetailsByClassID(form.getAyclassID());
			}
			
			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public void retrieveStandardDivisionListByAcademicYearID() throws Exception {
		
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveStandardDivisionListByAcademicYearID(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Standard Division details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public void retrieveClassLevelByClassID() throws Exception {
		
		daoInf = new LibraryDAOImpl();

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
			
			values = daoInf.retrieveClassLevelByClassID(form.getAyclassID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveRulesDetailsByClassLevel() throws Exception {
		
		daoInf = new LibraryDAOImpl();

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
			
			values = daoInf.retrieveRulesDetailsByClassLevel(form.getLevel(), form.getGenre(), userForm.getLibraryID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveStudentsBookIssueCount() throws Exception {
		
		daoInf = new LibraryDAOImpl();
		
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

			values = daoInf.retrieveStudentsBookIssueCount(form.getBookCount(), form.getStudentID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public String issueStudentBooks() throws Exception {
			
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = daoInf.insertIssuedBooks(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Books Issued successfully.");

			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Book Issued ", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while Issuing Books. Please check logs for more details.");
			
			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Issuing Books Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public String returnBook() throws Exception {
		
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
	
		message = daoInf.returnBooks(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Books returned successfully.");

			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Book Returned ", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while returning Books. Please check logs for more details.");
			
			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Exception occurred while Returning Book", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public void retrieveStudentDetailsForBookReturn() throws Exception {
		
		daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveStudentDetailsForBookReturn(form.getStudentIssueID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

		
	public void retrieveBooksByScanValue() throws Exception {
		
		daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveBooksByScanValue(form.getBookScanValue());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}


	public void retrieveIssuedBooks() throws Exception {
		
		daoInf = new LibraryDAOImpl();
		
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

			values = daoInf.retrieveIssuedBooksByStudentID(form.getStudentID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}


	public String renderconfigureStaffIssue() throws Exception {

		daoInf = new LibraryDAOImpl();
		daoInf1 = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		// getting StandardList value
		StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());

		return SUCCESS;
	}
	
	public void retrieveStaffDetailsByRole() throws Exception {
		
		daoInf = new LibraryDAOImpl();

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

			if(form.getStatus().equals("Report")) {
				values = daoInf.retrieveStaffsBookDetailsByRole(form.getRole(), userForm.getOrganizationID());
			}else {
				values = daoInf.retrieveStaffDetailsByRole(form.getRole(), userForm.getOrganizationID());
			}
			

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	public void retrieveStaffsIssuedBooks() throws Exception {
		
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
			
			values = daoInf.retrieveIssuedBooksByStaffID(form.getStaffID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Student details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	
	public void retrieveStaffsBookIssueCount() throws Exception {
		
		daoInf = new LibraryDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveStaffsBookIssueCount(form.getBookCount(), form.getStaffID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Staffs details based on StaffID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String issueStaffBooks() throws Exception {
		
		daoInf1 = new LoginDAOImpl();
		daoInf = new LibraryDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		message = daoInf.insertIssuedBooksForStaff(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Books Issued successfully.");

			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Book Issued ", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while Issuing Books. Please check logs for more details.");
			
			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Issuing Books Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public void retrieveStaffDetailsForBookReturn() throws Exception {
		
		daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveStaffDetailsForBookReturn(form.getStaffIssueID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving Staff details based on classID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	
	public String returnStaffBooks() throws Exception {
		 daoInf1 = new LoginDAOImpl();
		 daoInf = new LibraryDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
	
		message = daoInf.returnStaffBooks(form);
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Books returned successfully.");

			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Book Returned ", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while returning Books. Please check logs for more details.");
			
			// getting StandardList value
			StandardDivisionList = daoInf1.getStandardDivisionList(userForm.getAcademicYearID());
			
			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Exception occurred while Returning Book", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public String renderInventoryReport() throws Exception {
		
		 daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		
		// getting StandardList value
		SectionList = daoInf.retrieveSectionList();

		SectionList.put("all", "All");
		
		return SUCCESS;

	}

	public String exportInventoryReport() throws Exception {

		excelUtil = new ExcelUtil();

		daoInf1 = new LoginDAOImpl();

		 daoInf = new LibraryDAOImpl();
	
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
        
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
        
		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		String excelFileName = "Inventory_Excel_Sheet_" + timeStamp + ".xls";
		
		message = excelUtil.exportInventoryReportExcel(form.getStatus(), form.getSection(), form.getSchoolSection(), form.getAcademicYearID(), userForm.getLibraryID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {
			
			addActionMessage("Inventory Report excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName)).getObjectContent();
			
			form.setFileInputStream(s3ObjectInputStream);
			
			form.setFileName(excelFileName);

			AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
			
			// getting StandardList value
			SectionList = daoInf.retrieveSectionList();

			SectionList.put("All", "All");
			
			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating Inventory Report Excel Sheet", userForm.getUserID());

			return SUCCESS;

		} else {
			
			addActionError("Failed to export Inventory excel report.");

			AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
			
			// getting StandardList value
			SectionList = daoInf.retrieveSectionList();

			SectionList.put("All", "All");
			
			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Inventory Report Excel Sheet", userForm.getUserID());

			return ERROR;
		}
	}
	
	public String printInventoryReport() throws Exception {

		excelUtil = new ExcelUtil();

		daoInf1 = new LoginDAOImpl();

		 daoInf = new LibraryDAOImpl();
	
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
        
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
        
		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		String excelFileName = "Inventory_Excel_Sheet_" + timeStamp + ".xls";
		
		message = excelUtil.exportInventoryReportExcelForPrint(form.getStatus(), form.getSection(), form.getSchoolSection(), form.getAcademicYearID(), userForm.getLibraryID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {
			
			addActionMessage("Inventory Report excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName)).getObjectContent();
			
			form.setFileInputStream(s3ObjectInputStream);
			
			form.setFileName(excelFileName);

			AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
			
			// getting StandardList value
			SectionList = daoInf.retrieveSectionList();

			SectionList.put("All", "All");
			
			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating Inventory Report Excel Sheet", userForm.getUserID());

			return SUCCESS;

		} else {
			
			addActionError("Failed to export Inventory excel report.");

			AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
			
			// getting StandardList value
			SectionList = daoInf.retrieveSectionList();

			SectionList.put("All", "All");
			
			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Inventory Report Excel Sheet", userForm.getUserID());

			return ERROR;
		}
	}
	
	public String renderStaffReport() throws Exception {

		daoInf1 = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		
		return SUCCESS;

	}
	
	public String exportStaffsReport() throws Exception {

		excelUtil = new ExcelUtil();

		daoInf1 = new LoginDAOImpl();

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
        
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
        
		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String excelFileName = "Staffs_Excel_Sheet" + ".xls";
		
		message = excelUtil.exportStaffsReportExcel(form.getRole(), form.getStaffString(), form.getStatus(), form.getAcademicYearID(), userForm.getLibraryID(), realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {
			
			addActionMessage("Inventory Report excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName)).getObjectContent();
			
			form.setFileInputStream(s3ObjectInputStream);
			
			form.setFileName(excelFileName);
			
			AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
			
			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating Inventory Report Excel Sheet", userForm.getUserID());

			return SUCCESS;

		} else {
			
			addActionError("Failed to export Inventory excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Inventory Report Excel Sheet", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public String renderStudentsReport() throws Exception {

		daoInf1 = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
			
		return SUCCESS;

	}
	
	public String exportStudentsReport() throws Exception {

		excelUtil = new ExcelUtil();

		 daoInf1 = new LoginDAOImpl();

		 daoInf = new LibraryDAOImpl();
	
	   /*
		* Getting userID from Session
		*/
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
			
		AcademicYearNameList = daoInf1.getAcademicYearList(userForm.getOrganizationID());
		 
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
        
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(bucketRegion).build();
       
		/*
		 * Generating PDF Patient based report
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String ClassName = daoInf1.retrieveClassNameByClassID(form.getAyclassID());
	
		String excelFileName = ClassName + "_Excel_Sheet" + ".xls";

		message = excelUtil.exportStudentsReportExcel(form.getAyclassID(), form.getStudentString(), form.getStatus(), form.getAcademicYearID(), userForm.getLibraryID(), ClassName, realPath, excelFileName);

		if (message.equalsIgnoreCase("success")) {
			
			addActionMessage("Inventory Report excel sheet exported successfully.");

			File inputFile = new File(realPath + "/" +excelFileName);
			
			// Storing file to S3 RDML INPUT FILE location
			message = awss3Connect.pushFile(inputFile, excelFileName, bucketName, bucketRegion, s3rdmlFilePath);
			
			S3ObjectInputStream s3ObjectInputStream = s3.getObject(new GetObjectRequest(bucketName + "/" + s3rdmlFilePath, excelFileName)).getObjectContent();
			
			form.setFileInputStream(s3ObjectInputStream);
			
			form.setFileName(excelFileName);

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Generating Inventory Report Excel Sheet", userForm.getUserID());

			return SUCCESS;

		} else {
			
			addActionError("Failed to export Inventory excel report.");

			// Inserting into Audit
			daoInf1.insertAudit(request.getRemoteAddr(), "Eception Occurred While Exporting Inventory Report Excel Sheet", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public String renderMoveBook() throws Exception {
		
		 daoInf = new LibraryDAOImpl();
		
		CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
		
		ShelvesDetailsList = daoInf.retrieveShelvesDetailsList(form.getCupboardID());
		
		return SUCCESS;
	}
	
	
	public String loadBooksDetails() throws Exception {

		 daoInf1 = new LoginDAOImpl();

		 daoInf = new LibraryDAOImpl();
		
		CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
		
		ShelvesDetailsList = daoInf.retrieveShelvesDetailsList(form.getCupboardID());

		// getting AcademicYearNameList value
		BooksDetailsList = daoInf.retrieveBooks(form.getShelfID());
			
		if (BooksDetailsList == null) {

			addActionError("No records found.");

			request.setAttribute("loadBooks", "Disabled");

			return ERROR;

		} else if (BooksDetailsList.size() == 0) {

			addActionError("No records found.");

			request.setAttribute("loadBooks", "Disabled");

			return ERROR;

		} else {

			request.setAttribute("loadBooks", "Enabled");

			return SUCCESS;

		}
	}
	
	
	public String moveBooks() throws Exception {

		serviceInf = new kovidRMSServiceImpl();
		
		 daoInf1 = new LoginDAOImpl();

		 daoInf = new LibraryDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		CupboardDetailsList = daoInf.retrieveCupboardDetailsList();
		
		ShelvesDetailsList = daoInf.retrieveShelvesDetailsList(form.getCupboardID());
		
		message = serviceInf.moveBooks(form);
	
		if (message.equalsIgnoreCase("success")) {
			addActionMessage("Books Moved successfully.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Books Moved", userForm.getUserID());

			return SUCCESS;
		} else {
			addActionError("Error while Moving Books. Please check logs for more details.");

			// Inserting values into Audit table
			daoInf1.insertAudit(request.getRemoteAddr(), "Moving Books Exception occurred", userForm.getUserID());

			return ERROR;
		}
	}
	
	
	public void retrieveDelayedStudents() throws Exception {
		
		 daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveDelayedStudents();

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Delayed Students details");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
	
	
	public void retrieveDelayedStaffs() throws Exception {
		
		 daoInf = new LibraryDAOImpl();

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

			values = daoInf.retrieveDelayedStaffs();

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving Delayed Students details");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}
		
	
	public LibraryForm getModel() {
		// TODO Auto-generated method stub
		return form;
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

	public LibraryForm getForm() {
		return form;
	}

	public void setForm(LibraryForm form) {
		this.form = form;
	}
	
	public List<LibraryForm> getStaffRulesEditList() {
		return StaffRulesEditList;
	}

	public void setStaffRulesEditList(List<LibraryForm> staffRulesEditList) {
		StaffRulesEditList = staffRulesEditList;
	}

	public List<LibraryForm> getPrimaryRulesEditList() {
		return PrimaryRulesEditList;
	}

	public void setPrimaryRulesEditList(List<LibraryForm> primaryRulesEditList) {
		PrimaryRulesEditList = primaryRulesEditList;
	}

	public List<LibraryForm> getSecondaryRulesEditList() {
		return SecondaryRulesEditList;
	}

	public void setSecondaryRulesEditList(List<LibraryForm> secondaryRulesEditList) {
		SecondaryRulesEditList = secondaryRulesEditList;
	}

	public HashMap<String, String> getGenreList() {
		return GenreList;
	}

	public void setGenreList(HashMap<String, String> genreList) {
		GenreList = genreList;
	}

	/**
	 * @return the searchBookTypeList
	 */
	public List<LibraryForm> getSearchBookTypeList() {
		return searchBookTypeList;
	}

	/**
	 * @param searchBookTypeList the searchBookTypeList to set
	 */
	public void setSearchBookTypeList(List<LibraryForm> searchBookTypeList) {
		this.searchBookTypeList = searchBookTypeList;
	}

	/**
	 * @return the bookTypeEditList
	 */
	public List<LibraryForm> getBookTypeEditList() {
		return BookTypeEditList;
	}

	/**
	 * @param bookTypeEditList the bookTypeEditList to set
	 */
	public void setBookTypeEditList(List<LibraryForm> bookTypeEditList) {
		BookTypeEditList = bookTypeEditList;
	}

	/**
	 * @return the searchVendorList
	 */
	public List<LibraryForm> getSearchVendorList() {
		return searchVendorList;
	}

	/**
	 * @param searchVendorList the searchVendorList to set
	 */
	public void setSearchVendorList(List<LibraryForm> searchVendorList) {
		this.searchVendorList = searchVendorList;
	}

	/**
	 * @return the vendorsEditList
	 */
	public List<LibraryForm> getVendorsEditList() {
		return VendorsEditList;
	}

	/**
	 * @param vendorsEditList the vendorsEditList to set
	 */
	public void setVendorsEditList(List<LibraryForm> vendorsEditList) {
		VendorsEditList = vendorsEditList;
	}

	/**
	 * @return the searchGenreList
	 */
	public List<LibraryForm> getSearchGenreList() {
		return searchGenreList;
	}

	/**
	 * @param searchGenreList the searchGenreList to set
	 */
	public void setSearchGenreList(List<LibraryForm> searchGenreList) {
		this.searchGenreList = searchGenreList;
	}

	/**
	 * @return the genreEditList
	 */
	public List<LibraryForm> getGenreEditList() {
		return GenreEditList;
	}

	/**
	 * @param genreEditList the genreEditList to set
	 */
	public void setGenreEditList(List<LibraryForm> genreEditList) {
		GenreEditList = genreEditList;
	}

	/**
	 * @return the searchSectionList
	 */
	public List<LibraryForm> getSearchSectionList() {
		return searchSectionList;
	}

	/**
	 * @param searchSectionList the searchSectionList to set
	 */
	public void setSearchSectionList(List<LibraryForm> searchSectionList) {
		this.searchSectionList = searchSectionList;
	}

	/**
	 * @return the sectionEditList
	 */
	public List<LibraryForm> getSectionEditList() {
		return SectionEditList;
	}

	/**
	 * @param sectionEditList the sectionEditList to set
	 */
	public void setSectionEditList(List<LibraryForm> sectionEditList) {
		SectionEditList = sectionEditList;
	}

	/**
	 * @return the sectionList
	 */
	public HashMap<String, String> getSectionList() {
		return SectionList;
	}

	/**
	 * @param sectionList the sectionList to set
	 */
	public void setSectionList(HashMap<String, String> sectionList) {
		SectionList = sectionList;
	}

	/**
	 * @return the bookTypeList
	 */
	public HashMap<String, String> getBookTypeList() {
		return BookTypeList;
	}

	/**
	 * @param bookTypeList the bookTypeList to set
	 */
	public void setBookTypeList(HashMap<String, String> bookTypeList) {
		BookTypeList = bookTypeList;
	}

	/**
	 * @return the vendorList
	 */
	public HashMap<Integer, String> getVendorList() {
		return VendorList;
	}

	/**
	 * @param vendorList the vendorList to set
	 */
	public void setVendorList(HashMap<Integer, String> vendorList) {
		VendorList = vendorList;
	}

	/**
	 * @return the standardListNew
	 */
	public HashMap<String, String> getStandardListNew() {
		return StandardListNew;
	}

	/**
	 * @param standardListNew the standardListNew to set
	 */
	public void setStandardListNew(HashMap<String, String> standardListNew) {
		StandardListNew = standardListNew;
	}

	/**
	 * @return the cupboardList
	 */
	public List<LibraryForm> getCupboardList() {
		return CupboardList;
	}

	/**
	 * @param cupboardList the cupboardList to set
	 */
	public void setCupboardList(List<LibraryForm> cupboardList) {
		CupboardList = cupboardList;
	}

	/**
	 * @return the shelfList
	 */
	public List<LibraryForm> getShelfList() {
		return ShelfList;
	}

	/**
	 * @param shelfList the shelfList to set
	 */
	public void setShelfList(List<LibraryForm> shelfList) {
		ShelfList = shelfList;
	}

	/**
	 * @return the cupboardDetailsList
	 */
	public HashMap<Integer, String> getCupboardDetailsList() {
		return CupboardDetailsList;
	}

	/**
	 * @param cupboardDetailsList the cupboardDetailsList to set
	 */
	public void setCupboardDetailsList(HashMap<Integer, String> cupboardDetailsList) {
		CupboardDetailsList = cupboardDetailsList;
	}

	/**
	 * @return the shelvesDetailsList
	 */
	public HashMap<Integer, String> getShelvesDetailsList() {
		return ShelvesDetailsList;
	}

	/**
	 * @param shelvesDetailsList the shelvesDetailsList to set
	 */
	public void setShelvesDetailsList(HashMap<Integer, String> shelvesDetailsList) {
		ShelvesDetailsList = shelvesDetailsList;
	}

	/**
	 * @return the academicYearNameList
	 */
	public HashMap<Integer, String> getAcademicYearNameList() {
		return AcademicYearNameList;
	}

	/**
	 * @param academicYearNameList the academicYearNameList to set
	 */
	public void setAcademicYearNameList(HashMap<Integer, String> academicYearNameList) {
		AcademicYearNameList = academicYearNameList;
	}

	/**
	 * @return the searchBooksList
	 */
	public List<LibraryForm> getSearchBooksList() {
		return searchBooksList;
	}

	/**
	 * @param searchBooksList the searchBooksList to set
	 */
	public void setSearchBooksList(List<LibraryForm> searchBooksList) {
		this.searchBooksList = searchBooksList;
	}

	/**
	 * @return the signedUpBooksList
	 */
	public List<LibraryForm> getSignedUpBooksList() {
		return signedUpBooksList;
	}

	/**
	 * @param signedUpBooksList the signedUpBooksList to set
	 */
	public void setSignedUpBooksList(List<LibraryForm> signedUpBooksList) {
		this.signedUpBooksList = signedUpBooksList;
	}

	/**
	 * @return the standardDivisionList
	 */
	public HashMap<Integer, String> getStandardDivisionList() {
		return StandardDivisionList;
	}

	/**
	 * @param standardDivisionList the standardDivisionList to set
	 */
	public void setStandardDivisionList(HashMap<Integer, String> standardDivisionList) {
		StandardDivisionList = standardDivisionList;
	}

	/**
	 * @return the booksDetailsList
	 */
	public List<LibraryForm> getBooksDetailsList() {
		return BooksDetailsList;
	}

	/**
	 * @param booksDetailsList the booksDetailsList to set
	 */
	public void setBooksDetailsList(List<LibraryForm> booksDetailsList) {
		BooksDetailsList = booksDetailsList;
	}
	
	
}
