package com.tlw8253.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Application;

/**
 * This is a test driver for developing the ConnectionUtility class.
 * 
 * @author tlw8748253
 *
 */
public class ConnectionUtilityDriver {		
	private final static Logger objLogger = LoggerFactory.getLogger(ConnectionUtilityDriver.class);

	public ConnectionUtilityDriver() {
		super();
	}

	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");
		
		objLogger.debug(sMethod + "p0_db_url: [" + System.getenv("p0_db_url") + "]");
		objLogger.debug(sMethod + "p0_db_username: [" + System.getenv("p0_db_username") + "]");
		//really should not show or write the pwd to logs
		//objLogger.debug(sLogMsgHdr + "p0_db_password: [" + System.getenv("p0_db_password") + "]");
		

		try {
			Connection objConnection = ConnectionUtility.getConnection();
			System.out.println(objConnection);
		}
		catch(SQLException objE)
		{
			objLogger.error(sMethod + "SQLException when connecting to database.");
			objLogger.error(sMethod + "  Using p0_db_url: [" + System.getenv("p0_db_url") + "]");
			objLogger.error(sMethod + "  Exception message: [" + objE.getMessage() + "]");			
			objE.printStackTrace();
		}

	}


}
