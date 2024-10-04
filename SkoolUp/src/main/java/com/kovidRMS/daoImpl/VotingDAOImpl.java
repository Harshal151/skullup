package com.kovidRMS.daoImpl;

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
/*import org.omg.PortableInterceptor.ACTIVE;*/

import com.kovidRMS.daoInf.*;
import com.kovidRMS.form.*;
import com.kovidRMS.util.*;

public class VotingDAOImpl extends DAOConnection implements VotingDAOInf {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connection1 = null;
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;
	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;
	String status = "error";

	static int counter = 1;

	static boolean check = false;

	static int extraMinutes = 0;

	LoginDAOInf daoInf = null;

	ConfigurationUtil configurationUtil = null;

	ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

	@Override
	public String insertHeadGirlDetails(VotingForm form, String name, int academicYearID) {

		try {

			connection = getConnection();

			String insertHeadGirlDetailsQuery = QueryMaker.INSERT_HeadGirl_Details;

			preparedStatement = connection.prepareStatement(insertHeadGirlDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			System.out.println("Successfully insertd Head Girls Details into HeadGirl table");

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

	@Override
	public String insertHeadBoyDetails(VotingForm form, String name, int academicYearID) {

		try {

			connection = getConnection();

			String insertHeadBoyDetailsQuery = QueryMaker.INSERT_HeadBoy_Details;

			preparedStatement = connection.prepareStatement(insertHeadBoyDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			System.out.println("Successfully insertd Head Boys Details into HeadBoy table");

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

	@Override
	public String insertHouseCaptainRedDetails(VotingForm form, String name, int academicYearID) {

		try {

			connection = getConnection();

			String insertHouseCaptainRedDetailsQuery = QueryMaker.INSERT_HouseCaptain_Red_Details;

			preparedStatement = connection.prepareStatement(insertHouseCaptainRedDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			System.out.println("Successfully insertd House Captain Red Details into RedCaptain table");

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

	@Override
	public String insertHouseCaptainBlueDetails(VotingForm form, String name, int academicYearID) {

		try {

			connection = getConnection();

			String insertHouseCaptainBlueDetailsQuery = QueryMaker.INSERT_HouseCaptain_Blue_Details;

			preparedStatement = connection.prepareStatement(insertHouseCaptainBlueDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			System.out.println("Successfully insertd House Captain Blue Details into RedCaptain table");

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

	@Override
	public String insertHouseCaptainGreenDetails(VotingForm form, String name, int academicYearID) {

		try {

			connection = getConnection();

			String insertHouseCaptainGreenDetailsQuery = QueryMaker.INSERT_HouseCaptain_Green_Details;

			preparedStatement = connection.prepareStatement(insertHouseCaptainGreenDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			System.out.println("Successfully insertd House Captain Green Details into RedCaptain table");

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

	@Override
	public String insertHouseCaptainYellowDetails(VotingForm form, String name, int academicYearID) {

		try {

			connection = getConnection();

			String insertHouseCaptainYellowDetailsQuery = QueryMaker.INSERT_HouseCaptain_Yellow_Details;

			preparedStatement = connection.prepareStatement(insertHouseCaptainYellowDetailsQuery);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, academicYearID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			System.out.println("Successfully insertd House Captain Yellow Details into RedCaptain table");

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

	@Override
	public List<VotingForm> retreiveAcademicYear(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveAcademicYearNameQuery = QueryMaker.RETRIEVE_AcademicYear_Name;

			preparedStatement = connection.prepareStatement(retreiveAcademicYearNameQuery);

			preparedStatement.setInt(1, academicYearID);
			// preparedStatement.setString(2, ActivityStatus.ACTIVE);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setAcademicYearID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Academic Year list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;
	}

	@Override
	public JSONObject retrieveHeadGirlDetails(int academicYearID) {

		VotingForm form = new VotingForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveHeadGirlDetailsQuery = QueryMaker.RETRIEVE_HeadGirl_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveHeadGirlDetailsQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("headGirlID", resultSet.getInt("id"));

				object.put("name", resultSet.getString("name"));

				object.put("candidateApproved", resultSet.getString("candidateApproved"));

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
	public JSONObject retrieveHeadBoyDetails(int academicYearID) {

		VotingForm form = new VotingForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveHeadBoyDetailsQuery = QueryMaker.RETRIEVE_HeadBoy_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveHeadBoyDetailsQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("headBoyID", resultSet.getInt("id"));

				object.put("name", resultSet.getString("name"));

				object.put("candidateApproved", resultSet.getString("candidateApproved"));

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
	public JSONObject retrieveHouseCaptainRedDetails(int academicYearID) {

		VotingForm form = new VotingForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveHouseCaptainRedDetailsQuery = QueryMaker.RETRIEVE_HouseCaptain_Red_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveHouseCaptainRedDetailsQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("houseRedID", resultSet.getInt("id"));

				object.put("name", resultSet.getString("name"));

				object.put("candidateApproved", resultSet.getString("candidateApproved"));

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
	public JSONObject retrieveHouseCaptainBlueDetails(int academicYearID) {

		VotingForm form = new VotingForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveHouseCaptainBlueDetailsQuery = QueryMaker.RETRIEVE_HouseCaptain_Blue_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveHouseCaptainBlueDetailsQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("houseBlueID", resultSet.getInt("id"));

				object.put("name", resultSet.getString("name"));

				object.put("candidateApproved", resultSet.getString("candidateApproved"));

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
	public JSONObject retrieveHouseCaptainGreenDetails(int academicYearID) {

		VotingForm form = new VotingForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveHouseCaptainGreenDetailsQuery = QueryMaker.RETRIEVE_HouseCaptain_Green_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveHouseCaptainGreenDetailsQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("houseGreenID", resultSet.getInt("id"));

				object.put("name", resultSet.getString("name"));

				object.put("candidateApproved", resultSet.getString("candidateApproved"));

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
	public JSONObject retrieveHouseCaptainYellowDetails(int academicYearID) {

		VotingForm form = new VotingForm();

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;

		int check1 = 0;

		try {

			connection = getConnection();

			String retrieveHouseCaptainYellowDetailsQuery = QueryMaker.RETRIEVE_HouseCaptain_Yellow_Details_LIST;

			preparedStatement = connection.prepareStatement(retrieveHouseCaptainYellowDetailsQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("houseYellowID", resultSet.getInt("id"));

				object.put("name", resultSet.getString("name"));

				object.put("candidateApproved", resultSet.getString("candidateApproved"));

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
	public String updateHeadGirlDetails(VotingForm form, int headGirlID, String candidateApproved) {

		try {
			connection = getConnection();

			String updateHeadGirlDetailsQuery = QueryMaker.UPDATE_HeadGirl_Details;

			preparedStatement = connection.prepareStatement(updateHeadGirlDetailsQuery);

			preparedStatement.setString(1, candidateApproved);

			preparedStatement.setInt(2, headGirlID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated HeadGirl Details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating HeadGirl Details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String updateHeadBoyDetails(VotingForm form, int headBoyID, String candidateApproved) {

		try {
			connection = getConnection();

			String updateHeadBoyDetailsQuery = QueryMaker.UPDATE_HeadBoy_Details;

			preparedStatement = connection.prepareStatement(updateHeadBoyDetailsQuery);

			preparedStatement.setString(1, candidateApproved);

			preparedStatement.setInt(2, headBoyID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated HeadBoy Details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating HeadBoy Details into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String updateHouseCaptainRedDetails(VotingForm form, int houseRedID, String candidateApproved) {

		try {
			connection = getConnection();

			String updateHouseCaptainRedDetailsQuery = QueryMaker.UPDATE_HouseCaptain_Red_Details;

			preparedStatement = connection.prepareStatement(updateHouseCaptainRedDetailsQuery);

			preparedStatement.setString(1, candidateApproved);

			preparedStatement.setInt(2, houseRedID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated House Captain Red Details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating House Captain Red Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String updateHouseCaptainBlueDetails(VotingForm form, int houseBlueID, String candidateApproved) {

		try {
			connection = getConnection();

			String updateHouseCaptainBlueDetailsQuery = QueryMaker.UPDATE_HouseCaptain_Blue_Details;

			preparedStatement = connection.prepareStatement(updateHouseCaptainBlueDetailsQuery);

			preparedStatement.setString(1, candidateApproved);

			preparedStatement.setInt(2, houseBlueID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated House Captain Blue Details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating House Captain Blue Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String updateHouseCaptainGreenDetails(VotingForm form, int houseGreenID, String candidateApproved) {

		try {
			connection = getConnection();

			String updateHouseCaptainGreenDetailsQuery = QueryMaker.UPDATE_HouseCaptain_Green_Details;

			preparedStatement = connection.prepareStatement(updateHouseCaptainGreenDetailsQuery);

			preparedStatement.setString(1, candidateApproved);

			preparedStatement.setInt(2, houseGreenID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated House Captain Green Details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating House Captain Green Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String updateHouseCaptainYellowDetails(VotingForm form, int houseYellowID, String candidateApproved) {

		try {
			connection = getConnection();

			String updateHouseCaptainYellowDetailsQuery = QueryMaker.UPDATE_HouseCaptain_Yellow_Details;

			preparedStatement = connection.prepareStatement(updateHouseCaptainYellowDetailsQuery);

			preparedStatement.setString(1, candidateApproved);

			preparedStatement.setInt(2, houseYellowID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Successfully udpated House Captain Yellow Details into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating House Captain Yellow Details into table due to:::"
					+ exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String retrieveVotingStatus() {

		String VotingStatus = "";

		try {
			connection = getConnection();

			String retrieveVotingStatusQuery = QueryMaker.RETRIEVE_Voting_Status_Name;

			preparedStatement = connection.prepareStatement(retrieveVotingStatusQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				VotingStatus = resultSet.getString("votingStatus");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving Voting Status from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return VotingStatus;
	}

	@Override
	public List<VotingForm> retreiveHeadGirlsList(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveHeadGirlsListQuery = QueryMaker.RETRIEVE_HeadGirls_List;

			preparedStatement = connection.prepareStatement(retreiveHeadGirlsListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Yes");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setHeadGirlID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				form.setVoteCount(resultSet.getInt("voteCount"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Head Girls list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<VotingForm> retreiveHeadBoysList(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveHeadBoysListQuery = QueryMaker.RETRIEVE_HeadBoys_List;

			preparedStatement = connection.prepareStatement(retreiveHeadBoysListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Yes");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setHeadBoyID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				form.setVoteCount(resultSet.getInt("voteCount"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving Head Boys list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<VotingForm> retreiveHouseCaptainRedList(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveHouseCaptainRedListQuery = QueryMaker.RETRIEVE_HouseCaptain_Red_List;

			preparedStatement = connection.prepareStatement(retreiveHouseCaptainRedListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Yes");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setHouseCaptainRedID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				form.setVoteCount(resultSet.getInt("voteCount"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving House Captain Red list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<VotingForm> retreiveHouseCaptainBlueList(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveHouseCaptainBlueListQuery = QueryMaker.RETRIEVE_HouseCaptain_Blue_List;

			preparedStatement = connection.prepareStatement(retreiveHouseCaptainBlueListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Yes");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setHouseCaptainBlueID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				form.setVoteCount(resultSet.getInt("voteCount"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving House Captain Blue list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<VotingForm> retreiveHouseCaptainGreenList(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveHouseCaptainGreenListQuery = QueryMaker.RETRIEVE_HouseCaptain_Green_List;

			preparedStatement = connection.prepareStatement(retreiveHouseCaptainGreenListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Yes");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setHouseCaptainGreenID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				form.setVoteCount(resultSet.getInt("voteCount"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving House Captain Green list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public List<VotingForm> retreiveHouseCaptainYellowList(int academicYearID) {

		List<VotingForm> list = new ArrayList<VotingForm>();
		VotingForm form = null;

		try {

			connection = getConnection();

			String retreiveHouseCaptainYellowListQuery = QueryMaker.RETRIEVE_HouseCaptain_Yellow_List;

			preparedStatement = connection.prepareStatement(retreiveHouseCaptainYellowListQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, "Yes");
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new VotingForm();

				form.setHouseCaptainYellowID(resultSet.getInt("id"));

				form.setName(resultSet.getString("name"));

				form.setVoteCount(resultSet.getInt("voteCount"));

				list.add(form);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retriving House Captain Yellow list from database due to:::"
					+ exception.getMessage());

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return list;

	}

	@Override
	public String configureRedHouseVoting(int headBoyID, int headGirlID, int houseCaptainRedID) {

		try {

			connection = getConnection();

			String configureRedHouseVotingQuery = QueryMaker.UPDATE_HouseCaptain_Red__Voting_Details;
			String configureHeadBoyVotingQuery = QueryMaker.UPDATE_HeadBoy_Voting_Details;
			String configureHeadGirlVotingQuery = QueryMaker.UPDATE_HeadGirl_Voting_Details;

			preparedStatement = connection.prepareStatement(configureRedHouseVotingQuery);

			preparedStatement.setInt(1, houseCaptainRedID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			preparedStatement1 = connection.prepareStatement(configureHeadBoyVotingQuery);

			preparedStatement1.setInt(1, headBoyID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			preparedStatement1.executeUpdate();

			preparedStatement2 = connection.prepareStatement(configureHeadGirlVotingQuery);

			preparedStatement2.setInt(1, headGirlID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);

			preparedStatement2.executeUpdate();

			System.out.println("Successfully configured Voting Details into table");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String configureBlueHouseVoting(int headBoyID, int headGirlID, int houseCaptainBlueID) {

		try {

			connection = getConnection();

			String configureBlueHouseVotingQuery = QueryMaker.UPDATE_HouseCaptain_Blue__Voting_Details;
			String configureHeadBoyVotingQuery = QueryMaker.UPDATE_HeadBoy_Voting_Details;
			String configureHeadGirlVotingQuery = QueryMaker.UPDATE_HeadGirl_Voting_Details;

			preparedStatement = connection.prepareStatement(configureBlueHouseVotingQuery);

			preparedStatement.setInt(1, houseCaptainBlueID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			preparedStatement1 = connection.prepareStatement(configureHeadBoyVotingQuery);

			preparedStatement1.setInt(1, headBoyID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			preparedStatement1.executeUpdate();

			preparedStatement2 = connection.prepareStatement(configureHeadGirlVotingQuery);

			preparedStatement2.setInt(1, headGirlID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);

			preparedStatement2.executeUpdate();

			System.out.println("Successfully configured Voting Details into table");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String configureGreenHouseVoting(int headBoyID, int headGirlID, int houseCaptainGreenID) {

		try {

			connection = getConnection();

			String configureGreenHouseVotingQuery = QueryMaker.UPDATE_HouseCaptain_Green__Voting_Details;
			String configureHeadBoyVotingQuery = QueryMaker.UPDATE_HeadBoy_Voting_Details;
			String configureHeadGirlVotingQuery = QueryMaker.UPDATE_HeadGirl_Voting_Details;

			preparedStatement = connection.prepareStatement(configureGreenHouseVotingQuery);

			preparedStatement.setInt(1, houseCaptainGreenID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			preparedStatement1 = connection.prepareStatement(configureHeadBoyVotingQuery);

			preparedStatement1.setInt(1, headBoyID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			preparedStatement1.executeUpdate();

			preparedStatement2 = connection.prepareStatement(configureHeadGirlVotingQuery);

			preparedStatement2.setInt(1, headGirlID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);

			preparedStatement2.executeUpdate();

			System.out.println("Successfully configured Voting Details into table");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String configureYellowHouseVoting(int headBoyID, int headGirlID, int houseCaptainYellowID) {

		try {

			connection = getConnection();

			String configureYellowHouseVotingQuery = QueryMaker.UPDATE_HouseCaptain_Yellow__Voting_Details;
			String configureHeadBoyVotingQuery = QueryMaker.UPDATE_HeadBoy_Voting_Details;
			String configureHeadGirlVotingQuery = QueryMaker.UPDATE_HeadGirl_Voting_Details;

			preparedStatement = connection.prepareStatement(configureYellowHouseVotingQuery);

			preparedStatement.setInt(1, houseCaptainYellowID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.executeUpdate();

			preparedStatement1 = connection.prepareStatement(configureHeadBoyVotingQuery);

			preparedStatement1.setInt(1, headBoyID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			preparedStatement1.executeUpdate();

			preparedStatement2 = connection.prepareStatement(configureHeadGirlVotingQuery);

			preparedStatement2.setInt(1, headGirlID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);

			preparedStatement2.executeUpdate();

			System.out.println("Successfully configured Voting Details into table");

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeStatement(preparedStatement1);
			JDBCHelper.closeStatement(preparedStatement2);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String configureVotingStatus(int academicYearID, String votingStatus) {

		try {
			connection = getConnection();

			String updateHeadGirlDetailsQuery = QueryMaker.UPDATE_VOTING_STATUS;

			preparedStatement = connection.prepareStatement(updateHeadGirlDetailsQuery);

			preparedStatement.setString(1, votingStatus);

			preparedStatement.setInt(2, academicYearID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully configured Voting Status into table");

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while configuring Voting Status into table due to:::" + exception.getMessage());
			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return status;
	}

	@Override
	public String retrieveHeadGirlName(int academicYearID) {

		String HeadGirlDetails = "";

		try {
			connection = getConnection();

			String retrieveHeadGirlNameQuery = QueryMaker.RETRIEVE_HeadGirls_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveHeadGirlNameQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Yes");
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				HeadGirlDetails = resultSet.getString("Name") + "," + resultSet.getInt("voteCount");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving HeadGirl Details from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return HeadGirlDetails;
	}

	@Override
	public String retrieveHeadBoyName(int academicYearID) {

		String HeadBoyDetails = "";

		try {
			connection = getConnection();

			String retrieveHeadBoyNameQuery = QueryMaker.RETRIEVE_HeadBoy_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveHeadBoyNameQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Yes");
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				HeadBoyDetails = resultSet.getString("Name") + "," + resultSet.getInt("voteCount");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving HeadBoy Details from table due to:::" + exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return HeadBoyDetails;
	}

	@Override
	public String retrieveRedCaptainName(int academicYearID) {

		String RedCaptainDetails = "";

		try {
			connection = getConnection();

			String retrieveRedCaptainQuery = QueryMaker.RETRIEVE_RedCaptain_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveRedCaptainQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Yes");
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				RedCaptainDetails = resultSet.getString("Name") + "," + resultSet.getInt("voteCount");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving RedCaptain Details from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return RedCaptainDetails;
	}

	@Override
	public String retrieveBlueCaptainName(int academicYearID) {

		String BlueCaptainDetails = "";

		try {
			connection = getConnection();

			String retrieveBlueCaptainQuery = QueryMaker.RETRIEVE_BlueCaptain_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveBlueCaptainQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Yes");
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BlueCaptainDetails = resultSet.getString("Name") + "," + resultSet.getInt("voteCount");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving BlueCaptain Details from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return BlueCaptainDetails;
	}

	@Override
	public String retrieveGreenCaptainName(int academicYearID) {

		String GreenCaptainDetails = "";

		try {
			connection = getConnection();

			String retrieveGreenCaptainQuery = QueryMaker.RETRIEVE_GreenCaptain_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveGreenCaptainQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Yes");
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				GreenCaptainDetails = resultSet.getString("Name") + "," + resultSet.getInt("voteCount");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving GreenCaptain Details from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return GreenCaptainDetails;
	}

	@Override
	public String retrieveYellowCaptainName(int academicYearID) {

		String YellowCaptainDetails = "";

		try {
			connection = getConnection();

			String retrieveYellowCaptainQuery = QueryMaker.RETRIEVE_YellowCaptain_DETAILS;

			preparedStatement = connection.prepareStatement(retrieveYellowCaptainQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setString(3, "Yes");
			preparedStatement.setInt(4, academicYearID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				YellowCaptainDetails = resultSet.getString("Name") + "," + resultSet.getInt("voteCount");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving YellowCaptain Details from table due to:::"
					+ exception.getMessage());
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return YellowCaptainDetails;
	}

	@Override
	public HashMap<String, String> retrieveHeadGirlDetailsList(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveHeadGirlsTotalCountQuery = QueryMaker.RETRIEVE_HeadGirls_Total_Count;
			String retrieveApprovedHeadGirlCountQuery = QueryMaker.RETRIEVE_HeadGirls_Approved_Count;
			String retrieveHeadGirlsTotalVoteQuery = QueryMaker.RETRIEVE_HeadGirls_Total_Vote;

			preparedStatement = connection.prepareStatement(retrieveHeadGirlsTotalCountQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("HeadGirlCount", resultSet.getString("count"));

			}

			preparedStatement1 = connection.prepareStatement(retrieveApprovedHeadGirlCountQuery);

			preparedStatement1.setInt(1, academicYearID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, "Yes");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("HeadGirlApprovedCount", resultSet1.getString("count"));

			}

			preparedStatement2 = connection.prepareStatement(retrieveHeadGirlsTotalVoteQuery);

			preparedStatement2.setInt(1, academicYearID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);
			preparedStatement2.setString(3, "Yes");

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("HeadGirlTotalVote", resultSet2.getString("voterCount"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;

	}

	@Override
	public HashMap<String, String> retrieveHeadBoyDetailsList(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveHeadBoysTotalCountQuery = QueryMaker.RETRIEVE_HeadBoys_Total_Count;
			String retrieveApprovedHeadBoyCountQuery = QueryMaker.RETRIEVE_HeadBoys_Approved_Count;
			String retrieveHeadBoysTotalVoteQuery = QueryMaker.RETRIEVE_HeadBoys_Total_Vote;

			preparedStatement = connection.prepareStatement(retrieveHeadBoysTotalCountQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("HeadBoyCount", resultSet.getString("count"));

			}

			preparedStatement1 = connection.prepareStatement(retrieveApprovedHeadBoyCountQuery);

			preparedStatement1.setInt(1, academicYearID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, "Yes");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("HeadBoyApprovedCount", resultSet1.getString("count"));

			}

			preparedStatement2 = connection.prepareStatement(retrieveHeadBoysTotalVoteQuery);

			preparedStatement2.setInt(1, academicYearID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);
			preparedStatement2.setString(3, "Yes");

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("HeadBoyTotalVote", resultSet2.getString("voterCount"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;

	}

	@Override
	public HashMap<String, String> retrieveRedCaptainDetailsList(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveRedCaptainTotalCountQuery = QueryMaker.RETRIEVE_RedCaptain_Total_Count;
			String retrieveApprovedRedCaptainCountQuery = QueryMaker.RETRIEVE_RedCaptain_Approved_Count;
			String retrieveRedCaptainTotalVoteQuery = QueryMaker.RETRIEVE_RedCaptain_Total_Vote;

			preparedStatement = connection.prepareStatement(retrieveRedCaptainTotalCountQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("RedCaptainCount", resultSet.getString("count"));

			}

			preparedStatement1 = connection.prepareStatement(retrieveApprovedRedCaptainCountQuery);

			preparedStatement1.setInt(1, academicYearID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, "Yes");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("RedCaptainApprovedCount", resultSet1.getString("count"));

			}

			preparedStatement2 = connection.prepareStatement(retrieveRedCaptainTotalVoteQuery);

			preparedStatement2.setInt(1, academicYearID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);
			preparedStatement2.setString(3, "Yes");

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("RedCaptainTotalVote", resultSet2.getString("voterCount"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;

	}

	@Override
	public HashMap<String, String> retrieveBlueCaptainDetailsList(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveBlueCaptainTotalCountQuery = QueryMaker.RETRIEVE_BlueCaptain_Total_Count;
			String retrieveApprovedBlueCaptainCountQuery = QueryMaker.RETRIEVE_BlueCaptain_Approved_Count;
			String retrieveBlueCaptainTotalVoteQuery = QueryMaker.RETRIEVE_BlueCaptain_Total_Vote;

			preparedStatement = connection.prepareStatement(retrieveBlueCaptainTotalCountQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("BlueCaptainCount", resultSet.getString("count"));

			}

			preparedStatement1 = connection.prepareStatement(retrieveApprovedBlueCaptainCountQuery);

			preparedStatement1.setInt(1, academicYearID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, "Yes");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("BlueCaptainApprovedCount", resultSet1.getString("count"));

			}

			preparedStatement2 = connection.prepareStatement(retrieveBlueCaptainTotalVoteQuery);

			preparedStatement2.setInt(1, academicYearID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);
			preparedStatement2.setString(3, "Yes");

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("BlueCaptainTotalVote", resultSet2.getString("voterCount"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}
		return map;

	}

	@Override
	public HashMap<String, String> retrieveGreenCaptainDetailsList(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveGreenCaptainTotalCountQuery = QueryMaker.RETRIEVE_GreenCaptain_Total_Count;
			String retrieveApprovedGreenCaptainCountQuery = QueryMaker.RETRIEVE_GreenCaptain_Approved_Count;
			String retrieveGreenCaptainTotalVoteQuery = QueryMaker.RETRIEVE_GreenCaptain_Total_Vote;

			preparedStatement = connection.prepareStatement(retrieveGreenCaptainTotalCountQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("GreenCaptainCount", resultSet.getString("count"));

			}

			preparedStatement1 = connection.prepareStatement(retrieveApprovedGreenCaptainCountQuery);

			preparedStatement1.setInt(1, academicYearID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, "Yes");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("GreenCaptainApprovedCount", resultSet1.getString("count"));

			}

			preparedStatement2 = connection.prepareStatement(retrieveGreenCaptainTotalVoteQuery);

			preparedStatement2.setInt(1, academicYearID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);
			preparedStatement2.setString(3, "Yes");

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("GreenCaptainTotalVote", resultSet2.getString("voterCount"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;

	}

	@Override
	public HashMap<String, String> retrieveYellowCaptainDetailsList(int academicYearID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveYellowCaptainTotalCountQuery = QueryMaker.RETRIEVE_YellowCaptain_Total_Count;
			String retrieveApprovedYellowCaptainCountQuery = QueryMaker.RETRIEVE_YellowCaptain_Approved_Count;
			String retrieveYellowCaptainTotalVoteQuery = QueryMaker.RETRIEVE_YellowCaptain_Total_Vote;

			preparedStatement = connection.prepareStatement(retrieveYellowCaptainTotalCountQuery);

			preparedStatement.setInt(1, academicYearID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put("YellowCaptainCount", resultSet.getString("count"));

			}

			preparedStatement1 = connection.prepareStatement(retrieveApprovedYellowCaptainCountQuery);

			preparedStatement1.setInt(1, academicYearID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);
			preparedStatement1.setString(3, "Yes");

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				map.put("YellowCaptainApprovedCount", resultSet1.getString("count"));

			}

			preparedStatement2 = connection.prepareStatement(retrieveYellowCaptainTotalVoteQuery);

			preparedStatement2.setInt(1, academicYearID);
			preparedStatement2.setString(2, ActivityStatus.ACTIVE);
			preparedStatement2.setString(3, "Yes");

			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet2.next()) {

				map.put("YellowCaptainTotalVote", resultSet2.getString("voterCount"));

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;

	}

	@Override
	public JSONObject deleteHeadGirlByID(int headGirlID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteHeadGirlByIDQuery = QueryMaker.DELETE_HeadGirl_Row_BY_ID;

			preparedStatement = connection.prepareStatement(deleteHeadGirlByIDQuery);

			preparedStatement.setInt(1, headGirlID);

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

	@Override
	public JSONObject deleteHeadBoyByID(int headBoyID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteHeadBoyByIDQuery = QueryMaker.DELETE_HeadBoy_Row_BY_ID;

			preparedStatement = connection.prepareStatement(deleteHeadBoyByIDQuery);

			preparedStatement.setInt(1, headBoyID);

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

	@Override
	public JSONObject deleteRedCaptainRowByID(int houseCaptainRedID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteRedCaptainByIDQuery = QueryMaker.DELETE_RedCaptain_Row_BY_ID;

			preparedStatement = connection.prepareStatement(deleteRedCaptainByIDQuery);

			preparedStatement.setInt(1, houseCaptainRedID);

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

	@Override
	public JSONObject deleteBlueCaptainRowByID(int houseCaptainBlueID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteBlueCaptainByIDQuery = QueryMaker.DELETE_BlueCaptain_Row_BY_ID;

			preparedStatement = connection.prepareStatement(deleteBlueCaptainByIDQuery);

			preparedStatement.setInt(1, houseCaptainBlueID);

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

	@Override
	public JSONObject deleteGreenCaptainRowByID(int houseCaptainGreenID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteGreenCaptainByIDQuery = QueryMaker.DELETE_GreenCaptain_Row_BY_ID;

			preparedStatement = connection.prepareStatement(deleteGreenCaptainByIDQuery);

			preparedStatement.setInt(1, houseCaptainGreenID);

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

	@Override
	public JSONObject deleteYellowCaptainRowByID(int houseCaptainYellowID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String deleteYellowCaptainByIDQuery = QueryMaker.DELETE_YellowCaptain_Row_BY_ID;

			preparedStatement = connection.prepareStatement(deleteYellowCaptainByIDQuery);

			preparedStatement.setInt(1, houseCaptainYellowID);

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

}
