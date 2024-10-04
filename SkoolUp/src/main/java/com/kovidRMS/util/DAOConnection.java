package com.kovidRMS.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.kovidRMS.util.ConfigListenerUtil;

public class DAOConnection {

	/**
	 * 
	 * @return
	 */
	
	public static Connection getConnection() {
		Connection connection = null;
		
		 ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();
		 
		// Fetching Hostname from XML
		 String DBHostName = configXMLUtil.getDBIP().trim();
		 
		// Fetching DB Port No
		 String DBPort = configXMLUtil.getDBPort().trim();
		 
		// Fetching DB name from XML
		 String DBName = configXMLUtil.getDBName().trim();
		 
		 String DBDriver = "com.mysql.jdbc.Driver";
			String DBUrl = "jdbc:mysql://" + DBHostName + ":" + DBPort + "/"
					+ DBName + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		 
		// Fetching DB Username from XML
		 String DBUser = configXMLUtil.getDBUsername().trim();
		 
		// Fetching DB Password from XML
		 String DBPass = configXMLUtil.getDBPassword().trim();
		 
		 try {
			 Class.forName(DBDriver);
			 
			 connection = DriverManager.getConnection(DBUrl, DBUser, DBPass) ;
			 
		}catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Failed to establish connection due to ::::::"
				+ exception.getMessage());
		} 
		return connection;
	}

	/**
	 * 
	 * @param realPath
	 * @return
	 */
	
	public static Connection getConnection(String realPath) {
		Connection connection = null;
		
		ConfigListenerUtil configXMLUtil = new ConfigListenerUtil();
		
		// Fetching Hostname from XML
		String DBHostName = configXMLUtil.getDBIP(realPath).trim();

		// Fetching DB Port No
		String DBPort = configXMLUtil.getDBPort(realPath).trim();

		// Fetching DB name from XML
		String DBName = configXMLUtil.getDBName(realPath).trim();

		String DBDriver = "com.mysql.jdbc.Driver";
		String DBUrl = "jdbc:mysql://" + DBHostName + ":" + DBPort + "/"
				+ DBName + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";

		// Fetching DB Username from XML
		String DBUser = configXMLUtil.getDBUsername(realPath).trim();

		// Fetching DB Password from XML
		String DBPass = configXMLUtil.getDBPassword(realPath).trim();
		
		try {
			Class.forName(DBDriver);

			connection = DriverManager.getConnection(DBUrl, DBUser, DBPass);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Failed t establish connection duee to ::::::"
					+ exception.getMessage());
		}
		return connection;
		
	}
}
