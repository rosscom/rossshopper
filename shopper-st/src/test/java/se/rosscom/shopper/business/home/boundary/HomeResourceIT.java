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
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author ulfrossang
 */
public class HomeResourceIT {
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/home");

       
    @Test
    public void crud() {
        
        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();
        System.out.println("Create a home: dagg daggstigen 20");
        
        // Create 
        Response postResponse = this.provider.target().request().post(Entity.json(homeToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("Check that we have a home dagg: " +location);
        
        // Find with name
        System.out.println("Find home dagg");
        JsonObject daggHome = this.provider.client().
               target(location).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));        
        System.out.println("daggHome: " + daggHome.getString("name") + " " + daggHome.getString("adress"));

        // listAll
        System.out.println("list all");
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allHomes = response.readEntity(JsonArray.class);
        System.err.println("allHomes " + allHomes);
        assertFalse(allHomes.isEmpty());
        
        // Update
        System.out.println("check update");
        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("adress", "huddinge").build();
        
        this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(updated));

        // Find again
        // Find
        JsonObject updateHome = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertTrue(updateHome.getString("adress").contains("huddinge"));  

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allHomes = response.readEntity(JsonArray.class);
        System.err.println("allHomes " + allHomes);

        
        
        // delete
        System.out.println("check delete");
        Response deleteResponse = this.provider.target().
               path("dagg").
               request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteResponse.getStatus(), is(204));
        
        // listAll again after delete
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allHomes = response.readEntity(JsonArray.class);
        System.err.println("allHomes " + allHomes);
    }

}
