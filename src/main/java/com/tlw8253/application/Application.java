package com.tlw8253.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.controller.Controller;
import com.tlw8253.controller.ExceptionController;
import com.tlw8253.controller.ClientController;
//import com.tlw8253.controller.TestController; TODO:

import io.javalin.Javalin;


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
	private static Javalin app;
	
	public static void main(String[] args) {
		String sMethod = "main(): ";
		
		objLogger.trace(sMethod + "Entered");
		
		app = Javalin.create();
		mapControllers(/*new TestController(),*/ new ClientController(), new ExceptionController());
		
		objLogger.info(sMethod + "Starting listening on port: [" + ciListingPort + "]");
		app.start(ciListingPort); // start up our Javalin server on port defined for this program
		
//		JavalinHelper objJavalinHelper = new JavalinHelper();
//		objJavalinHelper.createRoutes();
//		objJavalinHelper.start(ciListingPort);
		
	}
	
	
	//
	//###
	public static void mapControllers(Controller... controllers) {
		for (Controller c : controllers) {
			c.mapEndpoints(Application.app);
		}
	}


}



















