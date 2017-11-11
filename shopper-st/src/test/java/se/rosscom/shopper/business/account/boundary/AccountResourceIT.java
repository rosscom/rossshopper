/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import java.util.Base64;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
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
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerAccount = buildWithURI("http://localhost:8080/shopper/api/account");
    public JAXRSClientProvider providerLogin = buildWithURI("http://localhost:8080/shopper/api/auth/login");

    
    
    @Test
    public void crud() {

        // Create an account json
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToCreate = accountBuilder.
                add("userId", "shoppertest").
                add("password", "timon").
                add("choosedHome", "ej valt").build();
        
        // Create 
        Response postResponse = this.provider.target().
                request().
                post(Entity.json(accountToCreate));
                
        assertThat(postResponse.getStatus(),is(200));
        
        String basicAuthString =  "Basic " + Base64.getEncoder().encodeToString(("shoppertest:timon").getBytes());
        String token = this.providerLogin.target().
                request().
                header("Authorization", basicAuthString).
                get(String.class);
        assertThat(token, startsWith("Auth-shopper"));
        System.out.println("Create an account               : ok "+ accountToCreate.toString());
        System.out.println("token                           : " +token);
       
        String location = provider.target().getUri()+"/"+accountToCreate.getString("userId");
        // Find
        JsonObject adminAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("shoppertest"));   
        System.out.println("Find admin account              : ok " + adminAccount.toString());
        System.out.println("adminAccount                    : " + adminAccount.getString("userId") + " " + adminAccount.getString("password"));

        
        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));

        JsonArray allAccounts = response.readEntity(JsonArray.class);
        System.out.println("list allAccounts                : " + allAccounts);
        assertFalse(allAccounts.isEmpty());

        // Update
        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("password", "nilsudden").build();
        
        this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                put(Entity.json(updated));

        // Find again
        // Find
        JsonObject updateAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateAccount.getString("password").contains("nilsudden"));  
        System.out.println("check update                    : ok "+ updateAccount.toString());

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allAccounts = response.readEntity(JsonArray.class);
        System.out.println("list allAccounts                : " + allAccounts);

        // Status update (login)
        JsonObjectBuilder loginBuilder =  Json.createObjectBuilder();
        updateAccount = loginBuilder.
                add("password", "nilsudden").
                add("loggedIn", true).
                build();
       
        this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                put(Entity.json(updateAccount));
 
        // Verify status
        updateAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateAccount.getBoolean("loggedIn"));
        System.out.println("check login                     : ok "+ updateAccount.toString());
        
        // listAll again after login
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allAccounts = response.readEntity(JsonArray.class);
        System.out.println("list allAccounts                : " + allAccounts);
    
        
        // delete
        Response deleteResponse = this.provider.target().
                path("shoppertest").
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteResponse.getStatus(), is(204));
        System.out.println("check delete                    : ok");

        // listAll again after delete
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allAccounts = response.readEntity(JsonArray.class);
        System.err.println("list allAccounts                : " + allAccounts);
    }
  

}
