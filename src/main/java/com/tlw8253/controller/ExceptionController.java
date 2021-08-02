package com.tlw8253.controller;

import com.tlw8253.dto.ExceptionMessageDTO;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.ClientNotFoundException;
import com.tlw8253.exception.DatabaseException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller {

	
	private ExceptionHandler<DatabaseException> databaseExceptionHandler = (e, ctx) -> {
		ctx.status(500); // 500 means "Internal Server Error"
		
		// Here we encapsulate the exception message into a DTO that will be sent as JSON back to the user
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	
	private ExceptionHandler<ClientNotFoundException> ClientNotFoundExceptionHandler = (e, ctx) -> {
		ctx.status(404); // 404 is "Not Found"
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	
	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx) -> {
		ctx.status(400);
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(DatabaseException.class, databaseExceptionHandler);
		app.exception(ClientNotFoundException.class, ClientNotFoundExceptionHandler);
		app.exception(BadParameterException.class, badParameterExceptionHandler);
	}


	
	
	
}











