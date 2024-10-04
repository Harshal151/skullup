package com.kovidRMS.form;

import java.io.InputStream;

public class VotingForm {

	private int id;
	private String username;
	private String password;
	private int voteCount;
	private String name;
	private String votingStatus;
	private int academicYearID;
	private String[] studentName;
	private String[] nameType;
	private String[] editStudentName;
	private String[] editNameType;
	private String[] checkboxValue;
	private String[] houseColorID;
	private String[] updateNameType;
	private int headGirlID;
	private int headBoyID;
	private int houseCaptainRedID;
	private int houseCaptainBlueID;
	private int houseCaptainGreenID;
	private int houseCaptainYellowID;
	private InputStream fileInputStream;
	private String fileName;
	
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public int getHeadBoyID() {
		return headBoyID;
	}
	public void setHeadBoyID(int headBoyID) {
		this.headBoyID = headBoyID;
	}
	public int getHouseCaptainRedID() {
		return houseCaptainRedID;
	}
	public void setHouseCaptainRedID(int houseCaptainRedID) {
		this.houseCaptainRedID = houseCaptainRedID;
	}
	public int getHouseCaptainBlueID() {
		return houseCaptainBlueID;
	}
	public void setHouseCaptainBlueID(int houseCaptainBlueID) {
		this.houseCaptainBlueID = houseCaptainBlueID;
	}
	public int getHouseCaptainGreenID() {
		return houseCaptainGreenID;
	}
	public void setHouseCaptainGreenID(int houseCaptainGreenID) {
		this.houseCaptainGreenID = houseCaptainGreenID;
	}
	public int getHouseCaptainYellowID() {
		return houseCaptainYellowID;
	}
	public void setHouseCaptainYellowID(int houseCaptainYellowID) {
		this.houseCaptainYellowID = houseCaptainYellowID;
	}
	public int getHeadGirlID() {
		return headGirlID;
	}
	public void setHeadGirlID(int headGirlID) {
		this.headGirlID = headGirlID;
	}
	public String[] getUpdateNameType() {
		return updateNameType;
	}
	public void setUpdateNameType(String[] updateNameType) {
		this.updateNameType = updateNameType;
	}
	public String[] getEditStudentName() {
		return editStudentName;
	}
	public void setEditStudentName(String[] editStudentName) {
		this.editStudentName = editStudentName;
	}
	public String[] getEditNameType() {
		return editNameType;
	}
	public void setEditNameType(String[] editNameType) {
		this.editNameType = editNameType;
	}
	public String[] getCheckboxValue() {
		return checkboxValue;
	}
	public void setCheckboxValue(String[] checkboxValue) {
		this.checkboxValue = checkboxValue;
	}
	public String[] getHouseColorID() {
		return houseColorID;
	}
	public void setHouseColorID(String[] houseColorID) {
		this.houseColorID = houseColorID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVotingStatus() {
		return votingStatus;
	}
	public void setVotingStatus(String votingStatus) {
		this.votingStatus = votingStatus;
	}
	public int getAcademicYearID() {
		return academicYearID;
	}
	public void setAcademicYearID(int academicYearID) {
		this.academicYearID = academicYearID;
	}
	public String[] getStudentName() {
		return studentName;
	}
	public void setStudentName(String[] studentName) {
		this.studentName = studentName;
	}
	
	public String[] getNameType() {
		return nameType;
	}
	public void setNameType(String[] nameType) {
		this.nameType = nameType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	
}