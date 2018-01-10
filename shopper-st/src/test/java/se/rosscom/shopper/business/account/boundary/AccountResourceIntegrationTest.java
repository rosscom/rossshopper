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
import se.rosscom.shopper.business.UserAndTokenHelper;

/**
 *
 * @author ulfrossang
 */
public class AccountResourceIntegrationTest {

    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/account");
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerAccount = buildWithURI("http://localhost:8080/shopper/api/account");
    public JAXRSClientProvider providerLogin = buildWithURI("http://localhost:8080/shopper/api/auth/login");

    public String token = null;
    
    

//    @Test
    public void crudAccount() {

        // Create account
        JsonObject accountToCreate = Json.createObjectBuilder().
                add("userId", "shoppertest").
                add("password", "timon").
                add("choosedHome", "ej valt").build();

        Response postResponse = this.provider.target().
                request().
                post(Entity.json(accountToCreate));
        assertThat(postResponse.getStatus(),is(200));
        
        token = UserAndTokenHelper.generateToken("shoppertest", "timon");
       
        String location = provider.target().getUri()+"/"+accountToCreate.getString("userId");
        // Find
        JsonObject adminAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("shoppertest"));   
        
        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));

        JsonArray allAccounts = response.readEntity(JsonArray.class);
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

        // Find
        JsonObject updateAccount = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateAccount.getString("password").contains("nilsudden"));  

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allAccounts = response.readEntity(JsonArray.class);

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
        
        // listAll again after login
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        allAccounts = response.readEntity(JsonArray.class);

        // delete
        Response deleteResponse = this.provider.target().
                path("shoppertest").
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteResponse.getStatus(), is(204));

        // listAll again after delete
        String lastToken = UserAndTokenHelper.generateTokenThroughRequest("test", "1");
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", lastToken).
                get();
        int allAccResponseSize = response.readEntity(JsonArray.class).size();
        assertThat(allAccResponseSize, is(1));

        // delete test user
        deleteResponse = this.provider.target().
                path("test").
                request(MediaType.APPLICATION_JSON).
                header("Authorization", lastToken).
                delete();
        assertThat(deleteResponse.getStatus(), is(204));

    }
  

}
