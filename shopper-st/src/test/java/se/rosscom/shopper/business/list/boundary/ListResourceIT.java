/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

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
import org.junit.Test;
import org.junit.Rule;

/**
 *
 * @author ulfrossang
 */
public class ListResourceIT {
    
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/listdetail");

       
    @Test
    public void crud() {
        
                
        JsonObjectBuilder listBuilder =  Json.createObjectBuilder();
        JsonObject listToCreate = listBuilder.
                add("item", "korv").build();
        System.out.println("Create a listitem: korv");
        Response postResponse = this.provider.target().request().post(Entity.json(listToCreate));
        assertThat(postResponse.getStatus(),is(204));
        
        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allListDetail = response.readEntity(JsonArray.class);
        System.err.println("allListDetail " + allListDetail);
        assertFalse(allListDetail.isEmpty());
        

    }

    
}
