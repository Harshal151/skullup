package com.kovidRMS.daoInf;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.kovidRMS.form.VotingForm;

public interface VotingDAOInf {

	public String insertHeadGirlDetails(VotingForm form, String name, int academicYearID);

	public String insertHeadBoyDetails(VotingForm form, String name, int academicYearID);

	public String insertHouseCaptainRedDetails(VotingForm form, String name, int academicYearID);

	public String insertHouseCaptainBlueDetails(VotingForm form, String name, int academicYearID);

	public String insertHouseCaptainGreenDetails(VotingForm form, String name, int academicYearID);

	public String insertHouseCaptainYellowDetails(VotingForm form, String name, int academicYearID);

	public List<VotingForm> retreiveAcademicYear(int academicYearID);

	public JSONObject retrieveHeadGirlDetails(int academicYearID);

	public JSONObject retrieveHeadBoyDetails(int academicYearID);

	public JSONObject retrieveHouseCaptainRedDetails(int academicYearID);

	public JSONObject retrieveHouseCaptainBlueDetails(int academicYearID);

	public JSONObject retrieveHouseCaptainGreenDetails(int academicYearID);

	public JSONObject retrieveHouseCaptainYellowDetails(int academicYearID);

	public String updateHeadGirlDetails(VotingForm form, int headGirlID, String candidateApproved);

	public String updateHeadBoyDetails(VotingForm form, int headBoyID, String candidateApproved);

	public String updateHouseCaptainRedDetails(VotingForm form, int houseRedID, String candidateApproved);

	public String updateHouseCaptainBlueDetails(VotingForm form, int houseBlueID, String candidateApproved);

	public String updateHouseCaptainGreenDetails(VotingForm form, int houseGreenID, String candidateApproved);

	public String updateHouseCaptainYellowDetails(VotingForm form, int houseYellowID, String candidateApproved);

	public String retrieveVotingStatus();

	public List<VotingForm> retreiveHeadGirlsList(int academicYearID);
	
	public List<VotingForm> retreiveHeadBoysList(int academicYearID);
	
	public List<VotingForm> retreiveHouseCaptainRedList(int academicYearID);
	
	public List<VotingForm> retreiveHouseCaptainBlueList(int academicYearID);
	
	public List<VotingForm> retreiveHouseCaptainGreenList(int academicYearID);
	
	public List<VotingForm> retreiveHouseCaptainYellowList(int academicYearID);

	public String configureRedHouseVoting(int headBoyID, int headGirlID, int houseCaptainRedID);

	public String configureBlueHouseVoting(int headBoyID, int headGirlID, int houseCaptainBlueID);

	public String configureGreenHouseVoting(int headBoyID, int headGirlID, int houseCaptainGreenID);

	public String configureYellowHouseVoting(int headBoyID, int headGirlID, int houseCaptainYellowID);

	public String configureVotingStatus(int academicYearID, String votingStatus);

	public String retrieveHeadGirlName(int academicYearID);
	
	public String retrieveHeadBoyName(int academicYearID);
	
	public String retrieveRedCaptainName(int academicYearID);
	
	public String retrieveBlueCaptainName(int academicYearID);
	
	public String retrieveGreenCaptainName(int academicYearID);
	
	public String retrieveYellowCaptainName(int academicYearID);

	public HashMap<String, String> retrieveHeadGirlDetailsList(int academicYearID);

	public HashMap<String, String> retrieveHeadBoyDetailsList(int academicYearID);

	public HashMap<String, String> retrieveRedCaptainDetailsList(int academicYearID);

	public HashMap<String, String> retrieveBlueCaptainDetailsList(int academicYearID);

	public HashMap<String, String> retrieveGreenCaptainDetailsList(int academicYearID);

	public HashMap<String, String> retrieveYellowCaptainDetailsList(int academicYearID);

	public JSONObject deleteHeadGirlByID(int headGirlID);

	public JSONObject deleteHeadBoyByID(int headBoyID);

	public JSONObject deleteRedCaptainRowByID(int houseCaptainRedID);

	public JSONObject deleteBlueCaptainRowByID(int houseCaptainBlueID);

	public JSONObject deleteGreenCaptainRowByID(int houseCaptainGreenID);

	public JSONObject deleteYellowCaptainRowByID(int houseCaptainYellowID);
	
	
}
