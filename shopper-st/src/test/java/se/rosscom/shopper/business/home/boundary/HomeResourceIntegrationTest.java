/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.home.boundary;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import se.rosscom.shopper.business.ClientWrapper;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;

/**
 *
 * @author ulfrossang
 */
public class HomeResourceIntegrationTest {

    public String url = "http://localhost:8080/shopper/api/home";

    private final String userId = "home";
    private final String token = UserAndTokenHelper.generateTokenThroughRequest(userId, "psw");

    @After
    public void tearDown() {
        EntityHelper.deleteAccountByUserId(userId, token);
    }

    @Test
    public void crud() {

        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();

        // Create
        Response postResponse = ClientWrapper.createClient(url).request().header("Authorization", token).post(Entity.json(homeToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");

        // Find with name
        JsonObject daggHome = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));

        // listAll
        Response response = ClientWrapper.createClient(url).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));
        assertThat(response.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getString("name").equals("dagg")), is(true));

        // Update
        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
        JsonObject updated = updateBuilder.add("adress", "huddinge").build();

        ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                put(Entity.json(updated));
        assertThat(response.getStatus(),is(200));

        // Find again
        JsonObject updateHome = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateHome.getString("adress").contains("huddinge"));

        // delete
        Response deleteResponse = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteResponse.getStatus(), is(204));

        // listAll again after delete
        response = ClientWrapper.createClient(url).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getString("name").equals("dagg")), is(false));
    }

}
