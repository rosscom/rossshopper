/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.home.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;

/**
 *
 * @author ulfrossang
 */
public class HomeResourceIntegrationTest {
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/home");

    private final String userId = "home";
    private final String token = UserAndTokenHelper.generateTokenThroughRequest(userId, "psw");

    @After
    public void tearDown() {
        EntityHelper.deleteAccountByUserId(userId, token);
    }
       
//    @Test
    public void crud() {

        String token = UserAndTokenHelper.generateTokenThroughRequest("user", "psw");

        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();
        
        // Create 
        Response postResponse = this.provider.target().request().header("Authorization", token).post(Entity.json(homeToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
                
        // Find with name
        JsonObject daggHome = this.provider.client().
               target(location).
               request(MediaType.APPLICATION_JSON).
               header("Authorization", token).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));

        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));
        JsonArray allHomes = response.readEntity(JsonArray.class);
        assertFalse(allHomes.isEmpty());
        
        // Update
        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("adress", "huddinge").build();
        
        this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                put(Entity.json(updated));
        assertThat(response.getStatus(),is(200));

        // Find again
        JsonObject updateHome = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateHome.getString("adress").contains("huddinge"));

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allHomes = response.readEntity(JsonArray.class);
        assertFalse(allHomes.isEmpty());
        
        
        // delete
        Response deleteResponse = this.provider.client().target(location).
               request(MediaType.APPLICATION_JSON).
               header("Authorization", token).
               delete();
        assertThat(deleteResponse.getStatus(), is(204));
        
        // listAll again after delete
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allHomes = response.readEntity(JsonArray.class);
        assertThat(allHomes.size(), is(0));
    }

}
