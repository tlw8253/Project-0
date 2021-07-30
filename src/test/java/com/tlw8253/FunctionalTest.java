package com.tlw8253;

import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import com.tlw8253.javalin.JavalinHelper;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * This class was taken by following instruction on the Javalin page on test tutorial https://javalin.io/tutorials/testing
 * 	Code example taken from GIT Clone of https://github.com/tipsy/javalin-testing-example.git
 * 	Source code from example are modified for this project
 */
public class FunctionalTest {

    private JavalinHelper app = new JavalinHelper(); // inject any dependencies you might have
    //private String usersJson = JavalinJson.toJson(UserController.getUserList());

    @Test
    public void GET_to_fetch_users_returns_list_of_users() {
        app.start(1234);
        HttpResponse response = Unirest.get("http://localhost:1234/users").asString();
        assertThat(response.getStatus()).isEqualTo(200);
//        assertThat(response.getBody()).isEqualTo(usersJson);
        app.stop();
    }

}
