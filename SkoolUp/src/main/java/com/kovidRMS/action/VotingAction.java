package com.kovidRMS.action;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kovidRMS.daoImpl.*;
import com.kovidRMS.daoInf.*;
import com.kovidRMS.form.*;
import com.kovidRMS.service.*;
import com.kovidRMS.util.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class VotingAction extends ActionSupport implements ModelDriven<VotingForm>, SessionAware, ServletResponseAware {

	String message = null;
	VotingForm form = new VotingForm();
	
	private Map<String, Object> sessionAttribute = null;

	List<VotingForm>HeadGirlList = null;
	
	List<VotingForm>HeadBoyList = null;
	
	List<VotingForm>RedList = null;
	
	List<VotingForm>BlueList = null;
	
	List<VotingForm>GreenList = null;
	
	List<VotingForm>YellowList = null;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response;

	VotingDAOInf daoInf = null;
	
	kovidRMSServiceInf serviceInf = null;
	
	public VotingForm getForm() {
		return form;
	}

	public void setForm(VotingForm form) {
		this.form = form;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureVotingDetails() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		
		daoInf = new VotingDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = serviceInf.configureVotingDetails(form);

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Election details configured successfully.");
			addActionMessage("Election details configured successfully.");

			request.setAttribute("loadAcademicYear", "Yes");
			
			String VotingStatus = daoInf.retrieveVotingStatus();
			
			System.out.println("VotingStatusnew1 :"+VotingStatus);
			if(VotingStatus == null || VotingStatus == ""){
				
				VotingStatus = "Done";
				
			}else if(VotingStatus.isEmpty()) {
			
				VotingStatus = "Done";
				
			}
			
			request.setAttribute("VotingStatus", VotingStatus);
			
			HeadGirlList = daoInf.retreiveHeadGirlsList(form.getAcademicYearID());
			request.setAttribute("HeadGirlList", HeadGirlList);
			
			HeadBoyList = daoInf.retreiveHeadBoysList(form.getAcademicYearID());
			request.setAttribute("HeadBoyList", HeadBoyList);
			
			RedList = daoInf.retreiveHouseCaptainRedList(form.getAcademicYearID());
			request.setAttribute("RedList", RedList);
			
			BlueList = daoInf.retreiveHouseCaptainBlueList(form.getAcademicYearID());
			request.setAttribute("BlueList", BlueList);
			
			GreenList = daoInf.retreiveHouseCaptainGreenList(form.getAcademicYearID());
			request.setAttribute("GreenList", GreenList);
			
			YellowList = daoInf.retreiveHouseCaptainYellowList(form.getAcademicYearID());
			request.setAttribute("YellowList", YellowList);
			
			return SUCCESS;

		} else {
			System.out.println("Error while configuring Election details. Please check logs for more details.");
			addActionError("Failed to configure Election details.");

			request.setAttribute("loadAcademicYear", "Yes");
			
			String VotingStatus = daoInf.retrieveVotingStatus();
			
			System.out.println("VotingStatusnew1 :"+VotingStatus);
			if(VotingStatus == null || VotingStatus == ""){
				
				VotingStatus = "Done";
				
			}else if(VotingStatus.isEmpty()) {
			
				VotingStatus = "Done";
				
			}
			
			request.setAttribute("VotingStatus", VotingStatus);
			
			HeadGirlList = daoInf.retreiveHeadGirlsList(form.getAcademicYearID());
			request.setAttribute("HeadGirlList", HeadGirlList);
			
			HeadBoyList = daoInf.retreiveHeadBoysList(form.getAcademicYearID());
			request.setAttribute("HeadBoyList", HeadBoyList);
			
			RedList = daoInf.retreiveHouseCaptainRedList(form.getAcademicYearID());
			request.setAttribute("RedList", RedList);
			
			BlueList = daoInf.retreiveHouseCaptainBlueList(form.getAcademicYearID());
			request.setAttribute("BlueList", BlueList);
			
			GreenList = daoInf.retreiveHouseCaptainGreenList(form.getAcademicYearID());
			request.setAttribute("GreenList", GreenList);
			
			YellowList = daoInf.retreiveHouseCaptainYellowList(form.getAcademicYearID());
			request.setAttribute("YellowList", YellowList);
			
			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateVotingDetails() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new VotingDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		String VotingStatus = daoInf.retrieveVotingStatus();

		request.setAttribute("VotingStatus", VotingStatus);
	
		message = serviceInf.updateVotingDetails(form, form.getAcademicYearID());

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Academic Year updated successfully.");
			addActionMessage("Academic Year updated successfully.");
			
			request.setAttribute("loadAcademicYear", "Yes");
			
			HeadGirlList = daoInf.retreiveHeadGirlsList(form.getAcademicYearID());
			request.setAttribute("HeadGirlList", HeadGirlList);
			
			HeadBoyList = daoInf.retreiveHeadBoysList(form.getAcademicYearID());
			request.setAttribute("HeadBoyList", HeadBoyList);
			
			RedList = daoInf.retreiveHouseCaptainRedList(form.getAcademicYearID());
			request.setAttribute("RedList", RedList);
			
			BlueList = daoInf.retreiveHouseCaptainBlueList(form.getAcademicYearID());
			request.setAttribute("BlueList", BlueList);
			
			GreenList = daoInf.retreiveHouseCaptainGreenList(form.getAcademicYearID());
			request.setAttribute("GreenList", GreenList);
			
			YellowList = daoInf.retreiveHouseCaptainYellowList(form.getAcademicYearID());
			request.setAttribute("YellowList", YellowList);

			return SUCCESS;

		} else {
			System.out.println("Error while updating academic year. Please check logs for more details.");
			addActionError("Failed to update academic year.");

			request.setAttribute("loadAcademicYear", "Yes");

			HeadGirlList = daoInf.retreiveHeadGirlsList(form.getAcademicYearID());
			request.setAttribute("HeadGirlList", HeadGirlList);
			
			HeadBoyList = daoInf.retreiveHeadBoysList(form.getAcademicYearID());
			request.setAttribute("HeadBoyList", HeadBoyList);
			
			RedList = daoInf.retreiveHouseCaptainRedList(form.getAcademicYearID());
			request.setAttribute("RedList", RedList);
			
			BlueList = daoInf.retreiveHouseCaptainBlueList(form.getAcademicYearID());
			request.setAttribute("BlueList", BlueList);
			
			GreenList = daoInf.retreiveHouseCaptainGreenList(form.getAcademicYearID());
			request.setAttribute("GreenList", GreenList);
			
			YellowList = daoInf.retreiveHouseCaptainYellowList(form.getAcademicYearID());
			request.setAttribute("YellowList", YellowList);

			return ERROR;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderElection() throws Exception {
		
		LoginDAOInf loginDao = new LoginDAOImpl();
		
		daoInf = new VotingDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");
		
		int AcademicYearID =  loginDao.retrieveAcademicYearID(userForm.getOrganizationID());
		
		request.setAttribute("loadAcademicYear", "Yes");
		
		String VotingStatus = daoInf.retrieveVotingStatus();
		
		System.out.println("VotingStatusnew1 :"+VotingStatus);
		if(VotingStatus == null || VotingStatus == ""){
			
			VotingStatus = "Done";
			
		}else if(VotingStatus.isEmpty()) {
		
			VotingStatus = "Done";
		}
		
		request.setAttribute("VotingStatus", VotingStatus);
		
		HeadGirlList = daoInf.retreiveHeadGirlsList(AcademicYearID);
		request.setAttribute("HeadGirlList", HeadGirlList);
		
		HeadBoyList = daoInf.retreiveHeadBoysList(AcademicYearID);
		request.setAttribute("HeadBoyList", HeadBoyList);
		
		RedList = daoInf.retreiveHouseCaptainRedList(AcademicYearID);
		request.setAttribute("RedList", RedList);
		
		BlueList = daoInf.retreiveHouseCaptainBlueList(AcademicYearID);
		request.setAttribute("BlueList", BlueList);
		
		GreenList = daoInf.retreiveHouseCaptainGreenList(AcademicYearID);
		request.setAttribute("GreenList", GreenList);
		
		YellowList = daoInf.retreiveHouseCaptainYellowList(AcademicYearID);
		request.setAttribute("YellowList", YellowList);
		
		return SUCCESS;

	}

	public void retrieveHeadGirlDetails() throws Exception {
		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveHeadGirlDetails(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving HeadGirl details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveHeadBoyDetails() throws Exception {
		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveHeadBoyDetails(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Exception occured while retrieving HeadBoy details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveHouseCaptainRedDetails() throws Exception {
		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveHouseCaptainRedDetails(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving House Captain Red details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveHouseCaptainBlueDetails() throws Exception {
		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveHouseCaptainBlueDetails(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving House Captain Blue details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveHouseCaptainGreenDetails() throws Exception {
		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveHouseCaptainGreenDetails(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving House Captain Green details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public void retrieveHouseCaptainYellowDetails() throws Exception {
		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = daoInf.retrieveHouseCaptainYellowDetails(form.getAcademicYearID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg",
					"Exception occured while retrieving House Captain Yellow details based on AcademicYearID ");
			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}
	}

	public String renderVoting() throws Exception {

		daoInf = new VotingDAOImpl();

		String VotingStatus = daoInf.retrieveVotingStatus();

		request.setAttribute("VotingStatus", VotingStatus);
	
		
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configureRedHouseVoting() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new VotingDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = daoInf.configureRedHouseVoting(form.getHeadBoyID(), form.getHeadGirlID(), form.getHouseCaptainRedID());

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Voting configured successfully.");
			addActionMessage("Voting configured successfully.");

			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			return SUCCESS;

		} else {
			System.out.println("Error while configuring Voting. Please check logs for more details.");
			addActionError("Failed to configure Voting.");

			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			return ERROR;
		}
	}
	
	
	public String configureBlueHouseVoting() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new VotingDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");
		//System.out.println(" BLUE ID:"+form.getHouseCaptainBlueID());
		message = daoInf.configureBlueHouseVoting(form.getHeadBoyID(), form.getHeadGirlID(), form.getHouseCaptainBlueID());

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Voting configured successfully.");
			addActionMessage("Voting configured successfully.");

			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			request.setAttribute("BlueHouseVoting", "enable");
			
			return SUCCESS;

		} else {
			System.out.println("Error while configuring Voting. Please check logs for more details.");
			addActionError("Failed to configure Voting.");
			
			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			request.setAttribute("BlueHouseVoting", "enable");
			
			return ERROR;
		}
	}
	
	
	public String configureGreenHouseVoting() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new VotingDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = daoInf.configureGreenHouseVoting(form.getHeadBoyID(), form.getHeadGirlID(), form.getHouseCaptainGreenID());

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Voting configured successfully.");
			addActionMessage("Voting configured successfully.");

			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			request.setAttribute("GreenHouseVoting", "enable");
			
			return SUCCESS;

		} else {
			System.out.println("Error while configuring Voting. Please check logs for more details.");
			addActionError("Failed to configure Voting.");

			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			request.setAttribute("GreenHouseVoting", "enable");
			
			return ERROR;
		}
	}
	
	
	public String configureYellowHouseVoting() throws Exception {
		serviceInf = new kovidRMSServiceImpl();
		daoInf = new VotingDAOImpl();
		/*
		 * Getting real Path from Context
		 */
		String realPath = request.getServletContext().getRealPath("/");

		message = daoInf.configureYellowHouseVoting(form.getHeadBoyID(), form.getHeadGirlID(), form.getHouseCaptainYellowID());

		if (message.equalsIgnoreCase("success")) {
			System.out.println("Voting configured successfully.");
			addActionMessage("Voting configured successfully.");
			
			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			request.setAttribute("YellowHouseVoting", "enable");

			return SUCCESS;

		} else {
			System.out.println("Error while configuring Voting. Please check logs for more details.");
			addActionError("Failed to configure voting.");
			
			String VotingStatus = daoInf.retrieveVotingStatus();

			request.setAttribute("VotingStatus", VotingStatus);
			
			request.setAttribute("YellowHouseVoting", "enable");

			return ERROR;
		}
	}
	
	public String configureVotingStatus() throws Exception {

		daoInf = new VotingDAOImpl();

		message = daoInf.configureVotingStatus(form.getAcademicYearID(), form.getVotingStatus());

		if (message.equals("success")) {
			System.out.println("Voting configured Succcessfully...");
			addActionError("Voting configured Succcessfully");
			
			request.setAttribute("loadAcademicYear", "Yes");
			
			String VotingStatus = daoInf.retrieveVotingStatus();
			
			if(VotingStatus == null || VotingStatus == ""){
				
				VotingStatus = "Done";
				
			}else if(VotingStatus.isEmpty()) {
			
				VotingStatus = "Done";
				
			}
			
			request.setAttribute("VotingStatus", VotingStatus);
			
			HeadGirlList = daoInf.retreiveHeadGirlsList(form.getAcademicYearID());
			request.setAttribute("HeadGirlList", HeadGirlList);
			
			HeadBoyList = daoInf.retreiveHeadBoysList(form.getAcademicYearID());
			request.setAttribute("HeadBoyList", HeadBoyList);
			
			RedList = daoInf.retreiveHouseCaptainRedList(form.getAcademicYearID());
			request.setAttribute("RedList", RedList);
			
			BlueList = daoInf.retreiveHouseCaptainBlueList(form.getAcademicYearID());
			request.setAttribute("BlueList", BlueList);
			
			GreenList = daoInf.retreiveHouseCaptainGreenList(form.getAcademicYearID());
			request.setAttribute("GreenList", GreenList);
			
			YellowList = daoInf.retreiveHouseCaptainYellowList(form.getAcademicYearID());
			request.setAttribute("YellowList", YellowList);
			
			return SUCCESS;
			
		} else {
			System.out.println("Error while configuring voting. Please check logs for more details.");
			addActionError("Failed to start voting.");
		
			request.setAttribute("loadAcademicYear", "Yes");
		
			String VotingStatus = daoInf.retrieveVotingStatus();
			
			if(VotingStatus == null || VotingStatus == ""){
				
				VotingStatus = "Done";
				
			}else if(VotingStatus.isEmpty()) {
			
				VotingStatus = "Done";
				
			}
			
			request.setAttribute("VotingStatus", VotingStatus);
			
			HeadGirlList = daoInf.retreiveHeadGirlsList(form.getAcademicYearID());
			request.setAttribute("HeadGirlList", HeadGirlList);
			
			HeadBoyList = daoInf.retreiveHeadBoysList(form.getAcademicYearID());
			request.setAttribute("HeadBoyList", HeadBoyList);
			
			RedList = daoInf.retreiveHouseCaptainRedList(form.getAcademicYearID());
			request.setAttribute("RedList", RedList);
			
			BlueList = daoInf.retreiveHouseCaptainBlueList(form.getAcademicYearID());
			request.setAttribute("BlueList", BlueList);
			
			GreenList = daoInf.retreiveHouseCaptainGreenList(form.getAcademicYearID());
			request.setAttribute("GreenList", GreenList);
			
			YellowList = daoInf.retreiveHouseCaptainYellowList(form.getAcademicYearID());
			request.setAttribute("YellowList", YellowList);

			return ERROR;
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	
	public void removeHeadGirlRow() throws Exception {

		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
		
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteHeadGirlByID(form.getHeadGirlID());

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
	
	public void removeHeadBoyRow() throws Exception {

		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
		
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteHeadBoyByID(form.getHeadBoyID());

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
	
	public void removeRedCaptainRow() throws Exception {

		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
		
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteRedCaptainRowByID(form.getHouseCaptainRedID());

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
	
	public void removeBlueCaptainRow() throws Exception {

		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
		
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteBlueCaptainRowByID(form.getHouseCaptainBlueID());

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
	
	public void removeGreenCaptainRow() throws Exception {

		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
		
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteGreenCaptainRowByID(form.getHouseCaptainGreenID());

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
	
	public void removeYellowCaptainRow() throws Exception {

		daoInf = new VotingDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {
		
			/*
			 * Deleting aySubject row from AYSUbject table based on AYSUbjectID
			 */
			values = daoInf.deleteYellowCaptainRowByID(form.getHouseCaptainYellowID());

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
	
	
	public String generateReport() throws Exception {

		ConvertToPDFUtil convertToPDFUtil = new ConvertToPDFUtil();

		daoInf = new VotingDAOImpl();
	
		LoginDAOInf loginDao = new LoginDAOImpl();
		
		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) sessionAttribute.get("USER");

		String realPath = request.getServletContext().getRealPath("/");

		String AcademicYearName = loginDao.retrieveAcademicYearName(userForm.getOrganizationID());
		
		//System.out.println("AcademicYearName:"+AcademicYearName);
		String pdfFIleName = AcademicYearName + "_report.pdf";

		message = convertToPDFUtil.convertVotingReportOPDPDF(form.getAcademicYearID(), AcademicYearName, realPath, pdfFIleName);

		if (message.equals("success")) {

			form.setFileInputStream(new FileInputStream(new File(realPath + pdfFIleName)));

			form.setFileName(pdfFIleName);

			return SUCCESS;

		} else {
			addActionError("Failed to create report PDF. Please check server logs for more details.");

			return ERROR;
		}
	}

	
	public VotingForm getModel() {
		// TODO Auto-generated method stub
		return form;
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
		this.response = response;
	}

	public List<VotingForm> getHeadGirlList() {
		return HeadGirlList;
	}

	public void setHeadGirlList(List<VotingForm> headGirlList) {
		HeadGirlList = headGirlList;
	}

	public List<VotingForm> getHeadBoyList() {
		return HeadBoyList;
	}

	public void setHeadBoyList(List<VotingForm> headBoyList) {
		HeadBoyList = headBoyList;
	}

	public List<VotingForm> getRedList() {
		return RedList;
	}

	public void setRedList(List<VotingForm> redList) {
		RedList = redList;
	}

	public List<VotingForm> getBlueList() {
		return BlueList;
	}

	public void setBlueList(List<VotingForm> blueList) {
		BlueList = blueList;
	}

	public List<VotingForm> getGreenList() {
		return GreenList;
	}

	public void setGreenList(List<VotingForm> greenList) {
		GreenList = greenList;
	}

	public List<VotingForm> getYellowList() {
		return YellowList;
	}

	public void setYellowList(List<VotingForm> yellowList) {
		YellowList = yellowList;
	}

	
}
