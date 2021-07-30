package com.tlw8253.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.javalin.JavalinHelper;

/**
 * This is the main driver for this project.
 * 
 * @author tlw8748253
 *
 */
public class Application implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");
		
		JavalinHelper objJavalinHelper = new JavalinHelper();
		objJavalinHelper.createRoutes();
		objJavalinHelper.start(ciListingPort);
		

	}

}
