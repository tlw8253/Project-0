package com.tlw8253.javalin.requests;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.javalin.JavalinConstants;

import io.javalin.http.Context;

/**
 * 
 * - `GET /clients`: Gets all clients ==> getAllClients(Context objCtx)
 * 
 * - `GET /clients/{id}`: Get client with an id of X (if the client exists) ==>
 * getClientById(Context objCtx, String sClientId)
 * 
 * - `GET /clients/{client_id}/accounts`: Get all accounts for client with id of
 * X (if client exists) ==> getClientAccounts(Context objCtx, String sClientId)
 * 
 * - `GET /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`: Get
 * all accounts for client id of X with balances between 400 and 2000 (if client
 * exists)
 * 
 * - `GET /clients/{client_id}/accounts/{account_id}`: Get account with id of Y
 * belonging to client with id of X (if client and account exist AND if account
 * belongs to client)
 * 
 * 
 * @author tlw87
 *
 */
public class GetRequest implements JavalinConstants {
	private final static Logger objLogger = LoggerFactory.getLogger(GetRequest.class);
	private static String sClass = "GetRequest{}: ";

	public GetRequest() {
		super();
	}

	public void getHandler(Context objCtx) {// should be able to control all get request based on parameters
		String sMethod = "getHandler(): ";
		String sLogMsgHdr = sClass + sMethod;
		objLogger.trace(sLogMsgHdr + "Entered.");

		Map<String, List<String>> mapQueryParam = objCtx.queryParamMap();
		if (mapQueryParam.isEmpty()) {
			getAllClients(objCtx);
		} else {
			/*
			 * The following code is based on an example @:
			 * https://www.tabnine.com/code/java/methods/io.javalin.Context/queryParamMap
			 */
			// parse the parameters
			objLogger.info(sLogMsgHdr + "Server query string for configuration {}", mapQueryParam);
			for (Entry<String, List<String>> entry : mapQueryParam.entrySet()) {
				String configKey = "wdm." + entry.getKey();
				String configValue = entry.getValue().get(0);
				objLogger.info("\t{} = {}", configKey, configValue);
				// System.setProperty(configKey, configValue);
			}

			String sClientId = objCtx.queryParam(csParamClientId);
			if (sClientId != null) {
				// - `GET /clients/{id}`: Get client with an id of X (if the client exists)
				objLogger.info(sLogMsgHdr + "sClientId: [" + sClientId + "]");
				getClientById(objCtx, sClientId);

			} else {
				String sClientIdForAccounts = objCtx.queryParam(csParamClientIdForAccts);
				if (sClientIdForAccounts != null) {
					// - `GET /clients/{client_id}/accounts`: Get all accounts for client with id of
					// X (if client exists)
					objLogger.info(sLogMsgHdr + "sClientIdForAccounts: [" + sClientIdForAccounts + "]");
					getClientAccounts(objCtx, sClientIdForAccounts);
				} else {
					String sClientIdForAccountsRange = objCtx.queryParam(csParamClientIdForAcctsRange);
					if (sClientIdForAccountsRange != null) {
						objLogger.info(sLogMsgHdr + "sClientIdForAccountsRange: [" + sClientIdForAccountsRange + "]");
						getClientAccountsRange(objCtx, sClientIdForAccountsRange);
					} else {
						objLogger.warn(sLogMsgHdr + "GET request not defined.");
						objCtx.result(sLogMsgHdr + "GET request not defined.");
					}
				}

			}

		}
	}

	//XXXXXXXXXXXXXXXXX STOPPED HERE 07/28/2021
	//
	// ### - `GET /clients/{client_id}/accounts?amountLessThan=2000 & amountGreaterThan=400`: Get
	// all accounts for client id of X with balances between 400 and 2000 (if client exists)
	private void getClientAccountsRange(Context objCtx, String sClientId) {
		String sMethod = "getClientAccountsRange(): ";
		String sLogMsgHdr = sClass + sMethod;
		objLogger.trace(sLogMsgHdr + "Entered.");

		int iClientId = 0;
		if (isInt(sClientId)) {
			iClientId = Integer.parseInt(sClientId);			
			objLogger.debug(sLogMsgHdr + "iClientId: [" + iClientId + "]");

			String sLowerRange = objCtx.queryParam(csParamAcctsLowerRange);
			String sUpperRange = objCtx.queryParam(csParamAcctsUpperRange);
			if ((sLowerRange != null) && (sUpperRange != null)) {
				if (isInt(sLowerRange) && isInt(sUpperRange)) {
					int iLowerRange = Integer.parseInt(sLowerRange);
					int iUpperRange = Integer.parseInt(sUpperRange);
					objLogger.debug(sLogMsgHdr + "valid range iLowerRange: [" + iLowerRange + "] iUpperRange: [" + iUpperRange + "]");
					
					String sMsg = "iClientId: [" + iClientId + "] iLowerRange: [" + iLowerRange + "] iUpperRange: [" + iUpperRange + "]";
					objLogger.debug(sLogMsgHdr + sMsg);
					objCtx.result(sLogMsgHdr + sMsg);

				} else {
					String sMsg = "Range parameters not integers: sLowerRange: [" + sLowerRange + "] sUpperRange ["
							+ sUpperRange + "]";
					objLogger.warn(sLogMsgHdr + sMsg);
					objCtx.result(sLogMsgHdr + sMsg);
				}
			} else {
				String sMsg = "Invalid range parameters: sLowerRange: [" + sLowerRange + "] sUpperRange [" + sUpperRange
						+ "]";
				objLogger.warn(sLogMsgHdr + sMsg);
				objCtx.result(sLogMsgHdr + sMsg);
			}
			//objCtx.result(sLogMsgHdr + "iClientId: " + iClientId);
		} else {
			objCtx.result(sLogMsgHdr + "Parameter received not a valid Client Id: sClientId: " + sClientId);
		}
	}

	//
	// ### - `GET /clients/{client_id}/accounts`: Get all accounts for client with
	// id of X (if client exists)
	private void getClientAccounts(Context objCtx, String sClientId) {
		String sMethod = "getClientAccounts(): ";
		String sLogMsgHdr = sClass + sMethod;
		objLogger.trace(sLogMsgHdr + "Entered.");

		int iClientId = 0;
		if (isInt(sClientId)) {
			iClientId = Integer.parseInt(sClientId);
			objCtx.result(sLogMsgHdr + "iClientId: " + iClientId);
		} else {
			objCtx.result(sLogMsgHdr + "Parameter received not a valid Client Id: sClientId: " + sClientId);
		}
	}

	//
	// ### - `GET /clients/{id}`: Get client with an id of X (if the client exists)
	private void getClientById(Context objCtx, String sClientId) {
		String sMethod = "getClientById(): ";
		String sLogMsgHdr = sClass + sMethod;
		objLogger.trace(sLogMsgHdr + "Entered.");

		int iClientId = 0;
		if (isInt(sClientId)) {
			iClientId = Integer.parseInt(sClientId);
			objCtx.result(sLogMsgHdr + "iClientId: " + iClientId);
		} else {
			objCtx.result(sLogMsgHdr + "Parameter received not a valid Client Id: sClientId: " + sClientId);
		}
	}

	//
	// ### - `GET /clients`: Gets all clients
	private void getAllClients(Context objCtx) {
		String sMethod = "create(): ";
		String sLogMsgHdr = sClass + sMethod;
		objLogger.info(sLogMsgHdr + "Entered:");

		objCtx.json(new Clients().getClients());
	}

	//
	// ### utility method, could move to utility class
	private boolean isInt(String sInt) {
		String sMethod = "parseInt(): ";
		String sLogMsgHdr = sClass + sMethod;
		boolean bRet = false;

		objLogger.trace(sLogMsgHdr + "Entered.");

		try {
			int iInt = Integer.parseInt(sInt);
			bRet = true;
			objLogger.debug(sLogMsgHdr + "iInt: [" + iInt + "]");
		} catch (NumberFormatException objE) {
			objLogger.warn(sLogMsgHdr + "NumberFormatException: [" + objE.getMessage() + "]");
		}
		return (bRet);
	}

}
