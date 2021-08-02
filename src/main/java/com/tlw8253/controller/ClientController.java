package com.tlw8253.controller;

import java.util.List;

import com.tlw8253.service.ClientService;
import com.tlw8253.model.Client;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController implements Controller {
	
	
	private ClientService objClientService;
	
	public ClientController() {
		this.objClientService = new ClientService();
	}
	

	private Handler getAllClients = (ctx) -> {		
		List<Client> lstClients = objClientService.getAllClients();
		
		ctx.status(200); // 200 means OK
		ctx.json(lstClients);
	};


	
	
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/get", getAllClients);
		
		
		/*
		app.get("/ship", getAllShips);
		app.get("/ship/:shipid", getShipById);
		app.post("/ship", addShip);
		app.put("/ship/:shipid", editShip);
		app.delete("/ship/:shipid", deleteShip);
		*/
	}

}
