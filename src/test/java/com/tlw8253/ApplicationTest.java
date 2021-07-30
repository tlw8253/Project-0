package com.tlw8253;

import static org.junit.Assert.*;

import io.javalin.Javalin;
import io.javalin.http.Context;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ApplicationTest {
	private static Javalin staticJavalin;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass()");
		staticJavalin = Javalin.create(); //static instance of Javalin
		//staticJavalin.start(3005); //localhost listening port
		System.out.println("staticJavalin.port(): [" + staticJavalin.port() + "]");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp()");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown()");
	}

	@Test
	public void test() {
		
		System.out.println("public void test()");
		
		// STUB: to return all clients whenever an HTTP client sends a GET request to /clients
		staticJavalin.get("http://localhost:3005/clients", (Context ctx) -> {
			ctx.result("Clients: " + ctx.resultString());
		});
		System.out.println("staticJavalin.port(): [" + staticJavalin.port() + "]");
	}

}
