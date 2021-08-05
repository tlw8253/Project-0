package com.tlw8253.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.mariadb.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Application;

public class ConnectionUtility {
	private final static Logger objLogger = LoggerFactory.getLogger(ConnectionUtility.class);

	private ConnectionUtility() {
	}
	
	public static Connection getConnection() throws SQLException {
		String sMethod = "getConnection(): ";
		objLogger.trace(sMethod + "Entered");
				
		DriverManager.registerDriver(new Driver());
		String sURL = System.getenv("p0_db_url");
		String sUsername = System.getenv("p0_db_username");
		String sPassword = System.getenv("p0_db_password");
		//sPassword = "bogus";	//used for exception testing
		
		objLogger.debug(sMethod + "Attempting database connection: URL: [" + sURL + "] username: [" + sUsername + "]");
		Connection conConnection = DriverManager.getConnection(sURL, sUsername, sPassword);
			
		return(conConnection);
	}

}
