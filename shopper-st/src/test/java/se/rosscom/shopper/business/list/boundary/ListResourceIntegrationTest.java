/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

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
import org.junit.Before;
import org.junit.Test;
import se.rosscom.shopper.business.ClientWrapper;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;

public class ListResourceIntegrationTest {

    private String url = "http://localhost:8080/shopper/api/listdetail";
    private final String userId = "list";
    private final String homeName = "dagg";
    private String familyId;
    private String token;

    @After
    public void tearDown() {
        //family
        EntityHelper.deleteFamilyByFamilyId(familyId, token);

        //home
        EntityHelper.deleteHomeByHomeName(homeName, token);

        //account
        EntityHelper.deleteAccountByUserId(userId, token);
    }

    @Before
    public void createDependencies() {
        //create account
        token = UserAndTokenHelper.generateTokenThroughRequest(userId, "psw");

        //create home
        EntityHelper.createHomeWithName(homeName, token);

        //create family
        familyId = EntityHelper.createFamilyWithHomeNameAndUserId(homeName, userId, token).toString();
    }

    @Test
    public void crud() {

        // Create listDetail
        JsonObjectBuilder listDeailBuilder =  Json.createObjectBuilder();
        JsonObject listDetailJson = listDeailBuilder.
                add("familyId", familyId).
                add("item", "test-item").build();

        Response createListDetail = ClientWrapper.createClient(url).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                post(Entity.json(listDetailJson));
        assertThat(createListDetail.getStatus(), is(201));
        String location = createListDetail.getHeaderString("Location");

        // Find list detail
        JsonObject findListDetail = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(findListDetail.getString("item").contains("test-item"));


        // Delete list detail
        Response deleteListDetail = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteListDetail.getStatus(), is(204));


        // listAll again
        Response listAllResponse = ClientWrapper.createClient(url).request(MediaType.APPLICATION_JSON).header("Authorization", token).get();
        assertThat(listAllResponse.getStatus(),is(200));
        assertThat(listAllResponse.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getString("item").equals("test-item")), is(false));
    }

    
}
