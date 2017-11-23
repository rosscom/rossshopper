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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;

/**
 *
 * @author ulfrossang
 */
public class FamilyResourceIntegrationTest {

    @Rule
    public JAXRSClientProvider provider = buildWithURI("https://localhost:8080/shopper/api/family");

    private final String userId = "family";
    private final String homeName = "dagg";
    private String token;

    @Before
    public void createDependencies() {
        //create account
        token = UserAndTokenHelper.generateTokenThroughRequest(userId, "psw");

        //create home
        EntityHelper.createHomeWithName(homeName, token);
    }

    @After
    public void tearDown() {
        EntityHelper.deleteHomeByHomeName(homeName, token);
        EntityHelper.deleteAccountByUserId(userId, token);
    }
  
    @Test
    public void crud() {

        //Create a family
        JsonObjectBuilder familyBuilder =  Json.createObjectBuilder();
        JsonObject familyToCreate = familyBuilder.
                add("homeName", homeName).
                add("userId", userId).build();

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
        assertThat(response.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getJsonObject("home").getString("name").equals("dagg")), is(true));

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
        assertThat(response.readEntity(JsonArray.class).stream().anyMatch(json -> ((JsonObject) json).getJsonObject("home").getString("name").equals("dagg")), is(false));
    }

}
