package com.tlw8253.javalin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.javalin.requests.GetRequest;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

/**
 *  This class is based on the Javalin webpage on test tutorial https://javalin.io/tutorials/testing
 * 	Code ideas from GIT Clone of https://github.com/tipsy/javalin-testing-example.git were used in
 * 	this source code and modified for this project
 * 
 * @author tlw8748253
 *
 */
public class ClientController implements JavalinConstants {
	private final static Logger objLogger = LoggerFactory.getLogger(ClientController.class);
	private static String sClass = "ClientController{}: ";

	// TODO: this will get replaced with database access storing client information
    static List<String> alClients = new ArrayList<>(Arrays.asList("Client1", "Client2", "Client3"));


	public ClientController(Logger objLogger) {
		super();
	}

	
    //
	//### controller methods for HTTP requests
    public static void postCtrl(Context objCtx) {
		String sMethod = "postCtrl(): ";
		String sLogMsgHdr = sClass + sMethod;    	
    	objLogger.info(sLogMsgHdr + "STUB: for post requests.");
    	
    }
    
    public static void putCtrl(Context objCtx) {
		String sMethod = "putCtrl(): ";
		String sLogMsgHdr = sClass + sMethod;    	
    	objLogger.info(sLogMsgHdr + "STUB: for put requests.");
    	
    }

    public static void deleteCtrl(Context objCtx) {
		String sMethod = "deleteCtrl(): ";
		String sLogMsgHdr = sClass + sMethod;    	
    	objLogger.info(sLogMsgHdr + "STUB: for delete requests.");
    	
    }

    public static void getCtrl(Context objCtx) {
 		String sMethod = "getCtrl(): ";
 		String sLogMsgHdr = sClass + sMethod;    	
     	objLogger.info(sLogMsgHdr + "STUB: for get requests.");
     	
     	GetRequest objGetRequest = new GetRequest();
     	objGetRequest.getHandler(objCtx);
     	
     	//for now just call getAll
     	//getAll(objCtx);
     	
     }

    //
    //###

    public static void create(Context objCtx) {
		String sMethod = "create(): ";
		String sLogMsgHdr = sClass + sMethod;
    	
    	objLogger.info(sLogMsgHdr + "STUB: to create a client whenever an HTTP client sends a POST request to /clients");
    	
        String sUsername = objCtx.queryParam(csParamClientName);
        
        
        if (sUsername == null || sUsername.length() < ciMinNameLen) {
            throw new BadRequestResponse();
        } else {
        	alClients.add(sUsername);
            objCtx.status(ciStatusCodeSuccess);
        }
    }

	
}
