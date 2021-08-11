package com.tlw8253.controller;

import com.tlw8253.application.Constants;
import com.tlw8253.dto.ExceptionMessageDTO;
import com.tlw8253.exception.AccountNotFoundException;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.ClientNotFoundException;
import com.tlw8253.exception.DatabaseException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller, Constants {

	
	private ExceptionHandler<DatabaseException> databaseExceptionHandler = (e, ctx) -> {
		ctx.status(ciStatusCodeInternalServerError); 
		
		// Here we encapsulate the exception message into a DTO that will be sent as JSON back to the user
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	
	private ExceptionHandler<ClientNotFoundException> ClientNotFoundExceptionHandler = (e, ctx) -> {
		ctx.status(ciStatusCodeNotFound); 
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	
	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx) -> {
		ctx.status(ciStatusCodeErrorBadRequest);
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	private ExceptionHandler<AccountNotFoundException> AccountNotFoundExceptionHandler = (e, ctx) -> {
		ctx.status(ciStatusCodeNotFound);
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(DatabaseException.class, databaseExceptionHandler);
		app.exception(ClientNotFoundException.class, ClientNotFoundExceptionHandler);
		app.exception(BadParameterException.class, badParameterExceptionHandler);
		app.exception(AccountNotFoundException.class, AccountNotFoundExceptionHandler);
	}


	
	
	
}











