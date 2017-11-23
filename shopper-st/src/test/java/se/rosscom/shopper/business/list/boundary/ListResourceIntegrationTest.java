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
import org.junit.Before;
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

        Response createListDetail = this.provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                post(Entity.json(listDetailJson));
        assertThat(createListDetail.getStatus(), is(201));
        String location = createListDetail.getHeaderString("Location");

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
