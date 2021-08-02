package com.tlw8253.controller;

import io.javalin.Javalin;

public interface Controller {

	public abstract void mapEndpoints(Javalin app);
	
}
