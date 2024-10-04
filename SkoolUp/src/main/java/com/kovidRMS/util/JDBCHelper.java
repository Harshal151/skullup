package com.kovidRMS.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCHelper {

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {

				connection.close();

			} catch (Exception exception) {
				System.out.println("Problem in closing connection");
				exception.printStackTrace();
			}
		}
	}

	public static void closeStatement(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {

				preparedStatement.close();

			} catch (Exception exception) {
				System.out.println("Problem in closing statement");
				exception.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {

				resultSet.close();

			} catch (Exception exception) {
				System.out.println("Problem in closing resultSet");
				exception.printStackTrace();
			}
		}
	}

}
