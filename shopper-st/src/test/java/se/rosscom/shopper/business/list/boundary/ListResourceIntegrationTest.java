/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;

import javax.json.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.Rule;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;

public class ListResourceIntegrationTest {
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/listdetail");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");

    private final String userId = "user";
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
       
    @Test
    public void crud() {

        token = UserAndTokenHelper.generateTokenThroughRequest(userId, "psw");

        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", homeName).
                add("adress", "daggstigen 20").build();
        
        Response postResponseHome = this.providerHome.target().request().header("Authorization", token).post(Entity.json(homeToCreate));
        assertThat(postResponseHome.getStatus(),is(201));

        // Create a family
        JsonObjectBuilder familyBuilder =  Json.createObjectBuilder();
        JsonObject familyToCreate = familyBuilder.
                add("homeName", homeName).
                add("userId", userId).build();

        Response postResponse = this.providerFamily.target().request().header("Authorization", token).post(Entity.json(familyToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");

        // find family
        JsonObject findFamily = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(findFamily.getJsonObject("home").getString("name").contains(homeName));

        // Create listDetail
        JsonObjectBuilder listDeailBuilder =  Json.createObjectBuilder();
        familyId = findFamily.get("familyId").toString();
        JsonObject listDetailJson = listDeailBuilder.
                add("familyId", familyId).
                add("item", "test-item").build();

        Response createListDetail = this.provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                post(Entity.json(listDetailJson));
        assertThat(createListDetail.getStatus(), is(201));
        location = createListDetail.getHeaderString("Location");

        // Find list detail
        JsonObject findListDetail = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(findListDetail.getString("item").contains("test-item"));


        // Delete list detail
        Response deleteListDetail = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteListDetail.getStatus(), is(204));
        

        // listAll again
        Response listAllResponse = provider.target().request(MediaType.APPLICATION_JSON).header("Authorization", token).get();
        assertThat(listAllResponse.getStatus(),is(200));
        assertThat(listAllResponse.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getString("item").equals("test-item")), is(false));
    }
}
