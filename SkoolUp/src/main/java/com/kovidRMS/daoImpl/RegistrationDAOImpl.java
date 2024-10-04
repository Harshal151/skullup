package com.kovidRMS.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kovidRMS.util.QueryMaker;
import com.kovidRMS.daoInf.RegistrationDAOinf;
import com.kovidRMS.util.DAOConnection;
import com.kovidRMS.util.JDBCHelper;

public class RegistrationDAOImpl extends DAOConnection implements RegistrationDAOinf {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatement1 = null;
	PreparedStatement preparedStatement2 = null;
	PreparedStatement preparedStatement3 = null;
	PreparedStatement preparedStatement4 = null;
	ResultSet resultSet = null;
	ResultSet resultSet1 = null;
	ResultSet resultSet2 = null;
	ResultSet resultSet3 = null;
	ResultSet resultSet4 = null;
	String status = "error";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.RegistrationDAOinf#retrieveUserIDByUsername(java
	 * .lang.String)
	 */
	public int retrieveUserIDByUsername(String username) {
		int userId = 0;

		try {
			connection = getConnection();

			String retrieveUserIDByUsernameQuery = QueryMaker.RETRIEVE_USERID;

			preparedStatement = connection.prepareStatement(retrieveUserIDByUsernameQuery);

			preparedStatement.setString(1, username);

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
}
