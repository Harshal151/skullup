package com.kovidRMS.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.kovidRMS.form.LoginForm;

public class ConfigurationUtil extends DAOConnection {
	Connection connection;

	PreparedStatement preparedStatement;
	ResultSet resultSet;

	HttpServletRequest request = ServletActionContext.getRequest();

	HttpSession session = request.getSession();

	LoginForm form = (LoginForm) session.getAttribute("USER");

	
	
	public boolean verifyCommunicationCheck(int studentID, String relation) {

		boolean check = false;
		String emailID = "";
		
		try {

			connection = getConnection();

			String verifyCommunicationCheckQuery = "SELECT emailId FROM Parent WHERE studentID = ? AND relation = ?";

			preparedStatement = connection.prepareStatement(verifyCommunicationCheckQuery);

			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, relation);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emailID = resultSet.getString("emailId");
				
				if(emailID == "" || emailID == null) {
					check = false;
				}else if(emailID.isEmpty()){
					check = false;
				}else{
					check = true;
				}
				
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			
			exception.printStackTrace();
		}

		return check;
	}



	public String getReportFilePath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
