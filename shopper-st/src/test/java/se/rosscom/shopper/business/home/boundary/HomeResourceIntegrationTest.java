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
public class HomeResourceIntegrationTest {
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/home");

       
    @Test
    public void crud() {
        
        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();
        
        // Create 
        Response postResponse = this.provider.target().request().post(Entity.json(homeToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("Create a home                 : ok "+ homeToCreate.toString());
        System.out.println("dagg location                 : " +location);
                
        // Find with name
        JsonObject daggHome = this.provider.client().
               target(location).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));        
        System.out.println("Find dagg home                : ok " + daggHome.toString());

        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allHomes = response.readEntity(JsonArray.class);
        System.err.println("list allHomes                 : " + allHomes);
        assertFalse(allHomes.isEmpty());
        
        // Update
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
        System.out.println("check update                  : ok "+ updateHome.toString());

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allHomes = response.readEntity(JsonArray.class);
        System.err.println("list allHomes                 : " + allHomes);

        
        
        // delete
        Response deleteResponse = this.provider.target().
               path("dagg").
               request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteResponse.getStatus(), is(204));
        
        // listAll again after delete
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allHomes = response.readEntity(JsonArray.class);
        System.err.println("list allHomes                 : " + allHomes);
    }

}
