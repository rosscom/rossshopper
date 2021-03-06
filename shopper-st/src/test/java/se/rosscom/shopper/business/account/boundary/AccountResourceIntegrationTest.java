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

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import se.rosscom.shopper.business.ClientWrapper;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;

/**
 *
 * @author ulfrossang
 */
public class AccountResourceIntegrationTest {


    private String url = "http://localhost:8080/shopper/api/account";
    private String lastToken;

    @After
    public void tearDown() {
        EntityHelper.deleteAccountByUserId("test", lastToken);
    }

    @Test
    public void crudAccount() {

        // Create account
        JsonObject accountToCreate = Json.createObjectBuilder().
                add("userId", "shoppertest").
                add("password", "timon").
                add("choosedHome", "ej valt").build();

        Response postResponse = ClientWrapper.createClient(url).
                request().
                post(Entity.json(accountToCreate));
        assertThat(postResponse.getStatus(),is(200));
        String token = UserAndTokenHelper.generateToken("shoppertest", "timon");
        String location = ClientWrapper.createClient(url).getUri()+"/"+accountToCreate.getString("userId");

        // Find
        JsonObject adminAccount = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("shoppertest"));

        // listAll
        Response response = ClientWrapper.createClient(url).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));
        assertThat(response.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getString("userId").equals("shoppertest")), is(true));

        // Update
        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
        JsonObject updated = updateBuilder.add("password", "nilsudden").build();

        ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                put(Entity.json(updated));

        // Find
        JsonObject updateAccount = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateAccount.getString("password").contains("nilsudden"));

        // Status update (login)
        JsonObjectBuilder loginBuilder =  Json.createObjectBuilder();
        updateAccount = loginBuilder.
                add("password", "nilsudden").
                add("loggedIn", true).
                build();

        ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                put(Entity.json(updateAccount));

        // Verify status
        updateAccount = ClientWrapper.createClient(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(updateAccount.getBoolean("loggedIn"));

        // delete
        Response deleteResponse = ClientWrapper.createClient(url).
                path("shoppertest").
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteResponse.getStatus(), is(204));

        // listAll again after delete
        lastToken = UserAndTokenHelper.generateTokenThroughRequest("test", "1");
        response = ClientWrapper.createClient(url).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", lastToken).
                get();
        assertThat(response.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getString("userId").equals("shoppertest")), is(false));
    }
  

}
