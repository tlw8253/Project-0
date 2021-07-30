package com.tlw8253.application;

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
public interface Constants {

	//Return status codes
	int ciStatusCodeSuccess = 201;


	//context parameters
	String csClientname = "clientname";
	
	//
	int ciMinNameLen = 5;
	int ciListingPort = 3005;
	
	//database constants
	String csDatabaseName = "project0";
	String csClientTable = csDatabaseName + "." + "client";
	String csPhoneTable = csDatabaseName + "." + "phone";
	String csAccountTable = csDatabaseName + "." + "account";
	
	
	
	


}













