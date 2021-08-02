package com.tlw8253.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.exception.DatabaseException;
import com.tlw8253.javalin.JavalinHelper;
import com.tlw8253.model.Client;
import com.tlw8253.service.ClinetService;

/**
 * This is the main driver for this project.
 * 
 * Concepts, classes, methods and coding are in part based on the javalin-jdbc-demo and jdbc-demo projects
 * shown in the Revature java-with-automation training by Bach Tran.  This project is not entirely a direct copy
 * from the training but concepts and coding examples where used and modified for the purposes of Project-0.  
 * With that said, parts of the code where used verbatim with class, method, and variable name changes.
 * 
 * Another source for inspiration and code manipulation came from the Javalin website: https://javalin.io/tutorials/testing
 * and a cloning of the https://github.com/tipsy/javalin-testing-example.git repository.  Code was modified for starting
 * this project.  However not much will remain at the end once implementing the .app, .controller, .dao, .dto. .exception, .model,
 * .service, .util model from the Revature training.  The Javalin website was extremely helpful in starting this project prior
 * to the javalin-jdbc-demo and jdbc-demo training.
 * 
 * @author tlw8253
 *
 */
public class Application implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");
		
//		JavalinHelper objJavalinHelper = new JavalinHelper();
//		objJavalinHelper.createRoutes();
//		objJavalinHelper.start(ciListingPort);
		
		ClinetService objClientService = new ClinetService();
		
		try {
			String sMsg = "";
			List<Client> lstClients = objClientService.getAllClients();
			for(int iCtr=0; iCtr<lstClients.size(); iCtr++) {
				sMsg = sMethod + "List element: [" + iCtr + "]: " + lstClients.get(iCtr).toString();
				objLogger.info(sMsg);
				
			}
				
			
		}
		catch(DatabaseException objE) {
			objLogger.error(sMethod + "DatabaseException: [" + objE + "]");
			
		}
		

	}

}
