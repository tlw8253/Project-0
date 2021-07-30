package com.tlw8253.javalin.requests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clients {
	private final static Logger objLogger = LoggerFactory.getLogger(Clients.class);
	private String sClass = "Clients{}: ";

	// TODO: this will get replaced with database access storing client information
    private List<String> alClients = new ArrayList<>(Arrays.asList("Client1", "Client2", "Client3"));

	public Clients() {
		super();
	}

	public List<String> getClients(){return(alClients);}
}
