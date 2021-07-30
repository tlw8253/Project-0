package com.tlw8253;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import org.junit.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/*
 * This class was taken by following instruction on the Javalin page on test tutorial https://javalin.io/tutorials/testing
 * 	Code example taken from GIT Clone of https://github.com/tipsy/javalin-testing-example.git
 * 	Source code from example are modified for this project
 */
public class UnitTest {

    private Context ctx = mock(Context.class);

    @Test
    public void POST_to_create_users_gives_201_for_valid_username() {
    	System.out.println("POST_to_create_users_gives_201_for_valid_username()");
        when(ctx.queryParam("username")).thenReturn("Roland");
//        UserController.create(ctx); // the handler we're testing
        verify(ctx).status(201);
    }

    @Test(expected = BadRequestResponse.class)
    public void POST_to_create_users_throws_for_invalid_username() {
    	System.out.println("POST_to_create_users_throws_for_invalid_username()");
        when(ctx.queryParam("username")).thenReturn(null);
//        UserController.create(ctx); // the handler we're testing
    }

}
