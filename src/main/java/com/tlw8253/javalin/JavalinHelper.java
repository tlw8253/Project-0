package com.tlw8253.javalin;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;
import static io.javalin.apibuilder.ApiBuilder.delete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;

/**
 *  This class is based on the Javalin webpage on test tutorial https://javalin.io/tutorials/testing
 * 	Code ideas from GIT Clone of https://github.com/tipsy/javalin-testing-example.git were used in
 * 	this source code and modified for this project
 * 
 * @author tlw8748253
 *
 */
public class JavalinHelper {
	private Logger objLogger = LoggerFactory.getLogger(JavalinHelper.class);
	private String sClass = "JavalinHelper{}: ";

	private Javalin staticJavalin;
/*
    private Javalin app = Javalin.create().routes(() -> {
        get("/users", UserController::getAll);
        post("/users", UserController::create);
        get("/ui", ctx -> ctx.html("<h1>User UI</h1>"));
    });
   */
	public JavalinHelper() {
		super();
	}
	
	/*
	 * - `POST /clients`: Creates a new client
	 * - `POST /clients/{client_id}/accounts`: Create a new account for a client with id of X (if client exists)
	 * 
	 * - `PUT /clients/{id}`: Update client with an id of X (if the client exists)
	 * - `PUT /clients/{client_id}/accounts/{account_id}`: Update account with id of Y belonging to client with id of 
	 * 		X (if client and account exist AND if account belongs to client)
	 * 
	 * - `DELETE /clients/{id}`: Delete client with an id of X (if the client exists)
	 * - `DELETE /clients/{client_id}/accounts/{account_id}`: Delete account with id of Y belonging to 
	 * 		client with id of X (if client and account exist AND if account belongs to client)
	 * 
	 * - `GET /clients`: Gets all clients
	 * - `GET /clients/{id}`: Get client with an id of X (if the client exists)
	 * - `GET /clients/{client_id}/accounts`: Get all accounts for client with id of X (if client exists)
	 * - `GET /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`: 
	 * 		Get all accounts for client id of X with balances between 400 and 2000 (if client exists)
	 * - `GET /clients/{client_id}/accounts/{account_id}`: Get account with id of Y belonging to client with id of 
	 * 		X (if client and account exist AND if account belongs to client)
	 * 
	 * 
	 */
	
	public void createRoutes() {
		String sMethod = "createRoutes(): ";
		String sLogMsgHdr = sClass + sMethod;
		
		objLogger.trace(sLogMsgHdr + "Entered");

		staticJavalin = Javalin.create().routes(() -> {
			post("/clients", ClientController::postCtrl);
	        get("/clients", ClientController::getCtrl);	        
	        put("/clients", ClientController::putCtrl);
	        delete("/clients", ClientController::deleteCtrl);
	    });
	}

	public void start(int port) {
        this.staticJavalin.start(port);
    }

    public void stop() {
        this.staticJavalin.stop();
    }


}



























