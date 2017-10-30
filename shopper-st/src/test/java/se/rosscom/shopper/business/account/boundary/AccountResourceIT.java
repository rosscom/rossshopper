/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
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
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerAccount = buildWithURI("http://localhost:8080/shopper/api/account");
    
    
    @Test
    public void crud() {

        // Create an account json
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToCreate = accountBuilder.
                add("userId", "admin").
                add("password", "password").
                add("choosedHome", "ej valt").build();
        
        // Create 
        Response postResponse = this.provider.target().
                request().
                post(Entity.json(accountToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("Create an account               : ok "+ accountToCreate.toString());
        System.out.println("admin location                  : " +location);
       
        // Find
        JsonObject adminAccount = this.provider.client().
               target(location).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("admin"));   
        System.out.println("Find admin account              : ok " + adminAccount.toString());
        System.out.println("adminAccount                    : " + adminAccount.getString("userId") + " " + adminAccount.getString("password"));

        
        // listAll
//        Response response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        assertThat(response.getStatus(),is(200));
//
//        JsonArray allAccounts = response.readEntity(JsonArray.class);
//        System.err.println("list allAccounts                : " + allAccounts);
//        assertFalse(allAccounts.isEmpty());

        // Update
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
        System.out.println("check update                    : ok "+ updateAccount.toString());

        // listAll again
//        response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        allAccounts = response.readEntity(JsonArray.class);
//        System.err.println("list allAccounts                : " + allAccounts);

        // Status update (login)
        JsonObjectBuilder loginBuilder =  Json.createObjectBuilder();
        updateAccount = loginBuilder.
                add("password", "nilsudden").
                add("loggedIn", true).
                build();
       
        this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(updateAccount));
 
        // Verify status
        updateAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertTrue(updateAccount.getBoolean("loggedIn"));
        System.out.println("check login                     : ok "+ updateAccount.toString());
        
        // listAll again after login
//        response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        allAccounts = response.readEntity(JsonArray.class);
//        System.err.println("list allAccounts                : " + allAccounts);
    
        
        // delete
//        Response deleteResponse = this.provider.target().
//               path("admin").
//               request(MediaType.APPLICATION_JSON).delete();
//        assertThat(deleteResponse.getStatus(), is(204));
//        System.out.println("check delete                    : ok");

        // listAll again after delete
//        response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        allAccounts = response.readEntity(JsonArray.class);
//        System.err.println("list allAccounts                : " + allAccounts);
    }
         
//    @Test
    public void testLogin() {
        
        // Find admin account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToFind = accountBuilder.
                add("userId", "admin").
                add("password", "password").build();
        System.out.println("Find this account: dagg daggstigen 20");
        
//        String location = this.providerFamily.target().getUriBuilder().toString();
//        System.out.println("location                      : ok "+location );
//
//        JsonArray accountFamily = this.providerFamily.client().
//                target(location).
//                path(accountToFind.getString("user")).
//                request(MediaType.APPLICATION_JSON).
//                get(JsonArray.class);
//        assertTrue(accountFamily.size()>0);
//        System.out.println("                              :"+accountToFind.getString("user"));
//        System.err.println("list family                   : " + accountFamily);

//        Response postResponseAccount = this.providerFamily.target().
//                path("user").
//                request().
//                get(Entity.json(accountToFind));
//        
//        System.out.println("Find admin account");
//        assertTrue(null!=postResponseAccount.getLocation());   
 //       System.out.println("adminAccount: " + postResponseAccount.getString("user") + " " + postResponseAccount.getString("password"));

        
        
    }

}
