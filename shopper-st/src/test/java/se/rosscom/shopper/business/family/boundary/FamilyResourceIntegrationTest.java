package se.rosscom.shopper.business.family.boundary;

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
import se.rosscom.shopper.business.UserAndTokenHelper;

/**
 *
 * @author ulfrossang
 */
public class FamilyResourceIntegrationTest {
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/family");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerAccount = buildWithURI("http://localhost:8080/shopper/api/account");
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");

    public String token = null;
  
//    @Test
    public void crud() {
        
        token = UserAndTokenHelper.generateTokenThroughRequest("shoppertest", "timon");
    
        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();
        
        Response postResponseHome = this.providerHome.target()
                .request()
                .header("Authorization", token)
                .post(Entity.json(homeToCreate));
        assertThat(postResponseHome.getStatus(),is(201));
        String locationHome = postResponseHome.getHeaderString("Location");
        
        // Find home with name
        JsonObject daggHome = this.provider.client().
               target(locationHome).
               request(MediaType.APPLICATION_JSON).
               header("Authorization", token).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));
        System.out.println("Find dagg home                : ok " + daggHome.toString());

        // Find admin account
        JsonObject adminAccount = this.providerAccount.client().
               target("http://localhost:8080/shopper/api/account/shoppertest").
               request(MediaType.APPLICATION_JSON).
               header("Authorization", token).
               get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("shoppertest"));   

        //Create a family
        JsonObjectBuilder familyBuilder =  Json.createObjectBuilder();
        JsonObject familyToCreate = familyBuilder.
                add("home", daggHome).
                add("account", adminAccount).build();

        Response postResponse = this.provider.target().request().header("Authorization", token).post(Entity.json(familyToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");

        //Find family
        JsonObject findFamily = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get(JsonObject.class);
        assertTrue(findFamily.getJsonObject("home").getString("name").contains("dagg"));

        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));
        assertThat(response.readEntity(JsonArray.class).size(), is(1));

        // Update family TODO?

        // Delete family
        Response deleteResponse = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                delete();
        assertThat(deleteResponse.getStatus(), is(204));

        // listAll again
        response = provider.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));
        assertThat(response.readEntity(JsonArray.class).size(), is(0));
    }

}
