package com.tlw8253.javalin;

/**
 * Implementing class Constants as an interface allows the class to 
 * use defined variable direction without using qualifying notation: Constants.varName;
 * 
 * Classes not implementing class Constants can still access variable using qualifying notation: Constants.varName;
 * 
 * @author tlw8748253
 *
 */

/*
 * Interface methods are by default abstract and public
 * Interface attributes are by default public, static and final
 */
public interface JavalinConstants {

	//Return status codes
	int ciStatusCodeSuccess = 201;


	//context parameters
	String csParamClientName = "client_name";
	String csParamClientId = "client_id";
	String csParamClientIdForAccts = "client_id_accounts";
	String csParamClientIdForAcctsRange = "client_id_accounts_range";
	String csParamAcctsLowerRange = "accounts_lower_range";
	String csParamAcctsUpperRange = "accounts_upper_range";
	
	//
	int ciMinNameLen = 5;
	int ciListingPort = 3005;
	
	
	
	
	
	
	


}













