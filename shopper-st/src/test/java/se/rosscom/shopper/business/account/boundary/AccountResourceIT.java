/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.boundary;

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
public class AccountResourceIT {

    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/account");
    
    @Test
    public void crud() {

        // Create an account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToCreate = accountBuilder.
                add("user", "admin").
                add("password", "password").build();
        System.out.println("Create an account: admin password");
        
        // Create 
        Response postResponse = this.provider.target().
                request().
                post(Entity.json(accountToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("Check that we have an account admin: " +location);
        
       
        // Find
        System.out.println("Find admin account");
        JsonObject adminAccount = this.provider.client().
               target(location).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(adminAccount.getString("user").contains("admin"));   
        System.out.println("adminAccount: " + adminAccount.getString("user") + " " + adminAccount.getString("password"));
               
        // listAll
        System.out.println("list all");
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));

        JsonArray allAccounts = response.readEntity(JsonArray.class);
        System.err.println("allAccounts " + allAccounts);
        assertFalse(allAccounts.isEmpty());

        // Update
        System.out.println("check update");
        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("password", "nilsudden").build();
        
        this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(updated));

        // Find again
        // Find
        JsonObject updateAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertTrue(updateAccount.getString("password").contains("nilsudden"));  

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allAccounts = response.readEntity(JsonArray.class);
        System.err.println("allAccounts " + allAccounts);

        
        // Status update (login)
        System.out.println("check login");
        JsonObjectBuilder loginBuilder =  Json.createObjectBuilder();
        JsonObject loggedIn = loginBuilder.
                add("loggedIn", true).
                build();
        
        this.provider.client().
                target(location).
                path("login").
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(loggedIn));
 
        // Verify status
        updateAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertThat(updateAccount.getBoolean("loggedIn"), is(true));  
        
        // listAll again after login
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allAccounts = response.readEntity(JsonArray.class);
        System.err.println("allAccounts " + allAccounts);
    
        
        // delete
        System.out.println("check delete");
        Response deleteResponse = this.provider.target().
               path("admin").
               request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteResponse.getStatus(), is(204));

        // listAll again after delete
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        allAccounts = response.readEntity(JsonArray.class);
        System.err.println("allAccounts " + allAccounts);
    }
}
